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
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.rest.RestClient;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class ActionGroupsTests extends MonitorManagementTest {
    private static String RG_NAME = "";

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        RG_NAME = generateRandomResourceName("jMonitor_", 18);

        super.initializeClients(restClient, defaultSubscription, domain);
    }
    @Override
    protected void cleanUpResources() {
        resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
    }

    @Test
    public void canCRUDActionGroups() throws Exception {

        ActionGroup ag = monitorManager.actionGroups().define("simpleActionGroup")
                .withNewResourceGroup(RG_NAME, Region.AUSTRALIA_SOUTHEAST)
                .defineReceiver("first")
                    .withAzureAppPush("azurepush@outlook.com")
                    .withEmail("justemail@outlook.com")
                    .withSms("1", "4255655665")
                    .withVoice("1", "2062066050")
                    .withWebhook("https://www.rate.am")
                    .attach()
                .defineReceiver("second")
                    .withEmail("secondemail@outlook.com")
                    .withWebhook("https://www.spyur.am")
                    .attach()
                .create();
        Assert.assertNotNull(ag);
        Assert.assertEquals("simpleAction", ag.shortName());
        Assert.assertNotNull(ag.azureAppPushReceivers());
        Assert.assertEquals(1, ag.azureAppPushReceivers().size());
        Assert.assertNotNull(ag.smsReceivers());
        Assert.assertEquals(1, ag.smsReceivers().size());
        Assert.assertNotNull(ag.voiceReceivers());
        Assert.assertEquals(1, ag.voiceReceivers().size());
        Assert.assertNotNull(ag.emailReceivers());
        Assert.assertEquals(2, ag.emailReceivers().size());
        Assert.assertNotNull(ag.webhookReceivers());
        Assert.assertEquals(2, ag.webhookReceivers().size());
        Assert.assertTrue(ag.emailReceivers().get(0).name().startsWith("first"));
        Assert.assertTrue(ag.emailReceivers().get(1).name().startsWith("second"));

        ag.update()
                .defineReceiver("third")
                    .withWebhook("https://www.news.am")
                    .attach()
                .updateReceiver("first")
                    .withoutSms()
                    .parent()
                .withoutReceiver("second")
                .apply();


        Assert.assertEquals(2, ag.webhookReceivers().size());
        Assert.assertEquals(1, ag.emailReceivers().size());
        Assert.assertEquals(0, ag.smsReceivers().size());

        ActionGroup agGet = monitorManager.actionGroups().getById(ag.id());
        Assert.assertEquals("simpleAction", agGet.shortName());
        Assert.assertEquals(2, agGet.webhookReceivers().size());
        Assert.assertEquals(1, agGet.emailReceivers().size());
        Assert.assertEquals(0, agGet.smsReceivers().size());

        monitorManager.actionGroups().enableReceiver(agGet.resourceGroupName(), agGet.name(), agGet.emailReceivers().get(0).name());

        PagedList<ActionGroup> agListByRg = monitorManager.actionGroups().listByResourceGroup(RG_NAME);
        Assert.assertNotNull(agListByRg);
        Assert.assertEquals(1, agListByRg.size());

        PagedList<ActionGroup> agList = monitorManager.actionGroups().list();
        Assert.assertNotNull(agListByRg);
        Assert.assertTrue(agListByRg.size() > 0);

        monitorManager.actionGroups().deleteById(ag.id());
        agListByRg = monitorManager.actionGroups().listByResourceGroup(RG_NAME);
        Assert.assertEquals(0, agListByRg.size());

        resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
    }
}

