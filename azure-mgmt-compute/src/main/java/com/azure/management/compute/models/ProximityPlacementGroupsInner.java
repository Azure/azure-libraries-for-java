// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.compute.models;

import com.azure.core.annotation.BodyParam;
import com.azure.core.annotation.Delete;
import com.azure.core.annotation.ExpectedResponses;
import com.azure.core.annotation.Get;
import com.azure.core.annotation.Host;
import com.azure.core.annotation.HostParam;
import com.azure.core.annotation.Patch;
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
import com.azure.management.compute.ProximityPlacementGroupUpdate;
import com.azure.management.resources.fluentcore.collection.InnerSupportsDelete;
import com.azure.management.resources.fluentcore.collection.InnerSupportsGet;
import com.azure.management.resources.fluentcore.collection.InnerSupportsListing;
import java.util.Map;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * ProximityPlacementGroups.
 */
public final class ProximityPlacementGroupsInner implements InnerSupportsGet<ProximityPlacementGroupInner>, InnerSupportsListing<ProximityPlacementGroupInner>, InnerSupportsDelete<Void> {
    /**
     * The proxy service used to perform REST calls.
     */
    private ProximityPlacementGroupsService service;

    /**
     * The service client containing this operation class.
     */
    private ComputeManagementClientImpl client;

    /**
     * Initializes an instance of ProximityPlacementGroupsInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public ProximityPlacementGroupsInner(ComputeManagementClientImpl client) {
        this.service = RestProxy.create(ProximityPlacementGroupsService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * ComputeManagementClientProximityPlacementGroups to be used by the proxy
     * service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "ComputeManagementClientProximityPlacementGroups")
    private interface ProximityPlacementGroupsService {
        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Compute/proximityPlacementGroups/{proximityPlacementGroupName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ProximityPlacementGroupInner>> createOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("proximityPlacementGroupName") String proximityPlacementGroupName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") ProximityPlacementGroupInner parameters, @QueryParam("api-version") String apiVersion);

        @Patch("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Compute/proximityPlacementGroups/{proximityPlacementGroupName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ProximityPlacementGroupInner>> update(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("proximityPlacementGroupName") String proximityPlacementGroupName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") ProximityPlacementGroupUpdate parameters, @QueryParam("api-version") String apiVersion);

        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Compute/proximityPlacementGroups/{proximityPlacementGroupName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<Response<Void>> delete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("proximityPlacementGroupName") String proximityPlacementGroupName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Compute/proximityPlacementGroups/{proximityPlacementGroupName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ProximityPlacementGroupInner>> getByResourceGroup(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("proximityPlacementGroupName") String proximityPlacementGroupName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/providers/Microsoft.Compute/proximityPlacementGroups")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ProximityPlacementGroupListResultInner>> list(@HostParam("$host") String host, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Compute/proximityPlacementGroups")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ProximityPlacementGroupListResultInner>> listByResourceGroup(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ProximityPlacementGroupListResultInner>> listBySubscriptionNext(@PathParam(value = "nextLink", encoded = true) String nextLink);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ProximityPlacementGroupListResultInner>> listByResourceGroupNext(@PathParam(value = "nextLink", encoded = true) String nextLink);
    }

    /**
     * Create or update a proximity placement group.
     * 
     * @param resourceGroupName 
     * @param proximityPlacementGroupName 
     * @param parameters Specifies information about the proximity placement group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ProximityPlacementGroupInner>> createOrUpdateWithResponseAsync(String resourceGroupName, String proximityPlacementGroupName, ProximityPlacementGroupInner parameters) {
        final String apiVersion = "2019-03-01";
        return service.createOrUpdate(this.client.getHost(), resourceGroupName, proximityPlacementGroupName, this.client.getSubscriptionId(), parameters, apiVersion);
    }

    /**
     * Create or update a proximity placement group.
     * 
     * @param resourceGroupName 
     * @param proximityPlacementGroupName 
     * @param parameters Specifies information about the proximity placement group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ProximityPlacementGroupInner> createOrUpdateAsync(String resourceGroupName, String proximityPlacementGroupName, ProximityPlacementGroupInner parameters) {
        return createOrUpdateWithResponseAsync(resourceGroupName, proximityPlacementGroupName, parameters)
            .flatMap((SimpleResponse<ProximityPlacementGroupInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Create or update a proximity placement group.
     * 
     * @param resourceGroupName 
     * @param proximityPlacementGroupName 
     * @param parameters Specifies information about the proximity placement group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ProximityPlacementGroupInner createOrUpdate(String resourceGroupName, String proximityPlacementGroupName, ProximityPlacementGroupInner parameters) {
        return createOrUpdateAsync(resourceGroupName, proximityPlacementGroupName, parameters).block();
    }

    /**
     * Update a proximity placement group.
     * 
     * @param resourceGroupName 
     * @param proximityPlacementGroupName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ProximityPlacementGroupInner>> updateWithResponseAsync(String resourceGroupName, String proximityPlacementGroupName, Map<String, String> tags) {
        final String apiVersion = "2019-03-01";
        ProximityPlacementGroupUpdate parameters = new ProximityPlacementGroupUpdate();
        parameters.withTags(tags);
        return service.update(this.client.getHost(), resourceGroupName, proximityPlacementGroupName, this.client.getSubscriptionId(), parameters, apiVersion);
    }

    /**
     * Update a proximity placement group.
     * 
     * @param resourceGroupName 
     * @param proximityPlacementGroupName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ProximityPlacementGroupInner> updateAsync(String resourceGroupName, String proximityPlacementGroupName, Map<String, String> tags) {
        return updateWithResponseAsync(resourceGroupName, proximityPlacementGroupName, tags)
            .flatMap((SimpleResponse<ProximityPlacementGroupInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Update a proximity placement group.
     * 
     * @param resourceGroupName 
     * @param proximityPlacementGroupName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ProximityPlacementGroupInner update(String resourceGroupName, String proximityPlacementGroupName, Map<String, String> tags) {
        return updateAsync(resourceGroupName, proximityPlacementGroupName, tags).block();
    }

    /**
     * Delete a proximity placement group.
     * 
     * @param resourceGroupName 
     * @param proximityPlacementGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> deleteWithResponseAsync(String resourceGroupName, String proximityPlacementGroupName) {
        final String apiVersion = "2019-03-01";
        return service.delete(this.client.getHost(), resourceGroupName, proximityPlacementGroupName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Delete a proximity placement group.
     * 
     * @param resourceGroupName 
     * @param proximityPlacementGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteAsync(String resourceGroupName, String proximityPlacementGroupName) {
        return deleteWithResponseAsync(resourceGroupName, proximityPlacementGroupName)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Delete a proximity placement group.
     * 
     * @param resourceGroupName 
     * @param proximityPlacementGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void delete(String resourceGroupName, String proximityPlacementGroupName) {
        deleteAsync(resourceGroupName, proximityPlacementGroupName).block();
    }

    /**
     * Retrieves information about a proximity placement group .
     * 
     * @param resourceGroupName 
     * @param proximityPlacementGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ProximityPlacementGroupInner>> getByResourceGroupWithResponseAsync(String resourceGroupName, String proximityPlacementGroupName) {
        final String apiVersion = "2019-03-01";
        return service.getByResourceGroup(this.client.getHost(), resourceGroupName, proximityPlacementGroupName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Retrieves information about a proximity placement group .
     * 
     * @param resourceGroupName 
     * @param proximityPlacementGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ProximityPlacementGroupInner> getByResourceGroupAsync(String resourceGroupName, String proximityPlacementGroupName) {
        return getByResourceGroupWithResponseAsync(resourceGroupName, proximityPlacementGroupName)
            .flatMap((SimpleResponse<ProximityPlacementGroupInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Retrieves information about a proximity placement group .
     * 
     * @param resourceGroupName 
     * @param proximityPlacementGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ProximityPlacementGroupInner getByResourceGroup(String resourceGroupName, String proximityPlacementGroupName) {
        return getByResourceGroupAsync(resourceGroupName, proximityPlacementGroupName).block();
    }

    /**
     * Lists all proximity placement groups in a subscription.
     * 
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<ProximityPlacementGroupInner>> listSinglePageAsync() {
        final String apiVersion = "2019-03-01";
        return service.list(this.client.getHost(), this.client.getSubscriptionId(), apiVersion).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().value(),
            res.getValue().nextLink(),
            null));
    }

    /**
     * Lists all proximity placement groups in a subscription.
     * 
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<ProximityPlacementGroupInner> listAsync() {
        return new PagedFlux<>(
            () -> listSinglePageAsync(),
            nextLink -> listBySubscriptionNextSinglePageAsync(nextLink));
    }

    /**
     * Lists all proximity placement groups in a subscription.
     * 
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<ProximityPlacementGroupInner> list() {
        return new PagedIterable<>(listAsync());
    }

    /**
     * Lists all proximity placement groups in a resource group.
     * 
     * @param resourceGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<ProximityPlacementGroupInner>> listByResourceGroupSinglePageAsync(String resourceGroupName) {
        final String apiVersion = "2019-03-01";
        return service.listByResourceGroup(this.client.getHost(), resourceGroupName, this.client.getSubscriptionId(), apiVersion).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().value(),
            res.getValue().nextLink(),
            null));
    }

    /**
     * Lists all proximity placement groups in a resource group.
     * 
     * @param resourceGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<ProximityPlacementGroupInner> listByResourceGroupAsync(String resourceGroupName) {
        return new PagedFlux<>(
            () -> listByResourceGroupSinglePageAsync(resourceGroupName),
            nextLink -> listByResourceGroupNextSinglePageAsync(nextLink));
    }

    /**
     * Lists all proximity placement groups in a resource group.
     * 
     * @param resourceGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<ProximityPlacementGroupInner> listByResourceGroup(String resourceGroupName) {
        return new PagedIterable<>(listByResourceGroupAsync(resourceGroupName));
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
    public Mono<PagedResponse<ProximityPlacementGroupInner>> listBySubscriptionNextSinglePageAsync(String nextLink) {
        return service.listBySubscriptionNext(nextLink).map(res -> new PagedResponseBase<>(
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
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<ProximityPlacementGroupInner>> listByResourceGroupNextSinglePageAsync(String nextLink) {
        return service.listByResourceGroupNext(nextLink).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().value(),
            res.getValue().nextLink(),
            null));
    }
}
