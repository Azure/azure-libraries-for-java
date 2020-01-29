/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cdn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines the parameters for the url rewrite action.
 */
public class UrlRewriteActionParameters {
    /**
     * The odatatype property.
     */
    @JsonProperty(value = "@odata\\.type", required = true)
    private String odatatype;

    /**
     * define a request URI pattern that identifies the type of requests that
     * may be rewritten. Currently, source pattern uses a prefix-based match.
     * To match all URL paths, use "/" as the source pattern value. To match
     * only the root directory and re-write this path, use the origin path
     * field.
     */
    @JsonProperty(value = "sourcePattern", required = true)
    private String sourcePattern;

    /**
     * Define the destination path for be used in the rewrite. This will
     * overwrite the source pattern.
     */
    @JsonProperty(value = "destination", required = true)
    private String destination;

    /**
     * If True, the remaining path after the source pattern will be appended to
     * the new destination path.
     */
    @JsonProperty(value = "preserveUnmatchedPath")
    private Boolean preserveUnmatchedPath;

    /**
     * Creates an instance of UrlRewriteActionParameters class.
     * @param sourcePattern define a request URI pattern that identifies the type of requests that may be rewritten. Currently, source pattern uses a prefix-based match. To match all URL paths, use "/" as the source pattern value. To match only the root directory and re-write this path, use the origin path field.
     * @param destination define the destination path for be used in the rewrite. This will overwrite the source pattern.
     */
    public UrlRewriteActionParameters() {
        odatatype = "#Microsoft.Azure.Cdn.Models.DeliveryRuleUrlRewriteActionParameters";
    }

    /**
     * Get the odatatype value.
     *
     * @return the odatatype value
     */
    public String odatatype() {
        return this.odatatype;
    }

    /**
     * Set the odatatype value.
     *
     * @param odatatype the odatatype value to set
     * @return the UrlRewriteActionParameters object itself.
     */
    public UrlRewriteActionParameters withOdatatype(String odatatype) {
        this.odatatype = odatatype;
        return this;
    }

    /**
     * Get define a request URI pattern that identifies the type of requests that may be rewritten. Currently, source pattern uses a prefix-based match. To match all URL paths, use "/" as the source pattern value. To match only the root directory and re-write this path, use the origin path field.
     *
     * @return the sourcePattern value
     */
    public String sourcePattern() {
        return this.sourcePattern;
    }

    /**
     * Set define a request URI pattern that identifies the type of requests that may be rewritten. Currently, source pattern uses a prefix-based match. To match all URL paths, use "/" as the source pattern value. To match only the root directory and re-write this path, use the origin path field.
     *
     * @param sourcePattern the sourcePattern value to set
     * @return the UrlRewriteActionParameters object itself.
     */
    public UrlRewriteActionParameters withSourcePattern(String sourcePattern) {
        this.sourcePattern = sourcePattern;
        return this;
    }

    /**
     * Get define the destination path for be used in the rewrite. This will overwrite the source pattern.
     *
     * @return the destination value
     */
    public String destination() {
        return this.destination;
    }

    /**
     * Set define the destination path for be used in the rewrite. This will overwrite the source pattern.
     *
     * @param destination the destination value to set
     * @return the UrlRewriteActionParameters object itself.
     */
    public UrlRewriteActionParameters withDestination(String destination) {
        this.destination = destination;
        return this;
    }

    /**
     * Get if True, the remaining path after the source pattern will be appended to the new destination path.
     *
     * @return the preserveUnmatchedPath value
     */
    public Boolean preserveUnmatchedPath() {
        return this.preserveUnmatchedPath;
    }

    /**
     * Set if True, the remaining path after the source pattern will be appended to the new destination path.
     *
     * @param preserveUnmatchedPath the preserveUnmatchedPath value to set
     * @return the UrlRewriteActionParameters object itself.
     */
    public UrlRewriteActionParameters withPreserveUnmatchedPath(Boolean preserveUnmatchedPath) {
        this.preserveUnmatchedPath = preserveUnmatchedPath;
        return this;
    }

}
