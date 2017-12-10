/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.graphrbac.BuiltInRole;
import com.microsoft.azure.management.graphrbac.RoleAssignment;
import com.microsoft.azure.management.graphrbac.ServicePrincipal;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.dag.TaskGroup;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;

import java.util.Map;

public class VirtualMachineManagedServiceIdentityOperationsTests extends ComputeManagementTest {
    private static String RG_NAME = "";
    private static final Region REGION = Region.US_SOUTH_CENTRAL;
    private static final String VMNAME = "javavm";

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        RG_NAME = generateRandomResourceName("javacsmrg", 15);
        super.initializeClients(restClient, defaultSubscription, domain);
    }

    @Override
    protected void cleanUpResources() {
        resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
    }

    @Test
    public void canSetMSIOnNewOrExistingVMWithoutRoleAssignment() throws Exception {
        // Create a virtual machine with just MSI enabled without role and scope.
        //
        VirtualMachine virtualMachine = computeManager.virtualMachines()
                .define(VMNAME)
                .withRegion(REGION)
                .withNewResourceGroup(RG_NAME)
                .withNewPrimaryNetwork("10.0.0.0/28")
                .withPrimaryPrivateIPAddressDynamic()
                .withoutPrimaryPublicIPAddress()
                .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                .withRootUsername("Foo12")
                .withRootPassword("abc!@#F0orL")
                .withSize(VirtualMachineSizeTypes.STANDARD_DS2_V2)
                .withOSDiskCaching(CachingTypes.READ_WRITE)
                .withSystemAssignedManagedServiceIdentity()
                .create();

        Assert.assertNotNull(virtualMachine);
        Assert.assertNotNull(virtualMachine.inner());
        Assert.assertTrue(virtualMachine.isManagedServiceIdentityEnabled());
        Assert.assertNotNull(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId());
        Assert.assertNotNull(virtualMachine.systemAssignedManagedServiceIdentityTenantId());

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
        // Check the default token port
        //
        Map<String, Object> publicSettings = msiExtension.publicSettings();
        Assert.assertNotNull(publicSettings);
        Assert.assertTrue(publicSettings.containsKey("port"));
        Object portObj = publicSettings.get("port");
        Assert.assertNotNull(portObj);
        int port = objectToInteger(portObj);
        Assert.assertEquals(50342, port);

        // Ensure NO role assigned for resource group
        //
        ResourceGroup resourceGroup = this.resourceManager.resourceGroups().getByName(virtualMachine.resourceGroupName());
        PagedList<RoleAssignment> rgRoleAssignments1 = rbacManager.roleAssignments().listByScope(resourceGroup.id());
        Assert.assertNotNull(rgRoleAssignments1);
        boolean found = false;
        for (RoleAssignment roleAssignment : rgRoleAssignments1) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId())) {
                found = true;
                break;
            }
        }
        Assert.assertFalse("Resource group should not have a role assignment with virtual machine MSI principal", found);

        virtualMachine = virtualMachine.update()
                .withSystemAssignedManagedServiceIdentity(50343)
                .apply();

        Assert.assertNotNull(virtualMachine);
        Assert.assertNotNull(virtualMachine.inner());
        Assert.assertTrue(virtualMachine.isManagedServiceIdentityEnabled());
        Assert.assertNotNull(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId());
        Assert.assertNotNull(virtualMachine.systemAssignedManagedServiceIdentityTenantId());

        extensions = virtualMachine.listExtensions();
        msiExtension = null;
        for (VirtualMachineExtension extension : extensions.values()) {
            if (extension.publisherName().equalsIgnoreCase("Microsoft.ManagedIdentity")
                    && extension.typeName().equalsIgnoreCase("ManagedIdentityExtensionForLinux")) {
                msiExtension = extension;
                break;
            }
        }
        Assert.assertNotNull(msiExtension);
        // Check the default token port
        //
        publicSettings = msiExtension.publicSettings();
        Assert.assertNotNull(publicSettings);
        Assert.assertTrue(publicSettings.containsKey("port"));
        portObj = publicSettings.get("port");
        Assert.assertNotNull(portObj);
        port = objectToInteger(portObj);
        Assert.assertEquals(50343, port);

        // Ensure NO role assigned for resource group
        //
        rgRoleAssignments1 = rbacManager.roleAssignments().listByScope(resourceGroup.id());
        Assert.assertNotNull(rgRoleAssignments1);
        found = false;
        for (RoleAssignment roleAssignment : rgRoleAssignments1) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId())) {
                found = true;
                break;
            }
        }
        Assert.assertFalse("Resource group should not have a role assignment with virtual machine MSI principal", found);
    }

    @Test
    public void canSetMSIOnNewVMWithRoleAssignedToCurrentResourceGroup() throws Exception {
        Observable<Indexable> resources = computeManager.virtualMachines()
                .define(VMNAME)
                .withRegion(REGION)
                .withNewResourceGroup(RG_NAME)
                .withNewPrimaryNetwork("10.0.0.0/28")
                .withPrimaryPrivateIPAddressDynamic()
                .withoutPrimaryPublicIPAddress()
                .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                .withRootUsername("Foo12")
                .withRootPassword("abc!@#F0orL")
                .withSize(VirtualMachineSizeTypes.STANDARD_DS2_V2)
                .withOSDiskCaching(CachingTypes.READ_WRITE)
                .withSystemAssignedManagedServiceIdentity()
                .withSystemAssignedIdentityBasedAccessToCurrentResourceGroup(BuiltInRole.CONTRIBUTOR)
                .createAsync();

        final VirtualMachine[] virtualMachines = new VirtualMachine[1];
        final RoleAssignment[] roleAssignments = new RoleAssignment[1];

        resources
                .toBlocking()
                .subscribe(new Action1<Indexable>() {
                    @Override
                    public void call(Indexable indexable) {
                        if (indexable instanceof VirtualMachine) {
                            virtualMachines[0] = (VirtualMachine) indexable;
                        }
                        if (indexable instanceof RoleAssignment) {
                            roleAssignments[0] = (RoleAssignment) indexable;
                        }
                    }
                });

        Assert.assertNotNull(virtualMachines[0]);
        Assert.assertNotNull(roleAssignments[0]);

        final VirtualMachine virtualMachine = virtualMachines[0];

        Assert.assertNotNull(virtualMachine);
        Assert.assertNotNull(virtualMachine.inner());
        Assert.assertTrue(virtualMachine.isManagedServiceIdentityEnabled());
        Assert.assertNotNull(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId());
        Assert.assertNotNull(virtualMachine.systemAssignedManagedServiceIdentityTenantId());

        // Validate service created service principal
        //
        ServicePrincipal servicePrincipal = rbacManager
                .servicePrincipals()
                .getById(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId());

        Assert.assertNotNull(servicePrincipal);
        Assert.assertNotNull(servicePrincipal.inner());

        // Ensure the MSI extension is set
        //
        Map<String, VirtualMachineExtension> extensions = virtualMachine.listExtensions();
        boolean extensionFound = false;
        for (VirtualMachineExtension extension : extensions.values()) {
            if (extension.publisherName().equalsIgnoreCase("Microsoft.ManagedIdentity")
                    && extension.typeName().equalsIgnoreCase("ManagedIdentityExtensionForLinux")) {
                extensionFound = true;
                break;
            }
        }
        Assert.assertTrue(extensionFound);

        // Ensure role assigned
        //
        ResourceGroup resourceGroup = this.resourceManager.resourceGroups().getByName(virtualMachine.resourceGroupName());
        PagedList<RoleAssignment> rgRoleAssignments = rbacManager.roleAssignments().listByScope(resourceGroup.id());
        boolean found = false;
        for (RoleAssignment rgRoleAssignment : rgRoleAssignments) {
            if (rgRoleAssignment.principalId() != null && rgRoleAssignment.principalId().equalsIgnoreCase(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId())) {
                found = true;
                break;
            }
        }

        Assert.assertTrue("Resource group should have a role assignment with virtual machine MSI principal", found);

        // Below we tests internal functionality to ensure a call for RoleAssignment is not happening.
        // NOT a pattern applications/customer should use
        //
        RoleAssignment savedRoleAssignment = roleAssignments[0];
        roleAssignments[0] = null;

        TaskGroup.HasTaskGroup hasTaskGroup = (TaskGroup.HasTaskGroup) virtualMachine;
        Assert.assertNotNull(hasTaskGroup);
        TaskGroup vmTaskGroup = hasTaskGroup.taskGroup();
        vmTaskGroup.invokeAsync(vmTaskGroup.newInvocationContext())
                .toBlocking()
                .subscribe(new Action1<Indexable>() {
                    @Override
                    public void call(Indexable indexable) {
                        if (indexable instanceof RoleAssignment) {
                            roleAssignments[0] = (RoleAssignment) indexable;
                        }
                    }
                });

        Assert.assertNotNull(roleAssignments[0]);
        Assert.assertTrue((roleAssignments[0]).key().equalsIgnoreCase(savedRoleAssignment.key()));
    }

    @Test
    public void canSetMSIOnNewVMWithMultipleRoleAssignments() throws Exception {
        String storageAccountName = generateRandomResourceName("javacsrg", 15);

        StorageAccount storageAccount = storageManager.storageAccounts()
                .define(storageAccountName)
                .withRegion(Region.US_EAST2)
                .withNewResourceGroup(RG_NAME)
                .create();

        ResourceGroup resourceGroup = this.resourceManager.resourceGroups().getByName(storageAccount.resourceGroupName());

        VirtualMachine virtualMachine = computeManager.virtualMachines()
                .define(VMNAME)
                .withRegion(REGION)
                .withExistingResourceGroup(RG_NAME)
                .withNewPrimaryNetwork("10.0.0.0/28")
                .withPrimaryPrivateIPAddressDynamic()
                .withoutPrimaryPublicIPAddress()
                .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                .withRootUsername("Foo12")
                .withRootPassword("abc!@#F0orL")
                .withSize(VirtualMachineSizeTypes.STANDARD_DS2_V2)
                .withOSDiskCaching(CachingTypes.READ_WRITE)
                .withSystemAssignedManagedServiceIdentity()
                .withSystemAssignedIdentityBasedAccessTo(resourceGroup.id(), BuiltInRole.CONTRIBUTOR)
                .withSystemAssignedIdentityBasedAccessTo(storageAccount.id(), BuiltInRole.CONTRIBUTOR)
                .create();

        // Validate service created service principal
        //
        ServicePrincipal servicePrincipal = rbacManager
                .servicePrincipals()
                .getById(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId());

        Assert.assertNotNull(servicePrincipal);
        Assert.assertNotNull(servicePrincipal.inner());

        // Ensure the MSI extension is set
        //
        Map<String, VirtualMachineExtension> extensions = virtualMachine.listExtensions();
        boolean extensionFound = false;
        for (VirtualMachineExtension extension : extensions.values()) {
            if (extension.publisherName().equalsIgnoreCase("Microsoft.ManagedIdentity")
                    && extension.typeName().equalsIgnoreCase("ManagedIdentityExtensionForLinux")) {
                extensionFound = true;
                break;
            }
        }
        Assert.assertTrue(extensionFound);

        // Ensure role assigned for resource group
        //
        PagedList<RoleAssignment> rgRoleAssignments = rbacManager.roleAssignments().listByScope(resourceGroup.id());
        Assert.assertNotNull(rgRoleAssignments);
        boolean found = false;
        for (RoleAssignment roleAssignment : rgRoleAssignments) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Resource group should have a role assignment with virtual machine MSI principal", found);

        // Ensure role assigned for storage account
        //
        PagedList<RoleAssignment> stgRoleAssignments = rbacManager.roleAssignments().listByScope(storageAccount.id());
        Assert.assertNotNull(stgRoleAssignments);
        found = false;
        for (RoleAssignment roleAssignment : stgRoleAssignments) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Storage account should have a role assignment with virtual machine MSI principal", found);
    }

    @Test
    public void canSetMSIOnExistingVMWithRoleAssignments() throws Exception  {
        VirtualMachine virtualMachine = computeManager.virtualMachines()
                .define(VMNAME)
                .withRegion(REGION)
                .withNewResourceGroup(RG_NAME)
                .withNewPrimaryNetwork("10.0.0.0/28")
                .withPrimaryPrivateIPAddressDynamic()
                .withoutPrimaryPublicIPAddress()
                .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                .withRootUsername("Foo12")
                .withRootPassword("abc!@#F0orL")
                .withSize(VirtualMachineSizeTypes.STANDARD_DS2_V2)
                .withOSDiskCaching(CachingTypes.READ_WRITE)
                .withSystemAssignedManagedServiceIdentity()
                .create();

        Assert.assertNotNull(virtualMachine);
        Assert.assertNotNull(virtualMachine.inner());
        Assert.assertTrue(virtualMachine.isManagedServiceIdentityEnabled());
        Assert.assertNotNull(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId());
        Assert.assertNotNull(virtualMachine.systemAssignedManagedServiceIdentityTenantId());

        Assert.assertNotNull(virtualMachine.managedServiceIdentityType());
        Assert.assertTrue(virtualMachine.managedServiceIdentityType().equals(ResourceIdentityType.SYSTEM_ASSIGNED));

        // Ensure the MSI extension is set
        //
        Map<String, VirtualMachineExtension> extensions = virtualMachine.listExtensions();
        boolean extensionFound = false;
        for (VirtualMachineExtension extension : extensions.values()) {
            if (extension.publisherName().equalsIgnoreCase("Microsoft.ManagedIdentity")
                    && extension.typeName().equalsIgnoreCase("ManagedIdentityExtensionForLinux")) {
                extensionFound = true;
                break;
            }
        }
        Assert.assertTrue(extensionFound);

        // Ensure NO role assigned for resource group
        //
        ResourceGroup resourceGroup = this.resourceManager.resourceGroups().getByName(virtualMachine.resourceGroupName());
        PagedList<RoleAssignment> rgRoleAssignments1 = rbacManager.roleAssignments().listByScope(resourceGroup.id());
        Assert.assertNotNull(rgRoleAssignments1);
        boolean found = false;
        for (RoleAssignment roleAssignment : rgRoleAssignments1) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId())) {
                found = true;
                break;
            }
        }
        Assert.assertFalse("Resource group should not have a role assignment with virtual machine MSI principal", found);

        virtualMachine.update()
                .withSystemAssignedManagedServiceIdentity()
                .withSystemAssignedIdentityBasedAccessToCurrentResourceGroup(BuiltInRole.CONTRIBUTOR)
                .apply();

        // Ensure role assigned for resource group
        //
        PagedList<RoleAssignment> roleAssignments2 = rbacManager.roleAssignments().listByScope(resourceGroup.id());
        Assert.assertNotNull(roleAssignments2);
        for (RoleAssignment roleAssignment : roleAssignments2) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(virtualMachine.systemAssignedManagedServiceIdentityPrincipalId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Resource group should have a role assignment with virtual machine MSI principal", found);
    }

    private static Integer objectToInteger(Object obj) {
        Integer result = null;
        if (obj != null) {
            if (obj instanceof Integer) {
                result = (Integer) obj;
            } else {
                result = Integer.valueOf((String) obj);
            }
        }
        return result;
    }
}