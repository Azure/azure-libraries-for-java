/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListingByRegion;

/**
 * Entry point for Batch AI resource usage management API.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_10_0)
public interface BatchAIUsages extends SupportsListingByRegion<BatchAIUsage> {
}