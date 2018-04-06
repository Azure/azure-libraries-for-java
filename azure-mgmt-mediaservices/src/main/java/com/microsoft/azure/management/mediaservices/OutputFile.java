/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.mediaservices;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represent an output file.
 */
public class OutputFile {
    /**
     * Gets the track labels.
     */
    @JsonProperty(value = "trackLabels")
    private List<String> trackLabels;

    /**
     * Get the trackLabels value.
     *
     * @return the trackLabels value
     */
    public List<String> trackLabels() {
        return this.trackLabels;
    }

    /**
     * Set the trackLabels value.
     *
     * @param trackLabels the trackLabels value to set
     * @return the OutputFile object itself.
     */
    public OutputFile withTrackLabels(List<String> trackLabels) {
        this.trackLabels = trackLabels;
        return this;
    }

}
