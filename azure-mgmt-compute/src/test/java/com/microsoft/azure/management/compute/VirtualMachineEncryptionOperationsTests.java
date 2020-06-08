/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute;

import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.keyvault.Vault;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class VirtualMachineEncryptionOperationsTests extends ComputeManagementTest {
    private static String RG_NAME = "";
    private static Region REGION = Region.US_EAST;
    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        RG_NAME = generateRandomResourceName("vmencryptst", 18);
        super.initializeClients(restClient, defaultSubscription, domain);
    }

    @Override
    protected void cleanUpResources() {
        resourceManager.resourceGroups().deleteByName(RG_NAME);
    }

    @Test
    public void canEncryptVirtualMachine() throws IOException {
        String rgName2 = generateRandomResourceName("rgencryptst", 18);
        String vaultName = generateRandomResourceName("vlt", 18);
        String vmName = generateRandomResourceName("vm", 18);
        String pipName = generateRandomResourceName("pip", 18);

        final String explicitlyCreatedEmptyDiskName1 = generateRandomResourceName(vmName + "_mdisk_", 25);
        final String explicitlyCreatedEmptyDiskName2 = generateRandomResourceName(vmName + "_mdisk_", 25);
        final String explicitlyCreatedEmptyDiskName3 = generateRandomResourceName(vmName + "_mdisk_", 25);
        ApplicationTokenCredentials credentials = ApplicationTokenCredentials.fromFile(new File(System.getenv("AZURE_AUTH_LOCATION")));

        try {
            resourceManager.resourceGroups().define(RG_NAME)
                    .withRegion(REGION)
                    .create();

            resourceManager.resourceGroups().define(rgName2)
                    .withRegion(REGION)
                    .create();

            Creatable<Disk> creatableEmptyDisk1 = computeManager.disks()
                    .define(explicitlyCreatedEmptyDiskName1)
                    .withRegion(REGION)
                    .withExistingResourceGroup(RG_NAME)
                    .withData()
                    .withSizeInGB(150);

            Creatable<Disk> creatableEmptyDisk2 = computeManager.disks()
                    .define(explicitlyCreatedEmptyDiskName2)
                    .withRegion(REGION)
                    .withExistingResourceGroup(RG_NAME)
                    .withData()
                    .withSizeInGB(150);

            Creatable<Disk> creatableEmptyDisk3 = computeManager.disks()
                    .define(explicitlyCreatedEmptyDiskName3)
                    .withRegion(REGION)
                    .withExistingResourceGroup(RG_NAME)
                    .withData()
                    .withSizeInGB(150);

            Vault vault = keyVaultManager.vaults().define(vaultName)
                    .withRegion(REGION)
                    .withExistingResourceGroup(rgName2)
                    .defineAccessPolicy()
                        .forServicePrincipal(credentials.clientId())
                        .allowCertificateAllPermissions()
                        .allowKeyAllPermissions()
                        .allowSecretAllPermissions()
                        .attach()
                    .withDiskEncryptionEnabled()
                    .create();

            VirtualMachine virtualMachine = computeManager.virtualMachines().define(vmName)
                    .withRegion(REGION)
                    .withExistingResourceGroup(RG_NAME)
                    .withNewPrimaryNetwork("10.0.0.0/24")
                    .withPrimaryPrivateIPAddressDynamic()
                    .withNewPrimaryPublicIPAddress(pipName)
                    .withPopularWindowsImage(KnownWindowsVirtualMachineImage.WINDOWS_SERVER_2008_R2_SP1)
                    .withAdminUsername("Foo12")
                    .withAdminPassword("abc!@#F0orL")
                    .withNewDataDisk(100, 1, CachingTypes.READ_ONLY)                  // CreateOption: EMPTY
                    .withNewDataDisk(creatableEmptyDisk1)                             // CreateOption: ATTACH
                    .withNewDataDisk(creatableEmptyDisk2, 2, CachingTypes.NONE)       // CreateOption: ATTACH
                    .withNewDataDisk(creatableEmptyDisk3, 3, CachingTypes.READ_WRITE)       // CreateOption: ATTACH
                    .withSize(VirtualMachineSizeTypes.STANDARD_D2_V3)
                    .withOSDiskCaching(CachingTypes.READ_WRITE)
                    .create();

            DiskVolumeEncryptionMonitor monitor = virtualMachine.diskEncryption().getMonitor();
            Assert.assertEquals(EncryptionStatus.NOT_ENCRYPTED, monitor.dataDiskStatus());
            Assert.assertEquals(EncryptionStatus.NOT_ENCRYPTED, monitor.osDiskStatus());

            WindowsVMDiskEncryptionConfiguration config = new WindowsVMDiskEncryptionConfiguration(vault.id());
            virtualMachine.diskEncryption().enable(config);

            monitor = virtualMachine.diskEncryption().getMonitor();
            Assert.assertEquals(EncryptionStatus.NOT_ENCRYPTED, monitor.dataDiskStatus());
            Assert.assertEquals(EncryptionStatus.ENCRYPTED, monitor.osDiskStatus());
        } catch (Exception ex) {

        } finally {
            resourceManager.resourceGroups().deleteByName(rgName2);
        }
    }
}
