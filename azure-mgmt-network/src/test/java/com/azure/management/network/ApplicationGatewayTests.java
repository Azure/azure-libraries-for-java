
/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.network;

import com.azure.core.util.serializer.JacksonAdapter;
import com.azure.core.util.serializer.SerializerEncoding;
import com.azure.management.ApplicationTokenCredential;
import com.azure.management.keyvault.Secret;
import com.azure.management.keyvault.Vault;
import com.azure.management.msi.Identity;
import com.azure.management.resources.fluentcore.arm.Region;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.io.Files;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    @Test
    @Ignore("Need client id for key vault usage")
    public void canCreateApplicationGatewayWithSecret() throws Exception {
        String appGatewayName = SdkContext.randomResourceName("agwaf", 15);
        String appPublicIp = SdkContext.randomResourceName("pip", 15);
        String identityName = SdkContext.randomResourceName("id", 10);

        PublicIPAddress pip = networkManager.publicIPAddresses()
                .define(appPublicIp)
                .withRegion(Region.US_EAST)
                .withNewResourceGroup(RG_NAME)
                .withSku(PublicIPSkuType.STANDARD)
                .withStaticIP()
                .create();

        ApplicationTokenCredential credentials = ApplicationTokenCredential
                .fromFile(new File(System.getenv("AZURE_AUTH_LOCATION")));

        Identity identity = msiManager.identities()
                .define(identityName)
                .withRegion(Region.US_EAST)
                .withExistingResourceGroup(RG_NAME)
                .create();

        Assert.assertNotNull(identity.name());
        Assert.assertNotNull(identity.principalId());

        Secret secret1 = createKeyVaultSecret(credentials.getClientId(), identity.principalId());
        Secret secret2 = createKeyVaultSecret(credentials.getClientId(), identity.principalId());

        ManagedServiceIdentity serviceIdentity = createManagedServiceIdentityFromIdentity(identity);

        ApplicationGateway appGateway = networkManager.applicationGateways().define(appGatewayName)
                .withRegion(Region.US_EAST)
                .withExistingResourceGroup(RG_NAME)
                // Request routing rules
                .defineRequestRoutingRule("rule1")
                .fromPublicFrontend()
                .fromFrontendHttpsPort(443)
                .withSslCertificate("ssl1")
                .toBackendHttpPort(8080)
                .toBackendIPAddress("11.1.1.1")
                .toBackendIPAddress("11.1.1.2")
                .attach()
                .withIdentity(serviceIdentity)
                .defineSslCertificate("ssl1")
                .withKeyVaultSecretId(secret1.id())
                .attach()
                .withExistingPublicIPAddress(pip)
                .withTier(ApplicationGatewayTier.WAF_V2)
                .withSize(ApplicationGatewaySkuName.WAF_V2)
                .withAutoScale(2, 5)
                .withWebApplicationFirewall(true, ApplicationGatewayFirewallMode.PREVENTION)
                .create();

        Assert.assertEquals(secret1.id(), appGateway.sslCertificates().get("ssl1").keyVaultSecretId());
        Assert.assertEquals(secret1.id(), appGateway.requestRoutingRules().get("rule1").sslCertificate().keyVaultSecretId());

        appGateway = appGateway.update()
                .defineSslCertificate("ssl2")
                .withKeyVaultSecretId(secret2.id())
                .attach()
                .apply();

        Assert.assertEquals(secret2.id(), appGateway.sslCertificates().get("ssl2").keyVaultSecretId());
    }

    private Secret createKeyVaultSecret(String servicePrincipal, String identityPrincipal) throws Exception {
        String vaultName = SdkContext.randomResourceName("vlt", 10);
        String secretName = SdkContext.randomResourceName("srt", 10);
        String secretValue = Files.readFirstLine(new File(getClass().getClassLoader().getResource("test.certificate").getFile()), Charset.defaultCharset());

        Vault vault = keyVaultManager.vaults()
                .define(vaultName)
                .withRegion(Region.US_EAST)
                .withExistingResourceGroup(RG_NAME)
                .defineAccessPolicy()
                .forServicePrincipal(servicePrincipal)
                .allowSecretAllPermissions()
                .attach()
                .defineAccessPolicy()
                .forObjectId(identityPrincipal)
                .allowSecretAllPermissions()
                .attach()
                .withAccessFromAzureServices()
                .withDeploymentEnabled()
                // Important!! Only soft delete enabled key vault can be assigned to application gateway
                // See also: https://github.com/MicrosoftDocs/azure-docs/issues/34382
                .withSoftDeleteEnabled()
                .create();

        return vault.secrets()
                .define(secretName)
                .withValue(secretValue)
                .create();
    }

    private static ManagedServiceIdentity createManagedServiceIdentityFromIdentity(Identity identity) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode userAssignedIdentitiesValueObject = mapper.createObjectNode();
        ((ObjectNode) userAssignedIdentitiesValueObject).put("principalId", identity.principalId());
        ((ObjectNode) userAssignedIdentitiesValueObject).put("clientId", identity.clientId());
        ComponentsSchemasManagedserviceidentityPropertiesUserassignedidentitiesAdditionalproperties userAssignedIdentitiesValue =
                new JacksonAdapter().deserialize(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userAssignedIdentitiesValueObject),
                        ComponentsSchemasManagedserviceidentityPropertiesUserassignedidentitiesAdditionalproperties.class,
                        SerializerEncoding.JSON);

        Map<String, ComponentsSchemasManagedserviceidentityPropertiesUserassignedidentitiesAdditionalproperties> userAssignedIdentities =
                new HashMap<>();
        userAssignedIdentities.put(identity.id(), userAssignedIdentitiesValue);

        ManagedServiceIdentity serviceIdentity = new ManagedServiceIdentity();
        serviceIdentity.withType(ResourceIdentityType.USER_ASSIGNED);
        serviceIdentity.withUserAssignedIdentities(userAssignedIdentities);
        return serviceIdentity;
    }
}
