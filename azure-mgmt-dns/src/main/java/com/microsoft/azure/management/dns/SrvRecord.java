/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.dns;


/**
 * An SRV record.
 */
public class SrvRecord {
    /**
     * Gets or sets the priority metric for this record.
     */
    private Integer priority;

    /**
     * Gets or sets the weight metric for this this record.
     */
    private Integer weight;

    /**
     * Gets or sets the port of the service for this record.
     */
    private Integer port;

    /**
     * Gets or sets the domain name of the target for this record, without a
     * terminating dot.
     */
    private String target;

    /**
     * Get the priority value.
     *
     * @return the priority value
     */
    public Integer priority() {
        return this.priority;
    }

    /**
     * Set the priority value.
     *
     * @param priority the priority value to set
     * @return the SrvRecord object itself.
     */
    public SrvRecord withPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Get the weight value.
     *
     * @return the weight value
     */
    public Integer weight() {
        return this.weight;
    }

    /**
     * Set the weight value.
     *
     * @param weight the weight value to set
     * @return the SrvRecord object itself.
     */
    public SrvRecord withWeight(Integer weight) {
        this.weight = weight;
        return this;
    }

    /**
     * Get the port value.
     *
     * @return the port value
     */
    public Integer port() {
        return this.port;
    }

    /**
     * Set the port value.
     *
     * @param port the port value to set
     * @return the SrvRecord object itself.
     */
    public SrvRecord withPort(Integer port) {
        this.port = port;
        return this;
    }

    /**
     * Get the target value.
     *
     * @return the target value
     */
    public String target() {
        return this.target;
    }

    /**
     * Set the target value.
     *
     * @param target the target value to set
     * @return the SrvRecord object itself.
     */
    public SrvRecord withTarget(String target) {
        this.target = target;
        return this;
    }

}
