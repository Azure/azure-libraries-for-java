/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.implementation;

import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;
import com.microsoft.azure.v2.management.resources.Tenant;

/**
 * Implementation for {@link Tenant}.
 */
final class TenantImpl extends
        IndexableWrapperImpl<TenantIdDescriptionInner>
        implements
        Tenant {

    TenantImpl(TenantIdDescriptionInner innerModel) {
        super(innerModel);
    }

    @Override
    public String tenantId() {
        return inner().tenantId();
    }
}
