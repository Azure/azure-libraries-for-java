/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network.implementation;

import com.microsoft.azure.v2.SubResource;
import com.microsoft.azure.v2.credentials.AzureTokenCredentials;
import com.microsoft.azure.v2.management.network.ApplicationGateway;
import com.microsoft.azure.v2.management.network.ApplicationGatewayBackend;
import com.microsoft.azure.v2.management.network.ApplicationGateways;
import com.microsoft.azure.v2.management.network.ApplicationSecurityGroups;
import com.microsoft.azure.v2.management.network.DdosProtectionPlans;
import com.microsoft.azure.v2.management.network.ExpressRouteCircuits;
import com.microsoft.azure.v2.management.network.ExpressRouteCrossConnections;
import com.microsoft.azure.v2.management.network.LoadBalancers;
import com.microsoft.azure.v2.management.network.LocalNetworkGateways;
import com.microsoft.azure.v2.management.network.Network;
import com.microsoft.azure.v2.management.network.NetworkInterfaces;
import com.microsoft.azure.v2.management.network.NetworkSecurityGroups;
import com.microsoft.azure.v2.management.network.NetworkUsages;
import com.microsoft.azure.v2.management.network.Networks;
import com.microsoft.azure.v2.management.network.PublicIPAddresses;
import com.microsoft.azure.v2.management.network.RouteFilters;
import com.microsoft.azure.v2.management.network.RouteTables;
import com.microsoft.azure.v2.management.network.Subnet;
import com.microsoft.azure.v2.management.network.NetworkWatchers;
import com.microsoft.azure.v2.management.network.VirtualNetworkGateways;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.AzureConfigurable;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation.AzureConfigurableImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation.Manager;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.ProviderRegistrationPolicyFactory;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.ResourceManagerThrottlingPolicyFactory;
import com.microsoft.rest.v2.annotations.Beta;
import com.microsoft.rest.v2.http.HttpPipeline;
import com.microsoft.rest.v2.http.HttpPipelineBuilder;
import com.microsoft.rest.v2.policy.CredentialsPolicyFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entry point to Azure network management.
 */
public final class NetworkManager extends Manager<NetworkManager, NetworkManagementClientImpl> {

    // Collections
    private PublicIPAddresses publicIPAddresses;
    private Networks networks;
    private NetworkSecurityGroups networkSecurityGroups;
    private NetworkInterfaces networkInterfaces;
    private LoadBalancers loadBalancers;
    private RouteTables routeTables;
    private ApplicationGateways applicationGateways;
    private NetworkUsages networkUsages;
    private NetworkWatchers networkWatchers;
    private VirtualNetworkGateways virtualNetworkGateways;
    private LocalNetworkGateways localNetworkGateways;
    private ExpressRouteCircuits expressRouteCircuits;
    private ApplicationSecurityGroups applicationSecurityGroups;
    private RouteFilters routeFilters;
    private DdosProtectionPlans ddosProtectionPlans;
    private ExpressRouteCrossConnections expressRouteCrossConnections;

    /**
     * Get a Configurable instance that can be used to create {@link NetworkManager}
     * with optional configuration.
     *
     * @return the instance allowing configurations
     */
    public static Configurable configure() {
        return new NetworkManager.ConfigurableImpl();
    }

    /**
     * Creates an instance of NetworkManager that exposes storage resource management API entry points.
     *
     * @param credentials the credentials to use
     * @param subscriptionId the subscription UUID
     * @return the StorageManager
     */
    public static NetworkManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
        return new NetworkManager(new HttpPipelineBuilder()
                .withRequestPolicy(new CredentialsPolicyFactory(credentials))
                .withRequestPolicy(new ProviderRegistrationPolicyFactory(credentials))
                .withRequestPolicy(new ResourceManagerThrottlingPolicyFactory())
                .build(), subscriptionId);
    }

    /**
     * Creates an instance of NetworkManager that exposes storage resource management API entry points.
     *
     * @param httpPipeline the HttpPipeline to be used for API calls.
     * @param subscriptionId the subscription UUID
     * @return the StorageManager
     */
    public static NetworkManager authenticate(HttpPipeline httpPipeline, String subscriptionId) {
        return new NetworkManager(httpPipeline, subscriptionId);
    }

    /**
     * The interface allowing configurations to be set.
     */
    public interface Configurable extends AzureConfigurable<Configurable> {
        /**
         * Creates an instance of NetworkManager that exposes network management API entry points.
         *
         * @param credentials the credentials to use
         * @param subscriptionId the subscription UUID
         * @return the interface exposing network management API entry points that work across subscriptions
         */
        NetworkManager authenticate(AzureTokenCredentials credentials, String subscriptionId);
    }

    /**
     * The implementation for Configurable interface.
     */
    private static final class ConfigurableImpl extends AzureConfigurableImpl<Configurable> implements Configurable {
        public NetworkManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
            return NetworkManager.authenticate(buildPipeline(credentials), subscriptionId);
        }
    }

    private NetworkManager(HttpPipeline httpPipeline, String subscriptionId) {
        super(
                httpPipeline,
                subscriptionId,
                new NetworkManagementClientImpl(httpPipeline).withSubscriptionId(subscriptionId));
    }

    /**
     * @return entry point to route table management
     */
    public RouteTables routeTables() {
        if (this.routeTables == null) {
            this.routeTables = new RouteTablesImpl(this);
        }
        return this.routeTables;
    }

    /**
     * @return entry point to virtual network management
     */
    public Networks networks() {
        if (this.networks == null) {
            this.networks = new NetworksImpl(this);
        }
        return this.networks;
    }

    /**
     * @return entry point to network security group management
     */
    public NetworkSecurityGroups networkSecurityGroups() {
        if (this.networkSecurityGroups == null) {
            this.networkSecurityGroups = new NetworkSecurityGroupsImpl(this);
        }
        return this.networkSecurityGroups;
    }

    /**
     * @return entry point to public IP address management
     */
    public PublicIPAddresses publicIPAddresses() {
        if (this.publicIPAddresses == null) {
            this.publicIPAddresses = new PublicIPAddressesImpl(this);
        }
        return this.publicIPAddresses;
    }

    /**
     * @return entry point to network interface management
     */
    public NetworkInterfaces networkInterfaces() {
        if (networkInterfaces == null) {
            this.networkInterfaces = new NetworkInterfacesImpl(this);
        }
        return this.networkInterfaces;
    }

    /**
     * @return entry point to application gateway management
     */
    public ApplicationGateways applicationGateways() {
        if (this.applicationGateways == null) {
            this.applicationGateways = new ApplicationGatewaysImpl(this);
        }
        return this.applicationGateways;
    }

    /**
     * @return entry point to load balancer management
     */
    public LoadBalancers loadBalancers() {
        if (this.loadBalancers == null) {
            this.loadBalancers = new LoadBalancersImpl(this);
        }
        return this.loadBalancers;
    }

    /**
     * @return entry point to network resource usage management API entry point
     */
    public NetworkUsages usages() {
        if (this.networkUsages == null) {
            this.networkUsages = new NetworkUsagesImpl(super.innerManagementClient);
        }
        return this.networkUsages;
    }

    /**
     * @return entry point to network watchers management API entry point
     */
    @Beta(since = "V1_2_0")
    public NetworkWatchers networkWatchers() {
        if (this.networkWatchers == null) {
            this.networkWatchers = new NetworkWatchersImpl(this);
        }
        return this.networkWatchers;
    }

    /**
     * @return entry point to virtual network gateways management
     */
    @Beta(since = "V1_3_0")
    public VirtualNetworkGateways virtualNetworkGateways() {
        if (this.virtualNetworkGateways == null) {
            this.virtualNetworkGateways = new VirtualNetworkGatewaysImpl(this);
        }
        return this.virtualNetworkGateways;
    }

    /**
     * @return entry point to local network gateway management
     */
    @Beta(since = "V1_3_0")
    public LocalNetworkGateways localNetworkGateways() {
        if (this.localNetworkGateways == null) {
            this.localNetworkGateways = new LocalNetworkGatewaysImpl(this);
        }
        return this.localNetworkGateways;
    }

    /**
     * @return entry point to express route circuit management
     */
    @Beta(since = "V1_4_0")
    public ExpressRouteCircuits expressRouteCircuits() {
        if (this.expressRouteCircuits == null) {
            this.expressRouteCircuits = new ExpressRouteCircuitsImpl(this);
        }
        return this.expressRouteCircuits;
    }

    /**
     * @return entry point to application security groups management
     */
    @Beta(since = "V1_10_0")
    public ApplicationSecurityGroups applicationSecurityGroups() {
        if (this.applicationSecurityGroups == null) {
            this.applicationSecurityGroups = new ApplicationSecurityGroupsImpl(this);
        }
        return this.applicationSecurityGroups;
    }

    /**
     * @return entry point to application security groups management
     */
    @Beta(since = "V1_10_0")
    public RouteFilters routeFilters() {
        if (this.routeFilters == null) {
            this.routeFilters = new RouteFiltersImpl(this);
        }
        return this.routeFilters;
    }

    /**
     * @return entry point to DDoS protection plans management
     */
    @Beta(since = "V1_10_0")
    public DdosProtectionPlans ddosProtectionPlans() {
        if (this.ddosProtectionPlans == null) {
            this.ddosProtectionPlans = new DdosProtectionPlansImpl(this);
        }
        return this.ddosProtectionPlans;
    }

    /**
     * @return entry point to express route cross connections management
     */
    @Beta(since = "V1_11_0")
    public ExpressRouteCrossConnections expressRouteCrossConnections() {
        if (this.expressRouteCrossConnections == null) {
            this.expressRouteCrossConnections = new ExpressRouteCrossConnectionsImpl(this);
        }
        return this.expressRouteCrossConnections;
    }

    // Internal utility function
    Subnet getAssociatedSubnet(SubResource subnetRef) {
        if (subnetRef == null) {
            return null;
        }

        String vnetId = ResourceUtils.parentResourceIdFromResourceId(subnetRef.id());
        String subnetName = ResourceUtils.nameFromResourceId(subnetRef.id());

        if (vnetId == null || subnetName == null) {
            return null;
        }

        Network network = this.networks().getById(vnetId);
        if (network == null) {
            return null;
        }

        return network.subnets().get(subnetName);
    }

    // Internal utility function
    List<Subnet> listAssociatedSubnets(List<SubnetInner> subnetRefs) {
        final Map<String, Network> networks = new HashMap<>();
        final List<Subnet> subnets = new ArrayList<>();

        if (subnetRefs != null) {
            for (SubnetInner subnetRef : subnetRefs) {
                String networkId = ResourceUtils.parentResourceIdFromResourceId(subnetRef.id());
                Network network = networks.get(networkId.toLowerCase());
                if (network == null) {
                    network = this.networks().getById(networkId);
                    networks.put(networkId.toLowerCase(), network);
                }

                String subnetName = ResourceUtils.nameFromResourceId(subnetRef.id());
                subnets.add(network.subnets().get(subnetName));
            }
        }

        return Collections.unmodifiableList(subnets);
    }

    // Internal utility function
    Collection<ApplicationGatewayBackend> listAssociatedApplicationGatewayBackends(List<ApplicationGatewayBackendAddressPoolInner> backendRefs) {
        final Map<String, ApplicationGateway> appGateways = new HashMap<>();
        final List<ApplicationGatewayBackend> backends = new ArrayList<>();

        if (backendRefs != null) {
            for (ApplicationGatewayBackendAddressPoolInner backendRef : backendRefs) {
                String appGatewayId = ResourceUtils.parentResourceIdFromResourceId(backendRef.id());
                ApplicationGateway appGateway = appGateways.get(appGatewayId.toLowerCase());
                if (appGateway == null) {
                    appGateway = this.applicationGateways().getById(appGatewayId);
                    appGateways.put(appGatewayId.toLowerCase(), appGateway);
                }

                String backendName = ResourceUtils.nameFromResourceId(backendRef.id());
                backends.add(appGateway.backends().get(backendName));
            }
        }

        return Collections.unmodifiableCollection(backends);
    }
}