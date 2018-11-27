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
import com.microsoft.rest.v2.annotations.ExpectedResponses;
import com.microsoft.rest.v2.annotations.GET;
import com.microsoft.rest.v2.annotations.HeaderParam;
import com.microsoft.rest.v2.annotations.Host;
import com.microsoft.rest.v2.annotations.PathParam;
import com.microsoft.rest.v2.annotations.QueryParam;
import com.microsoft.rest.v2.annotations.UnexpectedResponseExceptionType;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class provides access to all the operations defined in
 * TransparentDataEncryptionActivities.
 */
public final class TransparentDataEncryptionActivitiesInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private TransparentDataEncryptionActivitiesService service;

    /**
     * The service client containing this operation class.
     */
    private SqlManagementClientImpl client;

    /**
     * Initializes an instance of TransparentDataEncryptionActivitiesInner.
     *
     * @param client the instance of the service client containing this operation class.
     */
    public TransparentDataEncryptionActivitiesInner(SqlManagementClientImpl client) {
        this.service = AzureProxy.create(TransparentDataEncryptionActivitiesService.class, client);
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * TransparentDataEncryptionActivities to be used by the proxy service to
     * perform REST calls.
     */
    @Host("https://management.azure.com")
    private interface TransparentDataEncryptionActivitiesService {
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/databases/{databaseName}/transparentDataEncryption/{transparentDataEncryptionName}/operationResults")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<BodyResponse<List<TransparentDataEncryptionActivityInner>>> listByConfiguration(@PathParam("subscriptionId") String subscriptionId, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("databaseName") String databaseName, @PathParam("transparentDataEncryptionName") String transparentDataEncryptionName, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);
    }

    /**
     * Returns a database's transparent data encryption operation result.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database for which the transparent data encryption applies.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the List&lt;TransparentDataEncryptionActivityInner&gt; object if successful.
     */
    public List<TransparentDataEncryptionActivityInner> listByConfiguration(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName) {
        return listByConfigurationAsync(resourceGroupName, serverName, databaseName).blockingGet();
    }

    /**
     * Returns a database's transparent data encryption operation result.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database for which the transparent data encryption applies.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    public ServiceFuture<List<TransparentDataEncryptionActivityInner>> listByConfigurationAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, ServiceCallback<List<TransparentDataEncryptionActivityInner>> serviceCallback) {
        return ServiceFuture.fromBody(listByConfigurationAsync(resourceGroupName, serverName, databaseName), serviceCallback);
    }

    /**
     * Returns a database's transparent data encryption operation result.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database for which the transparent data encryption applies.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Single<BodyResponse<List<TransparentDataEncryptionActivityInner>>> listByConfigurationWithRestResponseAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
        }
        if (databaseName == null) {
            throw new IllegalArgumentException("Parameter databaseName is required and cannot be null.");
        }
        final String transparentDataEncryptionName = "current";
        final String apiVersion = "2014-04-01";
        return service.listByConfiguration(this.client.subscriptionId(), resourceGroupName, serverName, databaseName, transparentDataEncryptionName, apiVersion, this.client.acceptLanguage());
    }

    /**
     * Returns a database's transparent data encryption operation result.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database for which the transparent data encryption applies.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Maybe<List<TransparentDataEncryptionActivityInner>> listByConfigurationAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName) {
        return listByConfigurationWithRestResponseAsync(resourceGroupName, serverName, databaseName)
            .flatMapMaybe((BodyResponse<List<TransparentDataEncryptionActivityInner>> res) -> res.body() == null ? Maybe.empty() : Maybe.just(res.body()));
    }
}
