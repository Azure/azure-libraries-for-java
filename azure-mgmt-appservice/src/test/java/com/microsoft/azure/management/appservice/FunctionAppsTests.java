/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice;

import com.microsoft.azure.CloudException;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.storage.SkuName;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.azure.management.storage.implementation.StorageManager;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FunctionAppsTests extends AppServiceTest {
    private static String RG_NAME_1 = "";
    private static String RG_NAME_2 = "";
    private static String WEBAPP_NAME_1 = "";
    private static String WEBAPP_NAME_2 = "";
    private static String WEBAPP_NAME_3 = "";
    private static String APP_SERVICE_PLAN_NAME_1 = "";
    private static String APP_SERVICE_PLAN_NAME_2 = "";
    private static String STORAGE_ACCOUNT_NAME_1 = "";

    protected static StorageManager storageManager;

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        WEBAPP_NAME_1 = generateRandomResourceName("java-func-", 20);
        WEBAPP_NAME_2 = generateRandomResourceName("java-func-", 20);
        WEBAPP_NAME_3 = generateRandomResourceName("java-func-", 20);
        APP_SERVICE_PLAN_NAME_1 = generateRandomResourceName("java-asp-", 20);
        APP_SERVICE_PLAN_NAME_2 = generateRandomResourceName("java-asp-", 20);
        STORAGE_ACCOUNT_NAME_1 = generateRandomResourceName("javastore", 20);
        RG_NAME_1 = generateRandomResourceName("javacsmrg", 20);
        RG_NAME_2 = generateRandomResourceName("javacsmrg", 20);

        storageManager = StorageManager
                .authenticate(restClient, defaultSubscription);

        super.initializeClients(restClient, defaultSubscription, domain);
    }

    @Override
    protected void cleanUpResources() {
        resourceManager.resourceGroups().beginDeleteByName(RG_NAME_1);
        try {
            resourceManager.resourceGroups().beginDeleteByName(RG_NAME_2);
        } catch (CloudException e) {
            // fine, RG_NAME_2 is not created
        }
    }

    @Test
    public void canCRUDFunctionApp() throws Exception {
        // Create with consumption
        FunctionApp functionApp1 = appServiceManager.functionApps().define(WEBAPP_NAME_1)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(RG_NAME_1)
                .create();
        Assert.assertNotNull(functionApp1);
        Assert.assertEquals(Region.US_WEST, functionApp1.region());
        AppServicePlan plan1 = appServiceManager.appServicePlans().getById(functionApp1.appServicePlanId());
        Assert.assertNotNull(plan1);
        Assert.assertEquals(Region.US_WEST, plan1.region());
        Assert.assertEquals(new PricingTier("Dynamic", "Y1"), plan1.pricingTier());

        FunctionAppResource functionAppResource1 = getStorageAccount(storageManager, functionApp1);
        // consumption plan requires this 2 settings
        Assert.assertTrue(functionAppResource1.appSettings.containsKey(KEY_CONTENT_AZURE_FILE_CONNECTION_STRING));
        Assert.assertTrue(functionAppResource1.appSettings.containsKey(KEY_CONTENT_SHARE));
        Assert.assertEquals(functionAppResource1.appSettings.get(KEY_AZURE_WEB_JOBS_STORAGE).value(), functionAppResource1.appSettings.get(KEY_CONTENT_AZURE_FILE_CONNECTION_STRING).value());
        // verify accountKey
        Assert.assertEquals(functionAppResource1.storageAccount.getKeys().get(0).value(), functionAppResource1.accountKey);

        // Create with the same consumption plan
        FunctionApp functionApp2 = appServiceManager.functionApps().define(WEBAPP_NAME_2)
                .withExistingAppServicePlan(plan1)
                .withNewResourceGroup(RG_NAME_2)
                .withExistingStorageAccount(functionApp1.storageAccount())
                .create();
        Assert.assertNotNull(functionApp2);
        Assert.assertEquals(Region.US_WEST, functionApp2.region());

        // Create with app service plan
        FunctionApp functionApp3 = appServiceManager.functionApps().define(WEBAPP_NAME_3)
                .withRegion(Region.US_WEST)
                .withExistingResourceGroup(RG_NAME_2)
                .withNewAppServicePlan(PricingTier.BASIC_B1)
                .withExistingStorageAccount(functionApp1.storageAccount())
                .create();
        Assert.assertNotNull(functionApp2);
        Assert.assertEquals(Region.US_WEST, functionApp2.region());

        // app service plan does not have this 2 settings
        // https://github.com/Azure/azure-libraries-for-net/issues/485
        FunctionAppResource functionAppResource3 = getStorageAccount(storageManager, functionApp3);
        Assert.assertFalse(functionAppResource3.appSettings.containsKey(KEY_CONTENT_AZURE_FILE_CONNECTION_STRING));
        Assert.assertFalse(functionAppResource3.appSettings.containsKey(KEY_CONTENT_SHARE));
        // verify accountKey
        Assert.assertEquals(functionAppResource3.storageAccount.getKeys().get(0).value(), functionAppResource3.accountKey);

        // Get
        FunctionApp functionApp = appServiceManager.functionApps().getByResourceGroup(RG_NAME_1, functionApp1.name());
        Assert.assertEquals(functionApp1.id(), functionApp.id());
        functionApp = appServiceManager.functionApps().getById(functionApp2.id());
        Assert.assertEquals(functionApp2.name(), functionApp.name());

        // List
        List<FunctionApp> functionApps = appServiceManager.functionApps().listByResourceGroup(RG_NAME_1);
        Assert.assertEquals(1, functionApps.size());
        functionApps = appServiceManager.functionApps().listByResourceGroup(RG_NAME_2);
        Assert.assertEquals(2, functionApps.size());

        // Update
        functionApp2.update()
                .withNewStorageAccount(STORAGE_ACCOUNT_NAME_1, SkuName.STANDARD_GRS)
                .apply();
        Assert.assertEquals(STORAGE_ACCOUNT_NAME_1, functionApp2.storageAccount().name());

        FunctionAppResource functionAppResource2 = getStorageAccount(storageManager, functionApp2);
        Assert.assertTrue(functionAppResource2.appSettings.containsKey(KEY_CONTENT_AZURE_FILE_CONNECTION_STRING));
        Assert.assertTrue(functionAppResource2.appSettings.containsKey(KEY_CONTENT_SHARE));
        Assert.assertEquals(functionAppResource2.appSettings.get(KEY_AZURE_WEB_JOBS_STORAGE).value(), functionAppResource2.appSettings.get(KEY_CONTENT_AZURE_FILE_CONNECTION_STRING).value());
        Assert.assertEquals(STORAGE_ACCOUNT_NAME_1, functionAppResource2.storageAccount.name());
        Assert.assertEquals(functionAppResource2.storageAccount.getKeys().get(0).value(), functionAppResource2.accountKey);

        // Update, verify modify AppSetting does not create new storage account
        // https://github.com/Azure/azure-libraries-for-net/issues/457
        int numStorageAccountBefore = storageManager.storageAccounts().listByResourceGroup(RG_NAME_1).size();
        functionApp1.update()
                .withAppSetting("newKey", "newValue")
                .apply();
        int numStorageAccountAfter = storageManager.storageAccounts().listByResourceGroup(RG_NAME_1).size();
        Assert.assertEquals(numStorageAccountBefore, numStorageAccountAfter);
        FunctionAppResource functionAppResource1Updated = getStorageAccount(storageManager, functionApp1);
        Assert.assertTrue(functionAppResource1Updated.appSettings.containsKey("newKey"));
        Assert.assertEquals(functionAppResource1.appSettings.get(KEY_AZURE_WEB_JOBS_STORAGE).value(), functionAppResource1Updated.appSettings.get(KEY_AZURE_WEB_JOBS_STORAGE).value());
        Assert.assertEquals(functionAppResource1.appSettings.get(KEY_CONTENT_AZURE_FILE_CONNECTION_STRING).value(), functionAppResource1Updated.appSettings.get(KEY_CONTENT_AZURE_FILE_CONNECTION_STRING).value());
        Assert.assertEquals(functionAppResource1.appSettings.get(KEY_CONTENT_SHARE).value(), functionAppResource1Updated.appSettings.get(KEY_CONTENT_SHARE).value());
        Assert.assertEquals(functionAppResource1.storageAccount.name(), functionAppResource1Updated.storageAccount.name());

        // Scale
        functionApp3.update()
                .withNewAppServicePlan(PricingTier.STANDARD_S2)
                .apply();
        Assert.assertNotEquals(functionApp3.appServicePlanId(), functionApp1.appServicePlanId());
    }

    @Test
    public void canCRUDLinuxFunctionApp() throws Exception {
        // function app with consumption plan
        FunctionApp functionApp1 = appServiceManager.functionApps().define(WEBAPP_NAME_1)
                .withRegion(Region.US_EAST)
                .withNewResourceGroup(RG_NAME_1)
                .withNewLinuxConsumptionPlan()
                .withBuiltInImage(FunctionRuntimeStack.JAVA_8)
                .create();
        Assert.assertNotNull(functionApp1);
        assertLinuxJava8(functionApp1, FunctionRuntimeStack.JAVA_8.getLinuxFxVersionForConsumptionPlan());

        AppServicePlan plan1 = appServiceManager.appServicePlans().getById(functionApp1.appServicePlanId());
        Assert.assertNotNull(plan1);
        Assert.assertEquals(Region.US_EAST, plan1.region());
        Assert.assertEquals(new PricingTier(com.microsoft.azure.management.appservice.SkuName.DYNAMIC.toString(), "Y1"), plan1.pricingTier());
        Assert.assertTrue(plan1.inner().reserved());
        Assert.assertTrue(Arrays.asList(functionApp1.inner().kind().split(",")).containsAll(Arrays.asList("linux", "functionapp")));

        // deploy
        functionApp1.update()
                .withAppSetting("SCM_DO_BUILD_DURING_DEPLOYMENT", "false")
                .apply();
        if (!isPlaybackMode()) {
            SdkContext.sleep(5000);
            functionApp1.zipDeploy(new File(FunctionAppsTests.class.getResource("/java-functions.zip").getPath()));
        }

        List<FunctionApp> functionApps = appServiceManager.functionApps().listByResourceGroup(RG_NAME_1);
        Assert.assertEquals(1, functionApps.size());

        // function app with app service plan
        FunctionApp functionApp2 = appServiceManager.functionApps().define(WEBAPP_NAME_2)
                .withRegion(Region.US_EAST)
                .withExistingResourceGroup(RG_NAME_1)
                .withNewLinuxAppServicePlan(PricingTier.STANDARD_S1)
                .withBuiltInImage(FunctionRuntimeStack.JAVA_8)
                .withAppSetting("WEBSITE_RUN_FROM_PACKAGE", "1")
                .create();
        Assert.assertNotNull(functionApp2);
        assertLinuxJava8(functionApp2, FunctionRuntimeStack.JAVA_8.getLinuxFxVersionForDedicatedPlan());

        AppServicePlan plan2 = appServiceManager.appServicePlans().getById(functionApp2.appServicePlanId());
        Assert.assertNotNull(plan2);
        Assert.assertEquals(PricingTier.STANDARD_S1, plan2.pricingTier());
        Assert.assertTrue(plan2.inner().reserved());

        // one more function app using existing app service plan
        FunctionApp functionApp3 = appServiceManager.functionApps().define(WEBAPP_NAME_3)
                .withExistingLinuxAppServicePlan(plan2)
                .withExistingResourceGroup(RG_NAME_1)
                .withBuiltInImage(FunctionRuntimeStack.JAVA_8)
                .withAppSetting("WEBSITE_RUN_FROM_PACKAGE", "1")
                .create();
        Assert.assertNotNull(functionApp3);
        assertLinuxJava8(functionApp3, FunctionRuntimeStack.JAVA_8.getLinuxFxVersionForDedicatedPlan());

        // deploy
        if (!isPlaybackMode()) {
            functionApp2.zipDeploy(new File(FunctionAppsTests.class.getResource("/java-functions.zip").getPath()));
            functionApp3.zipDeploy(new File(FunctionAppsTests.class.getResource("/java-functions.zip").getPath()));
            SdkContext.sleep(10000);
        }

        functionApps = appServiceManager.functionApps().listByResourceGroup(RG_NAME_1);
        Assert.assertEquals(3, functionApps.size());

        // verify deploy
        List<FunctionEnvelope> functions = appServiceManager.functionApps().listFunctions(functionApp1.resourceGroupName(), functionApp1.name());
        Assert.assertEquals(1, functions.size());

        functions = appServiceManager.functionApps().listFunctions(functionApp2.resourceGroupName(), functionApp2.name());
        Assert.assertEquals(1, functions.size());

        functions = appServiceManager.functionApps().listFunctions(functionApp3.resourceGroupName(), functionApp3.name());
        Assert.assertEquals(1, functions.size());
    }

    @Test
    public void canCRUDLinuxFunctionAppPremium() {
        // function app with premium plan
        FunctionApp functionApp1 = appServiceManager.functionApps().define(WEBAPP_NAME_1)
                .withRegion(Region.US_EAST)
                .withNewResourceGroup(RG_NAME_1)
                .withNewLinuxAppServicePlan(new PricingTier(com.microsoft.azure.management.appservice.SkuName.ELASTIC_PREMIUM.toString(), "EP1"))
                .withBuiltInImage(FunctionRuntimeStack.JAVA_8)
                .withAppSetting("WEBSITE_RUN_FROM_PACKAGE", "1")
                .create();
        Assert.assertNotNull(functionApp1);
        AppServicePlan plan1 = appServiceManager.appServicePlans().getById(functionApp1.appServicePlanId());
        Assert.assertNotNull(plan1);
        Assert.assertEquals(new PricingTier(com.microsoft.azure.management.appservice.SkuName.ELASTIC_PREMIUM.toString(), "EP1"), plan1.pricingTier());
        Assert.assertTrue(plan1.inner().reserved());
        assertLinuxJava8(functionApp1, FunctionRuntimeStack.JAVA_8.getLinuxFxVersionForDedicatedPlan());

        // deploy
        if (!isPlaybackMode()) {
            functionApp1.zipDeploy(new File(FunctionAppsTests.class.getResource("/java-functions.zip").getPath()));
            SdkContext.sleep(30000);
        }

        // verify deploy
        List<FunctionEnvelope> functions = appServiceManager.functionApps().listFunctions(functionApp1.resourceGroupName(), functionApp1.name());
        Assert.assertEquals(1, functions.size());
    }

    @Test
    @Ignore("Need container registry")
    public void canCRUDLinuxFunctionAppPremiumDocker() {
        // function app with premium plan with private docker
        FunctionApp functionApp1 = appServiceManager.functionApps().define(WEBAPP_NAME_1)
                .withRegion(Region.US_EAST)
                .withNewResourceGroup(RG_NAME_1)
                .withNewLinuxAppServicePlan(new PricingTier(com.microsoft.azure.management.appservice.SkuName.ELASTIC_PREMIUM.toString(), "EP1"))
                .withPrivateRegistryImage("weidxuregistry.azurecr.io/az-func-java:v1", "https://weidxuregistry.azurecr.io")
                .withCredentials("weidxuregistry", "PASSWORD")
                .withRuntime("java")
                .withRuntimeVersion("~2")
                .withAppSetting("WEBSITE_RUN_FROM_PACKAGE", "1")
                .create();

        // deploy
        if (!isPlaybackMode()) {
            functionApp1.zipDeploy(new File(FunctionAppsTests.class.getResource("/java-functions.zip").getPath()));
        }
    }

    private static Map<String, AppSetting> assertLinuxJava8(FunctionApp functionApp, String linuxFxVersion) {
        Assert.assertEquals(linuxFxVersion, functionApp.linuxFxVersion());
        Assert.assertTrue(Arrays.asList(functionApp.inner().kind().split(",")).containsAll(Arrays.asList("linux", "functionapp")));
        Assert.assertTrue(functionApp.inner().reserved());

        Map<String, AppSetting> appSettings = functionApp.getAppSettings();
        Assert.assertNotNull(appSettings);
        Assert.assertNotNull(appSettings.get(KEY_AZURE_WEB_JOBS_STORAGE));
        Assert.assertEquals(FunctionRuntimeStack.JAVA_8.runtime(), appSettings.get(KEY_FUNCTIONS_WORKER_RUNTIME).value());
        Assert.assertEquals(FunctionRuntimeStack.JAVA_8.version(), appSettings.get(KEY_FUNCTIONS_EXTENSION_VERSION).value());

        return appSettings;
    }

    private static String KEY_AZURE_WEB_JOBS_STORAGE = "AzureWebJobsStorage";
    private static String KEY_CONTENT_AZURE_FILE_CONNECTION_STRING = "WEBSITE_CONTENTAZUREFILECONNECTIONSTRING";
    private static String KEY_CONTENT_SHARE = "WEBSITE_CONTENTSHARE";
    private static String KEY_FUNCTIONS_WORKER_RUNTIME = "FUNCTIONS_WORKER_RUNTIME";
    private static String KEY_FUNCTIONS_EXTENSION_VERSION = "FUNCTIONS_EXTENSION_VERSION";

    private static String ACCOUNT_NAME_SEGMENT = "AccountName=";
    private static String ACCOUNT_KEY_SEGMENT = "AccountKey=";

    private static class FunctionAppResource {
        Map<String, AppSetting> appSettings;

        String accountName;
        String accountKey;

        StorageAccount storageAccount;
    }

    private static FunctionAppResource getStorageAccount(StorageManager storageManager,
                                                         FunctionApp functionApp) {
        FunctionAppResource resource = new FunctionAppResource();
        resource.appSettings = functionApp.getAppSettings();

        String storageAccountConnectionString = resource.appSettings.get(KEY_AZURE_WEB_JOBS_STORAGE).value();
        String[] segments = storageAccountConnectionString.split(";");
        for (String segment : segments) {
            if (segment.startsWith(ACCOUNT_NAME_SEGMENT)) {
                resource.accountName = segment.substring(ACCOUNT_NAME_SEGMENT.length());
            } else if (segment.startsWith(ACCOUNT_KEY_SEGMENT)) {
                resource.accountKey = segment.substring(ACCOUNT_KEY_SEGMENT.length());
            }
        }
        if (resource.accountName != null) {
            PagedList<StorageAccount> storageAccounts = storageManager.storageAccounts().list();
            for (StorageAccount storageAccount : storageAccounts) {
                if (resource.accountName.equals(storageAccount.name())) {
                    resource.storageAccount = storageAccount;
                    break;
                }
            }
        }

        return resource;
    }

    private static String readLine(InputStream in) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int c;
        for (c = in.read(); c != '\n' && c >= 0 ; c = in.read()) {
            stream.write(c);
        }
        if (c == -1 && stream.size() == 0) {
            return null;
        }
        return stream.toString("UTF-8");
    }
}