/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.arm.collection.SupportsDeletingById;
import com.microsoft.azure.arm.model.HasInner;
import com.microsoft.azure.arm.resources.ResourceId;
import com.microsoft.azure.arm.resources.collection.SupportsDeletingByParent;
import com.microsoft.azure.arm.resources.collection.SupportsGettingById;
import com.microsoft.azure.arm.resources.collection.SupportsGettingByParent;
import com.microsoft.azure.arm.resources.collection.SupportsListingByParent;
import com.microsoft.azure.arm.resources.collection.implementation.CreatableResourcesImpl;
import com.microsoft.azure.arm.resources.models.HasManager;
import com.microsoft.azure.arm.resources.models.HasResourceGroup;
import com.microsoft.azure.arm.resources.models.IndependentChild;
import com.microsoft.azure.arm.resources.models.Resource;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.implementation.ManagerBase;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;
import rx.Observable;

/**
 * Base class for independent child collection class.
 * (Internal use only)
 * @param <T> the individual resource type returned
 * @param <ImplT> the individual resource implementation
 * @param <InnerT> the wrapper inner type
 * @param <InnerCollectionT> the inner type of the collection object
 * @param <ManagerT> the manager type for this resource provider type
 * @param <ParentT> the type of the parent resource
 */
@LangDefinition
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
    public InnerCollectionT inner() {
        return this.innerCollection;
    }

    @Override
    public T getByParent(String resourceGroup, String parentName, String name) {
        return getByParentAsync(resourceGroup, parentName, name).toBlocking().last();
    }

    @Override
    public T getByParent(ParentT parentResource, String name) {
        return getByParentAsync(parentResource, name).toBlocking().last();
    }

    @Override
    public Observable<T> getByParentAsync(ParentT parentResource, String name) {
        return getByParentAsync(parentResource.resourceGroupName(), parentResource.name(), name);
    }

    @Override
    public T getById(String id) {
        return getByIdAsync(id).toBlocking().last();
    }

    @Override
    public Observable<T> getByIdAsync(String id) {
        ResourceId resourceId = ResourceId.fromString(id);
        if (resourceId == null) {
            return null;
        }

        return getByParentAsync(resourceId.resourceGroupName(), resourceId.parent().name(), resourceId.name());
    }

    @Override
    public ServiceFuture<T> getByIdAsync(String id, ServiceCallback<T> callback) {
        return ServiceFuture.fromBody(getByIdAsync(id), callback);
    }

    @Override
    public PagedList<T> listByParent(ParentT parentResource) {
        return listByParent(parentResource.resourceGroupName(), parentResource.name());
    }

    @Override
    public void deleteByParent(String groupName, String parentName, String name) {
        deleteByParentAsync(groupName, parentName, name).await();
    }

    @Override
    public ServiceFuture<Void> deleteByParentAsync(String groupName, String parentName, String name, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(deleteByParentAsync(groupName, parentName, name), callback);
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        ResourceId resourceId = ResourceId.fromString(id);
        return deleteByParentAsync(resourceId.resourceGroupName(), resourceId.parent().name(), resourceId.name());
    }

    @Override
    public ManagerT manager() {
        return this.manager;
    }
}
