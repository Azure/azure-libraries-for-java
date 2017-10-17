/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.containerregistry;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The registry node that generated the event. Put differently, while the actor
 * initiates the event, the source generates it.
 */
public class Source {
    /**
     * The IP or hostname and the port of the registry node that generated the
     * event. Generally, this will be resolved by os.Hostname() along with the
     * running port.
     */
    @JsonProperty(value = "addr")
    private String addr;

    /**
     * The running instance of an application. Changes after each restart.
     */
    @JsonProperty(value = "instanceID")
    private String instanceID;

    /**
     * Get the addr value.
     *
     * @return the addr value
     */
    public String addr() {
        return this.addr;
    }

    /**
     * Set the addr value.
     *
     * @param addr the addr value to set
     * @return the Source object itself.
     */
    public Source withAddr(String addr) {
        this.addr = addr;
        return this;
    }

    /**
     * Get the instanceID value.
     *
     * @return the instanceID value
     */
    public String instanceID() {
        return this.instanceID;
    }

    /**
     * Set the instanceID value.
     *
     * @param instanceID the instanceID value to set
     * @return the Source object itself.
     */
    public Source withInstanceID(String instanceID) {
        this.instanceID = instanceID;
        return this;
    }

}
