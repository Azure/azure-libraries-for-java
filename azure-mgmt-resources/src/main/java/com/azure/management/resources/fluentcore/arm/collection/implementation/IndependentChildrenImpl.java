/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.fluentcore.arm.collection.implementation;

import com.azure.core.http.rest.PagedIterable;
import com.azure.management.resources.fluentcore.arm.collection.SupportsDeletingByParent;
import com.azure.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.azure.management.resources.fluentcore.arm.collection.SupportsGettingByParent;
import com.azure.management.resources.fluentcore.arm.collection.SupportsListingByParent;
import com.azure.management.resources.fluentcore.arm.ResourceId;
import com.azure.management.resources.fluentcore.arm.implementation.ManagerBase;
import com.azure.management.resources.fluentcore.arm.models.HasManager;
import com.azure.management.resources.fluentcore.arm.models.HasResourceGroup;
import com.azure.management.resources.fluentcore.arm.models.IndependentChild;
import com.azure.management.resources.fluentcore.arm.models.Resource;
import com.azure.management.resources.fluentcore.collection.SupportsDeletingById;
import com.azure.management.resources.fluentcore.model.HasInner;
import reactor.core.publisher.Mono;

/**
 * Base class for independent child collection class.
 * (Internal use only)
 *
 * @param <T>                the individual resource type returned
 * @param <ImplT>            the individual resource implementation
 * @param <InnerT>           the wrapper inner type
 * @param <InnerCollectionT> the inner type of the collection object
 * @param <ManagerT>         the manager type for this resource provider type
 * @param <ParentT>          the type of the parent resource
 */
public abstract class IndependentChildrenImpl<
        T extends IndependentChild<ManagerT>,
        ImplT extends T,
        InnerT,
        InnerCollectionT,
        ManagerT extends ManagerBase,
        ParentT extends Resource & HasResourceGroup>
        extends CreatableResourcesImpl<T, ImplT, InnerT>
        implements
        SupportsGettingById<T>,
        SupportsGettingByParent<T, ParentT, ManagerT>,
        SupportsListingByParent<T, ParentT, ManagerT>,
        SupportsDeletingById,
        SupportsDeletingByParent,
        HasManager<ManagerT>,
        HasInner<InnerCollectionT> {
    protected final InnerCollectionT innerCollection;
    protected final ManagerT manager;

    protected IndependentChildrenImpl(InnerCollectionT innerCollection, ManagerT manager) {
        this.innerCollection = innerCollection;
        this.manager = manager;
    }

    @Override
    public InnerCollectionT getInner() {
        return this.innerCollection;
    }

    @Override
    public T getByParent(String resourceGroup, String parentName, String name) {
        return getByParentAsync(resourceGroup, parentName, name).block();
    }

    @Override
    public T getByParent(ParentT parentResource, String name) {
        return getByParentAsync(parentResource, name).block();
    }

    @Override
    public Mono<T> getByParentAsync(ParentT parentResource, String name) {
        return getByParentAsync(parentResource.getResourceGroupName(), parentResource.getName(), name);
    }

    @Override
    public T getById(String id) {
        return getByIdAsync(id).block();
    }

    @Override
    public Mono<T> getByIdAsync(String id) {
        ResourceId resourceId = ResourceId.fromString(id);
        if (resourceId == null) {
            return null;
        }

        return getByParentAsync(resourceId.resourceGroupName(), resourceId.parent().name(), resourceId.name());
    }

    @Override
    public PagedIterable<T> listByParent(ParentT parentResource) {
        return listByParent(parentResource.getResourceGroupName(), parentResource.getName());
    }

    @Override
    public void deleteByParent(String groupName, String parentName, String name) {
        deleteByParentAsync(groupName, parentName, name).block();
    }

    @Override
    public Mono<Void> deleteByIdAsync(String id) {
        ResourceId resourceId = ResourceId.fromString(id);
        return deleteByParentAsync(resourceId.resourceGroupName(), resourceId.parent().name(), resourceId.name());
    }

    @Override
    public ManagerT getManager() {
        return this.manager;
    }
}