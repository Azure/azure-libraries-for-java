/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management;

import com.microsoft.azure.CloudException;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.compute.CachingTypes;
import com.microsoft.azure.management.compute.Disk;
import com.microsoft.azure.management.compute.KnownLinuxVirtualMachineImage;
import com.microsoft.azure.management.compute.PowerState;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.compute.VirtualMachineImage;
import com.microsoft.azure.management.compute.VirtualMachineOffer;
import com.microsoft.azure.management.compute.VirtualMachinePublisher;
import com.microsoft.azure.management.compute.VirtualMachineSizeTypes;
import com.microsoft.azure.management.compute.VirtualMachineSku;
import com.microsoft.azure.management.locks.LockLevel;
import com.microsoft.azure.management.locks.ManagementLock;
import com.microsoft.azure.management.network.Access;
import com.microsoft.azure.management.network.ConnectivityCheck;
import com.microsoft.azure.management.network.Direction;
import com.microsoft.azure.management.network.FlowLogSettings;
import com.microsoft.azure.management.network.IpFlowProtocol;
import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.network.NetworkSecurityGroup;
import com.microsoft.azure.management.network.NetworkWatcher;
import com.microsoft.azure.management.network.NextHop;
import com.microsoft.azure.management.network.NextHopType;
import com.microsoft.azure.management.network.PacketCapture;
import com.microsoft.azure.management.network.PcProtocol;
import com.microsoft.azure.management.network.PcStatus;
import com.microsoft.azure.management.network.SecurityGroupView;
import com.microsoft.azure.management.network.Subnet;
import com.microsoft.azure.management.network.Topology;
import com.microsoft.azure.management.network.VerificationIPFlow;
import com.microsoft.azure.management.resources.Deployment;
import com.microsoft.azure.management.resources.DeploymentMode;
import com.microsoft.azure.management.resources.GenericResource;
import com.microsoft.azure.management.resources.Location;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.Subscription;
import com.microsoft.azure.management.resources.core.TestBase;
import com.microsoft.azure.management.resources.fluentcore.arm.CountryIsoCode;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.CreatedResources;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.storage.SkuName;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.rest.RestClient;

import rx.Observable;
import rx.schedulers.Schedulers;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AzureTests extends TestBase {
    private Azure azure;

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        Azure.Authenticated azureAuthed = Azure.authenticate(restClient, defaultSubscription, domain);
        azure = azureAuthed.withSubscription(defaultSubscription);
    }

    @Override
    protected void cleanUpResources() {

    }

    /**
     * Stress-tests the resilience of ExpandableEnum to multi-threaded access
     * @throws Exception
     */
    @Test
    public void testExpandableEnum() throws Exception {

        // Define some threads that read from enum
        Runnable reader1 = new Runnable() {
            @Override
            public void run() {
                Assert.assertEquals(CountryIsoCode.AFGHANISTAN, CountryIsoCode.fromString("AF"));
                Assert.assertEquals(CountryIsoCode.ANTARCTICA, CountryIsoCode.fromString("AQ"));
                Assert.assertEquals(CountryIsoCode.ANDORRA, CountryIsoCode.fromString("AD"));
                Assert.assertEquals(CountryIsoCode.ARGENTINA, CountryIsoCode.fromString("AR"));
                Assert.assertEquals(CountryIsoCode.ALBANIA, CountryIsoCode.fromString("AL"));
                Assert.assertEquals(CountryIsoCode.ALGERIA, CountryIsoCode.fromString("DZ"));
                Assert.assertEquals(CountryIsoCode.AMERICAN_SAMOA, CountryIsoCode.fromString("AS"));
                Assert.assertEquals(CountryIsoCode.ANGOLA, CountryIsoCode.fromString("AO"));
                Assert.assertEquals(CountryIsoCode.ANGUILLA, CountryIsoCode.fromString("AI"));
                Assert.assertEquals(CountryIsoCode.ANTIGUA_AND_BARBUDA, CountryIsoCode.fromString("AG"));
                Assert.assertEquals(CountryIsoCode.ARMENIA, CountryIsoCode.fromString("AM"));
                Assert.assertEquals(CountryIsoCode.ARUBA, CountryIsoCode.fromString("AW"));
                Assert.assertEquals(CountryIsoCode.AUSTRALIA, CountryIsoCode.fromString("AU"));
                Assert.assertEquals(CountryIsoCode.AUSTRIA, CountryIsoCode.fromString("AT"));
                Assert.assertEquals(CountryIsoCode.AZERBAIJAN, CountryIsoCode.fromString("AZ"));
                Assert.assertEquals(PowerState.DEALLOCATED, PowerState.fromString("PowerState/deallocated"));
                Assert.assertEquals(PowerState.DEALLOCATING, PowerState.fromString("PowerState/deallocating"));
                Assert.assertEquals(PowerState.RUNNING, PowerState.fromString("PowerState/running"));
            }
        };

        Runnable reader2 = new Runnable() {
            @Override
            public void run() {
                Assert.assertEquals(CountryIsoCode.BAHAMAS, CountryIsoCode.fromString("BS"));
                Assert.assertEquals(CountryIsoCode.BAHRAIN, CountryIsoCode.fromString("BH"));
                Assert.assertEquals(CountryIsoCode.BANGLADESH, CountryIsoCode.fromString("BD"));
                Assert.assertEquals(CountryIsoCode.BARBADOS, CountryIsoCode.fromString("BB"));
                Assert.assertEquals(CountryIsoCode.BELARUS, CountryIsoCode.fromString("BY"));
                Assert.assertEquals(CountryIsoCode.BELGIUM, CountryIsoCode.fromString("BE"));
                Assert.assertEquals(PowerState.STARTING, PowerState.fromString("PowerState/starting"));
                Assert.assertEquals(PowerState.STOPPED, PowerState.fromString("PowerState/stopped"));
                Assert.assertEquals(PowerState.STOPPING, PowerState.fromString("PowerState/stopping"));
                Assert.assertEquals(PowerState.UNKNOWN, PowerState.fromString("PowerState/unknown"));
            }
        };

        // Define some threads that write to enum
        Runnable writer1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    CountryIsoCode.fromString("CountryIsoCode" + i);
                    PowerState.fromString("PowerState" + i);
                }
            }
        };

        Runnable writer2 = new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 20; i++) {
                    CountryIsoCode.fromString("CountryIsoCode" + i);
                    PowerState.fromString("PowerState" + i);
                }
            }
        };

        // Start the threads and repeat a few times
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        for (int repeat = 0; repeat < 10; repeat++) {
            threadPool.submit(reader1);
            threadPool.submit(reader2);
            threadPool.submit(writer1);
            threadPool.submit(writer2);
        }

        // Give the test a fixed amount of time to finish
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        // Verify country ISO codes
        Collection<CountryIsoCode> countryIsoCodes = CountryIsoCode.values();
        System.out.println("\n## Country ISO codes: " + countryIsoCodes.size());
        for (CountryIsoCode value : countryIsoCodes) {
            System.out.println(value.toString());
        }
        Assert.assertEquals(257, countryIsoCodes.size());

        // Verify power states
        Collection<PowerState> powerStates = PowerState.values();
        System.out.println("\n## Power states: " + powerStates.size());
        for (PowerState value : powerStates) {
            System.out.println(value.toString());
        }
        Assert.assertEquals(27, powerStates.size());
    }

    /**
     * Tests ARM template deployments.
     * @throws IOException
     * @throws CloudException
     */
    @Test
    public void testDeployments() throws Exception {
        String testId = SdkContext.randomResourceName("", 8);
        List<Deployment> deployments = azure.deployments().list();
        System.out.println("Deployments: " + deployments.size());
        Deployment deployment = azure.deployments()
            .define("depl" + testId)
            .withNewResourceGroup("rg" + testId, Region.US_WEST)
            .withTemplateLink(
                    "https://raw.githubusercontent.com/Azure/azure-quickstart-templates/master/101-vnet-two-subnets/azuredeploy.json",
                    "1.0.0.0")
            .withParametersLink(
                    "https://raw.githubusercontent.com/Azure/azure-quickstart-templates/master/101-vnet-two-subnets/azuredeploy.parameters.json",
                    "1.0.0.0")
            .withMode(DeploymentMode.COMPLETE)
            .create();
        System.out.println("Created deployment: " + deployment.correlationId());

        azure.resourceGroups().beginDeleteByName("rg" + testId);
    }

    /**
     * Tests basic generic resources retrieval.
     * @throws Exception
     */
    @Test
    public void testGenericResources() throws Exception {
        // Create some resources
        NetworkSecurityGroup nsg = azure.networkSecurityGroups().define(SdkContext.randomResourceName("nsg", 13))
            .withRegion(Region.US_EAST)
            .withNewResourceGroup()
            .create();
        azure.publicIPAddresses().define(SdkContext.randomResourceName("pip", 13))
            .withRegion(Region.US_EAST)
            .withExistingResourceGroup(nsg.resourceGroupName())
            .create();

        PagedList<GenericResource> resources = azure.genericResources().listByResourceGroup(nsg.resourceGroupName());
        Assert.assertEquals(2, resources.size());
        GenericResource firstResource = resources.get(0);

        GenericResource resourceById = azure.genericResources().getById(firstResource.id());
        GenericResource resourceByDetails = azure.genericResources().get(
                firstResource.resourceGroupName(),
                firstResource.resourceProviderNamespace(),
                firstResource.resourceType(),
                firstResource.name());
        Assert.assertTrue(resourceById.id().equalsIgnoreCase(resourceByDetails.id()));
        azure.resourceGroups().beginDeleteByName(nsg.resourceGroupName());
    }

    /**
     * Tests management locks.
     * NOTE: This requires the service principal to have an Owner role on the subscription
     * @throws Exception
     */
    @Test
    public void testManagementLocks() throws Exception {
        // Prepare a VM
        final String password = SdkContext.randomResourceName("P@s", 14);
        final String rgName = SdkContext.randomResourceName("rg", 15);
        final String vmName = SdkContext.randomResourceName("vm", 15);
        final String storageName = SdkContext.randomResourceName("st", 15);
        final String diskName = SdkContext.randomResourceName("dsk", 15);
        final String netName = SdkContext.randomResourceName("net", 15);
        final Region region = Region.US_EAST;

        ResourceGroup resourceGroup = null;
        ManagementLock lockGroup = null,
                lockVM = null,
                lockStorage = null,
                lockDiskRO = null,
                lockDiskDel = null,
                lockSubnet = null;
        try {
            resourceGroup = azure.resourceGroups().define(rgName)
                    .withRegion(region)
                    .create();
            Assert.assertNotNull(resourceGroup);

            Creatable<Network> netDefinition = azure.networks().define(netName)
                    .withRegion(region)
                    .withExistingResourceGroup(resourceGroup)
                    .withAddressSpace("10.0.0.0/28");

            // Define a VM for testing VM locks
            Creatable<VirtualMachine> vmDefinition = azure.virtualMachines().define(vmName)
                    .withRegion(region)
                    .withExistingResourceGroup(resourceGroup)
                    .withNewPrimaryNetwork(netDefinition)
                    .withPrimaryPrivateIPAddressDynamic()
                    .withoutPrimaryPublicIPAddress()
                    .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                    .withRootUsername("tester")
                    .withRootPassword(password)
                    .withSize(VirtualMachineSizeTypes.BASIC_A1);

            // Define a managed disk for testing locks on that
            Creatable<Disk> diskDefinition = azure.disks().define(diskName)
                    .withRegion(region)
                    .withExistingResourceGroup(resourceGroup)
                    .withData()
                    .withSizeInGB(100);

            // Define a storage account for testing locks on that
            Creatable<StorageAccount> storageDefinition = azure.storageAccounts().define(storageName)
                    .withRegion(region)
                    .withExistingResourceGroup(resourceGroup);

            // Create resources in parallel to save time and money
            Observable.merge(
                    storageDefinition.createAsync().subscribeOn(Schedulers.io()),
                    vmDefinition.createAsync().subscribeOn(Schedulers.io()),
                    diskDefinition.createAsync().subscribeOn(Schedulers.io()))
            .toBlocking().subscribe();

            VirtualMachine vm = (VirtualMachine) vmDefinition;
            StorageAccount storage = (StorageAccount) storageDefinition;
            Disk disk = (Disk) diskDefinition;
            Network network = vm.getPrimaryNetworkInterface().primaryIPConfiguration().getNetwork();
            Subnet subnet = network.subnets().values().iterator().next();

            // Lock subnet
            Creatable<ManagementLock> lockSubnetDef = azure.managementLocks().define("subnetLock")
                    .withLockedResource(subnet.inner().id())
                    .withLevel(LockLevel.READ_ONLY);

            // Lock VM
            Creatable<ManagementLock> lockVMDef = azure.managementLocks().define("vmlock")
                    .withLockedResource(vm)
                    .withLevel(LockLevel.READ_ONLY)
                    .withNotes("vm readonly lock");

            // Lock resource group
            Creatable<ManagementLock> lockGroupDef = azure.managementLocks().define("rglock")
                    .withLockedResource(resourceGroup.id())
                    .withLevel(LockLevel.CAN_NOT_DELETE);

            // Lock storage
            Creatable<ManagementLock> lockStorageDef = azure.managementLocks().define("stLock")
                    .withLockedResource(storage)
                    .withLevel(LockLevel.CAN_NOT_DELETE);

            // Create locks in parallel
            @SuppressWarnings("unchecked")
            CreatedResources<ManagementLock> created = azure.managementLocks().create(lockVMDef, lockGroupDef, lockStorageDef, lockSubnetDef);
            lockVM = created.get(lockVMDef.key());
            lockStorage = created.get(lockStorageDef.key());
            lockGroup = created.get(lockGroupDef.key());
            lockSubnet = created.get(lockSubnetDef.key());

            // Lock disk synchronously
            lockDiskRO = azure.managementLocks().define("diskLockRO")
                    .withLockedResource(disk)
                    .withLevel(LockLevel.READ_ONLY)
                    .create();

            lockDiskDel = azure.managementLocks().define("diskLockDel")
                    .withLockedResource(disk)
                    .withLevel(LockLevel.CAN_NOT_DELETE)
                    .create();

            // Verify VM lock
            Assert.assertEquals(2, azure.managementLocks().listForResource(vm.id()).size());

            Assert.assertNotNull(lockVM);
            lockVM = azure.managementLocks().getById(lockVM.id());
            Assert.assertNotNull(lockVM);
            TestUtils.print(lockVM);
            Assert.assertEquals(LockLevel.READ_ONLY, lockVM.level());
            Assert.assertTrue(vm.id().equalsIgnoreCase(lockVM.lockedResourceId()));

            // Verify resource group lock
            Assert.assertNotNull(lockGroup);
            lockGroup = azure.managementLocks().getByResourceGroup(resourceGroup.name(), "rglock");
            Assert.assertNotNull(lockGroup);
            TestUtils.print(lockVM);
            Assert.assertEquals(LockLevel.CAN_NOT_DELETE, lockGroup.level());
            Assert.assertTrue(resourceGroup.id().equalsIgnoreCase(lockGroup.lockedResourceId()));

            // Verify storage account lock
            Assert.assertEquals(2, azure.managementLocks().listForResource(storage.id()).size());

            Assert.assertNotNull(lockStorage);
            lockStorage = azure.managementLocks().getById(lockStorage.id());
            Assert.assertNotNull(lockStorage);
            TestUtils.print(lockStorage);
            Assert.assertEquals(LockLevel.CAN_NOT_DELETE, lockStorage.level());
            Assert.assertTrue(storage.id().equalsIgnoreCase(lockStorage.lockedResourceId()));

            // Verify disk lock
            Assert.assertEquals(3, azure.managementLocks().listForResource(disk.id()).size());

            Assert.assertNotNull(lockDiskRO);
            lockDiskRO = azure.managementLocks().getById(lockDiskRO.id());
            Assert.assertNotNull(lockDiskRO);
            TestUtils.print(lockDiskRO);
            Assert.assertEquals(LockLevel.READ_ONLY, lockDiskRO.level());
            Assert.assertTrue(disk.id().equalsIgnoreCase(lockDiskRO.lockedResourceId()));

            Assert.assertNotNull(lockDiskDel);
            lockDiskDel = azure.managementLocks().getById(lockDiskDel.id());
            Assert.assertNotNull(lockDiskDel);
            TestUtils.print(lockDiskDel);
            Assert.assertEquals(LockLevel.CAN_NOT_DELETE, lockDiskDel.level());
            Assert.assertTrue(disk.id().equalsIgnoreCase(lockDiskDel.lockedResourceId()));

            // Verify subnet lock
            Assert.assertEquals(2, azure.managementLocks().listForResource(network.id()).size());

            lockSubnet = azure.managementLocks().getById(lockSubnet.id());
            Assert.assertNotNull(lockSubnet);
            TestUtils.print(lockSubnet);
            Assert.assertEquals(LockLevel.READ_ONLY, lockSubnet.level());
            Assert.assertTrue(subnet.inner().id().equalsIgnoreCase(lockSubnet.lockedResourceId()));

            // Verify lock collection
            List<ManagementLock> locksSubscription = azure.managementLocks().list();
            List<ManagementLock> locksGroup = azure.managementLocks().listByResourceGroup(vm.resourceGroupName());
            Assert.assertNotNull(locksSubscription);
            Assert.assertNotNull(locksGroup);

            int locksAllCount = locksSubscription.size();
            System.out.println("All locks: " + locksAllCount);
            Assert.assertTrue(6 <= locksAllCount);

            int locksGroupCount = locksGroup.size();
            System.out.println("Group locks: " + locksGroupCount);
            Assert.assertEquals(6, locksGroup.size());
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        } finally {
            if (resourceGroup != null) {
                if (lockGroup != null) {
                    azure.managementLocks().deleteById(lockGroup.id());
                }
                if (lockVM != null) {
                    azure.managementLocks().deleteById(lockVM.id());
                }
                if (lockDiskRO != null) {
                    azure.managementLocks().deleteById(lockDiskRO.id());
                }
                if (lockDiskDel != null) {
                    azure.managementLocks().deleteById(lockDiskDel.id());
                }
                if (lockStorage != null) {
                    azure.managementLocks().deleteById(lockStorage.id());
                }
                if (lockSubnet != null) {
                    azure.managementLocks().deleteById(lockSubnet.id());
                }
                azure.resourceGroups().beginDeleteByName(resourceGroup.name());
            }
        }
    }


    /**
     * Tests VM images.
     * @throws IOException
     * @throws CloudException
     */
    @Test
    public void testVMImages() throws CloudException, IOException {
        List<VirtualMachinePublisher> publishers = azure.virtualMachineImages().publishers().listByRegion(Region.US_WEST);
        Assert.assertTrue(publishers.size() > 0);
        for (VirtualMachinePublisher p : publishers) {
            System.out.println(String.format("Publisher name: %s, region: %s", p.name(), p.region()));
            try {
                for (VirtualMachineOffer o : p.offers().list()) {
                    System.out.println(String.format("\tOffer name: %s", o.name()));
                    try {
                        for (VirtualMachineSku s : o.skus().list()) {
                            System.out.println(String.format("\t\tSku name: %s", s.name()));
                        }
                    } catch (com.microsoft.rest.RestException e) {
                        e.printStackTrace();
                    }
                }
            } catch (com.microsoft.rest.RestException e) {
                e.printStackTrace();
            }
        }
        List<VirtualMachineImage> images = azure.virtualMachineImages().listByRegion(Region.US_WEST);
        Assert.assertTrue(images.size() > 0);
        // Seems to help avoid connection refused error on subsequent mock test
        SdkContext.sleep(2000);
    }

    /**
     * Tests the network security group implementation.
     * @throws Exception
     */
    @Test
    public void testNetworkSecurityGroups() throws Exception {
        new TestNSG().runTest(azure.networkSecurityGroups(), azure.resourceGroups());
    }

    /**
     * Tests the inbound NAT rule support in load balancers.
     * @throws Exception
     */
    @Test
    public void testLoadBalancersNatRules() throws Exception {
        new TestLoadBalancer.InternetWithNatRule(azure.virtualMachines().manager())
            .runTest(azure.loadBalancers(), azure.resourceGroups());
    }

    /**
     * Tests the inbound NAT pool support in load balancers.
     * @throws Exception
     */
    @Test
    public void testLoadBalancersNatPools() throws Exception {
        new TestLoadBalancer.InternetWithNatPool(azure.virtualMachines().manager())
            .runTest(azure.loadBalancers(), azure.resourceGroups());
    }

    /**
     * Tests the minimum Internet-facing load balancer with a load balancing rule only
     * @throws Exception
     */
    @Test
    public void testLoadBalancersInternetMinimum() throws Exception {
        new TestLoadBalancer.InternetMinimal(azure.virtualMachines().manager())
            .runTest(azure.loadBalancers(), azure.resourceGroups());
    }

    /**
     * Tests the minimum Internet-facing load balancer with a NAT rule only
     * @throws Exception
     */
    @Test
    public void testLoadBalancersNatOnly() throws Exception {
        new TestLoadBalancer.InternetNatOnly(azure.virtualMachines().manager())
            .runTest(azure.loadBalancers(), azure.resourceGroups());
    }

    /**
     * Tests the minimum internal load balancer.
     * @throws Exception
     */
    @Test
    public void testLoadBalancersInternalMinimum() throws Exception {
        new TestLoadBalancer.InternalMinimal(azure.virtualMachines().manager())
            .runTest(azure.loadBalancers(), azure.resourceGroups());
    }

    /**
     * Tests the internal load balancer with availability zone.
     * @throws Exception
     */
    @Test
    @Ignore("Though valid scenario, NRP is failing")
    public void testLoadBalancersInternalWithAvailabilityZone() throws Exception {
        new TestLoadBalancer.InternalWithZone(azure.virtualMachines().manager())
                .runTest(azure.loadBalancers(), azure.resourceGroups());
    }

    @Test
    public void testManagedDiskVMUpdate() throws Exception {
        final String rgName = SdkContext.randomResourceName("rg", 13);
        final String linuxVM2Name = SdkContext.randomResourceName("vm" + "-", 10);
        final String linuxVM2Pip = SdkContext.randomResourceName("pip" + "-", 18);
        VirtualMachine linuxVM2 = azure.virtualMachines().define(linuxVM2Name)
                .withRegion(Region.US_EAST)
                .withNewResourceGroup(rgName)
                .withNewPrimaryNetwork("10.0.0.0/28")
                .withPrimaryPrivateIPAddressDynamic()
                .withNewPrimaryPublicIPAddress(linuxVM2Pip)
                .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                .withRootUsername("tester")
                .withRootPassword("Abcdef.123456!")
                // Begin: Managed data disks
                .withNewDataDisk(100)
                .withNewDataDisk(100, 1, CachingTypes.READ_WRITE)
                // End: Managed data disks
                .withSize(VirtualMachineSizeTypes.STANDARD_D3_V2)
                .create();

        linuxVM2.deallocate();
        linuxVM2.update()
                .withoutDataDisk(2)
                .withNewDataDisk(200)
                .apply();
        azure.resourceGroups().beginDeleteByName(rgName);
    }

    /**
     * Tests the public IP address implementation.
     * @throws Exception
     */
    @Test
    public void testPublicIPAddresses() throws Exception {
        new TestPublicIPAddress().runTest(azure.publicIPAddresses(), azure.resourceGroups());
    }

    /**
     * Tests the availability set implementation.
     * @throws Exception
     */
    @Test
    public void testAvailabilitySets() throws Exception {
        new TestAvailabilitySet().runTest(azure.availabilitySets(), azure.resourceGroups());
    }

    /**
     * Tests the virtual network implementation.
     * @throws Exception
     */
    @Test
    public void testNetworks() throws Exception {
        new TestNetwork.WithSubnets()
            .runTest(azure.networks(), azure.resourceGroups());
    }

    /**
     * Tests virtual network peering
     * @throws Exception
     */
    @Test
    public void testNetworkWithAccessFromServiceToSubnet() throws Exception {
        new TestNetwork.WithAccessFromServiceToSubnet()
                .runTest(azure.networks(), azure.resourceGroups());
    }

    /**
     * Tests virtual network peering
     * @throws Exception
     */
    @Test
    public void testNetworkPeerings() throws Exception {
        new TestNetwork.WithPeering()
            .runTest(azure.networks(), azure.resourceGroups());
    }

    /**
     * Tests route tables.
     * @throws Exception
     */
    @Test
    public void testRouteTables() throws Exception {
        new TestRouteTables.Minimal()
            .runTest(azure.routeTables(), azure.resourceGroups());
    }

    /**
     * Tests the regions enum.
     */
    @Test
    public void testRegions() {
        // Show built-in regions
        System.out.println("Built-in regions list:");
        int regionsCount = Region.values().length;

        for (Region region : Region.values()) {
            System.out.println("Name: " + region.name() + ", Label: " + region.label());
        }

        // Look up built-in region
        Region region = Region.fromName("westus");
        Assert.assertTrue(region == Region.US_WEST);

        // Add a region
        Region region2 = Region.fromName("madeUpRegion");
        Assert.assertNotNull(region2);
        Assert.assertTrue(region2.name().equalsIgnoreCase("madeUpRegion"));
        Region region3 = Region.fromName("madeupregion");
        Assert.assertEquals(region3, region2);
        Assert.assertEquals(Region.values().length, regionsCount + 1);
    }

    /**
     * Tests the network interface implementation.
     * @throws Exception
     */
    @Test
    public void testNetworkInterfaces() throws Exception {
        new TestNetworkInterface().runTest(azure.networkInterfaces(), azure.resourceGroups());
    }

    /**
     * Tests the network watcher implementation.
     * @throws Exception
     */
    @Test
    public void testNetworkWatchers() throws Exception {
        new TestNetworkWatcher().runTest(azure.networkWatchers(), azure.resourceGroups());
    }

    @Test
    public void testNetworkWatcherFunctions() throws Exception {
        TestNetworkWatcher tnw = new TestNetworkWatcher();

        NetworkWatcher nw = tnw.createResource(azure.networkWatchers());

        // pre-create VMs to show topology on
        VirtualMachine[] virtualMachines = tnw.ensureNetwork(azure.networkWatchers().manager().networks(),
                azure.virtualMachines(), azure.networkInterfaces());

        Topology topology = nw.topology().withTargetResourceGroup(virtualMachines[0].resourceGroupName()).execute();
        Assert.assertEquals(11, topology.resources().size());
        Assert.assertTrue(topology.resources().containsKey(virtualMachines[0].getPrimaryNetworkInterface().networkSecurityGroupId()));
        Assert.assertEquals(4, topology.resources().get(virtualMachines[0].primaryNetworkInterfaceId()).associations().size());

        SecurityGroupView sgViewResult = nw.getSecurityGroupView(virtualMachines[0].id());
        Assert.assertEquals(1, sgViewResult.networkInterfaces().size());
        Assert.assertEquals(virtualMachines[0].primaryNetworkInterfaceId(), sgViewResult.networkInterfaces().keySet().iterator().next());

        FlowLogSettings flowLogSettings = nw.getFlowLogSettings(virtualMachines[0].getPrimaryNetworkInterface().networkSecurityGroupId());
        StorageAccount storageAccount = tnw.ensureStorageAccount(azure.storageAccounts());
        flowLogSettings.update()
                .withLogging()
                .withStorageAccount(storageAccount.id())
                .withRetentionPolicyDays(5)
                .withRetentionPolicyEnabled()
                .apply();
        Assert.assertEquals(true, flowLogSettings.enabled());
        Assert.assertEquals(5, flowLogSettings.retentionDays());
        Assert.assertEquals(storageAccount.id(), flowLogSettings.storageId());

        NextHop nextHop = nw.nextHop().withTargetResourceId(virtualMachines[0].id())
            .withSourceIPAddress("10.0.0.4")
            .withDestinationIPAddress("8.8.8.8")
            .execute();
        Assert.assertEquals("System Route", nextHop.routeTableId());
        Assert.assertEquals(NextHopType.INTERNET, nextHop.nextHopType());
        Assert.assertNull(nextHop.nextHopIpAddress());

        VerificationIPFlow verificationIPFlow = nw.verifyIPFlow()
                .withTargetResourceId(virtualMachines[0].id())
                .withDirection(Direction.OUTBOUND)
                .withProtocol(IpFlowProtocol.TCP)
                .withLocalIPAddress("10.0.0.4")
                .withRemoteIPAddress("8.8.8.8")
                .withLocalPort("443")
                .withRemotePort("443")
                .execute();
        Assert.assertEquals(Access.ALLOW, verificationIPFlow.access());
        Assert.assertTrue("defaultSecurityRules/AllowInternetOutBound".equalsIgnoreCase(verificationIPFlow.ruleName()));

        // test packet capture
        List<PacketCapture> packetCaptures = nw.packetCaptures().list();
        Assert.assertEquals(0, packetCaptures.size());
        PacketCapture packetCapture = nw.packetCaptures()
                .define("NewPacketCapture")
                .withTarget(virtualMachines[0].id())
                .withStorageAccountId(storageAccount.id())
                .withTimeLimitInSeconds(1500)
                .definePacketCaptureFilter()
                    .withProtocol(PcProtocol.TCP)
                    .withLocalIPAddresses(Arrays.asList("127.0.0.1", "127.0.0.5"))
                    .attach()
                .create();
        packetCaptures = nw.packetCaptures().list();
        Assert.assertEquals(1, packetCaptures.size());
        Assert.assertEquals("NewPacketCapture", packetCapture.name());
        Assert.assertEquals(1500, packetCapture.timeLimitInSeconds());
        Assert.assertEquals(PcProtocol.TCP, packetCapture.filters().get(0).protocol());
        Assert.assertEquals("127.0.0.1;127.0.0.5", packetCapture.filters().get(0).localIPAddress());
//        Assert.assertEquals("Running", packetCapture.getStatus().packetCaptureStatus().toString());
        packetCapture.stop();
        Assert.assertEquals(PcStatus.STOPPED, packetCapture.getStatus().packetCaptureStatus());
        nw.packetCaptures().deleteByName(packetCapture.name());

        ConnectivityCheck connectivityCheck = nw.checkConnectivity()
                .toDestinationResourceId(virtualMachines[1].id())
                .toDestinationPort(80)
                .fromSourceVirtualMachine(virtualMachines[0].id())
                .execute();
        Assert.assertEquals("Reachable", connectivityCheck.connectionStatus().toString());

        azure.virtualMachines().deleteById(virtualMachines[1].id());
        topology.execute();
        Assert.assertEquals(10, topology.resources().size());

        azure.resourceGroups().deleteByName(nw.resourceGroupName());
        azure.resourceGroups().deleteByName(tnw.groupName());
    }

    /**
     * Tests the local network gateway implementation.
     * @throws Exception
     */
    @Test
    public void testLocalNetworkGateways() throws Exception {
        new TestLocalNetworkGateway().runTest(azure.localNetworkGateways(), azure.resourceGroups());
    }

    /**
     * Tests the express route circuit implementation.
     * @throws Exception
     */
    @Test
    public void testExpressRouteCircuits() throws Exception {
        new TestExpressRouteCircuit.Basic().runTest(azure.expressRouteCircuits(), azure.resourceGroups());
    }

    /**
     * Tests the express route circuit peerings implementation.
     * @throws Exception
     */
    @Test
    public void testExpressRouteCircuitPeering() throws Exception {
        new TestExpressRouteCircuit.ExpressRouteCircuitPeering().runTest(azure.expressRouteCircuits(), azure.resourceGroups());
    }

    /**
     * Tests virtual machines.
     * @throws Exception
     */
    @Test
    @Ignore("osDiskSize is returned as 127 instead of 128 - known service bug")
    public void testVirtualMachines() throws Exception {
        // Future: This method needs to have a better specific name since we are going to include unit test for
        // different vm scenarios.
        new TestVirtualMachine().runTest(azure.virtualMachines(), azure.resourceGroups());
    }

    /**
     * Tests the virtual machine data disk implementation.
     * @throws Exception
     */
    @Test
    public void testVirtualMachineDataDisk() throws Exception {
        new TestVirtualMachineDataDisk().runTest(azure.virtualMachines(), azure.resourceGroups());
    }

    /**
     * Tests the virtual machine network interface implementation.
     * @throws Exception
     */
    @Test
    public void testVirtualMachineNics() throws Exception {
        new TestVirtualMachineNics(azure.networks().manager())
            .runTest(azure.virtualMachines(), azure.resourceGroups());
    }

    /**
     * Tests virtual machine support for SSH.
     * @throws Exception
     */
    @Test
    public void testVirtualMachineSSh() throws Exception {
        new TestVirtualMachineSsh(azure.publicIPAddresses())
                .runTest(azure.virtualMachines(), azure.resourceGroups());
    }

    /**
     * Tests virtual machine sizes.
     * @throws Exception
     */
    @Test
    public void testVirtualMachineSizes() throws Exception {
        new TestVirtualMachineSizes()
                .runTest(azure.virtualMachines(), azure.resourceGroups());
    }

    @Test
    public void testVirtualMachineCustomData() throws Exception {
        new TestVirtualMachineCustomData(azure.publicIPAddresses())
                .runTest(azure.virtualMachines(), azure.resourceGroups());
    }

    @Test
    public void testVirtualMachineInAvailabilitySet() throws Exception {
        new TestVirtualMachineInAvailabilitySet().runTest(azure.virtualMachines(), azure.resourceGroups());
    }

    /**
     * Tests subscription listing.
     * @throws Exception
     */
    @Test
    public void listSubscriptions() throws Exception {
        Assert.assertTrue(0 < azure.subscriptions().list().size());
        Subscription subscription = azure.getCurrentSubscription();
        Assert.assertNotNull(subscription);
        Assert.assertTrue(azure.subscriptionId().equalsIgnoreCase(subscription.subscriptionId()));
    }

    /**
     * Tests location listing.
     * @throws Exception
     */
    @Test
    public void listLocations() throws Exception {
        Subscription subscription = azure.getCurrentSubscription();
        Assert.assertNotNull(subscription);
        for (Location location : subscription.listLocations()) {
            Region region = Region.findByLabelOrName(location.name());
            Assert.assertNotNull(region);
            Assert.assertEquals(region, location.region());
            Assert.assertEquals(region.name().toLowerCase(), location.name().toLowerCase());
        }

        Location location = subscription.getLocationByRegion(Region.US_WEST);
        Assert.assertNotNull(location);
        Assert.assertTrue(Region.US_WEST.name().equalsIgnoreCase(location.name()));
    }

    /**
     * Tests resource group listing.
     * @throws Exception
     */
    @Test
    public void listResourceGroups() throws Exception {
        int groupCount = azure.resourceGroups().list().size();
        System.out.println(String.format("Group count: %s", groupCount));
        Assert.assertTrue(0 < groupCount);
    }

    /**
     * Tests storage account listing.
     * @throws Exception
     */
    @Test
    public void listStorageAccounts() throws Exception {
        Assert.assertTrue(0 < azure.storageAccounts().list().size());
    }

    @Test
    public void createStorageAccount() throws Exception {
        String storageAccountName = generateRandomResourceName("testsa", 12);
        StorageAccount storageAccount = azure.storageAccounts().define(storageAccountName)
                .withRegion(Region.ASIA_EAST)
                .withNewResourceGroup()
                .withSku(SkuName.PREMIUM_LRS)
                .create();

        Assert.assertEquals(storageAccount.name(), storageAccountName);

        azure.resourceGroups().beginDeleteByName(storageAccount.resourceGroupName());
    }

    @Test
    public void testBatchAccount() throws Exception {
        new TestBatch().runTest(azure.batchAccounts(), azure.resourceGroups());
    }

    @Test
    public void testBatchAI() throws Exception {
        new TestBatchAI.Basic(azure.storageAccounts(), azure.networks()).runTest(azure.batchAIClusters(), azure.resourceGroups());
    }

    @Test
    public void testBatchAIJob() throws Exception {
        new TestBatchAI.JobCreate().runTest(azure.batchAIClusters(), azure.resourceGroups());
    }

    @Test
    public void testBatchAIFileServer() throws Exception {
        new TestBatchAIFileServers(azure.networks()).runTest(azure.batchAIFileServers(), azure.resourceGroups());
    }

    @Test
    public void testTrafficManager() throws Exception {
        new TestTrafficManager(azure.publicIPAddresses())
                .runTest(azure.trafficManagerProfiles(), azure.resourceGroups());
    }

    @Test
    public void testRedis() throws Exception {
        new TestRedis()
                .runTest(azure.redisCaches(), azure.resourceGroups());
    }

    @Test
    public void testCdnManager() throws Exception {
        new TestCdn()
                .runTest(azure.cdnProfiles(), azure.resourceGroups());
    }

    @Test
    public void testDnsZones() throws Exception {
        addTextReplacementRule("https://management.azure.com:443/", playbackUri + "/");
        new TestDns()
                .runTest(azure.dnsZones(), azure.resourceGroups());
    }

    @Test
    public void testSqlServer() throws Exception {
        new TestSql().runTest(azure.sqlServers(), azure.resourceGroups());
    }

    @Test
    public void testResourceStreaming() throws Exception {
        new TestResourceStreaming(azure.storageAccounts()).runTest(azure.virtualMachines(), azure.resourceGroups());
    }

    @Test
    public void testContainerService() throws Exception {
        new TestContainerService()
                .runTest(azure.containerServices(), azure.resourceGroups());
    }

    @Test
    public void testKubernetesCluster() throws Exception {
        if (isPlaybackMode()) {
            new TestKubernetesCluster()
                .runTest(azure.kubernetesClusters(), azure.resourceGroups());
        }
    }

    @Test
    public void testContainerInstance() throws Exception {
        new TestContainerInstance()
            .runTest(azure.containerGroups(), azure.resourceGroups());
    }

    @Test
    public void testContainerRegistry() throws Exception {
        new TestContainerRegistry()
                .runTest(azure.containerRegistries(), azure.resourceGroups());
    }

    @Test
    @Ignore("Runs locally find but fails for unknown reason on check in.")
    public void testCosmosDB() throws Exception {
        new TestCosmosDB()
                .runTest(azure.cosmosDBAccounts(), azure.resourceGroups());
    }

    @Test
    public void testSearchServiceFreeSku() throws Exception {
        new TestSearchService.SearchServiceFreeSku()
            .runTest(azure.searchServices(), azure.resourceGroups());
    }

    @Test
    public void testSearchServiceBasicSku() throws Exception {
        new TestSearchService.SearchServiceBasicSku()
            .runTest(azure.searchServices(), azure.resourceGroups());
    }

    @Test
    public void testSearchServiceStandardSku() throws Exception {
        new TestSearchService.SearchServiceStandardSku()
            .runTest(azure.searchServices(), azure.resourceGroups());
    }

    @Test
    public void testSearchServiceAnySku() throws Exception {
        new TestSearchService.SearchServiceAnySku()
            .runTest(azure.searchServices(), azure.resourceGroups());
    }

}
