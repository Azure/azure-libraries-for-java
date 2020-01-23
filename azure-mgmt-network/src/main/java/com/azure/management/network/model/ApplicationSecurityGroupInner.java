// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.model;

import com.azure.core.annotation.Immutable;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.Resource;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The ApplicationSecurityGroup model.
 */
@JsonFlatten
@Immutable
public class ApplicationSecurityGroupInner extends Resource {
    /*
     * A unique read-only string that changes whenever the resource is updated.
     */
    @JsonProperty(value = "etag", access = JsonProperty.Access.WRITE_ONLY)
    private String etag;

    /*
     * The resource GUID property of the application security group resource.
     * It uniquely identifies a resource, even if the user changes its name or
     * migrate the resource across subscriptions or resource groups.
     */
    @JsonProperty(value = "properties.resourceGuid", access = JsonProperty.Access.WRITE_ONLY)
    private String resourceGuid;

    /*
     * The provisioning state of the application security group resource.
     * Possible values are: 'Succeeded', 'Updating', 'Deleting', and 'Failed'.
     */
    @JsonProperty(value = "properties.provisioningState", access = JsonProperty.Access.WRITE_ONLY)
    private String provisioningState;

    /**
     * Get the etag property: A unique read-only string that changes whenever
     * the resource is updated.
     * 
     * @return the etag value.
     */
    public String getEtag() {
        return this.etag;
    }

    /**
     * Get the resourceGuid property: The resource GUID property of the
     * application security group resource. It uniquely identifies a resource,
     * even if the user changes its name or migrate the resource across
     * subscriptions or resource groups.
     * 
     * @return the resourceGuid value.
     */
    public String getResourceGuid() {
        return this.resourceGuid;
    }

    /**
     * Get the provisioningState property: The provisioning state of the
     * application security group resource. Possible values are: 'Succeeded',
     * 'Updating', 'Deleting', and 'Failed'.
     * 
     * @return the provisioningState value.
     */
    public String getProvisioningState() {
        return this.provisioningState;
    }
}
