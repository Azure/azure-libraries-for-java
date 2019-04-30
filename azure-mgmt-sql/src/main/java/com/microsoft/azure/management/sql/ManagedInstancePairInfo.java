/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Pairs of Managed Instances in the failover group.
 */
public class ManagedInstancePairInfo {
    /**
     * Id of Primary Managed Instance in pair.
     */
    @JsonProperty(value = "primaryManagedInstanceId")
    private String primaryManagedInstanceId;

    /**
     * Id of Partner Managed Instance in pair.
     */
    @JsonProperty(value = "partnerManagedInstanceId")
    private String partnerManagedInstanceId;

    /**
     * Get the primaryManagedInstanceId value.
     *
     * @return the primaryManagedInstanceId value
     */
    public String primaryManagedInstanceId() {
        return this.primaryManagedInstanceId;
    }

    /**
     * Set the primaryManagedInstanceId value.
     *
     * @param primaryManagedInstanceId the primaryManagedInstanceId value to set
     * @return the ManagedInstancePairInfo object itself.
     */
    public ManagedInstancePairInfo withPrimaryManagedInstanceId(String primaryManagedInstanceId) {
        this.primaryManagedInstanceId = primaryManagedInstanceId;
        return this;
    }

    /**
     * Get the partnerManagedInstanceId value.
     *
     * @return the partnerManagedInstanceId value
     */
    public String partnerManagedInstanceId() {
        return this.partnerManagedInstanceId;
    }

    /**
     * Set the partnerManagedInstanceId value.
     *
     * @param partnerManagedInstanceId the partnerManagedInstanceId value to set
     * @return the ManagedInstancePairInfo object itself.
     */
    public ManagedInstancePairInfo withPartnerManagedInstanceId(String partnerManagedInstanceId) {
        this.partnerManagedInstanceId = partnerManagedInstanceId;
        return this;
    }

}
