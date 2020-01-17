/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.graphrbac.implementation;

import com.azure.core.util.serializer.SerializerAdapter;
import com.azure.management.graphrbac.ActiveDirectoryGroup;
import com.azure.management.graphrbac.ActiveDirectoryObject;
import com.azure.management.graphrbac.ActiveDirectoryUser;
import com.azure.management.graphrbac.GroupCreateParameters;
import com.azure.management.graphrbac.ServicePrincipal;
import com.azure.management.graphrbac.models.ADGroupInner;
import com.azure.management.graphrbac.models.ApplicationInner;
import com.azure.management.graphrbac.models.DirectoryObjectInner;
import com.azure.management.graphrbac.models.ServicePrincipalInner;
import com.azure.management.graphrbac.models.UserInner;
import com.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.microsoft.azure.Page;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.graphrbac.ActiveDirectoryGroup;
import com.microsoft.azure.management.graphrbac.ActiveDirectoryObject;
import com.microsoft.azure.management.graphrbac.ActiveDirectoryUser;
import com.microsoft.azure.management.graphrbac.ServicePrincipal;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import com.microsoft.rest.protocol.SerializerAdapter;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Implementation for Group and its parent interfaces.
 */
class ActiveDirectoryGroupImpl
        extends CreatableUpdatableImpl<ActiveDirectoryGroup, ADGroupInner, ActiveDirectoryGroupImpl>
        implements ActiveDirectoryGroup,
            ActiveDirectoryGroup.Definition,
            ActiveDirectoryGroup.Update {

    private final GraphRbacManager manager;
    private GroupCreateParameters createParameters;
    private String domainName;
    private Set<String> membersToAdd;
    private Set<String> membersToRemove;

    ActiveDirectoryGroupImpl(ADGroupInner innerModel, GraphRbacManager manager) {
        super(innerModel.getDisplayName(), innerModel);
        this.manager = manager;
        this.createParameters = new GroupCreateParameters()
                .setDisplayName(innerModel.getDisplayName())
                .setMailEnabled(false)
                .setSecurityEnabled(true);
        membersToAdd = new HashSet<>();
        membersToRemove = new HashSet<>();
    }

    @Override
    public boolean securityEnabled() {
        return getInner().isSecurityEnabled() != null && getInner().isSecurityEnabled().booleanValue() == true;
    }

    @Override
    public String mail() {
        return getInner().getMail();
    }

    @Override
    public Set<ActiveDirectoryObject> listMembers() {
        return Collections.unmodifiableSet(new HashSet<ActiveDirectoryObject>(listMembersAsync().map(new Function<ActiveDirectoryObject, Object>() {
        }).toBlocking().single()));
    }

    @Override
    public Mono<ActiveDirectoryObject> listMembersAsync() {
        return getManager().inner().groups().getGroupMembersAsync(getId())
                .map(new Function<DirectoryObjectInner, ActiveDirectoryObject>() {
                    @Override
                    public ActiveDirectoryObject apply(DirectoryObjectInner aadObjectInner) {
                        getManager().inner().
                        SerializerAdapter<?> adapter = getManager().inner().getHttpPipeline().getPolicy(0).restClient().serializerAdapter();
                        try {
                            String serialized = adapter.serialize(aadObjectInner);
                            switch (aadObjectInner.objectType()) {
                                case "User":
                                    return new ActiveDirectoryUserImpl(adapter.<UserInner>deserialize(serialized, UserInner.class), manager());
                                case "Group":
                                    return new ActiveDirectoryGroupImpl(adapter.<ADGroupInner>deserialize(serialized, ADGroupInner.class), manager());
                                case "ServicePrincipal":
                                    return new ServicePrincipalImpl(adapter.<ServicePrincipalInner>deserialize(serialized, ServicePrincipalInner.class), manager());
                                case "Application":
                                    return new ActiveDirectoryApplicationImpl(adapter.<ApplicationInner>deserialize(serialized, ApplicationInner.class), manager());
                                default:
                                    return null;
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }

    @Override
    protected Mono<ADGroupInner> getInnerAsync() {
        return getManager().inner().groups().getAsync(getId());
    }

    @Override
    public boolean isInCreateMode() {
        return getId() == null;
    }

    @Override
    public Mono<ActiveDirectoryGroup> createResourceAsync() {
        Mono<?> group = Mono.just(this);
        if (isInCreateMode()) {
            group = getManager().inner().groups().createAsync(createParameters)
                    .map(innerToFluentMap(this));
        }
        if (!membersToRemove.isEmpty()) {
            group = group.flatMap(new Function<Object, Mono<?>>() {
                @Override
                public Mono<?> apply(Object o) {
                    return Mono.from(membersToRemove)
                            .flatMap(new Function<String, Mono<?>>() {
                                @Override
                                public Mono<?> apply(String s) {
                                    return manager().inner().groups().removeMemberAsync(getId(), s);
                                }
                            }).last().doOnCompleted(new Action0() {
                                @Override
                                public void call() {
                                    membersToRemove.clear();
                                }
                            });
                }
            });
        }
        if (!membersToAdd.isEmpty()) {
            group = group.flatMap(new Func1<Object, Observable<?>>() {
                @Override
                public Observable<?> call(Object o) {
                    return Observable.from(membersToAdd)
                            .flatMap(new Func1<String, Observable<?>>() {
                                @Override
                                public Observable<?> call(String s) {
                                    return manager().inner().groups().addMemberAsync(id(), new GroupAddMemberParametersInner().withUrl(s));
                                }
                            }).last().doOnCompleted(new Action0() {
                                @Override
                                public void call() {
                                    membersToAdd.clear();
                                }
                            });
                }
            });
        }
        return group.map(new Func1<Object, ActiveDirectoryGroup>() {
            @Override
            public ActiveDirectoryGroup call(Object o) {
                return ActiveDirectoryGroupImpl.this;
            }
        });
    }

    @Override
    public ActiveDirectoryGroupImpl withEmailAlias(String mailNickname) {
        // User providing domain
        if (mailNickname.contains("@")) {
            String[] parts = mailNickname.split("@");
            domainName = parts[1];
            mailNickname = parts[0];
        }
        createParameters.setMailNickname(mailNickname);
        return this;
    }

    @Override
    public ActiveDirectoryGroupImpl withMember(String objectId) {
        membersToAdd.add(String.format("https://%s/%s/directoryObjects/%s",
                getManager().inner().retrofit().baseUrl().host(), getManager().tenantId(), objectId));
        return this;
    }

    @Override
    public ActiveDirectoryGroupImpl withMember(ActiveDirectoryUser user) {
        return withMember(user.getId());
    }

    @Override
    public ActiveDirectoryGroupImpl withMember(ActiveDirectoryGroup group) {
        return withMember(group.getId());
    }

    @Override
    public ActiveDirectoryGroupImpl withMember(ServicePrincipal servicePrincipal) {
        return withMember(servicePrincipal.getId());
    }

    @Override
    public ActiveDirectoryGroupImpl withoutMember(String objectId) {
        membersToRemove.add(objectId);
        return this;
    }

    @Override
    public ActiveDirectoryGroupImpl withoutMember(ActiveDirectoryUser user) {
        return withoutMember(user.getId());
    }

    @Override
    public ActiveDirectoryGroupImpl withoutMember(ActiveDirectoryGroup group) {
        return withoutMember(group.getId());
    }

    @Override
    public ActiveDirectoryGroupImpl withoutMember(ServicePrincipal servicePrincipal) {
        return withoutMember(servicePrincipal.getId());
    }

    @Override
    public String getId() {
        return getInner().getObjectId();
    }

    @Override
    public GraphRbacManager getManager() {
        return this.manager;
    }
}
