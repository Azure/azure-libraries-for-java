/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.compute;

import com.azure.core.annotation.Fluent;
import com.azure.management.resources.fluentcore.arm.Region;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 *  Entry point to virtual machine extension image management.
 */
@Fluent
public interface VirtualMachineExtensionImages {
    /**
     * @return entry point to virtual machine extension image publishers
     */
    VirtualMachinePublishers publishers();

    /**
     * Lists all the resources of the virtual machine extension image in the specified region.
     *
     * @param region the selected Azure region
     * @return a {@link List} of resources
     */
    List<VirtualMachineExtensionImage> listByRegion(Region region);

    /**
     * Lists all the resources of the virtual machine extension image in the specified region.
     *
     * @param region the selected Azure region
     * @return a representation of the deferred computation of this call, returning the requested resources
     */
    Mono<List<VirtualMachineExtensionImage>> listByRegionAsync(Region region);

    /**
     * List all the resources of the virtual machine extension image in the specified region.
     *
     * @param regionName the name of an Azure region
     * @return a {@link List} list of resources
     */
    List<VirtualMachineExtensionImage> listByRegion(String regionName);

    /**
     * List all the resources of the virtual machine extension image in the specified region.
     *
     * @param regionName the name of an Azure region
     * @return a representation of the deferred computation of this call, returning the requested resources
     */
    Mono<List<VirtualMachineExtensionImage>> listByRegionAsync(String regionName);
}