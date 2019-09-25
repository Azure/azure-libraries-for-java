/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.cosmosdb;

import com.microsoft.azure.management.cosmosdb.implementation.CosmosDBManager;
import com.microsoft.azure.management.resources.core.TestBase;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.resources.implementation.ResourceManager;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CosmosDBTests extends TestBase {

    private static String RG_NAME = "";
    protected ResourceManager resourceManager;
    protected CosmosDBManager cosmosDBManager;
//    final String sqlPrimaryServerName = SdkContext.randomResourceName("sqlpri", 22);

    public CosmosDBTests() {
        super(TestBase.RunCondition.BOTH);
    }

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) throws IOException {
        RG_NAME = generateRandomResourceName("rgcosmosdb", 20);
        resourceManager = ResourceManager
            .authenticate(restClient)
            .withSubscription(defaultSubscription);

        cosmosDBManager = CosmosDBManager.authenticate(restClient, defaultSubscription);
    }

    @Override
    protected void cleanUpResources() {
        resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
    }

    @Test
    public void CanCreateCosmosDbSqlAccount() {
        final String cosmosDbAccountName = SdkContext.randomResourceName("cosmosdb", 22);

        CosmosDBAccount cosmosDBAccount = cosmosDBManager.databaseAccounts()
            .define(cosmosDbAccountName)
            .withRegion(Region.US_WEST_CENTRAL)
            .withNewResourceGroup(RG_NAME)
            .withDataModelSql()
            .withEventualConsistency()
            .withWriteReplication(Region.US_EAST)
            .withReadReplication(Region.US_CENTRAL)
            .withIpRangeFilter("")
            .withMultipleWriteLocationsEnabled(true)
            .withTag("tag1", "value1")
            .create();

        Assert.assertEquals(cosmosDBAccount.name(), cosmosDbAccountName.toLowerCase());
        Assert.assertEquals(cosmosDBAccount.kind(), DatabaseAccountKind.GLOBAL_DOCUMENT_DB);
        Assert.assertEquals(cosmosDBAccount.writableReplications().size(), 2);
        Assert.assertEquals(cosmosDBAccount.readableReplications().size(), 2);
        Assert.assertEquals(cosmosDBAccount.defaultConsistencyLevel(), DefaultConsistencyLevel.EVENTUAL);
        Assert.assertTrue(cosmosDBAccount.multipleWriteLocationsEnabled());
    }

    @Test
    public void CanCreateCosmosDbMongoDBAccount() {
        final String cosmosDbAccountName = SdkContext.randomResourceName("cosmosdb", 22);

        CosmosDBAccount cosmosDBAccount = cosmosDBManager.databaseAccounts()
            .define(cosmosDbAccountName)
            .withRegion(Region.US_WEST_CENTRAL)
            .withNewResourceGroup(RG_NAME)
            .withDataModelMongoDB()
            .withEventualConsistency()
            .withWriteReplication(Region.US_EAST)
            .withReadReplication(Region.US_CENTRAL)
            .withIpRangeFilter("")
            .withTag("tag1", "value1")
            .create();

        Assert.assertEquals(cosmosDBAccount.name(), cosmosDbAccountName.toLowerCase());
        Assert.assertEquals(cosmosDBAccount.kind(), DatabaseAccountKind.MONGO_DB);
        Assert.assertEquals(cosmosDBAccount.writableReplications().size(), 1);
        Assert.assertEquals(cosmosDBAccount.readableReplications().size(), 2);
        Assert.assertEquals(cosmosDBAccount.defaultConsistencyLevel(), DefaultConsistencyLevel.EVENTUAL);
    }

    @Test
    public void CanCreateCosmosDbCassandraAccount() {
        final String cosmosDbAccountName = SdkContext.randomResourceName("cosmosdb", 22);

        CosmosDBAccount cosmosDBAccount = cosmosDBManager.databaseAccounts()
            .define(cosmosDbAccountName)
            .withRegion(Region.US_WEST_CENTRAL)
            .withNewResourceGroup(RG_NAME)
            .withDataModelCassandra()
            .withEventualConsistency()
            .withWriteReplication(Region.US_EAST)
            .withReadReplication(Region.US_CENTRAL)
            .withIpRangeFilter("")
            .withCassandraConnector(ConnectorOffer.SMALL)
            .withTag("tag1", "value1")
            .create();

        Assert.assertEquals(cosmosDBAccount.name(), cosmosDbAccountName.toLowerCase());
        Assert.assertEquals(cosmosDBAccount.kind(), DatabaseAccountKind.GLOBAL_DOCUMENT_DB);
        Assert.assertEquals(cosmosDBAccount.capabilities().get(0).name(), "EnableCassandra");
        Assert.assertEquals(cosmosDBAccount.writableReplications().size(), 1);
        Assert.assertEquals(cosmosDBAccount.readableReplications().size(), 2);
        Assert.assertEquals(cosmosDBAccount.defaultConsistencyLevel(), DefaultConsistencyLevel.EVENTUAL);
        Assert.assertTrue(cosmosDBAccount.cassandraConnectorEnabled());
        Assert.assertEquals(ConnectorOffer.SMALL, cosmosDBAccount.cassandraConnectorOffer());

        cosmosDBAccount = cosmosDBAccount.update()
            .withoutCassandraConnector()
            .apply();

        Assert.assertFalse(cosmosDBAccount.cassandraConnectorEnabled());
        Assert.assertNull(cosmosDBAccount.cassandraConnectorOffer());
    }

    @Test
    public void CanCreateCosmosDbAzureTableAccount() {
        final String cosmosDbAccountName = SdkContext.randomResourceName("cosmosdb", 22);

        CosmosDBAccount cosmosDBAccount = cosmosDBManager.databaseAccounts()
            .define(cosmosDbAccountName)
            .withRegion(Region.US_WEST_CENTRAL)
            .withNewResourceGroup(RG_NAME)
            .withDataModelAzureTable()
            .withEventualConsistency()
            .withWriteReplication(Region.US_EAST)
            .withReadReplication(Region.US_CENTRAL)
            .withIpRangeFilter("")
            .withTag("tag1", "value1")
            .create();

        Assert.assertEquals(cosmosDBAccount.name(), cosmosDbAccountName.toLowerCase());
        Assert.assertEquals(cosmosDBAccount.kind(), DatabaseAccountKind.GLOBAL_DOCUMENT_DB);
        Assert.assertEquals(cosmosDBAccount.capabilities().get(0).name(), "EnableTable");
        Assert.assertEquals(cosmosDBAccount.writableReplications().size(), 1);
        Assert.assertEquals(cosmosDBAccount.readableReplications().size(), 2);
        Assert.assertEquals(cosmosDBAccount.defaultConsistencyLevel(), DefaultConsistencyLevel.EVENTUAL);
    }

}
