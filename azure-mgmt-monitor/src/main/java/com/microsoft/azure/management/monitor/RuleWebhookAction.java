/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.monitor;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Specifies the action to post to service when the rule condition is
 * evaluated. The discriminator is always RuleWebhookAction in this case.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "odata.type")
@JsonTypeName("Microsoft.Azure.Management.Insights.Models.RuleWebhookAction")
public class RuleWebhookAction extends RuleAction {
    /**
     * the service uri to Post the notification when the alert activates or
     * resolves.
     */
    @JsonProperty(value = "serviceUri")
    private String serviceUri;

    /**
     * the dictionary of custom properties to include with the post operation.
     * These data are appended to the webhook payload.
     */
    @JsonProperty(value = "properties")
    private Map<String, String> properties;

    /**
     * Get the service uri to Post the notification when the alert activates or resolves.
     *
     * @return the serviceUri value
     */
    public String serviceUri() {
        return this.serviceUri;
    }

    /**
     * Set the service uri to Post the notification when the alert activates or resolves.
     *
     * @param serviceUri the serviceUri value to set
     * @return the RuleWebhookAction object itself.
     */
    public RuleWebhookAction withServiceUri(String serviceUri) {
        this.serviceUri = serviceUri;
        return this;
    }

    /**
     * Get the dictionary of custom properties to include with the post operation. These data are appended to the webhook payload.
     *
     * @return the properties value
     */
    public Map<String, String> properties() {
        return this.properties;
    }

    /**
     * Set the dictionary of custom properties to include with the post operation. These data are appended to the webhook payload.
     *
     * @param properties the properties value to set
     * @return the RuleWebhookAction object itself.
     */
    public RuleWebhookAction withProperties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

}
