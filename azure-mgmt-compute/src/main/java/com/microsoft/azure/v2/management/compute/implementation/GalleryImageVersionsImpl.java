/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.compute.implementation;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.compute.GalleryImageVersion;
import com.microsoft.azure.v2.management.compute.GalleryImageVersions;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.PagedListConverter;
import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * The implementation for GalleryImageVersions.
 */
@LangDefinition
class GalleryImageVersionsImpl extends WrapperImpl<GalleryImageVersionsInner> implements GalleryImageVersions {
    private final ComputeManager manager;

    GalleryImageVersionsImpl(ComputeManager manager) {
        super(manager.inner().galleryImageVersions());
        this.manager = manager;
    }

    public ComputeManager manager() {
        return this.manager;
    }

    @Override
    public GalleryImageVersionImpl define(String name) {
        return wrapModel(name);
    }

    private GalleryImageVersionImpl wrapModel(GalleryImageVersionInner inner) {
        return new GalleryImageVersionImpl(inner, manager());
    }

    private GalleryImageVersionImpl wrapModel(String name) {
        return new GalleryImageVersionImpl(name, this.manager());
    }

    @Override
    public Observable<GalleryImageVersion> listByGalleryImageAsync(final String resourceGroupName, final String galleryName, final String galleryImageName) {
        GalleryImageVersionsInner client = this.inner();
        return client.listByGalleryImageAsync(resourceGroupName, galleryName, galleryImageName)
                .flatMapIterable(page -> page.items())
                .map(this::wrapModel);
    }

    @Override
    public PagedList<GalleryImageVersion> listByGalleryImage(String resourceGroupName, String galleryName, String galleryImageName) {
        return (new PagedListConverter<GalleryImageVersionInner, GalleryImageVersion>() {
            @Override
            public Observable<GalleryImageVersion> typeConvertAsync(final GalleryImageVersionInner inner) {
                return Observable.<GalleryImageVersion>just(wrapModel(inner));
            }
        }).convert(inner().listByGalleryImage(resourceGroupName, galleryName, galleryImageName));
    }

    @Override
    public Observable<GalleryImageVersion> getByGalleryImageAsync(String resourceGroupName, String galleryName, String galleryImageName, String galleryImageVersionName) {
        GalleryImageVersionsInner client = this.inner();
        return client.getAsync(resourceGroupName, galleryName, galleryImageName, galleryImageVersionName)
            .map(this::wrapModel)
            .map(t -> (GalleryImageVersion) t)
            .toObservable();
    }

    @Override
    public GalleryImageVersion getByGalleryImage(String resourceGroupName, String galleryName, String galleryImageName, String galleryImageVersionName) {
        return this.getByGalleryImageAsync(resourceGroupName, galleryName, galleryImageName, galleryImageVersionName).blockingLast();
    }

    @Override
    public Completable deleteByGalleryImageAsync(String resourceGroupName, String galleryName, String galleryImageName, String galleryImageVersionName) {
        GalleryImageVersionsInner client = this.inner();
        return client.deleteAsync(resourceGroupName, galleryName, galleryImageName, galleryImageVersionName);
    }

    @Override
    public void deleteByGalleryImage(String resourceGroupName, String galleryName, String galleryImageName, String galleryImageVersionName) {
        this.deleteByGalleryImageAsync(resourceGroupName, galleryName, galleryImageName, galleryImageVersionName).blockingAwait();
    }
}
