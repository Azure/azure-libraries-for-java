// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.model;

import com.azure.core.annotation.ExpectedResponses;
import com.azure.core.annotation.Get;
import com.azure.core.annotation.Host;
import com.azure.core.annotation.HostParam;
import com.azure.core.annotation.PathParam;
import com.azure.core.annotation.QueryParam;
import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceInterface;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.annotation.UnexpectedResponseExceptionType;
import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.PagedResponse;
import com.azure.core.http.rest.PagedResponseBase;
import com.azure.core.http.rest.RestProxy;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.management.CloudException;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * AvailablePrivateEndpointTypes.
 */
public final class AvailablePrivateEndpointTypesInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private AvailablePrivateEndpointTypesService service;

    /**
     * The service client containing this operation class.
     */
    private NetworkManagementClientImpl client;

    /**
     * Initializes an instance of AvailablePrivateEndpointTypesInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public AvailablePrivateEndpointTypesInner(NetworkManagementClientImpl client) {
        this.service = RestProxy.create(AvailablePrivateEndpointTypesService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * NetworkManagementClientAvailablePrivateEndpointTypes to be used by the
     * proxy service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "NetworkManagementClientAvailablePrivateEndpointTypes")
    private interface AvailablePrivateEndpointTypesService {
        @Get("/subscriptions/{subscriptionId}/providers/Microsoft.Network/locations/{location}/availablePrivateEndpointTypes")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<AvailablePrivateEndpointTypesResultInner>> list(@HostParam("$host") String host, @PathParam("location") String location, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/locations/{location}/availablePrivateEndpointTypes")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<AvailablePrivateEndpointTypesResultInner>> listByResourceGroup(@HostParam("$host") String host, @PathParam("location") String location, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<AvailablePrivateEndpointTypesResultInner>> listNext(@PathParam(value = "nextLink", encoded = true) String nextLink);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<AvailablePrivateEndpointTypesResultInner>> listByResourceGroupNext(@PathParam(value = "nextLink", encoded = true) String nextLink);
    }

    /**
     * Returns all of the resource types that can be linked to a Private Endpoint in this subscription in this region.
     * 
     * @param location MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<AvailablePrivateEndpointTypeInner>> listSinglePageAsync(String location) {
        return service.list(this.client.getHost(), location, this.client.getSubscriptionId(), this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }

    /**
     * Returns all of the resource types that can be linked to a Private Endpoint in this subscription in this region.
     * 
     * @param location MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<AvailablePrivateEndpointTypeInner> listAsync(String location) {
        return new PagedFlux<>(
            () -> listSinglePageAsync(location),
            nextLink -> listNextSinglePageAsync(nextLink));
    }

    /**
     * Returns all of the resource types that can be linked to a Private Endpoint in this subscription in this region.
     * 
     * @param location MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<AvailablePrivateEndpointTypeInner> list(String location) {
        return new PagedIterable<>(listAsync(location));
    }

    /**
     * Returns all of the resource types that can be linked to a Private Endpoint in this subscription in this region.
     * 
     * @param location MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<AvailablePrivateEndpointTypeInner>> listByResourceGroupSinglePageAsync(String location, String resourceGroupName) {
        return service.listByResourceGroup(this.client.getHost(), location, resourceGroupName, this.client.getSubscriptionId(), this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }

    /**
     * Returns all of the resource types that can be linked to a Private Endpoint in this subscription in this region.
     * 
     * @param location MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<AvailablePrivateEndpointTypeInner> listByResourceGroupAsync(String location, String resourceGroupName) {
        return new PagedFlux<>(
            () -> listByResourceGroupSinglePageAsync(location, resourceGroupName),
            nextLink -> listByResourceGroupNextSinglePageAsync(nextLink));
    }

    /**
     * Returns all of the resource types that can be linked to a Private Endpoint in this subscription in this region.
     * 
     * @param location MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<AvailablePrivateEndpointTypeInner> listByResourceGroup(String location, String resourceGroupName) {
        return new PagedIterable<>(listByResourceGroupAsync(location, resourceGroupName));
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
    public Mono<PagedResponse<AvailablePrivateEndpointTypeInner>> listNextSinglePageAsync(String nextLink) {
        return service.listNext(nextLink).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
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
    public Mono<PagedResponse<AvailablePrivateEndpointTypeInner>> listByResourceGroupNextSinglePageAsync(String nextLink) {
        return service.listByResourceGroupNext(nextLink).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }
}
