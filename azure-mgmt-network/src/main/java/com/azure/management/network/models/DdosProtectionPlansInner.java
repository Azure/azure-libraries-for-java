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
 * DdosProtectionPlans.
 */
public final class DdosProtectionPlansInner implements InnerSupportsGet<DdosProtectionPlanInner>, InnerSupportsListing<DdosProtectionPlanInner>, InnerSupportsDelete<Void> {
    /**
     * The proxy service used to perform REST calls.
     */
    private DdosProtectionPlansService service;

    /**
     * The service client containing this operation class.
     */
    private NetworkManagementClientImpl client;

    /**
     * Initializes an instance of DdosProtectionPlansInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public DdosProtectionPlansInner(NetworkManagementClientImpl client) {
        this.service = RestProxy.create(DdosProtectionPlansService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * NetworkManagementClientDdosProtectionPlans to be used by the proxy
     * service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "NetworkManagementClientDdosProtectionPlans")
    private interface DdosProtectionPlansService {
        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/ddosProtectionPlans/{ddosProtectionPlanName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<Flux<ByteBuffer>>> delete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("ddosProtectionPlanName") String ddosProtectionPlanName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/ddosProtectionPlans/{ddosProtectionPlanName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<DdosProtectionPlanInner>> getByResourceGroup(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("ddosProtectionPlanName") String ddosProtectionPlanName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/ddosProtectionPlans/{ddosProtectionPlanName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<Flux<ByteBuffer>>> createOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("ddosProtectionPlanName") String ddosProtectionPlanName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") DdosProtectionPlanInner parameters, @QueryParam("api-version") String apiVersion);

        @Patch("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/ddosProtectionPlans/{ddosProtectionPlanName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<Flux<ByteBuffer>>> updateTags(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("ddosProtectionPlanName") String ddosProtectionPlanName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") TagsObject parameters, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/providers/Microsoft.Network/ddosProtectionPlans")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<DdosProtectionPlanListResultInner>> list(@HostParam("$host") String host, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/ddosProtectionPlans")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<DdosProtectionPlanListResultInner>> listByResourceGroup(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/ddosProtectionPlans/{ddosProtectionPlanName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<Response<Void>> beginDelete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("ddosProtectionPlanName") String ddosProtectionPlanName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/ddosProtectionPlans/{ddosProtectionPlanName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<DdosProtectionPlanInner>> beginCreateOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("ddosProtectionPlanName") String ddosProtectionPlanName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") DdosProtectionPlanInner parameters, @QueryParam("api-version") String apiVersion);

        @Patch("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/ddosProtectionPlans/{ddosProtectionPlanName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<DdosProtectionPlanInner>> beginUpdateTags(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("ddosProtectionPlanName") String ddosProtectionPlanName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") TagsObject parameters, @QueryParam("api-version") String apiVersion);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<DdosProtectionPlanListResultInner>> listNext(@PathParam(value = "nextLink", encoded = true) String nextLink);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<DdosProtectionPlanListResultInner>> listByResourceGroupNext(@PathParam(value = "nextLink", encoded = true) String nextLink);
    }

    /**
     * Deletes the specified DDoS protection plan.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Flux<ByteBuffer>>> deleteWithResponseAsync(String resourceGroupName, String ddosProtectionPlanName) {
        final String apiVersion = "2019-06-01";
        return service.delete(this.client.getHost(), resourceGroupName, ddosProtectionPlanName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Deletes the specified DDoS protection plan.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteAsync(String resourceGroupName, String ddosProtectionPlanName) {
        Mono<SimpleResponse<Flux<ByteBuffer>>> response = deleteWithResponseAsync(resourceGroupName, ddosProtectionPlanName);
        return client.<Void, Void>getLroResultAsync(response, client.getHttpPipeline(), Void.class, Void.class)
            .last()
            .flatMap(AsyncPollResponse::getFinalResult);
    }

    /**
     * Deletes the specified DDoS protection plan.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void delete(String resourceGroupName, String ddosProtectionPlanName) {
        deleteAsync(resourceGroupName, ddosProtectionPlanName).block();
    }

    /**
     * Gets information about the specified DDoS protection plan.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<DdosProtectionPlanInner>> getByResourceGroupWithResponseAsync(String resourceGroupName, String ddosProtectionPlanName) {
        final String apiVersion = "2019-06-01";
        return service.getByResourceGroup(this.client.getHost(), resourceGroupName, ddosProtectionPlanName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Gets information about the specified DDoS protection plan.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<DdosProtectionPlanInner> getByResourceGroupAsync(String resourceGroupName, String ddosProtectionPlanName) {
        return getByResourceGroupWithResponseAsync(resourceGroupName, ddosProtectionPlanName)
            .flatMap((SimpleResponse<DdosProtectionPlanInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Gets information about the specified DDoS protection plan.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public DdosProtectionPlanInner getByResourceGroup(String resourceGroupName, String ddosProtectionPlanName) {
        return getByResourceGroupAsync(resourceGroupName, ddosProtectionPlanName).block();
    }

    /**
     * Creates or updates a DDoS protection plan.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @param parameters A DDoS protection plan in a resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Flux<ByteBuffer>>> createOrUpdateWithResponseAsync(String resourceGroupName, String ddosProtectionPlanName, DdosProtectionPlanInner parameters) {
        final String apiVersion = "2019-06-01";
        return service.createOrUpdate(this.client.getHost(), resourceGroupName, ddosProtectionPlanName, this.client.getSubscriptionId(), parameters, apiVersion);
    }

    /**
     * Creates or updates a DDoS protection plan.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @param parameters A DDoS protection plan in a resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<DdosProtectionPlanInner> createOrUpdateAsync(String resourceGroupName, String ddosProtectionPlanName, DdosProtectionPlanInner parameters) {
        Mono<SimpleResponse<Flux<ByteBuffer>>> response = createOrUpdateWithResponseAsync(resourceGroupName, ddosProtectionPlanName, parameters);
        return client.<DdosProtectionPlanInner, DdosProtectionPlanInner>getLroResultAsync(response, client.getHttpPipeline(), DdosProtectionPlanInner.class, DdosProtectionPlanInner.class)
            .last()
            .flatMap(AsyncPollResponse::getFinalResult);
    }

    /**
     * Creates or updates a DDoS protection plan.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @param parameters A DDoS protection plan in a resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public DdosProtectionPlanInner createOrUpdate(String resourceGroupName, String ddosProtectionPlanName, DdosProtectionPlanInner parameters) {
        return createOrUpdateAsync(resourceGroupName, ddosProtectionPlanName, parameters).block();
    }

    /**
     * Update a DDoS protection plan tags.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Flux<ByteBuffer>>> updateTagsWithResponseAsync(String resourceGroupName, String ddosProtectionPlanName, Map<String, String> tags) {
        final String apiVersion = "2019-06-01";
        TagsObject parameters = new TagsObject();
        parameters.withTags(tags);
        return service.updateTags(this.client.getHost(), resourceGroupName, ddosProtectionPlanName, this.client.getSubscriptionId(), parameters, apiVersion);
    }

    /**
     * Update a DDoS protection plan tags.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<DdosProtectionPlanInner> updateTagsAsync(String resourceGroupName, String ddosProtectionPlanName, Map<String, String> tags) {
        Mono<SimpleResponse<Flux<ByteBuffer>>> response = updateTagsWithResponseAsync(resourceGroupName, ddosProtectionPlanName, tags);
        return client.<DdosProtectionPlanInner, DdosProtectionPlanInner>getLroResultAsync(response, client.getHttpPipeline(), DdosProtectionPlanInner.class, DdosProtectionPlanInner.class)
            .last()
            .flatMap(AsyncPollResponse::getFinalResult);
    }

    /**
     * Update a DDoS protection plan tags.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public DdosProtectionPlanInner updateTags(String resourceGroupName, String ddosProtectionPlanName, Map<String, String> tags) {
        return updateTagsAsync(resourceGroupName, ddosProtectionPlanName, tags).block();
    }

    /**
     * Gets all DDoS protection plans in a subscription.
     * 
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<DdosProtectionPlanInner>> listSinglePageAsync() {
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
     * Gets all DDoS protection plans in a subscription.
     * 
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<DdosProtectionPlanInner> listAsync() {
        return new PagedFlux<>(
            () -> listSinglePageAsync(),
            nextLink -> listNextSinglePageAsync(nextLink));
    }

    /**
     * Gets all DDoS protection plans in a subscription.
     * 
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<DdosProtectionPlanInner> list() {
        return new PagedIterable<>(listAsync());
    }

    /**
     * Gets all the DDoS protection plans in a resource group.
     * 
     * @param resourceGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<DdosProtectionPlanInner>> listByResourceGroupSinglePageAsync(String resourceGroupName) {
        final String apiVersion = "2019-06-01";
        return service.listByResourceGroup(this.client.getHost(), resourceGroupName, this.client.getSubscriptionId(), apiVersion).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().value(),
            res.getValue().nextLink(),
            null));
    }

    /**
     * Gets all the DDoS protection plans in a resource group.
     * 
     * @param resourceGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<DdosProtectionPlanInner> listByResourceGroupAsync(String resourceGroupName) {
        return new PagedFlux<>(
            () -> listByResourceGroupSinglePageAsync(resourceGroupName),
            nextLink -> listByResourceGroupNextSinglePageAsync(nextLink));
    }

    /**
     * Gets all the DDoS protection plans in a resource group.
     * 
     * @param resourceGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<DdosProtectionPlanInner> listByResourceGroup(String resourceGroupName) {
        return new PagedIterable<>(listByResourceGroupAsync(resourceGroupName));
    }

    /**
     * Deletes the specified DDoS protection plan.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> beginDeleteWithResponseAsync(String resourceGroupName, String ddosProtectionPlanName) {
        final String apiVersion = "2019-06-01";
        return service.beginDelete(this.client.getHost(), resourceGroupName, ddosProtectionPlanName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Deletes the specified DDoS protection plan.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> beginDeleteAsync(String resourceGroupName, String ddosProtectionPlanName) {
        return beginDeleteWithResponseAsync(resourceGroupName, ddosProtectionPlanName)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Deletes the specified DDoS protection plan.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void beginDelete(String resourceGroupName, String ddosProtectionPlanName) {
        beginDeleteAsync(resourceGroupName, ddosProtectionPlanName).block();
    }

    /**
     * Creates or updates a DDoS protection plan.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @param parameters A DDoS protection plan in a resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<DdosProtectionPlanInner>> beginCreateOrUpdateWithResponseAsync(String resourceGroupName, String ddosProtectionPlanName, DdosProtectionPlanInner parameters) {
        final String apiVersion = "2019-06-01";
        return service.beginCreateOrUpdate(this.client.getHost(), resourceGroupName, ddosProtectionPlanName, this.client.getSubscriptionId(), parameters, apiVersion);
    }

    /**
     * Creates or updates a DDoS protection plan.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @param parameters A DDoS protection plan in a resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<DdosProtectionPlanInner> beginCreateOrUpdateAsync(String resourceGroupName, String ddosProtectionPlanName, DdosProtectionPlanInner parameters) {
        return beginCreateOrUpdateWithResponseAsync(resourceGroupName, ddosProtectionPlanName, parameters)
            .flatMap((SimpleResponse<DdosProtectionPlanInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Creates or updates a DDoS protection plan.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @param parameters A DDoS protection plan in a resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public DdosProtectionPlanInner beginCreateOrUpdate(String resourceGroupName, String ddosProtectionPlanName, DdosProtectionPlanInner parameters) {
        return beginCreateOrUpdateAsync(resourceGroupName, ddosProtectionPlanName, parameters).block();
    }

    /**
     * Update a DDoS protection plan tags.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<DdosProtectionPlanInner>> beginUpdateTagsWithResponseAsync(String resourceGroupName, String ddosProtectionPlanName, Map<String, String> tags) {
        final String apiVersion = "2019-06-01";
        TagsObject parameters = new TagsObject();
        parameters.withTags(tags);
        return service.beginUpdateTags(this.client.getHost(), resourceGroupName, ddosProtectionPlanName, this.client.getSubscriptionId(), parameters, apiVersion);
    }

    /**
     * Update a DDoS protection plan tags.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<DdosProtectionPlanInner> beginUpdateTagsAsync(String resourceGroupName, String ddosProtectionPlanName, Map<String, String> tags) {
        return beginUpdateTagsWithResponseAsync(resourceGroupName, ddosProtectionPlanName, tags)
            .flatMap((SimpleResponse<DdosProtectionPlanInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Update a DDoS protection plan tags.
     * 
     * @param resourceGroupName 
     * @param ddosProtectionPlanName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public DdosProtectionPlanInner beginUpdateTags(String resourceGroupName, String ddosProtectionPlanName, Map<String, String> tags) {
        return beginUpdateTagsAsync(resourceGroupName, ddosProtectionPlanName, tags).block();
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
    public Mono<PagedResponse<DdosProtectionPlanInner>> listNextSinglePageAsync(String nextLink) {
        return service.listNext(nextLink).map(res -> new PagedResponseBase<>(
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
    public Mono<PagedResponse<DdosProtectionPlanInner>> listByResourceGroupNextSinglePageAsync(String nextLink) {
        return service.listByResourceGroupNext(nextLink).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().value(),
            res.getValue().nextLink(),
            null));
    }
}
