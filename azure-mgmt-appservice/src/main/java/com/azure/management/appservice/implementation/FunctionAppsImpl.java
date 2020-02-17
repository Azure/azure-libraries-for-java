/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.appservice.implementation;

import com.azure.management.appservice.FunctionApp;
import com.azure.management.appservice.FunctionApps;
import com.azure.management.appservice.FunctionEnvelope;
import com.microsoft.azure.PagedList;
import com.azure.management.apigeneration.LangDefinition;
import com.azure.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;
import com.azure.management.resources.fluentcore.utils.PagedListConverter;
import java.util.Arrays;

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

    private final PagedListConverter<SiteInner, FunctionApp> converter;

    FunctionAppsImpl(final AppServiceManager manager) {
        super(manager.inner().webApps(), manager);
        converter = new PagedListConverter<SiteInner, FunctionApp>() {
            @Override
            public Observable<FunctionApp> typeConvertAsync(final SiteInner siteInner) {
                return Observable.zip(
                        manager().inner().webApps().getConfigurationAsync(siteInner.resourceGroup(), siteInner.name()),
                        manager().inner().webApps().getDiagnosticLogsConfigurationAsync(siteInner.resourceGroup(), siteInner.name()),
                        new Func2<SiteConfigResourceInner, SiteLogsConfigInner, FunctionApp>() {
                            @Override
                            public FunctionApp call(SiteConfigResourceInner siteConfigResourceInner, SiteLogsConfigInner logsConfigInner) {
                                return wrapModel(siteInner, siteConfigResourceInner, logsConfigInner);
                            }
                        });
            }

            @Override
            protected boolean filter(SiteInner inner) {
                return inner.kind() != null && Arrays.asList(inner.kind().split(",")).contains("functionapp");
            }
        };
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
    public Observable<FunctionApp> getByResourceGroupAsync(final String groupName, final String name) {
        final FunctionAppsImpl self = this;
        return this.inner().getByResourceGroupAsync(groupName, name).flatMap(new Func1<SiteInner, Observable<FunctionApp>>() {
            @Override
            public Observable<FunctionApp> call(final SiteInner siteInner) {
                if (siteInner == null) {
                    return Observable.just(null);
                }
                return Observable.zip(
                        self.inner().getConfigurationAsync(groupName, name),
                        self.inner().getDiagnosticLogsConfigurationAsync(groupName, name),
                        new Func2<SiteConfigResourceInner, SiteLogsConfigInner, FunctionApp>() {
                            @Override
                            public FunctionApp call(SiteConfigResourceInner siteConfigResourceInner, SiteLogsConfigInner logsConfigInner) {
                                return wrapModel(siteInner, siteConfigResourceInner, logsConfigInner);
                            }
                        });
            }
        });
    }

    @Override
    public PagedList<FunctionEnvelope> listFunctions(String resourceGroupName, String name) {
        return new PagedListConverter<FunctionEnvelopeInner, FunctionEnvelope>() {
            @Override
            public Observable<FunctionEnvelope> typeConvertAsync(FunctionEnvelopeInner functionEnvelopeInner) {
                return Observable.just((FunctionEnvelope) new FunctionEnvelopeImpl(functionEnvelopeInner));
            }
        }.convert(this.manager().webApps().inner().listFunctions(resourceGroupName, name));
    }


    @Override
    protected Completable deleteInnerAsync(String resourceGroupName, String name) {
        return this.inner().deleteAsync(resourceGroupName, name).toCompletable();
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
        return new FunctionAppImpl(inner.name(), inner, siteConfig, logConfig, this.manager());
    }

    protected PagedList<FunctionApp> wrapList(PagedList<SiteInner> pagedList) {
        return converter.convert(pagedList);
    }


    @Override
    public FunctionAppImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    public Completable deleteByResourceGroupAsync(String groupName, String name) {
        return this.inner().deleteAsync(groupName, name).toCompletable();
    }
}
