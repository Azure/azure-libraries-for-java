// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.sql.models;

import com.azure.core.annotation.BodyParam;
import com.azure.core.annotation.ExpectedResponses;
import com.azure.core.annotation.Get;
import com.azure.core.annotation.Headers;
import com.azure.core.annotation.Host;
import com.azure.core.annotation.HostParam;
import com.azure.core.annotation.Patch;
import com.azure.core.annotation.PathParam;
import com.azure.core.annotation.Put;
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
import com.azure.core.util.polling.AsyncPollResponse;
import java.nio.ByteBuffer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * ManagedBackupShortTermRetentionPolicies.
 */
public final class ManagedBackupShortTermRetentionPoliciesInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private ManagedBackupShortTermRetentionPoliciesService service;

    /**
     * The service client containing this operation class.
     */
    private SqlManagementClientImpl client;

    /**
     * Initializes an instance of ManagedBackupShortTermRetentionPoliciesInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    ManagedBackupShortTermRetentionPoliciesInner(SqlManagementClientImpl client) {
        this.service = RestProxy.create(ManagedBackupShortTermRetentionPoliciesService.class, client.getHttpPipeline(), client.getSerializerAdapter());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * SqlManagementClientManagedBackupShortTermRetentionPolicies to be used by
     * the proxy service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "SqlManagementClientManagedBackupShortTermRetentionPolicies")
    private interface ManagedBackupShortTermRetentionPoliciesService {
        @Headers({ "Accept: application/json", "Content-Type: application/json" })
        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/managedInstances/{managedInstanceName}/databases/{databaseName}/backupShortTermRetentionPolicies/{policyName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ManagedBackupShortTermRetentionPolicyInner>> get(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("managedInstanceName") String managedInstanceName, @PathParam("databaseName") String databaseName, @PathParam("policyName") String policyName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Headers({ "Accept: application/json", "Content-Type: application/json" })
        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/managedInstances/{managedInstanceName}/databases/{databaseName}/backupShortTermRetentionPolicies/{policyName}")
        @ExpectedResponses({200, 202})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<Flux<ByteBuffer>>> createOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("managedInstanceName") String managedInstanceName, @PathParam("databaseName") String databaseName, @PathParam("policyName") String policyName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion, @BodyParam("application/json") ManagedBackupShortTermRetentionPolicyInner parameters);

        @Headers({ "Accept: application/json", "Content-Type: application/json" })
        @Patch("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/managedInstances/{managedInstanceName}/databases/{databaseName}/backupShortTermRetentionPolicies/{policyName}")
        @ExpectedResponses({200, 202})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<Flux<ByteBuffer>>> update(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("managedInstanceName") String managedInstanceName, @PathParam("databaseName") String databaseName, @PathParam("policyName") String policyName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion, @BodyParam("application/json") ManagedBackupShortTermRetentionPolicyInner parameters);

        @Headers({ "Accept: application/json", "Content-Type: application/json" })
        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/managedInstances/{managedInstanceName}/databases/{databaseName}/backupShortTermRetentionPolicies")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ManagedBackupShortTermRetentionPolicyListResultInner>> listByDatabase(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("managedInstanceName") String managedInstanceName, @PathParam("databaseName") String databaseName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Headers({ "Accept: application/json", "Content-Type: application/json" })
        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/managedInstances/{managedInstanceName}/databases/{databaseName}/backupShortTermRetentionPolicies/{policyName}")
        @ExpectedResponses({200, 202})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ManagedBackupShortTermRetentionPolicyInner>> beginCreateOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("managedInstanceName") String managedInstanceName, @PathParam("databaseName") String databaseName, @PathParam("policyName") String policyName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion, @BodyParam("application/json") ManagedBackupShortTermRetentionPolicyInner parameters);

        @Headers({ "Accept: application/json", "Content-Type: application/json" })
        @Patch("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/managedInstances/{managedInstanceName}/databases/{databaseName}/backupShortTermRetentionPolicies/{policyName}")
        @ExpectedResponses({200, 202})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ManagedBackupShortTermRetentionPolicyInner>> beginUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("managedInstanceName") String managedInstanceName, @PathParam("databaseName") String databaseName, @PathParam("policyName") String policyName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion, @BodyParam("application/json") ManagedBackupShortTermRetentionPolicyInner parameters);

        @Headers({ "Accept: application/json", "Content-Type: application/json" })
        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<ManagedBackupShortTermRetentionPolicyListResultInner>> listByDatabaseNext(@PathParam(value = "nextLink", encoded = true) String nextLink);
    }

    /**
     * Gets a managed database's short term retention policy.
     * 
     * @param resourceGroupName 
     * @param managedInstanceName 
     * @param databaseName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ManagedBackupShortTermRetentionPolicyInner>> getWithResponseAsync(String resourceGroupName, String managedInstanceName, String databaseName) {
        final String policyName = "default";
        final String apiVersion = "2017-03-01-preview";
        return service.get(this.client.getHost(), resourceGroupName, managedInstanceName, databaseName, policyName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Gets a managed database's short term retention policy.
     * 
     * @param resourceGroupName 
     * @param managedInstanceName 
     * @param databaseName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ManagedBackupShortTermRetentionPolicyInner> getAsync(String resourceGroupName, String managedInstanceName, String databaseName) {
        return getWithResponseAsync(resourceGroupName, managedInstanceName, databaseName)
            .flatMap((SimpleResponse<ManagedBackupShortTermRetentionPolicyInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Gets a managed database's short term retention policy.
     * 
     * @param resourceGroupName 
     * @param managedInstanceName 
     * @param databaseName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ManagedBackupShortTermRetentionPolicyInner get(String resourceGroupName, String managedInstanceName, String databaseName) {
        return getAsync(resourceGroupName, managedInstanceName, databaseName).block();
    }

    /**
     * Updates a managed database's short term retention policy.
     * 
     * @param resourceGroupName 
     * @param managedInstanceName 
     * @param databaseName 
     * @param retentionDays The backup retention period in days. This is how many days Point-in-Time Restore will be supported.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Flux<ByteBuffer>>> createOrUpdateWithResponseAsync(String resourceGroupName, String managedInstanceName, String databaseName, Integer retentionDays) {
        final String policyName = "default";
        final String apiVersion = "2017-03-01-preview";
        ManagedBackupShortTermRetentionPolicyInner parameters = new ManagedBackupShortTermRetentionPolicyInner();
        parameters.withRetentionDays(retentionDays);
        return service.createOrUpdate(this.client.getHost(), resourceGroupName, managedInstanceName, databaseName, policyName, this.client.getSubscriptionId(), apiVersion, parameters);
    }

    /**
     * Updates a managed database's short term retention policy.
     * 
     * @param resourceGroupName 
     * @param managedInstanceName 
     * @param databaseName 
     * @param retentionDays The backup retention period in days. This is how many days Point-in-Time Restore will be supported.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ManagedBackupShortTermRetentionPolicyInner> createOrUpdateAsync(String resourceGroupName, String managedInstanceName, String databaseName, Integer retentionDays) {
        Mono<SimpleResponse<Flux<ByteBuffer>>> mono = createOrUpdateWithResponseAsync(resourceGroupName, managedInstanceName, databaseName, retentionDays);
        return this.client.<ManagedBackupShortTermRetentionPolicyInner, ManagedBackupShortTermRetentionPolicyInner>getLroResultAsync(mono, this.client.getHttpPipeline(), ManagedBackupShortTermRetentionPolicyInner.class, ManagedBackupShortTermRetentionPolicyInner.class)
            .last()
            .flatMap(AsyncPollResponse::getFinalResult);
    }

    /**
     * Updates a managed database's short term retention policy.
     * 
     * @param resourceGroupName 
     * @param managedInstanceName 
     * @param databaseName 
     * @param retentionDays The backup retention period in days. This is how many days Point-in-Time Restore will be supported.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ManagedBackupShortTermRetentionPolicyInner createOrUpdate(String resourceGroupName, String managedInstanceName, String databaseName, Integer retentionDays) {
        return createOrUpdateAsync(resourceGroupName, managedInstanceName, databaseName, retentionDays).block();
    }

    /**
     * Updates a managed database's short term retention policy.
     * 
     * @param resourceGroupName 
     * @param managedInstanceName 
     * @param databaseName 
     * @param retentionDays The backup retention period in days. This is how many days Point-in-Time Restore will be supported.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Flux<ByteBuffer>>> updateWithResponseAsync(String resourceGroupName, String managedInstanceName, String databaseName, Integer retentionDays) {
        final String policyName = "default";
        final String apiVersion = "2017-03-01-preview";
        ManagedBackupShortTermRetentionPolicyInner parameters = new ManagedBackupShortTermRetentionPolicyInner();
        parameters.withRetentionDays(retentionDays);
        return service.update(this.client.getHost(), resourceGroupName, managedInstanceName, databaseName, policyName, this.client.getSubscriptionId(), apiVersion, parameters);
    }

    /**
     * Updates a managed database's short term retention policy.
     * 
     * @param resourceGroupName 
     * @param managedInstanceName 
     * @param databaseName 
     * @param retentionDays The backup retention period in days. This is how many days Point-in-Time Restore will be supported.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ManagedBackupShortTermRetentionPolicyInner> updateAsync(String resourceGroupName, String managedInstanceName, String databaseName, Integer retentionDays) {
        Mono<SimpleResponse<Flux<ByteBuffer>>> mono = updateWithResponseAsync(resourceGroupName, managedInstanceName, databaseName, retentionDays);
        return this.client.<ManagedBackupShortTermRetentionPolicyInner, ManagedBackupShortTermRetentionPolicyInner>getLroResultAsync(mono, this.client.getHttpPipeline(), ManagedBackupShortTermRetentionPolicyInner.class, ManagedBackupShortTermRetentionPolicyInner.class)
            .last()
            .flatMap(AsyncPollResponse::getFinalResult);
    }

    /**
     * Updates a managed database's short term retention policy.
     * 
     * @param resourceGroupName 
     * @param managedInstanceName 
     * @param databaseName 
     * @param retentionDays The backup retention period in days. This is how many days Point-in-Time Restore will be supported.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ManagedBackupShortTermRetentionPolicyInner update(String resourceGroupName, String managedInstanceName, String databaseName, Integer retentionDays) {
        return updateAsync(resourceGroupName, managedInstanceName, databaseName, retentionDays).block();
    }

    /**
     * Gets a managed database's short term retention policy list.
     * 
     * @param resourceGroupName 
     * @param managedInstanceName 
     * @param databaseName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<ManagedBackupShortTermRetentionPolicyInner>> listByDatabaseSinglePageAsync(String resourceGroupName, String managedInstanceName, String databaseName) {
        final String apiVersion = "2017-03-01-preview";
        return service.listByDatabase(this.client.getHost(), resourceGroupName, managedInstanceName, databaseName, this.client.getSubscriptionId(), apiVersion)
            .map(res -> new PagedResponseBase<>(
                res.getRequest(),
                res.getStatusCode(),
                res.getHeaders(),
                res.getValue().value(),
                res.getValue().nextLink(),
                null));
    }

    /**
     * Gets a managed database's short term retention policy list.
     * 
     * @param resourceGroupName 
     * @param managedInstanceName 
     * @param databaseName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<ManagedBackupShortTermRetentionPolicyInner> listByDatabaseAsync(String resourceGroupName, String managedInstanceName, String databaseName) {
        return new PagedFlux<>(
            () -> listByDatabaseSinglePageAsync(resourceGroupName, managedInstanceName, databaseName),
            nextLink -> listByDatabaseNextSinglePageAsync(nextLink));
    }

    /**
     * Gets a managed database's short term retention policy list.
     * 
     * @param resourceGroupName 
     * @param managedInstanceName 
     * @param databaseName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<ManagedBackupShortTermRetentionPolicyInner> listByDatabase(String resourceGroupName, String managedInstanceName, String databaseName) {
        return new PagedIterable<>(listByDatabaseAsync(resourceGroupName, managedInstanceName, databaseName));
    }

    /**
     * Updates a managed database's short term retention policy.
     * 
     * @param resourceGroupName 
     * @param managedInstanceName 
     * @param databaseName 
     * @param retentionDays The backup retention period in days. This is how many days Point-in-Time Restore will be supported.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ManagedBackupShortTermRetentionPolicyInner>> beginCreateOrUpdateWithResponseAsync(String resourceGroupName, String managedInstanceName, String databaseName, Integer retentionDays) {
        final String policyName = "default";
        final String apiVersion = "2017-03-01-preview";
        ManagedBackupShortTermRetentionPolicyInner parameters = new ManagedBackupShortTermRetentionPolicyInner();
        parameters.withRetentionDays(retentionDays);
        return service.beginCreateOrUpdate(this.client.getHost(), resourceGroupName, managedInstanceName, databaseName, policyName, this.client.getSubscriptionId(), apiVersion, parameters);
    }

    /**
     * Updates a managed database's short term retention policy.
     * 
     * @param resourceGroupName 
     * @param managedInstanceName 
     * @param databaseName 
     * @param retentionDays The backup retention period in days. This is how many days Point-in-Time Restore will be supported.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ManagedBackupShortTermRetentionPolicyInner> beginCreateOrUpdateAsync(String resourceGroupName, String managedInstanceName, String databaseName, Integer retentionDays) {
        return beginCreateOrUpdateWithResponseAsync(resourceGroupName, managedInstanceName, databaseName, retentionDays)
            .flatMap((SimpleResponse<ManagedBackupShortTermRetentionPolicyInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Updates a managed database's short term retention policy.
     * 
     * @param resourceGroupName 
     * @param managedInstanceName 
     * @param databaseName 
     * @param retentionDays The backup retention period in days. This is how many days Point-in-Time Restore will be supported.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ManagedBackupShortTermRetentionPolicyInner beginCreateOrUpdate(String resourceGroupName, String managedInstanceName, String databaseName, Integer retentionDays) {
        return beginCreateOrUpdateAsync(resourceGroupName, managedInstanceName, databaseName, retentionDays).block();
    }

    /**
     * Updates a managed database's short term retention policy.
     * 
     * @param resourceGroupName 
     * @param managedInstanceName 
     * @param databaseName 
     * @param retentionDays The backup retention period in days. This is how many days Point-in-Time Restore will be supported.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ManagedBackupShortTermRetentionPolicyInner>> beginUpdateWithResponseAsync(String resourceGroupName, String managedInstanceName, String databaseName, Integer retentionDays) {
        final String policyName = "default";
        final String apiVersion = "2017-03-01-preview";
        ManagedBackupShortTermRetentionPolicyInner parameters = new ManagedBackupShortTermRetentionPolicyInner();
        parameters.withRetentionDays(retentionDays);
        return service.beginUpdate(this.client.getHost(), resourceGroupName, managedInstanceName, databaseName, policyName, this.client.getSubscriptionId(), apiVersion, parameters);
    }

    /**
     * Updates a managed database's short term retention policy.
     * 
     * @param resourceGroupName 
     * @param managedInstanceName 
     * @param databaseName 
     * @param retentionDays The backup retention period in days. This is how many days Point-in-Time Restore will be supported.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ManagedBackupShortTermRetentionPolicyInner> beginUpdateAsync(String resourceGroupName, String managedInstanceName, String databaseName, Integer retentionDays) {
        return beginUpdateWithResponseAsync(resourceGroupName, managedInstanceName, databaseName, retentionDays)
            .flatMap((SimpleResponse<ManagedBackupShortTermRetentionPolicyInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Updates a managed database's short term retention policy.
     * 
     * @param resourceGroupName 
     * @param managedInstanceName 
     * @param databaseName 
     * @param retentionDays The backup retention period in days. This is how many days Point-in-Time Restore will be supported.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ManagedBackupShortTermRetentionPolicyInner beginUpdate(String resourceGroupName, String managedInstanceName, String databaseName, Integer retentionDays) {
        return beginUpdateAsync(resourceGroupName, managedInstanceName, databaseName, retentionDays).block();
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
    public Mono<PagedResponse<ManagedBackupShortTermRetentionPolicyInner>> listByDatabaseNextSinglePageAsync(String nextLink) {
        return service.listByDatabaseNext(nextLink)
            .map(res -> new PagedResponseBase<>(
                res.getRequest(),
                res.getStatusCode(),
                res.getHeaders(),
                res.getValue().value(),
                res.getValue().nextLink(),
                null));
    }
}
