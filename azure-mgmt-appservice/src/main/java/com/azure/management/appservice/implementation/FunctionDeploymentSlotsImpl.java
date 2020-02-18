/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.appservice.implementation;

import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedIterable;
import com.azure.management.appservice.FunctionApp;
import com.azure.management.appservice.FunctionDeploymentSlot;
import com.azure.management.appservice.FunctionDeploymentSlots;
import com.azure.management.appservice.models.SiteConfigResourceInner;
import com.azure.management.appservice.models.SiteInner;
import com.azure.management.appservice.models.SiteLogsConfigInner;
import com.azure.management.appservice.models.WebAppsInner;
import com.azure.management.resources.fluentcore.arm.collection.implementation.IndependentChildResourcesImpl;
import reactor.core.publisher.Mono;

/**
 * The implementation DeploymentSlots.
 */
class FunctionDeploymentSlotsImpl
        extends IndependentChildResourcesImpl<
        FunctionDeploymentSlot,
        FunctionDeploymentSlotImpl,
        SiteInner,
        WebAppsInner,
        AppServiceManager,
        FunctionApp>
        implements FunctionDeploymentSlots {

    private final FunctionAppImpl parent;

    FunctionDeploymentSlotsImpl(final FunctionAppImpl parent) {
        super(parent.manager().inner().webApps(), parent.manager());

        this.parent = parent;
        // FIXME
//        final WebAppsInner innerCollection = this.inner();
//        converter = new PagedListConverter<SiteInner, FunctionDeploymentSlot>() {
//            @Override
//            public Observable<FunctionDeploymentSlot> typeConvertAsync(final SiteInner siteInner) {
//                return Observable.zip(
//                        innerCollection.getConfigurationSlotAsync(siteInner.resourceGroup(), parent.name(), siteInner.name()),
//                        innerCollection.getDiagnosticLogsConfigurationSlotAsync(siteInner.resourceGroup(), parent.name(), siteInner.name()),
//                        new Func2<SiteConfigResourceInner, SiteLogsConfigInner, FunctionDeploymentSlot>() {
//                            @Override
//                            public FunctionDeploymentSlot call(SiteConfigResourceInner siteConfigResourceInner, SiteLogsConfigInner logsConfigInner) {
//                                return wrapModel(siteInner, siteConfigResourceInner, logsConfigInner);
//                            }
//                        });
//            }
//        };
    }

    @Override
    protected FunctionDeploymentSlotImpl wrapModel(String name) {
        return new FunctionDeploymentSlotImpl(name, new SiteInner(), null, null, parent)
                .withRegion(parent.regionName())
                .withExistingResourceGroup(parent.resourceGroupName());
    }

    @Override
    protected FunctionDeploymentSlotImpl wrapModel(SiteInner inner) {
        if (inner == null) {
            return null;
        }
        return wrapModel(inner, null, null);
    }

    protected PagedIterable<FunctionDeploymentSlot> wrapList(PagedIterable<SiteInner> pagedList) {
        // TODO
        return pagedList.mapPage(this::wrapModel);
    }

    @Override
    public FunctionDeploymentSlotImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    public Mono<FunctionDeploymentSlot> getByParentAsync(final String resourceGroup, final String parentName, final String name) {
        return innerCollection.getSlotAsync(resourceGroup, parentName, name).flatMap(siteInner -> Mono.zip(
                innerCollection.getConfigurationSlotAsync(resourceGroup, parentName, name),
                innerCollection.getDiagnosticLogsConfigurationSlotAsync(resourceGroup, parentName, name),
                (SiteConfigResourceInner siteConfigResourceInner, SiteLogsConfigInner logsConfigInner) -> wrapModel(siteInner, siteConfigResourceInner, logsConfigInner)));
    }

    @Override
    public PagedIterable<FunctionDeploymentSlot> listByParent(String resourceGroupName, String parentName) {
        return wrapList(innerCollection.listSlots(resourceGroupName, parentName));
    }

    @Override
    public Mono<Void> deleteByParentAsync(String groupName, String parentName, String name) {
        return innerCollection.deleteSlotAsync(groupName, parentName, name);
    }

    @Override
    public void deleteByName(String name) {
        deleteByParent(parent.resourceGroupName(), parent.name(), name);
    }

    @Override
    public Mono<Void> deleteByNameAsync(String name) {
        return deleteByParentAsync(parent.resourceGroupName(), parent.name(), name);
    }

    @Override
    public PagedIterable<FunctionDeploymentSlot> list() {
        return listByParent(parent.resourceGroupName(), parent.name());
    }

    @Override
    public FunctionDeploymentSlot getByName(String name) {
        return getByParent(parent.resourceGroupName(), parent.name(), name);
    }

    @Override
    public Mono<FunctionDeploymentSlot> getByNameAsync(String name) {
        return getByParentAsync(parent.resourceGroupName(), parent.name(), name);
    }

    @Override
    public FunctionApp parent() {
        return this.parent;
    }

    @Override
    public PagedFlux<FunctionDeploymentSlot> listAsync() {
        return innerCollection.listSlotsAsync(parent.resourceGroupName(), parent.name()).mapPage(this::wrapModel);
    }

    private FunctionDeploymentSlotImpl wrapModel(SiteInner inner, SiteConfigResourceInner siteConfig, SiteLogsConfigInner logConfig) {
        if (inner == null) {
            return null;
        }
        return new FunctionDeploymentSlotImpl(inner.getName(), inner, siteConfig, logConfig, parent);
    }
}