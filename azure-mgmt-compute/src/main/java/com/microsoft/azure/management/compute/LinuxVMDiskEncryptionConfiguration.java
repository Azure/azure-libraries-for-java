/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.management.apigeneration.LangDefinition;

/**
 * Type representing encryption settings to be applied to a Linux virtual machine.
 */
@LangDefinition
public class LinuxVMDiskEncryptionConfiguration
        extends VirtualMachineEncryptionConfiguration<LinuxVMDiskEncryptionConfiguration> {
    /**
     * Creates LinuxVMDiskEncryptionSettings.
     *
     * @param keyVaultId the resource ID of the KeyVault to store the disk encryption key
     * @param aadClientId  client ID of an AAD application which has permission to the KeyVault
     * @param aadSecret client secret corresponding to the client ID
     */
    public LinuxVMDiskEncryptionConfiguration(String keyVaultId,
                                              String aadClientId,
                                              String aadSecret) {
        super(keyVaultId, aadClientId, aadSecret, null);
    }

    /**
     * Creates LinuxVMDiskEncryptionSettings.
     *
     * @param keyVaultId the resource ID of the KeyVault to store the disk encryption key
     * @param aadClientId  client ID of an AAD application which has permission to the KeyVault
     * @param aadSecret client secret corresponding to the client ID
     * @param azureEnvironment Azure environment
     */
    public LinuxVMDiskEncryptionConfiguration(String keyVaultId,
                                              String aadClientId,
                                              String aadSecret,
                                              AzureEnvironment azureEnvironment) {
        super(keyVaultId, aadClientId, aadSecret, azureEnvironment);
    }

    /**
     * Creates LinuxVMDiskEncryptionSettings.
     *
     * @param keyVaultId the resource ID of the KeyVault to store the disk encryption key
     */
    public LinuxVMDiskEncryptionConfiguration(String keyVaultId) {
        super(keyVaultId);
    }

    /**
     * Creates LinuxVMDiskEncryptionSettings.
     *
     * @param keyVaultId the resource ID of the KeyVault to store the disk encryption key
     * @param azureEnvironment Azure environment
     */
    public LinuxVMDiskEncryptionConfiguration(String keyVaultId, AzureEnvironment azureEnvironment) {
        super(keyVaultId, azureEnvironment);
    }

    @Override
    public OperatingSystemTypes osType() {
        return OperatingSystemTypes.LINUX;
    }

    /**
     * Specifies the pass phrase for encrypting Linux OS or data disks.
     *
     * @param passPhrase the pass phrase
     * @return LinuxVMDiskEncryptionSettings
     */
    public LinuxVMDiskEncryptionConfiguration withPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase;
        return this;
    }
}