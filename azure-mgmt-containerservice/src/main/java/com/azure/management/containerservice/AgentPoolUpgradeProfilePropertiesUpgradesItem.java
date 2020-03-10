/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.azure.management.containerservice;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The AgentPoolUpgradeProfilePropertiesUpgradesItem model.
 */
public class AgentPoolUpgradeProfilePropertiesUpgradesItem {
    /**
     * Kubernetes version (major, minor, patch).
     */
    @JsonProperty(value = "kubernetesVersion")
    private String kubernetesVersion;

    /**
     * Whether Kubernetes version is currently in preview.
     */
    @JsonProperty(value = "isPreview")
    private Boolean isPreview;

    /**
     * Get kubernetes version (major, minor, patch).
     *
     * @return the kubernetesVersion value
     */
    public String kubernetesVersion() {
        return this.kubernetesVersion;
    }

    /**
     * Set kubernetes version (major, minor, patch).
     *
     * @param kubernetesVersion the kubernetesVersion value to set
     * @return the AgentPoolUpgradeProfilePropertiesUpgradesItem object itself.
     */
    public AgentPoolUpgradeProfilePropertiesUpgradesItem withKubernetesVersion(String kubernetesVersion) {
        this.kubernetesVersion = kubernetesVersion;
        return this;
    }

    /**
     * Get whether Kubernetes version is currently in preview.
     *
     * @return the isPreview value
     */
    public Boolean isPreview() {
        return this.isPreview;
    }

    /**
     * Set whether Kubernetes version is currently in preview.
     *
     * @param isPreview the isPreview value to set
     * @return the AgentPoolUpgradeProfilePropertiesUpgradesItem object itself.
     */
    public AgentPoolUpgradeProfilePropertiesUpgradesItem withIsPreview(Boolean isPreview) {
        this.isPreview = isPreview;
        return this;
    }

}
