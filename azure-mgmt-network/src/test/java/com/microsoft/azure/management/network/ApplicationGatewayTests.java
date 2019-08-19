
/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.network;

import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

public class ApplicationGatewayTests extends NetworkManagementTest {

    @Test
    public void canCRUDApplicationGatewayWithWAF() throws Exception {
        String appGatewayName = SdkContext.randomResourceName("agwaf", 15);
        String appPublicIp = SdkContext.randomResourceName("pip", 15);
        PublicIPAddress pip =
                networkManager.publicIPAddresses()
                        .define(appPublicIp)
                        .withRegion(Region.US_EAST)
                        .withNewResourceGroup(RG_NAME)
                        .withSku(PublicIPSkuType.STANDARD)
                        .withStaticIP()
                        .create();

        ApplicationGateway appGateway = networkManager.applicationGateways().define(appGatewayName)
                .withRegion(Region.US_EAST)
                .withExistingResourceGroup(RG_NAME)
                // Request routing rules
                .defineRequestRoutingRule("rule1")
                .fromPublicFrontend()
                .fromFrontendHttpsPort(443)
                .withSslCertificateFromPfxFile(new File(getClass().getClassLoader().getResource("myTest.pfx").getFile()))
                .withSslCertificatePassword("Abc123")
                .toBackendHttpPort(8080)
                .toBackendIPAddress("11.1.1.1")
                .toBackendIPAddress("11.1.1.2")
                .attach()
                .withExistingPublicIPAddress(pip)
                .withTier(ApplicationGatewayTier.WAF_V2)
                .withSize(ApplicationGatewaySkuName.WAF_V2)
                .withAutoScale(2, 5)
                .withWebApplicationFirewall(true, ApplicationGatewayFirewallMode.PREVENTION)
                .create();

        Assert.assertTrue(appGateway != null);
        Assert.assertTrue(ApplicationGatewayTier.WAF_V2.equals(appGateway.tier()));
        Assert.assertTrue(ApplicationGatewaySkuName.WAF_V2.equals(appGateway.size()));
        Assert.assertTrue(appGateway.autoscaleConfiguration().minCapacity() == 2);
        Assert.assertTrue(appGateway.autoscaleConfiguration().maxCapacity() == 5);


        ApplicationGatewayWebApplicationFirewallConfiguration config = appGateway.webApplicationFirewallConfiguration();
        config.withFileUploadLimitInMb(200);
        config.withDisabledRuleGroups(Arrays.asList(
                new ApplicationGatewayFirewallDisabledRuleGroup()
                        .withRuleGroupName("REQUEST-943-APPLICATION-ATTACK-SESSION-FIXATION")));
        config.withRequestBodyCheck(true);
        config.withMaxRequestBodySizeInKb(64);
        config.withExclusions(Arrays.asList(
                new ApplicationGatewayFirewallExclusion()
                        .withMatchVariable("RequestHeaderNames")
                        .withSelectorMatchOperator("StartsWith")
                        .withSelector("User-Agent")));
        appGateway.update()
                .withWebApplicationFirewall(config)
                .apply();

        appGateway.refresh();

        // Verify WAF
        Assert.assertTrue(appGateway.webApplicationFirewallConfiguration().fileUploadLimitInMb() == 200);
        Assert.assertTrue(appGateway.webApplicationFirewallConfiguration().requestBodyCheck());
        Assert.assertEquals(appGateway.webApplicationFirewallConfiguration().maxRequestBodySizeInKb(), (Integer) 64);

        Assert.assertEquals(appGateway.webApplicationFirewallConfiguration().exclusions().size(), 1);

        Assert.assertEquals(appGateway.webApplicationFirewallConfiguration().exclusions().get(0).matchVariable(), "RequestHeaderNames");
        Assert.assertEquals(appGateway.webApplicationFirewallConfiguration().exclusions().get(0).selectorMatchOperator(), "StartsWith");
        Assert.assertEquals(appGateway.webApplicationFirewallConfiguration().exclusions().get(0).selector(), "User-Agent");

        Assert.assertEquals(appGateway.webApplicationFirewallConfiguration().disabledRuleGroups().size(), 1);
        Assert.assertEquals(appGateway.webApplicationFirewallConfiguration().disabledRuleGroups().get(0).ruleGroupName(),
                "REQUEST-943-APPLICATION-ATTACK-SESSION-FIXATION");
    }
}
