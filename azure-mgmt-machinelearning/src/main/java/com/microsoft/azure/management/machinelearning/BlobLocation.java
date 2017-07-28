/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.machinelearning;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Describes the access location for a blob.
 */
public class BlobLocation {
    /**
     * The URI from which the blob is accessible from. For example, aml://abc
     * for system assets or https://xyz for user assets or payload.
     */
    @JsonProperty(value = "uri", required = true)
    private String uri;

    /**
     * Access credentials for the blob, if applicable (e.g. blob specified by
     * storage account connection string + blob URI).
     */
    @JsonProperty(value = "credentials")
    private String credentials;

    /**
     * Get the uri value.
     *
     * @return the uri value
     */
    public String uri() {
        return this.uri;
    }

    /**
     * Set the uri value.
     *
     * @param uri the uri value to set
     * @return the BlobLocation object itself.
     */
    public BlobLocation withUri(String uri) {
        this.uri = uri;
        return this;
    }

    /**
     * Get the credentials value.
     *
     * @return the credentials value
     */
    public String credentials() {
        return this.credentials;
    }

    /**
     * Set the credentials value.
     *
     * @param credentials the credentials value to set
     * @return the BlobLocation object itself.
     */
    public BlobLocation withCredentials(String credentials) {
        this.credentials = credentials;
        return this;
    }

}
