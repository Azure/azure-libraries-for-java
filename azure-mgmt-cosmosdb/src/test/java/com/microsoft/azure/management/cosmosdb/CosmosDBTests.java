/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.cosmosdb;

import com.microsoft.azure.management.cosmosdb.implementation.CosmosDBManager;
import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.network.PrivateLinkServiceConnection;
import com.microsoft.azure.management.network.PrivateLinkServiceConnectionState;
import com.microsoft.azure.management.network.ServiceEndpointType;
import com.microsoft.azure.management.network.implementation.PrivateEndpointInner;
import com.microsoft.azure.management.resources.core.TestBase;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.resources.implementation.ResourceManager;
import com.microsoft.azure.management.network.implementation.NetworkManager;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class CosmosDBTests extends TestBase {

    private static String RG_NAME = "";
    protected ResourceManager resourceManager;
    protected CosmosDBManager cosmosDBManager;
    protected NetworkManager networkManager;
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

        networkManager = NetworkManager.authenticate(restClient, defaultSubscription);
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
    public void CanCreateSqlPrivateEndpoint() {
        final String cosmosDbAccountName = SdkContext.randomResourceName("cosmosdb", 22);
        final String networkName = SdkContext.randomResourceName("network", 22);
        final String subnetName = SdkContext.randomResourceName("subnet", 22);
        final String plsConnectionName = SdkContext.randomResourceName("plsconnect", 22);
        final String pedName = SdkContext.randomResourceName("ped", 22);
        final Region region = Region.US_WEST;

        cosmosDBManager.resourceManager().resourceGroups()
                .define(RG_NAME)
                .withRegion(region)
                .create();

        Network network = networkManager.networks()
                .define(networkName)
                .withRegion(region)
                .withExistingResourceGroup(RG_NAME)
                .withAddressSpace("10.0.0.0/16")
                .defineSubnet(subnetName)
                    .withAddressPrefix("10.0.0.0/24")
                    .withAccessFromService(ServiceEndpointType.MICROSOFT_AZURECOSMOSDB)
                    .attach()
                .create();

        network.subnets().get(subnetName).inner().withPrivateEndpointNetworkPolicies("Disabled");
        network.subnets().get(subnetName).inner().withPrivateLinkServiceNetworkPolicies("Disabled");

        network.update()
                .updateSubnet(subnetName)
                .parent()
                .apply();

        CosmosDBAccount cosmosDBAccount = cosmosDBManager.databaseAccounts()
                .define(cosmosDbAccountName)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(RG_NAME)
                .withDataModelSql()
                .withStrongConsistency()
                .withDisableKeyBaseMetadataWriteAccess(true)
                .create();

        Assert.assertTrue(cosmosDBAccount.keyBasedMetadataWriteAccessDisabled());

        // create network private endpoint.
        PrivateLinkServiceConnection privateLinkServiceConnection = new PrivateLinkServiceConnection()
                .withName(plsConnectionName)
                .withPrivateLinkServiceId(cosmosDBAccount.id())
                .withPrivateLinkServiceConnectionState(new PrivateLinkServiceConnectionState()
                    .withStatus("Approved"))
                .withGroupIds(Arrays.asList("Sql"));

        PrivateEndpointInner privateEndpoint = new PrivateEndpointInner()
                .withPrivateLinkServiceConnections(Arrays.asList(privateLinkServiceConnection))
                .withSubnet(network.subnets().get(subnetName).inner());

        privateEndpoint.withLocation(region.toString());
        privateEndpoint = networkManager.inner().privateEndpoints()
                .createOrUpdate(RG_NAME, pedName, privateEndpoint);

        cosmosDBAccount.update()
                .defineNewPrivateEndpointConnection(pedName)
                    .withStatus("Rejected")
                    .withDescription("Rej")
                    .attach()
                .apply();

        Map<String, PrivateEndpointConnection> connections = cosmosDBAccount.listPrivateEndpointConnection();
        Assert.assertTrue(connections.containsKey(pedName));
        Assert.assertEquals("Rejected", connections.get(pedName).privateLinkServiceConnectionState().status());

        Assert.assertEquals(1, cosmosDBAccount.listPrivateLinkResources().size());

        cosmosDBAccount.update()
                .updatePrivateEndpointConnection(pedName)
                    .withDescription("Test Update")
                    .parent()
                .apply();
        Assert.assertEquals("Test Update", cosmosDBAccount.getPrivateEndpointConnection(pedName).privateLinkServiceConnectionState().description());
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
            .withTag("tag1", "value1")
            .create();

        Assert.assertEquals(cosmosDBAccount.name(), cosmosDbAccountName.toLowerCase());
        Assert.assertEquals(cosmosDBAccount.kind(), DatabaseAccountKind.GLOBAL_DOCUMENT_DB);
        Assert.assertEquals(cosmosDBAccount.capabilities().get(0).name(), "EnableCassandra");
        Assert.assertEquals(cosmosDBAccount.writableReplications().size(), 1);
        Assert.assertEquals(cosmosDBAccount.readableReplications().size(), 2);
        Assert.assertEquals(cosmosDBAccount.defaultConsistencyLevel(), DefaultConsistencyLevel.EVENTUAL);
    }

    @Test
    public void CanUpdateCosmosDbCassandraConnector() {
        final String cosmosDbAccountName = SdkContext.randomResourceName("cosmosdb", 22);

        // CassandraConnector could only be used in West US and South Central US.
        CosmosDBAccount cosmosDBAccount = cosmosDBManager.databaseAccounts()
                .define(cosmosDbAccountName)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(RG_NAME)
                .withDataModelCassandra()
                .withStrongConsistency()
                .withCassandraConnector(ConnectorOffer.SMALL)
                .withTag("tag1", "value1")
                .create();

        Assert.assertEquals("value1", cosmosDBAccount.tags().get("tag1"));
        Assert.assertTrue(cosmosDBAccount.cassandraConnectorEnabled());
        Assert.assertEquals(ConnectorOffer.SMALL, cosmosDBAccount.cassandraConnectorOffer());

        cosmosDBAccount = cosmosDBAccount.update()
                .withoutCassandraConnector()
                .apply();

        Assert.assertFalse(cosmosDBAccount.cassandraConnectorEnabled());
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
