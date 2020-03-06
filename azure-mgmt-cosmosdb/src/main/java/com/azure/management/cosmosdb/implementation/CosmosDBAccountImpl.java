/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.cosmosdb.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.azure.management.cosmosdb.Capability;
import com.azure.management.cosmosdb.ConnectorOffer;
import com.azure.management.cosmosdb.ConsistencyPolicy;
import com.azure.management.cosmosdb.CosmosDBAccount;
import com.azure.management.cosmosdb.DatabaseAccountCreateUpdateParameters;
import com.azure.management.cosmosdb.DatabaseAccountKind;
import com.azure.management.cosmosdb.DatabaseAccountListConnectionStringsResult;
import com.azure.management.cosmosdb.DatabaseAccountListKeysResult;
import com.azure.management.cosmosdb.DatabaseAccountListReadOnlyKeysResult;
import com.azure.management.cosmosdb.DatabaseAccountOfferType;
import com.azure.management.cosmosdb.DatabaseAccountUpdateParameters;
import com.azure.management.cosmosdb.DefaultConsistencyLevel;
import com.azure.management.cosmosdb.FailoverPolicy;
import com.azure.management.cosmosdb.KeyKind;
import com.azure.management.cosmosdb.Location;
import com.azure.management.cosmosdb.PrivateEndpointConnection;
import com.azure.management.cosmosdb.PrivateLinkResource;
import com.azure.management.cosmosdb.SqlDatabase;
import com.azure.management.cosmosdb.VirtualNetworkRule;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * The implementation for CosmosDBAccount.
 */
@LangDefinition
class CosmosDBAccountImpl
        extends
        GroupableResourceImpl<
                CosmosDBAccount,
                DatabaseAccountGetResultsInner,
                CosmosDBAccountImpl,
                CosmosDBManager>
        implements CosmosDBAccount,
        CosmosDBAccount.Definition,
        CosmosDBAccount.Update {
    private List<FailoverPolicy> failoverPolicies;
    private boolean hasFailoverPolicyChanges;
    private final int maxDelayDueToMissingFailovers = 60 * 10;
    private Map<String, VirtualNetworkRule> virtualNetworkRulesMap;
    private PrivateEndpointConnectionsImpl privateEndpointConnections;

    CosmosDBAccountImpl(String name, DatabaseAccountGetResultsInner innerObject, CosmosDBManager manager) {
        super(fixDBName(name), innerObject, manager);
        this.failoverPolicies = new ArrayList<FailoverPolicy>();
        this.privateEndpointConnections = new PrivateEndpointConnectionsImpl(this.manager().inner().privateEndpointConnections(), this);
    }

    @Override
    public DatabaseAccountKind kind() {
        return this.inner().kind();
    }

    @Override
    public String documentEndpoint() {
        return this.inner().documentEndpoint();
    }

    @Override
    public DatabaseAccountOfferType databaseAccountOfferType() {
        return this.inner().databaseAccountOfferType();
    }

    @Override
    public String ipRangeFilter() {
        return this.inner().ipRangeFilter();
    }

    @Override
    public ConsistencyPolicy consistencyPolicy() {
        return this.inner().consistencyPolicy();
    }

    @Override
    public DefaultConsistencyLevel defaultConsistencyLevel() {
        if (this.inner().consistencyPolicy() == null) {
            throw new RuntimeException("Consistency policy is missing!");
        }

        return this.inner().consistencyPolicy().defaultConsistencyLevel();
    }

    @Override
    public List<Location> writableReplications() {
        return this.inner().writeLocations();
    }

    @Override
    public List<Location> readableReplications() {
        return this.inner().readLocations();
    }

    @Override
    public DatabaseAccountListKeysResult listKeys() {
        return this.listKeysAsync().toBlocking().last();
    }

    @Override
    public Observable<DatabaseAccountListKeysResult> listKeysAsync() {
        return this.manager().inner().databaseAccounts()
            .listKeysAsync(this.resourceGroupName(), this.name())
            .map(new Func1<DatabaseAccountListKeysResultInner, DatabaseAccountListKeysResult>() {
                @Override
                public DatabaseAccountListKeysResult call(DatabaseAccountListKeysResultInner databaseAccountListKeysResultInner) {
                    return new DatabaseAccountListKeysResultImpl(databaseAccountListKeysResultInner);
                }
            });
    }

    @Override
    public DatabaseAccountListReadOnlyKeysResult listReadOnlyKeys() {
        return this.listReadOnlyKeysAsync().toBlocking().last();
    }

    @Override
    public Observable<DatabaseAccountListReadOnlyKeysResult> listReadOnlyKeysAsync() {
        return this.manager().inner().databaseAccounts()
            .listReadOnlyKeysAsync(this.resourceGroupName(), this.name())
            .map(new Func1<DatabaseAccountListReadOnlyKeysResultInner, DatabaseAccountListReadOnlyKeysResult>() {
                @Override
                public DatabaseAccountListReadOnlyKeysResult call(DatabaseAccountListReadOnlyKeysResultInner databaseAccountListReadOnlyKeysResultInner) {
                    return new DatabaseAccountListReadOnlyKeysResultImpl(databaseAccountListReadOnlyKeysResultInner);
                }
            });
    }

    @Override
    public DatabaseAccountListConnectionStringsResult listConnectionStrings() {
        return this.listConnectionStringsAsync().toBlocking().last();
    }

    @Override
    public Observable<DatabaseAccountListConnectionStringsResult> listConnectionStringsAsync() {
        return this.manager().inner().databaseAccounts()
            .listConnectionStringsAsync(this.resourceGroupName(), this.name())
            .map(new Func1<DatabaseAccountListConnectionStringsResultInner, DatabaseAccountListConnectionStringsResult>() {
                @Override
                public DatabaseAccountListConnectionStringsResult call(DatabaseAccountListConnectionStringsResultInner databaseAccountListConnectionStringsResultInner) {
                    return new DatabaseAccountListConnectionStringsResultImpl(databaseAccountListConnectionStringsResultInner);
                }
            });
    }

    @Override
    public List<SqlDatabase> listSqlDatabases() {
        return this.listSqlDatabasesAsync().toBlocking().last();
    }

    @Override
    public Observable<List<SqlDatabase>> listSqlDatabasesAsync() {
        return this.manager().inner().sqlResources()
                .listSqlDatabasesAsync(this.resourceGroupName(), this.name())
                .map(new Func1<List<SqlDatabaseGetResultsInner>, List<SqlDatabase>>() {
                    @Override
                    public List<SqlDatabase> call(List<SqlDatabaseGetResultsInner> sqlDatabaseInners) {
                        List<SqlDatabase> sqlDatabases = new ArrayList<>();
                        for (SqlDatabaseGetResultsInner inner : sqlDatabaseInners) {
                            sqlDatabases.add(new SqlDatabaseImpl(inner));
                        }
                        return Collections.unmodifiableList(sqlDatabases);
                    }
                });
    }

    @Override
    public List<PrivateLinkResource> listPrivateLinkResources() {
        return this.listPrivateLinkResourcesAsync().toBlocking().last();
    }

    @Override
    public Observable<List<PrivateLinkResource>> listPrivateLinkResourcesAsync() {
        return this.manager().inner().privateLinkResources()
                .listByDatabaseAccountAsync(this.resourceGroupName(), this.name())
                .map(new Func1<List<PrivateLinkResourceInner>, List<PrivateLinkResource>>() {
                    @Override
                    public List<PrivateLinkResource> call(List<PrivateLinkResourceInner> privateLinkResourceInners) {
                        List<PrivateLinkResource> privateLinkResources = new ArrayList<>();
                        for (PrivateLinkResourceInner inner : privateLinkResourceInners) {
                            privateLinkResources.add(new PrivateLinkResourceImpl(inner));
                        }
                        return Collections.unmodifiableList(privateLinkResources);
                    }
                });
    }

    @Override
    public PrivateLinkResource getPrivateLinkResource(String groupName) {
        return this.getPrivateLinkResourceAsync(groupName).toBlocking().last();
    }

    @Override
    public Observable<PrivateLinkResource> getPrivateLinkResourceAsync(String groupName) {
        return this.manager().inner().privateLinkResources()
                .getAsync(this.resourceGroupName(), this.name(), groupName)
                .map(new Func1<PrivateLinkResourceInner, PrivateLinkResource>() {
                    @Override
                    public PrivateLinkResource call(PrivateLinkResourceInner privateLinkResourceInner) {
                        if (privateLinkResourceInner == null) {
                            return null;
                        }
                        return new PrivateLinkResourceImpl(privateLinkResourceInner);
                    }
                });
    }

    @Override
    public Map<String, PrivateEndpointConnection> listPrivateEndpointConnection() {
        return this.listPrivateEndpointConnectionAsync().toBlocking().last();
    }

    @Override
    public Observable<Map<String, PrivateEndpointConnection>> listPrivateEndpointConnectionAsync() {
        return this.privateEndpointConnections.asMapAsync();
    }

    @Override
    public PrivateEndpointConnection getPrivateEndpointConnection(String name) {
        return this.getPrivateEndpointConnectionAsync(name).toBlocking().last();
    }

    @Override
    public Observable<PrivateEndpointConnection> getPrivateEndpointConnectionAsync(String name) {
        return this.privateEndpointConnections.getImplAsync(name)
                .map(new Func1<PrivateEndpointConnectionImpl, PrivateEndpointConnection>() {
                    @Override
                    public PrivateEndpointConnection call(PrivateEndpointConnectionImpl privateEndpointConnection) {
                        return privateEndpointConnection;
                    }
                });
    }

    @Override
    public boolean multipleWriteLocationsEnabled() {
        return this.inner().enableMultipleWriteLocations();
    }

    @Override
    public boolean cassandraConnectorEnabled() {
        return this.inner().enableCassandraConnector();
    }

    @Override
    public ConnectorOffer cassandraConnectorOffer() {
        return this.inner().connectorOffer();
    }

    @Override
    public boolean keyBasedMetadataWriteAccessDisabled() {
        return this.inner().disableKeyBasedMetadataWriteAccess();
    }

    @Override
    public List<Capability> capabilities() {
        List<Capability> capabilities = this.inner().capabilities();
        if (capabilities == null) {
            capabilities = new ArrayList<>();
        }
        return Collections.unmodifiableList(capabilities);
    }

    @Override
    public List<VirtualNetworkRule> virtualNetworkRules() {
        List<VirtualNetworkRule> result = (this.inner() != null && this.inner().virtualNetworkRules() != null) ? this.inner().virtualNetworkRules() : new ArrayList<VirtualNetworkRule>();
        return Collections.unmodifiableList(result);
    }

    @Override
    public void offlineRegion(Region region) {
        this.manager().inner().databaseAccounts().offlineRegion(this.resourceGroupName(), this.name(), region.label());
    }

    @Override
    public Completable offlineRegionAsync(Region region) {
        return this.manager().inner().databaseAccounts().offlineRegionAsync(this.resourceGroupName(), this.name(), region.label()).toCompletable();
    }

    @Override
    public void onlineRegion(Region region) {
        this.manager().inner().databaseAccounts().onlineRegion(this.resourceGroupName(), this.name(), region.label());
    }

    @Override
    public Completable onlineRegionAsync(Region region) {
        return this.manager().inner().databaseAccounts().onlineRegionAsync(this.resourceGroupName(), this.name(), region.label()).toCompletable();
    }

    @Override
    public void regenerateKey(KeyKind keyKind) {
        this.manager().inner().databaseAccounts().regenerateKey(this.resourceGroupName(), this.name(), keyKind);
    }

    @Override
    public Completable regenerateKeyAsync(KeyKind keyKind) {
        return this.manager().inner().databaseAccounts().regenerateKeyAsync(this.resourceGroupName(),
                this.name(), keyKind).toCompletable();
    }

    @Override
    public CosmosDBAccountImpl withKind(DatabaseAccountKind kind) {
        this.inner().withKind(kind);
        return this;        
    }

    @Override
    public CosmosDBAccountImpl withKind(DatabaseAccountKind kind, Capability... capabilities) {
        this.inner().withKind(kind);
        this.inner().withCapabilities(Arrays.asList(capabilities));
        return this;
    }

    @Override
    public CosmosDBAccountImpl withDataModelSql() {
        this.inner().withKind(DatabaseAccountKind.GLOBAL_DOCUMENT_DB);
        return this;
    }

    @Override
    public CosmosDBAccountImpl withDataModelMongoDB() {
        this.inner().withKind(DatabaseAccountKind.MONGO_DB);
        return this;
    }

    @Override
    public CosmosDBAccountImpl withDataModelCassandra() {
        this.inner().withKind(DatabaseAccountKind.GLOBAL_DOCUMENT_DB);
        List<Capability> capabilities = new ArrayList<Capability>();
        capabilities.add(new Capability().withName("EnableCassandra"));
        this.inner().withCapabilities(capabilities);
        this.withTag("defaultExperience", "Cassandra");
        return this;
    }

    @Override
    public CosmosDBAccountImpl withDataModelAzureTable() {
        this.inner().withKind(DatabaseAccountKind.GLOBAL_DOCUMENT_DB);
        List<Capability> capabilities = new ArrayList<Capability>();
        capabilities.add(new Capability().withName("EnableTable"));
        this.inner().withCapabilities(capabilities);
        this.withTag("defaultExperience", "Table");
        return this;
    }

    @Override
    public CosmosDBAccountImpl withDataModelGremlin() {
        this.inner().withKind(DatabaseAccountKind.GLOBAL_DOCUMENT_DB);
        List<Capability> capabilities = new ArrayList<Capability>();
        capabilities.add(new Capability().withName("EnableGremlin"));
        this.inner().withCapabilities(capabilities);
        this.withTag("defaultExperience", "Graph");
        return this;
    }


    @Override
    public CosmosDBAccountImpl withIpRangeFilter(String ipRangeFilter) {
        this.inner().withIpRangeFilter(ipRangeFilter);
        return this;        
    }

    @Override
    protected Observable<DatabaseAccountGetResultsInner> getInnerAsync() {
        return this.manager().inner().databaseAccounts().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public CosmosDBAccountImpl withWriteReplication(Region region) {
        FailoverPolicy failoverPolicyInner = new FailoverPolicy();
        failoverPolicyInner.withLocationName(region.name());
        this.hasFailoverPolicyChanges = true;
        this.failoverPolicies.add(failoverPolicyInner);
        return this;
    }

    @Override
    public CosmosDBAccountImpl withReadReplication(Region region) {
        this.ensureFailoverIsInitialized();
        FailoverPolicy failoverPolicyInner = new FailoverPolicy();
        failoverPolicyInner.withLocationName(region.name());
        failoverPolicyInner.withFailoverPriority(this.failoverPolicies.size());
        this.hasFailoverPolicyChanges = true;
        this.failoverPolicies.add(failoverPolicyInner);
        return this;
    }

    @Override
    public CosmosDBAccountImpl withoutReadReplication(Region region) {
        this.ensureFailoverIsInitialized();
        for (int i = 1; i < this.failoverPolicies.size(); i++) {
            if (this.failoverPolicies.get(i).locationName() != null) {
                String locName = this.failoverPolicies.get(i).locationName().replace(" ", "").toLowerCase();
                if (locName.equals(region.name())) {
                    this.failoverPolicies.remove(i);
                }
            }
        }

        return this;
    }

    @Override
    public CosmosDBAccountImpl withEventualConsistency() {
        this.setConsistencyPolicy(DefaultConsistencyLevel.EVENTUAL, 0, 0);
        return this;
    }

    @Override
    public CosmosDBAccountImpl withSessionConsistency() {
        this.setConsistencyPolicy(DefaultConsistencyLevel.SESSION, 0, 0);
        return this;
    }

    @Override
    public CosmosDBAccountImpl withBoundedStalenessConsistency(long maxStalenessPrefix, int maxIntervalInSeconds) {
        this.setConsistencyPolicy(DefaultConsistencyLevel.BOUNDED_STALENESS,
                maxStalenessPrefix,
                maxIntervalInSeconds);
        return this;
    }

    @Override
    public CosmosDBAccountImpl withStrongConsistency() {
        this.setConsistencyPolicy(DefaultConsistencyLevel.STRONG, 0, 0);
        return this;
    }

    @Override
    public PrivateEndpointConnectionImpl defineNewPrivateEndpointConnection(String name) {
        return this.privateEndpointConnections.define(name);
    }

    @Override
    public PrivateEndpointConnectionImpl updatePrivateEndpointConnection(String name) {
        return this.privateEndpointConnections.update(name);
    }

    @Override
    public CosmosDBAccountImpl withoutPrivateEndpointConnection(String name) {
        this.privateEndpointConnections.remove(name);
        return this;
    }

    CosmosDBAccountImpl withPrivateEndpointConnection(PrivateEndpointConnectionImpl privateEndpointConnection) {
        this.privateEndpointConnections.addPrivateEndpointConnection(privateEndpointConnection);
        return this;
    }

    @Override
    public Observable<CosmosDBAccount> createResourceAsync() {
        return this.doDatabaseUpdateCreate();
    }

    private DatabaseAccountCreateUpdateParameters createUpdateParametersInner(DatabaseAccountGetResultsInner inner) {
        this.ensureFailoverIsInitialized();
        DatabaseAccountCreateUpdateParameters createUpdateParametersInner =
                new DatabaseAccountCreateUpdateParameters();
        createUpdateParametersInner.withLocation(this.regionName().toLowerCase());
        createUpdateParametersInner.withConsistencyPolicy(inner.consistencyPolicy());
        createUpdateParametersInner.withDatabaseAccountOfferType(
                DatabaseAccountOfferType.STANDARD.toString());
        createUpdateParametersInner.withIpRangeFilter(inner.ipRangeFilter());
        createUpdateParametersInner.withKind(inner.kind());
        createUpdateParametersInner.withCapabilities(inner.capabilities());
        createUpdateParametersInner.withTags(inner.getTags());
        createUpdateParametersInner.withEnableMultipleWriteLocations(inner.enableMultipleWriteLocations());
        this.addLocationsForParameters(new CreateUpdateLocationParameters(createUpdateParametersInner), this.failoverPolicies);
        createUpdateParametersInner.withIsVirtualNetworkFilterEnabled(inner.isVirtualNetworkFilterEnabled());
        createUpdateParametersInner.withEnableCassandraConnector(inner.enableCassandraConnector());
        createUpdateParametersInner.withConnectorOffer(inner.connectorOffer());
        createUpdateParametersInner.withEnableAutomaticFailover(inner.enableAutomaticFailover());
        createUpdateParametersInner.withDisableKeyBasedMetadataWriteAccess(inner.disableKeyBasedMetadataWriteAccess());
        if (this.virtualNetworkRulesMap != null) {
            createUpdateParametersInner.withVirtualNetworkRules(new ArrayList<VirtualNetworkRule>(this.virtualNetworkRulesMap.values()));
            this.virtualNetworkRulesMap = null;
        }
        return createUpdateParametersInner;
    }

    private DatabaseAccountUpdateParameters updateParametersInner(DatabaseAccountGetResultsInner inner) {
        this.ensureFailoverIsInitialized();
        DatabaseAccountUpdateParameters updateParameters = new DatabaseAccountUpdateParameters();
        updateParameters.withTags(inner.getTags());
        updateParameters.withLocation(this.regionName().toLowerCase());
        updateParameters.withConsistencyPolicy(inner.consistencyPolicy());
        updateParameters.withIpRangeFilter(inner.ipRangeFilter());
        updateParameters.withIsVirtualNetworkFilterEnabled(inner.isVirtualNetworkFilterEnabled());
        updateParameters.withEnableAutomaticFailover(inner.enableAutomaticFailover());
        updateParameters.withCapabilities(inner.capabilities());
        updateParameters.withEnableMultipleWriteLocations(inner.enableMultipleWriteLocations());
        updateParameters.withEnableCassandraConnector(inner.enableCassandraConnector());
        updateParameters.withConnectorOffer(inner.connectorOffer());
        updateParameters.withDisableKeyBasedMetadataWriteAccess(inner.disableKeyBasedMetadataWriteAccess());
        if (virtualNetworkRulesMap != null) {
            updateParameters.withVirtualNetworkRules(new ArrayList<>(this.virtualNetworkRulesMap.values()));
            virtualNetworkRulesMap = null;
        }
        this.addLocationsForParameters(new UpdateLocationParameters(updateParameters), this.failoverPolicies);

        return updateParameters;
    }

    private static String fixDBName(String name) {
        return name.toLowerCase();
    }

    private void setConsistencyPolicy(
            DefaultConsistencyLevel level,
            long maxStalenessPrefix,
            int maxIntervalInSeconds) {
        ConsistencyPolicy policy = new ConsistencyPolicy();
        policy.withDefaultConsistencyLevel(level);
        if (level == DefaultConsistencyLevel.BOUNDED_STALENESS) {
            policy.withMaxStalenessPrefix(maxStalenessPrefix);
            policy.withMaxIntervalInSeconds(maxIntervalInSeconds);
        }

        this.inner().withConsistencyPolicy(policy);
    }

    private void addLocationsForParameters(
            HasLocations locationParameters,
            List<FailoverPolicy> failoverPolicies) {
        List<Location> locations = new ArrayList<Location>();

        if (failoverPolicies.size() > 0) {
            for (int i = 0; i < failoverPolicies.size(); i++) {
                FailoverPolicy policyInner = failoverPolicies.get(i);
                Location location = new Location();
                location.withFailoverPriority(i);
                location.withLocationName(policyInner.locationName());
                locations.add(location);
            }
        } else {
            Location location = new Location();
            location.withFailoverPriority(0);
            location.withLocationName(locationParameters.location());
            locations.add(location);
        }
        locationParameters.withLocations(locations);
    }

    private Observable<CosmosDBAccount> updateFailoverPriorityAsync() {
        final CosmosDBAccountImpl self = this;
        return this.manager().inner().databaseAccounts().failoverPriorityChangeAsync(this.resourceGroupName(),
                this.name(), this.failoverPolicies).map(new Func1<Void, CosmosDBAccount>() {
            @Override
            public CosmosDBAccount call(Void voidInner) {
                if (self.inner().failoverPolicies() != null) {
                    self.inner().failoverPolicies().clear();
                    self.inner().failoverPolicies().addAll(self.failoverPolicies);
                }

                self.failoverPolicies.clear();
                return self;
            }
        });
    }

    private Observable<CosmosDBAccount> doDatabaseUpdateCreate() {
        final CosmosDBAccountImpl self = this;
        final List<Integer> data = new ArrayList<Integer>();
        data.add(0);

        Observable<DatabaseAccountGetResultsInner> request = null;
        HasLocations locationParameters = null;

        if (isInCreateMode()) {
            final DatabaseAccountCreateUpdateParameters createUpdateParametersInner =
                    this.createUpdateParametersInner(this.inner());
            request = this.manager().inner().databaseAccounts().createOrUpdateAsync(
                    resourceGroupName(),
                    name(),
                    createUpdateParametersInner
            );
            locationParameters = new CreateUpdateLocationParameters(createUpdateParametersInner);
        } else {
            final DatabaseAccountUpdateParameters updateParametersInner =
                    this.updateParametersInner(this.inner());
            request = this.manager().inner().databaseAccounts().updateAsync(
                    resourceGroupName(),
                    name(),
                    updateParametersInner
            );
            locationParameters = new UpdateLocationParameters(updateParametersInner);
        }

        final HasLocations finalLocationParameters = locationParameters;
        return request
                .flatMap(new Func1<DatabaseAccountGetResultsInner, Observable<? extends CosmosDBAccount>>() {
                    @Override
                    public Observable<? extends CosmosDBAccount> call(DatabaseAccountGetResultsInner databaseAccountInner) {
                        self.failoverPolicies.clear();
                        self.hasFailoverPolicyChanges = false;
                        return manager().databaseAccounts().getByResourceGroupAsync(
                                resourceGroupName(),
                                name()
                        ).repeatWhen(new Func1<Observable<? extends java.lang.Void>, Observable<?>>() {
                            @Override
                            public Observable<?> call(Observable<? extends Void> observable) {
                                data.set(0, data.get(0) + 5);
                                return observable.delay(5, TimeUnit.SECONDS);
                            }
                        })
                        .filter(new Func1<CosmosDBAccount, Boolean>() {
                            @Override
                            public Boolean call(CosmosDBAccount databaseAccount) {
                                if (maxDelayDueToMissingFailovers > data.get(0)
                                        && (databaseAccount.id() == null
                                        || databaseAccount.id().length() == 0
                                        || finalLocationParameters.locations().size()
                                        > databaseAccount.inner().failoverPolicies().size())) {
                                    data.set(0, data.get(0) + 5);
                                    return false;
                                }

                                if (isAFinalProvisioningState(databaseAccount.inner().provisioningState())) {
                                    for (Location location : databaseAccount.readableReplications()) {
                                        if (!isAFinalProvisioningState(location.provisioningState())) {
                                            return false;
                                        }

                                    }
                                } else {
                                    return false;
                                }

                                self.setInner(databaseAccount.inner());
                                return true;
                            }
                        })
                        .first();

                    }
                });
    }

    private void ensureFailoverIsInitialized() {
        if (this.isInCreateMode()) {
            return;
        }

        if (!this.hasFailoverPolicyChanges) {
            this.failoverPolicies.clear();
            FailoverPolicy[] policyInners = new FailoverPolicy[this.inner().failoverPolicies().size()];
            this.inner().failoverPolicies().toArray(policyInners);
            Arrays.sort(policyInners, new Comparator<FailoverPolicy>() {
                @Override
                public int compare(FailoverPolicy o1, FailoverPolicy o2) {
                    return o1.failoverPriority().compareTo(o2.failoverPriority());
                }
            });

            for (int i = 0; i < policyInners.length; i++) {
                this.failoverPolicies.add(policyInners[i]);
            }

            this.hasFailoverPolicyChanges = true;
        }
    }

    private boolean isAFinalProvisioningState(String state) {
        switch (state.toLowerCase()) {
            case "succeeded":
            case "canceled":
            case "failed":
                return true;
            default:
                return false;
        }
    }

    private Map<String, VirtualNetworkRule> ensureVirtualNetworkRules() {
        if (this.virtualNetworkRulesMap == null) {
            this.virtualNetworkRulesMap = new HashMap<>();
            if (this.inner() != null && this.inner().virtualNetworkRules() != null) {
                for (VirtualNetworkRule virtualNetworkRule : this.inner().virtualNetworkRules()) {
                    this.virtualNetworkRulesMap.put(virtualNetworkRule.id(), virtualNetworkRule);
                }
            }
        }

        return this.virtualNetworkRulesMap;
    }

    @Override
    public CosmosDBAccountImpl withVirtualNetwork(String virtualNetworkId, String subnetName) {
        this.inner().withIsVirtualNetworkFilterEnabled(true);
        String vnetId = virtualNetworkId + "/subnets/" + subnetName;
        ensureVirtualNetworkRules().put(vnetId, new VirtualNetworkRule().withId(vnetId));
        return this;
    }

    @Override
    public CosmosDBAccountImpl withoutVirtualNetwork(String virtualNetworkId, String subnetName) {
        Map<String, VirtualNetworkRule> vnetRules = ensureVirtualNetworkRules();
        vnetRules.remove(virtualNetworkId + "/subnets/" + subnetName);
        if (vnetRules.size() == 0) {
            this.inner().withIsVirtualNetworkFilterEnabled(false);
        }
        return this;
    }

    @Override
    public CosmosDBAccountImpl withVirtualNetworkRules(List<VirtualNetworkRule> virtualNetworkRules) {
        Map<String, VirtualNetworkRule> vnetRules = ensureVirtualNetworkRules();
        if (virtualNetworkRules == null || virtualNetworkRules.isEmpty()) {
            vnetRules.clear();
            this.inner().withIsVirtualNetworkFilterEnabled(false);
            return this;
        }
        this.inner().withIsVirtualNetworkFilterEnabled(true);
        for (VirtualNetworkRule vnetRule : virtualNetworkRules) {
            this.virtualNetworkRulesMap.put(vnetRule.id(), vnetRule);
        }

        return this;
    }

    @Override
    public CosmosDBAccountImpl withMultipleWriteLocationsEnabled(boolean enabled) {
        this.inner().withEnableMultipleWriteLocations(enabled);
        return this;
    }

    @Override
    public CosmosDBAccountImpl withCassandraConnector(ConnectorOffer connectorOffer) {
        this.inner().withEnableCassandraConnector(true);
        this.inner().withConnectorOffer(connectorOffer);
        return this;
    }

    @Override
    public CosmosDBAccountImpl withoutCassandraConnector() {
        this.inner().withEnableCassandraConnector(false);
        this.inner().withConnectorOffer(null);
        return this;
    }

    @Override
    public CosmosDBAccountImpl withDisableKeyBaseMetadataWriteAccess(boolean disabled) {
        this.inner().withDisableKeyBasedMetadataWriteAccess(disabled);
        return this;
    }

    interface HasLocations {
        String location();
        List<Location> locations();
        void withLocations(List<Location> locations);
    }

    class CreateUpdateLocationParameters implements HasLocations {
        private DatabaseAccountCreateUpdateParameters parameters;

        CreateUpdateLocationParameters(DatabaseAccountCreateUpdateParameters parametersObject) {
            parameters = parametersObject;
        }

        @Override
        public String location() {
            return parameters.location();
        }

        @Override
        public List<Location> locations() {
            return parameters.locations();
        }

        @Override
        public void withLocations(List<Location> locations) {
            parameters.withLocations(locations);
        }
    }

    class UpdateLocationParameters implements HasLocations {
        private DatabaseAccountUpdateParameters parameters;

        UpdateLocationParameters(DatabaseAccountUpdateParameters parametersObject) {
            parameters = parametersObject;
        }

        @Override
        public String location() {
            return parameters.location();
        }

        @Override
        public List<Location> locations() {
            return parameters.locations();
        }

        @Override
        public void withLocations(List<Location> locations) {
            parameters.withLocations(locations);
        }
    }
}
