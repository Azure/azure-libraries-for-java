/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.recoveryservicessiterecovery;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Azure VM managed disk input details.
 */
public class A2AVmManagedDiskInputDetails {
    /**
     * The disk Id.
     */
    @JsonProperty(value = "diskId")
    private String diskId;

    /**
     * The primary staging storage account Arm Id.
     */
    @JsonProperty(value = "primaryStagingAzureStorageAccountId")
    private String primaryStagingAzureStorageAccountId;

    /**
     * The target resource group Arm Id.
     */
    @JsonProperty(value = "recoveryResourceGroupId")
    private String recoveryResourceGroupId;

    /**
     * The replica disk type. Its an optional value and will be same as source
     * disk type if not user provided.
     */
    @JsonProperty(value = "recoveryReplicaDiskAccountType")
    private String recoveryReplicaDiskAccountType;

    /**
     * The target disk type after failover. Its an optional value and will be
     * same as source disk type if not user provided.
     */
    @JsonProperty(value = "recoveryTargetDiskAccountType")
    private String recoveryTargetDiskAccountType;

    /**
     * Get the diskId value.
     *
     * @return the diskId value
     */
    public String diskId() {
        return this.diskId;
    }

    /**
     * Set the diskId value.
     *
     * @param diskId the diskId value to set
     * @return the A2AVmManagedDiskInputDetails object itself.
     */
    public A2AVmManagedDiskInputDetails withDiskId(String diskId) {
        this.diskId = diskId;
        return this;
    }

    /**
     * Get the primaryStagingAzureStorageAccountId value.
     *
     * @return the primaryStagingAzureStorageAccountId value
     */
    public String primaryStagingAzureStorageAccountId() {
        return this.primaryStagingAzureStorageAccountId;
    }

    /**
     * Set the primaryStagingAzureStorageAccountId value.
     *
     * @param primaryStagingAzureStorageAccountId the primaryStagingAzureStorageAccountId value to set
     * @return the A2AVmManagedDiskInputDetails object itself.
     */
    public A2AVmManagedDiskInputDetails withPrimaryStagingAzureStorageAccountId(String primaryStagingAzureStorageAccountId) {
        this.primaryStagingAzureStorageAccountId = primaryStagingAzureStorageAccountId;
        return this;
    }

    /**
     * Get the recoveryResourceGroupId value.
     *
     * @return the recoveryResourceGroupId value
     */
    public String recoveryResourceGroupId() {
        return this.recoveryResourceGroupId;
    }

    /**
     * Set the recoveryResourceGroupId value.
     *
     * @param recoveryResourceGroupId the recoveryResourceGroupId value to set
     * @return the A2AVmManagedDiskInputDetails object itself.
     */
    public A2AVmManagedDiskInputDetails withRecoveryResourceGroupId(String recoveryResourceGroupId) {
        this.recoveryResourceGroupId = recoveryResourceGroupId;
        return this;
    }

    /**
     * Get the recoveryReplicaDiskAccountType value.
     *
     * @return the recoveryReplicaDiskAccountType value
     */
    public String recoveryReplicaDiskAccountType() {
        return this.recoveryReplicaDiskAccountType;
    }

    /**
     * Set the recoveryReplicaDiskAccountType value.
     *
     * @param recoveryReplicaDiskAccountType the recoveryReplicaDiskAccountType value to set
     * @return the A2AVmManagedDiskInputDetails object itself.
     */
    public A2AVmManagedDiskInputDetails withRecoveryReplicaDiskAccountType(String recoveryReplicaDiskAccountType) {
        this.recoveryReplicaDiskAccountType = recoveryReplicaDiskAccountType;
        return this;
    }

    /**
     * Get the recoveryTargetDiskAccountType value.
     *
     * @return the recoveryTargetDiskAccountType value
     */
    public String recoveryTargetDiskAccountType() {
        return this.recoveryTargetDiskAccountType;
    }

    /**
     * Set the recoveryTargetDiskAccountType value.
     *
     * @param recoveryTargetDiskAccountType the recoveryTargetDiskAccountType value to set
     * @return the A2AVmManagedDiskInputDetails object itself.
     */
    public A2AVmManagedDiskInputDetails withRecoveryTargetDiskAccountType(String recoveryTargetDiskAccountType) {
        this.recoveryTargetDiskAccountType = recoveryTargetDiskAccountType;
        return this;
    }

}
