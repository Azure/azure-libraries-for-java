/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerservice;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.rest.serializer.JacksonAdapter;

import org.junit.Assert;
import org.junit.Test;


public class KubernetesClustersTests extends ContainerServiceManagementTest {
    private static final String sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCfSPC2K7LZcFKEO+/t3dzmQYtrJFZNxOsbVgOVKietqHyvmYGHEC0J2wPdAqQ/63g/hhAEFRoyehM+rbeDri4txB3YFfnOK58jqdkyXzupWqXzOrlKY4Wz9SKjjN765+dqUITjKRIaAip1Ri137szRg71WnrmdP3SphTRlCx1Bk2nXqWPsclbRDCiZeF8QOTi4JqbmJyK5+0UqhqYRduun8ylAwKKQJ1NJt85sYIHn9f1Rfr6Tq2zS0wZ7DHbZL+zB5rSlAr8QyUdg/GQD+cmSs6LvPJKL78d6hMGk84ARtFo4A79ovwX/Fj01znDQkU6nJildfkaolH2rWFG/qttD azjava@javalib.Com";

    @Test
    public void canCRUDKubernetesCluster() throws Exception {
        String aksName = SdkContext.randomResourceName("aks", 15);
        String dnsPrefix = SdkContext.randomResourceName("dns", 10);
        String agentPoolName = SdkContext.randomResourceName("ap0", 10);
        String servicePrincipalClientId = "spId";
        String servicePrincipalSecret = "spSecret";

        // aks can use another azure auth rather than original client auth to access azure service.
        // Thus, set it to AZURE_AUTH_LOCATION_2 when you want.
        String envSecondaryServicePrincipal = System.getenv("AZURE_AUTH_LOCATION_2");
        if (envSecondaryServicePrincipal == null || envSecondaryServicePrincipal.isEmpty() || !(new File(envSecondaryServicePrincipal).exists())) {
            envSecondaryServicePrincipal = System.getenv("AZURE_AUTH_LOCATION");
        }

        if (!isPlaybackMode()) {
            HashMap<String, String> credentialsMap = ParseAuthFile(envSecondaryServicePrincipal);
            servicePrincipalClientId = credentialsMap.get("clientId");
            servicePrincipalSecret = credentialsMap.get("clientSecret");
        }

        // create
        KubernetesCluster kubernetesCluster = containerServiceManager
            .kubernetesClusters()
            .define(aksName)
            .withRegion(Region.US_EAST)
            .withExistingResourceGroup(RG_NAME)
            .withLatestVersion()
            .withRootUsername("testaks")
            .withSshKey(sshKey)
            .withServicePrincipalClientId(servicePrincipalClientId)
            .withServicePrincipalSecret(servicePrincipalSecret)
            .defineAgentPool(agentPoolName)
                .withVirtualMachineSize(ContainerServiceVMSizeTypes.STANDARD_D2_V2)
                .withAgentPoolType(AgentPoolType.VIRTUAL_MACHINE_SCALE_SETS)
                .withAgentPoolVirtualMachineCount(1)
                .withMode(AgentPoolMode.SYSTEM)
                .attach()
            .withDnsPrefix("mp1" + dnsPrefix)
            .withSku(new ManagedClusterSKU()
                    .withName(ManagedClusterSKUName.BASIC)
                    .withTier(ManagedClusterSKUTier.PAID))
            .withTag("tag1", "value1")
            .create();

        Assert.assertNotNull(kubernetesCluster.id());
        Assert.assertEquals(Region.US_EAST, kubernetesCluster.region());
        Assert.assertEquals("testaks", kubernetesCluster.linuxRootUsername());
        Assert.assertEquals(ManagedClusterSKUName.BASIC, kubernetesCluster.sku().name());
        Assert.assertEquals(ManagedClusterSKUTier.PAID, kubernetesCluster.sku().tier());
        Assert.assertEquals(1, kubernetesCluster.agentPools().size());
        Assert.assertNotNull(kubernetesCluster.agentPools().get(agentPoolName));
        Assert.assertEquals(1, kubernetesCluster.agentPools().get(agentPoolName).count());
        Assert.assertEquals(ContainerServiceVMSizeTypes.STANDARD_D2_V2, kubernetesCluster.agentPools().get(agentPoolName).vmSize());
        Assert.assertEquals(AgentPoolType.VIRTUAL_MACHINE_SCALE_SETS, kubernetesCluster.agentPools().get(agentPoolName).type());
        Assert.assertEquals(AgentPoolMode.SYSTEM, kubernetesCluster.agentPools().get(agentPoolName).mode());
        Assert.assertNotNull(kubernetesCluster.tags().get("tag1"));;

        // update
        kubernetesCluster = kubernetesCluster.update()
            .withAgentPoolVirtualMachineCount(agentPoolName, 5)
            .withTag("tag2", "value2")
            .withTag("tag3", "value3")
            .withoutTag("tag1")
            .apply();

        Assert.assertEquals(1, kubernetesCluster.agentPools().size());
        Assert.assertEquals(5, kubernetesCluster.agentPools().get(agentPoolName).count());
        Assert.assertNotNull(kubernetesCluster.tags().get("tag2"));
        Assert.assertTrue(!kubernetesCluster.tags().containsKey("tag1"));
    }

    /**
     * Parse azure auth to hashmap
     * @param authFilename the azure auth location
     * @return all fields in azure auth json
     * @throws Exception exception
     */
    private static HashMap<String, String> ParseAuthFile(String authFilename) throws Exception {
        String content = Files.toString(new File(authFilename), Charsets.UTF_8).trim();
        HashMap<String, String> auth = new HashMap<>();
        if (isJsonBased(content)) {
            auth = new JacksonAdapter().deserialize(content, auth.getClass());
        } else {
            Properties authSettings = new Properties();
            FileInputStream credentialsFileStream = new FileInputStream(new File(authFilename));
            authSettings.load(credentialsFileStream);
            credentialsFileStream.close();

            for (final String authName: authSettings.stringPropertyNames()) {
                auth.put(authName, authSettings.getProperty(authName));
            }
        }
        return auth;
    }

    private static boolean isJsonBased(String content) {
        return content.startsWith("{");
    }
}