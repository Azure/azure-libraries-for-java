/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.batch.implementation;

import com.microsoft.azure.AzureClient;
import com.microsoft.azure.AzureServiceClient;
import com.microsoft.rest.credentials.ServiceClientCredentials;
import com.microsoft.rest.RestClient;

/**
 * Initializes a new instance of the BatchManagementClientImpl class.
 */
public class BatchManagementClientImpl extends AzureServiceClient {
    /** the {@link AzureClient} used for long running operations. */
    private AzureClient azureClient;

    /**
     * Gets the {@link AzureClient} used for long running operations.
     * @return the azure client;
     */
    public AzureClient getAzureClient() {
        return this.azureClient;
    }

    /** The Azure subscription ID. This is a GUID-formatted string (e.g. 00000000-0000-0000-0000-000000000000). */
    private String subscriptionId;

    /**
     * Gets The Azure subscription ID. This is a GUID-formatted string (e.g. 00000000-0000-0000-0000-000000000000).
     *
     * @return the subscriptionId value.
     */
    public String subscriptionId() {
        return this.subscriptionId;
    }

    /**
     * Sets The Azure subscription ID. This is a GUID-formatted string (e.g. 00000000-0000-0000-0000-000000000000).
     *
     * @param subscriptionId the subscriptionId value.
     * @return the service client itself
     */
    public BatchManagementClientImpl withSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    /** The API version to be used with the HTTP request. */
    private String apiVersion;

    /**
     * Gets The API version to be used with the HTTP request.
     *
     * @return the apiVersion value.
     */
    public String apiVersion() {
        return this.apiVersion;
    }

    /** The preferred language for the response. */
    private String acceptLanguage;

    /**
     * Gets The preferred language for the response.
     *
     * @return the acceptLanguage value.
     */
    public String acceptLanguage() {
        return this.acceptLanguage;
    }

    /**
     * Sets The preferred language for the response.
     *
     * @param acceptLanguage the acceptLanguage value.
     * @return the service client itself
     */
    public BatchManagementClientImpl withAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
        return this;
    }

    /** The retry timeout in seconds for Long Running Operations. Default value is 30. */
    private int longRunningOperationRetryTimeout;

    /**
     * Gets The retry timeout in seconds for Long Running Operations. Default value is 30.
     *
     * @return the longRunningOperationRetryTimeout value.
     */
    public int longRunningOperationRetryTimeout() {
        return this.longRunningOperationRetryTimeout;
    }

    /**
     * Sets The retry timeout in seconds for Long Running Operations. Default value is 30.
     *
     * @param longRunningOperationRetryTimeout the longRunningOperationRetryTimeout value.
     * @return the service client itself
     */
    public BatchManagementClientImpl withLongRunningOperationRetryTimeout(int longRunningOperationRetryTimeout) {
        this.longRunningOperationRetryTimeout = longRunningOperationRetryTimeout;
        return this;
    }

    /** Whether a unique x-ms-client-request-id should be generated. When set to true a unique x-ms-client-request-id value is generated and included in each request. Default is true. */
    private boolean generateClientRequestId;

    /**
     * Gets Whether a unique x-ms-client-request-id should be generated. When set to true a unique x-ms-client-request-id value is generated and included in each request. Default is true.
     *
     * @return the generateClientRequestId value.
     */
    public boolean generateClientRequestId() {
        return this.generateClientRequestId;
    }

    /**
     * Sets Whether a unique x-ms-client-request-id should be generated. When set to true a unique x-ms-client-request-id value is generated and included in each request. Default is true.
     *
     * @param generateClientRequestId the generateClientRequestId value.
     * @return the service client itself
     */
    public BatchManagementClientImpl withGenerateClientRequestId(boolean generateClientRequestId) {
        this.generateClientRequestId = generateClientRequestId;
        return this;
    }

    /**
     * The BatchAccountsInner object to access its operations.
     */
    private BatchAccountsInner batchAccounts;

    /**
     * Gets the BatchAccountsInner object to access its operations.
     * @return the BatchAccountsInner object.
     */
    public BatchAccountsInner batchAccounts() {
        return this.batchAccounts;
    }

    /**
     * The ApplicationPackagesInner object to access its operations.
     */
    private ApplicationPackagesInner applicationPackages;

    /**
     * Gets the ApplicationPackagesInner object to access its operations.
     * @return the ApplicationPackagesInner object.
     */
    public ApplicationPackagesInner applicationPackages() {
        return this.applicationPackages;
    }

    /**
     * The ApplicationsInner object to access its operations.
     */
    private ApplicationsInner applications;

    /**
     * Gets the ApplicationsInner object to access its operations.
     * @return the ApplicationsInner object.
     */
    public ApplicationsInner applications() {
        return this.applications;
    }

    /**
     * The LocationsInner object to access its operations.
     */
    private LocationsInner locations;

    /**
     * Gets the LocationsInner object to access its operations.
     * @return the LocationsInner object.
     */
    public LocationsInner locations() {
        return this.locations;
    }

    /**
     * The OperationsInner object to access its operations.
     */
    private OperationsInner operations;

    /**
     * Gets the OperationsInner object to access its operations.
     * @return the OperationsInner object.
     */
    public OperationsInner operations() {
        return this.operations;
    }

    /**
     * The CertificatesInner object to access its operations.
     */
    private CertificatesInner certificates;

    /**
     * Gets the CertificatesInner object to access its operations.
     * @return the CertificatesInner object.
     */
    public CertificatesInner certificates() {
        return this.certificates;
    }

    /**
     * The PrivateLinkResourcesInner object to access its operations.
     */
    private PrivateLinkResourcesInner privateLinkResources;

    /**
     * Gets the PrivateLinkResourcesInner object to access its operations.
     * @return the PrivateLinkResourcesInner object.
     */
    public PrivateLinkResourcesInner privateLinkResources() {
        return this.privateLinkResources;
    }

    /**
     * The PrivateEndpointConnectionsInner object to access its operations.
     */
    private PrivateEndpointConnectionsInner privateEndpointConnections;

    /**
     * Gets the PrivateEndpointConnectionsInner object to access its operations.
     * @return the PrivateEndpointConnectionsInner object.
     */
    public PrivateEndpointConnectionsInner privateEndpointConnections() {
        return this.privateEndpointConnections;
    }

    /**
     * The PoolsInner object to access its operations.
     */
    private PoolsInner pools;

    /**
     * Gets the PoolsInner object to access its operations.
     * @return the PoolsInner object.
     */
    public PoolsInner pools() {
        return this.pools;
    }

    /**
     * Initializes an instance of BatchManagementClient client.
     *
     * @param credentials the management credentials for Azure
     */
    public BatchManagementClientImpl(ServiceClientCredentials credentials) {
        this("https://management.azure.com", credentials);
    }

    /**
     * Initializes an instance of BatchManagementClient client.
     *
     * @param baseUrl the base URL of the host
     * @param credentials the management credentials for Azure
     */
    public BatchManagementClientImpl(String baseUrl, ServiceClientCredentials credentials) {
        super(baseUrl, credentials);
        initialize();
    }

    /**
     * Initializes an instance of BatchManagementClient client.
     *
     * @param restClient the REST client to connect to Azure.
     */
    public BatchManagementClientImpl(RestClient restClient) {
        super(restClient);
        initialize();
    }

    protected void initialize() {
        this.apiVersion = "2021-06-01";
        this.acceptLanguage = "en-US";
        this.longRunningOperationRetryTimeout = 30;
        this.generateClientRequestId = true;
        this.batchAccounts = new BatchAccountsInner(restClient().retrofit(), this);
        this.applicationPackages = new ApplicationPackagesInner(restClient().retrofit(), this);
        this.applications = new ApplicationsInner(restClient().retrofit(), this);
        this.locations = new LocationsInner(restClient().retrofit(), this);
        this.operations = new OperationsInner(restClient().retrofit(), this);
        this.certificates = new CertificatesInner(restClient().retrofit(), this);
        this.privateLinkResources = new PrivateLinkResourcesInner(restClient().retrofit(), this);
        this.privateEndpointConnections = new PrivateEndpointConnectionsInner(restClient().retrofit(), this);
        this.pools = new PoolsInner(restClient().retrofit(), this);
        this.azureClient = new AzureClient(this);
    }

    /**
     * Gets the User-Agent header for the client.
     *
     * @return the user agent string.
     */
    @Override
    public String userAgent() {
        return String.format("%s (%s, %s)", super.userAgent(), "BatchManagementClient", "2021-06-01");
    }
}
