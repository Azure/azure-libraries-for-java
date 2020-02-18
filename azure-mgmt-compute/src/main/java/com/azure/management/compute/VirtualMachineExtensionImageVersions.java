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
 * Entry point to virtual machine image extension versions.
 */
@Fluent
public interface VirtualMachineExtensionImageVersions {
    /**
     * Lists all the resources of the virtual machine extension image version.
     *
     * @return A {@link List} of resources
     */
    List<VirtualMachineExtensionImageVersion> list();

    /**
     * Lists all the resources of the virtual machine extension image version.
     *
     * @return A {@link Mono<List>} of resources
     */
    Mono<List<VirtualMachineExtensionImageVersion>> listAsync();
}