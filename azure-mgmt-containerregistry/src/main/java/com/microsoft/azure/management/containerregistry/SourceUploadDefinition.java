/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.containerregistry.implementation.SourceUploadDefinitionInner;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * An object that represents a source upload definition.
 */
@Fluent
@Beta(Beta.SinceVersion.V2_0_0)
public interface SourceUploadDefinition extends
    HasInner<SourceUploadDefinitionInner> {

    /**
     * @return the URL where the client can upload the source
     */
    @Method
    String uploadUrl();

    /**
     * @return the relative path to the source. This is used to submit the subsequent queue build request
     */
    @Method
    String relativePath();
}
