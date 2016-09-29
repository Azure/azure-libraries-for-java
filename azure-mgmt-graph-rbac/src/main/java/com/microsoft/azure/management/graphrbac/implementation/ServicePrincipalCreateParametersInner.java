/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.graphrbac.implementation;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request parameters for create a new service principal.
 */
public class ServicePrincipalCreateParametersInner {
    /**
     * application Id.
     */
    @JsonProperty(required = true)
    private String appId;

    /**
     * Specifies if the account is enabled.
     */
    @JsonProperty(required = true)
    private boolean accountEnabled;

    /**
     * the list of KeyCredential objects.
     */
    private List<KeyCredentialInner> keyCredentials;

    /**
     * the list of PasswordCredential objects.
     */
    private List<PasswordCredentialInner> passwordCredentials;

    /**
     * Get the appId value.
     *
     * @return the appId value
     */
    public String appId() {
        return this.appId;
    }

    /**
     * Set the appId value.
     *
     * @param appId the appId value to set
     * @return the ServicePrincipalCreateParametersInner object itself.
     */
    public ServicePrincipalCreateParametersInner withAppId(String appId) {
        this.appId = appId;
        return this;
    }

    /**
     * Get the accountEnabled value.
     *
     * @return the accountEnabled value
     */
    public boolean accountEnabled() {
        return this.accountEnabled;
    }

    /**
     * Set the accountEnabled value.
     *
     * @param accountEnabled the accountEnabled value to set
     * @return the ServicePrincipalCreateParametersInner object itself.
     */
    public ServicePrincipalCreateParametersInner withAccountEnabled(boolean accountEnabled) {
        this.accountEnabled = accountEnabled;
        return this;
    }

    /**
     * Get the keyCredentials value.
     *
     * @return the keyCredentials value
     */
    public List<KeyCredentialInner> keyCredentials() {
        return this.keyCredentials;
    }

    /**
     * Set the keyCredentials value.
     *
     * @param keyCredentials the keyCredentials value to set
     * @return the ServicePrincipalCreateParametersInner object itself.
     */
    public ServicePrincipalCreateParametersInner withKeyCredentials(List<KeyCredentialInner> keyCredentials) {
        this.keyCredentials = keyCredentials;
        return this;
    }

    /**
     * Get the passwordCredentials value.
     *
     * @return the passwordCredentials value
     */
    public List<PasswordCredentialInner> passwordCredentials() {
        return this.passwordCredentials;
    }

    /**
     * Set the passwordCredentials value.
     *
     * @param passwordCredentials the passwordCredentials value to set
     * @return the ServicePrincipalCreateParametersInner object itself.
     */
    public ServicePrincipalCreateParametersInner withPasswordCredentials(List<PasswordCredentialInner> passwordCredentials) {
        this.passwordCredentials = passwordCredentials;
        return this;
    }

}
