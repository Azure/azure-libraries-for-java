/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai;

import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.compute.VirtualMachineSizeTypes;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.azure.management.storage.StorageAccountKey;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.file.CloudFileShare;
import com.microsoft.rest.LogLevel;

import java.io.File;

public final class ManageBatchAI {
    /**
     * Main function which runs the actual sample.
     *
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final Region region = Region.US_WEST2;
        final String rgName = SdkContext.randomResourceName("rg", 20);
        final String saName = SdkContext.randomResourceName("sa", 20);
        final String shareName = SdkContext.randomResourceName("fs", 20);
        final String clusterName = SdkContext.randomResourceName("cluster", 15);
        final String userName = "tirekicker";
        try {
            //=============================================================
            // Create a new storage account and an Azure file share resource

            StorageAccount storageAccount = azure.storageAccounts().define(saName)
                    .withRegion(region)
                    .withNewResourceGroup(rgName)
                    .create();

            StorageAccountKey storageAccountKey = storageAccount.getKeys().get(0);

            CloudFileShare cloudFileShare = CloudStorageAccount.parse(String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net",
                    saName, storageAccountKey.value()))
                    .createCloudFileClient()
                    .getShareReference(shareName);
            cloudFileShare.create();

            BatchAICluster cluster = azure.clusters().define(clusterName)
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
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Deleting Resource Group: " + rgName);
//                azure.resourceGroups().beginDeleteByName(rgName);
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