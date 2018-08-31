/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.monitor.AutoscaleProfile;
import com.microsoft.azure.management.monitor.AutoscaleSetting;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import rx.Observable;

/**
 * Implementation for AutoscaleSetting.
 */
@LangDefinition
class AutoscaleSettingImpl
        extends GroupableResourceImpl<
        AutoscaleSetting,
        AutoscaleSettingResourceInner,
        AutoscaleSettingImpl,
        MonitorManager>
        implements
        AutoscaleSetting,
        AutoscaleSetting.Definition,
        AutoscaleSetting.Update {

    AutoscaleSettingImpl(String name, final AutoscaleSettingResourceInner innerModel, final MonitorManager monitorManager) {
        super(name, innerModel, monitorManager);
    }

    @Override
    public AutoscaleProfileImpl defineAutoscaleProfile(String name) {
        return null;
    }

    @Override
    public AutoscaleProfileImpl updateAutoscaleProfile(String name) {
        return null;
    }

    @Override
    public AutoscaleSettingImpl withTargetResource(String targetResourceId) {
        return null;
    }

    @Override
    public AutoscaleSettingImpl withAdminEmailNotification() {
        return null;
    }

    @Override
    public AutoscaleSettingImpl withCoAdminEmailNotification() {
        return null;
    }

    @Override
    public AutoscaleSettingImpl withCustomEmailNotification(String customEmailAddresses) {
        return null;
    }

    @Override
    public AutoscaleSettingImpl withWebhookNotification(String serviceUri) {
        return null;
    }

    @Override
    public AutoscaleSettingImpl withoutAdminEmailNotification() {
        return null;
    }

    @Override
    public AutoscaleSettingImpl withoutCoAdminEmailNotification() {
        return null;
    }

    @Override
    public AutoscaleSettingImpl withoutCustomEmailNotification(String customEmailAddresses) {
        return null;
    }

    @Override
    public AutoscaleSettingImpl withoutWebhookNotification(String serviceUri) {
        return null;
    }

    @Override
    public AutoscaleSettingImpl withoutAutoscaleProfile(String name) {
        return null;
    }

    @Override
    public AutoscaleSettingImpl withAutoscaleEnabled() {
        return null;
    }

    @Override
    public AutoscaleSettingImpl withAutoscaleDisabled() {
        return null;
    }

    @Override
    public Observable<AutoscaleSetting> createResourceAsync() {
        return null;
    }

    @Override
    protected Observable<AutoscaleSettingResourceInner> getInnerAsync() {
        return null;
    }
}

