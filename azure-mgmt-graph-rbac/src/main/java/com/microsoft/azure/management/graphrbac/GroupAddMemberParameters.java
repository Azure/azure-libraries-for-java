/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.graphrbac;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request parameters for adding a member to a group.
 */
public class GroupAddMemberParameters {
    /**
     * A member object URL, such as
     * "https://graph.windows.net/0b1f9851-1bf0-433f-aec3-cb9272f093dc/directoryObjects/f260bbc4-c254-447b-94cf-293b5ec434dd",
     * where "0b1f9851-1bf0-433f-aec3-cb9272f093dc" is the tenantId and
     * "f260bbc4-c254-447b-94cf-293b5ec434dd" is the objectId of the member
     * (user, application, servicePrincipal, group) to be added.
     */
    @JsonProperty(required = true)
    private String url;

    /**
     * Get the url value.
     *
     * @return the url value
     */
    public String url() {
        return this.url;
    }

    /**
     * Set the url value.
     *
     * @param url the url value to set
     * @return the GroupAddMemberParameters object itself.
     */
    public GroupAddMemberParameters withUrl(String url) {
        this.url = url;
        return this;
    }

}
