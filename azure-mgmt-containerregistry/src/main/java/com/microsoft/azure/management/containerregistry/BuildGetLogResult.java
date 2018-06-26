/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.containerregistry.implementation.BuildGetLogResultInner;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * An object that represents a BuildGetLogResult.
 */
@Fluent
@Beta(Beta.SinceVersion.V2_0_0)
public interface BuildGetLogResult extends
    HasInner<BuildGetLogResultInner> {

    /**
     * @return the link to logs for a azure container registry build.
     */
    @Method
    String logLink();
}
