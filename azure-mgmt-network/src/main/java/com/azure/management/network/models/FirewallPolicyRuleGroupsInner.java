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
import com.azure.core.util.polling.AsyncPollResponse;
import java.nio.ByteBuffer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * FirewallPolicyRuleGroups.
 */
public final class FirewallPolicyRuleGroupsInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private FirewallPolicyRuleGroupsService service;

    /**
     * The service client containing this operation class.
     */
    private NetworkManagementClientImpl client;

    /**
     * Initializes an instance of FirewallPolicyRuleGroupsInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public FirewallPolicyRuleGroupsInner(NetworkManagementClientImpl client) {
        this.service = RestProxy.create(FirewallPolicyRuleGroupsService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * NetworkManagementClientFirewallPolicyRuleGroups to be used by the proxy
     * service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "NetworkManagementClientFirewallPolicyRuleGroups")
    private interface FirewallPolicyRuleGroupsService {
        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/firewallPolicies/{firewallPolicyName}/ruleGroups/{ruleGroupName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<Flux<ByteBuffer>>> delete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("firewallPolicyName") String firewallPolicyName, @PathParam("ruleGroupName") String ruleGroupName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/firewallPolicies/{firewallPolicyName}/ruleGroups/{ruleGroupName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<FirewallPolicyRuleGroupInner>> get(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("firewallPolicyName") String firewallPolicyName, @PathParam("ruleGroupName") String ruleGroupName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/firewallPolicies/{firewallPolicyName}/ruleGroups/{ruleGroupName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<Flux<ByteBuffer>>> createOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("firewallPolicyName") String firewallPolicyName, @PathParam("ruleGroupName") String ruleGroupName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") FirewallPolicyRuleGroupInner parameters, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/firewallPolicies/{firewallPolicyName}/ruleGroups")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<FirewallPolicyRuleGroupListResultInner>> list(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("firewallPolicyName") String firewallPolicyName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/firewallPolicies/{firewallPolicyName}/ruleGroups/{ruleGroupName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<Response<Void>> beginDelete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("firewallPolicyName") String firewallPolicyName, @PathParam("ruleGroupName") String ruleGroupName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/firewallPolicies/{firewallPolicyName}/ruleGroups/{ruleGroupName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<FirewallPolicyRuleGroupInner>> beginCreateOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("firewallPolicyName") String firewallPolicyName, @PathParam("ruleGroupName") String ruleGroupName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") FirewallPolicyRuleGroupInner parameters, @QueryParam("api-version") String apiVersion);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<FirewallPolicyRuleGroupListResultInner>> listNext(@PathParam(value = "nextLink", encoded = true) String nextLink);
    }

    /**
     * Deletes the specified FirewallPolicyRuleGroup.
     * 
     * @param resourceGroupName 
     * @param firewallPolicyName 
     * @param ruleGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Flux<ByteBuffer>>> deleteWithResponseAsync(String resourceGroupName, String firewallPolicyName, String ruleGroupName) {
        final String apiVersion = "2019-06-01";
        return service.delete(this.client.getHost(), resourceGroupName, firewallPolicyName, ruleGroupName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Deletes the specified FirewallPolicyRuleGroup.
     * 
     * @param resourceGroupName 
     * @param firewallPolicyName 
     * @param ruleGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteAsync(String resourceGroupName, String firewallPolicyName, String ruleGroupName) {
        Mono<SimpleResponse<Flux<ByteBuffer>>> response = deleteWithResponseAsync(resourceGroupName, firewallPolicyName, ruleGroupName);
        return client.<Void, Void>getLroResultAsync(response, client.getHttpPipeline(), Void.class, Void.class)
            .last()
            .flatMap(AsyncPollResponse::getFinalResult);
    }

    /**
     * Deletes the specified FirewallPolicyRuleGroup.
     * 
     * @param resourceGroupName 
     * @param firewallPolicyName 
     * @param ruleGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void delete(String resourceGroupName, String firewallPolicyName, String ruleGroupName) {
        deleteAsync(resourceGroupName, firewallPolicyName, ruleGroupName).block();
    }

    /**
     * Gets the specified FirewallPolicyRuleGroup.
     * 
     * @param resourceGroupName 
     * @param firewallPolicyName 
     * @param ruleGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<FirewallPolicyRuleGroupInner>> getWithResponseAsync(String resourceGroupName, String firewallPolicyName, String ruleGroupName) {
        final String apiVersion = "2019-06-01";
        return service.get(this.client.getHost(), resourceGroupName, firewallPolicyName, ruleGroupName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Gets the specified FirewallPolicyRuleGroup.
     * 
     * @param resourceGroupName 
     * @param firewallPolicyName 
     * @param ruleGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<FirewallPolicyRuleGroupInner> getAsync(String resourceGroupName, String firewallPolicyName, String ruleGroupName) {
        return getWithResponseAsync(resourceGroupName, firewallPolicyName, ruleGroupName)
            .flatMap((SimpleResponse<FirewallPolicyRuleGroupInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Gets the specified FirewallPolicyRuleGroup.
     * 
     * @param resourceGroupName 
     * @param firewallPolicyName 
     * @param ruleGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public FirewallPolicyRuleGroupInner get(String resourceGroupName, String firewallPolicyName, String ruleGroupName) {
        return getAsync(resourceGroupName, firewallPolicyName, ruleGroupName).block();
    }

    /**
     * Creates or updates the specified FirewallPolicyRuleGroup.
     * 
     * @param resourceGroupName 
     * @param firewallPolicyName 
     * @param ruleGroupName 
     * @param parameters Rule Group resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Flux<ByteBuffer>>> createOrUpdateWithResponseAsync(String resourceGroupName, String firewallPolicyName, String ruleGroupName, FirewallPolicyRuleGroupInner parameters) {
        final String apiVersion = "2019-06-01";
        return service.createOrUpdate(this.client.getHost(), resourceGroupName, firewallPolicyName, ruleGroupName, this.client.getSubscriptionId(), parameters, apiVersion);
    }

    /**
     * Creates or updates the specified FirewallPolicyRuleGroup.
     * 
     * @param resourceGroupName 
     * @param firewallPolicyName 
     * @param ruleGroupName 
     * @param parameters Rule Group resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<FirewallPolicyRuleGroupInner> createOrUpdateAsync(String resourceGroupName, String firewallPolicyName, String ruleGroupName, FirewallPolicyRuleGroupInner parameters) {
        Mono<SimpleResponse<Flux<ByteBuffer>>> response = createOrUpdateWithResponseAsync(resourceGroupName, firewallPolicyName, ruleGroupName, parameters);
        return client.<FirewallPolicyRuleGroupInner, FirewallPolicyRuleGroupInner>getLroResultAsync(response, client.getHttpPipeline(), FirewallPolicyRuleGroupInner.class, FirewallPolicyRuleGroupInner.class)
            .last()
            .flatMap(AsyncPollResponse::getFinalResult);
    }

    /**
     * Creates or updates the specified FirewallPolicyRuleGroup.
     * 
     * @param resourceGroupName 
     * @param firewallPolicyName 
     * @param ruleGroupName 
     * @param parameters Rule Group resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public FirewallPolicyRuleGroupInner createOrUpdate(String resourceGroupName, String firewallPolicyName, String ruleGroupName, FirewallPolicyRuleGroupInner parameters) {
        return createOrUpdateAsync(resourceGroupName, firewallPolicyName, ruleGroupName, parameters).block();
    }

    /**
     * Lists all FirewallPolicyRuleGroups in a FirewallPolicy resource.
     * 
     * @param resourceGroupName 
     * @param firewallPolicyName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<FirewallPolicyRuleGroupInner>> listSinglePageAsync(String resourceGroupName, String firewallPolicyName) {
        final String apiVersion = "2019-06-01";
        return service.list(this.client.getHost(), resourceGroupName, firewallPolicyName, this.client.getSubscriptionId(), apiVersion).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().value(),
            res.getValue().nextLink(),
            null));
    }

    /**
     * Lists all FirewallPolicyRuleGroups in a FirewallPolicy resource.
     * 
     * @param resourceGroupName 
     * @param firewallPolicyName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<FirewallPolicyRuleGroupInner> listAsync(String resourceGroupName, String firewallPolicyName) {
        return new PagedFlux<>(
            () -> listSinglePageAsync(resourceGroupName, firewallPolicyName),
            nextLink -> listNextSinglePageAsync(nextLink));
    }

    /**
     * Lists all FirewallPolicyRuleGroups in a FirewallPolicy resource.
     * 
     * @param resourceGroupName 
     * @param firewallPolicyName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<FirewallPolicyRuleGroupInner> list(String resourceGroupName, String firewallPolicyName) {
        return new PagedIterable<>(listAsync(resourceGroupName, firewallPolicyName));
    }

    /**
     * Deletes the specified FirewallPolicyRuleGroup.
     * 
     * @param resourceGroupName 
     * @param firewallPolicyName 
     * @param ruleGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> beginDeleteWithResponseAsync(String resourceGroupName, String firewallPolicyName, String ruleGroupName) {
        final String apiVersion = "2019-06-01";
        return service.beginDelete(this.client.getHost(), resourceGroupName, firewallPolicyName, ruleGroupName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Deletes the specified FirewallPolicyRuleGroup.
     * 
     * @param resourceGroupName 
     * @param firewallPolicyName 
     * @param ruleGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> beginDeleteAsync(String resourceGroupName, String firewallPolicyName, String ruleGroupName) {
        return beginDeleteWithResponseAsync(resourceGroupName, firewallPolicyName, ruleGroupName)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Deletes the specified FirewallPolicyRuleGroup.
     * 
     * @param resourceGroupName 
     * @param firewallPolicyName 
     * @param ruleGroupName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void beginDelete(String resourceGroupName, String firewallPolicyName, String ruleGroupName) {
        beginDeleteAsync(resourceGroupName, firewallPolicyName, ruleGroupName).block();
    }

    /**
     * Creates or updates the specified FirewallPolicyRuleGroup.
     * 
     * @param resourceGroupName 
     * @param firewallPolicyName 
     * @param ruleGroupName 
     * @param parameters Rule Group resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<FirewallPolicyRuleGroupInner>> beginCreateOrUpdateWithResponseAsync(String resourceGroupName, String firewallPolicyName, String ruleGroupName, FirewallPolicyRuleGroupInner parameters) {
        final String apiVersion = "2019-06-01";
        return service.beginCreateOrUpdate(this.client.getHost(), resourceGroupName, firewallPolicyName, ruleGroupName, this.client.getSubscriptionId(), parameters, apiVersion);
    }

    /**
     * Creates or updates the specified FirewallPolicyRuleGroup.
     * 
     * @param resourceGroupName 
     * @param firewallPolicyName 
     * @param ruleGroupName 
     * @param parameters Rule Group resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<FirewallPolicyRuleGroupInner> beginCreateOrUpdateAsync(String resourceGroupName, String firewallPolicyName, String ruleGroupName, FirewallPolicyRuleGroupInner parameters) {
        return beginCreateOrUpdateWithResponseAsync(resourceGroupName, firewallPolicyName, ruleGroupName, parameters)
            .flatMap((SimpleResponse<FirewallPolicyRuleGroupInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Creates or updates the specified FirewallPolicyRuleGroup.
     * 
     * @param resourceGroupName 
     * @param firewallPolicyName 
     * @param ruleGroupName 
     * @param parameters Rule Group resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public FirewallPolicyRuleGroupInner beginCreateOrUpdate(String resourceGroupName, String firewallPolicyName, String ruleGroupName, FirewallPolicyRuleGroupInner parameters) {
        return beginCreateOrUpdateAsync(resourceGroupName, firewallPolicyName, ruleGroupName, parameters).block();
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
    public Mono<PagedResponse<FirewallPolicyRuleGroupInner>> listNextSinglePageAsync(String nextLink) {
        return service.listNext(nextLink).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().value(),
            res.getValue().nextLink(),
            null));
    }
}
