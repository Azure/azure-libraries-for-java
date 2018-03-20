/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management;

import com.microsoft.azure.management.batchai.BatchAIFileServer;
import com.microsoft.azure.management.batchai.BatchAIFileServers;
import com.microsoft.azure.management.batchai.CachingType;
import com.microsoft.azure.management.batchai.StorageAccountType;
import com.microsoft.azure.management.compute.VirtualMachineSizeTypes;
import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.network.Networks;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.Assert;

public class TestBatchAIFileServers extends TestTemplate<BatchAIFileServer, BatchAIFileServers> {
    private Networks networks;

    public TestBatchAIFileServers(Networks networks) {
        this.networks = networks;
    }

    @Override
    public BatchAIFileServer createResource(BatchAIFileServers fileServers) throws Exception {
        final Region region = Region.EUROPE_WEST;
        final String groupName = SdkContext.randomResourceName("rg", 10);
        final String fsName = SdkContext.randomResourceName("fs", 15);
        final String vnetName = SdkContext.randomResourceName("vnet", 15);
        final String subnetName = "MySubnet";
        final String userName = "tirekicker";

        Network network = networks.define(vnetName)
                .withRegion(region)
                .withNewResourceGroup(groupName)
                .withAddressSpace("192.168.0.0/16")
                .withSubnet(subnetName, "192.168.200.0/24")
                .create();

        BatchAIFileServer fileServer = fileServers.define(fsName)
                .withRegion(region)
                .withNewResourceGroup(groupName)
                .withDataDisks(10, 2, StorageAccountType.STANDARD_LRS, CachingType.READWRITE)
                .withVMSize(VirtualMachineSizeTypes.STANDARD_D1_V2.toString())
                .withUserName(userName)
                .withPassword("MyPassword!")
                .withSubnet(network.id(), subnetName)
                .create();

        Assert.assertEquals(network.id() + "/subnets/" + subnetName, fileServer.subnet().id());
        Assert.assertEquals(CachingType.READWRITE, fileServer.dataDisks().cachingType());
        return fileServer;
    }

    @Override
    public BatchAIFileServer updateResource(BatchAIFileServer fileServer) throws Exception {
        return fileServer;
    }

    @Override
    public void print(BatchAIFileServer fileServer) {
        StringBuilder info = new StringBuilder();
        info.append("File Server: ").append(fileServer.id())
                .append("\n\tName: ").append(fileServer.name())
                .append("\n\tResource group: ").append(fileServer.resourceGroupName())
                .append("\n\tRegion: ").append(fileServer.regionName())
                .append("\n\tTags: ").append(fileServer.tags());
        System.out.println(info.toString());
    }
}
