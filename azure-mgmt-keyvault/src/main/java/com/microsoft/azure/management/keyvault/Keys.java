/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingById;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import rx.Observable;

/**
 * Entry point for Key Vault keys API.
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.Fluent.KeyVault")
public interface Keys extends
        SupportsCreating<Key.DefinitionStages.Blank>,
        SupportsDeletingById,
        SupportsGettingById<Key>,
        SupportsListing<Key> {
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
