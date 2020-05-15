/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.compute.implementation.GalleryImageVersionsInner;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import rx.Completable;
import rx.Observable;

/**
 * Entry point to gallery image versions management API in Azure.
 */
@Fluent
public interface GalleryImageVersions extends SupportsCreating<GalleryImageVersion.DefinitionStages.Blank>,
        HasInner<GalleryImageVersionsInner> {
    /**
     * Retrieves information about a gallery image version.
     *
     * @param resourceGroupName The name of the resource group.
     * @param galleryName The name of the gallery.
     * @param galleryImageName The name of the gallery image.
     * @param galleryImageVersionName The name of the gallery image version.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    Observable<GalleryImageVersion> getByGalleryImageAsync(String resourceGroupName, String galleryName, String galleryImageName, String galleryImageVersionName);

    /**
     * Retrieves information about a gallery image version.
     *
     * @param resourceGroupName The name of the resource group.
     * @param galleryName The name of the gallery.
     * @param galleryImageName The name of the gallery image.
     * @param galleryImageVersionName The name of the gallery image version.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the gallery image version resource
     */
    GalleryImageVersion getByGalleryImage(String resourceGroupName, String galleryName, String galleryImageName, String galleryImageVersionName);

    /**
     * List gallery image versions under a gallery image.
     *
     * @param resourceGroupName The name of the resource group.
     * @param galleryName The name of the gallery.
     * @param galleryImageName The name of the gallery image.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    Observable<GalleryImageVersion> listByGalleryImageAsync(String resourceGroupName, String galleryName, String galleryImageName);

    /**
     * List gallery image versions under a gallery image.
     *
     * @param resourceGroupName The name of the resource group.
     * @param galleryName The name of the gallery.
     * @param galleryImageName The name of the gallery image.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return list of gallery image versions
     */
    PagedList<GalleryImageVersion> listByGalleryImage(String resourceGroupName, String galleryName, String galleryImageName);

    /**
     * Delete a gallery image version.
     *
     * @param resourceGroupName The name of the resource group.
     * @param galleryName The name of the gallery.
     * @param galleryImageName The name of the gallery image.
     * @param galleryImageVersionName The name of the gallery image version.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the completable for the request
     */
    Completable deleteByGalleryImageAsync(String resourceGroupName, String galleryName, String galleryImageName, String galleryImageVersionName);

    /**
     * Delete a gallery image version.
     *
     * @param resourceGroupName The name of the resource group.
     * @param galleryName The name of the gallery.
     * @param galleryImageName The name of the gallery image.
     * @param galleryImageVersionName The name of the gallery image version.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     */
    void deleteByGalleryImage(String resourceGroupName, String galleryName, String galleryImageName, String galleryImageVersionName);
}
