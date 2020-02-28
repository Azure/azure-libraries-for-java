/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.samples;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Joiner;
import com.google.common.io.ByteStreams;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.resources.Deployment;
import com.microsoft.azure.management.resources.DeploymentMode;
import com.microsoft.azure.management.resources.DeploymentOperation;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.rest.LogLevel;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Azure Resource sample for deploying resources using an ARM template and
 * showing progress.
 */

public final class DeployUsingARMTemplateWithDeploymentOperations {

    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @param defaultPollingInterval polling interval in seconds
     * @return true if sample runs successfully
     */
    public static boolean runSample(final Azure azure, int defaultPollingInterval) {
        final String rgPrefix         = SdkContext.randomResourceName("rgJavaTest", 16);
        final String deploymentPrefix = SdkContext.randomResourceName("javaTest", 16);
        final String sshKey           = getSSHPublicKey();
        final int    numDeployments   = 3;
        final int    pollingInterval  = defaultPollingInterval < 0 ? 15 : defaultPollingInterval; // in seconds

        try {
            // Use the Simple VM Template with SSH Key auth from GH quickstarts

            final String templateUri = "https://raw.githubusercontent.com/Azure/azure-quickstart-templates/master/101-vm-sshkey/azuredeploy.json";
            final String templateContentVersion = "1.0.0.0";

            // Template only needs an SSH Key parameter

            final ObjectMapper mapper = new ObjectMapper();
            final ObjectNode rootNode = mapper.createObjectNode();
            rootNode.set("adminPublicKey", mapper.createObjectNode().put("value", sshKey));
            rootNode.set("projectName", mapper.createObjectNode().put("value", "fluenttest"));
            rootNode.set("adminUsername", mapper.createObjectNode().put("value", "fluenttesting"));
            final String parameters = rootNode.toString();
            System.out.println("Starting VM deployments...");

            // Store all deployments in a list
            final List<Deployment> deploymentList = new ArrayList<>();
            final CountDownLatch latch = new CountDownLatch(1);

            Observable.range(1, numDeployments)
                    .flatMap(new Func1<Integer, Observable<Deployment>>() {
                        @Override
                        public Observable<Deployment> call(Integer integer) {
                            try {
                                String params;
                                if (integer == numDeployments) {
                                    rootNode.set("adminPublicKey", mapper.createObjectNode().put("value", "bad content"));
                                    params = rootNode.toString(); // Invalid parameters as a negative path
                                } else {
                                    params = parameters;
                                }
                                return azure.deployments()
                                        .define(deploymentPrefix + "-" + integer)
                                        .withNewResourceGroup(rgPrefix + "-" + integer, Region.US_SOUTH_CENTRAL)
                                        .withTemplateLink(templateUri, templateContentVersion)
                                        .withParameters(params)
                                        .withMode(DeploymentMode.COMPLETE)
                                        .beginCreateAsync();
                            } catch (IOException e) {
                                return Observable.error(e);
                            }
                        }
                    })
                    .doOnNext(new Action1<Deployment>() {
                        @Override
                        public void call(Deployment deployment) {
                            System.out.println("Deployment created: " + deployment.name());
                            deploymentList.add(deployment);
                        }
                    })
                    .doOnCompleted(new Action0() {
                        @Override
                        public void call() {
                            latch.countDown();
                        }
                    }).subscribe();

            latch.await();

            // Track status of deployment operations

            System.out.println("Checking deployment operations...");
            final CountDownLatch operationLatch = new CountDownLatch(1);
            Observable.from(deploymentList)
                    .flatMap(new Func1<Deployment, Observable<List<DeploymentOperation>>>() {
                        @Override
                        public Observable<List<DeploymentOperation>> call(final Deployment deployment) {
                            return deployment.refreshAsync()
                                    .flatMap(new Func1<Deployment, Observable<List<DeploymentOperation>>>() {
                                        @Override
                                        public Observable<List<DeploymentOperation>> call(Deployment deployment) {
                                            return deployment.deploymentOperations().listAsync().toList();
                                        }
                                    })
                                    .map(new Func1<List<DeploymentOperation>, List<DeploymentOperation>>() {
                                        @Override
                                        public List<DeploymentOperation> call(List<DeploymentOperation> deploymentOperations) {
                                            synchronized (deploymentList) {
                                                System.out.println("--------------------" + deployment.name() + "--------------------");
                                                for (DeploymentOperation operation : deploymentOperations) {
                                                    if (operation.targetResource() != null) {
                                                        System.out.println(String.format("%s - %s: %s %s",
                                                                operation.targetResource().resourceType(),
                                                                operation.targetResource().resourceName(),
                                                                operation.provisioningState(),
                                                                operation.statusMessage() != null ? operation.statusMessage() : ""));
                                                    }
                                                }
                                            }
                                            return deploymentOperations;
                                        }
                                    })
                                    .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                                        @Override
                                        public Observable<?> call(Observable<? extends Void> observable) {
                                            return observable.delay(pollingInterval, TimeUnit.SECONDS);                                     }
                                    })
                                    .takeUntil(new Func1<List<DeploymentOperation>, Boolean>() {
                                        @Override
                                        public Boolean call(List<DeploymentOperation> deploymentOperations) {
                                            return "Succeeded".equalsIgnoreCase(deployment.provisioningState())
                                                    || "Canceled".equalsIgnoreCase(deployment.provisioningState())
                                                    || "Failed".equalsIgnoreCase(deployment.provisioningState());

                                        }
                                    });
                        }
                    }).doOnCompleted(new Action0() {
                        @Override
                        public void call() {
                            operationLatch.countDown();
                        }
                    }).subscribe();
            operationLatch.await();

            // Summary

            List<String> succeeded = new ArrayList<>();
            List<String> failed = new ArrayList<>();
            for (Deployment deployment : deploymentList) {
                if ("Succeeded".equalsIgnoreCase(deployment.provisioningState())) {
                    succeeded.add(deployment.name());
                } else {
                    failed.add(deployment.name());
                }
            }
            System.out.println(String.format("Deployments %s succeeded. %s failed.",
                    Joiner.on(", ").join(succeeded), Joiner.on(", ").join(failed)));

            return true;
        } catch (Exception f) {

            System.out.println(f.getMessage());
            f.printStackTrace();

        } finally {
            try {
                for (int i = 1; i != numDeployments; i++) {
                    String rgName = rgPrefix + "-" + i;
                    System.out.println("Deleting Resource Group: " + rgName);
                    azure.resourceGroups().beginDeleteByName(rgName);
                    System.out.println("Deleted Resource Group: " + rgName);
                }
            } catch (NullPointerException npe) {
                System.out.println("Did not create any resources in Azure. No clean up is necessary");
            } catch (Exception g) {
                g.printStackTrace();
            }

        }
        return false;
    }

    /**
     * Main entry point.
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {
            //=================================================================
            // Authenticate

            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));

            Azure azure = Azure.configure()
                    .withLogLevel(LogLevel.NONE)
                    .authenticate(credFile)
                    .withDefaultSubscription();

            runSample(azure, -1);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    private static String getSSHPublicKey() {
        byte[] content;
        try {
            content = ByteStreams.toByteArray(DeployUsingARMTemplateWithDeploymentOperations.class.getResourceAsStream("/rsa.pub"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(content);
    }

    private DeployUsingARMTemplateWithDeploymentOperations() {

    }
}
