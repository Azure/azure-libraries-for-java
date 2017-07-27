/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.streamanalytics;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * Describes an IoT Hub input data source that contains stream data.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("Microsoft.Devices/IotHubs")
@JsonFlatten
public class IoTHubStreamInputDataSource extends StreamInputDataSource {
    /**
     * The name or the URI of the IoT Hub. Required on PUT (CreateOrReplace)
     * requests.
     */
    @JsonProperty(value = "properties.iotHubNamespace")
    private String iotHubNamespace;

    /**
     * The shared access policy name for the IoT Hub. This policy must contain
     * at least the Service connect permission. Required on PUT
     * (CreateOrReplace) requests.
     */
    @JsonProperty(value = "properties.sharedAccessPolicyName")
    private String sharedAccessPolicyName;

    /**
     * The shared access policy key for the specified shared access policy.
     * Required on PUT (CreateOrReplace) requests.
     */
    @JsonProperty(value = "properties.sharedAccessPolicyKey")
    private String sharedAccessPolicyKey;

    /**
     * The name of an IoT Hub Consumer Group that should be used to read events
     * from the IoT Hub. If not specified, the input uses the Iot Hub’s default
     * consumer group.
     */
    @JsonProperty(value = "properties.consumerGroupName")
    private String consumerGroupName;

    /**
     * The IoT Hub endpoint to connect to (ie. messages/events,
     * messages/operationsMonitoringEvents, etc.).
     */
    @JsonProperty(value = "properties.endpoint")
    private String endpoint;

    /**
     * Get the iotHubNamespace value.
     *
     * @return the iotHubNamespace value
     */
    public String iotHubNamespace() {
        return this.iotHubNamespace;
    }

    /**
     * Set the iotHubNamespace value.
     *
     * @param iotHubNamespace the iotHubNamespace value to set
     * @return the IoTHubStreamInputDataSource object itself.
     */
    public IoTHubStreamInputDataSource withIotHubNamespace(String iotHubNamespace) {
        this.iotHubNamespace = iotHubNamespace;
        return this;
    }

    /**
     * Get the sharedAccessPolicyName value.
     *
     * @return the sharedAccessPolicyName value
     */
    public String sharedAccessPolicyName() {
        return this.sharedAccessPolicyName;
    }

    /**
     * Set the sharedAccessPolicyName value.
     *
     * @param sharedAccessPolicyName the sharedAccessPolicyName value to set
     * @return the IoTHubStreamInputDataSource object itself.
     */
    public IoTHubStreamInputDataSource withSharedAccessPolicyName(String sharedAccessPolicyName) {
        this.sharedAccessPolicyName = sharedAccessPolicyName;
        return this;
    }

    /**
     * Get the sharedAccessPolicyKey value.
     *
     * @return the sharedAccessPolicyKey value
     */
    public String sharedAccessPolicyKey() {
        return this.sharedAccessPolicyKey;
    }

    /**
     * Set the sharedAccessPolicyKey value.
     *
     * @param sharedAccessPolicyKey the sharedAccessPolicyKey value to set
     * @return the IoTHubStreamInputDataSource object itself.
     */
    public IoTHubStreamInputDataSource withSharedAccessPolicyKey(String sharedAccessPolicyKey) {
        this.sharedAccessPolicyKey = sharedAccessPolicyKey;
        return this;
    }

    /**
     * Get the consumerGroupName value.
     *
     * @return the consumerGroupName value
     */
    public String consumerGroupName() {
        return this.consumerGroupName;
    }

    /**
     * Set the consumerGroupName value.
     *
     * @param consumerGroupName the consumerGroupName value to set
     * @return the IoTHubStreamInputDataSource object itself.
     */
    public IoTHubStreamInputDataSource withConsumerGroupName(String consumerGroupName) {
        this.consumerGroupName = consumerGroupName;
        return this;
    }

    /**
     * Get the endpoint value.
     *
     * @return the endpoint value
     */
    public String endpoint() {
        return this.endpoint;
    }

    /**
     * Set the endpoint value.
     *
     * @param endpoint the endpoint value to set
     * @return the IoTHubStreamInputDataSource object itself.
     */
    public IoTHubStreamInputDataSource withEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

}
