/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.dns;


/**
 * An MX record.
 */
public class MxRecord {
    /**
     * The preference value for this MX record.
     */
    private Integer preference;

    /**
     * The domain name of the mail host for this MX record.
     */
    private String exchange;

    /**
     * Get the preference value.
     *
     * @return the preference value
     */
    public Integer preference() {
        return this.preference;
    }

    /**
     * Set the preference value.
     *
     * @param preference the preference value to set
     * @return the MxRecord object itself.
     */
    public MxRecord withPreference(Integer preference) {
        this.preference = preference;
        return this;
    }

    /**
     * Get the exchange value.
     *
     * @return the exchange value
     */
    public String exchange() {
        return this.exchange;
    }

    /**
     * Set the exchange value.
     *
     * @param exchange the exchange value to set
     * @return the MxRecord object itself.
     */
    public MxRecord withExchange(String exchange) {
        this.exchange = exchange;
        return this;
    }

}
