/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.implementation;

import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.resources.GenericResource;
import com.microsoft.azure.management.resources.GenericResources;
import com.microsoft.azure.management.resources.Provider;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.ResourcesMoveInfo;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.GroupableResourcesImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.List;

/**
 * Implementation of the {@link GenericResources}.
 */
final class GenericResourcesImpl
    extends GroupableResourcesImpl<
        GenericResource,
        GenericResourceImpl,
        GenericResourceInner,
        ResourcesInner,
        ResourceManager>
    implements GenericResources {

    private PagedListConverter<GenericResourceExpandedInner, GenericResource> converter;

    GenericResourcesImpl(final ResourceManager resourceManager) {
        super(resourceManager.inner().resources(), resourceManager);
        converter = new PagedListConverter<GenericResourceExpandedInner, GenericResource>() {
            @Override
            public Observable<GenericResource> typeConvertAsync(GenericResourceExpandedInner inner) {
                return Observable.just((GenericResource) new GenericResourceImpl(inner.name(), inner, manager()));
            }
        };
    }

    @Override
    public PagedList<GenericResource> list() {
        return converter.convert(this.inner().list());
    }

    @Override
    public PagedList<GenericResource> listByResourceGroup(String groupName) {
        return converter.convert(this.manager().inner().resources().listByResourceGroup(groupName));
    }

    @Override
    public PagedList<GenericResource> listByTag(String resourceGroupName, String tagName, String tagValue) {
        return converter.convert(this.manager().inner().resources().listByResourceGroup(resourceGroupName,
                Utils.createOdataFilterForTags(tagName, tagValue), null, null));
    }

    @Override
    public Observable<GenericResource> listByTagAsync(String resourceGroupName, String tagName, String tagValue) {
        return convert(this.manager().inner().resources().listByResourceGroupAsync(resourceGroupName,
                Utils.createOdataFilterForTags(tagName, tagValue), null, null));
    }

    @Override
    public GenericResource.DefinitionStages.Blank define(String name) {
        return new GenericResourceImpl(
                name,
                new GenericResourceInner(),
                this.manager());
    }

    @Override
    public boolean checkExistence(String resourceGroupName, String resourceProviderNamespace, String parentResourcePath, String resourceType, String resourceName, String apiVersion) {
        return this.inner().checkExistence(
                resourceGroupName,
                resourceProviderNamespace,
                parentResourcePath,
                resourceType,
                resourceName,
                apiVersion);
    }

    @Override
    public boolean checkExistenceById(String id) {
        String apiVersion = getApiVersionFromId(id).toBlocking().single();
        return this.inner().checkExistenceById(id, apiVersion);
    }

    @Override
    public GenericResource getById(String id) {
        Provider provider = this.manager().providers().getByName(ResourceUtils.resourceProviderFromResourceId(id));
        String apiVersion = ResourceUtils.defaultApiVersion(id, provider);
        GenericResourceImpl genericResource = wrapModel(this.inner().getById(id, apiVersion));
        return genericResource == null ? null : genericResource.withApiVersion(apiVersion);
    }

    @Override
    public GenericResource get(
            String resourceGroupName,
            String providerNamespace,
            String resourceType,
            String name) {

        PagedList<GenericResource> genericResources = this.listByResourceGroup(resourceGroupName);
        for (GenericResource resource : genericResources) {
            if (resource.name().equalsIgnoreCase(name)
                    && resource.resourceProviderNamespace().equalsIgnoreCase(providerNamespace)
                    && resource.resourceType().equalsIgnoreCase(resourceType)) {
                return resource;
            }
        }
        return null;
    }

    @Override
    public GenericResource get(
            String resourceGroupName,
            String resourceProviderNamespace,
            String parentResourcePath,
            String resourceType,
            String resourceName,
            String apiVersion) {

        // Correct for auto-gen'd API's treatment parent path as required even though it makes sense only for child resources
        if (parentResourcePath == null) {
            parentResourcePath = "";
        }

        GenericResourceInner inner = this.inner().get(
                resourceGroupName,
                resourceProviderNamespace,
                parentResourcePath,
                resourceType,
                resourceName,
                apiVersion);

        if (inner == null) {
            return null;
        }

        GenericResourceImpl resource = new GenericResourceImpl(
                resourceName,
                inner,
                this.manager());

        return resource.withExistingResourceGroup(resourceGroupName)
                .withProviderNamespace(resourceProviderNamespace)
                .withParentResourcePath(parentResourcePath)
                .withResourceType(resourceType)
                .withApiVersion(apiVersion);
    }

    @Override
    public void moveResources(String sourceResourceGroupName, ResourceGroup targetResourceGroup, List<String> resources) {
        this.moveResourcesAsync(sourceResourceGroupName, targetResourceGroup, resources).await();
    }

    @Override
    public Completable moveResourcesAsync(String sourceResourceGroupName, ResourceGroup targetResourceGroup, List<String> resources) {
        ResourcesMoveInfo moveInfo = new ResourcesMoveInfo();
        moveInfo.withTargetResourceGroup(targetResourceGroup.id());
        moveInfo.withResources(resources);
        return this.inner().moveResourcesAsync(sourceResourceGroupName, moveInfo).toCompletable();
    }

    @Override
    public ServiceFuture<Void> moveResourcesAsync(String sourceResourceGroupName, ResourceGroup targetResourceGroup, List<String> resources, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(this.moveResourcesAsync(sourceResourceGroupName, targetResourceGroup, resources), callback);
    }

    @Override
    public void delete(String resourceGroupName, String resourceProviderNamespace, String parentResourcePath, String resourceType, String resourceName, String apiVersion) {
        deleteAsync(resourceGroupName, resourceProviderNamespace, parentResourcePath, resourceType, resourceName, apiVersion).await();
    }

    @Override
    public Completable deleteAsync(String resourceGroupName, String resourceProviderNamespace, String parentResourcePath, String resourceType, String resourceName, String apiVersion) {
        return this.inner().deleteAsync(resourceGroupName, resourceProviderNamespace, parentResourcePath, resourceType, resourceName, apiVersion).toCompletable();
    }

    @Override
    public ServiceFuture<Void> deleteAsync(String resourceGroupName, String resourceProviderNamespace, String parentResourcePath, String resourceType, String resourceName, String apiVersion, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(deleteAsync(resourceGroupName, resourceProviderNamespace, parentResourcePath, resourceType, resourceName, apiVersion), callback);
    }

    @Override
    public void delete(String resourceGroupName, String resourceProviderNamespace, String parentResourcePath, String resourceType, String resourceName, String apiVersion, boolean forceDeletion) {
        deleteAsync(resourceGroupName, resourceProviderNamespace, parentResourcePath, resourceType, resourceName, apiVersion, forceDeletion).await();
    }

    @Override
    public Completable deleteAsync(String resourceGroupName, String resourceProviderNamespace, String parentResourcePath, String resourceType, String resourceName, String apiVersion, boolean forceDeletion) {
        return this.inner().deleteAsync(resourceGroupName, resourceProviderNamespace, parentResourcePath, resourceType, resourceName, apiVersion, forceDeletion).toCompletable();
    }

    @Override
    public ServiceFuture<Void> deleteAsync(String resourceGroupName, String resourceProviderNamespace, String parentResourcePath, String resourceType, String resourceName, String apiVersion, boolean forceDeletion, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(deleteAsync(resourceGroupName, resourceProviderNamespace, parentResourcePath, resourceType, resourceName, apiVersion, forceDeletion), callback);
    }

    @Override
    protected GenericResourceImpl wrapModel(String id) {
        return new GenericResourceImpl(id, new GenericResourceInner(), this.manager())
                .withExistingResourceGroup(ResourceUtils.groupFromResourceId(id))
                .withProviderNamespace(ResourceUtils.resourceProviderFromResourceId(id))
                .withResourceType(ResourceUtils.resourceTypeFromResourceId(id))
                .withParentResourceId(ResourceUtils.parentResourceIdFromResourceId(id));
    }

    @Override
    protected GenericResourceImpl wrapModel(GenericResourceInner inner) {
        if (inner == null) {
            return null;
        }
        return new GenericResourceImpl(inner.id(), inner, this.manager())
                .withExistingResourceGroup(ResourceUtils.groupFromResourceId(inner.id()))
                .withProviderNamespace(ResourceUtils.resourceProviderFromResourceId(inner.id()))
                .withResourceType(ResourceUtils.resourceTypeFromResourceId(inner.id()))
                .withParentResourceId(ResourceUtils.parentResourceIdFromResourceId(inner.id()));
    }

    @Override
    public Observable<GenericResourceInner> getInnerAsync(String groupName, String name) {
        // Not needed, can't be supported, provided only to satisfy GroupableResourceImpl's requirements
        throw new UnsupportedOperationException("Get just by resource group and name is not supported. Please use other overloads.");
    }

    @Override
    protected Completable deleteInnerAsync(String resourceGroupName, String name) {
        // Not needed, can't be supported, provided only to satisfy GroupableResourceImpl's requirements
        throw new UnsupportedOperationException("Delete just by resource group and name is not supported. Please use other overloads.");
    }

    @Override
    public Completable deleteByIdAsync(final String id) {
       final ResourcesInner inner = this.inner();
        return getApiVersionFromId(id)
                .flatMap(new Func1<String, Observable<Void>>() {
                    @Override
                    public Observable<Void> call(String apiVersion) {
                        return inner.deleteByIdAsync(id, apiVersion);
                    }
                }).toCompletable();
    }

    private Observable<String> getApiVersionFromId(final String id) {
        return this.manager().providers().getByNameAsync(ResourceUtils.resourceProviderFromResourceId(id))
                .map(new Func1<Provider, String>() {
                    @Override
                    public String call(Provider provider) {
                        return ResourceUtils.defaultApiVersion(id, provider);
                    }
                });
    }

    @Override
    public Observable<GenericResource> listAsync() {
        return convert(this.inner().listAsync());
    }

    @Override
    public Observable<GenericResource> listByResourceGroupAsync(String resourceGroupName) {
        return convert(this.manager().inner().resources().listByResourceGroupAsync(resourceGroupName));
    }

    private Observable<GenericResource> convert(Observable<Page<GenericResourceExpandedInner>> inners) {
        return inners.flatMap(new Func1<Page<GenericResourceExpandedInner>, Observable<GenericResourceExpandedInner>>() {
            @Override
            public Observable<GenericResourceExpandedInner> call(Page<GenericResourceExpandedInner> genericResourceExpandedInnerPage) {
                return Observable.from(genericResourceExpandedInnerPage.items());
            }
        }).map(new Func1<GenericResourceExpandedInner, GenericResource>() {
            @Override
            public GenericResource call(GenericResourceExpandedInner inner) {
                return wrapModel(inner);
            }
        });
    }
}
