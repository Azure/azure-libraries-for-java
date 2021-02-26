/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute.implementation;

import com.microsoft.azure.management.compute.CachingTypes;
import com.microsoft.azure.management.compute.ComputeManagementTest;
import com.microsoft.azure.management.compute.KnownLinuxVirtualMachineImage;
import com.microsoft.azure.management.compute.ResourceIdentityType;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.compute.VirtualMachineExtension;
import com.microsoft.azure.management.compute.VirtualMachineSizeTypes;
import com.microsoft.azure.management.graphrbac.BuiltInRole;
import com.microsoft.azure.management.resources.core.TestUtilities;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VirtualMachineUpdateTests extends ComputeManagementTest {

    private static String rgName;

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        rgName = generateRandomResourceName("vmupdatetest", 18);
        super.initializeClients(restClient, defaultSubscription, domain);
    }

    @Override
    protected void cleanUpResources() {
        resourceManager.resourceGroups().beginDeleteByName(rgName);
    }

    @Test
    public void testVirtualMachineUpdate() {
        final String vmname = "javavm1";

        final String mySqlInstallScript = "https://raw.githubusercontent.com/Azure/azure-quickstart-templates/4397e808d07df60ff3cdfd1ae40999f0130eb1b3/mysql-standalone-server-ubuntu/scripts/install_mysql_server_5.6.sh";
        final String installCommand = "bash install_mysql_server_5.6.sh Abc.123x(";
        List<String> fileUris = new ArrayList<>();
        fileUris.add(mySqlInstallScript);

        VirtualMachine vm = computeManager.virtualMachines()
                .define(vmname)
                .withRegion(Region.US_EAST)
                .withNewResourceGroup(rgName)
                .withNewPrimaryNetwork("10.0.0.0/28")
                .withPrimaryPrivateIPAddressDynamic()
                .withoutPrimaryPublicIPAddress()
                .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_14_04_LTS)
                .withRootUsername("Foo12")
                .withSsh(TestUtilities.createSshPublicKey())
                .withSize(VirtualMachineSizeTypes.fromString("Standard_D2a_v4"))
                .create();

        VirtualMachine.Update vmUpdate = vm.update();
        Assert.assertFalse(this.isVirtualMachineModifiedDuringUpdate(vm));

        vmUpdate = vmUpdate.defineNewExtension("CustomScriptForLinux")
                .withPublisher("Microsoft.OSTCExtensions")
                .withType("CustomScriptForLinux")
                .withVersion("1.4")
                .withMinorVersionAutoUpgrade()
                .withPublicSetting("fileUris", fileUris)
                .withPublicSetting("commandToExecute", installCommand)
                .attach();
        // extension added, but VM not modified
        Assert.assertFalse(this.isVirtualMachineModifiedDuringUpdate(vm));

        vmUpdate = vmUpdate.withOSDiskCaching(CachingTypes.READ_ONLY);
        Assert.assertTrue(this.isVirtualMachineModifiedDuringUpdate(vm));

        vmUpdate = vmUpdate.withOSDiskCaching(CachingTypes.READ_WRITE);
        Assert.assertFalse(this.isVirtualMachineModifiedDuringUpdate(vm));

        vm = vmUpdate.apply();

        Map<String, VirtualMachineExtension> extensions = vm.listExtensions();
        Assert.assertNotNull(extensions);
        Assert.assertFalse(extensions.isEmpty());
        VirtualMachineExtension customScriptExtension = extensions.get("CustomScriptForLinux");
        Assert.assertNotNull(customScriptExtension);

        vm.update()
                .withSystemAssignedManagedServiceIdentity()
                .withSystemAssignedIdentityBasedAccessToCurrentResourceGroup(BuiltInRole.CONTRIBUTOR)
                .apply();
        Assert.assertTrue(this.isVirtualMachineModifiedDuringUpdate(vm));

        Assert.assertEquals(ResourceIdentityType.SYSTEM_ASSIGNED, vm.managedServiceIdentityType());
        Assert.assertNotNull(vm.systemAssignedManagedServiceIdentityPrincipalId());
    }

    private boolean isVirtualMachineModifiedDuringUpdate(VirtualMachine vm) {
        VirtualMachineImpl vmImpl = (VirtualMachineImpl) vm;
        // this parameter is not correct for managed identities
        return vmImpl.isVirtualMachineModifiedDuringUpdate(vmImpl.deepCopyInnerToUpdateParameter());
    }
}
