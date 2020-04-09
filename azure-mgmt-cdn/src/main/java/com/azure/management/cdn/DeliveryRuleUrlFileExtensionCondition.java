// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.cdn;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * The DeliveryRuleUrlFileExtensionCondition model.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "name")
@JsonTypeName("UrlFileExtension")
@Fluent
public final class DeliveryRuleUrlFileExtensionCondition extends DeliveryRuleCondition {
    /*
     * Defines the parameters for the condition.
     */
    @JsonProperty(value = "parameters", required = true)
    private UrlFileExtensionConditionParameters parameters;

    /**
     * Get the parameters property: Defines the parameters for the condition.
     * 
     * @return the parameters value.
     */
    public UrlFileExtensionConditionParameters parameters() {
        return this.parameters;
    }

    /**
     * Set the parameters property: Defines the parameters for the condition.
     * 
     * @param parameters the parameters value to set.
     * @return the DeliveryRuleUrlFileExtensionCondition object itself.
     */
    public DeliveryRuleUrlFileExtensionCondition withParameters(UrlFileExtensionConditionParameters parameters) {
        this.parameters = parameters;
        return this;
    }
}
