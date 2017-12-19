/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management;

import com.microsoft.azure.management.batchai.Cluster;
import com.microsoft.azure.management.batchai.Clusters;
import com.microsoft.azure.management.batchai.VmPriority;
import com.microsoft.azure.management.compute.VirtualMachineSizeTypes;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.Assert;

/**
 * Test of Batch AI management.
 */
public class TestBatchAI {
    public static class Basic extends TestTemplate<Cluster, Clusters> {

        @Override
        public Cluster createResource(Clusters clusters) throws Exception {
            final Region region = Region.US_EAST;
            final String groupName = SdkContext.randomResourceName("rg", 10);
            final String clusterName = SdkContext.randomResourceName("cluster", 15);
            final String userName = "tirekicker";

            Cluster cluster = clusters.define(clusterName)
                    .withRegion(region)
                    .withNewResourceGroup(groupName)
                    .withVMSize(VirtualMachineSizeTypes.STANDARD_NC6.toString())
                    .withUserName(userName)
                    .withPassword("MyPassword")
                    .withAutoScale(1, 1)
                    .withLowPriority()
                    .defineSetupTask()
                        .withCommandLine("echo Hello World!")
                        .withStdOutErrPath("./outputpath")
                    .attach()
                    .withTag("tag1", "value1")
                    .create();
            Assert.assertEquals("steady", cluster.allocationState().toString());
            Assert.assertEquals(userName, cluster.adminUserName());
            Assert.assertEquals(VmPriority.LOWPRIORITY, cluster.vmPriority());
            return cluster;
        }

        @Override
        public Cluster updateResource(Cluster cluster) throws Exception {
            cluster.update()
                    .withAutoScale(1, 2, 2)
                    .withTag("tag1", "value2")
                    .apply();
            Assert.assertEquals(2, cluster.scaleSettings().autoScale().maximumNodeCount());
            Assert.assertEquals("value2", cluster.tags().get("tag1"));
            return cluster;
        }

        @Override
        public void print(Cluster resource) {
            printBatchAICluster(resource);
        }
    }

    public static class JobCreate extends TestTemplate<Cluster, Clusters> {

        @Override
        public Cluster createResource(Clusters clusters) throws Exception {
            final Region region = Region.US_EAST;
            final String groupName = SdkContext.randomResourceName("rg", 10);
            final String clusterName = SdkContext.randomResourceName("cluster", 15);
            final String userName = "tirekicker";

            Cluster cluster = clusters.define(clusterName)
                    .withRegion(region)
                    .withNewResourceGroup(groupName)
                    .withVMSize(VirtualMachineSizeTypes.STANDARD_NC6.toString())
                    .withUserName(userName)
                    .withPassword("MyPassword")
                    .withAutoScale(1, 1)
                    .create();
            Assert.assertEquals("steady", cluster.allocationState().toString());
            Assert.assertEquals(userName, cluster.adminUserName());
//            cluster.jobs().define("myJob")
//                    .withRegion(Region.US_WEST2)
//                    .withStdOutErrPathPrefix("$AZ_BATCHAI_MOUNT_ROOT/azurefileshare")
//                    .withNodeCount(1)
//                    .
//                    .create();
            return cluster;
        }

        @Override
        public Cluster updateResource(Cluster cluster) throws Exception {
            return cluster;
        }

        @Override
        public void print(Cluster resource) {
            printBatchAICluster(resource);
        }
    }

    private static void printBatchAICluster(Cluster cluster) {
        StringBuilder info = new StringBuilder();
        info.append("Batch AI Cluster: ").append(cluster.id())
                .append("\n\tName: ").append(cluster.name())
                .append("\n\tResource group: ").append(cluster.resourceGroupName())
                .append("\n\tRegion: ").append(cluster.regionName())
                .append("\n\tTags: ").append(cluster.tags())
                .append("\n\tVM size: ").append(cluster.vmSize())
                .append("\n\tAdmin user name: ").append(cluster.adminUserName())
                .append("\n\tCreation time: ").append(cluster.creationTime());

        System.out.println(info.toString());
    }
}
