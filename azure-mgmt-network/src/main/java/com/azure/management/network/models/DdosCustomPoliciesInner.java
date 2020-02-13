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
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.RestProxy;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.management.CloudException;
import com.azure.core.util.polling.AsyncPollResponse;
import com.azure.management.network.TagsObject;
import com.azure.management.resources.fluentcore.collection.InnerSupportsDelete;
import com.azure.management.resources.fluentcore.collection.InnerSupportsGet;
import java.nio.ByteBuffer;
import java.util.Map;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * DdosCustomPolicies.
 */
public final class DdosCustomPoliciesInner implements InnerSupportsGet<DdosCustomPolicyInner>, InnerSupportsDelete<Void> {
    /**
     * The proxy service used to perform REST calls.
     */
    private DdosCustomPoliciesService service;

    /**
     * The service client containing this operation class.
     */
    private NetworkManagementClientImpl client;

    /**
     * Initializes an instance of DdosCustomPoliciesInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public DdosCustomPoliciesInner(NetworkManagementClientImpl client) {
        this.service = RestProxy.create(DdosCustomPoliciesService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * NetworkManagementClientDdosCustomPolicies to be used by the proxy
     * service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "NetworkManagementClientDdosCustomPolicies")
    private interface DdosCustomPoliciesService {
        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/ddosCustomPolicies/{ddosCustomPolicyName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<Flux<ByteBuffer>>> delete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("ddosCustomPolicyName") String ddosCustomPolicyName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/ddosCustomPolicies/{ddosCustomPolicyName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<DdosCustomPolicyInner>> getByResourceGroup(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("ddosCustomPolicyName") String ddosCustomPolicyName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/ddosCustomPolicies/{ddosCustomPolicyName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<Flux<ByteBuffer>>> createOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("ddosCustomPolicyName") String ddosCustomPolicyName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") DdosCustomPolicyInner parameters, @QueryParam("api-version") String apiVersion);

        @Patch("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/ddosCustomPolicies/{ddosCustomPolicyName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<Flux<ByteBuffer>>> updateTags(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("ddosCustomPolicyName") String ddosCustomPolicyName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") TagsObject parameters, @QueryParam("api-version") String apiVersion);

        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/ddosCustomPolicies/{ddosCustomPolicyName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<Response<Void>> beginDelete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("ddosCustomPolicyName") String ddosCustomPolicyName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/ddosCustomPolicies/{ddosCustomPolicyName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<DdosCustomPolicyInner>> beginCreateOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("ddosCustomPolicyName") String ddosCustomPolicyName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") DdosCustomPolicyInner parameters, @QueryParam("api-version") String apiVersion);

        @Patch("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/ddosCustomPolicies/{ddosCustomPolicyName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<DdosCustomPolicyInner>> beginUpdateTags(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("ddosCustomPolicyName") String ddosCustomPolicyName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") TagsObject parameters, @QueryParam("api-version") String apiVersion);
    }

    /**
     * Deletes the specified DDoS custom policy.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Flux<ByteBuffer>>> deleteWithResponseAsync(String resourceGroupName, String ddosCustomPolicyName) {
        final String apiVersion = "2019-06-01";
        return service.delete(this.client.getHost(), resourceGroupName, ddosCustomPolicyName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Deletes the specified DDoS custom policy.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteAsync(String resourceGroupName, String ddosCustomPolicyName) {
        Mono<SimpleResponse<Flux<ByteBuffer>>> response = deleteWithResponseAsync(resourceGroupName, ddosCustomPolicyName);
        return client.<Void, Void>getLroResultAsync(response, client.getHttpPipeline(), Void.class, Void.class)
            .last()
            .flatMap(AsyncPollResponse::getFinalResult);
    }

    /**
     * Deletes the specified DDoS custom policy.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void delete(String resourceGroupName, String ddosCustomPolicyName) {
        deleteAsync(resourceGroupName, ddosCustomPolicyName).block();
    }

    /**
     * Gets information about the specified DDoS custom policy.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<DdosCustomPolicyInner>> getByResourceGroupWithResponseAsync(String resourceGroupName, String ddosCustomPolicyName) {
        final String apiVersion = "2019-06-01";
        return service.getByResourceGroup(this.client.getHost(), resourceGroupName, ddosCustomPolicyName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Gets information about the specified DDoS custom policy.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<DdosCustomPolicyInner> getByResourceGroupAsync(String resourceGroupName, String ddosCustomPolicyName) {
        return getByResourceGroupWithResponseAsync(resourceGroupName, ddosCustomPolicyName)
            .flatMap((SimpleResponse<DdosCustomPolicyInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Gets information about the specified DDoS custom policy.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public DdosCustomPolicyInner getByResourceGroup(String resourceGroupName, String ddosCustomPolicyName) {
        return getByResourceGroupAsync(resourceGroupName, ddosCustomPolicyName).block();
    }

    /**
     * Creates or updates a DDoS custom policy.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @param parameters A DDoS custom policy in a resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Flux<ByteBuffer>>> createOrUpdateWithResponseAsync(String resourceGroupName, String ddosCustomPolicyName, DdosCustomPolicyInner parameters) {
        final String apiVersion = "2019-06-01";
        return service.createOrUpdate(this.client.getHost(), resourceGroupName, ddosCustomPolicyName, this.client.getSubscriptionId(), parameters, apiVersion);
    }

    /**
     * Creates or updates a DDoS custom policy.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @param parameters A DDoS custom policy in a resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<DdosCustomPolicyInner> createOrUpdateAsync(String resourceGroupName, String ddosCustomPolicyName, DdosCustomPolicyInner parameters) {
        Mono<SimpleResponse<Flux<ByteBuffer>>> response = createOrUpdateWithResponseAsync(resourceGroupName, ddosCustomPolicyName, parameters);
        return client.<DdosCustomPolicyInner, DdosCustomPolicyInner>getLroResultAsync(response, client.getHttpPipeline(), DdosCustomPolicyInner.class, DdosCustomPolicyInner.class)
            .last()
            .flatMap(AsyncPollResponse::getFinalResult);
    }

    /**
     * Creates or updates a DDoS custom policy.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @param parameters A DDoS custom policy in a resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public DdosCustomPolicyInner createOrUpdate(String resourceGroupName, String ddosCustomPolicyName, DdosCustomPolicyInner parameters) {
        return createOrUpdateAsync(resourceGroupName, ddosCustomPolicyName, parameters).block();
    }

    /**
     * Update a DDoS custom policy tags.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Flux<ByteBuffer>>> updateTagsWithResponseAsync(String resourceGroupName, String ddosCustomPolicyName, Map<String, String> tags) {
        final String apiVersion = "2019-06-01";
        TagsObject parameters = new TagsObject();
        parameters.withTags(tags);
        return service.updateTags(this.client.getHost(), resourceGroupName, ddosCustomPolicyName, this.client.getSubscriptionId(), parameters, apiVersion);
    }

    /**
     * Update a DDoS custom policy tags.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<DdosCustomPolicyInner> updateTagsAsync(String resourceGroupName, String ddosCustomPolicyName, Map<String, String> tags) {
        Mono<SimpleResponse<Flux<ByteBuffer>>> response = updateTagsWithResponseAsync(resourceGroupName, ddosCustomPolicyName, tags);
        return client.<DdosCustomPolicyInner, DdosCustomPolicyInner>getLroResultAsync(response, client.getHttpPipeline(), DdosCustomPolicyInner.class, DdosCustomPolicyInner.class)
            .last()
            .flatMap(AsyncPollResponse::getFinalResult);
    }

    /**
     * Update a DDoS custom policy tags.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public DdosCustomPolicyInner updateTags(String resourceGroupName, String ddosCustomPolicyName, Map<String, String> tags) {
        return updateTagsAsync(resourceGroupName, ddosCustomPolicyName, tags).block();
    }

    /**
     * Deletes the specified DDoS custom policy.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> beginDeleteWithResponseAsync(String resourceGroupName, String ddosCustomPolicyName) {
        final String apiVersion = "2019-06-01";
        return service.beginDelete(this.client.getHost(), resourceGroupName, ddosCustomPolicyName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Deletes the specified DDoS custom policy.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> beginDeleteAsync(String resourceGroupName, String ddosCustomPolicyName) {
        return beginDeleteWithResponseAsync(resourceGroupName, ddosCustomPolicyName)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Deletes the specified DDoS custom policy.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void beginDelete(String resourceGroupName, String ddosCustomPolicyName) {
        beginDeleteAsync(resourceGroupName, ddosCustomPolicyName).block();
    }

    /**
     * Creates or updates a DDoS custom policy.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @param parameters A DDoS custom policy in a resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<DdosCustomPolicyInner>> beginCreateOrUpdateWithResponseAsync(String resourceGroupName, String ddosCustomPolicyName, DdosCustomPolicyInner parameters) {
        final String apiVersion = "2019-06-01";
        return service.beginCreateOrUpdate(this.client.getHost(), resourceGroupName, ddosCustomPolicyName, this.client.getSubscriptionId(), parameters, apiVersion);
    }

    /**
     * Creates or updates a DDoS custom policy.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @param parameters A DDoS custom policy in a resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<DdosCustomPolicyInner> beginCreateOrUpdateAsync(String resourceGroupName, String ddosCustomPolicyName, DdosCustomPolicyInner parameters) {
        return beginCreateOrUpdateWithResponseAsync(resourceGroupName, ddosCustomPolicyName, parameters)
            .flatMap((SimpleResponse<DdosCustomPolicyInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Creates or updates a DDoS custom policy.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @param parameters A DDoS custom policy in a resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public DdosCustomPolicyInner beginCreateOrUpdate(String resourceGroupName, String ddosCustomPolicyName, DdosCustomPolicyInner parameters) {
        return beginCreateOrUpdateAsync(resourceGroupName, ddosCustomPolicyName, parameters).block();
    }

    /**
     * Update a DDoS custom policy tags.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<DdosCustomPolicyInner>> beginUpdateTagsWithResponseAsync(String resourceGroupName, String ddosCustomPolicyName, Map<String, String> tags) {
        final String apiVersion = "2019-06-01";
        TagsObject parameters = new TagsObject();
        parameters.withTags(tags);
        return service.beginUpdateTags(this.client.getHost(), resourceGroupName, ddosCustomPolicyName, this.client.getSubscriptionId(), parameters, apiVersion);
    }

    /**
     * Update a DDoS custom policy tags.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<DdosCustomPolicyInner> beginUpdateTagsAsync(String resourceGroupName, String ddosCustomPolicyName, Map<String, String> tags) {
        return beginUpdateTagsWithResponseAsync(resourceGroupName, ddosCustomPolicyName, tags)
            .flatMap((SimpleResponse<DdosCustomPolicyInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Update a DDoS custom policy tags.
     * 
     * @param resourceGroupName 
     * @param ddosCustomPolicyName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public DdosCustomPolicyInner beginUpdateTags(String resourceGroupName, String ddosCustomPolicyName, Map<String, String> tags) {
        return beginUpdateTagsAsync(resourceGroupName, ddosCustomPolicyName, tags).block();
    }
}
