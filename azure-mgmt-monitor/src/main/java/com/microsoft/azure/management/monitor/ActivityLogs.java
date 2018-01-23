/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.monitor.implementation.MonitorManagementClientImpl;
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
        HasInner<MonitorManagementClientImpl> {

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
            FilterDefinitionStages.WithResponsePropertyDefinition,
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
            WithEndFilter withStartTime(DateTime startTime);
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
            WithFieldFilter withEndTime(DateTime endTime);
        }

        /**
         * The stage of a Activity Log query allowing to specify data fields in the server response.
         */
        interface WithFieldFilter {
            /**
             * Begins a definition of the data fields in the server response.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition defineResponseProperties();

            /**
             * Sets the server response to include all the available properties.
             *
             * @return the stage of Activity log filtering by type and query execution.
             */
            WithSelect withAllPropertiesInResponse();
        }

        /**
         * The stage of optional query parameter definition and query execution.
         */
        interface WithResponsePropertyDefinition {
            /**
             * Selects "Authorization" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withAuthorization();

            /**
             * Selects "Claims" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withClaims();

            /**
             * Selects "CorrelationId" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withCorrelationId();

            /**
             * Selects "Description" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withDescription();

            /**
             * Selects "EventDataId" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withEventDataId();

            /**
             * Selects "EventName" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withEventName();

            /**
             * Selects "EventTimestamp" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withEventTimestamp();

            /**
             * Selects "HttpRequest" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withHttpRequest();

            /**
             * Selects "Level" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withLevel();

            /**
             * Selects "OperationId" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withOperationId();

            /**
             * Selects "OperationName" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withOperationName();

            /**
             * Selects "Properties" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withProperties();

            /**
             * Selects "ResourceGroupName" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withResourceGroupName();

            /**
             * Selects "ResourceProviderName" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withResourceProviderName();

            /**
             * Selects "ResourceId" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withResourceId();

            /**
             * Selects "Status" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withStatus();

            /**
             * Selects "SubmissionTimestamp" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withSubmissionTimestamp();

            /**
             * Selects "SubStatus" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withSubStatus();

            /**
             * Selects "SubscriptionId" filed in the server response fields.
             *
             * @return the stage of the data fields definition.
             */
            WithResponsePropertyDefinition withSubscriptionId();

            /**
             * Applies the current selected response field filter to the query.
             *
             * @return the stage of the data fields definition.
             */
            WithSelect apply();
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