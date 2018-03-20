/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.eventhub.EventHubNamespace;
import com.microsoft.azure.management.eventhub.EventHubNamespaceAuthorizationRule;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.rest.RestClient;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DiagnosticSettingsTests extends MonitorManagementTest {
    private static String RG_NAME = "";
    private static String SA_NAME = "";
    private static String DS_NAME="";
    private static String EH_NAME="";

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        RG_NAME = generateRandomResourceName("jMonitor_", 18);
        SA_NAME = generateRandomResourceName("jMonitorSa", 18);
        DS_NAME = generateRandomResourceName("jMonitorDs_", 18);
        EH_NAME = generateRandomResourceName("jMonitorEH", 18);

        super.initializeClients(restClient, defaultSubscription, domain);
    }
    @Override
    protected void cleanUpResources() {
        resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
    }

    @Test
    public void canCRUDDiagnosticSettings() throws Exception {

        VirtualMachine vm = computeManager.virtualMachines().list().get(0);

        // clean all diagnostic settings.
        PagedList<DiagnosticSetting> dsList = monitorManager.diagnosticSettings().listByResource(vm.id());
        for(DiagnosticSetting dsd : dsList) {
            monitorManager.diagnosticSettings().deleteById(dsd.id());
        }

        StorageAccount sa = storageManager.storageAccounts()
                .define(SA_NAME)
                // Storage Account should be in the same region as resource
                .withRegion(vm.region())
                .withNewResourceGroup(RG_NAME)
                .withTag("tag1", "value1")
                .create();

        EventHubNamespace namespace = eventHubManager.namespaces()
                .define(EH_NAME)
                // EventHub should be in the same region as resource
                .withRegion(vm.region())
                .withNewResourceGroup(RG_NAME)
                .withNewManageRule("mngRule1")
                .withNewSendRule("sndRule1")
                .create();

        EventHubNamespaceAuthorizationRule evenHubNsRule = namespace.listAuthorizationRules().get(0);

        List<DiagnosticSettingsCategory> categories = monitorManager.diagnosticSettings()
                .listCategoriesByResource(vm.id());

        Assert.assertNotNull(categories);
        Assert.assertFalse(categories.isEmpty());

        DiagnosticSetting setting = monitorManager.diagnosticSettings()
                .define(DS_NAME)
                .withResource(vm.id())
                .withStorageAccount(sa.id())
                .withEventHub(evenHubNsRule.id())
                .withLogsAndMetrics(categories, Period.minutes(5), 7)
                .create();

        Assert.assertTrue(vm.id().equalsIgnoreCase(setting.resourceId()));
        Assert.assertTrue(sa.id().equalsIgnoreCase(setting.storageAccountId()));
        Assert.assertTrue(evenHubNsRule.id().equalsIgnoreCase(setting.eventHubAuthorizationRuleId()));
        Assert.assertNull(setting.eventHubName());
        Assert.assertNull(setting.workspaceId());
        Assert.assertTrue(setting.logs().isEmpty());
        Assert.assertFalse(setting.metrics().isEmpty());

        setting.update()
                .withoutStorageAccount()
                .withoutLogs()
                .apply();

        Assert.assertTrue(vm.id().equalsIgnoreCase(setting.resourceId()));
        Assert.assertTrue(evenHubNsRule.id().equalsIgnoreCase(setting.eventHubAuthorizationRuleId()));
        Assert.assertNull(setting.storageAccountId());
        Assert.assertNull(setting.eventHubName());
        Assert.assertNull(setting.workspaceId());
        Assert.assertTrue(setting.logs().isEmpty());
        Assert.assertFalse(setting.metrics().isEmpty());

        DiagnosticSetting ds1 = monitorManager.diagnosticSettings().get(setting.resourceId(), setting.name());
        checkDiagnosticSettingValues(setting, ds1);

        DiagnosticSetting ds2 = monitorManager.diagnosticSettings().getById(setting.id());
        checkDiagnosticSettingValues(setting, ds2);

        dsList = monitorManager.diagnosticSettings().listByResource(vm.id());
        Assert.assertNotNull(dsList);
        Assert.assertEquals(1, dsList.size());
        DiagnosticSetting ds3 = dsList.get(0);
        checkDiagnosticSettingValues(setting, ds3);

        monitorManager.diagnosticSettings().deleteById(setting.id());

        dsList = monitorManager.diagnosticSettings().listByResource(vm.id());
        Assert.assertNotNull(dsList);
        Assert.assertTrue(dsList.isEmpty());
    }

    private void checkDiagnosticSettingValues(DiagnosticSetting expected, DiagnosticSetting actual) {
        Assert.assertTrue(expected.resourceId().equalsIgnoreCase(actual.resourceId()));
        Assert.assertTrue(expected.name().equalsIgnoreCase(actual.name()));

        if (expected.workspaceId() == null) {
            Assert.assertNull(actual.workspaceId());
        } else {
            Assert.assertTrue(expected.workspaceId().equalsIgnoreCase(actual.workspaceId()));
        }
        if (expected.storageAccountId() == null) {
            Assert.assertNull(actual.storageAccountId());
        } else {
            Assert.assertTrue(expected.storageAccountId().equalsIgnoreCase(actual.storageAccountId()));
        }
        if (expected.eventHubAuthorizationRuleId() == null) {
            Assert.assertNull(actual.eventHubAuthorizationRuleId());
        } else {
            Assert.assertTrue(expected.eventHubAuthorizationRuleId().equalsIgnoreCase(actual.eventHubAuthorizationRuleId()));
        }
        if (expected.eventHubName() == null) {
            Assert.assertNull(actual.eventHubName());
        } else {
            Assert.assertTrue(expected.eventHubName().equalsIgnoreCase(actual.eventHubName()));
        }
        // arrays
        if (expected.logs() == null) {
            Assert.assertNull(actual.logs());
        } else {
            Assert.assertEquals(expected.logs().size(), actual.logs().size());
        }
        if (expected.metrics() == null) {
            Assert.assertNull(actual.metrics());
        } else {
            Assert.assertEquals(expected.metrics().size(), actual.metrics().size());
        }
    }
}

