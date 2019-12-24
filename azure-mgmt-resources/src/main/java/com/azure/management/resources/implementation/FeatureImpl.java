/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.implementation;

import com.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;
import com.azure.management.resources.Feature;
import com.azure.management.resources.models.FeatureResultInner;

/**
 * The implementation of {@link Feature}.
 */
final class FeatureImpl extends
        IndexableWrapperImpl<FeatureResultInner>
        implements
        Feature {

    FeatureImpl(FeatureResultInner innerModel) {
        super(innerModel);
    }

    @Override
    public String getName() {
        return getInner().getName();
    }

    @Override
    public String type() {
        return getInner().getType();
    }

    @Override
    public String state() {
        if (getInner().getProperties() == null) {
            return null;
        }
        return getInner().getProperties().getState();
    }
}
