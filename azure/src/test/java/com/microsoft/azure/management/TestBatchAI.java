/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management;

import com.microsoft.azure.management.batchai.AzureFileShareReference;
import com.microsoft.azure.management.batchai.BatchAICluster;
import com.microsoft.azure.management.batchai.VmPriority;
import com.microsoft.azure.management.batchai.Workspace;
import com.microsoft.azure.management.batchai.Workspaces;
import com.microsoft.azure.management.compute.VirtualMachineSizeTypes;
import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.network.Networks;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.azure.management.storage.StorageAccounts;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.file.CloudFileShare;
import org.junit.Assert;

import static com.microsoft.azure.management.resources.core.TestBase.isPlaybackMode;

/**
 * Test of Batch AI management.
 */
public class TestBatchAI {
    private static final Region region = Region.US_EAST;

    public static class Basic extends TestTemplate<Workspace, Workspaces> {
        private StorageAccounts storageAccounts;
        private Networks networks;

        private final String clusterName = SdkContext.randomResourceName("cluster", 15);

        public Basic(StorageAccounts storageAccounts, Networks networks) {
            this.storageAccounts = storageAccounts;
            this.networks = networks;
        }

        @Override
        public Workspace createResource(Workspaces workspaces) throws Exception {
            final String groupName = SdkContext.randomResourceName("rg", 10);
            final String workspaceName = SdkContext.randomResourceName("ws", 10);
            final String vnetName = SdkContext.randomResourceName("vnet", 15);
            final String saName = SdkContext.randomResourceName("cluster", 15);
            final String shareName = "myfileshare";
            final String shareMountPath = "azurefileshare";
            final String blobFileSystemPath = "myblobsystem";
            final String containerName = "mycontainer";
            final String userName = "tirekicker";
            final String subnetName = "MySubnet";
            String storageAccountKey;
            String fileShareUri;

            Workspace workspace = workspaces.define(workspaceName)
                    .withRegion(region)
                    .withNewResourceGroup(groupName)
                    .create();

            if (isPlaybackMode()) {
                storageAccountKey = "dummy_key";
                fileShareUri = "dummy_uri";
            } else {
                storageAccountKey = ensureStorageAccount(storageAccounts, saName, groupName, shareName);
                String connectionString = String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s", saName, storageAccountKey);

                CloudFileShare cloudFileShare = CloudStorageAccount.parse(String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net",
                        saName, storageAccountKey))
                        .createCloudFileClient()
                        .getShareReference(shareName);
                cloudFileShare.create();

                CloudStorageAccount account = CloudStorageAccount.parse(connectionString);
                CloudBlobClient cloudBlobClient = account.createCloudBlobClient();
                CloudBlobContainer container = cloudBlobClient.getContainerReference(containerName);
                container.createIfNotExists();
                fileShareUri = cloudFileShare.getStorageUri().getPrimaryUri().toString();
            }

            Network network = networks.define(vnetName)
                .withRegion(region)
                .withExistingResourceGroup(groupName)
                .withAddressSpace("192.168.0.0/16")
                .withSubnet(subnetName, "192.168.200.0/24")
                .create();

            BatchAICluster cluster = workspace.clusters().define(clusterName)
                    .withVMSize(VirtualMachineSizeTypes.STANDARD_D1_V2.toString())
                    .withUserName(userName)
                    .withPassword("MyPassword")
                    .withAutoScale(1, 1)
                    .withLowPriority()
                    .defineSetupTask()
                        .withCommandLine("echo Hello World!")
                        .withStdOutErrPath("./outputpath")
                    .attach()
                    .defineAzureFileShare()
                        .withStorageAccountName(saName)
                        .withAzureFileUrl(fileShareUri)
                        .withRelativeMountPath(shareMountPath)
                        .withAccountKey(storageAccountKey)
                        .attach()
                    .defineAzureBlobFileSystem()
                        .withStorageAccountName(saName)
                        .withContainerName(containerName)
                        .withRelativeMountPath(blobFileSystemPath)
                        .withAccountKey(storageAccountKey)
                        .attach()
                    .withVirtualMachineImage("microsoft-ads", "linux-data-science-vm-ubuntu", "linuxdsvmubuntu")
                    .withSubnet(network.id(), subnetName)
                    .withAppInsightsComponentId("appinsightsId")
                    .withInstrumentationKey("appInsightsKey")
                    .create();
            printBatchAICluster(cluster);
            Assert.assertEquals("steady", cluster.allocationState().toString());
            Assert.assertEquals(userName, cluster.adminUserName());
            Assert.assertEquals(VmPriority.LOWPRIORITY, cluster.vmPriority());
            Assert.assertEquals(1, cluster.nodeSetup().mountVolumes().azureFileShares().size());
            Assert.assertEquals(shareMountPath, cluster.nodeSetup().mountVolumes().azureFileShares().get(0).relativeMountPath());
            Assert.assertEquals(1, cluster.nodeSetup().mountVolumes().azureBlobFileSystems().size());
            Assert.assertEquals(blobFileSystemPath, cluster.nodeSetup().mountVolumes().azureBlobFileSystems().get(0).relativeMountPath());
            Assert.assertEquals(network.id() + "/subnets/" + subnetName, cluster.subnet().id());
            Assert.assertEquals("appinsightsId", cluster.nodeSetup().performanceCountersSettings().appInsightsReference().component().id());
            Assert.assertEquals("linux-data-science-vm-ubuntu", cluster.virtualMachineConfiguration().imageReference().offer());
            return workspace;
        }

        @Override
        public Workspace updateResource(Workspace workspace) throws Exception {
//            workspace.update().withTag("tag2", "value2").apply();
            BatchAICluster cluster = workspace.clusters().getByName(clusterName);
            cluster.update()
                    .withAutoScale(1, 2, 2)
                    .apply();
            Assert.assertEquals(2, cluster.scaleSettings().autoScale().maximumNodeCount());
            return workspace;
        }

        @Override
        public void print(Workspace resource) {

        }
    }

    private static String ensureStorageAccount(StorageAccounts storageAccounts, String saName, String rgName, String shareName) throws Exception {
        StorageAccount storageAccount = storageAccounts.define(saName)
                .withRegion(Region.US_WEST)
                .withExistingResourceGroup(rgName)
                .create();

        return storageAccount.getKeys().get(0).value();
    }

    private static void printBatchAICluster(BatchAICluster cluster) {
        StringBuilder info = new StringBuilder();
        info.append("Batch AI Cluster: ").append(cluster.id())
                .append("\n\tName: ").append(cluster.name())
                .append("\n\tResource group: ").append(cluster.workspace().resourceGroupName())
                .append("\n\tRegion: ").append(cluster.workspace().regionName())
                .append("\n\tVM size: ").append(cluster.vmSize())
                .append("\n\tAdmin user name: ").append(cluster.adminUserName())
                .append("\n\tCreation time: ").append(cluster.creationTime());
        if (cluster.nodeSetup() != null && cluster.nodeSetup().mountVolumes() != null && cluster.nodeSetup().mountVolumes().azureFileShares() != null) {
            info.append("\n\tAzure file shares: ");
            for (AzureFileShareReference fileShare : cluster.nodeSetup().mountVolumes().azureFileShares()) {
                info.append("\n\t\tAccount name: ").append(fileShare.accountName())
                    .append("\n\t\tAzure file url: ").append(fileShare.azureFileUrl())
                    .append("\n\t\tRelative mount path: ").append(fileShare.relativeMountPath());
            }
        }
        System.out.println(info.toString());
    }
}
