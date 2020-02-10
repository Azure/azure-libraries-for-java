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
        extends CreatableResourcesImpl<ActiveDirectoryApplication, ActiveDirectoryApplicationImpl, ApplicationInner>
        implements ActiveDirectoryApplications,
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
            return application.refreshCredentialsAsync().block();
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
        return new ActiveDirectoryApplicationImpl(applicationInner, manager());
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
                        return new ActiveDirectoryApplicationImpl(applicationInner, manager())
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
                .flatMap(response -> {
                    if (response == null || response.getItems() == null || response.getItems().isEmpty()) {
                        try {
                            UUID.fromString(trimmed);
                            return Mono.just(innerCollection.listAsync(String.format("appId eq '%s'", trimmed)));
                        } catch (IllegalArgumentException e) {
                            return null;
                        }
                    }
                    return Mono.just(new PagedFlux<>(
                            () -> Mono.just(response),
                            nextLink -> innerCollection.listNextSinglePageAsync(nextLink)
                    ));
                })
                .map(result -> result.blockFirst())
                .map(applicationInner -> new ActiveDirectoryApplicationImpl(applicationInner, manager()))
                .flatMap(activeDirectoryApplication -> activeDirectoryApplication.refreshCredentialsAsync())
                .switchIfEmpty(Mono.defer(() -> Mono.empty()));
    }

    @Override
    protected ActiveDirectoryApplicationImpl wrapModel(String name) {
        return new ActiveDirectoryApplicationImpl(new ApplicationInner().setDisplayName(name), manager());
    }

    @Override
    public Mono<Void> deleteByIdAsync(String id) {
        return inner().deleteAsync(id);
    }

    @Override
    public ActiveDirectoryApplicationImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    public GraphRbacManager manager() {
        return this.manager;
    }

    @Override
    public ApplicationsInner inner() {
        return this.innerCollection;
    }
}
