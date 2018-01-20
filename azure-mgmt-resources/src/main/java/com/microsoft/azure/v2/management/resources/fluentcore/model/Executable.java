/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.fluentcore.model;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.rest.v2.ServiceFuture;
import com.microsoft.rest.v2.ServiceCallback;
import io.reactivex.Single;

/**
 * The base interface for all template interfaces that support execute operations.
 *
 * @param <T> the type of result produced by the execution.
 */
@LangDefinition(ContainerName = "ResourceActions", CreateAsyncMultiThreadMethodParam = true)
public interface Executable<T> extends Indexable {
    /**
     * Execute the request.
     *
     * @return execution result object
     */
    @Method
    T execute();

    /**
     * Execute the request asynchronously.
     *
     * @return the handle to the REST call
     */
    @Method
    Single<T> executeAsync();

    /**
     * Execute the request asynchronously.
     *
     * @param callback the callback for success and failure
     * @return the handle to the REST call
     */
    ServiceFuture<T> executeAsync(ServiceCallback<T> callback);
}