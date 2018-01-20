/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.implementation;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.v2.management.resources.PolicyDefinition;
import com.microsoft.azure.v2.management.resources.PolicyDefinitions;
import com.microsoft.azure.v2.management.resources.PolicyType;
import com.microsoft.azure.v2.management.resources.ResourceGroups;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.CreatableWrappersImpl;
import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * The implementation for {@link ResourceGroups} and its parent interfaces.
 */
final class PolicyDefinitionsImpl
        extends CreatableWrappersImpl<PolicyDefinition, PolicyDefinitionImpl, PolicyDefinitionInner>
        implements PolicyDefinitions {
    private final PolicyDefinitionsInner client;

    /**
     * Creates an instance of the implementation.
     *
     * @param innerClient the inner policies client
     */
    PolicyDefinitionsImpl(final PolicyDefinitionsInner innerClient) {
        this.client = innerClient;
    }

    @Override
    public PagedList<PolicyDefinition> list() {
        return wrapList(client.list());
    }

    @Override
    public PolicyDefinitionImpl getByName(String name) {
        return wrapModel(client.get(name));
    }

    @Override
    public Completable deleteByIdAsync(String name) {
        return client.deleteAsync(name);
    }

    @Override
    public PolicyDefinitionImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    protected PolicyDefinitionImpl wrapModel(String name) {
        return new PolicyDefinitionImpl(
                new PolicyDefinitionInner().withName(name).withPolicyType(PolicyType.NOT_SPECIFIED).withDisplayName(name),
                client);
    }

    @Override
    protected PolicyDefinitionImpl wrapModel(PolicyDefinitionInner inner) {
        if (inner == null) {
            return null;
        }
        return new PolicyDefinitionImpl(inner, client);
    }

    @Override
    public Observable<PolicyDefinition> listAsync() {
        return wrapPageAsync(client.listAsync());
    }
}
