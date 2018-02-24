/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.datalake.store;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.datalake.store.models.CapabilityInformation;
import com.microsoft.azure.management.datalake.store.models.CreateDataLakeStoreAccountParameters;
import com.microsoft.azure.management.datalake.store.models.DataLakeStoreAccount;
import com.microsoft.azure.management.datalake.store.models.DataLakeStoreAccountBasic;
import com.microsoft.azure.management.datalake.store.models.UpdateDataLakeStoreAccountParameters;
import com.microsoft.azure.management.datalake.store.models.NameAvailabilityInformation;
import com.microsoft.azure.management.datalake.store.models.OperationListResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class DataLakeStoreAccountOperationsTests extends DataLakeStoreManagementTest
{
    @Test
    public void canCreateGetUpdateDeleteAdlsAccount() throws Exception
    {
        String adlsAcct = generateRandomResourceName("adlsacct", 15);

        // Ensure that the account name is available
        NameAvailabilityInformation checkNameGetResponse =
                dataLakeStoreAccountManagementClient.accounts().checkNameAvailability(
                        environmentLocation.name(),
                        adlsAcct
                );

        Assert.assertTrue(checkNameGetResponse.nameAvailable());

        // Create
        HashMap<String, String> tags = new HashMap<String, String>();
        tags.put("testley", "testvalue");

        CreateDataLakeStoreAccountParameters createParams = new CreateDataLakeStoreAccountParameters()
                .withLocation(environmentLocation.name())
                .withTags(tags);

        DataLakeStoreAccount createResponse =
                dataLakeStoreAccountManagementClient.accounts().create(
                        resourceGroupName,
                        adlsAcct,
                        createParams
                );

        Assert.assertEquals(environmentLocation.name(), createResponse.location());
        Assert.assertEquals("Microsoft.DataLakeStore/accounts", createResponse.type());
        Assert.assertNotNull(createResponse.id());
        Assert.assertTrue(createResponse.id().contains(adlsAcct));
        Assert.assertEquals(1, createResponse.getTags().size());

        // Update the tags
        tags.put("testkey2", "testvalue2");

        UpdateDataLakeStoreAccountParameters updateParams = new UpdateDataLakeStoreAccountParameters()
                .withTags(tags);

        DataLakeStoreAccount updateResponse =
                dataLakeStoreAccountManagementClient.accounts().update(
                        resourceGroupName,
                        adlsAcct,
                        updateParams
                );

        Assert.assertEquals(environmentLocation.name(), updateResponse.location());
        Assert.assertEquals("Microsoft.DataLakeStore/accounts", updateResponse.type());
        Assert.assertNotNull(updateResponse.id());
        Assert.assertTrue(updateResponse.id().contains(adlsAcct));
        Assert.assertEquals(2, updateResponse.getTags().size());

        // Get the account
        DataLakeStoreAccount getResponse =
                dataLakeStoreAccountManagementClient.accounts().get(
                        resourceGroupName,
                        adlsAcct
                );

        Assert.assertEquals(environmentLocation.name(), getResponse.location());
        Assert.assertEquals("Microsoft.DataLakeStore/accounts", getResponse.type());
        Assert.assertNotNull(getResponse.id());
        Assert.assertTrue(getResponse.id().contains(adlsAcct));
        Assert.assertEquals(2, getResponse.getTags().size());

        // List all accounts and make sure there is one.
        PagedList<DataLakeStoreAccountBasic> listResult = dataLakeStoreAccountManagementClient.accounts().list();

        DataLakeStoreAccountBasic discoveredAcct = null;
        for (DataLakeStoreAccountBasic acct : listResult)
        {
            if (acct.name().equals(adlsAcct))
            {
                discoveredAcct = acct;
                break;
            }
        }

        Assert.assertNotNull(discoveredAcct);
        Assert.assertEquals(environmentLocation.name(), discoveredAcct.location());
        Assert.assertEquals("Microsoft.DataLakeStore/accounts", discoveredAcct.type());
        Assert.assertNotNull(discoveredAcct.id());
        Assert.assertTrue(discoveredAcct.id().contains(adlsAcct));
        Assert.assertEquals(2, discoveredAcct.getTags().size());

        // List within a resource group
        listResult =
                dataLakeStoreAccountManagementClient.accounts().listByResourceGroup(
                        resourceGroupName
                );

        discoveredAcct = null;
        for (DataLakeStoreAccountBasic acct : listResult)
        {
            if (acct.name().equals(adlsAcct))
            {
                discoveredAcct = acct;
                break;
            }
        }

        Assert.assertNotNull(discoveredAcct);
        Assert.assertEquals(environmentLocation.name(), discoveredAcct.location());
        Assert.assertEquals("Microsoft.DataLakeStore/accounts", discoveredAcct.type());
        Assert.assertNotNull(discoveredAcct.id());
        Assert.assertTrue(discoveredAcct.id().contains(adlsAcct));
        Assert.assertEquals(2, discoveredAcct.getTags().size());

        // Check that Locations_GetCapability and Operations_List are functional
        CapabilityInformation capabilityGetResponse =
                dataLakeStoreAccountManagementClient.locations().getCapability(
                        environmentLocation.name()
                );

        Assert.assertNotNull(capabilityGetResponse);

        OperationListResult operationsListResponse = dataLakeStoreAccountManagementClient.operations().list();

        Assert.assertNotNull(operationsListResponse);

        // Delete the ADLS account
        dataLakeStoreAccountManagementClient.accounts().delete(
                resourceGroupName,
                adlsAcct
        );

        // Do it again, it should not throw
        dataLakeStoreAccountManagementClient.accounts().delete(
                resourceGroupName,
                adlsAcct
        );
    }
}
