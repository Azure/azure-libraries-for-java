/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.appservice.AppServiceCertificate;
import com.microsoft.azure.management.appservice.AppServiceDomain;
import com.microsoft.azure.management.appservice.AppSetting;
import com.microsoft.azure.management.appservice.AzureResourceType;
import com.microsoft.azure.management.appservice.CloningInfo;
import com.microsoft.azure.management.appservice.ConnStringValueTypePair;
import com.microsoft.azure.management.appservice.ConnectionString;
import com.microsoft.azure.management.appservice.ConnectionStringType;
import com.microsoft.azure.management.appservice.CustomHostNameDnsRecordType;
import com.microsoft.azure.management.appservice.FileSystemHttpLogsConfig;
import com.microsoft.azure.management.appservice.HostNameBinding;
import com.microsoft.azure.management.appservice.HostNameSslState;
import com.microsoft.azure.management.appservice.HostNameType;
import com.microsoft.azure.management.appservice.HttpLogsConfig;
import com.microsoft.azure.management.appservice.JavaVersion;
import com.microsoft.azure.management.appservice.ManagedPipelineMode;
import com.microsoft.azure.management.appservice.ManagedServiceIdentity;
import com.microsoft.azure.management.appservice.NetFrameworkVersion;
import com.microsoft.azure.management.appservice.OperatingSystem;
import com.microsoft.azure.management.appservice.PhpVersion;
import com.microsoft.azure.management.appservice.PlatformArchitecture;
import com.microsoft.azure.management.appservice.PythonVersion;
import com.microsoft.azure.management.appservice.RemoteVisualStudioVersion;
import com.microsoft.azure.management.appservice.ScmType;
import com.microsoft.azure.management.appservice.SiteAvailabilityState;
import com.microsoft.azure.management.appservice.SiteConfig;
import com.microsoft.azure.management.appservice.SslState;
import com.microsoft.azure.management.appservice.UsageState;
import com.microsoft.azure.management.appservice.WebAppAuthentication;
import com.microsoft.azure.management.appservice.WebAppBase;
import com.microsoft.azure.management.appservice.WebContainer;
import com.microsoft.azure.management.graphrbac.BuiltInRole;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.dag.FunctionalTaskItem;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import org.joda.time.DateTime;
import rx.Completable;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.FuncN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;

/**
 * The implementation for WebAppBase.
 * @param <FluentT> the fluent interface of the web app or deployment slot or function app
 * @param <FluentImplT> the fluent implementation of the web app or deployment slot or function app
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.AppService.Fluent")
abstract class WebAppBaseImpl<
        FluentT extends WebAppBase,
        FluentImplT extends WebAppBaseImpl<FluentT, FluentImplT>>
        extends GroupableResourceImpl<
            FluentT,
            SiteInner,
            FluentImplT,
            AppServiceManager>
        implements
            WebAppBase,
            WebAppBase.Definition<FluentT>,
            WebAppBase.Update<FluentT>,
            WebAppBase.UpdateStages.WithWebContainer<FluentT> {

    SiteConfigResourceInner siteConfig;

    private Set<String> hostNamesSet;
    private Set<String> enabledHostNamesSet;
    private Set<String> trafficManagerHostNamesSet;
    private Set<String> outboundIPAddressesSet;
    private Map<String, HostNameSslState> hostNameSslStateMap;
    private TreeMap<String, HostNameBindingImpl<FluentT, FluentImplT>> hostNameBindingsToCreate;
    private List<String> hostNameBindingsToDelete;
    private TreeMap<String, HostNameSslBindingImpl<FluentT, FluentImplT>> sslBindingsToCreate;

    private Map<String, String> appSettingsToAdd;
    private List<String> appSettingsToRemove;
    private Map<String, Boolean> appSettingStickiness;
    private Map<String, ConnStringValueTypePair> connectionStringsToAdd;
    private List<String> connectionStringsToRemove;
    private Map<String, Boolean> connectionStringStickiness;
    private WebAppSourceControlImpl<FluentT, FluentImplT> sourceControl;
    private boolean sourceControlToDelete;
    private MSDeployInner msDeploy;
    private WebAppAuthenticationImpl<FluentT, FluentImplT> authentication;
    private boolean authenticationToUpdate;
    private SiteLogsConfigInner siteLogsConfig;
    private FunctionalTaskItem msiHandler;
    private boolean isInCreateMode;

    WebAppBaseImpl(String name, SiteInner innerObject, SiteConfigResourceInner configObject, AppServiceManager manager) {
        super(name, innerObject, manager);
        if (innerObject != null && innerObject.kind() != null) {
            innerObject.withKind(innerObject.kind().replace(";", ","));
        }
        this.siteConfig = configObject;
        normalizeProperties();
        isInCreateMode = inner() == null || inner().id() == null;
    }

    public boolean isInCreateMode() {
        return isInCreateMode;
    }

    @Override
    public void setInner(SiteInner innerObject) {
        if (innerObject.kind() != null) {
            innerObject.withKind(innerObject.kind().replace(";", ","));
        }
        super.setInner(innerObject);
    }

    @SuppressWarnings("unchecked")
    private FluentT normalizeProperties() {
        this.hostNameBindingsToCreate = new TreeMap<>();
        this.hostNameBindingsToDelete = new ArrayList<>();
        this.appSettingsToAdd = new HashMap<>();
        this.appSettingsToRemove = new ArrayList<>();
        this.appSettingStickiness = new HashMap<>();
        this.connectionStringsToAdd = new HashMap<>();
        this.connectionStringsToRemove = new ArrayList<>();
        this.connectionStringStickiness = new HashMap<>();
        this.sourceControl = null;
        this.sourceControlToDelete = false;
        this.authenticationToUpdate = false;
        this.sslBindingsToCreate = new TreeMap<>();
        this.msiHandler = null;
        if (inner().hostNames() != null) {
            this.hostNamesSet = Sets.newHashSet(inner().hostNames());
        }
        if (inner().enabledHostNames() != null) {
            this.enabledHostNamesSet = Sets.newHashSet(inner().enabledHostNames());
        }
        if (inner().trafficManagerHostNames() != null) {
            this.trafficManagerHostNamesSet = Sets.newHashSet(inner().trafficManagerHostNames());
        }
        if (inner().outboundIpAddresses() != null) {
            this.outboundIPAddressesSet = Sets.newHashSet(inner().outboundIpAddresses().split(",[ ]*"));
        }
        this.hostNameSslStateMap = new HashMap<>();
        if (inner().hostNameSslStates() != null) {
            for (HostNameSslState hostNameSslState : inner().hostNameSslStates()) {
                // Server returns null sometimes, invalid on update, so we set default
                if (hostNameSslState.sslState() == null) {
                    hostNameSslState.withSslState(SslState.DISABLED);
                }
                hostNameSslStateMap.put(hostNameSslState.name(), hostNameSslState);
            }
        }
        return (FluentT) this;
    }

    @Override
    public String state() {
        return inner().state();
    }

    @Override
    public Set<String> hostNames() {
        return Collections.unmodifiableSet(hostNamesSet);
    }

    @Override
    public String repositorySiteName() {
        return inner().repositorySiteName();
    }

    @Override
    public UsageState usageState() {
        return inner().usageState();
    }

    @Override
    public boolean enabled() {
        return inner().enabled();
    }

    @Override
    public Set<String> enabledHostNames() {
        if (enabledHostNamesSet == null) {
            return null;
        }
        return Collections.unmodifiableSet(enabledHostNamesSet);
    }

    @Override
    public SiteAvailabilityState availabilityState() {
        return inner().availabilityState();
    }

    @Override
    public Map<String, HostNameSslState> hostNameSslStates() {
        return Collections.unmodifiableMap(hostNameSslStateMap);
    }

    @Override
    public String appServicePlanId() {
        return inner().serverFarmId();
    }

    @Override
    public DateTime lastModifiedTime() {
        return inner().lastModifiedTimeUtc();
    }

    @Override
    public Set<String> trafficManagerHostNames() {
        return Collections.unmodifiableSet(trafficManagerHostNamesSet);
    }

    @Override
    public boolean scmSiteAlsoStopped() {
        return inner().scmSiteAlsoStopped();
    }

    @Override
    public String targetSwapSlot() {
        return inner().targetSwapSlot();
    }

    @Override
    public boolean clientAffinityEnabled() {
        return inner().clientAffinityEnabled();
    }

    @Override
    public boolean clientCertEnabled() {
        return inner().clientCertEnabled();
    }

    @Override
    public boolean hostNamesDisabled() {
        return Utils.toPrimitiveBoolean(inner().hostNamesDisabled());
    }

    @Override
    public Set<String> outboundIPAddresses() {
        return Collections.unmodifiableSet(outboundIPAddressesSet);
    }

    @Override
    public int containerSize() {
        return Utils.toPrimitiveInt(inner().containerSize());
    }

    @Override
    public CloningInfo cloningInfo() {
        return inner().cloningInfo();
    }

    @Override
    public boolean isDefaultContainer() {
        return inner().isDefaultContainer();
    }

    @Override
    public String defaultHostName() {
        if (inner().defaultHostName() != null) {
            return inner().defaultHostName();
        } else {
            return "http://" + name() + ".azurewebsites.net";
        }
    }

    @Override
    public List<String> defaultDocuments() {
        if (siteConfig == null) {
            return null;
        }
        return Collections.unmodifiableList(siteConfig.defaultDocuments());
    }

    @Override
    public NetFrameworkVersion netFrameworkVersion() {
        if (siteConfig == null) {
            return null;
        }
        return NetFrameworkVersion.fromString(siteConfig.netFrameworkVersion());
    }

    @Override
    public PhpVersion phpVersion() {
        if (siteConfig == null || siteConfig.phpVersion() == null) {
            return PhpVersion.OFF;
        }
        return PhpVersion.fromString(siteConfig.phpVersion());
    }

    @Override
    public PythonVersion pythonVersion() {
        if (siteConfig == null || siteConfig.pythonVersion() == null) {
            return PythonVersion.OFF;
        }
        return PythonVersion.fromString(siteConfig.pythonVersion());
    }

    @Override
    public String nodeVersion() {
        if (siteConfig == null) {
            return null;
        }
        return siteConfig.nodeVersion();
    }

    @Override
    public boolean remoteDebuggingEnabled() {
        if (siteConfig == null) {
            return false;
        }
        return Utils.toPrimitiveBoolean(siteConfig.remoteDebuggingEnabled());
    }

    @Override
    public RemoteVisualStudioVersion remoteDebuggingVersion() {
        if (siteConfig == null) {
            return null;
        }
        return RemoteVisualStudioVersion.fromString(siteConfig.remoteDebuggingVersion());
    }

    @Override
    public boolean webSocketsEnabled() {
        if (siteConfig == null) {
            return false;
        }
        return Utils.toPrimitiveBoolean(siteConfig.webSocketsEnabled());
    }

    @Override
    public boolean alwaysOn() {
        if (siteConfig == null) {
            return false;
        }
        return Utils.toPrimitiveBoolean(siteConfig.alwaysOn());
    }

    @Override
    public JavaVersion javaVersion() {
        if (siteConfig == null || siteConfig.javaVersion() == null) {
            return JavaVersion.OFF;
        }
        return JavaVersion.fromString(siteConfig.javaVersion());
    }

    @Override
    public String javaContainer() {
        if (siteConfig == null) {
            return null;
        }
        return siteConfig.javaContainer();
    }

    @Override
    public String javaContainerVersion() {
        if (siteConfig == null) {
            return null;
        }
        return siteConfig.javaContainerVersion();
    }

    @Override
    public ManagedPipelineMode managedPipelineMode() {
        if (siteConfig == null) {
            return null;
        }
        return siteConfig.managedPipelineMode();
    }

    @Override
    public PlatformArchitecture platformArchitecture() {
        if (siteConfig.use32BitWorkerProcess()) {
            return PlatformArchitecture.X86;
        } else {
            return PlatformArchitecture.X64;
        }
    }

    @Override
    public String linuxFxVersion() {
        if (siteConfig == null) {
            return null;
        }
        return siteConfig.linuxFxVersion();
    }

    @Override
    public String autoSwapSlotName() {
        if (siteConfig == null) {
            return null;
        }
        return siteConfig.autoSwapSlotName();
    }

    @Override
    public OperatingSystem operatingSystem() {
        if (inner().kind() != null && inner().kind().toLowerCase().contains("linux")) {
            return OperatingSystem.LINUX;
        } else {
            return OperatingSystem.WINDOWS;
        }
    }

    @Override
    public String systemAssignedManagedServiceIdentityTenantId() {
        if (inner().identity() == null) {
            return null;
        }
        return inner().identity().tenantId();
    }

    @Override
    public String systemAssignedManagedServiceIdentityPrincipalId() {
        if (inner().identity() == null) {
            return null;
        }
        return inner().identity().principalId();
    }

    @Override
    public Map<String, AppSetting> getAppSettings() {
        return getAppSettingsAsync().toBlocking().single();
    }

    @Override
    public Observable<Map<String, AppSetting>> getAppSettingsAsync() {
        return Observable.zip(listAppSettings(), listSlotConfigurations(), new Func2<StringDictionaryInner, SlotConfigNamesResourceInner, Map<String, AppSetting>>() {
            @Override
            public Map<String, AppSetting> call(final StringDictionaryInner appSettingsInner, final SlotConfigNamesResourceInner slotConfigs) {
                if (appSettingsInner == null || appSettingsInner.properties() == null) {
                    return null;
                }
                return Maps.asMap(appSettingsInner.properties().keySet(), new Function<String, AppSetting>() {
                    @Override
                    public AppSetting apply(String input) {
                        return new AppSettingImpl(input, appSettingsInner.properties().get(input),
                                slotConfigs != null && slotConfigs.appSettingNames() != null && slotConfigs.appSettingNames().contains(input));
                    }
                });
            }
        });
    }

    @Override
    public Map<String, ConnectionString> getConnectionStrings() {
        return getConnectionStringsAsync().toBlocking().single();
    }

    @Override
    public Observable<Map<String, ConnectionString>> getConnectionStringsAsync() {
        return Observable.zip(listConnectionStrings(), listSlotConfigurations(), new Func2<ConnectionStringDictionaryInner, SlotConfigNamesResourceInner, Map<String, ConnectionString>>() {
            @Override
            public Map<String, ConnectionString> call(final ConnectionStringDictionaryInner connectionStringsInner, final SlotConfigNamesResourceInner slotConfigs) {
                if (connectionStringsInner == null || connectionStringsInner.properties() == null) {
                    return null;
                }
                return Maps.asMap(connectionStringsInner.properties().keySet(), new Function<String, ConnectionString>() {
                    @Override
                    public ConnectionString apply(String input) {
                        return new ConnectionStringImpl(input, connectionStringsInner.properties().get(input),
                                slotConfigs != null && slotConfigs.connectionStringNames() != null && slotConfigs.connectionStringNames().contains(input));
                    }
                });
            }
        });
    }

    @Override
    public WebAppAuthentication getAuthenticationConfig() {
        return getAuthenticationConfigAsync().toBlocking().single();
    }

    @Override
    public Observable<WebAppAuthentication> getAuthenticationConfigAsync() {
        return getAuthentication().map(new Func1<SiteAuthSettingsInner, WebAppAuthentication>() {
            @Override
            public WebAppAuthentication call(SiteAuthSettingsInner siteAuthSettingsInner) {
                return new WebAppAuthenticationImpl<>(siteAuthSettingsInner, WebAppBaseImpl.this);
            }
        });
    }

    abstract Observable<SiteInner> createOrUpdateInner(SiteInner site);

    abstract Observable<SiteInner> getInner();

    abstract Observable<SiteConfigResourceInner> getConfigInner();

    abstract Observable<SiteConfigResourceInner> createOrUpdateSiteConfig(SiteConfigResourceInner siteConfig);

    abstract Observable<Void> deleteHostNameBinding(String hostname);

    abstract Observable<StringDictionaryInner> listAppSettings();

    abstract Observable<StringDictionaryInner> updateAppSettings(StringDictionaryInner inner);

    abstract Observable<ConnectionStringDictionaryInner> listConnectionStrings();

    abstract Observable<ConnectionStringDictionaryInner> updateConnectionStrings(ConnectionStringDictionaryInner inner);

    abstract Observable<SlotConfigNamesResourceInner> listSlotConfigurations();

    abstract Observable<SlotConfigNamesResourceInner> updateSlotConfigurations(SlotConfigNamesResourceInner inner);

    abstract Observable<SiteSourceControlInner> createOrUpdateSourceControl(SiteSourceControlInner inner);

    abstract Observable<Void> deleteSourceControl();

    abstract Observable<SiteAuthSettingsInner> updateAuthentication(SiteAuthSettingsInner inner);

    abstract Observable<SiteAuthSettingsInner> getAuthentication();

    abstract Observable<MSDeployStatusInner> createMSDeploy(MSDeployInner msDeployInner);

    abstract Observable<SiteLogsConfigInner> updateDiagnosticLogsConfig(SiteLogsConfigInner siteLogsConfigInner);

    @Override
    public void beforeGroupCreateOrUpdate() {
        if (hostNameSslStateMap.size() > 0) {
            inner().withHostNameSslStates(new ArrayList<>(hostNameSslStateMap.values()));
        }
        // Hostname and SSL bindings
        addPostRunDependent(new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> call(Context context) {
                // Submit hostname bindings
                return submitHostNameBindings()
                        // Submit SSL bindings
                        .flatMap(new Func1<FluentT, Observable<Indexable>>() {
                            @Override
                            public Observable<Indexable> call(FluentT fluentT) {
                                return submitSslBindings(fluentT.inner());
                            }
                        });
            }
        });
        // Site config
        addPostRunDependent(new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> call(Context context) {
                return submitSiteConfig();
            }
        });
        // App settings and connection strings
        addPostRunDependent(new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> call(Context context) {
                return submitAppSettings().mergeWith(submitConnectionStrings())
                        .last().flatMap(new Func1<Indexable, Observable<Indexable>>() {
                            @Override
                            public Observable<Indexable> call(Indexable indexable) {
                                return submitStickiness();
                            }
                        });
            }
        });
        // Source control
        addPostRunDependent(new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> call(Context context) {
                return submitSourceControlToDelete().flatMap(new Func1<Indexable, Observable<Indexable>>() {
                    @Override
                    public Observable<Indexable> call(Indexable indexable) {
                        return submitSourceControlToCreate();
                    }
                });
            }
        });
        // Authentication
        addPostRunDependent(new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> call(Context context) {
                return submitAuthentication();
            }
        });
        // Log configuration
        addPostRunDependent(new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> call(Context context) {
                return submitLogConfiguration();
            }
        });
        // MSI roles
        if (msiHandler != null) {
            addPostRunDependent(msiHandler);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Observable<FluentT> createResourceAsync() {
        return submitSite(inner()).map(new Func1<SiteInner, FluentT>() {
            @Override
            public FluentT call(SiteInner siteInner) {
                setInner(siteInner);
                return (FluentT) WebAppBaseImpl.this;
            }
        });
    }

    @Override
    public Completable afterPostRunAsync(boolean succeeded) {
        if (succeeded) {
            isInCreateMode = false;
        }
        return Completable.fromAction(new Action0() {
            @Override
            public void call() {
                normalizeProperties();
            }
        });
    }

    Observable<SiteInner> submitSite(final SiteInner site) {
        site.withSiteConfig(new SiteConfig());
        // Construct web app observable
        return createOrUpdateInner(site)
                .map(new Func1<SiteInner, SiteInner>() {
                    @Override
                    public SiteInner call(SiteInner siteInner) {
                        site.withSiteConfig(null);
                        return siteInner;
                    }
                });
    }

    Observable<FluentT> submitHostNameBindings() {
        List<Observable<HostNameBinding>> bindingObservables = new ArrayList<>();
        for (HostNameBindingImpl<FluentT, FluentImplT> binding : hostNameBindingsToCreate.values()) {
            bindingObservables.add(Utils.<HostNameBinding>rootResource(binding.createAsync()));
        }
        for (String binding : hostNameBindingsToDelete) {
            bindingObservables.add(deleteHostNameBinding(binding).map(new Func1<Object, HostNameBinding>() {
                @Override
                public HostNameBinding call(Object o) {
                    return null;
                }
            }));
        }
        if (bindingObservables.isEmpty()) {
            return Observable.just((FluentT) this);
        } else {
            return Observable.zip(bindingObservables, new FuncN<WebAppBaseImpl>() {
                @Override
                public WebAppBaseImpl call(Object... args) {
                    return WebAppBaseImpl.this;
                }
            }).flatMap(new Func1<WebAppBaseImpl, Observable<FluentT>>() {
                @Override
                public Observable<FluentT> call(WebAppBaseImpl webAppBase) {
                    return webAppBase.refreshAsync();
                }
            });
        }
    }

    Observable<Indexable> submitSslBindings(final SiteInner site) {
        List<Observable<AppServiceCertificate>> certs = new ArrayList<>();
        for (final HostNameSslBindingImpl<FluentT, FluentImplT> binding : sslBindingsToCreate.values()) {
            certs.add(binding.newCertificate());
            hostNameSslStateMap.put(binding.inner().name(), binding.inner().withToUpdate(true));
        }
        if (certs.isEmpty()) {
            return Observable.just((Indexable) this);
        } else {
            site.withHostNameSslStates(new ArrayList<>(hostNameSslStateMap.values()));
            return Observable.zip(certs, new FuncN<SiteInner>() {
                @Override
                public SiteInner call(Object... args) {
                    return site;
                }
            }).flatMap(new Func1<SiteInner, Observable<SiteInner>>() {
                @Override
                public Observable<SiteInner> call(SiteInner inner) {
                    return createOrUpdateInner(inner);
                }
            }).map(new Func1<SiteInner, Indexable>() {
                @Override
                public Indexable call(SiteInner siteInner) {
                    setInner(siteInner);
                    return WebAppBaseImpl.this;
                }
            });
        }
    }

    Observable<Indexable> submitSiteConfig() {
        if (siteConfig == null) {
            return Observable.just((Indexable) this);
        }
        return createOrUpdateSiteConfig(siteConfig)
                .flatMap(new Func1<SiteConfigResourceInner, Observable<Indexable>>() {
                    @Override
                    public Observable<Indexable> call(SiteConfigResourceInner returnedSiteConfig) {
                        siteConfig = returnedSiteConfig;
                        return Observable.just((Indexable) WebAppBaseImpl.this);
                    }
                });
    }

    Observable<Indexable> submitAppSettings() {
        Observable<Indexable> observable = Observable.just((Indexable) this);
        if (!appSettingsToAdd.isEmpty() || !appSettingsToRemove.isEmpty()) {
            observable = listAppSettings()
                    .flatMap(new Func1<StringDictionaryInner, Observable<StringDictionaryInner>>() {
                        @Override
                        public Observable<StringDictionaryInner> call(StringDictionaryInner stringDictionaryInner) {
                            if (stringDictionaryInner == null) {
                                stringDictionaryInner = new StringDictionaryInner();
                            }
                            if (stringDictionaryInner.properties() == null) {
                                stringDictionaryInner.withProperties(new HashMap<String, String>());
                            }
                            for (String appSettingKey : appSettingsToRemove) {
                                stringDictionaryInner.properties().remove(appSettingKey);
                            }
                            stringDictionaryInner.properties().putAll(appSettingsToAdd);
                            return updateAppSettings(stringDictionaryInner);
                        }
                    }).map(new Func1<StringDictionaryInner, Indexable>() {
                        @Override
                        public Indexable call(StringDictionaryInner stringDictionaryInner) {
                            return WebAppBaseImpl.this;
                        }
                    });
        }
        return observable;
    }

    Observable<Indexable> submitConnectionStrings() {
        Observable<Indexable> observable = Observable.just((Indexable) this);
        if (!connectionStringsToAdd.isEmpty() || !connectionStringsToRemove.isEmpty()) {
            observable = listConnectionStrings()
                    .flatMap(new Func1<ConnectionStringDictionaryInner, Observable<ConnectionStringDictionaryInner>>() {
                        @Override
                        public Observable<ConnectionStringDictionaryInner> call(ConnectionStringDictionaryInner dictionaryInner) {
                            if (dictionaryInner == null) {
                                dictionaryInner = new ConnectionStringDictionaryInner();
                            }
                            if (dictionaryInner.properties() == null) {
                                dictionaryInner.withProperties(new HashMap<String, ConnStringValueTypePair>());
                            }
                            for (String connectionString : connectionStringsToRemove) {
                                dictionaryInner.properties().remove(connectionString);
                            }
                            dictionaryInner.properties().putAll(connectionStringsToAdd);
                            return updateConnectionStrings(dictionaryInner);
                        }
                    }).map(new Func1<ConnectionStringDictionaryInner, Indexable>() {
                        @Override
                        public Indexable call(ConnectionStringDictionaryInner stringDictionaryInner) {
                            return WebAppBaseImpl.this;
                        }
                    });
        }
        return observable;
    }

    Observable<Indexable> submitStickiness() {
        Observable<Indexable> observable = Observable.just((Indexable) this);
        if (!appSettingStickiness.isEmpty() || !connectionStringStickiness.isEmpty()) {
            observable = listSlotConfigurations()
                    .flatMap(new Func1<SlotConfigNamesResourceInner, Observable<SlotConfigNamesResourceInner>>() {
                        @Override
                        public Observable<SlotConfigNamesResourceInner> call(SlotConfigNamesResourceInner slotConfigNamesResourceInner) {
                            if (slotConfigNamesResourceInner == null) {
                                slotConfigNamesResourceInner = new SlotConfigNamesResourceInner();
                            }
                            if (slotConfigNamesResourceInner.appSettingNames() == null) {
                                slotConfigNamesResourceInner.withAppSettingNames(new ArrayList<String>());
                            }
                            if (slotConfigNamesResourceInner.connectionStringNames() == null) {
                                slotConfigNamesResourceInner.withConnectionStringNames(new ArrayList<String>());
                            }
                            Set<String> stickyAppSettingKeys = new HashSet<>(slotConfigNamesResourceInner.appSettingNames());
                            Set<String> stickyConnectionStringNames = new HashSet<>(slotConfigNamesResourceInner.connectionStringNames());
                            for (Map.Entry<String, Boolean> stickiness : appSettingStickiness.entrySet()) {
                                if (stickiness.getValue()) {
                                    stickyAppSettingKeys.add(stickiness.getKey());
                                } else {
                                    stickyAppSettingKeys.remove(stickiness.getKey());
                                }
                            }
                            for (Map.Entry<String, Boolean> stickiness : connectionStringStickiness.entrySet()) {
                                if (stickiness.getValue()) {
                                    stickyConnectionStringNames.add(stickiness.getKey());
                                } else {
                                    stickyConnectionStringNames.remove(stickiness.getKey());
                                }
                            }
                            slotConfigNamesResourceInner.withAppSettingNames(new ArrayList<>(stickyAppSettingKeys));
                            slotConfigNamesResourceInner.withConnectionStringNames(new ArrayList<>(stickyConnectionStringNames));
                            return updateSlotConfigurations(slotConfigNamesResourceInner);
                        }
                    }).map(new Func1<SlotConfigNamesResourceInner, Indexable>() {
                        @Override
                        public Indexable call(SlotConfigNamesResourceInner slotConfigNamesResourceInner) {
                            return WebAppBaseImpl.this;
                        }
                    });
        }
        return observable;
    }

    Observable<Indexable> submitSourceControlToCreate() {
        if (sourceControl == null || sourceControlToDelete) {
            return Observable.just((Indexable) this);
        }
        return sourceControl.registerGithubAccessToken()
                .flatMap(new Func1<SourceControlInner, Observable<SiteSourceControlInner>>() {
                    @Override
                    public Observable<SiteSourceControlInner> call(SourceControlInner sourceControlInner) {
                        return createOrUpdateSourceControl(sourceControl.inner());
                    }
                })
                .delay(new Func1<SiteSourceControlInner, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(SiteSourceControlInner siteSourceControlInner) {
                        return Observable.fromCallable(new Callable<Long>() {
                            @Override
                            public Long call() throws Exception {
                                SdkContext.sleep(30000);
                                return 30000L;
                            }
                        });
                    }
                })
                .map(new Func1<SiteSourceControlInner, Indexable>() {
                    @Override
                    public Indexable call(SiteSourceControlInner siteSourceControlInner) {
                        return WebAppBaseImpl.this;
                    }
                });
    }

    Observable<Indexable> submitSourceControlToDelete() {
        if (!sourceControlToDelete) {
            return Observable.just((Indexable) this);
        }
        return deleteSourceControl().map(new Func1<Void, Indexable>() {
            @Override
            public Indexable call(Void aVoid) {
                return WebAppBaseImpl.this;
            }
        });
    }

    Observable<Indexable> submitAuthentication() {
        if (!authenticationToUpdate) {
            return Observable.just((Indexable) this);
        }
        return updateAuthentication(authentication.inner()).map(new Func1<SiteAuthSettingsInner, Indexable>() {
            @Override
            public Indexable call(SiteAuthSettingsInner siteAuthSettingsInner) {
                return WebAppBaseImpl.this;
            }
        });
    }

    Observable<Indexable> submitLogConfiguration() {
        if (siteLogsConfig == null) {
            return Observable.just((Indexable) this);
        }
        return updateDiagnosticLogsConfig(siteLogsConfig)
                .map(new Func1<SiteLogsConfigInner, Indexable>() {
                    @Override
                    public Indexable call(SiteLogsConfigInner siteLogsConfigInner) {
                        siteLogsConfig = null;
                        return WebAppBaseImpl.this;
                    }
                });
    }

    @Override
    public WebDeploymentImpl<FluentT, FluentImplT> deploy() {
        return new WebDeploymentImpl<>(this);
    }

    WebAppBaseImpl<FluentT, FluentImplT> withNewHostNameSslBinding(final HostNameSslBindingImpl<FluentT, FluentImplT> hostNameSslBinding) {
        if (hostNameSslBinding.newCertificate() != null) {
            sslBindingsToCreate.put(hostNameSslBinding.name(), hostNameSslBinding);
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withManagedHostnameBindings(AppServiceDomain domain, String... hostnames) {
        for (String hostname : hostnames) {
            if (hostname.equals("@") || hostname.equalsIgnoreCase(domain.name())) {
                defineHostnameBinding()
                        .withAzureManagedDomain(domain)
                        .withSubDomain(hostname)
                        .withDnsRecordType(CustomHostNameDnsRecordType.A)
                        .attach();
            } else {
                defineHostnameBinding()
                        .withAzureManagedDomain(domain)
                        .withSubDomain(hostname)
                        .withDnsRecordType(CustomHostNameDnsRecordType.CNAME)
                        .attach();
            }
        }
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public HostNameBindingImpl<FluentT, FluentImplT> defineHostnameBinding() {
        HostNameBindingInner inner = new HostNameBindingInner();
        inner.withSiteName(name());
        inner.withAzureResourceType(AzureResourceType.WEBSITE);
        inner.withAzureResourceName(name());
        inner.withHostNameType(HostNameType.VERIFIED);
        return new HostNameBindingImpl<>(inner, (FluentImplT) this);
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withThirdPartyHostnameBinding(String domain, String... hostnames) {
        for (String hostname : hostnames) {
            defineHostnameBinding()
                    .withThirdPartyDomain(domain)
                    .withSubDomain(hostname)
                    .withDnsRecordType(CustomHostNameDnsRecordType.CNAME)
                    .attach();
        }
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withoutHostnameBinding(String hostname) {
        hostNameBindingsToDelete.add(hostname);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withoutSslBinding(String hostname) {
        if (hostNameSslStateMap.containsKey(hostname)) {
            hostNameSslStateMap.get(hostname).withSslState(SslState.DISABLED).withToUpdate(true);
        }
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    FluentImplT withHostNameBinding(final HostNameBindingImpl<FluentT, FluentImplT> hostNameBinding) {
        this.hostNameBindingsToCreate.put(
                hostNameBinding.name(),
                hostNameBinding);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withAppDisabledOnCreation() {
        inner().withEnabled(false);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withScmSiteAlsoStopped(boolean scmSiteAlsoStopped) {
        inner().withScmSiteAlsoStopped(scmSiteAlsoStopped);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withClientAffinityEnabled(boolean enabled) {
        inner().withClientAffinityEnabled(enabled);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withClientCertEnabled(boolean enabled) {
        inner().withClientCertEnabled(enabled);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public HostNameSslBindingImpl<FluentT, FluentImplT> defineSslBinding() {
        return new HostNameSslBindingImpl<>(new HostNameSslState(), (FluentImplT) this);
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withNetFrameworkVersion(NetFrameworkVersion version) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        siteConfig.withNetFrameworkVersion(version.toString());
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withPhpVersion(PhpVersion version) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        siteConfig.withPhpVersion(version.toString());
        return (FluentImplT) this;
    }

    public FluentImplT withoutPhp() {
        return withPhpVersion(PhpVersion.fromString(""));
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withJavaVersion(JavaVersion version) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        siteConfig.withJavaVersion(version.toString());
        return (FluentImplT) this;
    }

    public FluentImplT withoutJava() {
        return withJavaVersion(JavaVersion.fromString("")).withWebContainer(null);
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withWebContainer(WebContainer webContainer) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        if (webContainer == null) {
            siteConfig.withJavaContainer(null);
            siteConfig.withJavaContainerVersion(null);
        } else {
            String[] containerInfo = webContainer.toString().split(" ");
            siteConfig.withJavaContainer(containerInfo[0]);
            siteConfig.withJavaContainerVersion(containerInfo[1]);
        }
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withPythonVersion(PythonVersion version) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        siteConfig.withPythonVersion(version.toString());
        return (FluentImplT) this;
    }

    public FluentImplT withoutPython() {
        return withPythonVersion(PythonVersion.fromString(""));
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withPlatformArchitecture(PlatformArchitecture platform) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        siteConfig.withUse32BitWorkerProcess(platform.equals(PlatformArchitecture.X86));
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withWebSocketsEnabled(boolean enabled) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        siteConfig.withWebSocketsEnabled(enabled);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withWebAppAlwaysOn(boolean alwaysOn) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        siteConfig.withAlwaysOn(alwaysOn);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withManagedPipelineMode(ManagedPipelineMode managedPipelineMode) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        siteConfig.withManagedPipelineMode(managedPipelineMode);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withAutoSwapSlotName(String slotName) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        siteConfig.withAutoSwapSlotName(slotName);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withRemoteDebuggingEnabled(RemoteVisualStudioVersion remoteVisualStudioVersion) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        siteConfig.withRemoteDebuggingEnabled(true);
        siteConfig.withRemoteDebuggingVersion(remoteVisualStudioVersion.toString());
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withRemoteDebuggingDisabled() {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        siteConfig.withRemoteDebuggingEnabled(false);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withDefaultDocument(String document) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        if (siteConfig.defaultDocuments() == null) {
            siteConfig.withDefaultDocuments(new ArrayList<String>());
        }
        siteConfig.defaultDocuments().add(document);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withDefaultDocuments(List<String> documents) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        if (siteConfig.defaultDocuments() == null) {
            siteConfig.withDefaultDocuments(new ArrayList<String>());
        }
        siteConfig.defaultDocuments().addAll(documents);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withoutDefaultDocument(String document) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        if (siteConfig.defaultDocuments() != null) {
            siteConfig.defaultDocuments().remove(document);
        }
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withAppSetting(String key, String value) {
        appSettingsToAdd.put(key, value);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withAppSettings(Map<String, String> settings) {
        appSettingsToAdd.putAll(settings);
        return (FluentImplT) this;
    }

    public FluentImplT withStickyAppSetting(String key, String value) {
        withAppSetting(key, value);
        return withAppSettingStickiness(key, true);
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withStickyAppSettings(Map<String, String> settings) {
        withAppSettings(settings);
        appSettingStickiness.putAll(Maps.asMap(settings.keySet(), new Function<String, Boolean>() {
            @Override
            public Boolean apply(String input) {
                return true;
            }
        }));
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withoutAppSetting(String key) {
        appSettingsToRemove.add(key);
        appSettingStickiness.remove(key);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withAppSettingStickiness(String key, boolean sticky) {
        appSettingStickiness.put(key, sticky);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withConnectionString(String name, String value, ConnectionStringType type) {
        connectionStringsToAdd.put(name, new ConnStringValueTypePair().withValue(value).withType(type));
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withStickyConnectionString(String name, String value, ConnectionStringType type) {
        connectionStringsToAdd.put(name, new ConnStringValueTypePair().withValue(value).withType(type));
        connectionStringStickiness.put(name, true);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withoutConnectionString(String name) {
        connectionStringsToRemove.add(name);
        connectionStringStickiness.remove(name);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withConnectionStringStickiness(String name, boolean stickiness) {
        connectionStringStickiness.put(name, stickiness);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    void withSourceControl(WebAppSourceControlImpl<FluentT, FluentImplT> sourceControl) {
        this.sourceControl = sourceControl;
    }

    public WebAppSourceControlImpl<FluentT, FluentImplT> defineSourceControl() {
        SiteSourceControlInner sourceControlInner = new SiteSourceControlInner();
        return new WebAppSourceControlImpl<>(sourceControlInner, this);
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withLocalGitSourceControl() {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        siteConfig.withScmType(ScmType.LOCAL_GIT);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withoutSourceControl() {
        sourceControlToDelete = true;
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    void withAuthentication(WebAppAuthenticationImpl<FluentT, FluentImplT> authentication) {
        this.authentication = authentication;
        authenticationToUpdate = true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Observable<FluentT> refreshAsync() {
        return super.refreshAsync().flatMap(new Func1<FluentT, Observable<FluentT>>() {
            @Override
            public Observable<FluentT> call(final FluentT fluentT) {
                return getConfigInner().map(new Func1<SiteConfigResourceInner, FluentT>() {
                    @Override
                    public FluentT call(SiteConfigResourceInner returnedSiteConfig) {
                        siteConfig = returnedSiteConfig;
                        return fluentT;
                    }
                });
            }
        });
    }

    @Override
    protected Observable<SiteInner> getInnerAsync() {
        return getInner();
    }

    @Override
    public WebAppAuthenticationImpl<FluentT, FluentImplT> defineAuthentication() {
        return new WebAppAuthenticationImpl<>(new SiteAuthSettingsInner().withEnabled(true), this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withoutAuthentication() {
        this.authentication.inner().withEnabled(false);
        authenticationToUpdate = true;
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withContainerLoggingEnabled(int quotaInMB, int retentionDays) {
        siteLogsConfig = new SiteLogsConfigInner()
                .withHttpLogs(new HttpLogsConfig().withFileSystem(
                        new FileSystemHttpLogsConfig().withEnabled(true).withRetentionInMb(quotaInMB).withRetentionInDays(retentionDays)));
        return (FluentImplT) this;
    }

    @Override
    public FluentImplT withContainerLoggingEnabled() {
        return withContainerLoggingEnabled(35, 0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withContainerLoggingDisabled() {
        siteLogsConfig = new SiteLogsConfigInner()
                .withHttpLogs(new HttpLogsConfig().withFileSystem(
                        new FileSystemHttpLogsConfig().withEnabled(false)));
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withSystemAssignedManagedServiceIdentity() {
        inner().withIdentity(new ManagedServiceIdentity().withType("SystemAssigned"));
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withSystemAssignedIdentityBasedAccessTo(final String resourceId, final BuiltInRole role) {
        if (inner().identity() == null || inner().identity().type() == null) {
            throw new IllegalArgumentException("The web app must be assigned with Managed Service Identity.");
        }
        msiHandler = new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> call(Context context) {
                return manager().rbacManager().roleAssignments().define(SdkContext.randomUuid())
                        .forObjectId(systemAssignedManagedServiceIdentityPrincipalId())
                        .withBuiltInRole(role)
                        .withScope(resourceId)
                        .createAsync();
            }
        };
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withSystemAssignedIdentityBasedAccessToCurrentResourceGroup(final BuiltInRole role) {
        if (inner().identity() == null || inner().identity().type() == null) {
            throw new IllegalArgumentException("The web app must be assigned with Managed Service Identity.");
        }
        msiHandler = new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> call(Context context) {
                return manager().rbacManager().roleAssignments().define(SdkContext.randomUuid())
                        .forObjectId(systemAssignedManagedServiceIdentityPrincipalId())
                        .withBuiltInRole(role)
                        .withScope(resourceGroupId(id()))
                        .createAsync();
            }
        };
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withSystemAssignedIdentityBasedAccessTo(final String resourceId, final String roleDefinitionId) {
        if (inner().identity() == null || inner().identity().type() == null) {
            throw new IllegalArgumentException("The web app must be assigned with Managed Service Identity.");
        }
        msiHandler = new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> call(Context context) {
                return manager().rbacManager().roleAssignments().define(SdkContext.randomUuid())
                        .forServicePrincipal(systemAssignedManagedServiceIdentityPrincipalId())
                        .withRoleDefinition(roleDefinitionId)
                        .withScope(resourceId)
                        .createAsync();
            }
        };
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withSystemAssignedIdentityBasedAccessToCurrentResourceGroup(final String roleDefinitionId) {
        if (inner().identity() == null || inner().identity().type() == null) {
            throw new IllegalArgumentException("The web app must be assigned with Managed Service Identity.");
        }
        msiHandler = new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> call(Context context) {
                return manager().rbacManager().roleAssignments().define(SdkContext.randomUuid())
                        .forServicePrincipal(systemAssignedManagedServiceIdentityPrincipalId())
                        .withRoleDefinition(roleDefinitionId)
                        .withScope(resourceGroupId(id()))
                        .createAsync();
            }
        };
        return (FluentImplT) this;
    }

    private static String resourceGroupId(String id) {
        final ResourceId resourceId = ResourceId.fromString(id);
        return String.format("/subscriptions/%s/resourceGroups/%s",
                resourceId.subscriptionId(),
                resourceId.resourceGroupName());

    }
}
