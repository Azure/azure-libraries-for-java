/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.samples;

import com.microsoft.azure.management.cosmosdb.samples.CreateCosmosDBWithKindMongoDB;
import org.junit.Assert;
import org.junit.Test;

public class CosmosDBTests extends SamplesTestBase {

    @Test
    public void testManageContainerInstanceWithMultipleContainerImages() {
        Assert.assertTrue(CreateCosmosDBWithKindMongoDB.runSample(azure, "clientId"));
    }

}
