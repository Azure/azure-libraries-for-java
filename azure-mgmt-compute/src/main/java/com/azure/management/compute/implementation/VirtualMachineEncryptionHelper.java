/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute.implementation;

import com.microsoft.azure.management.compute.DiskEncryptionSettings;
import com.microsoft.azure.management.compute.DiskVolumeEncryptionMonitor;
import com.microsoft.azure.management.compute.DiskVolumeType;
import com.microsoft.azure.management.compute.OperatingSystemTypes;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.compute.VirtualMachineEncryptionConfiguration;
import com.microsoft.azure.management.compute.VirtualMachineExtension;
import com.microsoft.azure.management.compute.VirtualMachineExtensionInstanceView;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Helper type to enable or disable virtual machine disk (OS, Data) encryption.
 */
class VirtualMachineEncryptionHelper {
    private final OperatingSystemTypes osType;
    private final VirtualMachine virtualMachine;
    // Error messages
    private static final String ERROR_ENCRYPTION_EXTENSION_NOT_FOUND = "Expected encryption extension not found in the VM";
    private static final String ERROR_NON_SUCCESS_PROVISIONING_STATE = "Extension needed for disk encryption was not provisioned correctly, found ProvisioningState as '%s'";
    private static final String ERROR_EXPECTED_KEY_VAULT_URL_NOT_FOUND = "Could not found URL pointing to the secret for disk encryption";
    private static final String ERROR_EXPECTED_ENCRYPTION_EXTENSION_STATUS_NOT_FOUND = "Encryption extension with successful status not found in the VM";
    private static final String ERROR_ENCRYPTION_EXTENSION_STATUS_IS_EMPTY = "Encryption extension status is empty";
    private static final String ERROR_ON_LINUX_ONLY_DATA_DISK_CAN_BE_DECRYPTED = "Only data disk is supported to disable encryption on Linux VM";
    private static final String ERROR_LEGACY_ENCRYPTION_EXTENSION_FOUND_AAD_PARAMS_REQUIRED = "VM has Legacy Encryption Extension installed, updating it requires aadClientId and aadSecret parameters";
    private static final String ERROR_NOAAD_ENCRYPTION_EXTENSION_FOUND_AAD_PARAMS_NOT_REQUIRED = "VM has NoAAD Encryption Extension installed, aadClientId and aadSecret parameters are not allowed for this extension.";
    private static final String ERROR_NO_DECRYPT_ENCRYPTION_EXTENSION_NOT_FOUND = ERROR_ENCRYPTION_EXTENSION_NOT_FOUND + ", no decryption to perform";
    /**
     * Creates VirtualMachineEncryptionHelper.
     *
     * @param virtualMachine the virtual machine to enable or disable encryption
     */
    VirtualMachineEncryptionHelper(final VirtualMachine virtualMachine) {
        this.virtualMachine = virtualMachine;
        this.osType = this.virtualMachine.osType();
    }

    /**
     * Enables encryption.
     *
     * @param encryptionConfig the settings to be used for encryption extension
     * @param <T> the Windows or Linux encryption settings
     * @return an observable that emits the encryption status
     */
    <T extends VirtualMachineEncryptionConfiguration<T>> Observable<DiskVolumeEncryptionMonitor> enableEncryptionAsync(final VirtualMachineEncryptionConfiguration<T> encryptionConfig) {
        final EncryptionSettings.Enable<T> encryptSettings = EncryptionSettings.<T>createEnable(encryptionConfig);
        //
        // If encryption extension is already installed then ensure user input aligns with state of the extension
        return validateBeforeEncryptAsync(encryptSettings)
                // If encryption extension is already installed then update it
                .flatMap(new Func1<VirtualMachineExtension, Observable<VirtualMachine>>() {
                    @Override
                    public Observable<VirtualMachine> call(VirtualMachineExtension virtualMachineExtension) {
                        return updateEncryptionExtensionAsync(encryptSettings, virtualMachineExtension);
                    }
                })
                // If encryption extension is not installed then install it
                .switchIfEmpty(installEncryptionExtensionAsync(encryptSettings))
                .flatMap(new Func1<VirtualMachine, Observable<DiskVolumeEncryptionMonitor>>() {
                    @Override
                    public Observable<DiskVolumeEncryptionMonitor> call(VirtualMachine virtualMachine) {
                        if (encryptSettings.requestedForNoAADEncryptExtension()) {
                            return noAADExtensionEncryptPostProcessingAsync(virtualMachine);
                        } else {
                            return legacyExtensionEncryptPostProcessingAsync(encryptSettings);
                        }
                    }
                });
    }

    /**
     * Disables encryption on the given disk volume.
     *
     * @param volumeType the disk volume
     * @return an observable that emits the decryption status
     */
    Observable<DiskVolumeEncryptionMonitor> disableEncryptionAsync(final DiskVolumeType volumeType) {
        final EncryptionSettings.Disable encryptSettings = EncryptionSettings.createDisable(volumeType);
        //
        return validateBeforeDecryptAsync(volumeType)
                // Update the encryption extension
                .flatMap(new Func1<VirtualMachineExtension, Observable<VMExtTuple>>() {
                    @Override
                    public Observable<VMExtTuple> call(final VirtualMachineExtension virtualMachineExtension) {
                        return updateEncryptionExtensionAsync(encryptSettings, virtualMachineExtension)
                                .map(new Func1<VirtualMachine, VMExtTuple>() {
                                    @Override
                                    public VMExtTuple call(VirtualMachine virtualMachine) {
                                        return new VMExtTuple(virtualMachine, virtualMachineExtension);
                                    }
                                });
                    }
                })
                .flatMap(new Func1<VMExtTuple, Observable<DiskVolumeEncryptionMonitor>>() {
                    @Override
                    public Observable<DiskVolumeEncryptionMonitor> call(VMExtTuple vmExt) {
                        if (EncryptionExtensionIdentifier.isNoAADVersion(osType, vmExt.encryptExtension.versionName())) {
                            return noAADExtensionDecryptPostProcessingAsync(vmExt.virtualMachine);
                        } else {
                            return legacyExtensionDecryptPostProcessingAsync(encryptSettings);
                        }
                    }
                });
    }

    /**
     * Perform any post processing after initiating VM encryption through NoAAD extension.
     *
     * @param virtualMachine the encrypted virtual machine
     * @return the encryption progress monitor
     */
    private Observable<DiskVolumeEncryptionMonitor> noAADExtensionEncryptPostProcessingAsync(final VirtualMachine virtualMachine) {
        // Gets the encryption status
        return osType == OperatingSystemTypes.LINUX
                ? new LinuxDiskVolumeNoAADEncryptionMonitorImpl(virtualMachine.id(), virtualMachine.manager()).refreshAsync()
                : new WindowsVolumeNoAADEncryptionMonitorImpl(virtualMachine.id(), virtualMachine.manager()).refreshAsync();
    }

    /**
     * Perform any post processing after initiating VM encryption through Legacy extension.
     *
     * @param encryptConfig the user provided encryption config
     * @return the encryption progress monitor
     */
    private <T extends VirtualMachineEncryptionConfiguration<T>> Observable<DiskVolumeEncryptionMonitor> legacyExtensionEncryptPostProcessingAsync(final EncryptionSettings.Enable<T> encryptConfig) {
        // Retrieve the encryption key URL after extension install or update
        return retrieveEncryptionExtensionStatusStringAsync(ERROR_EXPECTED_KEY_VAULT_URL_NOT_FOUND)
                // Update the VM's OS Disk (in storage profile) with the encryption metadata
                .flatMap(new Func1<String, Observable<VirtualMachine>>() {
                    @Override
                    public Observable<VirtualMachine> call(String keyVaultSecretUrl) {
                        return updateVMStorageProfileAsync(encryptConfig, keyVaultSecretUrl);
                    }
                })
                // Gets the encryption status
                .flatMap(new Func1<VirtualMachine, Observable<DiskVolumeEncryptionMonitor>>() {
                    @Override
                    public Observable<DiskVolumeEncryptionMonitor> call(VirtualMachine virtualMachine) {
                        return osType == OperatingSystemTypes.LINUX
                                ? new LinuxDiskVolumeLegacyEncryptionMonitorImpl(virtualMachine.id(), virtualMachine.manager()).refreshAsync()
                                : new WindowsVolumeLegacyEncryptionMonitorImpl(virtualMachine.id(), virtualMachine.manager()).refreshAsync();
                    }
                });
    }

    /**
     * Perform any post processing after initiating VM decryption through NoAAD extension.
     *
     * @param virtualMachine the decrypted virtual machine
     * @return the decryption progress monitor
     */
    private Observable<DiskVolumeEncryptionMonitor> noAADExtensionDecryptPostProcessingAsync(final VirtualMachine virtualMachine) {
        // Gets the encryption status
        return osType == OperatingSystemTypes.LINUX
                ? new LinuxDiskVolumeNoAADEncryptionMonitorImpl(virtualMachine.id(), virtualMachine.manager()).refreshAsync()
                : new WindowsVolumeNoAADEncryptionMonitorImpl(virtualMachine.id(), virtualMachine.manager()).refreshAsync();
    }

    /**
     * Perform any post processing after initiating VM encryption through Legacy extension.
     *
     * @param encryptConfig the user provided encryption config
     * @return the encryption progress monitor
     */
    private Observable<DiskVolumeEncryptionMonitor> legacyExtensionDecryptPostProcessingAsync(final EncryptionSettings.Disable encryptConfig) {
        return retrieveEncryptionExtensionStatusStringAsync(ERROR_ENCRYPTION_EXTENSION_STATUS_IS_EMPTY)
                // Update the VM's OS profile by marking encryption disabled
                .flatMap(new Func1<String, Observable<VirtualMachine>>() {
                    @Override
                    public Observable<VirtualMachine> call(String status) {
                        return updateVMStorageProfileAsync(encryptConfig);
                    }
                })
                // Gets the encryption status
                .flatMap(new Func1<VirtualMachine, Observable<DiskVolumeEncryptionMonitor>>() {
                    @Override
                    public Observable<DiskVolumeEncryptionMonitor> call(VirtualMachine virtualMachine) {
                        return osType == OperatingSystemTypes.LINUX
                                ? new LinuxDiskVolumeLegacyEncryptionMonitorImpl(virtualMachine.id(), virtualMachine.manager()).refreshAsync()
                                : new WindowsVolumeLegacyEncryptionMonitorImpl(virtualMachine.id(), virtualMachine.manager()).refreshAsync();
                    }
                });
    }

    /**
     * If VM has encryption extension installed then validate that it can be updated based on user provided params,
     * if invalid then return observable emitting error otherwise an observable emitting the extension.
     * If extension is not installed then return empty observable.
     *
     * @param encryptSettings the user provided configuration
     * @return observable emitting error, extension or empty.
     */
    private <T extends VirtualMachineEncryptionConfiguration<T>> Observable<VirtualMachineExtension> validateBeforeEncryptAsync(final EncryptionSettings.Enable<T> encryptSettings) {
        if (this.virtualMachine.storageProfile().osDisk().encryptionSettings() != null && encryptSettings.requestedForNoAADEncryptExtension()) {
            return Observable.error(new RuntimeException(ERROR_LEGACY_ENCRYPTION_EXTENSION_FOUND_AAD_PARAMS_REQUIRED));
        }
        return getEncryptionExtensionInstalledInVMAsync()
                .flatMap(new Func1<VirtualMachineExtension, Observable<VirtualMachineExtension>>() {
                    @Override
                    public Observable<VirtualMachineExtension> call(VirtualMachineExtension extension) {
                        if (EncryptionExtensionIdentifier.isNoAADVersion(osType, extension.versionName())) {
                            // NoAAD-Encrypt-Extension exists so Legacy-Encrypt-Extension cannot be installed hence AAD params are not required.
                            return encryptSettings.requestedForNoAADEncryptExtension()
                                    ? Observable.just(extension)
                                    : Observable.<VirtualMachineExtension>error(new RuntimeException(ERROR_NOAAD_ENCRYPTION_EXTENSION_FOUND_AAD_PARAMS_NOT_REQUIRED));
                        } else {
                            // Legacy-Encrypt-Extension exists so NoAAD-Encrypt-Extension cannot be installed hence AAD params are required.
                            return encryptSettings.requestedForNoAADEncryptExtension()
                                    ? Observable.<VirtualMachineExtension>error(new RuntimeException(ERROR_LEGACY_ENCRYPTION_EXTENSION_FOUND_AAD_PARAMS_REQUIRED))
                                    : Observable.just(extension);
                        }
                    }
                });
    }

    /**
     * Checks the given volume type in the virtual machine can be decrypted.
     *
     * @param volumeType the volume type to decrypt
     * @return observable that emit existing encryption extension if installed else empty observable
     */
    private Observable<VirtualMachineExtension> validateBeforeDecryptAsync(final DiskVolumeType volumeType) {
        if (osType == OperatingSystemTypes.LINUX && volumeType != DiskVolumeType.DATA) {
            return toErrorObservable(ERROR_ON_LINUX_ONLY_DATA_DISK_CAN_BE_DECRYPTED);
        }
        return getEncryptionExtensionInstalledInVMAsync()
                .switchIfEmpty(this.<VirtualMachineExtension>toErrorObservable(ERROR_NO_DECRYPT_ENCRYPTION_EXTENSION_NOT_FOUND));
    }

    /**
     * Retrieves encryption extension installed in the virtual machine, if the extension is
     * not installed then return an empty observable.
     *
     * @return an observable that emits the encryption extension installed in the virtual machine
     */
    private Observable<VirtualMachineExtension> getEncryptionExtensionInstalledInVMAsync() {
        return virtualMachine.listExtensionsAsync()
                // firstOrDefault() is used intentionally here instead of first() to ensure
                // this method return empty observable if matching extension is not found.
                //
                .firstOrDefault(null, new Func1<VirtualMachineExtension, Boolean>() {
                    @Override
                    public Boolean call(final VirtualMachineExtension extension) {
                        return EncryptionExtensionIdentifier.isEncryptionPublisherName(extension.publisherName())
                                && EncryptionExtensionIdentifier.isEncryptionTypeName(extension.typeName(), osType);
                    }
                }).flatMap(new Func1<VirtualMachineExtension, Observable<VirtualMachineExtension>>() {
                    @Override
                    public Observable<VirtualMachineExtension> call(VirtualMachineExtension extension) {
                        if (extension == null) {
                            return Observable.empty();
                        }
                        return Observable.just(extension);
                    }
                });
    }

    /**
     * Updates the encryption extension in the virtual machine using provided configuration.
     * If extension is not installed then this method return empty observable.
     *
     * @param encryptSettings the volume encryption extension settings
     * @param encryptionExtension existing encryption extension
     * @return an observable that emits updated virtual machine if extension was already installed otherwise an empty observable.
     */
    private Observable<VirtualMachine> updateEncryptionExtensionAsync(final EncryptionSettings encryptSettings, VirtualMachineExtension encryptionExtension) {
        return virtualMachine.update()
                .updateExtension(encryptionExtension.name())
                    .withPublicSettings(encryptSettings.extensionPublicSettings())
                    .withProtectedSettings(encryptSettings.extensionProtectedSettings())
                    .parent()
                .applyAsync();
    }

    /**
     * Prepare encryption extension using provided configuration and install it in the virtual machine.
     *
     * @param encryptSettings the volume encryption configuration
     * @return an observable that emits updated virtual machine
     */
    private <T extends VirtualMachineEncryptionConfiguration<T>> Observable<VirtualMachine> installEncryptionExtensionAsync(final EncryptionSettings.Enable<T> encryptSettings) {
        return Observable.defer(new Func0<Observable<VirtualMachine>>() {
            @Override
            public Observable<VirtualMachine> call() {
                final String typeName = EncryptionExtensionIdentifier.typeName(osType);
                return virtualMachine.update()
                        .defineNewExtension(typeName)
                            .withPublisher(EncryptionExtensionIdentifier.publisherName())
                            .withType(typeName)
                            .withVersion(encryptSettings.encryptionExtensionVersion())
                            .withPublicSettings(encryptSettings.extensionPublicSettings())
                            .withProtectedSettings(encryptSettings.extensionProtectedSettings())
                            .withMinorVersionAutoUpgrade()
                            .attach()
                        .applyAsync();
            }
        });
    }

    /**
     * Retrieves the encryption extension status from the extension instance view.
     * An error observable will be returned if
     *   1. extension is not installed
     *   2. extension is not provisioned successfully
     *   2. extension status could be retrieved (either not found or empty)
     *
     * @param statusEmptyErrorMessage the error message to emit if unable to locate the status
     * @return an observable that emits status message
     */
    private Observable<String> retrieveEncryptionExtensionStatusStringAsync(final String statusEmptyErrorMessage) {
        final VirtualMachineEncryptionHelper self = this;
        return getEncryptionExtensionInstalledInVMAsync()
                .switchIfEmpty(self.<VirtualMachineExtension>toErrorObservable(ERROR_ENCRYPTION_EXTENSION_NOT_FOUND))
                .flatMap(new Func1<VirtualMachineExtension, Observable<VirtualMachineExtensionInstanceView>>() {
                    @Override
                    public Observable<VirtualMachineExtensionInstanceView> call(VirtualMachineExtension extension) {
                        if (!extension.provisioningState().equalsIgnoreCase("Succeeded")) {
                            return self.toErrorObservable((String.format(ERROR_NON_SUCCESS_PROVISIONING_STATE, extension.provisioningState())));
                        }
                        return extension.getInstanceViewAsync();
                    }
                })
                .flatMap(new Func1<VirtualMachineExtensionInstanceView, Observable<String>>() {
                    @Override
                    public Observable<String> call(VirtualMachineExtensionInstanceView instanceView) {
                        if (instanceView == null
                                || instanceView.statuses() == null
                                || instanceView.statuses().size() == 0) {
                            return self.toErrorObservable(ERROR_EXPECTED_ENCRYPTION_EXTENSION_STATUS_NOT_FOUND);
                        }
                        String extensionStatus = instanceView.statuses().get(0).message();
                        if (extensionStatus == null) {
                            return self.toErrorObservable(statusEmptyErrorMessage);
                        }
                        return Observable.just(extensionStatus);
                    }
                });
    }

    /**
     * Updates the virtual machine's OS Disk model with the encryption specific details so that platform can
     * use it while booting the virtual machine.
     *
     * @param encryptSettings the configuration specific to enabling the encryption
     * @param encryptionSecretKeyVaultUrl the keyVault URL pointing to secret holding disk encryption key
     * @return an observable that emits updated virtual machine
     */
    private Observable<VirtualMachine> updateVMStorageProfileAsync(final EncryptionSettings encryptSettings,
                                                                   final String encryptionSecretKeyVaultUrl) {
        DiskEncryptionSettings diskEncryptionSettings = encryptSettings.storageProfileEncryptionSettings();
        diskEncryptionSettings.diskEncryptionKey()
                .withSecretUrl(encryptionSecretKeyVaultUrl);
        return virtualMachine.update()
                .withOSDiskEncryptionSettings(diskEncryptionSettings)
                .applyAsync();
    }

    /**
     * Updates the virtual machine's OS Disk model with the encryption specific details.
     *
     * @param encryptSettings the configuration specific to disabling the encryption
     * @return an observable that emits updated virtual machine
     */
    private Observable<VirtualMachine> updateVMStorageProfileAsync(final EncryptionSettings encryptSettings) {
        DiskEncryptionSettings diskEncryptionSettings = encryptSettings.storageProfileEncryptionSettings();
        return virtualMachine.update()
                .withOSDiskEncryptionSettings(diskEncryptionSettings)
                .applyAsync();
    }

    /**
     * Wraps the given message in an error observable.
     *
     * @param message the error message
     * @param <ResultT> observable type
     * @return error observable with message wrapped
     */
    private <ResultT> Observable<ResultT> toErrorObservable(String message) {
        return Observable.error(new Exception(message));
    }

    private class VMExtTuple {
        private final VirtualMachine virtualMachine;
        private final VirtualMachineExtension encryptExtension;
        //
        VMExtTuple(VirtualMachine vm, VirtualMachineExtension ext) {
            this.virtualMachine = vm;
            this.encryptExtension = ext;
        }
    }
}