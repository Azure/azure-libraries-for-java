/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cdn;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Defines the PostArgs condition for the delivery rule.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "name")
@JsonTypeName("PostArgs")
public class DeliveryRulePostArgsCondition extends DeliveryRuleCondition {
    /**
     * Defines the parameters for the condition.
     */
    @JsonProperty(value = "parameters", required = true)
    private PostArgsMatchConditionParameters parameters;

    /**
     * Get defines the parameters for the condition.
     *
     * @return the parameters value
     */
    public PostArgsMatchConditionParameters parameters() {
        return this.parameters;
    }

    /**
     * Set defines the parameters for the condition.
     *
     * @param parameters the parameters value to set
     * @return the DeliveryRulePostArgsCondition object itself.
     */
    public DeliveryRulePostArgsCondition withParameters(PostArgsMatchConditionParameters parameters) {
        this.parameters = parameters;
        return this;
    }

}
