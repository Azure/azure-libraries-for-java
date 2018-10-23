/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.samples;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import com.microsoft.azure.v2.management.Azure;
import com.microsoft.azure.v2.management.resources.Deployment;
import com.microsoft.azure.v2.management.resources.DeploymentMode;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.rest.v2.policy.HttpLogDetailLevel;
import com.microsoft.rest.v2.policy.HttpLoggingPolicyFactory;
import io.reactivex.Observable;

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

            ObjectMapper mapper = new ObjectMapper();
            final String parameters = mapper.createObjectNode()
                    .set("sshKeyData", mapper.createObjectNode()
                            .put("value", sshKey)).toString();

            System.out.println("Starting VM deployments...");

            // Store all deployments in a list
            final List<Deployment> deploymentList = new ArrayList<>();
            final CountDownLatch latch = new CountDownLatch(1);

            Observable.range(1, numDeployments)
                    .flatMap(integer -> {
                            try {
                                String params;
                                if (integer == numDeployments) {
                                    params = "{\"sshKeyData\":{\"value\":\"bad content\"}}"; // Invalid parameters as a negative path
                                } else {
                                    params = parameters;
                                }
                                System.out.println("1. beginCreateAsync[" + integer + "]");

                                Observable<Deployment> dep = azure.deployments()
                                        .define(deploymentPrefix + "-" + integer)
                                        .withNewResourceGroup(rgPrefix + "-" + integer, Region.US_SOUTH_CENTRAL)
                                        .withTemplateLink(templateUri, templateContentVersion)
                                        .withParameters(params)
                                        .withMode(DeploymentMode.COMPLETE)
                                        .beginCreateAsync();

                                if (dep == null) {
                                    System.out.println("2. beginCreateAsync[null]");
                                } else {
                                    System.out.println("2. beginCreateAsync[non-null]");
                                }

                                return dep;
                            } catch (IOException e) {
                                System.out.println("Obs::onError" + e.getMessage());
                                return Observable.error(e);
                            }
                    })
                    .doOnNext(deployment -> {
                            System.out.println("Deployment created: " + deployment.name());
                            deploymentList.add(deployment);
                    })
                    .doOnComplete(() -> {
                        latch.countDown();
                    }).subscribe();

            latch.await();

            // Track status of deployment operations

            System.out.println("Checking deployment operations...");
            final CountDownLatch operationLatch = new CountDownLatch(1);
            Observable.fromIterable(deploymentList)
                    .flatMap(deployment -> {
                            return deployment.refreshAsync()
                                    .toObservable()
                                    .flatMap(deployment1 -> {
                                        return deployment1.deploymentOperations().listAsync();
                                    })
                                    .map(deploymentOperations -> {
                                            synchronized (deploymentList) {
                                                System.out.println("--------------------" + deployment.name() + "--------------------");

                                                    if (deploymentOperations.targetResource() != null) {
                                                        System.out.println(String.format("%s - %s: %s %s",
                                                                deploymentOperations.targetResource().resourceType(),
                                                                deploymentOperations.targetResource().resourceName(),
                                                                deploymentOperations.provisioningState(),
                                                                deploymentOperations.statusMessage() != null ? deploymentOperations.statusMessage() : ""));
                                                    }
                                                }
                                            return deploymentOperations;
                                    })
                                    .repeatWhen(observable -> {
                                            return observable.delay(pollingInterval, TimeUnit.SECONDS);
                                    })
                                    .takeUntil(deploymentOperations -> {
                                            return "Succeeded".equalsIgnoreCase(deployment.provisioningState())
                                                    || "Canceled".equalsIgnoreCase(deployment.provisioningState())
                                                    || "Failed".equalsIgnoreCase(deployment.provisioningState());

                                    });
                    }).doOnComplete(() -> {
                        System.out.println("doOnComplete");
                        operationLatch.countDown();
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
                    String.join(", ", succeeded), String.join(", ", failed)));


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
                    .withRequestPolicy(new HttpLoggingPolicyFactory(HttpLogDetailLevel.BODY_AND_HEADERS))
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