/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.samples;

import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.appservice.FunctionApp;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.rest.LogLevel;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.commons.lang3.time.StopWatch;
import rx.Subscriber;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Azure App Service basic sample for managing function apps.
 *  - Create a function app under the same new app service plan:
 *    - Deploy to app using FTP
 *    - stream logs synchronously for 30 seconds
 *    - stream logs asynchronously until 3 requests are completed
 */
public final class ManageFunctionAppLogs {

    private static OkHttpClient httpClient;

    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        // New resources
        final String suffix         = ".azurewebsites.net";
        final String appName       = SdkContext.randomResourceName("webapp1-", 20);
        final String appUrl        = appName + suffix;
        final String rgName         = SdkContext.randomResourceName("rg1NEMV_", 24);

        try {


            //============================================================
            // Create a function app with a new app service plan

            System.out.println("Creating function app " + appName + " in resource group " + rgName + "...");

            FunctionApp app = azure.appServices().functionApps().define(appName)
                    .withRegion(Region.US_WEST)
                    .withNewResourceGroup(rgName)
                    .defineDiagnosticLogsConfiguration()
                        .withApplicationLogging()
                        .withLogLevel(com.microsoft.azure.management.appservice.LogLevel.VERBOSE)
                        .withApplicationLogsStoredOnFileSystem()
                        .attach()
                    .create();

            System.out.println("Created function app " + app.name());
            Utils.print(app);

            //============================================================
            // Deploy to app 1 through FTP

            System.out.println("Deploying a function app to " + appName + " through FTP...");

            Utils.uploadFileToFunctionApp(app.getPublishingProfile(), "host.json", ManageFunctionAppLogs.class.getResourceAsStream("/square-function-app/host.json"));
            Utils.uploadFileToFunctionApp(app.getPublishingProfile(), "square/function.json", ManageFunctionAppLogs.class.getResourceAsStream("/square-function-app/square/function.json"));
            Utils.uploadFileToFunctionApp(app.getPublishingProfile(), "square/index.js", ManageFunctionAppLogs.class.getResourceAsStream("/square-function-app/square/index.js"));

            // sync triggers
            app.syncTriggers();

            System.out.println("Deployment square app to function app " + app.name() + " completed");
            Utils.print(app);

            // warm up
            System.out.println("Warming up " + appUrl + "/api/square...");
            post("http://" + appUrl + "/api/square", "625");
            SdkContext.sleep(5000);

            //============================================================
            // Listen to logs synchronously for 30 seconds

            final InputStream stream = app.streamApplicationLogs();
            System.out.println("Streaming logs from function app " + appName + "...");
            String line = readLine(stream);
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    post("http://" + appUrl + "/api/square", "625");
                    SdkContext.sleep(10000);
                    post("http://" + appUrl + "/api/square", "725");
                    SdkContext.sleep(10000);
                    post("http://" + appUrl + "/api/square", "825");
                }
            }).start();
            while (line != null && stopWatch.getTime() < 90000) {
                System.out.println(line);
                line = readLine(stream);
            }
            stream.close();

            //============================================================
            // Listen to logs asynchronously until 3 requests are completed

            new Thread(new Runnable() {
                @Override
                public void run() {
                    SdkContext.sleep(5000);
                    System.out.println("Starting hitting");
                    post("http://" + appUrl + "/api/square", "625");
                    SdkContext.sleep(10000);
                    post("http://" + appUrl + "/api/square", "725");
                    SdkContext.sleep(10000);
                    post("http://" + appUrl + "/api/square", "825");
                }
            }).start();

            final AtomicInteger count = new AtomicInteger(0);
            app.streamApplicationLogsAsync()
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            // automatically unsubscribe
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(String s) {
                            System.out.println(s);
                            if (s.contains("Function completed")) {
                                if (count.incrementAndGet() >= 3) {
                                    unsubscribe();
                                }
                            }
                        }
                    });

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
                    .withLogLevel(LogLevel.BASIC)
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

    private static String curl(String url) {
        Request request = new Request.Builder().url(url).get().build();
        try {
            return httpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            return null;
        }
    }

    private static String post(String url, String body) {
        Request request = new Request.Builder().url(url).post(RequestBody.create(MediaType.parse("text/plain"), body)).build();
        try {
            return httpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            return null;
        }
    }

    private static String readLine(InputStream in) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int c;
        for (c = in.read(); c != '\n' && c >= 0; c = in.read()) {
            stream.write(c);
        }
        if (c == -1 && stream.size() == 0) {
            return null;
        }
        return stream.toString("UTF-8");
    }

    static {
        httpClient = new OkHttpClient.Builder().readTimeout(1, TimeUnit.MINUTES).build();
    }
}
