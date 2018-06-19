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
 * Azure Application Insights information for performance counters reporting.
 */
public class AppInsightsReference {
    /**
     * Component ID.
     * Azure Application Insights component resource ID.
     */
    @JsonProperty(value = "component", required = true)
    private ResourceId component;

    /**
     * Instrumentation Key.
     * Value of the Azure Application Insights instrumentation key.
     */
    @JsonProperty(value = "instrumentationKey")
    private String instrumentationKey;

    /**
     * Instrumentation key KeyVault Secret reference.
     * KeyVault Store and Secret which contains Azure Application Insights
     * instrumentation key. One of instrumentationKey or
     * instrumentationKeySecretReference must be specified.
     */
    @JsonProperty(value = "instrumentationKeySecretReference")
    private KeyVaultSecretReference instrumentationKeySecretReference;

    /**
     * Get azure Application Insights component resource ID.
     *
     * @return the component value
     */
    public ResourceId component() {
        return this.component;
    }

    /**
     * Set azure Application Insights component resource ID.
     *
     * @param component the component value to set
     * @return the AppInsightsReference object itself.
     */
    public AppInsightsReference withComponent(ResourceId component) {
        this.component = component;
        return this;
    }

    /**
     * Get value of the Azure Application Insights instrumentation key.
     *
     * @return the instrumentationKey value
     */
    public String instrumentationKey() {
        return this.instrumentationKey;
    }

    /**
     * Set value of the Azure Application Insights instrumentation key.
     *
     * @param instrumentationKey the instrumentationKey value to set
     * @return the AppInsightsReference object itself.
     */
    public AppInsightsReference withInstrumentationKey(String instrumentationKey) {
        this.instrumentationKey = instrumentationKey;
        return this;
    }

    /**
     * Get keyVault Store and Secret which contains Azure Application Insights instrumentation key. One of instrumentationKey or instrumentationKeySecretReference must be specified.
     *
     * @return the instrumentationKeySecretReference value
     */
    public KeyVaultSecretReference instrumentationKeySecretReference() {
        return this.instrumentationKeySecretReference;
    }

    /**
     * Set keyVault Store and Secret which contains Azure Application Insights instrumentation key. One of instrumentationKey or instrumentationKeySecretReference must be specified.
     *
     * @param instrumentationKeySecretReference the instrumentationKeySecretReference value to set
     * @return the AppInsightsReference object itself.
     */
    public AppInsightsReference withInstrumentationKeySecretReference(KeyVaultSecretReference instrumentationKeySecretReference) {
        this.instrumentationKeySecretReference = instrumentationKeySecretReference;
        return this;
    }

}
