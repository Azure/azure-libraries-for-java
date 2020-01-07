/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 */

package com.azure.management.keyvault;

import com.azure.management.keyvault.implementation.CheckNameAvailabilityResultInner;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * The CheckNameAvailability operation response wrapper.
 *
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.Fluent.KeyVault")
@Beta(SinceVersion.V1_11_0)
public interface CheckNameAvailabilityResult extends HasInner<CheckNameAvailabilityResultInner> {

    /**
     * Get the nameAvailable value.
     *
     * @return the nameAvailable value
     */
    Boolean nameAvailable();

    /**
     * Get the reason value.
     *
     * @return the reason value
     */
    Reason reason();

    /**
     * Get the message value.
     *
     * @return the message value
     */
    String message();

}
