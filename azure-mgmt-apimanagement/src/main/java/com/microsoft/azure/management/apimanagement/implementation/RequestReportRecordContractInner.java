/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.apimanagement.implementation;

import org.joda.time.DateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request Report data.
 */
public class RequestReportRecordContractInner {
    /**
     * API identifier path. /apis/{apiId}.
     */
    @JsonProperty(value = "apiId")
    private String apiId;

    /**
     * Operation identifier path. /apis/{apiId}/operations/{operationId}.
     */
    @JsonProperty(value = "operationId")
    private String operationId;

    /**
     * Product identifier path. /products/{productId}.
     */
    @JsonProperty(value = "productId", access = JsonProperty.Access.WRITE_ONLY)
    private String productId;

    /**
     * User identifier path. /users/{userId}.
     */
    @JsonProperty(value = "userId", access = JsonProperty.Access.WRITE_ONLY)
    private String userId;

    /**
     * The HTTP method associated with this request..
     */
    @JsonProperty(value = "method")
    private String method;

    /**
     * The full URL associated with this request.
     */
    @JsonProperty(value = "url")
    private String url;

    /**
     * The client IP address associated with this request.
     */
    @JsonProperty(value = "ipAddress")
    private String ipAddress;

    /**
     * The HTTP status code received by the gateway as a result of forwarding
     * this request to the backend.
     */
    @JsonProperty(value = "backendResponseCode")
    private String backendResponseCode;

    /**
     * The HTTP status code returned by the gateway.
     */
    @JsonProperty(value = "responseCode")
    private Integer responseCode;

    /**
     * The size of the response returned by the gateway.
     */
    @JsonProperty(value = "responseSize")
    private Integer responseSize;

    /**
     * The date and time when this request was received by the gateway in ISO
     * 8601 format.
     */
    @JsonProperty(value = "timestamp")
    private DateTime timestamp;

    /**
     * Specifies if response cache was involved in generating the response. If
     * the value is none, the cache was not used. If the value is hit, cached
     * response was returned. If the value is miss, the cache was used but
     * lookup resulted in a miss and request was fullfilled by the backend.
     */
    @JsonProperty(value = "cache")
    private String cache;

    /**
     * The total time it took to process this request.
     */
    @JsonProperty(value = "apiTime")
    private Double apiTime;

    /**
     * he time it took to forward this request to the backend and get the
     * response back.
     */
    @JsonProperty(value = "serviceTime")
    private Double serviceTime;

    /**
     * Azure region where the gateway that processed this request is located.
     */
    @JsonProperty(value = "apiRegion")
    private String apiRegion;

    /**
     * Subscription identifier path. /subscriptions/{subscriptionId}.
     */
    @JsonProperty(value = "subscriptionId")
    private String subscriptionId;

    /**
     * Request Identifier.
     */
    @JsonProperty(value = "requestId")
    private String requestId;

    /**
     * The size of this request..
     */
    @JsonProperty(value = "requestSize")
    private Integer requestSize;

    /**
     * Get the apiId value.
     *
     * @return the apiId value
     */
    public String apiId() {
        return this.apiId;
    }

    /**
     * Set the apiId value.
     *
     * @param apiId the apiId value to set
     * @return the RequestReportRecordContractInner object itself.
     */
    public RequestReportRecordContractInner withApiId(String apiId) {
        this.apiId = apiId;
        return this;
    }

    /**
     * Get the operationId value.
     *
     * @return the operationId value
     */
    public String operationId() {
        return this.operationId;
    }

    /**
     * Set the operationId value.
     *
     * @param operationId the operationId value to set
     * @return the RequestReportRecordContractInner object itself.
     */
    public RequestReportRecordContractInner withOperationId(String operationId) {
        this.operationId = operationId;
        return this;
    }

    /**
     * Get the productId value.
     *
     * @return the productId value
     */
    public String productId() {
        return this.productId;
    }

    /**
     * Get the userId value.
     *
     * @return the userId value
     */
    public String userId() {
        return this.userId;
    }

    /**
     * Get the method value.
     *
     * @return the method value
     */
    public String method() {
        return this.method;
    }

    /**
     * Set the method value.
     *
     * @param method the method value to set
     * @return the RequestReportRecordContractInner object itself.
     */
    public RequestReportRecordContractInner withMethod(String method) {
        this.method = method;
        return this;
    }

    /**
     * Get the url value.
     *
     * @return the url value
     */
    public String url() {
        return this.url;
    }

    /**
     * Set the url value.
     *
     * @param url the url value to set
     * @return the RequestReportRecordContractInner object itself.
     */
    public RequestReportRecordContractInner withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Get the ipAddress value.
     *
     * @return the ipAddress value
     */
    public String ipAddress() {
        return this.ipAddress;
    }

    /**
     * Set the ipAddress value.
     *
     * @param ipAddress the ipAddress value to set
     * @return the RequestReportRecordContractInner object itself.
     */
    public RequestReportRecordContractInner withIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    /**
     * Get the backendResponseCode value.
     *
     * @return the backendResponseCode value
     */
    public String backendResponseCode() {
        return this.backendResponseCode;
    }

    /**
     * Set the backendResponseCode value.
     *
     * @param backendResponseCode the backendResponseCode value to set
     * @return the RequestReportRecordContractInner object itself.
     */
    public RequestReportRecordContractInner withBackendResponseCode(String backendResponseCode) {
        this.backendResponseCode = backendResponseCode;
        return this;
    }

    /**
     * Get the responseCode value.
     *
     * @return the responseCode value
     */
    public Integer responseCode() {
        return this.responseCode;
    }

    /**
     * Set the responseCode value.
     *
     * @param responseCode the responseCode value to set
     * @return the RequestReportRecordContractInner object itself.
     */
    public RequestReportRecordContractInner withResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    /**
     * Get the responseSize value.
     *
     * @return the responseSize value
     */
    public Integer responseSize() {
        return this.responseSize;
    }

    /**
     * Set the responseSize value.
     *
     * @param responseSize the responseSize value to set
     * @return the RequestReportRecordContractInner object itself.
     */
    public RequestReportRecordContractInner withResponseSize(Integer responseSize) {
        this.responseSize = responseSize;
        return this;
    }

    /**
     * Get the timestamp value.
     *
     * @return the timestamp value
     */
    public DateTime timestamp() {
        return this.timestamp;
    }

    /**
     * Set the timestamp value.
     *
     * @param timestamp the timestamp value to set
     * @return the RequestReportRecordContractInner object itself.
     */
    public RequestReportRecordContractInner withTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * Get the cache value.
     *
     * @return the cache value
     */
    public String cache() {
        return this.cache;
    }

    /**
     * Set the cache value.
     *
     * @param cache the cache value to set
     * @return the RequestReportRecordContractInner object itself.
     */
    public RequestReportRecordContractInner withCache(String cache) {
        this.cache = cache;
        return this;
    }

    /**
     * Get the apiTime value.
     *
     * @return the apiTime value
     */
    public Double apiTime() {
        return this.apiTime;
    }

    /**
     * Set the apiTime value.
     *
     * @param apiTime the apiTime value to set
     * @return the RequestReportRecordContractInner object itself.
     */
    public RequestReportRecordContractInner withApiTime(Double apiTime) {
        this.apiTime = apiTime;
        return this;
    }

    /**
     * Get the serviceTime value.
     *
     * @return the serviceTime value
     */
    public Double serviceTime() {
        return this.serviceTime;
    }

    /**
     * Set the serviceTime value.
     *
     * @param serviceTime the serviceTime value to set
     * @return the RequestReportRecordContractInner object itself.
     */
    public RequestReportRecordContractInner withServiceTime(Double serviceTime) {
        this.serviceTime = serviceTime;
        return this;
    }

    /**
     * Get the apiRegion value.
     *
     * @return the apiRegion value
     */
    public String apiRegion() {
        return this.apiRegion;
    }

    /**
     * Set the apiRegion value.
     *
     * @param apiRegion the apiRegion value to set
     * @return the RequestReportRecordContractInner object itself.
     */
    public RequestReportRecordContractInner withApiRegion(String apiRegion) {
        this.apiRegion = apiRegion;
        return this;
    }

    /**
     * Get the subscriptionId value.
     *
     * @return the subscriptionId value
     */
    public String subscriptionId() {
        return this.subscriptionId;
    }

    /**
     * Set the subscriptionId value.
     *
     * @param subscriptionId the subscriptionId value to set
     * @return the RequestReportRecordContractInner object itself.
     */
    public RequestReportRecordContractInner withSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    /**
     * Get the requestId value.
     *
     * @return the requestId value
     */
    public String requestId() {
        return this.requestId;
    }

    /**
     * Set the requestId value.
     *
     * @param requestId the requestId value to set
     * @return the RequestReportRecordContractInner object itself.
     */
    public RequestReportRecordContractInner withRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    /**
     * Get the requestSize value.
     *
     * @return the requestSize value
     */
    public Integer requestSize() {
        return this.requestSize;
    }

    /**
     * Set the requestSize value.
     *
     * @param requestSize the requestSize value to set
     * @return the RequestReportRecordContractInner object itself.
     */
    public RequestReportRecordContractInner withRequestSize(Integer requestSize) {
        this.requestSize = requestSize;
        return this;
    }

}
