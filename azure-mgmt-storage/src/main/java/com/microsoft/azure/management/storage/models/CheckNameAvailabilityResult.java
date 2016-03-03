/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator 0.15.0.0
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package com.microsoft.azure.management.storage.models;


/**
 * The CheckNameAvailability operation response.
 */
public class CheckNameAvailabilityResult {
    /**
     * Gets a boolean value that indicates whether the name is available for
     * you to use. If true, the name is available. If false, the name has
     * already been taken or invalid and cannot be used.
     */
    private Boolean nameAvailable;

    /**
     * Gets the reason that a storage account name could not be used. The
     * Reason element is only returned if NameAvailable is false. Possible
     * values include: 'AccountNameInvalid', 'AlreadyExists'.
     */
    private Reason reason;

    /**
     * Gets an error message explaining the Reason value in more detail.
     */
    private String message;

    /**
     * Get the nameAvailable value.
     *
     * @return the nameAvailable value
     */
    public Boolean getNameAvailable() {
        return this.nameAvailable;
    }

    /**
     * Set the nameAvailable value.
     *
     * @param nameAvailable the nameAvailable value to set
     */
    public void setNameAvailable(Boolean nameAvailable) {
        this.nameAvailable = nameAvailable;
    }

    /**
     * Get the reason value.
     *
     * @return the reason value
     */
    public Reason getReason() {
        return this.reason;
    }

    /**
     * Set the reason value.
     *
     * @param reason the reason value to set
     */
    public void setReason(Reason reason) {
        this.reason = reason;
    }

    /**
     * Get the message value.
     *
     * @return the message value
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Set the message value.
     *
     * @param message the message value to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
