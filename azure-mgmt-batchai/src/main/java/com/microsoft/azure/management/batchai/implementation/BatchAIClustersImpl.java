/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAICluster;
import com.microsoft.azure.management.batchai.BatchAIClusters;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;

/**
 *  Implementation for BatchAIClusters.
 */
@LangDefinition
class BatchAIClustersImpl
        extends TopLevelModifiableResourcesImpl<
        BatchAICluster,
        BatchAIClusterImpl,
        ClusterInner,
        ClustersInner,
        BatchAIManager>
        implements BatchAIClusters {

    BatchAIClustersImpl(final BatchAIManager batchAIManager) {
        super(batchAIManager.inner().clusters(), batchAIManager);
    }

    @Override
    public BatchAIClusterImpl define(String name) {
        return wrapModel(name);
    }

    // Fluent model create helpers

    @Override
    protected BatchAIClusterImpl wrapModel(String name) {
        ClusterInner inner = new ClusterInner();
        return new BatchAIClusterImpl(name, inner, super.manager());
    }

    @Override
    protected BatchAIClusterImpl wrapModel(ClusterInner inner) {
        if (inner == null) {
            return null;
        }
        return new BatchAIClusterImpl(inner.name(), inner, this.manager());
    }
}
