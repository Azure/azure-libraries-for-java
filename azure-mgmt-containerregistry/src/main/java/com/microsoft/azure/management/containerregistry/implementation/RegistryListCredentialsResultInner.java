/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.containerregistry.implementation;

import java.util.List;
import com.microsoft.azure.management.containerregistry.RegistryPassword;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The response from the ListCredentials operation.
 */
public class RegistryListCredentialsResultInner {
    /**
     * The username for a container registry.
     */
    @JsonProperty(value = "username")
    private String username;

    /**
     * The list of passwords for a container registry.
     */
    @JsonProperty(value = "passwords")
    private List<RegistryPassword> passwords;

    /**
     * Get the username for a container registry.
     *
     * @return the username value
     */
    public String username() {
        return this.username;
    }

    /**
     * Set the username for a container registry.
     *
     * @param username the username value to set
     * @return the RegistryListCredentialsResultInner object itself.
     */
    public RegistryListCredentialsResultInner withUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Get the list of passwords for a container registry.
     *
     * @return the passwords value
     */
    public List<RegistryPassword> passwords() {
        return this.passwords;
    }

    /**
     * Set the list of passwords for a container registry.
     *
     * @param passwords the passwords value to set
     * @return the RegistryListCredentialsResultInner object itself.
     */
    public RegistryListCredentialsResultInner withPasswords(List<RegistryPassword> passwords) {
        this.passwords = passwords;
        return this;
    }

}
