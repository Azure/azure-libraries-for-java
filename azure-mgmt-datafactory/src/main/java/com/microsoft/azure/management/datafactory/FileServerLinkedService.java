/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.datafactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.management.datafactory.implementation.LinkedServiceInner;

/**
 * File system linked service.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("FileServer")
@JsonFlatten
public class FileServerLinkedService extends LinkedServiceInner {
    /**
     * Host name of the server. Type: string (or Expression with resultType
     * string).
     */
    @JsonProperty(value = "typeProperties.host", required = true)
    private Object host;

    /**
     * User ID to logon the server. Type: string (or Expression with resultType
     * string).
     */
    @JsonProperty(value = "typeProperties.userId")
    private Object userId;

    /**
     * Password to logon the server.
     */
    @JsonProperty(value = "typeProperties.password")
    private SecretBase password;

    /**
     * The encrypted credential used for authentication. Credentials are
     * encrypted using the integration runtime credential manager. Type: string
     * (or Expression with resultType string).
     */
    @JsonProperty(value = "typeProperties.encryptedCredential")
    private Object encryptedCredential;

    /**
     * Get the host value.
     *
     * @return the host value
     */
    public Object host() {
        return this.host;
    }

    /**
     * Set the host value.
     *
     * @param host the host value to set
     * @return the FileServerLinkedService object itself.
     */
    public FileServerLinkedService withHost(Object host) {
        this.host = host;
        return this;
    }

    /**
     * Get the userId value.
     *
     * @return the userId value
     */
    public Object userId() {
        return this.userId;
    }

    /**
     * Set the userId value.
     *
     * @param userId the userId value to set
     * @return the FileServerLinkedService object itself.
     */
    public FileServerLinkedService withUserId(Object userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Get the password value.
     *
     * @return the password value
     */
    public SecretBase password() {
        return this.password;
    }

    /**
     * Set the password value.
     *
     * @param password the password value to set
     * @return the FileServerLinkedService object itself.
     */
    public FileServerLinkedService withPassword(SecretBase password) {
        this.password = password;
        return this;
    }

    /**
     * Get the encryptedCredential value.
     *
     * @return the encryptedCredential value
     */
    public Object encryptedCredential() {
        return this.encryptedCredential;
    }

    /**
     * Set the encryptedCredential value.
     *
     * @param encryptedCredential the encryptedCredential value to set
     * @return the FileServerLinkedService object itself.
     */
    public FileServerLinkedService withEncryptedCredential(Object encryptedCredential) {
        this.encryptedCredential = encryptedCredential;
        return this;
    }

}
