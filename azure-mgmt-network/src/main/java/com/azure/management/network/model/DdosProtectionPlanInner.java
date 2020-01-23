// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.model;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.SubResource;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * The DdosProtectionPlan model.
 */
@JsonFlatten
@Fluent
public class DdosProtectionPlanInner {
    /*
     * Resource ID.
     */
    @JsonProperty(value = "id", access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    /*
     * Resource name.
     */
    @JsonProperty(value = "name", access = JsonProperty.Access.WRITE_ONLY)
    private String name;

    /*
     * Resource type.
     */
    @JsonProperty(value = "type", access = JsonProperty.Access.WRITE_ONLY)
    private String type;

    /*
     * Resource location.
     */
    @JsonProperty(value = "location")
    private String location;

    /*
     * Resource tags.
     */
    @JsonProperty(value = "tags")
    private Map<String, String> tags;

    /*
     * A unique read-only string that changes whenever the resource is updated.
     */
    @JsonProperty(value = "etag", access = JsonProperty.Access.WRITE_ONLY)
    private String etag;

    /*
     * The resource GUID property of the DDoS protection plan resource. It
     * uniquely identifies the resource, even if the user changes its name or
     * migrate the resource across subscriptions or resource groups.
     */
    @JsonProperty(value = "properties.resourceGuid", access = JsonProperty.Access.WRITE_ONLY)
    private String resourceGuid;

    /*
     * The provisioning state of the DDoS protection plan resource. Possible
     * values are: 'Succeeded', 'Updating', 'Deleting', and 'Failed'.
     */
    @JsonProperty(value = "properties.provisioningState", access = JsonProperty.Access.WRITE_ONLY)
    private String provisioningState;

    /*
     * The list of virtual networks associated with the DDoS protection plan
     * resource. This list is read-only.
     */
    @JsonProperty(value = "properties.virtualNetworks", access = JsonProperty.Access.WRITE_ONLY)
    private List<SubResource> virtualNetworks;

    /**
     * Get the id property: Resource ID.
     * 
     * @return the id value.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Get the name property: Resource name.
     * 
     * @return the name value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the type property: Resource type.
     * 
     * @return the type value.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Get the location property: Resource location.
     * 
     * @return the location value.
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Set the location property: Resource location.
     * 
     * @param location the location value to set.
     * @return the DdosProtectionPlanInner object itself.
     */
    public DdosProtectionPlanInner setLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * Get the tags property: Resource tags.
     * 
     * @return the tags value.
     */
    public Map<String, String> getTags() {
        return this.tags;
    }

    /**
     * Set the tags property: Resource tags.
     * 
     * @param tags the tags value to set.
     * @return the DdosProtectionPlanInner object itself.
     */
    public DdosProtectionPlanInner setTags(Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

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
     * Get the resourceGuid property: The resource GUID property of the DDoS
     * protection plan resource. It uniquely identifies the resource, even if
     * the user changes its name or migrate the resource across subscriptions
     * or resource groups.
     * 
     * @return the resourceGuid value.
     */
    public String getResourceGuid() {
        return this.resourceGuid;
    }

    /**
     * Get the provisioningState property: The provisioning state of the DDoS
     * protection plan resource. Possible values are: 'Succeeded', 'Updating',
     * 'Deleting', and 'Failed'.
     * 
     * @return the provisioningState value.
     */
    public String getProvisioningState() {
        return this.provisioningState;
    }

    /**
     * Get the virtualNetworks property: The list of virtual networks
     * associated with the DDoS protection plan resource. This list is
     * read-only.
     * 
     * @return the virtualNetworks value.
     */
    public List<SubResource> getVirtualNetworks() {
        return this.virtualNetworks;
    }
}
