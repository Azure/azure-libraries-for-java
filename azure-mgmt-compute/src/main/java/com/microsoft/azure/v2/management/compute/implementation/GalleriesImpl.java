/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.compute.implementation;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.compute.Galleries;
import com.microsoft.azure.v2.management.compute.Gallery;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.GroupableResourcesImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * The implementation for Galleries.
 */
@LangDefinition
class GalleriesImpl extends GroupableResourcesImpl<Gallery, GalleryImpl, GalleryInner, GalleriesInner, ComputeManager> implements Galleries {
    protected GalleriesImpl(ComputeManager manager) {
        super(manager.inner().galleries(), manager);
    }

    @Override
    protected Maybe<GalleryInner> getInnerAsync(String resourceGroupName, String name) {
        GalleriesInner client = this.inner();
        return client.getByResourceGroupAsync(resourceGroupName, name);
    }

    @Override
    protected Completable deleteInnerAsync(String resourceGroupName, String name) {
        GalleriesInner client = this.inner();
        return client.deleteAsync(resourceGroupName, name).flatMapCompletable(o -> Completable.complete());
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

            Observable<String> o = this.inner().deleteAsync(resourceGroupName, name)
                    .flatMapCompletable(d -> Completable.complete())
                    // on success deleteAsync returns Maybe.empty() hence control will never be in the
                    // // lambda parameter of flatMapCompletable. It is necessary to do flatMapCompletable
                    // so that flow can continue with below stream emitting string type (different from
                    // Void).
                    .andThen(Observable.just(id));
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
            this.deleteByIdsAsync(ids).blockingLast();
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
                .flatMapIterable(page -> page.items())
                .map(this::wrapModel);
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
                .flatMapIterable(page -> page.items())
                .map(this::wrapModel);
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
