/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.dag.TaskGroup;
import com.microsoft.azure.v2.management.sql.ElasticPoolActivity;
import com.microsoft.azure.v2.management.sql.ElasticPoolDatabaseActivity;
import com.microsoft.azure.v2.management.sql.ElasticPoolEdition;
import com.microsoft.azure.v2.management.sql.ElasticPoolState;
import com.microsoft.azure.v2.management.sql.SqlDatabase;
import com.microsoft.azure.v2.management.sql.SqlDatabaseMetric;
import com.microsoft.azure.v2.management.sql.SqlDatabaseMetricDefinition;
import com.microsoft.azure.v2.management.sql.SqlDatabaseStandardServiceObjective;
import com.microsoft.azure.v2.management.sql.SqlElasticPool;
import com.microsoft.azure.v2.management.sql.SqlElasticPoolBasicEDTUs;
import com.microsoft.azure.v2.management.sql.SqlElasticPoolBasicMaxEDTUs;
import com.microsoft.azure.v2.management.sql.SqlElasticPoolBasicMinEDTUs;
import com.microsoft.azure.v2.management.sql.SqlElasticPoolOperations;
import com.microsoft.azure.v2.management.sql.SqlElasticPoolPremiumEDTUs;
import com.microsoft.azure.v2.management.sql.SqlElasticPoolPremiumMaxEDTUs;
import com.microsoft.azure.v2.management.sql.SqlElasticPoolPremiumMinEDTUs;
import com.microsoft.azure.v2.management.sql.SqlElasticPoolPremiumSorage;
import com.microsoft.azure.v2.management.sql.SqlElasticPoolStandardEDTUs;
import com.microsoft.azure.v2.management.sql.SqlElasticPoolStandardMaxEDTUs;
import com.microsoft.azure.v2.management.sql.SqlElasticPoolStandardMinEDTUs;
import com.microsoft.azure.v2.management.sql.SqlElasticPoolStandardStorage;
import com.microsoft.azure.v2.management.sql.SqlServer;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation for SqlElasticPool.
 */
@LangDefinition
public class SqlElasticPoolImpl
    extends
        ExternalChildResourceImpl<SqlElasticPool, ElasticPoolInner, SqlServerImpl, SqlServer>
    implements
        SqlElasticPool,
        SqlElasticPool.SqlElasticPoolDefinition<SqlServer.DefinitionStages.WithCreate>,
        SqlElasticPoolOperations.DefinitionStages.WithCreate,
        SqlElasticPool.Update,
        SqlElasticPoolOperations.SqlElasticPoolOperationsDefinition {

    private SqlServerManager sqlServerManager;
    private String resourceGroupName;
    private String sqlServerName;
    private String sqlServerLocation;

    private SqlDatabasesAsExternalChildResourcesImpl sqlDatabases;

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param name        the name of this external child resource
     * @param parent      reference to the parent of this external child resource
     * @param innerObject reference to the inner object representing this external child resource
     * @param sqlServerManager reference to the SQL server manager that accesses firewall rule operations
     */
    SqlElasticPoolImpl(String name, SqlServerImpl parent, ElasticPoolInner innerObject, SqlServerManager sqlServerManager) {
        super(name, parent, innerObject);

        Objects.requireNonNull(parent);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        this.resourceGroupName = parent.resourceGroupName();
        this.sqlServerName = parent.name();
        this.sqlServerLocation = parent.regionName();

        this.sqlDatabases = null;
    }

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param resourceGroupName the resource group name
     * @param sqlServerName the parent SQL server name
     * @param sqlServerLocation the parent SQL server location
     * @param name        the name of this external child resource
     * @param innerObject reference to the inner object representing this external child resource
     * @param sqlServerManager reference to the SQL server manager that accesses firewall rule operations
     */
    SqlElasticPoolImpl(String resourceGroupName, String sqlServerName, String sqlServerLocation, String name, ElasticPoolInner innerObject, SqlServerManager sqlServerManager) {
        super(name, null, innerObject);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        this.resourceGroupName = resourceGroupName;
        this.sqlServerName = sqlServerName;
        this.sqlServerLocation = sqlServerLocation;

        this.sqlDatabases = new SqlDatabasesAsExternalChildResourcesImpl(this.taskGroup(), this.sqlServerManager, "SqlDatabase");
    }

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param name        the name of this external child resource
     * @param innerObject reference to the inner object representing this external child resource
     * @param sqlServerManager reference to the SQL server manager that accesses firewall rule operations
     */
    SqlElasticPoolImpl(String name, ElasticPoolInner innerObject, SqlServerManager sqlServerManager) {
        super(name, null, innerObject);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;

        this.sqlDatabases = new SqlDatabasesAsExternalChildResourcesImpl(this.taskGroup(), this.sqlServerManager, "SqlDatabase");
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String resourceGroupName() {
        return this.resourceGroupName;
    }

    @Override
    public String sqlServerName() {
        return this.sqlServerName;
    }

    @Override
    public OffsetDateTime creationDate() {
        return this.inner().creationDate();
    }

    @Override
    public ElasticPoolState state() {
        return this.inner().state();
    }

    @Override
    public ElasticPoolEdition edition() {
        return this.inner().edition();
    }

    @Override
    public int dtu() {
        return this.inner().dtu();
    }

    @Override
    public int databaseDtuMax() {
        return this.inner().databaseDtuMax();
    }

    @Override
    public int databaseDtuMin() {
        return this.inner().databaseDtuMin();
    }

    @Override
    public int storageMB() {
        return this.inner().storageMB();
    }

    @Override
    public int storageCapacityInMB() {
        return this.inner().storageMB();
    }

    @Override
    public String parentId() {
        return ResourceUtils.parentResourceIdFromResourceId(this.id());
    }

    @Override
    public String regionName() {
        return this.sqlServerLocation;
    }

    @Override
    public Region region() {
        return Region.fromName(this.regionName());
    }

    @Override
    public List<ElasticPoolActivity> listActivities() {
        List<ElasticPoolActivity> elasticPoolActivities = new ArrayList<>();
        List<ElasticPoolActivityInner> elasticPoolActivityInners = this.sqlServerManager.inner()
            .elasticPoolActivities().listByElasticPool(this.resourceGroupName, this.sqlServerName, this.name());
        if (elasticPoolActivityInners != null) {
            for (ElasticPoolActivityInner inner : elasticPoolActivityInners) {
                elasticPoolActivities.add(new ElasticPoolActivityImpl(inner));
            }
        }
        return Collections.unmodifiableList(elasticPoolActivities);
    }

    @Override
    public Observable<ElasticPoolActivity> listActivitiesAsync() {
        return this.sqlServerManager.inner()
            .elasticPoolActivities().listByElasticPoolAsync(this.resourceGroupName, this.sqlServerName, this.name())
                .flatMapObservable(list -> Observable.fromIterable(list))
                .map(elasticPoolActivityInner -> new ElasticPoolActivityImpl(elasticPoolActivityInner));
    }

    @Override
    public List<ElasticPoolDatabaseActivity> listDatabaseActivities() {
        List<ElasticPoolDatabaseActivity> elasticPoolDatabaseActivities = new ArrayList<>();
        List<ElasticPoolDatabaseActivityInner> elasticPoolDatabaseActivityInners = this.sqlServerManager.inner()
            .elasticPoolDatabaseActivities().listByElasticPool(this.resourceGroupName, this.sqlServerName, this.name());
        if (elasticPoolDatabaseActivityInners != null) {
            for (ElasticPoolDatabaseActivityInner inner : elasticPoolDatabaseActivityInners) {
                elasticPoolDatabaseActivities.add(new ElasticPoolDatabaseActivityImpl(inner));
            }
        }
        return Collections.unmodifiableList(elasticPoolDatabaseActivities);
    }

    @Override
    public Observable<ElasticPoolDatabaseActivity> listDatabaseActivitiesAsync() {
        return this.sqlServerManager.inner()
            .elasticPoolDatabaseActivities().listByElasticPoolAsync(this.resourceGroupName, this.sqlServerName, this.name())
                .flatMapObservable(list -> Observable.fromIterable(list))
                .map(elasticPoolDatabaseActivityInner -> new ElasticPoolDatabaseActivityImpl(elasticPoolDatabaseActivityInner));
    }

    @Override
    public List<SqlDatabaseMetric> listDatabaseMetrics(String filter) {
        List<SqlDatabaseMetric> databaseMetrics = new ArrayList<>();
        List<MetricInner> inners = this.sqlServerManager.inner().elasticPools().listMetrics(this.resourceGroupName, this.sqlServerName, this.name(), filter);
        if (inners != null) {
            for (MetricInner inner : inners) {
                databaseMetrics.add(new SqlDatabaseMetricImpl(inner));
            }
        }

        return Collections.unmodifiableList(databaseMetrics);
    }

    @Override
    public Observable<SqlDatabaseMetric> listDatabaseMetricsAsync(String filter) {
        return this.sqlServerManager.inner().elasticPools().listMetricsAsync(this.resourceGroupName, this.sqlServerName, this.name(), filter)
                .flatMapObservable(list -> Observable.fromIterable(list))
                .map(metricInner -> new SqlDatabaseMetricImpl(metricInner));
    }

    @Override
    public List<SqlDatabaseMetricDefinition> listDatabaseMetricDefinitions() {
        List<SqlDatabaseMetricDefinition> databaseMetricDefinitions = new ArrayList<>();
        List<MetricDefinitionInner> inners = this.sqlServerManager.inner().elasticPools().listMetricDefinitions(this.resourceGroupName, this.sqlServerName, this.name());
        if (inners != null) {
            for (MetricDefinitionInner inner : inners) {
                databaseMetricDefinitions.add(new SqlDatabaseMetricDefinitionImpl(inner));
            }
        }

        return Collections.unmodifiableList(databaseMetricDefinitions);
    }

    @Override
    public Observable<SqlDatabaseMetricDefinition> listDatabaseMetricDefinitionsAsync() {
        return this.sqlServerManager.inner().elasticPools().listMetricDefinitionsAsync(this.resourceGroupName, this.sqlServerName, this.name())
                .flatMapObservable(list -> Observable.fromIterable(list))
                .map(metricDefinitionInner -> new SqlDatabaseMetricDefinitionImpl(metricDefinitionInner));
    }

    @Override
    public List<SqlDatabase> listDatabases() {
        List<SqlDatabase> databases = new ArrayList<>();
        List<DatabaseInner> databaseInners = this.sqlServerManager.inner().databases()
            .listByElasticPool(this.resourceGroupName, this.sqlServerName, this.name());
        if (databaseInners != null) {
            for (DatabaseInner inner : databaseInners) {
                databases.add(new SqlDatabaseImpl(this.resourceGroupName, this.sqlServerName, this.sqlServerLocation, inner.name(), inner, this.sqlServerManager));
            }
        }
        return Collections.unmodifiableList(databases);
    }

    @Override
    public Observable<SqlDatabase> listDatabasesAsync() {
        return this.sqlServerManager.inner().databases()
            .listByElasticPoolAsync(this.resourceGroupName, this.sqlServerName, this.name())
                .flatMapObservable(list -> Observable.fromIterable(list))
                .map(databaseInner ->
                        new SqlDatabaseImpl(this.resourceGroupName,
                                this.sqlServerName,
                                this.sqlServerLocation,
                                databaseInner.name(),
                                databaseInner, this.sqlServerManager));
    }

    @Override
    public SqlDatabase getDatabase(String databaseName) {
        DatabaseInner databaseInner = this.sqlServerManager.inner().databases()
            .get(this.resourceGroupName, this.sqlServerName, databaseName);

        return databaseInner != null ? new SqlDatabaseImpl(this.resourceGroupName, this.sqlServerName, this.sqlServerLocation, databaseName, databaseInner, this.sqlServerManager) : null;
    }

    @Override
    public SqlDatabase addNewDatabase(String databaseName) {
        return this.sqlServerManager.sqlServers().databases()
            .define(databaseName)
            .withExistingSqlServer(this.resourceGroupName, this.sqlServerName, this.sqlServerLocation)
            .withExistingElasticPool(this)
            .create();
    }

    @Override
    public SqlDatabase addExistingDatabase(String databaseName) {
        return this.getDatabase(databaseName)
            .update()
            .withExistingElasticPool(this)
            .apply();
    }

    @Override
    public SqlDatabase addExistingDatabase(SqlDatabase database) {
        return database
            .update()
            .withExistingElasticPool(this)
            .apply();
    }

    @Override
    public SqlDatabase removeDatabase(String databaseName) {
        return this.getDatabase(databaseName)
            .update()
            .withoutElasticPool()
            .withStandardEdition(SqlDatabaseStandardServiceObjective.S0)
            .apply();
    }

    @Override
    public void delete() {
        this.sqlServerManager.inner().elasticPools().delete(this.resourceGroupName, this.sqlServerName, this.name());
    }

    @Override
    public Completable deleteAsync() {
        return this.deleteResourceAsync();
    }

    @Override
    protected Maybe<ElasticPoolInner> getInnerAsync() {
        return this.sqlServerManager.inner().elasticPools().getAsync(this.resourceGroupName, this.sqlServerName, this.name());
    }

    @Override
    public Observable<SqlElasticPool> createResourceAsync() {
        this.inner().withLocation(this.sqlServerLocation);
        return this.sqlServerManager.inner().elasticPools()
            .createOrUpdateAsync(this.resourceGroupName, this.sqlServerName, this.name(), this.inner())
                .map(responseInner -> {
                    this.setInner(responseInner);
                    return (SqlElasticPool) this;
                })
                .toObservable();
    }

    @Override
    public Observable<SqlElasticPool> updateResourceAsync() {
        final SqlElasticPoolImpl self = this;
        return this.sqlServerManager.inner().elasticPools()
            .createOrUpdateAsync(this.resourceGroupName, this.sqlServerName, this.name(), this.inner())
                .map(responseInner -> {
                    this.setInner(responseInner);
                    return (SqlElasticPool) this;
                })
                .toObservable();
    }

    void addParentDependency(TaskGroup.HasTaskGroup parentDependency) {
        this.addDependency(parentDependency);
    }

    @Override
    public void beforeGroupCreateOrUpdate() {
    }

    @Override
    public Completable afterPostRunAsync(boolean isGroupFaulted) {
        if (this.sqlDatabases != null) {
            this.sqlDatabases.clear();
        }

        return Completable.complete();
    }

    @Override
    public Completable deleteResourceAsync() {
        return this.sqlServerManager.inner().elasticPools().deleteAsync(this.resourceGroupName, this.sqlServerName, this.name());
    }

    @Override
    public Update update() {
        super.prepareUpdate();
        return this;
    }

    @Override
    public SqlElasticPoolImpl withExistingSqlServer(String resourceGroupName, String sqlServerName, String location) {
        this.resourceGroupName = resourceGroupName;
        this.sqlServerName = sqlServerName;
        this.sqlServerLocation = location;

        return this;
    }

    @Override
    public SqlElasticPoolImpl withExistingSqlServer(SqlServer sqlServer) {
        this.resourceGroupName = sqlServer.resourceGroupName();
        this.sqlServerName = sqlServer.name();
        this.sqlServerLocation = sqlServer.regionName();

        return this;
    }

    @Override
    public SqlElasticPoolImpl withEdition(ElasticPoolEdition edition) {
        this.inner().withEdition(edition);
        return this;
    }

    @Override
    public SqlElasticPoolImpl withBasicPool() {
        this.inner().withEdition(ElasticPoolEdition.BASIC);
        return this;
    }

    @Override
    public SqlElasticPoolImpl withStandardPool() {
        this.inner().withEdition(ElasticPoolEdition.STANDARD);
        return this;
    }

    @Override
    public SqlElasticPoolImpl withPremiumPool() {
        this.inner().withEdition(ElasticPoolEdition.PREMIUM);
        return this;
    }

    @Override
    public SqlElasticPoolImpl withReservedDtu(SqlElasticPoolBasicEDTUs eDTU) {
        this.inner().withDtu(eDTU.value());
        return this;
    }

    @Override
    public SqlElasticPoolImpl withDatabaseDtuMax(SqlElasticPoolBasicMaxEDTUs eDTU) {
        this.inner().withDatabaseDtuMax(eDTU.value());
        return this;
    }

    @Override
    public SqlElasticPoolImpl withDatabaseDtuMin(SqlElasticPoolBasicMinEDTUs eDTU) {
        this.inner().withDatabaseDtuMin(eDTU.value());
        return this;
    }

    @Override
    public SqlElasticPoolImpl withReservedDtu(SqlElasticPoolStandardEDTUs eDTU) {
        this.inner().withDtu(eDTU.value());
        return this;
    }

    @Override
    public SqlElasticPoolImpl withDatabaseDtuMax(SqlElasticPoolStandardMaxEDTUs eDTU) {
        this.inner().withDatabaseDtuMax(eDTU.value());
        return this;
    }

    @Override
    public SqlElasticPoolImpl withDatabaseDtuMin(SqlElasticPoolStandardMinEDTUs eDTU) {
        this.inner().withDatabaseDtuMin(eDTU.value());
        return this;
    }

    @Override
    public SqlElasticPoolImpl withStorageCapacity(SqlElasticPoolStandardStorage storageCapacity) {
        this.inner().withStorageMB(storageCapacity.capacityInMB());
        return this;
    }

    @Override
    public SqlElasticPoolImpl withReservedDtu(SqlElasticPoolPremiumEDTUs eDTU) {
        this.inner().withDtu(eDTU.value());
        return this;
    }

    @Override
    public SqlElasticPoolImpl withDatabaseDtuMax(SqlElasticPoolPremiumMaxEDTUs eDTU) {
        this.inner().withDatabaseDtuMax(eDTU.value());
        return this;
    }

    @Override
    public SqlElasticPoolImpl withDatabaseDtuMin(SqlElasticPoolPremiumMinEDTUs eDTU) {
        this.inner().withDatabaseDtuMin(eDTU.value());
        return this;
    }

    @Override
    public SqlElasticPoolImpl withStorageCapacity(SqlElasticPoolPremiumSorage storageCapacity) {
        this.inner().withStorageMB(storageCapacity.capacityInMB());
        return this;
    }

    @Override
    public SqlElasticPoolImpl withDatabaseDtuMin(int databaseDtuMin) {
        this.inner().withDatabaseDtuMin(databaseDtuMin);
        return this;
    }

    @Override
    public SqlElasticPoolImpl withDatabaseDtuMax(int databaseDtuMax) {
        this.inner().withDatabaseDtuMax(databaseDtuMax);
        return this;
    }

    @Override
    public SqlElasticPoolImpl withDtu(int dtu) {
        this.inner().withDtu(dtu);
        return this;
    }

    @Override
    public SqlElasticPoolImpl withStorageCapacity(int storageMB) {
        this.inner().withStorageMB(storageMB);
        return this;
    }

    @Override
    public SqlElasticPoolImpl withNewDatabase(String databaseName) {
        if (this.sqlDatabases == null) {
            this.sqlDatabases = new SqlDatabasesAsExternalChildResourcesImpl(this.taskGroup(), this.sqlServerManager, "SqlDatabase");
        }

        return new SqlDatabaseForElasticPoolImpl(this, this.sqlDatabases
            .defineInlineDatabase(databaseName).withExistingSqlServer(this.resourceGroupName, this.sqlServerName, this.sqlServerLocation))
            .attach();
    }

    @Override
    public SqlElasticPoolImpl withExistingDatabase(String databaseName) {
        if (this.sqlDatabases == null) {
            this.sqlDatabases = new SqlDatabasesAsExternalChildResourcesImpl(this.taskGroup(), this.sqlServerManager, "SqlDatabase");
        }

        return new SqlDatabaseForElasticPoolImpl(this, this.sqlDatabases
            .patchUpdateDatabase(databaseName).withExistingSqlServer(this.resourceGroupName, this.sqlServerName, this.sqlServerLocation))
            .attach();
    }

    @Override
    public SqlElasticPoolImpl withExistingDatabase(SqlDatabase database) {
        if (this.sqlDatabases == null) {
            this.sqlDatabases = new SqlDatabasesAsExternalChildResourcesImpl(this.taskGroup(), this.sqlServerManager, "SqlDatabase");
        }

        return new SqlDatabaseForElasticPoolImpl(this, this.sqlDatabases
            .patchUpdateDatabase(database.name()).withExistingSqlServer(this.resourceGroupName, this.sqlServerName, this.sqlServerLocation))
            .attach();
    }

    @Override
    public SqlDatabaseForElasticPoolImpl defineDatabase(String databaseName) {
        if (this.sqlDatabases == null) {
            this.sqlDatabases = new SqlDatabasesAsExternalChildResourcesImpl(this.taskGroup(), this.sqlServerManager, "SqlDatabase");
        }

        return new SqlDatabaseForElasticPoolImpl(this, this.sqlDatabases
            .defineInlineDatabase(databaseName).withExistingSqlServer(this.resourceGroupName, this.sqlServerName, this.sqlServerLocation));
    }

    @Override
    public SqlElasticPoolImpl withTags(Map<String, String> tags) {
        this.inner().withTags(new HashMap<>(tags));
        return this;
    }

    @Override
    public SqlElasticPoolImpl withTag(String key, String value) {
        if (this.inner().getTags() == null) {
            this.inner().withTags(new HashMap<String, String>());
        }
        this.inner().getTags().put(key, value);
        return this;
    }

    @Override
    public SqlElasticPoolImpl withoutTag(String key) {
        if (this.inner().getTags() != null) {
            this.inner().getTags().remove(key);
        }
        return this;
    }

    @Override
    public SqlServerImpl attach() {
        return parent();
    }
}
