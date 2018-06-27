/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;


import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerregistry.SourceUploadDefinition;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

/**
 * Implementation for SourceUploadDefinition.
 */
@LangDefinition
public class SourceUploadDefinitionImpl
    extends WrapperImpl<SourceUploadDefinitionInner>
    implements SourceUploadDefinition {
    /**
     * Creates an instance of the source upload definition object.
     *
     * @param inner the inner object
     */
    SourceUploadDefinitionImpl(SourceUploadDefinitionInner inner) {
        super(inner);
    }

    @Override
    public String uploadUrl() {
        return this.inner().uploadUrl();
    }

    @Override
    public String relativePath() {
        return this.inner().relativePath();
    }
}
