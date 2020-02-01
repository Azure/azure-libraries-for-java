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
 * LoadBalancerLoadBalancingRules.
 */
public final class LoadBalancerLoadBalancingRulesInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private LoadBalancerLoadBalancingRulesService service;

    /**
     * The service client containing this operation class.
     */
    private NetworkManagementClientImpl client;

    /**
     * Initializes an instance of LoadBalancerLoadBalancingRulesInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public LoadBalancerLoadBalancingRulesInner(NetworkManagementClientImpl client) {
        this.service = RestProxy.create(LoadBalancerLoadBalancingRulesService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * NetworkManagementClientLoadBalancerLoadBalancingRules to be used by the
     * proxy service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "NetworkManagementClientLoadBalancerLoadBalancingRules")
    private interface LoadBalancerLoadBalancingRulesService {
        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/loadBalancers/{loadBalancerName}/loadBalancingRules")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<LoadBalancerLoadBalancingRuleListResultInner>> list(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("loadBalancerName") String loadBalancerName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/loadBalancers/{loadBalancerName}/loadBalancingRules/{loadBalancingRuleName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<LoadBalancingRuleInner>> get(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("loadBalancerName") String loadBalancerName, @PathParam("loadBalancingRuleName") String loadBalancingRuleName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<LoadBalancerLoadBalancingRuleListResultInner>> listNext(@PathParam(value = "nextLink", encoded = true) String nextLink);
    }

    /**
     * Gets all the load balancing rules in a load balancer.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<LoadBalancingRuleInner>> listSinglePageAsync(String resourceGroupName, String loadBalancerName) {
        return service.list(this.client.getHost(), resourceGroupName, loadBalancerName, this.client.getSubscriptionId(), this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }

    /**
     * Gets all the load balancing rules in a load balancer.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<LoadBalancingRuleInner> listAsync(String resourceGroupName, String loadBalancerName) {
        return new PagedFlux<>(
            () -> listSinglePageAsync(resourceGroupName, loadBalancerName),
            nextLink -> listNextSinglePageAsync(nextLink));
    }

    /**
     * Gets all the load balancing rules in a load balancer.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<LoadBalancingRuleInner> list(String resourceGroupName, String loadBalancerName) {
        return new PagedIterable<>(listAsync(resourceGroupName, loadBalancerName));
    }

    /**
     * Gets the specified load balancer load balancing rule.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @param loadBalancingRuleName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<LoadBalancingRuleInner>> getWithResponseAsync(String resourceGroupName, String loadBalancerName, String loadBalancingRuleName) {
        return service.get(this.client.getHost(), resourceGroupName, loadBalancerName, loadBalancingRuleName, this.client.getSubscriptionId(), this.client.getApiVersion());
    }

    /**
     * Gets the specified load balancer load balancing rule.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @param loadBalancingRuleName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LoadBalancingRuleInner> getAsync(String resourceGroupName, String loadBalancerName, String loadBalancingRuleName) {
        return getWithResponseAsync(resourceGroupName, loadBalancerName, loadBalancingRuleName)
            .flatMap((SimpleResponse<LoadBalancingRuleInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Gets the specified load balancer load balancing rule.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @param loadBalancingRuleName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public LoadBalancingRuleInner get(String resourceGroupName, String loadBalancerName, String loadBalancingRuleName) {
        return getAsync(resourceGroupName, loadBalancerName, loadBalancingRuleName).block();
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
    public Mono<PagedResponse<LoadBalancingRuleInner>> listNextSinglePageAsync(String nextLink) {
        return service.listNext(nextLink).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }
}
