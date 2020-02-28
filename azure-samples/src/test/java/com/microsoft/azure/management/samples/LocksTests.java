/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.samples;

import com.microsoft.azure.management.locks.samples.ManageLocks;

import org.junit.Assert;
import org.junit.Test;

public class LocksTests extends SamplesTestBase {

    @Test
    public void testManageLocks() {
        Assert.assertTrue(ManageLocks.runSample(azure));
    }
}
