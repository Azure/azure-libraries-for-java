/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.compute.implementation;

import com.azure.management.compute.models.VirtualMachineImagesInner;
import com.azure.management.compute.VirtualMachineImage;
import com.azure.management.compute.VirtualMachineImagesInSku;
import com.azure.management.compute.VirtualMachineSku;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * The implementation for {@link VirtualMachineImagesInSku}.
 */
class VirtualMachineImagesInSkuImpl implements VirtualMachineImagesInSku {

    private final VirtualMachineImagesInner innerCollection;
    private final VirtualMachineSku sku;

    VirtualMachineImagesInSkuImpl(VirtualMachineSku sku, VirtualMachineImagesInner innerCollection) {
        this.sku = sku;
        this.innerCollection = innerCollection;
    }

    @Override
    public List<VirtualMachineImage> list() {
        return listAsync().block();
    }

    @Override
    public Mono<List<VirtualMachineImage>> listAsync() {
        final VirtualMachineImagesInSkuImpl self = this;
        return innerCollection.listAsync(sku.region().toString(),
                    sku.publisher().name(),
                    sku.offer().name(),
                    sku.name())
                .flatMapMany(Flux::fromIterable)
                .flatMap(resourceInner -> innerCollection.getAsync(self.sku.region().toString(),
                            self.sku.publisher().name(),
                            self.sku.offer().name(),
                            self.sku.name(),
                            resourceInner.name())
                        .map(imageInner -> (VirtualMachineImage)new VirtualMachineImageImpl(self.sku.region(),
                                self.sku.publisher().name(),
                                self.sku.offer().name(),
                                self.sku.name(),
                                resourceInner.name(),
                                imageInner))
                )
                .collectList()
                .map(list -> Collections.unmodifiableList(list));
    }
}
