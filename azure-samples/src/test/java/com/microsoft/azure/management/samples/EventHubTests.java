/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.samples;

import com.microsoft.azure.management.eventhub.samples.ManageEventHub;
import com.microsoft.azure.management.eventhub.samples.ManageEventHubEvents;
import com.microsoft.azure.management.eventhub.samples.ManageEventHubGeoDisasterRecovery;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class EventHubTests extends SamplesTestBase {
    @Test
    public void testManageEventHubGeoDisasterRecovery() {
        Assert.assertTrue(ManageEventHubGeoDisasterRecovery.runSample(azure));
    }

    @Test
    @Ignore("Sample uses storage dataplane api which cannot be recorded")
    public void testManageEventHub() {
        Assert.assertTrue(ManageEventHub.runSample(azure));
    }

    @Test
    public void testManageEventHubEvents() {
        Assert.assertTrue(ManageEventHubEvents.runSample(azure));
    }
}
