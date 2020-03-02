// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.sql.models;

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
 * RestorableDroppedDatabases.
 */
public final class RestorableDroppedDatabasesInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private RestorableDroppedDatabasesService service;

    /**
     * The service client containing this operation class.
     */
    private SqlManagementClientImpl client;

    /**
     * Initializes an instance of RestorableDroppedDatabasesInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public RestorableDroppedDatabasesInner(SqlManagementClientImpl client) {
        this.service = RestProxy.create(RestorableDroppedDatabasesService.class, client.getHttpPipeline(), client.getSerializerAdapter());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * SqlManagementClientRestorableDroppedDatabases to be used by the proxy
     * service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "SqlManagementClientRestorableDroppedDatabases")
    private interface RestorableDroppedDatabasesService {
        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/restorableDroppedDatabases/{restorableDroppededDatabaseId}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<RestorableDroppedDatabaseInner>> get(@HostParam("$host") String host, @PathParam("subscriptionId") String subscriptionId, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("restorableDroppededDatabaseId") String restorableDroppededDatabaseId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/restorableDroppedDatabases")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<RestorableDroppedDatabaseListResultInner>> listByServer(@HostParam("$host") String host, @PathParam("subscriptionId") String subscriptionId, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @QueryParam("api-version") String apiVersion);
    }

    /**
     * Gets a deleted database that can be restored.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param restorableDroppededDatabaseId 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<RestorableDroppedDatabaseInner>> getWithResponseAsync(String resourceGroupName, String serverName, String restorableDroppededDatabaseId) {
        final String apiVersion = "2014-04-01";
        return service.get(this.client.getHost(), this.client.getSubscriptionId(), resourceGroupName, serverName, restorableDroppededDatabaseId, apiVersion);
    }

    /**
     * Gets a deleted database that can be restored.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param restorableDroppededDatabaseId 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<RestorableDroppedDatabaseInner> getAsync(String resourceGroupName, String serverName, String restorableDroppededDatabaseId) {
        return getWithResponseAsync(resourceGroupName, serverName, restorableDroppededDatabaseId)
            .flatMap((SimpleResponse<RestorableDroppedDatabaseInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Gets a deleted database that can be restored.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param restorableDroppededDatabaseId 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public RestorableDroppedDatabaseInner get(String resourceGroupName, String serverName, String restorableDroppededDatabaseId) {
        return getAsync(resourceGroupName, serverName, restorableDroppededDatabaseId).block();
    }

    /**
     * Gets a list of deleted databases that can be restored.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<RestorableDroppedDatabaseInner>> listByServerSinglePageAsync(String resourceGroupName, String serverName) {
        final String apiVersion = "2014-04-01";
        return service.listByServer(this.client.getHost(), this.client.getSubscriptionId(), resourceGroupName, serverName, apiVersion).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().value(),
            null,
            null));
    }

    /**
     * Gets a list of deleted databases that can be restored.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<RestorableDroppedDatabaseInner> listByServerAsync(String resourceGroupName, String serverName) {
        return new PagedFlux<>(
            () -> listByServerSinglePageAsync(resourceGroupName, serverName));
    }

    /**
     * Gets a list of deleted databases that can be restored.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<RestorableDroppedDatabaseInner> listByServer(String resourceGroupName, String serverName) {
        return new PagedIterable<>(listByServerAsync(resourceGroupName, serverName));
    }
}
