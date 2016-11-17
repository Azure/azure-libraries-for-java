/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.datalake.analytics.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Lake Analytics catalog credential creation parameters.
 */
public class DataLakeAnalyticsCatalogCredentialCreateParameters {
    /**
     * the password for the credential and user with access to the data source.
     */
    @JsonProperty(required = true)
    private String password;

    /**
     * the URI identifier for the data source this credential can connect to
     * in the format &lt;hostname&gt;:&lt;port&gt;.
     */
    @JsonProperty(required = true)
    private String uri;

    /**
     * the object identifier for the user associated with this credential with
     * access to the data source.
     */
    @JsonProperty(required = true)
    private String userId;

    /**
     * Get the password value.
     *
     * @return the password value
     */
    public String password() {
        return this.password;
    }

    /**
     * Set the password value.
     *
     * @param password the password value to set
     * @return the DataLakeAnalyticsCatalogCredentialCreateParameters object itself.
     */
    public DataLakeAnalyticsCatalogCredentialCreateParameters withPassword(String password) {
        this.password = password;
        return this;
    }

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
     * @return the DataLakeAnalyticsCatalogCredentialCreateParameters object itself.
     */
    public DataLakeAnalyticsCatalogCredentialCreateParameters withUri(String uri) {
        this.uri = uri;
        return this;
    }

    /**
     * Get the userId value.
     *
     * @return the userId value
     */
    public String userId() {
        return this.userId;
    }

    /**
     * Set the userId value.
     *
     * @param userId the userId value to set
     * @return the DataLakeAnalyticsCatalogCredentialCreateParameters object itself.
     */
    public DataLakeAnalyticsCatalogCredentialCreateParameters withUserId(String userId) {
        this.userId = userId;
        return this;
    }

}
