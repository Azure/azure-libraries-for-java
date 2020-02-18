/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.samples;

import com.microsoft.azure.management.trafficmanager.samples.ManageSimpleTrafficManager;
import com.microsoft.azure.management.trafficmanager.samples.ManageTrafficManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TrafficManagerSampleTests extends SamplesTestBase {
    @Test
    public void testManageSimpleTrafficManager() {
        Assertions.assertTrue(ManageSimpleTrafficManager.runSample(azure));
    }

    @Test
    @Disabled("Failing -  The subscription is not registered to use namespace 'Microsoft.DomainRegistration'")
    public void testManageTrafficManager() {
        Assertions.assertTrue(ManageTrafficManager.runSample(azure));
    }
}
