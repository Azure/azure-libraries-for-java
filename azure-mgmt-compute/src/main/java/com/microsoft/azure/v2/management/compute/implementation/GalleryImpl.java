/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.compute.implementation;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.compute.Gallery;
import com.microsoft.azure.v2.management.compute.GalleryImage;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * The implementation for Gallery and its create and update interfaces.
 */
@LangDefinition
class GalleryImpl
        extends GroupableResourceImpl<Gallery, GalleryInner, GalleryImpl, ComputeManager>
        implements Gallery, Gallery.Definition, Gallery.Update {
    GalleryImpl(String name, GalleryInner inner, ComputeManager manager) {
        super(name, inner, manager);
    }

    @Override
    public Observable<Gallery> createResourceAsync() {
        GalleriesInner client = this.manager().inner().galleries();
        return client.createOrUpdateAsync(this.resourceGroupName(), this.name(), this.inner())
            .map(innerToFluentMap(this))
            .toObservable();
    }

    @Override
    public Observable<Gallery> updateResourceAsync() {
        GalleriesInner client = this.manager().inner().galleries();
        return client.createOrUpdateAsync(this.resourceGroupName(), this.name(), this.inner())
            .map(innerToFluentMap(this))
            .toObservable();
    }

    @Override
    protected Maybe<GalleryInner> getInnerAsync() {
        GalleriesInner client = this.manager().inner().galleries();
        return client.getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().id() == null;
    }

    @Override
    public String description() {
        return this.inner().description();
    }

    @Override
    public String uniqueName() {
        return this.inner().identifier().uniqueName();
    }

    @Override
    public String provisioningState() {
        return this.inner().provisioningState();
    }

    @Override
    public Observable<GalleryImage> getImageAsync(String imageName) {
        return this.manager().galleryImages().getByGalleryAsync(this.resourceGroupName(), this.name(), imageName);
    }

    @Override
    public GalleryImage getImage(String imageName) {
        return this.manager().galleryImages().getByGallery(this.resourceGroupName(), this.name(), imageName);
    }

    @Override
    public Observable<GalleryImage> listImagesAsync() {
        return this.manager().galleryImages().listByGalleryAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public PagedList<GalleryImage> listImages() {
        return this.manager().galleryImages().listByGallery(this.resourceGroupName(), this.name());
    }

    @Override
    public GalleryImpl withDescription(String description) {
        this.inner().withDescription(description);
        return this;
    }
}
