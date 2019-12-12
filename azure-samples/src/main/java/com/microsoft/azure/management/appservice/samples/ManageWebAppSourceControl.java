/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.samples;

import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.appservice.AppServicePlan;
import com.microsoft.azure.management.appservice.JavaVersion;
import com.microsoft.azure.management.appservice.PricingTier;
import com.microsoft.azure.management.appservice.PublishingProfile;
import com.microsoft.azure.management.appservice.RuntimeStack;
import com.microsoft.azure.management.appservice.WebApp;
import com.microsoft.azure.management.appservice.WebContainer;
import com.azure.management.resources.fluentcore.arm.Region;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.rest.LogLevel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Azure App Service basic sample for managing web apps.
 *  - Create 6 web apps under the same new app service plan:
 *    - Deploy to 1 using FTP
 *    - Deploy to 2 using local Git repository
 *    - Deploy to 3 using a publicly available Git repository
 *    - Deploy to 4 using a GitHub repository with continuous integration
 *    - Deploy to 5 using Web deploy
 *    - Deploy to 6 using WAR deploy
 *    - Deploy to 7 using ZIP deploy
 */
public final class ManageWebAppSourceControl {

    private static OkHttpClient httpClient;

    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        // New resources
        final String suffix         = ".azurewebsites.net";
        final String app1Name       = SdkContext.randomResourceName("webapp1-", 20);
        final String app2Name       = SdkContext.randomResourceName("webapp2-", 20);
        final String app3Name       = SdkContext.randomResourceName("webapp3-", 20);
        final String app4Name       = SdkContext.randomResourceName("webapp4-", 20);
        final String app5Name       = SdkContext.randomResourceName("webapp5-", 20);
        final String app6Name       = SdkContext.randomResourceName("webapp5-", 20);
        final String app7Name       = SdkContext.randomResourceName("webapp7-", 20);
        final String app1Url        = app1Name + suffix;
        final String app2Url        = app2Name + suffix;
        final String app3Url        = app3Name + suffix;
        final String app4Url        = app4Name + suffix;
        final String app5Url        = app5Name + suffix;
        final String app6Url        = app6Name + suffix;
        final String app7Url        = app7Name + suffix;
        final String rgName         = SdkContext.randomResourceName("rg1NEMV_", 24);
        final String rg7Name         = SdkContext.randomResourceName("rg7NEMV_", 24);
        try {


            //============================================================
            // Create a web app with a new app service plan

            System.out.println("Creating web app " + app1Name + " in resource group " + rgName + "...");

            WebApp app1 = azure.webApps().define(app1Name)
                    .withRegion(Region.US_WEST)
                    .withNewResourceGroup(rgName)
                    .withNewWindowsPlan(PricingTier.STANDARD_S1)
                    .withJavaVersion(JavaVersion.JAVA_8_NEWEST)
                    .withWebContainer(WebContainer.TOMCAT_8_0_NEWEST)
                    .create();

            System.out.println("Created web app " + app1.name());
            Utils.print(app1);

            //============================================================
            // Deploy to app 1 through FTP

            System.out.println("Deploying helloworld.war to " + app1Name + " through FTP...");

            Utils.uploadFileToWebApp(app1.getPublishingProfile(), "helloworld.war", ManageWebAppSourceControl.class.getResourceAsStream("/helloworld.war"));

            System.out.println("Deployment helloworld.war to web app " + app1.name() + " completed");
            Utils.print(app1);

            // warm up
            System.out.println("Warming up " + app1Url + "/helloworld...");
            curl("http://" + app1Url + "/helloworld");
            SdkContext.sleep(5000);
            System.out.println("CURLing " + app1Url + "/helloworld...");
            System.out.println(curl("http://" + app1Url + "/helloworld"));

            //============================================================
            // Create a second web app with local git source control

            System.out.println("Creating another web app " + app2Name + " in resource group " + rgName + "...");
            AppServicePlan plan = azure.appServices().appServicePlans().getById(app1.appServicePlanId());
            WebApp app2 = azure.webApps().define(app2Name)
                    .withExistingWindowsPlan(plan)
                    .withExistingResourceGroup(rgName)
                    .withLocalGitSourceControl()
                    .withJavaVersion(JavaVersion.JAVA_8_NEWEST)
                    .withWebContainer(WebContainer.TOMCAT_8_0_NEWEST)
                    .create();

            System.out.println("Created web app " + app2.name());
            Utils.print(app2);

            //============================================================
            // Deploy to app 2 through local Git

            System.out.println("Deploying a local Tomcat source to " + app2Name + " through Git...");

            PublishingProfile profile = app2.getPublishingProfile();
            Git git = Git
                    .init()
                    .setDirectory(new File(ManageWebAppSourceControl.class.getResource("/azure-samples-appservice-helloworld/").getPath()))
                    .call();
            git.add().addFilepattern(".").call();
            git.commit().setMessage("Initial commit").call();
            PushCommand command = git.push();
            command.setRemote(profile.gitUrl());
            command.setCredentialsProvider(new UsernamePasswordCredentialsProvider(profile.gitUsername(), profile.gitPassword()));
            command.setRefSpecs(new RefSpec("master:master"));
            command.setForce(true);
            command.call();

            System.out.println("Deployment to web app " + app2.name() + " completed");
            Utils.print(app2);

            // warm up
            System.out.println("Warming up " + app2Url + "/helloworld...");
            curl("http://" + app2Url + "/helloworld");
            SdkContext.sleep(5000);
            System.out.println("CURLing " + app2Url + "/helloworld...");
            System.out.println(curl("http://" + app2Url + "/helloworld"));

            //============================================================
            // Create a 3rd web app with a public GitHub repo in Azure-Samples

            System.out.println("Creating another web app " + app3Name + "...");
            WebApp app3 = azure.webApps().define(app3Name)
                    .withExistingWindowsPlan(plan)
                    .withNewResourceGroup(rgName)
                    .defineSourceControl()
                        .withPublicGitRepository("https://github.com/Azure-Samples/app-service-web-dotnet-get-started")
                        .withBranch("master")
                        .attach()
                    .create();

            System.out.println("Created web app " + app3.name());
            Utils.print(app3);

            // warm up
            System.out.println("Warming up " + app3Url + "...");
            curl("http://" + app3Url);
            SdkContext.sleep(5000);
            System.out.println("CURLing " + app3Url + "...");
            System.out.println(curl("http://" + app3Url));

            //============================================================
            // Create a 4th web app with a personal GitHub repo and turn on continuous integration

            System.out.println("Creating another web app " + app4Name + "...");
            WebApp app4 = azure.webApps()
                    .define(app4Name)
                    .withExistingWindowsPlan(plan)
                    .withExistingResourceGroup(rgName)
                    // Uncomment the following lines to turn on 4th scenario
                    //.defineSourceControl()
                    //    .withContinuouslyIntegratedGitHubRepository("username", "reponame")
                    //    .withBranch("master")
                    //    .withGitHubAccessToken("YOUR GITHUB PERSONAL TOKEN")
                    //    .attach()
                    .create();

            System.out.println("Created web app " + app4.name());
            Utils.print(app4);

            // warm up
            System.out.println("Warming up " + app4Url + "...");
            curl("http://" + app4Url);
            SdkContext.sleep(5000);
            System.out.println("CURLing " + app4Url + "...");
            System.out.println(curl("http://" + app4Url));

            //============================================================
            // Create a 5th web app with the existing app service plan

            System.out.println("Creating web app " + app5Name + " in resource group " + rgName + "...");

            WebApp app5 = azure.webApps().define(app5Name)
                    .withExistingWindowsPlan(plan)
                    .withExistingResourceGroup(rgName)
                    .withJavaVersion(JavaVersion.JAVA_8_NEWEST)
                    .withWebContainer(WebContainer.TOMCAT_8_0_NEWEST)
                    .create();

            System.out.println("Created web app " + app5.name());
            Utils.print(app5);

            //============================================================
            // Deploy to the 5th web app through web deploy

            System.out.println("Deploying helloworld.war to " + app5Name + " through web deploy...");

            app5.deploy()
                    .withPackageUri("https://github.com/Azure/azure-libraries-for-java/raw/master/azure-samples/src/main/resources/helloworld.zip")
                    .withExistingDeploymentsDeleted(true)
                    .execute();

            System.out.println("Deploying coffeeshop.war to " + app5Name + " through web deploy...");

            app5.deploy()
                    .withPackageUri("https://github.com/Azure/azure-libraries-for-java/raw/master/azure-samples/src/main/resources/coffeeshop.zip")
                    .withExistingDeploymentsDeleted(false)
                    .execute();

            System.out.println("Deployments to web app " + app5.name() + " completed");
            Utils.print(app5);

            // warm up
            System.out.println("Warming up " + app5Url + "/helloworld...");
            curl("http://" + app5Url + "/helloworld");
            SdkContext.sleep(5000);
            System.out.println("CURLing " + app5Url + "/helloworld...");
            System.out.println(curl("http://" + app5Url + "/helloworld"));
            System.out.println("CURLing " + app5Url + "/coffeeshop...");
            System.out.println(curl("http://" + app5Url + "/coffeeshop"));

            //============================================================
            // Create a 6th web app with the existing app service plan

            System.out.println("Creating web app " + app6Name + " in resource group " + rgName + "...");

            WebApp app6 = azure.webApps().define(app6Name)
                    .withExistingWindowsPlan(plan)
                    .withExistingResourceGroup(rgName)
                    .withJavaVersion(JavaVersion.JAVA_8_NEWEST)
                    .withWebContainer(WebContainer.TOMCAT_8_0_NEWEST)
                    .create();

            System.out.println("Created web app " + app6.name());
            Utils.print(app6);

            //============================================================
            // Deploy to the 6th web app through WAR deploy

            System.out.println("Deploying helloworld.war to " + app6Name + " through WAR deploy...");

            app6.update().withAppSetting("SCM_TARGET_PATH", "webapps/helloworld").apply();
            app6.warDeploy(new File(ManageWebAppSourceControl.class.getResource("/helloworld.war").getPath()));

            System.out.println("Deploying coffeeshop.war to " + app6Name + " through WAR deploy...");

            app6.update().withAppSetting("SCM_TARGET_PATH", "webapps/coffeeshop").apply();
            app6.restart();
            app6.warDeploy(new File(ManageWebAppSourceControl.class.getResource("/coffeeshop.war").getPath()));

            System.out.println("Deployments to web app " + app6.name() + " completed");
            Utils.print(app6);

            // warm up
            System.out.println("Warming up " + app6Url + "/helloworld...");
            curl("http://" + app6Url + "/helloworld");
            SdkContext.sleep(5000);
            System.out.println("CURLing " + app6Url + "/helloworld...");
            System.out.println(curl("http://" + app6Url + "/helloworld"));
            System.out.println("CURLing " + app6Url + "/coffeeshop...");
            System.out.println(curl("http://" + app6Url + "/coffeeshop"));

            //============================================================
            // Create a 7th web app with the existing app service plan



            System.out.println("Creating web app " + app7Name + " in resource group " + rgName + "...");

            WebApp app7 = azure.webApps().define(app7Name)
                    .withRegion(Region.US_WEST)
                    .withNewResourceGroup(rg7Name)
                    .withNewLinuxPlan(PricingTier.STANDARD_S2)
                    .withBuiltInImage(RuntimeStack.JAVA_8_JRE8)
                    .withAppSetting("JAVA_OPTS", "-Djava.security.egd=file:/dev/./urandom")
                    .create();

            System.out.println("Created web app " + app7.name());
            Utils.print(app7);

            //============================================================
            // Deploy to the 7th web app through ZIP deploy

            System.out.println("Deploying app.zip to " + app7Name + " through ZIP deploy...");

            for (int i = 0; i < 5; i++) {
                try {
                    app7.zipDeploy(new File(ManageWebAppSourceControl.class.getResource("/app.zip").getPath()));
                    break;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("Deployments to web app " + app7.name() + " completed");
            Utils.print(app7);

            // warm up
            System.out.println("Warming up " + app7Url);
            curl("http://" + app7Url);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Deleting Resource Group: " + rgName);
                azure.resourceGroups().beginDeleteByName(rgName);
                azure.resourceGroups().beginDeleteByName(rg7Name);
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

    static {
        httpClient = new OkHttpClient.Builder().readTimeout(1, TimeUnit.MINUTES).build();
    }
}
