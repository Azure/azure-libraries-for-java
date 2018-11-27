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
import com.microsoft.azure.v2.OperationStatus;
import com.microsoft.azure.v2.management.sql.CreateDatabaseRestorePointDefinition;
import com.microsoft.azure.v2.util.ServiceFutureUtil;
import com.microsoft.rest.v2.BodyResponse;
import com.microsoft.rest.v2.OperationDescription;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import com.microsoft.rest.v2.VoidResponse;
import com.microsoft.rest.v2.annotations.BodyParam;
import com.microsoft.rest.v2.annotations.DELETE;
import com.microsoft.rest.v2.annotations.ExpectedResponses;
import com.microsoft.rest.v2.annotations.GET;
import com.microsoft.rest.v2.annotations.HeaderParam;
import com.microsoft.rest.v2.annotations.Host;
import com.microsoft.rest.v2.annotations.PathParam;
import com.microsoft.rest.v2.annotations.POST;
import com.microsoft.rest.v2.annotations.QueryParam;
import com.microsoft.rest.v2.annotations.ResumeOperation;
import com.microsoft.rest.v2.annotations.UnexpectedResponseExceptionType;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class provides access to all the operations defined in
 * RestorePoints.
 */
public final class RestorePointsInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private RestorePointsService service;

    /**
     * The service client containing this operation class.
     */
    private SqlManagementClientImpl client;

    /**
     * Initializes an instance of RestorePointsInner.
     *
     * @param client the instance of the service client containing this operation class.
     */
    public RestorePointsInner(SqlManagementClientImpl client) {
        this.service = AzureProxy.create(RestorePointsService.class, client);
        this.client = client;
    }

    /**
     * The interface defining all the services for RestorePoints to be used by
     * the proxy service to perform REST calls.
     */
    @Host("https://management.azure.com")
    private interface RestorePointsService {
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/databases/{databaseName}/restorePoints")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<BodyResponse<List<RestorePointInner>>> listByDatabase(@PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("databaseName") String databaseName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);

        @POST("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/databases/{databaseName}/restorePoints")
        @ExpectedResponses({200, 201, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Observable<OperationStatus<RestorePointInner>> beginCreate(@PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("databaseName") String databaseName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage, @BodyParam("application/json; charset=utf-8") CreateDatabaseRestorePointDefinition parameters);

        @POST("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/databases/{databaseName}/restorePoints")
        @ExpectedResponses({200, 201, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<BodyResponse<RestorePointInner>> create(@PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("databaseName") String databaseName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage, @BodyParam("application/json; charset=utf-8") CreateDatabaseRestorePointDefinition parameters);

        @POST("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/databases/{databaseName}/restorePoints")
        @ExpectedResponses({200, 201, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        @ResumeOperation
        Observable<OperationStatus<RestorePointInner>> resumeCreate(OperationDescription operationDescription);

        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/databases/{databaseName}/restorePoints/{restorePointName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<BodyResponse<RestorePointInner>> get(@PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("databaseName") String databaseName, @PathParam("restorePointName") String restorePointName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);

        @DELETE("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/databases/{databaseName}/restorePoints/{restorePointName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<VoidResponse> delete(@PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("databaseName") String databaseName, @PathParam("restorePointName") String restorePointName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);
    }

    /**
     * Gets a list of database restore points.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the List&lt;RestorePointInner&gt; object if successful.
     */
    public List<RestorePointInner> listByDatabase(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName) {
        return listByDatabaseAsync(resourceGroupName, serverName, databaseName).blockingGet();
    }

    /**
     * Gets a list of database restore points.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    public ServiceFuture<List<RestorePointInner>> listByDatabaseAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, ServiceCallback<List<RestorePointInner>> serviceCallback) {
        return ServiceFuture.fromBody(listByDatabaseAsync(resourceGroupName, serverName, databaseName), serviceCallback);
    }

    /**
     * Gets a list of database restore points.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Single<BodyResponse<List<RestorePointInner>>> listByDatabaseWithRestResponseAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName) {
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
        final String apiVersion = "2017-03-01-preview";
        return service.listByDatabase(resourceGroupName, serverName, databaseName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage());
    }

    /**
     * Gets a list of database restore points.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Maybe<List<RestorePointInner>> listByDatabaseAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName) {
        return listByDatabaseWithRestResponseAsync(resourceGroupName, serverName, databaseName)
            .flatMapMaybe((BodyResponse<List<RestorePointInner>> res) -> res.body() == null ? Maybe.empty() : Maybe.just(res.body()));
    }

    /**
     * Creates a restore point for a data warehouse.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param restorePointLabel The restore point label to apply.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the RestorePointInner object if successful.
     */
    public RestorePointInner beginCreate(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull String restorePointLabel) {
        return beginCreateAsync(resourceGroupName, serverName, databaseName, restorePointLabel).blockingLast().result();
    }

    /**
     * Creates a restore point for a data warehouse.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param restorePointLabel The restore point label to apply.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the ServiceFuture&lt;RestorePointInner&gt; object.
     */
    public ServiceFuture<RestorePointInner> beginCreateAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull String restorePointLabel, ServiceCallback<RestorePointInner> serviceCallback) {
        return ServiceFutureUtil.fromLRO(beginCreateAsync(resourceGroupName, serverName, databaseName, restorePointLabel), serviceCallback);
    }

    /**
     * Creates a restore point for a data warehouse.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param restorePointLabel The restore point label to apply.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the observable for the request.
     */
    public Observable<OperationStatus<RestorePointInner>> beginCreateAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull String restorePointLabel) {
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
        if (restorePointLabel == null) {
            throw new IllegalArgumentException("Parameter restorePointLabel is required and cannot be null.");
        }
        final String apiVersion = "2017-03-01-preview";
        CreateDatabaseRestorePointDefinition parameters = new CreateDatabaseRestorePointDefinition();
        parameters.withRestorePointLabel(restorePointLabel);
        return service.beginCreate(resourceGroupName, serverName, databaseName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage(), parameters);
    }

    /**
     * Creates a restore point for a data warehouse.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param restorePointLabel The restore point label to apply.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the RestorePointInner object if successful.
     */
    public RestorePointInner create(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull String restorePointLabel) {
        return createAsync(resourceGroupName, serverName, databaseName, restorePointLabel).blockingGet();
    }

    /**
     * Creates a restore point for a data warehouse.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param restorePointLabel The restore point label to apply.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    public ServiceFuture<RestorePointInner> createAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull String restorePointLabel, ServiceCallback<RestorePointInner> serviceCallback) {
        return ServiceFuture.fromBody(createAsync(resourceGroupName, serverName, databaseName, restorePointLabel), serviceCallback);
    }

    /**
     * Creates a restore point for a data warehouse.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param restorePointLabel The restore point label to apply.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Single<BodyResponse<RestorePointInner>> createWithRestResponseAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull String restorePointLabel) {
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
        if (restorePointLabel == null) {
            throw new IllegalArgumentException("Parameter restorePointLabel is required and cannot be null.");
        }
        final String apiVersion = "2017-03-01-preview";
        CreateDatabaseRestorePointDefinition parameters = new CreateDatabaseRestorePointDefinition();
        parameters.withRestorePointLabel(restorePointLabel);
        return service.create(resourceGroupName, serverName, databaseName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage(), parameters);
    }

    /**
     * Creates a restore point for a data warehouse.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param restorePointLabel The restore point label to apply.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Maybe<RestorePointInner> createAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull String restorePointLabel) {
        return createWithRestResponseAsync(resourceGroupName, serverName, databaseName, restorePointLabel)
            .flatMapMaybe((BodyResponse<RestorePointInner> res) -> res.body() == null ? Maybe.empty() : Maybe.just(res.body()));
    }

    /**
     * Creates a restore point for a data warehouse. (resume watch).
     *
     * @param operationDescription The OperationDescription object.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the observable for the request.
     */
    public Observable<OperationStatus<RestorePointInner>> resumeCreate(OperationDescription operationDescription) {
        if (operationDescription == null) {
            throw new IllegalArgumentException("Parameter operationDescription is required and cannot be null.");
        }
        return service.resumeCreate(operationDescription);
    }

    /**
     * Gets a restore point.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param restorePointName The name of the restore point.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the RestorePointInner object if successful.
     */
    public RestorePointInner get(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull String restorePointName) {
        return getAsync(resourceGroupName, serverName, databaseName, restorePointName).blockingGet();
    }

    /**
     * Gets a restore point.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param restorePointName The name of the restore point.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    public ServiceFuture<RestorePointInner> getAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull String restorePointName, ServiceCallback<RestorePointInner> serviceCallback) {
        return ServiceFuture.fromBody(getAsync(resourceGroupName, serverName, databaseName, restorePointName), serviceCallback);
    }

    /**
     * Gets a restore point.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param restorePointName The name of the restore point.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Single<BodyResponse<RestorePointInner>> getWithRestResponseAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull String restorePointName) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
        }
        if (databaseName == null) {
            throw new IllegalArgumentException("Parameter databaseName is required and cannot be null.");
        }
        if (restorePointName == null) {
            throw new IllegalArgumentException("Parameter restorePointName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        final String apiVersion = "2017-03-01-preview";
        return service.get(resourceGroupName, serverName, databaseName, restorePointName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage());
    }

    /**
     * Gets a restore point.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param restorePointName The name of the restore point.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Maybe<RestorePointInner> getAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull String restorePointName) {
        return getWithRestResponseAsync(resourceGroupName, serverName, databaseName, restorePointName)
            .flatMapMaybe((BodyResponse<RestorePointInner> res) -> res.body() == null ? Maybe.empty() : Maybe.just(res.body()));
    }

    /**
     * Deletes a restore point.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param restorePointName The name of the restore point.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    public void delete(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull String restorePointName) {
        deleteAsync(resourceGroupName, serverName, databaseName, restorePointName).blockingAwait();
    }

    /**
     * Deletes a restore point.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param restorePointName The name of the restore point.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    public ServiceFuture<Void> deleteAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull String restorePointName, ServiceCallback<Void> serviceCallback) {
        return ServiceFuture.fromBody(deleteAsync(resourceGroupName, serverName, databaseName, restorePointName), serviceCallback);
    }

    /**
     * Deletes a restore point.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param restorePointName The name of the restore point.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Single<VoidResponse> deleteWithRestResponseAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull String restorePointName) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
        }
        if (databaseName == null) {
            throw new IllegalArgumentException("Parameter databaseName is required and cannot be null.");
        }
        if (restorePointName == null) {
            throw new IllegalArgumentException("Parameter restorePointName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        final String apiVersion = "2017-03-01-preview";
        return service.delete(resourceGroupName, serverName, databaseName, restorePointName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage());
    }

    /**
     * Deletes a restore point.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param databaseName The name of the database.
     * @param restorePointName The name of the restore point.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Completable deleteAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String databaseName, @NonNull String restorePointName) {
        return deleteWithRestResponseAsync(resourceGroupName, serverName, databaseName, restorePointName)
            .toCompletable();
    }
}
