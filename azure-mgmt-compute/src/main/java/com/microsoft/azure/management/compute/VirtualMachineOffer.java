/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.compute;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;

/**
 * Represents a virtual machine image offer.
 */
@Fluent
public interface VirtualMachineOffer {
    /**
     * @return the region where this virtual machine image offer is available
     */
    Region region();

    /**
     * @return the publisher of this virtual machine image offer
     */
    VirtualMachinePublisher publisher();

    /**
     * @return the name of the virtual machine image offer
     */
    String name();

    /**
     * @return virtual machine image SKUs available in this offer
     */
    VirtualMachineSkus skus();
}
