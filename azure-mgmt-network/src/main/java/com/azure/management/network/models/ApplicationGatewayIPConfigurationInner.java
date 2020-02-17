// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.SubResource;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The ApplicationGatewayIPConfiguration model.
 */
@JsonFlatten
@Fluent
public class ApplicationGatewayIPConfigurationInner extends SubResource {
    /*
     * Name of the IP configuration that is unique within an Application
     * Gateway.
     */
    @JsonProperty(value = "name")
    private String name;

    /*
     * A unique read-only string that changes whenever the resource is updated.
     */
    @JsonProperty(value = "etag")
    private String etag;

    /*
     * Type of the resource.
     */
    @JsonProperty(value = "type")
    private String type;

    /*
     * Reference to another subresource.
     */
    @JsonProperty(value = "properties.subnet")
    private SubResource subnet;

    /*
     * Provisioning state of the application gateway subnet resource. Possible
     * values are: 'Updating', 'Deleting', and 'Failed'.
     */
    @JsonProperty(value = "properties.provisioningState")
    private String provisioningState;

    /**
     * Get the name property: Name of the IP configuration that is unique
     * within an Application Gateway.
     * 
     * @return the name value.
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name property: Name of the IP configuration that is unique
     * within an Application Gateway.
     * 
     * @param name the name value to set.
     * @return the ApplicationGatewayIPConfigurationInner object itself.
     */
    public ApplicationGatewayIPConfigurationInner withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the etag property: A unique read-only string that changes whenever
     * the resource is updated.
     * 
     * @return the etag value.
     */
    public String etag() {
        return this.etag;
    }

    /**
     * Set the etag property: A unique read-only string that changes whenever
     * the resource is updated.
     * 
     * @param etag the etag value to set.
     * @return the ApplicationGatewayIPConfigurationInner object itself.
     */
    public ApplicationGatewayIPConfigurationInner withEtag(String etag) {
        this.etag = etag;
        return this;
    }

    /**
     * Get the type property: Type of the resource.
     * 
     * @return the type value.
     */
    public String type() {
        return this.type;
    }

    /**
     * Set the type property: Type of the resource.
     * 
     * @param type the type value to set.
     * @return the ApplicationGatewayIPConfigurationInner object itself.
     */
    public ApplicationGatewayIPConfigurationInner withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get the subnet property: Reference to another subresource.
     * 
     * @return the subnet value.
     */
    public SubResource subnet() {
        return this.subnet;
    }

    /**
     * Set the subnet property: Reference to another subresource.
     * 
     * @param subnet the subnet value to set.
     * @return the ApplicationGatewayIPConfigurationInner object itself.
     */
    public ApplicationGatewayIPConfigurationInner withSubnet(SubResource subnet) {
        this.subnet = subnet;
        return this;
    }

    /**
     * Get the provisioningState property: Provisioning state of the
     * application gateway subnet resource. Possible values are: 'Updating',
     * 'Deleting', and 'Failed'.
     * 
     * @return the provisioningState value.
     */
    public String provisioningState() {
        return this.provisioningState;
    }

    /**
     * Set the provisioningState property: Provisioning state of the
     * application gateway subnet resource. Possible values are: 'Updating',
     * 'Deleting', and 'Failed'.
     * 
     * @param provisioningState the provisioningState value to set.
     * @return the ApplicationGatewayIPConfigurationInner object itself.
     */
    public ApplicationGatewayIPConfigurationInner withProvisioningState(String provisioningState) {
        this.provisioningState = provisioningState;
        return this;
    }
}
