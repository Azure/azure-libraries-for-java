/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerregistry;

import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import org.junit.Test;

import java.util.Arrays;

public class RegistryTaskTests extends RegistryTest {

    @Test
    public void FileTaskTest(){
        final String newName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String githubRepoUrl = "https://github.com/iscai-msft/file_task_test.git";
        String githubBranch = "master";
        String githubPAT = "36b0983ab6957db8748bee783d4751dd04714d5d";

//        Registry registry = registryManager.containerRegistries().define(newName + "1")
//                .withRegion(Region.US_WEST_CENTRAL)
//                .withNewResourceGroup(rgName)
//                .withPremiumSku()
//                .withRegistryNameAsAdminUser()
//                .withTag("tag1", "value1")
//                .create();

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

                .withExistingRegistry("rgacr91216", "acr7105111")
                .withLocation(Region.US_WEST_CENTRAL.name())
                .withLinux(Architecture.AMD64)
                .defineFileTaskStep()
                    .withTaskPath("https://github.com/iscai-msft/file_task_test.git#master:samples/java/task/acb.yaml")
                    .attach()
                .withCpuCount(2)
                .withTrigger(Arrays.asList(sourceTrigger), baseImageTrigger)
                .create();

        }


    @Override
    protected void cleanUpResources() {
    }
}
