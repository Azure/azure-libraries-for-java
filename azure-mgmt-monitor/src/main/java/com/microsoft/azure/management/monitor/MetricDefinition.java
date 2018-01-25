/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.LangMethodDefinition;
import com.microsoft.azure.management.monitor.implementation.MetricDefinitionInner;
import com.microsoft.azure.management.monitor.implementation.MonitorManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import org.joda.time.DateTime;
import org.joda.time.Period;
import rx.Observable;

import java.util.List;

/**
 * The Azure metric definition entries are of type MetricDefinition.
 */
public interface MetricDefinition extends
        HasManager<MonitorManager>,
        HasInner<MetricDefinitionInner> {

    /**
     * Get the resourceId value.
     *
     * @return the resourceId value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String resourceId();

    /**
     * Get the name value.
     *
     * @return the name value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    LocalizableString name();

    /**
     * Get the unit value.
     *
     * @return the unit value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    Unit unit();

    /**
     * Get the primaryAggregationType value.
     *
     * @return the primaryAggregationType value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    AggregationType primaryAggregationType();

    /**
     * Get the metricAvailabilities value.
     *
     * @return the metricAvailabilities value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    List<MetricAvailability> metricAvailabilities();

    /**
     * Get the id value.
     *
     * @return the id value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String id();

    /**
     * Begins a definition for a new resource Metric query.
     *
     * @return the stage of start time filter definition.
     */
    FilterDefinitionStages.WithStartTimeFilter  defineQuery();

    /**
     * The entirety of a Metrics query definition.
     */
    interface Definition extends
            FilterDefinitionStages.WithStartTimeFilter,
            FilterDefinitionStages.WithEndFilter,
            FilterDefinitionStages.WithExecute {
    }

    /**
     * Grouping of Metric query stages.
     */
    interface FilterDefinitionStages {

        /**
         * The stage of a Metric query allowing to specify start time filter.
         */
        interface WithStartTimeFilter {
            /**
             * Sets the start time for Metric query filter.
             *
             * @param startTime specifies start time of cut off filter.
             * @return the stage of end time filter definition.
             */
            WithEndFilter startingFrom(DateTime startTime);
        }

        /**
         * The stage of a Metric query allowing to specify end time filter.
         */
        interface WithEndFilter {
            /**
             * Sets the end time for Metric query filter.
             *
             * @param endTime specifies end time of cut off filter.
             * @return the stage of optional query parameter definition and query execution.
             */
            WithExecute endsBefore(DateTime endTime);
        }

        /**
         * The stage of a Metric query allowing to specify optional filters and execute the query.
         */
        interface WithExecute {
            /**
             * Sets the list of aggregation types to retrieve.
             *
             * @param aggregation The list of aggregation types (comma separated) to retrieve.
             * @return the stage of optional query parameter definition and query execution.
             */
            WithExecute withAggregation(String aggregation);

            /**
             * Sets the interval of the query.
             *
             * @param interval The interval of the query.
             * @return the stage of optional query parameter definition and query execution.
             */
            WithExecute withInterval(Period interval);

            /**
             * Sets the **$filter** that is used to reduce the set of metric data returned.
             * &lt;br&gt;Example:&lt;br&gt;
             * Metric contains metadata A, B and C.&lt;br&gt;
             *
             * - Return all time series of C where A = a1 and B = b1 or b2&lt;br&gt;
             * **$filter=A eq ‘a1’ and B eq ‘b1’ or B eq ‘b2’ and C eq ‘*’**&lt;br&gt;
             *
             * - Invalid variant:&lt;br&gt;
             * **$filter=A eq ‘a1’ and B eq ‘b1’ and C eq ‘*’ or B = ‘b2’**&lt;br&gt;
             * This is invalid because the logical or operator cannot separate two different metadata names.&lt;br&gt;
             *
             * - Return all time series where A = a1, B = b1 and C = c1:&lt;br&gt;
             * **$filter=A eq ‘a1’ and B eq ‘b1’ and C eq ‘c1’**&lt;br&gt;
             *
             * - Return all time series where A = a1&lt;br&gt;
             * **$filter=A eq ‘a1’ and B eq ‘*’ and C eq ‘*’**.
             *
             * @param odataFilter the **$filter** to reduce the set of the returned metric data.
             * @return the stage of optional query parameter definition and query execution.
             */
            WithExecute withOdataFilter(String odataFilter);

            /**
             * Reduces the set of data collected. The syntax allowed depends on the operation. See the operation's description for details. Possible values include: 'Data', 'Metadata'
             *
             * @param resultType the type of metric to retrieve.
             * @return the stage of optional query parameter definition and query execution.
             */
            WithExecute withResultType(ResultType resultType);

            /**
             * Sets the maximum number of records to retrieve.
             * Valid only if $filter is specified.
             * Defaults to 10.
             * @param top the maximum number of records to retrieve.
             * @return the stage of optional query parameter definition and query execution.
             */
            WithExecute selectTop(double top);

            /**
             * Sets the aggregation to use for sorting results and the direction of the sort.
             * Only one order can be specified.
             * Examples: sum asc.
             *
             * @param orderBy the aggregation to use for sorting results and the direction of the sort.
             * @return the stage of optional query parameter definition and query execution.
             */
            WithExecute orderBy(String orderBy);

            /**
             * Executes the query.
             *
             * @return Metric collection received after query execution.
             */
            MetricCollection execute();

            /**
             * Executes the query.
             *
             * @return a representation of the deferred computation of Metric collection query call
             */
            Observable<MetricCollection> executeAsync();
        }
    }
}
