/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerregistry;

import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

public class RegistryTaskTests extends RegistryTest {

    @Test
    @Ignore("Needs personal tokens to run")
    public void FileTaskTest(){
        final String newName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String githubRepoUrl = "Replace with your github repository url, eg: https://github.com/Azure/acr.git";
        String githubBranch = "Replace with your github repositoty branch, eg: master";
        String githubPAT = "Replace with your github personal access token which should have the scopes: admin:repo_hook and repo";

        Registry registry = registryManager.containerRegistries().define(newName + "1")
                .withRegion(Region.US_WEST_CENTRAL)
                .withNewResourceGroup(rgName)
                .withPremiumSku()
                .withRegistryNameAsAdminUser()
                .withTag("tag1", "value1")
                .create();

        SourceTrigger sourceTrigger = new SourceTrigger()
                .withName("SampleSourceTrigger")
                .withSourceRepository(new SourceProperties()
                        .withSourceControlType(SourceControlType.GITHUB)
                        .withBranch(githubBranch)
                        .withRepositoryUrl(githubRepoUrl)
                        .withSourceControlAuthProperties(new AuthInfo().withTokenType(TokenType.PAT).withToken(githubPAT)))
                .withSourceTriggerEvents(Arrays.asList(SourceTriggerEvent.COMMIT, SourceTriggerEvent.PULLREQUEST))
                .withStatus(TriggerStatus.ENABLED);

        BaseImageTrigger baseImageTrigger = new BaseImageTrigger()
                .withName("SampleBaseImageTrigger")
                .withBaseImageTriggerType(BaseImageTriggerType.RUNTIME);


        Task task = registryManager.containerRegistryTasks().define(generateRandomResourceName("ft", 10))

                .withExistingRegistry(rgName, newName)
                .withLocation(Region.US_WEST_CENTRAL.name())
                .withLinux(Architecture.AMD64)
                .defineFileTaskStep()
                    .withTaskPath("https://github.com/iscai-msft/file_task_test.git#master:samples/java/task/acb.yaml")
                    .attach()
                .withCpuCount(2)
                .withTrigger(Arrays.asList(sourceTrigger), baseImageTrigger)
                .create();

    }

    @Test
    @Ignore("Needs personal tokens to run")
    public void EncodedTaskTest(){
        final String newName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String githubRepoUrl = "Replace with your github repository url, eg: https://github.com/Azure/acr.git";
        String githubBranch = "Replace with your github repositoty branch, eg: master";
        String githubPAT = "Replace with your github personal access token which should have the scopes: admin:repo_hook and repo";

        Registry registry = registryManager.containerRegistries().define(newName + "1")
                .withRegion(Region.US_WEST_CENTRAL)
                .withNewResourceGroup(rgName)
                .withPremiumSku()
                .withRegistryNameAsAdminUser()
                .withTag("tag1", "value1")
                .create();

        SourceTrigger sourceTrigger = new SourceTrigger()
                .withName("SampleSourceTrigger")
                .withSourceRepository(new SourceProperties()
                        .withSourceControlType(SourceControlType.GITHUB)
                        .withBranch(githubBranch)
                        .withRepositoryUrl(githubRepoUrl)
                        .withSourceControlAuthProperties(new AuthInfo().withTokenType(TokenType.PAT).withToken(githubPAT)))
                .withSourceTriggerEvents(Arrays.asList(SourceTriggerEvent.COMMIT, SourceTriggerEvent.PULLREQUEST))
                .withStatus(TriggerStatus.ENABLED);

        BaseImageTrigger baseImageTrigger = new BaseImageTrigger()
                .withName("SampleBaseImageTrigger")
                .withBaseImageTriggerType(BaseImageTriggerType.RUNTIME);


        Task task = registryManager.containerRegistryTasks().define(generateRandomResourceName("ft", 10))

                .withExistingRegistry("rgacr14739", "acr09708c1")
                .withLocation(Region.US_WEST_CENTRAL.name())
                .withLinux(Architecture.AMD64)
                .defineEncodedTaskStep()
                    .withBase64EncodedTaskContent("dmVyc2lvbjogMC4wLjEKc3RlcHM6CiAgLSBidWlsZDogLXQge3suUnVuLlJlZ2lzdHJ5fX0vamF2YS1zYW1wbGU6e3suUnVuLklEfX0gLgogICAgd29ya2luZ0RpcmVjdG9yeTogc2FtcGxlcy9qYXZhL3Rhc2sKICAtIHB1c2g6IAogICAgLSB7ey5SdW4uUmVnaXN0cnl9fS9qYXZhLXNhbXBsZTp7ey5SdW4uSUR9fQ==")
                    .attach()
                .withCpuCount(2)
                .withTrigger(Arrays.asList(sourceTrigger), baseImageTrigger)
                .create();

    }



    @Override
    protected void cleanUpResources() {
    }
}
