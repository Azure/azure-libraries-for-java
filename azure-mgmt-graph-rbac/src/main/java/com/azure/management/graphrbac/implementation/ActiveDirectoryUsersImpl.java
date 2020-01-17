/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.graphrbac.implementation;

import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedIterable;
import com.azure.management.graphrbac.ActiveDirectoryUser;
import com.azure.management.graphrbac.ActiveDirectoryUsers;
import com.azure.management.graphrbac.models.UserInner;
import com.azure.management.graphrbac.models.UsersInner;
import com.azure.management.resources.fluentcore.arm.collection.implementation.CreatableWrappersImpl;
import com.azure.management.resources.fluentcore.model.HasInner;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * The implementation of Users and its parent interfaces.
 */
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
    public PagedIterable<ActiveDirectoryUser> list() {
        return wrapList(this.getManager().inner().users().list(null));
    }

    @Override
    protected ActiveDirectoryUserImpl wrapModel(UserInner userInner) {
        if (userInner == null) {
            return null;
        }
        return new ActiveDirectoryUserImpl(userInner, getManager());
    }

    @Override
    public ActiveDirectoryUserImpl getById(String objectId) {
        return (ActiveDirectoryUserImpl) getByIdAsync(objectId).block();
    }

    @Override
    public Mono<ActiveDirectoryUser> getByIdAsync(String id) {
        return getManager().inner().users().getAsync(id).map(userInner -> {
            if (userInner == null) {
                return null;
            } else {
                return new ActiveDirectoryUserImpl(userInner, getManager());
            }
        });
    }

    @Override
    public ActiveDirectoryUserImpl getByName(String upn) {
        return (ActiveDirectoryUserImpl) getByNameAsync(upn).block();
    }

    @Override
    public Mono<ActiveDirectoryUser> getByNameAsync(final String name) {
        return getManager().inner().users().getAsync(name)
                .flatMap((Function<UserInner, Mono<UserInner>>) userInner -> {
                    // Exact match
                    if (userInner != null) {
                        return Mono.just(userInner);
                    }
                    // Search mail & mail nickname
                    if (name.contains("@")) {
                        return getManager().inner().users().listAsync(String.format("mail eq '%s' or mailNickName eq '%s#EXT#'", name, name.replace("@", "_")))
                                .last();
                    }
                    // Search display name
                    else {
                        return getManager().inner().users().listAsync(String.format("displayName eq '%s'", name))
                                .last();
                    }
                })
                .map(userInnerServiceResponse -> {
                    if (userInnerServiceResponse == null) {
                        return null;
                    }
                    return new ActiveDirectoryUserImpl(userInnerServiceResponse, getManager());
                });
    }

    @Override
    public PagedFlux<ActiveDirectoryUser> listAsync() {
        return wrapPageAsync(this.getInner().listAsync(null));
    }

    @Override
    public ActiveDirectoryUserImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    protected ActiveDirectoryUserImpl wrapModel(String name) {
        return new ActiveDirectoryUserImpl((UserInner) new UserInner().setDisplayName(name), getManager());
    }

    @Override
    public Mono<Void> deleteByIdAsync(String id) {
        return getManager().inner().users().deleteAsync(id);
    }

    @Override
    public GraphRbacManager getManager() {
        return this.manager;
    }

    @Override
    public UsersInner getInner() {
        return getManager().getInner().users();
    }
}
