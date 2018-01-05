/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.batchai.implementation.BatchAIManager;
import com.microsoft.azure.management.batchai.implementation.FileInner;
import com.microsoft.azure.management.batchai.implementation.JobInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasParent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.IndependentChildResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import rx.Completable;
import rx.Observable;

/**
 * Client-side representation of Batch AI Job object, associated with Batch AI Cluster.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_6_0)
public interface BatchAIJob extends
        IndependentChildResource<BatchAIManager, JobInner>,
        Refreshable<BatchAIJob>,
        HasParent<BatchAICluster> {

    @Method
    void terminate();

    @Method
    Completable terminateAsync();

    @Method
    PagedList<OutputFile> listFiles(String outputDirectoryId);

    @Method
    Observable<OutputFile> listFilesAsync(String outputDirectoryId);

    /**
     * The entirety of the Batch AI job definition.
     */
    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.WithStdOutErrPathPrefix,
            DefinitionStages.WithNodeCount,
            DefinitionStages.WithToolType,
            DefinitionStages.WithCreate {
    }

    /**
     * Grouping of Batch AI job definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of Batch AI job definition.
         */
        interface Blank extends DefinitionWithRegion<WithNodeCount> {
        }

        /**
         * The stage of the setup task definition allowing to specify where Batch AI will upload stdout and stderr of the job.
         */
        interface WithStdOutErrPathPrefix {
            /**
             * @param stdOutErrPathPrefix the path where the Batch AI service will upload the stdout and stderror of the job
             * @return the next stage of the definition
             */
            WithToolType withStdOutErrPathPrefix(String stdOutErrPathPrefix);
        }

        /**
         * The stage of the definition allowing to specify number of compute nodes to run the job on.
         * The job will be gang scheduled on that many compute nodes.
         */
        interface WithNodeCount {
            /**
             * @param nodeCount number of nodes
             * @return the next staeg of the definition
             */
            WithStdOutErrPathPrefix withNodeCount(int nodeCount);
        }

        interface WithToolType {
            ToolTypeSettings.CognitiveToolkit.DefinitionStages.Blank<WithCreate> defineCognitiveToolkit();

            ToolTypeSettings.TensorFlow.DefinitionStages.Blank<WithCreate> defineTensorflow();

            ToolTypeSettings.Caffe.DefinitionStages.Blank<WithCreate> defineCaffe();

            ToolTypeSettings.Caffe2.DefinitionStages.Blank<WithCreate> defineCaffe2();

            ToolTypeSettings.Chainer.DefinitionStages.Blank<WithCreate> defineChainer();

            WithCreate withCustomCommandLine(String commandLine);
        }

        interface WithInputDirectory {
            WithCreate withInputDirectory(String id, String path);
        }

        interface WithOutputDirectory {
            WithCreate withOutputDirectory(String id, String pathPrefix);
        }

        /**
         * Specifies the command line to be executed before tool kit is launched.
         * The specified actions will run on all the nodes that are part of the job.
         */
        interface WithJobPreparation {
            /**
             * @param commandLine command line to execute
             * @return the next stage of the definition
             */
            WithCreate withCommandLine(String commandLine);
        }

        /**
         * Specifies details of the container registry and image such as name, URL and credentials.
         */
        interface WithContainerSettings {
            /**
             *
             * @param image the name of the image in image repository
             * @return the next stage of the definition
             */
            WithCreate withContainerImage(String image);
        }
        /**
         * The stage of a virtual network gateway connection definition with sufficient inputs to create a new connection in the cloud,
         * but exposing additional optional settings to specify.
         */
        interface WithCreate extends
                Creatable<BatchAIJob>,
                Resource.DefinitionWithTags<WithCreate>,
                WithJobPreparation,
                WithInputDirectory,
                WithOutputDirectory,
                WithContainerSettings {
        }
    }
}
