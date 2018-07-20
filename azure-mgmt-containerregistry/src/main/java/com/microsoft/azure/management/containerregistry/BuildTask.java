/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.containerregistry.implementation.BuildTaskInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.ExternalChildResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import org.joda.time.DateTime;

/**
 * An object that represents a build task for a container registry.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_14_0)
public interface BuildTask extends
    ExternalChildResource<BuildTask, Registry>,
    Resource,
    HasInner<BuildTaskInner>,
    Refreshable<BuildTask>,
    Updatable<BuildTask.Update> {


    /**
     * @return the parent ID of this resource
     */
    String parentId();

    /**
     * @return the provisioning state of the build task
     */
    ProvisioningState provisioningState();

    /**
     * @return the creation date of build task
     */
    DateTime creationDate();

    /**
     * @return the alternative name for a build task
     */
    String alias();

    /**
     * @return the current status of build task
     */
    BuildTaskStatus status();

    /**
     * @return the build timeout settings in seconds
     */
    int timeout();

    /**
     * @return the OS type required for the build
     */
    OsType osType();

    /**
     * @return the CPU configuration in terms of number of cores required for the build
     */
    int cpuCount();

    /**
     * @return the source control type
     */
    SourceControlType sourceControlType();

    /**
     * @return the URL to the source code repository
     */
    String repositoryUrl();

    /**
     * @return the whether the source control commit trigger is enabled or not
     */
    boolean isCommitTriggerEnabled();

    /**
     * @return the type of the authentication token
     */
    TokenType authenticationTokenType();

    /**
     * @return the access token used to access the source control provider
     */
    String authenticationToken();

    /**
     * @return the refresh token used to refresh the access token
     */
    String refreshToken();

    /**
     * @return the scope of the access token
     */
    String scope();

    /**
     * @return time in seconds that the token remains valid
     */
    int tokenExpirationTimeInSeconds();



    /**
     * Grouping of build task definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of the build task definition.
         */
        interface Blank extends WithOSType {
        }

        /**
         * The stage of the BuildTask definition allowing to specify the OS type required for the build.
         */
        interface WithOSType {
            /**
             * Specifies the OS type required for the build.
             *
             * @param osType the operating system type required for the build
             * @return the next stage of the definition
             */
            BuildTask.DefinitionStages.WithSourceControlType withOSType(OsType osType);
        }

        /**
         * The stage of the BuildTask definition allowing to specify the source control type.
         */
        interface WithSourceControlType {
            /**
             * Specifies full URL to the source code repository.
             *
             * @param sourceControlType full URL to the source code repository
             * @return the next stage of the definition
             */
            BuildTask.DefinitionStages.WithRepositoryUrl withSourceControlType(SourceControlType sourceControlType);
        }

        /**
         * The stage of the BuildTask definition allowing to specify the full URL to the source code repository.
         */
        interface WithRepositoryUrl {
            /**
             * Specifies the full URL to the source code repository.
             *
             * @param repositoryUrl the full URL to the source code repository
             * @return the next stage of the definition
             */
            BuildTask.DefinitionStages.WithAuthenticationTokenType withRepositoryUrl(String repositoryUrl);
        }

        /**
         * The stage of the BuildTask definition allowing to specify the type of the authentication token.
         */
        interface WithAuthenticationTokenType {
            /**
             * Specifies the type of the authentication token.
             *
             * @param tokenType the type of the authentication token
             * @return the next stage of the definition
             */
            BuildTask.DefinitionStages.WithAuthenticationToken withAuthenticationTokenType(TokenType tokenType);
        }

        /**
         * The stage of the BuildTask definition allowing to specify the authentication token.
         */
        interface WithAuthenticationToken {
            /**
             * Specifies the authentication token.
             *
             * @param token the authentication token
             * @return the next stage of the definition
             */
            BuildTask.DefinitionStages.WithAlias withAuthenticationToken(String token);
        }

        /**
         * The stage of the BuildTask definition allowing to specify the alternative name for a build task.
         */
        interface WithAlias {
            /**
             * Specifies the alternative name for a build task.
             *
             * @param alias the alternative name for a build task
             * @return the next stage of the definition
             */
            BuildTask.DefinitionStages.WithCreate withAlias(String alias);
        }

        /**
         * The stage of the BuildTask definition allowing to specify the authentication refresh token.
         */
        interface WithAuthenticationRefreshToken {
            /**
             * Specifies the authentication refresh token.
             *
             * @param refreshToken the authentication refresh token
             * @return the next stage of the definition
             */
            BuildTask.DefinitionStages.WithCreate withAuthenticationRefreshToken(String refreshToken);
        }

        /**
         * The stage of the BuildTask definition allowing to specify the time in seconds that the token remains valid.
         */
        interface WithTokenExpirationTimeInSeconds {
            /**
             * Specifies the time in seconds that the token remains valid.
             *
             * @param expirationTimeInSeconds the time in seconds that the token remains valid
             * @return the next stage of the definition
             */
            BuildTask.DefinitionStages.WithCreate withTokenExpirationTimeInSeconds(int expirationTimeInSeconds);
        }

        /**
         * The stage of the BuildTask definition allowing to specify the scope of the access token.
         */
        interface WithAuthenticationTokenAccessScope {
            /**
             * Specifies the scope of the access token.
             *
             * @param scope the scope of the access token
             * @return the next stage of the definition
             */
            BuildTask.DefinitionStages.WithCreate withAuthenticationTokenTypeAccessScope(String scope);
        }

        /**
         * The stage of the BuildTask definition allowing to specify whether the source control commit trigger is enabled or not.
         */
        interface WithCommitTrigger {
            /**
             * Specifies that the source control commit trigger is enabled.
             *
             * @return the next stage of the definition
             */
            BuildTask.DefinitionStages.WithCreate withCommitTriggerEnabled();

            /**
             * Specifies that the source control commit trigger is disabled.
             *
             * @return the next stage of the definition
             */
            BuildTask.DefinitionStages.WithCreate withCommitTriggerDisabled();
        }

        /**
         * The stage of the BuildTask definition allowing to specify the CPU configuration required for the build.
         */
        interface WithCpuCoresCount {
            /**
             * Specifies the CPU configuration in terms of number of cores required for the build.
             *
             * @param count the CPU configuration in terms of number of cores required for the build
             * @return the next stage of the definition
             */
            BuildTask.DefinitionStages.WithCreate withCpuCoresCount(int count);
        }

        /**
         * The stage of the BuildTask definition allowing to specify the build timeout.
         */
        interface WithBuildTimeoutInSeconds {
            /**
             * Specifies the build timeout.
             *
             * @param buildTimeoutInSeconds the build timeout in seconds
             * @return the next stage of the definition
             */
            BuildTask.DefinitionStages.WithCreate withBuildTimeoutInSeconds(int buildTimeoutInSeconds);
        }

        /**
         * The stage of the BuildTask definition allowing to specify current status of build task.
         */
        interface WithBuildTaskStatus {
            /**
             * Specifies that the current status of build task is enabled.
             *
             * @return the next stage of the definition
             */
            BuildTask.DefinitionStages.WithCreate withBuildTaskStatusEnabled();

            /**
             * Specifies that the current status of build task is disabled.
             *
             * @return the next stage of the definition
             */
            BuildTask.DefinitionStages.WithCreate withBuildTaskStatusDisabled();
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be created,
         *  but also allows for any other optional settings to be specified.
         */
        interface WithCreate extends
            BuildTask.DefinitionStages.WithAuthenticationRefreshToken,
            BuildTask.DefinitionStages.WithTokenExpirationTimeInSeconds,
            BuildTask.DefinitionStages.WithAuthenticationTokenAccessScope,
            BuildTask.DefinitionStages.WithCommitTrigger,
            BuildTask.DefinitionStages.WithCpuCoresCount,
            BuildTask.DefinitionStages.WithBuildTimeoutInSeconds,
            BuildTask.DefinitionStages.WithBuildTaskStatus,
            DefinitionWithTags<BuildTask.DefinitionStages.WithCreate>,
            Creatable<BuildTask> {
        }
    }

    /**
     * Grouping of the container register build task definitions.
     */
    interface BuildTaskDefinition extends
        BuildTask.DefinitionStages.Blank,
        BuildTask.DefinitionStages.WithOSType,
        BuildTask.DefinitionStages.WithSourceControlType,
        BuildTask.DefinitionStages.WithRepositoryUrl,
        BuildTask.DefinitionStages.WithAuthenticationTokenType,
        BuildTask.DefinitionStages.WithAuthenticationToken,
        BuildTask.DefinitionStages.WithAlias,
        BuildTask.DefinitionStages.WithAuthenticationRefreshToken,
        BuildTask.DefinitionStages.WithTokenExpirationTimeInSeconds,
        BuildTask.DefinitionStages.WithAuthenticationTokenAccessScope,
        BuildTask.DefinitionStages.WithCommitTrigger,
        BuildTask.DefinitionStages.WithCpuCoresCount,
        BuildTask.DefinitionStages.WithBuildTimeoutInSeconds,
        BuildTask.DefinitionStages.WithBuildTaskStatus,
        BuildTask.DefinitionStages.WithCreate {
    }


    /**
     * The entirety of a build task update.
     */
    interface Update extends
        BuildTask.UpdateStages.WithOSType,
        BuildTask.UpdateStages.WithSourceControlType,
        BuildTask.UpdateStages.WithRepositoryUrl,
        BuildTask.UpdateStages.WithAuthenticationTokenType,
        BuildTask.UpdateStages.WithAuthenticationToken,
        BuildTask.UpdateStages.WithAlias,
        BuildTask.UpdateStages.WithAuthenticationRefreshToken,
        BuildTask.UpdateStages.WithTokenExpirationTimeInSeconds,
        BuildTask.UpdateStages.WithAuthenticationTokenAccessScope,
        BuildTask.UpdateStages.WithCommitTrigger,
        BuildTask.UpdateStages.WithCpuCoresCount,
        BuildTask.UpdateStages.WithBuildTimeoutInSeconds,
        BuildTask.UpdateStages.WithBuildTaskStatus,
        Resource.UpdateWithTags<Update>,
        Appliable<BuildTask> {
    }

    /**
     * Grouping of build task update stages.
     */
    interface UpdateStages {
        /**
         * The stage of the BuildTask update definition allowing to specify the OS type required for the build.
         */
        interface WithOSType {
            /**
             * Specifies the OS type required for the build.
             *
             * @param osType the operating system type required for the build
             * @return the next stage of the definition
             */
            BuildTask.Update withOSType(OsType osType);
        }

        /**
         * The stage of the BuildTask update definition allowing to specify the source control type.
         */
        interface WithSourceControlType {
            /**
             * Specifies full URL to the source code repository.
             *
             * @param sourceControlType full URL to the source code repository
             * @return the next stage of the definition
             */
            BuildTask.Update withSourceControlType(SourceControlType sourceControlType);
        }

        /**
         * The stage of the BuildTask update definition allowing to specify the full URL to the source code repository.
         */
        interface WithRepositoryUrl {
            /**
             * Specifies the full URL to the source code repository.
             *
             * @param repositoryUrl the full URL to the source code repository
             * @return the next stage of the definition
             */
            BuildTask.Update withRepositoryUrl(String repositoryUrl);
        }

        /**
         * The stage of the BuildTask update definition allowing to specify the type of the authentication token.
         */
        interface WithAuthenticationTokenType {
            /**
             * Specifies the type of the authentication token.
             *
             * @param tokenType the type of the authentication token
             * @return the next stage of the definition
             */
            BuildTask.Update withAuthenticationTokenType(TokenType tokenType);
        }

        /**
         * The stage of the BuildTask update definition allowing to specify the authentication token.
         */
        interface WithAuthenticationToken {
            /**
             * Specifies the authentication token.
             *
             * @param token the authentication token
             * @return the next stage of the definition
             */
            BuildTask.Update withAuthenticationToken(String token);
        }

        /**
         * The stage of the BuildTask update definition allowing to specify the alternative name for a build task.
         */
        interface WithAlias {
            /**
             * Specifies the alternative name for a build task.
             *
             * @param alias the alternative name for a build task
             * @return the next stage of the definition
             */
            BuildTask.Update withAlias(String alias);
        }

        /**
         * The stage of the BuildTask update definition allowing to specify the authentication refresh token.
         */
        interface WithAuthenticationRefreshToken {
            /**
             * Specifies the authentication refresh token.
             *
             * @param refreshToken the authentication refresh token
             * @return the next stage of the definition
             */
            BuildTask.Update withAuthenticationRefreshToken(String refreshToken);
        }

        /**
         * The stage of the BuildTask update definition allowing to specify the time in seconds that the token remains valid.
         */
        interface WithTokenExpirationTimeInSeconds {
            /**
             * Specifies the time in seconds that the token remains valid.
             *
             * @param expirationTimeInSeconds the time in seconds that the token remains valid
             * @return the next stage of the definition
             */
            BuildTask.Update withTokenExpirationTimeInSeconds(int expirationTimeInSeconds);
        }

        /**
         * The stage of the BuildTask update definition allowing to specify the scope of the access token.
         */
        interface WithAuthenticationTokenAccessScope {
            /**
             * Specifies the scope of the access token.
             *
             * @param scope the scope of the access token
             * @return the next stage of the definition
             */
            BuildTask.Update withAuthenticationTokenTypeAccessScope(String scope);
        }

        /**
         * The stage of the BuildTask update definition allowing to specify whether the source control commit trigger is enabled or not.
         */
        interface WithCommitTrigger {
            /**
             * Specifies that the source control commit trigger is enabled.
             *
             * @return the next stage of the definition
             */
            BuildTask.Update withCommitTriggerEnabled();

            /**
             * Specifies that the source control commit trigger is disabled.
             *
             * @return the next stage of the definition
             */
            BuildTask.Update withCommitTriggerDisabled();
        }

        /**
         * The stage of the BuildTask update definition allowing to specify the CPU configuration required for the build.
         */
        interface WithCpuCoresCount {
            /**
             * Specifies the CPU configuration in terms of number of cores required for the build.
             *
             * @param count the CPU configuration in terms of number of cores required for the build
             * @return the next stage of the definition
             */
            BuildTask.Update withCpuCoresCount(int count);
        }

        /**
         * The stage of the BuildTask update definition allowing to specify the build timeout.
         */
        interface WithBuildTimeoutInSeconds {
            /**
             * Specifies the build timeout.
             *
             * @param buildTimeoutInSeconds the build timeout in seconds
             * @return the next stage of the definition
             */
            BuildTask.Update withBuildTimeoutInSeconds(int buildTimeoutInSeconds);
        }

        /**
         * The stage of the BuildTask update definition allowing to specify current status of build task.
         */
        interface WithBuildTaskStatus {
            /**
             * Specifies that the current status of build task is enabled.
             *
             * @return the next stage of the definition
             */
            BuildTask.Update withBuildTaskStatusEnabled();

            /**
             * Specifies that the current status of build task is disabled.
             *
             * @return the next stage of the definition
             */
            BuildTask.Update withBuildTaskStatusDisabled();
        }
    }
}
