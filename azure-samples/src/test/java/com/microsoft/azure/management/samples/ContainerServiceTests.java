/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.samples;

import com.microsoft.azure.management.containerservice.samples.DeployImageFromContainerRegistryToContainerServiceOrchestrator;
import com.microsoft.azure.management.containerservice.samples.ManageContainerServiceWithDockerSwarmOrchestrator;
import com.microsoft.azure.management.containerservice.samples.ManageContainerServiceWithKubernetesOrchestrator;
import org.junit.Assert;
import org.junit.Test;

public class ContainerServiceTests extends SamplesTestBase {
    @Test
    public void testManageContainerServiceWithKubernetesOrchestrator() {
        if (isPlaybackMode()) {
            // Disable mocked testing but keep it commented out in case we want to re-enable it later
            // Assert.assertTrue(ManageContainerServiceWithKubernetesOrchestrator.runSample(azure, "client id", "secret"));
        } else {
            Assert.assertTrue(ManageContainerServiceWithKubernetesOrchestrator.runSample(azure, "", ""));
        }
    }

    @Test
    public void testManageContainerServiceWithDockerSwarmOrchestrator() {
        Assert.assertTrue(ManageContainerServiceWithDockerSwarmOrchestrator.runSample(azure));
    }

    @Test
    public void testDeployImageFromContainerRegistryToContainerServiceOrchestrator() {
        if (!isPlaybackMode()) {
            Assert.assertTrue(DeployImageFromContainerRegistryToContainerServiceOrchestrator.runSample(azure, "", ""));
        }
    }

}
