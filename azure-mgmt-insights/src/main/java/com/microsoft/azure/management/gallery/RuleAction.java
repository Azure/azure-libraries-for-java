/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.gallery;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonSubTypes;

/**
 * The action that is performed when the alert rule becomes active, and when an
 * alert condition is resolved.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "odata.type")
@JsonTypeName("RuleAction")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "Microsoft.Azure.Management.Insights.Models.RuleEmailAction", value = RuleEmailAction.class),
    @JsonSubTypes.Type(name = "Microsoft.Azure.Management.Insights.Models.RuleWebhookAction", value = RuleWebhookAction.class)
})
public class RuleAction {
}
