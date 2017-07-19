/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.servicefabric.implementation;

import java.util.List;
import com.microsoft.azure.management.servicefabric.ClusterVersionDetails;
import com.microsoft.azure.management.servicefabric.CertificateDescription;
import com.microsoft.azure.management.servicefabric.ClientCertificateThumbprint;
import com.microsoft.azure.management.servicefabric.ClientCertificateCommonName;
import com.microsoft.azure.management.servicefabric.SettingsSectionDescription;
import com.microsoft.azure.management.servicefabric.NodeTypeDescription;
import com.microsoft.azure.management.servicefabric.AzureActiveDirectory;
import com.microsoft.azure.management.servicefabric.ProvisioningState;
import com.microsoft.azure.management.servicefabric.DiagnosticsStorageAccountConfig;
import com.microsoft.azure.management.servicefabric.ClusterUpgradePolicy;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.Resource;

/**
 * The cluster resource.
 */
@JsonFlatten
public class ClusterInner extends Resource {
    /**
     * The available cluster code version which the cluster can upgrade to,
     * note that you must choose upgradeMode to manual to upgrade to.
     */
    @JsonProperty(value = "properties.availableClusterVersions", access = JsonProperty.Access.WRITE_ONLY)
    private List<ClusterVersionDetails> availableClusterVersions;

    /**
     * The unique identifier for the cluster resource.
     */
    @JsonProperty(value = "properties.clusterId", access = JsonProperty.Access.WRITE_ONLY)
    private String clusterId;

    /**
     * The state for the cluster. Possible values include: 'WaitingForNodes',
     * 'Deploying', 'BaselineUpgrade', 'UpdatingUserConfiguration',
     * 'UpdatingUserCertificate', 'UpdatingInfrastructure',
     * 'EnforcingClusterVersion', 'UpgradeServiceUnreachable', 'AutoScale',
     * 'Ready'.
     */
    @JsonProperty(value = "properties.clusterState", access = JsonProperty.Access.WRITE_ONLY)
    private String clusterState;

    /**
     * The endpoint for the cluster connecting to servicefabric resource
     * provider.
     */
    @JsonProperty(value = "properties.clusterEndpoint", access = JsonProperty.Access.WRITE_ONLY)
    private String clusterEndpoint;

    /**
     * The ServiceFabric code version running in your cluster.
     */
    @JsonProperty(value = "properties.clusterCodeVersion")
    private String clusterCodeVersion;

    /**
     * This primay certificate will be used as cluster node to node security,
     * SSL certificate for cluster management endpoint and default admin
     * client.
     */
    @JsonProperty(value = "properties.certificate")
    private CertificateDescription certificate;

    /**
     * Cluster reliability level indicates replica set size of system service.
     * Possible values include: 'Bronze', 'Silver', 'Gold', 'Platinum'.
     */
    @JsonProperty(value = "properties.reliabilityLevel")
    private String reliabilityLevel;

    /**
     * Cluster upgrade mode indicates if fabric upgrade is initiated
     * automatically by the system or not. Possible values include:
     * 'Automatic', 'Manual'.
     */
    @JsonProperty(value = "properties.upgradeMode")
    private String upgradeMode;

    /**
     * The client thumbprint details ,it is used for client access for cluster
     * operation.
     */
    @JsonProperty(value = "properties.clientCertificateThumbprints")
    private List<ClientCertificateThumbprint> clientCertificateThumbprints;

    /**
     * List of client certificates to whitelist based on common names.
     */
    @JsonProperty(value = "properties.clientCertificateCommonNames")
    private List<ClientCertificateCommonName> clientCertificateCommonNames;

    /**
     * List of custom fabric settings to configure the cluster.
     */
    @JsonProperty(value = "properties.fabricSettings")
    private List<SettingsSectionDescription> fabricSettings;

    /**
     * The server certificate used by reverse proxy.
     */
    @JsonProperty(value = "properties.reverseProxyCertificate")
    private CertificateDescription reverseProxyCertificate;

    /**
     * The http management endpoint of the cluster.
     */
    @JsonProperty(value = "properties.managementEndpoint", required = true)
    private String managementEndpoint;

    /**
     * The list of nodetypes that make up the cluster.
     */
    @JsonProperty(value = "properties.nodeTypes", required = true)
    private List<NodeTypeDescription> nodeTypes;

    /**
     * The settings to enable AAD authentication on the cluster.
     */
    @JsonProperty(value = "properties.azureActiveDirectory")
    private AzureActiveDirectory azureActiveDirectory;

    /**
     * The provisioning state of the cluster resource. Possible values include:
     * 'Updating', 'Succeeded', 'Failed', 'Canceled'.
     */
    @JsonProperty(value = "properties.provisioningState", access = JsonProperty.Access.WRITE_ONLY)
    private ProvisioningState provisioningState;

    /**
     * The name of VM image VMSS has been configured with. Generic names such
     * as Windows or Linux can be used.
     */
    @JsonProperty(value = "properties.vmImage")
    private String vmImage;

    /**
     * The storage diagnostics account configuration details.
     */
    @JsonProperty(value = "properties.diagnosticsStorageAccountConfig")
    private DiagnosticsStorageAccountConfig diagnosticsStorageAccountConfig;

    /**
     * The policy to use when upgrading the cluster.
     */
    @JsonProperty(value = "properties.upgradeDescription")
    private ClusterUpgradePolicy upgradeDescription;

    /**
     * Get the availableClusterVersions value.
     *
     * @return the availableClusterVersions value
     */
    public List<ClusterVersionDetails> availableClusterVersions() {
        return this.availableClusterVersions;
    }

    /**
     * Get the clusterId value.
     *
     * @return the clusterId value
     */
    public String clusterId() {
        return this.clusterId;
    }

    /**
     * Get the clusterState value.
     *
     * @return the clusterState value
     */
    public String clusterState() {
        return this.clusterState;
    }

    /**
     * Get the clusterEndpoint value.
     *
     * @return the clusterEndpoint value
     */
    public String clusterEndpoint() {
        return this.clusterEndpoint;
    }

    /**
     * Get the clusterCodeVersion value.
     *
     * @return the clusterCodeVersion value
     */
    public String clusterCodeVersion() {
        return this.clusterCodeVersion;
    }

    /**
     * Set the clusterCodeVersion value.
     *
     * @param clusterCodeVersion the clusterCodeVersion value to set
     * @return the ClusterInner object itself.
     */
    public ClusterInner withClusterCodeVersion(String clusterCodeVersion) {
        this.clusterCodeVersion = clusterCodeVersion;
        return this;
    }

    /**
     * Get the certificate value.
     *
     * @return the certificate value
     */
    public CertificateDescription certificate() {
        return this.certificate;
    }

    /**
     * Set the certificate value.
     *
     * @param certificate the certificate value to set
     * @return the ClusterInner object itself.
     */
    public ClusterInner withCertificate(CertificateDescription certificate) {
        this.certificate = certificate;
        return this;
    }

    /**
     * Get the reliabilityLevel value.
     *
     * @return the reliabilityLevel value
     */
    public String reliabilityLevel() {
        return this.reliabilityLevel;
    }

    /**
     * Set the reliabilityLevel value.
     *
     * @param reliabilityLevel the reliabilityLevel value to set
     * @return the ClusterInner object itself.
     */
    public ClusterInner withReliabilityLevel(String reliabilityLevel) {
        this.reliabilityLevel = reliabilityLevel;
        return this;
    }

    /**
     * Get the upgradeMode value.
     *
     * @return the upgradeMode value
     */
    public String upgradeMode() {
        return this.upgradeMode;
    }

    /**
     * Set the upgradeMode value.
     *
     * @param upgradeMode the upgradeMode value to set
     * @return the ClusterInner object itself.
     */
    public ClusterInner withUpgradeMode(String upgradeMode) {
        this.upgradeMode = upgradeMode;
        return this;
    }

    /**
     * Get the clientCertificateThumbprints value.
     *
     * @return the clientCertificateThumbprints value
     */
    public List<ClientCertificateThumbprint> clientCertificateThumbprints() {
        return this.clientCertificateThumbprints;
    }

    /**
     * Set the clientCertificateThumbprints value.
     *
     * @param clientCertificateThumbprints the clientCertificateThumbprints value to set
     * @return the ClusterInner object itself.
     */
    public ClusterInner withClientCertificateThumbprints(List<ClientCertificateThumbprint> clientCertificateThumbprints) {
        this.clientCertificateThumbprints = clientCertificateThumbprints;
        return this;
    }

    /**
     * Get the clientCertificateCommonNames value.
     *
     * @return the clientCertificateCommonNames value
     */
    public List<ClientCertificateCommonName> clientCertificateCommonNames() {
        return this.clientCertificateCommonNames;
    }

    /**
     * Set the clientCertificateCommonNames value.
     *
     * @param clientCertificateCommonNames the clientCertificateCommonNames value to set
     * @return the ClusterInner object itself.
     */
    public ClusterInner withClientCertificateCommonNames(List<ClientCertificateCommonName> clientCertificateCommonNames) {
        this.clientCertificateCommonNames = clientCertificateCommonNames;
        return this;
    }

    /**
     * Get the fabricSettings value.
     *
     * @return the fabricSettings value
     */
    public List<SettingsSectionDescription> fabricSettings() {
        return this.fabricSettings;
    }

    /**
     * Set the fabricSettings value.
     *
     * @param fabricSettings the fabricSettings value to set
     * @return the ClusterInner object itself.
     */
    public ClusterInner withFabricSettings(List<SettingsSectionDescription> fabricSettings) {
        this.fabricSettings = fabricSettings;
        return this;
    }

    /**
     * Get the reverseProxyCertificate value.
     *
     * @return the reverseProxyCertificate value
     */
    public CertificateDescription reverseProxyCertificate() {
        return this.reverseProxyCertificate;
    }

    /**
     * Set the reverseProxyCertificate value.
     *
     * @param reverseProxyCertificate the reverseProxyCertificate value to set
     * @return the ClusterInner object itself.
     */
    public ClusterInner withReverseProxyCertificate(CertificateDescription reverseProxyCertificate) {
        this.reverseProxyCertificate = reverseProxyCertificate;
        return this;
    }

    /**
     * Get the managementEndpoint value.
     *
     * @return the managementEndpoint value
     */
    public String managementEndpoint() {
        return this.managementEndpoint;
    }

    /**
     * Set the managementEndpoint value.
     *
     * @param managementEndpoint the managementEndpoint value to set
     * @return the ClusterInner object itself.
     */
    public ClusterInner withManagementEndpoint(String managementEndpoint) {
        this.managementEndpoint = managementEndpoint;
        return this;
    }

    /**
     * Get the nodeTypes value.
     *
     * @return the nodeTypes value
     */
    public List<NodeTypeDescription> nodeTypes() {
        return this.nodeTypes;
    }

    /**
     * Set the nodeTypes value.
     *
     * @param nodeTypes the nodeTypes value to set
     * @return the ClusterInner object itself.
     */
    public ClusterInner withNodeTypes(List<NodeTypeDescription> nodeTypes) {
        this.nodeTypes = nodeTypes;
        return this;
    }

    /**
     * Get the azureActiveDirectory value.
     *
     * @return the azureActiveDirectory value
     */
    public AzureActiveDirectory azureActiveDirectory() {
        return this.azureActiveDirectory;
    }

    /**
     * Set the azureActiveDirectory value.
     *
     * @param azureActiveDirectory the azureActiveDirectory value to set
     * @return the ClusterInner object itself.
     */
    public ClusterInner withAzureActiveDirectory(AzureActiveDirectory azureActiveDirectory) {
        this.azureActiveDirectory = azureActiveDirectory;
        return this;
    }

    /**
     * Get the provisioningState value.
     *
     * @return the provisioningState value
     */
    public ProvisioningState provisioningState() {
        return this.provisioningState;
    }

    /**
     * Get the vmImage value.
     *
     * @return the vmImage value
     */
    public String vmImage() {
        return this.vmImage;
    }

    /**
     * Set the vmImage value.
     *
     * @param vmImage the vmImage value to set
     * @return the ClusterInner object itself.
     */
    public ClusterInner withVmImage(String vmImage) {
        this.vmImage = vmImage;
        return this;
    }

    /**
     * Get the diagnosticsStorageAccountConfig value.
     *
     * @return the diagnosticsStorageAccountConfig value
     */
    public DiagnosticsStorageAccountConfig diagnosticsStorageAccountConfig() {
        return this.diagnosticsStorageAccountConfig;
    }

    /**
     * Set the diagnosticsStorageAccountConfig value.
     *
     * @param diagnosticsStorageAccountConfig the diagnosticsStorageAccountConfig value to set
     * @return the ClusterInner object itself.
     */
    public ClusterInner withDiagnosticsStorageAccountConfig(DiagnosticsStorageAccountConfig diagnosticsStorageAccountConfig) {
        this.diagnosticsStorageAccountConfig = diagnosticsStorageAccountConfig;
        return this;
    }

    /**
     * Get the upgradeDescription value.
     *
     * @return the upgradeDescription value
     */
    public ClusterUpgradePolicy upgradeDescription() {
        return this.upgradeDescription;
    }

    /**
     * Set the upgradeDescription value.
     *
     * @param upgradeDescription the upgradeDescription value to set
     * @return the ClusterInner object itself.
     */
    public ClusterInner withUpgradeDescription(ClusterUpgradePolicy upgradeDescription) {
        this.upgradeDescription = upgradeDescription;
        return this;
    }

}
