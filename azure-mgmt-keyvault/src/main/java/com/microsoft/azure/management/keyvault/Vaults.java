/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault;

import java.util.List;

import com.microsoft.azure.CloudException;
import com.microsoft.azure.ListOperationCallback;
import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
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
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import com.microsoft.rest.ServiceResponse;

import rx.Observable;

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
     * @return the PagedList&lt;DeletedVault&gt; object if successful.
     */
	public PagedList<DeletedVault> listDeleted();
	
    /**
     * Gets information about the deleted vaults in a subscription.
     *
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<DeletedVault>> listDeletedAsync(final ListOperationCallback<DeletedVault> serviceCallback);

    /**
     * Gets information about the deleted vaults in a subscription.
     *
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;DeletedVault&gt; object
     */
    public Observable<DeletedVault> listDeletedAsync();

    /**
     * Gets information about the deleted vaults in a subscription.
     *
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;DeletedVault&gt; object
     */
    public Observable<ServiceResponse<Page<DeletedVault>>> listDeletedWithServiceResponseAsync();
	
    /**
     * Gets information about the deleted vaults in a subscription.
     *
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;DeletedVault&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<DeletedVault>>> listDeletedSinglePageAsync();
    
    /**
     * Gets information about the deleted vaults in a subscription.
     *
    ServiceResponse<PageImpl<DeletedVault>> * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;DeletedVault&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<DeletedVault>>> listDeletedNextSinglePageAsync(final String nextPageLink);

    /**
     * Gets the deleted Azure key vault.
     *
     * @param vaultName The name of the vault.
     * @param location The location of the deleted vault.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the DeletedVault object if successful.
     */
	public DeletedVault getDeleted(String vaultName, String location);
	
    /**
     * Gets the deleted Azure key vault.
     *
     * @param vaultName The name of the vault.
     * @param location The location of the deleted vault.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
	public Observable<DeletedVault> getDeletedAsync(String vaultName, String location);
	
    /**
     * Gets the deleted Azure key vault.
     *
     * @param vaultName The name of the vault.
     * @param location The location of the deleted vault.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the DeletedVault object
     */
	public Observable<ServiceResponse<DeletedVault>> getDeletedWithServiceResponseAsync(String vaultName, String location);
	
    /**
     * Gets the deleted Azure key vault.
     *
     * @param vaultName The name of the vault.
     * @param location The location of the deleted vault.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
	public ServiceFuture<DeletedVault> getDeletedAsync(String vaultname, String location, final ServiceCallback<DeletedVault> serviceCallback);
	
	
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
	
    /**
     * Permanently deletes the specified vault. aka Purges the deleted Azure key vault.
     *
     * @param vaultName The name of the soft-deleted vault.
     * @param location The location of the soft-deleted vault.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    public Observable<Void> purgeDeletedAsync(String vaultName, String location);
   
    /**
     * Checks that the vault name is valid and is not already in use.
     *
     * @param name The vault name.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the CheckNameAvailabilityResult object if successful.
     */
    public CheckNameAvailabilityResult checkNameAvailability(String name);

    /**
     * Checks that the vault name is valid and is not already in use.
     *
     * @param name The vault name.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the CheckNameAvailabilityResult object
     */
    public Observable<CheckNameAvailabilityResult> checkNameAvailabilityAsync(String name);

}
