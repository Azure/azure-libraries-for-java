/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.implementation;

import com.azure.core.http.rest.PagedFlux;
import com.azure.core.management.PagedList;
import com.microsoft.azure.management.resources.Deployment;
import com.microsoft.azure.management.resources.Deployments;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.SupportsGettingByResourceGroupImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupPagedList;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
            public Mono<Deployment> typeConvertAsync(DeploymentExtendedInner deploymentInner) {
                return Mono.just((Deployment) createFluentModel(deploymentInner));
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
    public Mono<Deployment> getByResourceGroupAsync(String groupName, String name) {
        return this.manager().inner().deployments().getByResourceGroupAsync(groupName, name)
                .flatMap(inner -> {
                    if (inner != null) {
                        return Mono.just(createFluentModel(inner));
                    } else {
                        return Mono.empty();
                    }
                });
    }

    @Override
    public void deleteByResourceGroup(String groupName, String name) {
        deleteByResourceGroupAsync(groupName, name).block();
    }

    @Override
    public Mono<Void> deleteByResourceGroupAsync(String groupName, String name) {
        return this.manager().inner().deployments().deleteAsync(groupName, name);
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
        return new DeploymentImpl(new DeploymentExtendedInner(), name, this.resourceManager);
    }

    protected DeploymentImpl createFluentModel(DeploymentExtendedInner deploymentExtendedInner) {
        return new DeploymentImpl(deploymentExtendedInner, deploymentExtendedInner.getName(), this.resourceManager);
    }

    @Override
    public Deployment getById(String id) {
        return this.getByResourceGroup(
                ResourceUtils.groupFromResourceId(id),
                ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public void deleteById(String id) {
        deleteByIdAsync(id).block();
    }


    @Override
    public Mono<Void> deleteByIdAsync(String id) {
        return deleteByResourceGroupAsync(ResourceUtils.groupFromResourceId(id), ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public ResourceManager manager() {
        return this.resourceManager;
    }

    @Override
    public PagedFlux<Deployment> listAsync() {
        final DeploymentsInner client = this.manager().inner().deployments();
        return client.listAsync().mapPage(inner -> createFluentModel(inner));
//        return this.manager().resourceGroups().listAsync().flatMap(rg -> listByResourceGroupAsync(rg.name()));
//
//                new Func1<ResourceGroup, Observable<Deployment>>() {
//            @Override
//            public Observable<Deployment> call(ResourceGroup resourceGroup) {
//                return listByResourceGroupAsync(resourceGroup.name());
//            }
//        });
    }


    @Override
    public PagedFlux<Deployment> listByResourceGroupAsync(String resourceGroupName) {
        final DeploymentsInner client = this.manager().inner().deployments();
        return client.listByResourceGroupAsync(resourceGroupName).mapPage(inner -> createFluentModel(inner));
//
//                .map(new Func1<DeploymentExtendedInner, Deployment>() {
//                    @Override
//                    public Deployment call(DeploymentExtendedInner deploymentExtendedInner) {
//                        return createFluentModel(deploymentExtendedInner);
//                    }
//                });
    }
}
