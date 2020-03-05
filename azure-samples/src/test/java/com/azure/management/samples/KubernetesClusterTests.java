/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.samples;

import com.azure.management.kubernetescluster.samples.DeployImageFromContainerRegistryToKubernetes;
import com.azure.management.kubernetescluster.samples.ManageKubernetesCluster;
import com.azure.management.kubernetescluster.samples.ManagedKubernetesClusterWithAdvancedNetworking;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class KubernetesClusterTests extends SamplesTestBase {
    @Test
    @Ignore("QuotaExceeded error: Public preview limit of 5 for managed cluster(AKS) has been reached for subscription sub-id in location ukwest. Same error even after deleting all clusters")
    public void testManageKubernetesCluster() {
        if (isPlaybackMode()) {
            // Disable mocked testing but keep it commented out in case we want to re-enable it later
            // Assert.assertTrue(ManageKubernetesCluster.runSample(azure, "client id", "secret"));
        } else {
            Assert.assertTrue(ManageKubernetesCluster.runSample(azure, "", ""));
        }
    }

    @Test
    @Ignore("QuotaExceeded error: Public preview limit of 5 for managed cluster(AKS) has been reached for subscription sub-id in location ukwest. Same error even after deleting all clusters")
    public void testManageKubernetesClusterWithAdvancedNetworking() {
        if (isPlaybackMode()) {
            // Disable mocked testing but keep it commented out in case we want to re-enable it later
            // Assert.assertTrue(ManageKubernetesCluster.runSample(azure, "client id", "secret"));
        } else {
            Assert.assertTrue(ManagedKubernetesClusterWithAdvancedNetworking.runSample(azure, "", ""));
        }
    }

    @Test
    public void testDeployImageFromContainerRegistryToKubernetes() {
        if (!isPlaybackMode()) {
            Assert.assertTrue(DeployImageFromContainerRegistryToKubernetes.runSample(azure, "", ""));
        }
    }
}
