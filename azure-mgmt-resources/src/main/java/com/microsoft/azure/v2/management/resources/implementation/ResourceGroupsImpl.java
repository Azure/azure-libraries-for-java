/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.implementation;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.v2.management.resources.ResourceGroup;
import com.microsoft.azure.v2.management.resources.ResourceGroups;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.CreatableResourcesImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.Utils;
import com.microsoft.rest.v2.ServiceFuture;
import com.microsoft.rest.v2.ServiceCallback;
import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * The implementation for ResourceGroups.
 */
final class ResourceGroupsImpl
        extends CreatableResourcesImpl<ResourceGroup, ResourceGroupImpl, ResourceGroupInner>
        implements ResourceGroups {
    private final ResourceGroupsInner client;
    private final ResourceManagementClientImpl serviceClient;

    /**
     * Creates an instance of the implementation.
     *
     * @param serviceClient the inner resource management client
     */
    ResourceGroupsImpl(final ResourceManagementClientImpl serviceClient) {
        this.serviceClient = serviceClient;
        this.client = serviceClient.resourceGroups();
    }

    @Override
    public PagedList<ResourceGroup> list() {
        return wrapList(client.list());
    }

    @Override
    public PagedList<ResourceGroup> listByTag(String tagName, String tagValue) {
        return wrapList(client.list(Utils.createOdataFilterForTags(tagName, tagValue), null));
    }

    @Override
    public Observable<ResourceGroup> listByTagAsync(String tagName, String tagValue) {
        return wrapPageAsync(client.listAsync(Utils.createOdataFilterForTags(tagName, tagValue), null));
    }

    @Override
    public ResourceGroupImpl getByName(String name) {
        return wrapModel(client.get(name));
    }

    @Override
    public void deleteByName(String name) {
        deleteByNameAsync(name).blockingAwait();
    }

    @Override
    public ServiceFuture<Void> deleteByNameAsync(String name, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(client.deleteAsync(name), callback);
    }

    @Override
    public Completable deleteByNameAsync(String name) {
        return client.deleteAsync(name);
    }

    @Override
    public ResourceGroupImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    public boolean checkExistence(String name) {
        return this.contain(name);
    }

    @Override
    public boolean contain(String name) {
        return client.checkExistence(name);
    }

    @Override
    protected ResourceGroupImpl wrapModel(String name) {
        return new ResourceGroupImpl(
                new ResourceGroupInner().withName(name),
                serviceClient);
    }

    @Override
    protected ResourceGroupImpl wrapModel(ResourceGroupInner inner) {
        if (inner == null) {
            return null;
        }
        return new ResourceGroupImpl(inner, serviceClient);
    }

    @Override
    public void beginDeleteByName(String id) {
        beginDeleteByNameAsync(id).blockingAwait();
    }

    @Override
    public ServiceFuture<Void> beginDeleteByNameAsync(String name, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(beginDeleteByNameAsync(name), callback);
    }

    @Override
    public Completable beginDeleteByNameAsync(String name) {
        return client.beginDeleteAsync(name).firstOrError().toCompletable();
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        return deleteByNameAsync(ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public Observable<ResourceGroup> listAsync() {
        return wrapPageAsync(this.client.listAsync());
    }
}
