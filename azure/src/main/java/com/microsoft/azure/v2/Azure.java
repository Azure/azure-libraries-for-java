/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2;

import com.microsoft.azure.v2.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.v2.credentials.AzureTokenCredentials;
import com.microsoft.azure.v2.management.compute.AvailabilitySets;
import com.microsoft.azure.v2.management.compute.ComputeSkus;
import com.microsoft.azure.v2.management.compute.ComputeUsages;
import com.microsoft.azure.v2.management.compute.Disks;
import com.microsoft.azure.v2.management.compute.Galleries;
import com.microsoft.azure.v2.management.compute.GalleryImageVersions;
import com.microsoft.azure.v2.management.compute.GalleryImages;
import com.microsoft.azure.v2.management.compute.Snapshots;
import com.microsoft.azure.v2.management.compute.VirtualMachineCustomImages;
import com.microsoft.azure.v2.management.compute.VirtualMachineImages;
import com.microsoft.azure.v2.management.compute.VirtualMachineScaleSets;
import com.microsoft.azure.v2.management.compute.VirtualMachines;
import com.microsoft.azure.v2.management.compute.implementation.ComputeManager;
import com.microsoft.azure.v2.management.graphrbac.ActiveDirectoryApplications;
import com.microsoft.azure.v2.management.graphrbac.ActiveDirectoryGroups;
import com.microsoft.azure.v2.management.graphrbac.ActiveDirectoryUsers;
import com.microsoft.azure.v2.management.graphrbac.RoleAssignments;
import com.microsoft.azure.v2.management.graphrbac.RoleDefinitions;
import com.microsoft.azure.v2.management.graphrbac.ServicePrincipals;
import com.microsoft.azure.v2.management.graphrbac.implementation.GraphRbacManager;
import com.microsoft.azure.v2.management.msi.Identities;
import com.microsoft.azure.v2.management.msi.implementation.MSIManager;
import com.microsoft.azure.v2.management.network.ApplicationGateways;
import com.microsoft.azure.v2.management.network.ApplicationSecurityGroups;
import com.microsoft.azure.v2.management.network.DdosProtectionPlans;
import com.microsoft.azure.v2.management.network.ExpressRouteCircuits;
import com.microsoft.azure.v2.management.network.ExpressRouteCrossConnections;
import com.microsoft.azure.v2.management.network.LoadBalancers;
import com.microsoft.azure.v2.management.network.LocalNetworkGateways;
import com.microsoft.azure.v2.management.network.NetworkInterfaces;
import com.microsoft.azure.v2.management.network.NetworkSecurityGroups;
import com.microsoft.azure.v2.management.network.NetworkUsages;
import com.microsoft.azure.v2.management.network.NetworkWatchers;
import com.microsoft.azure.v2.management.network.Networks;
import com.microsoft.azure.v2.management.network.PublicIPAddresses;
import com.microsoft.azure.v2.management.network.RouteFilters;
import com.microsoft.azure.v2.management.network.RouteTables;
import com.microsoft.azure.v2.management.network.VirtualNetworkGateways;
import com.microsoft.azure.v2.management.network.implementation.NetworkManager;
import com.microsoft.azure.v2.management.resources.Deployments;
import com.microsoft.azure.v2.management.resources.Features;
import com.microsoft.azure.v2.management.resources.GenericResources;
import com.microsoft.azure.v2.management.resources.PolicyAssignments;
import com.microsoft.azure.v2.management.resources.PolicyDefinitions;
import com.microsoft.azure.v2.management.resources.Providers;
import com.microsoft.azure.v2.management.resources.ResourceGroups;
import com.microsoft.azure.v2.management.resources.Subscription;
import com.microsoft.azure.v2.management.resources.Subscriptions;
import com.microsoft.azure.v2.management.resources.Tenants;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.AzureConfigurable;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation.AzureConfigurableImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.ProviderRegistrationPolicyFactory;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.ResourceManagerThrottlingPolicyFactory;
import com.microsoft.azure.v2.management.resources.implementation.ResourceManager;
import com.microsoft.azure.v2.management.storage.StorageAccounts;
import com.microsoft.azure.v2.management.storage.StorageSkus;
import com.microsoft.azure.v2.management.storage.Usages;
import com.microsoft.azure.v2.management.storage.implementation.StorageManager;
import com.microsoft.azure.v2.management.trafficmanager.TrafficManagerProfiles;
import com.microsoft.azure.v2.management.trafficmanager.implementation.TrafficManager;
import com.microsoft.azure.v2.policy.AsyncCredentialsPolicyFactory;
import com.microsoft.rest.v2.annotations.Beta;
import com.microsoft.rest.v2.http.HttpPipeline;
import com.microsoft.rest.v2.http.HttpPipelineBuilder;

import java.io.File;
import java.io.IOException;

//import com.microsoft.azure.v2.management.appservice.WebApps;
//import com.microsoft.azure.v2.management.appservice.implementation.AppServiceManager;
//import com.microsoft.azure.v2.management.batch.BatchAccounts;
//import com.microsoft.azure.v2.management.batch.implementation.BatchManager;
//import com.microsoft.azure.v2.management.batchai.BatchAIUsages;
//import com.microsoft.azure.v2.management.batchai.BatchAIWorkspaces;
//import com.microsoft.azure.v2.management.batchai.implementation.BatchAIManager;
//import com.microsoft.azure.v2.management.cdn.CdnProfiles;
//import com.microsoft.azure.v2.management.cdn.implementation.CdnManager;
//import com.microsoft.azure.v2.management.containerinstance.ContainerGroups;
//import com.microsoft.azure.v2.management.containerinstance.implementation.ContainerInstanceManager;
//import com.microsoft.azure.v2.management.containerregistry.Registries;
//import com.microsoft.azure.v2.management.containerregistry.implementation.ContainerRegistryManager;
//import com.microsoft.azure.v2.management.containerservice.ContainerServices;
//import com.microsoft.azure.v2.management.containerservice.KubernetesClusters;
//import com.microsoft.azure.v2.management.containerservice.implementation.ContainerServiceManager;
//import com.microsoft.azure.v2.management.dns.DnsZones;
//import com.microsoft.azure.v2.management.dns.implementation.DnsZoneManager;
//import com.microsoft.azure.v2.management.cosmosdb.CosmosDBAccounts;
//import com.microsoft.azure.v2.management.cosmosdb.implementation.CosmosDBManager;
//import com.microsoft.azure.v2.management.eventhub.EventHubDisasterRecoveryPairings;
//import com.microsoft.azure.v2.management.eventhub.EventHubNamespaces;
//import com.microsoft.azure.v2.management.eventhub.EventHubs;
//import com.microsoft.azure.v2.management.eventhub.implementation.EventHubManager;
//import com.microsoft.azure.v2.management.keyvault.Vaults;
//import com.microsoft.azure.v2.management.keyvault.implementation.KeyVaultManager;
//import com.microsoft.azure.v2.management.locks.ManagementLocks;
//import com.microsoft.azure.v2.management.locks.implementation.AuthorizationManager;
//import com.microsoft.azure.v2.management.monitor.ActionGroups;
//import com.microsoft.azure.v2.management.monitor.ActivityLogs;
//import com.microsoft.azure.v2.management.monitor.AlertRules;
//import com.microsoft.azure.v2.management.monitor.AutoscaleSettings;
//import com.microsoft.azure.v2.management.monitor.DiagnosticSettings;
//import com.microsoft.azure.v2.management.monitor.MetricDefinitions;
//import com.microsoft.azure.v2.management.monitor.implementation.MonitorManager;
//import com.microsoft.azure.v2.management.redis.RedisCaches;
//import com.microsoft.azure.v2.management.redis.implementation.RedisManager;
//import com.microsoft.azure.v2.management.search.SearchServices;
//import com.microsoft.azure.v2.management.search.implementation.SearchServiceManager;
//import com.microsoft.azure.v2.management.servicebus.ServiceBusNamespaces;
//import com.microsoft.azure.v2.management.servicebus.implementation.ServiceBusManager;
//import com.microsoft.azure.v2.management.sql.SqlServers;
//import com.microsoft.azure.v2.management.sql.implementation.SqlServerManager;

/**
 * The entry point for accessing resource management APIs in Azure.
 */
public final class Azure {
    private final ResourceManager resourceManager;
    private final StorageManager storageManager;
    private final ComputeManager computeManager;
    private final NetworkManager networkManager;
//     private final KeyVaultManager keyVaultManager;
//     private final BatchManager batchManager;
//     private final BatchAIManager batchAIManager;
    private final TrafficManager trafficManager;
//     private final RedisManager redisManager;
//     private final CdnManager cdnManager;
//     private final DnsZoneManager dnsZoneManager;
//     private final AppServiceManager appServiceManager;
//     private final SqlServerManager sqlServerManager;
//     private final ServiceBusManager serviceBusManager;
//     private final ContainerInstanceManager containerInstanceManager;
//     private final ContainerRegistryManager containerRegistryManager;
//     private final ContainerServiceManager containerServiceManager;
//     private final SearchServiceManager searchServiceManager;
//     private final CosmosDBManager cosmosDBManager;
//     private final AuthorizationManager authorizationManager;
    private final MSIManager msiManager;
    // private final MonitorManager monitorManager;
    // private final EventHubManager eventHubManager;
    private final String subscriptionId;
    private final Authenticated authenticated;

    /**
     * Authenticate to Azure using an Azure credentials object.
     *
     * @param credentials the credentials object
     * @return the authenticated Azure client
     */
    public static Authenticated authenticate(AzureTokenCredentials credentials) {
        return new AuthenticatedImpl(new HttpPipelineBuilder()
                .withRequestPolicy(new AsyncCredentialsPolicyFactory(credentials))
                .withRequestPolicy(new ProviderRegistrationPolicyFactory(credentials))
                .withRequestPolicy(new ResourceManagerThrottlingPolicyFactory())
                .build(), credentials.environment(), credentials.domain());
    }

    /**
     * Authenticates API access using a properties file containing the required credentials.
     * @param credentialsFile the file containing the credentials in the standard Java properties file format,
     * with the following keys:<p>
     * <code>
        *   subscription= #subscription ID<br>
        *   tenant= #tenant ID<br>
        *   client= #client id<br>
        *   key= #client key<br>
        *   managementURI= #management URI<br>
        *   baseURL= #base URL<br>
        *   authURL= #authentication URL<br>
     *</code>
     * @return authenticated Azure client
     * @throws IOException exception thrown from file access
     */
    public static Authenticated authenticate(File credentialsFile) throws IOException {
        ApplicationTokenCredentials credentials = ApplicationTokenCredentials.fromFile(credentialsFile);
        return new AuthenticatedImpl(new HttpPipelineBuilder()
                .withRequestPolicy(new AsyncCredentialsPolicyFactory(credentials))
                .withRequestPolicy(new ProviderRegistrationPolicyFactory(credentials))
                .withRequestPolicy(new ResourceManagerThrottlingPolicyFactory())
                .build(), credentials.environment(), credentials.domain()).withDefaultSubscription(credentials.defaultSubscriptionId());
    }

    /**
     * Authenticates API access using a RestClient instance.
     * @param httpPipeline the HttpPipeline configured with Azure authentication credentials
     * @param environment the azure environment
     * @param tenantId the tenantId in Active Directory
     * @return authenticated Azure client
     */
    public static Authenticated authenticate(HttpPipeline httpPipeline, AzureEnvironment environment, String tenantId) {
        return new AuthenticatedImpl(httpPipeline, environment, tenantId);
    }

    /**
     * Authenticates API access using a RestClient instance.
     * @param httpPipeline the HttpPipeline configured with Azure authentication credentials
     * @param tenantId the tenantId in Active Directory
     * @param environment the azure environment
     * @param subscriptionId the ID of the subscription
     * @return authenticated Azure client
     */
    public static Authenticated authenticate(HttpPipeline httpPipeline, AzureEnvironment environment, String tenantId, String subscriptionId) {
        return new AuthenticatedImpl(httpPipeline, environment, tenantId).withDefaultSubscription(subscriptionId);
    }

    /**
     * @return an interface allow configurations on the client.
     */
    public static Configurable configure() {
        return new ConfigurableImpl();
    }

    /**
     * The interface allowing configurations to be made on the client.
     */
    public interface Configurable extends AzureConfigurable<Configurable> {
        /**
         * Authenticates API access based on the provided credentials.
         *
         * @param credentials The credentials to authenticate API access with
         * @return the authenticated Azure client
         */
        Authenticated authenticate(AzureTokenCredentials credentials);

        /**
         * Authenticates API access using a properties file containing the required credentials.
         *
         * @param credentialsFile the file containing the credentials in the standard Java properties file format following
         * the same schema as {@link Azure#authenticate(File)}.<p>
         * @return Authenticated Azure client
          * @throws IOException exceptions thrown from file access
          */
        Authenticated authenticate(File credentialsFile) throws IOException;
    }

    /**
     * The implementation for {@link Configurable}.
     */
    private static final class ConfigurableImpl extends AzureConfigurableImpl<Configurable> implements Configurable {
        @Override
        public Authenticated authenticate(AzureTokenCredentials credentials) {
            if (credentials.defaultSubscriptionId() != null) {
                return Azure.authenticate(buildPipeline(credentials), credentials.environment(), credentials.domain(), credentials.defaultSubscriptionId());
            } else {
                return Azure.authenticate(buildPipeline(credentials), credentials.environment(), credentials.domain());
            }
        }

        @Override
        public Authenticated authenticate(File credentialsFile) throws IOException {
            ApplicationTokenCredentials credentials = ApplicationTokenCredentials.fromFile(credentialsFile);
            return Azure.authenticate(buildPipeline(credentials), credentials.environment(), credentials.domain(), credentials.defaultSubscriptionId());
        }
    }

    /**
     * Provides authenticated access to a subset of Azure APIs that do not require a specific subscription.
     * <p>
     * To access the subscription-specific APIs, use {@link Authenticated#withSubscription(String)},
     * or withDefaultSubscription() if a default subscription has already been previously specified
     * (for example, in a previously specified authentication file).
     */
    public interface Authenticated extends AccessManagement {
        /**
         * @return the currently selected tenant ID this client is authenticated to work with
         */
        String tenantId();

        /**
         * Entry point to subscription management APIs.
         *
         * @return Subscriptions interface providing access to subscription management
         */
        Subscriptions subscriptions();

        /**
         * Entry point to tenant management APIs.
         *
         * @return Tenants interface providing access to tenant management
         */
        Tenants tenants();

        /**
         * Selects a specific subscription for the APIs to work with.
         * <p>
         * Most Azure APIs require a specific subscription to be selected.
         * @param subscriptionId the ID of the subscription
         * @return an authenticated Azure client configured to work with the specified subscription
         */
        Azure withSubscription(String subscriptionId);

        /**
         * Selects the default subscription as the subscription for the APIs to work with.
         * <p>
         * The default subscription can be specified inside the authentication file using {@link Azure#authenticate(File)}.
         * If no default subscription has been previously provided, the first subscription as
         * returned by {@link Authenticated#subscriptions()} will be selected.
         * @return an authenticated Azure client configured to work with the default subscription
         * @throws CloudException exception thrown from Azure
         * @throws IOException exception thrown from serialization/deserialization
         */
        Azure withDefaultSubscription() throws CloudException, IOException;
    }

    /**
     * The implementation for the Authenticated interface.
     */
    private static final class AuthenticatedImpl implements Authenticated {
        private final HttpPipeline httpPipeline;
        private final AzureEnvironment environment;
        private final ResourceManager.Authenticated resourceManagerAuthenticated;
        private final GraphRbacManager graphRbacManager;
        private String defaultSubscription;
        private String tenantId;


        private AuthenticatedImpl(HttpPipeline httpPipeline, AzureEnvironment environment, String tenantId) {
            this.resourceManagerAuthenticated = ResourceManager.authenticate(httpPipeline, environment);
            this.graphRbacManager = GraphRbacManager.authenticate(httpPipeline, tenantId, environment);
            this.httpPipeline = httpPipeline;
            this.environment = environment;
            this.tenantId = tenantId;
        }

        private AuthenticatedImpl withDefaultSubscription(String subscriptionId) {
            this.defaultSubscription = subscriptionId;
            return this;
        }

        @Override
        public String tenantId() {
            return tenantId;
        }

        @Override
        public Subscriptions subscriptions() {
            return resourceManagerAuthenticated.subscriptions();
        }

        @Override
        public Tenants tenants() {
            return resourceManagerAuthenticated.tenants();
        }

        @Override
        public ActiveDirectoryUsers activeDirectoryUsers() {
            return graphRbacManager.users();
        }

        @Override
        public ActiveDirectoryGroups activeDirectoryGroups() {
            return graphRbacManager.groups();
        }

        @Override
        public ServicePrincipals servicePrincipals() {
            return graphRbacManager.servicePrincipals();
        }

        @Override
        public ActiveDirectoryApplications activeDirectoryApplications() {
            return graphRbacManager.applications();
        }

        @Override
        public RoleDefinitions roleDefinitions() {
            return graphRbacManager.roleDefinitions();
        }

        @Override
        public RoleAssignments roleAssignments() {
            return graphRbacManager.roleAssignments();
        }

        @Override
        public Azure withSubscription(String subscriptionId) {
            return new Azure(httpPipeline, subscriptionId, tenantId, environment, this);
        }

        @Override
        public Azure withDefaultSubscription() throws CloudException, IOException {
            if (this.defaultSubscription != null) {
                return withSubscription(this.defaultSubscription);
            } else {
                PagedList<Subscription> subs = this.subscriptions().list();
                if (!subs.isEmpty()) {
                    return withSubscription(subs.get(0).subscriptionId());
                } else {
                    return withSubscription(null);
                }
            }
        }
    }

    private Azure(HttpPipeline httpPipeline, String subscriptionId, String tenantId, AzureEnvironment environment, Authenticated authenticated) {
        this.resourceManager = ResourceManager.authenticate(httpPipeline, environment).withSubscription(subscriptionId);
        this.storageManager = StorageManager.authenticate(httpPipeline, subscriptionId, environment);
        this.computeManager = ComputeManager.authenticate(httpPipeline, subscriptionId, tenantId, environment);
        this.networkManager = NetworkManager.authenticate(httpPipeline, subscriptionId, environment);
//         this.keyVaultManager = KeyVaultManager.authenticate(httpPipeline, subscriptionId, tenantId, environment);
//         this.batchManager = BatchManager.authenticate(httpPipeline, subscriptionId, environment);
//         this.batchAIManager = BatchAIManager.authenticate(httpPipeline, subscriptionId, environment);
        this.trafficManager = TrafficManager.authenticate(httpPipeline, subscriptionId, environment);
//        this.redisManager = RedisManager.authenticate(httpPipeline, subscriptionId, environment);
//        this.cdnManager = CdnManager.authenticate(httpPipeline, subscriptionId, environment);
//        this.dnsZoneManager = DnsZoneManager.authenticate(httpPipeline, subscriptionId, environment);
//        this.appServiceManager = AppServiceManager.authenticate(httpPipeline, subscriptionId, tenantId, environment);
//        this.sqlServerManager = SqlServerManager.authenticate(httpPipeline, subscriptionId, tenantId, environment);
//        this.serviceBusManager = ServiceBusManager.authenticate(httpPipeline, subscriptionId, environment);
//        this.containerInstanceManager = ContainerInstanceManager.authenticate(httpPipeline, subscriptionId, environment);
//        this.containerRegistryManager = ContainerRegistryManager.authenticate(httpPipeline, subscriptionId, environment);
//        this.containerServiceManager = ContainerServiceManager.authenticate(httpPipeline, subscriptionId, environment);
//        this.cosmosDBManager = CosmosDBManager.authenticate(httpPipeline, subscriptionId, environment);
//        this.searchServiceManager = SearchServiceManager.authenticate(httpPipeline, subscriptionId, environment);
//        this.authorizationManager = AuthorizationManager.authenticate(httpPipeline, subscriptionId, environment);
        this.msiManager = MSIManager.authenticate(httpPipeline, subscriptionId, tenantId, environment);
//        this.monitorManager = MonitorManager.authenticate(httpPipeline, subscriptionId, environment);
//        this.eventHubManager = EventHubManager.authenticate(httpPipeline, subscriptionId, environment);
        this.subscriptionId = subscriptionId;
        this.authenticated = authenticated;
    }

    /**
     * @return the currently selected subscription ID this client is authenticated to work with
     */
    public String subscriptionId() {
        return this.subscriptionId;
    }

    /**
     * @return the currently selected subscription this client is authenticated to work with
     */
    public Subscription getCurrentSubscription() {
        return this.subscriptions().getById(this.subscriptionId());
    }

    /**
     * @return subscriptions that this authenticated client has access to
     */
    public Subscriptions subscriptions() {
        return this.authenticated.subscriptions();
    }

    /**
     * @return entry point to managing resource groups
     */
    public ResourceGroups resourceGroups() {
        return this.resourceManager.resourceGroups();
    }

    /**
     * @return entry point to managing deployments
     */
    public Deployments deployments() {
        return this.resourceManager.deployments();
    }

    /**
     * @return entry point to managing generic resources
     */
    public GenericResources genericResources() {
        return resourceManager.genericResources();
    }

//    /**
//     * @return entry point to managing management locks
//     */
//    public ManagementLocks managementLocks() {
//        return this.authorizationManager.managementLocks();
//    }

    /**
     * @return entry point to managing features
     */
    public Features features() {
        return resourceManager.features();
    }

    /**
     * @return entry point to managing resource providers
     */
    public Providers providers() {
        return resourceManager.providers();
    }

    /**
     * @return entry point to managing policy definitions.
     */
    public PolicyDefinitions policyDefinitions() {
        return resourceManager.policyDefinitions();
    }

    /**
     * @return entry point to managing policy assignments.
     */
    public PolicyAssignments policyAssignments() {
        return resourceManager.policyAssignments();
    }

    /**
     * @return entry point to managing storage accounts
     */
    public StorageAccounts storageAccounts() {
        return storageManager.storageAccounts();
    }

    /**
     * @return entry point to managing storage account usages
     */
    public Usages storageUsages() {
        return storageManager.usages();
    }

    /**
     * @return entry point to managing storage service SKUs
     */
    public StorageSkus storageSkus() {
        return storageManager.storageSkus();
    }

    /**
     * @return entry point to managing availability sets
     */
    public AvailabilitySets availabilitySets() {
        return computeManager.availabilitySets();
    }

    /**
     * @return entry point to managing virtual networks
     */
    public Networks networks() {
        return networkManager.networks();
    }

    /**
     * @return entry point to managing route tables
     */
    public RouteTables routeTables() {
        return networkManager.routeTables();
    }

    /**
     * @return entry point to managing load balancers
     */
    public LoadBalancers loadBalancers() {
        return networkManager.loadBalancers();
    }

    /**
     * @return entry point to managing application gateways
     */
    public ApplicationGateways applicationGateways() {
        return networkManager.applicationGateways();
    }

    /**
     * @return entry point to managing network security groups
     */
    public NetworkSecurityGroups networkSecurityGroups() {
        return networkManager.networkSecurityGroups();
    }

    /**
     * @return entry point to managing network resource usages
     */
    public NetworkUsages networkUsages() {
        return networkManager.usages();
    }

    /**
     * @return entry point to managing network watchers
     */
    public NetworkWatchers networkWatchers() {
        return networkManager.networkWatchers();
    }

    /**
     * @return entry point to managing virtual network gateways
     */
    public VirtualNetworkGateways virtualNetworkGateways() {
        return networkManager.virtualNetworkGateways();
    }

    /**
     * @return entry point to managing local network gateways
     */
    public LocalNetworkGateways localNetworkGateways() {
        return networkManager.localNetworkGateways();
    }

    /**
     * @return entry point to managing express route circuits
     */
    @Beta(since = "V1_2_3")
    public ExpressRouteCircuits expressRouteCircuits() {
        return networkManager.expressRouteCircuits();
    }

    /**
     * @return entry point to managing express route cross connections
     */
    @Beta(since = "V1_2_3")
    public ExpressRouteCrossConnections expressRouteCrossConnections() {
        return networkManager.expressRouteCrossConnections();
    }

    /**
     * @return entry point to managing express route circuits
     */
    @Beta(since = "V1_2_3")
    public ApplicationSecurityGroups applicationSecurityGroups() {
        return networkManager.applicationSecurityGroups();
    }

    /**
     * @return entry point to managing route filters
     */
    @Beta(since = "V1_2_3")
    public RouteFilters routeFilters() {
        return networkManager.routeFilters();
    }

    /**
     * @return entry point to managing DDoS protection plans
     */
    @Beta(since = "V1_2_3")
    public DdosProtectionPlans ddosProtectionPlans() {
        return networkManager.ddosProtectionPlans();
    }

    /**
     * @return entry point to managing virtual machines
     */
    public VirtualMachines virtualMachines() {
        return computeManager.virtualMachines();
    }

    /**
     * @return entry point to managing virtual machine scale sets.
     */
    public VirtualMachineScaleSets virtualMachineScaleSets() {
        return computeManager.virtualMachineScaleSets();
    }

    /**
     * @return entry point to managing virtual machine images
     */
    public VirtualMachineImages virtualMachineImages() {
        return computeManager.virtualMachineImages();
    }

    /**
     * @return entry point to managing virtual machine custom images
     */
    public VirtualMachineCustomImages virtualMachineCustomImages() {
        return computeManager.virtualMachineCustomImages();
    }

    /**
     * @return entry point to managing managed disks
     */
    public Disks disks() {
        return computeManager.disks();
    }

    /**
     * @return entry point to managing managed snapshots
     */
    public Snapshots snapshots() {
        return computeManager.snapshots();
    }

    /**
     * @return the compute service SKU management API entry point
     */
    public ComputeSkus computeSkus() {
        return computeManager.computeSkus();
    }

    /**
     * @return entry point to managing public IP addresses
     */
    public PublicIPAddresses publicIPAddresses() {
        return this.networkManager.publicIPAddresses();
    }

    /**
     * @return entry point to managing network interfaces
     */
    public NetworkInterfaces networkInterfaces() {
        return this.networkManager.networkInterfaces();
    }

    /**
     * @return entry point to managing compute resource usages
     */
    public ComputeUsages computeUsages() {
        return computeManager.usages();
    }

//    /**
//     * @return entry point to managing key vaults
//     */
//    public Vaults vaults() {
//        return this.keyVaultManager.vaults();
//    }

//    /**
//     * @return entry point to managing batch accounts.
//     */
//    public BatchAccounts batchAccounts() {
//        return batchManager.batchAccounts();
//    }

//    /**
//     * @return entry point to managing batch AI clusters.
//     */
//    @Beta(since = "V1_2_3")
//    public BatchAIWorkspaces batchAIWorkspaces() {
//        return batchAIManager.workspaces();
//    }

//    /**
//     * @return entry point to managing batch AI usages.
//     */
//    @Beta(since = "V1_2_3")
//    public BatchAIUsages batchAIUsages() {
//        return batchAIManager.usages();
//    }

    /**
     * @return entry point to managing traffic manager profiles.
     */
    public TrafficManagerProfiles trafficManagerProfiles() {
        return trafficManager.profiles();
    }

//    /**
//     * @return entry point to managing Redis Caches.
//     */
//    public RedisCaches redisCaches() {
//        return redisManager.redisCaches();
//    }

//    /**
//     * @return entry point to managing cdn manager profiles.
//     */
//    public CdnProfiles cdnProfiles() {
//        return cdnManager.profiles();
//    }

//    /**
//     * @return entry point to managing DNS zones.
//     */
//    public DnsZones dnsZones() {
//        return dnsZoneManager.zones();
//    }

//    /**
//     * @return entry point to managing web apps.
//     */
//    @Beta
//    public WebApps webApps() {
//        return appServiceManager.webApps();
//    }

//    /**
//     * @return entry point to managing app services.
//     */
//    @Beta
//    public AppServiceManager appServices() {
//        return appServiceManager;
//    }

//    /**
//     * @return entry point to managing Sql server.
//     */
//    public SqlServers sqlServers() {
//        return sqlServerManager.sqlServers();
//    }

//    /**
//     * @return entry point to managing Service Bus.
//     */
//    @Beta
//    public ServiceBusNamespaces serviceBusNamespaces() {
//        return serviceBusManager.namespaces();
//    }

//    /**
//     * @return entry point to managing Service Bus operations.
//     */
    // TODO: To be revisited in the future
    //@Beta(since = "V1_2_3")
    //public ServiceBusOperations serviceBusOperations() {
    //    return serviceBusManager.operations();
    //}

//    /**
//     * @return entry point to managing Container Services.
//     */
//    @Beta(since = "V1_2_3")
//    public ContainerServices containerServices() {
//        return containerServiceManager.containerServices();
//    }

//    /**
//     * @return entry point to managing Kubernetes clusters.
//     */
//    @Beta(since = "V1_2_3")
//    public KubernetesClusters kubernetesClusters() {
//        return containerServiceManager.kubernetesClusters();
//    }

//    /**
//     * @return entry point to managing Azure Container Instances.
//     */
//    @Beta(since = "V1_2_3")
//    public ContainerGroups containerGroups() {
//        return containerInstanceManager.containerGroups();
//    }

//    /**
//     * @return entry point to managing Container Registries.
//     */
//    @Beta(since = "V1_2_3")
//    public Registries containerRegistries() {
//        return containerRegistryManager.containerRegistries();
//    }

//    /**
//     * @return entry point to managing Container Regsitries.
//     */
//    @Beta(since = "V1_2_3")
//    public CosmosDBAccounts cosmosDBAccounts() {
//        return cosmosDBManager.databaseAccounts();
//    }

//    /**
//     * @return entry point to managing Search services.
//     */
//    @Beta(since = "V1_2_3")
//    public SearchServices searchServices() {
//        return searchServiceManager.searchServices();
//    }

    /**
     * @return entry point to managing Managed Service Identity (MSI) identities.
     */
    @Beta(since = "V1_2_3")
    public Identities identities() {
        return msiManager.identities();
    }

    /**
     * @return entry point to authentication and authorization management in Azure
     */
    @Beta(since = "V1_2_3")
    public AccessManagement accessManagement() {
        return this.authenticated;
    }

//    /**
//     * @return entry point to listing activity log events in Azure
//     */
//    @Beta(since = "V1_2_3")
//    public ActivityLogs activityLogs() {
//        return this.monitorManager.activityLogs();
//    }

//    /**
//     * @return entry point to listing metric definitions in Azure
//     */
//    @Beta(since = "V1_2_3")
//    public MetricDefinitions metricDefinitions() {
//        return this.monitorManager.metricDefinitions();
//    }

//    /**
//     * @return entry point to listing diagnostic settings in Azure
//     */
//    @Beta(since = "V1_2_3")
//    public DiagnosticSettings diagnosticSettings() {
//        return this.monitorManager.diagnosticSettings();
//    }

//    /**
//     * @return entry point to managing action groups in Azure
//     */
//    @Beta(since = "V1_2_3")
//    public ActionGroups actionGroups() {
//        return this.monitorManager.actionGroups();
//    }

//    /**
//     * @return entry point to managing alertRules in Azure
//     */
//    @Beta(since = "V1_2_3")
//    public AlertRules alertRules() {
//        return this.monitorManager.alertRules();
//    }


//    /**
//     * @return entry point to managing Autoscale Settings in Azure
//     */
//    @Beta(since = "V1_2_3")
//    public AutoscaleSettings autoscaleSettings() {
//        return this.monitorManager.autoscaleSettings();
//    }

//    /**
//     * @return entry point to managing event hub namespaces.
//     */
//    @Beta(since = "V1_2_3")
//    public EventHubNamespaces eventHubNamespaces() {
//        return this.eventHubManager.namespaces();
//    }

//    /**
//     * @return entry point to managing event hubs.
//     */
//    @Beta(since = "V1_2_3")
//    public EventHubs eventHubs() {
//        return this.eventHubManager.eventHubs();
//    }

//    /**
//     * @return entry point to managing event hub namespace geo disaster recovery.
//     */
//    @Beta(since = "V1_2_3")
//    public EventHubDisasterRecoveryPairings eventHubDisasterRecoveryPairings() {
//        return this.eventHubManager.eventHubDisasterRecoveryPairings();
//    }

    /**
     * @return entry point to manage compute galleries.
     */
    @Beta(since = "V1_2_3")
    public Galleries galleries() {
        return this.computeManager.galleries();
    }

    /**
     * @return entry point to manage compute gallery images.
     */
    @Beta(since = "V1_2_3")
    public GalleryImages galleryImages() {
        return this.computeManager.galleryImages();
    }

    /**
     * @return entry point to manage compute gallery image versions.
     */
    @Beta(since = "V1_2_3")
    public GalleryImageVersions galleryImageVersions() {
        return this.computeManager.galleryImageVersions();
    }
}
