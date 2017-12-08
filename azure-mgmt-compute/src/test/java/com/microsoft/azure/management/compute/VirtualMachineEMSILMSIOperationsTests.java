/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.compute.implementation.ComputeManager;
import com.microsoft.azure.management.graphrbac.BuiltInRole;
import com.microsoft.azure.management.graphrbac.RoleAssignment;
import com.microsoft.azure.management.msi.Identity;
import com.microsoft.azure.management.msi.implementation.MSIManager;
import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.network.implementation.NetworkManager;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.core.TestBase;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.implementation.ResourceManager;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class VirtualMachineEMSILMSIOperationsTests extends TestBase {
    private static String RG_NAME = "";
    private static Region region = Region.fromName("East US2 EUAP");
    private static final String VMNAME = "javavm";

    private ComputeManager computeManager;
    private MSIManager msiManager;
    private ResourceManager resourceManager;
    private NetworkManager networkManager;

    @Before
    public void beforeTest() throws IOException {
        super.setBaseUri("https:/brazilus.management.azure.com/");
        super.beforeTest();
    }

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) throws IOException {
        this.msiManager = MSIManager.authenticate(restClient, defaultSubscription);
        this.resourceManager = msiManager.resourceManager();
        this.computeManager = ComputeManager.authenticate(restClient, defaultSubscription);
        this.networkManager = NetworkManager.authenticate(restClient, defaultSubscription);
    }

    @Override
    protected void cleanUpResources() {
        this.resourceManager.resourceGroups().deleteByName(RG_NAME);
    }


    @Test
    @Ignore("Needs to run once the service is deployed to Canary")
    public void canCreateVirtualMachineWithEMSI() {
        RG_NAME = generateRandomResourceName("java-emsi-c-rg", 15);
        String identityName1 = generateRandomResourceName("msi-id", 15);
        String identityName2 = generateRandomResourceName("msi-id", 15);
        String networkName = generateRandomResourceName("nw", 10);

        // Prepare a definition for yet-to-be-created resource group
        //
        Creatable<ResourceGroup> creatableRG = resourceManager.resourceGroups()
                .define(RG_NAME)
                .withRegion(region);

        // Create a virtual network
        //
        Network network = networkManager.networks()
                .define(networkName)
                .withRegion(region)
                .withNewResourceGroup(creatableRG)
                .create();

        // Create an "External MSI" residing in the above RG and assign reader access to the virtual network
        //
        Identity createdIdentity = msiManager.identities()
                .define(identityName1)
                .withRegion(region)
                .withNewResourceGroup(creatableRG)
                .withAccessTo(network, BuiltInRole.READER)
                .create();

        // Prepare a definition for yet-to-be-created "External MSI" with contributor access to the resource group
        // it resides
        //
        Creatable<Identity> creatableIdentity = msiManager.identities()
                .define(identityName2)
                .withRegion(region)
                .withNewResourceGroup(creatableRG)
                .withAccessToCurrentResourceGroup(BuiltInRole.CONTRIBUTOR);


        // Create a virtual machine and associate it with existing and yet-t-be-created identities
        //
        VirtualMachine virtualMachine = computeManager.virtualMachines()
                .define(VMNAME)
                .withRegion(region)
                .withNewResourceGroup(RG_NAME)
                .withNewPrimaryNetwork("10.0.0.0/28")
                .withPrimaryPrivateIPAddressDynamic()
                .withoutPrimaryPublicIPAddress()
                .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                .withRootUsername("Foo12")
                .withRootPassword("abc!@#F0orL")
                .withExistingExternalManagedServiceIdentity(createdIdentity)
                .withNewExternalManagedServiceIdentity(creatableIdentity)
                .create();

        Assert.assertNotNull(virtualMachine);
        Assert.assertNotNull(virtualMachine.inner());
        Assert.assertTrue(virtualMachine.isManagedServiceIdentityEnabled());
        Assert.assertNull(virtualMachine.localManagedServiceIdentityPrincipalId()); // No Local MSI enabled
        Assert.assertNull(virtualMachine.localManagedServiceIdentityTenantId());    // No Local MSI enabled

        // Ensure the MSI extension is set
        //
        Map<String, VirtualMachineExtension> extensions = virtualMachine.listExtensions();
        VirtualMachineExtension msiExtension = null;
        for (VirtualMachineExtension extension : extensions.values()) {
            if (extension.publisherName().equalsIgnoreCase("Microsoft.ManagedIdentity")
                    && extension.typeName().equalsIgnoreCase("ManagedIdentityExtensionForLinux")) {
                msiExtension = extension;
                break;
            }
        }
        Assert.assertNotNull(msiExtension);

        // Ensure the "External MSI" id can be retrieved from the virtual machine
        //
        Set<String> emsiIds = virtualMachine.externalManagedServiceIdentityIds();
        Assert.assertNotNull(emsiIds);
        Assert.assertEquals(2, emsiIds.size());

        // Ensure the "External MSI"s matches with the those provided as part of VM create
        //
        Identity implicitlyCreatedIdentity = null;
        for (String emsiId : emsiIds) {
            Identity identity = msiManager.identities().getById(emsiId);
            Assert.assertNotNull(identity);
            Assert.assertTrue(identity.name().equalsIgnoreCase(identityName1)
                    || identity.name().equalsIgnoreCase(identityName2));
            Assert.assertNotNull(identity.principalId());

            if (identity.name().equalsIgnoreCase(identityName2)) {
                implicitlyCreatedIdentity = identity;
            }
        }
        Assert.assertNotNull(implicitlyCreatedIdentity);


        // Ensure expected role assignment exists for explicitly created EMSI
        //
        PagedList<RoleAssignment> roleAssignmentsForStorage = this.msiManager
                .graphRbacManager()
                .roleAssignments()
                .listByScope(network.id());
        boolean found = false;
        for (RoleAssignment roleAssignment : roleAssignmentsForStorage) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(createdIdentity.principalId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Expected role assignment not found for the virtual network for identity" + createdIdentity.name(), found);

        // Ensure expected role assignment exists for explicitly created EMSI
        //
        PagedList<RoleAssignment> roleAssignmentsForResourceGroup = this.msiManager
                .graphRbacManager()
                .roleAssignments()
                .listByScope(resourceManager.resourceGroups().getByName(virtualMachine.resourceGroupName()).id());
        found = false;
        for (RoleAssignment roleAssignment : roleAssignmentsForResourceGroup) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(implicitlyCreatedIdentity.principalId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Expected role assignment not found for the resource group for identity" + implicitlyCreatedIdentity.name(), found);
    }


    @Test
    @Ignore("Needs to run once the service is deployed to Canary")
    public void canCreateVirtualMachineWithLMSIAndEMSI() {
        RG_NAME = generateRandomResourceName("java-emsi-c-rg", 15);
        String identityName1 = generateRandomResourceName("msi-id", 15);
        String networkName = generateRandomResourceName("nw", 10);

        // Create a resource group
        //
        ResourceGroup resourceGroup = resourceManager.resourceGroups()
                .define(RG_NAME)
                .withRegion(region)
                .create();

        // Create a virtual network
        //
        Network network = networkManager.networks()
                .define(networkName)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroup)
                .create();

        // Prepare a definition for yet-to-be-created "External MSI" with contributor access to the resource group
        // it resides
        //
        Creatable<Identity> creatableIdentity = msiManager.identities()
                .define(identityName1)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroup)
                .withAccessToCurrentResourceGroup(BuiltInRole.CONTRIBUTOR);

        // Create a virtual machine and associate it with existing and yet-t-be-created identities
        //
        VirtualMachine virtualMachine = computeManager.virtualMachines()
                .define(VMNAME)
                .withRegion(region)
                .withNewResourceGroup(RG_NAME)
                .withNewPrimaryNetwork("10.0.0.0/28")
                .withPrimaryPrivateIPAddressDynamic()
                .withoutPrimaryPublicIPAddress()
                .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                .withRootUsername("Foo12")
                .withRootPassword("abc!@#F0orL")
                .withLocalManagedServiceIdentity()
                .withLocalIdentityBasedAccessTo(network.id(), BuiltInRole.CONTRIBUTOR)
                .withNewExternalManagedServiceIdentity(creatableIdentity)
                .create();

        Assert.assertNotNull(virtualMachine);
        Assert.assertNotNull(virtualMachine.inner());
        Assert.assertTrue(virtualMachine.isManagedServiceIdentityEnabled());
        Assert.assertNotNull(virtualMachine.localManagedServiceIdentityPrincipalId());
        Assert.assertNotNull(virtualMachine.localManagedServiceIdentityTenantId());

        // Ensure the MSI extension is set
        //
        Map<String, VirtualMachineExtension> extensions = virtualMachine.listExtensions();
        VirtualMachineExtension msiExtension = null;
        for (VirtualMachineExtension extension : extensions.values()) {
            if (extension.publisherName().equalsIgnoreCase("Microsoft.ManagedIdentity")
                    && extension.typeName().equalsIgnoreCase("ManagedIdentityExtensionForLinux")) {
                msiExtension = extension;
                break;
            }
        }
        Assert.assertNotNull(msiExtension);

        // Ensure the "External MSI" id can be retrieved from the virtual machine
        //
        Set<String> emsiIds = virtualMachine.externalManagedServiceIdentityIds();
        Assert.assertNotNull(emsiIds);
        Assert.assertEquals(1, emsiIds.size());

        Identity identity = msiManager.identities().getById(emsiIds.iterator().next());
        Assert.assertNotNull(identity);
        Assert.assertTrue(identity.name().equalsIgnoreCase(identityName1));

        // Ensure expected role assignment exists for LMSI
        //
        PagedList<RoleAssignment> roleAssignmentsForStorage = this.msiManager
                .graphRbacManager()
                .roleAssignments()
                .listByScope(network.id());
        boolean found = false;
        for (RoleAssignment roleAssignment : roleAssignmentsForStorage) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(virtualMachine.localManagedServiceIdentityPrincipalId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Expected role assignment not found for the virtual network for local identity" + virtualMachine.localManagedServiceIdentityPrincipalId(), found);

        // Ensure expected role assignment exists for EMSI
        //
        PagedList<RoleAssignment> roleAssignmentsForResourceGroup = this.msiManager
                .graphRbacManager()
                .roleAssignments()
                .listByScope(resourceManager.resourceGroups().getByName(virtualMachine.resourceGroupName()).id());
        found = false;
        for (RoleAssignment roleAssignment : roleAssignmentsForResourceGroup) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(identity.principalId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Expected role assignment not found for the resource group for identity" + identity.name(), found);
    }
}
