/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerregistry.BuildGetLogResult;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

/**
 * Implementation for BuildGetLogResult.
 */
@LangDefinition
public class BuildGetLogResultImpl
    extends WrapperImpl<BuildGetLogResultInner>
    implements BuildGetLogResult {

    /**
     * Creates an instance of the BuildGetLogResult object.
     *
     * @param inner the inner object
     */
    BuildGetLogResultImpl(BuildGetLogResultInner inner) {
        super(inner);
    }

    @Override
    public String logLink() {
        return this.inner().logLink();
    }
}
