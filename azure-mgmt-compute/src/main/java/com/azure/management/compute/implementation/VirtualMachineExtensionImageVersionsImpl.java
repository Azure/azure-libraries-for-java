/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.compute.implementation;

import com.azure.management.compute.models.VirtualMachineExtensionImageInner;
import com.azure.management.compute.models.VirtualMachineExtensionImagesInner;
import com.azure.management.compute.VirtualMachineExtensionImageType;
import com.azure.management.compute.VirtualMachineExtensionImageVersion;
import com.azure.management.compute.VirtualMachineExtensionImageVersions;
import com.azure.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * The implementation for VirtualMachineExtensionImageVersions.
 */
public class VirtualMachineExtensionImageVersionsImpl
        extends ReadableWrappersImpl<VirtualMachineExtensionImageVersion, VirtualMachineExtensionImageVersionImpl, VirtualMachineExtensionImageInner>
        implements VirtualMachineExtensionImageVersions {
    private final VirtualMachineExtensionImagesInner client;
    private final VirtualMachineExtensionImageType type;

    VirtualMachineExtensionImageVersionsImpl(VirtualMachineExtensionImagesInner client, VirtualMachineExtensionImageType type) {
        this.client = client;
        this.type = type;
    }

    @Override
    public List<VirtualMachineExtensionImageVersion> list() {
        return listAsync().block();
    }

    @Override
    protected VirtualMachineExtensionImageVersionImpl wrapModel(VirtualMachineExtensionImageInner inner) {
        if (inner == null) {
            return null;
        }
        return new VirtualMachineExtensionImageVersionImpl(this.client, this.type, inner);
    }

    @Override
    public Mono<List<VirtualMachineExtensionImageVersion>> listAsync() {
        return client.listVersionsAsync(type.regionName(), type.publisher().name(), type.name())
                .flatMapMany(Flux::fromIterable)
                .map((Function<VirtualMachineExtensionImageInner, VirtualMachineExtensionImageVersion>) this::wrapModel)
                .collectList()
                .map(list -> Collections.unmodifiableList(list));
    }
}