/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network;

import com.azure.management.network.implementation.NetworkManager;
import com.azure.management.resources.ResourceGroup;
import com.azure.management.resources.fluentcore.arm.Region;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.Assert;
import org.junit.Test;

public class LoadBalancerTests extends NetworkManagementTest {

    @Test
    public void canCRUDProbe() throws Exception {
        String vmName = SdkContext.randomResourceName("vm", 8);
        String lbName = SdkContext.randomResourceName("lb", 8);

        ResourceGroup resourceGroup = resourceManager.resourceGroups().define(RG_NAME)
                .withRegion(Region.US_EAST)
                .create();

        Network network = networkManager.networks().define(vmName)
                .withRegion(resourceGroup.region())
                .withExistingResourceGroup(resourceGroup.name())
                .withAddressSpace("172.18.0.0/28")
                .withSubnet(subnetName, "172.18.0.0/28")
                .create();

        LoadBalancer loadBalancer = this.createLoadBalancer(networkManager, resourceGroup, network, lbName);

        // verify created probes
        loadBalancer.refresh();
        Assert.assertEquals(2, loadBalancer.loadBalancingRules().size());
        Assert.assertEquals(0, loadBalancer.tcpProbes().size());
        Assert.assertEquals(1, loadBalancer.httpProbes().size());
        Assert.assertEquals(1, loadBalancer.httpsProbes().size());
        LoadBalancerHttpProbe httpProbe = loadBalancer.httpProbes().get(probeName1);
        Assert.assertNotNull(httpProbe);
        Assert.assertEquals(1, httpProbe.loadBalancingRules().size());
        LoadBalancerHttpProbe httpsProbe = loadBalancer.httpsProbes().get(probeName2);
        Assert.assertEquals(1, httpsProbe.loadBalancingRules().size());
        // verify https probe
        Assert.assertEquals(ProbeProtocol.HTTPS, httpsProbe.protocol());
        Assert.assertEquals(443, httpsProbe.port());
        Assert.assertEquals("/", httpsProbe.requestPath());

        // update probe
        loadBalancer.update()
                .updateHttpsProbe(probeName2)
                .withIntervalInSeconds(60)
                .withRequestPath("/health")
                .parent().apply();

        // verify probe updated
        loadBalancer.refresh();
        Assert.assertEquals(1, loadBalancer.httpProbes().size());
        Assert.assertEquals(1, loadBalancer.httpsProbes().size());
        Assert.assertEquals(1, httpProbe.loadBalancingRules().size());
        httpsProbe = loadBalancer.httpsProbes().get(probeName2);
        Assert.assertEquals(1, httpsProbe.loadBalancingRules().size());
        Assert.assertEquals(ProbeProtocol.HTTPS, httpsProbe.protocol());
        Assert.assertEquals(443, httpsProbe.port());
        Assert.assertEquals(60, httpsProbe.intervalInSeconds());
        Assert.assertEquals("/health", httpsProbe.requestPath());

        // delete probe
        loadBalancer.update()
                .withoutProbe(probeName2)
                .apply();

        // verify probe deleted (and deref from rule)
        loadBalancer.refresh();
        Assert.assertEquals(1, loadBalancer.httpProbes().size());
        Assert.assertEquals(0, loadBalancer.httpsProbes().size());
        Assert.assertNull(loadBalancer.loadBalancingRules().get(ruleName2).probe());

        // add probe
        loadBalancer.update()
                .defineHttpsProbe(probeName2)
                .withRequestPath("/")
                .attach()
                .apply();

        // verify probe added
        loadBalancer.refresh();
        Assert.assertEquals(1, loadBalancer.httpProbes().size());
        Assert.assertEquals(1, loadBalancer.httpsProbes().size());
        httpsProbe = loadBalancer.httpsProbes().get(probeName2);
        Assert.assertEquals(0, httpsProbe.loadBalancingRules().size());
    }

    static final String subnetName = "subnet1";
    static final String ruleName1 = "httpRule";
    static final String ruleName2 = "httpsRule";
    static final String probeName1 = "httpProbe";
    static final String probeName2 = "httpsProbe";

    private static LoadBalancer createLoadBalancer(NetworkManager networkManager, ResourceGroup resourceGroup, Network network, String lbName) {
        final String frontendName = lbName + "-FE1";
        final String backendPoolName1 = lbName + "-BAP1";
        final String backendPoolName2 = lbName + "-BAP2";
        final String natPool50XXto22 = lbName + "natPool50XXto22";
        final String natPool60XXto23 = lbName + "natPool60XXto23";

        LoadBalancer loadBalancer1 = networkManager.loadBalancers().define(lbName)
                .withRegion(resourceGroup.region())
                .withExistingResourceGroup(resourceGroup.name())
                // Add two rules that uses above backend and probe
                .defineLoadBalancingRule(ruleName1)
                .withProtocol(TransportProtocol.TCP)
                .fromFrontend(frontendName)
                .fromFrontendPort(80)
                .toBackend(backendPoolName1)
                .withProbe(probeName1)
                .attach()
                .defineLoadBalancingRule(ruleName2)
                .withProtocol(TransportProtocol.TCP)
                .fromFrontend(frontendName)
                .fromFrontendPort(443)
                .toBackend(backendPoolName2)
                .withProbe(probeName2)
                .attach()
                // Add nat pools to enable direct VM connectivity for
                //  SSH to port 22 and TELNET to port 23
                .defineInboundNatPool(natPool50XXto22)
                .withProtocol(TransportProtocol.TCP)
                .fromFrontend(frontendName)
                .fromFrontendPortRange(5000, 5099)
                .toBackendPort(22)
                .attach()
                .defineInboundNatPool(natPool60XXto23)
                .withProtocol(TransportProtocol.TCP)
                .fromFrontend(frontendName)
                .fromFrontendPortRange(6000, 6099)
                .toBackendPort(23)
                .attach()
                // Explicitly define the frontend
                .definePrivateFrontend(frontendName)
                .withExistingSubnet(network, subnetName)
                .attach()
                // Add two probes one per rule
                .defineHttpProbe(probeName1)
                .withRequestPath("/")
                .attach()
                .defineHttpsProbe(probeName2)
                .withRequestPath("/")
                .attach()
                .withSku(LoadBalancerSkuType.STANDARD)
                .create();

        return loadBalancer1;
    }
}
