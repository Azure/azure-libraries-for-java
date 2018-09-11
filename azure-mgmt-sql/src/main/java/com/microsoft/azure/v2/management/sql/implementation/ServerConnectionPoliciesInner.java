/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.sql.implementation;

import com.microsoft.azure.v2.AzureProxy;
import com.microsoft.azure.v2.CloudException;
import com.microsoft.azure.v2.management.sql.ServerConnectionType;
import com.microsoft.rest.v2.BodyResponse;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import com.microsoft.rest.v2.annotations.BodyParam;
import com.microsoft.rest.v2.annotations.ExpectedResponses;
import com.microsoft.rest.v2.annotations.GET;
import com.microsoft.rest.v2.annotations.HeaderParam;
import com.microsoft.rest.v2.annotations.Host;
import com.microsoft.rest.v2.annotations.PathParam;
import com.microsoft.rest.v2.annotations.PUT;
import com.microsoft.rest.v2.annotations.QueryParam;
import com.microsoft.rest.v2.annotations.UnexpectedResponseExceptionType;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

/**
 * An instance of this class provides access to all the operations defined in
 * ServerConnectionPolicies.
 */
public final class ServerConnectionPoliciesInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private ServerConnectionPoliciesService service;

    /**
     * The service client containing this operation class.
     */
    private SqlManagementClientImpl client;

    /**
     * Initializes an instance of ServerConnectionPoliciesInner.
     *
     * @param client the instance of the service client containing this operation class.
     */
    public ServerConnectionPoliciesInner(SqlManagementClientImpl client) {
        this.service = AzureProxy.create(ServerConnectionPoliciesService.class, client);
        this.client = client;
    }

    /**
     * The interface defining all the services for ServerConnectionPolicies to
     * be used by the proxy service to perform REST calls.
     */
    @Host("https://management.azure.com")
    private interface ServerConnectionPoliciesService {
        @PUT("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/connectionPolicies/{connectionPolicyName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<BodyResponse<ServerConnectionPolicyInner>> createOrUpdate(@PathParam("subscriptionId") String subscriptionId, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("connectionPolicyName") String connectionPolicyName, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage, @BodyParam("application/json; charset=utf-8") ServerConnectionPolicyInner parameters);

        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/connectionPolicies/{connectionPolicyName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<BodyResponse<ServerConnectionPolicyInner>> get(@PathParam("subscriptionId") String subscriptionId, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("connectionPolicyName") String connectionPolicyName, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);
    }

    /**
     * Creates or updates the server's connection policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param connectionType The server connection type. Possible values include: 'Default', 'Proxy', 'Redirect'.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the ServerConnectionPolicyInner object if successful.
     */
    public ServerConnectionPolicyInner createOrUpdate(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull ServerConnectionType connectionType) {
        return createOrUpdateAsync(resourceGroupName, serverName, connectionType).blockingGet();
    }

    /**
     * Creates or updates the server's connection policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param connectionType The server connection type. Possible values include: 'Default', 'Proxy', 'Redirect'.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    public ServiceFuture<ServerConnectionPolicyInner> createOrUpdateAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull ServerConnectionType connectionType, ServiceCallback<ServerConnectionPolicyInner> serviceCallback) {
        return ServiceFuture.fromBody(createOrUpdateAsync(resourceGroupName, serverName, connectionType), serviceCallback);
    }

    /**
     * Creates or updates the server's connection policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param connectionType The server connection type. Possible values include: 'Default', 'Proxy', 'Redirect'.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Single<BodyResponse<ServerConnectionPolicyInner>> createOrUpdateWithRestResponseAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull ServerConnectionType connectionType) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
        }
        if (connectionType == null) {
            throw new IllegalArgumentException("Parameter connectionType is required and cannot be null.");
        }
        final String connectionPolicyName = "default";
        final String apiVersion = "2014-04-01";
        ServerConnectionPolicyInner parameters = new ServerConnectionPolicyInner();
        parameters.withConnectionType(connectionType);
        return service.createOrUpdate(this.client.subscriptionId(), resourceGroupName, serverName, connectionPolicyName, apiVersion, this.client.acceptLanguage(), parameters);
    }

    /**
     * Creates or updates the server's connection policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param connectionType The server connection type. Possible values include: 'Default', 'Proxy', 'Redirect'.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Maybe<ServerConnectionPolicyInner> createOrUpdateAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull ServerConnectionType connectionType) {
        return createOrUpdateWithRestResponseAsync(resourceGroupName, serverName, connectionType)
            .flatMapMaybe((BodyResponse<ServerConnectionPolicyInner> res) -> res.body() == null ? Maybe.empty() : Maybe.just(res.body()));
    }

    /**
     * Gets the server's secure connection policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the ServerConnectionPolicyInner object if successful.
     */
    public ServerConnectionPolicyInner get(@NonNull String resourceGroupName, @NonNull String serverName) {
        return getAsync(resourceGroupName, serverName).blockingGet();
    }

    /**
     * Gets the server's secure connection policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    public ServiceFuture<ServerConnectionPolicyInner> getAsync(@NonNull String resourceGroupName, @NonNull String serverName, ServiceCallback<ServerConnectionPolicyInner> serviceCallback) {
        return ServiceFuture.fromBody(getAsync(resourceGroupName, serverName), serviceCallback);
    }

    /**
     * Gets the server's secure connection policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Single<BodyResponse<ServerConnectionPolicyInner>> getWithRestResponseAsync(@NonNull String resourceGroupName, @NonNull String serverName) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
        }
        final String connectionPolicyName = "default";
        final String apiVersion = "2014-04-01";
        return service.get(this.client.subscriptionId(), resourceGroupName, serverName, connectionPolicyName, apiVersion, this.client.acceptLanguage());
    }

    /**
     * Gets the server's secure connection policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Maybe<ServerConnectionPolicyInner> getAsync(@NonNull String resourceGroupName, @NonNull String serverName) {
        return getWithRestResponseAsync(resourceGroupName, serverName)
            .flatMapMaybe((BodyResponse<ServerConnectionPolicyInner> res) -> res.body() == null ? Maybe.empty() : Maybe.just(res.body()));
    }
}