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
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.RestProxy;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.management.CloudException;
import com.azure.management.resources.fluentcore.collection.InnerSupportsDelete;
import com.azure.management.resources.fluentcore.collection.InnerSupportsGet;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * ExpressRouteGateways.
 */
public final class ExpressRouteGatewaysInner implements InnerSupportsGet<ExpressRouteGatewayInner>, InnerSupportsDelete<Void> {
    /**
     * The proxy service used to perform REST calls.
     */
    private ExpressRouteGatewaysService service;

    /**
     * The service client containing this operation class.
     */
    private NetworkManagementClientImpl client;

    /**
     * Initializes an instance of ExpressRouteGatewaysInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public ExpressRouteGatewaysInner(NetworkManagementClientImpl client) {
        this.service = RestProxy.create(ExpressRouteGatewaysService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * NetworkManagementClientExpressRouteGateways to be used by the proxy
     * service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "NetworkManagementClientExpressRouteGateways")
    private interface ExpressRouteGatewaysService {
        @Get("/subscriptions/{subscriptionId}/providers/Microsoft.Network/expressRouteGateways")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ExpressRouteGatewayListInner>> list(@HostParam("$host") String host, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/expressRouteGateways")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ExpressRouteGatewayListInner>> listByResourceGroup(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/expressRouteGateways/{expressRouteGatewayName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ExpressRouteGatewayInner>> createOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("expressRouteGatewayName") String expressRouteGatewayName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") ExpressRouteGatewayInner putExpressRouteGatewayParameters, @QueryParam("api-version") String apiVersion);

        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/expressRouteGateways/{expressRouteGatewayName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ExpressRouteGatewayInner>> getByResourceGroup(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("expressRouteGatewayName") String expressRouteGatewayName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/expressRouteGateways/{expressRouteGatewayName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<Response<Void>> delete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("expressRouteGatewayName") String expressRouteGatewayName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/expressRouteGateways/{expressRouteGatewayName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ExpressRouteGatewayInner>> beginCreateOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("expressRouteGatewayName") String expressRouteGatewayName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json") ExpressRouteGatewayInner putExpressRouteGatewayParameters, @QueryParam("api-version") String apiVersion);

        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/expressRouteGateways/{expressRouteGatewayName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<Response<Void>> beginDelete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("expressRouteGatewayName") String expressRouteGatewayName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);
    }

    /**
     * Lists ExpressRoute gateways under a given subscription.
     * 
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ExpressRouteGatewayListInner>> listWithResponseAsync() {
        return service.list(this.client.getHost(), this.client.getSubscriptionId(), this.client.getApiVersion());
    }

    /**
     * Lists ExpressRoute gateways under a given subscription.
     * 
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ExpressRouteGatewayListInner> listAsync() {
        return listWithResponseAsync()
            .flatMap((SimpleResponse<ExpressRouteGatewayListInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Lists ExpressRoute gateways under a given subscription.
     * 
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ExpressRouteGatewayListInner list() {
        return listAsync().block();
    }

    /**
     * Lists ExpressRoute gateways in a given resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ExpressRouteGatewayListInner>> listByResourceGroupWithResponseAsync(String resourceGroupName) {
        return service.listByResourceGroup(this.client.getHost(), resourceGroupName, this.client.getSubscriptionId(), this.client.getApiVersion());
    }

    /**
     * Lists ExpressRoute gateways in a given resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ExpressRouteGatewayListInner> listByResourceGroupAsync(String resourceGroupName) {
        return listByResourceGroupWithResponseAsync(resourceGroupName)
            .flatMap((SimpleResponse<ExpressRouteGatewayListInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Lists ExpressRoute gateways in a given resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ExpressRouteGatewayListInner listByResourceGroup(String resourceGroupName) {
        return listByResourceGroupAsync(resourceGroupName).block();
    }

    /**
     * Creates or updates a ExpressRoute gateway in a specified resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param expressRouteGatewayName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param putExpressRouteGatewayParameters ExpressRoute gateway resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ExpressRouteGatewayInner>> createOrUpdateWithResponseAsync(String resourceGroupName, String expressRouteGatewayName, ExpressRouteGatewayInner putExpressRouteGatewayParameters) {
        return service.createOrUpdate(this.client.getHost(), resourceGroupName, expressRouteGatewayName, this.client.getSubscriptionId(), putExpressRouteGatewayParameters, this.client.getApiVersion());
    }

    /**
     * Creates or updates a ExpressRoute gateway in a specified resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param expressRouteGatewayName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param putExpressRouteGatewayParameters ExpressRoute gateway resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ExpressRouteGatewayInner> createOrUpdateAsync(String resourceGroupName, String expressRouteGatewayName, ExpressRouteGatewayInner putExpressRouteGatewayParameters) {
        return createOrUpdateWithResponseAsync(resourceGroupName, expressRouteGatewayName, putExpressRouteGatewayParameters)
            .flatMap((SimpleResponse<ExpressRouteGatewayInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Creates or updates a ExpressRoute gateway in a specified resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param expressRouteGatewayName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param putExpressRouteGatewayParameters ExpressRoute gateway resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ExpressRouteGatewayInner createOrUpdate(String resourceGroupName, String expressRouteGatewayName, ExpressRouteGatewayInner putExpressRouteGatewayParameters) {
        return createOrUpdateAsync(resourceGroupName, expressRouteGatewayName, putExpressRouteGatewayParameters).block();
    }

    /**
     * Fetches the details of a ExpressRoute gateway in a resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param expressRouteGatewayName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ExpressRouteGatewayInner>> getByResourceGroupWithResponseAsync(String resourceGroupName, String expressRouteGatewayName) {
        return service.getByResourceGroup(this.client.getHost(), resourceGroupName, expressRouteGatewayName, this.client.getSubscriptionId(), this.client.getApiVersion());
    }

    /**
     * Fetches the details of a ExpressRoute gateway in a resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param expressRouteGatewayName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ExpressRouteGatewayInner> getByResourceGroupAsync(String resourceGroupName, String expressRouteGatewayName) {
        return getByResourceGroupWithResponseAsync(resourceGroupName, expressRouteGatewayName)
            .flatMap((SimpleResponse<ExpressRouteGatewayInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Fetches the details of a ExpressRoute gateway in a resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param expressRouteGatewayName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ExpressRouteGatewayInner getByResourceGroup(String resourceGroupName, String expressRouteGatewayName) {
        return getByResourceGroupAsync(resourceGroupName, expressRouteGatewayName).block();
    }

    /**
     * Deletes the specified ExpressRoute gateway in a resource group. An ExpressRoute gateway resource can only be deleted when there are no connection subresources.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param expressRouteGatewayName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> deleteWithResponseAsync(String resourceGroupName, String expressRouteGatewayName) {
        return service.delete(this.client.getHost(), resourceGroupName, expressRouteGatewayName, this.client.getSubscriptionId(), this.client.getApiVersion());
    }

    /**
     * Deletes the specified ExpressRoute gateway in a resource group. An ExpressRoute gateway resource can only be deleted when there are no connection subresources.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param expressRouteGatewayName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteAsync(String resourceGroupName, String expressRouteGatewayName) {
        return deleteWithResponseAsync(resourceGroupName, expressRouteGatewayName)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Deletes the specified ExpressRoute gateway in a resource group. An ExpressRoute gateway resource can only be deleted when there are no connection subresources.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param expressRouteGatewayName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void delete(String resourceGroupName, String expressRouteGatewayName) {
        deleteAsync(resourceGroupName, expressRouteGatewayName).block();
    }

    /**
     * Creates or updates a ExpressRoute gateway in a specified resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param expressRouteGatewayName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param putExpressRouteGatewayParameters ExpressRoute gateway resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ExpressRouteGatewayInner>> beginCreateOrUpdateWithResponseAsync(String resourceGroupName, String expressRouteGatewayName, ExpressRouteGatewayInner putExpressRouteGatewayParameters) {
        return service.beginCreateOrUpdate(this.client.getHost(), resourceGroupName, expressRouteGatewayName, this.client.getSubscriptionId(), putExpressRouteGatewayParameters, this.client.getApiVersion());
    }

    /**
     * Creates or updates a ExpressRoute gateway in a specified resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param expressRouteGatewayName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param putExpressRouteGatewayParameters ExpressRoute gateway resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ExpressRouteGatewayInner> beginCreateOrUpdateAsync(String resourceGroupName, String expressRouteGatewayName, ExpressRouteGatewayInner putExpressRouteGatewayParameters) {
        return beginCreateOrUpdateWithResponseAsync(resourceGroupName, expressRouteGatewayName, putExpressRouteGatewayParameters)
            .flatMap((SimpleResponse<ExpressRouteGatewayInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Creates or updates a ExpressRoute gateway in a specified resource group.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param expressRouteGatewayName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param putExpressRouteGatewayParameters ExpressRoute gateway resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ExpressRouteGatewayInner beginCreateOrUpdate(String resourceGroupName, String expressRouteGatewayName, ExpressRouteGatewayInner putExpressRouteGatewayParameters) {
        return beginCreateOrUpdateAsync(resourceGroupName, expressRouteGatewayName, putExpressRouteGatewayParameters).block();
    }

    /**
     * Deletes the specified ExpressRoute gateway in a resource group. An ExpressRoute gateway resource can only be deleted when there are no connection subresources.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param expressRouteGatewayName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> beginDeleteWithResponseAsync(String resourceGroupName, String expressRouteGatewayName) {
        return service.beginDelete(this.client.getHost(), resourceGroupName, expressRouteGatewayName, this.client.getSubscriptionId(), this.client.getApiVersion());
    }

    /**
     * Deletes the specified ExpressRoute gateway in a resource group. An ExpressRoute gateway resource can only be deleted when there are no connection subresources.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param expressRouteGatewayName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> beginDeleteAsync(String resourceGroupName, String expressRouteGatewayName) {
        return beginDeleteWithResponseAsync(resourceGroupName, expressRouteGatewayName)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Deletes the specified ExpressRoute gateway in a resource group. An ExpressRoute gateway resource can only be deleted when there are no connection subresources.
     * 
     * @param resourceGroupName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param expressRouteGatewayName MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void beginDelete(String resourceGroupName, String expressRouteGatewayName) {
        beginDeleteAsync(resourceGroupName, expressRouteGatewayName).block();
    }
}
