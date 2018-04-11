/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.dag.FunctionalTaskItem;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.sql.ElasticPoolEditions;
import com.microsoft.azure.management.sql.IdentityType;
import com.microsoft.azure.management.sql.RecommendedElasticPool;
import com.microsoft.azure.management.sql.ResourceIdentity;
import com.microsoft.azure.management.sql.ServerMetric;
import com.microsoft.azure.management.sql.ServiceObjective;
import com.microsoft.azure.management.sql.SqlDatabaseOperations;
import com.microsoft.azure.management.sql.SqlElasticPoolOperations;
import com.microsoft.azure.management.sql.SqlEncryptionProtectorOperations;
import com.microsoft.azure.management.sql.SqlFailoverGroupOperations;
import com.microsoft.azure.management.sql.SqlFirewallRule;
import com.microsoft.azure.management.sql.SqlFirewallRuleOperations;
import com.microsoft.azure.management.sql.SqlRestorableDroppedDatabase;
import com.microsoft.azure.management.sql.SqlServer;
import com.microsoft.azure.management.sql.SqlServerAutomaticTuning;
import com.microsoft.azure.management.sql.SqlServerDnsAliasOperations;
import com.microsoft.azure.management.sql.SqlServerKeyOperations;
import com.microsoft.azure.management.sql.SqlVirtualNetworkRule;
import com.microsoft.azure.management.sql.SqlVirtualNetworkRuleOperations;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Implementation for SqlServer and its parent interfaces.
 */
@LangDefinition
public class SqlServerImpl
        extends
            GroupableResourceImpl<
                    SqlServer,
                    ServerInner,
                    SqlServerImpl,
                    SqlServerManager>
        implements
            SqlServer,
            SqlServer.Definition,
            SqlServer.Update {

    private FunctionalTaskItem sqlADAdminCreator;
    private boolean allowAzureServicesAccess;
    private SqlFirewallRulesAsExternalChildResourcesImpl sqlFirewallRules;
    private SqlFirewallRuleOperations.SqlFirewallRuleActionsDefinition sqlFirewallRuleOperations;
    private SqlVirtualNetworkRulesAsExternalChildResourcesImpl sqlVirtualNetworkRules;
    private SqlVirtualNetworkRuleOperations.SqlVirtualNetworkRuleActionsDefinition sqlVirtualNetworkRuleOperations;
    private SqlElasticPoolsAsExternalChildResourcesImpl sqlElasticPools;
    private SqlElasticPoolOperations.SqlElasticPoolActionsDefinition sqlElasticPoolOperations;
    private SqlDatabasesAsExternalChildResourcesImpl sqlDatabases;
    private SqlDatabaseOperations.SqlDatabaseActionsDefinition sqlDatabaseOperations;
    private SqlServerDnsAliasOperations.SqlServerDnsAliasActionsDefinition sqlServerDnsAliasOperations;
    private SqlFailoverGroupOperations.SqlFailoverGroupActionsDefinition sqlFailoverGroupOperations;
    private SqlServerKeyOperations.SqlServerKeyActionsDefinition sqlServerKeyOperations;
    private SqlEncryptionProtectorOperations.SqlEncryptionProtectorActionsDefinition sqlEncryptionProtectorsOperations;

    protected SqlServerImpl(String name, ServerInner innerObject, SqlServerManager manager) {
        super(name, innerObject, manager);

        this.sqlADAdminCreator = null;
        this.allowAzureServicesAccess = true;
        this.sqlFirewallRules = new SqlFirewallRulesAsExternalChildResourcesImpl(this, "SqlFirewallRule");
        this.sqlVirtualNetworkRules = new SqlVirtualNetworkRulesAsExternalChildResourcesImpl(this, "SqlVirtualNetworkRule");
        this.sqlElasticPools = new SqlElasticPoolsAsExternalChildResourcesImpl(this, "SqlElasticPool");
        this.sqlDatabases = new SqlDatabasesAsExternalChildResourcesImpl(this, "SqlDatabase");
    }

    @Override
    protected Observable<ServerInner> getInnerAsync() {
        return this.manager().inner().servers().getByResourceGroupAsync(
                this.resourceGroupName(), this.name());
    }

    @Override
    public Observable<SqlServer> createResourceAsync() {
        final SqlServer self = this;
        return this.manager().inner().servers().createOrUpdateAsync(this.resourceGroupName(), this.name(), this.inner())
            .map(new Func1<ServerInner, SqlServer>() {
                @Override
                public SqlServer call(ServerInner serverInner) {
                    setInner(serverInner);
                    return self;
                }
            });
    }

    @Override
    public void beforeGroupCreateOrUpdate() {
        if (this.isInCreateMode()) {
            if (allowAzureServicesAccess) {
                this.sqlFirewallRules
                    .defineInlineFirewallRule("AllowAllWindowsAzureIps")
                    .withStartIPAddress("0.0.0.0")
                    .withEndIPAddress("0.0.0.0");
            }
            if (sqlADAdminCreator != null) {
                this.addPostRunDependent(sqlADAdminCreator);
            }
        }
        if (this.sqlElasticPools != null && this.sqlDatabases != null) {
            // Databases must be deleted before the Elastic Pools (only an empty Elastic Pool can be deleted)
            List<SqlDatabaseImpl> dbToBeRemoved = this.sqlDatabases.getChildren(ExternalChildResourceImpl.PendingOperation.ToBeRemoved);
            List<SqlElasticPoolImpl> epToBeRemoved = this.sqlElasticPools.getChildren(ExternalChildResourceImpl.PendingOperation.ToBeRemoved);
            for (SqlElasticPoolImpl epItem : epToBeRemoved) {
                for (SqlDatabaseImpl dbItem : dbToBeRemoved) {
                    epItem.addParentDependency(dbItem);
                }
            }

            // Databases in a new Elastic Pool should be created after the Elastic Pool
            List<SqlDatabaseImpl> dbToBeCreated = this.sqlDatabases.getChildren(ExternalChildResourceImpl.PendingOperation.ToBeCreated);
            List<SqlElasticPoolImpl> epToBeCreated = this.sqlElasticPools.getChildren(ExternalChildResourceImpl.PendingOperation.ToBeCreated);
            for (SqlElasticPoolImpl epItem : epToBeCreated) {
                for (SqlDatabaseImpl dbItem : dbToBeCreated) {
                    if (dbItem.elasticPoolName() != null && dbItem.elasticPoolName().equals(epItem.name())) {
                        dbItem.addParentDependency(epItem);
                    }
                }
            }

        }
    }

    @Override
    public Completable afterPostRunAsync(boolean isGroupFaulted) {
        this.sqlADAdminCreator = null;
        this.sqlFirewallRules.clear();
        this.sqlElasticPools.clear();
        this.sqlDatabases.clear();
        return Completable.complete();
    }

    @Override
    public String fullyQualifiedDomainName() {
        return this.inner().fullyQualifiedDomainName();
    }

    @Override
    public String administratorLogin() {
        return this.inner().administratorLogin();
    }

    @Override
    public String kind() {
        return this.inner().kind();
    }

    @Override
    public String state() {
        return this.inner().state();
    }

    @Override
    public boolean isManagedServiceIdentityEnabled() {
        return this.inner().identity() != null && this.inner().identity().type().equals(IdentityType.SYSTEM_ASSIGNED);
    }

    @Override
    public String systemAssignedManagedServiceIdentityTenantId() {
        return this.inner().identity() != null ? this.inner().identity().tenantId().toString() : null;
    }

    @Override
    public String systemAssignedManagedServiceIdentityPrincipalId() {
        return this.inner().identity() != null ? this.inner().identity().principalId().toString() : null;
    }

    @Override
    public IdentityType managedServiceIdentityType() {
        return this.inner().identity() != null ? this.inner().identity().type() : null;
    }

    @Override
    public List<ServerMetric> listUsages() {
        return listUsageMetrics();
    }

    @Override
    public List<ServerMetric> listUsageMetrics() {
        List<ServerMetric> serverMetrics = new ArrayList<>();
        List<ServerUsageInner> serverUsageInners = this.manager().inner().serverUsages()
            .listByServer(this.resourceGroupName(), this.name());
        if (serverUsageInners != null) {
            for (ServerUsageInner serverUsageInner : serverUsageInners) {
                serverMetrics.add(new ServerMetricImpl(serverUsageInner));
            }
        }
        return Collections.unmodifiableList(serverMetrics);
    }

    @Override
    public List<ServiceObjective> listServiceObjectives() {
        List<ServiceObjective> serviceObjectives = new ArrayList<>();
        List<ServiceObjectiveInner> serviceObjectiveInners = this.manager().inner().serviceObjectives()
            .listByServer(this.resourceGroupName(), this.name());
        if (serviceObjectiveInners != null) {
            for (ServiceObjectiveInner inner : serviceObjectiveInners) {
                serviceObjectives.add(new ServiceObjectiveImpl(inner, this));
            }
        }
        return Collections.unmodifiableList(serviceObjectives);
    }

    @Override
    public ServiceObjective getServiceObjective(String serviceObjectiveName) {
        ServiceObjectiveInner inner = this.manager().inner().serviceObjectives()
            .get(this.resourceGroupName(), this.name(), serviceObjectiveName);
        return (inner != null) ? new ServiceObjectiveImpl(inner, this) : null;
    }

    @Override
    public Map<String, RecommendedElasticPool> listRecommendedElasticPools() {
        Map<String, RecommendedElasticPool> recommendedElasticPoolMap = new HashMap<>();
        List<RecommendedElasticPoolInner> recommendedElasticPoolInners = this.manager().inner()
            .recommendedElasticPools().listByServer(this.resourceGroupName(), this.name());
        if (recommendedElasticPoolInners != null) {
            for (RecommendedElasticPoolInner inner : recommendedElasticPoolInners) {
                recommendedElasticPoolMap.put(inner.name(), new RecommendedElasticPoolImpl(inner, this));
            }
        }

        return Collections.unmodifiableMap(recommendedElasticPoolMap);
    }

    @Override
    public List<SqlRestorableDroppedDatabase> listRestorableDroppedDatabases() {
        List<SqlRestorableDroppedDatabase> sqlRestorableDroppedDatabases = new ArrayList<>();
        List<RestorableDroppedDatabaseInner> restorableDroppedDatabasesInners = this.manager().inner()
            .restorableDroppedDatabases().listByServer(this.resourceGroupName(), this.name());
        if (restorableDroppedDatabasesInners != null) {
            for (RestorableDroppedDatabaseInner restorableDroppedDatabaseInner : restorableDroppedDatabasesInners) {
                sqlRestorableDroppedDatabases.add(new SqlRestorableDroppedDatabaseImpl(this.resourceGroupName(), this.name(), restorableDroppedDatabaseInner, this.manager()));
            }
        }
        return Collections.unmodifiableList(sqlRestorableDroppedDatabases);
    }

    @Override
    public Observable<SqlRestorableDroppedDatabase> listRestorableDroppedDatabasesAsync() {
        final SqlServerImpl self = this;
        return this.manager().inner()
            .restorableDroppedDatabases().listByServerAsync(this.resourceGroupName(), this.name())
            .flatMap(new Func1<List<RestorableDroppedDatabaseInner>, Observable<RestorableDroppedDatabaseInner>>() {
                @Override
                public Observable<RestorableDroppedDatabaseInner> call(List<RestorableDroppedDatabaseInner> restorableDroppedDatabaseInners) {
                    return Observable.from(restorableDroppedDatabaseInners);
                }
            })
            .map(new Func1<RestorableDroppedDatabaseInner, SqlRestorableDroppedDatabase>() {
                @Override
                public SqlRestorableDroppedDatabase call(RestorableDroppedDatabaseInner restorableDroppedDatabaseInner) {
                    return new SqlRestorableDroppedDatabaseImpl(self.resourceGroupName(), self.name(), restorableDroppedDatabaseInner, self.manager());
                }
            });
    }

    @Override
    public String version() {
        return this.inner().version();
    }

    @Override
    public SqlFirewallRule enableAccessFromAzureServices() {
        SqlFirewallRule firewallRule = this.manager().sqlServers().firewallRules()
                .getBySqlServer(this.resourceGroupName(), this.name(), "AllowAllWindowsAzureIps");
        if (firewallRule == null) {
            firewallRule = this.manager().sqlServers().firewallRules()
                .define("AllowAllWindowsAzureIps")
                .withExistingSqlServer(this.resourceGroupName(), this.name())
                .withIPAddress("0.0.0.0")
                .create();
        }

        return firewallRule;
    }

    @Override
    public void removeAccessFromAzureServices() {
        SqlFirewallRule firewallRule = this.manager().sqlServers().firewallRules()
            .getBySqlServer(this.resourceGroupName(), this.name(), "AllowAllWindowsAzureIps");
        if (firewallRule != null) {
            this.manager().sqlServers().firewallRules()
                .deleteBySqlServer(this.resourceGroupName(), this.name(), "AllowAllWindowsAzureIps");
        }
    }

    @Override
    public SqlActiveDirectoryAdministratorImpl setActiveDirectoryAdministrator(String userLogin, String objectId) {
        ServerAzureADAdministratorInner serverAzureADAdministratorInner = new ServerAzureADAdministratorInner()
            .withLogin(userLogin)
            .withSid(UUID.fromString(objectId))
            .withTenantId(UUID.fromString(this.manager().tenantId()));

        return new SqlActiveDirectoryAdministratorImpl(this.manager().inner().serverAzureADAdministrators().createOrUpdate(this.resourceGroupName(), this.name(), serverAzureADAdministratorInner));
    }

    @Override
    public SqlActiveDirectoryAdministratorImpl getActiveDirectoryAdministrator() {
        ServerAzureADAdministratorInner serverAzureADAdministratorInner = this.manager().inner().serverAzureADAdministrators().get(this.resourceGroupName(), this.name());

        return serverAzureADAdministratorInner != null ? new SqlActiveDirectoryAdministratorImpl(serverAzureADAdministratorInner) : null;
    }

    @Override
    public void removeActiveDirectoryAdministrator() {
        this.manager().inner().serverAzureADAdministrators().delete(this.resourceGroupName(), this.name());
    }

    @Override
    public SqlServerAutomaticTuning getServerAutomaticTuning() {
        ServerAutomaticTuningInner serverAutomaticTuningInner = this.manager().inner().serverAutomaticTunings()
            .get(this.resourceGroupName(), this.name());
        return serverAutomaticTuningInner != null ? new SqlServerAutomaticTuningImpl(this, serverAutomaticTuningInner) : null;
    }


    @Override
    public SqlFirewallRuleOperations.SqlFirewallRuleActionsDefinition firewallRules() {
        if (this.sqlFirewallRuleOperations == null) {
            this.sqlFirewallRuleOperations = new SqlFirewallRuleOperationsImpl(this, this.manager());
        }
        return this.sqlFirewallRuleOperations;
    }

    @Override
    public SqlVirtualNetworkRuleOperations.SqlVirtualNetworkRuleActionsDefinition virtualNetworkRules() {
        if (this.sqlVirtualNetworkRuleOperations == null) {
            this.sqlVirtualNetworkRuleOperations = new SqlVirtualNetworkRuleOperationsImpl(this, this.manager());
        }
        return this.sqlVirtualNetworkRuleOperations;
    }

    @Override
    public SqlServerImpl withAdministratorLogin(String administratorLogin) {
        this.inner().withAdministratorLogin(administratorLogin);
        return this;
    }

    @Override
    public SqlServerImpl withAdministratorPassword(String administratorLoginPassword) {
        this.inner().withAdministratorLoginPassword(administratorLoginPassword);
        return this;
    }

    @Override
    public SqlServerImpl withoutAccessFromAzureServices() {
        allowAzureServicesAccess = false;
        return this;
    }

    @Override
    public SqlServer.DefinitionStages.WithCreate withActiveDirectoryAdministrator(final String userLogin, final String objectId) {
        final SqlServerImpl self = this;
        sqlADAdminCreator = new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> call(final Context context) {
                ServerAzureADAdministratorInner serverAzureADAdministratorInner = new ServerAzureADAdministratorInner()
                    .withLogin(userLogin)
                    .withSid(UUID.fromString(objectId))
                    .withTenantId(UUID.fromString(self.manager().tenantId()));

                return self.manager().inner().serverAzureADAdministrators()
                    .createOrUpdateAsync(self.resourceGroupName(), self.name(), serverAzureADAdministratorInner)
                    .flatMap(new Func1<ServerAzureADAdministratorInner, Observable<Indexable>>() {
                        @Override
                        public Observable<Indexable> call(ServerAzureADAdministratorInner serverAzureADAdministratorInner) {
                            return context.voidObservable();
                        }
                    });
            }
        };
        return this;
    }

    @Override
    public SqlFirewallRuleImpl defineFirewallRule(String name) {
        return this.sqlFirewallRules.defineInlineFirewallRule(name);
    }

    @Override
    public SqlServerImpl withNewFirewallRule(String ipAddress) {
        return this.withNewFirewallRule(ipAddress, ipAddress);
    }

    @Override
    public SqlServerImpl withNewFirewallRule(String startIPAddress, String endIPAddress) {
        return this.withNewFirewallRule(startIPAddress, endIPAddress, SdkContext.randomResourceName("firewall_", 15));
    }

    @Override
    public SqlServerImpl withNewFirewallRule(String startIPAddress, String endIPAddress, String firewallRuleName) {
        return this.sqlFirewallRules
            .defineInlineFirewallRule(firewallRuleName)
            .withStartIPAddress(startIPAddress)
            .withEndIPAddress(endIPAddress)
            .attach();
    }

    @Override
    public SqlServerImpl withoutFirewallRule(String firewallRuleName) {
        sqlFirewallRules.removeInlineFirewallRule(firewallRuleName);
        return this;
    }

    @Override
    public SqlVirtualNetworkRule.DefinitionStages.Blank<SqlServer.DefinitionStages.WithCreate> defineVirtualNetworkRule(String virtualNetworkRuleName) {
        return this.sqlVirtualNetworkRules.defineInlineVirtualNetworkRule(virtualNetworkRuleName);
    }

    @Override
    public SqlElasticPoolOperations.SqlElasticPoolActionsDefinition elasticPools() {
        if (this.sqlElasticPoolOperations == null) {
            this.sqlElasticPoolOperations = new SqlElasticPoolOperationsImpl(this, this.manager());
        }
        return this.sqlElasticPoolOperations;
    }

    @Override
    public SqlDatabaseOperations.SqlDatabaseActionsDefinition databases() {
        if (this.sqlDatabaseOperations == null) {
            this.sqlDatabaseOperations = new SqlDatabaseOperationsImpl(this, this.manager());
        }
        return this.sqlDatabaseOperations;
    }

    @Override
    public SqlServerDnsAliasOperations.SqlServerDnsAliasActionsDefinition dnsAliases() {
        if (this.sqlServerDnsAliasOperations == null) {
            this.sqlServerDnsAliasOperations = new SqlServerDnsAliasOperationsImpl(this, this.manager());
        }
        return this.sqlServerDnsAliasOperations;
    }

    @Override
    public SqlFailoverGroupOperations.SqlFailoverGroupActionsDefinition failoverGroups() {
        if (this.sqlFailoverGroupOperations == null) {
            this.sqlFailoverGroupOperations = new SqlFailoverGroupOperationsImpl(this, this.manager());
        }
        return this.sqlFailoverGroupOperations;
    }

    @Override
    public SqlServerKeyOperations.SqlServerKeyActionsDefinition serverKeys() {
        if (this.sqlServerKeyOperations == null) {
            this.sqlServerKeyOperations = new SqlServerKeyOperationsImpl(this, this.manager());
        }
        return this.sqlServerKeyOperations;
    }

    @Override
    public SqlEncryptionProtectorOperations.SqlEncryptionProtectorActionsDefinition encryptionProtectors() {
        if (this.sqlEncryptionProtectorsOperations == null) {
            this.sqlEncryptionProtectorsOperations = new SqlEncryptionProtectorOperationsImpl(this, this.manager());
        }
        return this.sqlEncryptionProtectorsOperations;
    }

    @Override
    public SqlElasticPoolImpl defineElasticPool(String name) {
        return this.sqlElasticPools.defineInlineElasticPool(name);
    }

    @Override
    public SqlServerImpl withNewElasticPool(String elasticPoolName, ElasticPoolEditions elasticPoolEdition) {
        return this.sqlElasticPools
            .defineInlineElasticPool(elasticPoolName)
            .withEdition(elasticPoolEdition)
            .attach();
    }

    @Override
    public SqlServerImpl withoutElasticPool(String elasticPoolName) {
        sqlElasticPools.removeInlineElasticPool(elasticPoolName);
        return this;
    }

    @Override
    public SqlServerImpl withNewElasticPool(String elasticPoolName, ElasticPoolEditions elasticPoolEdition, String... databaseNames) {
        this.withNewElasticPool(elasticPoolName, elasticPoolEdition);
        for (String dbName : databaseNames) {
            this.defineDatabase(dbName)
                .withExistingElasticPool(elasticPoolName)
                .attach();
        }
        return this;
    }

    @Override
    public SqlDatabaseImpl defineDatabase(String name) {
        return this.sqlDatabases
            .defineInlineDatabase(name);
    }

    @Override
    public SqlServerImpl withNewDatabase(String databaseName) {
        return this.sqlDatabases
            .defineInlineDatabase(databaseName)
            .attach();
    }

    @Override
    public SqlServerImpl withoutDatabase(String databaseName) {
        this.sqlDatabases.removeInlineDatabase(databaseName);
        return this;
    }

    @Override
    public SqlServerImpl withSystemAssignedManagedServiceIdentity() {
        this.inner().withIdentity(new ResourceIdentity().withType(IdentityType.SYSTEM_ASSIGNED));
        return this;
    }
}
