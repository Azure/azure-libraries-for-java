/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.network;

import java.util.List;

/**
 * Dns settings of a network interface.
 */
public class NetworkInterfaceDnsSettings {
    /**
     * Gets or sets list of DNS servers IP addresses.
     */
    private List<String> dnsServers;

    /**
     * Gets or sets list of Applied DNS servers IP addresses.
     */
    private List<String> appliedDnsServers;

    /**
     * Gets or sets the internal DNS name.
     */
    private String internalDnsNameLabel;

    /**
     * Gets or sets the internal fqdn.
     */
    private String internalFqdn;

    /**
     * Gets or sets internal domain name suffix of the NIC.
     */
    private String internalDomainNameSuffix;

    /**
     * Get the dnsServers value.
     *
     * @return the dnsServers value
     */
    public List<String> dnsServers() {
        return this.dnsServers;
    }

    /**
     * Set the dnsServers value.
     *
     * @param dnsServers the dnsServers value to set
     * @return the NetworkInterfaceDnsSettings object itself.
     */
    public NetworkInterfaceDnsSettings withDnsServers(List<String> dnsServers) {
        this.dnsServers = dnsServers;
        return this;
    }

    /**
     * Get the appliedDnsServers value.
     *
     * @return the appliedDnsServers value
     */
    public List<String> appliedDnsServers() {
        return this.appliedDnsServers;
    }

    /**
     * Set the appliedDnsServers value.
     *
     * @param appliedDnsServers the appliedDnsServers value to set
     * @return the NetworkInterfaceDnsSettings object itself.
     */
    public NetworkInterfaceDnsSettings withAppliedDnsServers(List<String> appliedDnsServers) {
        this.appliedDnsServers = appliedDnsServers;
        return this;
    }

    /**
     * Get the internalDnsNameLabel value.
     *
     * @return the internalDnsNameLabel value
     */
    public String internalDnsNameLabel() {
        return this.internalDnsNameLabel;
    }

    /**
     * Set the internalDnsNameLabel value.
     *
     * @param internalDnsNameLabel the internalDnsNameLabel value to set
     * @return the NetworkInterfaceDnsSettings object itself.
     */
    public NetworkInterfaceDnsSettings withInternalDnsNameLabel(String internalDnsNameLabel) {
        this.internalDnsNameLabel = internalDnsNameLabel;
        return this;
    }

    /**
     * Get the internalFqdn value.
     *
     * @return the internalFqdn value
     */
    public String internalFqdn() {
        return this.internalFqdn;
    }

    /**
     * Set the internalFqdn value.
     *
     * @param internalFqdn the internalFqdn value to set
     * @return the NetworkInterfaceDnsSettings object itself.
     */
    public NetworkInterfaceDnsSettings withInternalFqdn(String internalFqdn) {
        this.internalFqdn = internalFqdn;
        return this;
    }

    /**
     * Get the internalDomainNameSuffix value.
     *
     * @return the internalDomainNameSuffix value
     */
    public String internalDomainNameSuffix() {
        return this.internalDomainNameSuffix;
    }

    /**
     * Set the internalDomainNameSuffix value.
     *
     * @param internalDomainNameSuffix the internalDomainNameSuffix value to set
     * @return the NetworkInterfaceDnsSettings object itself.
     */
    public NetworkInterfaceDnsSettings withInternalDomainNameSuffix(String internalDomainNameSuffix) {
        this.internalDomainNameSuffix = internalDomainNameSuffix;
        return this;
    }

}
