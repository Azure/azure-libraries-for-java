/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.sql.implementation;

import com.microsoft.azure.v2.AzureEnvironment;
import com.microsoft.azure.v2.AzureProxy;
import com.microsoft.azure.v2.AzureServiceClient;
import com.microsoft.rest.v2.credentials.ServiceClientCredentials;
import com.microsoft.rest.v2.http.HttpPipeline;
import io.reactivex.annotations.NonNull;

/**
 * Initializes a new instance of the SqlManagementClientImpl type.
 */
public final class SqlManagementClientImpl extends AzureServiceClient {
    /**
     * The subscription ID that identifies an Azure subscription.
     */
    private String subscriptionId;

    /**
     * Gets The subscription ID that identifies an Azure subscription.
     *
     * @return the subscriptionId value.
     */
    public String subscriptionId() {
        return this.subscriptionId;
    }

    /**
     * Sets The subscription ID that identifies an Azure subscription.
     *
     * @param subscriptionId the subscriptionId value.
     * @return the service client itself.
     */
    public SqlManagementClientImpl withSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    /**
     * The preferred language for the response.
     */
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
     * @return the service client itself.
     */
    public SqlManagementClientImpl withAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
        return this;
    }

    /**
     * The retry timeout in seconds for Long Running Operations. Default value is 30.
     */
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
     * @return the service client itself.
     */
    public SqlManagementClientImpl withLongRunningOperationRetryTimeout(int longRunningOperationRetryTimeout) {
        this.longRunningOperationRetryTimeout = longRunningOperationRetryTimeout;
        return this;
    }

    /**
     * Whether a unique x-ms-client-request-id should be generated. When set to true a unique x-ms-client-request-id value is generated and included in each request. Default is true.
     */
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
     * @return the service client itself.
     */
    public SqlManagementClientImpl withGenerateClientRequestId(boolean generateClientRequestId) {
        this.generateClientRequestId = generateClientRequestId;
        return this;
    }

    /**
     * The BackupLongTermRetentionPoliciesInner object to access its operations.
     */
    private BackupLongTermRetentionPoliciesInner backupLongTermRetentionPolicies;

    /**
     * Gets the BackupLongTermRetentionPoliciesInner object to access its operations.
     *
     * @return the BackupLongTermRetentionPoliciesInner object.
     */
    public BackupLongTermRetentionPoliciesInner backupLongTermRetentionPolicies() {
        return this.backupLongTermRetentionPolicies;
    }

    /**
     * The BackupLongTermRetentionVaultsInner object to access its operations.
     */
    private BackupLongTermRetentionVaultsInner backupLongTermRetentionVaults;

    /**
     * Gets the BackupLongTermRetentionVaultsInner object to access its operations.
     *
     * @return the BackupLongTermRetentionVaultsInner object.
     */
    public BackupLongTermRetentionVaultsInner backupLongTermRetentionVaults() {
        return this.backupLongTermRetentionVaults;
    }

    /**
     * The RecoverableDatabasesInner object to access its operations.
     */
    private RecoverableDatabasesInner recoverableDatabases;

    /**
     * Gets the RecoverableDatabasesInner object to access its operations.
     *
     * @return the RecoverableDatabasesInner object.
     */
    public RecoverableDatabasesInner recoverableDatabases() {
        return this.recoverableDatabases;
    }

    /**
     * The RestorableDroppedDatabasesInner object to access its operations.
     */
    private RestorableDroppedDatabasesInner restorableDroppedDatabases;

    /**
     * Gets the RestorableDroppedDatabasesInner object to access its operations.
     *
     * @return the RestorableDroppedDatabasesInner object.
     */
    public RestorableDroppedDatabasesInner restorableDroppedDatabases() {
        return this.restorableDroppedDatabases;
    }

    /**
     * The CapabilitiesInner object to access its operations.
     */
    private CapabilitiesInner capabilities;

    /**
     * Gets the CapabilitiesInner object to access its operations.
     *
     * @return the CapabilitiesInner object.
     */
    public CapabilitiesInner capabilities() {
        return this.capabilities;
    }

    /**
     * The ServersInner object to access its operations.
     */
    private ServersInner servers;

    /**
     * Gets the ServersInner object to access its operations.
     *
     * @return the ServersInner object.
     */
    public ServersInner servers() {
        return this.servers;
    }

    /**
     * The ServerConnectionPoliciesInner object to access its operations.
     */
    private ServerConnectionPoliciesInner serverConnectionPolicies;

    /**
     * Gets the ServerConnectionPoliciesInner object to access its operations.
     *
     * @return the ServerConnectionPoliciesInner object.
     */
    public ServerConnectionPoliciesInner serverConnectionPolicies() {
        return this.serverConnectionPolicies;
    }

    /**
     * The DatabasesInner object to access its operations.
     */
    private DatabasesInner databases;

    /**
     * Gets the DatabasesInner object to access its operations.
     *
     * @return the DatabasesInner object.
     */
    public DatabasesInner databases() {
        return this.databases;
    }

    /**
     * The DatabaseThreatDetectionPoliciesInner object to access its operations.
     */
    private DatabaseThreatDetectionPoliciesInner databaseThreatDetectionPolicies;

    /**
     * Gets the DatabaseThreatDetectionPoliciesInner object to access its operations.
     *
     * @return the DatabaseThreatDetectionPoliciesInner object.
     */
    public DatabaseThreatDetectionPoliciesInner databaseThreatDetectionPolicies() {
        return this.databaseThreatDetectionPolicies;
    }

    /**
     * The DataMaskingPoliciesInner object to access its operations.
     */
    private DataMaskingPoliciesInner dataMaskingPolicies;

    /**
     * Gets the DataMaskingPoliciesInner object to access its operations.
     *
     * @return the DataMaskingPoliciesInner object.
     */
    public DataMaskingPoliciesInner dataMaskingPolicies() {
        return this.dataMaskingPolicies;
    }

    /**
     * The DataMaskingRulesInner object to access its operations.
     */
    private DataMaskingRulesInner dataMaskingRules;

    /**
     * Gets the DataMaskingRulesInner object to access its operations.
     *
     * @return the DataMaskingRulesInner object.
     */
    public DataMaskingRulesInner dataMaskingRules() {
        return this.dataMaskingRules;
    }

    /**
     * The ElasticPoolsInner object to access its operations.
     */
    private ElasticPoolsInner elasticPools;

    /**
     * Gets the ElasticPoolsInner object to access its operations.
     *
     * @return the ElasticPoolsInner object.
     */
    public ElasticPoolsInner elasticPools() {
        return this.elasticPools;
    }

    /**
     * The FirewallRulesInner object to access its operations.
     */
    private FirewallRulesInner firewallRules;

    /**
     * Gets the FirewallRulesInner object to access its operations.
     *
     * @return the FirewallRulesInner object.
     */
    public FirewallRulesInner firewallRules() {
        return this.firewallRules;
    }

    /**
     * The GeoBackupPoliciesInner object to access its operations.
     */
    private GeoBackupPoliciesInner geoBackupPolicies;

    /**
     * Gets the GeoBackupPoliciesInner object to access its operations.
     *
     * @return the GeoBackupPoliciesInner object.
     */
    public GeoBackupPoliciesInner geoBackupPolicies() {
        return this.geoBackupPolicies;
    }

    /**
     * The RecommendedElasticPoolsInner object to access its operations.
     */
    private RecommendedElasticPoolsInner recommendedElasticPools;

    /**
     * Gets the RecommendedElasticPoolsInner object to access its operations.
     *
     * @return the RecommendedElasticPoolsInner object.
     */
    public RecommendedElasticPoolsInner recommendedElasticPools() {
        return this.recommendedElasticPools;
    }

    /**
     * The ReplicationLinksInner object to access its operations.
     */
    private ReplicationLinksInner replicationLinks;

    /**
     * Gets the ReplicationLinksInner object to access its operations.
     *
     * @return the ReplicationLinksInner object.
     */
    public ReplicationLinksInner replicationLinks() {
        return this.replicationLinks;
    }

    /**
     * The ServerAzureADAdministratorsInner object to access its operations.
     */
    private ServerAzureADAdministratorsInner serverAzureADAdministrators;

    /**
     * Gets the ServerAzureADAdministratorsInner object to access its operations.
     *
     * @return the ServerAzureADAdministratorsInner object.
     */
    public ServerAzureADAdministratorsInner serverAzureADAdministrators() {
        return this.serverAzureADAdministrators;
    }

    /**
     * The ServerCommunicationLinksInner object to access its operations.
     */
    private ServerCommunicationLinksInner serverCommunicationLinks;

    /**
     * Gets the ServerCommunicationLinksInner object to access its operations.
     *
     * @return the ServerCommunicationLinksInner object.
     */
    public ServerCommunicationLinksInner serverCommunicationLinks() {
        return this.serverCommunicationLinks;
    }

    /**
     * The ServiceObjectivesInner object to access its operations.
     */
    private ServiceObjectivesInner serviceObjectives;

    /**
     * Gets the ServiceObjectivesInner object to access its operations.
     *
     * @return the ServiceObjectivesInner object.
     */
    public ServiceObjectivesInner serviceObjectives() {
        return this.serviceObjectives;
    }

    /**
     * The ElasticPoolActivitiesInner object to access its operations.
     */
    private ElasticPoolActivitiesInner elasticPoolActivities;

    /**
     * Gets the ElasticPoolActivitiesInner object to access its operations.
     *
     * @return the ElasticPoolActivitiesInner object.
     */
    public ElasticPoolActivitiesInner elasticPoolActivities() {
        return this.elasticPoolActivities;
    }

    /**
     * The ElasticPoolDatabaseActivitiesInner object to access its operations.
     */
    private ElasticPoolDatabaseActivitiesInner elasticPoolDatabaseActivities;

    /**
     * Gets the ElasticPoolDatabaseActivitiesInner object to access its operations.
     *
     * @return the ElasticPoolDatabaseActivitiesInner object.
     */
    public ElasticPoolDatabaseActivitiesInner elasticPoolDatabaseActivities() {
        return this.elasticPoolDatabaseActivities;
    }

    /**
     * The ServiceTierAdvisorsInner object to access its operations.
     */
    private ServiceTierAdvisorsInner serviceTierAdvisors;

    /**
     * Gets the ServiceTierAdvisorsInner object to access its operations.
     *
     * @return the ServiceTierAdvisorsInner object.
     */
    public ServiceTierAdvisorsInner serviceTierAdvisors() {
        return this.serviceTierAdvisors;
    }

    /**
     * The TransparentDataEncryptionsInner object to access its operations.
     */
    private TransparentDataEncryptionsInner transparentDataEncryptions;

    /**
     * Gets the TransparentDataEncryptionsInner object to access its operations.
     *
     * @return the TransparentDataEncryptionsInner object.
     */
    public TransparentDataEncryptionsInner transparentDataEncryptions() {
        return this.transparentDataEncryptions;
    }

    /**
     * The TransparentDataEncryptionActivitiesInner object to access its operations.
     */
    private TransparentDataEncryptionActivitiesInner transparentDataEncryptionActivities;

    /**
     * Gets the TransparentDataEncryptionActivitiesInner object to access its operations.
     *
     * @return the TransparentDataEncryptionActivitiesInner object.
     */
    public TransparentDataEncryptionActivitiesInner transparentDataEncryptionActivities() {
        return this.transparentDataEncryptionActivities;
    }

    /**
     * The ServerUsagesInner object to access its operations.
     */
    private ServerUsagesInner serverUsages;

    /**
     * Gets the ServerUsagesInner object to access its operations.
     *
     * @return the ServerUsagesInner object.
     */
    public ServerUsagesInner serverUsages() {
        return this.serverUsages;
    }

    /**
     * The DatabaseUsagesInner object to access its operations.
     */
    private DatabaseUsagesInner databaseUsages;

    /**
     * Gets the DatabaseUsagesInner object to access its operations.
     *
     * @return the DatabaseUsagesInner object.
     */
    public DatabaseUsagesInner databaseUsages() {
        return this.databaseUsages;
    }

    /**
     * The DatabaseAutomaticTuningsInner object to access its operations.
     */
    private DatabaseAutomaticTuningsInner databaseAutomaticTunings;

    /**
     * Gets the DatabaseAutomaticTuningsInner object to access its operations.
     *
     * @return the DatabaseAutomaticTuningsInner object.
     */
    public DatabaseAutomaticTuningsInner databaseAutomaticTunings() {
        return this.databaseAutomaticTunings;
    }

    /**
     * The EncryptionProtectorsInner object to access its operations.
     */
    private EncryptionProtectorsInner encryptionProtectors;

    /**
     * Gets the EncryptionProtectorsInner object to access its operations.
     *
     * @return the EncryptionProtectorsInner object.
     */
    public EncryptionProtectorsInner encryptionProtectors() {
        return this.encryptionProtectors;
    }

    /**
     * The FailoverGroupsInner object to access its operations.
     */
    private FailoverGroupsInner failoverGroups;

    /**
     * Gets the FailoverGroupsInner object to access its operations.
     *
     * @return the FailoverGroupsInner object.
     */
    public FailoverGroupsInner failoverGroups() {
        return this.failoverGroups;
    }

    /**
     * The ManagedInstancesInner object to access its operations.
     */
    private ManagedInstancesInner managedInstances;

    /**
     * Gets the ManagedInstancesInner object to access its operations.
     *
     * @return the ManagedInstancesInner object.
     */
    public ManagedInstancesInner managedInstances() {
        return this.managedInstances;
    }

    /**
     * The OperationsInner object to access its operations.
     */
    private OperationsInner operations;

    /**
     * Gets the OperationsInner object to access its operations.
     *
     * @return the OperationsInner object.
     */
    public OperationsInner operations() {
        return this.operations;
    }

    /**
     * The ServerKeysInner object to access its operations.
     */
    private ServerKeysInner serverKeys;

    /**
     * Gets the ServerKeysInner object to access its operations.
     *
     * @return the ServerKeysInner object.
     */
    public ServerKeysInner serverKeys() {
        return this.serverKeys;
    }

    /**
     * The SyncAgentsInner object to access its operations.
     */
    private SyncAgentsInner syncAgents;

    /**
     * Gets the SyncAgentsInner object to access its operations.
     *
     * @return the SyncAgentsInner object.
     */
    public SyncAgentsInner syncAgents() {
        return this.syncAgents;
    }

    /**
     * The SyncGroupsInner object to access its operations.
     */
    private SyncGroupsInner syncGroups;

    /**
     * Gets the SyncGroupsInner object to access its operations.
     *
     * @return the SyncGroupsInner object.
     */
    public SyncGroupsInner syncGroups() {
        return this.syncGroups;
    }

    /**
     * The SyncMembersInner object to access its operations.
     */
    private SyncMembersInner syncMembers;

    /**
     * Gets the SyncMembersInner object to access its operations.
     *
     * @return the SyncMembersInner object.
     */
    public SyncMembersInner syncMembers() {
        return this.syncMembers;
    }

    /**
     * The SubscriptionUsagesInner object to access its operations.
     */
    private SubscriptionUsagesInner subscriptionUsages;

    /**
     * Gets the SubscriptionUsagesInner object to access its operations.
     *
     * @return the SubscriptionUsagesInner object.
     */
    public SubscriptionUsagesInner subscriptionUsages() {
        return this.subscriptionUsages;
    }

    /**
     * The VirtualNetworkRulesInner object to access its operations.
     */
    private VirtualNetworkRulesInner virtualNetworkRules;

    /**
     * Gets the VirtualNetworkRulesInner object to access its operations.
     *
     * @return the VirtualNetworkRulesInner object.
     */
    public VirtualNetworkRulesInner virtualNetworkRules() {
        return this.virtualNetworkRules;
    }

    /**
     * The ExtendedDatabaseBlobAuditingPoliciesInner object to access its operations.
     */
    private ExtendedDatabaseBlobAuditingPoliciesInner extendedDatabaseBlobAuditingPolicies;

    /**
     * Gets the ExtendedDatabaseBlobAuditingPoliciesInner object to access its operations.
     *
     * @return the ExtendedDatabaseBlobAuditingPoliciesInner object.
     */
    public ExtendedDatabaseBlobAuditingPoliciesInner extendedDatabaseBlobAuditingPolicies() {
        return this.extendedDatabaseBlobAuditingPolicies;
    }

    /**
     * The ExtendedServerBlobAuditingPoliciesInner object to access its operations.
     */
    private ExtendedServerBlobAuditingPoliciesInner extendedServerBlobAuditingPolicies;

    /**
     * Gets the ExtendedServerBlobAuditingPoliciesInner object to access its operations.
     *
     * @return the ExtendedServerBlobAuditingPoliciesInner object.
     */
    public ExtendedServerBlobAuditingPoliciesInner extendedServerBlobAuditingPolicies() {
        return this.extendedServerBlobAuditingPolicies;
    }

    /**
     * The ServerBlobAuditingPoliciesInner object to access its operations.
     */
    private ServerBlobAuditingPoliciesInner serverBlobAuditingPolicies;

    /**
     * Gets the ServerBlobAuditingPoliciesInner object to access its operations.
     *
     * @return the ServerBlobAuditingPoliciesInner object.
     */
    public ServerBlobAuditingPoliciesInner serverBlobAuditingPolicies() {
        return this.serverBlobAuditingPolicies;
    }

    /**
     * The DatabaseBlobAuditingPoliciesInner object to access its operations.
     */
    private DatabaseBlobAuditingPoliciesInner databaseBlobAuditingPolicies;

    /**
     * Gets the DatabaseBlobAuditingPoliciesInner object to access its operations.
     *
     * @return the DatabaseBlobAuditingPoliciesInner object.
     */
    public DatabaseBlobAuditingPoliciesInner databaseBlobAuditingPolicies() {
        return this.databaseBlobAuditingPolicies;
    }

    /**
     * The DatabaseVulnerabilityAssessmentRuleBaselinesInner object to access its operations.
     */
    private DatabaseVulnerabilityAssessmentRuleBaselinesInner databaseVulnerabilityAssessmentRuleBaselines;

    /**
     * Gets the DatabaseVulnerabilityAssessmentRuleBaselinesInner object to access its operations.
     *
     * @return the DatabaseVulnerabilityAssessmentRuleBaselinesInner object.
     */
    public DatabaseVulnerabilityAssessmentRuleBaselinesInner databaseVulnerabilityAssessmentRuleBaselines() {
        return this.databaseVulnerabilityAssessmentRuleBaselines;
    }

    /**
     * The DatabaseVulnerabilityAssessmentsInner object to access its operations.
     */
    private DatabaseVulnerabilityAssessmentsInner databaseVulnerabilityAssessments;

    /**
     * Gets the DatabaseVulnerabilityAssessmentsInner object to access its operations.
     *
     * @return the DatabaseVulnerabilityAssessmentsInner object.
     */
    public DatabaseVulnerabilityAssessmentsInner databaseVulnerabilityAssessments() {
        return this.databaseVulnerabilityAssessments;
    }

    /**
     * The JobAgentsInner object to access its operations.
     */
    private JobAgentsInner jobAgents;

    /**
     * Gets the JobAgentsInner object to access its operations.
     *
     * @return the JobAgentsInner object.
     */
    public JobAgentsInner jobAgents() {
        return this.jobAgents;
    }

    /**
     * The JobCredentialsInner object to access its operations.
     */
    private JobCredentialsInner jobCredentials;

    /**
     * Gets the JobCredentialsInner object to access its operations.
     *
     * @return the JobCredentialsInner object.
     */
    public JobCredentialsInner jobCredentials() {
        return this.jobCredentials;
    }

    /**
     * The JobExecutionsInner object to access its operations.
     */
    private JobExecutionsInner jobExecutions;

    /**
     * Gets the JobExecutionsInner object to access its operations.
     *
     * @return the JobExecutionsInner object.
     */
    public JobExecutionsInner jobExecutions() {
        return this.jobExecutions;
    }

    /**
     * The JobsInner object to access its operations.
     */
    private JobsInner jobs;

    /**
     * Gets the JobsInner object to access its operations.
     *
     * @return the JobsInner object.
     */
    public JobsInner jobs() {
        return this.jobs;
    }

    /**
     * The JobStepExecutionsInner object to access its operations.
     */
    private JobStepExecutionsInner jobStepExecutions;

    /**
     * Gets the JobStepExecutionsInner object to access its operations.
     *
     * @return the JobStepExecutionsInner object.
     */
    public JobStepExecutionsInner jobStepExecutions() {
        return this.jobStepExecutions;
    }

    /**
     * The JobStepsInner object to access its operations.
     */
    private JobStepsInner jobSteps;

    /**
     * Gets the JobStepsInner object to access its operations.
     *
     * @return the JobStepsInner object.
     */
    public JobStepsInner jobSteps() {
        return this.jobSteps;
    }

    /**
     * The JobTargetExecutionsInner object to access its operations.
     */
    private JobTargetExecutionsInner jobTargetExecutions;

    /**
     * Gets the JobTargetExecutionsInner object to access its operations.
     *
     * @return the JobTargetExecutionsInner object.
     */
    public JobTargetExecutionsInner jobTargetExecutions() {
        return this.jobTargetExecutions;
    }

    /**
     * The JobTargetGroupsInner object to access its operations.
     */
    private JobTargetGroupsInner jobTargetGroups;

    /**
     * Gets the JobTargetGroupsInner object to access its operations.
     *
     * @return the JobTargetGroupsInner object.
     */
    public JobTargetGroupsInner jobTargetGroups() {
        return this.jobTargetGroups;
    }

    /**
     * The JobVersionsInner object to access its operations.
     */
    private JobVersionsInner jobVersions;

    /**
     * Gets the JobVersionsInner object to access its operations.
     *
     * @return the JobVersionsInner object.
     */
    public JobVersionsInner jobVersions() {
        return this.jobVersions;
    }

    /**
     * The ManagedDatabasesInner object to access its operations.
     */
    private ManagedDatabasesInner managedDatabases;

    /**
     * Gets the ManagedDatabasesInner object to access its operations.
     *
     * @return the ManagedDatabasesInner object.
     */
    public ManagedDatabasesInner managedDatabases() {
        return this.managedDatabases;
    }

    /**
     * The SensitivityLabelsInner object to access its operations.
     */
    private SensitivityLabelsInner sensitivityLabels;

    /**
     * Gets the SensitivityLabelsInner object to access its operations.
     *
     * @return the SensitivityLabelsInner object.
     */
    public SensitivityLabelsInner sensitivityLabels() {
        return this.sensitivityLabels;
    }

    /**
     * The ServerAutomaticTuningsInner object to access its operations.
     */
    private ServerAutomaticTuningsInner serverAutomaticTunings;

    /**
     * Gets the ServerAutomaticTuningsInner object to access its operations.
     *
     * @return the ServerAutomaticTuningsInner object.
     */
    public ServerAutomaticTuningsInner serverAutomaticTunings() {
        return this.serverAutomaticTunings;
    }

    /**
     * The ServerDnsAliasesInner object to access its operations.
     */
    private ServerDnsAliasesInner serverDnsAliases;

    /**
     * Gets the ServerDnsAliasesInner object to access its operations.
     *
     * @return the ServerDnsAliasesInner object.
     */
    public ServerDnsAliasesInner serverDnsAliases() {
        return this.serverDnsAliases;
    }

    /**
     * The ServerSecurityAlertPoliciesInner object to access its operations.
     */
    private ServerSecurityAlertPoliciesInner serverSecurityAlertPolicies;

    /**
     * Gets the ServerSecurityAlertPoliciesInner object to access its operations.
     *
     * @return the ServerSecurityAlertPoliciesInner object.
     */
    public ServerSecurityAlertPoliciesInner serverSecurityAlertPolicies() {
        return this.serverSecurityAlertPolicies;
    }

    /**
     * The RestorePointsInner object to access its operations.
     */
    private RestorePointsInner restorePoints;

    /**
     * Gets the RestorePointsInner object to access its operations.
     *
     * @return the RestorePointsInner object.
     */
    public RestorePointsInner restorePoints() {
        return this.restorePoints;
    }

    /**
     * The DatabaseOperationsInner object to access its operations.
     */
    private DatabaseOperationsInner databaseOperations;

    /**
     * Gets the DatabaseOperationsInner object to access its operations.
     *
     * @return the DatabaseOperationsInner object.
     */
    public DatabaseOperationsInner databaseOperations() {
        return this.databaseOperations;
    }

    /**
     * The ElasticPoolOperationsInner object to access its operations.
     */
    private ElasticPoolOperationsInner elasticPoolOperations;

    /**
     * Gets the ElasticPoolOperationsInner object to access its operations.
     *
     * @return the ElasticPoolOperationsInner object.
     */
    public ElasticPoolOperationsInner elasticPoolOperations() {
        return this.elasticPoolOperations;
    }

    /**
     * The DatabaseVulnerabilityAssessmentScansInner object to access its operations.
     */
    private DatabaseVulnerabilityAssessmentScansInner databaseVulnerabilityAssessmentScans;

    /**
     * Gets the DatabaseVulnerabilityAssessmentScansInner object to access its operations.
     *
     * @return the DatabaseVulnerabilityAssessmentScansInner object.
     */
    public DatabaseVulnerabilityAssessmentScansInner databaseVulnerabilityAssessmentScans() {
        return this.databaseVulnerabilityAssessmentScans;
    }

    /**
     * The InstanceFailoverGroupsInner object to access its operations.
     */
    private InstanceFailoverGroupsInner instanceFailoverGroups;

    /**
     * Gets the InstanceFailoverGroupsInner object to access its operations.
     *
     * @return the InstanceFailoverGroupsInner object.
     */
    public InstanceFailoverGroupsInner instanceFailoverGroups() {
        return this.instanceFailoverGroups;
    }

    /**
     * The BackupShortTermRetentionPoliciesInner object to access its operations.
     */
    private BackupShortTermRetentionPoliciesInner backupShortTermRetentionPolicies;

    /**
     * Gets the BackupShortTermRetentionPoliciesInner object to access its operations.
     *
     * @return the BackupShortTermRetentionPoliciesInner object.
     */
    public BackupShortTermRetentionPoliciesInner backupShortTermRetentionPolicies() {
        return this.backupShortTermRetentionPolicies;
    }

    /**
     * The TdeCertificatesInner object to access its operations.
     */
    private TdeCertificatesInner tdeCertificates;

    /**
     * Gets the TdeCertificatesInner object to access its operations.
     *
     * @return the TdeCertificatesInner object.
     */
    public TdeCertificatesInner tdeCertificates() {
        return this.tdeCertificates;
    }

    /**
     * The ManagedInstanceTdeCertificatesInner object to access its operations.
     */
    private ManagedInstanceTdeCertificatesInner managedInstanceTdeCertificates;

    /**
     * Gets the ManagedInstanceTdeCertificatesInner object to access its operations.
     *
     * @return the ManagedInstanceTdeCertificatesInner object.
     */
    public ManagedInstanceTdeCertificatesInner managedInstanceTdeCertificates() {
        return this.managedInstanceTdeCertificates;
    }

    /**
     * The ManagedInstanceKeysInner object to access its operations.
     */
    private ManagedInstanceKeysInner managedInstanceKeys;

    /**
     * Gets the ManagedInstanceKeysInner object to access its operations.
     *
     * @return the ManagedInstanceKeysInner object.
     */
    public ManagedInstanceKeysInner managedInstanceKeys() {
        return this.managedInstanceKeys;
    }

    /**
     * The ManagedInstanceEncryptionProtectorsInner object to access its operations.
     */
    private ManagedInstanceEncryptionProtectorsInner managedInstanceEncryptionProtectors;

    /**
     * Gets the ManagedInstanceEncryptionProtectorsInner object to access its operations.
     *
     * @return the ManagedInstanceEncryptionProtectorsInner object.
     */
    public ManagedInstanceEncryptionProtectorsInner managedInstanceEncryptionProtectors() {
        return this.managedInstanceEncryptionProtectors;
    }

    /**
     * Initializes an instance of SqlManagementClient client.
     *
     * @param credentials the management credentials for Azure.
     */
    public SqlManagementClientImpl(@NonNull ServiceClientCredentials credentials) {
        this(AzureProxy.createDefaultPipeline(SqlManagementClientImpl.class, credentials));
    }

    /**
     * Initializes an instance of SqlManagementClient client.
     *
     * @param credentials the management credentials for Azure.
     * @param azureEnvironment The environment that requests will target.
     */
    public SqlManagementClientImpl(@NonNull ServiceClientCredentials credentials, @NonNull AzureEnvironment azureEnvironment) {
        this(AzureProxy.createDefaultPipeline(SqlManagementClientImpl.class, credentials), azureEnvironment);
    }

    /**
     * Initializes an instance of SqlManagementClient client.
     *
     * @param httpPipeline The HTTP pipeline to send requests through.
     */
    public SqlManagementClientImpl(@NonNull HttpPipeline httpPipeline) {
        this(httpPipeline, null);
    }

    /**
     * Initializes an instance of SqlManagementClient client.
     *
     * @param httpPipeline The HTTP pipeline to send requests through.
     * @param azureEnvironment The environment that requests will target.
     */
    public SqlManagementClientImpl(@NonNull HttpPipeline httpPipeline, @NonNull AzureEnvironment azureEnvironment) {
        super(httpPipeline, azureEnvironment);
        this.acceptLanguage = "en-US";
        this.longRunningOperationRetryTimeout = 30;
        this.generateClientRequestId = true;
        this.backupLongTermRetentionPolicies = new BackupLongTermRetentionPoliciesInner(this);
        this.backupLongTermRetentionVaults = new BackupLongTermRetentionVaultsInner(this);
        this.recoverableDatabases = new RecoverableDatabasesInner(this);
        this.restorableDroppedDatabases = new RestorableDroppedDatabasesInner(this);
        this.capabilities = new CapabilitiesInner(this);
        this.servers = new ServersInner(this);
        this.serverConnectionPolicies = new ServerConnectionPoliciesInner(this);
        this.databases = new DatabasesInner(this);
        this.databaseThreatDetectionPolicies = new DatabaseThreatDetectionPoliciesInner(this);
        this.dataMaskingPolicies = new DataMaskingPoliciesInner(this);
        this.dataMaskingRules = new DataMaskingRulesInner(this);
        this.elasticPools = new ElasticPoolsInner(this);
        this.firewallRules = new FirewallRulesInner(this);
        this.geoBackupPolicies = new GeoBackupPoliciesInner(this);
        this.recommendedElasticPools = new RecommendedElasticPoolsInner(this);
        this.replicationLinks = new ReplicationLinksInner(this);
        this.serverAzureADAdministrators = new ServerAzureADAdministratorsInner(this);
        this.serverCommunicationLinks = new ServerCommunicationLinksInner(this);
        this.serviceObjectives = new ServiceObjectivesInner(this);
        this.elasticPoolActivities = new ElasticPoolActivitiesInner(this);
        this.elasticPoolDatabaseActivities = new ElasticPoolDatabaseActivitiesInner(this);
        this.serviceTierAdvisors = new ServiceTierAdvisorsInner(this);
        this.transparentDataEncryptions = new TransparentDataEncryptionsInner(this);
        this.transparentDataEncryptionActivities = new TransparentDataEncryptionActivitiesInner(this);
        this.serverUsages = new ServerUsagesInner(this);
        this.databaseUsages = new DatabaseUsagesInner(this);
        this.databaseAutomaticTunings = new DatabaseAutomaticTuningsInner(this);
        this.encryptionProtectors = new EncryptionProtectorsInner(this);
        this.failoverGroups = new FailoverGroupsInner(this);
        this.managedInstances = new ManagedInstancesInner(this);
        this.operations = new OperationsInner(this);
        this.serverKeys = new ServerKeysInner(this);
        this.syncAgents = new SyncAgentsInner(this);
        this.syncGroups = new SyncGroupsInner(this);
        this.syncMembers = new SyncMembersInner(this);
        this.subscriptionUsages = new SubscriptionUsagesInner(this);
        this.virtualNetworkRules = new VirtualNetworkRulesInner(this);
        this.extendedDatabaseBlobAuditingPolicies = new ExtendedDatabaseBlobAuditingPoliciesInner(this);
        this.extendedServerBlobAuditingPolicies = new ExtendedServerBlobAuditingPoliciesInner(this);
        this.serverBlobAuditingPolicies = new ServerBlobAuditingPoliciesInner(this);
        this.databaseBlobAuditingPolicies = new DatabaseBlobAuditingPoliciesInner(this);
        this.databaseVulnerabilityAssessmentRuleBaselines = new DatabaseVulnerabilityAssessmentRuleBaselinesInner(this);
        this.databaseVulnerabilityAssessments = new DatabaseVulnerabilityAssessmentsInner(this);
        this.jobAgents = new JobAgentsInner(this);
        this.jobCredentials = new JobCredentialsInner(this);
        this.jobExecutions = new JobExecutionsInner(this);
        this.jobs = new JobsInner(this);
        this.jobStepExecutions = new JobStepExecutionsInner(this);
        this.jobSteps = new JobStepsInner(this);
        this.jobTargetExecutions = new JobTargetExecutionsInner(this);
        this.jobTargetGroups = new JobTargetGroupsInner(this);
        this.jobVersions = new JobVersionsInner(this);
        this.managedDatabases = new ManagedDatabasesInner(this);
        this.sensitivityLabels = new SensitivityLabelsInner(this);
        this.serverAutomaticTunings = new ServerAutomaticTuningsInner(this);
        this.serverDnsAliases = new ServerDnsAliasesInner(this);
        this.serverSecurityAlertPolicies = new ServerSecurityAlertPoliciesInner(this);
        this.restorePoints = new RestorePointsInner(this);
        this.databaseOperations = new DatabaseOperationsInner(this);
        this.elasticPoolOperations = new ElasticPoolOperationsInner(this);
        this.databaseVulnerabilityAssessmentScans = new DatabaseVulnerabilityAssessmentScansInner(this);
        this.instanceFailoverGroups = new InstanceFailoverGroupsInner(this);
        this.backupShortTermRetentionPolicies = new BackupShortTermRetentionPoliciesInner(this);
        this.tdeCertificates = new TdeCertificatesInner(this);
        this.managedInstanceTdeCertificates = new ManagedInstanceTdeCertificatesInner(this);
        this.managedInstanceKeys = new ManagedInstanceKeysInner(this);
        this.managedInstanceEncryptionProtectors = new ManagedInstanceEncryptionProtectorsInner(this);
    }
}