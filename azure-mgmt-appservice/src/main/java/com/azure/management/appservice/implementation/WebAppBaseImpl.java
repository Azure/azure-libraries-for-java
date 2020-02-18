/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.appservice.implementation;

import com.azure.core.exception.HttpResponseException;
import com.azure.core.management.AzureEnvironment;
import com.azure.management.appservice.models.ConnectionStringDictionaryInner;
import com.azure.management.appservice.models.HostNameBindingInner;
import com.azure.management.appservice.models.MSDeployStatusInner;
import com.azure.management.appservice.models.SiteAuthSettingsInner;
import com.azure.management.appservice.models.SiteConfigInner;
import com.azure.management.appservice.models.SiteConfigResourceInner;
import com.azure.management.appservice.models.SiteInner;
import com.azure.management.appservice.models.SiteLogsConfigInner;
import com.azure.management.appservice.models.SitePatchResourceInner;
import com.azure.management.appservice.models.SiteSourceControlInner;
import com.azure.management.appservice.models.SlotConfigNamesResourceInner;
import com.azure.management.appservice.models.StringDictionaryInner;
import com.azure.management.appservice.AppServiceCertificate;
import com.azure.management.appservice.AppServiceDomain;
import com.azure.management.appservice.AppSetting;
import com.azure.management.appservice.AzureResourceType;
import com.azure.management.appservice.CloningInfo;
import com.azure.management.appservice.ConnStringValueTypePair;
import com.azure.management.appservice.ConnectionString;
import com.azure.management.appservice.ConnectionStringType;
import com.azure.management.appservice.CustomHostNameDnsRecordType;
import com.azure.management.appservice.FtpsState;
import com.azure.management.appservice.HostNameBinding;
import com.azure.management.appservice.HostNameSslState;
import com.azure.management.appservice.HostNameType;
import com.azure.management.appservice.JavaVersion;
import com.azure.management.appservice.MSDeploy;
import com.azure.management.appservice.ManagedPipelineMode;
import com.azure.management.appservice.NetFrameworkVersion;
import com.azure.management.appservice.OperatingSystem;
import com.azure.management.appservice.PhpVersion;
import com.azure.management.appservice.PlatformArchitecture;
import com.azure.management.appservice.PythonVersion;
import com.azure.management.appservice.RemoteVisualStudioVersion;
import com.azure.management.appservice.ScmType;
import com.azure.management.appservice.SiteAvailabilityState;
import com.azure.management.appservice.SslState;
import com.azure.management.appservice.SupportedTlsVersions;
import com.azure.management.appservice.UsageState;
import com.azure.management.appservice.VirtualApplication;
import com.azure.management.appservice.WebAppAuthentication;
import com.azure.management.appservice.WebAppBase;
import com.azure.management.appservice.WebContainer;
import com.azure.management.graphrbac.BuiltInRole;
import com.azure.management.graphrbac.implementation.RoleAssignmentHelper;
import com.azure.management.msi.Identity;
import com.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.azure.management.resources.fluentcore.dag.FunctionalTaskItem;
import com.azure.management.resources.fluentcore.dag.IndexableTaskItem;
import com.azure.management.resources.fluentcore.model.Creatable;
import com.azure.management.resources.fluentcore.model.Indexable;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import com.azure.management.resources.fluentcore.utils.Utils;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * The implementation for WebAppBase.
 * @param <FluentT> the fluent interface of the web app or deployment slot or function app
 * @param <FluentImplT> the fluent implementation of the web app or deployment slot or function app
 */
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

    private static final Map<AzureEnvironment, String> DNS_MAP = new HashMap<AzureEnvironment, String>() {{
        put(AzureEnvironment.AZURE, "azurewebsites.net");
        put(AzureEnvironment.AZURE_CHINA, "chinacloudsites.cn");
        put(AzureEnvironment.AZURE_GERMANY, "azurewebsites.de");
        put(AzureEnvironment.AZURE_US_GOVERNMENT, "azurewebsites.us");
    }};

    SiteConfigResourceInner siteConfig;
    KuduClient kuduClient;

    private Set<String> hostNamesSet;
    private Set<String> enabledHostNamesSet;
    private Set<String> trafficManagerHostNamesSet;
    private Set<String> outboundIPAddressesSet;
    private Map<String, HostNameSslState> hostNameSslStateMap;
    private TreeMap<String, HostNameBindingImpl<FluentT, FluentImplT>> hostNameBindingsToCreate;
    private List<String> hostNameBindingsToDelete;
    private TreeMap<String, HostNameSslBindingImpl<FluentT, FluentImplT>> sslBindingsToCreate;

    protected Map<String, String> appSettingsToAdd;
    protected List<String> appSettingsToRemove;
    private Map<String, Boolean> appSettingStickiness;
    private Map<String, ConnStringValueTypePair> connectionStringsToAdd;
    private List<String> connectionStringsToRemove;
    private Map<String, Boolean> connectionStringStickiness;
    private WebAppSourceControlImpl<FluentT, FluentImplT> sourceControl;
    private boolean sourceControlToDelete;
    private MSDeploy msDeploy;
    private WebAppAuthenticationImpl<FluentT, FluentImplT> authentication;
    private boolean authenticationToUpdate;
    private WebAppDiagnosticLogsImpl<FluentT, FluentImplT> diagnosticLogs;
    private boolean diagnosticLogsToUpdate;
    private FunctionalTaskItem msiHandler;
    private boolean isInCreateMode;
    private WebAppMsiHandler webAppMsiHandler;

    WebAppBaseImpl(String name, SiteInner innerObject, SiteConfigResourceInner siteConfig, SiteLogsConfigInner logConfig, AppServiceManager manager) {
        super(name, innerObject, manager);
        if (innerObject != null && innerObject.kind() != null) {
            innerObject.withKind(innerObject.kind().replace(";", ","));
        }
        this.siteConfig = siteConfig;
        if (logConfig != null) {
            this.diagnosticLogs = new WebAppDiagnosticLogsImpl<>(logConfig, this);
        }

        webAppMsiHandler = new WebAppMsiHandler(manager.rbacManager(), this);
        normalizeProperties();
        isInCreateMode = inner() == null || inner().getId() == null;
        if (!isInCreateMode) {
            initializeKuduClient();
        }
    }

    public boolean isInCreateMode() {
        return isInCreateMode;
    }

    private void initializeKuduClient() {
        if (kuduClient == null) {
            kuduClient = new KuduClient(this);
        }
    }

    @Override
    public void setInner(SiteInner innerObject) {
        if (innerObject.kind() != null) {
            innerObject.withKind(innerObject.kind().replace(";", ","));
        }
        super.setInner(innerObject);
    }

    RoleAssignmentHelper.IdProvider idProvider() {
        return new RoleAssignmentHelper.IdProvider() {
            @Override
            public String principalId() {
                if (inner() != null && inner().identity() != null) {
                    return inner().identity().principalId();
                } else {
                    return null;
                }
            }

            @Override
            public String resourceId() {
                if (inner() != null) {
                    return inner().getId();
                } else {
                    return null;
                }
            }
        };
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
        this.diagnosticLogsToUpdate = false;
        this.sslBindingsToCreate = new TreeMap<>();
        this.msiHandler = null;
        if (inner().hostNames() != null) {
            this.hostNamesSet = new HashSet<>(inner().hostNames());
        }
        if (inner().enabledHostNames() != null) {
            this.enabledHostNamesSet = new HashSet<>(inner().enabledHostNames());
        }
        if (inner().trafficManagerHostNames() != null) {
            this.trafficManagerHostNamesSet = new HashSet<>(inner().trafficManagerHostNames());
        }
        if (inner().outboundIpAddresses() != null) {
            this.outboundIPAddressesSet = new HashSet<>(Arrays.asList(inner().outboundIpAddresses().split(",[ ]*")));
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
        this.webAppMsiHandler.clear();
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
    public OffsetDateTime lastModifiedTime() {
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
            AzureEnvironment environment = Utils.extractAzureEnvironment(manager().restClient());
            String dns = DNS_MAP.get(environment);
            String leaf = name();
            if (this instanceof DeploymentSlotBaseImpl<?, ?, ?, ?, ?>) {
                leaf = ((DeploymentSlotBaseImpl<?, ?, ?, ?, ?>) this).parent().name() + "-" + leaf;
            }
            return leaf + "." + dns;
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
    public boolean httpsOnly() {
        return Utils.toPrimitiveBoolean(inner().httpsOnly());
    }

    @Override
    public FtpsState ftpsState() {
        if (siteConfig == null) {
            return null;
        }
        return siteConfig.ftpsState();
    }

    @Override
    public List<VirtualApplication> virtualApplications() {
        if (siteConfig == null) {
            return null;
        }
        return siteConfig.virtualApplications();
    }

    @Override
    public boolean http20Enabled() {
        if (siteConfig == null) {
            return false;
        }
        return Utils.toPrimitiveBoolean(siteConfig.http20Enabled());
    }

    @Override
    public boolean localMySqlEnabled() {
        if (siteConfig == null) {
            return false;
        }
        return Utils.toPrimitiveBoolean(siteConfig.localMySqlEnabled());
    }

    @Override
    public ScmType scmType() {
        if (siteConfig == null) {
            return null;
        }
        return siteConfig.scmType();
    }

    @Override
    public String documentRoot() {
        if (siteConfig == null) {
            return null;
        }
        return siteConfig.documentRoot();
    }

    @Override
    public SupportedTlsVersions minTlsVersion() {
        if (siteConfig == null) {
            return null;
        }
        return siteConfig.minTlsVersion();
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
    public Set<String> userAssignedManagedServiceIdentityIds() {
        if (inner().identity() == null) {
            return null;
        }
        return inner().identity().userAssignedIdentities().keySet();
    }

    @Override
    public WebAppDiagnosticLogsImpl<FluentT, FluentImplT> diagnosticLogsConfig() {
        return diagnosticLogs;
    }

    @Override
    public InputStream streamApplicationLogs() {
        return pipeObservableToInputStream(streamApplicationLogsAsync());
    }

    @Override
    public Flux<String> streamApplicationLogsAsync() {
        return kuduClient.streamApplicationLogsAsync();
    }

    @Override
    public InputStream streamHttpLogs() {
        return pipeObservableToInputStream(streamHttpLogsAsync());
    }

    @Override
    public Flux<String> streamHttpLogsAsync() {
        return kuduClient.streamHttpLogsAsync();
    }

    @Override
    public InputStream streamTraceLogs() {
        return pipeObservableToInputStream(streamTraceLogsAsync());
    }

    @Override
    public Flux<String> streamTraceLogsAsync() {
        return kuduClient.streamTraceLogsAsync();
    }

    @Override
    public InputStream streamDeploymentLogs() {
        return pipeObservableToInputStream(streamDeploymentLogsAsync());
    }

    @Override
    public Flux<String> streamDeploymentLogsAsync() {
        return kuduClient.streamDeploymentLogsAsync();
    }

    @Override
    public InputStream streamAllLogs() {
        return pipeObservableToInputStream(streamAllLogsAsync());
    }

    @Override
    public Flux<String> streamAllLogsAsync() {
        return kuduClient.streamAllLogsAsync();
    }

    private InputStream pipeObservableToInputStream(Flux<String> observable) {
        PipedInputStreamWithCallback in = new PipedInputStreamWithCallback();
        final PipedOutputStream out = new PipedOutputStream();
        try {
            in.connect(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final Disposable subscription = observable
                // Do not block current thread
                .subscribeOn(Schedulers.elastic())
                .subscribe(s -> {
                    try {
                        out.write(s.getBytes());
                        out.write('\n');
                        out.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        in.addCallback(() -> {
            subscription.dispose();
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return in;
    }

    @Override
    public Map<String, AppSetting> getAppSettings() {
        return getAppSettingsAsync().block();
    }

    @Override
    public Mono<Map<String, AppSetting>> getAppSettingsAsync() {
        return Mono.zip(listAppSettings(), listSlotConfigurations(), (final StringDictionaryInner appSettingsInner, final SlotConfigNamesResourceInner slotConfigs) -> {
            Map<String, AppSetting> appSettingMap = new HashMap<>();
            for (Map.Entry<String, String> entry : appSettingsInner.properties().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                appSettingMap.put(key, new AppSettingImpl(key, value, slotConfigs.appSettingNames() != null && slotConfigs.appSettingNames().contains(key)));
            }
            return appSettingMap;
        });
    }

    @Override
    public Map<String, ConnectionString> getConnectionStrings() {
        return getConnectionStringsAsync().block();
    }

    @Override
    public Mono<Map<String, ConnectionString>> getConnectionStringsAsync() {
        return Mono.zip(listConnectionStrings(), listSlotConfigurations(), (final ConnectionStringDictionaryInner connectionStringsInner, final SlotConfigNamesResourceInner slotConfigs) -> {
            Map<String, ConnectionString> connectionStringMap = new HashMap<>();
            for (Map.Entry<String, ConnStringValueTypePair> entry : connectionStringsInner.properties().entrySet()) {
                String key = entry.getKey();
                ConnStringValueTypePair value = entry.getValue();
                connectionStringMap.put(key, new ConnectionStringImpl(key, value, slotConfigs.appSettingNames() != null && slotConfigs.appSettingNames().contains(key)));
            }
            return connectionStringMap;
        });
    }

    @Override
    public WebAppAuthentication getAuthenticationConfig() {
        return getAuthenticationConfigAsync().block();
    }

    @Override
    public Mono<WebAppAuthentication> getAuthenticationConfigAsync() {
        return getAuthentication().map(siteAuthSettingsInner -> new WebAppAuthenticationImpl<>(siteAuthSettingsInner, WebAppBaseImpl.this));
    }

    abstract Mono<SiteInner> createOrUpdateInner(SiteInner site);

    abstract Mono<SiteInner> updateInner(SitePatchResourceInner siteUpdate);

    abstract Mono<SiteInner> getInner();

    abstract Mono<SiteConfigResourceInner> getConfigInner();

    abstract Mono<SiteConfigResourceInner> createOrUpdateSiteConfig(SiteConfigResourceInner siteConfig);

    abstract Mono<Void> deleteHostNameBinding(String hostname);

    abstract Mono<StringDictionaryInner> listAppSettings();

    abstract Mono<StringDictionaryInner> updateAppSettings(StringDictionaryInner inner);

    abstract Mono<ConnectionStringDictionaryInner> listConnectionStrings();

    abstract Mono<ConnectionStringDictionaryInner> updateConnectionStrings(ConnectionStringDictionaryInner inner);

    abstract Mono<SlotConfigNamesResourceInner> listSlotConfigurations();

    abstract Mono<SlotConfigNamesResourceInner> updateSlotConfigurations(SlotConfigNamesResourceInner inner);

    abstract Mono<SiteSourceControlInner> createOrUpdateSourceControl(SiteSourceControlInner inner);

    abstract Mono<Void> deleteSourceControl();

    abstract Mono<SiteAuthSettingsInner> updateAuthentication(SiteAuthSettingsInner inner);

    abstract Mono<SiteAuthSettingsInner> getAuthentication();

    abstract Mono<MSDeployStatusInner> createMSDeploy(MSDeploy msDeployInner);

    abstract Mono<SiteLogsConfigInner> updateDiagnosticLogsConfig(SiteLogsConfigInner siteLogsConfigInner);

    @Override
    public void beforeGroupCreateOrUpdate() {
        if (hostNameSslStateMap.size() > 0) {
            inner().withHostNameSslStates(new ArrayList<>(hostNameSslStateMap.values()));
        }
        // Hostname and SSL bindings
        IndexableTaskItem rootTaskItem = wrapTask(new FunctionalTaskItem() {
            @Override
            public Mono<Indexable> apply(Context context) {
                // Submit hostname bindings
                return submitHostNameBindings()
                        // Submit SSL bindings
                        .flatMap(fluentT -> submitSslBindings(fluentT.inner()));
            }
        });
        IndexableTaskItem lastTaskItem = rootTaskItem;
        // Site config
        lastTaskItem = sequentialTask(lastTaskItem, new FunctionalTaskItem() {
            @Override
            public Mono<Indexable> apply(Context context) {
                return submitSiteConfig();
            }
        });
        // Metadata, app settings, and connection strings
        lastTaskItem = sequentialTask(lastTaskItem, new FunctionalTaskItem() {
            @Override
            public Mono<Indexable> apply(Context context) {
                return submitMetadata()
                        .flatMap(ignored -> submitAppSettings().mergeWith(submitConnectionStrings())
                        .last())
                        .flatMap(ignored -> submitStickiness());
            }
        });
        // Source control
        lastTaskItem = sequentialTask(lastTaskItem, new FunctionalTaskItem() {
            @Override
            public Mono<Indexable> apply(Context context) {
                return submitSourceControlToDelete().flatMap(indexable -> submitSourceControlToCreate());
            }
        });
        // Authentication
        lastTaskItem = sequentialTask(lastTaskItem, new FunctionalTaskItem() {
            @Override
            public Mono<Indexable> apply(Context context) {
                return submitAuthentication();
            }
        });
        // Log configuration
        lastTaskItem = sequentialTask(lastTaskItem, new FunctionalTaskItem() {
            @Override
            public Mono<Indexable> apply(Context context) {
                return submitLogConfiguration();
            }
        });
        // MSI roles
        if (msiHandler != null) {
            lastTaskItem = sequentialTask(lastTaskItem, msiHandler);
        }

        addPostRunDependent(rootTaskItem);
    }

    private static IndexableTaskItem wrapTask(FunctionalTaskItem taskItem) {
        return IndexableTaskItem.create(taskItem);
    }

    private static IndexableTaskItem sequentialTask(IndexableTaskItem taskItem1, FunctionalTaskItem taskItem2) {
        IndexableTaskItem taskItem = IndexableTaskItem.create(taskItem2);
        taskItem1.addPostRunDependent(taskItem);
        return taskItem;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Mono<FluentT> createResourceAsync() {
        this.webAppMsiHandler.processCreatedExternalIdentities();
        this.webAppMsiHandler.handleExternalIdentities();
        return submitSite(inner()).map(siteInner -> {
            setInner(siteInner);
            return (FluentT) WebAppBaseImpl.this;
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public Mono<FluentT> updateResourceAsync() {
        SiteInner siteInner = (SiteInner) this.inner();
        SitePatchResourceInner siteUpdate = new SitePatchResourceInner();
        siteUpdate.withHostNameSslStates(siteInner.hostNameSslStates());
        siteUpdate.withKind(siteInner.kind());
        siteUpdate.withEnabled(siteInner.enabled());
        siteUpdate.withServerFarmId(siteInner.serverFarmId());
        siteUpdate.withReserved(siteInner.reserved());
        siteUpdate.withIsXenon(siteInner.isXenon());
        siteUpdate.withHyperV(siteInner.hyperV());
        siteUpdate.withScmSiteAlsoStopped(siteInner.scmSiteAlsoStopped());
        siteUpdate.withHostingEnvironmentProfile(siteInner.hostingEnvironmentProfile());
        siteUpdate.withClientAffinityEnabled(siteInner.clientAffinityEnabled());
        siteUpdate.withClientCertEnabled(siteInner.clientCertEnabled());
        siteUpdate.withClientCertExclusionPaths(siteInner.clientCertExclusionPaths());
        siteUpdate.withHostNamesDisabled(siteInner.hostNamesDisabled());
        siteUpdate.withContainerSize(siteInner.containerSize());
        siteUpdate.withDailyMemoryTimeQuota(siteInner.dailyMemoryTimeQuota());
        siteUpdate.withCloningInfo(siteInner.cloningInfo());
        siteUpdate.withHttpsOnly(siteInner.httpsOnly());
        siteUpdate.withRedundancyMode(siteInner.redundancyMode());

        this.webAppMsiHandler.handleExternalIdentities(siteUpdate);
        return submitSite(siteUpdate).map(siteInner1 -> {
            setInner(siteInner1);
            webAppMsiHandler.clear();
            return (FluentT) WebAppBaseImpl.this;
        });
    }

    @Override
    public Mono<Void> afterPostRunAsync(final boolean isGroupFaulted) {
        if (!isGroupFaulted) {
            isInCreateMode = false;
            initializeKuduClient();
        }
        return Mono.fromCallable(() -> {
            normalizeProperties();
            return null;
        });
    }

    Mono<SiteInner> submitSite(final SiteInner site) {
        site.withSiteConfig(new SiteConfigInner());
        // Construct web app observable
        return createOrUpdateInner(site)
                .map(siteInner -> {
                    site.withSiteConfig(null);
                    return siteInner;
                });
    }

    Mono<SiteInner> submitSite(final SitePatchResourceInner siteUpdate) {
        // Construct web app observable
        return updateInner(siteUpdate)
                .map(siteInner -> {
                    siteInner.withSiteConfig(null);
                    return siteInner;
                });
    }

    @SuppressWarnings("unchecked")
    Mono<FluentT> submitHostNameBindings() {
        final List<Mono<HostNameBinding>> bindingObservables = new ArrayList<>();
        for (HostNameBindingImpl<FluentT, FluentImplT> binding : hostNameBindingsToCreate.values()) {
            bindingObservables.add(Utils.<HostNameBinding>rootResource(binding.createAsync().last()));
        }
        for (String binding : hostNameBindingsToDelete) {
            bindingObservables.add(deleteHostNameBinding(binding).then(Mono.empty()));
        }
        if (bindingObservables.isEmpty()) {
            return Mono.just((FluentT) this);
        } else {
            return Flux.zip(bindingObservables, ignored -> WebAppBaseImpl.this).last()
                    .onErrorResume(throwable -> {
                        if (throwable instanceof HttpResponseException && ((HttpResponseException) throwable).getResponse().getStatusCode() == 400) {
                            return submitSite(inner()).flatMap(ignored -> Flux.zip(bindingObservables, ignored1 -> WebAppBaseImpl.this).last());
                        } else {
                            return Mono.error(throwable);
                        }
                    }).flatMap(WebAppBaseImpl::refreshAsync);
        }
    }

    Mono<Indexable> submitSslBindings(final SiteInner site) {
        List<Mono<AppServiceCertificate>> certs = new ArrayList<>();
        for (final HostNameSslBindingImpl<FluentT, FluentImplT> binding : sslBindingsToCreate.values()) {
            certs.add(binding.newCertificate());
            hostNameSslStateMap.put(binding.inner().name(), binding.inner().withToUpdate(true));
        }
        if (certs.isEmpty()) {
            return Mono.just((Indexable) this);
        } else {
            site.withHostNameSslStates(new ArrayList<>(hostNameSslStateMap.values()));
            return Flux.zip(certs, ignored -> site).last().flatMap(this::createOrUpdateInner).map(siteInner -> {
                setInner(siteInner);
                return WebAppBaseImpl.this;
            });
        }
    }

    Mono<Indexable> submitSiteConfig() {
        if (siteConfig == null) {
            return Mono.just((Indexable) this);
        }
        return createOrUpdateSiteConfig(siteConfig)
                .flatMap(returnedSiteConfig -> {
                    siteConfig = returnedSiteConfig;
                    return Mono.just((Indexable) WebAppBaseImpl.this);
                });
    }

    Mono<Indexable> submitAppSettings() {
        Mono<Indexable> observable = Mono.just((Indexable) this);
        if (!appSettingsToAdd.isEmpty() || !appSettingsToRemove.isEmpty()) {
            observable = listAppSettings()
                    .flatMap(stringDictionaryInner -> {
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
                    }).map(ignored -> WebAppBaseImpl.this);
        }
        return observable;
    }

    Mono<Indexable> submitMetadata() {
        // NOOP
        return Mono.just((Indexable) this);
    }

    Mono<Indexable> submitConnectionStrings() {
        Mono<Indexable> observable = Mono.just((Indexable) this);
        if (!connectionStringsToAdd.isEmpty() || !connectionStringsToRemove.isEmpty()) {
            observable = listConnectionStrings()
                    .flatMap(dictionaryInner -> {
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
                    }).map(ignored -> WebAppBaseImpl.this);
        }
        return observable;
    }

    Mono<Indexable> submitStickiness() {
        Mono<Indexable> observable = Mono.just((Indexable) this);
        if (!appSettingStickiness.isEmpty() || !connectionStringStickiness.isEmpty()) {
            observable = listSlotConfigurations()
                    .flatMap(slotConfigNamesResourceInner -> {
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
                    }).map(ignored -> WebAppBaseImpl.this);
        }
        return observable;
    }

    Mono<Indexable> submitSourceControlToCreate() {
        if (sourceControl == null || sourceControlToDelete) {
            return Mono.just((Indexable) this);
        }
        return sourceControl.registerGithubAccessToken()
                .flatMap(sourceControlInner -> createOrUpdateSourceControl(sourceControl.inner()))
                .delayElement(SdkContext.getDelayDuration(Duration.ofSeconds(30)))
                .map(ignored -> WebAppBaseImpl.this);
    }

    Mono<Indexable> submitSourceControlToDelete() {
        if (!sourceControlToDelete) {
            return Mono.just((Indexable) this);
        }
        return deleteSourceControl().map(ignored -> WebAppBaseImpl.this);
    }

    Mono<Indexable> submitAuthentication() {
        if (!authenticationToUpdate) {
            return Mono.just((Indexable) this);
        }
        return updateAuthentication(authentication.inner()).map(siteAuthSettingsInner -> {
            WebAppBaseImpl.this.authentication = new WebAppAuthenticationImpl<>(siteAuthSettingsInner, WebAppBaseImpl.this);
            return WebAppBaseImpl.this;
        });
    }

    Mono<Indexable> submitLogConfiguration() {
        if (!diagnosticLogsToUpdate) {
            return Mono.just((Indexable) this);
        }
        return updateDiagnosticLogsConfig(diagnosticLogs.inner()).map(siteLogsConfigInner -> {
            WebAppBaseImpl.this.diagnosticLogs = new WebAppDiagnosticLogsImpl<>(siteLogsConfigInner, WebAppBaseImpl.this);
            return WebAppBaseImpl.this;
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
        return withJavaVersion(JavaVersion.fromString("")).withWebContainer(WebContainer.fromString(""));
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withWebContainer(WebContainer webContainer) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        if (webContainer == null) {
            siteConfig.withJavaContainer(null);
            siteConfig.withJavaContainerVersion(null);
        } else if (webContainer.toString().isEmpty()) {
            siteConfig.withJavaContainer("");
            siteConfig.withJavaContainerVersion("");
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
            siteConfig.withDefaultDocuments(new ArrayList<>());
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
    public FluentImplT withHttpsOnly(boolean httpsOnly) {
        inner().withHttpsOnly(httpsOnly);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withHttp20Enabled(boolean http20Enabled) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        siteConfig.withHttp20Enabled(http20Enabled);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withFtpsState(FtpsState ftpsState) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        siteConfig.withFtpsState(ftpsState);
        return (FluentImplT) this;
    }

    @SuppressWarnings("unchecked")
    public FluentImplT withVirtualApplications(List<VirtualApplication> virtualApplications) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        siteConfig.withVirtualApplications(virtualApplications);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withMinTlsVersion(SupportedTlsVersions minTlsVersion) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        siteConfig.withMinTlsVersion(minTlsVersion);
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
        for (String key : settings.keySet()) {
            appSettingStickiness.put(key, true);
        }
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

    void withDiagnosticLogs(WebAppDiagnosticLogsImpl<FluentT, FluentImplT> diagnosticLogs) {
        this.diagnosticLogs = diagnosticLogs;
        diagnosticLogsToUpdate = true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Mono<FluentT> refreshAsync() {
        return super.refreshAsync().flatMap(fluentT -> getConfigInner().map(returnedSiteConfig -> {
            siteConfig = returnedSiteConfig;
            return fluentT;
        }));
    }

    @Override
    protected Mono<SiteInner> getInnerAsync() {
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
        return updateDiagnosticLogsConfiguration()
                .withWebServerLogging()
                .withWebServerLogsStoredOnFileSystem()
                .withWebServerFileSystemQuotaInMB(quotaInMB)
                .withLogRetentionDays(retentionDays)
                .attach();
    }

    @Override
    public FluentImplT withContainerLoggingEnabled() {
        return withContainerLoggingEnabled(35, 0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withContainerLoggingDisabled() {
        return updateDiagnosticLogsConfiguration()
                .withoutWebServerLogging()
                .attach();
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withSystemAssignedManagedServiceIdentity() {
        this.webAppMsiHandler.withLocalManagedServiceIdentity();
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withoutSystemAssignedManagedServiceIdentity() {
        this.webAppMsiHandler.withoutLocalManagedServiceIdentity();
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withUserAssignedManagedServiceIdentity() {
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withSystemAssignedIdentityBasedAccessTo(final String resourceId, final BuiltInRole role) {
        this.webAppMsiHandler.withAccessTo(resourceId, role);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withSystemAssignedIdentityBasedAccessToCurrentResourceGroup(final BuiltInRole role) {
        this.webAppMsiHandler.withAccessToCurrentResourceGroup(role);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withSystemAssignedIdentityBasedAccessTo(final String resourceId, final String roleDefinitionId) {
        this.webAppMsiHandler.withAccessTo(resourceId, roleDefinitionId);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withSystemAssignedIdentityBasedAccessToCurrentResourceGroup(final String roleDefinitionId) {
        this.webAppMsiHandler.withAccessToCurrentResourceGroup(roleDefinitionId);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withNewUserAssignedManagedServiceIdentity(Creatable<Identity> creatableIdentity) {
        this.webAppMsiHandler.withNewExternalManagedServiceIdentity(creatableIdentity);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withExistingUserAssignedManagedServiceIdentity(Identity identity) {
        this.webAppMsiHandler.withExistingExternalManagedServiceIdentity(identity);
        return (FluentImplT) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentImplT withoutUserAssignedManagedServiceIdentity(String identityId) {
        this.webAppMsiHandler.withoutExternalManagedServiceIdentity(identityId);
        return (FluentImplT) this;
    }

    @Override
    public WebAppDiagnosticLogsImpl<FluentT, FluentImplT> defineDiagnosticLogsConfiguration() {
        if (diagnosticLogs == null) {
            return new WebAppDiagnosticLogsImpl<>(new SiteLogsConfigInner(), this);
        } else {
            return diagnosticLogs;
        }
    }

    @Override
    public WebAppDiagnosticLogsImpl<FluentT, FluentImplT> updateDiagnosticLogsConfiguration() {
        return defineDiagnosticLogsConfiguration();
    }

    private static class PipedInputStreamWithCallback extends PipedInputStream {
        private Runnable callback;

        private void addCallback(Runnable action) {
            this.callback = action;
        }

        @Override
        public void close() throws IOException {
            callback.run();
            super.close();
        }
    }
}
