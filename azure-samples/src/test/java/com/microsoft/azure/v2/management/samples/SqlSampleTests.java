/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.samples;

import com.microsoft.azure.v2.management.sql.samples.ManageSqlDatabase;
import com.microsoft.azure.v2.management.sql.samples.ManageSqlDatabaseInElasticPool;
import com.microsoft.azure.v2.management.sql.samples.ManageSqlDatabasesAcrossDifferentDataCenters;
import com.microsoft.azure.v2.management.sql.samples.ManageSqlFailoverGroups;
import com.microsoft.azure.v2.management.sql.samples.ManageSqlFirewallRules;
import com.microsoft.azure.v2.management.sql.samples.ManageSqlImportExportDatabase;
import com.microsoft.azure.v2.management.sql.samples.ManageSqlServerDnsAliases;
import com.microsoft.azure.v2.management.sql.samples.ManageSqlServerSecurityAlertPolicy;
import com.microsoft.azure.v2.management.sql.samples.ManageSqlVirtualNetworkRules;
import com.microsoft.azure.v2.management.sql.samples.ManageSqlWithRecoveredOrRestoredDatabase;
import org.junit.Assert;
import org.junit.Test;

public class SqlSampleTests extends SamplesTestBase {

    @Test
    public void testManageSqlDatabase() {
        Assert.assertTrue(ManageSqlDatabase.runSample(azure));
    }

    @Test
    public void testManageSqlDatabaseInElasticPool() {
        Assert.assertTrue(ManageSqlDatabaseInElasticPool.runSample(azure));
    }

    @Test
    public void testManageSqlDatabasesAcrossDifferentDataCenters() {
        Assert.assertTrue(ManageSqlDatabasesAcrossDifferentDataCenters.runSample(azure));
    }

    @Test
    public void testManageSqlFirewallRules() {
        Assert.assertTrue(ManageSqlFirewallRules.runSample(azure));
    }

    @Test
    public void testManageSqlServerSecurityAlertPolicy() {
        Assert.assertTrue(ManageSqlServerSecurityAlertPolicy.runSample(azure));
    }

    @Test
    public void testManageSqlVirtualNetworkRules() {
        Assert.assertTrue(ManageSqlVirtualNetworkRules.runSample(azure));
    }

    @Test
    public void testManageSqlImportExportDatabase() {
        // Skip test in "playback" mode due to HTTP calls made outside of the management plane which can not be recorded at this time
        if (!isPlaybackMode()) {
            Assert.assertTrue(ManageSqlImportExportDatabase.runSample(azure));
        }
    }

    @Test
    public void testManageSqlWithRecoveredOrRestoredDatabase() {
        // This test can take significant time to run since it depends on the availability of certain resources on the service side.
        Assert.assertTrue(ManageSqlWithRecoveredOrRestoredDatabase.runSample(azure));
    }

    @Test
    public void testManageSqlFailoverGroups() {
        Assert.assertTrue(ManageSqlFailoverGroups.runSample(azure));
    }

    @Test
    public void testManageSqlServerDnsAliases() {
        // Skip test in "playback" mode due to HTTP calls made outside of the management plane which can not be recorded at this time
        if (!isPlaybackMode()) {
            Assert.assertTrue(ManageSqlServerDnsAliases.runSample(azure));
        }
    }
}
