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
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingById;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;

/**
 * Entry point for Key Vault secrets API.
 */
@Beta(SinceVersion.V1_6_0)
@Fluent(ContainerName = "/Microsoft.Azure.Management.Fluent.KeyVault")
public interface Secrets extends
        SupportsCreating<Secret.DefinitionStages.Blank>,
        SupportsDeletingById,
        SupportsGettingById<Secret>,
        SupportsListing<Secret> {
}
