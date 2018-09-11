/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.compute;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.rest.v2.annotations.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.compute.implementation.GalleryImageVersionsInner;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Entry point to gallery image versions management API in Azure.
 */
@Fluent
@Beta(since = "V1_15_0")
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
    @Beta(since = "V1_15_0")
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
    @Beta(since = "V1_15_0")
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
    @Beta(since = "V1_15_0")
    Observable<GalleryImageVersion> listByGalleryImageAsync(final String resourceGroupName, final String galleryName, final String galleryImageName);

    /**
     * List gallery image versions under a gallery image.
     *
     * @param resourceGroupName The name of the resource group.
     * @param galleryName The name of the gallery.
     * @param galleryImageName The name of the gallery image.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return list of gallery image versions
     */
    @Beta(since = "V1_15_0")
    PagedList<GalleryImageVersion> listByGalleryImage(final String resourceGroupName, final String galleryName, final String galleryImageName);

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
    @Beta(since = "V1_15_0")
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
    @Beta(since = "V1_15_0")
    void deleteByGalleryImage(String resourceGroupName, String galleryName, String galleryImageName, String galleryImageVersionName);
}
