/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.graphrbac;

import java.util.List;
import com.microsoft.azure.management.graphrbac.implementation.PasswordCredentialInner;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request parameters for a PasswordCredentials update operation.
 */
public class PasswordCredentialsUpdateParameters {
    /**
     * A collection of PasswordCredentials.
     */
    @JsonProperty(value = "value", required = true)
    private List<PasswordCredentialInner> value;

    /**
     * Get the value value.
     *
     * @return the value value
     */
    public List<PasswordCredentialInner> value() {
        return this.value;
    }

    /**
     * Set the value value.
     *
     * @param value the value value to set
     * @return the PasswordCredentialsUpdateParameters object itself.
     */
    public PasswordCredentialsUpdateParameters withValue(List<PasswordCredentialInner> value) {
        this.value = value;
        return this;
    }

}
