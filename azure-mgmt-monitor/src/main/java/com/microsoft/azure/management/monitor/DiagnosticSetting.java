/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.LangMethodDefinition;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.monitor.implementation.DiagnosticSettingsResourceInner;
import com.microsoft.azure.management.monitor.implementation.MonitorManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import org.joda.time.Period;
import rx.Completable;
import rx.Observable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An immutable client-side representation of an Azure diagnostic settings.
 */
@Fluent
public interface DiagnosticSetting extends
        Indexable,
        HasId,
        HasName,
        HasManager<MonitorManager>,
        HasInner<DiagnosticSettingsResourceInner>,
        Refreshable<DiagnosticSetting>,
        Updatable<DiagnosticSetting.Update> {
    /**
     * Get the associated  resource Id value.
     *
     * @return the associated  resource Id value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String resourceId();

    /**
     * Get the storageAccountId value.
     *
     * @return the storageAccountId value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String storageAccountId();

    /**
     * Get the eventHubAuthorizationRuleId value.
     *
     * @return the eventHubAuthorizationRuleId value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String eventHubAuthorizationRuleId();

    /**
     * Get the eventHubName value.
     *
     * @return the eventHubName value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String eventHubName();

    /**
     * Get the metrics value.
     *
     * @return the metrics value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    List<MetricSettings> metrics();

    /**
     * Get the logs value.
     *
     * @return the logs value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    List<LogSettings> logs();

    /**
     * Get the workspaceId value.
     *
     * @return the workspaceId value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String workspaceId();

    /**
     * The entirety of a diagnostic settings definition.
     */
    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.WithCreate {
    }

    /**
     * Grouping of diagnostic settings definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of a diagnostic setting definition.
         */
        interface Blank {
            WithDiagnosticLogRecipient withResource(String resourceId);
        }

        interface WithDiagnosticLogRecipient {
            WithCreate withLogAnalytics(String workspaceId);
            WithCreate withStorageAccount(String storageAccountId);
            WithCreate withEventHub(String eventHubAuthorizationRuleId);
            WithCreate withEventHub(String eventHubAuthorizationRuleId, String eventHubName);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be created
         * but also allows for any other optional settings to be specified.
         */
        interface WithCreate extends
                WithDiagnosticLogRecipient,
                Creatable<DiagnosticSetting> {
            WithCreate withMetric(String category, Period timeGrain, int retentionDays);
            WithCreate withLog(String category, int retentionDays);
            WithCreate withLogsAndMetrics(List<DiagnosticSettingsCategory> categories, Period timeGrain, int retentionDays);
        }
    }

    /**
     * Grouping of diagnostic setting update stages.
     */
    interface UpdateStages {
        /**
         * The stage of a CDN profile update allowing to modify the endpoints for the profile.
         */
        interface WithStorageAccount {

            /**
             * Removes an endpoint from the profile.
             *
             * @param name the name of an existing endpoint
             * @return the next stage of the CDN profile update
             */
            Update withStorageAccount(String storageAccountId);
            Update withoutStorageAccount();
        }

        interface WithEventHub {
            Update withEventHub(String eventHubAuthorizationRuleId);
            Update withEventHub(String eventHubAuthorizationRuleId, String eventHubName);
            Update withoutEventHub();
        }

        interface WithLogAnalytics {
            Update withLogAnalytics(String workspaceId);
            Update withoutLogAnalytics();
        }

        interface WithMetricAndLogs {
            Update withMetric(String category, Period timeGrain, int retentionDays);
            Update withLog(String category, int retentionDays);
            Update withLogsAndMetrics(List<DiagnosticSettingsCategory> categories, Period timeGrain, int retentionDays);
            Update withoutMetric(String category);
            Update withoutLog(String category);
            Update withoutLogs();
            Update withoutMetrics();
        }
    }

    /**
     * The template for an update operation, containing all the settings that can be modified.
     */
    interface Update extends
            Appliable<DiagnosticSetting>,
            UpdateStages.WithStorageAccount,
            UpdateStages.WithEventHub,
            UpdateStages.WithLogAnalytics,
            UpdateStages.WithMetricAndLogs {
    }
}