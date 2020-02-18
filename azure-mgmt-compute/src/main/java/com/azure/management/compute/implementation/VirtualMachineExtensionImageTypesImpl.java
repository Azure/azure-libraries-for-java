/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.compute.implementation;

import com.azure.management.compute.models.VirtualMachineExtensionImageInner;
import com.azure.management.compute.models.VirtualMachineExtensionImagesInner;
import com.azure.management.compute.VirtualMachineExtensionImageType;
import com.azure.management.compute.VirtualMachineExtensionImageTypes;
import com.azure.management.compute.VirtualMachinePublisher;
import com.azure.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * The implementation for VirtualMachineExtensionImageTypes.
 */
class VirtualMachineExtensionImageTypesImpl
        extends ReadableWrappersImpl<VirtualMachineExtensionImageType, VirtualMachineExtensionImageTypeImpl, VirtualMachineExtensionImageInner>
        implements VirtualMachineExtensionImageTypes {
    private final VirtualMachineExtensionImagesInner client;
    private final VirtualMachinePublisher publisher;

    VirtualMachineExtensionImageTypesImpl(VirtualMachineExtensionImagesInner client, VirtualMachinePublisher publisher) {
        this.client = client;
        this.publisher = publisher;
    }

    @Override
    public List<VirtualMachineExtensionImageType> list() {
        return listAsync().block();
    }

    @Override
    protected VirtualMachineExtensionImageTypeImpl wrapModel(VirtualMachineExtensionImageInner inner) {
        if (inner == null) {
            return null;
        }
        return new VirtualMachineExtensionImageTypeImpl(this.client, this.publisher, inner);
    }

    @Override
    public Mono<List<VirtualMachineExtensionImageType>> listAsync() {
        return client.listTypesAsync(this.publisher.region().toString(), this.publisher.name())
                .flatMapMany(Flux::fromIterable)
                .map((Function<VirtualMachineExtensionImageInner, VirtualMachineExtensionImageType>) this::wrapModel)
                .collectList()
                .map(list -> Collections.unmodifiableList(list));
    }
}