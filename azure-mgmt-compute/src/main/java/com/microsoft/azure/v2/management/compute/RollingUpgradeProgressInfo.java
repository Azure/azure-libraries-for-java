/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.compute;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Information about the number of virtual machine instances in each upgrade
 * state.
 */
public final class RollingUpgradeProgressInfo {
    /**
     * The number of instances that have been successfully upgraded.
     */
    @JsonProperty(value = "successfulInstanceCount", access = JsonProperty.Access.WRITE_ONLY)
    private Integer successfulInstanceCount;

    /**
     * The number of instances that have failed to be upgraded successfully.
     */
    @JsonProperty(value = "failedInstanceCount", access = JsonProperty.Access.WRITE_ONLY)
    private Integer failedInstanceCount;

    /**
     * The number of instances that are currently being upgraded.
     */
    @JsonProperty(value = "inProgressInstanceCount", access = JsonProperty.Access.WRITE_ONLY)
    private Integer inProgressInstanceCount;

    /**
     * The number of instances that have not yet begun to be upgraded.
     */
    @JsonProperty(value = "pendingInstanceCount", access = JsonProperty.Access.WRITE_ONLY)
    private Integer pendingInstanceCount;

    /**
     * Get the successfulInstanceCount value.
     *
     * @return the successfulInstanceCount value.
     */
    public Integer successfulInstanceCount() {
        return this.successfulInstanceCount;
    }

    /**
     * Get the failedInstanceCount value.
     *
     * @return the failedInstanceCount value.
     */
    public Integer failedInstanceCount() {
        return this.failedInstanceCount;
    }

    /**
     * Get the inProgressInstanceCount value.
     *
     * @return the inProgressInstanceCount value.
     */
    public Integer inProgressInstanceCount() {
        return this.inProgressInstanceCount;
    }

    /**
     * Get the pendingInstanceCount value.
     *
     * @return the pendingInstanceCount value.
     */
    public Integer pendingInstanceCount() {
        return this.pendingInstanceCount;
    }
}
