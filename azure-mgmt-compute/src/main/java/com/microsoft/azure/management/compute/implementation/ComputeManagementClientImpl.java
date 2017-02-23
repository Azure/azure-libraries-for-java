/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute.implementation;

import com.microsoft.azure.AzureClient;
import com.microsoft.azure.AzureServiceClient;
import com.microsoft.rest.credentials.ServiceClientCredentials;
import com.microsoft.rest.RestClient;

/**
 * Initializes a new instance of the ComputeManagementClientImpl class.
 */
public class ComputeManagementClientImpl extends AzureServiceClient {
    /** the {@link AzureClient} used for long running operations. */
    private AzureClient azureClient;

    /**
     * Gets the {@link AzureClient} used for long running operations.
     * @return the azure client;
     */
    public AzureClient getAzureClient() {
        return this.azureClient;
    }

    /** Subscription credentials which uniquely identify Microsoft Azure subscription. The subscription ID forms part of the URI for every service call. */
    private String subscriptionId;

    /**
     * Gets Subscription credentials which uniquely identify Microsoft Azure subscription. The subscription ID forms part of the URI for every service call.
     *
     * @return the subscriptionId value.
     */
    public String subscriptionId() {
        return this.subscriptionId;
    }

    /**
     * Sets Subscription credentials which uniquely identify Microsoft Azure subscription. The subscription ID forms part of the URI for every service call.
     *
     * @param subscriptionId the subscriptionId value.
     * @return the service client itself
     */
    public ComputeManagementClientImpl withSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    /** Gets or sets the preferred language for the response. */
    private String acceptLanguage;

    /**
     * Gets Gets or sets the preferred language for the response.
     *
     * @return the acceptLanguage value.
     */
    public String acceptLanguage() {
        return this.acceptLanguage;
    }

    /**
     * Sets Gets or sets the preferred language for the response.
     *
     * @param acceptLanguage the acceptLanguage value.
     * @return the service client itself
     */
    public ComputeManagementClientImpl withAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
        return this;
    }

    /** Gets or sets the retry timeout in seconds for Long Running Operations. Default value is 30. */
    private int longRunningOperationRetryTimeout;

    /**
     * Gets Gets or sets the retry timeout in seconds for Long Running Operations. Default value is 30.
     *
     * @return the longRunningOperationRetryTimeout value.
     */
    public int longRunningOperationRetryTimeout() {
        return this.longRunningOperationRetryTimeout;
    }

    /**
     * Sets Gets or sets the retry timeout in seconds for Long Running Operations. Default value is 30.
     *
     * @param longRunningOperationRetryTimeout the longRunningOperationRetryTimeout value.
     * @return the service client itself
     */
    public ComputeManagementClientImpl withLongRunningOperationRetryTimeout(int longRunningOperationRetryTimeout) {
        this.longRunningOperationRetryTimeout = longRunningOperationRetryTimeout;
        return this;
    }

    /** When set to true a unique x-ms-client-request-id value is generated and included in each request. Default is true. */
    private boolean generateClientRequestId;

    /**
     * Gets When set to true a unique x-ms-client-request-id value is generated and included in each request. Default is true.
     *
     * @return the generateClientRequestId value.
     */
    public boolean generateClientRequestId() {
        return this.generateClientRequestId;
    }

    /**
     * Sets When set to true a unique x-ms-client-request-id value is generated and included in each request. Default is true.
     *
     * @param generateClientRequestId the generateClientRequestId value.
     * @return the service client itself
     */
    public ComputeManagementClientImpl withGenerateClientRequestId(boolean generateClientRequestId) {
        this.generateClientRequestId = generateClientRequestId;
        return this;
    }

    /**
     * The AvailabilitySetsInner object to access its operations.
     */
    private AvailabilitySetsInner availabilitySets;

    /**
     * Gets the AvailabilitySetsInner object to access its operations.
     * @return the AvailabilitySetsInner object.
     */
    public AvailabilitySetsInner availabilitySets() {
        return this.availabilitySets;
    }

    /**
     * The VirtualMachineExtensionImagesInner object to access its operations.
     */
    private VirtualMachineExtensionImagesInner virtualMachineExtensionImages;

    /**
     * Gets the VirtualMachineExtensionImagesInner object to access its operations.
     * @return the VirtualMachineExtensionImagesInner object.
     */
    public VirtualMachineExtensionImagesInner virtualMachineExtensionImages() {
        return this.virtualMachineExtensionImages;
    }

    /**
     * The VirtualMachineExtensionsInner object to access its operations.
     */
    private VirtualMachineExtensionsInner virtualMachineExtensions;

    /**
     * Gets the VirtualMachineExtensionsInner object to access its operations.
     * @return the VirtualMachineExtensionsInner object.
     */
    public VirtualMachineExtensionsInner virtualMachineExtensions() {
        return this.virtualMachineExtensions;
    }

    /**
     * The VirtualMachineImagesInner object to access its operations.
     */
    private VirtualMachineImagesInner virtualMachineImages;

    /**
     * Gets the VirtualMachineImagesInner object to access its operations.
     * @return the VirtualMachineImagesInner object.
     */
    public VirtualMachineImagesInner virtualMachineImages() {
        return this.virtualMachineImages;
    }

    /**
     * The UsagesInner object to access its operations.
     */
    private UsagesInner usages;

    /**
     * Gets the UsagesInner object to access its operations.
     * @return the UsagesInner object.
     */
    public UsagesInner usages() {
        return this.usages;
    }

    /**
     * The VirtualMachineSizesInner object to access its operations.
     */
    private VirtualMachineSizesInner virtualMachineSizes;

    /**
     * Gets the VirtualMachineSizesInner object to access its operations.
     * @return the VirtualMachineSizesInner object.
     */
    public VirtualMachineSizesInner virtualMachineSizes() {
        return this.virtualMachineSizes;
    }

    /**
     * The ImagesInner object to access its operations.
     */
    private ImagesInner images;

    /**
     * Gets the ImagesInner object to access its operations.
     * @return the ImagesInner object.
     */
    public ImagesInner images() {
        return this.images;
    }

    /**
     * The VirtualMachinesInner object to access its operations.
     */
    private VirtualMachinesInner virtualMachines;

    /**
     * Gets the VirtualMachinesInner object to access its operations.
     * @return the VirtualMachinesInner object.
     */
    public VirtualMachinesInner virtualMachines() {
        return this.virtualMachines;
    }

    /**
     * The VirtualMachineScaleSetsInner object to access its operations.
     */
    private VirtualMachineScaleSetsInner virtualMachineScaleSets;

    /**
     * Gets the VirtualMachineScaleSetsInner object to access its operations.
     * @return the VirtualMachineScaleSetsInner object.
     */
    public VirtualMachineScaleSetsInner virtualMachineScaleSets() {
        return this.virtualMachineScaleSets;
    }

    /**
     * The VirtualMachineScaleSetVMsInner object to access its operations.
     */
    private VirtualMachineScaleSetVMsInner virtualMachineScaleSetVMs;

    /**
     * Gets the VirtualMachineScaleSetVMsInner object to access its operations.
     * @return the VirtualMachineScaleSetVMsInner object.
     */
    public VirtualMachineScaleSetVMsInner virtualMachineScaleSetVMs() {
        return this.virtualMachineScaleSetVMs;
    }

    /**
     * The ContainerServicesInner object to access its operations.
     */
    private ContainerServicesInner containerServices;

    /**
     * Gets the ContainerServicesInner object to access its operations.
     * @return the ContainerServicesInner object.
     */
    public ContainerServicesInner containerServices() {
        return this.containerServices;
    }

    /**
     * The DisksInner object to access its operations.
     */
    private DisksInner disks;

    /**
     * Gets the DisksInner object to access its operations.
     * @return the DisksInner object.
     */
    public DisksInner disks() {
        return this.disks;
    }

    /**
     * The SnapshotsInner object to access its operations.
     */
    private SnapshotsInner snapshots;

    /**
     * Gets the SnapshotsInner object to access its operations.
     * @return the SnapshotsInner object.
     */
    public SnapshotsInner snapshots() {
        return this.snapshots;
    }

    /**
     * Initializes an instance of ComputeManagementClient client.
     *
     * @param credentials the management credentials for Azure
     */
    public ComputeManagementClientImpl(ServiceClientCredentials credentials) {
        this("https://management.azure.com", credentials);
    }

    /**
     * Initializes an instance of ComputeManagementClient client.
     *
     * @param baseUrl the base URL of the host
     * @param credentials the management credentials for Azure
     */
    public ComputeManagementClientImpl(String baseUrl, ServiceClientCredentials credentials) {
        super(baseUrl, credentials);
        initialize();
    }

    /**
     * Initializes an instance of ComputeManagementClient client.
     *
     * @param restClient the REST client to connect to Azure.
     */
    public ComputeManagementClientImpl(RestClient restClient) {
        super(restClient);
        initialize();
    }

    protected void initialize() {
        this.acceptLanguage = "en-US";
        this.longRunningOperationRetryTimeout = 30;
        this.generateClientRequestId = true;
        this.availabilitySets = new AvailabilitySetsInner(restClient().retrofit(), this);
        this.virtualMachineExtensionImages = new VirtualMachineExtensionImagesInner(restClient().retrofit(), this);
        this.virtualMachineExtensions = new VirtualMachineExtensionsInner(restClient().retrofit(), this);
        this.virtualMachineImages = new VirtualMachineImagesInner(restClient().retrofit(), this);
        this.usages = new UsagesInner(restClient().retrofit(), this);
        this.virtualMachineSizes = new VirtualMachineSizesInner(restClient().retrofit(), this);
        this.images = new ImagesInner(restClient().retrofit(), this);
        this.virtualMachines = new VirtualMachinesInner(restClient().retrofit(), this);
        this.virtualMachineScaleSets = new VirtualMachineScaleSetsInner(restClient().retrofit(), this);
        this.virtualMachineScaleSetVMs = new VirtualMachineScaleSetVMsInner(restClient().retrofit(), this);
        this.containerServices = new ContainerServicesInner(restClient().retrofit(), this);
        this.disks = new DisksInner(restClient().retrofit(), this);
        this.snapshots = new SnapshotsInner(restClient().retrofit(), this);
        this.azureClient = new AzureClient(this);
    }

    /**
     * Gets the User-Agent header for the client.
     *
     * @return the user agent string.
     */
    @Override
    public String userAgent() {
        return String.format("Azure-SDK-For-Java/%s (%s)",
                getClass().getPackage().getImplementationVersion(),
                "ComputeManagementClient, ");
    }
}
