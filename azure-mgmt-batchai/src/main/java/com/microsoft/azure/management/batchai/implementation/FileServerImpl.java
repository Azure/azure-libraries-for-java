/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.FileServer;
import com.microsoft.azure.management.batchai.FileServerReference;
import com.microsoft.azure.management.batchai.ResourceId;
import com.microsoft.azure.management.batchai.model.HasMountVolumes;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

/**
 * Represents file server reference.
 */
@LangDefinition
class FileServerImpl<ParentT> extends IndexableWrapperImpl<FileServerReference>
        implements
        FileServer,
        FileServer.Definition<ParentT> {
    private HasMountVolumes parent;

    FileServerImpl(FileServerReference inner, HasMountVolumes parent) {
        super(inner);
        this.parent = parent;
    }

    @Override
    public FileServerImpl withRelativeMountPath(String mountPath) {
        inner().withRelativeMountPath(mountPath);
        return this;
    }

    @Override
    public ParentT attach() {
        this.parent.attachFileServer(this);
        return (ParentT) parent;
    }

    @Override
    public FileServerImpl withMountOptions(String mountOptions) {
        inner().withMountOptions(mountOptions);
        return this;
    }

    @Override
    public FileServerImpl withFileServerId(String fileServerId) {
        inner().withFileServer(new ResourceId().withId(fileServerId));
        return this;
    }

    @Override
    public DefinitionStages.WithAttach<ParentT> withSourceDirectory(String sourceDirectory) {
        inner().withSourceDirectory(sourceDirectory);
        return this;
    }
}