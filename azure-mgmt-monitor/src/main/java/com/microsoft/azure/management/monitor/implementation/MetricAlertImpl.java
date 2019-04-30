/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.monitor.MetricAlert;
import com.microsoft.azure.management.monitor.MetricAlertAction;
import com.microsoft.azure.management.monitor.MetricAlertCondition;
import com.microsoft.azure.management.monitor.MetricAlertSingleResourceMultipleMetricCriteria;
import com.microsoft.azure.management.monitor.MetricCriteria;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
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
            MetricAlert.Update,
            MetricAlert.UpdateStages.WithMetricUpdate {

    private Map<String, MetricAlertCondition> conditions;

    MetricAlertImpl(String name, final MetricAlertResourceInner innerModel, final MonitorManager monitorManager) {
        super(name, innerModel, monitorManager);
        this.conditions = new TreeMap<>();
        MetricAlertSingleResourceMultipleMetricCriteria crits = (MetricAlertSingleResourceMultipleMetricCriteria) innerModel.criteria();
        if (crits != null) {
            for (MetricCriteria crit : crits.allOf()) {
                this.conditions.put(crit.name(), new MetricAlertConditionImpl(crit.name(), crit, this));
            }
        }
    }

    @Override
    public Observable<MetricAlert> createResourceAsync() {
        this.inner().withLocation("global");
        MetricAlertSingleResourceMultipleMetricCriteria crit = new MetricAlertSingleResourceMultipleMetricCriteria();
        crit.withAllOf(new ArrayList<MetricCriteria>());
        for (MetricAlertCondition mc : conditions.values()) {
            crit.allOf().add(mc.inner());
        }
        this.inner().withCriteria(crit);
        return this.manager().inner().metricAlerts().createOrUpdateAsync(this.resourceGroupName(), this.name(), this.inner())
                .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<MetricAlertResourceInner> getInnerAsync() {
        return this.manager().inner().metricAlerts().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public MetricAlertImpl withTargetResource(String resourceId) {
        this.inner().withScopes(new ArrayList<String>());
        this.inner().scopes().add(resourceId);
        return this;
    }

    @Override
    public MetricAlertImpl withTargetResource(HasId resource) {
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
    public MetricAlertConditionImpl updateAlertCriteria(String name) {
        return (MetricAlertConditionImpl) this.conditions.get(name);
    }

    @Override
    public MetricAlertImpl withoutAlertCriteria(String name) {
        if (this.conditions.containsKey(name)) {
            this.conditions.remove(name);
        }
        return this;
    }

    MetricAlertImpl withAlertCriteria(MetricAlertConditionImpl criteria) {
        this.withoutAlertCriteria(criteria.name());
        this.conditions.put(criteria.name(), criteria);
        return this;
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
        return this.conditions;
    }
}

