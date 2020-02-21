/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.compute.implementation;

import com.azure.management.compute.VirtualMachineExtensionImage;
import com.azure.management.compute.VirtualMachineExtensionImages;
import com.azure.management.compute.VirtualMachinePublishers;
import com.azure.management.resources.fluentcore.arm.Region;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * The implementation for {@link VirtualMachineExtensionImages}.
 */
class VirtualMachineExtensionImagesImpl
        implements VirtualMachineExtensionImages {
    private final VirtualMachinePublishers publishers;

    VirtualMachineExtensionImagesImpl(VirtualMachinePublishers publishers) {
        this.publishers = publishers;
    }

    @Override
    public List<VirtualMachineExtensionImage> listByRegion(Region region) {
        return listByRegion(region.toString());
    }

    @Override
    public List<VirtualMachineExtensionImage> listByRegion(String regionName) {
        return listByRegionAsync(regionName).block();
    }

    @Override
    public Mono<List<VirtualMachineExtensionImage>> listByRegionAsync(Region region) {
        return listByRegionAsync(region.name());
    }

    @Override
    public Mono<List<VirtualMachineExtensionImage>> listByRegionAsync(String regionName) {
        return publishers.listByRegionAsync(regionName)
                .flatMapMany(Flux::fromIterable)
                .flatMap(virtualMachinePublisher -> virtualMachinePublisher.extensionTypes().listAsync()
                        .onErrorResume(e -> Mono.empty())
                        .flatMapMany(Flux::fromIterable)
                        .flatMap(imageType -> imageType.versions().listAsync()
                                .flatMapMany(Flux::fromIterable)
                                .flatMap(imageVersion -> imageVersion.getImageAsync())
                        )
                )
                .collectList()
                .map(list -> Collections.unmodifiableList(list));
    }

    @Override
    public VirtualMachinePublishers publishers() {
        return this.publishers;
    }
}