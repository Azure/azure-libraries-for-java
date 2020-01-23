// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.model;

import com.azure.core.annotation.BodyParam;
import com.azure.core.annotation.Delete;
import com.azure.core.annotation.ExpectedResponses;
import com.azure.core.annotation.Get;
import com.azure.core.annotation.Host;
import com.azure.core.annotation.HostParam;
import com.azure.core.annotation.PathParam;
import com.azure.core.annotation.Put;
import com.azure.core.annotation.QueryParam;
import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceInterface;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.annotation.UnexpectedResponseExceptionType;
import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.PagedResponse;
import com.azure.core.http.rest.PagedResponseBase;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.RestProxy;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.management.CloudException;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * ServiceEndpointPolicyDefinitions.
 */
public final class ServiceEndpointPolicyDefinitionsInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private ServiceEndpointPolicyDefinitionsService service;

    /**
     * The service client containing this operation class.
     */
    private NetworkManagementClientImpl client;

    /**
     * Initializes an instance of ServiceEndpointPolicyDefinitionsInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public ServiceEndpointPolicyDefinitionsInner(NetworkManagementClientImpl client) {
        this.service = RestProxy.create(ServiceEndpointPolicyDefinitionsService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * NetworkManagementClientServiceEndpointPolicyDefinitions to be used by
     * the proxy service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "NetworkManagementClientServiceEndpointPolicyDefinitions")
    private interface ServiceEndpointPolicyDefinitionsService {
        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/serviceEndpointPolicies/{serviceEndpointPolicyName}/serviceEndpointPolicyDefinitions/{serviceEndpointPolicyDefinitionName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<Response<Void>> delete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("serviceEndpointPolicyName") String serviceEndpointPolicyName, @PathParam("serviceEndpointPolicyDefinitionName") String serviceEndpointPolicyDefinitionName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/serviceEndpointPolicies/{serviceEndpointPolicyName}/serviceEndpointPolicyDefinitions/{serviceEndpointPolicyDefinitionName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ServiceEndpointPolicyDefinitionInner>> get(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("serviceEndpointPolicyName") String serviceEndpointPolicyName, @PathParam("serviceEndpointPolicyDefinitionName") String serviceEndpointPolicyDefinitionName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/serviceEndpointPolicies/{serviceEndpointPolicyName}/serviceEndpointPolicyDefinitions/{serviceEndpointPolicyDefinitionName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ServiceEndpointPolicyDefinitionInner>> createOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("serviceEndpointPolicyName") String serviceEndpointPolicyName, @PathParam("serviceEndpointPolicyDefinitionName") String serviceEndpointPolicyDefinitionName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") ServiceEndpointPolicyDefinitionInner serviceEndpointPolicyDefinitions, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/serviceEndpointPolicies/{serviceEndpointPolicyName}/serviceEndpointPolicyDefinitions")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ServiceEndpointPolicyDefinitionListResultInner>> listByResourceGroup(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("serviceEndpointPolicyName") String serviceEndpointPolicyName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/serviceEndpointPolicies/{serviceEndpointPolicyName}/serviceEndpointPolicyDefinitions/{serviceEndpointPolicyDefinitionName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<Response<Void>> beginDelete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("serviceEndpointPolicyName") String serviceEndpointPolicyName, @PathParam("serviceEndpointPolicyDefinitionName") String serviceEndpointPolicyDefinitionName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/serviceEndpointPolicies/{serviceEndpointPolicyName}/serviceEndpointPolicyDefinitions/{serviceEndpointPolicyDefinitionName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ServiceEndpointPolicyDefinitionInner>> beginCreateOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("serviceEndpointPolicyName") String serviceEndpointPolicyName, @PathParam("serviceEndpointPolicyDefinitionName") String serviceEndpointPolicyDefinitionName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") ServiceEndpointPolicyDefinitionInner serviceEndpointPolicyDefinitions, @QueryParam("api-version") String apiVersion);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ServiceEndpointPolicyDefinitionListResultInner>> listByResourceGroupNext(@PathParam(value = "nextLink", encoded = true) String nextLink);
    }

    /**
     * Deletes the specified ServiceEndpoint policy definitions.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitionName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> deleteWithResponseAsync(String resourceGroupName, String serviceEndpointPolicyName, String serviceEndpointPolicyDefinitionName) {
        return service.delete(this.client.getHost(), resourceGroupName, serviceEndpointPolicyName, serviceEndpointPolicyDefinitionName, this.client.getSubscriptionId(), this.client.getApiVersion());
    }

    /**
     * Deletes the specified ServiceEndpoint policy definitions.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitionName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteAsync(String resourceGroupName, String serviceEndpointPolicyName, String serviceEndpointPolicyDefinitionName) {
        return deleteWithResponseAsync(resourceGroupName, serviceEndpointPolicyName, serviceEndpointPolicyDefinitionName)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Deletes the specified ServiceEndpoint policy definitions.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitionName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void delete(String resourceGroupName, String serviceEndpointPolicyName, String serviceEndpointPolicyDefinitionName) {
        deleteAsync(resourceGroupName, serviceEndpointPolicyName, serviceEndpointPolicyDefinitionName).block();
    }

    /**
     * Get the specified service endpoint policy definitions from service endpoint policy.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitionName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ServiceEndpointPolicyDefinitionInner>> getWithResponseAsync(String resourceGroupName, String serviceEndpointPolicyName, String serviceEndpointPolicyDefinitionName) {
        return service.get(this.client.getHost(), resourceGroupName, serviceEndpointPolicyName, serviceEndpointPolicyDefinitionName, this.client.getSubscriptionId(), this.client.getApiVersion());
    }

    /**
     * Get the specified service endpoint policy definitions from service endpoint policy.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitionName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ServiceEndpointPolicyDefinitionInner> getAsync(String resourceGroupName, String serviceEndpointPolicyName, String serviceEndpointPolicyDefinitionName) {
        return getWithResponseAsync(resourceGroupName, serviceEndpointPolicyName, serviceEndpointPolicyDefinitionName)
            .flatMap((SimpleResponse<ServiceEndpointPolicyDefinitionInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Get the specified service endpoint policy definitions from service endpoint policy.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitionName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ServiceEndpointPolicyDefinitionInner get(String resourceGroupName, String serviceEndpointPolicyName, String serviceEndpointPolicyDefinitionName) {
        return getAsync(resourceGroupName, serviceEndpointPolicyName, serviceEndpointPolicyDefinitionName).block();
    }

    /**
     * Creates or updates a service endpoint policy definition in the specified service endpoint policy.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitionName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitions Service Endpoint policy definitions.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ServiceEndpointPolicyDefinitionInner>> createOrUpdateWithResponseAsync(String resourceGroupName, String serviceEndpointPolicyName, String serviceEndpointPolicyDefinitionName, ServiceEndpointPolicyDefinitionInner serviceEndpointPolicyDefinitions) {
        return service.createOrUpdate(this.client.getHost(), resourceGroupName, serviceEndpointPolicyName, serviceEndpointPolicyDefinitionName, this.client.getSubscriptionId(), serviceEndpointPolicyDefinitions, this.client.getApiVersion());
    }

    /**
     * Creates or updates a service endpoint policy definition in the specified service endpoint policy.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitionName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitions Service Endpoint policy definitions.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ServiceEndpointPolicyDefinitionInner> createOrUpdateAsync(String resourceGroupName, String serviceEndpointPolicyName, String serviceEndpointPolicyDefinitionName, ServiceEndpointPolicyDefinitionInner serviceEndpointPolicyDefinitions) {
        return createOrUpdateWithResponseAsync(resourceGroupName, serviceEndpointPolicyName, serviceEndpointPolicyDefinitionName, serviceEndpointPolicyDefinitions)
            .flatMap((SimpleResponse<ServiceEndpointPolicyDefinitionInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Creates or updates a service endpoint policy definition in the specified service endpoint policy.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitionName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitions Service Endpoint policy definitions.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ServiceEndpointPolicyDefinitionInner createOrUpdate(String resourceGroupName, String serviceEndpointPolicyName, String serviceEndpointPolicyDefinitionName, ServiceEndpointPolicyDefinitionInner serviceEndpointPolicyDefinitions) {
        return createOrUpdateAsync(resourceGroupName, serviceEndpointPolicyName, serviceEndpointPolicyDefinitionName, serviceEndpointPolicyDefinitions).block();
    }

    /**
     * Gets all service endpoint policy definitions in a service end point policy.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<ServiceEndpointPolicyDefinitionInner>> listByResourceGroupSinglePageAsync(String resourceGroupName, String serviceEndpointPolicyName) {
        return service.listByResourceGroup(this.client.getHost(), resourceGroupName, serviceEndpointPolicyName, this.client.getSubscriptionId(), this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }

    /**
     * Gets all service endpoint policy definitions in a service end point policy.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<ServiceEndpointPolicyDefinitionInner> listByResourceGroupAsync(String resourceGroupName, String serviceEndpointPolicyName) {
        return new PagedFlux<>(
            () -> listByResourceGroupSinglePageAsync(resourceGroupName, serviceEndpointPolicyName),
            nextLink -> listByResourceGroupNextSinglePageAsync(nextLink));
    }

    /**
     * Gets all service endpoint policy definitions in a service end point policy.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<ServiceEndpointPolicyDefinitionInner> listByResourceGroup(String resourceGroupName, String serviceEndpointPolicyName) {
        return new PagedIterable<>(listByResourceGroupAsync(resourceGroupName, serviceEndpointPolicyName));
    }

    /**
     * Deletes the specified ServiceEndpoint policy definitions.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitionName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> beginDeleteWithResponseAsync(String resourceGroupName, String serviceEndpointPolicyName, String serviceEndpointPolicyDefinitionName) {
        return service.beginDelete(this.client.getHost(), resourceGroupName, serviceEndpointPolicyName, serviceEndpointPolicyDefinitionName, this.client.getSubscriptionId(), this.client.getApiVersion());
    }

    /**
     * Deletes the specified ServiceEndpoint policy definitions.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitionName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> beginDeleteAsync(String resourceGroupName, String serviceEndpointPolicyName, String serviceEndpointPolicyDefinitionName) {
        return beginDeleteWithResponseAsync(resourceGroupName, serviceEndpointPolicyName, serviceEndpointPolicyDefinitionName)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Deletes the specified ServiceEndpoint policy definitions.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitionName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void beginDelete(String resourceGroupName, String serviceEndpointPolicyName, String serviceEndpointPolicyDefinitionName) {
        beginDeleteAsync(resourceGroupName, serviceEndpointPolicyName, serviceEndpointPolicyDefinitionName).block();
    }

    /**
     * Creates or updates a service endpoint policy definition in the specified service endpoint policy.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitionName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitions Service Endpoint policy definitions.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ServiceEndpointPolicyDefinitionInner>> beginCreateOrUpdateWithResponseAsync(String resourceGroupName, String serviceEndpointPolicyName, String serviceEndpointPolicyDefinitionName, ServiceEndpointPolicyDefinitionInner serviceEndpointPolicyDefinitions) {
        return service.beginCreateOrUpdate(this.client.getHost(), resourceGroupName, serviceEndpointPolicyName, serviceEndpointPolicyDefinitionName, this.client.getSubscriptionId(), serviceEndpointPolicyDefinitions, this.client.getApiVersion());
    }

    /**
     * Creates or updates a service endpoint policy definition in the specified service endpoint policy.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitionName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitions Service Endpoint policy definitions.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ServiceEndpointPolicyDefinitionInner> beginCreateOrUpdateAsync(String resourceGroupName, String serviceEndpointPolicyName, String serviceEndpointPolicyDefinitionName, ServiceEndpointPolicyDefinitionInner serviceEndpointPolicyDefinitions) {
        return beginCreateOrUpdateWithResponseAsync(resourceGroupName, serviceEndpointPolicyName, serviceEndpointPolicyDefinitionName, serviceEndpointPolicyDefinitions)
            .flatMap((SimpleResponse<ServiceEndpointPolicyDefinitionInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Creates or updates a service endpoint policy definition in the specified service endpoint policy.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitionName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param serviceEndpointPolicyDefinitions Service Endpoint policy definitions.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ServiceEndpointPolicyDefinitionInner beginCreateOrUpdate(String resourceGroupName, String serviceEndpointPolicyName, String serviceEndpointPolicyDefinitionName, ServiceEndpointPolicyDefinitionInner serviceEndpointPolicyDefinitions) {
        return beginCreateOrUpdateAsync(resourceGroupName, serviceEndpointPolicyName, serviceEndpointPolicyDefinitionName, serviceEndpointPolicyDefinitions).block();
    }

    /**
     * Get the next page of items.
     * 
     * @param nextLink null
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<ServiceEndpointPolicyDefinitionInner>> listByResourceGroupNextSinglePageAsync(String nextLink) {
        return service.listByResourceGroupNext(nextLink).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }
}
