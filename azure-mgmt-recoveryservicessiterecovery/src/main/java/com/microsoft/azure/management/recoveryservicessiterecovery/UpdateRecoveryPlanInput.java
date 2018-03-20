/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.recoveryservicessiterecovery;

import com.microsoft.azure.management.recoveryservicessiterecovery.implementation.UpdateRecoveryPlanInputPropertiesInner;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Update recovery plan input class.
 */
public class UpdateRecoveryPlanInput {
    /**
     * Recovery plan update properties.
     */
    @JsonProperty(value = "properties")
    private UpdateRecoveryPlanInputPropertiesInner properties;

    /**
     * Get the properties value.
     *
     * @return the properties value
     */
    public UpdateRecoveryPlanInputPropertiesInner properties() {
        return this.properties;
    }

    /**
     * Set the properties value.
     *
     * @param properties the properties value to set
     * @return the UpdateRecoveryPlanInput object itself.
     */
    public UpdateRecoveryPlanInput withProperties(UpdateRecoveryPlanInputPropertiesInner properties) {
        this.properties = properties;
        return this;
    }

}
