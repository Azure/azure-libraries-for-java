/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator 0.15.0.0
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package com.microsoft.azure.management.website.models;


/**
 * Publishing options for requested profile.
 */
public class CsmPublishingProfileOptions {
    /**
     * Name of the format. Valid values are:
     * FileZilla3
     * WebDeploy -- default
     * Ftp.
     */
    private String format;

    /**
     * Get the format value.
     *
     * @return the format value
     */
    public String getFormat() {
        return this.format;
    }

    /**
     * Set the format value.
     *
     * @param format the format value to set
     */
    public void setFormat(String format) {
        this.format = format;
    }

}
