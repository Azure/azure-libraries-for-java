/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.samples;

import com.microsoft.azure.management.monitor.samples.QueryMetricsAndActivityLogs;
import org.junit.Assert;
import org.junit.Test;

public class MonitorTests extends SamplesTestBase {

    @Test
    public void testQueryMetricsAndActivityLogs() {
        Assert.assertTrue(QueryMetricsAndActivityLogs.runSample(azure));
    }
}
