/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.sql;

import com.microsoft.azure.management.resources.core.TestUtilities;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import com.microsoft.azure.management.sql.implementation.SyncGroupInner;
import com.microsoft.azure.management.storage.StorageAccount;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlServerOperationsTests extends SqlServerTest {
    private static final String SQL_DATABASE_NAME = "myTestDatabase2";
    private static final String COLLATION = "SQL_Latin1_General_CP1_CI_AS";
    private static final String SQL_ELASTIC_POOL_NAME = "testElasticPool";
    private static final String SQL_FIREWALLRULE_NAME = "firewallrule1";
    private static final String START_IPADDRESS = "10.102.1.10";
    private static final String END_IPADDRESS = "10.102.1.12";

    @Test
    public void canCRUDSqlSyncMember() throws Exception {
        String rgName = RG_NAME;
        String sqlServerName = SQL_SERVER_NAME;
        final String dbName = "dbSample";
        final String dbSyncName = "dbSync";
        final String dbMemberName = "dbMember";
        final String syncGroupName = "groupName";
        final String syncMemberName = "memberName";
        final String administratorLogin = "sqladmin";
        final String administratorPassword = "N0t@P@ssw0rd!";

        // Create
        SqlServer sqlPrimaryServer = sqlServerManager.sqlServers().define(sqlServerName)
            .withRegion(Region.US_SOUTH_CENTRAL)
            .withNewResourceGroup(rgName)
            .withAdministratorLogin(administratorLogin)
            .withAdministratorPassword(administratorPassword)
            .defineDatabase(dbName)
                .fromSample(SampleName.ADVENTURE_WORKS_LT)
                .withStandardEdition(SqlDatabaseStandardServiceObjective.S0)
                .attach()
            .defineDatabase(dbSyncName)
                .withStandardEdition(SqlDatabaseStandardServiceObjective.S0)
                .attach()
            .defineDatabase(dbMemberName)
                .withStandardEdition(SqlDatabaseStandardServiceObjective.S0)
                .attach()
            .create();

        SqlDatabase dbSource = sqlPrimaryServer.databases().get(dbName);
        SqlDatabase dbSync = sqlPrimaryServer.databases().get(dbSyncName);
        SqlDatabase dbMember = sqlPrimaryServer.databases().get(dbMemberName);

        SqlSyncGroup sqlSyncGroup = dbSync.syncGroups().define(syncGroupName)
            .withSyncDatabaseId(dbSource.id())
            .withDatabaseUserName(administratorLogin)
            .withDatabasePassword(administratorPassword)
            .withConflictResolutionPolicyHubWins()
            .withInterval(-1)
            .create();
        Assert.assertNotNull(sqlSyncGroup);

        SqlSyncMember sqlSyncMember = sqlSyncGroup.syncMembers().define(syncMemberName)
            .withMemberSqlDatabase(dbMember)
            .withMemberUserName(administratorLogin)
            .withMemberPassword(administratorPassword)
            .withMemberDatabaseType(SyncMemberDbType.AZURE_SQL_DATABASE)
            .withDatabaseType(SyncDirection.ONE_WAY_MEMBER_TO_HUB)
            .create();
        Assert.assertNotNull(sqlSyncMember);

        sqlSyncMember.update()
            .withDatabaseType(SyncDirection.BIDIRECTIONAL)
            .withMemberUserName(administratorLogin)
            .withMemberPassword(administratorPassword)
            .withMemberDatabaseType(SyncMemberDbType.AZURE_SQL_DATABASE)
            .apply();

        Assert.assertFalse(sqlSyncGroup.syncMembers().list().isEmpty());

        sqlSyncMember = sqlServerManager.sqlServers().syncMembers().getBySqlServer(rgName, sqlServerName, dbSyncName, syncGroupName, syncMemberName);
        Assert.assertNotNull(sqlSyncMember);

        sqlSyncMember.delete();

        sqlSyncGroup.delete();

    }

    @Test
    public void canCRUDSqlSyncGroup() throws Exception {
        String rgName = RG_NAME;
        String sqlServerName = SQL_SERVER_NAME;
        final String dbName = "dbSample";
        final String dbSyncName = "dbSync";
        final String syncGroupName = "groupName";
        final String administratorLogin = "sqladmin";
        final String administratorPassword = "N0t@P@ssw0rd!";

        // Create
        SqlServer sqlPrimaryServer = sqlServerManager.sqlServers().define(sqlServerName)
            .withRegion(Region.US_WEST2)
            .withNewResourceGroup(rgName)
            .withAdministratorLogin(administratorLogin)
            .withAdministratorPassword(administratorPassword)
            .defineDatabase(dbName)
                .fromSample(SampleName.ADVENTURE_WORKS_LT)
                .withStandardEdition(SqlDatabaseStandardServiceObjective.S0)
                .attach()
            .defineDatabase(dbSyncName)
                .withStandardEdition(SqlDatabaseStandardServiceObjective.S0)
                .attach()
            .create();

        SqlDatabase dbSource = sqlPrimaryServer.databases().get(dbName);
        SqlDatabase dbSync = sqlPrimaryServer.databases().get(dbSyncName);

        SqlSyncGroup sqlSyncGroup = dbSync.syncGroups().define(syncGroupName)
            .withSyncDatabaseId(dbSource.id())
            .withDatabaseUserName(administratorLogin)
            .withDatabasePassword(administratorPassword)
            .withConflictResolutionPolicyHubWins()
            .withInterval(-1)
            .create();

        Assert.assertNotNull(sqlSyncGroup);

        sqlSyncGroup.update()
            .withInterval(600)
            .withConflictResolutionPolicyMemberWins()
            .apply();

        Assert.assertFalse(sqlServerManager.sqlServers().syncGroups().listSyncDatabaseIds(Region.US_WEST2).isEmpty());
        Assert.assertFalse(dbSync.syncGroups().list().isEmpty());

        sqlSyncGroup = sqlServerManager.sqlServers().syncGroups().getBySqlServer(rgName, sqlServerName, dbSyncName, syncGroupName);
        Assert.assertNotNull(sqlSyncGroup);

        sqlSyncGroup.delete();

    }

    @Test
    public void canCopySqlDatabase() throws Exception {
        String rgName = RG_NAME;
        final String sqlPrimaryServerName = SdkContext.randomResourceName("sqlpri", 22);
        final String sqlSecondaryServerName = SdkContext.randomResourceName("sqlsec", 22);
        final String epName = "epSample";
        final String dbName = "dbSample";
        final String administratorLogin = "sqladmin";
        final String administratorPassword = "N0t@P@ssw0rd!";

        // Create
        SqlServer sqlPrimaryServer = sqlServerManager.sqlServers().define(sqlPrimaryServerName)
            .withRegion(Region.US_WEST2)
            .withNewResourceGroup(rgName)
            .withAdministratorLogin(administratorLogin)
            .withAdministratorPassword(administratorPassword)
            .defineElasticPool(epName)
                .withPremiumPool()
                .attach()
            .defineDatabase(dbName)
                .withExistingElasticPool(epName)
                .fromSample(SampleName.ADVENTURE_WORKS_LT)
                .attach()
            .create();

        SqlServer sqlSecondaryServer = sqlServerManager.sqlServers().define(sqlSecondaryServerName)
            .withRegion(Region.US_WEST)
            .withExistingResourceGroup(rgName)
            .withAdministratorLogin(administratorLogin)
            .withAdministratorPassword(administratorPassword)
            .create();

        SqlDatabase dbSample = sqlPrimaryServer.databases().get(dbName);

        SqlDatabase dbCopy = sqlSecondaryServer.databases()
            .define("dbCopy")
            .withSourceDatabase(dbSample)
            .withMode(CreateMode.COPY)
            .withServiceObjective(ServiceObjectiveName.P1)
            .create();

        Assert.assertNotNull(dbCopy);

    }

    @Test
    public void canCRUDSqlFailoverGroup() throws Exception {
        String rgName = RG_NAME;
        final String sqlPrimaryServerName = SdkContext.randomResourceName("sqlpri", 22);
        final String sqlSecondaryServerName = SdkContext.randomResourceName("sqlsec", 22);
        final String sqlOtherServerName = SdkContext.randomResourceName("sql000", 22);
        final String failoverGroupName = SdkContext.randomResourceName("fg", 22);
        final String failoverGroupName2 = SdkContext.randomResourceName("fg2", 22);
        final String dbName = "dbSample";
        final String administratorLogin = "sqladmin";
        final String administratorPassword = "N0t@P@ssw0rd!";

        // Create
        SqlServer sqlPrimaryServer = sqlServerManager.sqlServers().define(sqlPrimaryServerName)
            .withRegion(Region.US_EAST)
            .withNewResourceGroup(rgName)
            .withAdministratorLogin(administratorLogin)
            .withAdministratorPassword(administratorPassword)
            .defineDatabase(dbName)
                .fromSample(SampleName.ADVENTURE_WORKS_LT)
                .withStandardEdition(SqlDatabaseStandardServiceObjective.S0)
                .attach()
            .create();

        SqlServer sqlSecondaryServer = sqlServerManager.sqlServers().define(sqlSecondaryServerName)
            .withRegion(Region.US_WEST)
            .withExistingResourceGroup(rgName)
            .withAdministratorLogin(administratorLogin)
            .withAdministratorPassword(administratorPassword)
            .create();

        SqlServer sqlOtherServer = sqlServerManager.sqlServers().define(sqlOtherServerName)
            .withRegion(Region.US_CENTRAL)
            .withExistingResourceGroup(rgName)
            .withAdministratorLogin(administratorLogin)
            .withAdministratorPassword(administratorPassword)
            .create();

        SqlFailoverGroup failoverGroup = sqlPrimaryServer.failoverGroups().define(failoverGroupName)
            .withManualReadWriteEndpointPolicy()
            .withPartnerServerId(sqlSecondaryServer.id())
            .withReadOnlyEndpointPolicyDisabled()
            .create();
        Assert.assertNotNull(failoverGroup);
        Assert.assertEquals(failoverGroupName, failoverGroup.name());
        Assert.assertEquals(rgName, failoverGroup.resourceGroupName());
        Assert.assertEquals(sqlPrimaryServerName, failoverGroup.sqlServerName());
        Assert.assertEquals(FailoverGroupReplicationRole.PRIMARY, failoverGroup.replicationRole());
        Assert.assertEquals(1, failoverGroup.partnerServers().size());
        Assert.assertEquals(sqlSecondaryServer.id(), failoverGroup.partnerServers().get(0).id());
        Assert.assertEquals(FailoverGroupReplicationRole.SECONDARY, failoverGroup.partnerServers().get(0).replicationRole());
        Assert.assertEquals(0, failoverGroup.databases().size());
        Assert.assertEquals(0, failoverGroup.readWriteEndpointDataLossGracePeriodMinutes());
        Assert.assertEquals(ReadWriteEndpointFailoverPolicy.MANUAL, failoverGroup.readWriteEndpointPolicy());
        Assert.assertEquals(ReadOnlyEndpointFailoverPolicy.DISABLED, failoverGroup.readOnlyEndpointPolicy());

        SqlFailoverGroup failoverGroupOnPartner = sqlSecondaryServer.failoverGroups().get(failoverGroup.name());
        Assert.assertEquals(failoverGroupName, failoverGroupOnPartner.name());
        Assert.assertEquals(rgName, failoverGroupOnPartner.resourceGroupName());
        Assert.assertEquals(sqlSecondaryServerName, failoverGroupOnPartner.sqlServerName());
        Assert.assertEquals(FailoverGroupReplicationRole.SECONDARY, failoverGroupOnPartner.replicationRole());
        Assert.assertEquals(1, failoverGroupOnPartner.partnerServers().size());
        Assert.assertEquals(sqlPrimaryServer.id(), failoverGroupOnPartner.partnerServers().get(0).id());
        Assert.assertEquals(FailoverGroupReplicationRole.PRIMARY, failoverGroupOnPartner.partnerServers().get(0).replicationRole());
        Assert.assertEquals(0, failoverGroupOnPartner.databases().size());
        Assert.assertEquals(0, failoverGroupOnPartner.readWriteEndpointDataLossGracePeriodMinutes());
        Assert.assertEquals(ReadWriteEndpointFailoverPolicy.MANUAL, failoverGroupOnPartner.readWriteEndpointPolicy());
        Assert.assertEquals(ReadOnlyEndpointFailoverPolicy.DISABLED, failoverGroupOnPartner.readOnlyEndpointPolicy());

        SqlFailoverGroup failoverGroup2 = sqlPrimaryServer.failoverGroups().define(failoverGroupName2)
            .withAutomaticReadWriteEndpointPolicyAndDataLossGracePeriod(120)
            .withPartnerServerId(sqlOtherServer.id())
            .withReadOnlyEndpointPolicyEnabled()
            .create();
        Assert.assertNotNull(failoverGroup2);
        Assert.assertEquals(failoverGroupName2, failoverGroup2.name());
        Assert.assertEquals(rgName, failoverGroup2.resourceGroupName());
        Assert.assertEquals(sqlPrimaryServerName, failoverGroup2.sqlServerName());
        Assert.assertEquals(FailoverGroupReplicationRole.PRIMARY, failoverGroup2.replicationRole());
        Assert.assertEquals(1, failoverGroup2.partnerServers().size());
        Assert.assertEquals(sqlOtherServer.id(), failoverGroup2.partnerServers().get(0).id());
        Assert.assertEquals(FailoverGroupReplicationRole.SECONDARY, failoverGroup2.partnerServers().get(0).replicationRole());
        Assert.assertEquals(0, failoverGroup2.databases().size());
        Assert.assertEquals(120, failoverGroup2.readWriteEndpointDataLossGracePeriodMinutes());
        Assert.assertEquals(ReadWriteEndpointFailoverPolicy.AUTOMATIC, failoverGroup2.readWriteEndpointPolicy());
        Assert.assertEquals(ReadOnlyEndpointFailoverPolicy.ENABLED, failoverGroup2.readOnlyEndpointPolicy());

        failoverGroup.update()
            .withAutomaticReadWriteEndpointPolicyAndDataLossGracePeriod(120)
            .withReadOnlyEndpointPolicyEnabled()
            .withTag("tag1", "value1")
            .apply();
        Assert.assertEquals(120, failoverGroup.readWriteEndpointDataLossGracePeriodMinutes());
        Assert.assertEquals(ReadWriteEndpointFailoverPolicy.AUTOMATIC, failoverGroup.readWriteEndpointPolicy());
        Assert.assertEquals(ReadOnlyEndpointFailoverPolicy.ENABLED, failoverGroup.readOnlyEndpointPolicy());

        SqlDatabase db = sqlPrimaryServer.databases().get(dbName);
        failoverGroup.update()
            .withManualReadWriteEndpointPolicy()
            .withReadOnlyEndpointPolicyDisabled()
            .withNewDatabaseId(db.id())
            .apply();
        Assert.assertEquals(1, failoverGroup.databases().size());
        Assert.assertEquals(db.id(), failoverGroup.databases().get(0));
        Assert.assertEquals(0, failoverGroup.readWriteEndpointDataLossGracePeriodMinutes());
        Assert.assertEquals(ReadWriteEndpointFailoverPolicy.MANUAL, failoverGroup.readWriteEndpointPolicy());
        Assert.assertEquals(ReadOnlyEndpointFailoverPolicy.DISABLED, failoverGroup.readOnlyEndpointPolicy());

        List<SqlFailoverGroup> failoverGroupsList = sqlPrimaryServer.failoverGroups().list();
        Assert.assertEquals(2, failoverGroupsList.size());

        failoverGroupsList = sqlSecondaryServer.failoverGroups().list();
        Assert.assertEquals(1, failoverGroupsList.size());

        sqlPrimaryServer.failoverGroups().delete(failoverGroup2.name());
    }

    @Test
    public void canChangeSqlServerAndDatabaseAutomaticTuning() throws Exception {
        String rgName = RG_NAME;
        String sqlServerName = SQL_SERVER_NAME;
        String sqlServerAdminName = "sqladmin";
        String sqlServerAdminPassword = "N0t@P@ssw0rd!";
        String databaseName = "db-from-sample";
        String id = SdkContext.randomUuid();
        String storageName = SdkContext.randomResourceName(SQL_SERVER_NAME, 22);

        // Create
        SqlServer sqlServer = sqlServerManager
            .sqlServers()
            .define(sqlServerName)
            .withRegion(Region.US_EAST)
            .withNewResourceGroup(rgName)
            .withAdministratorLogin(sqlServerAdminName)
            .withAdministratorPassword(sqlServerAdminPassword)
            .defineDatabase(databaseName)
                .fromSample(SampleName.ADVENTURE_WORKS_LT)
                .withBasicEdition()
                .attach()
            .create();
        SqlDatabase dbFromSample = sqlServer.databases().get(databaseName);
        Assert.assertNotNull(dbFromSample);
        Assert.assertEquals(DatabaseEditions.BASIC, dbFromSample.edition());

        SqlServerAutomaticTuning serverAutomaticTuning = sqlServer.getServerAutomaticTuning();
        Assert.assertEquals(AutomaticTuningServerMode.UNSPECIFIED, serverAutomaticTuning.desiredState());
        Assert.assertEquals(AutomaticTuningServerMode.UNSPECIFIED, serverAutomaticTuning.actualState());
        Assert.assertEquals(4, serverAutomaticTuning.tuningOptions().size());

        serverAutomaticTuning.update()
            .withAutomaticTuningMode(AutomaticTuningServerMode.AUTO)
            .withAutomaticTuningOption("createIndex", AutomaticTuningOptionModeDesired.OFF)
            .withAutomaticTuningOption("dropIndex", AutomaticTuningOptionModeDesired.ON)
            .withAutomaticTuningOption("forceLastGoodPlan", AutomaticTuningOptionModeDesired.DEFAULT)
            .apply();
        Assert.assertEquals(AutomaticTuningServerMode.AUTO, serverAutomaticTuning.desiredState());
        Assert.assertEquals(AutomaticTuningServerMode.AUTO, serverAutomaticTuning.actualState());
        Assert.assertEquals(AutomaticTuningOptionModeDesired.OFF, serverAutomaticTuning.tuningOptions().get("createIndex").desiredState());
        Assert.assertEquals(AutomaticTuningOptionModeActual.OFF, serverAutomaticTuning.tuningOptions().get("createIndex").actualState());
        Assert.assertEquals(AutomaticTuningOptionModeDesired.ON, serverAutomaticTuning.tuningOptions().get("dropIndex").desiredState());
        Assert.assertEquals(AutomaticTuningOptionModeActual.ON, serverAutomaticTuning.tuningOptions().get("dropIndex").actualState());
        Assert.assertEquals(AutomaticTuningOptionModeDesired.DEFAULT, serverAutomaticTuning.tuningOptions().get("forceLastGoodPlan").desiredState());

        SqlDatabaseAutomaticTuning databaseAutomaticTuning = dbFromSample.getDatabaseAutomaticTuning();
        Assert.assertEquals(4, databaseAutomaticTuning.tuningOptions().size());

        // The following results in "InternalServerError" at the moment
        databaseAutomaticTuning.update()
            .withAutomaticTuningMode(AutomaticTuningMode.AUTO)
            .withAutomaticTuningOption("createIndex", AutomaticTuningOptionModeDesired.OFF)
            .withAutomaticTuningOption("dropIndex", AutomaticTuningOptionModeDesired.ON)
            .withAutomaticTuningOption("forceLastGoodPlan", AutomaticTuningOptionModeDesired.DEFAULT)
            .apply();
        Assert.assertEquals(AutomaticTuningMode.AUTO, databaseAutomaticTuning.desiredState());
        Assert.assertEquals(AutomaticTuningMode.AUTO, databaseAutomaticTuning.actualState());
        Assert.assertEquals(AutomaticTuningOptionModeDesired.OFF, databaseAutomaticTuning.tuningOptions().get("createIndex").desiredState());
        Assert.assertEquals(AutomaticTuningOptionModeActual.OFF, databaseAutomaticTuning.tuningOptions().get("createIndex").actualState());
        Assert.assertEquals(AutomaticTuningOptionModeDesired.ON, databaseAutomaticTuning.tuningOptions().get("dropIndex").desiredState());
        Assert.assertEquals(AutomaticTuningOptionModeActual.ON, databaseAutomaticTuning.tuningOptions().get("dropIndex").actualState());
        Assert.assertEquals(AutomaticTuningOptionModeDesired.DEFAULT, databaseAutomaticTuning.tuningOptions().get("forceLastGoodPlan").desiredState());

        // cleanup
        dbFromSample.delete();
        sqlServerManager.sqlServers().deleteByResourceGroup(rgName, sqlServerName);
    }

    @Test
    public void canCreateAndAquireServerDnsAlias () throws Exception {
        String rgName = RG_NAME;
        String sqlServerName1 = SQL_SERVER_NAME + "1";
        String sqlServerName2 = SQL_SERVER_NAME + "2";
        String sqlServerAdminName = "sqladmin";
        String sqlServerAdminPassword = "N0t@P@ssw0rd!";

        // Create
        SqlServer sqlServer1 = sqlServerManager.sqlServers().define(sqlServerName1)
            .withRegion(Region.US_EAST)
            .withNewResourceGroup(rgName)
            .withAdministratorLogin(sqlServerAdminName)
            .withAdministratorPassword(sqlServerAdminPassword)
            .create();
        Assert.assertNotNull(sqlServer1);

        SqlServerDnsAlias dnsAlias = sqlServer1.dnsAliases()
            .define(SQL_SERVER_NAME)
            .create();

        Assert.assertNotNull(dnsAlias);
        Assert.assertEquals(rgName, dnsAlias.resourceGroupName());
        Assert.assertEquals(sqlServerName1, dnsAlias.sqlServerName());

        dnsAlias = sqlServerManager.sqlServers().dnsAliases()
            .getBySqlServer(rgName, sqlServerName1, SQL_SERVER_NAME);
        Assert.assertNotNull(dnsAlias);
        Assert.assertEquals(rgName, dnsAlias.resourceGroupName());
        Assert.assertEquals(sqlServerName1, dnsAlias.sqlServerName());

        Assert.assertEquals(1, sqlServer1.databases().list().size());

        SqlServer sqlServer2 = sqlServerManager.sqlServers().define(sqlServerName2)
            .withRegion(Region.US_EAST)
            .withNewResourceGroup(rgName)
            .withAdministratorLogin(sqlServerAdminName)
            .withAdministratorPassword(sqlServerAdminPassword)
            .create();
        Assert.assertNotNull(sqlServer2);

        sqlServer2.dnsAliases().acquire(SQL_SERVER_NAME, sqlServer1.id());
        SdkContext.sleep(3 * 60 * 1000);

        dnsAlias = sqlServer2.dnsAliases().get(SQL_SERVER_NAME);
        Assert.assertNotNull(dnsAlias);
        Assert.assertEquals(rgName, dnsAlias.resourceGroupName());
        Assert.assertEquals(sqlServerName2, dnsAlias.sqlServerName());

        // cleanup
        dnsAlias.delete();

        sqlServerManager.sqlServers().deleteByResourceGroup(rgName, sqlServerName1);
        sqlServerManager.sqlServers().deleteByResourceGroup(rgName, sqlServerName2);
    }

    @Test
    public void canGetSqlServerCapabilitiesAndCreateIdentity () throws Exception {
        String rgName = RG_NAME;
        String sqlServerName = SQL_SERVER_NAME;
        String sqlServerAdminName = "sqladmin";
        String sqlServerAdminPassword = "N0t@P@ssw0rd!";
        String databaseName = "db-from-sample";

        RegionCapabilities regionCapabilities = sqlServerManager.sqlServers().getCapabilitiesByRegion(Region.US_EAST);
        Assert.assertNotNull(regionCapabilities);
        Assert.assertNotNull(regionCapabilities.supportedCapabilitiesByServerVersion().get("12.0"));
        Assert.assertTrue(regionCapabilities.supportedCapabilitiesByServerVersion().get("12.0").supportedEditions().size() > 0);
        Assert.assertTrue(regionCapabilities.supportedCapabilitiesByServerVersion().get("12.0").supportedElasticPoolEditions().size() > 0);

        // Create
        SqlServer sqlServer = sqlServerManager
            .sqlServers()
            .define(sqlServerName)
            .withRegion(Region.US_EAST)
            .withNewResourceGroup(rgName)
            .withAdministratorLogin(sqlServerAdminName)
            .withAdministratorPassword(sqlServerAdminPassword)
            .withSystemAssignedManagedServiceIdentity()
            .defineDatabase(databaseName)
                .fromSample(SampleName.ADVENTURE_WORKS_LT)
                .withBasicEdition()
                .attach()
            .create();
        SqlDatabase dbFromSample = sqlServer.databases().get(databaseName);
        Assert.assertNotNull(dbFromSample);
        Assert.assertEquals(DatabaseEditions.BASIC, dbFromSample.edition());

        Assert.assertTrue(sqlServer.isManagedServiceIdentityEnabled());
        Assert.assertEquals(sqlServerManager.tenantId(), sqlServer.systemAssignedManagedServiceIdentityTenantId());
        Assert.assertNotNull(sqlServer.systemAssignedManagedServiceIdentityPrincipalId());

        sqlServer.update()
            .withSystemAssignedManagedServiceIdentity()
            .apply();
        Assert.assertTrue(sqlServer.isManagedServiceIdentityEnabled());
        Assert.assertEquals(sqlServerManager.tenantId(), sqlServer.systemAssignedManagedServiceIdentityTenantId());
        Assert.assertNotNull(sqlServer.systemAssignedManagedServiceIdentityPrincipalId());


        // cleanup
        dbFromSample.delete();
        sqlServerManager.sqlServers().deleteByResourceGroup(rgName, sqlServerName);
    }

    @Test
    public void canCRUDSqlServerWithImportDatabase() throws Exception {
        if (isPlaybackMode()) {
            // The test makes calls to the Azure Storage data plane APIs which are not mocked at this time.
            return;
        }
        // Create

        String rgName = RG_NAME;
        String sqlServerName = SQL_SERVER_NAME;
        String sqlServerAdminName = "sqladmin";
        String sqlServerAdminPassword = "N0t@P@ssw0rd!";
        String id = SdkContext.randomUuid();
        String storageName = SdkContext.randomResourceName(SQL_SERVER_NAME, 22);

        SqlServer sqlServer = sqlServerManager
            .sqlServers()
            .define(sqlServerName)
                .withRegion(Region.US_EAST)
                .withNewResourceGroup(rgName)
                .withAdministratorLogin(sqlServerAdminName)
                .withAdministratorPassword(sqlServerAdminPassword)
                .withActiveDirectoryAdministrator("DSEng", id)
                .create();

        SqlDatabase dbFromSample = sqlServer.databases().define("db-from-sample")
            .fromSample(SampleName.ADVENTURE_WORKS_LT)
            .withBasicEdition()
            .withTag("tag1", "value1")
            .create();
        Assert.assertNotNull(dbFromSample);
        Assert.assertEquals(DatabaseEditions.BASIC, dbFromSample.edition());

        SqlDatabaseImportExportResponse exportedDB;
        StorageAccount storageAccount = storageManager.storageAccounts().getByResourceGroup(sqlServer.resourceGroupName(), storageName);
        if (storageAccount == null) {
            Creatable<StorageAccount> storageAccountCreatable = storageManager.storageAccounts()
                .define(storageName)
                .withRegion(sqlServer.regionName())
                .withExistingResourceGroup(sqlServer.resourceGroupName());

            exportedDB = dbFromSample.exportTo(storageAccountCreatable, "from-sample", "dbfromsample.bacpac")
                .withSqlAdministratorLoginAndPassword(sqlServerAdminName, sqlServerAdminPassword)
                .execute();
            storageAccount = storageManager.storageAccounts().getByResourceGroup(sqlServer.resourceGroupName(), storageName);
        } else {
            exportedDB = dbFromSample.exportTo(storageAccount, "from-sample", "dbfromsample.bacpac")
                .withSqlAdministratorLoginAndPassword(sqlServerAdminName, sqlServerAdminPassword)
                .execute();
        }

        SqlDatabase dbFromImport = sqlServer.databases().define("db-from-import")
            .defineElasticPool("ep1")
                .withBasicPool()
                .attach()
            .importFrom(storageAccount, "from-sample", "dbfromsample.bacpac")
            .withSqlAdministratorLoginAndPassword(sqlServerAdminName, sqlServerAdminPassword)
            .withTag("tag2", "value2")
            .create();
        Assert.assertNotNull(dbFromImport);
        Assert.assertEquals("ep1", dbFromImport.elasticPoolName());

        dbFromImport.delete();
        dbFromSample.delete();
        sqlServer.elasticPools().delete("ep1");
        sqlServerManager.sqlServers().deleteByResourceGroup(rgName, sqlServerName);
    }

    @Test
    public void canCRUDSqlServerWithFirewallRule() throws Exception {
        // Create

        String rgName = RG_NAME;
        String sqlServerName = SQL_SERVER_NAME;
        String sqlServerAdminName = "sqladmin";
        String id = SdkContext.randomUuid();

        SqlServer sqlServer = sqlServerManager
            .sqlServers()
                .define(SQL_SERVER_NAME)
                    .withRegion(Region.US_CENTRAL)
                    .withNewResourceGroup(RG_NAME)
                    .withAdministratorLogin(sqlServerAdminName)
                    .withAdministratorPassword("N0t@P@ssw0rd!")
                    .withActiveDirectoryAdministrator("DSEng", id)
                    .withoutAccessFromAzureServices()
                    .defineFirewallRule("somefirewallrule1")
                        .withIPAddress("0.0.0.1")
                        .attach()
                    .withTag("tag1", "value1")
                    .create();
        Assert.assertEquals(sqlServerAdminName, sqlServer.administratorLogin());
        Assert.assertEquals("v12.0", sqlServer.kind());
        Assert.assertEquals("12.0", sqlServer.version());

        sqlServer = sqlServerManager.sqlServers().getByResourceGroup(RG_NAME, SQL_SERVER_NAME);
        Assert.assertEquals(sqlServerAdminName, sqlServer.administratorLogin());
        Assert.assertEquals("v12.0", sqlServer.kind());
        Assert.assertEquals("12.0", sqlServer.version());

        SqlActiveDirectoryAdministrator sqlADAdmin = sqlServer.getActiveDirectoryAdministrator();
        Assert.assertNotNull(sqlADAdmin);
        Assert.assertEquals("DSEng", sqlADAdmin.signInName());
        Assert.assertNotNull(sqlADAdmin.id());
        Assert.assertEquals("ActiveDirectory", sqlADAdmin.administratorType());

        sqlADAdmin = sqlServer.setActiveDirectoryAdministrator("DSEngAll", id);
        Assert.assertNotNull(sqlADAdmin);
        Assert.assertEquals("DSEngAll", sqlADAdmin.signInName());
        Assert.assertNotNull(sqlADAdmin.id());
        Assert.assertEquals("ActiveDirectory", sqlADAdmin.administratorType());
        sqlServer.removeActiveDirectoryAdministrator();
        sqlADAdmin = sqlServer.getActiveDirectoryAdministrator();
        Assert.assertNull(sqlADAdmin);

        SqlFirewallRule firewallRule = sqlServerManager.sqlServers().firewallRules().getBySqlServer(RG_NAME, SQL_SERVER_NAME, "somefirewallrule1");
        Assert.assertEquals("0.0.0.1", firewallRule.startIPAddress());
        Assert.assertEquals("0.0.0.1", firewallRule.endIPAddress());

        firewallRule = sqlServerManager.sqlServers().firewallRules().getBySqlServer(RG_NAME, SQL_SERVER_NAME, "AllowAllWindowsAzureIps");
        Assert.assertNull(firewallRule);

        sqlServer.enableAccessFromAzureServices();
        firewallRule = sqlServerManager.sqlServers().firewallRules().getBySqlServer(RG_NAME, SQL_SERVER_NAME, "AllowAllWindowsAzureIps");
        Assert.assertEquals("0.0.0.0", firewallRule.startIPAddress());
        Assert.assertEquals("0.0.0.0", firewallRule.endIPAddress());

        sqlServer.update()
            .withNewFirewallRule("0.0.0.2", "0.0.0.2", "newFirewallRule1")
            .apply();
        sqlServer.firewallRules().delete("newFirewallRule2");
        Assert.assertNull(sqlServer.firewallRules().get("newFirewallRule2"));

        firewallRule = sqlServerManager.sqlServers().firewallRules()
            .define("newFirewallRule2")
            .withExistingSqlServer(RG_NAME, SQL_SERVER_NAME)
            .withIPAddress("0.0.0.3")
            .create();

        Assert.assertEquals("0.0.0.3", firewallRule.startIPAddress());
        Assert.assertEquals("0.0.0.3", firewallRule.endIPAddress());

        firewallRule = firewallRule.update().withStartIPAddress("0.0.0.1").apply();

        Assert.assertEquals("0.0.0.1", firewallRule.startIPAddress());
        Assert.assertEquals("0.0.0.3", firewallRule.endIPAddress());

        sqlServer.firewallRules().delete("somefirewallrule1");
        firewallRule = sqlServerManager.sqlServers().firewallRules().getBySqlServer(RG_NAME, SQL_SERVER_NAME, "somefirewallrule1");
        Assert.assertNull(firewallRule);

        firewallRule = sqlServer.firewallRules().define("somefirewallrule2")
            .withIPAddress("0.0.0.4")
            .create();

        Assert.assertEquals("0.0.0.4", firewallRule.startIPAddress());
        Assert.assertEquals("0.0.0.4", firewallRule.endIPAddress());

        firewallRule.delete();
    }

    @Ignore("Depends on the existing SQL server")
    @Test
    public void canListRecommendedElasticPools() throws Exception {
        SqlServer sqlServer = sqlServerManager.sqlServers().getByResourceGroup("ans", "ans-secondary");
        sqlServer.databases().list().get(0).listServiceTierAdvisors().values().iterator().next().serviceLevelObjectiveUsageMetrics();
        Map<String, RecommendedElasticPool> recommendedElasticPools = sqlServer.listRecommendedElasticPools();
        Assert.assertNotNull(recommendedElasticPools);
        Assert.assertNotNull(sqlServer.databases().list().get(0).getUpgradeHint());
    }

    @Test
    public void canCRUDSqlServer() throws Exception {

        // Check if the name is available
        CheckNameAvailabilityResult checkNameResult = sqlServerManager.sqlServers()
            .checkNameAvailability(SQL_SERVER_NAME);
        Assert.assertTrue(checkNameResult.isAvailable());

        // Create
        SqlServer sqlServer = createSqlServer();

        validateSqlServer(sqlServer);

        // Confirm the server name is unavailable
        checkNameResult = sqlServerManager.sqlServers()
            .checkNameAvailability(SQL_SERVER_NAME);
        Assert.assertFalse(checkNameResult.isAvailable());
        Assert.assertEquals(CheckNameAvailabilityReason.ALREADY_EXISTS.toString(), checkNameResult.unavailabilityReason());

        List<ServiceObjective> serviceObjectives = sqlServer.listServiceObjectives();

        Assert.assertNotEquals(serviceObjectives.size(), 0);
        Assert.assertNotNull(serviceObjectives.get(0).refresh());
        Assert.assertNotNull(sqlServer.getServiceObjective("d1737d22-a8ea-4de7-9bd0-33395d2a7419"));

        sqlServer.update().withAdministratorPassword("P@ssword~2").apply();

        // List
        List<SqlServer> sqlServers = sqlServerManager.sqlServers().listByResourceGroup(RG_NAME);
        boolean found = false;
        for (SqlServer server : sqlServers) {
            if (server.name().equals(SQL_SERVER_NAME)) {
                found = true;
            }
        }
        Assert.assertTrue(found);
        // Get
        sqlServer = sqlServerManager.sqlServers().getByResourceGroup(RG_NAME, SQL_SERVER_NAME);
        Assert.assertNotNull(sqlServer);

        sqlServerManager.sqlServers().deleteByResourceGroup(sqlServer.resourceGroupName(), sqlServer.name());
        validateSqlServerNotFound(sqlServer);
    }

    @Test
    public void canUseCoolShortcutsForResourceCreation() throws Exception {
        String database2Name = "database2";
        String database1InEPName = "database1InEP";
        String database2InEPName = "database2InEP";
        String elasticPool2Name = "elasticPool2";
        String elasticPool3Name = "elasticPool3";
        String elasticPool1Name = SQL_ELASTIC_POOL_NAME;

        // Create
        SqlServer sqlServer = sqlServerManager.sqlServers().define(SQL_SERVER_NAME)
                .withRegion(Region.US_CENTRAL)
                .withNewResourceGroup(RG_NAME)
                .withAdministratorLogin("userName")
                .withAdministratorPassword("Password~1")
                .withoutAccessFromAzureServices()
                .withNewDatabase(SQL_DATABASE_NAME)
                .withNewDatabase(database2Name)
                .withNewElasticPool(elasticPool1Name, ElasticPoolEditions.STANDARD)
                .withNewElasticPool(elasticPool2Name, ElasticPoolEditions.PREMIUM, database1InEPName, database2InEPName)
                .withNewElasticPool(elasticPool3Name, ElasticPoolEditions.STANDARD)
                .withNewFirewallRule(START_IPADDRESS, END_IPADDRESS, SQL_FIREWALLRULE_NAME)
                .withNewFirewallRule(START_IPADDRESS, END_IPADDRESS)
                .withNewFirewallRule(START_IPADDRESS)
                .create();

        validateMultiCreation(database2Name, database1InEPName, database2InEPName, elasticPool1Name, elasticPool2Name, elasticPool3Name, sqlServer, false);
        elasticPool1Name = SQL_ELASTIC_POOL_NAME + " U";
        database2Name = "database2U";
        database1InEPName = "database1InEPU";
        database2InEPName = "database2InEPU";
        elasticPool2Name = "elasticPool2U";
        elasticPool3Name = "elasticPool3U";

        // Update
        sqlServer = sqlServer.update()
                .withNewDatabase(SQL_DATABASE_NAME).withNewDatabase(database2Name)
                .withNewElasticPool(elasticPool1Name, ElasticPoolEditions.STANDARD)
                .withNewElasticPool(elasticPool2Name, ElasticPoolEditions.PREMIUM, database1InEPName, database2InEPName)
                .withNewElasticPool(elasticPool3Name, ElasticPoolEditions.STANDARD)
                .withNewFirewallRule(START_IPADDRESS, END_IPADDRESS, SQL_FIREWALLRULE_NAME)
                .withNewFirewallRule(START_IPADDRESS, END_IPADDRESS)
                .withNewFirewallRule(START_IPADDRESS)
                .withTag("tag2", "value2")
                .apply();

        validateMultiCreation(database2Name, database1InEPName, database2InEPName, elasticPool1Name, elasticPool2Name, elasticPool3Name, sqlServer, true);

        sqlServer.refresh();
        Assert.assertEquals(sqlServer.elasticPools().list().size(), 0);

        // List
        List<SqlServer> sqlServers = sqlServerManager.sqlServers().listByResourceGroup(RG_NAME);
        boolean found = false;
        for (SqlServer server : sqlServers) {
            if (server.name().equals(SQL_SERVER_NAME)) {
                found = true;
            }
        }

        Assert.assertTrue(found);
        // Get
        sqlServer = sqlServerManager.sqlServers().getByResourceGroup(RG_NAME, SQL_SERVER_NAME);
        Assert.assertNotNull(sqlServer);

        sqlServerManager.sqlServers().deleteByResourceGroup(sqlServer.resourceGroupName(), sqlServer.name());
        validateSqlServerNotFound(sqlServer);
    }

    @Test
    public void canCRUDSqlDatabase() throws Exception {
        // Create
        SqlServer sqlServer = createSqlServer();
        Observable<Indexable> resourceStream = sqlServer.databases()
                .define(SQL_DATABASE_NAME)
                .createAsync();

        SqlDatabase sqlDatabase = Utils.<SqlDatabase>rootResource(resourceStream)
                .toBlocking()
                .first();

        validateSqlDatabase(sqlDatabase, SQL_DATABASE_NAME);
        Assert.assertTrue(sqlServer.databases().list().size() > 0);

        // Test transparent data encryption settings.
        TransparentDataEncryption transparentDataEncryption = sqlDatabase.getTransparentDataEncryption();
        Assert.assertNotNull(transparentDataEncryption.status());

        List<TransparentDataEncryptionActivity> transparentDataEncryptionActivities = transparentDataEncryption.listActivities();
        Assert.assertNotNull(transparentDataEncryptionActivities);

        transparentDataEncryption = transparentDataEncryption.updateStatus(TransparentDataEncryptionStates.ENABLED);
        Assert.assertNotNull(transparentDataEncryption);
        Assert.assertEquals(transparentDataEncryption.status(), TransparentDataEncryptionStates.ENABLED);

        transparentDataEncryptionActivities = transparentDataEncryption.listActivities();
        Assert.assertNotNull(transparentDataEncryptionActivities);

        TestUtilities.sleep(10000, isRecordMode());
        transparentDataEncryption = sqlDatabase.getTransparentDataEncryption().updateStatus(TransparentDataEncryptionStates.DISABLED);
        Assert.assertNotNull(transparentDataEncryption);
        Assert.assertEquals(transparentDataEncryption.status(), TransparentDataEncryptionStates.DISABLED);
        Assert.assertEquals(transparentDataEncryption.sqlServerName(), SQL_SERVER_NAME);
        Assert.assertEquals(transparentDataEncryption.databaseName(), SQL_DATABASE_NAME);
        Assert.assertNotNull(transparentDataEncryption.name());
        Assert.assertNotNull(transparentDataEncryption.id());
        // Done testing with encryption settings.

        // Assert.assertNotNull(sqlDatabase.getUpgradeHint()); // This property is null

        // Test Service tier advisors.
        Map<String, ServiceTierAdvisor> serviceTierAdvisors = sqlDatabase.listServiceTierAdvisors();
        Assert.assertNotNull(serviceTierAdvisors);
        Assert.assertNotNull(serviceTierAdvisors.values().iterator().next().serviceLevelObjectiveUsageMetrics());
        Assert.assertNotEquals(serviceTierAdvisors.size(), 0);

        Assert.assertNotNull(serviceTierAdvisors.values().iterator().next().refresh());
        Assert.assertNotNull(serviceTierAdvisors.values().iterator().next().serviceLevelObjectiveUsageMetrics());
        // End of testing service tier advisors.

        sqlServer =  sqlServerManager.sqlServers().getByResourceGroup(RG_NAME, SQL_SERVER_NAME);
        validateSqlServer(sqlServer);

        // Create another database with above created database as source database.
        Creatable<SqlElasticPool> sqlElasticPoolCreatable = sqlServer.elasticPools()
                .define(SQL_ELASTIC_POOL_NAME)
                .withEdition(ElasticPoolEditions.STANDARD);
        String anotherDatabaseName = "anotherDatabase";
        SqlDatabase anotherDatabase = sqlServer.databases()
                .define(anotherDatabaseName)
                .withNewElasticPool(sqlElasticPoolCreatable)
                .withSourceDatabase(sqlDatabase.id())
                .withMode(CreateMode.COPY)
                .create();

        validateSqlDatabaseWithElasticPool(anotherDatabase, anotherDatabaseName);
        sqlServer.databases().delete(anotherDatabase.name());

        // Get
        validateSqlDatabase(sqlServer.databases().get(SQL_DATABASE_NAME), SQL_DATABASE_NAME);

        // List
        validateListSqlDatabase(sqlServer.databases().list());

        // Delete
        sqlServer.databases().delete(SQL_DATABASE_NAME);
        validateSqlDatabaseNotFound(SQL_DATABASE_NAME);

        // Add another database to the server
        resourceStream = sqlServer.databases()
                .define("newDatabase")
                .withEdition(DatabaseEditions.STANDARD)
                .withCollation(COLLATION)
                .createAsync();

        sqlDatabase = Utils.<SqlDatabase>rootResource(resourceStream)
                .toBlocking()
                .first();

        // Rename the database
        sqlDatabase = sqlDatabase.rename("renamedDatabase");
        validateSqlDatabase(sqlDatabase, "renamedDatabase");

        sqlServer.databases().delete(sqlDatabase.name());

        sqlServerManager.sqlServers().deleteByResourceGroup(sqlServer.resourceGroupName(), sqlServer.name());
        validateSqlServerNotFound(sqlServer);
    }

    @Test
    public void canManageReplicationLinks() throws Exception {
        // Create
        String anotherSqlServerName = SQL_SERVER_NAME + "another";
        SqlServer sqlServer1 = createSqlServer();
        SqlServer sqlServer2 = createSqlServer(anotherSqlServerName);

        Observable<Indexable> resourceStream = sqlServer1.databases()
                .define(SQL_DATABASE_NAME)
                .withEdition(DatabaseEditions.STANDARD)
                .withCollation(COLLATION)
                .createAsync();

        SqlDatabase databaseInServer1 = Utils.<SqlDatabase>rootResource(resourceStream)
                .toBlocking()
                .first();

        validateSqlDatabase(databaseInServer1, SQL_DATABASE_NAME);
        SqlDatabase databaseInServer2 = sqlServer2.databases()
                .define(SQL_DATABASE_NAME)
                .withSourceDatabase(databaseInServer1.id())
                .withMode(CreateMode.ONLINE_SECONDARY)
                .create();
        TestUtilities.sleep(2000, isRecordMode());
        List<ReplicationLink> replicationLinksInDb1 = new ArrayList<>(databaseInServer1.listReplicationLinks().values());

        Assert.assertEquals(replicationLinksInDb1.size() , 1);
        Assert.assertEquals(replicationLinksInDb1.get(0).partnerDatabase(), databaseInServer2.name());
        Assert.assertEquals(replicationLinksInDb1.get(0).partnerServer(), databaseInServer2.sqlServerName());

        List<ReplicationLink> replicationLinksInDb2 = new ArrayList<>(databaseInServer2.listReplicationLinks().values());

        Assert.assertEquals(replicationLinksInDb2.size() , 1);
        Assert.assertEquals(replicationLinksInDb2.get(0).partnerDatabase(), databaseInServer1.name());
        Assert.assertEquals(replicationLinksInDb2.get(0).partnerServer(), databaseInServer1.sqlServerName());

        Assert.assertNotNull(replicationLinksInDb1.get(0).refresh());

        // Failover
        replicationLinksInDb2.get(0).failover();
        replicationLinksInDb2.get(0).refresh();
        TestUtilities.sleep(30000, isRecordMode());
        // Force failover
        replicationLinksInDb1.get(0).forceFailoverAllowDataLoss();
        replicationLinksInDb1.get(0).refresh();

        TestUtilities.sleep(30000, isRecordMode());

        replicationLinksInDb2.get(0).delete();
        Assert.assertEquals(databaseInServer2.listReplicationLinks().size(), 0);

        sqlServer1.databases().delete(databaseInServer1.name());
        sqlServer2.databases().delete(databaseInServer2.name());

        sqlServerManager.sqlServers().deleteByResourceGroup(sqlServer2.resourceGroupName(), sqlServer2.name());
        validateSqlServerNotFound(sqlServer2);
        sqlServerManager.sqlServers().deleteByResourceGroup(sqlServer1.resourceGroupName(), sqlServer1.name());
        validateSqlServerNotFound(sqlServer1);
    }

    @Test
    public void canDoOperationsOnDataWarehouse() throws Exception {
        // Create
        SqlServer sqlServer = createSqlServer();

        validateSqlServer(sqlServer);

        // List usages for the server.
        Assert.assertNotNull(sqlServer.listUsages());

        Observable<Indexable> resourceStream = sqlServer.databases()
                .define(SQL_DATABASE_NAME)
                .withEdition(DatabaseEditions.DATA_WAREHOUSE)
                .withCollation(COLLATION)
                .createAsync();

        SqlDatabase sqlDatabase = Utils.<SqlDatabase>rootResource(resourceStream)
                .toBlocking()
                .first();
        Assert.assertNotNull(sqlDatabase);

        sqlDatabase = sqlServer.databases().get(SQL_DATABASE_NAME);
        Assert.assertNotNull(sqlDatabase);
        Assert.assertTrue(sqlDatabase.isDataWarehouse());

        // Get
        SqlWarehouse dataWarehouse = sqlServer.databases().get(SQL_DATABASE_NAME).asWarehouse();

        Assert.assertNotNull(dataWarehouse);
        Assert.assertEquals(dataWarehouse.name(), SQL_DATABASE_NAME);
        Assert.assertEquals(dataWarehouse.edition(), DatabaseEditions.DATA_WAREHOUSE);

        // List Restore points.
        Assert.assertNotNull(dataWarehouse.listRestorePoints());
        // Get usages.
        Assert.assertNotNull(dataWarehouse.listUsages());

        // Pause warehouse
        dataWarehouse.pauseDataWarehouse();

        // Resume warehouse
        dataWarehouse.resumeDataWarehouse();

        sqlServer.databases().delete(SQL_DATABASE_NAME);

        sqlServerManager.sqlServers().deleteByResourceGroup(sqlServer.resourceGroupName(), sqlServer.name());
        validateSqlServerNotFound(sqlServer);
    }

    @Test
    public void canCRUDSqlDatabaseWithElasticPool() throws Exception {
        // Create
        SqlServer sqlServer = createSqlServer();

        Creatable<SqlElasticPool> sqlElasticPoolCreatable = sqlServer.elasticPools()
                .define(SQL_ELASTIC_POOL_NAME)
                .withEdition(ElasticPoolEditions.STANDARD)
                .withTag("tag1", "value1");

        Observable<Indexable> resourceStream = sqlServer.databases()
                .define(SQL_DATABASE_NAME)
                .withNewElasticPool(sqlElasticPoolCreatable)
                .withCollation(COLLATION)
                .createAsync();

        SqlDatabase sqlDatabase = Utils.<SqlDatabase>rootResource(resourceStream)
                .toBlocking()
                .first();

        validateSqlDatabase(sqlDatabase, SQL_DATABASE_NAME);

        sqlServer =  sqlServerManager.sqlServers().getByResourceGroup(RG_NAME, SQL_SERVER_NAME);
        validateSqlServer(sqlServer);

        // Get Elastic pool
        SqlElasticPool elasticPool = sqlServer.elasticPools().get(SQL_ELASTIC_POOL_NAME);
        validateSqlElasticPool(elasticPool);

        // Get
        validateSqlDatabaseWithElasticPool(sqlServer.databases().get(SQL_DATABASE_NAME), SQL_DATABASE_NAME);

        // List
        validateListSqlDatabase(sqlServer.databases().list());

        // Remove database from elastic pools.
        sqlDatabase.update()
                .withoutElasticPool()
                .withEdition(DatabaseEditions.STANDARD)
                .withServiceObjective(ServiceObjectiveName.S3)
            .apply();
        sqlDatabase = sqlServer.databases().get(SQL_DATABASE_NAME);
        Assert.assertNull(sqlDatabase.elasticPoolName());

        // Update edition of the SQL database
        sqlDatabase.update()
                .withEdition(DatabaseEditions.PREMIUM)
                .withServiceObjective(ServiceObjectiveName.P1)
                .apply();
        sqlDatabase = sqlServer.databases().get(SQL_DATABASE_NAME);
        Assert.assertEquals(sqlDatabase.edition(), DatabaseEditions.PREMIUM);
        Assert.assertEquals(sqlDatabase.serviceLevelObjective(), ServiceObjectiveName.P1);

        // Update just the service level objective for database.
        sqlDatabase.update().withServiceObjective(ServiceObjectiveName.P2).apply();
        sqlDatabase = sqlServer.databases().get(SQL_DATABASE_NAME);
        Assert.assertEquals(sqlDatabase.serviceLevelObjective(), ServiceObjectiveName.P2);
        Assert.assertEquals(sqlDatabase.requestedServiceObjectiveName(), ServiceObjectiveName.P2);

        // Update max size bytes of the database.
        sqlDatabase.update()
                .withMaxSizeBytes(268435456000L)
                .apply();

        sqlDatabase = sqlServer.databases().get(SQL_DATABASE_NAME);
        Assert.assertEquals(sqlDatabase.maxSizeBytes(), 268435456000L);

        // Put the database back in elastic pool.
        sqlDatabase.update()
                .withExistingElasticPool(SQL_ELASTIC_POOL_NAME)
                .apply();

        sqlDatabase = sqlServer.databases().get(SQL_DATABASE_NAME);
        Assert.assertEquals(sqlDatabase.elasticPoolName(), SQL_ELASTIC_POOL_NAME);

        // List Activity in elastic pool
        Assert.assertNotNull(elasticPool.listActivities());

        // List Database activity in elastic pool.
        Assert.assertNotNull(elasticPool.listDatabaseActivities());

        // List databases in elastic pool.
        List<SqlDatabase> databasesInElasticPool = elasticPool.listDatabases();
        Assert.assertNotNull(databasesInElasticPool);
        Assert.assertEquals(databasesInElasticPool.size(), 1);

        // Get a particular database in elastic pool.
        SqlDatabase databaseInElasticPool = elasticPool.getDatabase(SQL_DATABASE_NAME);
        validateSqlDatabase(databaseInElasticPool, SQL_DATABASE_NAME);

        // Refresh works on the database got from elastic pool.
        databaseInElasticPool.refresh();

        // Validate that trying to get an invalid database from elastic pool returns null.
        SqlDatabase db_which_does_not_exist = elasticPool.getDatabase("does_not_exist");
        Assert.assertNull(db_which_does_not_exist);

        // Delete
        sqlServer.databases().delete(SQL_DATABASE_NAME);
        validateSqlDatabaseNotFound(SQL_DATABASE_NAME);

        SqlElasticPool sqlElasticPool = sqlServer.elasticPools().get(SQL_ELASTIC_POOL_NAME);

        // Add another database to the server and pool.
        resourceStream = sqlServer.databases()
                .define("newDatabase")
                .withExistingElasticPool(sqlElasticPool)
                .withCollation(COLLATION)
                .createAsync();

        sqlDatabase = Utils.<SqlDatabase>rootResource(resourceStream)
                .toBlocking()
                .first();
        sqlServer.databases().delete(sqlDatabase.name());
        validateSqlDatabaseNotFound("newDatabase");

        sqlServer.elasticPools().delete(SQL_ELASTIC_POOL_NAME);
        sqlServerManager.sqlServers().deleteByResourceGroup(sqlServer.resourceGroupName(), sqlServer.name());
        validateSqlServerNotFound(sqlServer);
    }

    @Test
    public void canCRUDSqlElasticPool() throws Exception {
        // Create
        SqlServer sqlServer = createSqlServer();

        sqlServer =  sqlServerManager.sqlServers().getByResourceGroup(RG_NAME, SQL_SERVER_NAME);
        validateSqlServer(sqlServer);

        Observable<Indexable> resourceStream = sqlServer.elasticPools()
                .define(SQL_ELASTIC_POOL_NAME)
                .withEdition(ElasticPoolEditions.STANDARD)
                .withTag("tag1", "value1")
                .createAsync();
        SqlElasticPool sqlElasticPool = Utils.<SqlElasticPool>rootResource(resourceStream)
                .toBlocking()
                .first();
        validateSqlElasticPool(sqlElasticPool);
        Assert.assertEquals(sqlElasticPool.listDatabases().size(), 0);

        sqlElasticPool = sqlElasticPool.update()
                .withDtu(100)
                .withDatabaseDtuMax(20)
                .withDatabaseDtuMin(10)
                .withStorageCapacity(102400)
                .withNewDatabase(SQL_DATABASE_NAME)
                .withTag("tag2", "value2")
                .apply();

        validateSqlElasticPool(sqlElasticPool);
        Assert.assertEquals(sqlElasticPool.listDatabases().size(), 1);

        // Get
        validateSqlElasticPool(sqlServer.elasticPools().get(SQL_ELASTIC_POOL_NAME));

        // List
        validateListSqlElasticPool(sqlServer.elasticPools().list());

        // Delete
        sqlServer.databases().delete(SQL_DATABASE_NAME);
        sqlServer.elasticPools().delete(SQL_ELASTIC_POOL_NAME);
        validateSqlElasticPoolNotFound(sqlServer, SQL_ELASTIC_POOL_NAME);

        // Add another database to the server
        resourceStream = sqlServer.elasticPools()
                .define("newElasticPool")
                .withEdition(ElasticPoolEditions.STANDARD)
                .createAsync();

        sqlElasticPool = Utils.<SqlElasticPool>rootResource(resourceStream)
                .toBlocking()
                .first();

        sqlServer.elasticPools().delete(sqlElasticPool.name());
        validateSqlElasticPoolNotFound(sqlServer, "newElasticPool");

        sqlServerManager.sqlServers().deleteByResourceGroup(sqlServer.resourceGroupName(), sqlServer.name());
        validateSqlServerNotFound(sqlServer);
    }

    @Test
    public void canCRUDSqlFirewallRule() throws Exception {
        // Create
        SqlServer sqlServer = createSqlServer();

        sqlServer =  sqlServerManager.sqlServers().getByResourceGroup(RG_NAME, SQL_SERVER_NAME);
        validateSqlServer(sqlServer);

        Observable<Indexable> resourceStream = sqlServer.firewallRules()
                .define(SQL_FIREWALLRULE_NAME)
                .withIPAddressRange(START_IPADDRESS, END_IPADDRESS)
                .createAsync();

        SqlFirewallRule sqlFirewallRule = Utils.<SqlFirewallRule>rootResource(resourceStream)
                .toBlocking()
                .first();

        validateSqlFirewallRule(sqlFirewallRule, SQL_FIREWALLRULE_NAME);
        validateSqlFirewallRule(sqlServer.firewallRules().get(SQL_FIREWALLRULE_NAME), SQL_FIREWALLRULE_NAME);


        String secondFirewallRuleName = "secondFireWallRule";
        SqlFirewallRule secondFirewallRule = sqlServer.firewallRules()
                .define(secondFirewallRuleName)
                .withIPAddress(START_IPADDRESS)
                .create();
        Assert.assertNotNull(secondFirewallRule);

        secondFirewallRule = sqlServer.firewallRules().get(secondFirewallRuleName);
        Assert.assertNotNull(secondFirewallRule);
        Assert.assertEquals(START_IPADDRESS, secondFirewallRule.endIPAddress());

        secondFirewallRule = secondFirewallRule.update().withEndIPAddress(END_IPADDRESS).apply();

        validateSqlFirewallRule(secondFirewallRule, secondFirewallRuleName);
        sqlServer.firewallRules().delete(secondFirewallRuleName);
        Assert.assertNull(sqlServer.firewallRules().get(secondFirewallRuleName));

        // Get
        sqlFirewallRule = sqlServer.firewallRules().get(SQL_FIREWALLRULE_NAME);
        validateSqlFirewallRule(sqlFirewallRule, SQL_FIREWALLRULE_NAME);

        // Update
        // Making start and end IP address same.
        sqlFirewallRule.update().withEndIPAddress(START_IPADDRESS).apply();
        sqlFirewallRule = sqlServer.firewallRules().get(SQL_FIREWALLRULE_NAME);
        Assert.assertEquals(sqlFirewallRule.endIPAddress(), START_IPADDRESS);

        // List
        validateListSqlFirewallRule(sqlServer.firewallRules().list());

        // Delete
        sqlServer.firewallRules().delete(sqlFirewallRule.name());
        validateSqlFirewallRuleNotFound();

        // Delete server
        sqlServerManager.sqlServers().deleteByResourceGroup(sqlServer.resourceGroupName(), sqlServer.name());
        validateSqlServerNotFound(sqlServer);
    }

    private static void validateMultiCreation(
            String database2Name,
            String database1InEPName,
            String database2InEPName,
            String elasticPool1Name,
            String elasticPool2Name,
            String elasticPool3Name,
            SqlServer sqlServer,
            boolean deleteUsingUpdate) {
        validateSqlServer(sqlServer);
        validateSqlServer(sqlServerManager.sqlServers().getByResourceGroup(RG_NAME, SQL_SERVER_NAME));
        validateSqlDatabase(sqlServer.databases().get(SQL_DATABASE_NAME), SQL_DATABASE_NAME);
        validateSqlFirewallRule(sqlServer.firewallRules().get(SQL_FIREWALLRULE_NAME), SQL_FIREWALLRULE_NAME);

        List<SqlFirewallRule> firewalls = sqlServer.firewallRules().list();
        Assert.assertEquals(3, firewalls.size());

        int startIPAddress = 0;
        int endIPAddress = 0;

        for (SqlFirewallRule firewall: firewalls) {
            if (!firewall.name().equalsIgnoreCase(SQL_FIREWALLRULE_NAME)) {
                Assert.assertEquals(firewall.startIPAddress(), START_IPADDRESS);
                if (firewall.endIPAddress().equalsIgnoreCase(START_IPADDRESS)) {
                    startIPAddress++;
                }
                else if (firewall.endIPAddress().equalsIgnoreCase(END_IPADDRESS)) {
                    endIPAddress++;
                }
            }
        }

        Assert.assertEquals(startIPAddress, 1);
        Assert.assertEquals(endIPAddress, 1);

        Assert.assertNotNull(sqlServer.databases().get(database2Name));
        Assert.assertNotNull(sqlServer.databases().get(database1InEPName));
        Assert.assertNotNull(sqlServer.databases().get(database2InEPName));

        SqlElasticPool ep1 = sqlServer.elasticPools().get(elasticPool1Name);
        validateSqlElasticPool(ep1, elasticPool1Name);
        SqlElasticPool ep2 = sqlServer.elasticPools().get(elasticPool2Name);

        Assert.assertNotNull(ep2);
        Assert.assertEquals(ep2.edition(), ElasticPoolEditions.PREMIUM);
        Assert.assertEquals(ep2.listDatabases().size(), 2);
        Assert.assertNotNull(ep2.getDatabase(database1InEPName));
        Assert.assertNotNull(ep2.getDatabase(database2InEPName));

        SqlElasticPool ep3 = sqlServer.elasticPools().get(elasticPool3Name);

        Assert.assertNotNull(ep3);
        Assert.assertEquals(ep3.edition(), ElasticPoolEditions.STANDARD);

        if (!deleteUsingUpdate) {
            sqlServer.databases().delete(database2Name);
            sqlServer.databases().delete(database1InEPName);
            sqlServer.databases().delete(database2InEPName);
            sqlServer.databases().delete(SQL_DATABASE_NAME);

            Assert.assertEquals(ep1.listDatabases().size(), 0);
            Assert.assertEquals(ep2.listDatabases().size(), 0);
            Assert.assertEquals(ep3.listDatabases().size(), 0);

            sqlServer.elasticPools().delete(elasticPool1Name);
            sqlServer.elasticPools().delete(elasticPool2Name);
            sqlServer.elasticPools().delete(elasticPool3Name);

            firewalls = sqlServer.firewallRules().list();

            for (SqlFirewallRule firewallRule :firewalls) {
                firewallRule.delete();
            }
        }
        else {
            sqlServer.update()
                    .withoutDatabase(database2Name)
                    .withoutElasticPool(elasticPool1Name)
                    .withoutElasticPool(elasticPool2Name)
                    .withoutElasticPool(elasticPool3Name)
                    .withoutDatabase(database1InEPName)
                    .withoutDatabase(SQL_DATABASE_NAME)
                    .withoutDatabase(database2InEPName)
                    .withoutFirewallRule(SQL_FIREWALLRULE_NAME)
                    .apply();

            Assert.assertEquals(sqlServer.elasticPools().list().size(), 0);

            firewalls = sqlServer.firewallRules().list();
            Assert.assertEquals(firewalls.size(), 2);
            for (SqlFirewallRule firewallRule :firewalls) {
                firewallRule.delete();
            }
        }

        Assert.assertEquals(sqlServer.elasticPools().list().size(), 0);
        // Only master database is remaining in the SQLServer.
        Assert.assertEquals(sqlServer.databases().list().size(), 1);
    }

    private static void validateSqlFirewallRuleNotFound() {
        Assert.assertNull(sqlServerManager.sqlServers().getByResourceGroup(RG_NAME, SQL_SERVER_NAME).firewallRules().get(SQL_FIREWALLRULE_NAME));
    }

    private static void validateSqlElasticPoolNotFound(SqlServer sqlServer, String elasticPoolName) {
        Assert.assertNull(sqlServer.elasticPools().get(elasticPoolName));
    }

    private static void validateSqlDatabaseNotFound(String newDatabase) {
        Assert.assertNull(sqlServerManager.sqlServers().getByResourceGroup(RG_NAME, SQL_SERVER_NAME).databases().get(newDatabase));
    }


    private static void validateSqlServerNotFound(SqlServer sqlServer) {
        Assert.assertNull(sqlServerManager.sqlServers().getById(sqlServer.id()));
    }

    private static SqlServer createSqlServer() {
        return createSqlServer(SQL_SERVER_NAME);
    }

    private static SqlServer createSqlServer(String SQL_SERVER_NAME) {
        return sqlServerManager.sqlServers()
                .define(SQL_SERVER_NAME)
                .withRegion(Region.US_CENTRAL)
                .withNewResourceGroup(RG_NAME)
                .withAdministratorLogin("userName")
                .withAdministratorPassword("P@ssword~1")
                .create();
    }

    private static void validateListSqlFirewallRule(List<SqlFirewallRule> sqlFirewallRules) {
        boolean found = false;
        for (SqlFirewallRule firewallRule: sqlFirewallRules) {
            if (firewallRule.name().equals(SQL_FIREWALLRULE_NAME)) {
                found = true;
            }
        }
        Assert.assertTrue(found);
    }

    private static void validateSqlFirewallRule(SqlFirewallRule sqlFirewallRule, String firewallName) {
        Assert.assertNotNull(sqlFirewallRule);
        Assert.assertEquals(firewallName, sqlFirewallRule.name());
        Assert.assertEquals(SQL_SERVER_NAME, sqlFirewallRule.sqlServerName());
        Assert.assertEquals(START_IPADDRESS, sqlFirewallRule.startIPAddress());
        Assert.assertEquals(END_IPADDRESS, sqlFirewallRule.endIPAddress());
        Assert.assertEquals(RG_NAME, sqlFirewallRule.resourceGroupName());
        Assert.assertEquals(SQL_SERVER_NAME, sqlFirewallRule.sqlServerName());
        Assert.assertEquals(Region.US_CENTRAL, sqlFirewallRule.region());
    }

    private static void validateListSqlElasticPool(List<SqlElasticPool> sqlElasticPools) {
        boolean found = false;
        for (SqlElasticPool elasticPool : sqlElasticPools) {
            if (elasticPool.name().equals(SQL_ELASTIC_POOL_NAME)) {
                found = true;
            }
        }
        Assert.assertTrue(found);
    }

    private static void validateSqlElasticPool(SqlElasticPool sqlElasticPool) {
        validateSqlElasticPool(sqlElasticPool, SQL_ELASTIC_POOL_NAME);
    }

    private static void validateSqlElasticPool(SqlElasticPool sqlElasticPool, String elasticPoolName) {
        Assert.assertNotNull(sqlElasticPool);
        Assert.assertEquals(RG_NAME, sqlElasticPool.resourceGroupName());
        Assert.assertEquals(elasticPoolName, sqlElasticPool.name());
        Assert.assertEquals(SQL_SERVER_NAME, sqlElasticPool.sqlServerName());
        Assert.assertEquals(ElasticPoolEditions.STANDARD, sqlElasticPool.edition());
        Assert.assertNotNull(sqlElasticPool.creationDate());
        Assert.assertNotEquals(0, sqlElasticPool.databaseDtuMax());
        Assert.assertNotEquals(0, sqlElasticPool.dtu());
    }

    private static void validateListSqlDatabase(List<SqlDatabase> sqlDatabases) {
        boolean found = false;
        for (SqlDatabase database : sqlDatabases) {
            if (database.name().equals(SQL_DATABASE_NAME)) {
                found = true;
            }
        }
        Assert.assertTrue(found);
    }

    private static void validateSqlServer(SqlServer sqlServer) {
        Assert.assertNotNull(sqlServer);
        Assert.assertEquals(RG_NAME, sqlServer.resourceGroupName());
        Assert.assertNotNull(sqlServer.fullyQualifiedDomainName());
//        Assert.assertEquals(ServerVersion.ONE_TWO_FULL_STOP_ZERO, sqlServer.version());
        Assert.assertEquals("userName", sqlServer.administratorLogin());
    }

    private static void validateSqlDatabase(SqlDatabase sqlDatabase, String databaseName) {
        Assert.assertNotNull(sqlDatabase);
        Assert.assertEquals(sqlDatabase.name(), databaseName);
        Assert.assertEquals(SQL_SERVER_NAME, sqlDatabase.sqlServerName());
        Assert.assertEquals(sqlDatabase.collation(), COLLATION);
        Assert.assertEquals(sqlDatabase.edition(), DatabaseEditions.STANDARD);
    }


    private static void validateSqlDatabaseWithElasticPool(SqlDatabase sqlDatabase, String databaseName) {
        validateSqlDatabase(sqlDatabase, databaseName);
        Assert.assertEquals(SQL_ELASTIC_POOL_NAME, sqlDatabase.elasticPoolName());
    }

}
