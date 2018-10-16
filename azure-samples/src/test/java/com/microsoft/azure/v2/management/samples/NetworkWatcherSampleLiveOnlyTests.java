/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.samples;

import com.microsoft.azure.v2.management.network.samples.ManageNetworkWatcher;
import com.microsoft.azure.v2.management.resources.core.TestBase;
import org.junit.Assert;
import org.junit.Test;

public class NetworkWatcherSampleLiveOnlyTests extends SamplesTestBase {
    public NetworkWatcherSampleLiveOnlyTests() {
        super(TestBase.RunCondition.LIVE_ONLY);
    }

    @Test
    public void testManageNetworkWatcher() {
        Assert.assertTrue(ManageNetworkWatcher.runSample(azure));
    }
}
