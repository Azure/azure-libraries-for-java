/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.trafficmanager.samples;

import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.appservice.AppServiceDomain;
import com.microsoft.azure.management.appservice.OperatingSystem;
import com.microsoft.azure.management.appservice.AppServicePlan;
import com.microsoft.azure.management.appservice.PricingTier;
import com.microsoft.azure.management.appservice.WebApp;
import com.microsoft.azure.management.resources.fluentcore.arm.CountryIsoCode;
import com.microsoft.azure.management.resources.fluentcore.arm.CountryPhoneCode;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.azure.management.trafficmanager.TrafficManagerProfile;
import com.microsoft.rest.LogLevel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Azure traffic manager sample for managing profiles.
 *  - Create a domain
 *  - Create a self-signed certificate for the domain
 *  - Create 5 app service plans in 5 different regions
 *  - Create 5 web apps under the each plan, bound to the domain and the certificate
 *  - Create a traffic manager in front of the web apps
 *  - Disable an endpoint
 *  - Delete an endpoint
 *  - Enable an endpoint
 *  - Change/configure traffic manager routing method
 *  - Disable traffic manager profile
 *  - Enable traffic manager profile
 */
public final class ManageTrafficManager {


    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final String rgName                     = SdkContext.randomResourceName("rgNEMV_", 24);
        final String domainName                 = SdkContext.randomResourceName("jsdkdemo-", 20) + ".com";
        // [SuppressMessage("Microsoft.Security", "CS002:SecretInNextLine", Justification="Serves as an example, not for deployment. Please change when using this in your code.")]
        final String certPassword               = "StrongPass!12";
        final String appServicePlanNamePrefix   = SdkContext.randomResourceName("jplan1_", 15);
        final String webAppNamePrefix           = SdkContext.randomResourceName("webapp1-", 20);
        final String tmName                     = SdkContext.randomResourceName("jsdktm-", 20);
        final List<Region> regions              = new ArrayList<>();
        // The regions in which web app needs to be created
        //
        regions.add(Region.US_WEST2);
        regions.add(Region.US_EAST2);
        regions.add(Region.ASIA_EAST);
        regions.add(Region.INDIA_WEST);
        regions.add(Region.US_CENTRAL);

        try {
            azure.resourceGroups().define(rgName)
                    .withRegion(Region.US_WEST)
                    .create();

            //============================================================
            // Purchase a domain (will be canceled for a full refund)

            System.out.println("Purchasing a domain " + domainName + "...");
            AppServiceDomain domain = azure.appServices().domains().define(domainName)
                    .withExistingResourceGroup(rgName)
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
                    .withAutoRenewEnabled(false)
                    .create();
            System.out.println("Purchased domain " + domain.name());
            Utils.print(domain);

            //============================================================
            // Create a self-singed SSL certificate

            String pfxPath = ManageTrafficManager.class.getResource("/").getPath() + "webapp_" + ManageTrafficManager.class.getSimpleName().toLowerCase() + ".pfx";
            String cerPath = ManageTrafficManager.class.getResource("/").getPath() + "webapp_" + ManageTrafficManager.class.getSimpleName().toLowerCase() + ".cer";

            System.out.println("Creating a self-signed certificate " + pfxPath + "...");

            Utils.createCertificate(cerPath, pfxPath, domainName, certPassword, "*." + domainName);

            //============================================================
            // Creates app service in 5 different region

            List<AppServicePlan> appServicePlans = new ArrayList<>();
            int id = 0;
            for (Region region : regions) {
                String planName = appServicePlanNamePrefix + id;
                System.out.println("Creating an app service plan " + planName + " in region " + region + "...");
                AppServicePlan appServicePlan = azure.appServices().appServicePlans().define(planName)
                        .withRegion(region)
                        .withExistingResourceGroup(rgName)
                        .withPricingTier(PricingTier.BASIC_B1)
                        .withOperatingSystem(OperatingSystem.WINDOWS)
                        .create();
                System.out.println("Created app service plan " + planName);
                Utils.print(appServicePlan);
                appServicePlans.add(appServicePlan);
                id++;
            }

            //============================================================
            // Creates websites using previously created plan
            List<WebApp> webApps = new ArrayList<>();
            id = 0;
            for (AppServicePlan appServicePlan : appServicePlans) {
                String webAppName = webAppNamePrefix + id;
                System.out.println("Creating a web app " + webAppName + " using the plan " + appServicePlan.name() + "...");
                WebApp webApp = azure.webApps().define(webAppName)
                        .withExistingWindowsPlan(appServicePlan)
                        .withExistingResourceGroup(rgName)
                        .withManagedHostnameBindings(domain, webAppName)
                        .defineSslBinding()
                            .forHostname(webAppName + "." + domain.name())
                            .withPfxCertificateToUpload(new File(pfxPath), certPassword)
                            .withSniBasedSsl()
                            .attach()
                        .defineSourceControl()
                            .withPublicGitRepository("https://github.com/jianghaolu/azure-site-test")
                            .withBranch("master")
                            .attach()
                        .create();
                System.out.println("Created web app " + webAppName);
                Utils.print(webApp);
                webApps.add(webApp);
                id++;
            }

            //============================================================
            // Creates a traffic manager profile

            System.out.println("Creating a traffic manager profile " + tmName + " for the web apps...");
            TrafficManagerProfile.DefinitionStages.WithEndpoint tmDefinition = azure.trafficManagerProfiles()
                    .define(tmName)
                        .withExistingResourceGroup(rgName)
                        .withLeafDomainLabel(tmName)
                        .withPriorityBasedRouting();
            Creatable<TrafficManagerProfile> tmCreatable = null;
            int priority = 1;
            for (WebApp webApp : webApps) {
                tmCreatable = tmDefinition.defineAzureTargetEndpoint("endpoint-" + priority)
                        .toResourceId(webApp.id())
                        .withRoutingPriority(priority)
                        .attach();
                priority++;
            }
            TrafficManagerProfile trafficManagerProfile = tmCreatable.create();
            System.out.println("Created traffic manager " + trafficManagerProfile.name());
            Utils.print(trafficManagerProfile);

            //============================================================
            // Disables one endpoint and removes another endpoint

            System.out.println("Disabling and removing endpoint...");
            trafficManagerProfile = trafficManagerProfile.update()
                    .updateAzureTargetEndpoint("endpoint-1")
                        .withTrafficDisabled()
                        .parent()
                    .withoutEndpoint("endpoint-2")
                    .apply();
            System.out.println("Endpoints updated");

            //============================================================
            // Enables an endpoint

            System.out.println("Enabling endpoint...");
            trafficManagerProfile = trafficManagerProfile.update()
                    .updateAzureTargetEndpoint("endpoint-1")
                        .withTrafficEnabled()
                        .parent()
                    .apply();
            System.out.println("Endpoint updated");
            Utils.print(trafficManagerProfile);

            //============================================================
            // Change/configure traffic manager routing method

            System.out.println("Changing traffic manager profile routing method...");
            trafficManagerProfile = trafficManagerProfile.update()
                    .withPerformanceBasedRouting()
                    .apply();
            System.out.println("Changed traffic manager profile routing method");

            //============================================================
            // Disables the traffic manager profile

            System.out.println("Disabling traffic manager profile...");
            trafficManagerProfile.update()
                    .withProfileStatusDisabled()
                    .apply();
            System.out.println("Traffic manager profile disabled");

            //============================================================
            // Enables the traffic manager profile

            System.out.println("Enabling traffic manager profile...");
            trafficManagerProfile.update()
                    .withProfileStatusDisabled()
                    .apply();
            System.out.println("Traffic manager profile enabled");

            //============================================================
            // Deletes the traffic manager profile

            System.out.println("Deleting the traffic manger profile...");
            azure.trafficManagerProfiles().deleteById(trafficManagerProfile.id());
            System.out.println("Traffic manager profile deleted");
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

            Azure azure = Azure.configure()
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
}
