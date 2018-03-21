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
import com.microsoft.azure.management.batchai.implementation.JobInner;
import com.microsoft.azure.management.batchai.model.HasMountVolumes;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasParent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.IndependentChildResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import org.joda.time.DateTime;
import rx.Completable;
import rx.Observable;

import java.util.List;

/**
 * Client-side representation of Batch AI Job object, associated with Batch AI Cluster.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_6_0)
public interface BatchAIJob extends
        IndependentChildResource<BatchAIManager, JobInner>,
        Refreshable<BatchAIJob>,
        HasParent<BatchAICluster> {

    /**
     * Terminates a job.
     */
    @Method
    void terminate();

    /**
     * Terminates a job.
     * @return a representation of the deferred computation of this call
     */
    @Method
    Completable terminateAsync();

    /**
     * List all files inside the given output directory (Only if the output directory is on Azure File Share or Azure Storage container).
     * @param outputDirectoryId Id of the job output directory. This is the OutputDirectory--&gt;id
     * parameter that is given by the user during Create Job.
     * @return list of files inside the given output directory
     */
    @Method
    PagedList<OutputFile> listFiles(String outputDirectoryId);

    /**
     * List all files inside the given output directory (Only if the output directory is on Azure File Share or Azure Storage container).
     * @param outputDirectoryId Id of the job output directory. This is the OutputDirectory--&gt;id
     * parameter that is given by the user during Create Job.
     * @return an observable that emits output file information
     */
    @Method
    Observable<OutputFile> listFilesAsync(String outputDirectoryId);

    /**
     * List all files inside the given output directory (Only if the output directory is on Azure File Share or Azure Storage container).
     * @param outputDirectoryId Id of the job output directory. This is the OutputDirectory--&gt;id parameter that is given by the user during Create Job.
     * @param directory the path to the directory
     * @param linkExpiryMinutes the number of minutes after which the download link will expire
     * @param maxResults the maximum number of items to return in the response. A maximum of 1000 files can be returned
     * @return list of files inside the given output directory
     */
    @Method
    PagedList<OutputFile> listFiles(String outputDirectoryId, String directory, Integer linkExpiryMinutes, Integer maxResults);

    /**
     * List all files inside the given output directory (Only if the output directory is on Azure File Share or Azure Storage container).
     * @param outputDirectoryId Id of the job output directory. This is the OutputDirectory--&gt;id parameter that is given by the user during Create Job.
     * @param directory the path to the directory
     * @param linkExpiryMinutes the number of minutes after which the download link will expire
     * @param maxResults the maximum number of items to return in the response. A maximum of 1000 files can be returned
     * @return an observable that emits output file information
     */
    @Method
    Observable<OutputFile> listFilesAsync(String outputDirectoryId, String directory, Integer linkExpiryMinutes, Integer maxResults);

    /**
     * @return the experiment information of the job.
     */
    String experimentName();

    /**
     * @return priority associated with the job. Priority values can range from -1000
     * to 1000, with -1000 being the lowest priority and 1000 being the highest
     * priority. The default value is 0.
     */
    Integer priority();

    /**
     * @return  the Id of the cluster on which this job will run.
     */
    ResourceId cluster();

    /**
     * @return information on mount volumes to be used by the job.
     * These volumes will be mounted before the job execution and will be
     * unmouted after the job completion. The volumes will be mounted at
     * location specified by $AZ_BATCHAI_JOB_MOUNT_ROOT environment variable.
     */
    MountVolumes mountVolumes();

    /**
     * @return a segment of job's output directories path created by BatchAI.
     * Batch AI creates job's output directories under an unique path to avoid
     * conflicts between jobs. This value contains a path segment generated by
     * Batch AI to make the path unique and can be used to find the output
     * directory on the node or mounted filesystem.
     */
    String jobOutputDirectoryPathSegment();

    /**
     * @return number of compute nodes to run the job on. The job will be gang scheduled on that many compute nodes.
     */
    int nodeCount();

    /**
     * @return the settings for the container to run the job. If not provided, the job will run on the VM.
     */
    ContainerSettings containerSettings();

    /**
     * @return The toolkit type of this job
     */
    ToolType toolType();

    /**
     * @return the settings for CNTK (aka Microsoft Cognitive Toolkit) job
     */
    CNTKsettings cntkSettings();

    /**
     * @return the settings for pyTorch job
     */
    PyTorchSettings pyTorchSettings();

    /**
     * @return the settings for Tensor Flow job
     */
    TensorFlowSettings tensorFlowSettings();

    /**
     * @return the settings for Caffe job.
     */
    CaffeSettings caffeSettings();

    /**
     * @return the settings for Chainer job.
     */
    ChainerSettings chainerSettings();

    /**
     * @return the settings for custom tool kit job
     */
    CustomToolkitSettings customToolkitSettings();

    /**
     * @return the actions to be performed before tool kit is launched.
     * The specified actions will run on all the nodes that are part of the
     * job.
     */
    JobPreparation jobPreparation();

    /**
     * @return the path where the Batch AI service will upload stdout and stderror of the job.
     */
    String stdOutErrPathPrefix();

    /**
     * @return the list of input directories for the Job
     */
    List<InputDirectory> inputDirectories();

    /**
     * @return the list of output directories where the models will be created
     */
    List<OutputDirectory> outputDirectories();

    /**
     * @return Additional environment variables to be passed to the job.
     * Batch AI services sets the following environment variables for all jobs:
     * AZ_BATCHAI_INPUT_id, AZ_BATCHAI_OUTPUT_id, AZ_BATCHAI_NUM_GPUS_PER_NODE,
     * For distributed TensorFlow jobs, following additional environment
     * variables are set by the Batch AI Service: AZ_BATCHAI_PS_HOSTS,
     * AZ_BATCHAI_WORKER_HOSTS.
     */
    List<EnvironmentVariable> environmentVariables();

    /**
     * @return environment variables with secret values to set on the job. Only names are reported,
     * server will never report values of these variables back.
     */
    List<EnvironmentVariableWithSecretValue> secrets();

    /**
     * @return constraints associated with the Job.
     */
    JobPropertiesConstraints constraints();

    /**
     * @return the creation time of the job
     */
    DateTime creationTime();

    /**
     * @return the provisioned state of the Batch AI job
     */
    ProvisioningState provisioningState();

    /**
     * The time at which the job entered its current provisioning state.
     * @return the time at which the job entered its current provisioning state
     */
    DateTime provisioningStateTransitionTime();

    /**
     * Gets the current state of the job. Possible values are: queued - The job is
     * queued and able to run. A job enters this state when it is created, or
     * when it is awaiting a retry after a failed run. running - The job is
     * running on a compute cluster. This includes job-level preparation such
     * as downloading resource files or set up container specified on the job -
     * it does not necessarily mean that the job command line has started
     * executing. terminating - The job is terminated by the user, the
     * terminate operation is in progress. succeeded - The job has completed
     * running succesfully and exited with exit code 0. failed - The job has
     * finished unsuccessfully (failed with a non-zero exit code) and has
     * exhausted its retry limit. A job is also marked as failed if an error
     * occurred launching the job.
     * @return the current state of the job
     */
    ExecutionState executionState();

    /**
     * @return the time at which the job entered its current execution state
     */
    DateTime executionStateTransitionTime();

    /**
     * @return  information about the execution of a job in the Azure Batch service.
     */
    JobPropertiesExecutionInfo executionInfo();

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
            @Method
            ToolTypeSettings.CognitiveToolkit.DefinitionStages.Blank<WithCreate> defineCognitiveToolkit();

            @Method
            ToolTypeSettings.TensorFlow.DefinitionStages.Blank<WithCreate> defineTensorflow();

            @Method
            ToolTypeSettings.Caffe.DefinitionStages.Blank<WithCreate> defineCaffe();

            @Method
            ToolTypeSettings.Caffe2.DefinitionStages.Blank<WithCreate> defineCaffe2();

            @Method
            ToolTypeSettings.Chainer.DefinitionStages.Blank<WithCreate> defineChainer();

            @Method
            @Beta(Beta.SinceVersion.V1_8_0)
            ToolTypeSettings.PyTorch.DefinitionStages.Blank<WithCreate> definePyTorch();

            WithCreate withCustomCommandLine(String commandLine);
        }

        interface WithInputDirectory {
            WithCreate withInputDirectory(String id, String path);
        }

        interface WithOutputDirectory {
            WithCreate withOutputDirectory(String id, String pathPrefix);

            /**
             * @param id The name for the output directory
             * @return the nest stage of the definition for output directory settings
             */
            @Method
            @Beta(Beta.SinceVersion.V1_8_0)
            OutputDirectorySettings.DefinitionStages.Blank<WithCreate> defineOutputDirectory(String id);
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
             * @param image the name of the image in image repository
             * @return the next stage of the definition
             */
            WithCreate withContainerImage(String image);

            ContainerImageSettings.DefinitionStages.Blank<BatchAIJob.DefinitionStages.WithCreate> defineContainerSettings(String image);
        }

        /**
         * Allows to specify the experiment information of the job.
         */
        interface WithExperimentName {
            /**
             * @param experimentName describes the experiment information of the job
             * @return the next stage of the definition
             */
            WithCreate withExperimentName(String experimentName);
        }

        /**
         * Allows to specify environment variables.
         */
        interface WithEnvironmentVariable {
            /**
             * @param name name of the variable to set
             * @param value value of the variable to set
             * @return the next stage of the definition
             */
            WithCreate withEnvironmentVariable(String name, String value);
        }

        /**
         * The stage of the Batch AI job definition allowing to specify environment variables with secrets.
         */
        interface WithEnvironmentVariableSecretValue {
            /**
             * Sets the value of the environment variable. This value will never be reported
             * back by Batch AI.
             * @param name name of the variable to set
             * @param value value of the variable to set
             * @return the next stage of the definition
             */
            WithCreate withEnvironmentVariableSecretValue(String name, String value);

            /**
             * Specifies KeyVault Store and Secret which contains the value for the
             * environment variable.
             * @param name name of the variable to set
             * @param keyVaultId fully qualified resource Id for the Key Vault
             * @param secretUrl the URL referencing a secret in a Key Vault
             * @return the next stage of the definition
             */
            WithCreate withEnvironmentVariableSecretValue(String name, String keyVaultId, String secretUrl);
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
                WithContainerSettings,
                WithExperimentName,
                WithEnvironmentVariable,
                WithEnvironmentVariableSecretValue,
                HasMountVolumes.DefinitionStages.WithMountVolumes {
        }
    }
}
