// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.model;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The IPAddressAvailabilityResult model.
 */
@Fluent
public final class IPAddressAvailabilityResultInner {
    /*
     * Private IP address availability.
     */
    @JsonProperty(value = "available")
    private Boolean available;

    /*
     * Contains other available private IP addresses if the asked for address
     * is taken.
     */
    @JsonProperty(value = "availableIPAddresses")
    private List<String> availableIPAddresses;

    /**
     * Get the available property: Private IP address availability.
     * 
     * @return the available value.
     */
    public Boolean isAvailable() {
        return this.available;
    }

    /**
     * Set the available property: Private IP address availability.
     * 
     * @param available the available value to set.
     * @return the IPAddressAvailabilityResultInner object itself.
     */
    public IPAddressAvailabilityResultInner setAvailable(Boolean available) {
        this.available = available;
        return this;
    }

    /**
     * Get the availableIPAddresses property: Contains other available private
     * IP addresses if the asked for address is taken.
     * 
     * @return the availableIPAddresses value.
     */
    public List<String> getAvailableIPAddresses() {
        return this.availableIPAddresses;
    }

    /**
     * Set the availableIPAddresses property: Contains other available private
     * IP addresses if the asked for address is taken.
     * 
     * @param availableIPAddresses the availableIPAddresses value to set.
     * @return the IPAddressAvailabilityResultInner object itself.
     */
    public IPAddressAvailabilityResultInner setAvailableIPAddresses(List<String> availableIPAddresses) {
        this.availableIPAddresses = availableIPAddresses;
        return this;
    }
}
