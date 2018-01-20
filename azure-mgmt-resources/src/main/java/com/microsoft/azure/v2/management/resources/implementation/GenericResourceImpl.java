/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.implementation;

import com.microsoft.azure.v2.management.resources.GenericResource;
import com.microsoft.azure.v2.management.resources.Plan;
import com.microsoft.azure.v2.management.resources.Provider;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.SdkContext;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

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
        resourceProviderNamespace = ResourceUtils.resourceProviderFromResourceId(innerModel.id());
        resourceType = ResourceUtils.resourceTypeFromResourceId(innerModel.id());
        parentResourcePath = ResourceUtils.parentRelativePathFromResourceId(innerModel.id());
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
        return inner().plan();
    }

    @Override
    public Object properties() {
        return inner().properties();
    }

    @Override
    protected Maybe<GenericResourceInner> getInnerAsync() {
        return this.manager().inner().resources().getAsync(
                resourceGroupName(),
                resourceProviderNamespace(),
                parentResourcePath(),
                resourceType(),
                name(),
                apiVersion());
    }

    public GenericResourceImpl withProperties(Object properties) {
            inner().withProperties(properties);
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
            inner().withPlan(new Plan().withName(name).withPublisher(publisher).withProduct(product).withPromotionCode(promotionCode));
        return this;
    }

    @Override
    public GenericResourceImpl withoutPlan() {
            inner().withPlan(null);
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
    public Observable<GenericResource> createResourceAsync() {
        final GenericResourceImpl self = this;
        Maybe<String> maybe;
        if (apiVersion != null) {
             maybe = Maybe.just(apiVersion);
        } else {
            final ResourceManagementClientImpl serviceClient = this.manager().inner();
            maybe = this.manager().providers().getByNameAsync(resourceProviderNamespace)
                    .map(new Function<Provider, String>() {
                        @Override
                        public String apply(Provider provider) {
                            String id;
                            if (!isInCreateMode()) {
                                id = inner().id();
                            } else {
                                id = ResourceUtils.constructResourceId(
                                        serviceClient.subscriptionId(),
                                        resourceGroupName(),
                                        resourceProviderNamespace(),
                                        resourceType(),
                                        name(),
                                        parentResourcePath());
                            }
                            self.apiVersion = ResourceUtils.defaultApiVersion(id, provider);
                            return self.apiVersion;
                        }
                    });
        }
        final ResourcesInner resourceClient = this.manager().inner().resources();
        return maybe
                .flatMap(new Function<String, Maybe<GenericResource>>() {
                    @Override
                    public Maybe<GenericResource> apply(String api) {
                        String name = name();
                        if (!isInCreateMode()) {
                            name = ResourceUtils.nameFromResourceId(inner().id());
                        }
                        return resourceClient.createOrUpdateAsync(
                                resourceGroupName(),
                                resourceProviderNamespace,
                                parentResourcePath(),
                                resourceType,
                                name,
                                api,
                                inner())
                                .subscribeOn(SdkContext.getRxScheduler())
                                .map(innerToFluentMap(self));
                    }
                }).toObservable();
    }
}
