/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.AzureClient;
import com.microsoft.azure.AzureServiceClient;
import com.microsoft.azure.RestClient;
import com.microsoft.rest.credentials.ServiceClientCredentials;

/**
 * Initializes a new instance of the SqlManagementClientImpl class.
 */
public final class SqlManagementClientImpl extends AzureServiceClient {
    /** the {@link AzureClient} used for long running operations. */
    private AzureClient azureClient;

    /**
     * Gets the {@link AzureClient} used for long running operations.
     * @return the azure client;
     */
    public AzureClient getAzureClient() {
        return this.azureClient;
    }

    /** The subscription credentials which uniquely identify Microsoft Azure subscription. */
    private String subscriptionId;

    /**
     * Gets The subscription credentials which uniquely identify Microsoft Azure subscription.
     *
     * @return the subscriptionId value.
     */
    public String subscriptionId() {
        return this.subscriptionId;
    }

    /**
     * Sets The subscription credentials which uniquely identify Microsoft Azure subscription.
     *
     * @param subscriptionId the subscriptionId value.
     * @return the service client itself
     */
    public SqlManagementClientImpl withSubscriptionId(String subscriptionId) {
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
    public SqlManagementClientImpl withAcceptLanguage(String acceptLanguage) {
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
    public SqlManagementClientImpl withLongRunningOperationRetryTimeout(int longRunningOperationRetryTimeout) {
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
    public SqlManagementClientImpl withGenerateClientRequestId(boolean generateClientRequestId) {
        this.generateClientRequestId = generateClientRequestId;
        return this;
    }

    /**
     * The DatabasesInner object to access its operations.
     */
    private DatabasesInner databases;

    /**
     * Gets the DatabasesInner object to access its operations.
     * @return the DatabasesInner object.
     */
    public DatabasesInner databases() {
        return this.databases;
    }

    /**
     * The DatabaseAdvisorsInner object to access its operations.
     */
    private DatabaseAdvisorsInner databaseAdvisors;

    /**
     * Gets the DatabaseAdvisorsInner object to access its operations.
     * @return the DatabaseAdvisorsInner object.
     */
    public DatabaseAdvisorsInner databaseAdvisors() {
        return this.databaseAdvisors;
    }

    /**
     * The DatabaseRecommendedActionsInner object to access its operations.
     */
    private DatabaseRecommendedActionsInner databaseRecommendedActions;

    /**
     * Gets the DatabaseRecommendedActionsInner object to access its operations.
     * @return the DatabaseRecommendedActionsInner object.
     */
    public DatabaseRecommendedActionsInner databaseRecommendedActions() {
        return this.databaseRecommendedActions;
    }

    /**
     * The ElasticPoolAdvisorsInner object to access its operations.
     */
    private ElasticPoolAdvisorsInner elasticPoolAdvisors;

    /**
     * Gets the ElasticPoolAdvisorsInner object to access its operations.
     * @return the ElasticPoolAdvisorsInner object.
     */
    public ElasticPoolAdvisorsInner elasticPoolAdvisors() {
        return this.elasticPoolAdvisors;
    }

    /**
     * The ElasticPoolRecommendedActionsInner object to access its operations.
     */
    private ElasticPoolRecommendedActionsInner elasticPoolRecommendedActions;

    /**
     * Gets the ElasticPoolRecommendedActionsInner object to access its operations.
     * @return the ElasticPoolRecommendedActionsInner object.
     */
    public ElasticPoolRecommendedActionsInner elasticPoolRecommendedActions() {
        return this.elasticPoolRecommendedActions;
    }

    /**
     * The ServerAdvisorsInner object to access its operations.
     */
    private ServerAdvisorsInner serverAdvisors;

    /**
     * Gets the ServerAdvisorsInner object to access its operations.
     * @return the ServerAdvisorsInner object.
     */
    public ServerAdvisorsInner serverAdvisors() {
        return this.serverAdvisors;
    }

    /**
     * The ServerRecommendedActionsInner object to access its operations.
     */
    private ServerRecommendedActionsInner serverRecommendedActions;

    /**
     * Gets the ServerRecommendedActionsInner object to access its operations.
     * @return the ServerRecommendedActionsInner object.
     */
    public ServerRecommendedActionsInner serverRecommendedActions() {
        return this.serverRecommendedActions;
    }

    /**
     * The ServersInner object to access its operations.
     */
    private ServersInner servers;

    /**
     * Gets the ServersInner object to access its operations.
     * @return the ServersInner object.
     */
    public ServersInner servers() {
        return this.servers;
    }

    /**
     * The ServerUpgradesInner object to access its operations.
     */
    private ServerUpgradesInner serverUpgrades;

    /**
     * Gets the ServerUpgradesInner object to access its operations.
     * @return the ServerUpgradesInner object.
     */
    public ServerUpgradesInner serverUpgrades() {
        return this.serverUpgrades;
    }

    /**
     * The ServerUsagesInner object to access its operations.
     */
    private ServerUsagesInner serverUsages;

    /**
     * Gets the ServerUsagesInner object to access its operations.
     * @return the ServerUsagesInner object.
     */
    public ServerUsagesInner serverUsages() {
        return this.serverUsages;
    }

    /**
     * The ElasticPoolsInner object to access its operations.
     */
    private ElasticPoolsInner elasticPools;

    /**
     * Gets the ElasticPoolsInner object to access its operations.
     * @return the ElasticPoolsInner object.
     */
    public ElasticPoolsInner elasticPools() {
        return this.elasticPools;
    }

    /**
     * The ElasticPoolsDatabaseActivitysInner object to access its operations.
     */
    private ElasticPoolsDatabaseActivitysInner elasticPoolsDatabaseActivitys;

    /**
     * Gets the ElasticPoolsDatabaseActivitysInner object to access its operations.
     * @return the ElasticPoolsDatabaseActivitysInner object.
     */
    public ElasticPoolsDatabaseActivitysInner elasticPoolsDatabaseActivitys() {
        return this.elasticPoolsDatabaseActivitys;
    }

    /**
     * The RecommendedElasticPoolsInner object to access its operations.
     */
    private RecommendedElasticPoolsInner recommendedElasticPools;

    /**
     * Gets the RecommendedElasticPoolsInner object to access its operations.
     * @return the RecommendedElasticPoolsInner object.
     */
    public RecommendedElasticPoolsInner recommendedElasticPools() {
        return this.recommendedElasticPools;
    }

    /**
     * The AuditingPoliciesInner object to access its operations.
     */
    private AuditingPoliciesInner auditingPolicies;

    /**
     * Gets the AuditingPoliciesInner object to access its operations.
     * @return the AuditingPoliciesInner object.
     */
    public AuditingPoliciesInner auditingPolicies() {
        return this.auditingPolicies;
    }

    /**
     * The DataMaskingsInner object to access its operations.
     */
    private DataMaskingsInner dataMaskings;

    /**
     * Gets the DataMaskingsInner object to access its operations.
     * @return the DataMaskingsInner object.
     */
    public DataMaskingsInner dataMaskings() {
        return this.dataMaskings;
    }

    /**
     * The CapabilitiesInner object to access its operations.
     */
    private CapabilitiesInner capabilities;

    /**
     * Gets the CapabilitiesInner object to access its operations.
     * @return the CapabilitiesInner object.
     */
    public CapabilitiesInner capabilities() {
        return this.capabilities;
    }

    /**
     * Initializes an instance of SqlManagementClient client.
     *
     * @param credentials the management credentials for Azure
     */
    public SqlManagementClientImpl(ServiceClientCredentials credentials) {
        this("https://management.azure.com", credentials);
    }

    /**
     * Initializes an instance of SqlManagementClient client.
     *
     * @param baseUrl the base URL of the host
     * @param credentials the management credentials for Azure
     */
    public SqlManagementClientImpl(String baseUrl, ServiceClientCredentials credentials) {
        this(new RestClient.Builder()
                .withBaseUrl(baseUrl)
                .withCredentials(credentials)
                .build());
    }

    /**
     * Initializes an instance of SqlManagementClient client.
     *
     * @param restClient the REST client to connect to Azure.
     */
    public SqlManagementClientImpl(RestClient restClient) {
        super(restClient);
        initialize();
    }

    protected void initialize() {
        this.acceptLanguage = "en-US";
        this.longRunningOperationRetryTimeout = 30;
        this.generateClientRequestId = true;
        this.databases = new DatabasesInner(restClient().retrofit(), this);
        this.databaseAdvisors = new DatabaseAdvisorsInner(restClient().retrofit(), this);
        this.databaseRecommendedActions = new DatabaseRecommendedActionsInner(restClient().retrofit(), this);
        this.elasticPoolAdvisors = new ElasticPoolAdvisorsInner(restClient().retrofit(), this);
        this.elasticPoolRecommendedActions = new ElasticPoolRecommendedActionsInner(restClient().retrofit(), this);
        this.serverAdvisors = new ServerAdvisorsInner(restClient().retrofit(), this);
        this.serverRecommendedActions = new ServerRecommendedActionsInner(restClient().retrofit(), this);
        this.servers = new ServersInner(restClient().retrofit(), this);
        this.serverUpgrades = new ServerUpgradesInner(restClient().retrofit(), this);
        this.serverUsages = new ServerUsagesInner(restClient().retrofit(), this);
        this.elasticPools = new ElasticPoolsInner(restClient().retrofit(), this);
        this.elasticPoolsDatabaseActivitys = new ElasticPoolsDatabaseActivitysInner(restClient().retrofit(), this);
        this.recommendedElasticPools = new RecommendedElasticPoolsInner(restClient().retrofit(), this);
        this.auditingPolicies = new AuditingPoliciesInner(restClient().retrofit(), this);
        this.dataMaskings = new DataMaskingsInner(restClient().retrofit(), this);
        this.capabilities = new CapabilitiesInner(restClient().retrofit(), this);
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
                "SqlManagementClient, ");
    }
}
