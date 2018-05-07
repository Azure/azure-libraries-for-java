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
        SupportsListing<Vault>,
        SupportsListingByResourceGroup<Vault>,
        SupportsGettingByResourceGroup<Vault>,
        SupportsGettingById<Vault>,
        SupportsDeletingByResourceGroup,
        HasManager<KeyVaultManager>,
        HasInner<VaultsInner> {
	
    /**
     * Create or update a key vault in the specified subscription.
     *
     * @param resourceGroupName The name of the Resource Group to which the server belongs.
     * @param vaultName Name of the vault
     * @param parameters Parameters to create or update the vault
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<Vault> createOrUpdateAsync(String resourceGroupName, String vaultName, VaultCreateOrUpdateParameters parameters, final ServiceCallback<Vault> serviceCallback);

    /**
     * Create or update a key vault in the specified subscription.
     *
     * @param resourceGroupName The name of the Resource Group to which the server belongs.
     * @param vaultName Name of the vault
     * @param parameters Parameters to create or update the vault
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    public Observable<Vault> createOrUpdateAsync(String resourceGroupName, String vaultName, VaultCreateOrUpdateParameters parameters);

    /**
     * Create or update a key vault in the specified subscription.
     *
     * @param resourceGroupName The name of the Resource Group to which the server belongs.
     * @param vaultName Name of the vault
     * @param parameters Parameters to create or update the vault
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    public Observable<ServiceResponse<Vault>> createOrUpdateWithServiceResponseAsync(String resourceGroupName, String vaultName, VaultCreateOrUpdateParameters parameters);
    
    /**
     * Update a key vault in the specified subscription.
     *
     * @param resourceGroupName The name of the Resource Group to which the server belongs.
     * @param vaultName Name of the vault
     * @param parameters Parameters to patch the vault
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<Vault> updateAsync(String resourceGroupName, String vaultName, VaultPatchParameters parameters, final ServiceCallback<Vault> serviceCallback);

    /**
     * Update a key vault in the specified subscription.
     *
     * @param resourceGroupName The name of the Resource Group to which the server belongs.
     * @param vaultName Name of the vault
     * @param parameters Parameters to patch the vault
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the Vault object
     */
    public Observable<Vault> updateAsync(String resourceGroupName, String vaultName, VaultPatchParameters parameters);

    /**
     * Update a key vault in the specified subscription.
     *
     * @param resourceGroupName The name of the Resource Group to which the server belongs.
     * @param vaultName Name of the vault
     * @param parameters Parameters to patch the vault
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the Vault object
     */
    public Observable<ServiceResponse<Vault>> updateWithServiceResponseAsync(String resourceGroupName, String vaultName, VaultPatchParameters parameters);
    
    /**
     * Deletes the specified Azure key vault.
     *
     * @param resourceGroupName The name of the Resource Group to which the vault belongs.
     * @param vaultName The name of the vault to delete
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<Void> deleteAsync(String resourceGroupName, String vaultName, final ServiceCallback<Void> serviceCallback);
    /**
     * Deletes the specified Azure key vault.
     *
     * @param resourceGroupName The name of the Resource Group to which the vault belongs.
     * @param vaultName The name of the vault to delete
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceResponse} object if successful.
     */
    public Observable<Void> deleteAsync(String resourceGroupName, String vaultName);

    /**
     * Deletes the specified Azure key vault.
     *
     * @param resourceGroupName The name of the Resource Group to which the vault belongs.
     * @param vaultName The name of the vault to delete
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceResponse} object if successful.
     */
    public Observable<ServiceResponse<Void>> deleteWithServiceResponseAsync(String resourceGroupName, String vaultName);
    
    /**
     * Gets the specified Azure key vault.
     *
     * @param resourceGroupName The name of the Resource Group to which the vault belongs.
     * @param vaultName The name of the vault.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<Vault> getByResourceGroupAsync(String resourceGroupName, String vaultName, final ServiceCallback<Vault> serviceCallback);

    /**
     * Gets the specified Azure key vault.
     *
     * @param resourceGroupName The name of the Resource Group to which the vault belongs.
     * @param vaultName The name of the vault.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the Vault object
     */
    public Observable<Vault> getByResourceGroupAsync(String resourceGroupName, String vaultName);
    
    /**
     * The List operation gets information about the vaults associated with the subscription and within the specified resource group.
     *
     * @param resourceGroupName The name of the Resource Group to which the vault belongs.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<Vault>> listByResourceGroupAsync(final String resourceGroupName, final ListOperationCallback<Vault> serviceCallback);

    /**
     * The List operation gets information about the vaults associated with the subscription and within the specified resource group.
     *
     * @param resourceGroupName The name of the Resource Group to which the vault belongs.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;Vault&gt; object
     */
    public Observable<ServiceResponse<Page<Vault>>> listByResourceGroupWithServiceResponseAsync(final String resourceGroupName);

    /**
     * The List operation gets information about the vaults associated with the subscription and within the specified resource group.
     *
     * @param resourceGroupName The name of the Resource Group to which the vault belongs.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;Vault&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<Vault>>> listByResourceGroupSinglePageAsync(final String resourceGroupName);
    
    /**
     * Update access policies in a key vault in the specified subscription.
     *
     * @param resourceGroupName The name of the Resource Group to which the vault belongs.
     * @param vaultName Name of the vault
     * @param operationKind Name of the operation. Possible values include: 'add', 'replace', 'remove'
     * @param properties Properties of the access policy
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<VaultAccessPolicyParameters> updateAccessPolicyAsync(String resourceGroupName, String vaultName, AccessPolicyUpdateKind operationKind, VaultAccessPolicyProperties properties, final ServiceCallback<VaultAccessPolicyParameters> serviceCallback);

    /**
     * Update access policies in a key vault in the specified subscription.
     *
     * @param resourceGroupName The name of the Resource Group to which the vault belongs.
     * @param vaultName Name of the vault
     * @param operationKind Name of the operation. Possible values include: 'add', 'replace', 'remove'
     * @param properties Properties of the access policy
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the VaultAccessPolicyParameters object
     */
    public Observable<VaultAccessPolicyParameters> updateAccessPolicyAsync(String resourceGroupName, String vaultName, AccessPolicyUpdateKind operationKind, VaultAccessPolicyProperties properties);

    /**
     * Update access policies in a key vault in the specified subscription.
     *
     * @param resourceGroupName The name of the Resource Group to which the vault belongs.
     * @param vaultName Name of the vault
     * @param operationKind Name of the operation. Possible values include: 'add', 'replace', 'remove'
     * @param properties Properties of the access policy
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the VaultAccessPolicyParameters object
     */
    public Observable<ServiceResponse<VaultAccessPolicyParameters>> updateAccessPolicyWithServiceResponseAsync(String resourceGroupName, String vaultName, AccessPolicyUpdateKind operationKind, VaultAccessPolicyProperties properties);

    /**
     * Gets the specified Azure key vault.
     *
     * @param resourceGroupName The name of the Resource Group to which the vault belongs.
     * @param vaultName The name of the vault.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the Vault object
     */
    public Observable<ServiceResponse<Vault>> getByResourceGroupWithServiceResponseAsync(String resourceGroupName, String vaultName);
    
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
     * The List operation gets information about the vaults associated with the subscription.
     *
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;Resource&gt; object
     */
    public Observable<ServiceResponse<Page<Vault>>> listWithServiceResponseAsync();
    
    /**
     * The List operation gets information about the vaults associated with the subscription.
     *
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;Resource&gt; object
     */
    public Observable<ServiceResponse<Page<Vault>>> listSinglePageAsync();
    
    /**
     * The List operation gets information about the vaults associated with the subscription.
     *
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<Vault>> listAsync(ListOperationCallback<Vault> serviceCallback);
    
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
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<Void> purgeDeletedAsync(String vaultName, String location, final ServiceCallback<Void> serviceCallback);

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
     * Permanently deletes the specified vault. aka Purges the deleted Azure key vault.
     *
     * @param vaultName The name of the soft-deleted vault.
     * @param location The location of the soft-deleted vault.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    public Observable<ServiceResponse<Void>> purgeDeletedWithServiceResponseAsync(String vaultName, String location);
    
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
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<CheckNameAvailabilityResult> checkNameAvailabilityAsync(String name, final ServiceCallback<CheckNameAvailabilityResult> serviceCallback);

    /**
     * Checks that the vault name is valid and is not already in use.
     *
     * @param name The vault name.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the CheckNameAvailabilityResult object
     */
    public Observable<CheckNameAvailabilityResult> checkNameAvailabilityAsync(String name);

    /**
     * Checks that the vault name is valid and is not already in use.
     *
     * @param name The vault name.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the CheckNameAvailabilityResult object
     */
    public Observable<ServiceResponse<CheckNameAvailabilityResult>> checkNameAvailabilityWithServiceResponseAsync(String name);

    /**
     * The List operation gets information about the vaults associated with the subscription and within the specified resource group.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PagedList&lt;Vault&gt; object if successful.
     */
    public PagedList<Vault> listByResourceGroupNext(final String nextPageLink);

    /**
     * The List operation gets information about the vaults associated with the subscription and within the specified resource group.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @param serviceFuture the ServiceFuture object tracking the Retrofit calls
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<Vault>> listByResourceGroupNextAsync(final String nextPageLink, final ServiceFuture<List<Vault>> serviceFuture, final ListOperationCallback<Vault> serviceCallback);

    /**
     * The List operation gets information about the vaults associated with the subscription and within the specified resource group.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;Vault&gt; object
     */
    public Observable<Vault> listByResourceGroupNextAsync(final String nextPageLink);

    /**
     * The List operation gets information about the vaults associated with the subscription and within the specified resource group.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;Vault&gt; object
     */
    public Observable<ServiceResponse<Page<Vault>>> listByResourceGroupNextWithServiceResponseAsync(final String nextPageLink);

    /**
     * The List operation gets information about the vaults associated with the subscription and within the specified resource group.
     *
    ServiceResponse<PageImpl<Vault>> * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;Vault&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<Vault>>> listByResourceGroupNextSinglePageAsync(final String nextPageLink);

    /**
     * Gets information about the deleted vaults in a subscription.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PagedList&lt;DeletedVault&gt; object if successful.
     */
    public PagedList<DeletedVault> listDeletedNext(final String nextPageLink);

    /**
     * Gets information about the deleted vaults in a subscription.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @param serviceFuture the ServiceFuture object tracking the Retrofit calls
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<DeletedVault>> listDeletedNextAsync(final String nextPageLink, final ServiceFuture<List<DeletedVault>> serviceFuture, final ListOperationCallback<DeletedVault> serviceCallback);

    /**
     * Gets information about the deleted vaults in a subscription.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;DeletedVault&gt; object
     */
    public Observable<DeletedVault> listDeletedNextAsync(final String nextPageLink);

    /**
     * Gets information about the deleted vaults in a subscription.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;DeletedVault&gt; object
     */
    public Observable<ServiceResponse<Page<DeletedVault>>> listDeletedNextWithServiceResponseAsync(final String nextPageLink);

    /**
     * The List operation gets information about the vaults associated with the subscription.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PagedList&lt;Resource&gt; object if successful.
     */
    public PagedList<Vault> listNext(final String nextPageLink);

    /**
     * The List operation gets information about the vaults associated with the subscription.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @param serviceFuture the ServiceFuture object tracking the Retrofit calls
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<Vault>> listNextAsync(final String nextPageLink, final ServiceFuture<List<Vault>> serviceFuture, final ListOperationCallback<Vault> serviceCallback);

    /**
     * The List operation gets information about the vaults associated with the subscription.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;Resource&gt; object
     */
    public Observable<Vault> listNextAsync(final String nextPageLink);

    /**
     * The List operation gets information about the vaults associated with the subscription.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;Resource&gt; object
     */
    public Observable<ServiceResponse<Page<Vault>>> listNextWithServiceResponseAsync(final String nextPageLink);

    /**
     * The List operation gets information about the vaults associated with the subscription.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;Resource&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<Vault>>> listNextSinglePageAsync(final String nextPageLink);
}
