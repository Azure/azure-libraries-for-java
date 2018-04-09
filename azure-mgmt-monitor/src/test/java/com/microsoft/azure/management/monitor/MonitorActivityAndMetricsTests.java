/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.compute.VirtualMachine;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MonitorActivityAndMetricsTests extends MonitorManagementTest {
    @Test
    public void canListEventsAndMetrics() throws Exception {
        DateTime recordDateTime = DateTime.parse("2018-03-26T00:07:40.350Z");
        VirtualMachine vm = computeManager.virtualMachines().list().get(0);

        // Metric Definition
        List<MetricDefinition> mt = monitorManager.metricDefinitions().listByResource(vm.id());

        Assert.assertNotNull(mt);
        MetricDefinition mDef = mt.get(0);
        Assert.assertNotNull(mDef.metricAvailabilities());
        Assert.assertNotNull(mDef.namespace());
        Assert.assertNotNull(mDef.supportedAggregationTypes());

        // Metric
        MetricCollection metrics = mDef.defineQuery()
                .startingFrom(recordDateTime.minusDays(30))
                .endsBefore(recordDateTime)
                .withResultType(ResultType.DATA)
                .execute();

        Assert.assertNotNull(metrics);
        Assert.assertNotNull(metrics.namespace());
        Assert.assertNotNull(metrics.resourceRegion());
        Assert.assertEquals("Microsoft.Compute/virtualMachines", metrics.namespace());

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

        // List Event Categories
        List<LocalizableString> eventCategories = monitorManager.activityLogs().listEventCategories();
        Assert.assertNotNull(eventCategories);
        Assert.assertFalse(eventCategories.isEmpty());

        // List Activity logs at tenant level is not allowed for the current tenant 
        try {
            monitorManager.activityLogs()
                    .defineQuery()
                    .startingFrom(recordDateTime.minusDays(30))
                    .endsBefore(recordDateTime)
                    .withResponseProperties(
                            EventDataPropertyName.RESOURCEID,
                            EventDataPropertyName.EVENTTIMESTAMP,
                            EventDataPropertyName.OPERATIONNAME,
                            EventDataPropertyName.EVENTNAME)
                    .filterByResource(vm.id())
                    .filterAtTenantLevel()
                    .execute();
        } catch (ErrorResponseException er) {
            // should throw "The client '...' with object id '...' does not have authorization to perform action
            // 'microsoft.insights/eventtypes/values/read' over scope '/providers/microsoft.insights/eventtypes/management'.
            Assert.assertEquals(403, er.response().raw().code());
        }
    }
}
