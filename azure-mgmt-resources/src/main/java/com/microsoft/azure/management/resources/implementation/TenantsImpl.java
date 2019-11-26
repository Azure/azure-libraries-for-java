/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.implementation;

import com.azure.core.http.rest.PagedFlux;
import com.azure.core.management.PagedList;
import com.microsoft.azure.management.resources.Tenant;
import com.microsoft.azure.management.resources.Tenants;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation for {@link Tenants}.
 */
final class TenantsImpl
        implements Tenants {
    private final TenantsInner client;

    TenantsImpl(final TenantsInner client) {
        this.client = client;
    }

    @Override
    public PagedList<Tenant> list() {
        PagedListConverter<TenantIdDescriptionInner, Tenant> converter = new PagedListConverter<TenantIdDescriptionInner, Tenant>() {
            @Override
            public Mono<Tenant> typeConvertAsync(TenantIdDescriptionInner tenantInner) {
                return Mono.just((Tenant) new TenantImpl(tenantInner));
            }
        };
        return converter.convert(client.list());
    }

    @Override
    public PagedFlux<Tenant> listAsync() {
        return client.listAsync().mapPage(inner -> new TenantImpl(inner));
//        return ReadableWrappersImpl.convertPageToInnerAsync(client.listAsync()).map(new Func1<TenantIdDescriptionInner, Tenant>() {
//            @Override
//            public Tenant call(TenantIdDescriptionInner tenantIdDescriptionInners) {
//                return new TenantImpl(tenantIdDescriptionInners);
//            }
//        });
    }
}
