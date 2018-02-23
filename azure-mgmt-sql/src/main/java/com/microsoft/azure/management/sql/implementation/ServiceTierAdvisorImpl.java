/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.RefreshableWrapperImpl;
import com.microsoft.azure.management.sql.ServiceLevelObjectiveUsageMetric;
import com.microsoft.azure.management.sql.ServiceTierAdvisor;
import com.microsoft.azure.management.sql.SloUsageMetric;
import com.microsoft.azure.management.sql.SloUsageMetricInterface;
import org.joda.time.DateTime;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementation for Azure SQL Database's service tier advisor.
 */
@LangDefinition
class ServiceTierAdvisorImpl
        extends RefreshableWrapperImpl<ServiceTierAdvisorInner, ServiceTierAdvisor>
        implements ServiceTierAdvisor {
    private final String sqlServerName;
    private final String resourceGroupName;
    private final SqlServerManager sqlServerManager;
    private final ResourceId resourceId;
    private List<SloUsageMetricInterface> sloUsageMetrics;
    private List<ServiceLevelObjectiveUsageMetric> serviceLevelObjectiveUsageMetrics;

    protected ServiceTierAdvisorImpl(String resourceGroupName, String sqlServerName, ServiceTierAdvisorInner innerObject, SqlServerManager sqlServerManager) {
        super(innerObject);
        this.resourceGroupName = resourceGroupName;
        this.sqlServerName = sqlServerName;
        this.sqlServerManager = sqlServerManager;
        this.resourceId = ResourceId.fromString(this.inner().id());
    }


    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String resourceGroupName() {
        return this.resourceGroupName;
    }

    @Override
    public String sqlServerName() {
        return this.sqlServerName;
    }

    @Override
    public String databaseName() {
        return this.resourceId.parent().name();
    }

    @Override
    public DateTime observationPeriodStart() {
        return this.inner().observationPeriodStart();
    }

    @Override
    public DateTime observationPeriodEnd() {
        return this.inner().observationPeriodEnd();
    }

    @Override
    public double activeTimeRatio() {
        return this.inner().activeTimeRatio();
    }

    @Override
    public double minDtu() {
        return this.inner().minDtu();
    }

    @Override
    public double avgDtu() {
        return this.inner().avgDtu();
    }

    @Override
    public double maxDtu() {
        return this.inner().maxDtu();
    }

    @Override
    public double maxSizeInGB() {
        return this.inner().maxSizeInGB();
    }

    @Override
    public List<SloUsageMetricInterface> serviceLevelObjectiveUsageMetrics() {
        if (this.sloUsageMetrics == null) {
            this.sloUsageMetrics = new ArrayList<>();
            for (SloUsageMetric sloUsageMetricInner : this.inner().serviceLevelObjectiveUsageMetrics()) {
                this.sloUsageMetrics.add(new SloUsageMetricImpl(sloUsageMetricInner));
            }
        }
        return sloUsageMetrics;
    }

    @Override
    public List<ServiceLevelObjectiveUsageMetric> serviceLevelObjectiveUsageMetric() {
        if (this.serviceLevelObjectiveUsageMetrics == null) {
            this.serviceLevelObjectiveUsageMetrics = new ArrayList<>();
            for (SloUsageMetric sloUsageMetricInner : this.inner().serviceLevelObjectiveUsageMetrics()) {
                this.serviceLevelObjectiveUsageMetrics.add(new ServiceLevelObjectiveUsageMetricImpl(sloUsageMetricInner));
            }
        }
        return serviceLevelObjectiveUsageMetrics;
    }

    @Override
    public String currentServiceLevelObjective() {
        return this.inner().currentServiceLevelObjective();
    }

    @Override
    public UUID currentServiceLevelObjectiveId() {
        return this.inner().currentServiceLevelObjectiveId();
    }

    @Override
    public String usageBasedRecommendationServiceLevelObjective() {
        return this.inner().usageBasedRecommendationServiceLevelObjective();
    }

    @Override
    public UUID usageBasedRecommendationServiceLevelObjectiveId() {
        return this.inner().currentServiceLevelObjectiveId();
    }

    @Override
    public String databaseSizeBasedRecommendationServiceLevelObjective() {
        return this.inner().databaseSizeBasedRecommendationServiceLevelObjective();
    }

    @Override
    public UUID databaseSizeBasedRecommendationServiceLevelObjectiveId() {
        return this.inner().databaseSizeBasedRecommendationServiceLevelObjectiveId();
    }

    @Override
    public String disasterPlanBasedRecommendationServiceLevelObjective() {
        return this.inner().disasterPlanBasedRecommendationServiceLevelObjective();
    }

    @Override
    public UUID disasterPlanBasedRecommendationServiceLevelObjectiveId() {
        return this.inner().disasterPlanBasedRecommendationServiceLevelObjectiveId();
    }

    @Override
    public String overallRecommendationServiceLevelObjective() {
        return this.inner().overallRecommendationServiceLevelObjective();
    }

    @Override
    public UUID overallRecommendationServiceLevelObjectiveId() {
        return this.inner().overallRecommendationServiceLevelObjectiveId();
    }

    @Override
    public double confidence() {
        return this.inner().confidence();
    }

    @Override
    protected Observable<ServiceTierAdvisorInner> getInnerAsync() {
        this.sloUsageMetrics = null;
        this.serviceLevelObjectiveUsageMetrics = null;

        return this.sqlServerManager.inner().serviceTierAdvisors().getAsync(this.resourceGroupName, this.sqlServerName, this.databaseName(), this.name());
    }
}
