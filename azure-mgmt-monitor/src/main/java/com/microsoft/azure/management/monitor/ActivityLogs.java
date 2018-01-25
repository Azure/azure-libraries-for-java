/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.monitor.implementation.ActivityLogsInner;
import com.microsoft.azure.management.monitor.implementation.MonitorManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import org.joda.time.DateTime;
import rx.Observable;

/**
 * Entry point for Monitor Activity logs API.
 */
public interface ActivityLogs extends
        HasManager<MonitorManager>,
        HasInner<ActivityLogsInner> {

    /**
     * Begins a definition for a new Activity log query.
     *
     * @return the stage of start time filter definition.
     */
    FilterDefinitionStages.WithStartTimeFilter  defineQuery();

    /**
     * The entirety of a Activity Logs query definition.
     */
    interface Definition extends
            FilterDefinitionStages.WithStartTimeFilter,
            FilterDefinitionStages.WithEndFilter,
            FilterDefinitionStages.WithFieldFilter,
            FilterDefinitionStages.WithSelect,
            FilterDefinitionStages.WithExecute {
    }

    /**
     * Grouping of Activity log query stages.
     */
    interface FilterDefinitionStages {

        /**
         * The stage of a Activity Log query allowing to specify start time filter.
         */
        interface WithStartTimeFilter {
            /**
             * Sets the start time for Activity Log query filter.
             *
             * @param startTime specifies start time of cut off filter.
             * @return the stage of end time filter definition.
             */
            WithEndFilter startingFrom(DateTime startTime);
        }

        /**
         * The stage of a Activity Log query allowing to specify end time filter.
         */
        interface WithEndFilter {
            /**
             * Sets the end time for Activity Log query filter.
             *
             * @param endTime specifies end time of cut off filter.
             * @return the stage of optional query parameter definition and query execution.
             */
            WithFieldFilter endsBefore(DateTime endTime);
        }

        /**
         * The stage of a Activity Log query allowing to specify data fields in the server response.
         */
        interface WithFieldFilter {
            /**
             * Selects data fields that will be populated in the server response.
             *
             * @param responseProperties field names in the server response.
             * @return the stage of Activity log filtering by type and query execution.
             */
            WithSelect withResponseProperties(EventDataPropertyName... responseProperties);

            /**
             * Sets the server response to include all the available properties.
             *
             * @return the stage of Activity log filtering by type and query execution.
             */
            WithSelect withAllPropertiesInResponse();
        }

        /**
         * The stage of the Activity log filtering by type and query execution.
         */
        interface WithSelect extends
                WithExecute {

            /**
             * Filters events for a given resource group.
             *
             * @param resourceGroupName Specifies resource group name.
             * @return the stage of Activity log filtering by type and query execution.
             */
            WithExecute filterByResourceGroup(String resourceGroupName);

            /**
             * Filters events for a given resource.
             *
             * @param resourceId Specifies resource Id.
             * @return the stage of Activity log filtering by type and query execution.
             */
            WithExecute filterByResource(String resourceId);

            /**
             * Filters events for a given resource provider.
             *
             * @param resourceProviderName Specifies resource provider.
             * @return the stage of Activity log filtering by type and query execution.
             */
            WithExecute filterByResourceProvider(String resourceProviderName);

            /**
             * Filters events for a given correlation id.
             *
             * @param correlationId Specifies correlation id.
             * @return the stage of Activity log filtering by type and query execution.
             */
            WithExecute filterByCorrelationId(String correlationId);
        }

        /**
         * The stage of the Activity log query execution.
         */
        interface WithExecute {
            /**
             * Executes the query.
             *
             * @return Activity Log events received after query execution.
             */
            PagedList<EventData> execute();

            /**
             * Executes the query.
             *
             * @return a representation of the deferred computation of Activity Log query call.
             */
            Observable<EventData> executeAsync();
        }
    }
}