/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation;

import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.rest.v2.http.HttpPipeline;

/**
 * Generic base class for Azure resource managers.
 * @param <T> specific manager type
 * @param <InnerT> inner management client implementation type
 */
public abstract class Manager<T, InnerT> extends ManagerBase implements HasInner<InnerT> {

    protected final InnerT innerManagementClient;

    protected Manager(HttpPipeline pipeline, String subscriptionId, InnerT innerManagementClient) {
        super(pipeline, subscriptionId);
        this.innerManagementClient = innerManagementClient;
    }

    @Override
    public InnerT inner() {
        return this.innerManagementClient;
    }
}
