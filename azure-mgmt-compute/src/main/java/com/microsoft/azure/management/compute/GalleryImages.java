/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.compute.implementation.GalleryImagesInner;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import rx.Completable;
import rx.Observable;

/**
 * Entry point to gallery images management API in Azure.
 */
@Fluent
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
    GalleryImage getByGallery(String resourceGroupName, String galleryName, String galleryImageName);

    /**
     * List images under a gallery.
     *
     * @param resourceGroupName The name of the resource group.
     * @param galleryName The name of the gallery.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    Observable<GalleryImage> listByGalleryAsync(String resourceGroupName, String galleryName);

    /**
     * List images under a gallery.
     *
     * @param resourceGroupName The name of the resource group.
     * @param galleryName The name of the gallery.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the list of images in the gallery
     */
    PagedList<GalleryImage> listByGallery(String resourceGroupName, String galleryName);

    /**
     * Delete a gallery image in a gallery.
     *
     * @param resourceGroupName The name of the resource group.
     * @param galleryName The name of the gallery.
     * @param galleryImageName The name of the gallery image.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the completable for the request
     */
    Completable deleteByGalleryAsync(String resourceGroupName, String galleryName, String galleryImageName);

    /**
     * Delete an image in a gallery.
     *
     * @param resourceGroupName The name of the resource group.
     * @param galleryName The name of the gallery.
     * @param galleryImageName The name of the gallery image.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     */
    void deleteByGallery(String resourceGroupName, String galleryName, String galleryImageName);
}
