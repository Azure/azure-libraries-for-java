/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.appservice.WebApp;
import com.microsoft.azure.management.appservice.WebApps;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

import java.util.Arrays;

/**
 * The implementation for WebApps.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.AppService.Fluent")
class WebAppsImpl
        extends TopLevelModifiableResourcesImpl<
                    WebApp,
                    WebAppImpl,
                    SiteInner,
                    WebAppsInner,
                    AppServiceManager>
        implements WebApps {

    private final PagedListConverter<SiteInner, WebApp> converter;

    // The default implementation is requesting extra properties that need specific permissions.
    // This converter is only used when listing webApps but ignoring the extra calls to the
    // API for getting those properties
    private final PagedListConverter<SiteInner, WebApp> converterWithoutProperties;

    WebAppsImpl(final AppServiceManager manager) {
        super(manager.inner().webApps(), manager);
        converter = new PagedListConverter<SiteInner, WebApp>() {
            @Override
            protected boolean filter(SiteInner inner) {
                return inner.kind() == null || Arrays.asList(inner.kind().split(",")).contains("app");
            }

            @Override
            public Observable<WebApp> typeConvertAsync(final SiteInner siteInner) {
                return Observable.zip(
                        manager().inner().webApps().getConfigurationAsync(siteInner.resourceGroup(), siteInner.name()),
                        manager().inner().webApps().getDiagnosticLogsConfigurationAsync(siteInner.resourceGroup(), siteInner.name()),
                        new Func2<SiteConfigResourceInner, SiteLogsConfigInner, WebApp>() {
                            @Override
                            public WebApp call(SiteConfigResourceInner siteConfigResourceInner, SiteLogsConfigInner logsConfigInner) {
                                return wrapModel(siteInner, siteConfigResourceInner, logsConfigInner);
                            }
                        });
            }
        };

        converterWithoutProperties = new PagedListConverter<SiteInner, WebApp>() {
            @Override
            public Observable<WebApp> typeConvertAsync(final SiteInner siteInner) {
                return Observable.just((WebApp) wrapModel(siteInner));
            }

            @Override
            protected boolean filter(SiteInner inner) {
                return !("functionapp".equalsIgnoreCase(inner.kind()));
            }
        };
    }

    @Override
    public Observable<WebApp> getByResourceGroupAsync(final String groupName, final String name) {
        final WebAppsImpl self = this;
        return this.inner().getByResourceGroupAsync(groupName, name).flatMap(new Func1<SiteInner, Observable<WebApp>>() {
            @Override
            public Observable<WebApp> call(final SiteInner siteInner) {
                if (siteInner == null) {
                    return null;
                }
                return Observable.zip(
                        self.inner().getConfigurationAsync(groupName, name),
                        self.inner().getDiagnosticLogsConfigurationAsync(groupName, name),
                        new Func2<SiteConfigResourceInner, SiteLogsConfigInner, WebApp>() {
                            @Override
                            public WebApp call(SiteConfigResourceInner siteConfigResourceInner, SiteLogsConfigInner logsConfigInner) {
                                return wrapModel(siteInner, siteConfigResourceInner, logsConfigInner);
                            }
                        });
            }
        });

    }

    @Override
    protected WebAppImpl wrapModel(String name) {
        return new WebAppImpl(name, new SiteInner().withKind("app"), null, null, this.manager());
    }

    protected WebAppImpl wrapModel(SiteInner inner, SiteConfigResourceInner siteConfig, SiteLogsConfigInner logConfig) {
        if (inner == null) {
            return null;
        }
        return new WebAppImpl(inner.name(), inner, siteConfig, logConfig, this.manager());
    }

    @Override
    protected WebAppImpl wrapModel(SiteInner inner) {
        return wrapModel(inner, null, null);
    }

    protected PagedList<WebApp> wrapList(PagedList<SiteInner> pagedList) {
        return converter.convert(pagedList);
    }

    @Override
    public WebAppImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    public PagedList<WebApp> listWithoutProperties() {
        return converterWithoutProperties.convert(inner().list());
    }
}
