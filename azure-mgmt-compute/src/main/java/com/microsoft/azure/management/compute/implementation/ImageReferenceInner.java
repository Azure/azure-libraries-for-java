/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.azure.SubResource;

/**
 * The image reference.
 */
public class ImageReferenceInner extends SubResource {
    /**
     * The image publisher.
     */
    @JsonProperty(value = "publisher")
    private String publisher;

    /**
     * The image offer.
     */
    @JsonProperty(value = "offer")
    private String offer;

    /**
     * The image SKU.
     */
    @JsonProperty(value = "sku")
    private String sku;

    /**
     * The image version. The allowed formats are Major.Minor.Build or
     * 'latest'. Major, Minor and Build are decimal numbers. Specify 'latest'
     * to use the latest version of the image.
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
     * @return the ImageReferenceInner object itself.
     */
    public ImageReferenceInner withPublisher(String publisher) {
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
     * @return the ImageReferenceInner object itself.
     */
    public ImageReferenceInner withOffer(String offer) {
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
     * @return the ImageReferenceInner object itself.
     */
    public ImageReferenceInner withSku(String sku) {
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
     * @return the ImageReferenceInner object itself.
     */
    public ImageReferenceInner withVersion(String version) {
        this.version = version;
        return this;
    }

}
