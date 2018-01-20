/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */


package com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation;

import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;

/**
 * Provides access to getting a specific Azure resource based on its resource ID.
 *
 * @param <T> the type of the resource collection
 */
public abstract class SupportsGettingByIdImpl<T> implements SupportsGettingById<T> {
    @Override
    public T getById(String id) {
        return getByIdAsync(id).blockingGet();
    }

    @Override
    public ServiceFuture<T> getByIdAsync(String id, ServiceCallback<T> callback) {
        return ServiceFuture.fromBody(getByIdAsync(id), callback);
    }
}
