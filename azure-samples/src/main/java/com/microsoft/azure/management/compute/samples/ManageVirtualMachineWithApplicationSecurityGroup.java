package com.microsoft.azure.management.compute.samples;

import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.compute.KnownWindowsVirtualMachineImage;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.network.ApplicationSecurityGroup;
import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.network.NetworkInterface;
import com.microsoft.azure.management.network.NetworkSecurityGroup;
import com.microsoft.azure.management.network.SecurityRuleProtocol;
import com.microsoft.azure.management.network.implementation.ApplicationSecurityGroupInner;
import com.microsoft.azure.management.network.implementation.NetworkInterfaceIPConfigurationInner;
import com.microsoft.azure.management.network.implementation.NetworkInterfaceInner;
import com.microsoft.azure.management.network.implementation.SubnetInner;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.rest.LogLevel;

import java.io.File;
import java.util.ArrayList;

/**
 * Azure Compute sample for managing virtual machines with application security group -
 *  - Create a resource group
 *  - Create a virtual network
 *  - Create an application security group for web server
 *  - Create another application security group for DB server
 *  - Create a network security group with application security groups binding to the security rules
 *  - Create network interface with the network security group
 *  - Create virtual machine with the network interface
 */
public class ManageVirtualMachineWithApplicationSecurityGroup {
    public static boolean runSample(Azure azure) {
        final Region region = Region.ASIA_SOUTHEAST;
        final String rgName = Utils.createRandomName("rgCOMV");
        final String networkName = Utils.createRandomName("vnetCOMV");
        final String subnetName = Utils.createRandomName("subnetCOMV");
        final String webServerGroupName = Utils.createRandomName("wsgCOMV");
        final String dbServerGroupName = Utils.createRandomName("dsgCOMV");
        final String nsgName = Utils.createRandomName("nsgCOMV");
        final String networkInterfaceName = Utils.createRandomName("niCOMV");
        final String webServerRuleName = Utils.createRandomName("wsrCOMV");
        final String dbServerRuleName = Utils.createRandomName("dsrCOMV");
        final String vmName = Utils.createRandomName("vmCOMV");
        final String userName = "tirekicker";
        // [SuppressMessage("Microsoft.Security", "CS002:SecretInNextLine", Justification="Serves as an example, not for deployment. Please change when using this in your code.")]
        final String password = "12NewPA$$w0rd!";
        try {
            //=============================================================
            // Create a resource group

            ResourceGroup resourceGroup = azure.resourceGroups().define(rgName)
                    .withRegion(region)
                    .create();

            System.out.println("Created a resource group:" + resourceGroup.id());
            Utils.print(resourceGroup);

            //=============================================================
            // Create a virtual network

            Network network = azure.networks().define(networkName)
                    .withRegion(region)
                    .withExistingResourceGroup(rgName)
                    .withAddressSpace("10.2.0.0/16")
                    .withSubnet(subnetName, "10.2.0.0/24")
                    .create();

            System.out.println("Created a virtual network:" + network.id());
            Utils.print(network);

            //=============================================================
            // Create an application security group for web server

            ApplicationSecurityGroup webServerGroup = azure.applicationSecurityGroups().define(webServerGroupName)
                    .withRegion(region)
                    .withExistingResourceGroup(rgName)
                    .create();

            System.out.println("Created an application security group for web server:" + webServerGroup.id());

            //=============================================================
            // Create another application security group for DB server

            ApplicationSecurityGroup dbServerGroup = azure.applicationSecurityGroups().define(dbServerGroupName)
                    .withRegion(region)
                    .withExistingResourceGroup(rgName)
                    .create();

            System.out.println("Created another application security group for DB server:" + dbServerGroup.id());

            //=============================================================
            // Create a network security group with application security groups binding to the security rules

            NetworkSecurityGroup nsg = azure.networkSecurityGroups().define(nsgName)
                    .withRegion(region)
                    .withExistingResourceGroup(rgName)
                    .defineRule(webServerRuleName)
                        .allowInbound()
                        .fromAddress("Internet")
                        .fromAnyPort()
                        .withDestinationApplicationSecurityGroup(webServerGroup.id())
                        .toPort(443)
                        .withProtocol(SecurityRuleProtocol.TCP)
                        .withPriority(110)
                        .attach()
                    .defineRule(dbServerRuleName)
                        .allowInbound()
                        .withSourceApplicationSecurityGroup(webServerGroup.id())
                        .fromAnyPort()
                        .withDestinationApplicationSecurityGroup(dbServerGroup.id())
                        .toPort(1433)
                        .withProtocol(SecurityRuleProtocol.TCP)
                        .withPriority(120)
                        .attach()
                    .create();

            System.out.println("Created a network security group:" + nsg.id());
            Utils.print(nsg);

            //=============================================================
            // Create network interface with the network security group

            NetworkInterfaceInner networkInterfaceInner = new NetworkInterfaceInner()
                    .withIpConfigurations(new ArrayList<NetworkInterfaceIPConfigurationInner>());
            // Set location
            networkInterfaceInner.withLocation(region.name());
            // Prepare SubnetInner
            SubnetInner subnetInner = new SubnetInner();
            subnetInner.withId(network.subnets().get(subnetName).inner().id());
            // Add SubnetInner
            networkInterfaceInner.ipConfigurations().add(
                    new NetworkInterfaceIPConfigurationInner()
                        .withSubnet(subnetInner)
                        .withApplicationSecurityGroups(new ArrayList<ApplicationSecurityGroupInner>())
            );
            // Set as primary IP Config
            networkInterfaceInner.ipConfigurations().get(0).withPrimary(true).withName("primary");
            // Bind ApplicationSecurityGroup
            networkInterfaceInner.ipConfigurations().get(0).applicationSecurityGroups()
                    .add(new ApplicationSecurityGroupInner().withId(webServerGroup.id()));
            // Create network interface and get NetworkInterfaceInner
            NetworkInterfaceInner inner = azure.networkInterfaces().inner()
                    .createOrUpdate(rgName, networkInterfaceName, networkInterfaceInner);
            // Get NetworkInterface to create VM in next step
            NetworkInterface networkInterface = azure.networkInterfaces().getById(inner.id());

            System.out.println("Created a network interface:" + networkInterface.id());
            Utils.print(networkInterface);

            //=============================================================
            // Create virtual machine with the network interface

            VirtualMachine vm = azure.virtualMachines().define(vmName)
                    .withRegion(region)
                    .withExistingResourceGroup(rgName)
                    .withExistingPrimaryNetworkInterface(networkInterface)
                    .withPopularWindowsImage(KnownWindowsVirtualMachineImage.WINDOWS_SERVER_2008_R2_SP1)
                    .withAdminUsername(userName)
                    .withAdminPassword(password)
                    .create();

            System.out.println("Created a Windows VM:" + vm.id());
            Utils.print(vm);

            return true;
        } catch (Exception f) {

            System.out.println(f.getMessage());
            f.printStackTrace();

        } finally {
            try {
                System.out.println("Deleting Resource Group: " + rgName);
                azure.resourceGroups().deleteByName(rgName);
                System.out.println("Deleted Resource Group: " + rgName);
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
                    .withLogLevel(LogLevel.BASIC)
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

    private ManageVirtualMachineWithApplicationSecurityGroup() {

    }
}
