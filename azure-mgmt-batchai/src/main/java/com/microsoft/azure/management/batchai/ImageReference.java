/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.batchai;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The image reference.
 */
public class ImageReference {
    /**
     * Publisher of the image.
     */
    @JsonProperty(value = "publisher", required = true)
    private String publisher;

    /**
     * Offer of the image.
     */
    @JsonProperty(value = "offer", required = true)
    private String offer;

    /**
     * SKU of the image.
     */
    @JsonProperty(value = "sku", required = true)
    private String sku;

    /**
     * Version of the image.
     */
    @JsonProperty(value = "version")
    private String version;

    /**
     * Get the publisher value.
     *
     * @return the publisher value
     */
    public String publisher() {
        return this.publisher;
    }

    /**
     * Set the publisher value.
     *
     * @param publisher the publisher value to set
     * @return the ImageReference object itself.
     */
    public ImageReference withPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    /**
     * Get the offer value.
     *
     * @return the offer value
     */
    public String offer() {
        return this.offer;
    }

    /**
     * Set the offer value.
     *
     * @param offer the offer value to set
     * @return the ImageReference object itself.
     */
    public ImageReference withOffer(String offer) {
        this.offer = offer;
        return this;
    }

    /**
     * Get the sku value.
     *
     * @return the sku value
     */
    public String sku() {
        return this.sku;
    }

    /**
     * Set the sku value.
     *
     * @param sku the sku value to set
     * @return the ImageReference object itself.
     */
    public ImageReference withSku(String sku) {
        this.sku = sku;
        return this;
    }

    /**
     * Get the version value.
     *
     * @return the version value
     */
    public String version() {
        return this.version;
    }

    /**
     * Set the version value.
     *
     * @param version the version value to set
     * @return the ImageReference object itself.
     */
    public ImageReference withVersion(String version) {
        this.version = version;
        return this;
    }

}
