/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.containerservice;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Profile for enabling a user to access a managed cluster.
 */
public class AccessProfile {
    /**
     * Base64-encoded Kubernetes configuration file.
     */
    @JsonProperty(value = "kubeConfig")
    private String kubeConfig;

    /**
     * Get the kubeConfig value.
     *
     * @return the kubeConfig value
     */
    public String kubeConfig() {
        return this.kubeConfig;
    }

    /**
     * Set the kubeConfig value.
     *
     * @param kubeConfig the kubeConfig value to set
     * @return the AccessProfile object itself.
     */
    public AccessProfile withKubeConfig(String kubeConfig) {
        this.kubeConfig = kubeConfig;
        return this;
    }

}
