/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.FileServer;
import com.microsoft.azure.management.batchai.FileServers;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;

/**
 *  Implementation for FileServers.
 */
@LangDefinition
class FileServersImpl
        extends TopLevelModifiableResourcesImpl<
        FileServer,
        FileServerImpl,
        FileServerInner,
        FileServersInner,
        BatchAIManager>
        implements FileServers {

    FileServersImpl(final BatchAIManager batchAIManager) {
        super(batchAIManager.inner().fileServers(), batchAIManager);
    }

    @Override
    public FileServerImpl define(String name) {
        return wrapModel(name);
    }

    // Fluent model create helpers

    @Override
    protected FileServerImpl wrapModel(String name) {
        FileServerInner inner = new FileServerInner();
        return new FileServerImpl(name, inner, super.manager());
    }

    @Override
    protected FileServerImpl wrapModel(FileServerInner inner) {
        if (inner == null) {
            return null;
        }
        return new FileServerImpl(inner.name(), inner, this.manager());
    }
}
