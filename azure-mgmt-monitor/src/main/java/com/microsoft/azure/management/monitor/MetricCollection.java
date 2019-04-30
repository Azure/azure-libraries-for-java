/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.apigeneration.LangMethodDefinition;
import com.microsoft.azure.management.monitor.implementation.ResponseInner;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import org.joda.time.Period;

import java.util.List;

/**
 * The MetricCollection representing wrapper over ResponseInner type.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Monitor.Fluent.Models")
public interface MetricCollection
        extends HasInner<ResponseInner> {
    /**
     * Get the namespace value.
     *
     * @return the namespace value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String namespace();

    /**
     * Get the resource region value.
     *
     * @return the resource region value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String resourceRegion();

    /**
     * Get the cost value.
     *
     * @return the cost value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    Double cost();

    /**
     * Get the timespan value.
     *
     * @return the timespan value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String timespan();

    /**
     * Get the interval value.
     *
     * @return the interval value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    Period interval();

    /**
     * Get the metric collection value.
     *
     * @return the metric collection value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    List<Metric> metrics();
}
