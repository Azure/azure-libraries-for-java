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
import com.azure.management.resources.fluentcore.collection.InnerSupportsDelete;
import com.azure.management.resources.fluentcore.collection.InnerSupportsGet;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * PrivateEndpoints.
 */
public final class PrivateEndpointsInner implements InnerSupportsGet<PrivateEndpointInner>, InnerSupportsDelete<Void> {
    /**
     * The proxy service used to perform REST calls.
     */
    private PrivateEndpointsService service;

    /**
     * The service client containing this operation class.
     */
    private NetworkManagementClientImpl client;

    /**
     * Initializes an instance of PrivateEndpointsInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public PrivateEndpointsInner(NetworkManagementClientImpl client) {
        this.service = RestProxy.create(PrivateEndpointsService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * NetworkManagementClientPrivateEndpoints to be used by the proxy service
     * to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "NetworkManagementClientPrivateEndpoints")
    private interface PrivateEndpointsService {
        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/privateEndpoints/{privateEndpointName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<Response<Void>> delete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("privateEndpointName") String privateEndpointName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/privateEndpoints/{privateEndpointName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<PrivateEndpointInner>> getByResourceGroup(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("privateEndpointName") String privateEndpointName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("$expand") String expand, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/privateEndpoints/{privateEndpointName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<PrivateEndpointInner>> createOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("privateEndpointName") String privateEndpointName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") PrivateEndpointInner parameters, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/privateEndpoints")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<PrivateEndpointListResultInner>> listByResourceGroup(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/providers/Microsoft.Network/privateEndpoints")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<PrivateEndpointListResultInner>> listBySubscription(@HostParam("$host") String host, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/privateEndpoints/{privateEndpointName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<Response<Void>> beginDelete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("privateEndpointName") String privateEndpointName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/privateEndpoints/{privateEndpointName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<PrivateEndpointInner>> beginCreateOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("privateEndpointName") String privateEndpointName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") PrivateEndpointInner parameters, @QueryParam("api-version") String apiVersion);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<PrivateEndpointListResultInner>> listNext(@PathParam(value = "nextLink", encoded = true) String nextLink);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<PrivateEndpointListResultInner>> listBySubscriptionNext(@PathParam(value = "nextLink", encoded = true) String nextLink);
    }

    /**
     * Deletes the specified private endpoint.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param privateEndpointName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> deleteWithResponseAsync(String resourceGroupName, String privateEndpointName) {
        return service.delete(this.client.getHost(), resourceGroupName, privateEndpointName, this.client.getSubscriptionId(), this.client.getApiVersion());
    }

    /**
     * Deletes the specified private endpoint.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param privateEndpointName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteAsync(String resourceGroupName, String privateEndpointName) {
        return deleteWithResponseAsync(resourceGroupName, privateEndpointName)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Deletes the specified private endpoint.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param privateEndpointName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void delete(String resourceGroupName, String privateEndpointName) {
        deleteAsync(resourceGroupName, privateEndpointName).block();
    }

    /**
     * Gets the specified private endpoint by resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param privateEndpointName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param expand MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<PrivateEndpointInner>> getByResourceGroupWithResponseAsync(String resourceGroupName, String privateEndpointName, String expand) {
        return service.getByResourceGroup(this.client.getHost(), resourceGroupName, privateEndpointName, this.client.getSubscriptionId(), expand, this.client.getApiVersion());
    }

    /**
     * Gets the specified private endpoint by resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param privateEndpointName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param expand MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PrivateEndpointInner> getByResourceGroupAsync(String resourceGroupName, String privateEndpointName, String expand) {
        return getByResourceGroupWithResponseAsync(resourceGroupName, privateEndpointName, expand)
            .flatMap((SimpleResponse<PrivateEndpointInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Gets the specified private endpoint by resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param privateEndpointName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param expand MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public PrivateEndpointInner getByResourceGroup(String resourceGroupName, String privateEndpointName, String expand) {
        return getByResourceGroupAsync(resourceGroupName, privateEndpointName, expand).block();
    }

    /**
     * Creates or updates an private endpoint in the specified resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param privateEndpointName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param parameters Private endpoint resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<PrivateEndpointInner>> createOrUpdateWithResponseAsync(String resourceGroupName, String privateEndpointName, PrivateEndpointInner parameters) {
        return service.createOrUpdate(this.client.getHost(), resourceGroupName, privateEndpointName, this.client.getSubscriptionId(), parameters, this.client.getApiVersion());
    }

    /**
     * Creates or updates an private endpoint in the specified resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param privateEndpointName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param parameters Private endpoint resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PrivateEndpointInner> createOrUpdateAsync(String resourceGroupName, String privateEndpointName, PrivateEndpointInner parameters) {
        return createOrUpdateWithResponseAsync(resourceGroupName, privateEndpointName, parameters)
            .flatMap((SimpleResponse<PrivateEndpointInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Creates or updates an private endpoint in the specified resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param privateEndpointName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param parameters Private endpoint resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public PrivateEndpointInner createOrUpdate(String resourceGroupName, String privateEndpointName, PrivateEndpointInner parameters) {
        return createOrUpdateAsync(resourceGroupName, privateEndpointName, parameters).block();
    }

    /**
     * Gets all private endpoints in a resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<PrivateEndpointInner>> listByResourceGroupSinglePageAsync(String resourceGroupName) {
        return service.listByResourceGroup(this.client.getHost(), resourceGroupName, this.client.getSubscriptionId(), this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }

    /**
     * Gets all private endpoints in a resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<PrivateEndpointInner> listByResourceGroupAsync(String resourceGroupName) {
        return new PagedFlux<>(
            () -> listByResourceGroupSinglePageAsync(resourceGroupName),
            nextLink -> listNextSinglePageAsync(nextLink));
    }

    /**
     * Gets all private endpoints in a resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<PrivateEndpointInner> listByResourceGroup(String resourceGroupName) {
        return new PagedIterable<>(listByResourceGroupAsync(resourceGroupName));
    }

    /**
     * Gets all private endpoints in a subscription.
     * 
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<PrivateEndpointInner>> listBySubscriptionSinglePageAsync() {
        return service.listBySubscription(this.client.getHost(), this.client.getSubscriptionId(), this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }

    /**
     * Gets all private endpoints in a subscription.
     * 
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<PrivateEndpointInner> listBySubscriptionAsync() {
        return new PagedFlux<>(
            () -> listBySubscriptionSinglePageAsync(),
            nextLink -> listBySubscriptionNextSinglePageAsync(nextLink));
    }

    /**
     * Gets all private endpoints in a subscription.
     * 
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<PrivateEndpointInner> listBySubscription() {
        return new PagedIterable<>(listBySubscriptionAsync());
    }

    /**
     * Deletes the specified private endpoint.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param privateEndpointName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> beginDeleteWithResponseAsync(String resourceGroupName, String privateEndpointName) {
        return service.beginDelete(this.client.getHost(), resourceGroupName, privateEndpointName, this.client.getSubscriptionId(), this.client.getApiVersion());
    }

    /**
     * Deletes the specified private endpoint.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param privateEndpointName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> beginDeleteAsync(String resourceGroupName, String privateEndpointName) {
        return beginDeleteWithResponseAsync(resourceGroupName, privateEndpointName)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Deletes the specified private endpoint.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param privateEndpointName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void beginDelete(String resourceGroupName, String privateEndpointName) {
        beginDeleteAsync(resourceGroupName, privateEndpointName).block();
    }

    /**
     * Creates or updates an private endpoint in the specified resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param privateEndpointName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param parameters Private endpoint resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<PrivateEndpointInner>> beginCreateOrUpdateWithResponseAsync(String resourceGroupName, String privateEndpointName, PrivateEndpointInner parameters) {
        return service.beginCreateOrUpdate(this.client.getHost(), resourceGroupName, privateEndpointName, this.client.getSubscriptionId(), parameters, this.client.getApiVersion());
    }

    /**
     * Creates or updates an private endpoint in the specified resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param privateEndpointName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param parameters Private endpoint resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PrivateEndpointInner> beginCreateOrUpdateAsync(String resourceGroupName, String privateEndpointName, PrivateEndpointInner parameters) {
        return beginCreateOrUpdateWithResponseAsync(resourceGroupName, privateEndpointName, parameters)
            .flatMap((SimpleResponse<PrivateEndpointInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Creates or updates an private endpoint in the specified resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param privateEndpointName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param parameters Private endpoint resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public PrivateEndpointInner beginCreateOrUpdate(String resourceGroupName, String privateEndpointName, PrivateEndpointInner parameters) {
        return beginCreateOrUpdateAsync(resourceGroupName, privateEndpointName, parameters).block();
    }

    /**
     * Get the next page of items.
     * 
     * @param nextLink null
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<PrivateEndpointInner>> listNextSinglePageAsync(String nextLink) {
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
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<PrivateEndpointInner>> listBySubscriptionNextSinglePageAsync(String nextLink) {
        return service.listBySubscriptionNext(nextLink).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }
}
