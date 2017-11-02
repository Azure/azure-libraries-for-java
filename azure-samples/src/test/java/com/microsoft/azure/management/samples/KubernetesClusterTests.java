/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.samples;

import com.microsoft.azure.management.kubernetescluster.samples.DeployImageFromContainerRegistryToKubernetes;
import com.microsoft.azure.management.kubernetescluster.samples.ManageKubernetesCluster;
import org.junit.Assert;
import org.junit.Test;

public class KubernetesClusterTests extends SamplesTestBase {
    @Test
    public void testManageKubernetesCluster() {
        if (isPlaybackMode()) {
            Assert.assertTrue(ManageKubernetesCluster.runSample(azure, "client id", "secret"));
        } else {
            Assert.assertTrue(ManageKubernetesCluster.runSample(azure, "", ""));
        }
    }

    @Test
    public void testDeployImageFromContainerRegistryToKubernetes() {
        if (!isPlaybackMode()) {
            Assert.assertTrue(DeployImageFromContainerRegistryToKubernetes.runSample(azure, "", ""));
        }
    }
}
