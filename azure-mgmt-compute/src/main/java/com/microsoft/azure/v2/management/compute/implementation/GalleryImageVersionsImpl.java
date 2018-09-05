/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.compute.implementation;

import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.compute.GalleryImageVersion;
import com.microsoft.azure.v2.management.compute.GalleryImageVersions;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

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
        .flatMapIterable(new Func1<Page<GalleryImageVersionInner>, Iterable<GalleryImageVersionInner>>() {
            @Override
            public Iterable<GalleryImageVersionInner> call(Page<GalleryImageVersionInner> page) {
                return page.items();
            }
        })
        .map(new Func1<GalleryImageVersionInner, GalleryImageVersion>() {
            @Override
            public GalleryImageVersion call(GalleryImageVersionInner inner) {
                return wrapModel(inner);
            }
        });
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
        .map(new Func1<GalleryImageVersionInner, GalleryImageVersion>() {
            @Override
            public GalleryImageVersion call(GalleryImageVersionInner inner) {
                return wrapModel(inner);
            }
       });
    }

    @Override
    public GalleryImageVersion getByGalleryImage(String resourceGroupName, String galleryName, String galleryImageName, String galleryImageVersionName) {
        return this.getByGalleryImageAsync(resourceGroupName, galleryName, galleryImageName, galleryImageVersionName).toBlocking().last();
    }

    @Override
    public Completable deleteByGalleryImageAsync(String resourceGroupName, String galleryName, String galleryImageName, String galleryImageVersionName) {
        GalleryImageVersionsInner client = this.inner();
        return client.deleteAsync(resourceGroupName, galleryName, galleryImageName, galleryImageVersionName).toCompletable();
    }

    @Override
    public void deleteByGalleryImage(String resourceGroupName, String galleryName, String galleryImageName, String galleryImageVersionName) {
        this.deleteByGalleryImageAsync(resourceGroupName, galleryName, galleryImageName, galleryImageVersionName).await();
    }
}
