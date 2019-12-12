/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources;

import com.azure.management.resources.implementation.TenantIdDescriptionInner;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.azure.management.resources.fluentcore.model.Indexable;
import com.azure.management.resources.fluentcore.model.HasInner;
import com.azure.management.resources.implementation.TenantIdDescriptionInner;

/**
 * An immutable client-side representation of an Azure tenant.
 */
@Fluent
public interface Tenant extends
        Indexable,
        HasInner<TenantIdDescriptionInner> {

    /**
     * @return a UUID of the tenant
     */
    String tenantId();
}
