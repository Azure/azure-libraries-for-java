/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.batchai.implementation.JobsInner;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingByName;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingByNameAsync;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasParent;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingById;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingByName;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * Entry point for Batch AI jobs management API in Azure.
 */
@Fluent
@Beta
public interface Jobs extends
        SupportsCreating<BatchAIJob.DefinitionStages.Blank>,
        SupportsListing<BatchAIJob>,
        SupportsGettingByName<BatchAIJob>,
        SupportsGettingById<BatchAIJob>,
        SupportsGettingByNameAsync<BatchAIJob>,
        SupportsDeletingByName,
        SupportsDeletingById,
        HasInner<JobsInner>,
        HasParent<BatchAICluster> {
}
