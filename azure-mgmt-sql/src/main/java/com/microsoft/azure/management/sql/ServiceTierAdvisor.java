/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.sql;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.sql.implementation.ServiceTierAdvisorInner;
import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;


/**
 * An immutable client-side representation of an Azure SQL Service tier advisor.
 */
@Fluent
public interface ServiceTierAdvisor extends
        Refreshable<ServiceTierAdvisor>,
        HasInner<ServiceTierAdvisorInner>,
        HasResourceGroup,
        HasName,
        HasId {

    /**
     * @return name of the SQL Server to which this replication belongs
     */
    String sqlServerName();

    /**
     * @return name of the SQL Database to which this replication belongs
     */
    String databaseName();

    /**
     * @return the observation period start (ISO8601 format)
     */
    DateTime observationPeriodStart();

    /**
     * @return the observation period start (ISO8601 format)
     */
    DateTime observationPeriodEnd();

    /**
     * @return the activeTimeRatio for the service tier advisor
     */
    double activeTimeRatio();

    /**
     * @return or sets minDtu for the service tier advisor
     */
    double minDtu();

    /**
     * @return the average DTU for the service tier advisor
     */
    double avgDtu();

    /**
     * @return the maximum DTU for the service tier advisor
     */
    double maxDtu();

    /**
     * @return the maximum size in GB for the service tier advisor
     */
    double maxSizeInGB();

    /**
     * @return the service level objective usage metrics for the service tier advisor
     */
    @Deprecated
    List<SloUsageMetricInterface> serviceLevelObjectiveUsageMetrics();

    /**
     * @return the service level objective usage metric for the service tier
     * advisor.
     */
    List<ServiceLevelObjectiveUsageMetric> serviceLevelObjectiveUsageMetric();

    /**
     * @return the current service level Objective for the service tier advisor
     */
    String currentServiceLevelObjective();

    /**
     * @return the current service level objective ID for the service tier advisor
     */
    UUID currentServiceLevelObjectiveId();

    /**
     * @return the usage based recommendation service level objective for the service tier advisor
     */
    String usageBasedRecommendationServiceLevelObjective();

    /**
     * @return the usage based recommendation service level objective ID for the service tier advisor.
     */
    UUID usageBasedRecommendationServiceLevelObjectiveId();

    /**
     * @return the database size based recommendation service level objective for the service tier advisor
     */
    String databaseSizeBasedRecommendationServiceLevelObjective();

    /**
     * @return the database size based recommendation service level objective ID for the service tier advisor
     */
    UUID databaseSizeBasedRecommendationServiceLevelObjectiveId();

    /**
     * @return the disaster plan based recommendation service level objective for the service tier advisor.
     */
    String disasterPlanBasedRecommendationServiceLevelObjective();

    /**
     * @return the disaster plan based recommendation service level objective ID for the service tier advisor.
     */
    UUID disasterPlanBasedRecommendationServiceLevelObjectiveId();

    /**
     * @return the overall recommendation service level objective for the service tier advisor.
     */
    String overallRecommendationServiceLevelObjective();

    /**
     * @return the overall recommendation service level objective ID for the service tier advisor.
     */
    UUID overallRecommendationServiceLevelObjectiveId();

    /**
     * @return the confidence for service tier advisor.
     */
    double confidence();
}

