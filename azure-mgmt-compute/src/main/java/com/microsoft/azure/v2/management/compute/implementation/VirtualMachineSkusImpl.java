/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.compute.implementation;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.compute.VirtualMachineOffer;
import com.microsoft.azure.v2.management.compute.VirtualMachineSkus;
import com.microsoft.azure.v2.management.compute.VirtualMachineSku;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import io.reactivex.Observable;

/**
 * The implementation for {@link VirtualMachineSkus}.
 */
@LangDefinition
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
    public PagedList<VirtualMachineSku> list() {
        return wrapList(innerCollection.listSkus(
                offer.region().toString(),
                offer.publisher().name(),
                offer.name()));
    }

    @Override
    protected VirtualMachineSkuImpl wrapModel(VirtualMachineImageResourceInner inner) {
        if (inner == null) {
            return null;
        }
        return new VirtualMachineSkuImpl(this.offer, inner.name(), innerCollection);
    }

    @Override
    public Observable<VirtualMachineSku> listAsync() {
        return innerCollection.listSkusAsync(offer.region().toString(), offer.publisher().name(), offer.name())
                .flatMapObservable(list -> Observable.fromIterable(list))
                .map(this::wrapModel);
    }
}
