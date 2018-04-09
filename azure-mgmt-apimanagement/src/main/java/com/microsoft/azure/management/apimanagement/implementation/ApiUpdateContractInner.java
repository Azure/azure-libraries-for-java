/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.apimanagement.implementation;

import com.microsoft.azure.management.apimanagement.AuthenticationSettingsContract;
import com.microsoft.azure.management.apimanagement.SubscriptionKeyParameterNamesContract;
import com.microsoft.azure.management.apimanagement.ApiType;
import java.util.List;
import com.microsoft.azure.management.apimanagement.Protocol;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * API update contract details.
 */
@JsonFlatten
public class ApiUpdateContractInner {
    /**
     * Description of the API. May include HTML formatting tags.
     */
    @JsonProperty(value = "properties.description")
    private String description;

    /**
     * Collection of authentication settings included into this API.
     */
    @JsonProperty(value = "properties.authenticationSettings")
    private AuthenticationSettingsContract authenticationSettings;

    /**
     * Protocols over which API is made available.
     */
    @JsonProperty(value = "properties.subscriptionKeyParameterNames")
    private SubscriptionKeyParameterNamesContract subscriptionKeyParameterNames;

    /**
     * Type of API. Possible values include: 'http', 'soap'.
     */
    @JsonProperty(value = "properties.type")
    private ApiType apiType;

    /**
     * Describes the Revision of the Api. If no value is provided, default
     * revision 1 is created.
     */
    @JsonProperty(value = "properties.apiRevision")
    private String apiRevision;

    /**
     * Indicates the Version identifier of the API if the API is versioned.
     */
    @JsonProperty(value = "properties.apiVersion")
    private String apiVersion;

    /**
     * Indicates if API revision is current api revision.
     */
    @JsonProperty(value = "properties.isCurrent", access = JsonProperty.Access.WRITE_ONLY)
    private Boolean isCurrent;

    /**
     * Indicates if API revision is accessible via the gateway.
     */
    @JsonProperty(value = "properties.isOnline", access = JsonProperty.Access.WRITE_ONLY)
    private Boolean isOnline;

    /**
     * A resource identifier for the related ApiVersionSet.
     */
    @JsonProperty(value = "properties.apiVersionSetId")
    private String apiVersionSetId;

    /**
     * API name.
     */
    @JsonProperty(value = "properties.displayName")
    private String displayName;

    /**
     * Absolute URL of the backend service implementing this API.
     */
    @JsonProperty(value = "properties.serviceUrl")
    private String serviceUrl;

    /**
     * Relative URL uniquely identifying this API and all of its resource paths
     * within the API Management service instance. It is appended to the API
     * endpoint base URL specified during the service instance creation to form
     * a public URL for this API.
     */
    @JsonProperty(value = "properties.path")
    private String path;

    /**
     * Describes on which protocols the operations in this API can be invoked.
     */
    @JsonProperty(value = "properties.protocols")
    private List<Protocol> protocols;

    /**
     * Get the description value.
     *
     * @return the description value
     */
    public String description() {
        return this.description;
    }

    /**
     * Set the description value.
     *
     * @param description the description value to set
     * @return the ApiUpdateContractInner object itself.
     */
    public ApiUpdateContractInner withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get the authenticationSettings value.
     *
     * @return the authenticationSettings value
     */
    public AuthenticationSettingsContract authenticationSettings() {
        return this.authenticationSettings;
    }

    /**
     * Set the authenticationSettings value.
     *
     * @param authenticationSettings the authenticationSettings value to set
     * @return the ApiUpdateContractInner object itself.
     */
    public ApiUpdateContractInner withAuthenticationSettings(AuthenticationSettingsContract authenticationSettings) {
        this.authenticationSettings = authenticationSettings;
        return this;
    }

    /**
     * Get the subscriptionKeyParameterNames value.
     *
     * @return the subscriptionKeyParameterNames value
     */
    public SubscriptionKeyParameterNamesContract subscriptionKeyParameterNames() {
        return this.subscriptionKeyParameterNames;
    }

    /**
     * Set the subscriptionKeyParameterNames value.
     *
     * @param subscriptionKeyParameterNames the subscriptionKeyParameterNames value to set
     * @return the ApiUpdateContractInner object itself.
     */
    public ApiUpdateContractInner withSubscriptionKeyParameterNames(SubscriptionKeyParameterNamesContract subscriptionKeyParameterNames) {
        this.subscriptionKeyParameterNames = subscriptionKeyParameterNames;
        return this;
    }

    /**
     * Get the apiType value.
     *
     * @return the apiType value
     */
    public ApiType apiType() {
        return this.apiType;
    }

    /**
     * Set the apiType value.
     *
     * @param apiType the apiType value to set
     * @return the ApiUpdateContractInner object itself.
     */
    public ApiUpdateContractInner withApiType(ApiType apiType) {
        this.apiType = apiType;
        return this;
    }

    /**
     * Get the apiRevision value.
     *
     * @return the apiRevision value
     */
    public String apiRevision() {
        return this.apiRevision;
    }

    /**
     * Set the apiRevision value.
     *
     * @param apiRevision the apiRevision value to set
     * @return the ApiUpdateContractInner object itself.
     */
    public ApiUpdateContractInner withApiRevision(String apiRevision) {
        this.apiRevision = apiRevision;
        return this;
    }

    /**
     * Get the apiVersion value.
     *
     * @return the apiVersion value
     */
    public String apiVersion() {
        return this.apiVersion;
    }

    /**
     * Set the apiVersion value.
     *
     * @param apiVersion the apiVersion value to set
     * @return the ApiUpdateContractInner object itself.
     */
    public ApiUpdateContractInner withApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    /**
     * Get the isCurrent value.
     *
     * @return the isCurrent value
     */
    public Boolean isCurrent() {
        return this.isCurrent;
    }

    /**
     * Get the isOnline value.
     *
     * @return the isOnline value
     */
    public Boolean isOnline() {
        return this.isOnline;
    }

    /**
     * Get the apiVersionSetId value.
     *
     * @return the apiVersionSetId value
     */
    public String apiVersionSetId() {
        return this.apiVersionSetId;
    }

    /**
     * Set the apiVersionSetId value.
     *
     * @param apiVersionSetId the apiVersionSetId value to set
     * @return the ApiUpdateContractInner object itself.
     */
    public ApiUpdateContractInner withApiVersionSetId(String apiVersionSetId) {
        this.apiVersionSetId = apiVersionSetId;
        return this;
    }

    /**
     * Get the displayName value.
     *
     * @return the displayName value
     */
    public String displayName() {
        return this.displayName;
    }

    /**
     * Set the displayName value.
     *
     * @param displayName the displayName value to set
     * @return the ApiUpdateContractInner object itself.
     */
    public ApiUpdateContractInner withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Get the serviceUrl value.
     *
     * @return the serviceUrl value
     */
    public String serviceUrl() {
        return this.serviceUrl;
    }

    /**
     * Set the serviceUrl value.
     *
     * @param serviceUrl the serviceUrl value to set
     * @return the ApiUpdateContractInner object itself.
     */
    public ApiUpdateContractInner withServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
        return this;
    }

    /**
     * Get the path value.
     *
     * @return the path value
     */
    public String path() {
        return this.path;
    }

    /**
     * Set the path value.
     *
     * @param path the path value to set
     * @return the ApiUpdateContractInner object itself.
     */
    public ApiUpdateContractInner withPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * Get the protocols value.
     *
     * @return the protocols value
     */
    public List<Protocol> protocols() {
        return this.protocols;
    }

    /**
     * Set the protocols value.
     *
     * @param protocols the protocols value to set
     * @return the ApiUpdateContractInner object itself.
     */
    public ApiUpdateContractInner withProtocols(List<Protocol> protocols) {
        this.protocols = protocols;
        return this;
    }

}
