/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.appservice.FunctionApp;
import com.microsoft.azure.management.appservice.FunctionApps;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * The implementation for WebApps.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.AppService.Fluent")
class FunctionAppsImpl
        extends TopLevelModifiableResourcesImpl<
                    FunctionApp,
                    FunctionAppImpl,
                    SiteInner,
                    WebAppsInner,
                    AppServiceManager>
        implements FunctionApps {

    private final PagedListConverter<SiteInner, FunctionApp> converter;

    // The default implementation is requesting extra properties that need specific permissions.
    // This converter is only used when listing functionApps but ignoring the extra calls to the
    // API for getting those properties
    private final PagedListConverter<SiteInner, FunctionApp> converterWithoutProperties;

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
                return "functionapp".equalsIgnoreCase(inner.kind());
            }
        };

        converterWithoutProperties = new PagedListConverter<SiteInner, FunctionApp>() {
            @Override
            public Observable<FunctionApp> typeConvertAsync(final SiteInner siteInner) {
                return Observable.just((FunctionApp) wrapModel(siteInner));
            }

            @Override
            protected boolean filter(SiteInner inner) {
                return "functionapp".equalsIgnoreCase(inner.kind());
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
                    return null;
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

    @Override
    public PagedList<FunctionApp> listWithoutProperties() {
        return converterWithoutProperties.convert(inner().list());
    }
}
