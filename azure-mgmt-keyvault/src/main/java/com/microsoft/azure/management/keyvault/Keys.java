/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingByNameAsync;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingById;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import rx.Observable;

/**
 * Entry point for Key Vault keys API.
 */
@Beta(SinceVersion.V1_6_0)
@Fluent(ContainerName = "/Microsoft.Azure.Management.Fluent.KeyVault")
public interface Keys extends
        SupportsCreating<Key.DefinitionStages.Blank>,
        SupportsDeletingById,
        SupportsGettingById<Key>,
        SupportsGettingByNameAsync<Key>,
        SupportsListing<Key> {
    /**
     * Gets a Key Vault key.
     * @param name the name of the key
     * @param version the version of the key
     * @return the key
     */
    Key getByNameAndVersion(String name, String version);

    /**
     * Gets a Key Vault key.
     * @param name the name of the key
     * @param version the version of the key
     * @return the key
     */
    Observable<Key> getByNameAndVersionAsync(String name, String version);

    /**
     * Restores a backup key into a Key Vault key.
     * @param backup the backup key
     * @return the key restored from the backup
     */
    Key restore(byte[] backup);

    /**
     * Restores a backup key into a Key Vault key.
     * @param backup the backup key
     * @return the key restored from the backup
     */
    Observable<Key> restoreAsync(byte[] backup);
}
