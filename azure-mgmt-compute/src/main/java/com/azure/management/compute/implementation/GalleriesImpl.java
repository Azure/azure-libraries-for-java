/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.compute.implementation;

import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedIterable;
import com.azure.management.compute.models.GalleriesInner;
import com.azure.management.compute.models.GalleryInner;
import com.azure.management.compute.Galleries;
import com.azure.management.compute.Gallery;
import com.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.azure.management.resources.fluentcore.arm.collection.implementation.GroupableResourcesImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * The implementation for Galleries.
 */
class GalleriesImpl extends GroupableResourcesImpl<Gallery, GalleryImpl, GalleryInner, GalleriesInner, ComputeManager> implements Galleries {
    protected GalleriesImpl(ComputeManager manager) {
        super(manager.inner().galleries(), manager);
    }

    @Override
    protected Mono<GalleryInner> getInnerAsync(String resourceGroupName, String name) {
        return inner().getByResourceGroupAsync(resourceGroupName, name);
    }

    @Override
    protected Mono<Void> deleteInnerAsync(String resourceGroupName, String name) {
        return inner().deleteAsync(resourceGroupName, name);
    }

    @Override
    public Flux<Void> deleteByIdsAsync(Collection<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Flux.empty();
        }
        return Flux.fromStream(ids.stream())
                .flatMap(id -> inner().deleteAsync(
                        ResourceUtils.groupFromResourceId(id),
                        ResourceUtils.nameFromResourceId(id)
                ));
    }

    @Override
    public Flux<Void> deleteByIdsAsync(String...ids) {
        return this.deleteByIdsAsync(new ArrayList<>(Arrays.asList(ids)));
    }

    @Override
    public void deleteByIds(Collection<String> ids) {
        if (ids != null && !ids.isEmpty()) {
            this.deleteByIdsAsync(ids).last().block();
        }
    }

    @Override
    public void deleteByIds(String...ids) {
        this.deleteByIds(new ArrayList<>(Arrays.asList(ids)));
    }

    @Override
    public PagedIterable<Gallery> listByResourceGroup(String resourceGroupName) {
        return this.wrapList(inner().listByResourceGroup(resourceGroupName));
    }

    @Override
    public PagedFlux<Gallery> listByResourceGroupAsync(String resourceGroupName) {
        return inner().listByResourceGroupAsync(resourceGroupName)
                .mapPage(this::wrapModel);
    }

    @Override
    public PagedIterable<Gallery> list() {
        return this.wrapList(inner().list());
    }

    @Override
    public PagedFlux<Gallery> listAsync() {
        return inner().listAsync()
                .mapPage(this::wrapModel);
    }

    @Override
    public GalleryImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    protected GalleryImpl wrapModel(GalleryInner inner) {
        return new GalleryImpl(inner.getName(), inner, manager());
    }

    @Override
    protected GalleryImpl wrapModel(String name) {
        return new GalleryImpl(name, new GalleryInner(), this.manager());
    }
}
