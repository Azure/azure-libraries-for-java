/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.samples;


import com.azure.management.network.samples.CreateSimpleInternetFacingLoadBalancer;
import com.azure.management.network.samples.ManageApplicationGateway;
import com.azure.management.network.samples.ManageIPAddress;
import com.azure.management.network.samples.ManageInternalLoadBalancer;
import com.azure.management.network.samples.ManageInternetFacingLoadBalancer;
import com.azure.management.network.samples.ManageNetworkInterface;
import com.azure.management.network.samples.ManageNetworkPeeringInSameSubscription;
import com.azure.management.network.samples.ManageNetworkSecurityGroup;
import com.azure.management.network.samples.ManageSimpleApplicationGateway;
import com.azure.management.network.samples.ManageVirtualMachinesInParallelWithNetwork;
import com.azure.management.network.samples.ManageVirtualNetwork;
import com.azure.management.network.samples.ManageVirtualNetworkAsync;
import com.azure.management.network.samples.ManageVpnGatewayPoint2SiteConnection;
import com.azure.management.network.samples.ManageVpnGatewaySite2SiteConnection;
import com.azure.management.network.samples.VerifyNetworkPeeringWithNetworkWatcher;
import jdk.nashorn.internal.ir.annotations.Ignore;

public class NetworkSampleTests extends SamplesTestBase {

    @Test
    public void testManageNetworkPeeringInSameSubscription() {
        Assert.assertTrue(ManageNetworkPeeringInSameSubscription.runSample(azure));
    }

    @Test
    @Ignore("Get error `Cannot create more than 1 network watchers for this subscription in this region.` with test subscription")
    public void testVerifyNetworkPeeringWithNetworkWatcher() {
        Assert.assertTrue(VerifyNetworkPeeringWithNetworkWatcher.runSample(azure));
    }

    @Test
    public void testManageApplicationGateway() {
        Assert.assertTrue(ManageApplicationGateway.runSample(azure));
    }

    @Test
    public void testManageInternalLoadBalancer() {
        Assert.assertTrue(ManageInternalLoadBalancer.runSample(azure));
    }

    @Test
    public void testCreateSimpleInternetFacingLoadBalancer() {
        Assert.assertTrue(CreateSimpleInternetFacingLoadBalancer.runSample(azure));
    }

    @Test
    public void testManageInternetFacingLoadBalancer() {
        Assert.assertTrue(ManageInternetFacingLoadBalancer.runSample(azure));
    }

    @Test
    public void testManageIPAddress() {
        Assert.assertTrue(ManageIPAddress.runSample(azure));
    }

    @Test
    public void testManageNetworkInterface() {
        Assert.assertTrue(ManageNetworkInterface.runSample(azure));
    }

    @Test
    public void testManageNetworkSecurityGroup() {
        Assert.assertTrue(ManageNetworkSecurityGroup.runSample(azure));
    }

    @Test
    public void testManageSimpleApplicationGateway() {
        Assert.assertTrue(ManageSimpleApplicationGateway.runSample(azure));
    }

    @Test
    public void testManageVirtualMachinesInParallelWithNetwork() {
        Assert.assertTrue(ManageVirtualMachinesInParallelWithNetwork.runSample(azure));
    }

    @Test
    public void testManageVirtualNetwork() {
        Assert.assertTrue(ManageVirtualNetwork.runSample(azure));
    }

    @Test
    public void testManageVirtualNetworkAsync() {
        Assert.assertTrue(ManageVirtualNetworkAsync.runSample(azure));
    }

    @Test
    public void testManageVpnGatewaySite2SiteConnection() {
        Assert.assertTrue(ManageVpnGatewaySite2SiteConnection.runSample(azure));
    }

    @Test
    @Ignore("Need root certificate file and client certificate thumbprint to run the sample")
    public void testManageVpnGatewayPoint2SiteConnection() {
        Assert.assertTrue(ManageVpnGatewayPoint2SiteConnection.runSample(azure));
    }
}
