/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.compute.implementation;

import com.microsoft.azure.v2.management.compute.DiskVolumeEncryptionMonitor;
import com.microsoft.azure.v2.management.compute.DiskVolumeType;
import com.microsoft.azure.v2.management.compute.LinuxVMDiskEncryptionConfiguration;
import com.microsoft.azure.v2.management.compute.OperatingSystemTypes;
import com.microsoft.azure.v2.management.compute.VirtualMachine;
import com.microsoft.azure.v2.management.compute.VirtualMachineEncryption;
import com.microsoft.azure.v2.management.compute.WindowsVMDiskEncryptionConfiguration;
import io.reactivex.Observable;

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
            return enableAsync(new LinuxVMDiskEncryptionConfiguration(keyVaultId, aadClientId, aadSecret));
        } else {
            return enableAsync(new WindowsVMDiskEncryptionConfiguration(keyVaultId, aadClientId, aadSecret));
        }
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
        if (this.virtualMachine.osType() == OperatingSystemTypes.LINUX) {
            return new LinuxDiskVolumeEncryptionMonitorImpl(virtualMachine.id(), virtualMachine.manager())
                    .refreshAsync()
                    .toObservable();
        } else {
            return new WindowsVolumeEncryptionMonitorImpl(virtualMachine.id(), virtualMachine.manager())
                    .refreshAsync()
                    .toObservable();
        }
    }

    @Override
    public DiskVolumeEncryptionMonitor enable(String keyVaultId, String aadClientId, String aadSecret) {
        return enableAsync(keyVaultId, aadClientId, aadSecret).blockingLast(null);
    }

    @Override
    public DiskVolumeEncryptionMonitor enable(WindowsVMDiskEncryptionConfiguration encryptionSettings) {
        return enableAsync(encryptionSettings).blockingLast(null);
    }

    @Override
    public DiskVolumeEncryptionMonitor enable(LinuxVMDiskEncryptionConfiguration encryptionSettings) {
        return enableAsync(encryptionSettings).blockingLast(null);
    }

    @Override
    public DiskVolumeEncryptionMonitor disable(final DiskVolumeType volumeType) {
        return disableAsync(volumeType).blockingLast(null);
    }

    @Override
    public DiskVolumeEncryptionMonitor getMonitor() {
        return getMonitorAsync().blockingLast(null);
    }
}
