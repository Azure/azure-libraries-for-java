/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.resources.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Tenant Id information.
 */
public final class TenantIdDescriptionInner {
    /**
     * The fully qualified ID of the tenant. For example,
     * /tenants/00000000-0000-0000-0000-000000000000.
     */
    @JsonProperty(value = "id", access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    /**
     * The tenant ID. For example, 00000000-0000-0000-0000-000000000000.
     */
    @JsonProperty(value = "tenantId", access = JsonProperty.Access.WRITE_ONLY)
    private String tenantId;

    /**
     * Get the id value.
     *
     * @return the id value.
     */
    public String id() {
        return this.id;
    }

    /**
     * Get the tenantId value.
     *
     * @return the tenantId value.
     */
    public String tenantId() {
        return this.tenantId;
    }
}
