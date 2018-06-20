/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.batchai.implementation.BatchAIManager;
import com.microsoft.azure.management.batchai.implementation.ClustersInner;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingByNameAsync;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasParent;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingById;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingByName;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * Entry point to Batch AI cluster management API in Azure.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_6_0)
public interface BatchAIClusters extends
        SupportsCreating<BatchAICluster.DefinitionStages.Blank>,
        SupportsListing<BatchAICluster>,
        SupportsGettingById<BatchAICluster>,
        SupportsGettingByNameAsync<BatchAICluster>,
        SupportsDeletingById,
        SupportsDeletingByName,
        HasManager<BatchAIManager>,
        HasInner<ClustersInner>,
        HasParent<BatchAIWorkspace> {
}
