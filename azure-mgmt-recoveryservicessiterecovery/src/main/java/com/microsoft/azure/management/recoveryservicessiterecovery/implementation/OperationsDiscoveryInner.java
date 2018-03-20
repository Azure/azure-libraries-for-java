/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.recoveryservicessiterecovery.implementation;

import com.microsoft.azure.management.recoveryservicessiterecovery.Display;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Operations discovery class.
 */
public class OperationsDiscoveryInner {
    /**
     * Name of the API. The name of the operation being performed on this
     * particular object. It should match the action name that appears in RBAC
     * / the event service. Examples of operations include: *
     * Microsoft.Compute/virtualMachine/capture/action *
     * Microsoft.Compute/virtualMachine/restart/action *
     * Microsoft.Compute/virtualMachine/write *
     * Microsoft.Compute/virtualMachine/read *
     * Microsoft.Compute/virtualMachine/delete Each action should include, in
     * order: (1) Resource Provider Namespace (2) Type hierarchy for which the
     * action applies (e.g. server/databases for a SQL Azure database) (3)
     * Read, Write, Action or Delete indicating which type applies. If it is a
     * PUT/PATCH on a collection or named value, Write should be used. If it is
     * a GET, Read should be used. If it is a DELETE, Delete should be used. If
     * it is a POST, Action should be used. As a note: all resource providers
     * would need to include the "{Resource Provider
     * Namespace}/register/action" operation in their response. This API is
     * used to register for their service, and should include details about the
     * operation (e.g. a localized name for the resource provider + any special
     * considerations like PII release).
     */
    @JsonProperty(value = "name")
    private String name;

    /**
     * Object type.
     */
    @JsonProperty(value = "display")
    private Display display;

    /**
     * Origin. The intended executor of the operation; governs the display of
     * the operation in the RBAC UX and the audit logs UX. Default value is
     * "user,system".
     */
    @JsonProperty(value = "origin")
    private String origin;

    /**
     * Properties. Reserved for future use.
     */
    @JsonProperty(value = "properties")
    private Object properties;

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name value.
     *
     * @param name the name value to set
     * @return the OperationsDiscoveryInner object itself.
     */
    public OperationsDiscoveryInner withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the display value.
     *
     * @return the display value
     */
    public Display display() {
        return this.display;
    }

    /**
     * Set the display value.
     *
     * @param display the display value to set
     * @return the OperationsDiscoveryInner object itself.
     */
    public OperationsDiscoveryInner withDisplay(Display display) {
        this.display = display;
        return this;
    }

    /**
     * Get the origin value.
     *
     * @return the origin value
     */
    public String origin() {
        return this.origin;
    }

    /**
     * Set the origin value.
     *
     * @param origin the origin value to set
     * @return the OperationsDiscoveryInner object itself.
     */
    public OperationsDiscoveryInner withOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    /**
     * Get the properties value.
     *
     * @return the properties value
     */
    public Object properties() {
        return this.properties;
    }

    /**
     * Set the properties value.
     *
     * @param properties the properties value to set
     * @return the OperationsDiscoveryInner object itself.
     */
    public OperationsDiscoveryInner withProperties(Object properties) {
        this.properties = properties;
        return this;
    }

}
