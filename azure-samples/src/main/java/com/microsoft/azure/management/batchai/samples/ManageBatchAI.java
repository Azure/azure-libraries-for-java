/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.samples;

import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.batchai.BatchAICluster;
import com.microsoft.azure.management.batchai.BatchAIJob;
import com.microsoft.azure.management.batchai.ExecutionState;
import com.microsoft.azure.management.batchai.OutputFile;
import com.microsoft.azure.management.compute.VirtualMachineSizeTypes;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.azure.management.storage.StorageAccountKey;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.file.CloudFileDirectory;
import com.microsoft.azure.storage.file.CloudFileShare;
import com.microsoft.rest.LogLevel;

import java.io.File;

/**
 * Azure Batch AI sample.
 *  - Create Storage account and Azure file share
 *  - Upload sample data to Azure file share
 *  - Create Batch AI cluster that uses Azure file share to host the training data and scripts for the learning job
 *  - Create Microsoft Cognitive Toolkit job to run on the cluster
 *  - Wait for job to complete
 *  - Get output files
 *
 * Please note: in order to run this sample, please download and unzip sample package from here: https://batchaisamples.blob.core.windows.net/samples/BatchAIQuickStart.zip?st=2017-09-29T18%3A29%3A00Z&amp;se=2099-12-31T08%3A00%3A00Z&amp;sp=rl&amp;sv=2016-05-31&amp;sr=b&amp;sig=hrAZfbZC%2BQ%2FKccFQZ7OC4b%2FXSzCF5Myi4Cj%2BW3sVZDo%3D
 * Export path to the content to $SAMPLE_DATA_PATH.
 */
public final class ManageBatchAI {
    /**
     * Main function which runs the actual sample.
     *
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final String sampleDataPath = System.getenv("SAMPLE_DATA_PATH");
        final Region region = Region.EUROPE_WEST;
        final String rgName = SdkContext.randomResourceName("rg", 20);
        final String saName = SdkContext.randomResourceName("sa", 20);
        final String shareName = SdkContext.randomResourceName("fs", 20);
        final String clusterName = SdkContext.randomResourceName("cluster", 15);
        final String userName = "tirekicker";
        final String sharePath = "mnistcntksample";
        try {
            //=============================================================
            // Create a new storage account and an Azure file share resource
            System.out.println("Creating a storage account...");
            StorageAccount storageAccount = azure.storageAccounts().define(saName)
                    .withRegion(region)
                    .withNewResourceGroup(rgName)
                    .create();
            System.out.println("Created storage account.");

            StorageAccountKey storageAccountKey = storageAccount.getKeys().get(0);

            CloudFileShare cloudFileShare = CloudStorageAccount.parse(String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net",
                    saName, storageAccountKey.value()))
                    .createCloudFileClient()
                    .getShareReference(shareName);
            cloudFileShare.create();

            //=============================================================
            // Upload sample data to Azure file share

            //Get a reference to the root directory for the share.
            CloudFileDirectory rootDir = cloudFileShare.getRootDirectoryReference();

            //Get a reference to the sampledir directory
            CloudFileDirectory sampleDir = rootDir.getDirectoryReference(sharePath);
            sampleDir.create();

            sampleDir.getFileReference("Train-28x28_cntk_text.txt").uploadFromFile(sampleDataPath + "/Train-28x28_cntk_text.txt");
            sampleDir.getFileReference("Test-28x28_cntk_text.txt").uploadFromFile(sampleDataPath + "/Test-28x28_cntk_text.txt");
            sampleDir.getFileReference("ConvNet_MNIST.py").uploadFromFile(sampleDataPath + "/ConvNet_MNIST.py");

            //=============================================================
            // Create Batch AI cluster that uses Azure file share to host the training data and scripts for the learning job
            System.out.println("Creating Batch AI cluster...");
            BatchAICluster cluster = azure.batchAIClusters().define(clusterName)
                    .withRegion(region)
                    .withNewResourceGroup(rgName)
                    .withVMSize(VirtualMachineSizeTypes.STANDARD_NC6.toString())
                    .withUserName(userName)
                    .withPassword("MyPassword")
                    .withAutoScale(0, 2)
                    .defineAzureFileShare()
                        .withStorageAccountName(saName)
                        .withAzureFileUrl(cloudFileShare.getUri().toString())
                        .withRelativeMountPath("azurefileshare")
                        .withAccountKey(storageAccountKey.value())
                        .attach()
                    .create();
            System.out.println("Created Batch AI cluster.");
            Utils.print(cluster);

            // =============================================================
            // Create Microsoft Cognitive Toolkit job to run on the cluster
            System.out.println("Creating Batch AI job...");
            BatchAIJob job = cluster.jobs().define("myJob")
                    .withRegion(region)
                    .withNodeCount(1)
                    .withStdOutErrPathPrefix("$AZ_BATCHAI_MOUNT_ROOT/azurefileshare")
                    .defineCognitiveToolkit()
                        .withPythonScriptFile("$AZ_BATCHAI_INPUT_SAMPLE/ConvNet_MNIST.py")
                        .withCommandLineArgs("$AZ_BATCHAI_INPUT_SAMPLE $AZ_BATCHAI_OUTPUT_MODEL")
                        .attach()
                    .withInputDirectory("SAMPLE", "$AZ_BATCHAI_MOUNT_ROOT/azurefileshare/" + sharePath)
                    .withOutputDirectory("MODEL", "$AZ_BATCHAI_MOUNT_ROOT/azurefileshare/model")
                    .withContainerImage("microsoft/cntk:2.1-gpu-python3.5-cuda8.0-cudnn6.0")
                    .create();
            System.out.println("Created Batch AI job.");
            Utils.print(job);

            // =============================================================
            // Wait for job results

            // Wait for job to start running
            System.out.println("Waiting for Batch AI job to start running...");
            while (ExecutionState.QUEUED.equals(job.executionState())) {
                SdkContext.sleep(5000);
                job.refresh();
            }

            // Wait for job to complete and job output to become available
            System.out.println("Waiting for Batch AI job to complete...");
            while (!(ExecutionState.SUCCEEDED.equals(job.executionState()) || ExecutionState.FAILED.equals(job.executionState()))) {
                SdkContext.sleep(5000);
                job.refresh();
            }

            // =============================================================
            // Get output files

            // Print stdout and stderr
            for (OutputFile outputFile : job.listFiles("stdouterr")) {
                System.out.println(Utils.curl(outputFile.downloadUrl()));
            }
            // List model output files
            for (OutputFile outputFile : job.listFiles("MODEL")) {
                System.out.println(outputFile.downloadUrl());
            }

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Deleting Resource Group: " + rgName);
                azure.resourceGroups().beginDeleteByName(rgName);
            } catch (NullPointerException npe) {
                System.out.println("Did not create any resources in Azure. No clean up is necessary");
            } catch (Exception g) {
                g.printStackTrace();
            }
        }

        return false;
    }

    /**
     * Main entry point.
     *
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {
            //=============================================================
            // Authenticate

            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));

            Azure azure = Azure.configure()
                    .withLogLevel(LogLevel.BODY)
                    .authenticate(credFile)
                    .withDefaultSubscription();

            // Print selected subscription
            System.out.println("Selected subscription: " + azure.subscriptionId());

            runSample(azure);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private ManageBatchAI() {
    }
}