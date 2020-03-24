/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.containerregistry;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.azure.management.containerregistry.implementation.SourceUploadDefinitionInner;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * The source repository properties for a build task.
 */
@Fluent
public interface SourceUploadDefinition extends HasInner<SourceUploadDefinitionInner> {
    /**
     * @return the URL where the client can upload the source
     */
    String uploadUrl();

    /**
     * @return the the relative path to the source; this is used to submit the subsequent queue build request
     */
    String relativePath();
}
