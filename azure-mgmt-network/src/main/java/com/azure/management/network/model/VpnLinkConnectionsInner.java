// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.model;

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
import com.azure.management.network.models.ErrorException;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * VpnLinkConnections.
 */
public final class VpnLinkConnectionsInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private VpnLinkConnectionsService service;

    /**
     * The service client containing this operation class.
     */
    private NetworkManagementClientImpl client;

    /**
     * Initializes an instance of VpnLinkConnectionsInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public VpnLinkConnectionsInner(NetworkManagementClientImpl client) {
        this.service = RestProxy.create(VpnLinkConnectionsService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * NetworkManagementClientVpnLinkConnections to be used by the proxy
     * service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "NetworkManagementClientVpnLinkConnections")
    private interface VpnLinkConnectionsService {
        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/vpnGateways/{gatewayName}/vpnConnections/{connectionName}/vpnLinkConnections")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<ListVpnSiteLinkConnectionsResultInner>> listByVpnConnection(@HostParam("$host") String host, @PathParam("subscriptionId") String subscriptionId, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("gatewayName") String gatewayName, @PathParam("connectionName") String connectionName, @QueryParam("api-version") String apiVersion);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<ListVpnSiteLinkConnectionsResultInner>> listByVpnConnectionNext(@PathParam(value = "nextLink", encoded = true) String nextLink);
    }

    /**
     * Retrieves all vpn site link connections for a particular virtual wan vpn gateway vpn connection.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param gatewayName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param connectionName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<VpnSiteLinkConnectionInner>> listByVpnConnectionSinglePageAsync(String resourceGroupName, String gatewayName, String connectionName) {
        return service.listByVpnConnection(this.client.getHost(), this.client.getSubscriptionId(), resourceGroupName, gatewayName, connectionName, this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }

    /**
     * Retrieves all vpn site link connections for a particular virtual wan vpn gateway vpn connection.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param gatewayName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param connectionName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<VpnSiteLinkConnectionInner> listByVpnConnectionAsync(String resourceGroupName, String gatewayName, String connectionName) {
        return new PagedFlux<>(
            () -> listByVpnConnectionSinglePageAsync(resourceGroupName, gatewayName, connectionName),
            nextLink -> listByVpnConnectionNextSinglePageAsync(nextLink));
    }

    /**
     * Retrieves all vpn site link connections for a particular virtual wan vpn gateway vpn connection.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param gatewayName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param connectionName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<VpnSiteLinkConnectionInner> listByVpnConnection(String resourceGroupName, String gatewayName, String connectionName) {
        return new PagedIterable<>(listByVpnConnectionAsync(resourceGroupName, gatewayName, connectionName));
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
    public Mono<PagedResponse<VpnSiteLinkConnectionInner>> listByVpnConnectionNextSinglePageAsync(String nextLink) {
        return service.listByVpnConnectionNext(nextLink).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }
}
