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
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Preset for Media Encoder Standard.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@odata.type")
@JsonTypeName("#Microsoft.Media.StandardEncoderPreset")
public class StandardEncoderPreset extends Preset {
    /**
     * Gets the Stream definitions for this source.
     */
    @JsonProperty(value = "streams")
    private List<StreamSelection> streams;

    /**
     * Gets or sets the filters for the preset.
     */
    @JsonProperty(value = "filters")
    private Filters filters;

    /**
     * Gets the list of codecs to use.
     */
    @JsonProperty(value = "codecs")
    private List<Codec> codecs;

    /**
     * Gets the list of outputs.
     */
    @JsonProperty(value = "formats")
    private List<Format> formats;

    /**
     * Get the streams value.
     *
     * @return the streams value
     */
    public List<StreamSelection> streams() {
        return this.streams;
    }

    /**
     * Set the streams value.
     *
     * @param streams the streams value to set
     * @return the StandardEncoderPreset object itself.
     */
    public StandardEncoderPreset withStreams(List<StreamSelection> streams) {
        this.streams = streams;
        return this;
    }

    /**
     * Get the filters value.
     *
     * @return the filters value
     */
    public Filters filters() {
        return this.filters;
    }

    /**
     * Set the filters value.
     *
     * @param filters the filters value to set
     * @return the StandardEncoderPreset object itself.
     */
    public StandardEncoderPreset withFilters(Filters filters) {
        this.filters = filters;
        return this;
    }

    /**
     * Get the codecs value.
     *
     * @return the codecs value
     */
    public List<Codec> codecs() {
        return this.codecs;
    }

    /**
     * Set the codecs value.
     *
     * @param codecs the codecs value to set
     * @return the StandardEncoderPreset object itself.
     */
    public StandardEncoderPreset withCodecs(List<Codec> codecs) {
        this.codecs = codecs;
        return this;
    }

    /**
     * Get the formats value.
     *
     * @return the formats value
     */
    public List<Format> formats() {
        return this.formats;
    }

    /**
     * Set the formats value.
     *
     * @param formats the formats value to set
     * @return the StandardEncoderPreset object itself.
     */
    public StandardEncoderPreset withFormats(List<Format> formats) {
        this.formats = formats;
        return this;
    }

}
