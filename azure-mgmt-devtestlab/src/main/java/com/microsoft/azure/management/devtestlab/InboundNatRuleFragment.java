/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.devtestlab;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A rule for NAT - exposing a VM's port (backendPort) on the public IP address
 * using a load balancer.
 */
public class InboundNatRuleFragment {
    /**
     * The transport protocol for the endpoint. Possible values include: 'Tcp',
     * 'Udp'.
     */
    @JsonProperty(value = "transportProtocol")
    private TransportProtocol transportProtocol;

    /**
     * The external endpoint port of the inbound connection. Possible values
     * range between 1 and 65535, inclusive. If unspecified, a value will be
     * allocated automatically.
     */
    @JsonProperty(value = "frontendPort")
    private Integer frontendPort;

    /**
     * The port to which the external traffic will be redirected.
     */
    @JsonProperty(value = "backendPort")
    private Integer backendPort;

    /**
     * Get the transportProtocol value.
     *
     * @return the transportProtocol value
     */
    public TransportProtocol transportProtocol() {
        return this.transportProtocol;
    }

    /**
     * Set the transportProtocol value.
     *
     * @param transportProtocol the transportProtocol value to set
     * @return the InboundNatRuleFragment object itself.
     */
    public InboundNatRuleFragment withTransportProtocol(TransportProtocol transportProtocol) {
        this.transportProtocol = transportProtocol;
        return this;
    }

    /**
     * Get the frontendPort value.
     *
     * @return the frontendPort value
     */
    public Integer frontendPort() {
        return this.frontendPort;
    }

    /**
     * Set the frontendPort value.
     *
     * @param frontendPort the frontendPort value to set
     * @return the InboundNatRuleFragment object itself.
     */
    public InboundNatRuleFragment withFrontendPort(Integer frontendPort) {
        this.frontendPort = frontendPort;
        return this;
    }

    /**
     * Get the backendPort value.
     *
     * @return the backendPort value
     */
    public Integer backendPort() {
        return this.backendPort;
    }

    /**
     * Set the backendPort value.
     *
     * @param backendPort the backendPort value to set
     * @return the InboundNatRuleFragment object itself.
     */
    public InboundNatRuleFragment withBackendPort(Integer backendPort) {
        this.backendPort = backendPort;
        return this;
    }

}
