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
import com.microsoft.azure.management.graphrbac.RoleDefinition;
import com.microsoft.azure.management.msi.Identity;
import com.microsoft.azure.management.msi.implementation.MSIManager;
import com.microsoft.azure.management.network.LoadBalancer;
import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.network.TransportProtocol;
import com.microsoft.azure.management.network.implementation.NetworkManager;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.core.TestBase;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.implementation.ResourceManager;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class VirtualMachineScaleSetEMSILMSIOperationsTests  extends TestBase {
    private static String RG_NAME = "";
    private static Region region = Region.fromName("West Central US");
    private static final String VMSSNAME = "javavmss";

    private ComputeManager computeManager;
    private MSIManager msiManager;
    private ResourceManager resourceManager;
    private NetworkManager networkManager;

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
    public void canCreateVirtualMachineScaleSetWithEMSI() throws Exception {
        RG_NAME = generateRandomResourceName("java-ems-c-rg", 15);
        String identityName1 = generateRandomResourceName("msi-id", 15);
        String identityName2 = generateRandomResourceName("msi-id", 15);
        String networkName = generateRandomResourceName("nw", 10);

        ResourceGroup resourceGroup = this.resourceManager.resourceGroups()
                .define(RG_NAME)
                .withRegion(region)
                .create();

        // Create a virtual network to which we will assign "EMSI" with reader access
        //
        Network network = networkManager.networks()
                .define(networkName)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroup)
                .create();

        // Create an "User Assigned (External) MSI" residing in the above RG and assign reader access to the virtual network
        //
        Identity createdIdentity = msiManager.identities()
                .define(identityName1)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroup)
                .withAccessTo(network, BuiltInRole.READER)
                .create();

        // Prepare a definition for yet-to-be-created "User Assigned (External) MSI" with contributor access to the resource group
        // it resides
        //
        Creatable<Identity> creatableIdentity = msiManager.identities()
                .define(identityName2)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroup)
                .withAccessToCurrentResourceGroup(BuiltInRole.CONTRIBUTOR);

        // Create a virtual network for VMSS
        //
        Network vmssNetwork = this.networkManager
                .networks()
                .define("vmssvnet")
                .withRegion(region)
                .withExistingResourceGroup(resourceGroup)
                .withAddressSpace("10.0.0.0/28")
                .withSubnet("subnet1", "10.0.0.0/28")
                .create();

        // Create a Load balancer for VMSS
        //
        LoadBalancer vmssInternalLoadBalancer = createInternalLoadBalancer(region,
                resourceGroup,
                vmssNetwork,
                "1");

        VirtualMachineScaleSet virtualMachineScaleSet = this.computeManager.virtualMachineScaleSets()
                .define(VMSSNAME)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroup)
                .withSku(VirtualMachineScaleSetSkuTypes.STANDARD_A0)
                .withExistingPrimaryNetworkSubnet(vmssNetwork, "subnet1")
                .withoutPrimaryInternetFacingLoadBalancer()
                .withExistingPrimaryInternalLoadBalancer(vmssInternalLoadBalancer)
                .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                .withRootUsername("jvuser")
                .withRootPassword("123OData!@#123")
                .withExistingUserAssignedManagedServiceIdentity(createdIdentity)
                .withNewUserAssignedManagedServiceIdentity(creatableIdentity)
                .create();

        Assert.assertNotNull(virtualMachineScaleSet);
        Assert.assertNotNull(virtualMachineScaleSet.inner());
        Assert.assertTrue(virtualMachineScaleSet.isManagedServiceIdentityEnabled());
        Assert.assertNull(virtualMachineScaleSet.systemAssignedManagedServiceIdentityPrincipalId()); // No Local MSI enabled
        Assert.assertNull(virtualMachineScaleSet.systemAssignedManagedServiceIdentityTenantId());    // No Local MSI enabled

        // Ensure the MSI extension is set
        //
        Map<String, VirtualMachineScaleSetExtension> extensions = virtualMachineScaleSet.extensions();
        VirtualMachineScaleSetExtension msiExtension = null;
        for (VirtualMachineScaleSetExtension extension : extensions.values()) {
            if (extension.publisherName().equalsIgnoreCase("Microsoft.ManagedIdentity")
                    && extension.typeName().equalsIgnoreCase("ManagedIdentityExtensionForLinux")) {
                msiExtension = extension;
                break;
            }
        }
        Assert.assertNotNull(msiExtension);

        // Ensure the "User Assigned (External) MSI" id can be retrieved from the virtual machine scale set
        //
        Set<String> emsiIds = virtualMachineScaleSet.userAssignedManagedServiceIdentityIds();
        Assert.assertNotNull(emsiIds);
        Assert.assertEquals(2, emsiIds.size());

        // Ensure the "User Assigned (External) MSI"s matches with the those provided as part of VMSS create
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
                .toBlocking()
                .last();

        Assert.assertNotNull("Expected role assignment with ROLE not found for the virtual network for identity", assignment);

        // Ensure expected role assignment exists for explicitly created EMSI
        //
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
                .toBlocking()
                .last();

        Assert.assertNotNull("Expected role assignment with ROLE not found for the resource group for identity", assignment);
    }



    @Test
    public void canCreateVirtualMachineScaleSetWithLMSIAndEMSI() throws Exception {
        RG_NAME = generateRandomResourceName("java-emsi-c-rg", 15);
        String identityName1 = generateRandomResourceName("msi-id", 15);
        String networkName = generateRandomResourceName("nw", 10);

        // Create a resource group
        //
        ResourceGroup resourceGroup = resourceManager.resourceGroups()
                .define(RG_NAME)
                .withRegion(region)
                .create();

        // Create a virtual network to which we will assign "EMSI" with reader access
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

        // Create a virtual network for VMSS
        //
        Network vmssNetwork = this.networkManager
                .networks()
                .define("vmssvnet")
                .withRegion(region)
                .withExistingResourceGroup(resourceGroup)
                .withAddressSpace("10.0.0.0/28")
                .withSubnet("subnet1", "10.0.0.0/28")
                .create();

        // Create a Load balancer for VMSS
        //
        LoadBalancer vmssInternalLoadBalancer = createInternalLoadBalancer(region,
                resourceGroup,
                vmssNetwork,
                "1");

        VirtualMachineScaleSet virtualMachineScaleSet = this.computeManager.virtualMachineScaleSets()
                .define(VMSSNAME)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroup)
                .withSku(VirtualMachineScaleSetSkuTypes.STANDARD_A0)
                .withExistingPrimaryNetworkSubnet(vmssNetwork, "subnet1")
                .withoutPrimaryInternetFacingLoadBalancer()
                .withExistingPrimaryInternalLoadBalancer(vmssInternalLoadBalancer)
                .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                .withRootUsername("jvuser")
                .withRootPassword("123OData!@#123")
                .withSystemAssignedManagedServiceIdentity()
                .withSystemAssignedIdentityBasedAccessTo(network.id(), BuiltInRole.CONTRIBUTOR)
                .withNewUserAssignedManagedServiceIdentity(creatableIdentity)
                .create();

        Assert.assertNotNull(virtualMachineScaleSet);
        Assert.assertNotNull(virtualMachineScaleSet.inner());
        Assert.assertTrue(virtualMachineScaleSet.isManagedServiceIdentityEnabled());
        Assert.assertNotNull(virtualMachineScaleSet.systemAssignedManagedServiceIdentityPrincipalId());
        Assert.assertNotNull(virtualMachineScaleSet.systemAssignedManagedServiceIdentityTenantId());

        // Ensure the MSI extension is set
        //
        Map<String, VirtualMachineScaleSetExtension> extensions = virtualMachineScaleSet.extensions();
        VirtualMachineScaleSetExtension msiExtension = null;
        for (VirtualMachineScaleSetExtension extension : extensions.values()) {
            if (extension.publisherName().equalsIgnoreCase("Microsoft.ManagedIdentity")
                    && extension.typeName().equalsIgnoreCase("ManagedIdentityExtensionForLinux")) {
                msiExtension = extension;
                break;
            }
        }
        Assert.assertNotNull(msiExtension);

        // Ensure the "User Assigned (External) MSI" id can be retrieved from the virtual machine
        //
        Set<String> emsiIds = virtualMachineScaleSet.userAssignedManagedServiceIdentityIds();
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
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(virtualMachineScaleSet.systemAssignedManagedServiceIdentityPrincipalId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Expected role assignment not found for the virtual network for local identity" + virtualMachineScaleSet.systemAssignedManagedServiceIdentityPrincipalId(), found);

        RoleAssignment assignment = lookupRoleAssignmentUsingScopeAndRoleAsync(network.id(), BuiltInRole.CONTRIBUTOR, virtualMachineScaleSet.systemAssignedManagedServiceIdentityPrincipalId())
                .toBlocking()
                .last();

        Assert.assertNotNull("Expected role assignment with ROLE not found for the virtual network for system assigned identity", assignment);

        // Ensure expected role assignment exists for EMSI
        //
        PagedList<RoleAssignment> roleAssignmentsForResourceGroup = this.msiManager
                .graphRbacManager()
                .roleAssignments()
                .listByScope(resourceManager.resourceGroups().getByName(virtualMachineScaleSet.resourceGroupName()).id());
        found = false;
        for (RoleAssignment roleAssignment : roleAssignmentsForResourceGroup) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(identity.principalId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Expected role assignment not found for the resource group for identity" + identity.name(), found);

        assignment = lookupRoleAssignmentUsingScopeAndRoleAsync(resourceGroup.id(), BuiltInRole.CONTRIBUTOR, identity.principalId())
                .toBlocking()
                .last();

        Assert.assertNotNull("Expected role assignment with ROLE not found for the resource group for system assigned identity", assignment);
    }

    @Test
    public void canUpdateVirtualMachineScaleSetWithEMSIAndLMSI() throws Exception {
        RG_NAME = generateRandomResourceName("java-emsi-c-rg", 15);
        String identityName1 = generateRandomResourceName("msi-id-1", 15);
        String identityName2 = generateRandomResourceName("msi-id-2", 15);

        // Create a resource group
        //
        ResourceGroup resourceGroup = resourceManager.resourceGroups()
                .define(RG_NAME)
                .withRegion(region)
                .create();

        // Create a virtual network for VMSS
        //
        Network vmssNetwork = this.networkManager
                .networks()
                .define("vmssvnet")
                .withRegion(region)
                .withExistingResourceGroup(resourceGroup)
                .withAddressSpace("10.0.0.0/28")
                .withSubnet("subnet1", "10.0.0.0/28")
                .create();

        // Create a Load balancer for VMSS
        //
        LoadBalancer vmssInternalLoadBalancer = createInternalLoadBalancer(region,
                resourceGroup,
                vmssNetwork,
                "1");

        VirtualMachineScaleSet virtualMachineScaleSet = this.computeManager.virtualMachineScaleSets()
                .define(VMSSNAME)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroup)
                .withSku(VirtualMachineScaleSetSkuTypes.STANDARD_A0)
                .withExistingPrimaryNetworkSubnet(vmssNetwork, "subnet1")
                .withoutPrimaryInternetFacingLoadBalancer()
                .withExistingPrimaryInternalLoadBalancer(vmssInternalLoadBalancer)
                .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                .withRootUsername("jvuser")
                .withRootPassword("123OData!@#123")
                .create();

        // Prepare a definition for yet-to-be-created "User Assigned (External) MSI" with contributor access to the resource group
        // it resides
        //
        Creatable<Identity> creatableIdentity = msiManager.identities()
                .define(identityName1)
                .withRegion(region)
                .withExistingResourceGroup(virtualMachineScaleSet.resourceGroupName())
                .withAccessToCurrentResourceGroup(BuiltInRole.CONTRIBUTOR);

        // Update virtual machine so that it depends on the EMSI
        //
        virtualMachineScaleSet = virtualMachineScaleSet.update()
                .withNewUserAssignedManagedServiceIdentity(creatableIdentity)
                .apply();

        // Ensure the MSI extension is set
        //
        Map<String, VirtualMachineScaleSetExtension> extensions = virtualMachineScaleSet.extensions();
        VirtualMachineScaleSetExtension msiExtension = null;
        for (VirtualMachineScaleSetExtension extension : extensions.values()) {
            if (extension.publisherName().equalsIgnoreCase("Microsoft.ManagedIdentity")
                    && extension.typeName().equalsIgnoreCase("ManagedIdentityExtensionForLinux")) {
                msiExtension = extension;
                break;
            }
        }
        Assert.assertNotNull(msiExtension);

        Assert.assertNotNull("Expected MSI extension not found in the virtual machine ", msiExtension);

        // Ensure the "User Assigned (External) MSI" id can be retrieved from the virtual machine
        //
        Set<String> emsiIds = virtualMachineScaleSet.userAssignedManagedServiceIdentityIds();
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
                .withExistingResourceGroup(virtualMachineScaleSet.resourceGroupName())
                .withAccessToCurrentResourceGroup(BuiltInRole.CONTRIBUTOR)
                .create();

        // Update the virtual machine by removing the an EMSI and adding existing EMSI
        //
        virtualMachineScaleSet = virtualMachineScaleSet.update()
                .withoutUserAssignedManagedServiceIdentity(identity.id())
                .withExistingUserAssignedManagedServiceIdentity(createdIdentity)
                .apply();

        // Ensure the "User Assigned (External) MSI" id can be retrieved from the virtual machine
        //
        emsiIds = virtualMachineScaleSet.userAssignedManagedServiceIdentityIds();
        Assert.assertNotNull(emsiIds);
        Assert.assertEquals(1, emsiIds.size());

        identity = msiManager.identities().getById(emsiIds.iterator().next());
        Assert.assertNotNull(identity);
        Assert.assertTrue(identity.name().equalsIgnoreCase(identityName2));

        // Update the virtual machine by enabling "LMSI"

        virtualMachineScaleSet
                .update()
                .withSystemAssignedManagedServiceIdentity()
                .apply();

        Assert.assertNotNull(virtualMachineScaleSet);
        Assert.assertNotNull(virtualMachineScaleSet.inner());
        Assert.assertTrue(virtualMachineScaleSet.isManagedServiceIdentityEnabled());
        Assert.assertNotNull(virtualMachineScaleSet.systemAssignedManagedServiceIdentityPrincipalId());
        Assert.assertNotNull(virtualMachineScaleSet.systemAssignedManagedServiceIdentityTenantId());
    }

    private LoadBalancer createInternalLoadBalancer(Region region, ResourceGroup resourceGroup,
                                                      Network network, String id) throws Exception {
        final String loadBalancerName = generateRandomResourceName("InternalLb" + id + "-", 18);
        final String privateFrontEndName = loadBalancerName + "-FE1";
        final String backendPoolName1 = loadBalancerName + "-BAP1";
        final String backendPoolName2 = loadBalancerName + "-BAP2";
        final String natPoolName1 = loadBalancerName + "-INP1";
        final String natPoolName2 = loadBalancerName + "-INP2";
        final String subnetName = "subnet1";

        LoadBalancer loadBalancer = this.networkManager.loadBalancers().define(loadBalancerName)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroup)
                // Add two rules that uses above backend and probe
                .defineLoadBalancingRule("httpRule")
                .withProtocol(TransportProtocol.TCP)
                .fromFrontend(privateFrontEndName)
                .fromFrontendPort(1000)
                .toBackend(backendPoolName1)
                .withProbe("httpProbe")
                .attach()
                .defineLoadBalancingRule("httpsRule")
                .withProtocol(TransportProtocol.TCP)
                .fromFrontend(privateFrontEndName)
                .fromFrontendPort(1001)
                .toBackend(backendPoolName2)
                .withProbe("httpsProbe")
                .attach()

                // Add two NAT pools to enable direct VM connectivity to port 44 and 45
                .defineInboundNatPool(natPoolName1)
                .withProtocol(TransportProtocol.TCP)
                .fromFrontend(privateFrontEndName)
                .fromFrontendPortRange(8000, 8099)
                .toBackendPort(44)
                .attach()
                .defineInboundNatPool(natPoolName2)
                .withProtocol(TransportProtocol.TCP)
                .fromFrontend(privateFrontEndName)
                .fromFrontendPortRange(9000, 9099)
                .toBackendPort(45)
                .attach()

                // Explicitly define the frontend
                .definePrivateFrontend(privateFrontEndName)
                .withExistingSubnet(network, subnetName) // Frontend with VNET means internal load-balancer
                .attach()

                // Add two probes one per rule
                .defineHttpProbe("httpProbe")
                .withRequestPath("/")
                .attach()
                .defineHttpProbe("httpsProbe")
                .withRequestPath("/")
                .attach()

                .create();
        return loadBalancer;
    }

    private Observable<RoleAssignment> lookupRoleAssignmentUsingScopeAndRoleAsync(final String scope, BuiltInRole role, final String principalId) {
        return this.msiManager.graphRbacManager()
                .roleDefinitions()
                .getByScopeAndRoleNameAsync(scope, role.toString())
                .flatMap(new Func1<RoleDefinition, Observable<RoleAssignment>>() {
                    @Override
                    public Observable<RoleAssignment> call(final RoleDefinition roleDefinition) {
                        return msiManager.graphRbacManager()
                                .roleAssignments()
                                .listByScopeAsync(scope)
                                .filter(new Func1<RoleAssignment, Boolean>() {
                                    @Override
                                    public Boolean call(RoleAssignment roleAssignment) {
                                        if (roleDefinition != null && roleAssignment != null) {
                                            return roleAssignment.roleDefinitionId().equalsIgnoreCase(roleDefinition.id()) && roleAssignment.principalId().equalsIgnoreCase(principalId);
                                        } else {
                                            return false;
                                        }
                                    }
                                });
                    }
                })
                .switchIfEmpty(Observable.<RoleAssignment>just(null));
    }
}
