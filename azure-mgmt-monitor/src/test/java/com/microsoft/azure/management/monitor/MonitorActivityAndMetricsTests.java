/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.monitor.EventDataPropertyName;
import com.microsoft.azure.management.monitor.EventData;
import com.microsoft.azure.management.monitor.MetricCollection;
import com.microsoft.azure.management.monitor.MetricDefinition;
import com.microsoft.azure.management.monitor.ResultType;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MonitorActivityAndMetricsTests extends MonitorManagementTest {
    @Test
    public void canListEventsAndMetrics() throws Exception {
        DateTime recordDateTime = DateTime.parse("2018-01-24T00:07:40.350Z");
        VirtualMachine vm = computeManager.virtualMachines().list().get(0);

        // Metric Definition
        List<MetricDefinition> mt = monitorManager.metricDefinitions().listByResource(vm.id());

        Assert.assertNotNull(mt);

        // Metric
        MetricCollection metrics = mt.get(0).defineQuery()
                .startingFrom(recordDateTime.minusDays(30))
                .endsBefore(recordDateTime)
                .withResultType(ResultType.DATA)
                .execute();

        Assert.assertNotNull(metrics);

        // Activity Logs
        PagedList<EventData> retVal = monitorManager.activityLogs()
                .defineQuery()
                .startingFrom(recordDateTime.minusDays(30))
                .endsBefore(recordDateTime)
                .withResponseProperties(
                        EventDataPropertyName.RESOURCEID,
                        EventDataPropertyName.EVENTTIMESTAMP,
                        EventDataPropertyName.OPERATIONNAME,
                        EventDataPropertyName.EVENTNAME)
                .filterByResource(vm.id())
                .execute();

        Assert.assertNotNull(retVal);
        for (EventData event : retVal) {
            Assert.assertEquals(vm.id().toLowerCase(), event.resourceId().toLowerCase());
            Assert.assertNotNull(event.eventName().localizedValue());
            Assert.assertNotNull(event.operationName().localizedValue());
            Assert.assertNotNull(event.eventTimestamp());

            Assert.assertNull(event.category());
            Assert.assertNull(event.authorization());
            Assert.assertNull(event.caller());
            Assert.assertNull(event.correlationId());
            Assert.assertNull(event.description());
            Assert.assertNull(event.eventDataId());
            Assert.assertNull(event.httpRequest());
            Assert.assertNull(event.level());
        }
    }
}
