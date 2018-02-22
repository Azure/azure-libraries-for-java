/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management;

import com.microsoft.azure.management.network.NetworkWatcher;
import com.microsoft.azure.management.network.Troubleshooting;
import com.microsoft.azure.management.network.VirtualNetworkGateway;
import com.microsoft.azure.management.network.VirtualNetworkGatewayConnection;
import com.microsoft.azure.management.network.VirtualNetworkGatewaySkuName;
import com.microsoft.azure.management.resources.core.TestBase;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class VirtualNetworkGatewayTests extends TestBase {
    private Azure azure;

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        Azure.Authenticated azureAuthed = Azure.authenticate(restClient, defaultSubscription, domain);
        azure = azureAuthed.withSubscription(defaultSubscription);
    }

    @Override
    protected void cleanUpResources() {
    }

    @Test
    @Ignore("Service has bug that cause 'InternalServerError' - record this once service is fixed")
    //

    public void testNetworkWatcherTroubleshooting() throws Exception {
        String gatewayName = SdkContext.randomResourceName("vngw", 8);
        String connectionName = SdkContext.randomResourceName("vngwc", 8);

        TestNetworkWatcher tnw = new TestNetworkWatcher();
        NetworkWatcher nw = tnw.createResource(azure.networkWatchers());
        Region region = nw.region();
        String resourceGroup = nw.resourceGroupName();

        VirtualNetworkGateway vngw1 = azure.virtualNetworkGateways().define(gatewayName)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroup)
                .withNewNetwork("10.11.0.0/16", "10.11.255.0/27")
                .withRouteBasedVpn()
                .withSku(VirtualNetworkGatewaySkuName.VPN_GW1)
                .create();

        VirtualNetworkGateway vngw2 = azure.virtualNetworkGateways().define(gatewayName + "2")
                .withRegion(region)
                .withExistingResourceGroup(resourceGroup)
                .withNewNetwork("10.41.0.0/16", "10.41.255.0/27")
                .withRouteBasedVpn()
                .withSku(VirtualNetworkGatewaySkuName.VPN_GW1)
                .create();
        VirtualNetworkGatewayConnection connection1 = vngw1.connections()
                .define(connectionName)
                .withVNetToVNet()
                .withSecondVirtualNetworkGateway(vngw2)
                .withSharedKey("MySecretKey")
                .create();

        // Create storage account to store troubleshooting information
        StorageAccount storageAccount = azure.storageAccounts().define("sa" + SdkContext.randomResourceName("", 8))
                .withRegion(region)
                .withExistingResourceGroup(resourceGroup)
                .create();

        // Troubleshoot connection
        Troubleshooting troubleshooting = nw.troubleshoot()
                .withTargetResourceId(connection1.id())
                .withStorageAccount(storageAccount.id())
                .withStoragePath(storageAccount.endPoints().primary().blob() + "results")
                .execute();
        Assert.assertEquals("UnHealthy", troubleshooting.code());

        // Create corresponding connection on second gateway to make it work
        vngw2.connections()
                .define(connectionName + "2")
                .withVNetToVNet()
                .withSecondVirtualNetworkGateway(vngw1)
                .withSharedKey("MySecretKey")
                .create();
        SdkContext.sleep(250000);
        troubleshooting = nw.troubleshoot()
                .withTargetResourceId(connection1.id())
                .withStorageAccount(storageAccount.id())
                .withStoragePath(storageAccount.endPoints().primary().blob() + "results")
                .execute();
        Assert.assertEquals("Healthy", troubleshooting.code());

        azure.resourceGroups().deleteByName(resourceGroup);
    }

    /**
     * Tests the virtual network gateway implementation.
     * @throws Exception
     */
    @Test
    public void testVirtualNetworkGateways() throws Exception {
        new TestVirtualNetworkGateway.Basic(azure.virtualNetworkGateways().manager()).runTest(azure.virtualNetworkGateways(), azure.resourceGroups());
    }

    /**
     * Tests the virtual network gateway and virtual network gateway connection implementations for Site-to-Site connection.
     * @throws Exception
     */
    @Test
    public void testVirtualNetworkGatewaySiteToSite() throws Exception {
        new TestVirtualNetworkGateway.SiteToSite(azure.virtualNetworkGateways().manager())
                .runTest(azure.virtualNetworkGateways(), azure.resourceGroups());
    }

    /**
     * Tests the virtual network gateway and virtual network gateway connection implementations for VNet-to-VNet connection.
     * @throws Exception
     */
    @Test
    public void testVirtualNetworkGatewayVNetToVNet() throws Exception {
        new TestVirtualNetworkGateway.VNetToVNet(azure.virtualNetworkGateways().manager())
                .runTest(azure.virtualNetworkGateways(), azure.resourceGroups());
    }

    /**
     * Tests the virtual network gateway Point-to-Site connection.
     * @throws Exception
     */
    @Test
    public void testVirtualNetworkGatewayPointToSite() throws Exception {
        new TestVirtualNetworkGateway.PointToSite(azure.virtualNetworkGateways().manager())
                .runTest(azure.virtualNetworkGateways(), azure.resourceGroups());
    }

}
