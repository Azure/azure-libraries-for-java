/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.samples;

import com.microsoft.azure.v2.management.compute.samples.ConvertVirtualMachineToManagedDisks;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class ComputeSampleTests extends SamplesTestBase {

    @Test
    public void testConvertVirtualMachineToManagedDisks() {
        Assert.assertTrue(ConvertVirtualMachineToManagedDisks.runSample(azure));
    }
}