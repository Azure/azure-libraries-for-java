/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.samples;

import com.microsoft.azure.v2.management.storage.samples.ManageStorageAccount;
import org.junit.Assert;
import org.junit.Test;

public class StorageSampleTests extends SamplesTestBase {
    @Test
    public void testManageStorageAccount() {
        Assert.assertTrue(ManageStorageAccount.runSample(azure));
    }

}