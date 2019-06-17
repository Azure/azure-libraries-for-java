/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.eventhub.implementation;

import com.microsoft.azure.management.eventhub.Sku;
import org.joda.time.DateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.rest.SkipParentValidation;
import com.microsoft.azure.Resource;

/**
 * Single Namespace item in List or Get Operation.
 */
@JsonFlatten
@SkipParentValidation
public class EHNamespaceInner extends Resource {
    /**
     * Properties of sku resource.
     */
    @JsonProperty(value = "sku")
    private Sku sku;

    /**
     * Provisioning state of the Namespace.
     */
    @JsonProperty(value = "properties.provisioningState", access = JsonProperty.Access.WRITE_ONLY)
    private String provisioningState;

    /**
     * The time the Namespace was created.
     */
    @JsonProperty(value = "properties.createdAt", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime createdAt;

    /**
     * The time the Namespace was updated.
     */
    @JsonProperty(value = "properties.updatedAt", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime updatedAt;

    /**
     * Endpoint you can use to perform Service Bus operations.
     */
    @JsonProperty(value = "properties.serviceBusEndpoint", access = JsonProperty.Access.WRITE_ONLY)
    private String serviceBusEndpoint;

    /**
     * Identifier for Azure Insights metrics.
     */
    @JsonProperty(value = "properties.metricId", access = JsonProperty.Access.WRITE_ONLY)
    private String metricId;

    /**
     * Value that indicates whether AutoInflate is enabled for eventhub
     * namespace.
     */
    @JsonProperty(value = "properties.isAutoInflateEnabled")
    private Boolean isAutoInflateEnabled;

    /**
     * Upper limit of throughput units when AutoInflate is enabled, value
     * should be within 0 to 20 throughput units. ( '0' if AutoInflateEnabled =
     * true).
     */
    @JsonProperty(value = "properties.maximumThroughputUnits")
    private Integer maximumThroughputUnits;

    /**
     * Value that indicates whether Kafka is enabled for eventhub namespace.
     */
    @JsonProperty(value = "properties.kafkaEnabled")
    private Boolean kafkaEnabled;

    /**
     * Get properties of sku resource.
     *
     * @return the sku value
     */
    public Sku sku() {
        return this.sku;
    }

    /**
     * Set properties of sku resource.
     *
     * @param sku the sku value to set
     * @return the EHNamespaceInner object itself.
     */
    public EHNamespaceInner withSku(Sku sku) {
        this.sku = sku;
        return this;
    }

    /**
     * Get provisioning state of the Namespace.
     *
     * @return the provisioningState value
     */
    public String provisioningState() {
        return this.provisioningState;
    }

    /**
     * Get the time the Namespace was created.
     *
     * @return the createdAt value
     */
    public DateTime createdAt() {
        return this.createdAt;
    }

    /**
     * Get the time the Namespace was updated.
     *
     * @return the updatedAt value
     */
    public DateTime updatedAt() {
        return this.updatedAt;
    }

    /**
     * Get endpoint you can use to perform Service Bus operations.
     *
     * @return the serviceBusEndpoint value
     */
    public String serviceBusEndpoint() {
        return this.serviceBusEndpoint;
    }

    /**
     * Get identifier for Azure Insights metrics.
     *
     * @return the metricId value
     */
    public String metricId() {
        return this.metricId;
    }

    /**
     * Get value that indicates whether AutoInflate is enabled for eventhub namespace.
     *
     * @return the isAutoInflateEnabled value
     */
    public Boolean isAutoInflateEnabled() {
        return this.isAutoInflateEnabled;
    }

    /**
     * Set value that indicates whether AutoInflate is enabled for eventhub namespace.
     *
     * @param isAutoInflateEnabled the isAutoInflateEnabled value to set
     * @return the EHNamespaceInner object itself.
     */
    public EHNamespaceInner withIsAutoInflateEnabled(Boolean isAutoInflateEnabled) {
        this.isAutoInflateEnabled = isAutoInflateEnabled;
        return this;
    }

    /**
     * Get upper limit of throughput units when AutoInflate is enabled, value should be within 0 to 20 throughput units. ( '0' if AutoInflateEnabled = true).
     *
     * @return the maximumThroughputUnits value
     */
    public Integer maximumThroughputUnits() {
        return this.maximumThroughputUnits;
    }

    /**
     * Set upper limit of throughput units when AutoInflate is enabled, value should be within 0 to 20 throughput units. ( '0' if AutoInflateEnabled = true).
     *
     * @param maximumThroughputUnits the maximumThroughputUnits value to set
     * @return the EHNamespaceInner object itself.
     */
    public EHNamespaceInner withMaximumThroughputUnits(Integer maximumThroughputUnits) {
        this.maximumThroughputUnits = maximumThroughputUnits;
        return this;
    }

    /**
     * Get value that indicates whether Kafka is enabled for eventhub namespace.
     *
     * @return the kafkaEnabled value
     */
    public Boolean kafkaEnabled() {
        return this.kafkaEnabled;
    }

    /**
     * Set value that indicates whether Kafka is enabled for eventhub namespace.
     *
     * @param kafkaEnabled the kafkaEnabled value to set
     * @return the EHNamespaceInner object itself.
     */
    public EHNamespaceInner withKafkaEnabled(Boolean kafkaEnabled) {
        this.kafkaEnabled = kafkaEnabled;
        return this;
    }

}
