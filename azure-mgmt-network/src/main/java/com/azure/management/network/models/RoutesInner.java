// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.models;

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
 * Routes.
 */
public final class RoutesInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private RoutesService service;

    /**
     * The service client containing this operation class.
     */
    private NetworkManagementClientImpl client;

    /**
     * Initializes an instance of RoutesInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public RoutesInner(NetworkManagementClientImpl client) {
        this.service = RestProxy.create(RoutesService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * NetworkManagementClientRoutes to be used by the proxy service to perform
     * REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "NetworkManagementClientRoutes")
    private interface RoutesService {
        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/routeTables/{routeTableName}/routes/{routeName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<Response<Void>> delete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("routeTableName") String routeTableName, @PathParam("routeName") String routeName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/routeTables/{routeTableName}/routes/{routeName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<RouteInner>> get(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("routeTableName") String routeTableName, @PathParam("routeName") String routeName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/routeTables/{routeTableName}/routes/{routeName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<RouteInner>> createOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("routeTableName") String routeTableName, @PathParam("routeName") String routeName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") RouteInner routeParameters, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/routeTables/{routeTableName}/routes")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<RouteListResultInner>> list(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("routeTableName") String routeTableName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/routeTables/{routeTableName}/routes/{routeName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<Response<Void>> beginDelete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("routeTableName") String routeTableName, @PathParam("routeName") String routeName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/routeTables/{routeTableName}/routes/{routeName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<RouteInner>> beginCreateOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("routeTableName") String routeTableName, @PathParam("routeName") String routeName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") RouteInner routeParameters, @QueryParam("api-version") String apiVersion);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<RouteListResultInner>> listNext(@PathParam(value = "nextLink", encoded = true) String nextLink);
    }

    /**
     * Deletes the specified route from a route table.
     * 
     * @param resourceGroupName 
     * @param routeTableName 
     * @param routeName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> deleteWithResponseAsync(String resourceGroupName, String routeTableName, String routeName) {
        return service.delete(this.client.getHost(), resourceGroupName, routeTableName, routeName, this.client.getSubscriptionId(), this.client.getApiVersion());
    }

    /**
     * Deletes the specified route from a route table.
     * 
     * @param resourceGroupName 
     * @param routeTableName 
     * @param routeName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteAsync(String resourceGroupName, String routeTableName, String routeName) {
        return deleteWithResponseAsync(resourceGroupName, routeTableName, routeName)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Deletes the specified route from a route table.
     * 
     * @param resourceGroupName 
     * @param routeTableName 
     * @param routeName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void delete(String resourceGroupName, String routeTableName, String routeName) {
        deleteAsync(resourceGroupName, routeTableName, routeName).block();
    }

    /**
     * Gets the specified route from a route table.
     * 
     * @param resourceGroupName 
     * @param routeTableName 
     * @param routeName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<RouteInner>> getWithResponseAsync(String resourceGroupName, String routeTableName, String routeName) {
        return service.get(this.client.getHost(), resourceGroupName, routeTableName, routeName, this.client.getSubscriptionId(), this.client.getApiVersion());
    }

    /**
     * Gets the specified route from a route table.
     * 
     * @param resourceGroupName 
     * @param routeTableName 
     * @param routeName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<RouteInner> getAsync(String resourceGroupName, String routeTableName, String routeName) {
        return getWithResponseAsync(resourceGroupName, routeTableName, routeName)
            .flatMap((SimpleResponse<RouteInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Gets the specified route from a route table.
     * 
     * @param resourceGroupName 
     * @param routeTableName 
     * @param routeName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public RouteInner get(String resourceGroupName, String routeTableName, String routeName) {
        return getAsync(resourceGroupName, routeTableName, routeName).block();
    }

    /**
     * Creates or updates a route in the specified route table.
     * 
     * @param resourceGroupName 
     * @param routeTableName 
     * @param routeName 
     * @param routeParameters Route resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<RouteInner>> createOrUpdateWithResponseAsync(String resourceGroupName, String routeTableName, String routeName, RouteInner routeParameters) {
        return service.createOrUpdate(this.client.getHost(), resourceGroupName, routeTableName, routeName, this.client.getSubscriptionId(), routeParameters, this.client.getApiVersion());
    }

    /**
     * Creates or updates a route in the specified route table.
     * 
     * @param resourceGroupName 
     * @param routeTableName 
     * @param routeName 
     * @param routeParameters Route resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<RouteInner> createOrUpdateAsync(String resourceGroupName, String routeTableName, String routeName, RouteInner routeParameters) {
        return createOrUpdateWithResponseAsync(resourceGroupName, routeTableName, routeName, routeParameters)
            .flatMap((SimpleResponse<RouteInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Creates or updates a route in the specified route table.
     * 
     * @param resourceGroupName 
     * @param routeTableName 
     * @param routeName 
     * @param routeParameters Route resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public RouteInner createOrUpdate(String resourceGroupName, String routeTableName, String routeName, RouteInner routeParameters) {
        return createOrUpdateAsync(resourceGroupName, routeTableName, routeName, routeParameters).block();
    }

    /**
     * Gets all routes in a route table.
     * 
     * @param resourceGroupName 
     * @param routeTableName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<RouteInner>> listSinglePageAsync(String resourceGroupName, String routeTableName) {
        return service.list(this.client.getHost(), resourceGroupName, routeTableName, this.client.getSubscriptionId(), this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }

    /**
     * Gets all routes in a route table.
     * 
     * @param resourceGroupName 
     * @param routeTableName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<RouteInner> listAsync(String resourceGroupName, String routeTableName) {
        return new PagedFlux<>(
            () -> listSinglePageAsync(resourceGroupName, routeTableName),
            nextLink -> listNextSinglePageAsync(nextLink));
    }

    /**
     * Gets all routes in a route table.
     * 
     * @param resourceGroupName 
     * @param routeTableName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<RouteInner> list(String resourceGroupName, String routeTableName) {
        return new PagedIterable<>(listAsync(resourceGroupName, routeTableName));
    }

    /**
     * Deletes the specified route from a route table.
     * 
     * @param resourceGroupName 
     * @param routeTableName 
     * @param routeName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> beginDeleteWithResponseAsync(String resourceGroupName, String routeTableName, String routeName) {
        return service.beginDelete(this.client.getHost(), resourceGroupName, routeTableName, routeName, this.client.getSubscriptionId(), this.client.getApiVersion());
    }

    /**
     * Deletes the specified route from a route table.
     * 
     * @param resourceGroupName 
     * @param routeTableName 
     * @param routeName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> beginDeleteAsync(String resourceGroupName, String routeTableName, String routeName) {
        return beginDeleteWithResponseAsync(resourceGroupName, routeTableName, routeName)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Deletes the specified route from a route table.
     * 
     * @param resourceGroupName 
     * @param routeTableName 
     * @param routeName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void beginDelete(String resourceGroupName, String routeTableName, String routeName) {
        beginDeleteAsync(resourceGroupName, routeTableName, routeName).block();
    }

    /**
     * Creates or updates a route in the specified route table.
     * 
     * @param resourceGroupName 
     * @param routeTableName 
     * @param routeName 
     * @param routeParameters Route resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<RouteInner>> beginCreateOrUpdateWithResponseAsync(String resourceGroupName, String routeTableName, String routeName, RouteInner routeParameters) {
        return service.beginCreateOrUpdate(this.client.getHost(), resourceGroupName, routeTableName, routeName, this.client.getSubscriptionId(), routeParameters, this.client.getApiVersion());
    }

    /**
     * Creates or updates a route in the specified route table.
     * 
     * @param resourceGroupName 
     * @param routeTableName 
     * @param routeName 
     * @param routeParameters Route resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<RouteInner> beginCreateOrUpdateAsync(String resourceGroupName, String routeTableName, String routeName, RouteInner routeParameters) {
        return beginCreateOrUpdateWithResponseAsync(resourceGroupName, routeTableName, routeName, routeParameters)
            .flatMap((SimpleResponse<RouteInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Creates or updates a route in the specified route table.
     * 
     * @param resourceGroupName 
     * @param routeTableName 
     * @param routeName 
     * @param routeParameters Route resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public RouteInner beginCreateOrUpdate(String resourceGroupName, String routeTableName, String routeName, RouteInner routeParameters) {
        return beginCreateOrUpdateAsync(resourceGroupName, routeTableName, routeName, routeParameters).block();
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
    public Mono<PagedResponse<RouteInner>> listNextSinglePageAsync(String nextLink) {
        return service.listNext(nextLink).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }
}
