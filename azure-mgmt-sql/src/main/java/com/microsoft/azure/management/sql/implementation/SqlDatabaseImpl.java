/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.dag.FunctionalTaskItem;
import com.microsoft.azure.management.resources.fluentcore.dag.TaskGroup;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.sql.AuthenticationType;
import com.microsoft.azure.management.sql.CreateMode;
import com.microsoft.azure.management.sql.DatabaseEditions;
import com.microsoft.azure.management.sql.DatabaseMetric;
import com.microsoft.azure.management.sql.ReplicationLink;
import com.microsoft.azure.management.sql.RestorePoint;
import com.microsoft.azure.management.sql.SampleName;
import com.microsoft.azure.management.sql.ServiceObjectiveName;
import com.microsoft.azure.management.sql.ServiceTierAdvisor;
import com.microsoft.azure.management.sql.SqlDatabase;
import com.microsoft.azure.management.sql.SqlDatabaseAutomaticTuning;
import com.microsoft.azure.management.sql.SqlDatabaseBasicStorage;
import com.microsoft.azure.management.sql.SqlDatabaseMetric;
import com.microsoft.azure.management.sql.SqlDatabaseMetricDefinition;
import com.microsoft.azure.management.sql.SqlDatabaseOperations;
import com.microsoft.azure.management.sql.SqlDatabasePremiumServiceObjective;
import com.microsoft.azure.management.sql.SqlDatabasePremiumStorage;
import com.microsoft.azure.management.sql.SqlDatabaseStandardServiceObjective;
import com.microsoft.azure.management.sql.SqlDatabaseStandardStorage;
import com.microsoft.azure.management.sql.SqlDatabaseThreatDetectionPolicy;
import com.microsoft.azure.management.sql.SqlDatabaseUsageMetric;
import com.microsoft.azure.management.sql.SqlElasticPool;
import com.microsoft.azure.management.sql.SqlRestorableDroppedDatabase;
import com.microsoft.azure.management.sql.SqlServer;
import com.microsoft.azure.management.sql.SqlSyncGroupOperations;
import com.microsoft.azure.management.sql.SqlWarehouse;
import com.microsoft.azure.management.sql.StorageKeyType;
import com.microsoft.azure.management.sql.TransparentDataEncryption;
import com.microsoft.azure.management.sql.UpgradeHintInterface;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.azure.management.storage.StorageAccountKey;
import org.joda.time.DateTime;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation for SqlDatabase and its parent interfaces.
 */
@LangDefinition
class SqlDatabaseImpl
    extends
        ExternalChildResourceImpl<SqlDatabase, DatabaseInner, SqlServerImpl, SqlServer>
    implements
        SqlDatabase,
        SqlDatabase.SqlDatabaseDefinition<SqlServer.DefinitionStages.WithCreate>,
        SqlDatabase.DefinitionStages.WithExistingDatabaseAfterElasticPool<SqlServer.DefinitionStages.WithCreate>,
        SqlDatabase.DefinitionStages.WithStorageKeyAfterElasticPool<SqlServer.DefinitionStages.WithCreate>,
        SqlDatabase.DefinitionStages.WithAuthenticationAfterElasticPool<SqlServer.DefinitionStages.WithCreate>,
        SqlDatabase.DefinitionStages.WithRestorePointDatabaseAfterElasticPool<SqlServer.DefinitionStages.WithCreate>,
        SqlDatabase.Update,
        SqlDatabaseOperations.DefinitionStages.WithExistingDatabaseAfterElasticPool,
        SqlDatabaseOperations.DefinitionStages.WithStorageKeyAfterElasticPool,
        SqlDatabaseOperations.DefinitionStages.WithAuthenticationAfterElasticPool,
        SqlDatabaseOperations.DefinitionStages.WithRestorePointDatabaseAfterElasticPool,
        SqlDatabaseOperations.DefinitionStages.WithCreateAfterElasticPoolOptions,
        SqlDatabaseOperations.SqlDatabaseOperationsDefinition {

    private SqlElasticPoolsAsExternalChildResourcesImpl sqlElasticPools;

    protected SqlServerManager sqlServerManager;
    protected String resourceGroupName;
    protected String sqlServerName;
    protected String sqlServerLocation;
    private boolean isPatchUpdate;
    private ImportRequestInner importRequestInner;

    private SqlSyncGroupOperationsImpl syncGroups;


    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param name        the name of this external child resource
     * @param parent      reference to the parent of this external child resource
     * @param innerObject reference to the inner object representing this external child resource
     * @param sqlServerManager reference to the SQL server manager that accesses firewall rule operations
     */
    SqlDatabaseImpl(String name, SqlServerImpl parent, DatabaseInner innerObject, SqlServerManager sqlServerManager) {
        super(name, parent, innerObject);

        Objects.requireNonNull(parent);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        this.resourceGroupName = parent.resourceGroupName();
        this.sqlServerName = parent.name();
        this.sqlServerLocation = parent.regionName();

        this.sqlElasticPools = null;
        this.isPatchUpdate = false;
        this.importRequestInner = null;
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
    SqlDatabaseImpl(String resourceGroupName, String sqlServerName, String sqlServerLocation, String name, DatabaseInner innerObject, SqlServerManager sqlServerManager) {
        super(name, null, innerObject);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        this.resourceGroupName = resourceGroupName;
        this.sqlServerName = sqlServerName;
        this.sqlServerLocation = sqlServerLocation;

        this.sqlElasticPools = new SqlElasticPoolsAsExternalChildResourcesImpl(this.sqlServerManager, "SqlElasticPool");
        this.isPatchUpdate = false;
        this.importRequestInner = null;
    }

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param parentSqlElasticPool the parent SqlElasticPool this database belongs to
     * @param name        the name of this external child resource
     * @param innerObject reference to the inner object representing this external child resource
     * @param sqlServerManager reference to the SQL server manager that accesses firewall rule operations
     */
    SqlDatabaseImpl(TaskGroup.HasTaskGroup parentSqlElasticPool, String name, DatabaseInner innerObject, SqlServerManager sqlServerManager) {
        super(name, null, innerObject);
        Objects.requireNonNull(parentSqlElasticPool);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;

        this.sqlElasticPools = new SqlElasticPoolsAsExternalChildResourcesImpl(this.sqlServerManager, "SqlElasticPool");
        this.isPatchUpdate = false;
        this.importRequestInner = null;
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
    public String collation() {
        return this.inner().collation();
    }

    @Override
    public DateTime creationDate() {
        return this.inner().creationDate();
    }

    @Override
    public UUID currentServiceObjectiveId() {
        return this.inner().currentServiceObjectiveId();
    }

    @Override
    public String databaseId() {
        return this.inner().databaseId().toString();
    }

    @Override
    public DateTime earliestRestoreDate() {
        return this.inner().earliestRestoreDate();
    }

    @Override
    public DatabaseEditions edition() {
        return this.inner().edition();
    }

    @Override
    public UUID requestedServiceObjectiveId() {
        return this.inner().requestedServiceObjectiveId();
    }

    @Override
    public long maxSizeBytes() {
        return Long.valueOf(this.inner().maxSizeBytes());
    }

    @Override
    public ServiceObjectiveName requestedServiceObjectiveName() {
        return this.inner().requestedServiceObjectiveName();
    }

    @Override
    public ServiceObjectiveName serviceLevelObjective() {
        return this.inner().serviceLevelObjective();
    }

    @Override
    public String status() {
        return this.inner().status();
    }

    @Override
    public String elasticPoolName() {
        return this.inner().elasticPoolName();
    }

    @Override
    public String defaultSecondaryLocation() {
        return this.inner().defaultSecondaryLocation();
    }

    @Override
    public boolean isDataWarehouse() {
        return this.inner().edition().toString().equalsIgnoreCase(DatabaseEditions.DATA_WAREHOUSE.toString());
    }

    @Override
    public SqlWarehouse asWarehouse() {
        if (this.isDataWarehouse()) {
            if (this.parent() != null) {
                return new SqlWarehouseImpl(this.name(), this.parent(), this.inner(), this.sqlServerManager);
            } else {
                return new SqlWarehouseImpl(this.resourceGroupName, this.sqlServerName, this.sqlServerLocation, this.name(), this.inner(), this.sqlServerManager);
            }
        }

        return null;
    }

    @Override
    public List<RestorePoint> listRestorePoints() {
        List<RestorePoint> restorePoints = new ArrayList<>();
        List<RestorePointInner> restorePointInners = this.sqlServerManager.inner()
            .restorePoints().listByDatabase(this.resourceGroupName, this.sqlServerName, this.name());
        if (restorePointInners != null) {
            for (RestorePointInner inner : restorePointInners) {
                restorePoints.add(new RestorePointImpl(this.resourceGroupName, this.sqlServerName, inner));
            }
        }
        return Collections.unmodifiableList(restorePoints);
    }

    @Override
    public Observable<RestorePoint> listRestorePointsAsync() {
        final SqlDatabaseImpl self = this;
        return this.sqlServerManager.inner()
            .restorePoints().listByDatabaseAsync(this.resourceGroupName, this.sqlServerName, this.name())
            .flatMap(new Func1<List<RestorePointInner>, Observable<RestorePointInner>>() {
                @Override
                public Observable<RestorePointInner> call(List<RestorePointInner> restorePointInners) {
                    return Observable.from(restorePointInners);
                }
            })
            .map(new Func1<RestorePointInner, RestorePoint>() {
                @Override
                public RestorePoint call(RestorePointInner restorePointInner) {
                    return new RestorePointImpl(self.resourceGroupName, self.sqlServerName, restorePointInner);
                }
            });
    }

    @Override
    public Map<String, ReplicationLink> listReplicationLinks() {
        Map<String, ReplicationLink> replicationLinkMap = new HashMap<>();
        List<ReplicationLinkInner> replicationLinkInners = this.sqlServerManager.inner()
            .replicationLinks().listByDatabase(this.resourceGroupName, this.sqlServerName, this.name());
        if (replicationLinkInners != null) {
            for (ReplicationLinkInner inner : replicationLinkInners) {
                replicationLinkMap.put(inner.name(), new ReplicationLinkImpl(this.resourceGroupName, this.sqlServerName, inner, this.sqlServerManager));
            }
        }
        return Collections.unmodifiableMap(replicationLinkMap);
    }

    @Override
    public Observable<ReplicationLink> listReplicationLinksAsync() {
        final SqlDatabaseImpl self = this;
        return this.sqlServerManager.inner()
            .replicationLinks().listByDatabaseAsync(this.resourceGroupName, this.sqlServerName, this.name())
            .flatMap(new Func1<List<ReplicationLinkInner>, Observable<ReplicationLinkInner>>() {
                @Override
                public Observable<ReplicationLinkInner> call(List<ReplicationLinkInner> replicationLinkInners) {
                    return Observable.from(replicationLinkInners);
                }
            })
            .map(new Func1<ReplicationLinkInner, ReplicationLink>() {
                @Override
                public ReplicationLink call(ReplicationLinkInner replicationLinkInner) {
                    return new ReplicationLinkImpl(self.resourceGroupName, self.sqlServerName, replicationLinkInner, self.sqlServerManager);
                }
            });
    }

    @Override
    public SqlDatabaseExportRequestImpl exportTo(String storageUri) {
        return new SqlDatabaseExportRequestImpl(this, this.sqlServerManager)
            .exportTo(storageUri);
    }

    @Override
    public SqlDatabaseExportRequestImpl exportTo(StorageAccount storageAccount, String containerName, String fileName) {
        Objects.requireNonNull(storageAccount);
        return new SqlDatabaseExportRequestImpl(this, this.sqlServerManager)
            .exportTo(storageAccount, containerName, fileName);
    }

    @Override
    public SqlDatabaseExportRequestImpl exportTo(Creatable<StorageAccount> storageAccountCreatable, String containerName, String fileName) {
        Objects.requireNonNull(storageAccountCreatable);
        return new SqlDatabaseExportRequestImpl(this, this.sqlServerManager)
            .exportTo(storageAccountCreatable, containerName, fileName);
    }

    @Override
    public SqlDatabaseImportRequestImpl importBacpac(String storageUri) {
        return new SqlDatabaseImportRequestImpl(this, this.sqlServerManager)
            .importFrom(storageUri);
    }

    @Override
    public SqlDatabaseImportRequestImpl importBacpac(StorageAccount storageAccount, String containerName, String fileName) {
        Objects.requireNonNull(storageAccount);
        return new SqlDatabaseImportRequestImpl(this, this.sqlServerManager)
            .importFrom(storageAccount, containerName, fileName);
    }

    @Override
    public SqlDatabaseThreatDetectionPolicy.DefinitionStages.Blank defineThreatDetectionPolicy(String policyName) {
        return new SqlDatabaseThreatDetectionPolicyImpl(policyName, this, new DatabaseSecurityAlertPolicyInner(), this.sqlServerManager);
    }

    @Override
    public SqlDatabaseThreatDetectionPolicy getThreatDetectionPolicy() {
        DatabaseSecurityAlertPolicyInner policyInner = this.sqlServerManager.inner().databaseThreatDetectionPolicies()
            .get(this.resourceGroupName, this.sqlServerName, this.name());
        return policyInner != null ? new SqlDatabaseThreatDetectionPolicyImpl(policyInner.name(), this, policyInner, this.sqlServerManager) : null;
    }

    @Override
    public SqlDatabaseAutomaticTuning getDatabaseAutomaticTuning() {
        DatabaseAutomaticTuningInner databaseAutomaticTuningInner = this.sqlServerManager.inner().databaseAutomaticTunings()
            .get(this.resourceGroupName, this.sqlServerName, this.name());
        return databaseAutomaticTuningInner != null ? new SqlDatabaseAutomaticTuningImpl(this, databaseAutomaticTuningInner) : null;
    }

    @Override
    public List<SqlDatabaseUsageMetric> listUsageMetrics() {
        List<SqlDatabaseUsageMetric> databaseUsageMetrics = new ArrayList<>();
        List<DatabaseUsageInner> databaseUsageInners = this.sqlServerManager.inner().databaseUsages()
            .listByDatabase(this.resourceGroupName, this.sqlServerName, this.name());
        if (databaseUsageInners != null) {
            for (DatabaseUsageInner inner : databaseUsageInners) {
                databaseUsageMetrics.add(new SqlDatabaseUsageMetricImpl(inner));
            }
        }
        return Collections.unmodifiableList(databaseUsageMetrics);
    }

    @Override
    public Observable<SqlDatabaseUsageMetric> listUsageMetricsAsync() {
        return this.sqlServerManager.inner().databaseUsages()
            .listByDatabaseAsync(this.resourceGroupName, this.sqlServerName, this.name())
            .flatMap(new Func1<List<DatabaseUsageInner>, Observable<DatabaseUsageInner>>() {
                @Override
                public Observable<DatabaseUsageInner> call(List<DatabaseUsageInner> databaseUsageInners) {
                    return Observable.from(databaseUsageInners);
                }
            })
            .map(new Func1<DatabaseUsageInner, SqlDatabaseUsageMetric>() {
                @Override
                public SqlDatabaseUsageMetric call(DatabaseUsageInner databaseUsageInner) {
                    return new SqlDatabaseUsageMetricImpl(databaseUsageInner);
                }
            });
    }

    @Override
    public SqlDatabase rename(String newDatabaseName) {
        ResourceId resourceId = ResourceId.fromString(this.id());
        String newId = resourceId.parent().id() + "/databases/" + newDatabaseName;
        this.sqlServerManager.inner().databases()
            .rename(this.resourceGroupName, this.sqlServerName, this.name(), newId);
        return this.sqlServerManager.sqlServers().databases()
            .getBySqlServer(this.resourceGroupName, this.sqlServerName, newDatabaseName);
    }

    @Override
    public Observable<SqlDatabase> renameAsync(final String newDatabaseName) {
        final SqlDatabaseImpl self = this;
        ResourceId resourceId = ResourceId.fromString(this.id());
        String newId = resourceId.parent().id() + "/databases/" + newDatabaseName;
        return this.sqlServerManager.inner().databases()
            .renameAsync(this.resourceGroupName, this.sqlServerName, self.name(), newId)
            .flatMap(new Func1<Void, Observable<SqlDatabase>>() {
                @Override
                public Observable<SqlDatabase> call(Void aVoid) {
                    return self.sqlServerManager.sqlServers().databases()
                        .getBySqlServerAsync(self.resourceGroupName, self.sqlServerName, newDatabaseName);
                }
            });
    }

    @Override
    public List<DatabaseMetric> listUsages() {
        // This method was deprecated in favor of the other database metric related methods
        return Collections.unmodifiableList(new ArrayList<DatabaseMetric>());
    }

    @Override
    public List<SqlDatabaseMetric> listMetrics(String filter) {
        List<SqlDatabaseMetric> sqlDatabaseMetrics = new ArrayList<>();
        List<MetricInner> metricInners = this.sqlServerManager.inner().databases()
            .listMetrics(this.resourceGroupName, this.sqlServerName, this.name(), filter);
        if (metricInners != null) {
            for (MetricInner metricInner : metricInners) {
                sqlDatabaseMetrics.add(new SqlDatabaseMetricImpl(metricInner));
            }
        }
        return Collections.unmodifiableList(sqlDatabaseMetrics);
    }

    @Override
    public Observable<SqlDatabaseMetric> listMetricsAsync(final String filter) {
        return this.sqlServerManager.inner().databases()
            .listMetricsAsync(this.resourceGroupName, this.sqlServerName, this.name(), filter)
            .flatMap(new Func1<List<MetricInner>, Observable<MetricInner>>() {
                @Override
                public Observable<MetricInner> call(List<MetricInner> metricInners) {
                    return Observable.from(metricInners);
                }
            })
            .map(new Func1<MetricInner, SqlDatabaseMetric>() {
                @Override
                public SqlDatabaseMetric call(MetricInner metricInner) {
                    return new SqlDatabaseMetricImpl(metricInner);
                }
            });
    }

    @Override
    public List<SqlDatabaseMetricDefinition> listMetricDefinitions() {
        List<SqlDatabaseMetricDefinition> sqlDatabaseMetricDefinitions = new ArrayList<>();
        List<MetricDefinitionInner> metricDefinitionInners = this.sqlServerManager.inner().databases()
            .listMetricDefinitions(this.resourceGroupName, this.sqlServerName, this.name());
        if (metricDefinitionInners != null) {
            for (MetricDefinitionInner metricDefinitionInner : metricDefinitionInners) {
                sqlDatabaseMetricDefinitions.add(new SqlDatabaseMetricDefinitionImpl(metricDefinitionInner));
            }
        }

        return Collections.unmodifiableList(sqlDatabaseMetricDefinitions);
    }

    @Override
    public Observable<SqlDatabaseMetricDefinition> listMetricDefinitionsAsync() {
        return this.sqlServerManager.inner().databases()
            .listMetricDefinitionsAsync(this.resourceGroupName, this.sqlServerName, this.name())
            .flatMap(new Func1<List<MetricDefinitionInner>, Observable<MetricDefinitionInner>>() {
                @Override
                public Observable<MetricDefinitionInner> call(List<MetricDefinitionInner> metricDefinitionInners) {
                    return Observable.from(metricDefinitionInners);
                }
            })
            .map(new Func1<MetricDefinitionInner, SqlDatabaseMetricDefinition>() {
                @Override
                public SqlDatabaseMetricDefinition call(MetricDefinitionInner metricDefinitionInner) {
                    return new SqlDatabaseMetricDefinitionImpl(metricDefinitionInner);
                }
            });
    }

    @Override
    public TransparentDataEncryption getTransparentDataEncryption() {
        TransparentDataEncryptionInner transparentDataEncryptionInner = this.sqlServerManager.inner()
            .transparentDataEncryptions().get(this.resourceGroupName, this.sqlServerName, this.name());
        return new TransparentDataEncryptionImpl(this.resourceGroupName, this.sqlServerName, transparentDataEncryptionInner, this.sqlServerManager);
    }

    @Override
    public Observable<TransparentDataEncryption> getTransparentDataEncryptionAsync() {
        final SqlDatabaseImpl self = this;
        return this.sqlServerManager.inner()
            .transparentDataEncryptions().getAsync(this.resourceGroupName, this.sqlServerName, this.name())
            .map(new Func1<TransparentDataEncryptionInner, TransparentDataEncryption>() {
                @Override
                public TransparentDataEncryption call(TransparentDataEncryptionInner transparentDataEncryptionInner) {
                    return new TransparentDataEncryptionImpl(self.resourceGroupName, self.sqlServerName, transparentDataEncryptionInner, self.sqlServerManager);
                }
            });
    }

    @Override
    public Map<String, ServiceTierAdvisor> listServiceTierAdvisors() {
        Map<String, ServiceTierAdvisor> serviceTierAdvisorMap = new HashMap<>();
        List<ServiceTierAdvisorInner> serviceTierAdvisorInners = this.sqlServerManager.inner()
            .serviceTierAdvisors().listByDatabase(this.resourceGroupName, this.sqlServerName, this.name());
        if (serviceTierAdvisorInners != null) {
            for (ServiceTierAdvisorInner serviceTierAdvisorInner : serviceTierAdvisorInners) {
                serviceTierAdvisorMap.put(serviceTierAdvisorInner.name(),
                    new ServiceTierAdvisorImpl(this.resourceGroupName, this.sqlServerName, serviceTierAdvisorInner, this.sqlServerManager));
            }
        }
        return Collections.unmodifiableMap(serviceTierAdvisorMap);
    }

    @Override
    public Observable<ServiceTierAdvisor> listServiceTierAdvisorsAsync() {
        final SqlDatabaseImpl self = this;
        return this.sqlServerManager.inner()
            .serviceTierAdvisors().listByDatabaseAsync(this.resourceGroupName, this.sqlServerName, this.name())
            .flatMap(new Func1<List<ServiceTierAdvisorInner>, Observable<ServiceTierAdvisorInner>>() {
                @Override
                public Observable<ServiceTierAdvisorInner> call(List<ServiceTierAdvisorInner> serviceTierAdvisorInners) {
                    return Observable.from(serviceTierAdvisorInners);
                }
            })
            .map(new Func1<ServiceTierAdvisorInner, ServiceTierAdvisor>() {
                @Override
                public ServiceTierAdvisor call(ServiceTierAdvisorInner serviceTierAdvisorInner) {
                    return new ServiceTierAdvisorImpl(self.resourceGroupName, self.sqlServerName, serviceTierAdvisorInner, self.sqlServerManager);
                }
            });
    }

    @Override
    public String parentId() {
        return ResourceUtils.parentResourceIdFromResourceId(this.id());
    }

    @Override
    public String regionName() {
        return this.inner().location();
    }

    @Override
    public Region region() {
        return Region.findByLabelOrName(this.regionName());
    }

    @Override
    public UpgradeHintInterface getUpgradeHint() {
        return null;
    }

    @Override
    public SqlSyncGroupOperations.SqlSyncGroupActionsDefinition syncGroups() {
        if (this.syncGroups == null) {
            this.syncGroups = new SqlSyncGroupOperationsImpl(this, this.sqlServerManager);
        }

        return this.syncGroups;
    }


    SqlDatabaseImpl withPatchUpdate() {
        this.isPatchUpdate = true;
        return this;
    }

    @Override
    protected Observable<DatabaseInner> getInnerAsync() {
        return this.sqlServerManager.inner().databases().getAsync(this.resourceGroupName, this.sqlServerName, this.name());
    }

    void addParentDependency(TaskGroup.HasTaskGroup parentDependency) {
        this.addDependency(parentDependency);
    }

    @Override
    public void beforeGroupCreateOrUpdate() {
        if (this.importRequestInner != null && this.elasticPoolName() != null) {
            final SqlDatabaseImpl self = this;
            final String epName = this.elasticPoolName();
            this.addPostRunDependent(new FunctionalTaskItem() {
                @Override
                public Observable<Indexable> call(final Context context) {
                    self.importRequestInner = null;
                    self.withExistingElasticPool(epName);
                    return self.createResourceAsync()
                        .flatMap(new Func1<SqlDatabase, Observable<Indexable>>() {
                            @Override
                            public Observable<Indexable> call(SqlDatabase sqlDatabase) {
                                return context.voidObservable();
                            }
                        });
                }
            });
        }
    }

    @Override
    public Observable<SqlDatabase> createResourceAsync() {
        final SqlDatabaseImpl self = this;
        this.inner().withLocation(this.sqlServerLocation);
        if (this.importRequestInner != null) {
            this.importRequestInner.withDatabaseName(this.name());
            if (this.importRequestInner.edition() == null) {
                this.importRequestInner.withEdition(this.inner().edition());
            }
            if (this.importRequestInner.serviceObjectiveName() == null) {
                this.importRequestInner.withServiceObjectiveName((this.inner().requestedServiceObjectiveName()));
            }
            if (this.importRequestInner.maxSizeBytes() == null) {
                this.importRequestInner.withMaxSizeBytes(this.inner().maxSizeBytes());
            }

            return this.sqlServerManager.inner().databases()
                .importMethodAsync(this.resourceGroupName, this.sqlServerName, this.importRequestInner)
                .flatMap(new Func1<ImportExportResponseInner, Observable<SqlDatabase>>() {
                    @Override
                    public Observable<SqlDatabase> call(ImportExportResponseInner importExportResponseInner) {
                        if (self.elasticPoolName() != null) {
                            self.importRequestInner = null;
                            return self.withExistingElasticPool(self.elasticPoolName()).withPatchUpdate().updateResourceAsync();
                        } else {
                            return self.refreshAsync();
                        }
                    }
                });
        } else {
            return this.sqlServerManager.inner().databases()
                .createOrUpdateAsync(this.resourceGroupName, this.sqlServerName, this.name(), this.inner())
                .map(new Func1<DatabaseInner, SqlDatabase>() {
                    @Override
                    public SqlDatabase call(DatabaseInner inner) {
                        self.setInner(inner);
                        return self;
                    }
                });
        }
    }

    @Override
    public Observable<SqlDatabase> updateResourceAsync() {
        if (this.isPatchUpdate) {
            final SqlDatabaseImpl self = this;
            DatabaseUpdateInner databaseUpdateInner = new DatabaseUpdateInner()
                .withTags(self.inner().getTags())
                .withCollation(self.inner().collation())
                .withSourceDatabaseId(self.inner().sourceDatabaseId())
                .withCreateMode(self.inner().createMode())
                .withEdition(self.inner().edition())
                .withRequestedServiceObjectiveName(this.inner().requestedServiceObjectiveName())
                .withMaxSizeBytes(this.inner().maxSizeBytes())
                .withElasticPoolName(this.inner().elasticPoolName());
            databaseUpdateInner.withLocation(self.inner().location());
            return this.sqlServerManager.inner().databases()
                .updateAsync(this.resourceGroupName, this.sqlServerName, this.name(), databaseUpdateInner)
                .map(new Func1<DatabaseInner, SqlDatabase>() {
                    @Override
                    public SqlDatabase call(DatabaseInner inner) {
                        self.setInner(inner);
                        self.isPatchUpdate = false;
                        return self;
                    }
                });

        } else {
            return this.createResourceAsync();
        }
    }

    @Override
    public SqlDatabaseImpl update() {
        super.prepareUpdate();
        return this;
    }

    @Override
    public Completable afterPostRunAsync(boolean isGroupFaulted) {
        if (this.sqlElasticPools != null) {
            this.sqlElasticPools.clear();
        }
        this.importRequestInner = null;

        return Completable.complete();
    }

    @Override
    public Observable<Void> deleteResourceAsync() {
        return this.sqlServerManager.inner().databases().deleteAsync(this.resourceGroupName, this.sqlServerName, this.name());
    }

    @Override
    public void delete() {
        this.sqlServerManager.inner().databases().delete(this.resourceGroupName, this.sqlServerName, this.name());
    }

    @Override
    public Completable deleteAsync() {
        return this.deleteResourceAsync().toCompletable();
    }

    @Override
    public SqlDatabaseImpl withExistingSqlServer(String resourceGroupName, String sqlServerName, String sqlServerLocation) {
        this.resourceGroupName = resourceGroupName;
        this.sqlServerName = sqlServerName;
        this.sqlServerLocation = sqlServerLocation;

        return this;
    }

    @Override
    public SqlDatabaseImpl withExistingSqlServer(SqlServer sqlServer) {
        Objects.requireNonNull(sqlServer);
        this.resourceGroupName = sqlServer.resourceGroupName();
        this.sqlServerName = sqlServer.name();
        this.sqlServerLocation = sqlServer.regionName();

        return this;
    }

    @Override
    public SqlServerImpl attach() {
        return this.parent();
    }

    @Override
    public SqlDatabaseImpl withoutElasticPool() {
        this.inner().withElasticPoolName(null);
        this.inner().withRequestedServiceObjectiveId(null);
        this.inner().withRequestedServiceObjectiveName(ServiceObjectiveName.S0);

        return this;
    }

    @Override
    public SqlDatabaseImpl withExistingElasticPool(String elasticPoolName) {
        this.inner().withEdition(null);
        this.inner().withRequestedServiceObjectiveId(null);
        this.inner().withRequestedServiceObjectiveName(null);
        this.inner().withElasticPoolName(elasticPoolName);

        return this;
    }

    @Override
    public SqlDatabaseImpl withExistingElasticPool(SqlElasticPool sqlElasticPool) {
        Objects.requireNonNull(sqlElasticPool);
        this.inner().withEdition(null);
        this.inner().withRequestedServiceObjectiveId(null);
        this.inner().withRequestedServiceObjectiveName(null);
        this.inner().withElasticPoolName(sqlElasticPool.name());

        return this;
    }

    @Override
    public SqlDatabaseImpl withNewElasticPool(final Creatable<SqlElasticPool> sqlElasticPool) {
        Objects.requireNonNull(sqlElasticPool);
        this.inner().withEdition(null);
        this.inner().withRequestedServiceObjectiveId(null);
        this.inner().withRequestedServiceObjectiveName(null);
        this.inner().withElasticPoolName(sqlElasticPool.name());
        this.addDependency(sqlElasticPool);

        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl defineElasticPool(String elasticPoolName) {
        if (this.sqlElasticPools == null) {
            this.sqlElasticPools = new SqlElasticPoolsAsExternalChildResourcesImpl(this.taskGroup(), this.sqlServerManager, "SqlElasticPool");
        }
        this.inner().withEdition(null);
        this.inner().withRequestedServiceObjectiveId(null);
        this.inner().withRequestedServiceObjectiveName(null);
        this.inner().withElasticPoolName(elasticPoolName);

        return new SqlElasticPoolForDatabaseImpl(this, this.sqlElasticPools
            .defineIndependentElasticPool(elasticPoolName).withExistingSqlServer(this.resourceGroupName, this.sqlServerName, this.sqlServerLocation));
    }

    @Override
    public SqlDatabaseImpl fromRestorableDroppedDatabase(SqlRestorableDroppedDatabase restorableDroppedDatabase) {
        Objects.requireNonNull(restorableDroppedDatabase);
        return this.withSourceDatabase(restorableDroppedDatabase.id())
            .withMode(CreateMode.RESTORE);
    }

    private void initializeImportRequestInner() {
        this.importRequestInner = new ImportRequestInner();
        if (this.elasticPoolName() != null) {
            this.importRequestInner.withEdition(DatabaseEditions.BASIC);
            this.importRequestInner.withServiceObjectiveName(ServiceObjectiveName.BASIC);
            this.importRequestInner.withMaxSizeBytes(Long.toString(SqlDatabaseBasicStorage.MAX_2_GB.capacity()));
        } else {
            this.withStandardEdition(SqlDatabaseStandardServiceObjective.S0);
        }
    }

    @Override
    public SqlDatabaseImpl importFrom(String storageUri) {
        this.initializeImportRequestInner();
        this.importRequestInner.withStorageUri(storageUri);
        return this;
    }

    @Override
    public SqlDatabaseImpl importFrom(final StorageAccount storageAccount, final String containerName, final String fileName) {
        final SqlDatabaseImpl self = this;
        Objects.requireNonNull(storageAccount);
        this.initializeImportRequestInner();
        this.addDependency(new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> call(final Context context) {
                return storageAccount.getKeysAsync()
                    .flatMap(new Func1<List<StorageAccountKey>, Observable<StorageAccountKey>>() {
                        @Override
                        public Observable<StorageAccountKey> call(List<StorageAccountKey> storageAccountKeys) {
                            return Observable.from(storageAccountKeys).first();
                        }
                    })
                    .flatMap(new Func1<StorageAccountKey, Observable<Indexable>>() {
                        @Override
                        public Observable<Indexable> call(StorageAccountKey storageAccountKey) {
                            self.importRequestInner.withStorageUri(String.format("%s%s/%s", storageAccount.endPoints().primary().blob(), containerName, fileName));
                            self.importRequestInner.withStorageKeyType(StorageKeyType.STORAGE_ACCESS_KEY);
                            self.importRequestInner.withStorageKey(storageAccountKey.value());
                            return context.voidObservable();
                        }
                    });
            }
        });
        return this;
    }

    @Override
    public SqlDatabaseImpl withStorageAccessKey(String storageAccessKey) {
        this.importRequestInner.withStorageKeyType(StorageKeyType.STORAGE_ACCESS_KEY);
        this.importRequestInner.withStorageKey(storageAccessKey);
        return this;
    }

    @Override
    public SqlDatabaseImpl withSharedAccessKey(String sharedAccessKey) {
        this.importRequestInner.withStorageKeyType(StorageKeyType.SHARED_ACCESS_KEY);
        this.importRequestInner.withStorageKey(sharedAccessKey);
        return this;
    }

    @Override
    public SqlDatabaseImpl withSqlAdministratorLoginAndPassword(String administratorLogin, String administratorPassword) {
        this.importRequestInner.withAuthenticationType(AuthenticationType.SQL);
        this.importRequestInner.withAdministratorLogin(administratorLogin);
        this.importRequestInner.withAdministratorLoginPassword(administratorPassword);
        return this;
    }

    @Override
    public SqlDatabaseImpl withActiveDirectoryLoginAndPassword(String administratorLogin, String administratorPassword) {
        this.importRequestInner.withAuthenticationType(AuthenticationType.ADPASSWORD);
        this.importRequestInner.withAdministratorLogin(administratorLogin);
        this.importRequestInner.withAdministratorLoginPassword(administratorPassword);
        return this;
    }

    @Override
    public SqlDatabaseImpl fromRestorePoint(RestorePoint restorePoint) {
        return fromRestorePoint(restorePoint, restorePoint.earliestRestoreDate());
    }

    @Override
    public SqlDatabaseImpl fromRestorePoint(RestorePoint restorePoint, DateTime restorePointDateTime) {
        Objects.requireNonNull(restorePoint);
        this.inner().withRestorePointInTime(restorePointDateTime);
        return this.withSourceDatabase(restorePoint.databaseId())
            .withMode(CreateMode.POINT_IN_TIME_RESTORE);
    }

    @Override
    public SqlDatabaseImpl withSourceDatabase(String sourceDatabaseId) {
        this.inner().withSourceDatabaseId(sourceDatabaseId);

        return this;
    }

    @Override
    public SqlDatabaseImpl withSourceDatabase(SqlDatabase sourceDatabase) {
        return this.withSourceDatabase(sourceDatabase.id());
    }

    @Override
    public SqlDatabaseImpl withMode(CreateMode createMode) {
        this.inner().withCreateMode(createMode);

        return this;
    }

    @Override
    public SqlDatabaseImpl withCollation(String collation) {
        this.inner().withCollation(collation);

        return this;
    }

    @Override
    public SqlDatabaseImpl withMaxSizeBytes(long maxSizeBytes) {
        this.inner().withMaxSizeBytes(Long.toString(maxSizeBytes));

        return this;
    }

    @Override
    public SqlDatabaseImpl withEdition(DatabaseEditions edition) {
        this.inner().withElasticPoolName(null);
        this.inner().withRequestedServiceObjectiveId(null);
        this.inner().withEdition(edition);

        return this;
    }

    @Override
    public SqlDatabaseImpl withBasicEdition() {
        return this.withBasicEdition(SqlDatabaseBasicStorage.MAX_2_GB);
    }

    @Override
    public SqlDatabaseImpl withBasicEdition(SqlDatabaseBasicStorage maxStorageCapacity) {
        this.inner().withEdition(DatabaseEditions.BASIC);
        this.withServiceObjective(ServiceObjectiveName.BASIC);
        this.inner().withMaxSizeBytes(Long.toString(maxStorageCapacity.capacity()));
        return this;
    }

    @Override
    public SqlDatabaseImpl withStandardEdition(SqlDatabaseStandardServiceObjective serviceObjective) {
        return this.withStandardEdition(serviceObjective, SqlDatabaseStandardStorage.MAX_250_GB);
    }

    @Override
    public SqlDatabaseImpl withStandardEdition(SqlDatabaseStandardServiceObjective serviceObjective, SqlDatabaseStandardStorage maxStorageCapacity) {
        this.inner().withEdition(DatabaseEditions.STANDARD);
        this.withServiceObjective(ServiceObjectiveName.fromString(serviceObjective.toString()));
        this.inner().withMaxSizeBytes(Long.toString(maxStorageCapacity.capacity()));
        return this;
    }

    @Override
    public SqlDatabaseImpl withPremiumEdition(SqlDatabasePremiumServiceObjective serviceObjective) {
        return this.withPremiumEdition(serviceObjective, SqlDatabasePremiumStorage.MAX_500_GB);
    }

    @Override
    public SqlDatabaseImpl withPremiumEdition(SqlDatabasePremiumServiceObjective serviceObjective, SqlDatabasePremiumStorage maxStorageCapacity) {
        this.inner().withEdition(DatabaseEditions.PREMIUM);
        this.withServiceObjective(ServiceObjectiveName.fromString(serviceObjective.toString()));
        this.inner().withMaxSizeBytes(Long.toString(maxStorageCapacity.capacity()));
        return this;
    }

    @Override
    public SqlDatabaseImpl withServiceObjective(ServiceObjectiveName serviceLevelObjective) {
        this.inner().withElasticPoolName(null);
        this.inner().withRequestedServiceObjectiveId(null);
        this.inner().withRequestedServiceObjectiveName(serviceLevelObjective);

        return this;
    }

    @Override
    public SqlDatabaseImpl withTags(Map<String, String> tags) {
        this.inner().withTags(new HashMap<>(tags));
        return this;
    }

    @Override
    public SqlDatabaseImpl withTag(String key, String value) {
        if (this.inner().getTags() == null) {
            this.inner().withTags(new HashMap<String, String>());
        }
        this.inner().getTags().put(key, value);
        return this;
    }

    @Override
    public SqlDatabaseImpl withoutTag(String key) {
        if (this.inner().getTags() != null) {
            this.inner().getTags().remove(key);
        }
        return this;
    }

    @Override
    public SqlDatabaseImpl fromSample(SampleName sampleName) {
        this.inner().withSampleName(sampleName);
        return this;
    }
}
