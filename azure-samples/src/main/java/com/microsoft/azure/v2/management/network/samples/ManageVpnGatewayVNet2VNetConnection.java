/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.network.samples;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.v2.management.Azure;
import com.microsoft.azure.v2.management.compute.KnownLinuxVirtualMachineImage;
import com.microsoft.azure.v2.management.compute.VirtualMachine;
import com.microsoft.azure.v2.management.network.ConnectivityCheck;
import com.microsoft.azure.v2.management.network.Network;
import com.microsoft.azure.v2.management.network.NetworkWatcher;
import com.microsoft.azure.v2.management.network.Troubleshooting;
import com.microsoft.azure.v2.management.network.VirtualNetworkGateway;
import com.microsoft.azure.v2.management.network.VirtualNetworkGatewayConnection;
import com.microsoft.azure.v2.management.network.VirtualNetworkGatewaySkuName;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.v2.management.resources.fluentcore.model.CreatedResources;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.v2.management.samples.Utils;
import com.microsoft.azure.v2.management.storage.StorageAccount;
import com.microsoft.rest.v2.policy.HttpLogDetailLevel;
import com.microsoft.rest.v2.policy.HttpLoggingPolicyFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Azure Network sample for managing virtual network gateway.
 *  - Create 2 virtual networks with subnets and 2 virtual network gateways corresponding to each network
 *  - Create VPN VNet-to-VNet connection
 *  - Troubleshoot the connection
 *    - Create network watcher in the same region as virtual network gateway
 *    - Create storage account to store troubleshooting information
 *    - Run troubleshooting for the connection - result will be 'UnHealthy' as need to create symmetrical connection from second gateway to the first
 *  - Create virtual network connection from second gateway to the first and run troubleshooting. Result will be 'Healthy'.
 *  - List VPN Gateway connections for the first gateway
 *  - Create 2 virtual machines, each one in its network and verify connectivity between them
 */

public final class ManageVpnGatewayVNet2VNetConnection {

    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final Region region = Region.US_WEST2;
        final String rgName = SdkContext.randomResourceName("rg", 20);
        final String vnetName = SdkContext.randomResourceName("vnet", 20);
        final String vnet2Name = SdkContext.randomResourceName("vnet", 20);
        final String vpnGatewayName = SdkContext.randomResourceName("vngw", 20);
        final String vpnGateway2Name = SdkContext.randomResourceName("vngw2", 20);
        final String connectionName = SdkContext.randomResourceName("con", 20);
        final String connection2Name = SdkContext.randomResourceName("con2", 20);
        final String nwName = SdkContext.randomResourceName("nw", 20);
        final String vm1Name = SdkContext.randomResourceName("vm1", 20);
        final String vm2Name = SdkContext.randomResourceName("vm2", 20);
        final String rootname = "tirekicker";
        final String password = SdkContext.randomResourceName("pWd!", 15);
        final String storageContainerName = "results";

        try {
            //============================================================
            // Create 2 virtual networks with subnets and 2 virtual network gateways corresponding to each network
            System.out.println("Creating virtual network...");
            Network network1 = azure.networks().define(vnetName)
                    .withRegion(region)
                    .withNewResourceGroup(rgName)
                    .withAddressSpace("10.11.0.0/16")
                    .withSubnet("GatewaySubnet", "10.11.255.0/27")
                    .withSubnet("Subnet1", "10.11.0.0/24")
                    .create();
            System.out.println("Created network");
            // Print the virtual network
            Utils.print(network1);

            System.out.println("Creating virtual network gateway...");
            VirtualNetworkGateway vngw1 = azure.virtualNetworkGateways().define(vpnGatewayName)
                    .withRegion(region)
                    .withExistingResourceGroup(rgName)
                    .withExistingNetwork(network1)
                    .withRouteBasedVpn()
                    .withSku(VirtualNetworkGatewaySkuName.VPN_GW1)
                    .create();
            System.out.println("Created virtual network gateway");

            System.out.println("Creating virtual network...");
            Network network2 = azure.networks().define(vnet2Name)
                    .withRegion(region)
                    .withNewResourceGroup(rgName)
                    .withAddressSpace("10.41.0.0/16")
                    .withSubnet("GatewaySubnet", "10.41.255.0/27")
                    .withSubnet("Subnet2", "10.41.0.0/24")
                    .create();
            System.out.println("Created virtual network");

            System.out.println("Creating virtual network gateway...");
            VirtualNetworkGateway vngw2 = azure.virtualNetworkGateways().define(vpnGateway2Name)
                    .withRegion(region)
                    .withExistingResourceGroup(rgName)
                    .withExistingNetwork(network2)
                    .withRouteBasedVpn()
                    .withSku(VirtualNetworkGatewaySkuName.VPN_GW1)
                    .create();
            System.out.println("Created virtual network gateway");

            System.out.println("Creating virtual network gateway connection...");
            VirtualNetworkGatewayConnection connection = vngw1.connections()
                    .define(connectionName)
                    .withVNetToVNet()
                    .withSecondVirtualNetworkGateway(vngw2)
                    .withSharedKey("MySecretKey")
                    .create();
            System.out.println("Created virtual network gateway connection");

            //============================================================
            // Troubleshoot the connection

            // create Network Watcher
            NetworkWatcher nw = azure.networkWatchers().define(nwName)
                    .withRegion(region)
                    .withExistingResourceGroup(rgName)
                    .create();
            // Create storage account to store troubleshooting information
            StorageAccount storageAccount = azure.storageAccounts().define("sa" + SdkContext.randomResourceName("", 8))
                    .withRegion(region)
                    .withExistingResourceGroup(rgName)
                    .create();

            // Create storage container to store troubleshooting results
            String accountKey = storageAccount.getKeys().get(0).value();
            String connectionString = String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s", storageAccount.name(), accountKey);
            CloudStorageAccount account = CloudStorageAccount.parse(connectionString);
            CloudBlobClient cloudBlobClient = account.createCloudBlobClient();
            CloudBlobContainer container = cloudBlobClient.getContainerReference(storageContainerName);
            container.create();

            // Run troubleshooting for the connection - result will be 'UnHealthy' as need to create symmetrical connection from second gateway to the first
            Troubleshooting troubleshooting = nw.troubleshoot()
                    .withTargetResourceId(connection.id())
                    .withStorageAccount(storageAccount.id())
                    .withStoragePath(storageAccount.endPoints().primary().blob() + storageContainerName)
                    .execute();
            System.out.println("Troubleshooting status is: " + troubleshooting.code());

            //============================================================
            //  Create virtual network connection from second gateway to the first and run troubleshooting. Result will be 'Healthy'.
            vngw2.connections()
                    .define(connection2Name)
                    .withVNetToVNet()
                    .withSecondVirtualNetworkGateway(vngw1)
                    .withSharedKey("MySecretKey")
                    .create();
            SdkContext.sleep(250000);
            troubleshooting = nw.troubleshoot()
                    .withTargetResourceId(connection.id())
                    .withStorageAccount(storageAccount.id())
                    .withStoragePath(storageAccount.endPoints().primary().blob() + storageContainerName)
                    .execute();
            System.out.println("Troubleshooting status is: " + troubleshooting.code());

            //============================================================
            // List VPN Gateway connections for particular gateway
            List<VirtualNetworkGatewayConnection> connections = vngw1.listConnections();

            //============================================================
            // Create 2 virtual machines, each one in its network and verify connectivity between them
            List<Creatable<VirtualMachine>> vmDefinitions = new ArrayList<>();

            vmDefinitions.add(azure.virtualMachines().define(vm1Name)
                    .withRegion(region)
                    .withExistingResourceGroup(rgName)
                    .withExistingPrimaryNetwork(network1)
                    .withSubnet("Subnet1")
                    .withPrimaryPrivateIPAddressDynamic()
                    .withoutPrimaryPublicIPAddress()
                    .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                    .withRootUsername(rootname)
                    .withRootPassword(password)
                    // Extension currently needed for network watcher support
                    .defineNewExtension("networkWatcher")
                        .withPublisher("Microsoft.Azure.NetworkWatcher")
                        .withType("NetworkWatcherAgentLinux")
                        .withVersion("1.4")
                        .attach());
            vmDefinitions.add(azure.virtualMachines().define(vm2Name)
                    .withRegion(region)
                    .withExistingResourceGroup(rgName)
                    .withExistingPrimaryNetwork(network2)
                    .withSubnet("Subnet2")
                    .withPrimaryPrivateIPAddressDynamic()
                    .withoutPrimaryPublicIPAddress()
                    .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                    .withRootUsername(rootname)
                    .withRootPassword(password)
                    // Extension currently needed for network watcher support
                    .defineNewExtension("networkWatcher")
                        .withPublisher("Microsoft.Azure.NetworkWatcher")
                        .withType("NetworkWatcherAgentLinux")
                        .withVersion("1.4")
                        .attach());
            CreatedResources<VirtualMachine> createdVMs = azure.virtualMachines().create(vmDefinitions);
            VirtualMachine vm1 = createdVMs.get(vmDefinitions.get(0).key());
            VirtualMachine vm2 = createdVMs.get(vmDefinitions.get(1).key());

            ConnectivityCheck connectivity = nw.checkConnectivity()
                    .toDestinationResourceId(vm2.id())
                    .toDestinationPort(22)
                    .fromSourceVirtualMachine(vm1.id())
                    .execute();
            System.out.println("Connectivity status: " + connectivity.connectionStatus());
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
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

            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));

            Azure azure = Azure.configure()
                    .withRequestPolicy(new HttpLoggingPolicyFactory(HttpLogDetailLevel.BODY_AND_HEADERS))
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

    private ManageVpnGatewayVNet2VNetConnection() {
    }
}