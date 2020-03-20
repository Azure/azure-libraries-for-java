// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.sql.models;

import com.azure.core.annotation.BodyParam;
import com.azure.core.annotation.Delete;
import com.azure.core.annotation.ExpectedResponses;
import com.azure.core.annotation.Get;
import com.azure.core.annotation.Headers;
import com.azure.core.annotation.Host;
import com.azure.core.annotation.HostParam;
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
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.RestProxy;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.management.CloudException;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * JobSteps.
 */
public final class JobStepsInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private JobStepsService service;

    /**
     * The service client containing this operation class.
     */
    private SqlManagementClientImpl client;

    /**
     * Initializes an instance of JobStepsInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    JobStepsInner(SqlManagementClientImpl client) {
        this.service = RestProxy.create(JobStepsService.class, client.getHttpPipeline(), client.getSerializerAdapter());
        this.client = client;
    }

    /**
     * The interface defining all the services for SqlManagementClientJobSteps
     * to be used by the proxy service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "SqlManagementClientJobSteps")
    private interface JobStepsService {
        @Headers({ "Accept: application/json", "Content-Type: application/json" })
        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/jobAgents/{jobAgentName}/jobs/{jobName}/versions/{jobVersion}/steps")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<JobStepListResultInner>> listByVersion(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("jobAgentName") String jobAgentName, @PathParam("jobName") String jobName, @PathParam("jobVersion") int jobVersion, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Headers({ "Accept: application/json", "Content-Type: application/json" })
        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/jobAgents/{jobAgentName}/jobs/{jobName}/versions/{jobVersion}/steps/{stepName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<JobStepInner>> getByVersion(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("jobAgentName") String jobAgentName, @PathParam("jobName") String jobName, @PathParam("jobVersion") int jobVersion, @PathParam("stepName") String stepName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Headers({ "Accept: application/json", "Content-Type: application/json" })
        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/jobAgents/{jobAgentName}/jobs/{jobName}/steps")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<JobStepListResultInner>> listByJob(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("jobAgentName") String jobAgentName, @PathParam("jobName") String jobName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Headers({ "Accept: application/json", "Content-Type: application/json" })
        @Get("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/jobAgents/{jobAgentName}/jobs/{jobName}/steps/{stepName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<JobStepInner>> get(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("jobAgentName") String jobAgentName, @PathParam("jobName") String jobName, @PathParam("stepName") String stepName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Headers({ "Accept: application/json", "Content-Type: application/json" })
        @Put("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/jobAgents/{jobAgentName}/jobs/{jobName}/steps/{stepName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<JobStepInner>> createOrUpdate(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("jobAgentName") String jobAgentName, @PathParam("jobName") String jobName, @PathParam("stepName") String stepName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion, @BodyParam("application/json") JobStepInner parameters);

        @Headers({ "Accept: application/json;q=0.9", "Content-Type: application/json" })
        @Delete("/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/jobAgents/{jobAgentName}/jobs/{jobName}/steps/{stepName}")
        @ExpectedResponses({200, 204})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<Response<Void>> delete(@HostParam("$host") String host, @PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("jobAgentName") String jobAgentName, @PathParam("jobName") String jobName, @PathParam("stepName") String stepName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion);

        @Headers({ "Accept: application/json", "Content-Type: application/json" })
        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<JobStepListResultInner>> listByVersionNext(@PathParam(value = "nextLink", encoded = true) String nextLink);

        @Headers({ "Accept: application/json", "Content-Type: application/json" })
        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<JobStepListResultInner>> listByJobNext(@PathParam(value = "nextLink", encoded = true) String nextLink);
    }

    /**
     * Gets all job steps in the specified job version.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param jobAgentName 
     * @param jobName 
     * @param jobVersion 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<JobStepInner>> listByVersionSinglePageAsync(String resourceGroupName, String serverName, String jobAgentName, String jobName, int jobVersion) {
        final String apiVersion = "2017-03-01-preview";
        return service.listByVersion(this.client.getHost(), resourceGroupName, serverName, jobAgentName, jobName, jobVersion, this.client.getSubscriptionId(), apiVersion)
            .map(res -> new PagedResponseBase<>(
                res.getRequest(),
                res.getStatusCode(),
                res.getHeaders(),
                res.getValue().value(),
                res.getValue().nextLink(),
                null));
    }

    /**
     * Gets all job steps in the specified job version.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param jobAgentName 
     * @param jobName 
     * @param jobVersion 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<JobStepInner> listByVersionAsync(String resourceGroupName, String serverName, String jobAgentName, String jobName, int jobVersion) {
        return new PagedFlux<>(
            () -> listByVersionSinglePageAsync(resourceGroupName, serverName, jobAgentName, jobName, jobVersion),
            nextLink -> listByVersionNextSinglePageAsync(nextLink));
    }

    /**
     * Gets all job steps in the specified job version.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param jobAgentName 
     * @param jobName 
     * @param jobVersion 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<JobStepInner> listByVersion(String resourceGroupName, String serverName, String jobAgentName, String jobName, int jobVersion) {
        return new PagedIterable<>(listByVersionAsync(resourceGroupName, serverName, jobAgentName, jobName, jobVersion));
    }

    /**
     * Gets the specified version of a job step.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param jobAgentName 
     * @param jobName 
     * @param jobVersion 
     * @param stepName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<JobStepInner>> getByVersionWithResponseAsync(String resourceGroupName, String serverName, String jobAgentName, String jobName, int jobVersion, String stepName) {
        final String apiVersion = "2017-03-01-preview";
        return service.getByVersion(this.client.getHost(), resourceGroupName, serverName, jobAgentName, jobName, jobVersion, stepName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Gets the specified version of a job step.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param jobAgentName 
     * @param jobName 
     * @param jobVersion 
     * @param stepName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<JobStepInner> getByVersionAsync(String resourceGroupName, String serverName, String jobAgentName, String jobName, int jobVersion, String stepName) {
        return getByVersionWithResponseAsync(resourceGroupName, serverName, jobAgentName, jobName, jobVersion, stepName)
            .flatMap((SimpleResponse<JobStepInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Gets the specified version of a job step.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param jobAgentName 
     * @param jobName 
     * @param jobVersion 
     * @param stepName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public JobStepInner getByVersion(String resourceGroupName, String serverName, String jobAgentName, String jobName, int jobVersion, String stepName) {
        return getByVersionAsync(resourceGroupName, serverName, jobAgentName, jobName, jobVersion, stepName).block();
    }

    /**
     * Gets all job steps for a job's current version.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param jobAgentName 
     * @param jobName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<JobStepInner>> listByJobSinglePageAsync(String resourceGroupName, String serverName, String jobAgentName, String jobName) {
        final String apiVersion = "2017-03-01-preview";
        return service.listByJob(this.client.getHost(), resourceGroupName, serverName, jobAgentName, jobName, this.client.getSubscriptionId(), apiVersion)
            .map(res -> new PagedResponseBase<>(
                res.getRequest(),
                res.getStatusCode(),
                res.getHeaders(),
                res.getValue().value(),
                res.getValue().nextLink(),
                null));
    }

    /**
     * Gets all job steps for a job's current version.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param jobAgentName 
     * @param jobName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<JobStepInner> listByJobAsync(String resourceGroupName, String serverName, String jobAgentName, String jobName) {
        return new PagedFlux<>(
            () -> listByJobSinglePageAsync(resourceGroupName, serverName, jobAgentName, jobName),
            nextLink -> listByJobNextSinglePageAsync(nextLink));
    }

    /**
     * Gets all job steps for a job's current version.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param jobAgentName 
     * @param jobName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<JobStepInner> listByJob(String resourceGroupName, String serverName, String jobAgentName, String jobName) {
        return new PagedIterable<>(listByJobAsync(resourceGroupName, serverName, jobAgentName, jobName));
    }

    /**
     * Gets a job step in a job's current version.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param jobAgentName 
     * @param jobName 
     * @param stepName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<JobStepInner>> getWithResponseAsync(String resourceGroupName, String serverName, String jobAgentName, String jobName, String stepName) {
        final String apiVersion = "2017-03-01-preview";
        return service.get(this.client.getHost(), resourceGroupName, serverName, jobAgentName, jobName, stepName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Gets a job step in a job's current version.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param jobAgentName 
     * @param jobName 
     * @param stepName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<JobStepInner> getAsync(String resourceGroupName, String serverName, String jobAgentName, String jobName, String stepName) {
        return getWithResponseAsync(resourceGroupName, serverName, jobAgentName, jobName, stepName)
            .flatMap((SimpleResponse<JobStepInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Gets a job step in a job's current version.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param jobAgentName 
     * @param jobName 
     * @param stepName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public JobStepInner get(String resourceGroupName, String serverName, String jobAgentName, String jobName, String stepName) {
        return getAsync(resourceGroupName, serverName, jobAgentName, jobName, stepName).block();
    }

    /**
     * Creates or updates a job step. This will implicitly create a new job version.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param jobAgentName 
     * @param jobName 
     * @param stepName 
     * @param parameters A job step.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<JobStepInner>> createOrUpdateWithResponseAsync(String resourceGroupName, String serverName, String jobAgentName, String jobName, String stepName, JobStepInner parameters) {
        final String apiVersion = "2017-03-01-preview";
        return service.createOrUpdate(this.client.getHost(), resourceGroupName, serverName, jobAgentName, jobName, stepName, this.client.getSubscriptionId(), apiVersion, parameters);
    }

    /**
     * Creates or updates a job step. This will implicitly create a new job version.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param jobAgentName 
     * @param jobName 
     * @param stepName 
     * @param parameters A job step.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<JobStepInner> createOrUpdateAsync(String resourceGroupName, String serverName, String jobAgentName, String jobName, String stepName, JobStepInner parameters) {
        return createOrUpdateWithResponseAsync(resourceGroupName, serverName, jobAgentName, jobName, stepName, parameters)
            .flatMap((SimpleResponse<JobStepInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Creates or updates a job step. This will implicitly create a new job version.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param jobAgentName 
     * @param jobName 
     * @param stepName 
     * @param parameters A job step.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public JobStepInner createOrUpdate(String resourceGroupName, String serverName, String jobAgentName, String jobName, String stepName, JobStepInner parameters) {
        return createOrUpdateAsync(resourceGroupName, serverName, jobAgentName, jobName, stepName, parameters).block();
    }

    /**
     * Deletes a job step. This will implicitly create a new job version.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param jobAgentName 
     * @param jobName 
     * @param stepName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> deleteWithResponseAsync(String resourceGroupName, String serverName, String jobAgentName, String jobName, String stepName) {
        final String apiVersion = "2017-03-01-preview";
        return service.delete(this.client.getHost(), resourceGroupName, serverName, jobAgentName, jobName, stepName, this.client.getSubscriptionId(), apiVersion);
    }

    /**
     * Deletes a job step. This will implicitly create a new job version.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param jobAgentName 
     * @param jobName 
     * @param stepName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteAsync(String resourceGroupName, String serverName, String jobAgentName, String jobName, String stepName) {
        return deleteWithResponseAsync(resourceGroupName, serverName, jobAgentName, jobName, stepName)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Deletes a job step. This will implicitly create a new job version.
     * 
     * @param resourceGroupName 
     * @param serverName 
     * @param jobAgentName 
     * @param jobName 
     * @param stepName 
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void delete(String resourceGroupName, String serverName, String jobAgentName, String jobName, String stepName) {
        deleteAsync(resourceGroupName, serverName, jobAgentName, jobName, stepName).block();
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
    public Mono<PagedResponse<JobStepInner>> listByVersionNextSinglePageAsync(String nextLink) {
        return service.listByVersionNext(nextLink)
            .map(res -> new PagedResponseBase<>(
                res.getRequest(),
                res.getStatusCode(),
                res.getHeaders(),
                res.getValue().value(),
                res.getValue().nextLink(),
                null));
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
    public Mono<PagedResponse<JobStepInner>> listByJobNextSinglePageAsync(String nextLink) {
        return service.listByJobNext(nextLink)
            .map(res -> new PagedResponseBase<>(
                res.getRequest(),
                res.getStatusCode(),
                res.getHeaders(),
                res.getValue().value(),
                res.getValue().nextLink(),
                null));
    }
}
