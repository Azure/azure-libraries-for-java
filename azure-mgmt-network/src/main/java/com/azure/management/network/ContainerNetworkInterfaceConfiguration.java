// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.SubResource;
import com.azure.management.network.model.IPConfigurationProfileInner;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The ContainerNetworkInterfaceConfiguration model.
 */
@JsonFlatten
@Fluent
public class ContainerNetworkInterfaceConfiguration extends SubResource {
    /*
     * The name of the resource. This name can be used to access the resource.
     */
    @JsonProperty(value = "name")
    private String name;

    /*
     * Sub Resource type.
     */
    @JsonProperty(value = "type", access = JsonProperty.Access.WRITE_ONLY)
    private String type;

    /*
     * A unique read-only string that changes whenever the resource is updated.
     */
    @JsonProperty(value = "etag")
    private String etag;

    /*
     * A list of ip configurations of the container network interface
     * configuration.
     */
    @JsonProperty(value = "properties.ipConfigurations")
    private List<IPConfigurationProfileInner> ipConfigurations;

    /*
     * A list of container network interfaces created from this container
     * network interface configuration.
     */
    @JsonProperty(value = "properties.containerNetworkInterfaces")
    private List<SubResource> containerNetworkInterfaces;

    /*
     * The provisioning state of the resource.
     */
    @JsonProperty(value = "properties.provisioningState", access = JsonProperty.Access.WRITE_ONLY)
    private String provisioningState;

    /**
     * Get the name property: The name of the resource. This name can be used
     * to access the resource.
     * 
     * @return the name value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name property: The name of the resource. This name can be used
     * to access the resource.
     * 
     * @param name the name value to set.
     * @return the ContainerNetworkInterfaceConfiguration object itself.
     */
    public ContainerNetworkInterfaceConfiguration setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the type property: Sub Resource type.
     * 
     * @return the type value.
     */
    public String getType() {
        return this.type;
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
     * Set the etag property: A unique read-only string that changes whenever
     * the resource is updated.
     * 
     * @param etag the etag value to set.
     * @return the ContainerNetworkInterfaceConfiguration object itself.
     */
    public ContainerNetworkInterfaceConfiguration setEtag(String etag) {
        this.etag = etag;
        return this;
    }

    /**
     * Get the ipConfigurations property: A list of ip configurations of the
     * container network interface configuration.
     * 
     * @return the ipConfigurations value.
     */
    public List<IPConfigurationProfileInner> getIpConfigurations() {
        return this.ipConfigurations;
    }

    /**
     * Set the ipConfigurations property: A list of ip configurations of the
     * container network interface configuration.
     * 
     * @param ipConfigurations the ipConfigurations value to set.
     * @return the ContainerNetworkInterfaceConfiguration object itself.
     */
    public ContainerNetworkInterfaceConfiguration setIpConfigurations(List<IPConfigurationProfileInner> ipConfigurations) {
        this.ipConfigurations = ipConfigurations;
        return this;
    }

    /**
     * Get the containerNetworkInterfaces property: A list of container network
     * interfaces created from this container network interface configuration.
     * 
     * @return the containerNetworkInterfaces value.
     */
    public List<SubResource> getContainerNetworkInterfaces() {
        return this.containerNetworkInterfaces;
    }

    /**
     * Set the containerNetworkInterfaces property: A list of container network
     * interfaces created from this container network interface configuration.
     * 
     * @param containerNetworkInterfaces the containerNetworkInterfaces value
     * to set.
     * @return the ContainerNetworkInterfaceConfiguration object itself.
     */
    public ContainerNetworkInterfaceConfiguration setContainerNetworkInterfaces(List<SubResource> containerNetworkInterfaces) {
        this.containerNetworkInterfaces = containerNetworkInterfaces;
        return this;
    }

    /**
     * Get the provisioningState property: The provisioning state of the
     * resource.
     * 
     * @return the provisioningState value.
     */
    public String getProvisioningState() {
        return this.provisioningState;
    }
}