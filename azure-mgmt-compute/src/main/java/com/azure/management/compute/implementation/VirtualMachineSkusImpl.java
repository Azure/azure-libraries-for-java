/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.compute.implementation;

import com.azure.management.compute.models.VirtualMachineImageResourceInner;
import com.azure.management.compute.models.VirtualMachineImagesInner;
import com.azure.management.compute.VirtualMachineOffer;
import com.azure.management.compute.VirtualMachineSku;
import com.azure.management.compute.VirtualMachineSkus;
import com.azure.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The implementation for {@link VirtualMachineSkus}.
 */
class VirtualMachineSkusImpl
        extends ReadableWrappersImpl<VirtualMachineSku, VirtualMachineSkuImpl, VirtualMachineImageResourceInner>
        implements VirtualMachineSkus {

    private final VirtualMachineImagesInner innerCollection;
    private final VirtualMachineOffer offer;

    VirtualMachineSkusImpl(VirtualMachineOffer offer, VirtualMachineImagesInner innerCollection) {
        this.innerCollection = innerCollection;
        this.offer = offer;
    }

    @Override
    public List<VirtualMachineSku> list() {
        return listAsync().block();
    }

    @Override
    protected VirtualMachineSkuImpl wrapModel(VirtualMachineImageResourceInner inner) {
        if (inner == null) {
            return null;
        }
        return new VirtualMachineSkuImpl(this.offer, inner.name(), innerCollection);
    }

    @Override
    public Mono<List<VirtualMachineSku>> listAsync() {
        return innerCollection.listSkusAsync(
                offer.region().toString(),
                offer.publisher().name(),
                offer.name())
                .map(inners -> {
                    List<VirtualMachineSku> skuList = new ArrayList<>();
                    for (VirtualMachineImageResourceInner inner : inners) {
                        skuList.add(wrapModel(inner));
                    }
                    return Collections.unmodifiableList(skuList);
                });
    }
}
