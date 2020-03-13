/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.apigeneration.LangMethodDefinition;
import com.azure.management.monitor.implementation.MetricInner;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

import java.util.List;

/**
 * The Azure metric entries are of type Metric.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Monitor.Fluent.Models")
public interface Metric
        extends HasInner<MetricInner> {
    /**
     * Get the metric Id.
     *
     * @return the id value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String id();

    /**
     * Get the resource type of the metric resource.
     *
     * @return the type value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String type();

    /**
     * Get the name and the display name of the metric, i.e. it is localizable string.
     *
     * @return the name value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    LocalizableString name();

    /**
     * Get the unit of the metric. Possible values include: 'Count', 'Bytes', 'Seconds', 'CountPerSecond', 'BytesPerSecond', 'Percent', 'MilliSeconds', 'ByteSeconds', 'Unspecified'.
     *
     * @return the unit value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    Unit unit();

    /**
     * Get the time series returned when a data query is performed.
     *
     * @return the timeseries value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    List<TimeSeriesElement> timeseries();
}
