/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql;


/**
 * The implementation information for a recommended action.
 */
public class ImplementationInfo {
    /**
     * The method property.
     */
    private String method;

    /**
     * The script property.
     */
    private String script;

    /**
     * Get the method value.
     *
     * @return the method value
     */
    public String method() {
        return this.method;
    }

    /**
     * Set the method value.
     *
     * @param method the method value to set
     * @return the ImplementationInfo object itself.
     */
    public ImplementationInfo withMethod(String method) {
        this.method = method;
        return this;
    }

    /**
     * Get the script value.
     *
     * @return the script value
     */
    public String script() {
        return this.script;
    }

    /**
     * Set the script value.
     *
     * @param script the script value to set
     * @return the ImplementationInfo object itself.
     */
    public ImplementationInfo withScript(String script) {
        this.script = script;
        return this;
    }

}
