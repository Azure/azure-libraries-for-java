/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.graphrbac.implementation;

import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.PagedResponse;
import com.azure.management.graphrbac.ActiveDirectoryApplication;
import com.azure.management.graphrbac.ActiveDirectoryApplications;
import com.azure.management.graphrbac.models.ApplicationInner;
import com.azure.management.graphrbac.models.ApplicationsInner;
import com.azure.management.resources.fluentcore.arm.collection.implementation.CreatableResourcesImpl;
import com.azure.management.resources.fluentcore.arm.models.HasManager;
import com.azure.management.resources.fluentcore.model.HasInner;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Function;

/**
 * The implementation of Applications and its parent interfaces.
 */

class ActiveDirectoryApplicationsImpl
        extends CreatableResourcesImpl<
                ActiveDirectoryApplication,
                    ActiveDirectoryApplicationImpl,
                ApplicationInner>
        implements
        ActiveDirectoryApplications,
        HasManager<GraphRbacManager>,
        HasInner<ApplicationsInner> {
    private ApplicationsInner innerCollection;
    private GraphRbacManager manager;

    ActiveDirectoryApplicationsImpl(
            final ApplicationsInner client,
            final GraphRbacManager graphRbacManager) {
        this.innerCollection = client;
        this.manager = graphRbacManager;
    }

    @Override
    public PagedIterable<ActiveDirectoryApplication> list() {
        return this.innerCollection.list(null).mapPage(inner -> {
            ActiveDirectoryApplicationImpl application = wrapModel(inner);
            application.refreshCredentialsAsync();
            return application;
        });
    }

    @Override
    public PagedFlux<ActiveDirectoryApplication> listAsync() {
        return this.innerCollection.listAsync(null).mapPage(inner -> {
            ActiveDirectoryApplicationImpl application = wrapModel(inner);
            application.refreshCredentialsAsync();
            return application;
        });
    }

    @Override
    protected ActiveDirectoryApplicationImpl wrapModel(ApplicationInner applicationInner) {
        if (applicationInner == null) {
            return null;
        }
        return new ActiveDirectoryApplicationImpl(applicationInner, getManager());
    }

    @Override
    public ActiveDirectoryApplicationImpl getById(String id) {
        return (ActiveDirectoryApplicationImpl) getByIdAsync(id).block();
    }

    @Override
    public Mono<ActiveDirectoryApplication> getByIdAsync(String id) {
        return innerCollection.getAsync(id)
                .flatMap((Function<ApplicationInner, Mono<ActiveDirectoryApplication>>) applicationInner -> {
                    if (applicationInner == null) {
                        return Mono.just(null);
                    } else {
                        return new ActiveDirectoryApplicationImpl(applicationInner, getManager())
                                .refreshCredentialsAsync();
                    }
                });
    }

    @Override
    public ActiveDirectoryApplication getByName(String spn) {
        return getByNameAsync(spn).block();
    }

    @Override
    public Mono<ActiveDirectoryApplication> getByNameAsync(String name) {
        final String trimmed = name.replaceFirst("^'+", "").replaceAll("'+$", "");
        return innerCollection.listSinglePageAsync(String.format("displayName eq '%s'", trimmed))
                .flatMap((Function<PagedResponse<ApplicationInner>, Mono<PagedFlux<ApplicationInner>>>) result -> {
                    if (result == null || result.getItems() == null || result.getItems().isEmpty()) {
                        try {
                            UUID.fromString(trimmed);
                            return Mono.just(innerCollection.listAsync(String.format("appId eq '%s'", trimmed)));
                        } catch (IllegalArgumentException e) {
                            return null;
                        }
                    }
                    return Mono.just(new PagedFlux<>(
                            () -> Mono.just(result),
                            nextLink -> innerCollection.listNextSinglePageAsync(nextLink)
                    ));
                })
                .map(result -> {
                    if (result == null || result.toIterable() == null || !result.toIterable().iterator().hasNext()) {
                        return null;
                    }
                    return new ActiveDirectoryApplicationImpl(result.toIterable().iterator().next(), getManager());
                }).flatMap((Function<ActiveDirectoryApplicationImpl, Mono<ActiveDirectoryApplication>>) application -> {
                    if (application == null) {
                        return null;
                    }
                    return application.refreshCredentialsAsync();
                });
    }

    @Override
    protected ActiveDirectoryApplicationImpl wrapModel(String name) {
        return new ActiveDirectoryApplicationImpl(new ApplicationInner().setDisplayName(name), getManager());
    }

    @Override
    public Mono<Void> deleteByIdAsync(String id) {
        return getInner().deleteAsync(id);
    }

    @Override
    public ActiveDirectoryApplicationImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    public GraphRbacManager getManager() {
        return this.manager;
    }

    @Override
    public ApplicationsInner getInner() {
        return this.innerCollection;
    }
}
