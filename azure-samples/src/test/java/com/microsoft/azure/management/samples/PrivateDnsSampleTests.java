/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.samples;

import com.microsoft.azure.management.network.samples.ManagePrivateDns;
import com.microsoft.azure.management.privatedns.v2018_09_01.implementation.privatednsManager;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class PrivateDnsSampleTests extends SamplesTestBase {
    @Test
    @Ignore
    public void testManagePrivateDns() {
        privatednsManager prDnsManager = privatednsManager.authenticate(restClient, azure.subscriptionId());
        Assert.assertTrue(ManagePrivateDns.runSample(azure, prDnsManager));
    }
}
