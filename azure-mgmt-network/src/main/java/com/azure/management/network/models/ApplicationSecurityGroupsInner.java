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
 * ApplicationSecurityGroups.
 */
public final class ApplicationSecurityGroupsInner implements InnerSupportsGet<ApplicationSecurityGroupInner>, InnerSupportsListing<ApplicationSecurityGroupInner>, InnerSupportsDelete<Void> {
    /**
     * The proxy service used to perform REST calls.
     */
    private ApplicationSecurityGroupsService service;

    /**
     * The service client containing this operation class.
     */
    private NetworkManagementClientImpl client;

    /**
     * Initializes an instance of ApplicationSecurityGroupsInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public ApplicationSecurityGroupsInner(NetworkManagementClientImpl client) {
        this.service = RestProxy.create(ApplicationSecurityGroupsService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * NetworkManagementClientApplicationSecurityGroups to be used by the proxy
     * service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "NetworkManagementClientApplicationSecurityGroups")
    private interface ApplicationSecurityGroupsService {
        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/applicationSecurityGroups/{applicationSecurityGroupName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<Flux<ByteBuffer>>> delete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("applicationSecurityGroupName") String applicationSecurityGroupName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/applicationSecurityGroups/{applicationSecurityGroupName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ApplicationSecurityGroupInner>> getByResourceGroup(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("applicationSecurityGroupName") String applicationSecurityGroupName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/applicationSecurityGroups/{applicationSecurityGroupName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<Flux<ByteBuffer>>> createOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("applicationSecurityGroupName") String applicationSecurityGroupName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") ApplicationSecurityGroupInner parameters, @QueryParam("api-version") String apiVersion);

        @Patch("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/applicationSecurityGroups/{applicationSecurityGroupName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<Flux<ByteBuffer>>> updateTags(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("applicationSecurityGroupName") String applicationSecurityGroupName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") TagsObject parameters, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/providers/Microsoft.Network/applicationSecurityGroups")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ApplicationSecurityGroupListResultInner>> list(@HostParam("$host") String host, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/applicationSecurityGroups")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ApplicationSecurityGroupListResultInner>> listByResourceGroup(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/applicationSecurityGroups/{applicationSecurityGroupName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<Response<Void>> beginDelete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("applicationSecurityGroupName") String applicationSecurityGroupName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/applicationSecurityGroups/{applicationSecurityGroupName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ApplicationSecurityGroupInner>> beginCreateOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("applicationSecurityGroupName") String applicationSecurityGroupName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") ApplicationSecurityGroupInner parameters, @QueryParam("api-version") String apiVersion);

        @Patch("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/applicationSecurityGroups/{applicationSecurityGroupName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ApplicationSecurityGroupInner>> beginUpdateTags(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("applicationSecurityGroupName") String applicationSecurityGroupName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") TagsObject parameters, @QueryParam("api-version") String apiVersion);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ApplicationSecurityGroupListResultInner>> listAllNext(@PathParam(value = "nextLink", encoded = true) String nextLink);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ApplicationSecurityGroupListResultInner>> listNext(@PathParam(value = "nextLink", encoded = true) String nextLink);
    }

    /**
     * Deletes the specified application security group.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Flux<ByteBuffer>>> deleteWithResponseAsync(String resourceGroupName, String applicationSecurityGroupName) {
        final String apiVersion = "2019-06-01";
        return service.delete(this.client.getHost(), resourceGroupName, applicationSecurityGroupName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Deletes the specified application security group.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteAsync(String resourceGroupName, String applicationSecurityGroupName) {
        Mono<SimpleResponse<Flux<ByteBuffer>>> response = deleteWithResponseAsync(resourceGroupName, applicationSecurityGroupName);
        return client.<Void, Void>getLroResultAsync(response, client.getHttpPipeline(), Void.class, Void.class)
            .last()
            .flatMap(AsyncPollResponse::getFinalResult);
    }

    /**
     * Deletes the specified application security group.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void delete(String resourceGroupName, String applicationSecurityGroupName) {
        deleteAsync(resourceGroupName, applicationSecurityGroupName).block();
    }

    /**
     * Gets information about the specified application security group.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ApplicationSecurityGroupInner>> getByResourceGroupWithResponseAsync(String resourceGroupName, String applicationSecurityGroupName) {
        final String apiVersion = "2019-06-01";
        return service.getByResourceGroup(this.client.getHost(), resourceGroupName, applicationSecurityGroupName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Gets information about the specified application security group.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ApplicationSecurityGroupInner> getByResourceGroupAsync(String resourceGroupName, String applicationSecurityGroupName) {
        return getByResourceGroupWithResponseAsync(resourceGroupName, applicationSecurityGroupName)
            .flatMap((SimpleResponse<ApplicationSecurityGroupInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Gets information about the specified application security group.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ApplicationSecurityGroupInner getByResourceGroup(String resourceGroupName, String applicationSecurityGroupName) {
        return getByResourceGroupAsync(resourceGroupName, applicationSecurityGroupName).block();
    }

    /**
     * Creates or updates an application security group.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @param parameters An application security group in a resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Flux<ByteBuffer>>> createOrUpdateWithResponseAsync(String resourceGroupName, String applicationSecurityGroupName, ApplicationSecurityGroupInner parameters) {
        final String apiVersion = "2019-06-01";
        return service.createOrUpdate(this.client.getHost(), resourceGroupName, applicationSecurityGroupName, this.client.getSubscriptionId(), parameters, apiVersion);
    }

    /**
     * Creates or updates an application security group.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @param parameters An application security group in a resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ApplicationSecurityGroupInner> createOrUpdateAsync(String resourceGroupName, String applicationSecurityGroupName, ApplicationSecurityGroupInner parameters) {
        Mono<SimpleResponse<Flux<ByteBuffer>>> response = createOrUpdateWithResponseAsync(resourceGroupName, applicationSecurityGroupName, parameters);
        return client.<ApplicationSecurityGroupInner, ApplicationSecurityGroupInner>getLroResultAsync(response, client.getHttpPipeline(), ApplicationSecurityGroupInner.class, ApplicationSecurityGroupInner.class)
            .last()
            .flatMap(AsyncPollResponse::getFinalResult);
    }

    /**
     * Creates or updates an application security group.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @param parameters An application security group in a resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ApplicationSecurityGroupInner createOrUpdate(String resourceGroupName, String applicationSecurityGroupName, ApplicationSecurityGroupInner parameters) {
        return createOrUpdateAsync(resourceGroupName, applicationSecurityGroupName, parameters).block();
    }

    /**
     * Updates an application security group's tags.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Flux<ByteBuffer>>> updateTagsWithResponseAsync(String resourceGroupName, String applicationSecurityGroupName, Map<String, String> tags) {
        final String apiVersion = "2019-06-01";
        TagsObject parameters = new TagsObject();
        parameters.withTags(tags);
        return service.updateTags(this.client.getHost(), resourceGroupName, applicationSecurityGroupName, this.client.getSubscriptionId(), parameters, apiVersion);
    }

    /**
     * Updates an application security group's tags.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ApplicationSecurityGroupInner> updateTagsAsync(String resourceGroupName, String applicationSecurityGroupName, Map<String, String> tags) {
        Mono<SimpleResponse<Flux<ByteBuffer>>> response = updateTagsWithResponseAsync(resourceGroupName, applicationSecurityGroupName, tags);
        return client.<ApplicationSecurityGroupInner, ApplicationSecurityGroupInner>getLroResultAsync(response, client.getHttpPipeline(), ApplicationSecurityGroupInner.class, ApplicationSecurityGroupInner.class)
            .last()
            .flatMap(AsyncPollResponse::getFinalResult);
    }

    /**
     * Updates an application security group's tags.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ApplicationSecurityGroupInner updateTags(String resourceGroupName, String applicationSecurityGroupName, Map<String, String> tags) {
        return updateTagsAsync(resourceGroupName, applicationSecurityGroupName, tags).block();
    }

    /**
     * Gets all application security groups in a subscription.
     * 
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<ApplicationSecurityGroupInner>> listSinglePageAsync() {
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
     * Gets all application security groups in a subscription.
     * 
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<ApplicationSecurityGroupInner> listAsync() {
        return new PagedFlux<>(
            () -> listSinglePageAsync(),
            nextLink -> listAllNextSinglePageAsync(nextLink));
    }

    /**
     * Gets all application security groups in a subscription.
     * 
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<ApplicationSecurityGroupInner> list() {
        return new PagedIterable<>(listAsync());
    }

    /**
     * Gets all the application security groups in a resource group.
     * 
     * @param resourceGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<ApplicationSecurityGroupInner>> listByResourceGroupSinglePageAsync(String resourceGroupName) {
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
     * Gets all the application security groups in a resource group.
     * 
     * @param resourceGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<ApplicationSecurityGroupInner> listByResourceGroupAsync(String resourceGroupName) {
        return new PagedFlux<>(
            () -> listByResourceGroupSinglePageAsync(resourceGroupName),
            nextLink -> listNextSinglePageAsync(nextLink));
    }

    /**
     * Gets all the application security groups in a resource group.
     * 
     * @param resourceGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<ApplicationSecurityGroupInner> listByResourceGroup(String resourceGroupName) {
        return new PagedIterable<>(listByResourceGroupAsync(resourceGroupName));
    }

    /**
     * Deletes the specified application security group.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> beginDeleteWithResponseAsync(String resourceGroupName, String applicationSecurityGroupName) {
        final String apiVersion = "2019-06-01";
        return service.beginDelete(this.client.getHost(), resourceGroupName, applicationSecurityGroupName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Deletes the specified application security group.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> beginDeleteAsync(String resourceGroupName, String applicationSecurityGroupName) {
        return beginDeleteWithResponseAsync(resourceGroupName, applicationSecurityGroupName)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Deletes the specified application security group.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void beginDelete(String resourceGroupName, String applicationSecurityGroupName) {
        beginDeleteAsync(resourceGroupName, applicationSecurityGroupName).block();
    }

    /**
     * Creates or updates an application security group.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @param parameters An application security group in a resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ApplicationSecurityGroupInner>> beginCreateOrUpdateWithResponseAsync(String resourceGroupName, String applicationSecurityGroupName, ApplicationSecurityGroupInner parameters) {
        final String apiVersion = "2019-06-01";
        return service.beginCreateOrUpdate(this.client.getHost(), resourceGroupName, applicationSecurityGroupName, this.client.getSubscriptionId(), parameters, apiVersion);
    }

    /**
     * Creates or updates an application security group.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @param parameters An application security group in a resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ApplicationSecurityGroupInner> beginCreateOrUpdateAsync(String resourceGroupName, String applicationSecurityGroupName, ApplicationSecurityGroupInner parameters) {
        return beginCreateOrUpdateWithResponseAsync(resourceGroupName, applicationSecurityGroupName, parameters)
            .flatMap((SimpleResponse<ApplicationSecurityGroupInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Creates or updates an application security group.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @param parameters An application security group in a resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ApplicationSecurityGroupInner beginCreateOrUpdate(String resourceGroupName, String applicationSecurityGroupName, ApplicationSecurityGroupInner parameters) {
        return beginCreateOrUpdateAsync(resourceGroupName, applicationSecurityGroupName, parameters).block();
    }

    /**
     * Updates an application security group's tags.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ApplicationSecurityGroupInner>> beginUpdateTagsWithResponseAsync(String resourceGroupName, String applicationSecurityGroupName, Map<String, String> tags) {
        final String apiVersion = "2019-06-01";
        TagsObject parameters = new TagsObject();
        parameters.withTags(tags);
        return service.beginUpdateTags(this.client.getHost(), resourceGroupName, applicationSecurityGroupName, this.client.getSubscriptionId(), parameters, apiVersion);
    }

    /**
     * Updates an application security group's tags.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ApplicationSecurityGroupInner> beginUpdateTagsAsync(String resourceGroupName, String applicationSecurityGroupName, Map<String, String> tags) {
        return beginUpdateTagsWithResponseAsync(resourceGroupName, applicationSecurityGroupName, tags)
            .flatMap((SimpleResponse<ApplicationSecurityGroupInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Updates an application security group's tags.
     * 
     * @param resourceGroupName 
     * @param applicationSecurityGroupName 
     * @param tags Resource tags.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ApplicationSecurityGroupInner beginUpdateTags(String resourceGroupName, String applicationSecurityGroupName, Map<String, String> tags) {
        return beginUpdateTagsAsync(resourceGroupName, applicationSecurityGroupName, tags).block();
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
    public Mono<PagedResponse<ApplicationSecurityGroupInner>> listAllNextSinglePageAsync(String nextLink) {
        return service.listAllNext(nextLink).map(res -> new PagedResponseBase<>(
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
    public Mono<PagedResponse<ApplicationSecurityGroupInner>> listNextSinglePageAsync(String nextLink) {
        return service.listNext(nextLink).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().value(),
            res.getValue().nextLink(),
            null));
    }
}
