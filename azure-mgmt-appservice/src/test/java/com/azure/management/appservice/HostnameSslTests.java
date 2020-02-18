/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.appservice;

import com.azure.management.RestClient;
import com.azure.management.resources.fluentcore.arm.Region;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLPeerUnverifiedException;
import java.util.concurrent.TimeUnit;

public class HostnameSslTests extends AppServiceTest {
    private static String WEBAPP_NAME = "";
    private static String APP_SERVICE_PLAN_NAME = "";
    private static OkHttpClient httpClient = new OkHttpClient.Builder().readTimeout(1, TimeUnit.MINUTES).build();
    private String DOMAIN = "";

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        super.initializeClients(restClient, defaultSubscription, domain);

        WEBAPP_NAME = generateRandomResourceName("java-webapp-", 20);
        APP_SERVICE_PLAN_NAME = generateRandomResourceName("java-asp-", 20);

        DOMAIN = super.domain.name();
    }

    @Test
    @Disabled("Need a domain and a certificate")
    public void canBindHostnameAndSsl() throws Exception {
        // hostname binding
        appServiceManager.webApps().define(WEBAPP_NAME)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(RG_NAME)
                .withNewWindowsPlan(PricingTier.BASIC_B1)
                .defineHostnameBinding()
                    .withAzureManagedDomain(domain)
                    .withSubDomain(WEBAPP_NAME)
                    .withDnsRecordType(CustomHostNameDnsRecordType.CNAME)
                    .attach()
                .create();

        WebApp webApp = appServiceManager.webApps().getByResourceGroup(RG_NAME, WEBAPP_NAME);
        Assertions.assertNotNull(webApp);
        if (!isPlaybackMode()) {
            Response response = curl("http://" + WEBAPP_NAME + "." + DOMAIN);
            Assertions.assertEquals(200, response.code());
            Assertions.assertNotNull(response.body().string());
        }
        // hostname binding shortcut
        webApp.update()
                .withManagedHostnameBindings(domain, WEBAPP_NAME + "-1", WEBAPP_NAME + "-2")
                .apply();
        if (!isPlaybackMode()) {
            Response response = curl("http://" + WEBAPP_NAME + "-1." + DOMAIN);
            Assertions.assertEquals(200, response.code());
            Assertions.assertNotNull(response.body().string());
            response = curl("http://" + WEBAPP_NAME + "-2." + DOMAIN);
            Assertions.assertEquals(200, response.code());
            Assertions.assertNotNull(response.body().string());
        }
        // SSL binding
        webApp.update()
                .defineSslBinding()
                    .forHostname(WEBAPP_NAME + "." + DOMAIN)
                    .withExistingAppServiceCertificateOrder(certificateOrder)
                    .withSniBasedSsl()
                    .attach()
                .apply();
        if (!isPlaybackMode()) {
            Response response = null;
            int retryCount = 3;
            while (response == null && retryCount > 0) {
                try {
                    response = curl("https://" + WEBAPP_NAME + "." + DOMAIN);
                } catch (SSLPeerUnverifiedException e) {
                    retryCount--;
                    SdkContext.sleep(5000);
                }
            }
            if (retryCount == 0) {
                Assertions.fail();
            }
            Assertions.assertEquals(200, response.code());
            Assertions.assertNotNull(response.body().string());
        }
    }
}