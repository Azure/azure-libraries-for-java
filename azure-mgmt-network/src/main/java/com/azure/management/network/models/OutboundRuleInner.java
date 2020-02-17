// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.SubResource;
import com.azure.management.network.LoadBalancerOutboundRuleProtocol;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The OutboundRule model.
 */
@JsonFlatten
@Fluent
public class OutboundRuleInner extends SubResource {
    /*
     * The name of the resource that is unique within the set of outbound rules
     * used by the load balancer. This name can be used to access the resource.
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
    @JsonProperty(value = "type", access = JsonProperty.Access.WRITE_ONLY)
    private String type;

    /*
     * The number of outbound ports to be used for NAT.
     */
    @JsonProperty(value = "properties.allocatedOutboundPorts")
    private Integer allocatedOutboundPorts;

    /*
     * The Frontend IP addresses of the load balancer.
     */
    @JsonProperty(value = "properties.frontendIPConfigurations")
    private List<SubResource> frontendIPConfigurations;

    /*
     * Reference to another subresource.
     */
    @JsonProperty(value = "properties.backendAddressPool")
    private SubResource backendAddressPool;

    /*
     * Gets the provisioning state of the PublicIP resource. Possible values
     * are: 'Updating', 'Deleting', and 'Failed'.
     */
    @JsonProperty(value = "properties.provisioningState")
    private String provisioningState;

    /*
     * The protocol for the outbound rule in load balancer.
     */
    @JsonProperty(value = "properties.protocol")
    private LoadBalancerOutboundRuleProtocol protocol;

    /*
     * Receive bidirectional TCP Reset on TCP flow idle timeout or unexpected
     * connection termination. This element is only used when the protocol is
     * set to TCP.
     */
    @JsonProperty(value = "properties.enableTcpReset")
    private Boolean enableTcpReset;

    /*
     * The timeout for the TCP idle connection.
     */
    @JsonProperty(value = "properties.idleTimeoutInMinutes")
    private Integer idleTimeoutInMinutes;

    /**
     * Get the name property: The name of the resource that is unique within
     * the set of outbound rules used by the load balancer. This name can be
     * used to access the resource.
     * 
     * @return the name value.
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name property: The name of the resource that is unique within
     * the set of outbound rules used by the load balancer. This name can be
     * used to access the resource.
     * 
     * @param name the name value to set.
     * @return the OutboundRuleInner object itself.
     */
    public OutboundRuleInner withName(String name) {
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
     * @return the OutboundRuleInner object itself.
     */
    public OutboundRuleInner withEtag(String etag) {
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
     * Get the allocatedOutboundPorts property: The number of outbound ports to
     * be used for NAT.
     * 
     * @return the allocatedOutboundPorts value.
     */
    public Integer allocatedOutboundPorts() {
        return this.allocatedOutboundPorts;
    }

    /**
     * Set the allocatedOutboundPorts property: The number of outbound ports to
     * be used for NAT.
     * 
     * @param allocatedOutboundPorts the allocatedOutboundPorts value to set.
     * @return the OutboundRuleInner object itself.
     */
    public OutboundRuleInner withAllocatedOutboundPorts(Integer allocatedOutboundPorts) {
        this.allocatedOutboundPorts = allocatedOutboundPorts;
        return this;
    }

    /**
     * Get the frontendIPConfigurations property: The Frontend IP addresses of
     * the load balancer.
     * 
     * @return the frontendIPConfigurations value.
     */
    public List<SubResource> frontendIPConfigurations() {
        return this.frontendIPConfigurations;
    }

    /**
     * Set the frontendIPConfigurations property: The Frontend IP addresses of
     * the load balancer.
     * 
     * @param frontendIPConfigurations the frontendIPConfigurations value to
     * set.
     * @return the OutboundRuleInner object itself.
     */
    public OutboundRuleInner withFrontendIPConfigurations(List<SubResource> frontendIPConfigurations) {
        this.frontendIPConfigurations = frontendIPConfigurations;
        return this;
    }

    /**
     * Get the backendAddressPool property: Reference to another subresource.
     * 
     * @return the backendAddressPool value.
     */
    public SubResource backendAddressPool() {
        return this.backendAddressPool;
    }

    /**
     * Set the backendAddressPool property: Reference to another subresource.
     * 
     * @param backendAddressPool the backendAddressPool value to set.
     * @return the OutboundRuleInner object itself.
     */
    public OutboundRuleInner withBackendAddressPool(SubResource backendAddressPool) {
        this.backendAddressPool = backendAddressPool;
        return this;
    }

    /**
     * Get the provisioningState property: Gets the provisioning state of the
     * PublicIP resource. Possible values are: 'Updating', 'Deleting', and
     * 'Failed'.
     * 
     * @return the provisioningState value.
     */
    public String provisioningState() {
        return this.provisioningState;
    }

    /**
     * Set the provisioningState property: Gets the provisioning state of the
     * PublicIP resource. Possible values are: 'Updating', 'Deleting', and
     * 'Failed'.
     * 
     * @param provisioningState the provisioningState value to set.
     * @return the OutboundRuleInner object itself.
     */
    public OutboundRuleInner withProvisioningState(String provisioningState) {
        this.provisioningState = provisioningState;
        return this;
    }

    /**
     * Get the protocol property: The protocol for the outbound rule in load
     * balancer.
     * 
     * @return the protocol value.
     */
    public LoadBalancerOutboundRuleProtocol protocol() {
        return this.protocol;
    }

    /**
     * Set the protocol property: The protocol for the outbound rule in load
     * balancer.
     * 
     * @param protocol the protocol value to set.
     * @return the OutboundRuleInner object itself.
     */
    public OutboundRuleInner withProtocol(LoadBalancerOutboundRuleProtocol protocol) {
        this.protocol = protocol;
        return this;
    }

    /**
     * Get the enableTcpReset property: Receive bidirectional TCP Reset on TCP
     * flow idle timeout or unexpected connection termination. This element is
     * only used when the protocol is set to TCP.
     * 
     * @return the enableTcpReset value.
     */
    public Boolean enableTcpReset() {
        return this.enableTcpReset;
    }

    /**
     * Set the enableTcpReset property: Receive bidirectional TCP Reset on TCP
     * flow idle timeout or unexpected connection termination. This element is
     * only used when the protocol is set to TCP.
     * 
     * @param enableTcpReset the enableTcpReset value to set.
     * @return the OutboundRuleInner object itself.
     */
    public OutboundRuleInner withEnableTcpReset(Boolean enableTcpReset) {
        this.enableTcpReset = enableTcpReset;
        return this;
    }

    /**
     * Get the idleTimeoutInMinutes property: The timeout for the TCP idle
     * connection.
     * 
     * @return the idleTimeoutInMinutes value.
     */
    public Integer idleTimeoutInMinutes() {
        return this.idleTimeoutInMinutes;
    }

    /**
     * Set the idleTimeoutInMinutes property: The timeout for the TCP idle
     * connection.
     * 
     * @param idleTimeoutInMinutes the idleTimeoutInMinutes value to set.
     * @return the OutboundRuleInner object itself.
     */
    public OutboundRuleInner withIdleTimeoutInMinutes(Integer idleTimeoutInMinutes) {
        this.idleTimeoutInMinutes = idleTimeoutInMinutes;
        return this;
    }
}
