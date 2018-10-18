///**
// * Copyright (c) Microsoft Corporation. All rights reserved.
// * Licensed under the MIT License. See License.txt in the project root for
// * license information.
// */
//package com.microsoft.azure.management.containerregistry;
//
//import com.microsoft.azure.management.apigeneration.Beta;
//import com.microsoft.azure.management.apigeneration.Fluent;
//import com.microsoft.azure.management.containerregistry.implementation.BuildStepInner;
//import com.microsoft.azure.management.resources.fluentcore.arm.models.ExternalChildResource;
//import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
//import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
//import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
//import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
//import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
//import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
//import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
//
///**
// * An object that represents a build step for a build task in a container registry.
// */
//@Fluent
//@Beta(Beta.SinceVersion.V1_14_0)
//public interface BuildStep extends
//    ExternalChildResource<BuildStep, BuildTask>,
//    HasName,
//    HasId,
//    HasInner<BuildStepInner>,
//    Refreshable<BuildStep>,
//    Updatable<BuildStep.Update> {
//
//    /**
//     * @return the provisioning state value
//     */
//    ProvisioningState provisioningState();
//
//
//
//    /**
//     * Grouping of build task build steps definition stages.
//     */
//    interface BuildTaskBuildStepsDefinitionStages {
//        /**
//         * The first stage of the build task build steps definition.
//         */
//        interface Blank extends WithCreate {
//        }
//
//        /**
//         * The stage of the build task build steps definition allowing to specify the branch.
//         */
//        interface WithBranch {
//            /**
//             * Specifies the branch required for the build.
//             *
//             * @param branch the branch settings required for the build
//             * @return the next stage of the definition
//             */
//            BuildTaskBuildStepsDefinitionStages.WithCreate withBranch(String branch);
//        }
//
//        /**
//         * The stage of the build task build steps definition allowing to specify the relative context path for a docker build in the source.
//         */
//        interface WithContext {
//            /**
//             * Specifies the relative context path for a docker build in the source required for the build.
//             *
//             * @param contextPath the relative context path for a docker build in the source required for the build
//             * @return the next stage of the definition
//             */
//            BuildTaskBuildStepsDefinitionStages.WithCreate withContextPath(String contextPath);
//        }
//
//        /**
//         * The stage of the build task build steps definition allowing to specify the Docker file path.
//         */
//        interface WithDockerFilePath {
//            /**
//             * Specifies the Docker file path required for the build.
//             *
//             * @param dockerFilePath the Docker file path required for the build
//             * @return the next stage of the definition
//             */
//            BuildTaskBuildStepsDefinitionStages.WithCreate withDockerFilePath(String dockerFilePath);
//        }
//
//        /**
//         * The stage of the build task build steps definition allowing to specify the image names for the build.
//         */
//        interface WithImageNames {
//            /**
//             * Specifies the image names for the build.
//             *
//             * @param imageNames the image names to be used by the build
//             * @return the next stage of the definition
//             */
//            BuildTaskBuildStepsDefinitionStages.WithCreate withImageNames(String... imageNames);
//        }
//
//        /**
//         * The stage of the build task build steps definition allowing to specify if the image cache is enabled or not.
//         */
//        interface WithImageCache {
//            /**
//             * Specifies that the image cache is enabled.
//             *
//             * @return the next stage of the definition
//             */
//            BuildTaskBuildStepsDefinitionStages.WithCreate withImageCacheEnabled();
//
//            /**
//             * Specifies that the image cache is disabled.
//             *
//             * @return the next stage of the definition
//             */
//            BuildTaskBuildStepsDefinitionStages.WithCreate withImageCacheDisabled();
//        }
//
//        /**
//         * The stage of the build task build steps definition allowing to specify if the image built should be pushed to the registry or not.
//         */
//        interface WithImagePush {
//            /**
//             * Specifies that the image built should be pushed to the registry.
//             *
//             * @return the next stage of the definition
//             */
//            BuildTaskBuildStepsDefinitionStages.WithCreate withImagePushEnabled();
//
//            /**
//             * Specifies that the image built should not be pushed to the registry.
//             *
//             * @return the next stage of the definition
//             */
//            BuildTaskBuildStepsDefinitionStages.WithCreate withImagePushDisabled();
//        }
//
//        /**
//         * The stage of the queued build definition allowing to specify the build arguments.
//         */
//        interface WithBuildArgument {
//            /**
//             * Specifies the build argument settings.
//             *
//             * <p>
//             * @param name the name of the build argument
//             * @param value the value of the build argument
//             * @param secrecy the secrecy of the build argument
//             * @return the next stage of the definition
//             */
//            BuildTaskBuildStepsDefinitionStages.WithCreate buildArgument(String name, String value, boolean secrecy);
//        }
//
//        /**
//         * The stage of the definition which contains all the minimum required inputs for the resource to be created,
//         *  but also allows for any other optional settings to be specified.
//         */
//        interface WithCreate extends
//            BuildTaskBuildStepsDefinitionStages.WithBranch,
//            BuildTaskBuildStepsDefinitionStages.WithContext,
//            BuildTaskBuildStepsDefinitionStages.WithDockerFilePath,
//            BuildTaskBuildStepsDefinitionStages.WithImageNames,
//            BuildTaskBuildStepsDefinitionStages.WithImageCache,
//            BuildTaskBuildStepsDefinitionStages.WithImagePush,
//            BuildTaskBuildStepsDefinitionStages.WithBuildArgument,
//            Creatable<BuildStep> {
//        }
//    }
//
//    /**
//     * Grouping of the container register queued quick build definitions.
//     */
//    interface BuildTaskBuildStepsDefinition extends
//        BuildStep.BuildTaskBuildStepsDefinitionStages.Blank,
//        BuildStep.BuildTaskBuildStepsDefinitionStages.WithBranch,
//        BuildStep.BuildTaskBuildStepsDefinitionStages.WithContext,
//        BuildStep.BuildTaskBuildStepsDefinitionStages.WithDockerFilePath,
//        BuildStep.BuildTaskBuildStepsDefinitionStages.WithImageNames,
//        BuildStep.BuildTaskBuildStepsDefinitionStages.WithImageCache,
//        BuildStep.BuildTaskBuildStepsDefinitionStages.WithImagePush,
//        BuildStep.BuildTaskBuildStepsDefinitionStages.WithBuildArgument,
//        BuildStep.BuildTaskBuildStepsDefinitionStages.WithCreate {
//    }
//
//    /**
//     * The entirety of a build task update.
//     */
//    interface Update extends
//        BuildStep.UpdateStages.WithBranch,
//        BuildStep.UpdateStages.WithContext,
//        BuildStep.UpdateStages.WithDockerFilePath,
//        BuildStep.UpdateStages.WithImageNames,
//        BuildStep.UpdateStages.WithImageCache,
//        BuildStep.UpdateStages.WithImagePush,
//        BuildStep.UpdateStages.WithBuildArgument,
//        Appliable<BuildStep> {
//    }
//
//    /**
//     * Grouping of queued build update stages.
//     */
//    interface UpdateStages {
//        /**
//         * The stage of the build task build steps update definition allowing to specify the branch.
//         */
//        interface WithBranch {
//            /**
//             * Specifies the branch required for the build.
//             *
//             * @param branch the branch settings required for the build
//             * @return the next stage of the definition
//             */
//            BuildStep.Update withBranch(String branch);
//        }
//
//        /**
//         * The stage of the build task build steps update definition allowing to specify the relative context path for a docker build in the source.
//         */
//        interface WithContext {
//            /**
//             * Specifies the relative context path for a docker build in the source required for the build.
//             *
//             * @param contextPath the relative context path for a docker build in the source required for the build
//             * @return the next stage of the definition
//             */
//            BuildStep.Update withContextPath(String contextPath);
//        }
//
//        /**
//         * The stage of the build task build steps update definition allowing to specify the Docker file path.
//         */
//        interface WithDockerFilePath {
//            /**
//             * Specifies the Docker file path required for the build.
//             *
//             * @param dockerFilePath the Docker file path required for the build
//             * @return the next stage of the definition
//             */
//            BuildStep.Update withDockerFilePath(String dockerFilePath);
//        }
//
//        /**
//         * The stage of the build task build steps update definition allowing to specify the image names for the build.
//         */
//        interface WithImageNames {
//            /**
//             * Specifies the image names for the build.
//             *
//             * @param imageNames the image names to be used by the build
//             * @return the next stage of the definition
//             */
//            BuildStep.Update withImageNames(String... imageNames);
//        }
//
//        /**
//         * The stage of the build task build steps update definition allowing to specify if the image cache is enabled or not.
//         */
//        interface WithImageCache {
//            /**
//             * Specifies that the image cache is enabled.
//             *
//             * @return the next stage of the definition
//             */
//            BuildStep.Update withImageCacheEnabled();
//
//            /**
//             * Specifies that the image cache is disabled.
//             *
//             * @return the next stage of the definition
//             */
//            BuildStep.Update withImageCacheDisabled();
//        }
//
//        /**
//         * The stage of the build task build steps update definition allowing to specify if the image built should be pushed to the registry or not.
//         */
//        interface WithImagePush {
//            /**
//             * Specifies that the image built should be pushed to the registry.
//             *
//             * @return the next stage of the definition
//             */
//            BuildStep.Update withImagePushEnabled();
//
//            /**
//             * Specifies that the image built should not be pushed to the registry.
//             *
//             * @return the next stage of the definition
//             */
//            BuildStep.Update withImagePushDisabled();
//        }
//
//        /**
//         * The stage of the queued build update definition allowing to specify the build arguments.
//         */
//        interface WithBuildArgument {
//            /**
//             * Specifies the build argument settings.
//             *
//             * <p>
//             * @param name the name of the build argument
//             * @param value the value of the build argument
//             * @param secrecy the secrecy of the build argument
//             * @return the next stage of the definition
//             */
//            BuildStep.Update buildArgument(String name, String value, boolean secrecy);
//        }
//    }
//}
