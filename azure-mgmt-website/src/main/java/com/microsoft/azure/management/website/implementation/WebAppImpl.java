/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.website.implementation;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.ResourceNamer;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import com.microsoft.azure.management.website.AppServicePlan;
import com.microsoft.azure.management.website.AppServicePricingTier;
import com.microsoft.azure.management.website.AzureResourceType;
import com.microsoft.azure.management.website.Certificate;
import com.microsoft.azure.management.website.CloningInfo;
import com.microsoft.azure.management.website.CustomHostNameDnsRecordType;
import com.microsoft.azure.management.website.Domain;
import com.microsoft.azure.management.website.HostNameBinding;
import com.microsoft.azure.management.website.HostNameSslState;
import com.microsoft.azure.management.website.HostNameType;
import com.microsoft.azure.management.website.JavaVersion;
import com.microsoft.azure.management.website.ManagedPipelineMode;
import com.microsoft.azure.management.website.NetFrameworkVersion;
import com.microsoft.azure.management.website.PhpVersion;
import com.microsoft.azure.management.website.PlatformArchitecture;
import com.microsoft.azure.management.website.PythonVersion;
import com.microsoft.azure.management.website.RemoteVisualStudioVersion;
import com.microsoft.azure.management.website.SiteAvailabilityState;
import com.microsoft.azure.management.website.SslState;
import com.microsoft.azure.management.website.UsageState;
import com.microsoft.azure.management.website.WebApp;
import com.microsoft.azure.management.website.WebContainer;
import org.joda.time.DateTime;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.FuncN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The implementation for {@link WebApp}.
 */
class WebAppImpl
        extends GroupableResourceImpl<
            WebApp,
            SiteInner,
            WebAppImpl,
            AppServiceManager>
        implements
            WebApp,
            WebApp.Definition,
            WebApp.Update {

    private final WebAppsInner client;

    private Set<String> hostNamesSet;
    private Set<String> enabledHostNamesSet;
    private Set<String> trafficManagerHostNamesSet;
    private Set<String> outboundIpAddressesSet;
    private Map<String, HostNameSslState> hostNameSslStateMap;
    private Map<String, HostNameBindingImpl> hostNameBindingsToCreate;

    WebAppImpl(String key, SiteInner innerObject, SiteConfigInner configObject, final WebAppsInner client, AppServiceManager manager) {
        super(key, innerObject, manager);
        this.client = client;
        this.inner().withSiteConfig(configObject);
        normalizeProperties();
    }

    private WebAppImpl normalizeProperties() {
        this.hostNameBindingsToCreate = new HashMap<>();
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
            this.outboundIpAddressesSet = Sets.newHashSet(inner().outboundIpAddresses().split(",[ ]*"));
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
        return this;
    }

    @Override
    public String state() {
        return inner().state();
    }

    @Override
    public Set<String> hostNames() {
        return hostNamesSet;
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
        return hostNameSslStateMap;
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
        return trafficManagerHostNamesSet;
    }

    @Override
    public boolean isPremiumApp() {
        return inner().premiumAppDeployed();
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
    public String microService() {
        return inner().microService();
    }

    @Override
    public String gatewaySiteName() {
        return inner().gatewaySiteName();
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
        return false;
    }

    @Override
    public Set<String> outboundIpAddresses() {
        return outboundIpAddressesSet;
    }

    @Override
    public int containerSize() {
        return inner().containerSize();
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
        return inner().defaultHostName();
    }

    @Override
    public Map<String, HostNameBinding> getHostNameBindings() {
        List<HostNameBindingInner> collectionInner = client.listHostNameBindings(resourceGroupName(), name());
        List<HostNameBinding> hostNameBindings = new ArrayList<>();
        for (HostNameBindingInner inner : collectionInner) {
            hostNameBindings.add(new HostNameBindingImpl(inner.name(), inner, this, client));
        }
        return Maps.uniqueIndex(hostNameBindings, new Function<HostNameBinding, String>() {
            @Override
            public String apply(HostNameBinding input) {
                return input.name().replace(name() + "/", "");
            }
        });
    }

    @Override
    public List<String> defaultDocuments() {
        return inner().siteConfig().defaultDocuments();
    }

    @Override
    public NetFrameworkVersion netFrameworkVersion() {
        return new NetFrameworkVersion(inner().siteConfig().netFrameworkVersion());
    }

    @Override
    public PhpVersion phpVersion() {
        return new PhpVersion(inner().siteConfig().phpVersion());
    }

    @Override
    public PythonVersion pythonVersion() {
        return new PythonVersion(inner().siteConfig().pythonVersion());
    }

    @Override
    public String nodeVersion() {
        return inner().siteConfig().nodeVersion();
    }

    @Override
    public boolean remoteDebuggingEnabled() {
        return Utils.toPrimitiveBoolean(inner().siteConfig().remoteDebuggingEnabled());
    }

    @Override
    public RemoteVisualStudioVersion remoteDebuggingVersion() {
        return new RemoteVisualStudioVersion(inner().siteConfig().remoteDebuggingVersion());
    }

    @Override
    public boolean webSocketsEnabled() {
        return Utils.toPrimitiveBoolean(inner().siteConfig().webSocketsEnabled());
    }

    @Override
    public boolean alwaysOn() {
        return Utils.toPrimitiveBoolean(inner().siteConfig().alwaysOn());
    }

    @Override
    public JavaVersion javaVersion() {
        return new JavaVersion(inner().siteConfig().javaVersion());
    }

    @Override
    public String javaContainer() {
        return inner().siteConfig().javaContainer();
    }

    @Override
    public String javaContainerVersion() {
        return inner().siteConfig().javaContainerVersion();
    }

    @Override
    public ManagedPipelineMode managedPipelineMode() {
        return inner().siteConfig().managedPipelineMode();
    }

    @Override
    public String autoSwapSlotName() {
        return inner().siteConfig().autoSwapSlotName();
    }

    @Override
    public WebAppImpl refresh() {
        this.setInner(client.get(resourceGroupName(), name()));
        return this;
    }

    @Override
    public WebAppImpl withNewFreeAppServicePlan() {
        String appServicePlanName = ResourceNamer.randomResourceName(name(), 10);
        AppServicePlan.DefinitionStages.WithCreate creatable = myManager.appServicePlans().define(appServicePlanName)
                .withRegion(region())
                .withNewResourceGroup(resourceGroupName())
                .withPricingTier(AppServicePricingTier.FREE_F1);
        addCreatableDependency(creatable);
        inner().withServerFarmId(appServicePlanName);
        return this;
    }

    @Override
    public WebAppImpl withNewAppServicePlan(String name, AppServicePricingTier pricingTier) {
        AppServicePlan.DefinitionStages.WithCreate creatable = myManager.appServicePlans().define(name)
                .withRegion(region())
                .withNewResourceGroup(resourceGroupName())
                .withPricingTier(pricingTier);
        addCreatableDependency(creatable);
        inner().withServerFarmId(name);
        return this;
    }

    @Override
    public WebAppImpl withExistingAppServicePlan(String appServicePlanName) {
        inner().withServerFarmId(appServicePlanName);
        return this;
    }

//    private boolean isUpdateSsl(String hostName) {
//        boolean update = false;
//        if (hostNameSslStates() != null) {
//            for (HostNameSslState hostNameSslState : hostNameSslStates()) {
//                if (hostName.equals(hostNameSslState.name())) {
//                    update = true;
//                }
//            }
//        }
//        return update;
//    }
//
//    @Override
//    public WebAppImpl disableSsl(String hostName) {
//        if (hostName == null) {
//            throw new IllegalArgumentException("Null host name");
//        }
//        hostNameSslStateMap.put(hostName, new HostNameSslState()
//                .withName(hostName)
//                .withSslState(SslState.DISABLED)
//                .withToUpdate(isUpdateSsl(hostName)));
//        return this;
//    }
//
//    @Override
//    public WebAppImpl enableSniSsl(String hostName, String thumbprint) {
//        if (hostName == null) {
//            throw new IllegalArgumentException("Null host name");
//        }
//        hostNameSslStateMap.put(hostName, new HostNameSslState()
//                .withName(hostName)
//                .withSslState(SslState.SNI_ENABLED)
//                .withThumbprint(thumbprint)
//                .withToUpdate(isUpdateSsl(hostName)));
//        return this;
//    }
//
//    @Override
//    public WebAppImpl enableIpBasedSsl(String hostName, String thumbprint, String virtualIp) {
//        if (hostName == null) {
//            throw new IllegalArgumentException("Null host name");
//        }
//        hostNameSslStateMap.put(hostName, new HostNameSslState()
//                .withName(hostName)
//                .withSslState(SslState.SNI_ENABLED)
//                .withThumbprint(thumbprint)
//                .withVirtualIP(virtualIp)
//                .withToUpdate(isUpdateSsl(hostName)));
//        return this;
//    }

    WebAppImpl withNewHostNameSslBinding(final HostNameSslBindingImpl hostNameSslBinding) {
        this.hostNameSslStateMap.put(hostNameSslBinding.name(), hostNameSslBinding.inner().withToUpdate(true));
        if (hostNameSslBinding.newCertificate() != null) {
            final CertificateImpl certificateImpl = (CertificateImpl) hostNameSslBinding.newCertificate();
            // Convert creatable to apply thumbprint after creation
            CreatableUpdatableImpl<Certificate, CertificateInner, CertificateImpl> postCert = new CreatableUpdatableImpl<Certificate, CertificateInner, CertificateImpl>(
                    certificateImpl.name(),
                    certificateImpl.inner()) {
                @Override
                public Observable<Certificate> createResourceAsync() {
                    return hostNameSslBinding.newCertificate().createAsync().map(new Func1<Certificate, Certificate>() {
                        @Override
                        public Certificate call(Certificate certificate) {
                            hostNameSslBinding.withCertificateThumbprint(certificate.thumbprint());
                            return certificate;
                        }
                    });
                }

                @Override
                public boolean isInCreateMode() {
                    return certificateImpl.isInCreateMode();
                }

                @Override
                public Certificate refresh() {
                    return certificateImpl.refresh();
                }
            };
            ((CertificateImpl) hostNameSslBinding.newCertificate()).creatorUpdatorTaskGroup().merge(postCert.creatorUpdatorTaskGroup());
            addCreatableDependency(postCert);
        }
        if (hostNameSslBinding.newCertificateOrder() != null) {
            addCreatableDependency(hostNameSslBinding.newCertificateOrder());
        }
        return this;
    }

    @Override
    public WebAppImpl withManagedHostNameBindings(Domain domain, String... hostnames) {
        for(String hostname : hostnames) {
            if (hostname.equals("@") || hostname.equalsIgnoreCase(domain.name())) {
                defineNewHostNameBinding(hostname)
                        .withAzureManagedDomain(domain)
                        .withDnsRecordType(CustomHostNameDnsRecordType.A)
                        .attach();
            } else {
                defineNewHostNameBinding(hostname)
                        .withAzureManagedDomain(domain)
                        .withDnsRecordType(CustomHostNameDnsRecordType.CNAME)
                        .attach();
            }
        }
        return this;
    }

    @Override
    public HostNameBindingImpl defineNewHostNameBinding(String hostname) {
        HostNameBindingInner inner = new HostNameBindingInner();
        inner.withSiteName(name());
        inner.withLocation(regionName());
        inner.withAzureResourceType(AzureResourceType.WEBSITE);
        inner.withAzureResourceName(name());
        inner.withHostNameType(HostNameType.VERIFIED);
        return new HostNameBindingImpl(hostname, inner, this, client);
    }

    @Override
    public WebAppImpl withVerifiedHostNameBinding(String domain, String... hostnames) {
        for(String hostname : hostnames) {
            defineNewHostNameBinding(hostname)
                    .withThirdPartyDomain(domain)
                    .withDnsRecordType(CustomHostNameDnsRecordType.CNAME)
                    .attach();
        }
        return this;
    }

    WebAppImpl withHostNameBinding(final HostNameBindingImpl hostNameBinding) {
        this.hostNameBindingsToCreate.put(
                hostNameBinding.name(),
                hostNameBinding);
        return this;
    }

    @Override
    public Observable<WebApp> createResourceAsync() {
        if (hostNameSslStateMap.size() > 0) {
            inner().withHostNameSslStates(new ArrayList<>(hostNameSslStateMap.values()));
        }
        // Construct web app observable
        inner().siteConfig().withLocation(inner().location());
        return client.createOrUpdateAsync(resourceGroupName(), name(), inner())
                // Submit hostname bindings
                .flatMap(new Func1<SiteInner, Observable<SiteInner>>() {
                    @Override
                    public Observable<SiteInner> call(final SiteInner site) {
                        List<Observable<HostNameBinding>> bindingObservables = new ArrayList<>();
                        for (HostNameBindingImpl binding: hostNameBindingsToCreate.values()) {
                            bindingObservables.add(binding.createAsync());
                        }
                        hostNameBindingsToCreate.clear();
                        if (bindingObservables.isEmpty()) {
                            return Observable.just(site);
                        } else {
                            return Observable.zip(bindingObservables, new FuncN<SiteInner>() {
                                @Override
                                public SiteInner call(Object... args) {
                                    return site;
                                }
                            });
                        }
                    }
                })
                .flatMap(new Func1<SiteInner, Observable<SiteInner>>() {
                    @Override
                    public Observable<SiteInner> call(SiteInner site) {
                        return client.getAsync(resourceGroupName(), site.name());
                    }
                })
                .flatMap(new Func1<SiteInner, Observable<SiteInner>>() {
                    @Override
                    public Observable<SiteInner> call(final SiteInner siteInner) {
                        inner().siteConfig().withLocation(inner().location());
                        return client.createOrUpdateConfigurationAsync(resourceGroupName(), name(), inner().siteConfig())
                                .flatMap(new Func1<SiteConfigInner, Observable<SiteInner>>() {
                                    @Override
                                    public Observable<SiteInner> call(SiteConfigInner siteConfigInner) {
                                        siteInner.withSiteConfig(siteConfigInner);
                                        return Observable.just(siteInner);
                                    }
                                });
                    }
                })
                .map(new Func1<SiteInner, WebApp>() {
                    @Override
                    public WebApp call(SiteInner siteInner) {
                        setInner(siteInner);
                        return normalizeProperties();
                    }
                });
    }

    @Override
    public WebAppImpl withAppDisabledOnCreation() {
        inner().withEnabled(false);
        return this;
    }

    @Override
    public WebAppImpl withScmSiteAlsoStopped(boolean scmSiteAlsoStopped) {
        inner().withScmSiteAlsoStopped(scmSiteAlsoStopped);
        return this;
    }

    @Override
    public WebAppImpl withClientAffinityEnabled(boolean enabled) {
        inner().withClientAffinityEnabled(enabled);
        return this;
    }

    @Override
    public WebAppImpl withClientCertEnabled(boolean enabled) {
        inner().withClientCertEnabled(enabled);
        return this;
    }

    @Override
    public HostNameSslBindingImpl defineNewSSLBindingForHostName(String hostname) {
        return new HostNameSslBindingImpl(new HostNameSslState().withName(hostname), this, myManager);
    }

    @Override
    public WebAppImpl withNetFrameworkVersion(NetFrameworkVersion version) {
        inner().siteConfig().withNetFrameworkVersion(version.toString());
        return this;
    }

    @Override
    public WebAppImpl withPhpVersion(PhpVersion version) {
        inner().siteConfig().withPhpVersion(version.toString());
        return this;
    }

    @Override
    public WebAppImpl withJavaVersion(JavaVersion version) {
        inner().siteConfig().withJavaVersion(version.toString());
        return this;
    }

    @Override
    public WebAppImpl withWebContainer(WebContainer webContainer) {
        String[] containerInfo = webContainer.toString().split(" ");
        inner().siteConfig().withJavaContainer(containerInfo[0]);
        inner().siteConfig().withJavaContainerVersion(containerInfo[1]);
        return this;
    }

    @Override
    public WebAppImpl withPythonVersion(PythonVersion version) {
        inner().siteConfig().withPythonVersion(version.toString());
        return this;
    }

    @Override
    public WebAppImpl withPlatformArchitecture(PlatformArchitecture platform) {
        inner().siteConfig().withUse32BitWorkerProcess(platform.equals(PlatformArchitecture.X86));
        return this;
    }

    @Override
    public WebAppImpl withWebSocketsEnabled(boolean enabled) {
        inner().siteConfig().withWebSocketsEnabled(enabled);
        return this;
    }

    @Override
    public WebAppImpl withWebAppAlwaysOn(boolean alwaysOn) {
        inner().siteConfig().withAlwaysOn(alwaysOn);
        return this;
    }

    @Override
    public WebAppImpl withManagedPipelineMode(ManagedPipelineMode managedPipelineMode) {
        inner().siteConfig().withManagedPipelineMode(managedPipelineMode);
        return this;
    }

    @Override
    public WebAppImpl withAutoSwapSlotName(String slotName) {
        inner().siteConfig().withAutoSwapSlotName(slotName);
        return this;
    }

    @Override
    public WebAppImpl withRemoteDebuggingEnabled(RemoteVisualStudioVersion remoteVisualStudioVersion) {
        inner().siteConfig().withRemoteDebuggingEnabled(true);
        inner().siteConfig().withRemoteDebuggingVersion(remoteVisualStudioVersion.toString());
        return this;
    }

    @Override
    public WebAppImpl withReoteDebuggingDisabled() {
        inner().siteConfig().withRemoteDebuggingEnabled(false);
        return this;
    }

    @Override
    public WebAppImpl withDefaultDocument(String document) {
        if (inner().siteConfig().defaultDocuments() == null) {
            inner().siteConfig().withDefaultDocuments(new ArrayList<String>());
        }
        inner().siteConfig().defaultDocuments().add(document);
        return this;
    }

    @Override
    public WebAppImpl withDefaultDocuments(List<String> documents) {
        if (inner().siteConfig().defaultDocuments() == null) {
            inner().siteConfig().withDefaultDocuments(new ArrayList<String>());
        }
        inner().siteConfig().defaultDocuments().addAll(documents);
        return this;
    }

    @Override
    public WebAppImpl withoutDefaultDocument(String document) {
        if (inner().siteConfig().defaultDocuments() != null) {
            inner().siteConfig().defaultDocuments().remove(document);
        }
        return this;
    }
}