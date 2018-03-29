/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.managementgroups.implementation;

import com.microsoft.azure.management.managementgroups.ManagementGroupDetails;
import java.util.List;
import com.microsoft.azure.management.managementgroups.ManagementGroupChildInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * The management group details.
 */
@JsonFlatten
public class ManagementGroupInner {
    /**
     * The fully qualified ID for the management group.  For example,
     * /providers/Microsoft.Management/managementGroups/0000000-0000-0000-0000-000000000000.
     */
    @JsonProperty(value = "id", access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    /**
     * The type of the resource.  For example,
     * /providers/Microsoft.Management/managementGroups.
     */
    @JsonProperty(value = "type", access = JsonProperty.Access.WRITE_ONLY)
    private String type;

    /**
     * The name of the management group. For example,
     * 00000000-0000-0000-0000-000000000000.
     */
    @JsonProperty(value = "name", access = JsonProperty.Access.WRITE_ONLY)
    private String name;

    /**
     * The AAD Tenant ID associated with the management group. For example,
     * 00000000-0000-0000-0000-000000000000.
     */
    @JsonProperty(value = "properties.tenantId")
    private String tenantId;

    /**
     * The friendly name of the management group.
     */
    @JsonProperty(value = "properties.displayName")
    private String displayName;

    /**
     * Details.
     */
    @JsonProperty(value = "properties.details")
    private ManagementGroupDetails details;

    /**
     * The list of children.
     */
    @JsonProperty(value = "properties.children")
    private List<ManagementGroupChildInfo> children;

    /**
     * Get the id value.
     *
     * @return the id value
     */
    public String id() {
        return this.id;
    }

    /**
     * Get the type value.
     *
     * @return the type value
     */
    public String type() {
        return this.type;
    }

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Get the tenantId value.
     *
     * @return the tenantId value
     */
    public String tenantId() {
        return this.tenantId;
    }

    /**
     * Set the tenantId value.
     *
     * @param tenantId the tenantId value to set
     * @return the ManagementGroupInner object itself.
     */
    public ManagementGroupInner withTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    /**
     * Get the displayName value.
     *
     * @return the displayName value
     */
    public String displayName() {
        return this.displayName;
    }

    /**
     * Set the displayName value.
     *
     * @param displayName the displayName value to set
     * @return the ManagementGroupInner object itself.
     */
    public ManagementGroupInner withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Get the details value.
     *
     * @return the details value
     */
    public ManagementGroupDetails details() {
        return this.details;
    }

    /**
     * Set the details value.
     *
     * @param details the details value to set
     * @return the ManagementGroupInner object itself.
     */
    public ManagementGroupInner withDetails(ManagementGroupDetails details) {
        this.details = details;
        return this;
    }

    /**
     * Get the children value.
     *
     * @return the children value
     */
    public List<ManagementGroupChildInfo> children() {
        return this.children;
    }

    /**
     * Set the children value.
     *
     * @param children the children value to set
     * @return the ManagementGroupInner object itself.
     */
    public ManagementGroupInner withChildren(List<ManagementGroupChildInfo> children) {
        this.children = children;
        return this;
    }

}
