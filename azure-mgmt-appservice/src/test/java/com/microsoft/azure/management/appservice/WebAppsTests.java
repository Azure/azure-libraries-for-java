/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice;

import com.microsoft.azure.management.appservice.implementation.AppServiceManager;
import com.microsoft.azure.management.appservice.implementation.ApplicationStackInner;
import com.microsoft.azure.management.appservice.implementation.ProvidersInner;
import com.microsoft.azure.management.appservice.implementation.WebSiteManagementClientImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class WebAppsTests extends AppServiceTest {
    private static String RG_NAME_1 = "";
    private static String RG_NAME_2 = "";
    private static String RG_NAME_3 = "";
    private static String RG_NAME_4 = "";
    private static String RG_NAME_5 = "";
    private static String WEBAPP_NAME_1 = "";
    private static String WEBAPP_NAME_2 = "";
    private static String WEBAPP_NAME_3 = "";
    private static String WEBAPP_NAME_4 = "";
    private static String WEBAPP_NAME_5 = "";
    private boolean createdWebApp1 = false;
    private boolean createdWebApp2 = false;
    private boolean createdWebApp3 = false;
    private boolean createdWebApp4 = false;
    private boolean createdWebApp5 = false;
    private RestClient restClient = null;

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        WEBAPP_NAME_1 = generateRandomResourceName("java-webapp-", 20);
        WEBAPP_NAME_2 = generateRandomResourceName("java-webapp-", 20);
        WEBAPP_NAME_3 = generateRandomResourceName("java-webapp-", 20);
        WEBAPP_NAME_4 = generateRandomResourceName("java-webapp-", 20);
        WEBAPP_NAME_5 = generateRandomResourceName("java-webapp-", 20);
        RG_NAME_1 = generateRandomResourceName("javacsmrg", 20);
        RG_NAME_2 = generateRandomResourceName("javacsmrg", 20);
        RG_NAME_3 = generateRandomResourceName("javacsmrg", 20);
        RG_NAME_4 = generateRandomResourceName("javacsmrg", 20);
        RG_NAME_5 = generateRandomResourceName("javacsmrg", 20);

        this.restClient = restClient;
        super.initializeClients(restClient, defaultSubscription, domain);
    }

    @Override
    protected void cleanUpResources() {
        if (createdWebApp5) {
            resourceManager.resourceGroups().beginDeleteByName(RG_NAME_5);
        }

        if (createdWebApp4) {
            resourceManager.resourceGroups().beginDeleteByName(RG_NAME_4);
        }

        if (createdWebApp3) {
            resourceManager.resourceGroups().beginDeleteByName(RG_NAME_3);
        }

        if (createdWebApp2) {
            resourceManager.resourceGroups().beginDeleteByName(RG_NAME_2);
        }

        if (createdWebApp1) {
            resourceManager.resourceGroups().beginDeleteByName(RG_NAME_1);
        }
    }

    @Test
    public void canCRUDWebApp() throws Exception {
        // Create with new app service plan
        WebApp webApp1 = appServiceManager.webApps().define(WEBAPP_NAME_1)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(RG_NAME_1)
                .withNewWindowsPlan(PricingTier.BASIC_B1)
                .withPhpVersion(PhpVersion.PHP5_6)
                //.withRemoteDebuggingEnabled(RemoteVisualStudioVersion.VS2015)
                .create();
        createdWebApp1 = true;
        Assert.assertNotNull(webApp1);
        Assert.assertEquals(Region.US_WEST, webApp1.region());
        AppServicePlan plan1 = appServiceManager.appServicePlans().getById(webApp1.appServicePlanId());
        Assert.assertNotNull(plan1);
        Assert.assertEquals(Region.US_WEST, plan1.region());
        Assert.assertEquals(PricingTier.BASIC_B1, plan1.pricingTier());

        // Create in a new group with existing app service plan
        WebApp webApp2 = appServiceManager.webApps().define(WEBAPP_NAME_2)
                .withExistingWindowsPlan(plan1)
                .withNewResourceGroup(RG_NAME_2)
                .create();
        createdWebApp2 = true;
        Assert.assertNotNull(webApp2);
        Assert.assertEquals(Region.US_WEST, webApp1.region());

        WebApp webApp3 = appServiceManager.webApps().define(WEBAPP_NAME_3)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(RG_NAME_3)
                .withNewWindowsPlan(PricingTier.BASIC_B1)
                .withJavaVersion(JavaVersion.JAVA_8_NEWEST)
                .withWebContainer(WebContainer.TOMCAT_8_0_NEWEST)
                //.withRemoteDebuggingEnabled(RemoteVisualStudioVersion.VS2015)
                .create();
        createdWebApp3 = true;
        Assert.assertNotNull(webApp3);
        Assert.assertEquals(Region.US_WEST, webApp3.region());
        AppServicePlan plan3 = appServiceManager.appServicePlans().getById(webApp3.appServicePlanId());
        Assert.assertNotNull(plan3);
        Assert.assertEquals(Region.US_WEST, plan3.region());
        Assert.assertEquals(PricingTier.BASIC_B1, plan3.pricingTier());

        WebApp webApp4 = appServiceManager.webApps().define(WEBAPP_NAME_4)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(RG_NAME_4)
                .withNewWindowsPlan(PricingTier.BASIC_B1)
                .withNodeVersion(NodeVersion.NODE6_5)
                //.withRemoteDebuggingEnabled(RemoteVisualStudioVersion.VS2015)
                .create();
        createdWebApp4 = true;
        Assert.assertNotNull(webApp4);
        Assert.assertEquals(Region.US_WEST, webApp4.region());
        AppServicePlan plan4 = appServiceManager.appServicePlans().getById(webApp4.appServicePlanId());
        Assert.assertNotNull(plan4);
        Assert.assertEquals(Region.US_WEST, plan4.region());
        Assert.assertEquals(PricingTier.BASIC_B1, plan4.pricingTier());

        // Get
        WebApp webApp = appServiceManager.webApps().getByResourceGroup(RG_NAME_1, webApp1.name());
        Assert.assertEquals(webApp1.id(), webApp.id());
        webApp = appServiceManager.webApps().getById(webApp2.id());
        Assert.assertEquals(webApp2.name(), webApp.name());

        // List
        List<WebApp> webApps = appServiceManager.webApps().listByResourceGroup(RG_NAME_1);
        Assert.assertEquals(1, webApps.size());
        webApps = appServiceManager.webApps().listByResourceGroup(RG_NAME_2);
        Assert.assertEquals(1, webApps.size());

        // Update
        webApp1.update()
                .withNewAppServicePlan(PricingTier.STANDARD_S2)
                .apply();
        AppServicePlan plan2 = appServiceManager.appServicePlans().getById(webApp1.appServicePlanId());
        Assert.assertNotNull(plan2);
        Assert.assertEquals(Region.US_WEST, plan2.region());
        Assert.assertEquals(PricingTier.STANDARD_S2, plan2.pricingTier());

    }

    @Test
    public void canUpdateRuntimes() {

        //Get the runtime data and check that all the altest runtimes do have it
        ProvidersInner providerInner = new ProvidersInner(restClient.retrofit(), new WebSiteManagementClientImpl(restClient)
                .withSubscriptionId(appServiceManager.subscriptionId()));

        Iterator<ApplicationStackInner> stackIter = providerInner.getAvailableStacks("Windows").iterator();

        //Get the runtimes from appSvcManager
        AppServiceRuntimes runtimes = appServiceManager.latestWindowsRuntimes();

        //Check if all the AppServiceRuntimesImpl returned by the providerInner are present in the runtimes
        while (stackIter.hasNext()) {
            ApplicationStackInner stackInfo = stackIter.next();

            String valuesNotFound = null;
            if (stackInfo.name().equalsIgnoreCase(WebContainer.COMPONENT_NAME)){
                valuesNotFound = checkJavaContainerEnumContainsAllValues(stackInfo.properties().frameworks(), runtimes.Webcontainers());
            } else {
                valuesNotFound = checkRuntimeContainsValues(stackInfo.name(), stackInfo.properties().majorVersions(), runtimes);
            }

            Assert.assertNull(valuesNotFound);
        }
    }

    private String checkJavaContainerEnumContainsAllValues(List<ApplicationStackInner.Properties> frameworks, Collection<WebContainer> webContainerCollection) {
        String valuesNotFound = null;

        for(ApplicationStackInner.Properties framerwork : frameworks) {
            for (StackMajorVersion majorVersion : framerwork.majorVersions()) {
                String majorVersionString = framerwork.name() + WebContainer.SEPERATOR + majorVersion.runtimeVersion();

                if (!WebContainer.OFF.containsVersion(majorVersionString)) {
                    valuesNotFound += "\'" + majorVersionString + "\' ";
                }

                //for webContainers we process the minor versions as well
                if (majorVersion.minorVersions() != null) {
                    for (StackMinorVersion minorVersion : majorVersion.minorVersions()) {
                        String minorVersionString = framerwork.name() + WebContainer.SEPERATOR + minorVersion.runtimeVersion();
                        if (!WebContainer.OFF.containsVersion(minorVersionString)) {
                            valuesNotFound += "\'" + minorVersionString + "\' ";
                        }
                    }
                }
            }
        }

        return valuesNotFound;
    }

    private String checkRuntimeContainsValues(String runtimeName, List<StackMajorVersion> majorVersions, AppServiceRuntimes runtimes) {
        String valuesNotFound = null;
        boolean checkMinorVersion = false;
        boolean checkRuntimeVersion = true;

        RuntimeVersion runtimeVersionToUse = null;
        switch (runtimeName) {
            case JavaVersion.COMPONENT_NAME: {
                runtimeVersionToUse = JavaVersion.OFF;
                checkMinorVersion = true;
                break;
            }
            case NodeVersion.COMPONENT_NAME: {
                runtimeVersionToUse = NodeVersion.OFF;
                break;
            }
            case PhpVersion.COMPONENT_NAME: {
                runtimeVersionToUse = PhpVersion.OFF;
                break;
            }
            case PythonVersion.COMPONENT_NAME: {
                runtimeVersionToUse = PythonVersion.OFF;
                break;
            }
            case NetFrameworkVersion.COMPONENT_NAME: {
                runtimeVersionToUse = NetFrameworkVersion.OFF;
                checkRuntimeVersion = false;
                break;
            }
            default :
                //runtime name not found
                return runtimeName;
        }

        for (StackMajorVersion majorVersion : majorVersions) {
            String majorVersionString = majorVersion.runtimeVersion();
            if (!checkRuntimeVersion) {
                majorVersionString = majorVersion.displayVersion();
            }

            if (!runtimeVersionToUse.containsVersion(majorVersionString)) {
                valuesNotFound += "\'" + majorVersionString + "\' ";
            }

            if (checkMinorVersion && majorVersion.minorVersions() != null) {
                for (StackMinorVersion minorVersion : majorVersion.minorVersions()) {
                    String minorVersionString = minorVersion.runtimeVersion();
                    if (!checkRuntimeVersion) {
                        minorVersionString = minorVersion.displayVersion();
                    }

                    if (!runtimeVersionToUse.containsVersion(minorVersionString)) {
                        valuesNotFound += "\'" + minorVersionString + "\' ";
                    }
                }
            }
        }

        return valuesNotFound;
    }
}