/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute.implementation;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.AzureTokenCredentials;
import com.microsoft.azure.management.compute.DiskVolumeEncryptionMonitor;
import com.microsoft.azure.management.compute.DiskVolumeType;
import com.microsoft.azure.management.compute.LinuxVMDiskEncryptionConfiguration;
import com.microsoft.azure.management.compute.OperatingSystemTypes;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.compute.VirtualMachineEncryption;
import com.microsoft.azure.management.compute.WindowsVMDiskEncryptionConfiguration;
import com.microsoft.rest.credentials.ServiceClientCredentials;
import rx.Observable;

/**
 * Implementation of VirtualMachineEncryption.
 */
class VirtualMachineEncryptionImpl implements VirtualMachineEncryption {
    private final VirtualMachine virtualMachine;
    private final VirtualMachineEncryptionHelper virtualMachineEncryptionHelper;

    /**
     * Creates VirtualMachineEncryptionImpl.
     *
     * @param virtualMachine virtual machine on which encryption related operations to be performed
     */
    VirtualMachineEncryptionImpl(final VirtualMachine virtualMachine) {
        this.virtualMachine = virtualMachine;
        this.virtualMachineEncryptionHelper = new VirtualMachineEncryptionHelper(virtualMachine);
    }

    @Override
    public Observable<DiskVolumeEncryptionMonitor> enableAsync(String keyVaultId, String aadClientId, String aadSecret) {
        if (this.virtualMachine.osType() == OperatingSystemTypes.LINUX) {
            return enableAsync(new LinuxVMDiskEncryptionConfiguration(keyVaultId, aadClientId, aadSecret, getAzureEnvironment()));
        } else {
            return enableAsync(new WindowsVMDiskEncryptionConfiguration(keyVaultId, aadClientId, aadSecret, getAzureEnvironment()));
        }
    }

    @Override
    public Observable<DiskVolumeEncryptionMonitor> enableAsync(String keyVaultId) {
        if (this.virtualMachine.osType() == OperatingSystemTypes.LINUX) {
            return enableAsync(new LinuxVMDiskEncryptionConfiguration(keyVaultId, getAzureEnvironment()));
        } else {
            return enableAsync(new WindowsVMDiskEncryptionConfiguration(keyVaultId, getAzureEnvironment()));
        }
    }

    private AzureEnvironment getAzureEnvironment() {
        AzureEnvironment azureEnvironment = null;
        if (virtualMachine.manager().inner().restClient() != null) {
            ServiceClientCredentials credentials = virtualMachine.manager().inner().restClient().credentials();
            if (credentials instanceof AzureTokenCredentials) {
                azureEnvironment = ((AzureTokenCredentials) credentials).environment();
            }
        }
        return azureEnvironment;
    }

    @Override
    public Observable<DiskVolumeEncryptionMonitor> enableAsync(WindowsVMDiskEncryptionConfiguration encryptionSettings) {
        return virtualMachineEncryptionHelper.enableEncryptionAsync(encryptionSettings);
    }

    @Override
    public Observable<DiskVolumeEncryptionMonitor> enableAsync(LinuxVMDiskEncryptionConfiguration encryptionSettings) {
        return virtualMachineEncryptionHelper.enableEncryptionAsync(encryptionSettings);
    }

    @Override
    public Observable<DiskVolumeEncryptionMonitor> disableAsync(final DiskVolumeType volumeType) {
        return virtualMachineEncryptionHelper.disableEncryptionAsync(volumeType);
    }

    @Override
    public Observable<DiskVolumeEncryptionMonitor> getMonitorAsync() {
        return new ProxyEncryptionMonitorImpl(this.virtualMachine).refreshAsync();
    }

    @Override
    public DiskVolumeEncryptionMonitor enable(String keyVaultId, String aadClientId, String aadSecret) {
        return enableAsync(keyVaultId, aadClientId, aadSecret).toBlocking().last();
    }

    @Override
    public DiskVolumeEncryptionMonitor enable(String keyVaultId) {
        return enableAsync(keyVaultId).toBlocking().last();
    }

    @Override
    public DiskVolumeEncryptionMonitor enable(WindowsVMDiskEncryptionConfiguration encryptionSettings) {
        return enableAsync(encryptionSettings).toBlocking().last();
    }

    @Override
    public DiskVolumeEncryptionMonitor enable(LinuxVMDiskEncryptionConfiguration encryptionSettings) {
        return enableAsync(encryptionSettings).toBlocking().last();
    }

    @Override
    public DiskVolumeEncryptionMonitor disable(final DiskVolumeType volumeType) {
        return disableAsync(volumeType).toBlocking().last();
    }

    @Override
    public DiskVolumeEncryptionMonitor getMonitor() {
        return getMonitorAsync().toBlocking().last();
    }
}
