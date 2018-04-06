/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.mediaservices;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to specify drm configurations of CommonEncryptionCbcs scheme in
 * Streaming Policy.
 */
public class CbcsDrmConfiguration {
    /**
     * Fairplay configurations.
     */
    @JsonProperty(value = "fairPlay")
    private StreamingPolicyFairPlayConfiguration fairPlay;

    /**
     * PlayReady configurations.
     */
    @JsonProperty(value = "playReady")
    private StreamingPolicyPlayReadyConfiguration playReady;

    /**
     * Widevine configurations.
     */
    @JsonProperty(value = "widevine")
    private StreamingPolicyWidevineConfiguration widevine;

    /**
     * Get the fairPlay value.
     *
     * @return the fairPlay value
     */
    public StreamingPolicyFairPlayConfiguration fairPlay() {
        return this.fairPlay;
    }

    /**
     * Set the fairPlay value.
     *
     * @param fairPlay the fairPlay value to set
     * @return the CbcsDrmConfiguration object itself.
     */
    public CbcsDrmConfiguration withFairPlay(StreamingPolicyFairPlayConfiguration fairPlay) {
        this.fairPlay = fairPlay;
        return this;
    }

    /**
     * Get the playReady value.
     *
     * @return the playReady value
     */
    public StreamingPolicyPlayReadyConfiguration playReady() {
        return this.playReady;
    }

    /**
     * Set the playReady value.
     *
     * @param playReady the playReady value to set
     * @return the CbcsDrmConfiguration object itself.
     */
    public CbcsDrmConfiguration withPlayReady(StreamingPolicyPlayReadyConfiguration playReady) {
        this.playReady = playReady;
        return this;
    }

    /**
     * Get the widevine value.
     *
     * @return the widevine value
     */
    public StreamingPolicyWidevineConfiguration widevine() {
        return this.widevine;
    }

    /**
     * Set the widevine value.
     *
     * @param widevine the widevine value to set
     * @return the CbcsDrmConfiguration object itself.
     */
    public CbcsDrmConfiguration withWidevine(StreamingPolicyWidevineConfiguration widevine) {
        this.widevine = widevine;
        return this;
    }

}
