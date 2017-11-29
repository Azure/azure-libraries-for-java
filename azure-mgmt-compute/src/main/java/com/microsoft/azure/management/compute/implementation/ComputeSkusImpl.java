/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute.implementation;

import com.google.common.collect.Lists;
import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.compute.ComputeResourceType;
import com.microsoft.azure.management.compute.ComputeSku;
import com.microsoft.azure.management.compute.ComputeSkus;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import com.microsoft.rest.RestException;
import rx.Observable;
import rx.functions.Func1;

import java.io.IOException;
import java.util.List;

/**
 * The implementation for {@link ComputeSkus}.
 */
@LangDefinition
final class ComputeSkusImpl
        extends
        ReadableWrappersImpl<ComputeSku, ComputeSkuImpl, ResourceSkuInner>
        implements
        ComputeSkus {
    private final ComputeManager manager;

    ComputeSkusImpl(ComputeManager computeManager) {
        this.manager = computeManager;
    }

    @Override
    protected ComputeSkuImpl wrapModel(ResourceSkuInner inner) {
        return new ComputeSkuImpl(inner);
    }

    @Override
    public PagedList<ComputeSku> list() {
        return wrapList(this.inner().list());
    }

    @Override
    public Observable<ComputeSku> listAsync() {
        return wrapPageAsync(this.inner().listAsync());
    }

    @Override
    public PagedList<ComputeSku> listByRegion(String regionName) {
        return this.listByRegion(Region.fromName(regionName));
    }

    @Override
    public PagedList<ComputeSku> listByRegion(Region region) {
        return toPagedList(listByRegionAsync(region));
    }

    @Override
    public Observable<ComputeSku> listByRegionAsync(String regionName) {
        return this.listByRegionAsync(Region.fromName(regionName));
    }

    @Override
    public Observable<ComputeSku> listByRegionAsync(final Region region) {
        return this.listAsync()
                .filter(new Func1<ComputeSku, Boolean>() {
                    @Override
                    public Boolean call(ComputeSku computeSku) {
                        return computeSku.regions() != null && computeSku.regions().contains(region);
                    }
                });
    }

    @Override
    public ResourceSkusInner inner() {
        return this.manager.inner().resourceSkus();
    }

    @Override
    public ComputeManager manager() {
        return this.manager;
    }

    @Override
    public PagedList<ComputeSku> listByResourceType(ComputeResourceType resourceType) {
        return toPagedList(listByResourceTypeAsync(resourceType));
    }

    @Override
    public Observable<ComputeSku> listByResourceTypeAsync(final ComputeResourceType resourceType) {
        return this.listAsync()
                .filter(new Func1<ComputeSku, Boolean>() {
                    @Override
                    public Boolean call(ComputeSku computeSku) {
                        return computeSku.resourceType() != null && computeSku.resourceType().equals(resourceType);
                    }
                });
    }

    @Override
    public PagedList<ComputeSku> listbyRegionAndResourceType(Region region, ComputeResourceType resourceType) {
        return toPagedList(listbyRegionAndResourceTypeAsync(region, resourceType));
    }

    @Override
    public Observable<ComputeSku> listbyRegionAndResourceTypeAsync(final Region region, final ComputeResourceType resourceType) {
        return this.listAsync()
                .filter(new Func1<ComputeSku, Boolean>() {
                    @Override
                    public Boolean call(ComputeSku computeSku) {
                        return computeSku.resourceType() != null
                                && computeSku.resourceType().equals(resourceType)
                                && computeSku.regions() != null
                                && computeSku.regions().contains(region);
                    }
                });
    }

    /**
     * Util function to block on an observable and turn that to a paged list with one page.
     *
     * @param skuObservable the observable
     * @param <T> the item type
     * @return a paged list with items collected from the given observable
     */
    private static <T> PagedList<T> toPagedList(final Observable<T> skuObservable) {
        Page<T> singlePage = new Page<T>() {
            @Override
            public List<T> items() {
                return Lists.newArrayList(skuObservable.toBlocking().toIterable());
            }

            @Override
            public String nextPageLink() {
                return null;
            }
        };
        return new PagedList<T>(singlePage) {
            @Override
            public Page<T> nextPage(String s) throws RestException, IOException {
                return null;
            }
        };
    }
}
