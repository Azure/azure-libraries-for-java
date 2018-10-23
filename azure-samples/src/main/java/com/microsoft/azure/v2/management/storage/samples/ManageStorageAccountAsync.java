/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.storage.samples;


import com.microsoft.azure.v2.management.Azure;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.v2.management.samples.Utils;
import com.microsoft.azure.v2.management.storage.StorageAccount;
import com.microsoft.azure.v2.management.storage.StorageAccounts;
import com.microsoft.rest.v2.policy.HttpLogDetailLevel;
import com.microsoft.rest.v2.policy.HttpLoggingPolicyFactory;
import io.reactivex.Observable;

import java.io.File;
/**
 * Azure Storage sample for managing storage accounts -
 *  - Create two storage account
 *  - List storage accounts and regenerate storage account access keys
 *  - Delete both storage account.
 */

public final class ManageStorageAccountAsync {
    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(final Azure azure) {
        final String storageAccountName = Utils.createRandomName("sa");
        final String storageAccountName2 = Utils.createRandomName("sa2");
        final String rgName = Utils.createRandomName("rgSTMS");
        try {

            // ============================================================
            // Create storage accounts

            System.out.println("Creating a Storage Accounts");

            Observable.merge(
                    azure.storageAccounts().define(storageAccountName)
                            .withRegion(Region.US_EAST)
                            .withNewResourceGroup(rgName)
                            .createAsync(),
                    azure.storageAccounts().define(storageAccountName2)
                            .withRegion(Region.US_EAST)
                            .withNewResourceGroup(rgName)
                            .createAsync())
                    .map(indexable -> {
                            if (indexable instanceof StorageAccount) {
                                StorageAccount storageAccount = (StorageAccount) indexable;

                                System.out.println("Created a Storage Account:");
                                Utils.print(storageAccount);
                            }
                            return indexable;
                    }).blockingLast();

            // ============================================================
            // List storage accounts and regenerate storage account access keys

            System.out.println("Listing storage accounts");

            StorageAccounts storageAccounts = azure.storageAccounts();

            storageAccounts.listByResourceGroupAsync(rgName)
                    .flatMapMaybe(storageAccount -> {
                            System.out.println("Getting storage account access keys for Storage Account "
                                    + storageAccount.name() + " created @ " + storageAccount.creationTime());

                            return storageAccount.getKeysAsync()
                                    .flatMap(storageAccountKeys -> {
                                            System.out.println("Regenerating first storage account access key");
                                            return storageAccount.regenerateKeyAsync(storageAccountKeys.get(0).keyName());
                                    });
                    })
                    .map(storageAccountKeys -> {
                            Utils.print(storageAccountKeys);
                            return storageAccountKeys;
                    }).blockingLast();

            // ============================================================
            // Delete storage accounts

            storageAccounts.listByResourceGroupAsync(rgName)
                    .flatMap(storageAccount -> {
                            System.out.println("Deleting a storage account - " + storageAccount.name()
                                    + " created @ " + storageAccount.creationTime());
                            return azure.storageAccounts().deleteByIdAsync(storageAccount.id()).toObservable();
                    }).doOnComplete(() -> {
                        System.out.println("Deleted storage account");
            });

            return true;
        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        } finally {
            try {
                System.out.println("Deleting Resource Group: " + rgName);
                azure.resourceGroups().deleteByNameAsync(rgName).blockingAwait();
                System.out.println("Deleted Resource Group: " + rgName);
            }
            catch (Exception e) {
                System.out.println("Did not create any resources in Azure. No clean up is necessary");
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
            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));

            Azure azure = Azure.configure()
                    .withRequestPolicy(new HttpLoggingPolicyFactory(HttpLogDetailLevel.BODY_AND_HEADERS))
                    .authenticate(credFile)
                    .withDefaultSubscription();

            // Print selected subscription
            System.out.println("Selected subscription: " + azure.subscriptionId());

            runSample(azure);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private ManageStorageAccountAsync() {

    }
}