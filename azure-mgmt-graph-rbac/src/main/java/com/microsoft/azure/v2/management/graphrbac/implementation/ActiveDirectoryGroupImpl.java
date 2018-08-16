/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.graphrbac.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.graphrbac.ActiveDirectoryGroup;
import com.microsoft.azure.v2.management.graphrbac.ActiveDirectoryObject;
import com.microsoft.azure.v2.management.graphrbac.ActiveDirectoryUser;
import com.microsoft.azure.v2.management.graphrbac.GroupAddMemberParameters;
import com.microsoft.azure.v2.management.graphrbac.GroupCreateParameters;
import com.microsoft.azure.v2.management.graphrbac.ServicePrincipal;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.Utils;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.microsoft.rest.v2.protocol.SerializerAdapter;
import io.reactivex.Maybe;
import io.reactivex.Observable;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation for Group and its parent interfaces.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Graph.RBAC.Fluent")
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
        super(innerModel.displayName(), innerModel);
        this.manager = manager;
        this.createParameters = new GroupCreateParameters()
                .withDisplayName(innerModel.displayName())
                .withMailEnabled(false)
                .withSecurityEnabled(true);
        membersToAdd = new HashSet<>();
        membersToRemove = new HashSet<>();
    }

    @Override
    public String id() {
        return inner().objectId();
    }

    @Override
    public String name() {
        return inner().displayName();
    }

    @Override
    public boolean securityEnabled() {
        return Utils.toPrimitiveBoolean(inner().securityEnabled());
    }

    @Override
    public String mail() {
        return inner().mail();
    }

    @Override
    public Set<ActiveDirectoryObject> listMembers() {
        return Collections.unmodifiableSet(new HashSet<>(listMembersAsync().toList().blockingGet()));
    }

    @Override
    public Observable<ActiveDirectoryObject> listMembersAsync() {
        Observable<ActiveDirectoryObject> adObjObservable = manager().inner().groups().getGroupMembersAsync(id())
                .flatMapIterable(pageOfAADObjectInner -> pageOfAADObjectInner.items())
                .map(aadObjectInner -> {
                    SerializerAdapter<?> adapter = manager().inner().serializerAdapter();
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
                });
        //
        return adObjObservable;
    }

    @Override
    public GraphRbacManager manager() {
        return manager;
    }

    @Override
    protected Maybe<ADGroupInner> getInnerAsync() {
        return manager().inner().groups().getAsync(id());
    }

    @Override
    public boolean isInCreateMode() {
        return id() == null;
    }

    @Override
    public Observable<ActiveDirectoryGroup> createResourceAsync() {
        Maybe<ActiveDirectoryGroup> group = Maybe.just(this);
        //
        if (isInCreateMode()) {
            group = manager().inner().groups().createAsync(createParameters)
                    .map(innerToFluentMap(this));
        }
        if (!membersToRemove.isEmpty()) {
            group = group.concatMap(g -> Observable.fromIterable(membersToRemove)
                    .flatMapCompletable(s -> manager().inner().groups().removeMemberAsync(id(), s))
                    .doOnComplete(membersToRemove::clear)
                    .<ActiveDirectoryGroup>toMaybe());
        }
        if (!membersToAdd.isEmpty()) {
            group = group.concatMap(g -> Observable.fromIterable(membersToAdd)
                    .flatMapCompletable(s -> manager().inner().groups().addMemberAsync(id(), new GroupAddMemberParameters().withUrl(s)))
                    .doOnComplete(membersToAdd::clear)
                    .<ActiveDirectoryGroup>toMaybe());
        }
        return group.toObservable().concatMap(g -> Observable.<ActiveDirectoryGroup>just(this));
    }

    @Override
    public ActiveDirectoryGroupImpl withEmailAlias(String mailNickname) {
        // User providing domain
        if (mailNickname.contains("@")) {
            String[] parts = mailNickname.split("@");
            domainName = parts[1];
            mailNickname = parts[0];
        }
        createParameters.withMailNickname(mailNickname);
        return this;
    }

    @Override
    public ActiveDirectoryGroupImpl withMember(String objectId) {
        membersToAdd.add(String.format("%s/%s/directoryObjects/%s",
                manager().inner().azureEnvironment().graphEndpoint(), manager().tenantId(), objectId));
        return this;
    }

    @Override
    public ActiveDirectoryGroupImpl withMember(ActiveDirectoryUser user) {
        return withMember(user.id());
    }

    @Override
    public ActiveDirectoryGroupImpl withMember(ActiveDirectoryGroup group) {
        return withMember(group.id());
    }

    @Override
    public ActiveDirectoryGroupImpl withMember(ServicePrincipal servicePrincipal) {
        return withMember(servicePrincipal.id());
    }

    @Override
    public ActiveDirectoryGroupImpl withoutMember(String objectId) {
        membersToRemove.add(objectId);
        return this;
    }

    @Override
    public ActiveDirectoryGroupImpl withoutMember(ActiveDirectoryUser user) {
        return withoutMember(user.id());
    }

    @Override
    public ActiveDirectoryGroupImpl withoutMember(ActiveDirectoryGroup group) {
        return withoutMember(group.id());
    }

    @Override
    public ActiveDirectoryGroupImpl withoutMember(ServicePrincipal servicePrincipal) {
        return withoutMember(servicePrincipal.id());
    }
}
