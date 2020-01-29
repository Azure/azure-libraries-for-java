/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.management.network.models.FlowLogInformationInner;
import com.azure.management.resources.fluentcore.model.implementation.RefreshableWrapperImpl;
import com.azure.management.resources.fluentcore.utils.Utils;
import com.azure.management.network.FlowLogSettings;
import com.azure.management.network.RetentionPolicyParameters;
import reactor.core.publisher.Mono;


/**
 * Implementation for {@link FlowLogSettings} and its create and update interfaces.
 */
class FlowLogSettingsImpl extends RefreshableWrapperImpl<FlowLogInformationInner,
        FlowLogSettings>
        implements
        FlowLogSettings,
        FlowLogSettings.Update {
    private final NetworkWatcherImpl parent;
    private final String nsgId;

    FlowLogSettingsImpl(NetworkWatcherImpl parent, FlowLogInformationInner inner, String nsgId) {
        super(inner);
        this.parent = parent;
        this.nsgId = nsgId;
    }

    @Override
    public FlowLogSettings apply() {
        return applyAsync().block();
    }

    @Override
    public Mono<FlowLogSettings> applyAsync() {
        return this.parent().manager().inner().networkWatchers()
                .setFlowLogConfigurationAsync(parent().resourceGroupName(), parent().name(), this.inner())
                .map(flowLogInformationInner -> new FlowLogSettingsImpl(FlowLogSettingsImpl.this.parent, flowLogInformationInner, nsgId));
    }

    @Override
    public Update withLogging() {
        this.inner().setEnabled(true);
        return this;
    }

    @Override
    public Update withoutLogging() {
        this.inner().setEnabled(false);
        return this;
    }

    @Override
    public Update withStorageAccount(String storageId) {
        this.inner().setStorageId(storageId);
        return this;
    }

    @Override
    public Update withRetentionPolicyEnabled() {
        ensureRetentionPolicy();
        this.inner().getRetentionPolicy().setEnabled(true);
        return this;
    }

    @Override
    public Update withRetentionPolicyDisabled() {
        ensureRetentionPolicy();
        this.inner().getRetentionPolicy().setEnabled(false);
        return this;
    }

    @Override
    public Update withRetentionPolicyDays(int days) {
        ensureRetentionPolicy();
        this.inner().getRetentionPolicy().setDays(days);
        return this;
    }

    private void ensureRetentionPolicy() {
        if (this.inner().getRetentionPolicy() == null) {
            this.inner().setRetentionPolicy(new RetentionPolicyParameters());
        }
    }

    @Override
    public Update update() {
        if (this.inner().getFlowAnalyticsConfiguration() != null && this.inner().getFlowAnalyticsConfiguration().getNetworkWatcherFlowAnalyticsConfiguration() == null) {
            // Service response could have such case, which is not valid in swagger that networkWatcherFlowAnalyticsConfiguration is a required field.
            this.inner().setFlowAnalyticsConfiguration(null);
        }
        return this;
    }

    @Override
    protected Mono<FlowLogInformationInner> getInnerAsync() {
        return this.parent().manager().inner().networkWatchers()
                .getFlowLogStatusAsync(parent().resourceGroupName(), parent().name(), inner().getTargetResourceId());
    }

    @Override
    public NetworkWatcherImpl parent() {
        return parent;
    }

    @Override
    public String key() {
        return null;
    }

    @Override
    public String targetResourceId() {
        return inner().getTargetResourceId();
    }

    @Override
    public String storageId() {
        return inner().getStorageId();
    }

    @Override
    public boolean enabled() {
        return Utils.toPrimitiveBoolean(inner().isEnabled());
    }

    @Override
    public boolean isRetentionEnabled() {
        // will return default values if server response for retention policy was empty
        ensureRetentionPolicy();
        return Utils.toPrimitiveBoolean(inner().getRetentionPolicy().isEnabled());
    }

    @Override
    public int retentionDays() {
        // will return default values if server response for retention policy was empty
        ensureRetentionPolicy();
        return Utils.toPrimitiveInt(inner().getRetentionPolicy().getDays());
    }

    @Override
    public String networkSecurityGroupId() {
        return nsgId;
    }
}
