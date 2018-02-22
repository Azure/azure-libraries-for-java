/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management;

import com.microsoft.azure.management.compute.KnownLinuxVirtualMachineImage;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.network.ApplicationGateway;
import com.microsoft.azure.management.network.ApplicationGatewayBackend;
import com.microsoft.azure.management.network.ApplicationGatewayBackendHealth;
import com.microsoft.azure.management.network.ApplicationGatewayBackendHttpConfigurationHealth;
import com.microsoft.azure.management.network.ApplicationGatewayBackendServerHealth;
import com.microsoft.azure.management.network.ApplicationGatewayOperationalState;
import com.microsoft.azure.management.network.ApplicationGatewayRequestRoutingRule;
import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.network.NetworkInterface;
import com.microsoft.azure.management.network.NicIPConfiguration;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.core.TestBase;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.CreatedResources;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApplicationGatewayTests extends TestBase {
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
     * Tests a complex internal application gateway.
     * @throws Exception
     */
    @Test
    public void testAppGatewaysInternalComplex() throws Exception {
        new TestApplicationGateway.PrivateComplex()
                .runTest(azure.applicationGateways(),  azure.resourceGroups());
    }

    @Test
    public void testAppGatewayBackendHealthCheck() throws Exception {
        String testId = SdkContext.randomResourceName("", 15);
        String name = "ag" + testId;
        Region region = Region.US_EAST;
        String password = SdkContext.randomResourceName("Abc.123", 12);
        String vnetName = "net" + testId;
        String rgName = "rg" + testId;

        try {
            // Create a vnet
            Network network = azure.networks().define(vnetName)
                    .withRegion(region)
                    .withNewResourceGroup(rgName)
                    .withAddressSpace("10.0.0.0/28")
                    .withSubnet("subnet1", "10.0.0.0/29")
                    .withSubnet("subnet2", "10.0.0.8/29")
                    .create();

            // Create VMs for the backend in the network to connect to
            List<Creatable<VirtualMachine>> vmsDefinitions = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                vmsDefinitions.add(azure.virtualMachines().define("vm" + i + testId)
                        .withRegion(region)
                        .withExistingResourceGroup(rgName)
                        .withExistingPrimaryNetwork(network)
                        .withSubnet("subnet2")
                        .withPrimaryPrivateIPAddressDynamic()
                        .withoutPrimaryPublicIPAddress()
                        .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                        .withRootUsername("tester")
                        .withRootPassword(password));
            }

            CreatedResources<VirtualMachine> createdVms = azure.virtualMachines().create(vmsDefinitions);
            VirtualMachine[] vms = new VirtualMachine[createdVms.size()];
            for (int i = 0; i < vmsDefinitions.size(); i++) {
                vms[i] = createdVms.get(vmsDefinitions.get(i).key());
            }

            String[] ipAddresses = new String[vms.length];
            for (int i = 0; i < vms.length; i++) {
                ipAddresses[i] = vms[i].getPrimaryNetworkInterface().primaryPrivateIP();
            }

            // Create the app gateway in the other subnet of the same vnet and point the backend at the VMs
            ApplicationGateway appGateway = azure.applicationGateways().define(name)
                    .withRegion(region)
                    .withExistingResourceGroup(rgName)
                    .defineRequestRoutingRule("rule1")
                    .fromPrivateFrontend()
                    .fromFrontendHttpPort(80)
                    .toBackendHttpPort(8080)
                    .toBackendIPAddresses(ipAddresses) // Connect the VMs via IP addresses
                    .attach()
                    .defineRequestRoutingRule("rule2")
                    .fromPrivateFrontend()
                    .fromFrontendHttpPort(25)
                    .toBackendHttpPort(22)
                    .toBackend("nicBackend")
                    .attach()
                    .withExistingSubnet(network.subnets().get("subnet1")) // Backend for connecting the VMs via NICs
                    .create();

            // Connect the 1st VM via NIC IP config
            NetworkInterface nic = vms[0].getPrimaryNetworkInterface();
            Assert.assertNotNull(nic);
            ApplicationGatewayBackend appGatewayBackend = appGateway.backends().get("nicBackend");
            Assert.assertNotNull(appGatewayBackend);
            nic.update().updateIPConfiguration(nic.primaryIPConfiguration().name())
                    .withExistingApplicationGatewayBackend(appGateway, appGatewayBackend.name())
                    .parent()
                    .apply();

            // Get the health of the VMs
            appGateway.refresh();
            Map<String, ApplicationGatewayBackendHealth> backendHealths = appGateway.checkBackendHealth();

            StringBuilder info = new StringBuilder();
            info.append("\nApplication gateway backend healths: ").append(backendHealths.size());
            for (ApplicationGatewayBackendHealth backendHealth : backendHealths.values()) {
                info.append("\n\tApplication gateway backend name: ").append(backendHealth.name())
                        .append("\n\t\tHTTP configuration healths: ").append(backendHealth.httpConfigurationHealths().size());
                Assert.assertNotNull(backendHealth.backend());
                for (ApplicationGatewayBackendHttpConfigurationHealth backendConfigHealth : backendHealth.httpConfigurationHealths().values()) {
                    info.append("\n\t\t\tHTTP configuration name: ").append(backendConfigHealth.name())
                            .append("\n\t\t\tServers: ").append(backendConfigHealth.inner().servers().size());
                    Assert.assertNotNull(backendConfigHealth.backendHttpConfiguration());
                    for (ApplicationGatewayBackendServerHealth serverHealth : backendConfigHealth.serverHealths().values()) {
                        NicIPConfiguration ipConfig = serverHealth.getNetworkInterfaceIPConfiguration();
                        if (ipConfig != null) {
                            info.append("\n\t\t\t\tServer NIC ID: ").append(ipConfig.parent().id())
                                    .append("\n\t\t\t\tIP Config name: ").append(ipConfig.name());
                        } else {
                            info.append("\n\t\t\t\tServer IP: " + serverHealth.ipAddress());
                        }
                        info.append("\n\t\t\t\tHealth status: ").append(serverHealth.status());
                    }
                }
            }
            System.out.println(info.toString());

            // Verify app gateway
            Assert.assertEquals(2,  appGateway.backends().size());
            ApplicationGatewayRequestRoutingRule rule1 = appGateway.requestRoutingRules().get("rule1");
            Assert.assertNotNull(rule1);
            ApplicationGatewayBackend backend1 = rule1.backend();
            Assert.assertNotNull(backend1);
            ApplicationGatewayRequestRoutingRule rule2 = appGateway.requestRoutingRules().get("rule2");
            Assert.assertNotNull(rule2);
            ApplicationGatewayBackend backend2 = rule2.backend();
            Assert.assertNotNull(backend2);

            Assert.assertEquals(2, backendHealths.size());

            // Verify first backend (IP address-based)
            ApplicationGatewayBackendHealth backendHealth1 = backendHealths.get(backend1.name());
            Assert.assertNotNull(backendHealth1);
            Assert.assertNotNull(backendHealth1.backend());
            for (int i = 0; i < ipAddresses.length; i++) {
                Assert.assertTrue(backend1.containsIPAddress(ipAddresses[i]));
            }

            // Verify second backend (NIC based)
            ApplicationGatewayBackendHealth backendHealth2 = backendHealths.get(backend2.name());
            Assert.assertNotNull(backendHealth2);
            Assert.assertNotNull(backendHealth2.backend());
            Assert.assertEquals(backend2.name(), backendHealth2.name());
            Assert.assertEquals(1,  backendHealth2.httpConfigurationHealths().size());
            ApplicationGatewayBackendHttpConfigurationHealth httpConfigHealth2 = backendHealth2.httpConfigurationHealths().values().iterator().next();
            Assert.assertNotNull(httpConfigHealth2.backendHttpConfiguration());
            Assert.assertEquals(1, httpConfigHealth2.serverHealths().size());
            ApplicationGatewayBackendServerHealth serverHealth = httpConfigHealth2.serverHealths().values().iterator().next();
            NicIPConfiguration ipConfig2 = serverHealth.getNetworkInterfaceIPConfiguration();
            Assert.assertEquals(nic.primaryIPConfiguration().name(), ipConfig2.name());
        } catch (Exception e) {
            throw e;
        } finally {
            if (azure.resourceGroups().contain(rgName)) {
                azure.resourceGroups().beginDeleteByName(rgName);
            }
        }
    }

    /**
     * Tests a minimal internal application gateway
     * @throws Exception
     */
    @Test
    public void testAppGatewaysInternalMinimal() throws Exception {
        new TestApplicationGateway.PrivateMinimal()
                .runTest(azure.applicationGateways(),  azure.resourceGroups());
    }

    @Test
    public void testAppGatewaysStartStop() throws Exception {
        String rgName = SdkContext.randomResourceName("rg", 13);
        Region region = Region.US_EAST;
        String name = SdkContext.randomResourceName("ag", 15);
        ApplicationGateway appGateway = azure.applicationGateways().define(name)
                .withRegion(region)
                .withNewResourceGroup(rgName)

                // Request routing rules
                .defineRequestRoutingRule("rule1")
                .fromPrivateFrontend()
                .fromFrontendHttpPort(80)
                .toBackendHttpPort(8080)
                .toBackendIPAddress("11.1.1.1")
                .toBackendIPAddress("11.1.1.2")
                .attach()
                .create();

        // Test stop/start
        appGateway.stop();
        Assert.assertEquals(ApplicationGatewayOperationalState.STOPPED, appGateway.operationalState());
        appGateway.start();
        Assert.assertEquals(ApplicationGatewayOperationalState.RUNNING, appGateway.operationalState());

        azure.resourceGroups().beginDeleteByName(rgName);
    }

    @Test
    public void testApplicationGatewaysInParallel() throws Exception {
        String rgName = SdkContext.randomResourceName("rg", 13);
        Region region = Region.US_EAST;
        Creatable<ResourceGroup> resourceGroup = azure.resourceGroups().define(rgName)
                .withRegion(region);
        List<Creatable<ApplicationGateway>> agCreatables = new ArrayList<>();

        agCreatables.add(azure.applicationGateways().define(SdkContext.randomResourceName("ag", 13))
                .withRegion(Region.US_EAST)
                .withNewResourceGroup(resourceGroup)
                .defineRequestRoutingRule("rule1")
                .fromPrivateFrontend()
                .fromFrontendHttpPort(80)
                .toBackendHttpPort(8080)
                .toBackendIPAddress("10.0.0.1")
                .toBackendIPAddress("10.0.0.2")
                .attach());

        agCreatables.add(azure.applicationGateways().define(SdkContext.randomResourceName("ag", 13))
                .withRegion(Region.US_EAST)
                .withNewResourceGroup(resourceGroup)
                .defineRequestRoutingRule("rule1")
                .fromPrivateFrontend()
                .fromFrontendHttpPort(80)
                .toBackendHttpPort(8080)
                .toBackendIPAddress("10.0.0.3")
                .toBackendIPAddress("10.0.0.4")
                .attach());

        CreatedResources<ApplicationGateway> created = azure.applicationGateways().create(agCreatables);
        List<ApplicationGateway> ags = new ArrayList<>();
        List<String> agIds = new ArrayList<>();
        for (Creatable<ApplicationGateway> creatable : agCreatables) {
            ApplicationGateway ag = created.get(creatable.key());
            Assert.assertNotNull(ag);
            ags.add(ag);
            agIds.add(ag.id());
        }

        azure.applicationGateways().stop(agIds);

        for (ApplicationGateway ag : ags) {
            Assert.assertEquals(ApplicationGatewayOperationalState.STOPPED, ag.refresh().operationalState());
        }

        azure.applicationGateways().start(agIds);

        for (ApplicationGateway ag : ags) {
            Assert.assertEquals(ApplicationGatewayOperationalState.RUNNING, ag.refresh().operationalState());
        }

        azure.applicationGateways().deleteByIds(agIds);
        for (String id : agIds) {
            Assert.assertNull(azure.applicationGateways().getById(id));
        }

        azure.resourceGroups().beginDeleteByName(rgName);
    }

    /**
     * Tests a minimal Internet-facing application gateway.
     * @throws Exception
     */
    @Test
    public void testAppGatewaysInternetFacingMinimal() throws Exception {
        new TestApplicationGateway.PublicMinimal()
                .runTest(azure.applicationGateways(),  azure.resourceGroups());
    }

    /**
     * Tests a complex Internet-facing application gateway.
     * @throws Exception
     */
    @Test
    public void testAppGatewaysInternetFacingComplex() throws Exception {
        new TestApplicationGateway.PublicComplex()
                .runTest(azure.applicationGateways(),  azure.resourceGroups());
    }
}
