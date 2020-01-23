/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.network.model;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.rest.ServiceFuture;
import com.microsoft.rest.ServiceCallback;
import rx.Observable;

/**
 * The base interface for all template interfaces that support update tags operations.
 *
 * @param <T> the type of the resource returned from the update.
 */
@LangDefinition
public interface AppliableWithTags<T> extends UpdatableWithTags.UpdateWithTags<T> {
    /**
     * Execute the update request.
     *
     * @return the updated resource
     */
    @Method
    T applyTags();

    /**
     * Execute the update request asynchronously.
     *
     * @return the handle to the REST call
     */
    @Method
    Observable<T> applyTagsAsync();

    /**
     * Execute the update request asynchronously.
     *
     * @param callback the callback for success and failure
     * @return the handle to the REST call
     */
    ServiceFuture<T> applyTagsAsync(ServiceCallback<T> callback);
}
