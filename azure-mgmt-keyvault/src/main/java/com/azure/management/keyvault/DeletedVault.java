/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 */

package com.azure.management.keyvault;

import java.util.Map;

import com.azure.management.keyvault.implementation.DeletedVaultInner;
import org.joda.time.DateTime;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * An immutable client-side representation of an Azure Key Vault.
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.Fluent.KeyVault")
@Beta(SinceVersion.V1_11_0)
public interface DeletedVault extends HasInner<DeletedVaultInner>, HasName, HasId {

    /**
     * Get the location value.
     *
     * @return the location value
     */
    String location();

    /**
     * Get the deletionDate value.
     *
     * @return the deletionDate value
     */
    DateTime deletionDate();

    /**
     * Get the scheduledPurgeDate value.
     *
     * @return the scheduledPurgeDate value
     */
    DateTime scheduledPurgeDate();

    /**
     * Get the tags value.
     *
     * @return the tags value
     */
    Map<String, String> tags();
}
