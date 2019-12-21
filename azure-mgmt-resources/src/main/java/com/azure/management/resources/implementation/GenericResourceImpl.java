/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.implementation;

import com.azure.management.resources.GenericResource;
import com.azure.management.resources.Plan;
import com.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import com.azure.management.resources.models.GenericResourceInner;
import com.azure.management.resources.models.ResourceManagementClientImpl;
import com.azure.management.resources.models.ResourcesInner;
import reactor.core.publisher.Mono;

/**
 * The implementation for GenericResource and its nested interfaces.
 */
final class GenericResourceImpl
        extends GroupableResourceImpl<
        GenericResource,
        GenericResourceInner,
        GenericResourceImpl,
        ResourceManager>
        implements
        GenericResource,
        GenericResource.Definition,
        GenericResource.UpdateStages.WithApiVersion,
        GenericResource.Update {
    private String resourceProviderNamespace;
    private String parentResourcePath;
    private String resourceType;
    private String apiVersion;

    GenericResourceImpl(String key,
                        GenericResourceInner innerModel,
                        final ResourceManager resourceManager) {
        super(key, innerModel, resourceManager);
        resourceProviderNamespace = ResourceUtils.resourceProviderFromResourceId(innerModel.getId());
        resourceType = ResourceUtils.resourceTypeFromResourceId(innerModel.getId());
        parentResourcePath = ResourceUtils.parentRelativePathFromResourceId(innerModel.getId());
    }

    @Override
    public String resourceProviderNamespace() {
        return resourceProviderNamespace;
    }

    @Override
    public String parentResourcePath() {
        if (parentResourcePath == null) {
            return "";
        }
        return parentResourcePath;
    }

    @Override
    public String resourceType() {
        return resourceType;
    }

    @Override
    public String apiVersion() {
        return apiVersion;
    }

    @Override
    public Plan plan() {
        return getInner().getPlan();
    }

    @Override
    public Object properties() {
        return getInner().getProperties();
    }

    @Override
    protected Mono<GenericResourceInner> getInnerAsync() {
        // TODO: Whey fluent V1 has api version here.
        return this.getManager().getInner().resources().getAsync(
                getResourceGroupName(),
                resourceProviderNamespace(),
                parentResourcePath(),
                resourceType(),
                getName());
    }

    public GenericResourceImpl withProperties(Object properties) {
        getInner().setProperties(properties);
        return this;
    }

    @Override
    public GenericResourceImpl withParentResourceId(String parentResourceId) {
        return withParentResourcePath(ResourceUtils.relativePathFromResourceId(parentResourceId));
    }

    @Override
    public GenericResourceImpl withParentResourcePath(String parentResourcePath) {
        this.parentResourcePath = parentResourcePath;
        return this;
    }

    public GenericResourceImpl withPlan(String name, String publisher, String product, String promotionCode) {
        getInner().setPlan(new Plan().setName(name).setPublisher(publisher).setProduct(product).setPromotionCode(promotionCode));
        return this;
    }

    @Override
    public GenericResourceImpl withoutPlan() {
        getInner().setPlan(null);
        return this;
    }

    @Override
    public GenericResourceImpl withProviderNamespace(String resourceProviderNamespace) {
        this.resourceProviderNamespace = resourceProviderNamespace;
        return this;
    }

    @Override
    public GenericResourceImpl withResourceType(String resourceType) {
        this.resourceType = resourceType;
        return this;
    }

    @Override
    public GenericResourceImpl withApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    // CreateUpdateTaskGroup.ResourceCreator implementation
    @Override
    public Mono<GenericResource> createResourceAsync() {
        final GenericResourceImpl self = this;
        Mono<String> observable = null;
        if (apiVersion != null) {
            observable = Mono.just(apiVersion);
        } else {
            final ResourceManagementClientImpl serviceClient = this.getManager().getInner();
            observable = this.getManager().providers().getByNameAsync(resourceProviderNamespace)
                    .flatMap(provider -> {
                        String id;
                        if (!isInCreateMode()) {
                            id = getInner().getId();
                        } else {
                            id = ResourceUtils.constructResourceId(
                                    serviceClient.getSubscriptionId(),
                                    getResourceGroupName(),
                                    resourceProviderNamespace(),
                                    resourceType(),
                                    getName(),
                                    parentResourcePath());
                        }
                        self.apiVersion = ResourceUtils.defaultApiVersion(id, provider);
                        return Mono.just(self.apiVersion);
                    });
        }
        final ResourcesInner resourceClient = this.getManager().getInner().resources();
        return observable
                .flatMap(api -> {
                    String name = getName();
                    if (!isInCreateMode()) {
                        name = ResourceUtils.nameFromResourceId(getInner().getId());
                    }
                    return resourceClient.createOrUpdateAsync(
                            getResourceGroupName(),
                            resourceProviderNamespace,
                            parentResourcePath(),
                            resourceType,
                            name,
// FIXME: API version
//                            api,
                            getInner())
                            .subscribeOn(SdkContext.getReactorScheduler())
                            .map(innerToFluentMap(self));
                });
    }
}
