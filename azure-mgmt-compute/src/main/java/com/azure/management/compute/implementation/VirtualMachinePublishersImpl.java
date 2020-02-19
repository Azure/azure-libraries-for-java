/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.compute.implementation;

import com.azure.management.compute.models.VirtualMachineExtensionImagesInner;
import com.azure.management.compute.models.VirtualMachineImageResourceInner;
import com.azure.management.compute.models.VirtualMachineImagesInner;
import com.azure.management.compute.VirtualMachinePublisher;
import com.azure.management.compute.VirtualMachinePublishers;
import com.azure.management.resources.fluentcore.arm.Region;
import com.azure.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * The implementation for {@link VirtualMachinePublishers}.
 */
class VirtualMachinePublishersImpl
        extends ReadableWrappersImpl<VirtualMachinePublisher, VirtualMachinePublisherImpl, VirtualMachineImageResourceInner>
        implements VirtualMachinePublishers {

    private final VirtualMachineImagesInner imagesInnerCollection;
    private final VirtualMachineExtensionImagesInner extensionsInnerCollection;

    VirtualMachinePublishersImpl(VirtualMachineImagesInner imagesInnerCollection, VirtualMachineExtensionImagesInner extensionsInnerCollection) {
        this.imagesInnerCollection = imagesInnerCollection;
        this.extensionsInnerCollection = extensionsInnerCollection;
    }

    @Override
    public List<VirtualMachinePublisher> listByRegion(Region region) {
        return listByRegion(region.toString());
    }

    @Override
    protected VirtualMachinePublisherImpl wrapModel(VirtualMachineImageResourceInner inner) {
        if (inner == null) {
            return null;
        }
        return new VirtualMachinePublisherImpl(Region.fromName(inner.location()),
                inner.name(),
                this.imagesInnerCollection,
                this.extensionsInnerCollection);
    }

    @Override
    public List<VirtualMachinePublisher> listByRegion(String regionName) {
        return listByRegionAsync(regionName).block();
    }

    @Override
    public Mono<List<VirtualMachinePublisher>> listByRegionAsync(Region region) {
        return listByRegionAsync(region.name());
    }

    @Override
    public Mono<List<VirtualMachinePublisher>> listByRegionAsync(String regionName) {
        return imagesInnerCollection.listPublishersAsync(regionName)
                .flatMapMany(Flux::fromIterable)
                .map(this::wrapModel)
                .collectList()
                .map(list -> Collections.unmodifiableList(list));
    }
}