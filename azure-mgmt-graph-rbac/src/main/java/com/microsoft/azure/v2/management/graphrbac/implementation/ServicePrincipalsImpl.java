/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.graphrbac.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.Page;
import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.v2.management.graphrbac.ServicePrincipal;
import com.microsoft.azure.v2.management.graphrbac.ServicePrincipals;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.CreatableWrappersImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.PagedListConverter;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * The implementation of ServicePrincipals and its parent interfaces.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Graph.RBAC.Fluent")
class ServicePrincipalsImpl
        extends CreatableWrappersImpl<
                    ServicePrincipal,
                    ServicePrincipalImpl,
                    ServicePrincipalInner>
        implements
        ServicePrincipals,
        HasManager<GraphRbacManager>,
        HasInner<ServicePrincipalsInner> {
    private final PagedListConverter<ServicePrincipalInner, ServicePrincipal> converter;
    private ServicePrincipalsInner innerCollection;
    private GraphRbacManager manager;

    ServicePrincipalsImpl(
            final ServicePrincipalsInner client,
            final GraphRbacManager graphRbacManager) {
        this.innerCollection = client;
        this.manager = graphRbacManager;
        converter = new PagedListConverter<ServicePrincipalInner, ServicePrincipal>() {
            @Override
            public Observable<ServicePrincipal> typeConvertAsync(ServicePrincipalInner servicePrincipalInner) {
                ServicePrincipalImpl impl = wrapModel(servicePrincipalInner);
                return impl.refreshCredentialsAsync().toObservable();
            }
        };
    }

    @Override
    public PagedList<ServicePrincipal> list() {
        return converter.convert(this.inner().list());
    }

    @Override
    public Observable<ServicePrincipal> listAsync() {
        return wrapPageAsync(this.inner().listAsync())
                .flatMap(servicePrincipal -> ((ServicePrincipalImpl) servicePrincipal).refreshCredentialsAsync().toObservable());
    }

    @Override
    protected ServicePrincipalImpl wrapModel(ServicePrincipalInner servicePrincipalInner) {
        if (servicePrincipalInner == null) {
            return null;
        }
        return new ServicePrincipalImpl(servicePrincipalInner, manager());
    }

    @Override
    public ServicePrincipalImpl getById(String id) {
        return (ServicePrincipalImpl) getByIdAsync(id).blockingGet();
    }

    @Override
    public Maybe<ServicePrincipal> getByIdAsync(String id) {
        return innerCollection.getAsync(id)
                .flatMap(servicePrincipalInner -> {
                    if (servicePrincipalInner == null) {
                        return Maybe.empty();
                    } else {
                        return new ServicePrincipalImpl(servicePrincipalInner, manager()).refreshCredentialsAsync().toMaybe();
                    }
                });
    }

    @Override
    public ServiceFuture<ServicePrincipal> getByIdAsync(String id, ServiceCallback<ServicePrincipal> callback) {
        return ServiceFuture.fromBody(getByIdAsync(id), callback);
    }

    @Override
    public ServicePrincipal getByName(String spn) {
        return getByNameAsync(spn).lastElement().blockingGet();
    }

    @Override
    public Observable<ServicePrincipal> getByNameAsync(final String name) {
        return innerCollection.listAsync(String.format("servicePrincipalNames/any(c:c eq '%s')", name))
                .flatMap((Function<Page<ServicePrincipalInner>, Observable<Page<ServicePrincipalInner>>>) servicePrincipalInnerPage -> {
                    if (servicePrincipalInnerPage == null || servicePrincipalInnerPage.items() == null || servicePrincipalInnerPage.items().isEmpty()) {
                        return innerCollection.listAsync(String.format("displayName eq '%s'", name));
                    }
                    return Observable.just(servicePrincipalInnerPage);
                }).map(servicePrincipalInnerPage -> {
                    if (servicePrincipalInnerPage == null || servicePrincipalInnerPage.items() == null || servicePrincipalInnerPage.items().isEmpty()) {
                        return null;
                    }
                    return new ServicePrincipalImpl(servicePrincipalInnerPage.items().get(0), manager());
                }).flatMap((Function<ServicePrincipalImpl, Observable<ServicePrincipal>>) servicePrincipalImpl -> {
                    if (servicePrincipalImpl == null) {
                        return null;
                    }
                    return servicePrincipalImpl.refreshCredentialsAsync().toObservable();
                });
    }

    @Override
    public GraphRbacManager manager() {
        return this.manager;
    }

    @Override
    public ServicePrincipalsInner inner() {
        return this.innerCollection;
    }

    @Override
    public ServicePrincipalImpl define(String name) {
        return new ServicePrincipalImpl((ServicePrincipalInner) new ServicePrincipalInner().withDisplayName(name), manager());
    }

    @Override
    protected ServicePrincipalImpl wrapModel(String name) {
        return new ServicePrincipalImpl((ServicePrincipalInner) new ServicePrincipalInner().withDisplayName(name), manager());
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        return manager().inner().servicePrincipals().deleteAsync(id);
    }
}
