// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.resources.models;

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
 * Tenants.
 */
public final class TenantsInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private TenantsService service;

    /**
     * The service client containing this operation class.
     */
    private SubscriptionClientImpl client;

    /**
     * Initializes an instance of TenantsInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public TenantsInner(SubscriptionClientImpl client) {
        this.service = RestProxy.create(TenantsService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for SubscriptionClientTenants to
     * be used by the proxy service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "SubscriptionClientTenants")
    private interface TenantsService {
        @Get("/tenants")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<TenantListResultInner>> list(@HostParam("$host") String host, @QueryParam("api-version") String apiVersion);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<TenantListResultInner>> listNext(@PathParam(value = "nextLink", encoded = true) String nextLink);
    }

    /**
     * Gets the tenants for your account.
     * 
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<TenantIdDescriptionInner>> listSinglePageAsync() {
        return service.list(this.client.getHost(), this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }

    /**
     * Gets the tenants for your account.
     * 
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<TenantIdDescriptionInner> listAsync() {
        return new PagedFlux<>(
            () -> listSinglePageAsync(),
            nextLink -> listNextSinglePageAsync(nextLink));
    }

    /**
     * Gets the tenants for your account.
     * 
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<TenantIdDescriptionInner> list() {
        return new PagedIterable<>(listAsync());
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
    public Mono<PagedResponse<TenantIdDescriptionInner>> listNextSinglePageAsync(String nextLink) {
        return service.listNext(nextLink).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }
}
