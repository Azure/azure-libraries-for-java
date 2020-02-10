// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.models;

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
 * AvailableResourceGroupDelegations.
 */
public final class AvailableResourceGroupDelegationsInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private AvailableResourceGroupDelegationsService service;

    /**
     * The service client containing this operation class.
     */
    private NetworkManagementClientImpl client;

    /**
     * Initializes an instance of AvailableResourceGroupDelegationsInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public AvailableResourceGroupDelegationsInner(NetworkManagementClientImpl client) {
        this.service = RestProxy.create(AvailableResourceGroupDelegationsService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * NetworkManagementClientAvailableResourceGroupDelegations to be used by
     * the proxy service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "NetworkManagementClientAvailableResourceGroupDelegations")
    private interface AvailableResourceGroupDelegationsService {
        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/locations/{location}/availableDelegations")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<AvailableDelegationsResultInner>> list(@HostParam("$host") String host, @PathParam("location") String location, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<AvailableDelegationsResultInner>> listNext(@PathParam(value = "nextLink", encoded = true) String nextLink);
    }

    /**
     * Gets all of the available subnet delegations for this resource group in this region.
     * 
     * @param location 
     * @param resourceGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<AvailableDelegationInner>> listSinglePageAsync(String location, String resourceGroupName) {
        final String apiVersion = "2019-06-01";
        return service.list(this.client.getHost(), location, resourceGroupName, this.client.getSubscriptionId(), apiVersion).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().value(),
            res.getValue().nextLink(),
            null));
    }

    /**
     * Gets all of the available subnet delegations for this resource group in this region.
     * 
     * @param location 
     * @param resourceGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<AvailableDelegationInner> listAsync(String location, String resourceGroupName) {
        return new PagedFlux<>(
            () -> listSinglePageAsync(location, resourceGroupName),
            nextLink -> listNextSinglePageAsync(nextLink));
    }

    /**
     * Gets all of the available subnet delegations for this resource group in this region.
     * 
     * @param location 
     * @param resourceGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<AvailableDelegationInner> list(String location, String resourceGroupName) {
        return new PagedIterable<>(listAsync(location, resourceGroupName));
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
    public Mono<PagedResponse<AvailableDelegationInner>> listNextSinglePageAsync(String nextLink) {
        return service.listNext(nextLink).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().value(),
            res.getValue().nextLink(),
            null));
    }
}
