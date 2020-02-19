/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.samples;

import com.microsoft.azure.management.eventhub.samples.ManageEventHub;
import com.microsoft.azure.management.eventhub.samples.ManageEventHubEvents;
import com.microsoft.azure.management.eventhub.samples.ManageEventHubGeoDisasterRecovery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class EventHubTests extends SamplesTestBase {
    @Test
    public void testManageEventHubGeoDisasterRecovery() {
        Assertions.assertTrue(ManageEventHubGeoDisasterRecovery.runSample(azure));
    }

    @Test
    @Disabled("Sample uses storage dataplane api which cannot be recorded")
    public void testManageEventHub() {
        Assertions.assertTrue(ManageEventHub.runSample(azure));
    }

    @Test
    public void testManageEventHubEvents() {
        Assertions.assertTrue(ManageEventHubEvents.runSample(azure));
    }
}
