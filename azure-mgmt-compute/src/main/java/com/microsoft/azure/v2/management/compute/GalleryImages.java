/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.compute;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.rest.v2.annotations.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.compute.implementation.GalleryImagesInner;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Entry point to gallery images management API in Azure.
 */
@Fluent
@Beta(since="V1_15_0")
public interface GalleryImages extends SupportsCreating<GalleryImage.DefinitionStages.Blank>,
        HasInner<GalleryImagesInner> {
    /**
     * Retrieves information about an image in a gallery.
     *
     * @param resourceGroupName The name of the resource group.
     * @param galleryName The name of the gallery.
     * @param galleryImageName The name of the gallery image.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    @Beta(since="V1_15_0")
    Observable<GalleryImage> getByGalleryAsync(String resourceGroupName, String galleryName, String galleryImageName);

    /**
     * Retrieves information about an image in a gallery.
     *
     * @param resourceGroupName The name of the resource group.
     * @param galleryName The name of the gallery.
     * @param galleryImageName The name of the gallery image.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the gallery image
     */
    @Beta(since="V1_15_0")
    GalleryImage getByGallery(String resourceGroupName, String galleryName, String galleryImageName);

    /**
     * List images under a gallery.
     *
     * @param resourceGroupName The name of the resource group.
     * @param galleryName The name of the gallery.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    @Beta(since="V1_15_0")
    Observable<GalleryImage> listByGalleryAsync(final String resourceGroupName, final String galleryName);

    /**
     * List images under a gallery.
     *
     * @param resourceGroupName The name of the resource group.
     * @param galleryName The name of the gallery.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the list of images in the gallery
     */
    @Beta(since="V1_15_0")
    PagedList<GalleryImage> listByGallery(final String resourceGroupName, final String galleryName);

    /**
     * Delete a gallery image in a gallery.
     *
     * @param resourceGroupName The name of the resource group.
     * @param galleryName The name of the gallery.
     * @param galleryImageName The name of the gallery image.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the completable for the request
     */
    @Beta(since="V1_15_0")
    Completable deleteByGalleryAsync(String resourceGroupName, String galleryName, String galleryImageName);

    /**
     * Delete an image in a gallery.
     *
     * @param resourceGroupName The name of the resource group.
     * @param galleryName The name of the gallery.
     * @param galleryImageName The name of the gallery image.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     */
    @Beta(since="V1_15_0")
    void deleteByGallery(String resourceGroupName, String galleryName, String galleryImageName);
}
