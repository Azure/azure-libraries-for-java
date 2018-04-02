/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.keyvault;

import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.keyvault.implementation.SecretsInner;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * Type representing Secrets.
 */
public interface Secrets extends SupportsCreating<Secret.DefinitionStages.Blank>, HasInner<SecretsInner> {

    /**
    * The List operation gets information about the secrets in a vault.  NOTE: This API is intended for internal use in ARM deployments. Users should use the data-plane REST service for interaction with vault secrets.
    *
    * @param resourceGroupName The name of the Resource Group to which the vault belongs.
    * @param vaultName The name of the vault.
    * @throws IllegalArgumentException thrown if parameters fail the validation
    * @return the observable for the request
    */
    Observable<Secret> listByVaultAsync(final String resourceGroupName, final String vaultName);

    /**
    * Gets the specified secret.  NOTE: This API is intended for internal use in ARM deployments. Users should use the data-plane REST service for interaction with vault secrets.
    *
    * @param resourceGroupName The name of the Resource Group to which the vault belongs.
    * @param vaultName The name of the vault.
    * @param secretName The name of the secret.
    * @throws IllegalArgumentException thrown if parameters fail the validation
    * @return the observable for the request
    */
    Observable<Secret> getByVaultAsync(String resourceGroupName, String vaultName, String secretName);
}
