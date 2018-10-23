/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.samples;

import com.microsoft.azure.v2.management.network.samples.CreateSimpleInternetFacingLoadBalancer;
import com.microsoft.azure.v2.management.network.samples.ManageApplicationGateway;
import com.microsoft.azure.v2.management.network.samples.ManageIPAddress;
import com.microsoft.azure.v2.management.network.samples.ManageInternalLoadBalancer;
import com.microsoft.azure.v2.management.network.samples.ManageInternetFacingLoadBalancer;
import com.microsoft.azure.v2.management.network.samples.ManageNetworkInterface;
import com.microsoft.azure.v2.management.network.samples.ManageNetworkPeeringInSameSubscription;
import com.microsoft.azure.v2.management.network.samples.ManageNetworkSecurityGroup;
import com.microsoft.azure.v2.management.network.samples.ManageSimpleApplicationGateway;
import com.microsoft.azure.v2.management.network.samples.ManageVirtualMachinesInParallelWithNetwork;
import com.microsoft.azure.v2.management.network.samples.ManageVirtualNetwork;
import com.microsoft.azure.v2.management.network.samples.ManageVirtualNetworkAsync;
import com.microsoft.azure.v2.management.network.samples.ManageVpnGatewayPoint2SiteConnection;
import com.microsoft.azure.v2.management.network.samples.ManageVpnGatewaySite2SiteConnection;
import com.microsoft.azure.v2.management.network.samples.VerifyNetworkPeeringWithNetworkWatcher;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class NetworkSampleTests extends SamplesTestBase {

    @Test
    public void testManageNetworkPeeringInSameSubscription() {
        Assert.assertTrue(ManageNetworkPeeringInSameSubscription.runSample(azure));
    }

    @Ignore("Fails with 500 internal server error. Same error in v1 as well.")
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
