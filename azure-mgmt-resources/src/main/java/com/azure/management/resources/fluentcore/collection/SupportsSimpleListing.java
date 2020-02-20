/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.fluentcore.collection;

import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Provides access to listing Azure resources of a specific type in a subscription.
 * <p>
 * (Note: this interface is not intended to be implemented by user code)
 *
 * @param <T> the fluent type of the resource
 */
public interface SupportsSimpleListing<T> {
    /**
     * Lists all the resources of the specified type in the currently selected subscription.
     *
     * @return A {@link List} of resources
     */
    List<T> list();

    /**
     * Lists all the resources of the specified type in the currently selected subscription.
     *
     * @return A {@link Mono<List>} of resources
     */
    Mono<List<T>> listAsync();
}
