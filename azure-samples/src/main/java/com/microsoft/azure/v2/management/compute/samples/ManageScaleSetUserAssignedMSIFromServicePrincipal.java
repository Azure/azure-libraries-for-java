/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.compute.samples;

import com.microsoft.azure.v2.AzureEnvironment;
import com.microsoft.azure.v2.CloudException;
import com.microsoft.azure.v2.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.v2.management.Azure;
import com.microsoft.azure.v2.management.compute.KnownLinuxVirtualMachineImage;
import com.microsoft.azure.v2.management.compute.VirtualMachineScaleSet;
import com.microsoft.azure.v2.management.compute.VirtualMachineScaleSetSkuTypes;
import com.microsoft.azure.v2.management.graphrbac.BuiltInRole;
import com.microsoft.azure.v2.management.graphrbac.ServicePrincipal;
import com.microsoft.azure.v2.management.msi.Identity;
import com.microsoft.azure.v2.management.network.Network;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.Utils;
import com.microsoft.rest.v2.policy.HttpLogDetailLevel;
import com.microsoft.rest.v2.policy.HttpLoggingPolicyFactory;

import java.io.File;

/**
 * Azure Compute sample for assigning service identity to virtual machine scale set using newly created service principal
 *  - Create a VM scale-set
 *  - Create a managed service identity #1 and create a service principal. Configure the service principal to have 2 permissions, to update the scale set and assign the managed service identity #1 to the scale set
 *  - Create a managed service identity #2
 *  - Login using created service principle and verify it can assign/remove identity #1, but not #2
 */
public final class ManageScaleSetUserAssignedMSIFromServicePrincipal {
    /**
     * Main function which runs the actual sample.
     *
     * @param authenticated instance of Authenticated
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure.Authenticated authenticated) {
        Region region = Region.US_WEST_CENTRAL;
        String vmssName = SdkContext.randomResourceName("vmss", 15);
        String spName1 = SdkContext.randomResourceName("sp1", 21);
        String rgName = SdkContext.randomResourceName("rg", 22);
        String identityName1 = SdkContext.randomResourceName("msi-id1", 15);
        String identityName2 = SdkContext.randomResourceName("msi-id1", 15);
        ServicePrincipal servicePrincipal = null;
        String subscription = "0b1f6471-1bf0-4dda-aec3-cb9272f09590";

        final String userName = "tirekicker";
        final String password = "12NewPA23w0rd!";

        Azure azure = null;

        try {
            azure = authenticated.withDefaultSubscription();

            System.out.println("Creating network for virtual machine scale sets");

            // ============================================================
            // Create Virtual Machine Scale Set
            Network network = azure.networks()
                    .define("vmssvnet")
                    .withRegion(region)
                    .withNewResourceGroup(rgName)
                    .withAddressSpace("10.0.0.0/28")
                    .withSubnet("subnet1", "10.0.0.0/28")
                    .create();

            VirtualMachineScaleSet virtualMachineScaleSet1 = azure.virtualMachineScaleSets()
                    .define(vmssName)
                    .withRegion(region)
                    .withExistingResourceGroup(rgName)
                    .withSku(VirtualMachineScaleSetSkuTypes.STANDARD_D3_V2)
                    .withExistingPrimaryNetworkSubnet(network, "subnet1")
                    .withoutPrimaryInternetFacingLoadBalancer()
                    .withoutPrimaryInternalLoadBalancer()
                    .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                    .withRootUsername(userName)
                    .withRootPassword(password)
                    .create();

            // ============================================================
            // Create a managed service identity #1 and create a service principal. Configure the service principal to have 2 permissions, to update the scale set and assign the managed service identity #1 to the scale set
            servicePrincipal = authenticated.servicePrincipals().define(spName1)
                    .withNewApplication("http://lenala.azure.com/ansp/" + spName1)
                    .definePasswordCredential("sppass")
                    .withPasswordValue("StrongPass!12")
                    .attach()
                    .withNewRole(BuiltInRole.CONTRIBUTOR, Utils.resourceGroupId(virtualMachineScaleSet1.id()))
                    .create();

            Identity identity1 = azure.identities().define(identityName1)
                    .withRegion(region)
                    .withExistingResourceGroup(rgName)
                    .create();

            servicePrincipal.update()
                    .withNewRole(BuiltInRole.MANAGED_IDENTITY_OPERATOR, identity1.id())
                    .apply();

            // ============================================================
            // Create a managed service identity #2
            Identity identity2 = azure.identities().define(identityName2)
                    .withRegion(region)
                    .withNewResourceGroup(rgName + "2")
                    .create();

            // ============================================================
            // Login using created service principle and verify it can assign/remove identity #1, but not #2
            ApplicationTokenCredentials credentials = new ApplicationTokenCredentials(servicePrincipal.applicationId(), servicePrincipal.manager().tenantId(), "StrongPass!12",  AzureEnvironment.AZURE);

            Azure azure2 = Azure.configure()
                    .withRequestPolicy(new HttpLoggingPolicyFactory(HttpLogDetailLevel.BODY_AND_HEADERS))
                    .authenticate(credentials)
                    .withSubscription(subscription);

            VirtualMachineScaleSet vmss = azure2.virtualMachineScaleSets().getById(virtualMachineScaleSet1.id());

            vmss.update()
                    .withExistingUserAssignedManagedServiceIdentity(identity1)
                    .apply();

            // verify that cannot assign user identity #2 as service principal does not have permissions
            try {
                vmss.update()
                        .withExistingUserAssignedManagedServiceIdentity(identity2)
                        .apply();
                throw new RuntimeException("Should not be able to assign identity #2 as service principal does not have permissions");
            } catch (CloudException ex) {
                ex.printStackTrace();
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            azure.resourceGroups().beginDeleteByName(rgName);
            try {
                authenticated.servicePrincipals().deleteById(servicePrincipal.id());
            } catch (Exception e) { }
            try {
                authenticated.activeDirectoryApplications().deleteById(authenticated.activeDirectoryApplications().getByName(servicePrincipal.applicationId()).id());
            } catch (Exception e) { }
        }
        return false;
    }

    /**
     * Main entry point.
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {
            //=============================================================
            // Authenticate

            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));

            Azure.Authenticated authenticated = Azure.configure()
                    .withRequestPolicy(new HttpLoggingPolicyFactory(HttpLogDetailLevel.BODY_AND_HEADERS))
                    .authenticate(credFile);

            runSample(authenticated);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private ManageScaleSetUserAssignedMSIFromServicePrincipal() {
    }

}