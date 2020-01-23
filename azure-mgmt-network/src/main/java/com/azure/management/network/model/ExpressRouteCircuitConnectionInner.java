// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.model;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.SubResource;
import com.azure.management.network.CircuitConnectionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The ExpressRouteCircuitConnection model.
 */
@JsonFlatten
@Fluent
public class ExpressRouteCircuitConnectionInner extends SubResource {
    /*
     * Gets name of the resource that is unique within a resource group. This
     * name can be used to access the resource.
     */
    @JsonProperty(value = "name")
    private String name;

    /*
     * A unique read-only string that changes whenever the resource is updated.
     */
    @JsonProperty(value = "etag", access = JsonProperty.Access.WRITE_ONLY)
    private String etag;

    /*
     * Type of the resource.
     */
    @JsonProperty(value = "type", access = JsonProperty.Access.WRITE_ONLY)
    private String type;

    /*
     * Reference to another subresource.
     */
    @JsonProperty(value = "properties.expressRouteCircuitPeering")
    private SubResource expressRouteCircuitPeering;

    /*
     * Reference to another subresource.
     */
    @JsonProperty(value = "properties.peerExpressRouteCircuitPeering")
    private SubResource peerExpressRouteCircuitPeering;

    /*
     * /29 IP address space to carve out Customer addresses for tunnels.
     */
    @JsonProperty(value = "properties.addressPrefix")
    private String addressPrefix;

    /*
     * The authorization key.
     */
    @JsonProperty(value = "properties.authorizationKey")
    private String authorizationKey;

    /*
     * Express Route Circuit connection state.
     */
    @JsonProperty(value = "properties.circuitConnectionStatus")
    private CircuitConnectionStatus circuitConnectionStatus;

    /*
     * Provisioning state of the circuit connection resource. Possible values
     * are: 'Succeeded', 'Updating', 'Deleting', and 'Failed'.
     */
    @JsonProperty(value = "properties.provisioningState", access = JsonProperty.Access.WRITE_ONLY)
    private String provisioningState;

    /**
     * Get the name property: Gets name of the resource that is unique within a
     * resource group. This name can be used to access the resource.
     * 
     * @return the name value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name property: Gets name of the resource that is unique within a
     * resource group. This name can be used to access the resource.
     * 
     * @param name the name value to set.
     * @return the ExpressRouteCircuitConnectionInner object itself.
     */
    public ExpressRouteCircuitConnectionInner setName(String name) {
        this.name = name;
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
     * Get the type property: Type of the resource.
     * 
     * @return the type value.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Get the expressRouteCircuitPeering property: Reference to another
     * subresource.
     * 
     * @return the expressRouteCircuitPeering value.
     */
    public SubResource getExpressRouteCircuitPeering() {
        return this.expressRouteCircuitPeering;
    }

    /**
     * Set the expressRouteCircuitPeering property: Reference to another
     * subresource.
     * 
     * @param expressRouteCircuitPeering the expressRouteCircuitPeering value
     * to set.
     * @return the ExpressRouteCircuitConnectionInner object itself.
     */
    public ExpressRouteCircuitConnectionInner setExpressRouteCircuitPeering(SubResource expressRouteCircuitPeering) {
        this.expressRouteCircuitPeering = expressRouteCircuitPeering;
        return this;
    }

    /**
     * Get the peerExpressRouteCircuitPeering property: Reference to another
     * subresource.
     * 
     * @return the peerExpressRouteCircuitPeering value.
     */
    public SubResource getPeerExpressRouteCircuitPeering() {
        return this.peerExpressRouteCircuitPeering;
    }

    /**
     * Set the peerExpressRouteCircuitPeering property: Reference to another
     * subresource.
     * 
     * @param peerExpressRouteCircuitPeering the peerExpressRouteCircuitPeering
     * value to set.
     * @return the ExpressRouteCircuitConnectionInner object itself.
     */
    public ExpressRouteCircuitConnectionInner setPeerExpressRouteCircuitPeering(SubResource peerExpressRouteCircuitPeering) {
        this.peerExpressRouteCircuitPeering = peerExpressRouteCircuitPeering;
        return this;
    }

    /**
     * Get the addressPrefix property: /29 IP address space to carve out
     * Customer addresses for tunnels.
     * 
     * @return the addressPrefix value.
     */
    public String getAddressPrefix() {
        return this.addressPrefix;
    }

    /**
     * Set the addressPrefix property: /29 IP address space to carve out
     * Customer addresses for tunnels.
     * 
     * @param addressPrefix the addressPrefix value to set.
     * @return the ExpressRouteCircuitConnectionInner object itself.
     */
    public ExpressRouteCircuitConnectionInner setAddressPrefix(String addressPrefix) {
        this.addressPrefix = addressPrefix;
        return this;
    }

    /**
     * Get the authorizationKey property: The authorization key.
     * 
     * @return the authorizationKey value.
     */
    public String getAuthorizationKey() {
        return this.authorizationKey;
    }

    /**
     * Set the authorizationKey property: The authorization key.
     * 
     * @param authorizationKey the authorizationKey value to set.
     * @return the ExpressRouteCircuitConnectionInner object itself.
     */
    public ExpressRouteCircuitConnectionInner setAuthorizationKey(String authorizationKey) {
        this.authorizationKey = authorizationKey;
        return this;
    }

    /**
     * Get the circuitConnectionStatus property: Express Route Circuit
     * connection state.
     * 
     * @return the circuitConnectionStatus value.
     */
    public CircuitConnectionStatus getCircuitConnectionStatus() {
        return this.circuitConnectionStatus;
    }

    /**
     * Set the circuitConnectionStatus property: Express Route Circuit
     * connection state.
     * 
     * @param circuitConnectionStatus the circuitConnectionStatus value to set.
     * @return the ExpressRouteCircuitConnectionInner object itself.
     */
    public ExpressRouteCircuitConnectionInner setCircuitConnectionStatus(CircuitConnectionStatus circuitConnectionStatus) {
        this.circuitConnectionStatus = circuitConnectionStatus;
        return this;
    }

    /**
     * Get the provisioningState property: Provisioning state of the circuit
     * connection resource. Possible values are: 'Succeeded', 'Updating',
     * 'Deleting', and 'Failed'.
     * 
     * @return the provisioningState value.
     */
    public String getProvisioningState() {
        return this.provisioningState;
    }
}