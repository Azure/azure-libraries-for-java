/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.implementation;

import com.microsoft.azure.arm.model.implementation.IndexableWrapperImpl;
import com.microsoft.azure.arm.resources.Region;
import com.microsoft.azure.management.resources.Location;

/**
 * The implementation of {@link Location}.
 */
final class LocationImpl extends
        IndexableWrapperImpl<LocationInner>
        implements
        Location {
    LocationImpl(LocationInner innerModel) {
        super(innerModel);
    }

    @Override
    public String subscriptionId() {
        return this.inner().subscriptionId();
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public String displayName() {
        return this.inner().displayName();
    }

    @Override
    public String latitude() {
        return this.inner().latitude();
    }

    @Override
    public String longitude() {
        return this.inner().longitude();
    }

    @Override
    public Region region() {
        return Region.findByLabelOrName(this.name());
    }
}
