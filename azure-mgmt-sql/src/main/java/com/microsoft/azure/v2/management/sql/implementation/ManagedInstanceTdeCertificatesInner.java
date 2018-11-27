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
import com.microsoft.rest.v2.OperationDescription;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import com.microsoft.rest.v2.Validator;
import com.microsoft.rest.v2.VoidResponse;
import com.microsoft.rest.v2.annotations.BodyParam;
import com.microsoft.rest.v2.annotations.ExpectedResponses;
import com.microsoft.rest.v2.annotations.HeaderParam;
import com.microsoft.rest.v2.annotations.Host;
import com.microsoft.rest.v2.annotations.PathParam;
import com.microsoft.rest.v2.annotations.POST;
import com.microsoft.rest.v2.annotations.QueryParam;
import com.microsoft.rest.v2.annotations.ResumeOperation;
import com.microsoft.rest.v2.annotations.UnexpectedResponseExceptionType;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

/**
 * An instance of this class provides access to all the operations defined in
 * ManagedInstanceTdeCertificates.
 */
public final class ManagedInstanceTdeCertificatesInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private ManagedInstanceTdeCertificatesService service;

    /**
     * The service client containing this operation class.
     */
    private SqlManagementClientImpl client;

    /**
     * Initializes an instance of ManagedInstanceTdeCertificatesInner.
     *
     * @param client the instance of the service client containing this operation class.
     */
    public ManagedInstanceTdeCertificatesInner(SqlManagementClientImpl client) {
        this.service = AzureProxy.create(ManagedInstanceTdeCertificatesService.class, client);
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * ManagedInstanceTdeCertificates to be used by the proxy service to
     * perform REST calls.
     */
    @Host("https://management.azure.com")
    private interface ManagedInstanceTdeCertificatesService {
        @POST("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/managedInstances/{managedInstanceName}/tdeCertificates")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Observable<OperationStatus<Void>> beginCreate(@PathParam("resourceGroupName") String resourceGroupName, @PathParam("managedInstanceName") String managedInstanceName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json; charset=utf-8") TdeCertificateInner parameters, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);

        @POST("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/managedInstances/{managedInstanceName}/tdeCertificates")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<VoidResponse> create(@PathParam("resourceGroupName") String resourceGroupName, @PathParam("managedInstanceName") String managedInstanceName, @PathParam("subscriptionId") String subscriptionId, @BodyParam("application/json; charset=utf-8") TdeCertificateInner parameters, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);

        @POST("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/managedInstances/{managedInstanceName}/tdeCertificates")
        @ExpectedResponses({200, 202, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        @ResumeOperation
        Observable<OperationStatus<Void>> resumeCreate(OperationDescription operationDescription);
    }

    /**
     * Creates a TDE certificate for a given server.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param parameters The requested TDE certificate to be created or updated.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    public void beginCreate(@NonNull String resourceGroupName, @NonNull String managedInstanceName, @NonNull TdeCertificateInner parameters) {
        beginCreateAsync(resourceGroupName, managedInstanceName, parameters).blockingLast();
    }

    /**
     * Creates a TDE certificate for a given server.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param parameters The requested TDE certificate to be created or updated.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the ServiceFuture&lt;Void&gt; object.
     */
    public ServiceFuture<Void> beginCreateAsync(@NonNull String resourceGroupName, @NonNull String managedInstanceName, @NonNull TdeCertificateInner parameters, ServiceCallback<Void> serviceCallback) {
        return ServiceFutureUtil.fromLRO(beginCreateAsync(resourceGroupName, managedInstanceName, parameters), serviceCallback);
    }

    /**
     * Creates a TDE certificate for a given server.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param parameters The requested TDE certificate to be created or updated.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the observable for the request.
     */
    public Observable<OperationStatus<Void>> beginCreateAsync(@NonNull String resourceGroupName, @NonNull String managedInstanceName, @NonNull TdeCertificateInner parameters) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (managedInstanceName == null) {
            throw new IllegalArgumentException("Parameter managedInstanceName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (parameters == null) {
            throw new IllegalArgumentException("Parameter parameters is required and cannot be null.");
        }
        Validator.validate(parameters);
        final String apiVersion = "2017-10-01-preview";
        return service.beginCreate(resourceGroupName, managedInstanceName, this.client.subscriptionId(), parameters, apiVersion, this.client.acceptLanguage());
    }

    /**
     * Creates a TDE certificate for a given server.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param parameters The requested TDE certificate to be created or updated.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    public void create(@NonNull String resourceGroupName, @NonNull String managedInstanceName, @NonNull TdeCertificateInner parameters) {
        createAsync(resourceGroupName, managedInstanceName, parameters).blockingAwait();
    }

    /**
     * Creates a TDE certificate for a given server.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param parameters The requested TDE certificate to be created or updated.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    public ServiceFuture<Void> createAsync(@NonNull String resourceGroupName, @NonNull String managedInstanceName, @NonNull TdeCertificateInner parameters, ServiceCallback<Void> serviceCallback) {
        return ServiceFuture.fromBody(createAsync(resourceGroupName, managedInstanceName, parameters), serviceCallback);
    }

    /**
     * Creates a TDE certificate for a given server.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param parameters The requested TDE certificate to be created or updated.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Single<VoidResponse> createWithRestResponseAsync(@NonNull String resourceGroupName, @NonNull String managedInstanceName, @NonNull TdeCertificateInner parameters) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (managedInstanceName == null) {
            throw new IllegalArgumentException("Parameter managedInstanceName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (parameters == null) {
            throw new IllegalArgumentException("Parameter parameters is required and cannot be null.");
        }
        Validator.validate(parameters);
        final String apiVersion = "2017-10-01-preview";
        return service.create(resourceGroupName, managedInstanceName, this.client.subscriptionId(), parameters, apiVersion, this.client.acceptLanguage());
    }

    /**
     * Creates a TDE certificate for a given server.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param parameters The requested TDE certificate to be created or updated.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Completable createAsync(@NonNull String resourceGroupName, @NonNull String managedInstanceName, @NonNull TdeCertificateInner parameters) {
        return createWithRestResponseAsync(resourceGroupName, managedInstanceName, parameters)
            .toCompletable();
    }

    /**
     * Creates a TDE certificate for a given server. (resume watch).
     *
     * @param operationDescription The OperationDescription object.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the observable for the request.
     */
    public Observable<OperationStatus<Void>> resumeCreate(OperationDescription operationDescription) {
        if (operationDescription == null) {
            throw new IllegalArgumentException("Parameter operationDescription is required and cannot be null.");
        }
        return service.resumeCreate(operationDescription);
    }
}
