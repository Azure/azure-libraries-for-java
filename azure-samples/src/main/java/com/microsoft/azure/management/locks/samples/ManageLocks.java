/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.locks.samples;

import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.compute.CachingTypes;
import com.microsoft.azure.management.compute.Disk;
import com.microsoft.azure.management.compute.KnownLinuxVirtualMachineImage;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.compute.VirtualMachineSizeTypes;
import com.microsoft.azure.management.locks.LockLevel;
import com.microsoft.azure.management.locks.ManagementLock;
import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.network.Subnet;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.CreatedResources;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.azure.management.storage.StorageAccount;

import rx.Observable;

import java.io.File;
import java.util.List;

/**
 * This sample shows examples of management locks usage on various resources.
 *  - Create a number of various resources to apply locks to
 *  - Apply various locks to the resources
 *  - Retrieve and show lock information
 *  - Remove the locks and clean up
 */
public final class ManageLocks {

    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        
        final String password = SdkContext.randomResourceName("P@s", 14);
        final String rgName = SdkContext.randomResourceName("rg", 15);
        final String vmName = SdkContext.randomResourceName("vm", 15);
        final String storageName = SdkContext.randomResourceName("st", 15);
        final String diskName = SdkContext.randomResourceName("dsk", 15);
        final String netName = SdkContext.randomResourceName("net", 15);
        final Region region = Region.US_SOUTH_CENTRAL;

        ResourceGroup resourceGroup = null;
        ManagementLock lockGroup = null,
                lockVM = null,
                lockStorage = null,
                lockDiskRO = null,
                lockDiskDel = null,
                lockSubnet = null;

        try {
            //=============================================================
            // Create a shared resource group for all the resources so they can all be deleted together
            //
            resourceGroup = azure.resourceGroups().define(rgName)
                    .withRegion(region)
                    .create();
            System.out.println("Created a new resource group - " + resourceGroup.id());

            //============================================================
            // Create various resources for demonstrating locks
            //

            // Define a network to apply a lock to
            Creatable<Network> netDefinition = azure.networks().define(netName)
                    .withRegion(region)
                    .withExistingResourceGroup(resourceGroup)
                    .withAddressSpace("10.0.0.0/28");
    
            // Define a managed disk for testing locks on that
            Creatable<Disk> diskDefinition = azure.disks().define(diskName)
                    .withRegion(region)
                    .withExistingResourceGroup(resourceGroup)
                    .withData()
                    .withSizeInGB(100);
    
            // Define a VM to apply a lock to
            Creatable<VirtualMachine> vmDefinition = azure.virtualMachines().define(vmName)
                        .withRegion(region)
                        .withExistingResourceGroup(resourceGroup)
                        .withNewPrimaryNetwork(netDefinition)
                        .withPrimaryPrivateIPAddressDynamic()
                        .withoutPrimaryPublicIPAddress()
                        .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                        .withRootUsername("tester")
                        .withRootPassword(password)
                        .withNewDataDisk(diskDefinition, 1, CachingTypes.NONE)
                        .withSize(VirtualMachineSizeTypes.BASIC_A1);
    
            // Define a storage account to apply a lock to
            Creatable<StorageAccount> storageDefinition = azure.storageAccounts().define(storageName)
                    .withRegion(region)
                    .withExistingResourceGroup(resourceGroup);
    
            // Create resources in parallel to save time
            System.out.println("Creating the needed resources...");
            Observable.merge(
                    storageDefinition.createAsync().subscribeOn(SdkContext.getRxScheduler()),
                    vmDefinition.createAsync().subscribeOn(SdkContext.getRxScheduler()))
            .toBlocking().subscribe();
            System.out.println("Resources created.");
    
            VirtualMachine vm = (VirtualMachine) vmDefinition;
            StorageAccount storage = (StorageAccount) storageDefinition;
            Disk disk = (Disk) diskDefinition;
            Network network = vm.getPrimaryNetworkInterface().primaryIPConfiguration().getNetwork();
            Subnet subnet = network.subnets().values().iterator().next();

            //============================================================
            // Create various locks for the created resources
            //

            // Locks can be created serially, and multiple can be applied to the same resource:
            System.out.println("Creating locks sequentially...");

            // Apply a ReadOnly lock to the disk
            lockDiskRO = azure.managementLocks().define("diskLockRO")
                    .withLockedResource(disk)
                    .withLevel(LockLevel.READ_ONLY)
                    .create();

            // Apply a lock preventing the disk from being deleted
            lockDiskDel = azure.managementLocks().define("diskLockDel")
                    .withLockedResource(disk)
                    .withLevel(LockLevel.CAN_NOT_DELETE)
                    .create();

            // Locks can also be created in parallel, for better overall performance:
            System.out.println("Creating locks in parallel...");

            // Define a subnet lock
            Creatable<ManagementLock> lockSubnetDef = azure.managementLocks().define("subnetLock")
                    .withLockedResource(subnet.inner().id())
                    .withLevel(LockLevel.READ_ONLY);
    
            // Define a VM lock
            Creatable<ManagementLock> lockVMDef = azure.managementLocks().define("vmlock")
                    .withLockedResource(vm)
                    .withLevel(LockLevel.READ_ONLY)
                    .withNotes("vm readonly lock");
    
            // Define a resource group lock
            Creatable<ManagementLock> lockGroupDef = azure.managementLocks().define("rglock")
                    .withLockedResource(resourceGroup.id())
                    .withLevel(LockLevel.CAN_NOT_DELETE);
    
            // Define a storage lock
            Creatable<ManagementLock> lockStorageDef = azure.managementLocks().define("stLock")
                    .withLockedResource(storage)
                    .withLevel(LockLevel.CAN_NOT_DELETE);
    
            @SuppressWarnings("unchecked")
            CreatedResources<ManagementLock> created = azure.managementLocks().create(
                    lockVMDef,
                    lockGroupDef,
                    lockStorageDef,
                    lockSubnetDef);

            lockVM = created.get(lockVMDef.key());
            lockStorage = created.get(lockStorageDef.key());
            lockGroup = created.get(lockGroupDef.key());
            lockSubnet = created.get(lockSubnetDef.key());
    
            System.out.println("Locks created.");
    
            //============================================================
            // Retrieve and show lock information
            //

            // Count and show locks (Note: locks returned for a resource include the locks for its resource group and child resources)
            int lockCount = azure.managementLocks().listForResource(vm.id()).size();
            System.out.println("Number of locks applied to the virtual machine: " + lockCount);
            lockCount = azure.managementLocks().listByResourceGroup(resourceGroup.name()).size();
            System.out.println("Number of locks applied to the resource group (includes locks on resources in the group): " + lockCount);
            lockCount = azure.managementLocks().listForResource(storage.id()).size();
            System.out.println("Number of locks applied to the storage account: " + lockCount);
            lockCount = azure.managementLocks().listForResource(disk.id()).size();
            System.out.println("Number of locks applied to the managed disk: " + lockCount);
            lockCount = azure.managementLocks().listForResource(network.id()).size();
            System.out.println("Number of locks applied to the network (including its subnets): " + lockCount);
    
            // Locks can be retrieved using their ID
            lockVM = azure.managementLocks().getById(lockVM.id());
            lockGroup = azure.managementLocks().getByResourceGroup(resourceGroup.name(), "rglock");
            lockStorage = azure.managementLocks().getById(lockStorage.id());
            lockDiskRO = azure.managementLocks().getById(lockDiskRO.id());
            lockDiskDel = azure.managementLocks().getById(lockDiskDel.id());
            lockSubnet = azure.managementLocks().getById(lockSubnet.id());
    
            // Show the locks
            Utils.print(lockGroup);
            Utils.print(lockVM);
            Utils.print(lockDiskDel);
            Utils.print(lockDiskRO);
            Utils.print(lockStorage);
            Utils.print(lockSubnet);
    
            // List all locks within a subscription
            List<ManagementLock> locksSubscription = azure.managementLocks().list();
            System.out.println("Total number of locks within this subscription: " + locksSubscription.size());
            return true;
        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();            

        } finally {

            //============================================================
            // Delete locks and clean up
            //

            try {
                // Clean up (remember to unlock resources before deleting the resource group)
                azure.managementLocks().deleteByIds(
                        lockGroup.id(),
                        lockVM.id(),
                        lockDiskRO.id(),
                        lockDiskDel.id(),
                        lockStorage.id(),
                        lockSubnet.id());
                System.out.println("Deleting Resource Group: " + rgName);
                azure.resourceGroups().beginDeleteByName(rgName);
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
            System.out.println("AZURE_AUTH_LOCATION=" + System.getenv("AZURE_AUTH_LOCATION"));
            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));
            Azure azure = Azure.authenticate(credFile).withDefaultSubscription();

            // Print selected subscription
            System.out.println("Selected subscription: " + azure.subscriptionId());

            runSample(azure);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private ManageLocks() {
    }
}
