/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.network;

import com.azure.core.http.rest.PagedIterable;
import com.azure.management.resources.core.TestUtilities;
import com.azure.management.resources.fluentcore.arm.Region;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class NetworkUsageOperationsTests extends NetworkManagementTest {
    @Test
    public void canListNetworkUsages() throws Exception {
        PagedIterable<NetworkUsage> usages = networkManager.usages().listByRegion(Region.US_EAST);
        Assert.assertTrue(TestUtilities.getPagedIterableSize(usages) > 0);
    }

    @Override
    protected void cleanUpResources() {
    }
}
