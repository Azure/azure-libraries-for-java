/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network.implementation;

import com.microsoft.azure.management.network.ApplicationGateway;
import com.microsoft.azure.management.network.ApplicationGatewayBackend;
import com.microsoft.azure.management.network.ApplicationGatewayBackendHttpConfiguration;
import com.microsoft.azure.management.network.ApplicationGatewayFrontend;
import com.microsoft.azure.management.network.ApplicationGatewayFrontendListener;
import com.microsoft.azure.management.network.ApplicationGatewayIpConfiguration;
import com.microsoft.azure.management.network.ApplicationGatewayOperationalState;
import com.microsoft.azure.management.network.ApplicationGatewayRequestRoutingRule;
import com.microsoft.azure.management.network.ApplicationGatewaySku;
import com.microsoft.azure.management.network.ApplicationGatewaySkuName;
import com.microsoft.azure.management.network.ApplicationGatewaySslCertificate;
import com.microsoft.azure.management.network.ApplicationGatewaySslPolicy;
import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.network.PublicIpAddress;
import com.microsoft.azure.management.network.Subnet;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.utils.ResourceNamer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Set;

import com.microsoft.azure.SubResource;
import com.microsoft.azure.management.apigeneration.LangDefinition;

import rx.Observable;

/**
 * Implementation of the LoadBalancer interface.
 */
@LangDefinition
class ApplicationGatewayImpl
    extends NetworkGroupableParentResourceImpl<
        ApplicationGateway,
        ApplicationGatewayInner,
        ApplicationGatewayImpl,
        ApplicationGatewaysInner>
    implements
        ApplicationGateway,
        ApplicationGateway.Definition,
        ApplicationGateway.Update {

    private Map<String, ApplicationGatewayIpConfiguration> ipConfigs;
    private Map<String, ApplicationGatewayFrontend> frontends;
    private Map<String, ApplicationGatewayBackend> backends;
    private Map<String, ApplicationGatewayBackendHttpConfiguration> backendHttpConfigs;
    private Map<String, ApplicationGatewayFrontendListener> httpListeners;
    private Map<String, ApplicationGatewayRequestRoutingRule> rules;
    private Map<String, ApplicationGatewaySslCertificate> sslCerts;

    ApplicationGatewayImpl(String name,
            final ApplicationGatewayInner innerModel,
            final ApplicationGatewaysInner innerCollection,
            final NetworkManager networkManager) {
        super(name, innerModel, innerCollection, networkManager);
    }

    // Verbs

    @Override
    public ApplicationGatewayImpl refresh() {
        ApplicationGatewayInner inner = this.innerCollection.get(this.resourceGroupName(), this.name());
        this.setInner(inner);
        initializeChildrenFromInner();
        return this;
    }

    // Helpers

    @Override
    protected void initializeChildrenFromInner() {
        initializeConfigsFromInner();
        initializeFrontendsFromInner();
        initializeBackendsFromInner();
        initializeBackendHttpConfigsFromInner();
        initializeHttpListenersFromInner();
        initializeRequestRoutingRulesFromInner();
        initializeSslCertificatesFromInner();
    }

    private void initializeSslCertificatesFromInner() {
        this.sslCerts = new TreeMap<>();
        List<ApplicationGatewaySslCertificateInner> inners = this.inner().sslCertificates();
        if (inners != null) {
            for (ApplicationGatewaySslCertificateInner inner : inners) {
                ApplicationGatewaySslCertificateImpl cert = new ApplicationGatewaySslCertificateImpl(inner, this);
                this.sslCerts.put(inner.name(), cert);
            }
        }
    }

    private void initializeFrontendsFromInner() {
        this.frontends = new TreeMap<>();
        List<ApplicationGatewayFrontendIPConfigurationInner> inners = this.inner().frontendIPConfigurations();
        if (inners != null) {
            for (ApplicationGatewayFrontendIPConfigurationInner inner : inners) {
                ApplicationGatewayFrontendImpl frontend = new ApplicationGatewayFrontendImpl(inner, this);
                this.frontends.put(inner.name(), frontend);
            }
        }
    }

    private void initializeBackendsFromInner() {
        this.backends = new TreeMap<>();
        List<ApplicationGatewayBackendAddressPoolInner> inners = this.inner().backendAddressPools();
        if (inners != null) {
            for (ApplicationGatewayBackendAddressPoolInner inner : inners) {
                ApplicationGatewayBackendImpl backend = new ApplicationGatewayBackendImpl(inner, this);
                this.backends.put(inner.name(), backend);
            }
        }
    }

    private void initializeBackendHttpConfigsFromInner() {
        this.backendHttpConfigs = new TreeMap<>();
        List<ApplicationGatewayBackendHttpSettingsInner> inners = this.inner().backendHttpSettingsCollection();
        if (inners != null) {
            for (ApplicationGatewayBackendHttpSettingsInner inner : inners) {
                ApplicationGatewayBackendHttpConfigurationImpl httpConfig = new ApplicationGatewayBackendHttpConfigurationImpl(inner, this);
                this.backendHttpConfigs.put(inner.name(), httpConfig);
            }
        }
    }

    private void initializeHttpListenersFromInner() {
        this.httpListeners = new TreeMap<>();
        List<ApplicationGatewayHttpListenerInner> inners = this.inner().httpListeners();
        if (inners != null) {
            for (ApplicationGatewayHttpListenerInner inner : inners) {
                ApplicationGatewayFrontendListenerImpl httpListener = new ApplicationGatewayFrontendListenerImpl(inner, this);
                this.httpListeners.put(inner.name(), httpListener);
            }
        }
    }

    private void initializeRequestRoutingRulesFromInner() {
        this.rules = new TreeMap<>();
        List<ApplicationGatewayRequestRoutingRuleInner> inners = this.inner().requestRoutingRules();
        if (inners != null) {
            for (ApplicationGatewayRequestRoutingRuleInner inner : inners) {
                ApplicationGatewayRequestRoutingRuleImpl rule = new ApplicationGatewayRequestRoutingRuleImpl(inner, this);
                this.rules.put(inner.name(), rule);
            }
        }
    }

    private void initializeConfigsFromInner() {
        this.ipConfigs = new TreeMap<>();
        List<ApplicationGatewayIPConfigurationInner> inners = this.inner().gatewayIPConfigurations();
        if (inners != null) {
            for (ApplicationGatewayIPConfigurationInner inner : inners) {
                ApplicationGatewayIpConfigurationImpl config = new ApplicationGatewayIpConfigurationImpl(inner, this);
                this.ipConfigs.put(inner.name(), config);
            }
        }
    }

    @Override
    protected void beforeCreating()  {
        // Account for the newly created public IPs
        for (Entry<String, String> pipFrontendAssociation : this.creatablePIPKeys.entrySet()) {
            PublicIpAddress pip = (PublicIpAddress) this.createdResource(pipFrontendAssociation.getKey());
            if (pip != null) {
                withExistingPublicIpAddress(pip.id(), pipFrontendAssociation.getValue());
            }
        }
        this.creatablePIPKeys.clear();

        // Reset and update configs
        this.inner().withGatewayIPConfigurations(innersFromWrappers(this.ipConfigs.values()));

        // Clean up frontends that are neither public nor private
        Set<String> frontendNamesToRemove = new HashSet<String>();
        for (ApplicationGatewayFrontend frontend : this.frontends.values()) {
            if (!frontend.isPublic() && !frontend.isPrivate()) {
                frontendNamesToRemove.add(frontend.name());
            }
        }

        for (String frontendName : frontendNamesToRemove) {
            this.frontends.remove(frontendName);
        }

        // Reset and update frontends
        this.inner().withFrontendIPConfigurations(innersFromWrappers(this.frontends.values()));

        // Reset and update backends
        this.inner().withBackendAddressPools(innersFromWrappers(this.backends.values()));

        // Reset and update backend HTTP settings configs
        this.inner().withBackendHttpSettingsCollection(innersFromWrappers(this.backendHttpConfigs.values()));

        // Reset and update HTTP listeners
        this.inner().withHttpListeners(innersFromWrappers(this.httpListeners.values()));
        for (ApplicationGatewayFrontendListener listener : this.httpListeners.values()) {
            SubResource ref;

            // Clear deleted frontend references
            ref = listener.inner().frontendIPConfiguration();
            if (ref != null
                    && !this.frontends().containsKey(ResourceUtils.nameFromResourceId(ref.id()))) {
                listener.inner().withFrontendIPConfiguration(null);
            }

            // Clear deleted frontend port references
            ref = listener.inner().frontendPort();
            if (ref != null
                    && !this.frontendPorts().containsKey(ResourceUtils.nameFromResourceId(ref.id()))) {
                listener.inner().withFrontendPort(null);
            }

            // Clear deleted SSL certificate references
            ref = listener.inner().sslCertificate();
            if (ref != null
                    && !this.sslCertificates().containsKey(ResourceUtils.nameFromResourceId(ref.id()))) {
                listener.inner().withSslCertificate(null);
            }
        }

        // Reset and update request routing rules
        this.inner().withRequestRoutingRules(innersFromWrappers(this.rules.values()));
        for (ApplicationGatewayRequestRoutingRule rule : this.rules.values()) {
            SubResource ref;

            // Clear deleted backends
            ref = rule.inner().backendAddressPool();
            if (ref != null
                    && !this.backends().containsKey(ResourceUtils.nameFromResourceId(ref.id()))) {
                rule.inner().withBackendAddressPool(null);
            }

            // Clear deleted backend HTTP configs
            ref = rule.inner().backendHttpSettings();
            if (ref != null
                    && !this.backendHttpConfigurations().containsKey(ResourceUtils.nameFromResourceId(ref.id()))) {
                rule.inner().withBackendHttpSettings(null);
            }

            // Clear deleted frontend HTTP listeners
            ref = rule.inner().httpListener();
            if (ref != null
                    && !this.frontendListeners().containsKey(ResourceUtils.nameFromResourceId(ref.id()))) {
                rule.inner().withHttpListener(null);
            }
        }

        // Reset and update SSL certs
        this.inner().withSslCertificates(innersFromWrappers(this.sslCerts.values()));
    }

    @Override
    protected void afterCreating() {
    }

    @Override
    protected Observable<ApplicationGatewayInner> createInner() {
        return this.innerCollection.createOrUpdateAsync(this.resourceGroupName(), this.name(), this.inner());
    }

    NetworkManager manager() {
        return this.myManager;
    }

    /**
     * Determines whether the app gateway child that can be found using a name or a port number can be created,
     * or it already exists, or there is a clash.
     * @param byName object found by name
     * @param byPort object found by port
     * @param name the desired name of the object
     * @return true if already found, false if ok to create, null if conflict
     */
    <T> Boolean needToCreate(T byName, T byPort, String name) {
        if (byName != null && byPort != null) {
            // If objects with this name and/or port already exist...
            if (byName == byPort) {
                // ...and it is the same object, then do nothing
                return false;
            } else {
                // ...but if they are inconsistent, then fail fast
                return null;
            }
        } else if (byPort != null) {
            // If no object with the requested name, but the port number is found...
            if (name == null) {
                // ...and no name is requested, then do nothing, because the object already exists
                return false;
            } else {
                // ...but if a clashing name is requested, then fail fast
                return null;
            }
        } else {
            // Ok to create the object
            return true;
        }
    }

    String futureResourceId() {
        return new StringBuilder()
                .append(super.resourceIdBase())
                .append("/providers/Microsoft.Network/applicationGateways/")
                .append(this.name()).toString();
    }

     // Withers (fluent)

    ApplicationGatewayImpl withFrontend(ApplicationGatewayFrontendImpl frontend) {
        if (frontend == null) {
            return null;
        } else {
            this.frontends.put(frontend.name(), frontend);
            return this;
        }
    }

    ApplicationGatewayImpl withBackend(ApplicationGatewayBackendImpl backend) {
        if (backend == null) {
            return null;
        } else {
            this.backends.put(backend.name(), backend);
            return this;
        }
    }

    ApplicationGatewayImpl withSslCertificate(ApplicationGatewaySslCertificateImpl cert) {
        if (cert == null) {
            return null;
        } else {
            this.sslCerts.put(cert.name(), cert);
            return this;
        }
    }

    ApplicationGatewayImpl withHttpListener(ApplicationGatewayFrontendListenerImpl httpListener) {
        if (httpListener == null) {
            return null;
        } else {
            this.httpListeners.put(httpListener.name(), httpListener);
            return this;
        }
    }

    ApplicationGatewayImpl withRequestRoutingRule(ApplicationGatewayRequestRoutingRuleImpl rule) {
        if (rule == null) {
            return null;
        } else {
            this.rules.put(rule.name(), rule);
            return this;
        }
    }

    ApplicationGatewayImpl withBackendHttpConfiguration(ApplicationGatewayBackendHttpConfigurationImpl httpConfig) {
        if (httpConfig == null) {
            return null;
        } else {
            this.backendHttpConfigs.put(httpConfig.name(), httpConfig);
            return this;
        }
    }

    @Override
    public ApplicationGatewayImpl withSku(ApplicationGatewaySkuName skuName, int capacity) {
        ApplicationGatewaySku sku = new ApplicationGatewaySku()
                .withName(skuName)
                .withCapacity(capacity);
        this.inner().withSku(sku);
        return this;
    }

    @Override
    public ApplicationGatewayImpl withContainingSubnet(Subnet subnet) {
        this.defineIpConfiguration(DEFAULT)
            .withContainingSubnet(subnet)
            .attach();
        return this;
    }

    @Override
    public ApplicationGatewayImpl withContainingSubnet(Network network, String subnetName) {
        this.defineIpConfiguration(DEFAULT)
            .withContainingSubnet(network, subnetName)
            .attach();
        return this;
    }

    @Override
    public ApplicationGatewayImpl withContainingSubnet(String networkResourceId, String subnetName) {
        this.defineIpConfiguration(DEFAULT)
            .withContainingSubnet(networkResourceId, subnetName)
            .attach();
        return this;
    }

    ApplicationGatewayImpl withConfig(ApplicationGatewayIpConfigurationImpl config) {
        if (config == null) {
            return null;
        } else {
            this.ipConfigs.put(config.name(), config);
            return this;
        }
    }

    @Override
    public ApplicationGatewaySslCertificateImpl defineSslCertificate(String name) {
        ApplicationGatewaySslCertificate cert = this.sslCerts.get(name);
        if (cert == null) {
            ApplicationGatewaySslCertificateInner inner = new ApplicationGatewaySslCertificateInner()
                    .withName(name);
            return new ApplicationGatewaySslCertificateImpl(inner, this);
        } else {
            return (ApplicationGatewaySslCertificateImpl) cert;
        }
    }

    @Override
    public ApplicationGatewayIpConfigurationImpl defineIpConfiguration(String name) {
        ApplicationGatewayIpConfiguration config = this.ipConfigs.get(name);
        if (config == null) {
            ApplicationGatewayIPConfigurationInner inner = new ApplicationGatewayIPConfigurationInner()
                    .withName(name);
            return new ApplicationGatewayIpConfigurationImpl(inner, this);
        } else {
            return (ApplicationGatewayIpConfigurationImpl) config;
        }
    }

    /* TODO Since multiple frontends are not yet supported by Azure, this shoudl be revisited when they are.
     * For now, the assumption is there is only one frontend.
     */
    //TODO @Override
    public ApplicationGatewayFrontendImpl definePrivateFrontend(String name) {
        return defineFrontend(name);
    }

    @Override
    public ApplicationGatewayFrontendImpl definePublicFrontend(String name) {
        return defineFrontend(name);
    }

    private ApplicationGatewayFrontendImpl defineFrontend(String name) {
        ApplicationGatewayFrontend frontend = this.frontends.get(name);
        if (frontend == null) {
            ApplicationGatewayFrontendIPConfigurationInner inner = new ApplicationGatewayFrontendIPConfigurationInner()
                    .withName(name);
            return new ApplicationGatewayFrontendImpl(inner, this);
        } else {
            return (ApplicationGatewayFrontendImpl) frontend;
        }
    }

    @Override
    public ApplicationGatewayBackendImpl defineBackend(String name) {
        ApplicationGatewayBackend backend = this.backends.get(name);
        if (backend == null) {
            ApplicationGatewayBackendAddressPoolInner inner = new ApplicationGatewayBackendAddressPoolInner()
                    .withName(name);
            return new ApplicationGatewayBackendImpl(inner, this);
        } else {
            return (ApplicationGatewayBackendImpl) backend;
        }
    }

    @Override
    public ApplicationGatewayFrontendListenerImpl defineFrontendListener(String name) {
        ApplicationGatewayFrontendListener httpListener = this.httpListeners.get(name);
        if (httpListener == null) {
            ApplicationGatewayHttpListenerInner inner = new ApplicationGatewayHttpListenerInner()
                    .withName(name);
            return new ApplicationGatewayFrontendListenerImpl(inner, this);
        } else {
            return (ApplicationGatewayFrontendListenerImpl) httpListener;
        }
    }

    @Override
    public ApplicationGatewayRequestRoutingRuleImpl defineRequestRoutingRule(String name) {
        ApplicationGatewayRequestRoutingRule rule = this.rules.get(name);
        if (rule == null) {
            ApplicationGatewayRequestRoutingRuleInner inner = new ApplicationGatewayRequestRoutingRuleInner()
                    .withName(name);
            return new ApplicationGatewayRequestRoutingRuleImpl(inner, this);
        } else {
            return (ApplicationGatewayRequestRoutingRuleImpl) rule;
        }
    }

    @Override
    public ApplicationGatewayBackendHttpConfigurationImpl defineBackendHttpConfiguration(String name) {
        ApplicationGatewayBackendHttpConfiguration httpConfig = this.backendHttpConfigs.get(name);
        if (httpConfig == null) {
            ApplicationGatewayBackendHttpSettingsInner inner = new ApplicationGatewayBackendHttpSettingsInner()
                    .withName(name)
                    .withPort(80);
            return new ApplicationGatewayBackendHttpConfigurationImpl(inner, this);
        } else {
            return (ApplicationGatewayBackendHttpConfigurationImpl) httpConfig;
        }
    }

    @Override
    protected ApplicationGatewayImpl withExistingPublicIpAddress(String resourceId, String frontendName) {
        if (frontendName == null) {
            frontendName = DEFAULT;
        }

        return this.definePublicFrontend(frontendName)
                .withExistingPublicIpAddress(resourceId)
                .attach();
    }

    @Override
    public ApplicationGatewayImpl withoutPrivateFrontend() {
        // Ensure no frontend is private
        for (ApplicationGatewayFrontend frontend : this.frontends.values()) {
            frontend.inner().withSubnet(null).withPrivateIPAddress(null);
        }

        return this;
    }

    @Override
    public ApplicationGatewayImpl withoutPublicFrontend() {
        // Ensure no frontend is public
        for (ApplicationGatewayFrontend frontend : this.frontends.values()) {
            frontend.inner().withPublicIPAddress(null);
        }
        return this;
    }

    @Override
    public ApplicationGatewayImpl withFrontendPort(int portNumber) {
        return withFrontendPort(portNumber, null);
    }

    @Override
    public ApplicationGatewayImpl withFrontendPort(int portNumber, String name) {
        // Ensure inner ports list initialized
        List<ApplicationGatewayFrontendPortInner> frontendPorts = this.inner().frontendPorts();
        if (frontendPorts == null) {
            frontendPorts = new ArrayList<ApplicationGatewayFrontendPortInner>();
            this.inner().withFrontendPorts(frontendPorts);
        }

        // Attempt to find inner port by name if provided, or port number otherwise
        ApplicationGatewayFrontendPortInner frontendPortByName = null;
        ApplicationGatewayFrontendPortInner frontendPortByNumber = null;
        for (ApplicationGatewayFrontendPortInner inner : this.inner().frontendPorts()) {
            if (name != null && name.equalsIgnoreCase(inner.name())) {
                frontendPortByName = inner;
            }
            if (inner.port() == portNumber) {
                frontendPortByNumber = inner;
            }
        }

        Boolean needToCreate = this.needToCreate(frontendPortByName, frontendPortByNumber, name);
        if (Boolean.TRUE.equals(needToCreate)) {
            // If no conflict, create a new port
            if (name == null) {
                // No name specified, so auto-name it
                name = ResourceNamer.randomResourceName("port", 9);
            }

            frontendPortByName = new ApplicationGatewayFrontendPortInner()
                    .withName(name)
                    .withPort(portNumber);
            frontendPorts.add(frontendPortByName);
            return this;
        } else if (Boolean.FALSE.equals(needToCreate)) {
            // If found matching port, then nothing needs to happen
            return this;
        } else {
            // If name conflict for the same port number, then fail
            return null;
        }
    }

    @Override
    public ApplicationGatewayImpl withPrivateFrontend() {
        return withPrivateFrontend(DEFAULT);
    }

    /* TODO Since Azure does not currently support multiple frontends, despite what the auto-gen'd API says, this needs to be
     * revisited when support is added.
     */
    //TODO @Override
    public ApplicationGatewayImpl withPrivateFrontend(String frontendName) {
        /* NOTE: This logic is a workaround for the unusual Azure API logic:
         * - although app gateway API definition allows multiple IP configs, only one is allowed by the service currently;
         * - although app gateway frontend API definition allows for multiple frontends, only one is allowed by the service today;
         * - and although app gateway API definition allows different subnets to be specified between the IP configs and frontends, the service
         * requires the frontend and the containing subnet to be one and the same currently.
         *
         * So the logic here attempts to figure out from the API what that containing subnet for the app gateway is so that the user wouldn't
         * have to re-enter it redundantly when enabling a private frontend, since only that one subnet is supported anyway.
         *
         * TODO: When the underlying Azure API is reworked to make more sense, or the app gateway service starts supporting the functionality
         * that the underlying API implies is supported, this model and implementation should be revisited.
         */

        // Attempt to get the default config first
        ApplicationGatewayIpConfiguration ipConfig = this.ipConfigs.get(DEFAULT);
        if (ipConfig == null) {
            // No default config, so get the first IP config that exists
            ipConfig = this.ipConfigs.values().iterator().next();
            if (ipConfig == null) {
                // No IP config found, so fail fast, since there is nothing else that could be done here,
                // the state is corrupt, this should not happen
                return null;
            }
        }

        // Get the needed subnet reference
        String subnetId = ipConfig.inner().subnet().id();
        String networkId = ResourceUtils.parentResourceIdFromResourceId(subnetId);
        String subnetName = ResourceUtils.nameFromResourceId(subnetId);
        return this.definePrivateFrontend(frontendName)
            .withExistingSubnet(networkId, subnetName)
            .attach();
    }

    @Override
    public ApplicationGatewayImpl withNewPublicIpAddress() {
        this.definePublicFrontend(DEFAULT).attach();
        return super.withNewPublicIpAddress();
    }

    @Override
    public ApplicationGatewayImpl withNewPublicIpAddress(String dnsLeafLabel) {
        this.definePublicFrontend(DEFAULT).attach();
        return super.withNewPublicIpAddress(dnsLeafLabel);
    }

    @Override
    public ApplicationGatewayImpl withoutBackendFqdn(String fqdn) {
        for (ApplicationGatewayBackend backend : this.backends.values()) {
            ApplicationGatewayBackendImpl backendImpl = (ApplicationGatewayBackendImpl) backend;
            backendImpl.withoutFqdn(fqdn);
        }
        return this;
    }

    @Override
    public ApplicationGatewayImpl withoutBackendIpAddress(String ipAddress) {
        for (ApplicationGatewayBackend backend : this.backends.values()) {
            ApplicationGatewayBackendImpl backendImpl = (ApplicationGatewayBackendImpl) backend;
            backendImpl.withoutIpAddress(ipAddress);
        }
        return this;
    }

    @Override
    public ApplicationGatewayImpl withoutIpConfiguration(String ipConfigurationName) {
        this.ipConfigs.remove(ipConfigurationName);
        return this;
    }

    @Override
    public ApplicationGatewayImpl withoutFrontend(String frontendName) {
        this.frontends.remove(frontendName);
        return this;
    }

    @Override
    public ApplicationGatewayImpl withoutFrontendPort(String name) {
        if (this.inner().frontendPorts() == null) {
            return this;
        }

        for (int i = 0; i < this.inner().frontendPorts().size(); i++) {
            ApplicationGatewayFrontendPortInner inner = this.inner().frontendPorts().get(i);
            if (inner.name().equalsIgnoreCase(name)) {
                this.inner().frontendPorts().remove(i);
                break;
            }
        }

        return this;
    }

    @Override
    public ApplicationGatewayImpl withoutFrontendPort(int portNumber) {
        for (int i = 0; i < this.inner().frontendPorts().size(); i++) {
            ApplicationGatewayFrontendPortInner inner = this.inner().frontendPorts().get(i);
            if (inner.port().equals(portNumber)) {
                this.inner().frontendPorts().remove(i);
                break;
            }
        }

        return this;
    }

    @Override
    public ApplicationGatewayImpl withoutCertificate(String name) {
        this.sslCerts.remove(name);
        return this;
    }

    @Override
    public ApplicationGatewayImpl withoutFrontendHttpListener(String name) {
        this.httpListeners.remove(name);
        return this;
    }

    @Override
    public ApplicationGatewayImpl withoutRequestRoutingRule(String name) {
        this.rules.remove(name);
        return this;
    }

    @Override
    public ApplicationGatewayImpl withoutBackend(String backendName) {
        this.backends.remove(backendName);
        return this;
    }

    @Override
    public ApplicationGatewayBackendImpl updateBackend(String name) {
        return (ApplicationGatewayBackendImpl) this.backends.get(name);
    }

    @Override
    public ApplicationGatewayImpl withoutBackendHttpConfiguration(String name) {
        this.backendHttpConfigs.remove(name);
        return this;
    }

    @Override
    public ApplicationGatewayBackendHttpConfigurationImpl updateBackendHttpConfiguration(String name) {
        return (ApplicationGatewayBackendHttpConfigurationImpl) this.backendHttpConfigs.get(name);
    }

    // Getters

    @Override
    public ApplicationGatewayFrontendListener getFrontendListenerByPortNumber(int portNumber) {
        ApplicationGatewayFrontendListener listener = null;
        for (ApplicationGatewayFrontendListener l : this.httpListeners.values()) {
            if (l.frontendPortNumber() == portNumber) {
                listener = l;
                break;
            }
        }
        return listener;
    }

    @Override
    public Map<String, ApplicationGatewayBackendHttpConfiguration> backendHttpConfigurations() {
        return Collections.unmodifiableMap(this.backendHttpConfigs);
    }

    @Override
    public Map<String, ApplicationGatewayBackend> backends() {
        return Collections.unmodifiableMap(this.backends);
    }

    @Override
    public Map<String, ApplicationGatewayRequestRoutingRule> requestRoutingRules() {
        return Collections.unmodifiableMap(this.rules);
    }

    @Override
    public Map<String, ApplicationGatewayFrontend> frontends() {
        return Collections.unmodifiableMap(this.frontends);
    }

    @Override
    public Map<String, ApplicationGatewaySslCertificate> sslCertificates() {
        return Collections.unmodifiableMap(this.sslCerts);
    }

    @Override
    public Map<String, ApplicationGatewayFrontendListener> frontendListeners() {
        return Collections.unmodifiableMap(this.httpListeners);
    }

    @Override
    public Map<String, ApplicationGatewayIpConfiguration> ipConfigurations() {
        return Collections.unmodifiableMap(this.ipConfigs);
    }

    @Override
    public ApplicationGatewaySku sku() {
        return this.inner().sku();
    }

    @Override
    public ApplicationGatewayOperationalState operationalState() {
        return this.inner().operationalState();
    }

    @Override
    public ApplicationGatewaySslPolicy sslPolicy() {
        return this.inner().sslPolicy();
    }

    @Override
    public Map<String, Integer> frontendPorts() {
        Map<String, Integer> ports = new TreeMap<>();
        if (this.inner().frontendPorts() != null) {
            for (ApplicationGatewayFrontendPortInner portInner : this.inner().frontendPorts()) {
                ports.put(portInner.name(), portInner.port());
            }
        }
        return Collections.unmodifiableMap(ports);
    }

    @Override
    public String frontendPortNameFromNumber(int portNumber) {
        String portName = null;
        for (Entry<String, Integer> portEntry : this.frontendPorts().entrySet()) {
            if (portNumber == portEntry.getValue()) {
                portName = portEntry.getKey();
                break;
            }
        }
        return portName;
    }
}
