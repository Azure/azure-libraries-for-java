/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.samples;

import com.microsoft.azure.management.containerinstance.samples.ManageContainerInstanceWithAzureFileShareMount;
import com.microsoft.azure.management.containerinstance.samples.ManageContainerInstanceWithManualAzureFileShareMountCreation;
import com.microsoft.azure.management.containerinstance.samples.ManageContainerInstanceWithMultipleContainerImages;
import com.microsoft.azure.management.containerinstance.samples.ManageContainerInstanceZeroToOneAndOneToManyUsingContainerServiceOrchestrator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContainerInstanceTests extends SamplesTestBase {

    @Test
    public void testManageContainerInstanceWithAzureFileShareMount() {
        // Skip test in "playback" mode due to HTTP calls made outside of the management plane which can not be recorded at this time
        if (!isPlaybackMode()) {
            Assertions.assertTrue(ManageContainerInstanceWithAzureFileShareMount.runSample(azure));
        }
    }

    @Test
    public void testManageContainerInstanceWithManualAzureFileShareMountCreation() {
        // Skip test in "playback" mode due to HTTP calls made outside of the management plane which can not be recorded at this time
        if (!isPlaybackMode()) {
            Assertions.assertTrue(ManageContainerInstanceWithManualAzureFileShareMountCreation.runSample(azure));
        }
    }

    @Test
    public void testManageContainerInstanceWithMultipleContainerImages() {
        Assertions.assertTrue(ManageContainerInstanceWithMultipleContainerImages.runSample(azure));
    }

    @Test
    public void testManageContainerInstanceZeroToOneAndOneToManyUsingContainerServiceOrchestrator() {
        // Skip test in "playback" mode due to HTTP calls made outside of the management plane which can not be recorded at this time
        if (!isPlaybackMode()) {
            Assertions.assertTrue(ManageContainerInstanceZeroToOneAndOneToManyUsingContainerServiceOrchestrator.runSample(azure, "", ""));
        }
    }

}
