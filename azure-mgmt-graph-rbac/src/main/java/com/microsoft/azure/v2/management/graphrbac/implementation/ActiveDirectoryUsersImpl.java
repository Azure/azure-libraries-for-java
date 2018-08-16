/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.graphrbac.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.v2.management.graphrbac.ActiveDirectoryUser;
import com.microsoft.azure.v2.management.graphrbac.ActiveDirectoryUsers;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.CreatableWrappersImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * The implementation of Users and its parent interfaces.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Graph.RBAC.Fluent")
class ActiveDirectoryUsersImpl
        extends CreatableWrappersImpl<
                ActiveDirectoryUser,
                    ActiveDirectoryUserImpl,
                    UserInner>
        implements
            ActiveDirectoryUsers,
        HasInner<UsersInner> {
    private final GraphRbacManager manager;

    ActiveDirectoryUsersImpl(
            final GraphRbacManager manager) {
        this.manager = manager;
    }

    @Override
    public PagedList<ActiveDirectoryUser> list() {
        return wrapList(this.manager().inner().users().list());
    }

    @Override
    protected ActiveDirectoryUserImpl wrapModel(UserInner userInner) {
        if (userInner == null) {
            return null;
        }
        return new ActiveDirectoryUserImpl(userInner, manager());
    }

    @Override
    public ActiveDirectoryUserImpl getById(String objectId) {
        return (ActiveDirectoryUserImpl) getByIdAsync(objectId).blockingGet();
    }

    @Override
    public Maybe<ActiveDirectoryUser> getByIdAsync(String id) {
        return manager().inner().users().getAsync(id)
                .map(userInner -> {
                    if (userInner == null) {
                        return null;
                    } else {
                        return new ActiveDirectoryUserImpl(userInner, manager());
                    }
                });
    }

    @Override
    public ServiceFuture<ActiveDirectoryUser> getByIdAsync(String id, ServiceCallback<ActiveDirectoryUser> callback) {
        return ServiceFuture.fromBody(getByIdAsync(id), callback);
    }

    @Override
    public ActiveDirectoryUserImpl getByName(String upn) {
        return (ActiveDirectoryUserImpl) getByNameAsync(upn).lastElement().blockingGet();
    }

    @Override
    public Observable<ActiveDirectoryUser> getByNameAsync(final String name) {
        return manager().inner().users().getAsync(name)
                .flatMap(userInner -> {
                   if (userInner != null) {
                       return Maybe.just(userInner);
                   }
                   // Search mail & mail nickname
                   if (name.contains("@")) {
                       return manager().inner().users().listAsync(String.format("mail eq '%s' or mailNickName eq '%s#EXT#'", name, name.replace("@", "_")))
                               .map(pageOfUserInner -> {
                                   if (pageOfUserInner.items() == null || pageOfUserInner.items().isEmpty()) {
                                       return null;
                                   }
                                   return pageOfUserInner.items().get(0);
                               }).lastElement();
                   }
                   // Search display name
                   else {
                       return manager().inner().users().listAsync(String.format("displayName eq '%s'", name))
                               .map(pageOfUserInner -> {
                                   if (pageOfUserInner.items() == null || pageOfUserInner.items().isEmpty()) {
                                       return null;
                                   }
                                   return pageOfUserInner.items().get(0);
                               }).lastElement();

                   }
                })
                .map((io.reactivex.functions.Function<UserInner, ActiveDirectoryUser>)userInner -> {
                    if (userInner == null) {
                        return null;
                    }
                    return new ActiveDirectoryUserImpl(userInner, manager());
                }).toObservable();
    }

    @Override
    public UsersInner inner() {
        return this.manager().inner().users();
    }

    @Override
    public Observable<ActiveDirectoryUser> listAsync() {
        return wrapPageAsync(this.inner().listAsync());
    }

    @Override
    public GraphRbacManager manager() {
        return manager;
    }

    @Override
    public ActiveDirectoryUserImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    protected ActiveDirectoryUserImpl wrapModel(String name) {
        return new ActiveDirectoryUserImpl((UserInner) new UserInner().withDisplayName(name), manager());
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        return manager().inner().users().deleteAsync(id);
    }
}
