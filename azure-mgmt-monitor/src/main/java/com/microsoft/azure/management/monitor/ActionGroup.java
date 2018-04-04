/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.LangMethodDefinition;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.monitor.implementation.ActionGroupResourceInner;
import com.microsoft.azure.management.monitor.implementation.MonitorManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;

import java.util.List;

/**
 * An immutable client-side representation of an Azure Action Group.
 */
@Fluent
public interface ActionGroup extends
        GroupableResource<MonitorManager, ActionGroupResourceInner>,
        Refreshable<ActionGroup>,
        Updatable<ActionGroup.Update> {
    /**
     * Get the groupShortName value.
     *
     * @return the groupShortName value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String shortName();

    /**
     * Get the enabled value.
     *
     * @return the enabled value
     */
    /*@LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    boolean enabled();*/

    /**
     * Get the emailReceivers value.
     *
     * @return the emailReceivers value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    List<EmailReceiver> emailReceivers();

    /**
     * Get the smsReceivers value.
     *
     * @return the smsReceivers value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    List<SmsReceiver> smsReceivers();

    /**
     * Get the webhookReceivers value.
     *
     * @return the webhookReceivers value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    List<WebhookReceiver> webhookReceivers();

    /**
     * Get the itsmReceivers value.
     *
     * @return the itsmReceivers value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    List<ItsmReceiver> itsmReceivers();

    /**
     * Get the azureAppPushReceivers value.
     *
     * @return the azureAppPushReceivers value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    List<AzureAppPushReceiver> azureAppPushReceivers();

    /**
     * Get the automationRunbookReceivers value.
     *
     * @return the automationRunbookReceivers value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    List<AutomationRunbookReceiver> automationRunbookReceivers();

    /**
     * Get the voiceReceivers value.
     *
     * @return the voiceReceivers value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    List<VoiceReceiver> voiceReceivers();

    /**
     * Get the logicAppReceivers value.
     *
     * @return the logicAppReceivers value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    List<LogicAppReceiver> logicAppReceivers();

    /**
     * Get the azureFunctionReceivers value.
     *
     * @return the azureFunctionReceivers value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    List<AzureFunctionReceiver> azureFunctionReceivers();


    interface ActionDefinition<ParentT> {
        ActionDefinition<ParentT> withEmail(String emailAddress);

        ActionDefinition<ParentT> withSms(String countryCode, String phoneNumber);

        ActionDefinition<ParentT> withWebhook(String serviceUri);

        ActionDefinition<ParentT> withItsm(String workspaceId, String connectionId, String ticketConfiguration, String region);

        ActionDefinition<ParentT> withAzureAppPush(String emailAddress);

        ActionDefinition<ParentT> withAutomationRunbook(String automationAccountId, String runbookName, String webhookResourceId, boolean isGlobalRunbook);

        ActionDefinition<ParentT> withVoice(String countryCode, String phoneNumber);

        ActionDefinition<ParentT> withLogicApp(String logicAppResourceId, String callbackUrl);

        ActionDefinition<ParentT> withAzureFunction(String functionAppResourceId, String functionName, String httpTriggerUrl);

        @Method
        ParentT attach();
    }

    /**
     * The entirety of a Action Group definition.
     */
    interface Definition extends
            DefinitionStages.Blank,
            ActionDefinition,
            DefinitionStages.WithCreate {
    }

    /**
     * Grouping of Action Group definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of a Action Group definition allowing the resource group to be specified.
         */
        interface Blank extends GroupableResource.DefinitionStages.WithGroupAndRegion<WithCreate> {
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be created
         * but also allows for any other optional settings to be specified.
         */
        interface WithCreate extends
                Creatable<ActionGroup>,
                DefinitionWithTags<WithCreate> {

            ActionDefinition<ActionGroup.DefinitionStages.WithCreate> defineReceiver(String actionNamePrefix);

            WithCreate withShortName(String shortName);
        }
    }

    interface UpdateStages {

        interface WithActionDefinition {
            Update withoutReceiver(String actionNamePrefix);

            ActionDefinition<Update> defineReceiver(String actionNamePrefix);

            WithActionUpdateDefinition updateReceiver(String actionNamePrefix);

        }

        interface WithActionUpdateDefinition {

            @Method
            WithActionUpdateDefinition withoutEmail();

            @Method
            WithActionUpdateDefinition withoutSms();

            @Method
            WithActionUpdateDefinition withoutWebhook();

            @Method
            WithActionUpdateDefinition withoutItsm();

            @Method
            WithActionUpdateDefinition withoutAzureAppPush();

            @Method
            WithActionUpdateDefinition withoutAutomationRunbook();

            @Method
            WithActionUpdateDefinition withoutVoice();

            @Method
            WithActionUpdateDefinition withoutLogicApp();

            @Method
            WithActionUpdateDefinition withoutAzureFunction();

            WithActionUpdateDefinition withEmail(String emailAddress);

            WithActionUpdateDefinition withSms(String countryCode, String phoneNumber);

            WithActionUpdateDefinition withWebhook(String serviceUri);

            WithActionUpdateDefinition withItsm(String workspaceId, String connectionId, String ticketConfiguration, String region);

            WithActionUpdateDefinition withAzureAppPush(String emailAddress);

            WithActionUpdateDefinition withAutomationRunbook(String automationAccountId, String runbookName, String webhookResourceId, boolean isGlobalRunbook);

            WithActionUpdateDefinition withVoice(String countryCode, String phoneNumber);

            WithActionUpdateDefinition withLogicApp(String logicAppResourceId, String callbackUrl);

            WithActionUpdateDefinition withAzureFunction(String functionAppResourceId, String functionName, String httpTriggerUrl);

            @Method
            Update parent();
        }
    }

    /**
     * The template for an update operation, containing all the settings that can be modified.
     */
    interface Update extends
            Appliable<ActionGroup>,
            UpdateStages.WithActionDefinition,
            Resource.UpdateWithTags<Update> {
    }
}