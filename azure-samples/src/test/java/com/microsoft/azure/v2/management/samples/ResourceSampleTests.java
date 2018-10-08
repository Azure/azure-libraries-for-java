/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.samples;

import com.microsoft.azure.v2.management.resources.samples.ManageResourceGroup;
import org.junit.Assert;
import org.junit.Test;

public class ResourceSampleTests extends SamplesTestBase {


    @Test
    public void testManageResourceGroup() {
        Assert.assertTrue(ManageResourceGroup.runSample(azure));
    }

}