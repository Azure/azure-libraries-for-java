/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.fluentcore.arm;

import com.microsoft.rest.v2.policy.RequestPolicyFactory;

import java.net.Proxy;

/**
 * The base interface for allowing configurations to be made on the HTTP client.
 *
 * @param <T> the actual type of the interface extending this interface
 */
public interface AzureConfigurable<T extends AzureConfigurable<T>> {

    /**
     * Add a RequestPolicyFactory to the end of the HTTP pipeline.
     *
     * @param factory the factory producing the request policy to use
     * @return the configurable object itself
     */
    T withRequestPolicy(RequestPolicyFactory factory);

    /**
     * Sets the proxy for the HTTP client.
     *
     * @param proxy the proxy to use
     * @return the configurable object itself for chaining
     */
    T withProxy(Proxy proxy);
}
