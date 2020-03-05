/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.appservice.samples;

import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.appservice.FunctionApp;
import com.microsoft.azure.management.appservice.FunctionRuntimeStack;
import com.microsoft.azure.management.appservice.PricingTier;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.azure.management.samples.Utils;
import com.microsoft.azure.management.storage.StorageAccountSkuType;
import com.microsoft.rest.LogLevel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.lang3.time.StopWatch;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Azure App Service basic sample for managing function apps.
 *  - Create 2 linux function apps.
 *    - Deploy 1 under new dedicated app service plan, run from a package.
 *    - Deploy 1 under new consumption plan, run from a package.
 */
public class ManageLinuxFunctionAppSourceControl {

    private static OkHttpClient httpClient;

    private static final String FUNCTION_APP_PACKAGE_URL = "https://raw.github.com/Azure/azure-libraries-for-java/master/azure-mgmt-appservice/src/test/resources/java-functions.zip";
    private static final long TIMEOUT_IN_SECONDS = 5 * 60;

    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final String suffix         = ".azurewebsites.net";
        final String app1Name       = SdkContext.randomResourceName("webapp1-", 20);
        final String app2Name       = SdkContext.randomResourceName("webapp2-", 20);
        final String app1Url        = app1Name + suffix;
        final String app2Url        = app2Name + suffix;
        final String plan1Name      = SdkContext.randomResourceName("plan1-", 20);
        final String plan2Name      = SdkContext.randomResourceName("plan2-", 20);
        final String storage1Name   = SdkContext.randomResourceName("storage1", 20);
        final String rgName         = SdkContext.randomResourceName("rg1NEMV_", 24);

        try {

            //============================================================
            // Create a function app with a new dedicated app service plan, configure as run from a package

            System.out.println("Creating function app " + app1Name + " in resource group " + rgName + "...");

            FunctionApp app1 = azure.appServices().functionApps().define(app1Name)
                    .withRegion(Region.US_WEST)
                    .withNewResourceGroup(rgName)
                    .withNewLinuxAppServicePlan(plan1Name, PricingTier.STANDARD_S1)
                    .withBuiltInImage(FunctionRuntimeStack.JAVA_8)
                    .withNewStorageAccount(storage1Name, StorageAccountSkuType.STANDARD_RAGRS)
                    .withHttpsOnly(true)
                    .withAppSetting("WEBSITE_RUN_FROM_PACKAGE", FUNCTION_APP_PACKAGE_URL)
                    .create();

            System.out.println("Created function app " + app1.name());
            Utils.print(app1);

            // warm up
            String app1UrlFunction = app1Url + "/api/HttpTrigger-Java?name=linux_function_app1";
            System.out.println("Warming up " + app1UrlFunction + "...");
            StopWatch stopWatch = StopWatch.createStarted();
            while (stopWatch.getTime() < TIMEOUT_IN_SECONDS * 1000) {
                String response = get("https://" + app1UrlFunction);
                if (response != null && response.contains("Hello")) {
                    break;
                }
                SdkContext.sleep(10 * 1000);
            }

            // call function
            System.out.println("CURLing " + app1UrlFunction + "...");
            System.out.println("Response is " + get("https://" + app1UrlFunction));
            // response would be "Hello, ..."


            //============================================================
            // Create a function app with a new consumption plan, configure as run from a package

            System.out.println("Creating function app " + app2Name + " in resource group " + rgName + "...");

            FunctionApp app2 = azure.appServices().functionApps().define(app2Name)
                    .withRegion(Region.US_EAST)
                    .withExistingResourceGroup(rgName)
                    .withNewLinuxConsumptionPlan(plan2Name)
                    .withBuiltInImage(FunctionRuntimeStack.JAVA_8)
                    .withExistingStorageAccount(azure.storageAccounts().getByResourceGroup(rgName, storage1Name))
                    .withHttpsOnly(true)
                    .withAppSetting("WEBSITE_RUN_FROM_PACKAGE", FUNCTION_APP_PACKAGE_URL)
                    .create();

            System.out.println("Created function app " + app2.name());
            Utils.print(app2);

            // warm up
            String app2UrlFunction = app2Url + "/api/HttpTrigger-Java?name=linux_function_app2";
            System.out.println("Warming up " + app2UrlFunction + "...");
            stopWatch = StopWatch.createStarted();
            while (stopWatch.getTime() < TIMEOUT_IN_SECONDS * 1000) {
                String response = get("https://" + app2UrlFunction);
                if (response != null && response.contains("Hello")) {
                    break;
                }
                SdkContext.sleep(10 * 1000);
            }

            // call function
            System.out.println("CURLing " + app2UrlFunction + "...");
            System.out.println("Response is " + get("https://" + app2UrlFunction));
            // response would be "Hello, ..."

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Deleting Resource Group: " + rgName);
                azure.resourceGroups().beginDeleteByName(rgName);
                System.out.println("Deleted Resource Group: " + rgName);
            } catch (NullPointerException npe) {
                System.out.println("Did not create any resources in Azure. No clean up is necessary");
            } catch (Exception g) {
                g.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Main entry point.
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {

            //=============================================================
            // Authenticate

            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));

            Azure azure = Azure
                    .configure()
                    .withLogLevel(LogLevel.BODY_AND_HEADERS)
                    .authenticate(credFile)
                    .withDefaultSubscription();

            // Print selected subscription
            System.out.println("Selected subscription: " + azure.subscriptionId());
            runSample(azure);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static String get(String url) {
        Request request = new Request.Builder().url(url).get().build();
        try {
            return httpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            return null;
        }
    }

    static {
        httpClient = new OkHttpClient.Builder().readTimeout(1, TimeUnit.MINUTES).build();
    }
}
