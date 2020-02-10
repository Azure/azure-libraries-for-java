// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The NetworkConfigurationDiagnosticProfile model.
 */
@Fluent
public final class NetworkConfigurationDiagnosticProfile {
    /*
     * The direction of the traffic.
     */
    @JsonProperty(value = "direction", required = true)
    private Direction direction;

    /*
     * Protocol to be verified on. Accepted values are '*', TCP, UDP.
     */
    @JsonProperty(value = "protocol", required = true)
    private String protocol;

    /*
     * Traffic source. Accepted values are '*', IP Address/CIDR, Service Tag.
     */
    @JsonProperty(value = "source", required = true)
    private String source;

    /*
     * Traffic destination. Accepted values are: '*', IP Address/CIDR, Service
     * Tag.
     */
    @JsonProperty(value = "destination", required = true)
    private String destination;

    /*
     * Traffic destination port. Accepted values are '*', port (for example,
     * 3389) and port range (for example, 80-100).
     */
    @JsonProperty(value = "destinationPort", required = true)
    private String destinationPort;

    /**
     * Get the direction property: The direction of the traffic.
     * 
     * @return the direction value.
     */
    public Direction direction() {
        return this.direction;
    }

    /**
     * Set the direction property: The direction of the traffic.
     * 
     * @param direction the direction value to set.
     * @return the NetworkConfigurationDiagnosticProfile object itself.
     */
    public NetworkConfigurationDiagnosticProfile withDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    /**
     * Get the protocol property: Protocol to be verified on. Accepted values
     * are '*', TCP, UDP.
     * 
     * @return the protocol value.
     */
    public String protocol() {
        return this.protocol;
    }

    /**
     * Set the protocol property: Protocol to be verified on. Accepted values
     * are '*', TCP, UDP.
     * 
     * @param protocol the protocol value to set.
     * @return the NetworkConfigurationDiagnosticProfile object itself.
     */
    public NetworkConfigurationDiagnosticProfile withProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    /**
     * Get the source property: Traffic source. Accepted values are '*', IP
     * Address/CIDR, Service Tag.
     * 
     * @return the source value.
     */
    public String source() {
        return this.source;
    }

    /**
     * Set the source property: Traffic source. Accepted values are '*', IP
     * Address/CIDR, Service Tag.
     * 
     * @param source the source value to set.
     * @return the NetworkConfigurationDiagnosticProfile object itself.
     */
    public NetworkConfigurationDiagnosticProfile withSource(String source) {
        this.source = source;
        return this;
    }

    /**
     * Get the destination property: Traffic destination. Accepted values are:
     * '*', IP Address/CIDR, Service Tag.
     * 
     * @return the destination value.
     */
    public String destination() {
        return this.destination;
    }

    /**
     * Set the destination property: Traffic destination. Accepted values are:
     * '*', IP Address/CIDR, Service Tag.
     * 
     * @param destination the destination value to set.
     * @return the NetworkConfigurationDiagnosticProfile object itself.
     */
    public NetworkConfigurationDiagnosticProfile withDestination(String destination) {
        this.destination = destination;
        return this;
    }

    /**
     * Get the destinationPort property: Traffic destination port. Accepted
     * values are '*', port (for example, 3389) and port range (for example,
     * 80-100).
     * 
     * @return the destinationPort value.
     */
    public String destinationPort() {
        return this.destinationPort;
    }

    /**
     * Set the destinationPort property: Traffic destination port. Accepted
     * values are '*', port (for example, 3389) and port range (for example,
     * 80-100).
     * 
     * @param destinationPort the destinationPort value to set.
     * @return the NetworkConfigurationDiagnosticProfile object itself.
     */
    public NetworkConfigurationDiagnosticProfile withDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
        return this;
    }
}
