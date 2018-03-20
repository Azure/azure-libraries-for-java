/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.recoveryservicessiterecovery.implementation;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The properties of the Failover Process Server request.
 */
public class FailoverProcessServerRequestPropertiesInner {
    /**
     * The container identifier.
     */
    @JsonProperty(value = "containerName")
    private String containerName;

    /**
     * The source process server.
     */
    @JsonProperty(value = "sourceProcessServerId")
    private String sourceProcessServerId;

    /**
     * The new process server.
     */
    @JsonProperty(value = "targetProcessServerId")
    private String targetProcessServerId;

    /**
     * The VMS to migrate.
     */
    @JsonProperty(value = "vmsToMigrate")
    private List<String> vmsToMigrate;

    /**
     * A value for failover type. It can be systemlevel/serverlevel.
     */
    @JsonProperty(value = "updateType")
    private String updateType;

    /**
     * Get the containerName value.
     *
     * @return the containerName value
     */
    public String containerName() {
        return this.containerName;
    }

    /**
     * Set the containerName value.
     *
     * @param containerName the containerName value to set
     * @return the FailoverProcessServerRequestPropertiesInner object itself.
     */
    public FailoverProcessServerRequestPropertiesInner withContainerName(String containerName) {
        this.containerName = containerName;
        return this;
    }

    /**
     * Get the sourceProcessServerId value.
     *
     * @return the sourceProcessServerId value
     */
    public String sourceProcessServerId() {
        return this.sourceProcessServerId;
    }

    /**
     * Set the sourceProcessServerId value.
     *
     * @param sourceProcessServerId the sourceProcessServerId value to set
     * @return the FailoverProcessServerRequestPropertiesInner object itself.
     */
    public FailoverProcessServerRequestPropertiesInner withSourceProcessServerId(String sourceProcessServerId) {
        this.sourceProcessServerId = sourceProcessServerId;
        return this;
    }

    /**
     * Get the targetProcessServerId value.
     *
     * @return the targetProcessServerId value
     */
    public String targetProcessServerId() {
        return this.targetProcessServerId;
    }

    /**
     * Set the targetProcessServerId value.
     *
     * @param targetProcessServerId the targetProcessServerId value to set
     * @return the FailoverProcessServerRequestPropertiesInner object itself.
     */
    public FailoverProcessServerRequestPropertiesInner withTargetProcessServerId(String targetProcessServerId) {
        this.targetProcessServerId = targetProcessServerId;
        return this;
    }

    /**
     * Get the vmsToMigrate value.
     *
     * @return the vmsToMigrate value
     */
    public List<String> vmsToMigrate() {
        return this.vmsToMigrate;
    }

    /**
     * Set the vmsToMigrate value.
     *
     * @param vmsToMigrate the vmsToMigrate value to set
     * @return the FailoverProcessServerRequestPropertiesInner object itself.
     */
    public FailoverProcessServerRequestPropertiesInner withVmsToMigrate(List<String> vmsToMigrate) {
        this.vmsToMigrate = vmsToMigrate;
        return this;
    }

    /**
     * Get the updateType value.
     *
     * @return the updateType value
     */
    public String updateType() {
        return this.updateType;
    }

    /**
     * Set the updateType value.
     *
     * @param updateType the updateType value to set
     * @return the FailoverProcessServerRequestPropertiesInner object itself.
     */
    public FailoverProcessServerRequestPropertiesInner withUpdateType(String updateType) {
        this.updateType = updateType;
        return this;
    }

}
