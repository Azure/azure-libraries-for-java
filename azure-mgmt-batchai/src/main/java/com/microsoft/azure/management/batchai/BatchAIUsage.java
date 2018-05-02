/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.batchai.implementation.UsageInner;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * An immutable client-side representation of an Azure Batch AI resource usage info object.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_10_0)
public interface BatchAIUsage extends HasInner<UsageInner> {

    /**
     * @return the unit of usage measurement
     */
    String unit();

    /**
     * @return the current count of the allocated resources in the subscription
     */
    int currentValue();

    /**
     * @return the maximum count of the resources that can be allocated in the
     * subscription
     */
    long limit();

    /**
     * @return the name of the type of usage
     */
    UsageName name();
}
