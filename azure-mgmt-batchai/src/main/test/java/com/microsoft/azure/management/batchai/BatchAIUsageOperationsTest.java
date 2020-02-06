/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.batchai;

import com.microsoft.azure.management.batchai.implementation.BatchAIManager;
import com.microsoft.azure.management.resources.core.TestBase;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BatchAIUsageOperationsTest extends TestBase {
    public BatchAIUsageOperationsTest() {
        super(TestBase.RunCondition.BOTH);
    }

    private BatchAIManager batchAIManager;

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        batchAIManager = BatchAIManager.authenticate(restClient, defaultSubscription);
    }

    @Override
    protected void cleanUpResources() {
    }

    @Test
    @Ignore("Deprecated")
    public void canListBatchAIUsages() throws Exception {
        List<BatchAIUsage> usages = batchAIManager.usages().listByRegion(Region.EUROPE_WEST);
        Assert.assertTrue(usages.size() > 0);
    }
}
