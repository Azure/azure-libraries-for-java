/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.compute.implementation;

import com.azure.management.compute.models.VirtualMachineImageResourceInner;
import com.azure.management.compute.models.VirtualMachineImagesInner;
import com.azure.management.compute.VirtualMachineOffer;
import com.azure.management.compute.VirtualMachineOffers;
import com.azure.management.compute.VirtualMachinePublisher;
import com.azure.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The implementation for {@link VirtualMachineOffers}.
 */
class VirtualMachineOffersImpl
        extends ReadableWrappersImpl<VirtualMachineOffer, VirtualMachineOfferImpl, VirtualMachineImageResourceInner>
        implements VirtualMachineOffers {

    private final VirtualMachineImagesInner innerCollection;
    private final VirtualMachinePublisher publisher;

    VirtualMachineOffersImpl(VirtualMachineImagesInner innerCollection, VirtualMachinePublisher publisher) {
        this.innerCollection = innerCollection;
        this.publisher = publisher;
    }

    @Override
    protected VirtualMachineOfferImpl wrapModel(VirtualMachineImageResourceInner inner) {
        if (inner == null) {
            return null;
        }
        return new VirtualMachineOfferImpl(this.publisher, inner.name(), this.innerCollection);
    }

    @Override
    public List<VirtualMachineOffer> list() {
        return listAsync().block();
    }

    @Override
    public Mono<List<VirtualMachineOffer>> listAsync() {
        return innerCollection.listOffersAsync(publisher.region().toString(), publisher.name())
                .map(inners -> {
                    List<VirtualMachineOffer> offerList = new ArrayList<>();
                    for (VirtualMachineImageResourceInner inner : inners) {
                        offerList.add(wrapModel(inner));
                    }
                    return Collections.unmodifiableList(offerList);
                });
    }
}
