/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.graphrbac.implementation;

import com.microsoft.azure.management.graphrbac.PasswordProfile;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request parameters for updating an existing work or school account user.
 */
public class UserUpdateParametersInner {
    /**
     * Whether the account is enabled.
     */
    @JsonProperty(value = "accountEnabled")
    private Boolean accountEnabled;

    /**
     * The display name of the user.
     */
    @JsonProperty(value = "displayName")
    private String displayName;

    /**
     * The password profile of the user.
     */
    @JsonProperty(value = "passwordProfile")
    private PasswordProfile passwordProfile;

    /**
     * The mail alias for the user.
     */
    @JsonProperty(value = "mailNickname")
    private String mailNickname;

    /**
     * A two letter country code (ISO standard 3166). Required for users that
     * will be assigned licenses due to legal requirement to check for
     * availability of services in countries. Examples include: "US", "JP", and
     * "GB".
     */
    @JsonProperty(value = "usageLocation")
    private String usageLocation;

    /**
     * Get the accountEnabled value.
     *
     * @return the accountEnabled value
     */
    public Boolean accountEnabled() {
        return this.accountEnabled;
    }

    /**
     * Set the accountEnabled value.
     *
     * @param accountEnabled the accountEnabled value to set
     * @return the UserUpdateParametersInner object itself.
     */
    public UserUpdateParametersInner withAccountEnabled(Boolean accountEnabled) {
        this.accountEnabled = accountEnabled;
        return this;
    }

    /**
     * Get the displayName value.
     *
     * @return the displayName value
     */
    public String displayName() {
        return this.displayName;
    }

    /**
     * Set the displayName value.
     *
     * @param displayName the displayName value to set
     * @return the UserUpdateParametersInner object itself.
     */
    public UserUpdateParametersInner withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Get the passwordProfile value.
     *
     * @return the passwordProfile value
     */
    public PasswordProfile passwordProfile() {
        return this.passwordProfile;
    }

    /**
     * Set the passwordProfile value.
     *
     * @param passwordProfile the passwordProfile value to set
     * @return the UserUpdateParametersInner object itself.
     */
    public UserUpdateParametersInner withPasswordProfile(PasswordProfile passwordProfile) {
        this.passwordProfile = passwordProfile;
        return this;
    }

    /**
     * Get the mailNickname value.
     *
     * @return the mailNickname value
     */
    public String mailNickname() {
        return this.mailNickname;
    }

    /**
     * Set the mailNickname value.
     *
     * @param mailNickname the mailNickname value to set
     * @return the UserUpdateParametersInner object itself.
     */
    public UserUpdateParametersInner withMailNickname(String mailNickname) {
        this.mailNickname = mailNickname;
        return this;
    }

    /**
     * Get the usageLocation value.
     *
     * @return the usageLocation value
     */
    public String usageLocation() {
        return this.usageLocation;
    }

    /**
     * Set the usageLocation value.
     *
     * @param usageLocation the usageLocation value to set
     * @return the UserUpdateParametersInner object itself.
     */
    public UserUpdateParametersInner withUsageLocation(String usageLocation) {
        this.usageLocation = usageLocation;
        return this;
    }

}
