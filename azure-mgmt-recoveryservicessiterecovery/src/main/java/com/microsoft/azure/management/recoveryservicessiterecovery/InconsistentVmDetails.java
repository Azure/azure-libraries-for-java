/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.recoveryservicessiterecovery;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the monitoring details for consistency check of
 * inconsistent Protected Entity.
 */
public class InconsistentVmDetails {
    /**
     * The Vm name.
     */
    @JsonProperty(value = "vmName")
    private String vmName;

    /**
     * The Cloud name.
     */
    @JsonProperty(value = "cloudName")
    private String cloudName;

    /**
     * The list of details regarding state of the Protected Entity in SRS and
     * On prem.
     */
    @JsonProperty(value = "details")
    private List<String> details;

    /**
     * The list of error ids.
     */
    @JsonProperty(value = "errorIds")
    private List<String> errorIds;

    /**
     * Get the vmName value.
     *
     * @return the vmName value
     */
    public String vmName() {
        return this.vmName;
    }

    /**
     * Set the vmName value.
     *
     * @param vmName the vmName value to set
     * @return the InconsistentVmDetails object itself.
     */
    public InconsistentVmDetails withVmName(String vmName) {
        this.vmName = vmName;
        return this;
    }

    /**
     * Get the cloudName value.
     *
     * @return the cloudName value
     */
    public String cloudName() {
        return this.cloudName;
    }

    /**
     * Set the cloudName value.
     *
     * @param cloudName the cloudName value to set
     * @return the InconsistentVmDetails object itself.
     */
    public InconsistentVmDetails withCloudName(String cloudName) {
        this.cloudName = cloudName;
        return this;
    }

    /**
     * Get the details value.
     *
     * @return the details value
     */
    public List<String> details() {
        return this.details;
    }

    /**
     * Set the details value.
     *
     * @param details the details value to set
     * @return the InconsistentVmDetails object itself.
     */
    public InconsistentVmDetails withDetails(List<String> details) {
        this.details = details;
        return this;
    }

    /**
     * Get the errorIds value.
     *
     * @return the errorIds value
     */
    public List<String> errorIds() {
        return this.errorIds;
    }

    /**
     * Set the errorIds value.
     *
     * @param errorIds the errorIds value to set
     * @return the InconsistentVmDetails object itself.
     */
    public InconsistentVmDetails withErrorIds(List<String> errorIds) {
        this.errorIds = errorIds;
        return this;
    }

}
