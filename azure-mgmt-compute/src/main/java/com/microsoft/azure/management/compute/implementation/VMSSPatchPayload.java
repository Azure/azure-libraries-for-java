/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute.implementation;

import com.microsoft.azure.SubResource;
import com.microsoft.azure.management.compute.VirtualMachineScaleSet;
import com.microsoft.azure.management.compute.VirtualMachineScaleSetIPConfiguration;
import com.microsoft.azure.management.compute.VirtualMachineScaleSetNetworkConfiguration;
import com.microsoft.azure.management.compute.VirtualMachineScaleSetUpdate;
import com.microsoft.azure.management.compute.VirtualMachineScaleSetUpdateIPConfiguration;
import com.microsoft.azure.management.compute.VirtualMachineScaleSetUpdateNetworkConfiguration;
import com.microsoft.azure.management.compute.VirtualMachineScaleSetUpdateNetworkProfile;
import com.microsoft.azure.management.compute.VirtualMachineScaleSetUpdateOSDisk;
import com.microsoft.azure.management.compute.VirtualMachineScaleSetUpdateOSProfile;
import com.microsoft.azure.management.compute.VirtualMachineScaleSetUpdatePublicIPAddressConfiguration;
import com.microsoft.azure.management.compute.VirtualMachineScaleSetUpdateStorageProfile;
import com.microsoft.azure.management.compute.VirtualMachineScaleSetUpdateVMProfile;

import java.util.ArrayList;

class VMSSPatchPayload {
    static VirtualMachineScaleSetUpdate preparePatchPayload(VirtualMachineScaleSet scaleSet) {
        VirtualMachineScaleSetUpdate updateParameter = new VirtualMachineScaleSetUpdate();
        //
        updateParameter.withIdentity(scaleSet.inner().identity());
        updateParameter.withOverprovision(scaleSet.inner().overprovision());
        updateParameter.withPlan(scaleSet.inner().plan());
        updateParameter.withSinglePlacementGroup(scaleSet.inner().singlePlacementGroup());
        updateParameter.withSku(scaleSet.inner().sku());
        updateParameter.withTags(scaleSet.inner().getTags());
        updateParameter.withUpgradePolicy(scaleSet.inner().upgradePolicy());
        //
        if (scaleSet.inner().virtualMachineProfile() != null) {
            // --
            VirtualMachineScaleSetUpdateVMProfile updateVMProfile = new VirtualMachineScaleSetUpdateVMProfile();
            updateVMProfile.withDiagnosticsProfile(scaleSet.inner().virtualMachineProfile().diagnosticsProfile());
            updateVMProfile.withExtensionProfile(scaleSet.inner().virtualMachineProfile().extensionProfile());
            updateVMProfile.withLicenseType(scaleSet.inner().virtualMachineProfile().licenseType());
            //
            if (scaleSet.inner().virtualMachineProfile().storageProfile() != null) {
                // -- --
                VirtualMachineScaleSetUpdateStorageProfile storageProfile = new VirtualMachineScaleSetUpdateStorageProfile();
                storageProfile.withDataDisks(scaleSet.inner().virtualMachineProfile().storageProfile().dataDisks());
                storageProfile.withImageReference(scaleSet.inner().virtualMachineProfile().storageProfile().imageReference());

                if (scaleSet.inner().virtualMachineProfile().storageProfile().osDisk() != null) {
                    VirtualMachineScaleSetUpdateOSDisk osDisk = new VirtualMachineScaleSetUpdateOSDisk();
                    osDisk.withCaching(scaleSet.inner().virtualMachineProfile().storageProfile().osDisk().caching());
                    osDisk.withImage(scaleSet.inner().virtualMachineProfile().storageProfile().osDisk().image());
                    osDisk.withManagedDisk(scaleSet.inner().virtualMachineProfile().storageProfile().osDisk().managedDisk());
                    osDisk.withVhdContainers(scaleSet.inner().virtualMachineProfile().storageProfile().osDisk().vhdContainers());
                    osDisk.withWriteAcceleratorEnabled(scaleSet.inner().virtualMachineProfile().storageProfile().osDisk().writeAcceleratorEnabled());
                    storageProfile.withOsDisk(osDisk);
                }
                updateVMProfile.withStorageProfile(storageProfile);
                // -- --
            }
            if (scaleSet.inner().virtualMachineProfile().osProfile() != null) {
                // -- --
                VirtualMachineScaleSetUpdateOSProfile osProfile = new VirtualMachineScaleSetUpdateOSProfile();
                osProfile.withCustomData(scaleSet.inner().virtualMachineProfile().osProfile().customData());
                osProfile.withLinuxConfiguration(scaleSet.inner().virtualMachineProfile().osProfile().linuxConfiguration());
                osProfile.withSecrets(scaleSet.inner().virtualMachineProfile().osProfile().secrets());
                osProfile.withWindowsConfiguration(scaleSet.inner().virtualMachineProfile().osProfile().windowsConfiguration());
                updateVMProfile.withOsProfile(osProfile);
                // -- --
            }
            if (scaleSet.inner().virtualMachineProfile().networkProfile() != null) {
                // -- --
                VirtualMachineScaleSetUpdateNetworkProfile networkProfile = new VirtualMachineScaleSetUpdateNetworkProfile();

                if (scaleSet.inner().virtualMachineProfile().networkProfile().networkInterfaceConfigurations() != null) {
                    networkProfile.withNetworkInterfaceConfigurations(new ArrayList<VirtualMachineScaleSetUpdateNetworkConfiguration>());
                    for (VirtualMachineScaleSetNetworkConfiguration nicConfig : scaleSet.inner().virtualMachineProfile().networkProfile().networkInterfaceConfigurations()) {
                        VirtualMachineScaleSetUpdateNetworkConfiguration nicPatchConfig = new VirtualMachineScaleSetUpdateNetworkConfiguration();
                        nicPatchConfig.withDnsSettings(nicConfig.dnsSettings());
                        nicPatchConfig.withEnableAcceleratedNetworking(nicConfig.enableAcceleratedNetworking());
                        nicPatchConfig.withEnableIPForwarding(nicConfig.enableIPForwarding());
                        nicPatchConfig.withName(nicConfig.name());
                        nicPatchConfig.withNetworkSecurityGroup(nicConfig.networkSecurityGroup());
                        nicPatchConfig.withPrimary(nicConfig.primary());
                        nicPatchConfig.withId(nicConfig.id());
                        if (nicConfig.ipConfigurations() != null) {
                            nicPatchConfig.withIpConfigurations(new ArrayList<VirtualMachineScaleSetUpdateIPConfiguration>());
                            for (VirtualMachineScaleSetIPConfiguration ipConfig : nicConfig.ipConfigurations()) {
                                VirtualMachineScaleSetUpdateIPConfiguration patchIpConfig = new VirtualMachineScaleSetUpdateIPConfiguration();
                                patchIpConfig.withApplicationGatewayBackendAddressPools(ipConfig.applicationGatewayBackendAddressPools());
                                patchIpConfig.withLoadBalancerBackendAddressPools(ipConfig.loadBalancerBackendAddressPools());
                                patchIpConfig.withLoadBalancerInboundNatPools(ipConfig.loadBalancerInboundNatPools());
                                patchIpConfig.withName(ipConfig.name());
                                patchIpConfig.withPrimary(ipConfig.primary());
                                patchIpConfig.withPrivateIPAddressVersion(ipConfig.privateIPAddressVersion());
                                patchIpConfig.withSubnet(ipConfig.subnet());
                                patchIpConfig.withId(ipConfig.id());
                                if (ipConfig.publicIPAddressConfiguration() != null) {
                                    patchIpConfig.withPublicIPAddressConfiguration(new VirtualMachineScaleSetUpdatePublicIPAddressConfiguration());
                                    patchIpConfig.publicIPAddressConfiguration().withDnsSettings(ipConfig.publicIPAddressConfiguration().dnsSettings());
                                    patchIpConfig.publicIPAddressConfiguration().withIdleTimeoutInMinutes(ipConfig.publicIPAddressConfiguration().idleTimeoutInMinutes());
                                    patchIpConfig.publicIPAddressConfiguration().withName(ipConfig.publicIPAddressConfiguration().name());
                                }
                                if (ipConfig.applicationSecurityGroups() != null) {
                                    patchIpConfig.withApplicationSecurityGroups(new ArrayList<SubResource>());
                                    for (SubResource asg : ipConfig.applicationSecurityGroups()) {
                                        patchIpConfig.applicationSecurityGroups().add(new SubResource().withId(asg.id()));
                                    }
                                }
                                nicPatchConfig.ipConfigurations().add(patchIpConfig);
                            }
                        }
                        networkProfile.networkInterfaceConfigurations().add(nicPatchConfig);
                    }
                }
                updateVMProfile.withNetworkProfile(networkProfile);
                // -- --
            }
            updateParameter.withVirtualMachineProfile(updateVMProfile);
            // --
        }
        //
        return updateParameter;
    }
}
