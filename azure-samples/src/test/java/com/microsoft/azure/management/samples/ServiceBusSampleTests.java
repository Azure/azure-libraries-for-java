/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.samples;

import com.microsoft.azure.management.servicebus.samples.ServiceBusPublishSubscribeAdvanceFeatures;
import com.microsoft.azure.management.servicebus.samples.ServiceBusPublishSubscribeBasic;
import com.microsoft.azure.management.servicebus.samples.ServiceBusQueueAdvanceFeatures;
import com.microsoft.azure.management.servicebus.samples.ServiceBusQueueBasic;
import com.microsoft.azure.management.servicebus.samples.ServiceBusWithClaimBasedAuthorization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ServiceBusSampleTests extends SamplesTestBase {
    @Test
    public void testServiceBusQueueBasic() {
        Assertions.assertTrue(ServiceBusQueueBasic.runSample(azure));
    }

    @Test
    public void testServiceBusPublishSubscribeBasic() {
        Assertions.assertTrue(ServiceBusPublishSubscribeBasic.runSample(azure));
    }

    @Test
    public void testServiceBusWithClaimBasedAuthorization() {
        Assertions.assertTrue(ServiceBusWithClaimBasedAuthorization.runSample(azure));
    }

    @Test
    public void testServiceBusQueueAdvanceFeatures() {
        Assertions.assertTrue(ServiceBusQueueAdvanceFeatures.runSample(azure));
    }

    @Test
    public void testServiceBusPublishSubscribeAdvanceFeatures() {
        Assertions.assertTrue(ServiceBusPublishSubscribeAdvanceFeatures.runSample(azure));
    }
}
