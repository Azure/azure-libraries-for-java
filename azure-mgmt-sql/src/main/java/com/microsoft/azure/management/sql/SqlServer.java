/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.sql;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import com.microsoft.azure.management.sql.implementation.ServerInner;
import com.microsoft.azure.management.sql.implementation.SqlServerManager;
import rx.Observable;

import java.util.List;
import java.util.Map;

/**
 * An immutable client-side representation of an Azure SQL Server.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_7_0)
public interface SqlServer extends
        GroupableResource<SqlServerManager, ServerInner>,
        Refreshable<SqlServer>,
        Updatable<SqlServer.Update> {

    /**
     * @return fully qualified name of the SQL Server
     */
    String fullyQualifiedDomainName();

    /**
     * @return the administrator login user name for the SQL Server
     */
    String administratorLogin();

    /**
     * @return the SQL Server version
     */
    String version();

    /**
     * @return the SQL Server "kind"
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    String kind();

    /**
     * @return the state of the server.
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    String state();

    /**
     * @return true if Managed Service Identity is enabled for the SQL server
     */
    @Beta(Beta.SinceVersion.V1_8_0)
    boolean isManagedServiceIdentityEnabled();

    /**
     * @return the System Assigned (Local) Managed Service Identity specific Active Directory tenant ID assigned
     * to the SQL server.
     */
    @Beta(Beta.SinceVersion.V1_8_0)
    String systemAssignedManagedServiceIdentityTenantId();

    /**
     * @return the System Assigned (Local) Managed Service Identity specific Active Directory service principal ID
     * assigned to the SQL server.
     */
    @Beta(Beta.SinceVersion.V1_8_0)
    String systemAssignedManagedServiceIdentityPrincipalId();

    /**
     * @return the type of Managed Service Identity used for the SQL server.
     */
    @Beta(Beta.SinceVersion.V1_8_0)
    IdentityType managedServiceIdentityType();


    // Actions

    /**
     * @return returns the list of usages (ServerMetric) of Azure SQL Server
     */
    @Method
    @Deprecated
    List<ServerMetric> listUsages();

    /**
     * @return returns the list of usage metrics for an Azure SQL Server
     */
    @Method
    @Beta(Beta.SinceVersion.V1_7_0)
    List<ServerMetric> listUsageMetrics();

    /**
     * @return the list of information on all service objectives
     */
    @Method
    List<ServiceObjective> listServiceObjectives();

    /**
     * Gets the information on a particular Sql Server Service Objective.
     * @param serviceObjectiveName name of the service objective to be fetched
     * @return information of the service objective
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    ServiceObjective getServiceObjective(String serviceObjectiveName);

    /**
     * Returns all the recommended elastic pools for the server.
     *
     * @return list of recommended elastic pools for the server
     */
    @Method
    Map<String, RecommendedElasticPool> listRecommendedElasticPools();

    /**
     * @return the list of all restorable dropped databases
     */
    @Method
    List<SqlRestorableDroppedDatabase> listRestorableDroppedDatabases();

    /**
     * @return the list of all restorable dropped databases
     */
    @Method
    @Beta(Beta.SinceVersion.V1_7_0)
    Observable<SqlRestorableDroppedDatabase> listRestorableDroppedDatabasesAsync();

    /**
     * Sets the Azure services default access to this server to true.
     * <p>
     * A firewall rule named "AllowAllWindowsAzureIps" with the start IP "0.0.0.0" will be added
     *   to the SQL server if one does not exist.
     *
     * @return the SQL Firewall rule
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    @Method
    SqlFirewallRule enableAccessFromAzureServices();

    /**
     * Sets the Azure services default access to this server to false.
     * <p>
     * The firewall rule named "AllowAllWindowsAzureIps" will be removed from the SQL server.
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    @Method
    void removeAccessFromAzureServices();

    /**
     * Sets an Active Directory administrator to this server.
     * <p>
     * Azure Active Directory authentication allows you to centrally manage identity and access
     *   to your Azure SQL Database V12.
     *
     * @param userLogin the user or group login; it can be the name or the email address
     * @param id the user or group unique ID
     * @return a representation of a SQL Server Active Directory administrator object
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    @Method
    SqlActiveDirectoryAdministrator setActiveDirectoryAdministrator(String userLogin, String id);

    /**
     * Gets the Active Directory administrator for this server.
     *
     * @return a representation of a SQL Server Active Directory administrator object (null if one is not set)
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    @Method
    SqlActiveDirectoryAdministrator getActiveDirectoryAdministrator();

    /**
     * Removes the Active Directory administrator from this server.
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    @Method
    void removeActiveDirectoryAdministrator();

    /**
     * Gets a SQL server automatic tuning state and options.
     *
     * @return the SQL server automatic tuning state and options
     */
    @Method
    @Beta(Beta.SinceVersion.V1_8_0)
    SqlServerAutomaticTuning getServerAutomaticTuning();


    // Collections

    /**
     * @return returns entry point to manage SQL Firewall rules for this server.
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    SqlFirewallRuleOperations.SqlFirewallRuleActionsDefinition firewallRules();

    /**
     * @return returns entry point to manage SQL Virtual Network Rule for this server.
     */
    @Beta(Beta.SinceVersion.V1_8_0)
    SqlVirtualNetworkRuleOperations.SqlVirtualNetworkRuleActionsDefinition virtualNetworkRules();

    /**
     * @return returns entry point to manage the SQL Elastic Pools for this server.
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    SqlElasticPoolOperations.SqlElasticPoolActionsDefinition elasticPools();

    /**
     * @return entry point to manage Databases for this SQL server.
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    SqlDatabaseOperations.SqlDatabaseActionsDefinition databases();

    /**
     * @return returns entry point to manage SQL Server DNS aliases for this server.
     */
    @Beta(Beta.SinceVersion.V1_8_0)
    SqlServerDnsAliasOperations.SqlServerDnsAliasActionsDefinition dnsAliases();

    /**
     * @return returns entry point to manage SQL Failover Group for this server.
     */
    @Beta(Beta.SinceVersion.V1_8_0)
    SqlFailoverGroupOperations.SqlFailoverGroupActionsDefinition failoverGroups();

    /**
     * @return returns entry point to manage SQL Server Keys for this server.
     */
    @Beta(Beta.SinceVersion.V1_8_0)
    SqlServerKeyOperations.SqlServerKeyActionsDefinition serverKeys();

    /**
     * @return returns entry point to manage SQL Encryption Protector for this server.
     */
    @Beta(Beta.SinceVersion.V1_9_0)
    SqlEncryptionProtectorOperations.SqlEncryptionProtectorActionsDefinition encryptionProtectors();


    /**************************************************************
     * Fluent interfaces to provision a SqlServer
     **************************************************************/

    /**
     * Container interface for all the definitions that need to be implemented.
     */
    interface Definition extends
        DefinitionStages.Blank,
        DefinitionStages.WithGroup,
        DefinitionStages.WithAdministratorLogin,
        DefinitionStages.WithAdministratorPassword,
        DefinitionStages.WithElasticPool,
        DefinitionStages.WithDatabase,
        DefinitionStages.WithFirewallRule,
        DefinitionStages.WithCreate {
    }

    /**
     * Grouping of all the storage account definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of the SQL Server definition.
         */
        interface Blank extends DefinitionWithRegion<WithGroup> {
        }

        /**
         * A SQL Server definition allowing resource group to be set.
         */
        interface WithGroup extends GroupableResource.DefinitionStages.WithGroup<WithAdministratorLogin> {
        }

        /**
         * A SQL Server definition setting administrator user name.
         */
        interface WithAdministratorLogin {
            /**
             * Sets the administrator login user name.
             *
             * @param administratorLogin administrator login user name
             * @return Next stage of the SQL Server definition
             */
            WithAdministratorPassword withAdministratorLogin(String administratorLogin);
        }

        /**
         * A SQL Server definition setting admin user password.
         */
        interface WithAdministratorPassword {
            /**
             * Sets the administrator login password.
             *
             * @param administratorLoginPassword password for administrator login
             * @return Next stage of the SQL Server definition
             */
            WithCreate withAdministratorPassword(String administratorLoginPassword);
        }

        /**
         * A SQL Server definition setting the Active Directory administrator.
         */
        @Beta(Beta.SinceVersion.V1_7_0)
        interface WithActiveDirectoryAdministrator {
            /**
             * Sets the SQL Active Directory administrator.
             * <p>
             * Azure Active Directory authentication allows you to centrally manage identity and access
             *   to your Azure SQL Database V12.
             *
             * @param userLogin the user or group login; it can be the name or the email address
             * @param id the user or group unique ID
             * @return Next stage of the SQL Server definition
             */
            WithCreate withActiveDirectoryAdministrator(String userLogin, String id);
        }

        /**
         * A SQL Server definition setting the managed service identity.
         */
        @Beta(Beta.SinceVersion.V1_8_0)
        interface WithSystemAssignedManagedServiceIdentity {
            /**
             * Sets a system assigned (local) Managed Service Identity (MSI) for the SQL server resource.
             *
             * @return Next stage of the SQL Server definition
             */
            @Beta(Beta.SinceVersion.V1_8_0)
            @Method
            WithCreate withSystemAssignedManagedServiceIdentity();
        }

        /**
         * A SQL Server definition for specifying elastic pool.
         */
        interface WithElasticPool {
            /**
             * Begins the definition of a new SQL Elastic Pool to be added to this server.
             *
             * @param elasticPoolName the name of the new SQL Elastic Pool
             * @return the first stage of the new SQL Elastic Pool definition
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            SqlElasticPool.DefinitionStages.Blank<WithCreate> defineElasticPool(String elasticPoolName);

            /**
             * Creates new elastic pool in the SQL Server.
             * @param elasticPoolName name of the elastic pool to be created
             * @param elasticPoolEdition edition of the elastic pool
             * @param databaseNames names of the database to be included in the elastic pool
             * @return Next stage of the SQL Server definition
             */
            @Deprecated
            WithCreate withNewElasticPool(String elasticPoolName, ElasticPoolEditions elasticPoolEdition, String... databaseNames);

            /**
             * Creates new elastic pool in the SQL Server.
             * @param elasticPoolName name of the elastic pool to be created
             * @param elasticPoolEdition edition of the elastic pool
             * @return Next stage of the SQL Server definition
             */
            @Deprecated
            WithCreate withNewElasticPool(String elasticPoolName, ElasticPoolEditions elasticPoolEdition);
        }

        /**
         * A SQL Server definition for specifying the databases.
         */
        interface WithDatabase {
            /**
             * Begins the definition of a new SQL Database to be added to this server.
             *
             * @param databaseName the name of the new SQL Database
             * @return the first stage of the new SQL Database definition
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            SqlDatabase.DefinitionStages.Blank<WithCreate> defineDatabase(String databaseName);

            /**
             * Creates new database in the SQL Server.
             * @param databaseName name of the database to be created
             * @return Next stage of the SQL Server definition
             */
            @Deprecated
            WithCreate withNewDatabase(String databaseName);
        }

        /**
         * The stage of the SQL Server definition allowing to specify the SQL Firewall rules.
         */
        interface WithFirewallRule {
            /**
             * Sets the Azure services default access to this server to false.
             * <p>
             * The default is to allow Azure services default access to this server via a special
             *   firewall rule named "AllowAllWindowsAzureIps" with the start IP "0.0.0.0".
             *
             * @return Next stage of the SQL Server definition
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            WithCreate withoutAccessFromAzureServices();

            /**
             * Begins the definition of a new SQL Firewall rule to be added to this server.
             *
             * @param firewallRuleName the name of the new SQL Firewall rule
             * @return the first stage of the new SQL Firewall rule definition
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            SqlFirewallRule.DefinitionStages.Blank<WithCreate> defineFirewallRule(String firewallRuleName);

            /**
             * Creates new firewall rule in the SQL Server.
             *
             * @param ipAddress ipAddress for the firewall rule
             * @return Next stage of the SQL Server definition
             */
            @Deprecated
            WithCreate withNewFirewallRule(String ipAddress);

            /**
             * Creates new firewall rule in the SQL Server.
             *
             * @param startIPAddress start IP address for the firewall rule
             * @param endIPAddress end IP address for the firewall rule
             * @return Next stage of the SQL Server definition
             */
            @Deprecated
            WithCreate withNewFirewallRule(String startIPAddress, String endIPAddress);

            /**
             * Creates new firewall rule in the SQL Server.
             *
             * @param startIPAddress start IP address for the firewall rule
             * @param endIPAddress end IP address for the firewall rule
             * @param firewallRuleName name for the firewall rule
             * @return Next stage of the SQL Server definition
             */
            @Deprecated
            WithCreate withNewFirewallRule(String startIPAddress, String endIPAddress, String firewallRuleName);
        }

        /**
         * The stage of the SQL Server definition allowing to specify the SQL Virtual Network Rules.
         */
        @Beta(Beta.SinceVersion.V1_8_0)
        interface WithVirtualNetworkRule {
            /**
             * Begins the definition of a new SQL Virtual Network Rule to be added to this server.
             *
             * @param virtualNetworkRuleName the name of the new SQL Virtual Network Rule
             * @return the first stage of the new SQL Virtual Network Rule definition
             */
            @Beta(Beta.SinceVersion.V1_8_0)
            SqlVirtualNetworkRule.DefinitionStages.Blank<WithCreate> defineVirtualNetworkRule(String virtualNetworkRuleName);
        }

        /**
         * A SQL Server definition with sufficient inputs to create a new
         * SQL Server in the cloud, but exposing additional optional inputs to
         * specify.
         */
        @Beta(Beta.SinceVersion.V1_7_0)
        interface WithCreate extends
            Creatable<SqlServer>,
            WithActiveDirectoryAdministrator,
            WithSystemAssignedManagedServiceIdentity,
            WithElasticPool,
            WithDatabase,
            WithFirewallRule,
            WithVirtualNetworkRule,
            DefinitionWithTags<WithCreate> {
        }
    }

    /**
     * The template for a SQLServer update operation, containing all the settings that can be modified.
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    interface Update extends
            Appliable<SqlServer>,
            UpdateStages.WithAdministratorPassword,
            UpdateStages.WithElasticPool,
            UpdateStages.WithDatabase,
            UpdateStages.WithFirewallRule,
            UpdateStages.WithSystemAssignedManagedServiceIdentity,
            Resource.UpdateWithTags<Update> {
    }

    /**
     * Grouping of all the SQLServer update stages.
     */
    interface UpdateStages {
        /**
         * A SQL Server update stage setting admin user password.
         */
        interface WithAdministratorPassword {
            /**
             * Sets the administrator login password.
             *
             * @param administratorLoginPassword password for administrator login
             * @return Next stage of the update.
             */
            Update withAdministratorPassword(String administratorLoginPassword);
        }

        /**
         * A SQL Server definition setting the managed service identity.
         */
        @Beta(Beta.SinceVersion.V1_8_0)
        interface WithSystemAssignedManagedServiceIdentity {
            /**
             * Sets a system assigned (local) Managed Service Identity (MSI) for the SQL server resource.
             *
             * @return Next stage of the SQL Server definition
             */
            @Beta(Beta.SinceVersion.V1_8_0)
            @Method
            Update withSystemAssignedManagedServiceIdentity();
        }


        /**
         * A SQL Server definition for specifying elastic pool.
         */
        interface WithElasticPool {
            /**
             * Create new elastic pool in the SQL Server.
             * @param elasticPoolName name of the elastic pool to be created
             * @param elasticPoolEdition edition of the elastic pool
             * @param databaseNames names of the database to be included in the elastic pool
             * @return Next stage of the SQL Server update
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            @Deprecated
            Update withNewElasticPool(String elasticPoolName, ElasticPoolEditions elasticPoolEdition, String... databaseNames);

            /**
             * Create new elastic pool in the SQL Server.
             * @param elasticPoolName name of the elastic pool to be created
             * @param elasticPoolEdition edition of the elastic pool
             * @return Next stage of the SQL Server update
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            @Deprecated
            Update withNewElasticPool(String elasticPoolName, ElasticPoolEditions elasticPoolEdition);

            /**
             * Removes elastic pool from the SQL Server.
             * @param elasticPoolName name of the elastic pool to be removed
             * @return Next stage of the SQL Server update
             */
            @Deprecated
            Update withoutElasticPool(String elasticPoolName);
        }

        /**
         * A SQL Server definition for specifying the databases.
         */
        interface WithDatabase {
            /**
             * Create new database in the SQL Server.
             * @param databaseName name of the database to be created
             * @return Next stage of the SQL Server update
             */
            @Deprecated
            Update withNewDatabase(String databaseName);

            /**
             * Remove database from the SQL Server.
             * @param databaseName name of the database to be removed
             * @return Next stage of the SQL Server update
             */
            @Deprecated
            Update withoutDatabase(String databaseName);
        }

        /**
         * The stage of the SQL Server update definition allowing to specify the SQL Firewall rules.
         */
        interface WithFirewallRule {
            /**
             * Create new firewall rule in the SQL Server.
             *
             * @param ipAddress IP address for the firewall rule
             * @return Next stage of the SQL Server update
             */
            @Deprecated
            Update withNewFirewallRule(String ipAddress);

            /**
             * Create new firewall rule in the SQL Server.
             *
             * @param startIPAddress Start IP address for the firewall rule
             * @param endIPAddress IP address for the firewall rule
             * @return Next stage of the SQL Server update
             */
            @Deprecated
            Update withNewFirewallRule(String startIPAddress, String endIPAddress);

            /**
             * Creates new firewall rule in the SQL Server.
             *
             * @param startIPAddress start IP address for the firewall rule
             * @param endIPAddress end IP address for the firewall rule
             * @param firewallRuleName name for the firewall rule
             * @return Next stage of the SQL Server update
             */
            @Deprecated
            Update withNewFirewallRule(String startIPAddress, String endIPAddress, String firewallRuleName);

            /**
             * Removes firewall rule from the SQL Server.
             *
             * @param firewallRuleName name of the firewall rule to be removed
             * @return Next stage of the SQL Server update
             */
            @Deprecated
            Update withoutFirewallRule(String firewallRuleName);
        }
    }
}

