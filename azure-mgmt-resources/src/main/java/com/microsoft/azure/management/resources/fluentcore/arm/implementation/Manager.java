/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.arm.implementation;

import com.microsoft.azure.management.RestClient;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * Generic base class for Azure resource managers.
 * @param <T> specific manager type
 * @param <InnerT> inner management client implementation type
 */
public abstract class Manager<T, InnerT> extends ManagerBase implements HasInner<InnerT> {

    protected final InnerT innerManagementClient;

    protected Manager(RestClient restClient, String subscriptionId, InnerT innerManagementClient) {
        super(restClient, subscriptionId);
        this.innerManagementClient = innerManagementClient;
    }

    @Override
    public InnerT inner() {
        return this.innerManagementClient;
    }
}
