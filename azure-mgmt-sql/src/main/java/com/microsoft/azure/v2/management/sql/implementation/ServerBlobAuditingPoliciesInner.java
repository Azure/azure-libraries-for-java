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
import com.microsoft.azure.v2.util.ServiceFutureUtil;
import com.microsoft.rest.v2.BodyResponse;
import com.microsoft.rest.v2.OperationDescription;
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
import com.microsoft.rest.v2.annotations.ResumeOperation;
import com.microsoft.rest.v2.annotations.UnexpectedResponseExceptionType;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

/**
 * An instance of this class provides access to all the operations defined in
 * ServerBlobAuditingPolicies.
 */
public final class ServerBlobAuditingPoliciesInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private ServerBlobAuditingPoliciesService service;

    /**
     * The service client containing this operation class.
     */
    private SqlManagementClientImpl client;

    /**
     * Initializes an instance of ServerBlobAuditingPoliciesInner.
     *
     * @param client the instance of the service client containing this operation class.
     */
    public ServerBlobAuditingPoliciesInner(SqlManagementClientImpl client) {
        this.service = AzureProxy.create(ServerBlobAuditingPoliciesService.class, client);
        this.client = client;
    }

    /**
     * The interface defining all the services for ServerBlobAuditingPolicies
     * to be used by the proxy service to perform REST calls.
     */
    @Host("https://management.azure.com")
    private interface ServerBlobAuditingPoliciesService {
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/auditingSettings/{blobAuditingPolicyName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<BodyResponse<ServerBlobAuditingPolicyInner>> get(@PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("blobAuditingPolicyName") String blobAuditingPolicyName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);

        @PUT("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/auditingSettings/{blobAuditingPolicyName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Observable<OperationStatus<ServerBlobAuditingPolicyInner>> beginCreateOrUpdate(@PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("blobAuditingPolicyName") String blobAuditingPolicyName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json; charset=utf-8") ServerBlobAuditingPolicyInner parameters, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);

        @PUT("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/auditingSettings/{blobAuditingPolicyName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<BodyResponse<ServerBlobAuditingPolicyInner>> createOrUpdate(@PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("blobAuditingPolicyName") String blobAuditingPolicyName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json; charset=utf-8") ServerBlobAuditingPolicyInner parameters, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);

        @PUT("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/auditingSettings/{blobAuditingPolicyName}")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        @ResumeOperation
        Observable<OperationStatus<ServerBlobAuditingPolicyInner>> resumeCreateOrUpdate(OperationDescription operationDescription);
    }

    /**
     * Gets a server's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the ServerBlobAuditingPolicyInner object if successful.
     */
    public ServerBlobAuditingPolicyInner get(@NonNull String resourceGroupName, @NonNull String serverName) {
        return getAsync(resourceGroupName, serverName).blockingGet();
    }

    /**
     * Gets a server's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    public ServiceFuture<ServerBlobAuditingPolicyInner> getAsync(@NonNull String resourceGroupName, @NonNull String serverName, ServiceCallback<ServerBlobAuditingPolicyInner> serviceCallback) {
        return ServiceFuture.fromBody(getAsync(resourceGroupName, serverName), serviceCallback);
    }

    /**
     * Gets a server's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Single<BodyResponse<ServerBlobAuditingPolicyInner>> getWithRestResponseAsync(@NonNull String resourceGroupName, @NonNull String serverName) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        final String blobAuditingPolicyName = "default";
        final String apiVersion = "2017-03-01-preview";
        return service.get(resourceGroupName, serverName, blobAuditingPolicyName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage());
    }

    /**
     * Gets a server's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Maybe<ServerBlobAuditingPolicyInner> getAsync(@NonNull String resourceGroupName, @NonNull String serverName) {
        return getWithRestResponseAsync(resourceGroupName, serverName)
            .flatMapMaybe((BodyResponse<ServerBlobAuditingPolicyInner> res) -> res.body() == null ? Maybe.empty() : Maybe.just(res.body()));
    }

    /**
     * Creates or updates a server's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param parameters Properties of blob auditing policy.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the ServerBlobAuditingPolicyInner object if successful.
     */
    public ServerBlobAuditingPolicyInner beginCreateOrUpdate(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull ServerBlobAuditingPolicyInner parameters) {
        return beginCreateOrUpdateAsync(resourceGroupName, serverName, parameters).blockingLast().result();
    }

    /**
     * Creates or updates a server's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param parameters Properties of blob auditing policy.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the ServiceFuture&lt;ServerBlobAuditingPolicyInner&gt; object.
     */
    public ServiceFuture<ServerBlobAuditingPolicyInner> beginCreateOrUpdateAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull ServerBlobAuditingPolicyInner parameters, ServiceCallback<ServerBlobAuditingPolicyInner> serviceCallback) {
        return ServiceFutureUtil.fromLRO(beginCreateOrUpdateAsync(resourceGroupName, serverName, parameters), serviceCallback);
    }

    /**
     * Creates or updates a server's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param parameters Properties of blob auditing policy.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the observable for the request.
     */
    public Observable<OperationStatus<ServerBlobAuditingPolicyInner>> beginCreateOrUpdateAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull ServerBlobAuditingPolicyInner parameters) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
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
        return service.beginCreateOrUpdate(resourceGroupName, serverName, blobAuditingPolicyName, this.client.subscriptionId(), parameters, apiVersion, this.client.acceptLanguage());
    }

    /**
     * Creates or updates a server's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param parameters Properties of blob auditing policy.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the ServerBlobAuditingPolicyInner object if successful.
     */
    public ServerBlobAuditingPolicyInner createOrUpdate(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull ServerBlobAuditingPolicyInner parameters) {
        return createOrUpdateAsync(resourceGroupName, serverName, parameters).blockingGet();
    }

    /**
     * Creates or updates a server's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param parameters Properties of blob auditing policy.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    public ServiceFuture<ServerBlobAuditingPolicyInner> createOrUpdateAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull ServerBlobAuditingPolicyInner parameters, ServiceCallback<ServerBlobAuditingPolicyInner> serviceCallback) {
        return ServiceFuture.fromBody(createOrUpdateAsync(resourceGroupName, serverName, parameters), serviceCallback);
    }

    /**
     * Creates or updates a server's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param parameters Properties of blob auditing policy.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Single<BodyResponse<ServerBlobAuditingPolicyInner>> createOrUpdateWithRestResponseAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull ServerBlobAuditingPolicyInner parameters) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
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
        return service.createOrUpdate(resourceGroupName, serverName, blobAuditingPolicyName, this.client.subscriptionId(), parameters, apiVersion, this.client.acceptLanguage());
    }

    /**
     * Creates or updates a server's blob auditing policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param parameters Properties of blob auditing policy.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Maybe<ServerBlobAuditingPolicyInner> createOrUpdateAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull ServerBlobAuditingPolicyInner parameters) {
        return createOrUpdateWithRestResponseAsync(resourceGroupName, serverName, parameters)
            .flatMapMaybe((BodyResponse<ServerBlobAuditingPolicyInner> res) -> res.body() == null ? Maybe.empty() : Maybe.just(res.body()));
    }

    /**
     * Creates or updates a server's blob auditing policy. (resume watch).
     *
     * @param operationDescription The OperationDescription object.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the observable for the request.
     */
    public Observable<OperationStatus<ServerBlobAuditingPolicyInner>> resumeCreateOrUpdate(OperationDescription operationDescription) {
        if (operationDescription == null) {
            throw new IllegalArgumentException("Parameter operationDescription is required and cannot be null.");
        }
        return service.resumeCreateOrUpdate(operationDescription);
    }
}