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
 * InboundNatRules.
 */
public final class InboundNatRulesInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private InboundNatRulesService service;

    /**
     * The service client containing this operation class.
     */
    private NetworkManagementClientImpl client;

    /**
     * Initializes an instance of InboundNatRulesInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public InboundNatRulesInner(NetworkManagementClientImpl client) {
        this.service = RestProxy.create(InboundNatRulesService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * NetworkManagementClientInboundNatRules to be used by the proxy service
     * to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "NetworkManagementClientInboundNatRules")
    private interface InboundNatRulesService {
        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/loadBalancers/{loadBalancerName}/inboundNatRules")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<InboundNatRuleListResultInner>> list(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("loadBalancerName") String loadBalancerName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/loadBalancers/{loadBalancerName}/inboundNatRules/{inboundNatRuleName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<Response<Void>> delete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("loadBalancerName") String loadBalancerName, @PathParam("inboundNatRuleName") String inboundNatRuleName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/loadBalancers/{loadBalancerName}/inboundNatRules/{inboundNatRuleName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<InboundNatRuleInner>> get(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("loadBalancerName") String loadBalancerName, @PathParam("inboundNatRuleName") String inboundNatRuleName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("$expand") String expand, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/loadBalancers/{loadBalancerName}/inboundNatRules/{inboundNatRuleName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<InboundNatRuleInner>> createOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("loadBalancerName") String loadBalancerName, @PathParam("inboundNatRuleName") String inboundNatRuleName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") InboundNatRuleInner inboundNatRuleParameters, @QueryParam("api-version") String apiVersion);

        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/loadBalancers/{loadBalancerName}/inboundNatRules/{inboundNatRuleName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<Response<Void>> beginDelete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("loadBalancerName") String loadBalancerName, @PathParam("inboundNatRuleName") String inboundNatRuleName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/loadBalancers/{loadBalancerName}/inboundNatRules/{inboundNatRuleName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<InboundNatRuleInner>> beginCreateOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("loadBalancerName") String loadBalancerName, @PathParam("inboundNatRuleName") String inboundNatRuleName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") InboundNatRuleInner inboundNatRuleParameters, @QueryParam("api-version") String apiVersion);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<InboundNatRuleListResultInner>> listNext(@PathParam(value = "nextLink", encoded = true) String nextLink);
    }

    /**
     * Gets all the inbound nat rules in a load balancer.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<InboundNatRuleInner>> listSinglePageAsync(String resourceGroupName, String loadBalancerName) {
        return service.list(this.client.getHost(), resourceGroupName, loadBalancerName, this.client.getSubscriptionId(), this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }

    /**
     * Gets all the inbound nat rules in a load balancer.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<InboundNatRuleInner> listAsync(String resourceGroupName, String loadBalancerName) {
        return new PagedFlux<>(
            () -> listSinglePageAsync(resourceGroupName, loadBalancerName),
            nextLink -> listNextSinglePageAsync(nextLink));
    }

    /**
     * Gets all the inbound nat rules in a load balancer.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<InboundNatRuleInner> list(String resourceGroupName, String loadBalancerName) {
        return new PagedIterable<>(listAsync(resourceGroupName, loadBalancerName));
    }

    /**
     * Deletes the specified load balancer inbound nat rule.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @param inboundNatRuleName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> deleteWithResponseAsync(String resourceGroupName, String loadBalancerName, String inboundNatRuleName) {
        return service.delete(this.client.getHost(), resourceGroupName, loadBalancerName, inboundNatRuleName, this.client.getSubscriptionId(), this.client.getApiVersion());
    }

    /**
     * Deletes the specified load balancer inbound nat rule.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @param inboundNatRuleName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteAsync(String resourceGroupName, String loadBalancerName, String inboundNatRuleName) {
        return deleteWithResponseAsync(resourceGroupName, loadBalancerName, inboundNatRuleName)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Deletes the specified load balancer inbound nat rule.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @param inboundNatRuleName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void delete(String resourceGroupName, String loadBalancerName, String inboundNatRuleName) {
        deleteAsync(resourceGroupName, loadBalancerName, inboundNatRuleName).block();
    }

    /**
     * Gets the specified load balancer inbound nat rule.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @param inboundNatRuleName 
     * @param expand 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<InboundNatRuleInner>> getWithResponseAsync(String resourceGroupName, String loadBalancerName, String inboundNatRuleName, String expand) {
        return service.get(this.client.getHost(), resourceGroupName, loadBalancerName, inboundNatRuleName, this.client.getSubscriptionId(), expand, this.client.getApiVersion());
    }

    /**
     * Gets the specified load balancer inbound nat rule.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @param inboundNatRuleName 
     * @param expand 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<InboundNatRuleInner> getAsync(String resourceGroupName, String loadBalancerName, String inboundNatRuleName, String expand) {
        return getWithResponseAsync(resourceGroupName, loadBalancerName, inboundNatRuleName, expand)
            .flatMap((SimpleResponse<InboundNatRuleInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Gets the specified load balancer inbound nat rule.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @param inboundNatRuleName 
     * @param expand 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public InboundNatRuleInner get(String resourceGroupName, String loadBalancerName, String inboundNatRuleName, String expand) {
        return getAsync(resourceGroupName, loadBalancerName, inboundNatRuleName, expand).block();
    }

    /**
     * Creates or updates a load balancer inbound nat rule.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @param inboundNatRuleName 
     * @param inboundNatRuleParameters Inbound NAT rule of the load balancer.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<InboundNatRuleInner>> createOrUpdateWithResponseAsync(String resourceGroupName, String loadBalancerName, String inboundNatRuleName, InboundNatRuleInner inboundNatRuleParameters) {
        return service.createOrUpdate(this.client.getHost(), resourceGroupName, loadBalancerName, inboundNatRuleName, this.client.getSubscriptionId(), inboundNatRuleParameters, this.client.getApiVersion());
    }

    /**
     * Creates or updates a load balancer inbound nat rule.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @param inboundNatRuleName 
     * @param inboundNatRuleParameters Inbound NAT rule of the load balancer.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<InboundNatRuleInner> createOrUpdateAsync(String resourceGroupName, String loadBalancerName, String inboundNatRuleName, InboundNatRuleInner inboundNatRuleParameters) {
        return createOrUpdateWithResponseAsync(resourceGroupName, loadBalancerName, inboundNatRuleName, inboundNatRuleParameters)
            .flatMap((SimpleResponse<InboundNatRuleInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Creates or updates a load balancer inbound nat rule.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @param inboundNatRuleName 
     * @param inboundNatRuleParameters Inbound NAT rule of the load balancer.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public InboundNatRuleInner createOrUpdate(String resourceGroupName, String loadBalancerName, String inboundNatRuleName, InboundNatRuleInner inboundNatRuleParameters) {
        return createOrUpdateAsync(resourceGroupName, loadBalancerName, inboundNatRuleName, inboundNatRuleParameters).block();
    }

    /**
     * Deletes the specified load balancer inbound nat rule.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @param inboundNatRuleName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> beginDeleteWithResponseAsync(String resourceGroupName, String loadBalancerName, String inboundNatRuleName) {
        return service.beginDelete(this.client.getHost(), resourceGroupName, loadBalancerName, inboundNatRuleName, this.client.getSubscriptionId(), this.client.getApiVersion());
    }

    /**
     * Deletes the specified load balancer inbound nat rule.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @param inboundNatRuleName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> beginDeleteAsync(String resourceGroupName, String loadBalancerName, String inboundNatRuleName) {
        return beginDeleteWithResponseAsync(resourceGroupName, loadBalancerName, inboundNatRuleName)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Deletes the specified load balancer inbound nat rule.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @param inboundNatRuleName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void beginDelete(String resourceGroupName, String loadBalancerName, String inboundNatRuleName) {
        beginDeleteAsync(resourceGroupName, loadBalancerName, inboundNatRuleName).block();
    }

    /**
     * Creates or updates a load balancer inbound nat rule.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @param inboundNatRuleName 
     * @param inboundNatRuleParameters Inbound NAT rule of the load balancer.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<InboundNatRuleInner>> beginCreateOrUpdateWithResponseAsync(String resourceGroupName, String loadBalancerName, String inboundNatRuleName, InboundNatRuleInner inboundNatRuleParameters) {
        return service.beginCreateOrUpdate(this.client.getHost(), resourceGroupName, loadBalancerName, inboundNatRuleName, this.client.getSubscriptionId(), inboundNatRuleParameters, this.client.getApiVersion());
    }

    /**
     * Creates or updates a load balancer inbound nat rule.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @param inboundNatRuleName 
     * @param inboundNatRuleParameters Inbound NAT rule of the load balancer.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<InboundNatRuleInner> beginCreateOrUpdateAsync(String resourceGroupName, String loadBalancerName, String inboundNatRuleName, InboundNatRuleInner inboundNatRuleParameters) {
        return beginCreateOrUpdateWithResponseAsync(resourceGroupName, loadBalancerName, inboundNatRuleName, inboundNatRuleParameters)
            .flatMap((SimpleResponse<InboundNatRuleInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Creates or updates a load balancer inbound nat rule.
     * 
     * @param resourceGroupName 
     * @param loadBalancerName 
     * @param inboundNatRuleName 
     * @param inboundNatRuleParameters Inbound NAT rule of the load balancer.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public InboundNatRuleInner beginCreateOrUpdate(String resourceGroupName, String loadBalancerName, String inboundNatRuleName, InboundNatRuleInner inboundNatRuleParameters) {
        return beginCreateOrUpdateAsync(resourceGroupName, loadBalancerName, inboundNatRuleName, inboundNatRuleParameters).block();
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
    public Mono<PagedResponse<InboundNatRuleInner>> listNextSinglePageAsync(String nextLink) {
        return service.listNext(nextLink).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }
}
