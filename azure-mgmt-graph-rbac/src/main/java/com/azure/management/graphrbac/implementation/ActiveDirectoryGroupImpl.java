/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.graphrbac.implementation;

import com.azure.core.http.rest.PagedFlux;
import com.azure.core.util.serializer.SerializerAdapter;
import com.azure.core.util.serializer.SerializerEncoding;
import com.azure.management.graphrbac.ActiveDirectoryGroup;
import com.azure.management.graphrbac.ActiveDirectoryObject;
import com.azure.management.graphrbac.ActiveDirectoryUser;
import com.azure.management.graphrbac.GroupAddMemberParameters;
import com.azure.management.graphrbac.GroupCreateParameters;
import com.azure.management.graphrbac.ServicePrincipal;
import com.azure.management.graphrbac.models.*;
import com.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
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
        return Collections.unmodifiableSet(new HashSet(listMembersAsync().buffer().blockLast()));
    }

    @Override
    public PagedFlux<ActiveDirectoryObject> listMembersAsync() {
        return getManager().getInner().groups().getGroupMembersAsync(getId())
                .mapPage(directoryObjectInner -> {
                    String objectType = directoryObjectInner.getClass().getName();
                    if (UserInner.class.getName().equals(objectType)) {
                        return new ActiveDirectoryUserImpl((UserInner) directoryObjectInner, getManager());
                    } else if (ADGroupInner.class.getName().equals(objectType)) {
                        return new ActiveDirectoryGroupImpl((ADGroupInner)directoryObjectInner, getManager());
                    } else if (ServicePrincipalInner.class.getName().equals(objectType)) {
                        return new ServicePrincipalImpl((ServicePrincipalInner)directoryObjectInner, getManager());
                    } else if (ApplicationInner.class.getName().equals(objectType)) {
                        return new ActiveDirectoryApplicationImpl((ApplicationInner)directoryObjectInner, getManager());
                    } else {
                        return null;
                    }
                });
    }

    @Override
    protected Mono<ADGroupInner> getInnerAsync() {
        return getManager().getInner().groups().getAsync(getId());
    }

    @Override
    public boolean isInCreateMode() {
        return getId() == null;
    }

    @Override
    public Mono<ActiveDirectoryGroup> createResourceAsync() {
        Mono<?> group = Mono.just(this);
        if (isInCreateMode()) {
            group = getManager().getInner().groups().createAsync(createParameters)
                    .map(innerToFluentMap(this));
        }
        if (!membersToRemove.isEmpty()) {
            group = group.flatMap((Function<Object, Mono<?>>) o -> Mono.just(membersToRemove.iterator())
                    .flatMap((Function<Iterator<String>, Mono<?>>) stringIterator -> {
                        if (stringIterator.hasNext()) {
                            return getManager().getInner().groups().removeMemberAsync(getId(), stringIterator.next());
                        }
                        return null;
                    })
                    .doOnSuccess(o1 -> membersToRemove.clear()));
        }
        if (!membersToAdd.isEmpty()) {
//            group = group.flatMap((Function<Object, Mono<?>>) o -> Mono.just(membersToAdd.iterator())
//                    .flatMap((Function<Iterator<String>, Mono<?>>) stringIterator -> {
//                        if (stringIterator.hasNext()) {
//                            return getManager().getInner().groups().addMemberAsync(getId(), new GroupAddMemberParameters().setUrl(stringIterator.next())).then(Mono.just(ActiveDirectoryGroupImpl.this));
//                        }
//                        return null;
//                    })
//                    .doOnSuccess(o12 -> membersToAdd.clear()));

            for (String member : membersToAdd) {
                group = group.flatMap(ignore -> getManager().getInner().groups().addMemberAsync(getId(), new GroupAddMemberParameters().setUrl(member))).then(Mono.just(this));
            }
        }
        return group.map((Function<Object, ActiveDirectoryGroup>) o -> ActiveDirectoryGroupImpl.this);
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
        membersToAdd.add(String.format("%s%s/directoryObjects/%s",
                getManager().getInner().getHost(), getManager().tenantId(), objectId));
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
