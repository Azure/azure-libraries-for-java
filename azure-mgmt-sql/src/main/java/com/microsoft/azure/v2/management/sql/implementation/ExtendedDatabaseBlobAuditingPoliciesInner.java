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
import com.microsoft.rest.v2.BodyResponse;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import com.microsoft.rest.v2.Validator;
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
 * ExtendedDatabaseBlobAuditingPolicies.
 */
public final class ExtendedDatabaseBlobAuditingPoliciesInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private ExtendedDatabaseBlobAuditingPoliciesService service;

    /**
     * The service client containing this operation class.
     */
    private SqlManagementClientImpl client;

    /**
     * Initializes an instance of ExtendedDatabaseBlobAuditingPoliciesInner.
     *
     * @param client the instance of the service client containing this operation class.
     */
    public ExtendedDatabaseBlobAuditingPoliciesInner(SqlManagementClientImpl client) {
        this.service = AzureProxy.create(ExtendedDatabaseBlobAuditingPoliciesService.class, client);
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * ExtendedDatabaseBlobAuditingPolicies to be used by the proxy service to
     * perform REST calls.
     */
    @Host("https://management.azure.com")
    private interface ExtendedDatabaseBlobAuditingPoliciesService {
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/databases/{databaseName}/extendedAuditingSettings/{blobAuditingPolicyName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<BodyResponse<ExtendedDatabaseBlobAuditingPolicyInner>> get(@PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("databaseName") String databaseName, @PathParam("blobAuditingPolicyName") String blobAuditingPolicyName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);

        @PUT("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/databases/{databaseName}/extendedAuditingSettings/{blobAuditingPolicyName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<BodyResponse<ExtendedDatabaseBlobAuditingPolicyInner>> createOrUpdate(@PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("databaseName") String databaseName, @PathParam("blobAuditingPolicyName") String blobAuditingPolicyName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json; charset=utf-8") ExtendedDatabaseBlobAuditingPolicyInner parameters, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);
    }

    /**
     * Gets an extended database's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the ExtendedDatabaseBlobAuditingPolicyInner object if successful.
     */
    public ExtendedDatabaseBlobAuditingPolicyInner get(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName) {
        return getAsync(resourceGroupName, serverName, databaseName).blockingGet();
    }

    /**
     * Gets an extended database's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    public ServiceFuture<ExtendedDatabaseBlobAuditingPolicyInner> getAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, ServiceCallback<ExtendedDatabaseBlobAuditingPolicyInner> serviceCallback) {
        return ServiceFuture.fromBody(getAsync(resourceGroupName, serverName, databaseName), serviceCallback);
    }

    /**
     * Gets an extended database's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Single<BodyResponse<ExtendedDatabaseBlobAuditingPolicyInner>> getWithRestResponseAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
        }
        if (databaseName == null) {
            throw new IllegalArgumentException("Parameter databaseName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        final String blobAuditingPolicyName = "default";
        final String apiVersion = "2017-03-01-preview";
        return service.get(resourceGroupName, serverName, databaseName, blobAuditingPolicyName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage());
    }

    /**
     * Gets an extended database's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Maybe<ExtendedDatabaseBlobAuditingPolicyInner> getAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName) {
        return getWithRestResponseAsync(resourceGroupName, serverName, databaseName)
            .flatMapMaybe((BodyResponse<ExtendedDatabaseBlobAuditingPolicyInner> res) -> res.body() == null ? Maybe.empty() : Maybe.just(res.body()));
    }

    /**
     * Creates or updates an extended database's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param parameters The extended database blob auditing policy.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the ExtendedDatabaseBlobAuditingPolicyInner object if successful.
     */
    public ExtendedDatabaseBlobAuditingPolicyInner createOrUpdate(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull ExtendedDatabaseBlobAuditingPolicyInner parameters) {
        return createOrUpdateAsync(resourceGroupName, serverName, databaseName, parameters).blockingGet();
    }

    /**
     * Creates or updates an extended database's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param parameters The extended database blob auditing policy.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    public ServiceFuture<ExtendedDatabaseBlobAuditingPolicyInner> createOrUpdateAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull ExtendedDatabaseBlobAuditingPolicyInner parameters, ServiceCallback<ExtendedDatabaseBlobAuditingPolicyInner> serviceCallback) {
        return ServiceFuture.fromBody(createOrUpdateAsync(resourceGroupName, serverName, databaseName, parameters), serviceCallback);
    }

    /**
     * Creates or updates an extended database's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param parameters The extended database blob auditing policy.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Single<BodyResponse<ExtendedDatabaseBlobAuditingPolicyInner>> createOrUpdateWithRestResponseAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull ExtendedDatabaseBlobAuditingPolicyInner parameters) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
        }
        if (databaseName == null) {
            throw new IllegalArgumentException("Parameter databaseName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (parameters == null) {
            throw new IllegalArgumentException("Parameter parameters is required and cannot be null.");
        }
        Validator.validate(parameters);
        final String blobAuditingPolicyName = "default";
        final String apiVersion = "2017-03-01-preview";
        return service.createOrUpdate(resourceGroupName, serverName, databaseName, blobAuditingPolicyName, this.client.subscriptionId(), parameters, apiVersion, this.client.acceptLanguage());
    }

    /**
     * Creates or updates an extended database's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param parameters The extended database blob auditing policy.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Maybe<ExtendedDatabaseBlobAuditingPolicyInner> createOrUpdateAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull ExtendedDatabaseBlobAuditingPolicyInner parameters) {
        return createOrUpdateWithRestResponseAsync(resourceGroupName, serverName, databaseName, parameters)
            .flatMapMaybe((BodyResponse<ExtendedDatabaseBlobAuditingPolicyInner> res) -> res.body() == null ? Maybe.empty() : Maybe.just(res.body()));
    }
}
