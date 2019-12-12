/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.implementation;

import com.microsoft.azure.PagedList;
import com.azure.management.resources.Tenant;
import com.azure.management.resources.Tenants;
import com.azure.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import com.azure.management.resources.fluentcore.utils.PagedListConverter;
import rx.Observable;
import rx.functions.Func1;

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
            public Observable<Tenant> typeConvertAsync(TenantIdDescriptionInner tenantInner) {
                return Observable.just((Tenant) new TenantImpl(tenantInner));
            }
        };
        return converter.convert(client.list());
    }

    @Override
    public Observable<Tenant> listAsync() {
        return ReadableWrappersImpl.convertPageToInnerAsync(client.listAsync()).map(new Func1<TenantIdDescriptionInner, Tenant>() {
            @Override
            public Tenant call(TenantIdDescriptionInner tenantIdDescriptionInners) {
                return new TenantImpl(tenantIdDescriptionInners);
            }
        });
    }
}
