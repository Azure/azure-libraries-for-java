/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.fluentcore.collection;

import com.azure.core.http.rest.PagedIterable;
import com.azure.management.resources.fluentcore.arm.Region;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Provides access to listing Azure resources of a specific type based on their region.
 * <p>
 * (Note: this interface is not intended to be implemented by user code)
 *
 * @param <T> the fluent type of the resource
 */
public interface SupportsSimpleListingByRegion<T> {
    /**
     * Lists all the resources of the specified type in the specified region.
     *
     * @param region the selected Azure region
     * @return a {@link List} of resources
     */
    List<T> listByRegion(Region region);

    /**
     * List all the resources of the specified type in the specified region.
     *
     * @param regionName the name of an Azure region
     * @return a {@link PagedIterable} list of resources
     */
    List<T> listByRegion(String regionName);

    /**
     * Lists all the resources of the specified type in the specified region.
     *
     * @param region the selected Azure region
     * @return a representation of the deferred computation of this call, returning the requested resources
     */
    Mono<List<T>> listByRegionAsync(Region region);

    /**
     * List all the resources of the specified type in the specified region.
     *
     * @param regionName the name of an Azure region
     * @return a representation of the deferred computation of this call, returning the requested resources
     */
    Mono<List<T>> listByRegionAsync(String regionName);
}