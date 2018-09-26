/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management;

import com.microsoft.azure.management.batchai.BatchAIFileServer;
import com.microsoft.azure.management.batchai.BatchAIWorkspace;
import com.microsoft.azure.management.batchai.BatchAIWorkspaces;
import com.microsoft.azure.management.batchai.CachingType;
import com.microsoft.azure.management.batchai.StorageAccountType;
import com.microsoft.azure.management.compute.VirtualMachineSizeTypes;
import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.network.Networks;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.Assert;

public class TestBatchAIFileServers extends TestTemplate<BatchAIWorkspace, BatchAIWorkspaces> {
    private Networks networks;

    public TestBatchAIFileServers(Networks networks) {
        this.networks = networks;
    }

    @Override
    public BatchAIWorkspace createResource(BatchAIWorkspaces workspaces) throws Exception {
        final Region region = Region.EUROPE_WEST;
        final String groupName = SdkContext.randomResourceName("rg", 10);
        final String wsName = SdkContext.randomResourceName("ws", 10);
        final String fsName = SdkContext.randomResourceName("fs", 15);
        final String vnetName = SdkContext.randomResourceName("vnet", 15);
        final String subnetName = "MySubnet";
        final String userName = "tirekicker";

        BatchAIWorkspace workspace = workspaces.define(wsName)
                .withRegion(region)
                .withNewResourceGroup(groupName)
                .create();

        Network network = networks.define(vnetName)
                .withRegion(region)
                .withExistingResourceGroup(groupName)
                .withAddressSpace("192.168.0.0/16")
                .withSubnet(subnetName, "192.168.200.0/24")
                .create();

        BatchAIFileServer fileServer = workspace.fileServers().define(fsName)
                .withDataDisks(10, 2, StorageAccountType.STANDARD_LRS, CachingType.READWRITE)
                .withVMSize(VirtualMachineSizeTypes.STANDARD_D1_V2.toString())
                .withUserName(userName)
                .withPassword("MyPassword!")
                .withSubnet(network.id(), subnetName)
                .create();

        Assert.assertEquals(network.id() + "/subnets/" + subnetName, fileServer.subnet().id());
        Assert.assertEquals(CachingType.READWRITE, fileServer.dataDisks().cachingType());
        return workspace;
    }

    @Override
    public BatchAIWorkspace updateResource(BatchAIWorkspace workspace) throws Exception {
        return workspace;
    }

    @Override
    public void print(BatchAIWorkspace workspace) {
        StringBuilder info = new StringBuilder();
        info.append("Workspace: ").append(workspace.id())
                .append("\n\tName: ").append(workspace.name())
                .append("\n\tResource group: ").append(workspace.resourceGroupName())
                .append("\n\tRegion: ").append(workspace.regionName())
                .append("\n\tTags: ").append(workspace.tags());
        System.out.println(info.toString());
    }
}
