/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.implementation;

import com.google.common.base.Strings;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.monitor.AutoscaleProfile;
import com.microsoft.azure.management.monitor.DayOfWeek;
import com.microsoft.azure.management.monitor.Recurrence;
import com.microsoft.azure.management.monitor.RecurrenceFrequency;
import com.microsoft.azure.management.monitor.RecurrentSchedule;
import com.microsoft.azure.management.monitor.ScaleCapacity;
import com.microsoft.azure.management.monitor.ScaleRule;
import com.microsoft.azure.management.monitor.TimeWindow;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

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

    AutoscaleProfileImpl(String name, AutoscaleProfileInner innerObject, AutoscaleSettingImpl parent) {
        super(innerObject);
        this.inner().withName(name);
        this.parent = parent;
        if(this.inner().capacity() == null) {
            this.inner().withCapacity(new ScaleCapacity());
        }
        if(this.inner().rules() == null) {
            this.inner().withRules(new ArrayList<ScaleRuleInner>());
        }
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public int minInstanceCount() {
        if(this.inner().capacity() != null) {
            return Integer.parseInt(this.inner().capacity().minimum());
        }
        return 0;
    }

    @Override
    public int maxInstanceCount() {
        if(this.inner().capacity() != null) {
            return Integer.parseInt(this.inner().capacity().maximum());
        }
        return 0;
    }

    @Override
    public int defaultInstanceCount() {
        if(this.inner().capacity() != null) {
            return Integer.parseInt(this.inner().capacity().defaultProperty());
        }
        return 0;
    }

    @Override
    public TimeWindow fixedDateSchedule() {
        return this.inner().fixedDate();
    }

    @Override
    public Recurrence recurrentSchedule() {
        return this.inner().recurrence();
    }

    @Override
    public List<ScaleRule> rules() {
        List<ScaleRule> rules = new ArrayList<>();
        if(this.inner().rules() != null) {
            for (ScaleRuleInner ruleInner : this.inner().rules()) {
                rules.add(new ScaleRuleImpl(ruleInner, this));
            }
        }
        return rules;
    }

    @Override
    public AutoscaleSettingImpl parent() {
        return parent;
    }

    @Override
    public AutoscaleSettingImpl attach() {
        return parent.addNewAutoscaleProfile(this);
    }

    @Override
    public AutoscaleProfileImpl withMetricBasedScale(int minimumInstanceCount, int maximumInstanceCount, int defaultInstanceCount) {
        this.inner().capacity().withMinimum(Integer.toString(minimumInstanceCount));
        this.inner().capacity().withMaximum(Integer.toString(maximumInstanceCount));
        this.inner().capacity().withDefaultProperty(Integer.toString(defaultInstanceCount));
        return this;
    }

    @Override
    public AutoscaleProfileImpl withScheduleBasedScale(int instanceCount) {
        return this.withMetricBasedScale(instanceCount, instanceCount, instanceCount);
    }

    @Override
    public AutoscaleProfileImpl withFixedDateSchedule(String timeZone, DateTime start, DateTime end) {
        this.inner().withFixedDate(new TimeWindow()
                                        .withTimeZone(timeZone)
                                        .withStart(start)
                                        .withEnd(end));
        if(this.inner().recurrence() != null) {
            this.inner().withRecurrence(null);
        }
        return this;
    }

    @Override
    public AutoscaleProfileImpl withRecurrentSchedule(String scheduleTimeZone, String startTime, DayOfWeek... weekday) {
        int hh, mm;
        if(Strings.isNullOrEmpty(startTime) ||
                startTime.length() != 5 ||
                startTime.charAt(2) != ':' ||
                !Character.isDigit(startTime.charAt(0)) ||
                !Character.isDigit(startTime.charAt(1)) ||
                !Character.isDigit(startTime.charAt(3)) ||
                !Character.isDigit(startTime.charAt(4)) ||
                (hh = Integer.parseInt(startTime.substring(0,2))) > 23 ||
                (mm = Integer.parseInt(startTime.substring(3))) > 60) {
            throw new IllegalArgumentException("Start time should have format of 'hh:mm' where hh is in 24-hour clock (AM/PM times are not supported).");
        }

        this.inner().withRecurrence(new Recurrence());

        this.inner().recurrence().withFrequency(RecurrenceFrequency.WEEK);
        this.inner().recurrence().withSchedule(new RecurrentSchedule());
        this.inner().recurrence().schedule().withTimeZone(scheduleTimeZone);
        this.inner().recurrence().schedule().withHours(new ArrayList<Integer>());
        this.inner().recurrence().schedule().withMinutes(new ArrayList<Integer>());
        this.inner().recurrence().schedule().hours().add(hh);
        this.inner().recurrence().schedule().minutes().add(mm);
        this.inner().recurrence().schedule().withDays(new ArrayList<String>());

        for(DayOfWeek dof : weekday) {
            this.inner().recurrence().schedule().days().add(dof.toString());
        }

        this.inner().withFixedDate(null);
        return this;
    }

    @Override
    public ScaleRuleImpl defineScaleRule() {
        return new ScaleRuleImpl(new ScaleRuleInner(), this);
    }

    @Override
    public ScaleRuleImpl updateScaleRule(int ruleIndex) {
        ScaleRuleImpl srToUpdate = new ScaleRuleImpl(this.inner().rules().get(ruleIndex), this);
        return srToUpdate;
    }

    @Override
    public AutoscaleProfileImpl withoutScaleRule(int ruleIndex) {
        this.inner().rules().remove(ruleIndex);
        return this;
    }

    AutoscaleProfileImpl addNewScaleRule(ScaleRuleImpl scaleRule) {
        this.inner().rules().add(scaleRule.inner());
        return this;
    }
}

