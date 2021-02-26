/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The source image from which the Image Version is going to be created.
 */
public class UserArtifactSource {
    /**
     * Required. The mediaLink of the artifact, must be a readable storage page
     * blob.
     */
    @JsonProperty(value = "mediaLink", required = true)
    private String mediaLink;

    /**
     * Optional. The defaultConfigurationLink of the artifact, must be a
     * readable storage page blob.
     */
    @JsonProperty(value = "defaultConfigurationLink")
    private String defaultConfigurationLink;

    /**
     * Get required. The mediaLink of the artifact, must be a readable storage page blob.
     *
     * @return the mediaLink value
     */
    public String mediaLink() {
        return this.mediaLink;
    }

    /**
     * Set required. The mediaLink of the artifact, must be a readable storage page blob.
     *
     * @param mediaLink the mediaLink value to set
     * @return the UserArtifactSource object itself.
     */
    public UserArtifactSource withMediaLink(String mediaLink) {
        this.mediaLink = mediaLink;
        return this;
    }

    /**
     * Get optional. The defaultConfigurationLink of the artifact, must be a readable storage page blob.
     *
     * @return the defaultConfigurationLink value
     */
    public String defaultConfigurationLink() {
        return this.defaultConfigurationLink;
    }

    /**
     * Set optional. The defaultConfigurationLink of the artifact, must be a readable storage page blob.
     *
     * @param defaultConfigurationLink the defaultConfigurationLink value to set
     * @return the UserArtifactSource object itself.
     */
    public UserArtifactSource withDefaultConfigurationLink(String defaultConfigurationLink) {
        this.defaultConfigurationLink = defaultConfigurationLink;
        return this;
    }

}
