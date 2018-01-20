/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.implementation;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.v2.management.resources.Deployment;
import com.microsoft.azure.v2.management.resources.Deployments;
import com.microsoft.azure.v2.management.resources.ResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.SupportsGettingByResourceGroupImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.implementation.GroupPagedList;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.PagedListConverter;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

import java.util.List;

/**
 * The implementation for {@link Deployments}.
 */
final class DeploymentsImpl
    extends SupportsGettingByResourceGroupImpl<Deployment>
    implements Deployments,
    HasManager<ResourceManager> {

    private final ResourceManager resourceManager;
    private PagedListConverter<DeploymentExtendedInner, Deployment> converter;

    DeploymentsImpl(final ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        converter = new PagedListConverter<DeploymentExtendedInner, Deployment>() {
            @Override
            public Observable<Deployment> typeConvertAsync(DeploymentExtendedInner deploymentInner) {
                return Observable.just((Deployment) createFluentModel(deploymentInner));
            }
        };
    }

    @Override
    public PagedList<Deployment> list() {
        final DeploymentsInner client = this.manager().inner().deployments();
        return new GroupPagedList<Deployment>(this.resourceManager.resourceGroups().list()) {
            @Override
            public List<Deployment> listNextGroup(String resourceGroupName) {
                return converter.convert(client.listByResourceGroup(resourceGroupName));
            }
        };
    }

    @Override
    public PagedList<Deployment> listByResourceGroup(String groupName) {
        return converter.convert(this.manager().inner().deployments().listByResourceGroup(groupName));
    }

    @Override
    public Deployment getByName(String name) {
        for (ResourceGroup group : this.resourceManager.resourceGroups().list()) {
            DeploymentExtendedInner inner = this.manager().inner().deployments().getByResourceGroup(group.name(), name);
            if (inner != null) {
                return createFluentModel(inner);
            }
        }
        return null;
    }

    @Override
    public Maybe<Deployment> getByResourceGroupAsync(String groupName, String name) {
        return this.manager().inner().deployments().getByResourceGroupAsync(groupName, name).map(new Function<DeploymentExtendedInner, Deployment>() {
            @Override
            public Deployment apply(DeploymentExtendedInner deploymentExtendedInner) {
                return createFluentModel(deploymentExtendedInner);
            }
        });
    }

    @Override
    public void deleteByResourceGroup(String groupName, String name) {
        deleteByResourceGroupAsync(groupName, name).blockingAwait();
    }

    @Override
    public ServiceFuture<Void> deleteByResourceGroupAsync(String groupName, String name, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(deleteByResourceGroupAsync(groupName, name), callback);
    }

    @Override
    public Completable deleteByResourceGroupAsync(String groupName, String name) {
        return this.manager().inner().deployments().deleteAsync(groupName, name).ignoreElement();
    }

    @Override
    public DeploymentImpl define(String name) {
        return createFluentModel(name);
    }

    @Override
    public boolean checkExistence(String resourceGroupName, String deploymentName) {
        return this.manager().inner().deployments().checkExistence(resourceGroupName, deploymentName);
    }

    protected DeploymentImpl createFluentModel(String name) {
        return new DeploymentImpl(
                new DeploymentExtendedInner().withName(name),
                this.resourceManager);
    }

    protected DeploymentImpl createFluentModel(DeploymentExtendedInner deploymentExtendedInner) {
        return new DeploymentImpl(deploymentExtendedInner, this.resourceManager);
    }

    @Override
    public Deployment getById(String id) {
        return this.getByResourceGroup(
                ResourceUtils.groupFromResourceId(id),
                ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public void deleteById(String id) {
        deleteByIdAsync(id).blockingAwait();
    }

    @Override
    public ServiceFuture<Void> deleteByIdAsync(String id, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(deleteByIdAsync(id), callback);
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        return deleteByResourceGroupAsync(ResourceUtils.groupFromResourceId(id), ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public ResourceManager manager() {
        return this.resourceManager;
    }

    @Override
    public Observable<Deployment> listAsync() {
        return this.manager().resourceGroups().listAsync().flatMap(new Function<ResourceGroup, Observable<Deployment>>() {
            @Override
            public Observable<Deployment> apply(ResourceGroup resourceGroup) {
                return listByResourceGroupAsync(resourceGroup.name());
            }
        });
    }


    @Override
    public Observable<Deployment> listByResourceGroupAsync(String resourceGroupName) {
        final DeploymentsInner client = this.manager().inner().deployments();
        return ReadableWrappersImpl.convertPageToInnerAsync(client.listByResourceGroupAsync(resourceGroupName))
                .map(new Function<DeploymentExtendedInner, Deployment>() {
                    @Override
                    public Deployment apply(DeploymentExtendedInner deploymentExtendedInner) {
                        return createFluentModel(deploymentExtendedInner);
                    }
        });
    }
}
