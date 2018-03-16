/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.sql.implementation.SubscriptionUsageInner;

/**
 * The result of SQL server usages per current subscription.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_8_0)
public interface SqlSubscriptionUsageMetric extends
    Refreshable<SqlSubscriptionUsageMetric>,
    HasId,
    HasName,
    HasInner<SubscriptionUsageInner> {

    /**
     * @return a user-readable name of the metric
     */
    String displayName();

    /**
     * @return the current value of the metric
     */
    double currentValue();

    /**
     * @return the boundary value of the metric
     */
    double limit();

    /**
     * @return the unit of the metric
     */
    String unit();

    /**
     * @return the resource type
     */
    String type();
}
