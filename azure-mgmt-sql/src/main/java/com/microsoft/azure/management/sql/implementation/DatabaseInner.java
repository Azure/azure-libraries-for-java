/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import org.joda.time.DateTime;
import java.util.UUID;
import com.microsoft.azure.management.sql.CreateMode;
import com.microsoft.azure.management.sql.DatabaseEdition;
import com.microsoft.azure.management.sql.ServiceObjectiveName;
import java.util.List;
import com.microsoft.azure.management.sql.RecommendedIndex;
import com.microsoft.azure.management.sql.ReadScale;
import com.microsoft.azure.management.sql.SampleName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.Resource;

/**
 * Represents a database.
 */
@JsonFlatten
public class DatabaseInner extends Resource {
    /**
     * Kind of database.  This is metadata used for the Azure portal
     * experience.
     */
    @JsonProperty(value = "kind", access = JsonProperty.Access.WRITE_ONLY)
    private String kind;

    /**
     * The collation of the database. If createMode is not Default, this value
     * is ignored.
     */
    @JsonProperty(value = "properties.collation")
    private String collation;

    /**
     * The creation date of the database (ISO8601 format).
     */
    @JsonProperty(value = "properties.creationDate", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime creationDate;

    /**
     * The containment state of the database.
     */
    @JsonProperty(value = "properties.containmentState", access = JsonProperty.Access.WRITE_ONLY)
    private Long containmentState;

    /**
     * The current service level objective ID of the database. This is the ID
     * of the service level objective that is currently active.
     */
    @JsonProperty(value = "properties.currentServiceObjectiveId", access = JsonProperty.Access.WRITE_ONLY)
    private UUID currentServiceObjectiveId;

    /**
     * The ID of the database.
     */
    @JsonProperty(value = "properties.databaseId", access = JsonProperty.Access.WRITE_ONLY)
    private UUID databaseId;

    /**
     * This records the earliest start date and time that restore is available
     * for this database (ISO8601 format).
     */
    @JsonProperty(value = "properties.earliestRestoreDate", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime earliestRestoreDate;

    /**
     * Specifies the mode of database creation.
     *
     * Default: regular database creation.
     *
     * Copy: creates a database as a copy of an existing database.
     * sourceDatabaseId must be specified as the resource ID of the source
     * database.
     *
     * OnlineSecondary/NonReadableSecondary: creates a database as a (readable
     * or nonreadable) secondary replica of an existing database.
     * sourceDatabaseId must be specified as the resource ID of the existing
     * primary database.
     *
     * PointInTimeRestore: Creates a database by restoring a point in time
     * backup of an existing database. sourceDatabaseId must be specified as
     * the resource ID of the existing database, and restorePointInTime must be
     * specified.
     *
     * Recovery: Creates a database by restoring a geo-replicated backup.
     * sourceDatabaseId must be specified as the recoverable database resource
     * ID to restore.
     *
     * Restore: Creates a database by restoring a backup of a deleted database.
     * sourceDatabaseId must be specified. If sourceDatabaseId is the
     * database's original resource ID, then sourceDatabaseDeletionDate must be
     * specified. Otherwise sourceDatabaseId must be the restorable dropped
     * database resource ID and sourceDatabaseDeletionDate is ignored.
     * restorePointInTime may also be specified to restore from an earlier
     * point in time.
     *
     * RestoreLongTermRetentionBackup: Creates a database by restoring from a
     * long term retention vault. recoveryServicesRecoveryPointResourceId must
     * be specified as the recovery point resource ID.
     *
     * Copy, NonReadableSecondary, OnlineSecondary and
     * RestoreLongTermRetentionBackup are not supported for DataWarehouse
     * edition. Possible values include: 'Copy', 'Default',
     * 'NonReadableSecondary', 'OnlineSecondary', 'PointInTimeRestore',
     * 'Recovery', 'Restore', 'RestoreLongTermRetentionBackup'.
     */
    @JsonProperty(value = "properties.createMode")
    private CreateMode createMode;

    /**
     * Conditional. If createMode is Copy, NonReadableSecondary,
     * OnlineSecondary, PointInTimeRestore, Recovery, or Restore, then this
     * value is required. Specifies the resource ID of the source database. If
     * createMode is NonReadableSecondary or OnlineSecondary, the name of the
     * source database must be the same as the new database being created.
     */
    @JsonProperty(value = "properties.sourceDatabaseId")
    private String sourceDatabaseId;

    /**
     * Conditional. If createMode is Restore and sourceDatabaseId is the
     * deleted database's original resource id when it existed (as opposed to
     * its current restorable dropped database id), then this value is
     * required. Specifies the time that the database was deleted.
     */
    @JsonProperty(value = "properties.sourceDatabaseDeletionDate")
    private DateTime sourceDatabaseDeletionDate;

    /**
     * Conditional. If createMode is PointInTimeRestore, this value is
     * required. If createMode is Restore, this value is optional. Specifies
     * the point in time (ISO8601 format) of the source database that will be
     * restored to create the new database. Must be greater than or equal to
     * the source database's earliestRestoreDate value.
     */
    @JsonProperty(value = "properties.restorePointInTime")
    private DateTime restorePointInTime;

    /**
     * Conditional. If createMode is RestoreLongTermRetentionBackup, then this
     * value is required. Specifies the resource ID of the recovery point to
     * restore from.
     */
    @JsonProperty(value = "properties.recoveryServicesRecoveryPointResourceId")
    private String recoveryServicesRecoveryPointResourceId;

    /**
     * The edition of the database. The DatabaseEditions enumeration contains
     * all the valid editions. If createMode is NonReadableSecondary or
     * OnlineSecondary, this value is ignored.
     *
     * The list of SKUs may vary by region and support offer. To determine the
     * SKUs (including the SKU name, tier/edition, family, and capacity) that
     * are available to your subscription in an Azure region, use the
     * `Capabilities_ListByLocation` REST API or one of the following commands:
     *
     * ```azurecli
     * az sql db list-editions -l &lt;location&gt; -o table
     * ````
     *
     * ```powershell
     * Get-AzSqlServerServiceObjective -Location &lt;location&gt;
     * ````
     * . Possible values include: 'Web', 'Business', 'Basic', 'Standard',
     * 'Premium', 'PremiumRS', 'Free', 'Stretch', 'DataWarehouse', 'System',
     * 'System2', 'GeneralPurpose', 'BusinessCritical', 'Hyperscale'.
     */
    @JsonProperty(value = "properties.edition")
    private DatabaseEdition edition;

    /**
     * The max size of the database expressed in bytes. If createMode is not
     * Default, this value is ignored. To see possible values, query the
     * capabilities API
     * (/subscriptions/{subscriptionId}/providers/Microsoft.Sql/locations/{locationID}/capabilities)
     * referred to by operationId: "Capabilities_ListByLocation.".
     */
    @JsonProperty(value = "properties.maxSizeBytes")
    private String maxSizeBytes;

    /**
     * The configured service level objective ID of the database. This is the
     * service level objective that is in the process of being applied to the
     * database. Once successfully updated, it will match the value of
     * currentServiceObjectiveId property. If requestedServiceObjectiveId and
     * requestedServiceObjectiveName are both updated, the value of
     * requestedServiceObjectiveId overrides the value of
     * requestedServiceObjectiveName.
     *
     * The list of SKUs may vary by region and support offer. To determine the
     * service objective ids that are available to your subscription in an
     * Azure region, use the `Capabilities_ListByLocation` REST API.
     */
    @JsonProperty(value = "properties.requestedServiceObjectiveId")
    private UUID requestedServiceObjectiveId;

    /**
     * The name of the configured service level objective of the database. This
     * is the service level objective that is in the process of being applied
     * to the database. Once successfully updated, it will match the value of
     * serviceLevelObjective property.
     *
     * The list of SKUs may vary by region and support offer. To determine the
     * SKUs (including the SKU name, tier/edition, family, and capacity) that
     * are available to your subscription in an Azure region, use the
     * `Capabilities_ListByLocation` REST API or one of the following commands:
     *
     * ```azurecli
     * az sql db list-editions -l &lt;location&gt; -o table
     * ````
     *
     * ```powershell
     * Get-AzSqlServerServiceObjective -Location &lt;location&gt;
     * ````
     * . Possible values include: 'System', 'System0', 'System1', 'System2',
     * 'System3', 'System4', 'System2L', 'System3L', 'System4L', 'Free',
     * 'Basic', 'S0', 'S1', 'S2', 'S3', 'S4', 'S6', 'S7', 'S9', 'S12', 'P1',
     * 'P2', 'P3', 'P4', 'P6', 'P11', 'P15', 'PRS1', 'PRS2', 'PRS4', 'PRS6',
     * 'DW100', 'DW200', 'DW300', 'DW400', 'DW500', 'DW600', 'DW1000',
     * 'DW1200', 'DW1000c', 'DW1500', 'DW1500c', 'DW2000', 'DW2000c', 'DW3000',
     * 'DW2500c', 'DW3000c', 'DW6000', 'DW5000c', 'DW6000c', 'DW7500c',
     * 'DW10000c', 'DW15000c', 'DW30000c', 'DS100', 'DS200', 'DS300', 'DS400',
     * 'DS500', 'DS600', 'DS1000', 'DS1200', 'DS1500', 'DS2000', 'ElasticPool'.
     */
    @JsonProperty(value = "properties.requestedServiceObjectiveName")
    private ServiceObjectiveName requestedServiceObjectiveName;

    /**
     * The current service level objective of the database. Possible values
     * include: 'System', 'System0', 'System1', 'System2', 'System3',
     * 'System4', 'System2L', 'System3L', 'System4L', 'Free', 'Basic', 'S0',
     * 'S1', 'S2', 'S3', 'S4', 'S6', 'S7', 'S9', 'S12', 'P1', 'P2', 'P3', 'P4',
     * 'P6', 'P11', 'P15', 'PRS1', 'PRS2', 'PRS4', 'PRS6', 'DW100', 'DW200',
     * 'DW300', 'DW400', 'DW500', 'DW600', 'DW1000', 'DW1200', 'DW1000c',
     * 'DW1500', 'DW1500c', 'DW2000', 'DW2000c', 'DW3000', 'DW2500c',
     * 'DW3000c', 'DW6000', 'DW5000c', 'DW6000c', 'DW7500c', 'DW10000c',
     * 'DW15000c', 'DW30000c', 'DS100', 'DS200', 'DS300', 'DS400', 'DS500',
     * 'DS600', 'DS1000', 'DS1200', 'DS1500', 'DS2000', 'ElasticPool'.
     */
    @JsonProperty(value = "properties.serviceLevelObjective", access = JsonProperty.Access.WRITE_ONLY)
    private ServiceObjectiveName serviceLevelObjective;

    /**
     * The status of the database.
     */
    @JsonProperty(value = "properties.status", access = JsonProperty.Access.WRITE_ONLY)
    private String status;

    /**
     * The name of the elastic pool the database is in. If elasticPoolName and
     * requestedServiceObjectiveName are both updated, the value of
     * requestedServiceObjectiveName is ignored. Not supported for
     * DataWarehouse edition.
     */
    @JsonProperty(value = "properties.elasticPoolName")
    private String elasticPoolName;

    /**
     * The default secondary region for this database.
     */
    @JsonProperty(value = "properties.defaultSecondaryLocation", access = JsonProperty.Access.WRITE_ONLY)
    private String defaultSecondaryLocation;

    /**
     * The list of service tier advisors for this database. Expanded property.
     */
    @JsonProperty(value = "properties.serviceTierAdvisors", access = JsonProperty.Access.WRITE_ONLY)
    private List<ServiceTierAdvisorInner> serviceTierAdvisors;

    /**
     * The transparent data encryption info for this database.
     */
    @JsonProperty(value = "properties.transparentDataEncryption", access = JsonProperty.Access.WRITE_ONLY)
    private List<TransparentDataEncryptionInner> transparentDataEncryption;

    /**
     * The recommended indices for this database.
     */
    @JsonProperty(value = "properties.recommendedIndex", access = JsonProperty.Access.WRITE_ONLY)
    private List<RecommendedIndex> recommendedIndex;

    /**
     * The resource identifier of the failover group containing this database.
     */
    @JsonProperty(value = "properties.failoverGroupId", access = JsonProperty.Access.WRITE_ONLY)
    private String failoverGroupId;

    /**
     * Conditional. If the database is a geo-secondary, readScale indicates
     * whether read-only connections are allowed to this database or not. Not
     * supported for DataWarehouse edition. Possible values include: 'Enabled',
     * 'Disabled'.
     */
    @JsonProperty(value = "properties.readScale")
    private ReadScale readScale;

    /**
     * Indicates the name of the sample schema to apply when creating this
     * database. If createMode is not Default, this value is ignored. Not
     * supported for DataWarehouse edition. Possible values include:
     * 'AdventureWorksLT'.
     */
    @JsonProperty(value = "properties.sampleName")
    private SampleName sampleName;

    /**
     * Whether or not this database is zone redundant, which means the replicas
     * of this database will be spread across multiple availability zones.
     */
    @JsonProperty(value = "properties.zoneRedundant")
    private Boolean zoneRedundant;

    /**
     * Get kind of database.  This is metadata used for the Azure portal experience.
     *
     * @return the kind value
     */
    public String kind() {
        return this.kind;
    }

    /**
     * Get the collation of the database. If createMode is not Default, this value is ignored.
     *
     * @return the collation value
     */
    public String collation() {
        return this.collation;
    }

    /**
     * Set the collation of the database. If createMode is not Default, this value is ignored.
     *
     * @param collation the collation value to set
     * @return the DatabaseInner object itself.
     */
    public DatabaseInner withCollation(String collation) {
        this.collation = collation;
        return this;
    }

    /**
     * Get the creation date of the database (ISO8601 format).
     *
     * @return the creationDate value
     */
    public DateTime creationDate() {
        return this.creationDate;
    }

    /**
     * Get the containment state of the database.
     *
     * @return the containmentState value
     */
    public Long containmentState() {
        return this.containmentState;
    }

    /**
     * Get the current service level objective ID of the database. This is the ID of the service level objective that is currently active.
     *
     * @return the currentServiceObjectiveId value
     */
    public UUID currentServiceObjectiveId() {
        return this.currentServiceObjectiveId;
    }

    /**
     * Get the ID of the database.
     *
     * @return the databaseId value
     */
    public UUID databaseId() {
        return this.databaseId;
    }

    /**
     * Get this records the earliest start date and time that restore is available for this database (ISO8601 format).
     *
     * @return the earliestRestoreDate value
     */
    public DateTime earliestRestoreDate() {
        return this.earliestRestoreDate;
    }

    /**
     * Get specifies the mode of database creation.
     Default: regular database creation.
     Copy: creates a database as a copy of an existing database. sourceDatabaseId must be specified as the resource ID of the source database.
     OnlineSecondary/NonReadableSecondary: creates a database as a (readable or nonreadable) secondary replica of an existing database. sourceDatabaseId must be specified as the resource ID of the existing primary database.
     PointInTimeRestore: Creates a database by restoring a point in time backup of an existing database. sourceDatabaseId must be specified as the resource ID of the existing database, and restorePointInTime must be specified.
     Recovery: Creates a database by restoring a geo-replicated backup. sourceDatabaseId must be specified as the recoverable database resource ID to restore.
     Restore: Creates a database by restoring a backup of a deleted database. sourceDatabaseId must be specified. If sourceDatabaseId is the database's original resource ID, then sourceDatabaseDeletionDate must be specified. Otherwise sourceDatabaseId must be the restorable dropped database resource ID and sourceDatabaseDeletionDate is ignored. restorePointInTime may also be specified to restore from an earlier point in time.
     RestoreLongTermRetentionBackup: Creates a database by restoring from a long term retention vault. recoveryServicesRecoveryPointResourceId must be specified as the recovery point resource ID.
     Copy, NonReadableSecondary, OnlineSecondary and RestoreLongTermRetentionBackup are not supported for DataWarehouse edition. Possible values include: 'Copy', 'Default', 'NonReadableSecondary', 'OnlineSecondary', 'PointInTimeRestore', 'Recovery', 'Restore', 'RestoreLongTermRetentionBackup'.
     *
     * @return the createMode value
     */
    public CreateMode createMode() {
        return this.createMode;
    }

    /**
     * Set specifies the mode of database creation.
     Default: regular database creation.
     Copy: creates a database as a copy of an existing database. sourceDatabaseId must be specified as the resource ID of the source database.
     OnlineSecondary/NonReadableSecondary: creates a database as a (readable or nonreadable) secondary replica of an existing database. sourceDatabaseId must be specified as the resource ID of the existing primary database.
     PointInTimeRestore: Creates a database by restoring a point in time backup of an existing database. sourceDatabaseId must be specified as the resource ID of the existing database, and restorePointInTime must be specified.
     Recovery: Creates a database by restoring a geo-replicated backup. sourceDatabaseId must be specified as the recoverable database resource ID to restore.
     Restore: Creates a database by restoring a backup of a deleted database. sourceDatabaseId must be specified. If sourceDatabaseId is the database's original resource ID, then sourceDatabaseDeletionDate must be specified. Otherwise sourceDatabaseId must be the restorable dropped database resource ID and sourceDatabaseDeletionDate is ignored. restorePointInTime may also be specified to restore from an earlier point in time.
     RestoreLongTermRetentionBackup: Creates a database by restoring from a long term retention vault. recoveryServicesRecoveryPointResourceId must be specified as the recovery point resource ID.
     Copy, NonReadableSecondary, OnlineSecondary and RestoreLongTermRetentionBackup are not supported for DataWarehouse edition. Possible values include: 'Copy', 'Default', 'NonReadableSecondary', 'OnlineSecondary', 'PointInTimeRestore', 'Recovery', 'Restore', 'RestoreLongTermRetentionBackup'.
     *
     * @param createMode the createMode value to set
     * @return the DatabaseInner object itself.
     */
    public DatabaseInner withCreateMode(CreateMode createMode) {
        this.createMode = createMode;
        return this;
    }

    /**
     * Get conditional. If createMode is Copy, NonReadableSecondary, OnlineSecondary, PointInTimeRestore, Recovery, or Restore, then this value is required. Specifies the resource ID of the source database. If createMode is NonReadableSecondary or OnlineSecondary, the name of the source database must be the same as the new database being created.
     *
     * @return the sourceDatabaseId value
     */
    public String sourceDatabaseId() {
        return this.sourceDatabaseId;
    }

    /**
     * Set conditional. If createMode is Copy, NonReadableSecondary, OnlineSecondary, PointInTimeRestore, Recovery, or Restore, then this value is required. Specifies the resource ID of the source database. If createMode is NonReadableSecondary or OnlineSecondary, the name of the source database must be the same as the new database being created.
     *
     * @param sourceDatabaseId the sourceDatabaseId value to set
     * @return the DatabaseInner object itself.
     */
    public DatabaseInner withSourceDatabaseId(String sourceDatabaseId) {
        this.sourceDatabaseId = sourceDatabaseId;
        return this;
    }

    /**
     * Get conditional. If createMode is Restore and sourceDatabaseId is the deleted database's original resource id when it existed (as opposed to its current restorable dropped database id), then this value is required. Specifies the time that the database was deleted.
     *
     * @return the sourceDatabaseDeletionDate value
     */
    public DateTime sourceDatabaseDeletionDate() {
        return this.sourceDatabaseDeletionDate;
    }

    /**
     * Set conditional. If createMode is Restore and sourceDatabaseId is the deleted database's original resource id when it existed (as opposed to its current restorable dropped database id), then this value is required. Specifies the time that the database was deleted.
     *
     * @param sourceDatabaseDeletionDate the sourceDatabaseDeletionDate value to set
     * @return the DatabaseInner object itself.
     */
    public DatabaseInner withSourceDatabaseDeletionDate(DateTime sourceDatabaseDeletionDate) {
        this.sourceDatabaseDeletionDate = sourceDatabaseDeletionDate;
        return this;
    }

    /**
     * Get conditional. If createMode is PointInTimeRestore, this value is required. If createMode is Restore, this value is optional. Specifies the point in time (ISO8601 format) of the source database that will be restored to create the new database. Must be greater than or equal to the source database's earliestRestoreDate value.
     *
     * @return the restorePointInTime value
     */
    public DateTime restorePointInTime() {
        return this.restorePointInTime;
    }

    /**
     * Set conditional. If createMode is PointInTimeRestore, this value is required. If createMode is Restore, this value is optional. Specifies the point in time (ISO8601 format) of the source database that will be restored to create the new database. Must be greater than or equal to the source database's earliestRestoreDate value.
     *
     * @param restorePointInTime the restorePointInTime value to set
     * @return the DatabaseInner object itself.
     */
    public DatabaseInner withRestorePointInTime(DateTime restorePointInTime) {
        this.restorePointInTime = restorePointInTime;
        return this;
    }

    /**
     * Get conditional. If createMode is RestoreLongTermRetentionBackup, then this value is required. Specifies the resource ID of the recovery point to restore from.
     *
     * @return the recoveryServicesRecoveryPointResourceId value
     */
    public String recoveryServicesRecoveryPointResourceId() {
        return this.recoveryServicesRecoveryPointResourceId;
    }

    /**
     * Set conditional. If createMode is RestoreLongTermRetentionBackup, then this value is required. Specifies the resource ID of the recovery point to restore from.
     *
     * @param recoveryServicesRecoveryPointResourceId the recoveryServicesRecoveryPointResourceId value to set
     * @return the DatabaseInner object itself.
     */
    public DatabaseInner withRecoveryServicesRecoveryPointResourceId(String recoveryServicesRecoveryPointResourceId) {
        this.recoveryServicesRecoveryPointResourceId = recoveryServicesRecoveryPointResourceId;
        return this;
    }

    /**
     * Get the edition of the database. The DatabaseEditions enumeration contains all the valid editions. If createMode is NonReadableSecondary or OnlineSecondary, this value is ignored.
     The list of SKUs may vary by region and support offer. To determine the SKUs (including the SKU name, tier/edition, family, and capacity) that are available to your subscription in an Azure region, use the `Capabilities_ListByLocation` REST API or one of the following commands:
     ```azurecli
     az sql db list-editions -l &lt;location&gt; -o table
     ````
     ```powershell
     Get-AzSqlServerServiceObjective -Location &lt;location&gt;
     ````
     . Possible values include: 'Web', 'Business', 'Basic', 'Standard', 'Premium', 'PremiumRS', 'Free', 'Stretch', 'DataWarehouse', 'System', 'System2', 'GeneralPurpose', 'BusinessCritical', 'Hyperscale'.
     *
     * @return the edition value
     */
    public DatabaseEdition edition() {
        return this.edition;
    }

    /**
     * Set the edition of the database. The DatabaseEditions enumeration contains all the valid editions. If createMode is NonReadableSecondary or OnlineSecondary, this value is ignored.
     The list of SKUs may vary by region and support offer. To determine the SKUs (including the SKU name, tier/edition, family, and capacity) that are available to your subscription in an Azure region, use the `Capabilities_ListByLocation` REST API or one of the following commands:
     ```azurecli
     az sql db list-editions -l &lt;location&gt; -o table
     ````
     ```powershell
     Get-AzSqlServerServiceObjective -Location &lt;location&gt;
     ````
     . Possible values include: 'Web', 'Business', 'Basic', 'Standard', 'Premium', 'PremiumRS', 'Free', 'Stretch', 'DataWarehouse', 'System', 'System2', 'GeneralPurpose', 'BusinessCritical', 'Hyperscale'.
     *
     * @param edition the edition value to set
     * @return the DatabaseInner object itself.
     */
    public DatabaseInner withEdition(DatabaseEdition edition) {
        this.edition = edition;
        return this;
    }

    /**
     * Get the max size of the database expressed in bytes. If createMode is not Default, this value is ignored. To see possible values, query the capabilities API (/subscriptions/{subscriptionId}/providers/Microsoft.Sql/locations/{locationID}/capabilities) referred to by operationId: "Capabilities_ListByLocation.".
     *
     * @return the maxSizeBytes value
     */
    public String maxSizeBytes() {
        return this.maxSizeBytes;
    }

    /**
     * Set the max size of the database expressed in bytes. If createMode is not Default, this value is ignored. To see possible values, query the capabilities API (/subscriptions/{subscriptionId}/providers/Microsoft.Sql/locations/{locationID}/capabilities) referred to by operationId: "Capabilities_ListByLocation.".
     *
     * @param maxSizeBytes the maxSizeBytes value to set
     * @return the DatabaseInner object itself.
     */
    public DatabaseInner withMaxSizeBytes(String maxSizeBytes) {
        this.maxSizeBytes = maxSizeBytes;
        return this;
    }

    /**
     * Get the configured service level objective ID of the database. This is the service level objective that is in the process of being applied to the database. Once successfully updated, it will match the value of currentServiceObjectiveId property. If requestedServiceObjectiveId and requestedServiceObjectiveName are both updated, the value of requestedServiceObjectiveId overrides the value of requestedServiceObjectiveName.
     The list of SKUs may vary by region and support offer. To determine the service objective ids that are available to your subscription in an Azure region, use the `Capabilities_ListByLocation` REST API.
     *
     * @return the requestedServiceObjectiveId value
     */
    public UUID requestedServiceObjectiveId() {
        return this.requestedServiceObjectiveId;
    }

    /**
     * Set the configured service level objective ID of the database. This is the service level objective that is in the process of being applied to the database. Once successfully updated, it will match the value of currentServiceObjectiveId property. If requestedServiceObjectiveId and requestedServiceObjectiveName are both updated, the value of requestedServiceObjectiveId overrides the value of requestedServiceObjectiveName.
     The list of SKUs may vary by region and support offer. To determine the service objective ids that are available to your subscription in an Azure region, use the `Capabilities_ListByLocation` REST API.
     *
     * @param requestedServiceObjectiveId the requestedServiceObjectiveId value to set
     * @return the DatabaseInner object itself.
     */
    public DatabaseInner withRequestedServiceObjectiveId(UUID requestedServiceObjectiveId) {
        this.requestedServiceObjectiveId = requestedServiceObjectiveId;
        return this;
    }

    /**
     * Get the name of the configured service level objective of the database. This is the service level objective that is in the process of being applied to the database. Once successfully updated, it will match the value of serviceLevelObjective property.
     The list of SKUs may vary by region and support offer. To determine the SKUs (including the SKU name, tier/edition, family, and capacity) that are available to your subscription in an Azure region, use the `Capabilities_ListByLocation` REST API or one of the following commands:
     ```azurecli
     az sql db list-editions -l &lt;location&gt; -o table
     ````
     ```powershell
     Get-AzSqlServerServiceObjective -Location &lt;location&gt;
     ````
     . Possible values include: 'System', 'System0', 'System1', 'System2', 'System3', 'System4', 'System2L', 'System3L', 'System4L', 'Free', 'Basic', 'S0', 'S1', 'S2', 'S3', 'S4', 'S6', 'S7', 'S9', 'S12', 'P1', 'P2', 'P3', 'P4', 'P6', 'P11', 'P15', 'PRS1', 'PRS2', 'PRS4', 'PRS6', 'DW100', 'DW200', 'DW300', 'DW400', 'DW500', 'DW600', 'DW1000', 'DW1200', 'DW1000c', 'DW1500', 'DW1500c', 'DW2000', 'DW2000c', 'DW3000', 'DW2500c', 'DW3000c', 'DW6000', 'DW5000c', 'DW6000c', 'DW7500c', 'DW10000c', 'DW15000c', 'DW30000c', 'DS100', 'DS200', 'DS300', 'DS400', 'DS500', 'DS600', 'DS1000', 'DS1200', 'DS1500', 'DS2000', 'ElasticPool'.
     *
     * @return the requestedServiceObjectiveName value
     */
    public ServiceObjectiveName requestedServiceObjectiveName() {
        return this.requestedServiceObjectiveName;
    }

    /**
     * Set the name of the configured service level objective of the database. This is the service level objective that is in the process of being applied to the database. Once successfully updated, it will match the value of serviceLevelObjective property.
     The list of SKUs may vary by region and support offer. To determine the SKUs (including the SKU name, tier/edition, family, and capacity) that are available to your subscription in an Azure region, use the `Capabilities_ListByLocation` REST API or one of the following commands:
     ```azurecli
     az sql db list-editions -l &lt;location&gt; -o table
     ````
     ```powershell
     Get-AzSqlServerServiceObjective -Location &lt;location&gt;
     ````
     . Possible values include: 'System', 'System0', 'System1', 'System2', 'System3', 'System4', 'System2L', 'System3L', 'System4L', 'Free', 'Basic', 'S0', 'S1', 'S2', 'S3', 'S4', 'S6', 'S7', 'S9', 'S12', 'P1', 'P2', 'P3', 'P4', 'P6', 'P11', 'P15', 'PRS1', 'PRS2', 'PRS4', 'PRS6', 'DW100', 'DW200', 'DW300', 'DW400', 'DW500', 'DW600', 'DW1000', 'DW1200', 'DW1000c', 'DW1500', 'DW1500c', 'DW2000', 'DW2000c', 'DW3000', 'DW2500c', 'DW3000c', 'DW6000', 'DW5000c', 'DW6000c', 'DW7500c', 'DW10000c', 'DW15000c', 'DW30000c', 'DS100', 'DS200', 'DS300', 'DS400', 'DS500', 'DS600', 'DS1000', 'DS1200', 'DS1500', 'DS2000', 'ElasticPool'.
     *
     * @param requestedServiceObjectiveName the requestedServiceObjectiveName value to set
     * @return the DatabaseInner object itself.
     */
    public DatabaseInner withRequestedServiceObjectiveName(ServiceObjectiveName requestedServiceObjectiveName) {
        this.requestedServiceObjectiveName = requestedServiceObjectiveName;
        return this;
    }

    /**
     * Get the current service level objective of the database. Possible values include: 'System', 'System0', 'System1', 'System2', 'System3', 'System4', 'System2L', 'System3L', 'System4L', 'Free', 'Basic', 'S0', 'S1', 'S2', 'S3', 'S4', 'S6', 'S7', 'S9', 'S12', 'P1', 'P2', 'P3', 'P4', 'P6', 'P11', 'P15', 'PRS1', 'PRS2', 'PRS4', 'PRS6', 'DW100', 'DW200', 'DW300', 'DW400', 'DW500', 'DW600', 'DW1000', 'DW1200', 'DW1000c', 'DW1500', 'DW1500c', 'DW2000', 'DW2000c', 'DW3000', 'DW2500c', 'DW3000c', 'DW6000', 'DW5000c', 'DW6000c', 'DW7500c', 'DW10000c', 'DW15000c', 'DW30000c', 'DS100', 'DS200', 'DS300', 'DS400', 'DS500', 'DS600', 'DS1000', 'DS1200', 'DS1500', 'DS2000', 'ElasticPool'.
     *
     * @return the serviceLevelObjective value
     */
    public ServiceObjectiveName serviceLevelObjective() {
        return this.serviceLevelObjective;
    }

    /**
     * Get the status of the database.
     *
     * @return the status value
     */
    public String status() {
        return this.status;
    }

    /**
     * Get the name of the elastic pool the database is in. If elasticPoolName and requestedServiceObjectiveName are both updated, the value of requestedServiceObjectiveName is ignored. Not supported for DataWarehouse edition.
     *
     * @return the elasticPoolName value
     */
    public String elasticPoolName() {
        return this.elasticPoolName;
    }

    /**
     * Set the name of the elastic pool the database is in. If elasticPoolName and requestedServiceObjectiveName are both updated, the value of requestedServiceObjectiveName is ignored. Not supported for DataWarehouse edition.
     *
     * @param elasticPoolName the elasticPoolName value to set
     * @return the DatabaseInner object itself.
     */
    public DatabaseInner withElasticPoolName(String elasticPoolName) {
        this.elasticPoolName = elasticPoolName;
        return this;
    }

    /**
     * Get the default secondary region for this database.
     *
     * @return the defaultSecondaryLocation value
     */
    public String defaultSecondaryLocation() {
        return this.defaultSecondaryLocation;
    }

    /**
     * Get the list of service tier advisors for this database. Expanded property.
     *
     * @return the serviceTierAdvisors value
     */
    public List<ServiceTierAdvisorInner> serviceTierAdvisors() {
        return this.serviceTierAdvisors;
    }

    /**
     * Get the transparent data encryption info for this database.
     *
     * @return the transparentDataEncryption value
     */
    public List<TransparentDataEncryptionInner> transparentDataEncryption() {
        return this.transparentDataEncryption;
    }

    /**
     * Get the recommended indices for this database.
     *
     * @return the recommendedIndex value
     */
    public List<RecommendedIndex> recommendedIndex() {
        return this.recommendedIndex;
    }

    /**
     * Get the resource identifier of the failover group containing this database.
     *
     * @return the failoverGroupId value
     */
    public String failoverGroupId() {
        return this.failoverGroupId;
    }

    /**
     * Get conditional. If the database is a geo-secondary, readScale indicates whether read-only connections are allowed to this database or not. Not supported for DataWarehouse edition. Possible values include: 'Enabled', 'Disabled'.
     *
     * @return the readScale value
     */
    public ReadScale readScale() {
        return this.readScale;
    }

    /**
     * Set conditional. If the database is a geo-secondary, readScale indicates whether read-only connections are allowed to this database or not. Not supported for DataWarehouse edition. Possible values include: 'Enabled', 'Disabled'.
     *
     * @param readScale the readScale value to set
     * @return the DatabaseInner object itself.
     */
    public DatabaseInner withReadScale(ReadScale readScale) {
        this.readScale = readScale;
        return this;
    }

    /**
     * Get indicates the name of the sample schema to apply when creating this database. If createMode is not Default, this value is ignored. Not supported for DataWarehouse edition. Possible values include: 'AdventureWorksLT'.
     *
     * @return the sampleName value
     */
    public SampleName sampleName() {
        return this.sampleName;
    }

    /**
     * Set indicates the name of the sample schema to apply when creating this database. If createMode is not Default, this value is ignored. Not supported for DataWarehouse edition. Possible values include: 'AdventureWorksLT'.
     *
     * @param sampleName the sampleName value to set
     * @return the DatabaseInner object itself.
     */
    public DatabaseInner withSampleName(SampleName sampleName) {
        this.sampleName = sampleName;
        return this;
    }

    /**
     * Get whether or not this database is zone redundant, which means the replicas of this database will be spread across multiple availability zones.
     *
     * @return the zoneRedundant value
     */
    public Boolean zoneRedundant() {
        return this.zoneRedundant;
    }

    /**
     * Set whether or not this database is zone redundant, which means the replicas of this database will be spread across multiple availability zones.
     *
     * @param zoneRedundant the zoneRedundant value to set
     * @return the DatabaseInner object itself.
     */
    public DatabaseInner withZoneRedundant(Boolean zoneRedundant) {
        this.zoneRedundant = zoneRedundant;
        return this;
    }

}
