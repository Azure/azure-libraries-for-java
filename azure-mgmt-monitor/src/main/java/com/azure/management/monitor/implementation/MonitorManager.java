/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.monitor.implementation;

import com.azure.management.monitor.ActionGroups;
import com.azure.management.monitor.AutoscaleSettings;
import com.azure.management.monitor.DiagnosticSettings;
import com.azure.management.monitor.MetricDefinitions;
import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.AzureResponseBuilder;
import com.microsoft.azure.credentials.AzureTokenCredentials;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.azure.management.monitor.ActivityLogs;
import com.azure.management.monitor.AlertRules;
import com.microsoft.azure.management.resources.fluentcore.arm.AzureConfigurable;
import com.microsoft.azure.management.resources.fluentcore.arm.implementation.AzureConfigurableImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.implementation.Manager;
import com.microsoft.azure.management.resources.fluentcore.utils.ProviderRegistrationInterceptor;
import com.microsoft.azure.serializer.AzureJacksonAdapter;
import com.microsoft.rest.RestClient;

/**
 * Entry point to Azure Monitor.
 */
@Beta(SinceVersion.V1_2_0)
public final class MonitorManager extends Manager<MonitorManager, MonitorManagementClientImpl> {
    // Collections
    private ActivityLogs activityLogs;
    private MetricDefinitions metricDefinitions;
    private DiagnosticSettings diagnosticSettings;
    private ActionGroups actionGroups;
    private AlertRules alerts;
    private AutoscaleSettings autoscaleSettings;

    /**
    * Get a Configurable instance that can be used to create MonitorManager with optional configuration.
    *
    * @return the instance allowing configurations
    */
    public static Configurable configure() {
        return new MonitorManager.ConfigurableImpl();
    }
    /**
    * Creates an instance of MonitorManager that exposes Monitor API entry points.
    *
    * @param credentials the credentials to use
    * @param subscriptionId the subscription UUID
    * @return the MonitorManager
    */
    public static MonitorManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
        return new MonitorManager(new RestClient.Builder()
            .withBaseUrl(credentials.environment(), AzureEnvironment.Endpoint.RESOURCE_MANAGER)
            .withCredentials(credentials)
            .withSerializerAdapter(new AzureJacksonAdapter())
            .withResponseBuilderFactory(new AzureResponseBuilder.Factory())
            .withInterceptor(new ProviderRegistrationInterceptor(credentials))
            .build(), subscriptionId);
    }
    /**
    * Creates an instance of MonitorManager that exposes Monitor API entry points.
    *
    * @param restClient the RestClient to be used for API calls.
    * @param subscriptionId the subscription UUID
    * @return the MonitorManager
    */
    public static MonitorManager authenticate(RestClient restClient, String subscriptionId) {
        return new MonitorManager(restClient, subscriptionId);
    }
    /**
    * The interface allowing configurations to be set.
    */
    public interface Configurable extends AzureConfigurable<Configurable> {
        /**
        * Creates an instance of MonitorManager that exposes Monitor API entry points.
        *
        * @param credentials the credentials to use
        * @param subscriptionId the subscription UUID
        * @return the interface exposing monitor API entry points that work across subscriptions
        */
        MonitorManager authenticate(AzureTokenCredentials credentials, String subscriptionId);
    }

    /**
     * @return the Azure Activity Logs API entry point
     */
    public ActivityLogs activityLogs() {
        if (this.activityLogs == null) {
            this.activityLogs = new ActivityLogsImpl(this);
        }
        return this.activityLogs;
    }

    /**
     * @return the Azure Metric Definitions API entry point
     */
    public MetricDefinitions metricDefinitions() {
        if (this.metricDefinitions == null) {
            this.metricDefinitions = new MetricDefinitionsImpl(this);
        }
        return this.metricDefinitions;
    }

    /**
     * @return the Azure Diagnostic Settings API entry point
     */
    @Beta(SinceVersion.V1_8_0)
    public DiagnosticSettings diagnosticSettings() {
        if (this.diagnosticSettings == null) {
            this.diagnosticSettings = new DiagnosticSettingsImpl(this);
        }
        return this.diagnosticSettings;
    }

    /**
     * @return the Azure Action Groups API entry point
     */
    @Beta(SinceVersion.V1_8_0)
    public ActionGroups actionGroups() {
        if (this.actionGroups == null) {
            this.actionGroups = new ActionGroupsImpl(this);
        }
        return this.actionGroups;
    }

    /**
     * @return the Azure AlertRules API entry point
     */
    @Beta(SinceVersion.V1_15_0)
    public AlertRules alertRules() {
        if (this.alerts == null) {
            this.alerts = new AlertRulesImpl(this);
        }
        return this.alerts;
    }

    /**
     * @return the Azure AutoscaleSettings API entry point
     */
    @Beta(SinceVersion.V1_15_0)
    public AutoscaleSettings autoscaleSettings() {
        if (this.autoscaleSettings == null) {
            this.autoscaleSettings = new AutoscaleSettingsImpl(this);
        }
        return this.autoscaleSettings;
    }

    /**
    * The implementation for Configurable interface.
    */
    private static final class ConfigurableImpl extends AzureConfigurableImpl<Configurable> implements Configurable {
        public MonitorManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
           return MonitorManager.authenticate(buildRestClient(credentials), subscriptionId);
        }
    }

    private MonitorManager(RestClient restClient, String subscriptionId) {
        super(
                restClient,
                subscriptionId,
                new MonitorManagementClientImpl(restClient).withSubscriptionId(subscriptionId));
    }
}
