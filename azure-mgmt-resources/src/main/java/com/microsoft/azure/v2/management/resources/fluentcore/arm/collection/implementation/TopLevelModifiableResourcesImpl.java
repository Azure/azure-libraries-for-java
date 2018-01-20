/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.v2.Resource;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsBatchDeletion;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsDeletingByResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsGettingByResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsListingByResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation.ManagerBase;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.InnerSupportsDelete;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.InnerSupportsGet;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.InnerSupportsListing;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Base class for resource collection classes.
 * (Internal use only)
 * @param <T> the individual resource type returned
 * @param <ImplT> the individual resource implementation
 * @param <InnerT> the wrapper inner type
 * @param <InnerCollectionT> the inner type of the collection object
 * @param <ManagerT> the manager type for this resource provider type
 */
public abstract class TopLevelModifiableResourcesImpl<
        T extends GroupableResource<ManagerT, InnerT>,
        ImplT extends T,
        InnerT extends Resource,
        InnerCollectionT extends InnerSupportsListing<InnerT> & InnerSupportsGet<InnerT> & InnerSupportsDelete<?>,
        ManagerT extends ManagerBase>
    extends GroupableResourcesImpl<T, ImplT, InnerT, InnerCollectionT, ManagerT>
    implements
        SupportsGettingById<T>,
        SupportsGettingByResourceGroup<T>,
        SupportsDeletingByResourceGroup,
        HasManager<ManagerT>,
        HasInner<InnerCollectionT>,
        SupportsListing<T>,
        SupportsListingByResourceGroup<T>,
        SupportsBatchDeletion {

    protected TopLevelModifiableResourcesImpl(InnerCollectionT innerCollection, ManagerT manager) {
        super(innerCollection, manager);
    }

    @Override
    protected final Maybe<InnerT> getInnerAsync(String resourceGroupName, String name) {
        return this.inner().getByResourceGroupAsync(resourceGroupName, name);
    }

    @Override
    protected Completable deleteInnerAsync(String resourceGroupName, String name) {
        return inner().deleteAsync(resourceGroupName, name).ignoreElement();
    }

    @Override
    public Observable<String> deleteByIdsAsync(String...ids) {
        return this.deleteByIdsAsync(Arrays.asList(ids));
    }

    @Override
    public Observable<String> deleteByIdsAsync(Collection<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Observable.empty();
        }

        return Observable.fromIterable(ids)
                .flatMapSingle(new Function<String, SingleSource<String>>() {
                    @Override
                    public SingleSource<String> apply(String id) throws Exception {
                        final String resourceGroupName = ResourceUtils.groupFromResourceId(id);
                        final String name = ResourceUtils.nameFromResourceId(id);
                        return inner().deleteAsync(resourceGroupName, name).ignoreElement().toSingleDefault(id);
                    }
                }, true);
    }

    @Override
    public void deleteByIds(String...ids) {
        this.deleteByIds(new ArrayList<>(Arrays.asList(ids)));
    }

    @Override
    public void deleteByIds(Collection<String> ids) {
        if (ids != null && !ids.isEmpty()) {
            this.deleteByIdsAsync(ids).blockingSubscribe();
        }
    }

    @Override
    public Observable<T> listAsync() {
        return wrapPageAsync(inner().listAsync());
    }

    @Override
    public Observable<T> listByResourceGroupAsync(String resourceGroupName) {
        return wrapPageAsync(inner().listByResourceGroupAsync(resourceGroupName));
    }

    @Override
    public PagedList<T> list() {
        return wrapList(inner().list());
    }

    @Override
    public PagedList<T> listByResourceGroup(String resourceGroupName) {
        return wrapList(inner().listByResourceGroup(resourceGroupName));
    }
}
