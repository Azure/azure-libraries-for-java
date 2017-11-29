/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.compute.implementation.ComputeManager;
import com.microsoft.azure.management.compute.implementation.ResourceSkusInner;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListingByRegion;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import rx.Observable;


/**
 * Entry point to compute service SKUs.
 */
@Fluent
@Beta // TODO Add since v1.5 param
public interface ComputeSkus
        extends SupportsListing<ComputeSku>,
        SupportsListingByRegion<ComputeSku>,
        HasInner<ResourceSkusInner>,
        HasManager<ComputeManager> {
    /**
     * Lists all the skus with the specified resource type.
     *
     * @param resourceType the compute resource type
     * @return the skus list
     */
    PagedList<ComputeSku> listByResourceType(ComputeResourceType resourceType);

    /**
     * Lists all the skus with the specified resource type.
     *
     * @param resourceType the compute resource type
     * @return an observable that emits skus
     */
    Observable<ComputeSku> listByResourceTypeAsync(ComputeResourceType resourceType);

    /**
     * Lists all the skus with the specified resource type in the given region.
     *
     * @param region the region
     * @param resourceType the resource type
     * @return the skus list
     */
    PagedList<ComputeSku> listbyRegionAndResourceType(Region region, ComputeResourceType resourceType);

    /**
     * Lists all the skus with the specified resource type in the given region.
     *
     * @param region the region
     * @param resourceType the resource type
     * @return an observable that emits skus
     */
    Observable<ComputeSku> listbyRegionAndResourceTypeAsync(Region region, ComputeResourceType resourceType);
}
