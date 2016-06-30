/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.compute.implementation;

import com.microsoft.azure.management.compute.VirtualMachineOffers;
import com.microsoft.azure.management.compute.VirtualMachinePublisher;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;

/**
 * The implementation for {@link VirtualMachinePublisher}.
 */
class VirtualMachinePublisherImpl
        implements VirtualMachinePublisher {
    private final Region location;
    private final String publisher;
    private final VirtualMachineOffers offers;

    VirtualMachinePublisherImpl(Region location, String publisher, VirtualMachineImagesInner client) {
        this.location = location;
        this.publisher = publisher;
        this.offers = new VirtualMachineOffersImpl(client, this);
    }

    @Override
    public Region region() {
        return location;
    }

    @Override
    public String name() {
        return publisher;
    }

    @Override
    public VirtualMachineOffers offers() {
        return this.offers;
    }
}
