/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute.samples;

import com.microsoft.azure.CloudException;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.compute.AvailabilitySet;
import com.microsoft.azure.management.compute.KnownLinuxVirtualMachineImage;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.compute.VirtualMachineSizeTypes;
import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.network.NetworkInterface;
import com.microsoft.azure.management.network.PublicIPAddress;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.rest.LogLevel;

import rx.Completable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Making use of the reactive pattern in a complex virtual machine creation scenario
 *
 * This sample shows how the reactive pattern (RXJava's Observables) supported by the Azure Libraries for Java in their asynchronous API can be
 * used for handling some potentially more complex real world scenarios with relative ease, involving parallel, distributed computation in the cloud.
 * The specific example here shows how Observables can be used to enable real time tracking of the creation of many virtual machines in parallel and
 * all their related resources. Since Azure does not support transactional creation of virtual machines (no automatic rollback in case of failure),
 * this could be useful for example for the purposes of deleting "orphaned" resources if the creation of some other resources fails.
 * Or to simply show real-time progress in some UI to the end user.
 *
 * The sample goes through the following steps:
 *
 * 1. Define a number of virtual machines and their related required resources (such as virtual networks, etc)
 * 2. Start the parallel creation of those virtual machine but with observers set up that will keep track of the created resources and report them on the console
 * 3. Clean up those successfully created resources whose virtual machines for some reason failed to be created
 */
public final class CreateVirtualMachinesAsyncTrackingRelatedResources {

    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {

        final int desiredVMCount = 15;
        final Region region = Region.US_EAST;
        final String resourceGroupName = SdkContext.randomResourceName("rg", 15);

        try {
            // Create one resource group for everything for easier cleanup later
            System.out.println(String.format("Creating the resource group (`%s`)...", resourceGroupName));
            ResourceGroup resourceGroup = azure.resourceGroups().define(resourceGroupName)
                    .withRegion(region)
                    .create();
            System.out.println("Resource group created.");

            // =====================================================================
            // Define a number of virtual machines and their related resources
            //

            // Needed for tracking related resources
            final Map<String, Collection<Creatable<? extends Resource>>> vmNonNicResourceDefinitions = new HashMap<>();
            final Map<String, Creatable<NetworkInterface>> nicDefinitions = new HashMap<>(); // Tracking NICs separately because they have to be deleted first
            final Map<String, Creatable<VirtualMachine>> vmDefinitions = new HashMap<>();
            final Map<String, String> createdResourceIds = new HashMap<>();
            final List<Throwable> errors = new ArrayList<>();

            for (int i = 0; i < desiredVMCount; i++) {
                Collection<Creatable<? extends Resource>> relatedDefinitions = new ArrayList<>();

                // Define a network for each VM
                String networkName = SdkContext.randomResourceName("net", 14);
                Creatable<Network> networkDefinition = azure.networks().define(networkName)
                        .withRegion(region)
                        .withExistingResourceGroup(resourceGroup)
                        .withAddressSpace("10.0." + i + ".0/29");
                relatedDefinitions.add(networkDefinition);

                // Define a PIP for each VM
                String pipName = SdkContext.randomResourceName("pip", 14);
                PublicIPAddress.DefinitionStages.WithCreate pipDefinition = azure.publicIPAddresses().define(pipName)
                        .withRegion(region)
                        .withExistingResourceGroup(resourceGroup);
                relatedDefinitions.add(pipDefinition);

                // Define a NIC for each VM
                String nicName = SdkContext.randomResourceName("nic", 14);
                Creatable<NetworkInterface> nicDefinition = azure.networkInterfaces().define(nicName)
                        .withRegion(region)
                        .withExistingResourceGroup(resourceGroup)
                        .withNewPrimaryNetwork(networkDefinition)
                        .withPrimaryPrivateIPAddressDynamic()
                        .withNewPrimaryPublicIPAddress(pipDefinition);

                // Define an availability set for each VM
                String availabilitySetName = SdkContext.randomResourceName("as", 14);
                Creatable<AvailabilitySet> availabilitySetDefinition = azure.availabilitySets().define(availabilitySetName)
                        .withRegion(region)
                        .withExistingResourceGroup(resourceGroup);
                relatedDefinitions.add(availabilitySetDefinition);

                String vmName = SdkContext.randomResourceName("vm", 14);

                // Define a VM
                String userName;
                if (i == desiredVMCount / 2) {
                    // Intentionally cause a failure in one of the VMs to test the sample's rollback implementation
                    userName = "";
                } else {
                    userName = "tester";
                }

                Creatable<VirtualMachine> vmDefinition = azure.virtualMachines().define(vmName)
                        .withRegion(region)
                        .withExistingResourceGroup(resourceGroup)
                        .withNewPrimaryNetworkInterface(nicDefinition)
                        .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                        .withRootUsername(userName)
                        .withRootPassword("Abcdef.123456!")
                        .withSize(VirtualMachineSizeTypes.STANDARD_DS1_V2)
                        .withNewAvailabilitySet(availabilitySetDefinition);

                // Keep track of all the related resource definitions based on the VM definition
                vmNonNicResourceDefinitions.put(vmDefinition.key(), relatedDefinitions);
                nicDefinitions.put(vmDefinition.key(), nicDefinition);
                vmDefinitions.put(vmDefinition.key(), vmDefinition);
            }

            // =====================================================================
            // Start the parallel creation of everything asynchronously
            //
            System.out.println("Creating the virtual machines and related required resources in parallel...");
            azure.virtualMachines().createAsync(new ArrayList<>(vmDefinitions.values()))

                // The Observable returned by createAsync() emits a resource as soon as that resource is successfully created, so
                // this is where the RX map() operator can be used to handle each such emitted resource accordingly.
                //
                // Resources will be created across multiple threads and the order in which they will be emitted is unpredictable.
                // Since the SDK and the RX framework handle the multi-threading and parallelization under the hood, our code below
                // does not need to worry about it, just handle each resource as it is emitted.
                //
                // Thanks to how RX works, we can also assume our callback below will be always called on the same thread, so we do
                // not need to worry about making the collections it uses thread-safe.
                .map(new Func1<Indexable, Indexable>() {
                    @Override
                    public Indexable call(Indexable createdResource) {

                        // Since the resources are of different types, each resources is emitted as the Indexable base type
                        // so it needs to be cast and handled depending on its type
                        if (createdResource instanceof Resource) {
                            Resource resource = (Resource) createdResource;

                            // Report the creation of a resource in the UI
                            System.out.println(String.format("\tCreated: %s '%s'",
                                    ResourceUtils.resourceTypeFromResourceId(resource.id()),
                                    ResourceUtils.nameFromResourceId(resource.id())));

                            if (resource instanceof VirtualMachine) {
                                // Track the successful creation of virtual machines, so that their related resources do not cleaned up later
                                VirtualMachine virtualMachine = (VirtualMachine) resource;

                                // Record that this VM was created successfully
                                vmDefinitions.remove(virtualMachine.key());

                                // Remove the associated resources from cleanup list
                                vmNonNicResourceDefinitions.remove(virtualMachine.key());

                                // Remove the associated NIC from cleanup list
                                nicDefinitions.remove(virtualMachine.key());
                            } else {
                                // Since this is not a VM, add this resource to the potential cleanup list
                                createdResourceIds.put(resource.key(), resource.id());
                            }
                        }
                        return createdResource;
                    }
               })
               .onErrorReturn(new Func1<Throwable, Indexable>() {
                   // Aggregate all the errors
                   @Override
                   public Indexable call(Throwable throwable) {
                       errors.add(throwable);
                       return null;
                   }
                })

               // Start the process and wait for the Observable to stop emitting
               .toBlocking().last();

               System.out.println("Creation completed.");

            // =====================================================================
            // Clean up orphaned resources
            //

            // After everything has been created, first we delete the remaining successfully created NICs of failed VM creations
            Collection<String> nicIdsToDelete = new ArrayList<>();
            for (Creatable<NetworkInterface> nicDefinition : nicDefinitions.values()) {
                String nicId = createdResourceIds.get(nicDefinition.key());
                if (nicId != null) {
                    nicIdsToDelete.add(nicId);
                }
            }
            if (!nicIdsToDelete.isEmpty()) {
                // Delete the NICs in parallel for better performance
                azure.networkInterfaces().deleteByIds(nicIdsToDelete);
            }

            // Delete remaining successfully created resources of failed VM creations in parallel
            Collection<Completable> deleteObservables = new ArrayList<>();
            for (Collection<Creatable<? extends Resource>> relatedResources : vmNonNicResourceDefinitions.values()) {
                for (Creatable<? extends Resource> resource : relatedResources) {
                    String createdResourceId = createdResourceIds.get(resource.key());
                    if (createdResourceId != null) {
                        // Prepare the deletion of each related resource (treating it as a generic resource) as a multi-threaded Observable
                        deleteObservables.add(azure.genericResources().deleteByIdAsync(createdResourceId).subscribeOn(Schedulers.io()));
                    }
                }
            }

            // Delete the related resources in parallel, as much as possible, postponing the errors till the end
            Completable.mergeDelayError(deleteObservables).await();

            // Show any errors
            for (Throwable error : errors) {
                System.out.println("\n### ERROR ###\n");
                if (error instanceof CloudException) {
                    CloudException ce = (CloudException) error;
                    System.out.println("Cloud Exception: " + ce.getMessage());
                } else {
                    error.printStackTrace();
                }
            }

            System.out.println("Number of failed/cleaned up VM creations: " + vmNonNicResourceDefinitions.size());

            // Verifications
            final int successfulVMCount = desiredVMCount - vmNonNicResourceDefinitions.size();
            final int actualVMCount = azure.virtualMachines().listByResourceGroup(resourceGroupName).size();
            System.out.println("Number of successful VMs: " + actualVMCount);

            final int actualNicCount = azure.networkInterfaces().listByResourceGroup(resourceGroupName).size();
            System.out.println(String.format("Remaining network interfaces (should be %d): %d", successfulVMCount, actualNicCount));

            final int actualNetworkCount = azure.networks().listByResourceGroup(resourceGroupName).size();
            System.out.println(String.format("Remaining virtual networks (should be %d): %d", successfulVMCount, actualNetworkCount));

            final int actualPipCount = azure.publicIPAddresses().listByResourceGroup(resourceGroupName).size();
            System.out.println(String.format("Remaining public IP addresses (should be %d): %d", successfulVMCount, actualPipCount));

            final int actualAvailabilitySetCount = azure.availabilitySets().listByResourceGroup(resourceGroupName).size();
            System.out.println(String.format("Remaining availability sets (should be %d): %d", successfulVMCount, actualAvailabilitySetCount));

            return true;
        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        } finally {
            try {
                System.out.println("Starting the deletion of resource group: " + resourceGroupName);
                azure.resourceGroups().beginDeleteByName(resourceGroupName);
            } catch (NullPointerException npe) {
                System.out.println("Did not create any resources in Azure. No clean up is necessary");
            } catch (Exception g) {
                g.printStackTrace();
            }
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
            //
            System.out.println("AZURE_AUTH_LOCATION_2=" + System.getenv("AZURE_AUTH_LOCATION_2"));
            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION_2"));

            Azure azure = Azure
                    .configure()
                    .withLogLevel(LogLevel.NONE)
                    .authenticate(credFile)
                    .withDefaultSubscription();

            // Print selected subscription
            System.out.println("Selected subscription: " + azure.subscriptionId());

            runSample(azure);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private CreateVirtualMachinesAsyncTrackingRelatedResources() {

    }
}
