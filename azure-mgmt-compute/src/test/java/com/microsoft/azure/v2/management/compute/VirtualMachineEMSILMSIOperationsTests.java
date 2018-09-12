/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.compute;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.v2.management.compute.implementation.ComputeManager;
import com.microsoft.azure.v2.management.graphrbac.BuiltInRole;
import com.microsoft.azure.v2.management.graphrbac.RoleAssignment;
import com.microsoft.azure.v2.management.msi.Identity;
import com.microsoft.azure.v2.management.msi.implementation.MSIManager;
import com.microsoft.azure.v2.management.network.Network;
import com.microsoft.azure.v2.management.network.implementation.NetworkManager;
import com.microsoft.azure.v2.management.resources.ResourceGroup;
import com.microsoft.azure.v2.management.resources.core.TestBase;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.v2.management.resources.implementation.ResourceManager;
import com.microsoft.rest.v2.http.HttpPipeline;
import io.reactivex.Observable;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

public class VirtualMachineEMSILMSIOperationsTests extends TestBase {
    private static String RG_NAME = "";
    private static Region region = Region.fromName("West Central US");
    private static final String VMNAME = "javavm";

    private ComputeManager computeManager;
    private MSIManager msiManager;
    private ResourceManager resourceManager;
    private NetworkManager networkManager;

    @Override
    protected void initializeClients(HttpPipeline httpPipeline, String defaultSubscription, String domain) {

        this.msiManager = MSIManager.authenticate(httpPipeline, defaultSubscription, domain);
        this.resourceManager = msiManager.resourceManager();
        this.computeManager = ComputeManager.authenticate(httpPipeline, defaultSubscription, domain);
        this.networkManager = NetworkManager.authenticate(httpPipeline, defaultSubscription);
    }

    @Override
    protected void cleanUpResources() {
        this.resourceManager.resourceGroups().deleteByName(RG_NAME);
    }

    @Test
    public void canCreateUpdateVirtualMachineWithEMSI() {
        // this.resourceManager.resourceGroups().beginDeleteByName("41522c6e938c4f6");

        RG_NAME = generateRandomResourceName("java-emsi-c-rg", 15);
        String identityName1 = generateRandomResourceName("msi-id", 15);
        String identityName2 = generateRandomResourceName("msi-id", 15);
        String networkName = generateRandomResourceName("nw", 10);

        // Prepare a definition for yet-to-be-created resource group
        //
        Creatable<ResourceGroup> creatableRG = resourceManager.resourceGroups()
                .define(RG_NAME)
                .withRegion(region);

        // Create a virtual network residing in the above RG
        //
        final Network network = networkManager.networks()
                .define(networkName)
                .withRegion(region)
                .withNewResourceGroup(creatableRG)
                .create();

        // Create an "User Assigned (External) MSI" residing in the above RG and assign reader access to the virtual network
        //
        final Identity createdIdentity = msiManager.identities()
                .define(identityName1)
                .withRegion(region)
                .withNewResourceGroup(creatableRG)
                .withAccessTo(network, BuiltInRole.READER)
                .create();

        // Prepare a definition for yet-to-be-created "User Assigned (External) MSI" with contributor access to the resource group
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
                .withExistingUserAssignedManagedServiceIdentity(createdIdentity)
                .withNewUserAssignedManagedServiceIdentity(creatableIdentity)
                .create();

        Assert.assertNotNull(virtualMachine);
        Assert.assertNotNull(virtualMachine.inner());
        Assert.assertTrue(virtualMachine.isManagedServiceIdentityEnabled());
        Assert.assertNull(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId()); // No Local MSI enabled
        Assert.assertNull(virtualMachine.systemAssignedManagedServiceIdentityTenantId());    // No Local MSI enabled

        // Ensure the "User Assigned (External) MSI" id can be retrieved from the virtual machine
        //
        Set<String> emsiIds = virtualMachine.userAssignedManagedServiceIdentityIds();
        Assert.assertNotNull(emsiIds);
        Assert.assertEquals(2, emsiIds.size());

        // Ensure the "User Assigned (External) MSI"s matches with the those provided as part of VM create
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
        PagedList<RoleAssignment> roleAssignmentsForNetwork = this.msiManager
                .graphRbacManager()
                .roleAssignments()
                .listByScope(network.id());
        boolean found = false;
        for (RoleAssignment roleAssignment : roleAssignmentsForNetwork) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(createdIdentity.principalId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Expected role assignment not found for the virtual network for identity" + createdIdentity.name(), found);

        RoleAssignment assignment = lookupRoleAssignmentUsingScopeAndRoleAsync(network.id(), BuiltInRole.READER, createdIdentity.principalId())
                .blockingLast(null);

        Assert.assertNotNull("Expected role assignment with ROLE not found for the virtual network for identity", assignment);


        // Ensure expected role assignment exists for explicitly created EMSI
        //
        ResourceGroup resourceGroup = resourceManager.resourceGroups().getByName(virtualMachine.resourceGroupName());
        Assert.assertNotNull(resourceGroup);

        PagedList<RoleAssignment> roleAssignmentsForResourceGroup = this.msiManager
                .graphRbacManager()
                .roleAssignments()
                .listByScope(resourceGroup.id());
        found = false;
        for (RoleAssignment roleAssignment : roleAssignmentsForResourceGroup) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(implicitlyCreatedIdentity.principalId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Expected role assignment not found for the resource group for identity" + implicitlyCreatedIdentity.name(), found);

        assignment = lookupRoleAssignmentUsingScopeAndRoleAsync(resourceGroup.id(), BuiltInRole.CONTRIBUTOR, implicitlyCreatedIdentity.principalId())
                .blockingLast(null);

        Assert.assertNotNull("Expected role assignment with ROLE not found for the resource group for identity", assignment);

        emsiIds = virtualMachine.userAssignedManagedServiceIdentityIds();
        Iterator<String> itr = emsiIds.iterator();
        // Remove both (all) identities
        virtualMachine.update()
                .withoutUserAssignedManagedServiceIdentity(itr.next())
                .withoutUserAssignedManagedServiceIdentity(itr.next())
                .apply();
        //
        Assert.assertEquals(0, virtualMachine.userAssignedManagedServiceIdentityIds().size());
        if (virtualMachine.managedServiceIdentityType() != null) {
            Assert.assertTrue(virtualMachine.managedServiceIdentityType().equals(ResourceIdentityType.NONE));
        }
        // fetch vm again and validate
        virtualMachine.refresh();
        //
        Assert.assertEquals(0, virtualMachine.userAssignedManagedServiceIdentityIds().size());
        if (virtualMachine.managedServiceIdentityType() != null) {
            Assert.assertTrue(virtualMachine.managedServiceIdentityType().equals(ResourceIdentityType.NONE));
        }
        //
        itr = emsiIds.iterator();
        Identity identity1 = msiManager.identities().getById(itr.next());
        Identity identity2 = msiManager.identities().getById(itr.next());
        //
        // Update VM by enabling System-MSI and add two identities
        virtualMachine.update()
                .withSystemAssignedManagedServiceIdentity()
                .withExistingUserAssignedManagedServiceIdentity(identity1)
                .withExistingUserAssignedManagedServiceIdentity(identity2)
                .apply();

        Assert.assertNotNull(virtualMachine.userAssignedManagedServiceIdentityIds());
        Assert.assertEquals(2, virtualMachine.userAssignedManagedServiceIdentityIds().size());
        Assert.assertNotNull(virtualMachine.managedServiceIdentityType());
        Assert.assertTrue(virtualMachine.managedServiceIdentityType().equals(ResourceIdentityType.SYSTEM_ASSIGNED__USER_ASSIGNED));
        //
        Assert.assertNotNull(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId());
        Assert.assertNotNull(virtualMachine.systemAssignedManagedServiceIdentityTenantId());
        //
        virtualMachine.refresh();
        Assert.assertNotNull(virtualMachine.userAssignedManagedServiceIdentityIds());
        Assert.assertEquals(2, virtualMachine.userAssignedManagedServiceIdentityIds().size());
        Assert.assertNotNull(virtualMachine.managedServiceIdentityType());
        Assert.assertTrue(virtualMachine.managedServiceIdentityType().equals(ResourceIdentityType.SYSTEM_ASSIGNED__USER_ASSIGNED));
        //
        Assert.assertNotNull(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId());
        Assert.assertNotNull(virtualMachine.systemAssignedManagedServiceIdentityTenantId());
        //
        itr = emsiIds.iterator();
        // Remove identities one by one (first one)
        virtualMachine.update()
                .withoutUserAssignedManagedServiceIdentity(itr.next())
                .apply();
        //
        Assert.assertNotNull(virtualMachine.userAssignedManagedServiceIdentityIds());
        Assert.assertEquals(1, virtualMachine.userAssignedManagedServiceIdentityIds().size());
        Assert.assertNotNull(virtualMachine.managedServiceIdentityType());
        Assert.assertTrue(virtualMachine.managedServiceIdentityType().equals(ResourceIdentityType.SYSTEM_ASSIGNED__USER_ASSIGNED));
        Assert.assertNotNull(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId());
        Assert.assertNotNull(virtualMachine.systemAssignedManagedServiceIdentityTenantId());
        // Remove identities one by one (second one)
        virtualMachine.update()
                .withoutUserAssignedManagedServiceIdentity(itr.next())
                .apply();
        //
        Assert.assertEquals(0, virtualMachine.userAssignedManagedServiceIdentityIds().size());
        Assert.assertNotNull(virtualMachine.managedServiceIdentityType());
        Assert.assertTrue(virtualMachine.managedServiceIdentityType().equals(ResourceIdentityType.SYSTEM_ASSIGNED));
        //
    }

    @Test
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

        // Prepare a definition for yet-to-be-created "User Assigned (External) MSI" with contributor access to the resource group
        // it resides
        //
        Creatable<Identity> creatableIdentity = msiManager.identities()
                .define(identityName1)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroup)
                .withAccessToCurrentResourceGroup(BuiltInRole.CONTRIBUTOR);

        // Create a virtual machine and associate it with existing and yet-to-be-created identities
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
                .withSystemAssignedManagedServiceIdentity()
                .withSystemAssignedIdentityBasedAccessTo(network.id(), BuiltInRole.CONTRIBUTOR)
                .withNewUserAssignedManagedServiceIdentity(creatableIdentity)
                .create();

        Assert.assertNotNull(virtualMachine);
        Assert.assertNotNull(virtualMachine.inner());
        Assert.assertTrue(virtualMachine.isManagedServiceIdentityEnabled());
        Assert.assertNotNull(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId());
        Assert.assertNotNull(virtualMachine.systemAssignedManagedServiceIdentityTenantId());

        // Ensure the "User Assigned (External) MSI" id can be retrieved from the virtual machine
        //
        Set<String> emsiIds = virtualMachine.userAssignedManagedServiceIdentityIds();
        Assert.assertNotNull(emsiIds);
        Assert.assertEquals(1, emsiIds.size());

        Identity identity = msiManager.identities().getById(emsiIds.iterator().next());
        Assert.assertNotNull(identity);
        Assert.assertTrue(identity.name().equalsIgnoreCase(identityName1));

        // Ensure expected role assignment exists for LMSI
        //
        PagedList<RoleAssignment> roleAssignmentsForNetwork = this.msiManager
                .graphRbacManager()
                .roleAssignments()
                .listByScope(network.id());

        boolean found = false;
        for (RoleAssignment roleAssignment : roleAssignmentsForNetwork) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Expected role assignment not found for the virtual network for local identity" + virtualMachine.systemAssignedManagedServiceIdentityPrincipalId(), found);

        RoleAssignment assignment = lookupRoleAssignmentUsingScopeAndRoleAsync(network.id(), BuiltInRole.CONTRIBUTOR, virtualMachine.systemAssignedManagedServiceIdentityPrincipalId())
                .blockingLast(null);

        Assert.assertNotNull("Expected role assignment with ROLE not found for the virtual network for system assigned identity", assignment);

        // Ensure expected role assignment exists for EMSI
        //
        ResourceGroup resourceGroup1 = resourceManager.resourceGroups().getByName(virtualMachine.resourceGroupName());

        PagedList<RoleAssignment> roleAssignmentsForResourceGroup = this.msiManager
                .graphRbacManager()
                .roleAssignments()
                .listByScope(resourceGroup1.id());
        found = false;
        for (RoleAssignment roleAssignment : roleAssignmentsForResourceGroup) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(identity.principalId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Expected role assignment not found for the resource group for identity" + identity.name(), found);

        assignment = lookupRoleAssignmentUsingScopeAndRoleAsync(resourceGroup1.id(), BuiltInRole.CONTRIBUTOR, identity.principalId())
                .blockingLast(null);

        Assert.assertNotNull("Expected role assignment with ROLE not found for the resource group for system assigned identity", assignment);
    }

    @Test
    public void canUpdateVirtualMachineWithEMSIAndLMSI() throws Exception {
        RG_NAME = generateRandomResourceName("java-emsi-c-rg", 15);
        String identityName1 = generateRandomResourceName("msi-id-1", 15);
        String identityName2 = generateRandomResourceName("msi-id-2", 15);

        // Create a virtual machine with no EMSI & LMSI
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
                .create();

        // Prepare a definition for yet-to-be-created "User Assigned (External) MSI" with contributor access to the resource group
        // it resides
        //
        Creatable<Identity> creatableIdentity = msiManager.identities()
                .define(identityName1)
                .withRegion(region)
                .withExistingResourceGroup(virtualMachine.resourceGroupName())
                .withAccessToCurrentResourceGroup(BuiltInRole.CONTRIBUTOR);

        // Update virtual machine so that it depends on the EMSI
        //
        virtualMachine = virtualMachine.update()
                .withNewUserAssignedManagedServiceIdentity(creatableIdentity)
                .apply();

        // Ensure the "User Assigned (External) MSI" id can be retrieved from the virtual machine
        //
        Set<String> emsiIds = virtualMachine.userAssignedManagedServiceIdentityIds();
        Assert.assertNotNull(emsiIds);
        Assert.assertEquals(1, emsiIds.size());

        Identity identity = msiManager.identities().getById(emsiIds.iterator().next());
        Assert.assertNotNull(identity);
        Assert.assertTrue(identity.name().equalsIgnoreCase(identityName1));

        // Creates an EMSI
        //
        Identity createdIdentity = msiManager.identities()
                .define(identityName2)
                .withRegion(region)
                .withExistingResourceGroup(virtualMachine.resourceGroupName())
                .withAccessToCurrentResourceGroup(BuiltInRole.CONTRIBUTOR)
                .create();

        // Update the virtual machine by removing the an EMSI and adding existing EMSI
        //
        virtualMachine = virtualMachine.update()
                .withoutUserAssignedManagedServiceIdentity(identity.id())
                .withExistingUserAssignedManagedServiceIdentity(createdIdentity)
                .apply();

        // Ensure the "User Assigned (External) MSI" id can be retrieved from the virtual machine
        //
        emsiIds = virtualMachine.userAssignedManagedServiceIdentityIds();
        Assert.assertNotNull(emsiIds);
        Assert.assertEquals(1, emsiIds.size());

        identity = msiManager.identities().getById(emsiIds.iterator().next());
        Assert.assertNotNull(identity);
        Assert.assertTrue(identity.name().equalsIgnoreCase(identityName2));

        // Update the virtual machine by enabling "LMSI"

        virtualMachine
                .update()
                .withSystemAssignedManagedServiceIdentity()
                .apply();
        //
        Assert.assertNotNull(virtualMachine);
        Assert.assertNotNull(virtualMachine.inner());
        Assert.assertTrue(virtualMachine.isManagedServiceIdentityEnabled());
        Assert.assertNotNull(virtualMachine.managedServiceIdentityType());
        Assert.assertTrue(virtualMachine.managedServiceIdentityType().equals(ResourceIdentityType.SYSTEM_ASSIGNED__USER_ASSIGNED));
        Assert.assertNotNull(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId());
        Assert.assertNotNull(virtualMachine.systemAssignedManagedServiceIdentityTenantId());
        Assert.assertEquals(1, virtualMachine.userAssignedManagedServiceIdentityIds().size());
        //
        virtualMachine
                .update()
                .withoutSystemAssignedManagedServiceIdentity()
                .apply();

        Assert.assertTrue(virtualMachine.isManagedServiceIdentityEnabled());
        Assert.assertNotNull(virtualMachine.managedServiceIdentityType());
        Assert.assertTrue(virtualMachine.managedServiceIdentityType().equals(ResourceIdentityType.USER_ASSIGNED));
        Assert.assertNull(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId());
        Assert.assertNull(virtualMachine.systemAssignedManagedServiceIdentityTenantId());
        Assert.assertEquals(1, virtualMachine.userAssignedManagedServiceIdentityIds().size());
        //
        virtualMachine
                .update()
                .withoutUserAssignedManagedServiceIdentity(identity.id())
                .apply();
        Assert.assertFalse(virtualMachine.isManagedServiceIdentityEnabled());
        if (virtualMachine.managedServiceIdentityType() != null) {
            Assert.assertTrue(virtualMachine.managedServiceIdentityType().equals(ResourceIdentityType.NONE));
        }
        Assert.assertNull(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId());
        Assert.assertNull(virtualMachine.systemAssignedManagedServiceIdentityTenantId());
        Assert.assertEquals(0, virtualMachine.userAssignedManagedServiceIdentityIds().size());
    }

    private Observable<RoleAssignment> lookupRoleAssignmentUsingScopeAndRoleAsync(final String scope, BuiltInRole role, final String principalId) {
        return this.msiManager.graphRbacManager()
                .roleDefinitions()
                .getByScopeAndRoleNameAsync(scope, role.toString())
                .flatMap(roleDefinition -> {
                    return msiManager.graphRbacManager()
                            .roleAssignments()
                            .listByScopeAsync(scope)
                            .filter(roleAssignment -> {
                                if (roleDefinition != null && roleAssignment != null) {
                                    return roleAssignment.roleDefinitionId().equalsIgnoreCase(roleDefinition.id()) && roleAssignment.principalId().equalsIgnoreCase(principalId);
                                } else {
                                    return false;
                                }
                            });
                });
    }
}
