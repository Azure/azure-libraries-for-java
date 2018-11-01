/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerregistry;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

public class RegistryTaskTests extends RegistryTest {

    @Test
    @Ignore("Needs personal tokens to run")
    public void FileTaskTest(){
        final String acrName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String githubRepoUrl = "Replace with your github repository url, eg: https://github.com/Azure/acr.git";
        String githubBranch = "Replace with your github repositoty branch, eg: master";
        String githubPAT = "Replace with your github personal access token which should have the scopes: admin:repo_hook and repo";
        String taskFilePath = "https://github.com/iscai-msft/file_task_test.git#master:samples/java/registryTask/acb.yaml";

        Registry registry = registryManager.containerRegistries().define(acrName)
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


        String taskName = generateRandomResourceName("ft", 10);
        RegistryTask registryTask = registryManager.containerRegistryTasks().define(taskName)

                .withExistingRegistry(rgName, acrName)
                .withLocation(Region.US_WEST_CENTRAL.name())
                .withLinux(Architecture.AMD64)
                .defineFileTaskStep()
                    .withTaskPath("https://github.com/iscai-msft/file_task_test.git#master:samples/java/registryTask/acb.yaml")
                    .attach()
                .withCpuCount(2)
                .withTrigger(Arrays.asList(sourceTrigger), baseImageTrigger)
                .create();

        RegistryFileTaskStep registryFileTaskStep = (RegistryFileTaskStep) registryTask.registryTaskStep();

        //Assert the name of the registryTask is correct
        Assert.assertEquals(taskName, registryTask.name());

        //Assert the resource group name is correct
        Assert.assertEquals(rgName, registryTask.resourceGroupName());

        //Assert location is correct
        Assert.assertEquals(Region.US_WEST_CENTRAL.name(), registryTask.regionName());

        //Assert OS is correct
        Assert.assertEquals(OS.LINUX, registryTask.platform().os());

        //Assert architecture is correct
        Assert.assertEquals(Architecture.AMD64, registryTask.platform().architecture());

        //Assert that the registryTask file path is correct
        Assert.assertEquals(taskFilePath, registryFileTaskStep.taskFilePath());

        //Assert CPU count is correct
        Assert.assertEquals(2, registryTask.cpuCount());

        //Assert the length of the source triggers array list is correct
        Assert.assertTrue(registryTask.trigger().sourceTriggers().size() == 1);

        //Assert source triggers are correct
        Assert.assertEquals(sourceTrigger.name(), registryTask.trigger().sourceTriggers().get(0).name());

        //Assert base image trigger is correct
        Assert.assertEquals(baseImageTrigger.name(), registryTask.trigger().baseImageTrigger().name());


    }

    @Test
    @Ignore("Needs personal tokens to run")
    public void FileTaskUpdateTest(){
        final String acrName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String githubRepoUrl = "Replace with your github repository url, eg: https://github.com/Azure/acr.git";
        String githubBranch = "Replace with your github repositoty branch, eg: master";
        String githubPAT = "Replace with your github personal access token which should have the scopes: admin:repo_hook and repo";
        String taskFilePath = "https://github.com/iscai-msft/file_task_test.git#master:samples/java/registryTask/acb_update.yaml";
        String taskFileUpdatePath = "https://github.com/iscai-msft/file_task_test.git#master:samples/java/registryTask/acb_update.yaml";

        Registry registry = registryManager.containerRegistries().define(acrName)
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


        String taskName = generateRandomResourceName("ft", 10);
        RegistryTask registryTask = registryManager.containerRegistryTasks().define(taskName)

                .withExistingRegistry(rgName, acrName)
                .withLocation(Region.US_WEST_CENTRAL.name())
                .withLinux(Architecture.AMD64)
                .defineFileTaskStep()
                    .withTaskPath(taskFilePath)
                    .attach()
                .withCpuCount(2)
                .withTrigger(Arrays.asList(sourceTrigger), baseImageTrigger)
                .create();

        registryTask.update()
                .updateFileTaskStep()
                    .withTaskPath(taskFileUpdatePath)
                    .parent()
                .apply();

        RegistryFileTaskStep registryFileTaskStep = (RegistryFileTaskStep) registryTask.registryTaskStep();

        //Assert the name of the registryTask is correct
        Assert.assertEquals(taskName, registryTask.name());

        //Assert the resource group name is correct
        Assert.assertEquals(rgName, registryTask.resourceGroupName());

        //Assert location is correct
        Assert.assertEquals(Region.US_WEST_CENTRAL.name(), registryTask.regionName());

        //Assert OS is correct
        Assert.assertEquals(OS.LINUX, registryTask.platform().os());

        //Assert architecture is correct
        Assert.assertEquals(Architecture.AMD64, registryTask.platform().architecture());

        //Assert CPU count is correct
        Assert.assertEquals(2, registryTask.cpuCount());

        //Assert the length of the source triggers array list is correct
        Assert.assertTrue(registryTask.trigger().sourceTriggers().size() == 1);

        //Assert source triggers are correct
        Assert.assertEquals(sourceTrigger.name(), registryTask.trigger().sourceTriggers().get(0).name());

        //Assert base image trigger is correct
        Assert.assertEquals(baseImageTrigger.name(), registryTask.trigger().baseImageTrigger().name());

        //Checking to see whether file path name is updated correctly
        Assert.assertEquals(taskFilePath, registryFileTaskStep.taskFilePath());

        boolean errorRaised = false;
        try {
            registryTask.update()
                    .updateEncodedTaskStep()
                    .parent()
                    .apply();
        } catch (UnsupportedOperationException e) {
            errorRaised = true;
        }

        //Checking to see whether error is raised if update is called on the incorrect registryTask step type.
        Assert.assertTrue(errorRaised);
    }

    @Test
    @Ignore("Needs personal tokens to run")
    public void EncodedTaskTest(){

        final String acrName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String githubRepoUrl = "Replace with your github repository url, eg: https://github.com/Azure/acr.git";
        String githubBranch = "Replace with your github repositoty branch, eg: master";
        String githubPAT = "Replace with your github personal access token which should have the scopes: admin:repo_hook and repo";
        String encodedTaskContent = "dmVyc2lvbjogMC4wLjEKc3RlcHM6CiAgLSBidWlsZDogLXQge3suUnVuLlJlZ2lzdHJ5fX0vamF2YS1zYW1wbGU6e3suUnVuLklEfX0gLgogICAgd29ya2luZ0RpcmVjdG9yeTogc2FtcGxlcy9qYXZhL3Rhc2sKICAtIHB1c2g6IAogICAgLSB7ey5SdW4uUmVnaXN0cnl9fS9qYXZhLXNhbXBsZTp7ey5SdW4uSUR9fQ==";

        Registry registry = registryManager.containerRegistries().define(acrName)
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


        String taskName = generateRandomResourceName("ft", 10);

        RegistryTask registryTask = registryManager.containerRegistryTasks().define(taskName)

                .withExistingRegistry(rgName, acrName)
                .withLocation(Region.US_WEST_CENTRAL.name())
                .withLinux(Architecture.AMD64)
                .defineEncodedTaskStep()
                    .withBase64EncodedTaskContent(encodedTaskContent)
                    .attach()
                .withCpuCount(2)
                .withTrigger(Arrays.asList(sourceTrigger), baseImageTrigger)
                .create();

        RegistryEncodedTaskStep registryEncodedTaskStep = (RegistryEncodedTaskStep) registryTask.registryTaskStep();

        //Assert the name of the registryTask is correct
        Assert.assertEquals(taskName, registryTask.name());

        //Assert the resource group name is correct
        Assert.assertEquals(rgName, registryTask.resourceGroupName());

        //Assert location is correct
        Assert.assertEquals(Region.US_WEST_CENTRAL.name(), registryTask.regionName());

        //Assert OS is correct
        Assert.assertEquals(OS.LINUX, registryTask.platform().os());

        //Assert architecture is correct
        Assert.assertEquals(Architecture.AMD64, registryTask.platform().architecture());

        //Assert that the registryTask file path is correct
        Assert.assertEquals(encodedTaskContent, registryEncodedTaskStep.encodedTaskContent());

        //Assert CPU count is correct
        Assert.assertEquals(2, registryTask.cpuCount());

        //Assert the length of the source triggers array list is correct
        Assert.assertTrue(registryTask.trigger().sourceTriggers().size() == 1);

        //Assert source triggers are correct
        Assert.assertEquals(sourceTrigger.name(), registryTask.trigger().sourceTriggers().get(0).name());

        //Assert base image trigger is correct
        Assert.assertEquals(baseImageTrigger.name(), registryTask.trigger().baseImageTrigger().name());

    }


    @Test
    @Ignore("Needs personal tokens to run")
    public void EncodedTaskUpdateTest(){

        final String acrName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String githubRepoUrl = "Replace with your github repository url, eg: https://github.com/Azure/acr.git";
        String githubBranch = "Replace with your github repositoty branch, eg: master";
        String githubPAT = "Replace with your github personal access token which should have the scopes: admin:repo_hook and repo";
        String encodedTaskContent = "dmVyc2lvbjogMC4wLjEKc3RlcHM6CiAgLSBidWlsZDogLXQge3suUnVuLlJlZ2lzdHJ5fX0vamF2YS1zYW1wbGU6e3suUnVuLklEfX0gLgogICAgd29ya2luZ0RpcmVjdG9yeTogc2FtcGxlcy9qYXZhL3Rhc2sKICAtIHB1c2g6IAogICAgLSB7ey5SdW4uUmVnaXN0cnl9fS9qYXZhLXNhbXBsZTp7ey5SdW4uSUR9fQ==";
        String encodedTaskContentUpdate = "dmVyc2lvbjogMC4wLjEKc3RlcHM6CiAgLSBidWlsZDogLXQge3suUnVuLlJlZ2lzdHJ5fX0vamF2YS1zYW1wbGU6e3suUnVuLklEfX0gLgogICAgd29ya2luZ0RpcmVjdG9yeTogc2FtcGxlcy9qYXZhL3VwZGF0ZQogIC0gcHVzaDogCiAgICAtIHt7LlJ1bi5SZWdpc3RyeX19L2phdmEtc2FtcGxlOnt7LlJ1bi5JRH19";

        Registry registry = registryManager.containerRegistries().define(acrName)
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


        String taskName = generateRandomResourceName("ft", 10);

        RegistryTask registryTask = registryManager.containerRegistryTasks().define(taskName)

                .withExistingRegistry(rgName, acrName)
                .withLocation(Region.US_WEST_CENTRAL.name())
                .withLinux(Architecture.AMD64)
                .defineEncodedTaskStep()
                .withBase64EncodedTaskContent(encodedTaskContent)
                .attach()
                .withCpuCount(2)
                .withTrigger(Arrays.asList(sourceTrigger), baseImageTrigger)
                .create();


        registryTask.update()
                .updateEncodedTaskStep()
                    .withBase64EncodedTaskContent(encodedTaskContentUpdate)
                    .parent()
                .withCpuCount(1)
                .apply();

        RegistryEncodedTaskStep registryEncodedTaskStep = (RegistryEncodedTaskStep) registryTask.registryTaskStep();

        //Assert the name of the registryTask is correct
        Assert.assertEquals(taskName, registryTask.name());

        //Assert the resource group name is correct
        Assert.assertEquals(rgName, registryTask.resourceGroupName());

        //Assert location is correct
        Assert.assertEquals(Region.US_WEST_CENTRAL.name(), registryTask.regionName());

        //Assert OS is correct
        Assert.assertEquals(OS.LINUX, registryTask.platform().os());

        //Assert architecture is correct
        Assert.assertEquals(Architecture.AMD64, registryTask.platform().architecture());

        //Assert that the registryTask file path is correct
        Assert.assertEquals(encodedTaskContentUpdate, registryEncodedTaskStep.encodedTaskContent());

        //Assert CPU count is correct
        Assert.assertEquals(1, registryTask.cpuCount());

        //Assert the length of the source triggers array list is correct
        Assert.assertTrue(registryTask.trigger().sourceTriggers().size() == 1);

        //Assert source triggers are correct
        Assert.assertEquals(sourceTrigger.name(), registryTask.trigger().sourceTriggers().get(0).name());

        //Assert base image trigger is correct
        Assert.assertEquals(baseImageTrigger.name(), registryTask.trigger().baseImageTrigger().name());

        boolean errorRaised = false;
        try {
            registryTask.update()
                    .updateDockerTaskStep()
                    .parent()
                    .apply();
        } catch (UnsupportedOperationException e) {
            errorRaised = true;
        }

        //Checking to see whether error is raised if update is called on the incorrect registryTask step type.
        Assert.assertTrue(errorRaised);

    }

    @Test
    @Ignore("Needs personal tokens to run")
    public void DockerTaskTest(){
        final String acrName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String githubRepoUrl = "Replace with your github repository url, eg: https://github.com/Azure/acr.git";
        String githubBranch = "Replace with your github repositoty branch, eg: master";
        String githubPAT = "Replace with your github personal access token which should have the scopes: admin:repo_hook and repo";
        String dockerFilePath = "Replace with your docker file path relative to githubContext, eg: Dockerfile";
        String imageName = "java-sample:{{.Run.ID}}";

        Registry registry = registryManager.containerRegistries().define(acrName)
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


        String taskName = generateRandomResourceName("ft", 10);
        RegistryTask registryTask = registryManager.containerRegistryTasks().define(taskName)

                .withExistingRegistry(rgName, acrName)
                .withLocation(Region.US_WEST_CENTRAL.name())
                .withLinux(Architecture.AMD64)
                .defineDockerTaskStep()
                    .withDockerFilePath(dockerFilePath)
                    .withImageNames(Arrays.asList(imageName))
                    .withCache()
                    .withPushEnabled()
                    .attach()
                .withCpuCount(2)
                .withTrigger(Arrays.asList(sourceTrigger), baseImageTrigger)
                .create();

        RegistryDockerTaskStep registryDockerTaskStep = (RegistryDockerTaskStep) registryTask.registryTaskStep();

        //Assert the name of the registryTask is correct
        Assert.assertEquals(taskName, registryTask.name());

        //Assert the resource group name is correct
        Assert.assertEquals(rgName, registryTask.resourceGroupName());

        //Assert location is correct
        Assert.assertEquals(Region.US_WEST_CENTRAL.name(), registryTask.regionName());

        //Assert OS is correct
        Assert.assertEquals(OS.LINUX, registryTask.platform().os());

        //Assert architecture is correct
        Assert.assertEquals(Architecture.AMD64, registryTask.platform().architecture());

        //Assert that the registryTask file path is correct
        Assert.assertEquals(dockerFilePath, registryDockerTaskStep.dockerFilePath());

        //Assert that the image name array is correct
        Assert.assertEquals(imageName, registryDockerTaskStep.imageNames().get(0));

        //Assert that with cache works
        Assert.assertTrue(!registryDockerTaskStep.noCache());

        //Assert that push is enabled
        Assert.assertTrue(registryDockerTaskStep.isPushEnabled());

        //Assert CPU count is correct
        Assert.assertEquals(2, registryTask.cpuCount());

        //Assert the length of the source triggers array list is correct
        Assert.assertTrue(registryTask.trigger().sourceTriggers().size() == 1);

        //Assert source triggers are correct
        Assert.assertEquals(sourceTrigger.name(), registryTask.trigger().sourceTriggers().get(0).name());

        //Assert base image trigger is correct
        Assert.assertEquals(baseImageTrigger.name(), registryTask.trigger().baseImageTrigger().name());

    }

    @Test
    @Ignore("Needs personal tokens to run")
    public void DockerTaskUpdateTest(){
        final String acrName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String githubRepoUrl = "Replace with your github repository url, eg: https://github.com/Azure/acr.git";
        String githubBranch = "Replace with your github repositoty branch, eg: master";
        String githubPAT = "Replace with your github personal access token which should have the scopes: admin:repo_hook and repo";
        String dockerFilePath = "Replace with your docker file path relative to githubContext, eg: Dockerfile";
        String dockerFilePathUpdate = "Replace this with your docker file path that you updated your registryTask to, if you did update your docker file path";
        String imageName = "java-sample:{{.Run.ID}}";

        Registry registry = registryManager.containerRegistries().define(acrName)
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


        String taskName = generateRandomResourceName("ft", 10);
        RegistryTask registryTask = registryManager.containerRegistryTasks().define(taskName)

                .withExistingRegistry(rgName, acrName)
                .withLocation(Region.US_WEST_CENTRAL.name())
                .withLinux(Architecture.AMD64)
                .defineDockerTaskStep()
                    .withDockerFilePath(dockerFilePath)
                    .withImageNames(Arrays.asList(imageName))
                    .withCache()
                    .withPushEnabled()
                    .attach()
                .withCpuCount(2)
                .withTrigger(Arrays.asList(sourceTrigger), baseImageTrigger)
                .create();

        registryTask.update()
                .updateDockerTaskStep()
                    .withDockerFilePath(dockerFilePathUpdate)
                    .withoutCache()
                    .withPushDisabled()
                    .parent()
                .withCpuCount(1)
                .apply();

        RegistryDockerTaskStep registryDockerTaskStep = (RegistryDockerTaskStep) registryTask.registryTaskStep();

        //Assert the name of the registryTask is correct
        Assert.assertEquals(taskName, registryTask.name());

        //Assert the resource group name is correct
        Assert.assertEquals(rgName, registryTask.resourceGroupName());

        //Assert location is correct
        Assert.assertEquals(Region.US_WEST_CENTRAL.name(), registryTask.regionName());

        //Assert OS is correct
        Assert.assertEquals(OS.LINUX, registryTask.platform().os());

        //Assert architecture is correct
        Assert.assertEquals(Architecture.AMD64, registryTask.platform().architecture());

        //Assert that the registryTask file path is correct
        Assert.assertEquals(dockerFilePathUpdate, registryDockerTaskStep.dockerFilePath());

        //Assert that the image name array is correct
        Assert.assertEquals(imageName, registryDockerTaskStep.imageNames().get(0));

        //Assert that with no cache works
        Assert.assertTrue(registryDockerTaskStep.noCache());

        //Assert that push is disabled
        Assert.assertTrue(!registryDockerTaskStep.isPushEnabled());

        //Assert CPU count is correct
        Assert.assertEquals(1, registryTask.cpuCount());

        //Assert the length of the source triggers array list is correct
        Assert.assertTrue(registryTask.trigger().sourceTriggers().size() == 1);

        //Assert source triggers are correct
        Assert.assertEquals(sourceTrigger.name(), registryTask.trigger().sourceTriggers().get(0).name());

        //Assert base image trigger is correct
        Assert.assertEquals(baseImageTrigger.name(), registryTask.trigger().baseImageTrigger().name());

        boolean errorRaised = false;
        try {
            registryTask.update()
                    .updateFileTaskStep()
                    .parent()
                    .apply();
        } catch (UnsupportedOperationException e) {
            errorRaised = true;
        }

        //Checking to see whether error is raised if update is called on the incorrect registryTask step type.
        Assert.assertTrue(errorRaised);

    }

    @Test
    public void FileTaskRunRequestFromRegistry() {
        final String acrName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String taskFilePath = "https://github.com/iscai-msft/file_task_test.git#master:samples/java/task/acb.yaml";

        Registry registry = registryManager.containerRegistries().define(acrName)
                .withRegion(Region.US_WEST_CENTRAL)
                .withNewResourceGroup(rgName)
                .withPremiumSku()
                .withRegistryNameAsAdminUser()
                .withTag("tag1", "value1")
                .create();

        RegistryTaskRun registryTaskRun = registry.scheduleRun()
                .withWindows()
                .withFileTaskRunRequest()
                    .defineFileTaskStep()
                        .withTaskPath(taskFilePath)
                        .attach()
                    .withSourceLocation("https://github.com/iscai-msft/file_task_test.git")
                .withArchiveEnabled()
                .execute();

        registryTaskRun.refresh();
        Assert.assertEquals(registry.resourceGroupName(), registryTaskRun.resourceGroupName());
        Assert.assertEquals(acrName, registryTaskRun.registryName());
        Assert.assertTrue(registryTaskRun.isArchiveEnabled());
        Assert.assertEquals(OS.WINDOWS,registryTaskRun.platform().os());

        PagedList<RegistryTaskRun> registryTaskRuns = registryManager.registryTaskRuns().listByRegistry(rgName, acrName);
        RegistryTaskRun registryTaskRunFromList = registryTaskRuns.get(0);
        Assert.assertTrue(registryTaskRunFromList.status() != null);
        Assert.assertEquals("QuickRun", registryTaskRunFromList.runType().toString());
        Assert.assertTrue(registryTaskRunFromList.isArchiveEnabled());
        Assert.assertEquals(OS.WINDOWS, registryTaskRunFromList.platform().os());
        Assert.assertEquals("Succeeded", registryTaskRunFromList.provisioningState().toString());



    }

    @Test
    public void FileTaskRunRequestFromRuns() {
        final String acrName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String taskFilePath = "https://github.com/iscai-msft/file_task_test.git#master:samples/java/task/acb.yaml";

        Registry registry = registryManager.containerRegistries().define(acrName)
                .withRegion(Region.US_WEST_CENTRAL)
                .withNewResourceGroup(rgName)
                .withPremiumSku()
                .withRegistryNameAsAdminUser()
                .withTag("tag1", "value1")
                .create();

        RegistryTaskRun registryTaskRun = registryManager.registryTaskRuns().scheduleRun()
                .withExistingRegistry(rgName, acrName)
                .withLinux()
                .withFileTaskRunRequest()
                    .defineFileTaskStep()
                        .withTaskPath(taskFilePath)
                        .attach()
                .withSourceLocation("https://github.com/iscai-msft/file_task_test.git")
                .withArchiveEnabled()
                .execute();

        registryTaskRun.refresh();
        Assert.assertEquals(registry.resourceGroupName(), registryTaskRun.resourceGroupName());
        Assert.assertEquals(acrName, registryTaskRun.registryName());
        //commented out because of server side issue
        Assert.assertTrue(registryTaskRun.isArchiveEnabled());
        Assert.assertEquals(OS.LINUX,registryTaskRun.platform().os());

        PagedList<RegistryTaskRun> registryTaskRuns = registryManager.registryTaskRuns().listByRegistry(rgName, acrName);
        RegistryTaskRun registryTaskRunFromList = registryTaskRuns.get(0);
        Assert.assertTrue(registryTaskRunFromList.status() != null);
        Assert.assertEquals("QuickRun", registryTaskRunFromList.runType().toString());
        Assert.assertTrue(registryTaskRunFromList.isArchiveEnabled());
        Assert.assertEquals(OS.LINUX, registryTaskRunFromList.platform().os());
        Assert.assertEquals("Succeeded", registryTaskRunFromList.provisioningState().toString());



    }

    @Test
    public void EncodedTaskRunRequestFromRegistry() {
        final String acrName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String encodedTaskContent = "dmVyc2lvbjogMC4wLjEKc3RlcHM6CiAgLSBidWlsZDogLXQge3suUnVuLlJlZ2lzdHJ5fX0vamF2YS1zYW1wbGU6e3suUnVuLklEfX0gLgogICAgd29ya2luZ0RpcmVjdG9yeTogc2FtcGxlcy9qYXZhL3Rhc2sKICAtIHB1c2g6IAogICAgLSB7ey5SdW4uUmVnaXN0cnl9fS9qYXZhLXNhbXBsZTp7ey5SdW4uSUR9fQ==";

        Registry registry = registryManager.containerRegistries().define(acrName)
                .withRegion(Region.US_WEST_CENTRAL)
                .withNewResourceGroup(rgName)
                .withPremiumSku()
                .withRegistryNameAsAdminUser()
                .withTag("tag1", "value1")
                .create();

        RegistryTaskRun registryTaskRun = registry.scheduleRun()
                .withLinux()
                .withEncodedTaskRunRequest()
                    .defineEncodedTaskStep()
                        .withBase64EncodedTaskContent(encodedTaskContent)
                        .attach()
                    .withSourceLocation("https://github.com/iscai-msft/encoded_task_test.git")
                .withArchiveEnabled()
                .execute();


        registryTaskRun.refresh();

        Assert.assertEquals(registry.resourceGroupName(), registryTaskRun.resourceGroupName());
        Assert.assertEquals(acrName, registryTaskRun.registryName());
        //commented out because of server side issue
        Assert.assertTrue(registryTaskRun.isArchiveEnabled());
        Assert.assertEquals(OS.LINUX,registryTaskRun.platform().os());

        PagedList<RegistryTaskRun> registryTaskRuns = registryManager.registryTaskRuns().listByRegistry(rgName, acrName);
        RegistryTaskRun registryTaskRunFromList = registryTaskRuns.get(0);
        Assert.assertTrue(registryTaskRunFromList.status() != null);
        Assert.assertEquals("QuickRun", registryTaskRunFromList.runType().toString());
        Assert.assertTrue(registryTaskRunFromList.isArchiveEnabled());
        Assert.assertEquals(OS.LINUX, registryTaskRunFromList.platform().os());
        Assert.assertEquals("Succeeded", registryTaskRunFromList.provisioningState().toString());
    }

    @Test
    public void EncodedTaskRunRequestFromRuns() {
        final String acrName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String encodedTaskContent = "dmVyc2lvbjogMC4wLjEKc3RlcHM6CiAgLSBidWlsZDogLXQge3suUnVuLlJlZ2lzdHJ5fX0vamF2YS1zYW1wbGU6e3suUnVuLklEfX0gLgogICAgd29ya2luZ0RpcmVjdG9yeTogc2FtcGxlcy9qYXZhL3Rhc2sKICAtIHB1c2g6IAogICAgLSB7ey5SdW4uUmVnaXN0cnl9fS9qYXZhLXNhbXBsZTp7ey5SdW4uSUR9fQ==";

        Registry registry = registryManager.containerRegistries().define(acrName)
                .withRegion(Region.US_WEST_CENTRAL)
                .withNewResourceGroup(rgName)
                .withPremiumSku()
                .withRegistryNameAsAdminUser()
                .withTag("tag1", "value1")
                .create();

        RegistryTaskRun registryTaskRun = registryManager.registryTaskRuns().scheduleRun()
                .withExistingRegistry(rgName, acrName)
                .withLinux()
                .withEncodedTaskRunRequest()
                    .defineEncodedTaskStep()
                        .withBase64EncodedTaskContent(encodedTaskContent)
                        .attach()
                .withSourceLocation("https://github.com/iscai-msft/encoded_task_test.git")
                .withArchiveEnabled()
                .execute();

        registryTaskRun.refresh();

        Assert.assertEquals(registry.resourceGroupName(), registryTaskRun.resourceGroupName());
        Assert.assertEquals(acrName, registryTaskRun.registryName());
        Assert.assertTrue(registryTaskRun.isArchiveEnabled());
        Assert.assertEquals(OS.LINUX,registryTaskRun.platform().os());

        PagedList<RegistryTaskRun> registryTaskRuns = registryManager.registryTaskRuns().listByRegistry(rgName, acrName);
        RegistryTaskRun registryTaskRunFromList = registryTaskRuns.get(0);
        Assert.assertTrue(registryTaskRunFromList.status() != null);
        Assert.assertEquals("QuickRun", registryTaskRunFromList.runType().toString());
        Assert.assertTrue(registryTaskRunFromList.isArchiveEnabled());
        Assert.assertEquals(OS.LINUX, registryTaskRunFromList.platform().os());
        Assert.assertEquals("Succeeded", registryTaskRunFromList.provisioningState().toString());
    }

    @Test
    public void DockerTaskRunRequestFromRegistry() {
        final String acrName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String dockerFilePath = "https://github.com/iscai-msft/docker_task_test/tree/master/samples/java/task/Dockerfile";
        String imageName = "java-sample:{{.Run.ID}}";
        String sourceLocation = "https://github.com/iscai-msft/docker_task_test.git";

        Registry registry = registryManager.containerRegistries().define(acrName)
                .withRegion(Region.US_WEST_CENTRAL)
                .withNewResourceGroup(rgName)
                .withPremiumSku()
                .withRegistryNameAsAdminUser()
                .withTag("tag1", "value1")
                .create();

        RegistryTaskRun registryTaskRun = registry.scheduleRun()
                .withLinux()
                .withDockerTaskRunRequest()
                    .defineDockerTaskStep()
                        .withDockerFilePath(dockerFilePath)
                        .withImageNames(Arrays.asList(imageName))
                        .withCache()
                        .withPushEnabled()
                        .attach()
                    .withSourceLocation(sourceLocation)
                .withArchiveEnabled()
                .execute();

        registryTaskRun.refresh();
        Assert.assertEquals(registry.resourceGroupName(), registryTaskRun.resourceGroupName());
        Assert.assertEquals(acrName, registryTaskRun.registryName());
        Assert.assertTrue(registryTaskRun.isArchiveEnabled());
        Assert.assertEquals(OS.LINUX,registryTaskRun.platform().os());

        PagedList<RegistryTaskRun> registryTaskRuns = registryManager.registryTaskRuns().listByRegistry(rgName, acrName);
        RegistryTaskRun registryTaskRunFromList = registryTaskRuns.get(0);
        Assert.assertTrue(registryTaskRunFromList.status() != null);
        Assert.assertEquals("QuickRun", registryTaskRunFromList.runType().toString());
        Assert.assertTrue(registryTaskRunFromList.isArchiveEnabled());
        Assert.assertEquals(OS.LINUX, registryTaskRunFromList.platform().os());
        Assert.assertEquals("Succeeded", registryTaskRunFromList.provisioningState().toString());



    }

    @Test
    public void DockerTaskRunRequestFromRuns() {
        final String acrName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String dockerFilePath = "https://github.com/iscai-msft/docker_task_test/tree/master/samples/java/task/Dockerfile";
        String imageName = "java-sample:{{.Run.ID}}";
        String sourceLocation = "https://github.com/iscai-msft/docker_task_test.git";

        Registry registry = registryManager.containerRegistries().define(acrName)
                .withRegion(Region.US_WEST_CENTRAL)
                .withNewResourceGroup(rgName)
                .withPremiumSku()
                .withRegistryNameAsAdminUser()
                .withTag("tag1", "value1")
                .create();

        RegistryTaskRun registryTaskRun = registryManager.registryTaskRuns().scheduleRun()
                .withExistingRegistry(rgName, acrName)
                .withLinux()
                .withDockerTaskRunRequest()
                    .defineDockerTaskStep()
                        .withDockerFilePath(dockerFilePath)
                        .withImageNames(Arrays.asList(imageName))
                        .withCache()
                        .withPushEnabled()
                        .attach()
                .withSourceLocation(sourceLocation)
                .withArchiveEnabled()
                .execute();

        registryTaskRun.refresh();
        Assert.assertEquals(registry.resourceGroupName(), registryTaskRun.resourceGroupName());
        Assert.assertEquals(acrName, registryTaskRun.registryName());

        Assert.assertTrue(registryTaskRun.isArchiveEnabled());
        Assert.assertEquals(OS.LINUX,registryTaskRun.platform().os());

        PagedList<RegistryTaskRun> registryTaskRuns = registryManager.registryTaskRuns().listByRegistry(rgName, acrName);
        RegistryTaskRun registryTaskRunFromList = registryTaskRuns.get(0);
        Assert.assertTrue(registryTaskRunFromList.status() != null);
        Assert.assertEquals("QuickRun", registryTaskRunFromList.runType().toString());
        Assert.assertTrue(registryTaskRunFromList.isArchiveEnabled());
        Assert.assertEquals(OS.LINUX, registryTaskRunFromList.platform().os());
        Assert.assertEquals("Succeeded", registryTaskRunFromList.provisioningState().toString());


    }

    @Test
    @Ignore("Needs personal tokens to run")
    public void TaskRunRequestFromRegistry() {
        final String acrName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String dockerFilePath = "https://github.com/iscai-msft/docker_task_test/tree/master/samples/java/registryTask/Dockerfile";
        String imageName = "java-sample:{{.Run.ID}}";
        String taskName = generateRandomResourceName("ft", 10);
        String githubRepoUrl = "Replace with your github repository url, eg: https://github.com/Azure/acr.git";
        String githubBranch = "Replace with your github repositoty branch, eg: master";
        String githubPAT = "Replace with your github personal access token which should have the scopes: admin:repo_hook and repo";

        Registry registry = registryManager.containerRegistries().define(acrName)
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

        RegistryTask registryTask = registryManager.containerRegistryTasks().define(taskName)

                .withExistingRegistry(rgName, acrName)
                .withLocation(Region.US_WEST_CENTRAL.name())
                .withLinux(Architecture.AMD64)
                .defineDockerTaskStep()
                    .withDockerFilePath(dockerFilePath)
                    .withImageNames(Arrays.asList(imageName))
                    .withCache()
                    .withPushEnabled()
                    .attach()
                .withCpuCount(2)
                .withTrigger(Arrays.asList(sourceTrigger), baseImageTrigger)
                .create();

        RegistryTaskRun registryTaskRun = registry.scheduleRun()
                .withTaskRunRequest(taskName)
                .withArchiveEnabled()
                .execute();

        boolean stillQueued = true;
        while(stillQueued) {
            registryTaskRun.refresh();
            if (registryTaskRun.status() != RunStatus.QUEUED){
                stillQueued = false;
            }
            if (registryTaskRun.status() == RunStatus.FAILED) {
                System.out.println(registryManager.registryTaskRuns().getLogSasUrl(rgName, acrName, registryTaskRun.runId()));
                stillQueued = false;
            }
            SdkContext.sleep(10000);
        }

        Assert.assertEquals(registry.resourceGroupName(), registryTaskRun.resourceGroupName());
        Assert.assertEquals(acrName, registryTaskRun.registryName());
        Assert.assertEquals(taskName, registryTaskRun.taskName());
        Assert.assertTrue(registryTaskRun.isArchiveEnabled());
        Assert.assertEquals(OS.LINUX,registryTaskRun.platform().os());

        PagedList<RegistryTaskRun> registryTaskRuns = registryManager.registryTaskRuns().listByRegistry(rgName, acrName);
        RegistryTaskRun registryTaskRunFromList = registryTaskRuns.get(0);
        Assert.assertTrue(registryTaskRunFromList.status() != null);
        Assert.assertEquals("QuickRun", registryTaskRunFromList.runType().toString());
        Assert.assertTrue(registryTaskRunFromList.isArchiveEnabled());
        Assert.assertEquals(OS.LINUX, registryTaskRunFromList.platform().os());
        Assert.assertEquals("Succeeded", registryTaskRunFromList.provisioningState().toString());
        Assert.assertEquals(taskName, registryTaskRunFromList.taskName());


    }

    @Test
    @Ignore("Needs personal tokens to run")
    public void TaskRunRequestFromRuns() {
        final String acrName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String dockerFilePath = "https://github.com/iscai-msft/docker_task_test/tree/master/samples/java/registryTask/Dockerfile";
        String imageName = "java-sample:{{.Run.ID}}";
        String taskName = generateRandomResourceName("ft", 10);
        String githubRepoUrl = "Replace with your github repository url, eg: https://github.com/Azure/acr.git";
        String githubBranch = "Replace with your github repositoty branch, eg: master";
        String githubPAT = "Replace with your github personal access token which should have the scopes: admin:repo_hook and repo";

        Registry registry = registryManager.containerRegistries().define(acrName)
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

        RegistryTask registryTask = registryManager.containerRegistryTasks().define(taskName)

                .withExistingRegistry(rgName, acrName)
                .withLocation(Region.US_WEST_CENTRAL.name())
                .withLinux(Architecture.AMD64)
                .defineDockerTaskStep()
                    .withDockerFilePath(dockerFilePath)
                    .withImageNames(Arrays.asList(imageName))
                    .withCache()
                    .withPushEnabled()
                    .attach()
                .withCpuCount(2)
                .withTrigger(Arrays.asList(sourceTrigger), baseImageTrigger)
                .create();

        RegistryTaskRun registryTaskRun = registryManager.registryTaskRuns().scheduleRun()
                .withExistingRegistry(rgName, acrName)
                .withTaskRunRequest(taskName)
                .withArchiveEnabled()
                .execute();

        boolean stillQueued = true;
        while(stillQueued) {
            registryTaskRun.refresh();
            if (registryTaskRun.status() != RunStatus.QUEUED){
                stillQueued = false;
            }
            if (registryTaskRun.status() == RunStatus.FAILED) {
                System.out.println(registryManager.registryTaskRuns().getLogSasUrl(rgName, acrName, registryTaskRun.runId()));
                stillQueued = false;
            }
            SdkContext.sleep(10000);
        }
        Assert.assertEquals(registry.resourceGroupName(), registryTaskRun.resourceGroupName());
        Assert.assertEquals(acrName, registryTaskRun.registryName());
        Assert.assertEquals(taskName, registryTaskRun.taskName());
        Assert.assertTrue(registryTaskRun.isArchiveEnabled());
        Assert.assertEquals(OS.LINUX,registryTaskRun.platform().os());


        PagedList<RegistryTaskRun> registryTaskRuns = registryManager.registryTaskRuns().listByRegistry(rgName, acrName);
        RegistryTaskRun registryTaskRunFromList = registryTaskRuns.get(0);
        Assert.assertTrue(registryTaskRunFromList.status() != null);
        Assert.assertEquals("QuickRun", registryTaskRunFromList.runType().toString());
        Assert.assertTrue(registryTaskRunFromList.isArchiveEnabled());
        Assert.assertEquals(OS.LINUX, registryTaskRunFromList.platform().os());
        Assert.assertEquals("Succeeded", registryTaskRunFromList.provisioningState().toString());
        Assert.assertEquals(taskName, registryTaskRunFromList.taskName());




    }

    @Test
    public void GetBuildSourceUploadUrlFromRegistryAndRegistries() {
        final String acrName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);

        Registry registry = registryManager.containerRegistries().define(acrName)
                .withRegion(Region.US_WEST_CENTRAL)
                .withNewResourceGroup(rgName)
                .withPremiumSku()
                .withRegistryNameAsAdminUser()
                .withTag("tag1", "value1")
                .create();

        //Calling getBuildSourceUploadUrl from Registry
        SourceUploadDefinition buildSourceUploadUrlRegistry = registry.getBuildSourceUploadUrl();
        Assert.assertNotNull(buildSourceUploadUrlRegistry.relativePath());
        Assert.assertNotNull(buildSourceUploadUrlRegistry.uploadUrl());

        //Calling getBuildSourceUploadUrl from Registries
        SourceUploadDefinition buildSourceUploadUrlRegistries = registryManager.containerRegistries().getBuildSourceUploadUrl(rgName, acrName);
        Assert.assertNotNull(buildSourceUploadUrlRegistries.relativePath());
        Assert.assertNotNull(buildSourceUploadUrlRegistries.uploadUrl());
    }


    @Test
    @Ignore("server side issue regarding delete function")
    public void CancelAndDeleteRunsAndTasks() {
        final String acrName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String taskName = generateRandomResourceName("ft", 10);
        String dockerFilePath = "Relative path to Dockerfile. Path is relative to the Github repo url.";
        String imageName = "java-sample:{{.Run.ID}}";
        String githubRepoUrl = "Replace with your github repository url, eg: https://github.com/Azure/acr.git";
        String githubBranch = "Replace with your github repositoty branch, eg: master";
        String githubPAT = "Replace with your github personal access token which should have the scopes: admin:repo_hook and repo";

        Registry registry = registryManager.containerRegistries().define(acrName)
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

        RegistryTask registryTask = registryManager.containerRegistryTasks().define(taskName)

                .withExistingRegistry(rgName, acrName)
                .withLocation(Region.US_WEST_CENTRAL.name())
                .withLinux(Architecture.AMD64)
                .defineDockerTaskStep()
                .withDockerFilePath(dockerFilePath)
                .withImageNames(Arrays.asList(imageName))
                .withCache()
                .withPushEnabled()
                .attach()
                .withCpuCount(2)
                .withTrigger(Arrays.asList(sourceTrigger), baseImageTrigger)
                .create();

        RegistryTaskRun registryTaskRun = registry.scheduleRun()
                .withTaskRunRequest(taskName)
                .withArchiveEnabled()
                .execute();

        boolean stillQueued = true;
        while(stillQueued) {
            registryTaskRun.refresh();
            if (registryTaskRun.status() != RunStatus.QUEUED){
                stillQueued = false;
            }
            if (registryTaskRun.status() == RunStatus.FAILED) {
                System.out.println(registryManager.registryTaskRuns().getLogSasUrl(rgName, acrName, registryTaskRun.runId()));
                Assert.fail("Registry registryTask run failed");
            }
            SdkContext.sleep(10000);
        }

        Assert.assertTrue(registryManager.registryTaskRuns().listByRegistry(rgName, acrName).size() == 1);

        //cancelling the run we just created
        registryManager.inner().runs().cancel(rgName, acrName, registryTaskRun.runId());

        boolean notCanceled = true;
        while(notCanceled) {
            registryTaskRun.refresh();
            if (registryTaskRun.status() == RunStatus.CANCELED){
                notCanceled = false;
            }
            if (registryTaskRun.status() == RunStatus.FAILED) {
                System.out.println(registryManager.registryTaskRuns().getLogSasUrl(rgName, acrName, registryTaskRun.runId()));
                Assert.fail("Registry registryTask run failed");
            }
            SdkContext.sleep(10000);
        }

        PagedList<RegistryTaskRun> registryTaskRuns = registryManager.registryTaskRuns().listByRegistry(rgName, acrName);

        for (RegistryTaskRun rtr : registryTaskRuns) {
            Assert.assertTrue(rtr.status() == RunStatus.CANCELED);
        }

        //deleting the run we just cancelled

        for (RegistryTaskRun rtr : registryTaskRuns) {
            registryManager.containerRegistryTasks().deleteByRegistry(rgName, acrName, taskName);
        }

        registryTaskRuns = registryManager.registryTaskRuns().listByRegistry(rgName, acrName);
        Assert.assertTrue(registryManager.containerRegistryTasks().listByRegistry(rgName, acrName).size() == 0);



    }

    @Test
    public void GetLogSasUrl() {
        final String acrName = generateRandomResourceName("acr", 10);
        final String rgName = generateRandomResourceName("rgacr", 10);
        String dockerFilePath = "https://github.com/iscai-msft/docker_task_test/tree/master/samples/java/task/Dockerfile";
        String imageName = "java-sample:{{.Run.ID}}";
        String sourceLocation = "https://github.com/iscai-msft/docker_task_test.git";

        Registry registry = registryManager.containerRegistries().define(acrName)
                .withRegion(Region.US_WEST_CENTRAL)
                .withNewResourceGroup(rgName)
                .withPremiumSku()
                .withRegistryNameAsAdminUser()
                .withTag("tag1", "value1")
                .create();

        RegistryTaskRun registryTaskRun = registryManager.registryTaskRuns().scheduleRun()
                .withExistingRegistry(rgName, acrName)
                .withLinux()
                .withDockerTaskRunRequest()
                    .defineDockerTaskStep()
                        .withDockerFilePath(dockerFilePath)
                        .withImageNames(Arrays.asList(imageName))
                        .withCache()
                        .withPushEnabled()
                        .attach()
                .withSourceLocation(sourceLocation)
                .withArchiveEnabled()
                .execute();

        String sasUrl = registryManager.registryTaskRuns().getLogSasUrl(rgName, acrName, registryTaskRun.runId());
        Assert.assertNotNull(sasUrl);
        Assert.assertNotEquals("", sasUrl);

    }



    @Override
    protected void cleanUpResources() {
    }
}
