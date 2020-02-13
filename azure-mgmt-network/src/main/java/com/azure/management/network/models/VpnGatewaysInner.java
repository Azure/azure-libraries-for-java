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
import com.azure.core.annotation.Patch;
import com.azure.core.annotation.PathParam;
import com.azure.core.annotation.Post;
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
import com.azure.core.util.polling.AsyncPollResponse;
import com.azure.management.network.ErrorException;
import com.azure.management.network.TagsObject;
import com.azure.management.resources.fluentcore.collection.InnerSupportsDelete;
import com.azure.management.resources.fluentcore.collection.InnerSupportsGet;
import com.azure.management.resources.fluentcore.collection.InnerSupportsListing;
import java.nio.ByteBuffer;
import java.util.Map;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * VpnGateways.
 */
public final class VpnGatewaysInner implements InnerSupportsGet<VpnGatewayInner>, InnerSupportsListing<VpnGatewayInner>, InnerSupportsDelete<Void> {
    /**
     * The proxy service used to perform REST calls.
     */
    private VpnGatewaysService service;

    /**
     * The service client containing this operation class.
     */
    private NetworkManagementClientImpl client;

    /**
     * Initializes an instance of VpnGatewaysInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public VpnGatewaysInner(NetworkManagementClientImpl client) {
        this.service = RestProxy.create(VpnGatewaysService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * NetworkManagementClientVpnGateways to be used by the proxy service to
     * perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "NetworkManagementClientVpnGateways")
    private interface VpnGatewaysService {
        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/vpnGateways/{gatewayName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<VpnGatewayInner>> getByResourceGroup(@HostParam("$host") String host, @PathParam("subscriptionId") String subscriptionId, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("gatewayName") String gatewayName, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/vpnGateways/{gatewayName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<Flux<ByteBuffer>>> createOrUpdate(@HostParam("$host") String host, @PathParam("subscriptionId") String subscriptionId, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("gatewayName") String gatewayName, @BodyParam("application/json") VpnGatewayInner vpnGatewayParameters, @QueryParam("api-version") String apiVersion);

        @Patch("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/vpnGateways/{gatewayName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<Flux<ByteBuffer>>> updateTags(@HostParam("$host") String host, @PathParam("subscriptionId") String subscriptionId, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("gatewayName") String gatewayName, @BodyParam("application/json") TagsObject vpnGatewayParameters, @QueryParam("api-version") String apiVersion);

        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/vpnGateways/{gatewayName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<Flux<ByteBuffer>>> delete(@HostParam("$host") String host, @PathParam("subscriptionId") String subscriptionId, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("gatewayName") String gatewayName, @QueryParam("api-version") String apiVersion);

        @Post("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/vpnGateways/{gatewayName}/reset")
        @ExpectedResponses({200, 202})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<Flux<ByteBuffer>>> reset(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("gatewayName") String gatewayName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/vpnGateways")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<ListVpnGatewaysResultInner>> listByResourceGroup(@HostParam("$host") String host, @PathParam("subscriptionId") String subscriptionId, @PathParam("resourceGroupName") String resourceGroupName, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/providers/Microsoft.Network/vpnGateways")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<ListVpnGatewaysResultInner>> list(@HostParam("$host") String host, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/vpnGateways/{gatewayName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<VpnGatewayInner>> beginCreateOrUpdate(@HostParam("$host") String host, @PathParam("subscriptionId") String subscriptionId, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("gatewayName") String gatewayName, @BodyParam("application/json") VpnGatewayInner vpnGatewayParameters, @QueryParam("api-version") String apiVersion);

        @Patch("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/vpnGateways/{gatewayName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<VpnGatewayInner>> beginUpdateTags(@HostParam("$host") String host, @PathParam("subscriptionId") String subscriptionId, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("gatewayName") String gatewayName, @BodyParam("application/json") TagsObject vpnGatewayParameters, @QueryParam("api-version") String apiVersion);

        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/vpnGateways/{gatewayName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<Response<Void>> beginDelete(@HostParam("$host") String host, @PathParam("subscriptionId") String subscriptionId, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("gatewayName") String gatewayName, @QueryParam("api-version") String apiVersion);

        @Post("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/vpnGateways/{gatewayName}/reset")
        @ExpectedResponses({200, 202})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<VpnGatewayInner>> beginReset(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("gatewayName") String gatewayName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<ListVpnGatewaysResultInner>> listByResourceGroupNext(@PathParam(value = "nextLink", encoded = true) String nextLink);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<ListVpnGatewaysResultInner>> listNext(@PathParam(value = "nextLink", encoded = true) String nextLink);
    }

    /**
     * Retrieves the details of a virtual wan vpn gateway.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<VpnGatewayInner>> getByResourceGroupWithResponseAsync(String resourceGroupName, String gatewayName) {
        final String apiVersion = "2019-06-01";
        return service.getByResourceGroup(this.client.getHost(), this.client.getSubscriptionId(), resourceGroupName, gatewayName, apiVersion);
    }

    /**
     * Retrieves the details of a virtual wan vpn gateway.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VpnGatewayInner> getByResourceGroupAsync(String resourceGroupName, String gatewayName) {
        return getByResourceGroupWithResponseAsync(resourceGroupName, gatewayName)
            .flatMap((SimpleResponse<VpnGatewayInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Retrieves the details of a virtual wan vpn gateway.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public VpnGatewayInner getByResourceGroup(String resourceGroupName, String gatewayName) {
        return getByResourceGroupAsync(resourceGroupName, gatewayName).block();
    }

    /**
     * Creates a virtual wan vpn gateway if it doesn't exist else updates the existing gateway.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @param vpnGatewayParameters VpnGateway Resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Flux<ByteBuffer>>> createOrUpdateWithResponseAsync(String resourceGroupName, String gatewayName, VpnGatewayInner vpnGatewayParameters) {
        final String apiVersion = "2019-06-01";
        return service.createOrUpdate(this.client.getHost(), this.client.getSubscriptionId(), resourceGroupName, gatewayName, vpnGatewayParameters, apiVersion);
    }

    /**
     * Creates a virtual wan vpn gateway if it doesn't exist else updates the existing gateway.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @param vpnGatewayParameters VpnGateway Resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VpnGatewayInner> createOrUpdateAsync(String resourceGroupName, String gatewayName, VpnGatewayInner vpnGatewayParameters) {
        Mono<SimpleResponse<Flux<ByteBuffer>>> response = createOrUpdateWithResponseAsync(resourceGroupName, gatewayName, vpnGatewayParameters);
        return client.<VpnGatewayInner, VpnGatewayInner>getLroResultAsync(response, client.getHttpPipeline(), VpnGatewayInner.class, VpnGatewayInner.class)
            .last()
            .flatMap(AsyncPollResponse::getFinalResult);
    }

    /**
     * Creates a virtual wan vpn gateway if it doesn't exist else updates the existing gateway.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @param vpnGatewayParameters VpnGateway Resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public VpnGatewayInner createOrUpdate(String resourceGroupName, String gatewayName, VpnGatewayInner vpnGatewayParameters) {
        return createOrUpdateAsync(resourceGroupName, gatewayName, vpnGatewayParameters).block();
    }

    /**
     * Updates virtual wan vpn gateway tags.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Flux<ByteBuffer>>> updateTagsWithResponseAsync(String resourceGroupName, String gatewayName, Map<String, String> tags) {
        final String apiVersion = "2019-06-01";
        TagsObject vpnGatewayParameters = new TagsObject();
        vpnGatewayParameters.withTags(tags);
        return service.updateTags(this.client.getHost(), this.client.getSubscriptionId(), resourceGroupName, gatewayName, vpnGatewayParameters, apiVersion);
    }

    /**
     * Updates virtual wan vpn gateway tags.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VpnGatewayInner> updateTagsAsync(String resourceGroupName, String gatewayName, Map<String, String> tags) {
        Mono<SimpleResponse<Flux<ByteBuffer>>> response = updateTagsWithResponseAsync(resourceGroupName, gatewayName, tags);
        return client.<VpnGatewayInner, VpnGatewayInner>getLroResultAsync(response, client.getHttpPipeline(), VpnGatewayInner.class, VpnGatewayInner.class)
            .last()
            .flatMap(AsyncPollResponse::getFinalResult);
    }

    /**
     * Updates virtual wan vpn gateway tags.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public VpnGatewayInner updateTags(String resourceGroupName, String gatewayName, Map<String, String> tags) {
        return updateTagsAsync(resourceGroupName, gatewayName, tags).block();
    }

    /**
     * Deletes a virtual wan vpn gateway.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Flux<ByteBuffer>>> deleteWithResponseAsync(String resourceGroupName, String gatewayName) {
        final String apiVersion = "2019-06-01";
        return service.delete(this.client.getHost(), this.client.getSubscriptionId(), resourceGroupName, gatewayName, apiVersion);
    }

    /**
     * Deletes a virtual wan vpn gateway.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteAsync(String resourceGroupName, String gatewayName) {
        Mono<SimpleResponse<Flux<ByteBuffer>>> response = deleteWithResponseAsync(resourceGroupName, gatewayName);
        return client.<Void, Void>getLroResultAsync(response, client.getHttpPipeline(), Void.class, Void.class)
            .last()
            .flatMap(AsyncPollResponse::getFinalResult);
    }

    /**
     * Deletes a virtual wan vpn gateway.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void delete(String resourceGroupName, String gatewayName) {
        deleteAsync(resourceGroupName, gatewayName).block();
    }

    /**
     * Resets the primary of the vpn gateway in the specified resource group.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Flux<ByteBuffer>>> resetWithResponseAsync(String resourceGroupName, String gatewayName) {
        final String apiVersion = "2019-06-01";
        return service.reset(this.client.getHost(), resourceGroupName, gatewayName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Resets the primary of the vpn gateway in the specified resource group.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VpnGatewayInner> resetAsync(String resourceGroupName, String gatewayName) {
        Mono<SimpleResponse<Flux<ByteBuffer>>> response = resetWithResponseAsync(resourceGroupName, gatewayName);
        return client.<VpnGatewayInner, VpnGatewayInner>getLroResultAsync(response, client.getHttpPipeline(), VpnGatewayInner.class, VpnGatewayInner.class)
            .last()
            .flatMap(AsyncPollResponse::getFinalResult);
    }

    /**
     * Resets the primary of the vpn gateway in the specified resource group.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public VpnGatewayInner reset(String resourceGroupName, String gatewayName) {
        return resetAsync(resourceGroupName, gatewayName).block();
    }

    /**
     * Lists all the VpnGateways in a resource group.
     * 
     * @param resourceGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<VpnGatewayInner>> listByResourceGroupSinglePageAsync(String resourceGroupName) {
        final String apiVersion = "2019-06-01";
        return service.listByResourceGroup(this.client.getHost(), this.client.getSubscriptionId(), resourceGroupName, apiVersion).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().value(),
            res.getValue().nextLink(),
            null));
    }

    /**
     * Lists all the VpnGateways in a resource group.
     * 
     * @param resourceGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<VpnGatewayInner> listByResourceGroupAsync(String resourceGroupName) {
        return new PagedFlux<>(
            () -> listByResourceGroupSinglePageAsync(resourceGroupName),
            nextLink -> listByResourceGroupNextSinglePageAsync(nextLink));
    }

    /**
     * Lists all the VpnGateways in a resource group.
     * 
     * @param resourceGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<VpnGatewayInner> listByResourceGroup(String resourceGroupName) {
        return new PagedIterable<>(listByResourceGroupAsync(resourceGroupName));
    }

    /**
     * Lists all the VpnGateways in a subscription.
     * 
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<VpnGatewayInner>> listSinglePageAsync() {
        final String apiVersion = "2019-06-01";
        return service.list(this.client.getHost(), this.client.getSubscriptionId(), apiVersion).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().value(),
            res.getValue().nextLink(),
            null));
    }

    /**
     * Lists all the VpnGateways in a subscription.
     * 
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<VpnGatewayInner> listAsync() {
        return new PagedFlux<>(
            () -> listSinglePageAsync(),
            nextLink -> listNextSinglePageAsync(nextLink));
    }

    /**
     * Lists all the VpnGateways in a subscription.
     * 
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<VpnGatewayInner> list() {
        return new PagedIterable<>(listAsync());
    }

    /**
     * Creates a virtual wan vpn gateway if it doesn't exist else updates the existing gateway.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @param vpnGatewayParameters VpnGateway Resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<VpnGatewayInner>> beginCreateOrUpdateWithResponseAsync(String resourceGroupName, String gatewayName, VpnGatewayInner vpnGatewayParameters) {
        final String apiVersion = "2019-06-01";
        return service.beginCreateOrUpdate(this.client.getHost(), this.client.getSubscriptionId(), resourceGroupName, gatewayName, vpnGatewayParameters, apiVersion);
    }

    /**
     * Creates a virtual wan vpn gateway if it doesn't exist else updates the existing gateway.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @param vpnGatewayParameters VpnGateway Resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VpnGatewayInner> beginCreateOrUpdateAsync(String resourceGroupName, String gatewayName, VpnGatewayInner vpnGatewayParameters) {
        return beginCreateOrUpdateWithResponseAsync(resourceGroupName, gatewayName, vpnGatewayParameters)
            .flatMap((SimpleResponse<VpnGatewayInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Creates a virtual wan vpn gateway if it doesn't exist else updates the existing gateway.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @param vpnGatewayParameters VpnGateway Resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public VpnGatewayInner beginCreateOrUpdate(String resourceGroupName, String gatewayName, VpnGatewayInner vpnGatewayParameters) {
        return beginCreateOrUpdateAsync(resourceGroupName, gatewayName, vpnGatewayParameters).block();
    }

    /**
     * Updates virtual wan vpn gateway tags.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<VpnGatewayInner>> beginUpdateTagsWithResponseAsync(String resourceGroupName, String gatewayName, Map<String, String> tags) {
        final String apiVersion = "2019-06-01";
        TagsObject vpnGatewayParameters = new TagsObject();
        vpnGatewayParameters.withTags(tags);
        return service.beginUpdateTags(this.client.getHost(), this.client.getSubscriptionId(), resourceGroupName, gatewayName, vpnGatewayParameters, apiVersion);
    }

    /**
     * Updates virtual wan vpn gateway tags.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VpnGatewayInner> beginUpdateTagsAsync(String resourceGroupName, String gatewayName, Map<String, String> tags) {
        return beginUpdateTagsWithResponseAsync(resourceGroupName, gatewayName, tags)
            .flatMap((SimpleResponse<VpnGatewayInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Updates virtual wan vpn gateway tags.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public VpnGatewayInner beginUpdateTags(String resourceGroupName, String gatewayName, Map<String, String> tags) {
        return beginUpdateTagsAsync(resourceGroupName, gatewayName, tags).block();
    }

    /**
     * Deletes a virtual wan vpn gateway.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> beginDeleteWithResponseAsync(String resourceGroupName, String gatewayName) {
        final String apiVersion = "2019-06-01";
        return service.beginDelete(this.client.getHost(), this.client.getSubscriptionId(), resourceGroupName, gatewayName, apiVersion);
    }

    /**
     * Deletes a virtual wan vpn gateway.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> beginDeleteAsync(String resourceGroupName, String gatewayName) {
        return beginDeleteWithResponseAsync(resourceGroupName, gatewayName)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Deletes a virtual wan vpn gateway.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void beginDelete(String resourceGroupName, String gatewayName) {
        beginDeleteAsync(resourceGroupName, gatewayName).block();
    }

    /**
     * Resets the primary of the vpn gateway in the specified resource group.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<VpnGatewayInner>> beginResetWithResponseAsync(String resourceGroupName, String gatewayName) {
        final String apiVersion = "2019-06-01";
        return service.beginReset(this.client.getHost(), resourceGroupName, gatewayName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Resets the primary of the vpn gateway in the specified resource group.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VpnGatewayInner> beginResetAsync(String resourceGroupName, String gatewayName) {
        return beginResetWithResponseAsync(resourceGroupName, gatewayName)
            .flatMap((SimpleResponse<VpnGatewayInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Resets the primary of the vpn gateway in the specified resource group.
     * 
     * @param resourceGroupName 
     * @param gatewayName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public VpnGatewayInner beginReset(String resourceGroupName, String gatewayName) {
        return beginResetAsync(resourceGroupName, gatewayName).block();
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
    public Mono<PagedResponse<VpnGatewayInner>> listByResourceGroupNextSinglePageAsync(String nextLink) {
        return service.listByResourceGroupNext(nextLink).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().value(),
            res.getValue().nextLink(),
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
    public Mono<PagedResponse<VpnGatewayInner>> listNextSinglePageAsync(String nextLink) {
        return service.listNext(nextLink).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().value(),
            res.getValue().nextLink(),
            null));
    }
}
