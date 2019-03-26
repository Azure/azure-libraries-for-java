package com.microsoft.azure.management;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.appservice.FunctionApps;
import com.microsoft.azure.management.appservice.WebApps;
import com.microsoft.azure.management.batch.BatchAccounts;
import com.microsoft.azure.management.cdn.CdnProfiles;
import com.microsoft.azure.management.compute.AvailabilitySets;
import com.microsoft.azure.management.compute.ComputeUsages;
import com.microsoft.azure.management.compute.Disks;
import com.microsoft.azure.management.compute.Snapshots;
import com.microsoft.azure.management.compute.VirtualMachineCustomImages;
import com.microsoft.azure.management.compute.VirtualMachineImages;
import com.microsoft.azure.management.compute.VirtualMachineScaleSets;
import com.microsoft.azure.management.compute.VirtualMachines;
import com.microsoft.azure.management.containerinstance.ContainerGroups;
import com.microsoft.azure.management.containerregistry.Registries;
import com.microsoft.azure.management.containerservice.ContainerServices;
import com.microsoft.azure.management.containerservice.KubernetesClusters;
import com.microsoft.azure.management.cosmosdb.CosmosDBAccounts;
import com.microsoft.azure.management.dns.DnsZones;
import com.microsoft.azure.management.keyvault.Vaults;
import com.microsoft.azure.management.msi.Identities;
import com.microsoft.azure.management.network.*;
import com.microsoft.azure.management.redis.RedisCaches;
import com.microsoft.azure.management.resources.Deployments;
import com.microsoft.azure.management.resources.Features;
import com.microsoft.azure.management.resources.GenericResources;
import com.microsoft.azure.management.resources.PolicyAssignments;
import com.microsoft.azure.management.resources.PolicyDefinitions;
import com.microsoft.azure.management.resources.Providers;
import com.microsoft.azure.management.resources.ResourceGroups;
import com.microsoft.azure.management.resources.Subscription;
import com.microsoft.azure.management.resources.Subscriptions;
import com.microsoft.azure.management.search.SearchServices;
import com.microsoft.azure.management.servicebus.ServiceBusNamespaces;
import com.microsoft.azure.management.sql.SqlServers;
import com.microsoft.azure.management.storage.StorageAccounts;
import com.microsoft.azure.management.storage.Usages;
import com.microsoft.azure.management.trafficmanager.TrafficManagerProfiles;

public interface AzureInterface {
    String subscriptionId();

    Subscription getCurrentSubscription();

    Subscriptions subscriptions();

    ResourceGroups resourceGroups();

    Deployments deployments();

    GenericResources genericResources();

    Features features();

    Providers providers();

    PolicyDefinitions policyDefinitions();

    PolicyAssignments policyAssignments();

    StorageAccounts storageAccounts();

    Usages storageUsages();

    AvailabilitySets availabilitySets();

    Networks networks();

    RouteTables routeTables();

    LoadBalancers loadBalancers();

    ApplicationGateways applicationGateways();

    NetworkSecurityGroups networkSecurityGroups();

    NetworkUsages networkUsages();

    NetworkWatchers networkWatchers();

    VirtualNetworkGateways virtualNetworkGateways();

    LocalNetworkGateways localNetworkGateways();

    VirtualMachines virtualMachines();

    VirtualMachineScaleSets virtualMachineScaleSets();

    VirtualMachineImages virtualMachineImages();

    VirtualMachineCustomImages virtualMachineCustomImages();

    Disks disks();

    Snapshots snapshots();

    PublicIPAddresses publicIPAddresses();

    NetworkInterfaces networkInterfaces();

    ComputeUsages computeUsages();

    Vaults vaults();

    BatchAccounts batchAccounts();

    TrafficManagerProfiles trafficManagerProfiles();

    RedisCaches redisCaches();

    CdnProfiles cdnProfiles();

    DnsZones dnsZones();

    @Beta
    WebApps webApps();

    @Beta
    FunctionApps functionApps();

    SqlServers sqlServers();

    @Beta
    ServiceBusNamespaces serviceBusNamespaces();

    @Beta(Beta.SinceVersion.V1_4_0)
    ContainerServices containerServices();

    @Beta(Beta.SinceVersion.V1_4_0)
    KubernetesClusters kubernetesClusters();

    @Beta(Beta.SinceVersion.V1_3_0)
    ContainerGroups containerGroups();

    @Beta(Beta.SinceVersion.V1_1_0)
    Registries containerRegistries();

    @Beta(Beta.SinceVersion.V1_2_0)
    CosmosDBAccounts cosmosDBAccounts();

    @Beta(Beta.SinceVersion.V1_2_0)
    SearchServices searchServices();

    @Beta(Beta.SinceVersion.V1_5_1)
    Identities identities();

    @Beta(Beta.SinceVersion.V1_2_0)
    AccessManagement accessManagement();
}
