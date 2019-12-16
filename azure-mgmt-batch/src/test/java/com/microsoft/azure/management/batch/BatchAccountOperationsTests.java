/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.batch;

import com.microsoft.azure.CloudException;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;

public class BatchAccountOperationsTests extends BatchManagementTest {
    @Test
    public void canCRUDBatchAccount() throws Exception {
        // Create
        Observable<Indexable> resourceStream = batchManager.batchAccounts()
                .define(BATCH_NAME)
                .withRegion(Region.US_CENTRAL)
                .withNewResourceGroup(RG_NAME)
                .createAsync();

        BatchAccount batchAccount = Utils.<BatchAccount>rootResource(resourceStream)
                .toBlocking().last();
        Assert.assertEquals(RG_NAME, batchAccount.resourceGroupName());
        Assert.assertNull(batchAccount.autoStorage());
        // List
        List<BatchAccount> accounts = batchManager.batchAccounts().listByResourceGroup(RG_NAME);
        boolean found = false;
        for (BatchAccount account : accounts) {
            if (account.name().equals(BATCH_NAME)) {
                found = true;
            }
        }
        Assert.assertTrue(found);
        // Get
        batchAccount = batchManager.batchAccounts().getByResourceGroup(RG_NAME, BATCH_NAME);
        Assert.assertNotNull(batchAccount);

        // Get Keys
        BatchAccountKeys keys = batchAccount.getKeys();
        Assert.assertNotNull(keys.primary());
        Assert.assertNotNull(keys.secondary());

        BatchAccountKeys newKeys = batchAccount.regenerateKeys(AccountKeyType.PRIMARY);
        Assert.assertNotNull(newKeys.primary());
        Assert.assertNotNull(newKeys.secondary());

        Assert.assertNotEquals(newKeys.primary(), keys.primary());
        Assert.assertEquals(newKeys.secondary(), keys.secondary());

        batchAccount = batchAccount.update()
                .withNewStorageAccount(SA_NAME)
                .apply();

        Assert.assertNotNull(batchAccount.autoStorage().storageAccountId());
        Assert.assertNotNull(batchAccount.autoStorage().lastKeySync());
        DateTime lastSync = batchAccount.autoStorage().lastKeySync();

        batchAccount.synchronizeAutoStorageKeys();
        batchAccount.refresh();

        Assert.assertNotEquals(lastSync, batchAccount.autoStorage().lastKeySync());

        // Test applications.
        String applicationId = "myApplication";
        String applicationDisplayName = "displayName";
        String applicationPackageName = "applicationPackage";

        boolean updatesAllowed = true;
        batchAccount.update()
                .defineNewApplication(applicationId)
                    .defineNewApplicationPackage(applicationPackageName)
                .withDisplayName(applicationDisplayName)
                .withAllowUpdates(updatesAllowed)
                .attach()
                .apply();
        Assert.assertTrue(batchAccount.applications().containsKey(applicationId));

        // Refresh to fetch batch account and application again.
        batchAccount.refresh();
        Assert.assertTrue(batchAccount.applications().containsKey(applicationId));

        Application application = batchAccount.applications().get(applicationId);
        Assert.assertEquals(application.displayName(), applicationDisplayName);
        Assert.assertEquals(application.updatesAllowed(), updatesAllowed);
        Assert.assertEquals(1, application.applicationPackages().size());
        ApplicationPackage applicationPackage = application.applicationPackages().get(applicationPackageName);
        Assert.assertEquals(applicationPackage.name(), applicationPackageName);

        // Delete application package directly.
        applicationPackage.delete();
        batchAccount
                .update()
                .withoutApplication(applicationId)
                .apply();

        SdkContext.sleep(30 * 1000);
        batchAccount.refresh();
        Assert.assertFalse(batchAccount.applications().containsKey(applicationId));

        String applicationPackage1Name = "applicationPackage1";
        String applicationPackage2Name = "applicationPackage2";
        batchAccount.update()
                .defineNewApplication(applicationId)
                    .defineNewApplicationPackage(applicationPackage1Name)
                    .defineNewApplicationPackage(applicationPackage2Name)
                .withDisplayName(applicationDisplayName)
                .withAllowUpdates(updatesAllowed)
                .attach()
                .apply();
        Assert.assertTrue(batchAccount.applications().containsKey(applicationId));
        application.refresh();
        Assert.assertEquals(2, application.applicationPackages().size());

        String newApplicationDisplayName = "newApplicationDisplayName";
        batchAccount
                .update()
                .updateApplication(applicationId)
                    .withoutApplicationPackage(applicationPackage2Name)
                .withDisplayName(newApplicationDisplayName)
                .parent()
                .apply();
        application = batchAccount.applications().get(applicationId);
        Assert.assertEquals(application.displayName(), newApplicationDisplayName);

        batchAccount.refresh();
        application = batchAccount.applications().get(applicationId);

        Assert.assertEquals(application.displayName(), newApplicationDisplayName);
        Assert.assertEquals(1, application.applicationPackages().size());

        applicationPackage = application.applicationPackages().get(applicationPackage1Name);

        Assert.assertNotNull(applicationPackage);
        String id = applicationPackage.id();
        Assert.assertNotNull(applicationPackage.id());
        Assert.assertEquals(applicationPackage.name(), applicationPackage1Name);
        Assert.assertNull(applicationPackage.format());

        batchAccount
                .update()
                .updateApplication(applicationId)
                    .withoutApplicationPackage(applicationPackage1Name)
                .parent()
                .apply();
        try {
            batchManager.batchAccounts().deleteByResourceGroup(batchAccount.resourceGroupName(), batchAccount.name());
        } catch (CloudException cloudException) {
            // Service bud: LRO fail with error
            //      {
            //          "error":
            //              {
            //                 "code":"ResourceNotFound",
            //                  "message":"The Resource 'Microsoft.Batch/batchAccounts/javabatch78086' under resource group 'javabatchrg43b36438' was not found."
            //               }
            //      }
            //
        }

        batchAccount = batchManager.batchAccounts().getById(batchAccount.id());
        Assert.assertNull(batchAccount);

    }

    @Test
    public void canCRUDBatchPool() throws Exception {
        //Create
        Observable<Indexable> resourceStream = batchManager.batchAccounts()
                .define(BATCH_NAME)
                .withRegion(Region.US_CENTRAL)
                .withNewResourceGroup(RG_NAME)
                .createAsync();

        BatchAccount batchAccount = Utils.<BatchAccount>rootResource(resourceStream)
                .toBlocking().last();
        Assert.assertEquals(RG_NAME, batchAccount.resourceGroupName());
        Assert.assertNull(batchAccount.autoStorage());
        // List
        List<BatchAccount> accounts = batchManager.batchAccounts().listByResourceGroup(RG_NAME);
        boolean found = false;
        for (BatchAccount account : accounts) {
            if (account.name().equals(BATCH_NAME)) {
                found = true;
            }
        }
        Assert.assertTrue(found);

        // Get
        batchAccount = batchManager.batchAccounts().getByResourceGroup(RG_NAME, BATCH_NAME);
        Assert.assertNotNull(batchAccount);

        // Get Keys
        BatchAccountKeys keys = batchAccount.getKeys();
        Assert.assertNotNull(keys.primary());
        Assert.assertNotNull(keys.secondary());

        BatchAccountKeys newKeys = batchAccount.regenerateKeys(AccountKeyType.PRIMARY);
        Assert.assertNotNull(newKeys.primary());
        Assert.assertNotNull(newKeys.secondary());

        Assert.assertNotEquals(newKeys.primary(), keys.primary());
        Assert.assertEquals(newKeys.secondary(), keys.secondary());

        batchAccount = batchAccount.update()
                .withNewStorageAccount(SA_NAME)
                .apply();

        Assert.assertNotNull(batchAccount.autoStorage().storageAccountId());
        Assert.assertNotNull(batchAccount.autoStorage().lastKeySync());
        DateTime lastSync = batchAccount.autoStorage().lastKeySync();

        batchAccount.synchronizeAutoStorageKeys();
        batchAccount.refresh();

        Assert.assertNotEquals(lastSync, batchAccount.autoStorage().lastKeySync());

        //Test Pool
        String poolId = "testpool";
        String displayName = "my-pool-name";
        String vmSize = "STANDARD_D4";
        int maxTasksPerNode = 13;

        //create task scheduling policy
        TaskSchedulingPolicy taskSchedulingPolicy = new TaskSchedulingPolicy();
        taskSchedulingPolicy.withNodeFillType(ComputeNodeFillType.PACK);

        //create deployment configuration
        String osFamily = "4";
        String osVersion = "WA-GUEST-OS-4.45_201708-01";
        DeploymentConfiguration deploymentConfiguration = new DeploymentConfiguration();
        deploymentConfiguration.withCloudServiceConfiguration(new CloudServiceConfiguration());
        deploymentConfiguration.cloudServiceConfiguration()
                .withOsFamily(osFamily)
                .withOsVersion(osVersion);

        //create scale settings
        int targetDedicateNodes = 6;
        String resizeTimeout = "PT8M";
        ScaleSettings scaleSettings = new ScaleSettings();
        scaleSettings.withFixedScale(new FixedScaleSettings());
        scaleSettings.fixedScale().withTargetDedicatedNodes(targetDedicateNodes)
                .withResizeTimeout(Period.parse(resizeTimeout))
                .withNodeDeallocationOption(ComputeNodeDeallocationOption.TASK_COMPLETION);

        //create meta data
        String metadataName1 = "metadata-1";
        String metadataName2 = "metadata-2";
        String metadataValue1 = "value-1";
        String metadataValue2 = "value-2";
        List<MetadataItem> metadata = new ArrayList<>();
        MetadataItem metadataItem1 = new MetadataItem();
        metadataItem1.withName(metadataName1)
                .withValue(metadataValue1);

        MetadataItem metadataItem2 = new MetadataItem();
        metadataItem2.withName(metadataName2)
                .withValue(metadataValue2);

        metadata.add(metadataItem1);
        metadata.add(metadataItem2);

        //create start task
        String cmdLine = "cmd /c SET";
        int maxTaskRetryCount = 6;
        String httpUrl = "https://testaccount.blob.core.windows.net/example-blob-file";
        String filePath = "c:\\\\temp\\\\gohere";
        String fileMode = "777";
        String envName = "MYSET";
        String envValue = "1234";
        StartTask startTask = new StartTask();
        startTask.withCommandLine(cmdLine)
                .withResourceFiles(new ArrayList<ResourceFile>())
                .withEnvironmentSettings(new ArrayList<EnvironmentSetting>())
                .withUserIdentity(new UserIdentity())
                .withMaxTaskRetryCount(maxTaskRetryCount)
                .withWaitForSuccess(Boolean.TRUE);

        startTask.resourceFiles().add(new ResourceFile());
        startTask.resourceFiles().get(0).withHttpUrl(httpUrl)
                .withFilePath(filePath)
                .withFileMode(fileMode);

        startTask.environmentSettings().add(new EnvironmentSetting());
        startTask.environmentSettings().get(0).withName(envName)
                .withValue(envValue);

        startTask.userIdentity().withAutoUser(new AutoUserSpecification());
        startTask.userIdentity().autoUser().withScope(AutoUserScope.POOL)
                .withElevationLevel(ElevationLevel.ADMIN);

        //create user accounts
        String userAccountName = "username1";
        String userAccountPassword = "fluentPA$$w0rdForBATCH";
        String sshPrivateKey = "sshprivatekeyvalue";
        int uid = 1234;
        int gid = 4567;
        List<UserAccount> userAccounts = new ArrayList<>();
        userAccounts.add(new UserAccount());
        userAccounts.get(0).withName(userAccountName)
                .withPassword(userAccountPassword)
                .withElevationLevel(ElevationLevel.ADMIN)
                .withLinuxUserConfiguration(new LinuxUserConfiguration());

        userAccounts.get(0).linuxUserConfiguration().withUid(uid)
                .withGid(gid)
                .withSshPrivateKey(sshPrivateKey);

        //define new pool
        batchAccount.update()
                .defineNewPool(poolId)
                .withDisplayName(displayName)
                .withVmSize(vmSize)
                .withInterNodeCommunication(InterNodeCommunicationState.ENABLED)
                .withMaxTasksPerNode(maxTasksPerNode)
                .withTaskSchedulingPolicy(taskSchedulingPolicy)
                .withDeploymentConfiguration(deploymentConfiguration)
                .withScaleSettings(scaleSettings)
                .withMetadata(metadata)
                .withStartTask(startTask)
                .withUserAccounts(userAccounts)
                .attach()
                .apply();

        Assert.assertTrue(batchAccount.pools().containsKey(poolId));
        batchAccount.refresh();
        Assert.assertTrue(batchAccount.pools().containsKey(poolId));

        Pool pool = batchAccount.pools().get(poolId);
        Assert.assertEquals(vmSize, pool.vmSize());
        Assert.assertNull(pool.mountConfiguration());
        Assert.assertNotNull(pool.startTask());
        Assert.assertNotNull(pool.startTask().maxTaskRetryCount());
        Assert.assertEquals(maxTaskRetryCount, pool.startTask().maxTaskRetryCount().intValue());
        Assert.assertEquals(1, pool.userAccounts().size());

        batchAccount.update()
                .withoutPool(poolId)
                .apply();

        batchAccount.refresh();
        Assert.assertFalse(batchAccount.pools().containsKey(poolId));

        batchManager.batchAccounts().deleteByResourceGroup(batchAccount.resourceGroupName(), batchAccount.name());
        batchAccount = batchManager.batchAccounts().getById(batchAccount.id());
        Assert.assertNull(batchAccount);
    }

    @Test
    public void canCreateBatchAccountWithApplication() throws Exception {
        String applicationId = "myApplication";
        String applicationDisplayName = "displayName";
        boolean allowUpdates = true;

        // Create
        Observable<Indexable> resourceStream = batchManager.batchAccounts()
                .define(BATCH_NAME)
                .withRegion(Region.US_CENTRAL)
                .withNewResourceGroup(RG_NAME)
                .defineNewApplication(applicationId)
                .withDisplayName(applicationDisplayName)
                .withAllowUpdates(allowUpdates)
                .attach()
                .withNewStorageAccount(SA_NAME)
                .createAsync();

        BatchAccount batchAccount = Utils.<BatchAccount>rootResource(resourceStream)
                .toBlocking().last();
        Assert.assertEquals(RG_NAME, batchAccount.resourceGroupName());
        Assert.assertNotNull(batchAccount.autoStorage());
        Assert.assertEquals(ResourceUtils.nameFromResourceId(batchAccount.autoStorage().storageAccountId()), SA_NAME);

        // List
        List<BatchAccount> accounts = batchManager.batchAccounts().listByResourceGroup(RG_NAME);
        boolean found = false;
        for (BatchAccount account : accounts) {
            if (account.name().equals(BATCH_NAME)) {
                found = true;
            }
        }
        Assert.assertTrue(found);
        // Get
        batchAccount = batchManager.batchAccounts().getByResourceGroup(RG_NAME, BATCH_NAME);
        Assert.assertNotNull(batchAccount);

        Assert.assertTrue(batchAccount.applications().containsKey(applicationId));
        Application application = batchAccount.applications().get(applicationId);

        Assert.assertNotNull(application);
        Assert.assertEquals(application.displayName(), applicationDisplayName);
        Assert.assertEquals(application.updatesAllowed(), allowUpdates);

        batchManager.batchAccounts().deleteByResourceGroup(batchAccount.resourceGroupName(), batchAccount.name());
        batchAccount = batchManager.batchAccounts().getById(batchAccount.id());
        Assert.assertNull(batchAccount);
    }

    @Test
    public void batchAccountListAsyncTest() throws Exception {
        // Create
        Observable<Indexable> resourceStream = batchManager.batchAccounts()
                .define(BATCH_NAME)
                .withRegion(Region.US_CENTRAL)
                .withNewResourceGroup(RG_NAME)
                .createAsync();

        final List<BatchAccount> batchAccounts = new ArrayList<>();
        final List<BatchAccount> createdBatchAccounts  = new ArrayList<>();
        final Action1<BatchAccount> onListBatchAccount = new Action1<BatchAccount>() {
            @Override
            public void call(BatchAccount batchAccountInList) {
                batchAccounts.add(batchAccountInList);
            }
        };

        final Func1<BatchAccount, Observable<BatchAccount>> onCreateBatchAccount = new Func1<BatchAccount, Observable<BatchAccount>>() {
            @Override
            public Observable<BatchAccount> call(final BatchAccount createdBatchAccount) {
                createdBatchAccounts.add(createdBatchAccount);
                return batchManager.batchAccounts().listAsync().doOnNext(onListBatchAccount);
            }
        };

        Utils.<BatchAccount>rootResource(resourceStream).flatMap(onCreateBatchAccount).toBlocking().last();
        Assert.assertEquals(1, createdBatchAccounts.size());
        boolean accountExists = false;
        for (BatchAccount batchAccountInList: batchAccounts) {
            if (createdBatchAccounts.get(0).id().equalsIgnoreCase(batchAccountInList.id())) {
                accountExists = true;
            }
            Assert.assertNotNull(batchAccountInList.id());
        }
        Assert.assertTrue(accountExists);
    }
}
