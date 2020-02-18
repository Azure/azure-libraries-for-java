/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.compute;
import com.azure.core.annotation.Fluent;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Entry point to virtual machine SKU images.
 */
@Fluent
public interface VirtualMachineImagesInSku {
    /**
     * Lists all the resources of the virtual machine image.
     *
     * @return A {@link List} of resources
     */
    List<VirtualMachineImage> list();

    /**
     * Lists all the resources of the virtual machine image.
     *
     * @return A {@link Mono<List>} of resources
     */
    Mono<List<VirtualMachineImage>> listAsync();
}
