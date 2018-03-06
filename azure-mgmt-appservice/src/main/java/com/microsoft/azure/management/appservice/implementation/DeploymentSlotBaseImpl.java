/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.microsoft.azure.Page;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.appservice.AppSetting;
import com.microsoft.azure.management.appservice.ConnectionString;
import com.microsoft.azure.management.appservice.HostNameBinding;
import com.microsoft.azure.management.appservice.PublishingProfile;
import com.microsoft.azure.management.appservice.WebAppBase;
import com.microsoft.azure.management.appservice.WebAppSourceControl;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import rx.Completable;
import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Func1;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The implementation for DeploymentSlot.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.AppService.Fluent")
class DeploymentSlotBaseImpl<
        FluentT extends WebAppBase,
        FluentImplT extends DeploymentSlotBaseImpl<FluentT, FluentImplT, ParentImplT, FluentWithCreateT, FluentUpdateT>,
        ParentImplT extends AppServiceBaseImpl<?, ?, ?, ?>,
        FluentWithCreateT,
        FluentUpdateT>
        extends WebAppBaseImpl<FluentT, FluentImplT> {
    private final ParentImplT parent;
    private final String name;
    WebAppBase configurationSource;

    DeploymentSlotBaseImpl(String name, SiteInner innerObject, SiteConfigResourceInner configObject, final ParentImplT parent) {
        super(name.replaceAll(".*/", ""), innerObject, configObject, parent.manager());
        this.name = name.replaceAll(".*/", "");
        this.parent = parent;
        inner().withServerFarmId(parent.appServicePlanId());
        inner().withLocation(regionName());
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Map<String, HostNameBinding> getHostNameBindings() {
        return getHostNameBindingsAsync().toBlocking().single();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Observable<Map<String, HostNameBinding>> getHostNameBindingsAsync() {
        return this.manager().inner().webApps().listHostNameBindingsSlotAsync(resourceGroupName(), parent().name(), name())
                .flatMap(new Func1<Page<HostNameBindingInner>, Observable<HostNameBindingInner>>() {
                    @Override
                    public Observable<HostNameBindingInner> call(Page<HostNameBindingInner> hostNameBindingInnerPage) {
                        return Observable.from(hostNameBindingInnerPage.items());
                    }
                })
                .map(new Func1<HostNameBindingInner, HostNameBinding>() {
                    @Override
                    public HostNameBinding call(HostNameBindingInner hostNameBindingInner) {
                        return new HostNameBindingImpl<FluentT, FluentImplT>(hostNameBindingInner, (FluentImplT) DeploymentSlotBaseImpl.this);
                    }
                }).toList()
                .map(new Func1<List<HostNameBinding>, Map<String, HostNameBinding>>() {
                    @Override
                    public Map<String, HostNameBinding> call(List<HostNameBinding> hostNameBindings) {
                        return Collections.unmodifiableMap(Maps.uniqueIndex(hostNameBindings, new Function<HostNameBinding, String>() {
                            @Override
                            public String apply(HostNameBinding input) {
                                return input.name().replace(name() + "/", "");
                            }
                        }));
                    }
                });
    }

    @Override
    public PublishingProfile getPublishingProfile() {
        return getPublishingProfileAsync().toBlocking().single();
    }

    public Observable<PublishingProfile> getPublishingProfileAsync() {
        return manager().inner().webApps().listPublishingProfileXmlWithSecretsSlotAsync(resourceGroupName(), this.parent().name(), name())
                .map(new Func1<InputStream, PublishingProfile>() {
                    @Override
                    public PublishingProfile call(InputStream stream) {
                        try {
                            String xml = CharStreams.toString(new InputStreamReader(stream));
                            return new PublishingProfileImpl(xml, DeploymentSlotBaseImpl.this);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }

    @Override
    public void start() {
        startAsync().toObservable().toBlocking().subscribe();
    }

    @Override
    public Completable startAsync() {
        return manager().inner().webApps().startSlotAsync(resourceGroupName(), this.parent().name(), name())
                .flatMap(new Func1<Void, Observable<?>>() {
                    @Override
                    public Observable<?> call(Void aVoid) {
                        return refreshAsync();
                    }
                }).toCompletable();
    }

    @Override
    public void stop() {
        stopAsync().toObservable().toBlocking().subscribe();
    }

    @Override
    public Completable stopAsync() {
        return manager().inner().webApps().stopSlotAsync(resourceGroupName(), this.parent().name(), name())
                .flatMap(new Func1<Void, Observable<?>>() {
                    @Override
                    public Observable<?> call(Void aVoid) {
                        return refreshAsync();
                    }
                }).toCompletable();
    }

    @Override
    public void restart() {
        restartAsync().toObservable().toBlocking().subscribe();
    }

    @Override
    public Completable restartAsync() {
        return manager().inner().webApps().restartSlotAsync(resourceGroupName(), this.parent().name(), name())
                .flatMap(new Func1<Void, Observable<?>>() {
                    @Override
                    public Observable<?> call(Void aVoid) {
                        return refreshAsync();
                    }
                }).toCompletable();
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withBrandNewConfiguration() {
        this.siteConfig = null;
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withConfigurationFromDeploymentSlot(FluentT slot) {
        this.siteConfig = ((WebAppBaseImpl) slot).siteConfig;
        configurationSource = slot;
        return (FluentImplT) this;
    }

    Observable<Indexable> submitAppSettings() {
        return Observable.just(configurationSource).flatMap(new Func1<WebAppBase, Observable<Indexable>>() {
            @Override
            public Observable<Indexable> call(WebAppBase webAppBase) {
                if (webAppBase == null || !isInCreateMode()) {
                    return DeploymentSlotBaseImpl.super.submitAppSettings();
                }
                return webAppBase.getAppSettingsAsync().flatMap(new Func1<Map<String, AppSetting>, Observable<Indexable>>() {
                    @Override
                    public Observable<Indexable> call(Map<String, AppSetting> stringAppSettingMap) {
                        for (AppSetting appSetting : stringAppSettingMap.values()) {
                            if (appSetting.sticky()) {
                                withStickyAppSetting(appSetting.key(), appSetting.value());
                            } else {
                                withAppSetting(appSetting.key(), appSetting.value());
                            }
                        }
                        return DeploymentSlotBaseImpl.super.submitAppSettings();
                    }
                });
            }
        });
    }

    Observable<Indexable> submitConnectionStrings() {
        return Observable.just(configurationSource).flatMap(new Func1<WebAppBase, Observable<Indexable>>() {
            @Override
            public Observable<Indexable> call(WebAppBase webAppBase) {
                if (webAppBase == null || !isInCreateMode()) {
                    return DeploymentSlotBaseImpl.super.submitConnectionStrings();
                }
                return webAppBase.getConnectionStringsAsync().flatMap(new Func1<Map<String, ConnectionString>, Observable<Indexable>>() {
                    @Override
                    public Observable<Indexable> call(Map<String, ConnectionString> stringConnectionStringMap) {
                        for (ConnectionString connectionString : stringConnectionStringMap.values()) {
                            if (connectionString.sticky()) {
                                withStickyConnectionString(connectionString.name(), connectionString.value(), connectionString.type());
                            } else {
                                withConnectionString(connectionString.name(), connectionString.value(), connectionString.type());
                            }
                        }
                        return DeploymentSlotBaseImpl.super.submitConnectionStrings();

                    }
                });
            }
        });
    }

    public ParentImplT parent() {
        return this.parent;
    }

    @Override
    Observable<SiteInner> createOrUpdateInner(SiteInner site) {
        return manager().inner().webApps().createOrUpdateSlotAsync(resourceGroupName(), this.parent().name(), name(), site);
    }

    @Override
    Observable<SiteInner> getInner() {
        return manager().inner().webApps().getSlotAsync(resourceGroupName(), this.parent().name(), name());
    }

    @Override
    Observable<SiteConfigResourceInner> getConfigInner() {
        return manager().inner().webApps().getConfigurationSlotAsync(resourceGroupName(), parent().name(), name());
    }

    @Override
    Observable<SiteConfigResourceInner> createOrUpdateSiteConfig(SiteConfigResourceInner siteConfig) {
        return manager().inner().webApps().createOrUpdateConfigurationSlotAsync(resourceGroupName(), this.parent().name(), name(), siteConfig);
    }

    @Override
    Observable<Void> deleteHostNameBinding(String hostname) {
        return manager().inner().webApps().deleteHostNameBindingSlotAsync(resourceGroupName(), parent().name(), name(), hostname);
    }

    @Override
    Observable<StringDictionaryInner> listAppSettings() {
        return manager().inner().webApps().listApplicationSettingsSlotAsync(resourceGroupName(), parent().name(), name());
    }

    @Override
    Observable<StringDictionaryInner> updateAppSettings(StringDictionaryInner inner) {
        return manager().inner().webApps().updateApplicationSettingsSlotAsync(resourceGroupName(), parent().name(), name(), inner);
    }

    @Override
    Observable<ConnectionStringDictionaryInner> listConnectionStrings() {
        return manager().inner().webApps().listConnectionStringsSlotAsync(resourceGroupName(), parent().name(), name());
    }

    @Override
    Observable<ConnectionStringDictionaryInner> updateConnectionStrings(ConnectionStringDictionaryInner inner) {
        return manager().inner().webApps().updateConnectionStringsSlotAsync(resourceGroupName(), parent().name(), name(), inner);
    }

    @Override
    Observable<SlotConfigNamesResourceInner> listSlotConfigurations() {
        return manager().inner().webApps().listSlotConfigurationNamesAsync(resourceGroupName(), parent().name());
    }

    @Override
    Observable<SlotConfigNamesResourceInner> updateSlotConfigurations(SlotConfigNamesResourceInner inner) {
        return manager().inner().webApps().updateSlotConfigurationNamesAsync(resourceGroupName(), parent().name(), inner);
    }

    @Override
    Observable<SiteSourceControlInner> createOrUpdateSourceControl(SiteSourceControlInner inner) {
        return manager().inner().webApps().createOrUpdateSourceControlSlotAsync(resourceGroupName(), parent().name(), name(), inner);
    }

    @Override
    public void swap(String slotName) {
        swapAsync(slotName).toObservable().toBlocking().subscribe();
    }

    @Override
    public Completable swapAsync(String slotName) {
        return manager().inner().webApps().swapSlotSlotAsync(resourceGroupName(), this.parent().name(), name(), new CsmSlotEntityInner().withTargetSlot(slotName))
                .flatMap(new Func1<Void, Observable<?>>() {
                    @Override
                    public Observable<?> call(Void aVoid) {
                        return refreshAsync();
                    }
                }).toCompletable();
    }

    @Override
    public void applySlotConfigurations(String slotName) {
        applySlotConfigurationsAsync(slotName).toObservable().toBlocking().subscribe();
    }

    @Override
    public Completable applySlotConfigurationsAsync(String slotName) {
        return manager().inner().webApps().applySlotConfigurationSlotAsync(resourceGroupName(), this.parent().name(), name(), new CsmSlotEntityInner().withTargetSlot(slotName))
                .flatMap(new Func1<Void, Observable<?>>() {
                    @Override
                    public Observable<?> call(Void aVoid) {
                        return refreshAsync();
                    }
                }).toCompletable();
    }

    @Override
    public void resetSlotConfigurations() {
        resetSlotConfigurationsAsync().toObservable().toBlocking().subscribe();
    }

    @Override
    public Completable resetSlotConfigurationsAsync() {
        return manager().inner().webApps().resetSlotConfigurationSlotAsync(resourceGroupName(), this.parent().name(), name())
                .flatMap(new Func1<Void, Observable<?>>() {
                    @Override
                    public Observable<?> call(Void aVoid) {
                        return refreshAsync();
                    }
                }).toCompletable();
    }

    @Override
    Observable<Void> deleteSourceControl() {
        return manager().inner().webApps().deleteSourceControlSlotAsync(resourceGroupName(), parent().name(), name()).map(new Func1<Object, Void>() {
            @Override
            public Void call(Object o) {
                return null;
            }
        });
    }

    @Override
    Observable<SiteAuthSettingsInner> updateAuthentication(SiteAuthSettingsInner inner) {
        return manager().inner().webApps().updateAuthSettingsSlotAsync(resourceGroupName(), parent().name(), name(), inner);
    }

    @Override
    Observable<SiteAuthSettingsInner> getAuthentication() {
        return manager().inner().webApps().getAuthSettingsSlotAsync(resourceGroupName(), parent().name(), name());
    }

    @Override
    Observable<MSDeployStatusInner> createMSDeploy(MSDeployInner msDeployInner) {
        return parent().manager().inner().webApps()
                .createMSDeployOperationAsync(parent().resourceGroupName(), parent().name(), msDeployInner);
    }

    @Override
    public WebAppSourceControl getSourceControl() {
        return getSourceControlAsync().toBlocking().single();
    }

    @Override
    public Observable<WebAppSourceControl> getSourceControlAsync() {
        return manager().inner().webApps().getSourceControlSlotAsync(resourceGroupName(), parent().name(), name())
                .map(new Func1<SiteSourceControlInner, WebAppSourceControl>() {
                    @Override
                    public WebAppSourceControl call(SiteSourceControlInner siteSourceControlInner) {
                        return new WebAppSourceControlImpl<>(siteSourceControlInner, DeploymentSlotBaseImpl.this);
                    }
                });
    }

    @Override
    public byte[] getContainerLogs() {
        return getContainerLogsAsync().toBlocking().single();
    }

    @Override
    public Observable<byte[]> getContainerLogsAsync() {
        return manager().inner().webApps().getWebSiteContainerLogsSlotAsync(resourceGroupName(), parent().name(), name())
                .map(new Func1<InputStream, byte[]>() {
                    @Override
                    public byte[] call(InputStream inputStream) {
                        try {
                            return ByteStreams.toByteArray(inputStream);
                        } catch (IOException e) {
                            throw Exceptions.propagate(e);
                        }
                    }
                });
    }

    @Override
    public byte[] getContainerLogsZip() {
        return getContainerLogsZipAsync().toBlocking().single();
    }

    @Override
    public Observable<byte[]> getContainerLogsZipAsync() {
        return manager().inner().webApps().getWebSiteContainerLogsZipSlotAsync(resourceGroupName(), parent().name(), name())
                .map(new Func1<InputStream, byte[]>() {
                    @Override
                    public byte[] call(InputStream inputStream) {
                        try {
                            return ByteStreams.toByteArray(inputStream);
                        } catch (IOException e) {
                            throw Exceptions.propagate(e);
                        }
                    }
                });
    }

    @Override
    Observable<SiteLogsConfigInner> updateDiagnosticLogsConfig(SiteLogsConfigInner siteLogsConfigInner) {
        return manager().inner().webApps().updateDiagnosticLogsConfigSlotAsync(resourceGroupName(), parent().name(), name(), siteLogsConfigInner);
    }

    @Override
    public void verifyDomainOwnership(String certificateOrderName, String domainVerificationToken) {
        verifyDomainOwnershipAsync(certificateOrderName, domainVerificationToken).toObservable().toBlocking().subscribe();
    }

    @Override
    public Completable verifyDomainOwnershipAsync(String certificateOrderName, String domainVerificationToken) {
        IdentifierInner identifierInner = new IdentifierInner().withIdentifierId(domainVerificationToken);
        return manager().inner().webApps().createOrUpdateDomainOwnershipIdentifierSlotAsync(resourceGroupName(), parent().name(), name(), certificateOrderName, identifierInner)
                .map(new Func1<IdentifierInner, Void>() {
                    @Override
                    public Void call(IdentifierInner identifierInner) {
                        return null;
                    }
                }).toCompletable();
    }
}