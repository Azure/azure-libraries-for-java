/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.appservice.implementation;

import com.azure.core.http.rest.PagedIterable;
import com.azure.management.appservice.FunctionApp;
import com.azure.management.appservice.FunctionApps;
import com.azure.management.appservice.FunctionEnvelope;
import com.azure.management.appservice.models.SiteConfigResourceInner;
import com.azure.management.appservice.models.SiteInner;
import com.azure.management.appservice.models.SiteLogsConfigInner;
import com.azure.management.appservice.models.WebAppsInner;
import com.azure.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;
import reactor.core.publisher.Mono;

/**
 * The implementation for WebApps.
 */
class FunctionAppsImpl
        extends TopLevelModifiableResourcesImpl<
                    FunctionApp,
                    FunctionAppImpl,
                    SiteInner,
                    WebAppsInner,
                    AppServiceManager>
        implements FunctionApps {

    FunctionAppsImpl(final AppServiceManager manager) {
        super(manager.inner().webApps(), manager);
        // FIXME
//        converter = new PagedListConverter<SiteInner, FunctionApp>() {
//            @Override
//            public Observable<FunctionApp> typeConvertAsync(final SiteInner siteInner) {
//                return Observable.zip(
//                        manager().inner().webApps().getConfigurationAsync(siteInner.resourceGroup(), siteInner.name()),
//                        manager().inner().webApps().getDiagnosticLogsConfigurationAsync(siteInner.resourceGroup(), siteInner.name()),
//                        new Func2<SiteConfigResourceInner, SiteLogsConfigInner, FunctionApp>() {
//                            @Override
//                            public FunctionApp call(SiteConfigResourceInner siteConfigResourceInner, SiteLogsConfigInner logsConfigInner) {
//                                return wrapModel(siteInner, siteConfigResourceInner, logsConfigInner);
//                            }
//                        });
//            }
//
//            @Override
//            protected boolean filter(SiteInner inner) {
//                return inner.kind() != null && Arrays.asList(inner.kind().split(",")).contains("functionapp");
//            }
//        };
    }

    @Override
    public FunctionApp getByResourceGroup(String groupName, String name) {
        SiteInner siteInner = this.inner().getByResourceGroup(groupName, name);
        if (siteInner == null) {
            return null;
        }
        return wrapModel(siteInner, this.inner().getConfiguration(groupName, name), this.inner().getDiagnosticLogsConfiguration(groupName, name));
    }

    @Override
    public Mono<FunctionApp> getByResourceGroupAsync(final String groupName, final String name) {
        final FunctionAppsImpl self = this;
        return this.inner().getByResourceGroupAsync(groupName, name).flatMap(siteInner -> Mono.zip(
                self.inner().getConfigurationAsync(groupName, name),
                self.inner().getDiagnosticLogsConfigurationAsync(groupName, name),
                (SiteConfigResourceInner siteConfigResourceInner, SiteLogsConfigInner logsConfigInner) -> wrapModel(siteInner, siteConfigResourceInner, logsConfigInner)));
    }

    @Override
    public PagedIterable<FunctionEnvelope> listFunctions(String resourceGroupName, String name) {
        return this.manager().webApps().inner().listFunctions(resourceGroupName, name).mapPage(FunctionEnvelopeImpl::new);
    }


    @Override
    protected Mono<Void> deleteInnerAsync(String resourceGroupName, String name) {
        return this.inner().deleteAsync(resourceGroupName, name);
    }

    @Override
    protected FunctionAppImpl wrapModel(String name) {
        return new FunctionAppImpl(name, new SiteInner().withKind("functionapp"), null, null, this.manager());
    }

    @Override
    protected FunctionAppImpl wrapModel(SiteInner inner) {
        if (inner == null) {
            return null;
        }
        return wrapModel(inner, null, null);
    }

    private FunctionAppImpl wrapModel(SiteInner inner, SiteConfigResourceInner siteConfig, SiteLogsConfigInner logConfig) {
        if (inner == null) {
            return null;
        }
        return new FunctionAppImpl(inner.getName(), inner, siteConfig, logConfig, this.manager());
    }

    protected PagedIterable<FunctionApp> wrapList(PagedIterable<SiteInner> pagedList) {
        // FIXME
        return pagedList.mapPage(this::wrapModel);
    }


    @Override
    public FunctionAppImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    public Mono<Void> deleteByResourceGroupAsync(String groupName, String name) {
        return this.inner().deleteAsync(groupName, name);
    }
}
