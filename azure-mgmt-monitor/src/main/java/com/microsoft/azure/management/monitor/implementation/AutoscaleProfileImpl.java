/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.monitor.AutoscaleProfile;
import com.microsoft.azure.management.monitor.AutoscaleSetting;
import com.microsoft.azure.management.monitor.DayOfWeek;
import com.microsoft.azure.management.monitor.MetricAlert;
import com.microsoft.azure.management.monitor.MetricAlertCondition;
import com.microsoft.azure.management.monitor.MetricAlertRuleCondition;
import com.microsoft.azure.management.monitor.MetricAlertRuleTimeAggregation;
import com.microsoft.azure.management.monitor.MetricCriteria;
import com.microsoft.azure.management.monitor.MetricDimension;
import com.microsoft.azure.management.monitor.ScaleCapacity;
import com.microsoft.azure.management.monitor.ScaleRule;
import com.microsoft.azure.management.monitor.TimeWindow;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

/**
 * Implementation for AutoscaleProfile.
 */
@LangDefinition
class AutoscaleProfileImpl
        extends WrapperImpl<AutoscaleProfileInner>
        implements
            AutoscaleProfile,
            AutoscaleProfile.Definition,
            AutoscaleProfile.UpdateDefinition,
            AutoscaleProfile.Update {

    private final AutoscaleSettingImpl parent;
    private TreeMap<String, MetricDimension> dimensions;

    AutoscaleProfileImpl(String name, AutoscaleProfileInner innerObject, AutoscaleSettingImpl parent) {
        super(innerObject);
        this.inner().withName(name);
        this.parent = parent;
    }

    @Override
    public AutoscaleSettingImpl parent() {
        return null;
    }

    @Override
    public AutoscaleSettingImpl attach() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public ScaleCapacity capacity() {
        return null;
    }

    @Override
    public List<ScaleRule> rules() {
        return null;
    }

    @Override
    public TimeWindow fixedDate() {
        return null;
    }

    @Override
    public AutoscaleProfileImpl withMetricBasedScale(int minimumInstanceCount, int maximumInstanceCount, int defaultInstanceCount) {
        return null;
    }

    @Override
    public AutoscaleProfileImpl withScheduleBasedScale(int instanceCount) {
        return null;
    }

    @Override
    public AutoscaleProfileImpl withoutScaleRule(int ruleIndex) {
        return null;
    }

    @Override
    public AutoscaleProfileImpl withFixedDateSchedule(String timeZone, DateTime start, DateTime end) {
        return null;
    }

    @Override
    public AutoscaleProfileImpl withRecurrentSchedule(String scheduleTimeZone, String startTime, DayOfWeek... weekday) {
        return null;
    }

    @Override
    public ScaleRuleImpl defineScaleRule() {
        return null;
    }

    @Override
    public ScaleRuleImpl updateScaleRule(int ruleIndex) {
        return null;
    }
}

