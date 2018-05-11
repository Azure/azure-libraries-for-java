/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault;

import com.microsoft.azure.CloudException;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.keyvault.implementation.KeyVaultManager;
import com.microsoft.azure.management.keyvault.implementation.VaultsInner;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsDeletingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsListingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingById;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.rest.ServiceFuture;

import rx.Completable;
import rx.Observable;

/**
 * Entry point for key vaults management API.
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.Fluent.KeyVault")
public interface Vaults extends SupportsCreating<Vault.DefinitionStages.Blank>, SupportsDeletingById,
        SupportsListingByResourceGroup<Vault>, SupportsGettingByResourceGroup<Vault>, SupportsGettingById<Vault>,
        SupportsDeletingByResourceGroup, HasManager<KeyVaultManager>, HasInner<VaultsInner> {

    /**
     * Gets information about the deleted vaults in a subscription.
     *
     * @throws IllegalArgumentException
     *             thrown if parameters fail the validation
     * @throws CloudException
     *             thrown if the request is rejected by server
     * @throws RuntimeException
     *             all other wrapped checked exceptions if the request fails to be
     *             sent
     * @return the PagedList&lt;DeletedVault&gt; object if successful.
     */
    @Beta(SinceVersion.V1_11_0)
    PagedList<DeletedVault> listDeleted();

    /**
     * Gets information about the deleted vaults in a subscription.
     *
     * @throws IllegalArgumentException
     *             thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;DeletedVault&gt; object
     */
    @Beta(SinceVersion.V1_11_0)
    Observable<DeletedVault> listDeletedAsync();

    /**
     * Gets the deleted Azure key vault.
     *
     * @param vaultName
     *            The name of the vault.
     * @param location
     *            The location of the deleted vault.
     * @throws IllegalArgumentException
     *             thrown if parameters fail the validation
     * @throws CloudException
     *             thrown if the request is rejected by server
     * @throws RuntimeException
     *             all other wrapped checked exceptions if the request fails to be
     *             sent
     * @return the DeletedVault object if successful.
     */
    @Beta(SinceVersion.V1_11_0)
    DeletedVault getDeleted(String vaultName, String location);

    /**
     * Gets the deleted Azure key vault.
     *
     * @param vaultName
     *            The name of the vault.
     * @param location
     *            The location of the deleted vault.
     * @throws IllegalArgumentException
     *             thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    @Beta(SinceVersion.V1_11_0)
    Observable<DeletedVault> getDeletedAsync(String vaultName, String location);

    /**
     * Permanently deletes the specified vault. aka Purges the deleted Azure key
     * vault.
     *
     * @param vaultName
     *            The name of the soft-deleted vault.
     * @param location
     *            The location of the soft-deleted vault.
     * @throws IllegalArgumentException
     *             thrown if parameters fail the validation
     * @throws CloudException
     *             thrown if the request is rejected by server
     * @throws RuntimeException
     *             all other wrapped checked exceptions if the request fails to be
     *             sent
     */
    @Beta(SinceVersion.V1_11_0)
    void purgeDeleted(String vaultName, String location);

    /**
     * Permanently deletes the specified vault. aka Purges the deleted Azure key
     * vault.
     *
     * @param vaultName
     *            The name of the soft-deleted vault.
     * @param location
     *            The location of the soft-deleted vault.
     * @throws IllegalArgumentException
     *             thrown if parameters fail the validation
     * @return the observable for the request
     */
    @Beta(SinceVersion.V1_11_0)
    Completable purgeDeletedAsync(String vaultName, String location);

    /**
     * Checks that the vault name is valid and is not already in use.
     *
     * @param name
     *            The vault name.
     * @throws IllegalArgumentException
     *             thrown if parameters fail the validation
     * @throws CloudException
     *             thrown if the request is rejected by server
     * @throws RuntimeException
     *             all other wrapped checked exceptions if the request fails to be
     *             sent
     * @return the CheckNameAvailabilityResult object if successful.
     */
    @Beta(SinceVersion.V1_11_0)
    CheckNameAvailabilityResult checkNameAvailability(String name);

    /**
     * Checks that the vault name is valid and is not already in use.
     *
     * @param name
     *            The vault name.
     * @throws IllegalArgumentException
     *             thrown if parameters fail the validation
     * @return the observable to the CheckNameAvailabilityResult object
     */
    @Beta(SinceVersion.V1_11_0)
    Observable<CheckNameAvailabilityResult> checkNameAvailabilityAsync(String name);
    
    /**
     * Recovers a soft deleted vault.
     * 
     * @param resourceGroupName The name of the Resource Group to which the server belongs.
     * @param vaultName Name of the vault
     * @param location The location of the deleted vault.
     * @return the recovered Vault object if successful
     */
    @Beta(SinceVersion.V1_11_0)
    Vault recoverSoftDeletedVault(String resourceGroupName, String vaultName, String location);

    /**
     * Recovers a soft deleted vault.
     * 
     * @param resourceGroupName The name of the Resource Group to which the server belongs.
     * @param vaultName Name of the vault
     * @param location The location of the deleted vault.
     * @return the recovered Vault object if successful
     */
    @Beta(SinceVersion.V1_11_0)
    Observable<Vault> recoverSoftDeletedVaultAsync(String resourceGroupName, String vaultName, String location);

}
