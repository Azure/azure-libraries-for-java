// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.model;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.SubResource;
import com.azure.management.network.LoadDistribution;
import com.azure.management.network.TransportProtocol;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The LoadBalancingRule model.
 */
@JsonFlatten
@Fluent
public class LoadBalancingRuleInner extends SubResource {
    /*
     * The name of the resource that is unique within the set of load balancing
     * rules used by the load balancer. This name can be used to access the
     * resource.
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
     * Reference to another subresource.
     */
    @JsonProperty(value = "properties.frontendIPConfiguration")
    private SubResource frontendIPConfiguration;

    /*
     * Reference to another subresource.
     */
    @JsonProperty(value = "properties.backendAddressPool")
    private SubResource backendAddressPool;

    /*
     * Reference to another subresource.
     */
    @JsonProperty(value = "properties.probe")
    private SubResource probe;

    /*
     * The transport protocol for the endpoint.
     */
    @JsonProperty(value = "properties.protocol")
    private TransportProtocol protocol;

    /*
     * The load distribution policy for this rule.
     */
    @JsonProperty(value = "properties.loadDistribution")
    private LoadDistribution loadDistribution;

    /*
     * The port for the external endpoint. Port numbers for each rule must be
     * unique within the Load Balancer. Acceptable values are between 0 and
     * 65534. Note that value 0 enables "Any Port".
     */
    @JsonProperty(value = "properties.frontendPort")
    private Integer frontendPort;

    /*
     * The port used for internal connections on the endpoint. Acceptable
     * values are between 0 and 65535. Note that value 0 enables "Any Port".
     */
    @JsonProperty(value = "properties.backendPort")
    private Integer backendPort;

    /*
     * The timeout for the TCP idle connection. The value can be set between 4
     * and 30 minutes. The default value is 4 minutes. This element is only
     * used when the protocol is set to TCP.
     */
    @JsonProperty(value = "properties.idleTimeoutInMinutes")
    private Integer idleTimeoutInMinutes;

    /*
     * Configures a virtual machine's endpoint for the floating IP capability
     * required to configure a SQL AlwaysOn Availability Group. This setting is
     * required when using the SQL AlwaysOn Availability Groups in SQL server.
     * This setting can't be changed after you create the endpoint.
     */
    @JsonProperty(value = "properties.enableFloatingIP")
    private Boolean enableFloatingIP;

    /*
     * Receive bidirectional TCP Reset on TCP flow idle timeout or unexpected
     * connection termination. This element is only used when the protocol is
     * set to TCP.
     */
    @JsonProperty(value = "properties.enableTcpReset")
    private Boolean enableTcpReset;

    /*
     * Configures SNAT for the VMs in the backend pool to use the publicIP
     * address specified in the frontend of the load balancing rule.
     */
    @JsonProperty(value = "properties.disableOutboundSnat")
    private Boolean disableOutboundSnat;

    /*
     * Gets the provisioning state of the PublicIP resource. Possible values
     * are: 'Updating', 'Deleting', and 'Failed'.
     */
    @JsonProperty(value = "properties.provisioningState")
    private String provisioningState;

    /**
     * Get the name property: The name of the resource that is unique within
     * the set of load balancing rules used by the load balancer. This name can
     * be used to access the resource.
     * 
     * @return the name value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name property: The name of the resource that is unique within
     * the set of load balancing rules used by the load balancer. This name can
     * be used to access the resource.
     * 
     * @param name the name value to set.
     * @return the LoadBalancingRuleInner object itself.
     */
    public LoadBalancingRuleInner setName(String name) {
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
     * Set the etag property: A unique read-only string that changes whenever
     * the resource is updated.
     * 
     * @param etag the etag value to set.
     * @return the LoadBalancingRuleInner object itself.
     */
    public LoadBalancingRuleInner setEtag(String etag) {
        this.etag = etag;
        return this;
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
     * Get the frontendIPConfiguration property: Reference to another
     * subresource.
     * 
     * @return the frontendIPConfiguration value.
     */
    public SubResource getFrontendIPConfiguration() {
        return this.frontendIPConfiguration;
    }

    /**
     * Set the frontendIPConfiguration property: Reference to another
     * subresource.
     * 
     * @param frontendIPConfiguration the frontendIPConfiguration value to set.
     * @return the LoadBalancingRuleInner object itself.
     */
    public LoadBalancingRuleInner setFrontendIPConfiguration(SubResource frontendIPConfiguration) {
        this.frontendIPConfiguration = frontendIPConfiguration;
        return this;
    }

    /**
     * Get the backendAddressPool property: Reference to another subresource.
     * 
     * @return the backendAddressPool value.
     */
    public SubResource getBackendAddressPool() {
        return this.backendAddressPool;
    }

    /**
     * Set the backendAddressPool property: Reference to another subresource.
     * 
     * @param backendAddressPool the backendAddressPool value to set.
     * @return the LoadBalancingRuleInner object itself.
     */
    public LoadBalancingRuleInner setBackendAddressPool(SubResource backendAddressPool) {
        this.backendAddressPool = backendAddressPool;
        return this;
    }

    /**
     * Get the probe property: Reference to another subresource.
     * 
     * @return the probe value.
     */
    public SubResource getProbe() {
        return this.probe;
    }

    /**
     * Set the probe property: Reference to another subresource.
     * 
     * @param probe the probe value to set.
     * @return the LoadBalancingRuleInner object itself.
     */
    public LoadBalancingRuleInner setProbe(SubResource probe) {
        this.probe = probe;
        return this;
    }

    /**
     * Get the protocol property: The transport protocol for the endpoint.
     * 
     * @return the protocol value.
     */
    public TransportProtocol getProtocol() {
        return this.protocol;
    }

    /**
     * Set the protocol property: The transport protocol for the endpoint.
     * 
     * @param protocol the protocol value to set.
     * @return the LoadBalancingRuleInner object itself.
     */
    public LoadBalancingRuleInner setProtocol(TransportProtocol protocol) {
        this.protocol = protocol;
        return this;
    }

    /**
     * Get the loadDistribution property: The load distribution policy for this
     * rule.
     * 
     * @return the loadDistribution value.
     */
    public LoadDistribution getLoadDistribution() {
        return this.loadDistribution;
    }

    /**
     * Set the loadDistribution property: The load distribution policy for this
     * rule.
     * 
     * @param loadDistribution the loadDistribution value to set.
     * @return the LoadBalancingRuleInner object itself.
     */
    public LoadBalancingRuleInner setLoadDistribution(LoadDistribution loadDistribution) {
        this.loadDistribution = loadDistribution;
        return this;
    }

    /**
     * Get the frontendPort property: The port for the external endpoint. Port
     * numbers for each rule must be unique within the Load Balancer.
     * Acceptable values are between 0 and 65534. Note that value 0 enables
     * "Any Port".
     * 
     * @return the frontendPort value.
     */
    public Integer getFrontendPort() {
        return this.frontendPort;
    }

    /**
     * Set the frontendPort property: The port for the external endpoint. Port
     * numbers for each rule must be unique within the Load Balancer.
     * Acceptable values are between 0 and 65534. Note that value 0 enables
     * "Any Port".
     * 
     * @param frontendPort the frontendPort value to set.
     * @return the LoadBalancingRuleInner object itself.
     */
    public LoadBalancingRuleInner setFrontendPort(Integer frontendPort) {
        this.frontendPort = frontendPort;
        return this;
    }

    /**
     * Get the backendPort property: The port used for internal connections on
     * the endpoint. Acceptable values are between 0 and 65535. Note that value
     * 0 enables "Any Port".
     * 
     * @return the backendPort value.
     */
    public Integer getBackendPort() {
        return this.backendPort;
    }

    /**
     * Set the backendPort property: The port used for internal connections on
     * the endpoint. Acceptable values are between 0 and 65535. Note that value
     * 0 enables "Any Port".
     * 
     * @param backendPort the backendPort value to set.
     * @return the LoadBalancingRuleInner object itself.
     */
    public LoadBalancingRuleInner setBackendPort(Integer backendPort) {
        this.backendPort = backendPort;
        return this;
    }

    /**
     * Get the idleTimeoutInMinutes property: The timeout for the TCP idle
     * connection. The value can be set between 4 and 30 minutes. The default
     * value is 4 minutes. This element is only used when the protocol is set
     * to TCP.
     * 
     * @return the idleTimeoutInMinutes value.
     */
    public Integer getIdleTimeoutInMinutes() {
        return this.idleTimeoutInMinutes;
    }

    /**
     * Set the idleTimeoutInMinutes property: The timeout for the TCP idle
     * connection. The value can be set between 4 and 30 minutes. The default
     * value is 4 minutes. This element is only used when the protocol is set
     * to TCP.
     * 
     * @param idleTimeoutInMinutes the idleTimeoutInMinutes value to set.
     * @return the LoadBalancingRuleInner object itself.
     */
    public LoadBalancingRuleInner setIdleTimeoutInMinutes(Integer idleTimeoutInMinutes) {
        this.idleTimeoutInMinutes = idleTimeoutInMinutes;
        return this;
    }

    /**
     * Get the enableFloatingIP property: Configures a virtual machine's
     * endpoint for the floating IP capability required to configure a SQL
     * AlwaysOn Availability Group. This setting is required when using the SQL
     * AlwaysOn Availability Groups in SQL server. This setting can't be
     * changed after you create the endpoint.
     * 
     * @return the enableFloatingIP value.
     */
    public Boolean isEnableFloatingIP() {
        return this.enableFloatingIP;
    }

    /**
     * Set the enableFloatingIP property: Configures a virtual machine's
     * endpoint for the floating IP capability required to configure a SQL
     * AlwaysOn Availability Group. This setting is required when using the SQL
     * AlwaysOn Availability Groups in SQL server. This setting can't be
     * changed after you create the endpoint.
     * 
     * @param enableFloatingIP the enableFloatingIP value to set.
     * @return the LoadBalancingRuleInner object itself.
     */
    public LoadBalancingRuleInner setEnableFloatingIP(Boolean enableFloatingIP) {
        this.enableFloatingIP = enableFloatingIP;
        return this;
    }

    /**
     * Get the enableTcpReset property: Receive bidirectional TCP Reset on TCP
     * flow idle timeout or unexpected connection termination. This element is
     * only used when the protocol is set to TCP.
     * 
     * @return the enableTcpReset value.
     */
    public Boolean isEnableTcpReset() {
        return this.enableTcpReset;
    }

    /**
     * Set the enableTcpReset property: Receive bidirectional TCP Reset on TCP
     * flow idle timeout or unexpected connection termination. This element is
     * only used when the protocol is set to TCP.
     * 
     * @param enableTcpReset the enableTcpReset value to set.
     * @return the LoadBalancingRuleInner object itself.
     */
    public LoadBalancingRuleInner setEnableTcpReset(Boolean enableTcpReset) {
        this.enableTcpReset = enableTcpReset;
        return this;
    }

    /**
     * Get the disableOutboundSnat property: Configures SNAT for the VMs in the
     * backend pool to use the publicIP address specified in the frontend of
     * the load balancing rule.
     * 
     * @return the disableOutboundSnat value.
     */
    public Boolean isDisableOutboundSnat() {
        return this.disableOutboundSnat;
    }

    /**
     * Set the disableOutboundSnat property: Configures SNAT for the VMs in the
     * backend pool to use the publicIP address specified in the frontend of
     * the load balancing rule.
     * 
     * @param disableOutboundSnat the disableOutboundSnat value to set.
     * @return the LoadBalancingRuleInner object itself.
     */
    public LoadBalancingRuleInner setDisableOutboundSnat(Boolean disableOutboundSnat) {
        this.disableOutboundSnat = disableOutboundSnat;
        return this;
    }

    /**
     * Get the provisioningState property: Gets the provisioning state of the
     * PublicIP resource. Possible values are: 'Updating', 'Deleting', and
     * 'Failed'.
     * 
     * @return the provisioningState value.
     */
    public String getProvisioningState() {
        return this.provisioningState;
    }

    /**
     * Set the provisioningState property: Gets the provisioning state of the
     * PublicIP resource. Possible values are: 'Updating', 'Deleting', and
     * 'Failed'.
     * 
     * @param provisioningState the provisioningState value to set.
     * @return the LoadBalancingRuleInner object itself.
     */
    public LoadBalancingRuleInner setProvisioningState(String provisioningState) {
        this.provisioningState = provisioningState;
        return this;
    }
}
