/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * An immutable client-side representation of a Container Registry source trigger.
 */
@Fluent()
@Beta
public interface RegistrySourceTrigger extends
        HasInner<SourceTrigger> {

    /**
     * Container interface for all of the definitions related to a container registry source trigger.
     */
    interface Definition extends
            RegistrySourceTrigger.DefinitionStages.Blank,
            RegistrySourceTrigger.DefinitionStages.SourceControlType,
            RegistrySourceTrigger.DefinitionStages.RepositoryUrl,
            RegistrySourceTrigger.DefinitionStages.TriggerEventsDefinition,
            RegistrySourceTrigger.DefinitionStages.RepositoryBranchAndAuth,
            RegistrySourceTrigger.DefinitionStages.TriggerStatusDefinition,
            RegistrySourceTrigger.DefinitionStages.SourceTriggerAttachable {

    }

    /**
     * Grouping of source trigger definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of a container registry task definition.
         */
        interface Blank {
            /**
             * The function that specifies the name of the container registry source trigger.
             *
             * @param name the name of the source trigger.
             * @return the next stage of the container registry source trigger definition.
             */
            SourceControlType withName(String name);
        }

        /**
         * The stage of the container registry source trigger definition allowing to specify the type of source control.
         */
        interface SourceControlType {
            /**
             * The function that specifies Github will be used as the type of source control.
             *
             * @return the next stage of the container registry source trigger definition.
             */
            RepositoryUrl withGithubAsSourceControl();

            /**
             * The function that specifies Azure DevOps will be used as the type of source control.
             *
             * @return the next stage of the container registry source trigger definition.
             */
            RepositoryUrl withAzureDevOpsAsSourceControl();

            /**
             * The function that allows the user to input their own kind of source control.
             *
             * @param sourceControl the source control the user wishes to use.
             * @return the next stage of the container registry source trigger definition.
             */
            RepositoryUrl withSourceControl(SourceControlType sourceControl);
        }

        /**
         * The stage of the container registry source trigger definition allowing to specify the URL of the source control repository.
         */
        interface RepositoryUrl {
            /**
             * The function that specifies the URL of the source control repository.
             *
             * @param sourceControlRepositoryUrl the URL of the source control repository.
             * @return the next stage of the container registry source trigger definition.
             */
            TriggerEventsDefinition withSourceControlRepositoryUrl(String sourceControlRepositoryUrl);
        }

        /**
         * The stage of the container registry source trigger definition allowing to specify the type of actions that will trigger a run.
         */
        interface TriggerEventsDefinition {
            /**
             * The function that specifies a commit action will trigger a run.
             *
             * @return the next stage of the container registry source trigger definition.
             */
            SourceTriggerAttachable withCommitTriggerEvent();

            /**
             * The function that specifies a pull action will trigger a run.
             *
             * @return the next stage of the container registry source trigger definition.
             */
            SourceTriggerAttachable withPullTriggerEvent();

            /**
             * The function that allows the user to specify an action that will trigger a run when it is executed.
             *
             * @param sourceTriggerEvent the action that will trigger a run.
             * @return the next stage of the container registry source trigger definition.
             */
            SourceTriggerAttachable withTriggerEvent(SourceTriggerEvent sourceTriggerEvent);
        }

        /**
         * The stage of the container registry source trigger definition allowing to specify the branch of the repository and authentication credentials
         * if needed to interact with the source control repository.
         */
        interface RepositoryBranchAndAuth {
            /**
             * The function that specifies the branch of the respository to use.
             *
             * @param branch the repository branch.
             * @return the next stage of the container registry source trigger definition.
             */
            SourceTriggerAttachable withRepositoryBranch(String branch);

            /**
             * The function that allows the user to input the type of the token used for authentication and the token itself to authenticate to the source control repository.
             *
             * @param tokenType the type of the token used to authenticate to the source control repository.
             * @param token the token used to authenticate to the source control repository.
             * @return the next stage of the container registry source trigger definition.
             */
            SourceTriggerAttachable withRepositoryAuthentication(TokenType tokenType, String token);

            /**
             * The function that allows the user to input the type of the token used for authentication and the token itself to authenticate to the source control repository.
             *
             * @param tokenType the type of the token used to authenticate to the source control repository.
             * @param token the token used to authenticate to the source control repository.
             * @param refreshToken the token that is used to refresh the access token.
             * @param scope the scope of the access token.
             * @param expiresIn time in seconds that the token remains valid.
             * @return the next stage of the container registry source trigger definition.
             */
            SourceTriggerAttachable withRepositoryAuthentication(TokenType tokenType, String token, String refreshToken, String scope, int expiresIn);
        }

        /**
         * The stage of the container registry source trigger definition allowing to specify the status of the trigger.
         */
        interface TriggerStatusDefinition {
            /**
             * The function that sets the trigger status to be enabled.
             *
             * @return the next stage of the container registry source trigger definition.
             */
            SourceTriggerAttachable withTriggerStatusEnabled();

            /**
             * The function that sets the trigger status to be disabled.
             *
             * @return the next stage of the container registry source trigger definition.
             */
            SourceTriggerAttachable withTriggerStatusDisabled();

            /**
             * The function that allows the user to input the state of the trigger status.
             *
             * @param triggerStatus the user's choice for the trigger status.
             * @return the next stage of the container registry source trigger definition.
             */
            SourceTriggerAttachable withTriggerStatus(TriggerStatus triggerStatus);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be attached,
         *  but also allows for any other optional settings to be specified.
         */
        interface SourceTriggerAttachable extends
                RepositoryBranchAndAuth,
                TriggerEventsDefinition,
                TriggerStatusDefinition,
                Attachable<RegistryTask.DefinitionStages.TaskCreatable> {

        }
    }
}
