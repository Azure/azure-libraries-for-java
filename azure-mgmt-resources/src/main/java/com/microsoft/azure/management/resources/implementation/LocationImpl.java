/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.implementation;

import com.microsoft.azure.management.resources.Location;
import com.microsoft.azure.management.resources.RegionCategory;
import com.microsoft.azure.management.resources.RegionType;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

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
        return this.inner().metadata() == null ? null : this.inner().metadata().latitude();
    }

    @Override
    public String longitude() {
        return this.inner().metadata() == null ? null : this.inner().metadata().longitude();
    }

    @Override
    public RegionType regionType() {
        return this.inner().metadata() == null ? null : this.inner().metadata().regionType();
    }

    @Override
    public RegionCategory regionCategory() {
        return this.inner().metadata() == null ? null : this.inner().metadata().regionCategory();
    }

    @Override
    public String geographyGroup() {
        return this.inner().metadata() == null ? null : this.inner().metadata().geographyGroup();
    }

    @Override
    public String physicalLocation() {
        return this.inner().metadata() == null ? null : this.inner().metadata().physicalLocation();
    }

    @Override
    public Region region() {
        return Region.fromName(this.name());
    }
}
