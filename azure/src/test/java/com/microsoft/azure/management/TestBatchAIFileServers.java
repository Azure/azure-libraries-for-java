/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management;

import com.microsoft.azure.management.batchai.BatchAIFileServer;
import com.microsoft.azure.management.batchai.BatchAIFileServers;
import com.microsoft.azure.management.batchai.StorageAccountType;
import com.microsoft.azure.management.compute.VirtualMachineSizeTypes;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;

public class TestBatchAIFileServers extends TestTemplate<BatchAIFileServer, BatchAIFileServers> {
    @Override
    public BatchAIFileServer createResource(BatchAIFileServers fileServers) throws Exception {
        final Region region = Region.US_EAST;
        final String groupName = SdkContext.randomResourceName("rg", 10);
        final String fsName = SdkContext.randomResourceName("fs", 15);
        final String userName = "tirekicker";

        BatchAIFileServer fileServer = fileServers.define(fsName)
                .withRegion(region)
                .withNewResourceGroup(groupName)
                .withDataDisks(10, 2, StorageAccountType.STANDARD_LRS)
                .withVMSize(VirtualMachineSizeTypes.STANDARD_D1_V2.toString())
                .withUserName(userName)
                .withPassword("MyPassword!")
                .create();
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
