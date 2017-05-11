/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management;


import org.junit.Assert;

import com.microsoft.azure.management.network.NewTopLevelModel;
import com.microsoft.azure.management.network.NewTopLevelModels;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;

/**
 * Test of virtual network management.
 */
public class TestNewModel extends TestTemplate<NewTopLevelModel, NewTopLevelModels> {
    /**
     * Outputs info about a network.
     * @param resource a network
     */
    public static void printModel(NewTopLevelModel resource) {
        StringBuilder info = new StringBuilder();
        info.append("Model: ").append(resource.id())
                .append("Name: ").append(resource.name())
                .append("\n\tResource group: ").append(resource.resourceGroupName())
                .append("\n\tRegion: ").append(resource.region())
                .append("\n\tTags: ").append(resource.tags())
                .append("\n\tAddress spaces: ").append(resource.addressSpaces());

        System.out.println(info.toString());
    }

    @Override
    public NewTopLevelModel createResource(NewTopLevelModels resources) throws Exception {
        String resourceName = SdkContext.randomResourceName("foo", 13);
        NewTopLevelModel resource = resources.define(resourceName)
                .withRegion(Region.US_EAST)
                .withNewResourceGroup()
                .withAddressSpace("10.0.0.0/29")
                .withAddressSpace("10.0.1.0/29")
                .withAddressSpaces("10.2.0.0/29", "10.2.1.0/29")
                .create();

        return resource;
    }

    @Override
    public NewTopLevelModel updateResource(NewTopLevelModel resource) throws Exception {
        resource.update()
            .withAddressSpace("10.5.0.0/28")
            .withoutAddressSpace("10.0.0.0/29")
            .withTag("foo", "bar")
            .apply();

        Assert.assertNotNull(resource.tags().get("foo"));
        return resource;
    }

    @Override
    public void print(NewTopLevelModel resource) {
        printModel(resource);
    }
}
