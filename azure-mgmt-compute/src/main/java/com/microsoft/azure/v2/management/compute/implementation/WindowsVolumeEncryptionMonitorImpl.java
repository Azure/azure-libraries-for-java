/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.compute.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.compute.DiskEncryptionSettings;
import com.microsoft.azure.v2.management.compute.DiskVolumeEncryptionMonitor;
import com.microsoft.azure.v2.management.compute.EncryptionStatus;
import com.microsoft.azure.v2.management.compute.OperatingSystemTypes;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.Utils;
import io.reactivex.Maybe;
import io.reactivex.Observable;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * The implementation for DiskVolumeEncryptionStatus for Windows virtual machine.
 */
@LangDefinition
class WindowsVolumeEncryptionMonitorImpl implements DiskVolumeEncryptionMonitor {
    private final String rgName;
    private final String vmName;
    private final ComputeManager computeManager;
    private VirtualMachineExtensionInner encryptionExtension;
    private VirtualMachineInner virtualMachine;

    /**
     * Creates WindowsVolumeEncryptionMonitorImpl.
     *
     * @param virtualMachineId resource id of Windows virtual machine to retrieve encryption status from
     * @param computeManager compute manager
     */
    WindowsVolumeEncryptionMonitorImpl(String virtualMachineId, ComputeManager computeManager) {
        this.rgName = ResourceUtils.groupFromResourceId(virtualMachineId);
        this.vmName = ResourceUtils.nameFromResourceId(virtualMachineId);
        this.computeManager = computeManager;
    }

    @Override
    public OperatingSystemTypes osType() {
        return OperatingSystemTypes.WINDOWS;
    }

    @Override
    public String progressMessage() {
        if (!hasEncryptionDetails()) {
            return null;
        }
        return String.format("OSDisk: %s DataDisk: %s", osDiskStatus(), dataDiskStatus());
    }

    @Override
    public EncryptionStatus osDiskStatus() {
        if (!hasEncryptionDetails()) {
            return EncryptionStatus.NOT_ENCRYPTED;
        }
        if (encryptionExtension.provisioningState() == null) {
            return EncryptionStatus.NOT_ENCRYPTED;
        }
        if (!encryptionExtension.provisioningState().equalsIgnoreCase("Succeeded")) {
            return EncryptionStatus.NOT_ENCRYPTED;
        }
        if (this.virtualMachine.storageProfile() == null
                || virtualMachine.storageProfile().osDisk() == null
                || virtualMachine.storageProfile().osDisk().encryptionSettings() == null) {
            return EncryptionStatus.NOT_ENCRYPTED;
        }
        DiskEncryptionSettings encryptionSettings = virtualMachine
                .storageProfile()
                .osDisk()
                .encryptionSettings();
        if (encryptionSettings.diskEncryptionKey() != null
                && encryptionSettings.diskEncryptionKey().secretUrl() != null
                && Utils.toPrimitiveBoolean(encryptionSettings.enabled())) {
            return EncryptionStatus.ENCRYPTED;
        }
        return EncryptionStatus.NOT_ENCRYPTED;
    }

    @Override
    public EncryptionStatus dataDiskStatus() {
        if (!hasEncryptionDetails()) {
            return EncryptionStatus.NOT_ENCRYPTED;
        }
        if (encryptionExtension.provisioningState() == null) {
            return EncryptionStatus.NOT_ENCRYPTED;
        }
        if (!encryptionExtension.provisioningState().equalsIgnoreCase("Succeeded")) {
            return EncryptionStatus.NOT_ENCRYPTED;
        }
        HashMap<String, Object> publicSettings = new LinkedHashMap<>();
        if (encryptionExtension.settings() == null) {
            publicSettings = (LinkedHashMap<String, Object>) encryptionExtension.settings();
        }
        if (!publicSettings.containsKey("VolumeType")
                || publicSettings.get("VolumeType") == null
                || ((String) publicSettings.get("VolumeType")).isEmpty()
                || ((String) publicSettings.get("VolumeType")).equalsIgnoreCase("All")
                || ((String) publicSettings.get("VolumeType")).equalsIgnoreCase("Data")) {
            String encryptionOperation = (String) publicSettings.get("EncryptionOperation");
            if (encryptionOperation != null && encryptionOperation.equalsIgnoreCase("EnableEncryption")) {
                return EncryptionStatus.ENCRYPTED;
            }
            return EncryptionStatus.NOT_ENCRYPTED;
        }
        return EncryptionStatus.UNKNOWN;
    }

    @Override
    public DiskVolumeEncryptionMonitor refresh() {
        return refreshAsync().blockingGet();
    }

    @Override
    public Maybe<DiskVolumeEncryptionMonitor> refreshAsync() {
        final WindowsVolumeEncryptionMonitorImpl self = this;
        // Refreshes the cached Windows virtual machine and installed encryption extension
        //
        return retrieveVirtualMachineAsync()
                .map(virtualMachineInner -> {
                    this.virtualMachine = virtualMachineInner;
                    if (virtualMachineInner.resources() != null) {
                        for (VirtualMachineExtensionInner extension : virtualMachineInner.resources()) {
                            if (extension.publisher().equalsIgnoreCase("Microsoft.Azure.Security")
                                    && extension.virtualMachineExtensionType().equalsIgnoreCase("AzureDiskEncryption")) {
                                this.encryptionExtension = extension;
                                break;
                            }
                        }
                    }
                    return (DiskVolumeEncryptionMonitor) this;
                }).lastElement();
    }

    /**
     * Retrieve the virtual machine.
     * If the virtual machine does not exists then an error observable will be returned.
     *
     * @return the retrieved virtual machine
     */
    private Observable<VirtualMachineInner> retrieveVirtualMachineAsync() {
        return this.computeManager
                .inner()
                .virtualMachines()
                .getByResourceGroupAsync(rgName, vmName)
                .switchIfEmpty(Maybe.error(new Exception(String.format("VM with name '%s' not found (resource group '%s')", vmName, rgName))))
                .toObservable();
    }

    private boolean hasEncryptionDetails() {
        return virtualMachine != null && this.encryptionExtension != null;
    }
}
