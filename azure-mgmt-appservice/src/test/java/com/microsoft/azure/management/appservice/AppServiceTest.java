/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice;

import com.microsoft.azure.management.appservice.implementation.AppServiceManager;
import com.microsoft.azure.management.keyvault.implementation.KeyVaultManager;
import com.microsoft.azure.management.resources.core.TestBase;
import com.microsoft.azure.management.resources.fluentcore.arm.CountryIsoCode;
import com.microsoft.azure.management.resources.fluentcore.arm.CountryPhoneCode;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.implementation.ResourceManager;
import com.microsoft.rest.RestClient;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * The base for app service tests.
 */
public class AppServiceTest extends TestBase {
    protected static ResourceManager resourceManager;
    protected static KeyVaultManager keyVaultManager;
    protected static AppServiceManager appServiceManager;

    protected static AppServiceDomain domain;
    protected static AppServiceCertificateOrder certificateOrder;
    protected static String RG_NAME = "";

    private static OkHttpClient httpClient = new OkHttpClient.Builder().readTimeout(3, TimeUnit.MINUTES).build();

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        RG_NAME = generateRandomResourceName("javacsmrg", 20);
        resourceManager = ResourceManager
                .authenticate(restClient)
                .withSubscription(defaultSubscription);

        keyVaultManager = KeyVaultManager
                .authenticate(restClient, domain, defaultSubscription);

        appServiceManager = AppServiceManager
                .authenticate(restClient, domain, defaultSubscription);

        //useExistingDomainAndCertificate();
        //createNewDomainAndCertificate();
    }

    @Override
    protected void cleanUpResources() {
//        resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
    }

    private void useExistingDomainAndCertificate() {
        String rgName = "rgnemv24d683784f51d";
        String certOrder = "wild2crt8b42374211";
        String domainName = "jsdk79877.com";
        if (System.getenv("appservice-group") != null) {
            rgName = System.getenv("appservice-group");
        }
        if (System.getenv("appservice-domain") != null) {
            domainName = System.getenv("appservice-domain");
        }
        if (System.getenv("appservice-certificateorder") != null) {
            certOrder = System.getenv("appservice-certificateorder");
        }

        domain = appServiceManager.domains().getByResourceGroup(rgName, domainName);
        certificateOrder = appServiceManager.certificateOrders().getByResourceGroup(rgName, certOrder);
    }

    private static void createNewDomainAndCertificate() {
        domain = appServiceManager.domains().define(System.getenv("appservice-domain"))
                .withExistingResourceGroup(System.getenv("appservice-group"))
                .defineRegistrantContact()
                    .withFirstName("Jon")
                    .withLastName("Doe")
                    .withEmail("jondoe@contoso.com")
                    .withAddressLine1("123 4th Ave")
                    .withCity("Redmond")
                    .withStateOrProvince("WA")
                    .withCountry(CountryIsoCode.UNITED_STATES)
                    .withPostalCode("98052")
                    .withPhoneCountryCode(CountryPhoneCode.UNITED_STATES)
                    .withPhoneNumber("4258828080")
                    .attach()
                .withDomainPrivacyEnabled(true)
                .withAutoRenewEnabled(true)
                .create();
        certificateOrder = appServiceManager.certificateOrders()
                .define(System.getenv("appservice-certificateorder"))
                .withExistingResourceGroup(System.getenv("appservice-group"))
                .withHostName("*." + domain.name())
                .withWildcardSku()
                .withDomainVerification(domain)
                .withNewKeyVault("graphvault", Region.US_WEST)
                .withValidYears(1)
                .create();
    }

    /**
     * Uploads a file to an Azure web app.
     * @param profile the publishing profile for the web app.
     * @param fileName the name of the file on server
     * @param file the local file
     */
    public static void uploadFileToWebApp(PublishingProfile profile, String fileName, InputStream file) {
        FTPClient ftpClient = new FTPClient();
        String[] ftpUrlSegments = profile.ftpUrl().split("/", 2);
        String server = ftpUrlSegments[0];
        String path = "./site/wwwroot/webapps";
        if (fileName.contains("/")) {
            int lastslash = fileName.lastIndexOf('/');
            path = path + "/" + fileName.substring(0, lastslash);
            fileName = fileName.substring(lastslash + 1);
        }
        try {
            ftpClient.connect(server);
            ftpClient.login(profile.ftpUsername(), profile.ftpPassword());
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            for (String segment : path.split("/")) {
                if (!ftpClient.changeWorkingDirectory(segment)) {
                    ftpClient.makeDirectory(segment);
                    ftpClient.changeWorkingDirectory(segment);
                }
            }
            ftpClient.storeFile(fileName, file);
            ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Response curl(String url) throws IOException {
        Request request = new Request.Builder().url(url).get().build();
        return httpClient.newCall(request).execute();
    }

    static String post(String url, String body) {
        Request request = new Request.Builder().url(url).post(RequestBody.create(MediaType.parse("text/plain"), body)).build();
        try {
            return httpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            return null;
        }
    }
}