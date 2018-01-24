/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management;

import com.microsoft.azure.management.network.LocalNetworkGateway;
import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.network.Subnet;
import com.microsoft.azure.management.network.VirtualNetworkGateway;
import com.microsoft.azure.management.network.VirtualNetworkGatewayConnection;
import com.microsoft.azure.management.network.VirtualNetworkGatewaySkuName;
import com.microsoft.azure.management.network.VirtualNetworkGateways;
import com.microsoft.azure.management.network.implementation.NetworkManager;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.Assert;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;


/**
 * Tests Virtual Network Gateway.
 */
public class TestVirtualNetworkGateway {
    private static String TEST_ID = "";
    private static Region REGION = Region.US_NORTH_CENTRAL;
    private static String GROUP_NAME;
    private static String GATEWAY_NAME1;
    private static String GATEWAY_NAME2;
    private static String NETWORK_NAME;
    private static String CONNECTION_NAME = "myNewConnection";

    private static void initializeResourceNames() {
        TEST_ID = SdkContext.randomResourceName("", 8);
        GROUP_NAME = "rg" + TEST_ID;
        GATEWAY_NAME1 = "vngw" + TEST_ID;
        GATEWAY_NAME2 = "vngw2" + TEST_ID;
        NETWORK_NAME = "nw" + TEST_ID;
    }

    /**
     * Test Virtual Network Gateway Create and Update.
     */
    public static class Basic extends TestTemplate<VirtualNetworkGateway, VirtualNetworkGateways> {

        public Basic(NetworkManager networkManager) {
            initializeResourceNames();
        }

        @Override
        public void print(VirtualNetworkGateway resource) {
            printVirtualNetworkGateway(resource);
        }

        @Override
        public VirtualNetworkGateway createResource(VirtualNetworkGateways gateways) throws Exception {
            VirtualNetworkGateway vngw = gateways.define(GATEWAY_NAME1)
                    .withRegion(REGION)
                    .withNewResourceGroup(GROUP_NAME)
                    .withNewNetwork("10.0.0.0/25", "10.0.0.0/27")
                    .withRouteBasedVpn()
                    .withSku(VirtualNetworkGatewaySkuName.VPN_GW1)
                    .withTag("tag1", "value1")
                    .create();
            return vngw;
        }

        @Override
        public VirtualNetworkGateway updateResource(VirtualNetworkGateway resource) throws Exception {
            resource.update()
                    .withSku(VirtualNetworkGatewaySkuName.VPN_GW2)
                    .withTag("tag2", "value2")
                    .withoutTag("tag1")
                    .apply();
            resource.refresh();
            Assert.assertTrue(resource.tags().containsKey("tag2"));
            Assert.assertTrue(!resource.tags().containsKey("tag1"));
            return resource;
        }
    }


    /**
     * Test Site-To-Site Virtual Network Gateway Connection.
     */
    public static class SiteToSite extends TestTemplate<VirtualNetworkGateway, VirtualNetworkGateways> {
        public SiteToSite(NetworkManager networkManager) {
            initializeResourceNames();
        }

        @Override
        public void print(VirtualNetworkGateway resource) {
            printVirtualNetworkGateway(resource);
        }

        @Override
        public VirtualNetworkGateway createResource(VirtualNetworkGateways gateways) throws Exception {

            // Create virtual network gateway
            initializeResourceNames();
            VirtualNetworkGateway vngw = gateways.define(GATEWAY_NAME1)
                    .withRegion(REGION)
                    .withNewResourceGroup()
                    .withNewNetwork("10.0.0.0/25", "10.0.0.0/27")
                    .withRouteBasedVpn()
                    .withSku(VirtualNetworkGatewaySkuName.VPN_GW1)
                    .withBgp(65010, "10.12.255.30")
                    .create();
            LocalNetworkGateway lngw = gateways.manager().localNetworkGateways().define("lngw" + TEST_ID)
                    .withRegion(vngw.region())
                    .withExistingResourceGroup(vngw.resourceGroupName())
                    .withIPAddress("40.71.184.214")
                    .withAddressSpace("192.168.3.0/24")
                    .withBgp(65050, "10.51.255.254")
                    .create();
            vngw.connections()
                    .define(CONNECTION_NAME)
                    .withSiteToSite()
                    .withLocalNetworkGateway(lngw)
                    .withSharedKey("MySecretKey")
                    .create();

            Assert.assertEquals(1, vngw.ipConfigurations().size());
            Subnet subnet = vngw.ipConfigurations().iterator().next().getSubnet();
            Assert.assertEquals("10.0.0.0/27", subnet.addressPrefix());
            Assert.assertTrue(vngw.isBgpEnabled());
            Assert.assertEquals("10.12.255.30", vngw.bgpSettings().bgpPeeringAddress());

            Assert.assertEquals("40.71.184.214", lngw.ipAddress());
            Assert.assertEquals(1, lngw.addressSpaces().size());
            Assert.assertEquals("192.168.3.0/24", lngw.addressSpaces().iterator().next());
            Assert.assertNotNull(lngw.bgpSettings());
            Assert.assertEquals("10.51.255.254", lngw.bgpSettings().bgpPeeringAddress());

            List<VirtualNetworkGatewayConnection> connections = vngw.listConnections();
            Assert.assertEquals(1, connections.size());
            Assert.assertEquals(vngw.id(), connections.get(0).virtualNetworkGateway1Id());
            Assert.assertEquals(lngw.id(), connections.get(0).localNetworkGateway2Id());
            return vngw;
        }

        @Override
        public VirtualNetworkGateway updateResource(VirtualNetworkGateway resource) throws Exception {
            VirtualNetworkGatewayConnection connection = resource.connections().getByName(CONNECTION_NAME);
            Assert.assertFalse(connection.isBgpEnabled());
            connection.update()
                    .withBgp()
                    .apply();
            Assert.assertTrue(connection.isBgpEnabled());

            resource.connections().deleteByName(CONNECTION_NAME);
            List<VirtualNetworkGatewayConnection> connections = resource.listConnections();
            Assert.assertEquals(0, connections.size());
            return resource;
        }
    }

    /**
     * Test VNet-to-VNet Virtual Network Gateway Connection.
     */
    public static class VNetToVNet extends TestTemplate<VirtualNetworkGateway, VirtualNetworkGateways> {

        public VNetToVNet(NetworkManager networkManager) {
            initializeResourceNames();
        }

        @Override
        public void print(VirtualNetworkGateway resource) {
            printVirtualNetworkGateway(resource);
        }

        @Override
        public VirtualNetworkGateway createResource(final VirtualNetworkGateways gateways) throws Exception {

            // Create virtual network gateway
            initializeResourceNames();
            final List<VirtualNetworkGateway> gws = new ArrayList<>();
            Observable<?> vngwObservable = gateways.define(GATEWAY_NAME1)
                    .withRegion(REGION)
                    .withNewResourceGroup(GROUP_NAME)
                    .withNewNetwork("10.11.0.0/16", "10.11.255.0/27")
                    .withRouteBasedVpn()
                    .withSku(VirtualNetworkGatewaySkuName.VPN_GW1)
                    .createAsync();

            Observable<?> vngw2Observable = gateways.define(GATEWAY_NAME2)
                    .withRegion(REGION)
                    .withNewResourceGroup(GROUP_NAME)
                    .withNewNetwork("10.41.0.0/16", "10.41.255.0/27")
                    .withRouteBasedVpn()
                    .withSku(VirtualNetworkGatewaySkuName.VPN_GW1)
                    .createAsync();

            Observable.merge(vngwObservable, vngw2Observable).map(new Func1<Object, Void>() {
                @Override
                public Void call(Object object) {
                    if (object instanceof VirtualNetworkGateway) {
                        gws.add((VirtualNetworkGateway) object);
                    }
                    return null;
                }
            }).toCompletable().await();
            VirtualNetworkGateway vngw1 = gws.get(0);
            VirtualNetworkGateway vngw2 = gws.get(1);
            vngw1.connections()
                    .define(CONNECTION_NAME)
                    .withVNetToVNet()
                    .withSecondVirtualNetworkGateway(vngw2)
                    .withSharedKey("MySecretKey")
                    .create();
            vngw2.connections()
                    .define(CONNECTION_NAME + "2")
                    .withVNetToVNet()
                    .withSecondVirtualNetworkGateway(vngw1)
                    .withSharedKey("MySecretKey")
                    .create();
            List<VirtualNetworkGatewayConnection> connections = vngw1.listConnections();
            Assert.assertEquals(1, connections.size());
            Assert.assertEquals(vngw1.id(), connections.get(0).virtualNetworkGateway1Id());
            Assert.assertEquals(vngw2.id(), connections.get(0).virtualNetworkGateway2Id());
            return vngw1;
        }

        @Override
        public VirtualNetworkGateway updateResource(VirtualNetworkGateway resource) throws Exception {
            resource.connections().deleteByName(CONNECTION_NAME);
            List<VirtualNetworkGatewayConnection> connections = resource.listConnections();
            Assert.assertEquals(0, connections.size());
            return resource;
        }
    }

    /**
     * Test VNet-to-VNet Virtual Network Gateway Connection.
     */
    public static class PointToSite extends TestTemplate<VirtualNetworkGateway, VirtualNetworkGateways> {

        public PointToSite(NetworkManager networkManager) {
            initializeResourceNames();
        }

        @Override
        public void print(VirtualNetworkGateway resource) {
            printVirtualNetworkGateway(resource);
        }

        @Override
        public VirtualNetworkGateway createResource(final VirtualNetworkGateways gateways) throws Exception {

            // Create virtual network gateway
            initializeResourceNames();

            Network network = gateways.manager().networks().define(NETWORK_NAME)
                    .withRegion(REGION)
                    .withNewResourceGroup(GROUP_NAME)
                    .withAddressSpace("192.168.0.0/16")
                    .withAddressSpace("10.254.0.0/16")
                    .withSubnet("GatewaySubnet", "192.168.200.0/24")
                    .withSubnet("FrontEnd", "192.168.1.0/24")
                    .withSubnet("BackEnd", "10.254.1.0/24")
                    .create();
            System.out.println("Created network");
            // Print the virtual network

            System.out.println("Creating virtual network gateway...");
            VirtualNetworkGateway vngw1 = gateways.define(GATEWAY_NAME1)
                    .withRegion(REGION)
                    .withExistingResourceGroup(GROUP_NAME)
                    .withExistingNetwork(network)
                    .withRouteBasedVpn()
                    .withSku(VirtualNetworkGatewaySkuName.VPN_GW1)
                    .create();
            System.out.println("Created virtual network gateway");

            vngw1.update()
                    .definePointToSiteConfiguration()
                    .withAddressPool("172.16.201.0/24")
                    .withAzureCertificate()
                    .withRootCertificate("myCertificate.cer", "MIIC5zCCAc+gAwIBAgIQUIcRQlcJRpJFiqjTDA/eaDANBgkqhkiG9w0BAQsFADAWMRQwEgYDVQQDDAtQMlNSb290Q2VydDAeFw0xODAxMjMwMDI0MjhaFw0xOTAxMjMwMDQ0MjhaMBYxFDASBgNVBAMMC1AyU1Jvb3RDZXJ0MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA9gGcJrS4a8Nb67To6Zilv0AG0Jw4ZmWXjT5an8peBbVrEjIVtTN5CbD2M9WJhBDi1GQH6hj1xzHltPQY0HRQCqfZ25zs20kBi1SJF5fE8q2t4q26jF7lUjAtwzniqHh8/Y+LqI9ldyV+Lxj0L7brDnkU4mryy3h86V60PsMSl7n2Y8ly1b3uT2mvd49wATCp7cs0bGweWopq+D6LaFSyWp1JoWTFFgi9KysN9D/auFwxdTqJH/IXCFaiAl/iKketERIA95wQ+BpDbuVux35fVMXE0GfiaubNBtf9/DmJBMjrWcy9BCYNBaJtzHq6vT9etej28V4sAW0PXOXwiOg9vQIDAQABozEwLzAOBgNVHQ8BAf8EBAMCAgQwHQYDVR0OBBYEFO5DxuEoPKP0uPMckMX6q90yEeMaMA0GCSqGSIb3DQEBCwUAA4IBAQACLZEizRDy0559l5EmkelKbH1n4rxxIS4ID7zlceVXNgvuoDKqWSdhYMilEoXtb1cZXNAylWH+9JoXt7QzjvM05NvvSegaIu8ndBrxBHtwDfVw+rGsghy4JQeIW0pAy9eshHDwXzoBZgC3uUlHDgnVZZ3o9Td+6uLKKRZNERGPu5uqpjylJmpEyyeb7zfv9cyPBB/L4BclIcM43pjJ7/yf1skWdinoIa1azdOAJgG6aVwDWtGAW+UO9SXb36dZrCl9W5ZqTjXJjjk1fP4fANpx+xeLbIo6JVxIKglF8SIDxmOHfFRxg8l48V1o5pLppQ50Q3JhvSPuNqWfQQMmrMOc")
                    .attach()
                    .apply();

            String profile = vngw1.generateVpnProfile();
            System.out.println(profile);
            return vngw1;
        }

        @Override
        public VirtualNetworkGateway updateResource(VirtualNetworkGateway vngw1) throws Exception {
            vngw1.update().updatePointToSiteConfiguration()
                    .withRevokedCertificate("myCertificate.cer", "bdf834528f0fff6eaae4c154e06b54322769276c")
                    .parent()
                    .apply();

            vngw1.update().updatePointToSiteConfiguration()
                    .withoutRootCertificate("myCertificate.cer")
                    .parent()
                    .apply();
            return vngw1;
        }
    }

    static void printVirtualNetworkGateway(VirtualNetworkGateway gateway) {
        StringBuilder info = new StringBuilder();
        info.append("Virtual Network Gateway: ").append(gateway.id())
                .append("\n\tName: ").append(gateway.name())
                .append("\n\tResource group: ").append(gateway.resourceGroupName())
                .append("\n\tRegion: ").append(gateway.regionName())
                .append("\n\tTags: ").append(gateway.tags());
        System.out.println(info.toString());
    }
}

