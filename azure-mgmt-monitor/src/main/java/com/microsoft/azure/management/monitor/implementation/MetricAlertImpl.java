/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.monitor.DynamicMetricCriteria;
import com.microsoft.azure.management.monitor.MetricAlert;
import com.microsoft.azure.management.monitor.MetricAlertAction;
import com.microsoft.azure.management.monitor.MetricAlertCondition;
import com.microsoft.azure.management.monitor.MetricAlertMultipleResourceMultipleMetricCriteria;
import com.microsoft.azure.management.monitor.MetricAlertSingleResourceMultipleMetricCriteria;
import com.microsoft.azure.management.monitor.MetricCriteria;
import com.microsoft.azure.management.monitor.MetricDynamicAlertCondition;
import com.microsoft.azure.management.monitor.MultiMetricCriteria;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import org.joda.time.DateTime;
import org.joda.time.Period;
import rx.Observable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implementation for MetricAlert.
 */
@LangDefinition
class MetricAlertImpl
        extends GroupableResourceImpl<
            MetricAlert,
            MetricAlertResourceInner,
            MetricAlertImpl,
            MonitorManager>
        implements
            MetricAlert,
            MetricAlert.Definition,
            MetricAlert.DefinitionMultipleResource,
            MetricAlert.Update,
            MetricAlert.UpdateStages.WithMetricUpdate {

    // 2019/09 at present service support 2 static criteria, or 1 dynamic criteria
    // static criteria
    private Map<String, MetricAlertCondition> conditions;
    // dynamic criteria
    private Map<String, MetricDynamicAlertCondition> dynamicConditions;

    private boolean multipleResource = false;

    MetricAlertImpl(String name, final MetricAlertResourceInner innerModel, final MonitorManager monitorManager) {
        super(name, innerModel, monitorManager);
        this.conditions = new TreeMap<>();
        this.dynamicConditions = new TreeMap<>();
        if (innerModel.criteria() != null) {
            if (innerModel.criteria() instanceof MetricAlertSingleResourceMultipleMetricCriteria) {
                multipleResource = false;
                // single resource with multiple static criteria
                MetricAlertSingleResourceMultipleMetricCriteria crits = (MetricAlertSingleResourceMultipleMetricCriteria) innerModel.criteria();
                List<MetricCriteria> criteria = crits.allOf();
                if (criteria != null) {
                    for (MetricCriteria crit : criteria) {
                        this.conditions.put(crit.name(), new MetricAlertConditionImpl(crit.name(), crit, this));
                    }
                }
            } else if (innerModel.criteria() instanceof MetricAlertMultipleResourceMultipleMetricCriteria) {
                multipleResource = true;
                // multiple resource with either multiple static criteria, or (currently single) dynamic criteria
                MetricAlertMultipleResourceMultipleMetricCriteria crits = (MetricAlertMultipleResourceMultipleMetricCriteria) innerModel.criteria();
                List<MultiMetricCriteria> criteria = crits.allOf();
                if (criteria != null) {
                    for (MultiMetricCriteria crit : criteria) {
                        if (crit instanceof MetricCriteria) {
                            this.conditions.put(crit.name(), new MetricAlertConditionImpl(crit.name(), (MetricCriteria) crit, this));
                        } else if (crit instanceof DynamicMetricCriteria) {
                            this.dynamicConditions.put(crit.name(), new MetricDynamicAlertConditionImpl(crit.name(), (DynamicMetricCriteria) crit, this));
                        }
                    }
                }
            }
        }
    }

    @Override
    public Observable<MetricAlert> createResourceAsync() {
        if (this.conditions.isEmpty() && this.dynamicConditions.isEmpty()) {
            throw new IllegalArgumentException("Condition cannot be empty");
        } else if (!this.conditions.isEmpty() && !this.dynamicConditions.isEmpty()) {
            throw new IllegalArgumentException("Static condition and dynamic condition cannot co-exist");
        }

        this.inner().withLocation("global");
        if (!this.conditions.isEmpty()) {
            if (!multipleResource) {
                MetricAlertSingleResourceMultipleMetricCriteria crit = new MetricAlertSingleResourceMultipleMetricCriteria();
                crit.withAllOf(new ArrayList<MetricCriteria>());
                for (MetricAlertCondition mc : conditions.values()) {
                    crit.allOf().add(mc.inner());
                }
                this.inner().withCriteria(crit);
            } else {
                MetricAlertMultipleResourceMultipleMetricCriteria crit = new MetricAlertMultipleResourceMultipleMetricCriteria();
                crit.withAllOf(new ArrayList<MultiMetricCriteria>());
                for (MetricAlertCondition mc : conditions.values()) {
                    crit.allOf().add(mc.inner());
                }
                this.inner().withCriteria(crit);
            }
        } else if (!this.dynamicConditions.isEmpty()) {
            MetricAlertMultipleResourceMultipleMetricCriteria crit = new MetricAlertMultipleResourceMultipleMetricCriteria();
            crit.withAllOf(new ArrayList<MultiMetricCriteria>());
            for (MetricDynamicAlertCondition mc : dynamicConditions.values()) {
                crit.allOf().add(mc.inner());
            }
            this.inner().withCriteria(crit);
        }
        return this.manager().inner().metricAlerts().createOrUpdateAsync(this.resourceGroupName(), this.name(), this.inner())
                .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<MetricAlertResourceInner> getInnerAsync() {
        return this.manager().inner().metricAlerts().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public MetricAlertImpl withTargetResource(String resourceId) {
        multipleResource = false;

        this.inner().withScopes(new ArrayList<String>());
        this.inner().scopes().add(resourceId);
        return this;
    }

    @Override
    public MetricAlertImpl withTargetResource(HasId resource) {
        multipleResource = false;

        return this.withTargetResource(resource.id());
    }

    @Override
    public MetricAlertImpl withPeriod(Period size) {
        this.inner().withWindowSize(size);
        return this;
    }

    @Override
    public MetricAlertImpl withFrequency(Period frequency) {
        this.inner().withEvaluationFrequency(frequency);
        return this;
    }

    @Override
    public MetricAlertImpl withSeverity(int severity) {
        this.inner().withSeverity(severity);
        return this;
    }

    @Override
    public MetricAlertImpl withAlertDetails(int severity, String description) {
        this.withSeverity(severity);
        return this.withDescription(description);
    }

    @Override
    public MetricAlertImpl withDescription(String description) {
        this.inner().withDescription(description);
        return this;
    }

    @Override
    public MetricAlertImpl withRuleEnabled() {
        this.inner().withEnabled(true);
        return this;
    }

    @Override
    public MetricAlertImpl withRuleDisabled() {
        this.inner().withEnabled(false);
        return this;
    }

    @Override
    public MetricAlertImpl withAutoMitigation() {
        this.inner().withAutoMitigate(true);
        return this;
    }

    @Override
    public MetricAlertImpl withoutAutoMitigation() {
        this.inner().withAutoMitigate(false);
        return this;
    }

    @Override
    public MetricAlertImpl withActionGroups(String... actionGroupId) {
        if (this.inner().actions() == null) {
            this.inner().withActions(new ArrayList<MetricAlertAction>());
        }
        this.inner().actions().clear();
        for (String agid : actionGroupId) {
            MetricAlertAction maa = new MetricAlertAction();
            maa.withActionGroupId(agid);
            this.inner().actions().add(maa);
        }
        return this;
    }

    @Override
    public MetricAlertImpl withoutActionGroup(String actionGroupId) {
        if (this.inner().actions() != null) {
            List<MetricAlertAction> toDelete = new ArrayList<>();
            for (MetricAlertAction maa : this.inner().actions()) {
                if (maa.actionGroupId().equalsIgnoreCase(actionGroupId)) {
                    toDelete.add(maa);
                }
            }
            this.inner().actions().removeAll(toDelete);
        }
        return this;
    }

    @Override
    public MetricAlertConditionImpl defineAlertCriteria(String name) {
        return new MetricAlertConditionImpl(name, new MetricCriteria(), this);
    }

    @Override
    public MetricDynamicAlertConditionImpl defineDynamicAlertCriteria(String name) {
        return new MetricDynamicAlertConditionImpl(name, new DynamicMetricCriteria(), this);
    }

    @Override
    public MetricAlertConditionImpl updateAlertCriteria(String name) {
        return (MetricAlertConditionImpl) this.conditions.get(name);
    }

    @Override
    public MetricDynamicAlertConditionImpl updateDynamicAlertCriteria(String name) {
        return (MetricDynamicAlertConditionImpl) this.dynamicConditions.get(name);
    }

    @Override
    public MetricAlertImpl withoutAlertCriteria(String name) {
        if (this.conditions.containsKey(name)) {
            this.conditions.remove(name);
        }
        if (this.dynamicConditions.containsKey(name)) {
            this.dynamicConditions.remove(name);
        }
        return this;
    }

    MetricAlertImpl withAlertCriteria(MetricAlertConditionImpl criteria) {
        this.withoutAlertCriteria(criteria.name());
        this.conditions.put(criteria.name(), criteria);
        return this;
    }

    MetricAlertImpl withDynamicAlertCriteria(MetricDynamicAlertConditionImpl criteria) {
        this.withoutAlertCriteria(criteria.name());
        this.dynamicConditions.put(criteria.name(), criteria);
        return this;
    }

    @Override
    public MetricAlertImpl withMultipleTargetResources(Collection<String> resourceIds, String type, String region) {
        if (resourceIds == null || resourceIds.isEmpty()) {
            throw new IllegalArgumentException("Target resource cannot be empty");
        }

        multipleResource = true;

        this.inner().withScopes(new ArrayList<>(resourceIds));
        this.inner().withTargetResourceType(type);
        this.inner().withTargetResourceRegion(region);
        return this;
    }

    @Override
    public MetricAlertImpl withMultipleTargetResources(Collection<? extends Resource> resources) {
        if (resources == null || resources.isEmpty()) {
            throw new IllegalArgumentException("Target resource cannot be empty");
        }

        multipleResource = true;

        List<String> resourceIds = new ArrayList<>();
        String type = resources.iterator().next().type();
        String region = resources.iterator().next().regionName();
        for (Resource resource : resources) {
            if (!type.equalsIgnoreCase(resource.type()) || !region.equalsIgnoreCase(resource.regionName())) {
                throw new IllegalArgumentException("Target resource must be of same resource type and in same region");
            }

            resourceIds.add(resource.id());
        }
        return this.withMultipleTargetResources(resourceIds, type, region);
    }

    @Override
    public String description() {
        return this.inner().description();
    }

    @Override
    public int severity() {
        return this.inner().severity();
    }

    @Override
    public boolean enabled() {
        return this.inner().enabled();
    }

    @Override
    public Period evaluationFrequency() {
        return this.inner().evaluationFrequency();
    }

    @Override
    public Period windowSize() {
        return this.inner().windowSize();
    }

    @Override
    public boolean autoMitigate() {
        return this.inner().autoMitigate();
    }

    @Override
    public DateTime lastUpdatedTime() {
        return this.inner().lastUpdatedTime();
    }

    @Override
    public Collection<String> scopes() {
        return Collections.unmodifiableCollection(this.inner().scopes());
    }

    @Override
    public Collection<String> actionGroupIds() {
        if (this.inner().actions() != null
                && this.inner().actions() != null) {
            List<String> ids = new ArrayList<>();
            for (MetricAlertAction maag : this.inner().actions()) {
                ids.add(maag.actionGroupId());
            }
            return Collections.unmodifiableCollection(ids);
        }
        return Collections.emptyList();
    }

    @Override
    public Map<String, MetricAlertCondition> alertCriterias() {
        return Collections.unmodifiableMap(this.conditions);
    }

    @Override
    public Map<String, MetricDynamicAlertCondition> dynamicAlertCriterias() {
        return Collections.unmodifiableMap(this.dynamicConditions);
    }
}

