/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.microsoft.azure.v2.management.sql.RecommendedElasticPoolMetric;
import java.time.OffsetDateTime;

/**
 * Implementation for RecommendedElasticPoolMetric interface.
 */
@LangDefinition
class RecommendedElasticPoolMetricImpl
        extends WrapperImpl<RecommendedElasticPoolMetricInner>
        implements RecommendedElasticPoolMetric {

    protected RecommendedElasticPoolMetricImpl(RecommendedElasticPoolMetricInner innerObject) {
        super(innerObject);
    }

    @Override
    public OffsetDateTime dateTimeProperty() {
        return this.inner().dateTime();
    }

    @Override
    public double dtu() {
        return this.inner().dtu();
    }

    @Override
    public double sizeGB() {
        return this.inner().sizeGB();
    }
}
