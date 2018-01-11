/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIFileServer;
import com.microsoft.azure.management.batchai.BatchAIFileServers;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;

/**
 *  Implementation for BatchAIFileServers.
 */
@LangDefinition
class BatchAIFileServersImpl
        extends TopLevelModifiableResourcesImpl<
        BatchAIFileServer,
        BatchAIFileServerImpl,
        FileServerInner,
        FileServersInner,
        BatchAIManager>
        implements BatchAIFileServers {

    BatchAIFileServersImpl(final BatchAIManager batchAIManager) {
        super(batchAIManager.inner().fileServers(), batchAIManager);
    }

    @Override
    public BatchAIFileServerImpl define(String name) {
        return wrapModel(name);
    }

    // Fluent model create helpers

    @Override
    protected BatchAIFileServerImpl wrapModel(String name) {
        FileServerInner inner = new FileServerInner();
        return new BatchAIFileServerImpl(name, inner, super.manager());
    }

    @Override
    protected BatchAIFileServerImpl wrapModel(FileServerInner inner) {
        if (inner == null) {
            return null;
        }
        return new BatchAIFileServerImpl(inner.name(), inner, this.manager());
    }
}
