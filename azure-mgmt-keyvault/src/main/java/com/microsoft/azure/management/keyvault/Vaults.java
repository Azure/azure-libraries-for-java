/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault;

import com.microsoft.azure.CloudException;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.keyvault.implementation.KeyVaultManager;
import com.microsoft.azure.management.keyvault.implementation.VaultsInner;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsDeletingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsListingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingById;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * Entry point for key vaults management API.
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.Fluent.KeyVault")
public interface Vaults extends
        SupportsCreating<Vault.DefinitionStages.Blank>,
        SupportsDeletingById,
        SupportsListingByResourceGroup<Vault>,
        SupportsGettingByResourceGroup<Vault>,
        SupportsGettingById<Vault>,
        SupportsDeletingByResourceGroup,
        HasManager<KeyVaultManager>,
        HasInner<VaultsInner> {
	
    /**
     * Gets information about the deleted vaults in a subscription.
     *
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PagedList&lt;DeletedVaultInner&gt; object if successful.
     */
	public PagedList<DeletedVault> listDeleted();
	
    /**
     * Gets the deleted Azure key vault.
     *
     * @param vaultName The name of the vault.
     * @param location The location of the deleted vault.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the DeletedVaultInner object if successful.
     */
	public DeletedVault getDeleted(String vaultName, String location);
	
    /**
     * Permanently deletes the specified vault. aka Purges the deleted Azure key vault.
     *
     * @param vaultName The name of the soft-deleted vault.
     * @param location The location of the soft-deleted vault.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     */
	public void purgeDeleted(String vaultName, String location);
}
