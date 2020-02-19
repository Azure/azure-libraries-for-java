/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.samples;

import com.microsoft.azure.management.batch.samples.ManageBatchAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BatchSampleTests extends SamplesTestBase {
    @Test
    public void testManageBatchAccount() {
        Assertions.assertTrue(ManageBatchAccount.runSample(azure));
    }
}
