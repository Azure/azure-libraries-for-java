/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.compute;

import com.azure.core.annotation.Fluent;
import com.azure.management.resources.fluentcore.arm.Region;
import com.azure.management.resources.fluentcore.collection.SupportsListingByRegion;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 *  Entry point to virtual machine image management API.
 */
@Fluent
public interface VirtualMachineImages {
    /**
     * Gets a virtual machine image.
     *
     * @param region the region
     * @param publisherName publisher name
     * @param offerName offer name
     * @param skuName SKU name
     * @param version version name
     * @return the virtual machine image
     */
    VirtualMachineImage getImage(Region region, String publisherName, String offerName, String skuName, String version);

    /**
     * Gets a virtual machine image.
     *
     * @param region the region
     * @param publisherName publisher name
     * @param offerName offer name
     * @param skuName SKU name
     * @param version version name
     * @return the virtual machine image
     */
    VirtualMachineImage getImage(String region, String publisherName, String offerName, String skuName, String version);

    /**
     * @return entry point to virtual machine image publishers
     */
    VirtualMachinePublishers publishers();

    /**
     * Lists all the resources of the virtual machine image in the specified region.
     *
     * @param region the selected Azure region
     * @return a {@link List} of resources
     */
    List<VirtualMachineImage> listByRegion(Region region);

    /**
     * Lists all the resources of the virtual machine image in the specified region.
     *
     * @param region the selected Azure region
     * @return a representation of the deferred computation of this call, returning the requested resources
     */
    Mono<List<VirtualMachineImage>> listByRegionAsync(Region region);

    /**
     * List all the resources of the virtual machine image in the specified region.
     *
     * @param regionName the name of an Azure region
     * @return a {@link List} list of resources
     */
    List<VirtualMachineImage> listByRegion(String regionName);

    /**
     * List all the resources of the virtual machine image in the specified region.
     *
     * @param regionName the name of an Azure region
     * @return a representation of the deferred computation of this call, returning the requested resources
     */
    Mono<List<VirtualMachineImage>> listByRegionAsync(String regionName);
}
