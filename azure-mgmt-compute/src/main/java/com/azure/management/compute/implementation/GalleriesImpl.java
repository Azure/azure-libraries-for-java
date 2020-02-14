/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.compute.implementation;

import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.azure.management.apigeneration.LangDefinition;
import com.azure.management.compute.Galleries;
import com.azure.management.compute.Gallery;
import com.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.azure.management.resources.fluentcore.arm.collection.implementation.GroupableResourcesImpl;
import com.azure.management.resources.fluentcore.utils.RXMapper;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * The implementation for Galleries.
 */
@LangDefinition
class GalleriesImpl extends GroupableResourcesImpl<Gallery, GalleryImpl, GalleryInner, GalleriesInner, ComputeManager> implements Galleries {
    protected GalleriesImpl(ComputeManager manager) {
        super(manager.inner().galleries(), manager);
    }

    @Override
    protected Observable<GalleryInner> getInnerAsync(String resourceGroupName, String name) {
        GalleriesInner client = this.inner();
        return client.getByResourceGroupAsync(resourceGroupName, name);
    }

    @Override
    protected Completable deleteInnerAsync(String resourceGroupName, String name) {
        GalleriesInner client = this.inner();
        return client.deleteAsync(resourceGroupName, name).toCompletable();
    }

    @Override
    public Observable<String> deleteByIdsAsync(Collection<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Observable.empty();
        }
        Collection<Observable<String>> observables = new ArrayList<>();
        for (String id : ids) {
            final String resourceGroupName = ResourceUtils.groupFromResourceId(id);
            final String name = ResourceUtils.nameFromResourceId(id);
            Observable<String> o = RXMapper.map(this.inner().deleteAsync(resourceGroupName, name), id);
            observables.add(o);
        }
        return Observable.mergeDelayError(observables);
    }

    @Override
    public Observable<String> deleteByIdsAsync(String...ids) {
        return this.deleteByIdsAsync(new ArrayList<String>(Arrays.asList(ids)));
    }

    @Override
    public void deleteByIds(Collection<String> ids) {
        if (ids != null && !ids.isEmpty()) {
            this.deleteByIdsAsync(ids).toBlocking().last();
        }
    }

    @Override
    public void deleteByIds(String...ids) {
        this.deleteByIds(new ArrayList<String>(Arrays.asList(ids)));
    }

    @Override
    public PagedList<Gallery> listByResourceGroup(String resourceGroupName) {
        GalleriesInner client = this.inner();
        return this.wrapList(client.listByResourceGroup(resourceGroupName));
    }

    @Override
    public Observable<Gallery> listByResourceGroupAsync(String resourceGroupName) {
        GalleriesInner client = this.inner();
        return client.listByResourceGroupAsync(resourceGroupName)
        .flatMapIterable(new Func1<Page<GalleryInner>, Iterable<GalleryInner>>() {
            @Override
            public Iterable<GalleryInner> call(Page<GalleryInner> page) {
                return page.items();
            }
        })
        .map(new Func1<GalleryInner, Gallery>() {
            @Override
            public Gallery call(GalleryInner inner) {
                return wrapModel(inner);
            }
        });
    }

    @Override
    public PagedList<Gallery> list() {
        GalleriesInner client = this.inner();
        return this.wrapList(client.list());
    }

    @Override
    public Observable<Gallery> listAsync() {
        GalleriesInner client = this.inner();
        return client.listAsync()
        .flatMapIterable(new Func1<Page<GalleryInner>, Iterable<GalleryInner>>() {
            @Override
            public Iterable<GalleryInner> call(Page<GalleryInner> page) {
                return page.items();
            }
        })
        .map(new Func1<GalleryInner, Gallery>() {
            @Override
            public Gallery call(GalleryInner inner) {
                return wrapModel(inner);
            }
        });
    }

    @Override
    public GalleryImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    protected GalleryImpl wrapModel(GalleryInner inner) {
        return new GalleryImpl(inner.name(), inner, manager());
    }

    @Override
    protected GalleryImpl wrapModel(String name) {
        return new GalleryImpl(name, new GalleryInner(), this.manager());
    }
}
