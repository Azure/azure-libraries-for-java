/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.datalake.analytics;

import com.microsoft.azure.management.datalake.analytics.models.AddDataLakeStoreWithAccountParameters;
import com.microsoft.azure.management.datalake.analytics.models.CapabilityInformation;
import com.microsoft.azure.management.datalake.analytics.models.CreateDataLakeAnalyticsAccountParameters;
import com.microsoft.azure.management.datalake.analytics.models.DataLakeAnalyticsAccount;
import com.microsoft.azure.management.datalake.analytics.models.DataLakeAnalyticsAccountBasic;
import com.microsoft.azure.management.datalake.analytics.models.DataLakeStoreAccountInformation;
import com.microsoft.azure.management.datalake.analytics.models.NameAvailabilityInformation;
import com.microsoft.azure.management.datalake.analytics.models.OperationListResult;
import com.microsoft.azure.management.datalake.analytics.models.StorageAccountInformation;
import com.microsoft.azure.management.datalake.analytics.models.UpdateDataLakeAnalyticsAccountParameters;
import com.microsoft.azure.management.datalake.store.models.CreateDataLakeStoreAccountParameters;
import com.microsoft.azure.management.storage.SkuName;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataLakeAnalyticsAccountOperationsTests extends DataLakeAnalyticsManagementTestBase
{
    @Test
    public void canCreateGetUpdateDeleteAdlaAccount() throws Exception
    {
        String adlaAcct = generateRandomResourceName("adla",15);
        String storageAcct = generateRandomResourceName("wasb",15);
        String adlsName2 = generateRandomResourceName("adls2",15);

        // Ensure that the account name is available
        NameAvailabilityInformation checkNameGetResponse =
                dataLakeAnalyticsAccountManagementClient.accounts().checkNameAvailability(
                        environmentLocation.name(),
                        adlaAcct
                );

        Assert.assertTrue(checkNameGetResponse.nameAvailable());

        // Create
        storageManagementClient.storageAccounts()
                .define(storageAcct)
                .withRegion(environmentLocation)
                .withExistingResourceGroup(rgName)
                .withSku(SkuName.STANDARD_LRS)
                .withGeneralPurposeAccountKind()
                .create();

        String storageAccessKey = storageManagementClient.storageAccounts()
                .getByResourceGroup(rgName, storageAcct)
                .getKeys().get(0).value();

        // Create second ADLS account
        CreateDataLakeStoreAccountParameters adlsCreateParams = new CreateDataLakeStoreAccountParameters()
                .withLocation(environmentLocation.name());

        dataLakeStoreAccountManagementClient.accounts().create(
                rgName,
                adlsName2,
                adlsCreateParams
        );

        AddDataLakeStoreWithAccountParameters adlsInfo = new AddDataLakeStoreWithAccountParameters()
                .withName(adlsName);

        List<AddDataLakeStoreWithAccountParameters> adlsAccts = new ArrayList<AddDataLakeStoreWithAccountParameters>();
        adlsAccts.add(adlsInfo);

        HashMap<String, String> tags = new HashMap<String, String>();
        tags.put("testkey", "testvalue");

        CreateDataLakeAnalyticsAccountParameters createParams = new CreateDataLakeAnalyticsAccountParameters()
                .withLocation(environmentLocation.name())
                .withDefaultDataLakeStoreAccount(adlsName)
                .withDataLakeStoreAccounts(adlsAccts)
                .withTags(tags);

        // Ensure that the account name is no longer available
        DataLakeAnalyticsAccount createResponse =
                dataLakeAnalyticsAccountManagementClient.accounts().create(
                        rgName,
                        adlaAcct,
                        createParams
                );

        checkNameGetResponse =
                dataLakeAnalyticsAccountManagementClient.accounts().checkNameAvailability(
                        environmentLocation.name(),
                        adlaAcct
                );

        Assert.assertFalse(checkNameGetResponse.nameAvailable());
        Assert.assertEquals(environmentLocation.name(), createResponse.location());
        Assert.assertEquals("Microsoft.DataLakeAnalytics/accounts", createResponse.type());
        Assert.assertNotNull(createResponse.id());
        Assert.assertTrue(createResponse.id().contains(adlaAcct));
        Assert.assertEquals(1, createResponse.getTags().size());
        Assert.assertEquals(1, createResponse.dataLakeStoreAccounts().size());
        Assert.assertEquals(adlsName, createResponse.dataLakeStoreAccounts().get(0).name());

        // Update the tags
        tags.put("testkey2", "testvalue2");

        UpdateDataLakeAnalyticsAccountParameters updateParams = new UpdateDataLakeAnalyticsAccountParameters()
                .withTags(tags);

        DataLakeAnalyticsAccount updateResponse =
                dataLakeAnalyticsAccountManagementClient.accounts().update(
                        rgName,
                        adlaAcct,
                        updateParams
                );

        Assert.assertEquals(environmentLocation.name(), updateResponse.location());
        Assert.assertEquals("Microsoft.DataLakeAnalytics/accounts", updateResponse.type());
        Assert.assertNotNull(updateResponse.id());
        Assert.assertTrue(updateResponse.id().contains(adlaAcct));
        Assert.assertEquals(2, updateResponse.getTags().size());
        Assert.assertEquals(1, updateResponse.dataLakeStoreAccounts().size());
        Assert.assertEquals(adlsName, updateResponse.dataLakeStoreAccounts().get(0).name());

        // Get the account
        DataLakeAnalyticsAccount getResponse =
                dataLakeAnalyticsAccountManagementClient.accounts().get(
                        rgName,
                        adlaAcct
                );

        Assert.assertEquals(environmentLocation.name(), getResponse.location());
        Assert.assertEquals("Microsoft.DataLakeAnalytics/accounts", getResponse.type());
        Assert.assertNotNull(getResponse.id());
        Assert.assertTrue(getResponse.id().contains(adlaAcct));
        Assert.assertEquals(2, getResponse.getTags().size());
        Assert.assertEquals(1, getResponse.dataLakeStoreAccounts().size());
        Assert.assertEquals(adlsName, getResponse.dataLakeStoreAccounts().get(0).name());

        // List all accounts and make sure there is one.
        List<DataLakeAnalyticsAccountBasic> listResult = dataLakeAnalyticsAccountManagementClient.accounts().list();
        DataLakeAnalyticsAccountBasic discoveredAcct = null;
        for (DataLakeAnalyticsAccountBasic acct : listResult)
        {
            if (acct.name().equals(adlaAcct))
            {
                discoveredAcct = acct;
                break;
            }
        }

        Assert.assertNotNull(discoveredAcct);
        Assert.assertEquals(environmentLocation.name(), discoveredAcct.location());
        Assert.assertEquals("Microsoft.DataLakeAnalytics/accounts", discoveredAcct.type());
        Assert.assertNotNull(discoveredAcct.id());
        Assert.assertTrue(discoveredAcct.id().contains(adlaAcct));
        Assert.assertEquals(2, discoveredAcct.getTags().size());

        // List within a resource group
        listResult = dataLakeAnalyticsAccountManagementClient.accounts().listByResourceGroup(rgName);
        discoveredAcct = null;
        for (DataLakeAnalyticsAccountBasic acct : listResult)
        {
            if (acct.name().equals(adlaAcct))
            {
                discoveredAcct = acct;
                break;
            }
        }

        Assert.assertNotNull(discoveredAcct);
        Assert.assertEquals(environmentLocation.name(), discoveredAcct.location());
        Assert.assertEquals("Microsoft.DataLakeAnalytics/accounts", discoveredAcct.type());
        Assert.assertNotNull(discoveredAcct.id());
        Assert.assertTrue(discoveredAcct.id().contains(adlaAcct));
        Assert.assertEquals(2, discoveredAcct.getTags().size());

        // Add, list, get and remove a data lake store account
        dataLakeAnalyticsAccountManagementClient.dataLakeStoreAccounts().add(
                rgName,
                adlaAcct,
                adlsName2
        );

        // List ADLS accounts
        List<DataLakeStoreAccountInformation> adlsListResult =
                dataLakeAnalyticsAccountManagementClient.dataLakeStoreAccounts().listByAccount(
                        rgName,
                        adlaAcct
                );

        Assert.assertEquals(2, adlsListResult.size());

        // Get the one we just added
        DataLakeStoreAccountInformation adlsGetResult =
                dataLakeAnalyticsAccountManagementClient.dataLakeStoreAccounts().get(
                        rgName,
                        adlaAcct,
                        adlsName2
                );

        Assert.assertEquals(adlsName2, adlsGetResult.name());

        // Remove the data source
        dataLakeAnalyticsAccountManagementClient.dataLakeStoreAccounts().delete(
                rgName,
                adlaAcct,
                adlsName2
        );

        // List again, confirming there is only one ADLS account
        adlsListResult =
                dataLakeAnalyticsAccountManagementClient.dataLakeStoreAccounts().listByAccount(
                        rgName,
                        adlaAcct
                );

        Assert.assertEquals(1, adlsListResult.size());

        // Add, list get and remove an azure blob account
        dataLakeAnalyticsAccountManagementClient.storageAccounts().add(
                rgName,
                adlaAcct,
                storageAcct,
                storageAccessKey
        );

        // List ADLS accounts
        List<StorageAccountInformation> storeListResult =
                dataLakeAnalyticsAccountManagementClient.storageAccounts().listByAccount(
                        rgName,
                        adlaAcct
                );

        Assert.assertEquals(1, storeListResult.size());

        // Get the one we just added
        StorageAccountInformation storageGetResult =
                dataLakeAnalyticsAccountManagementClient.storageAccounts().get(
                        rgName,
                        adlaAcct,
                        storageAcct
                );

        Assert.assertEquals(storageAcct, storageGetResult.name());

        // Remove the data source
        dataLakeAnalyticsAccountManagementClient.storageAccounts().delete(
                rgName,
                adlaAcct,
                storageAcct
        );

        // List again, confirming there is only one ADLS account
        storeListResult =
                dataLakeAnalyticsAccountManagementClient.storageAccounts().listByAccount(
                        rgName,
                        adlaAcct
                );

        Assert.assertEquals(0, storeListResult.size());

        // Check that Locations_GetCapability and Operations_List are functional
        CapabilityInformation capabilityGetResponse =
                dataLakeAnalyticsAccountManagementClient.locations().getCapability(
                        environmentLocation.name()
                );

        Assert.assertNotNull(capabilityGetResponse);

        OperationListResult operationsListResponse = dataLakeAnalyticsAccountManagementClient.operations().list();

        Assert.assertNotNull(operationsListResponse);

        // Delete the ADLA account
        dataLakeAnalyticsAccountManagementClient.accounts().delete(
                rgName,
                adlaAcct
        );

        // Do it again, it should not throw
        dataLakeAnalyticsAccountManagementClient.accounts().delete(
                rgName,
                adlaAcct
        );
    }
}
