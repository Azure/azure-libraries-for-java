/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.servermanagement.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON properties that the gateway service uses know how to communicate with
 * the resource.
 */
public class GatewayProfileInner {
    /**
     * The Dataplane connection URL.
     */
    @JsonProperty(value = "dataPlaneServiceBaseAddress")
    private String dataPlaneServiceBaseAddress;

    /**
     * The ID of the gateway.
     */
    @JsonProperty(value = "gatewayId")
    private String gatewayId;

    /**
     * The environment for the gateway (DEV, DogFood, or Production).
     */
    @JsonProperty(value = "environment")
    private String environment;

    /**
     * Gateway upgrade manifest URL.
     */
    @JsonProperty(value = "upgradeManifestUrl")
    private String upgradeManifestUrl;

    /**
     * Messaging namespace.
     */
    @JsonProperty(value = "messagingNamespace")
    private String messagingNamespace;

    /**
     * Messaging Account.
     */
    @JsonProperty(value = "messagingAccount")
    private String messagingAccount;

    /**
     * Messaging Key.
     */
    @JsonProperty(value = "messagingKey")
    private String messagingKey;

    /**
     * Request queue name.
     */
    @JsonProperty(value = "requestQueue")
    private String requestQueue;

    /**
     * Response topic name.
     */
    @JsonProperty(value = "responseTopic")
    private String responseTopic;

    /**
     * The gateway status blob SAS URL.
     */
    @JsonProperty(value = "statusBlobSignature")
    private String statusBlobSignature;

    /**
     * Get the dataPlaneServiceBaseAddress value.
     *
     * @return the dataPlaneServiceBaseAddress value
     */
    public String dataPlaneServiceBaseAddress() {
        return this.dataPlaneServiceBaseAddress;
    }

    /**
     * Set the dataPlaneServiceBaseAddress value.
     *
     * @param dataPlaneServiceBaseAddress the dataPlaneServiceBaseAddress value to set
     * @return the GatewayProfileInner object itself.
     */
    public GatewayProfileInner withDataPlaneServiceBaseAddress(String dataPlaneServiceBaseAddress) {
        this.dataPlaneServiceBaseAddress = dataPlaneServiceBaseAddress;
        return this;
    }

    /**
     * Get the gatewayId value.
     *
     * @return the gatewayId value
     */
    public String gatewayId() {
        return this.gatewayId;
    }

    /**
     * Set the gatewayId value.
     *
     * @param gatewayId the gatewayId value to set
     * @return the GatewayProfileInner object itself.
     */
    public GatewayProfileInner withGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
        return this;
    }

    /**
     * Get the environment value.
     *
     * @return the environment value
     */
    public String environment() {
        return this.environment;
    }

    /**
     * Set the environment value.
     *
     * @param environment the environment value to set
     * @return the GatewayProfileInner object itself.
     */
    public GatewayProfileInner withEnvironment(String environment) {
        this.environment = environment;
        return this;
    }

    /**
     * Get the upgradeManifestUrl value.
     *
     * @return the upgradeManifestUrl value
     */
    public String upgradeManifestUrl() {
        return this.upgradeManifestUrl;
    }

    /**
     * Set the upgradeManifestUrl value.
     *
     * @param upgradeManifestUrl the upgradeManifestUrl value to set
     * @return the GatewayProfileInner object itself.
     */
    public GatewayProfileInner withUpgradeManifestUrl(String upgradeManifestUrl) {
        this.upgradeManifestUrl = upgradeManifestUrl;
        return this;
    }

    /**
     * Get the messagingNamespace value.
     *
     * @return the messagingNamespace value
     */
    public String messagingNamespace() {
        return this.messagingNamespace;
    }

    /**
     * Set the messagingNamespace value.
     *
     * @param messagingNamespace the messagingNamespace value to set
     * @return the GatewayProfileInner object itself.
     */
    public GatewayProfileInner withMessagingNamespace(String messagingNamespace) {
        this.messagingNamespace = messagingNamespace;
        return this;
    }

    /**
     * Get the messagingAccount value.
     *
     * @return the messagingAccount value
     */
    public String messagingAccount() {
        return this.messagingAccount;
    }

    /**
     * Set the messagingAccount value.
     *
     * @param messagingAccount the messagingAccount value to set
     * @return the GatewayProfileInner object itself.
     */
    public GatewayProfileInner withMessagingAccount(String messagingAccount) {
        this.messagingAccount = messagingAccount;
        return this;
    }

    /**
     * Get the messagingKey value.
     *
     * @return the messagingKey value
     */
    public String messagingKey() {
        return this.messagingKey;
    }

    /**
     * Set the messagingKey value.
     *
     * @param messagingKey the messagingKey value to set
     * @return the GatewayProfileInner object itself.
     */
    public GatewayProfileInner withMessagingKey(String messagingKey) {
        this.messagingKey = messagingKey;
        return this;
    }

    /**
     * Get the requestQueue value.
     *
     * @return the requestQueue value
     */
    public String requestQueue() {
        return this.requestQueue;
    }

    /**
     * Set the requestQueue value.
     *
     * @param requestQueue the requestQueue value to set
     * @return the GatewayProfileInner object itself.
     */
    public GatewayProfileInner withRequestQueue(String requestQueue) {
        this.requestQueue = requestQueue;
        return this;
    }

    /**
     * Get the responseTopic value.
     *
     * @return the responseTopic value
     */
    public String responseTopic() {
        return this.responseTopic;
    }

    /**
     * Set the responseTopic value.
     *
     * @param responseTopic the responseTopic value to set
     * @return the GatewayProfileInner object itself.
     */
    public GatewayProfileInner withResponseTopic(String responseTopic) {
        this.responseTopic = responseTopic;
        return this;
    }

    /**
     * Get the statusBlobSignature value.
     *
     * @return the statusBlobSignature value
     */
    public String statusBlobSignature() {
        return this.statusBlobSignature;
    }

    /**
     * Set the statusBlobSignature value.
     *
     * @param statusBlobSignature the statusBlobSignature value to set
     * @return the GatewayProfileInner object itself.
     */
    public GatewayProfileInner withStatusBlobSignature(String statusBlobSignature) {
        this.statusBlobSignature = statusBlobSignature;
        return this;
    }

}
