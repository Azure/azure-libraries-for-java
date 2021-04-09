/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerservice;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
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
        String agentPoolName1 = generateRandomResourceName("ap1", 10);
        String agentPoolName2 = generateRandomResourceName("ap2", 10);
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
                .withMode(AgentPoolMode.SYSTEM)
                .attach()
            .defineAgentPool(agentPoolName1)
                .withVirtualMachineSize(ContainerServiceVMSizeTypes.STANDARD_A2_V2)
                .withAgentPoolVirtualMachineCount(2)
                .attach()
            .withDnsPrefix("mp1" + dnsPrefix)
            .withTag("tag1", "value1")
            .create();

        Assert.assertNotNull(kubernetesCluster.id());
        Assert.assertEquals(Region.US_EAST, kubernetesCluster.region());
        Assert.assertEquals("testaks", kubernetesCluster.linuxRootUsername());

        Assert.assertEquals(2, kubernetesCluster.agentPools().size());

        KubernetesClusterAgentPool agentPool = kubernetesCluster.agentPools().get(agentPoolName);
        Assert.assertNotNull(agentPool);
        Assert.assertEquals(1, agentPool.count());
        Assert.assertEquals(ContainerServiceVMSizeTypes.STANDARD_D2_V2, agentPool.vmSize());
        Assert.assertEquals(AgentPoolType.VIRTUAL_MACHINE_SCALE_SETS, agentPool.type());
        Assert.assertEquals(AgentPoolMode.SYSTEM, agentPool.mode());

        agentPool = kubernetesCluster.agentPools().get(agentPoolName1);
        Assert.assertNotNull(agentPool);
        Assert.assertEquals(2, agentPool.count());
        Assert.assertEquals(ContainerServiceVMSizeTypes.STANDARD_A2_V2, agentPool.vmSize());
        Assert.assertEquals(AgentPoolType.VIRTUAL_MACHINE_SCALE_SETS, agentPool.type());

        Assert.assertNotNull(kubernetesCluster.tags().get("tag1"));;

        // update
        kubernetesCluster = kubernetesCluster.update()
            .updateAgentPool(agentPoolName1)
                .withAgentPoolMode(AgentPoolMode.SYSTEM)
                .withAgentPoolVirtualMachineCount(3)
                .parent()
            .defineAgentPool(agentPoolName2)
                .withVirtualMachineSize(ContainerServiceVMSizeTypes.STANDARD_A2_V2)
                .withAgentPoolVirtualMachineCount(1)
                .attach()
            .withTag("tag2", "value2")
            .withTag("tag3", "value3")
            .withoutTag("tag1")
            .apply();

        Assert.assertEquals(3, kubernetesCluster.agentPools().size());

        agentPool = kubernetesCluster.agentPools().get(agentPoolName1);
        Assert.assertEquals(3, agentPool.count());
        Assert.assertEquals(AgentPoolMode.SYSTEM, agentPool.mode());

        agentPool = kubernetesCluster.agentPools().get(agentPoolName2);
        Assert.assertNotNull(agentPool);
        Assert.assertEquals(ContainerServiceVMSizeTypes.STANDARD_A2_V2, agentPool.vmSize());
        Assert.assertEquals(1, agentPool.count());

        Assert.assertNotNull(kubernetesCluster.tags().get("tag2"));
        Assert.assertFalse(kubernetesCluster.tags().containsKey("tag1"));

        // deprecated method
        kubernetesCluster.update()
                .withAgentPoolVirtualMachineCount(1)
                .apply();

        Assert.assertEquals(3, kubernetesCluster.agentPools().size());
        for (KubernetesClusterAgentPool pool : kubernetesCluster.agentPools().values()) {
            Assert.assertEquals(1, pool.count());
        }

        kubernetesCluster.update()
                .withAgentPoolVirtualMachineCount(agentPoolName1, 2)
                .apply();

        for (Map.Entry<String, KubernetesClusterAgentPool> entry : kubernetesCluster.agentPools().entrySet()) {
            String name = entry.getKey();
            int count = entry.getValue().count();
            Assert.assertEquals(agentPoolName1.equals(name) ? 2 : 1, count);
        }
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