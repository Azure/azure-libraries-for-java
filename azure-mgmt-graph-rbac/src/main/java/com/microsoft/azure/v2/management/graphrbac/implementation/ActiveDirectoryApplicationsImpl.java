/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.graphrbac.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.v2.management.graphrbac.ActiveDirectoryApplication;
import com.microsoft.azure.v2.management.graphrbac.ActiveDirectoryApplications;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.CreatableResourcesImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.PagedListConverter;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

import java.util.UUID;

/**
 * The implementation of Applications and its parent interfaces.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Graph.RBAC.Fluent")
class ActiveDirectoryApplicationsImpl
        extends CreatableResourcesImpl<
                    ActiveDirectoryApplication,
                    ActiveDirectoryApplicationImpl,
                    ApplicationInner>
        implements
        ActiveDirectoryApplications,
        HasManager<GraphRbacManager>,
        HasInner<ApplicationsInner> {
    private final PagedListConverter<ApplicationInner, ActiveDirectoryApplication> converter;
    private ApplicationsInner innerCollection;
    private GraphRbacManager manager;

    ActiveDirectoryApplicationsImpl(
            final ApplicationsInner client,
            final GraphRbacManager graphRbacManager) {
        this.innerCollection = client;
        this.manager = graphRbacManager;
        converter = new PagedListConverter<ApplicationInner, ActiveDirectoryApplication>() {
            @Override
            public Observable<ActiveDirectoryApplication> typeConvertAsync(ApplicationInner applicationsInner) {
                ActiveDirectoryApplicationImpl impl = wrapModel(applicationsInner);
                return impl.refreshCredentialsAsync().toObservable();
            }
        };

    }

    @Override
    public PagedList<ActiveDirectoryApplication> list() {
        return wrapList(this.innerCollection.list());
    }

    @Override
    protected PagedList<ActiveDirectoryApplication> wrapList(PagedList<ApplicationInner> pagedList) {
        return converter.convert(pagedList);
    }

    @Override
    public Observable<ActiveDirectoryApplication> listAsync() {
        return wrapPageAsync(this.inner().listAsync())
                .flatMap(application -> ((ActiveDirectoryApplicationImpl) application).refreshCredentialsAsync().toObservable());
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
        return (ActiveDirectoryApplicationImpl) getByIdAsync(id).blockingGet();
    }

    @Override
    public Maybe<ActiveDirectoryApplication> getByIdAsync(String id) {
        return innerCollection.getAsync(id)
                .flatMap(applicationInner ->  {
                    if (applicationInner == null) {
                        return Maybe.empty();
                    } else {
                        return new ActiveDirectoryApplicationImpl(applicationInner, manager()).refreshCredentialsAsync();
                    }
                });
    }

    @Override
    public ServiceFuture<ActiveDirectoryApplication> getByIdAsync(String id, ServiceCallback<ActiveDirectoryApplication> callback) {
        return ServiceFuture.fromBody(getByIdAsync(id), callback);
    }

    @Override
    public ActiveDirectoryApplication getByName(String spn) {
        return getByNameAsync(spn).blockingLast();
    }

    @Override
    public Observable<ActiveDirectoryApplication> getByNameAsync(String name) {
        final String trimmed = name.replaceFirst("^'+", "").replaceAll("'+$", "");
        return innerCollection.listAsync(String.format("displayName eq '%s'", trimmed))
                .flatMap(pageOfApplicationInner -> {
                    if (pageOfApplicationInner == null || pageOfApplicationInner.items() == null || pageOfApplicationInner.items().isEmpty()) {
                        try {
                            UUID.fromString(trimmed);
                            return innerCollection.listAsync(String.format("appId eq '%s'", trimmed));
                        } catch (IllegalArgumentException e) {
                            return null;
                        }
                    } else {
                        return Observable.just(pageOfApplicationInner);
                    }
                }).map(pageOfApplicationInner -> {
                    if (pageOfApplicationInner == null || pageOfApplicationInner.items() == null || pageOfApplicationInner.items().isEmpty()) {
                        return null;
                    } else {
                        return new ActiveDirectoryApplicationImpl(pageOfApplicationInner.items().get(0), manager());
                    }
                }).flatMap(application -> {
                    if (application == null) {
                        return null;
                    } else {
                        return application.refreshCredentialsAsync().toObservable();
                    }
                });
    }

    @Override
    public GraphRbacManager manager() {
        return this.manager;
    }

    @Override
    public ApplicationsInner inner() {
        return this.innerCollection;
    }

    @Override
    protected ActiveDirectoryApplicationImpl wrapModel(String name) {
        return new ActiveDirectoryApplicationImpl((ApplicationInner) new ApplicationInner().withDisplayName(name), manager());
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        return inner().deleteAsync(id);
    }

    @Override
    public ActiveDirectoryApplicationImpl define(String name) {
        return wrapModel(name);
    }
}
