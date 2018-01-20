/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.implementation;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.v2.management.resources.Provider;
import com.microsoft.azure.v2.management.resources.Providers;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * The implementation for {@link Providers}.
 */
final class ProvidersImpl
        extends ReadableWrappersImpl<Provider, ProviderImpl, ProviderInner>
        implements Providers {
    private final ProvidersInner client;

    ProvidersImpl(final ProvidersInner client) {
        this.client = client;
    }

    @Override
    public PagedList<Provider> list() {
        return wrapList(client.list());
    }

    @Override
    public Provider unregister(String resourceProviderNamespace) {
        return this.unregisterAsync(resourceProviderNamespace).blockingGet();
    }

    @Override
    public Maybe<Provider> unregisterAsync(String resourceProviderNamespace) {
        return client.unregisterAsync(resourceProviderNamespace).map(new Function<ProviderInner, Provider>() {
            @Override
            public Provider apply(ProviderInner providerInner) {
                return wrapModel(providerInner);
            }
        });
    }

    @Override
    public ServiceFuture<Provider> unregisterAsync(String resourceProviderNamespace, ServiceCallback<Provider> callback) {
        return ServiceFuture.fromBody(this.unregisterAsync(resourceProviderNamespace), callback);
    }

    @Override
    public Provider register(String resourceProviderNamespace) {
        return this.registerAsync(resourceProviderNamespace).blockingGet();
    }

    @Override
    public Maybe<Provider> registerAsync(String resourceProviderNamespace) {
        return client.registerAsync(resourceProviderNamespace).map(new Function<ProviderInner, Provider>() {
            @Override
            public Provider apply(ProviderInner providerInner) {
                return wrapModel(providerInner);
            }
        });
    }

    @Override
    public ServiceFuture<Provider> registerAsync(String resourceProviderNamespace, ServiceCallback<Provider> callback) {
        return ServiceFuture.fromBody(this.registerAsync(resourceProviderNamespace), callback);
    }

    @Override
    public Maybe<Provider> getByNameAsync(String name) {
        return client.getAsync(name)
                .map(new Function<ProviderInner, Provider>() {
                    @Override
                    public Provider apply(ProviderInner providerInner) {
                        return wrapModel(providerInner);
                    }
                });
    }

    @Override
    public Provider getByName(String resourceProviderNamespace) {
        return wrapModel(client.get(resourceProviderNamespace));
    }

    @Override
    protected ProviderImpl wrapModel(ProviderInner inner) {
        if (inner == null) {
            return null;
        }
        return new ProviderImpl(inner);
    }

    @Override
    public Observable<Provider> listAsync() {
        return wrapPageAsync(this.client.listAsync());
    }
}
