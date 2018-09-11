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
import com.microsoft.azure.v2.Page;
import com.microsoft.azure.v2.PagedList;
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
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

/**
 * An instance of this class provides access to all the operations defined in
 * JobVersions.
 */
public final class JobVersionsInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private JobVersionsService service;

    /**
     * The service client containing this operation class.
     */
    private SqlManagementClientImpl client;

    /**
     * Initializes an instance of JobVersionsInner.
     *
     * @param client the instance of the service client containing this operation class.
     */
    public JobVersionsInner(SqlManagementClientImpl client) {
        this.service = AzureProxy.create(JobVersionsService.class, client);
        this.client = client;
    }

    /**
     * The interface defining all the services for JobVersions to be used by
     * the proxy service to perform REST calls.
     */
    @Host("https://management.azure.com")
    private interface JobVersionsService {
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/jobAgents/{jobAgentName}/jobs/{jobName}/versions")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<BodyResponse<PageImpl1<JobVersionInner>>> listByJob(@PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("jobAgentName") String jobAgentName, @PathParam("jobName") String jobName, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);

        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/jobAgents/{jobAgentName}/jobs/{jobName}/versions/{jobVersion}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<BodyResponse<JobVersionInner>> get(@PathParam("resourceGroupName") String resourceGroupName, @PathParam("serverName") String serverName, @PathParam("jobAgentName") String jobAgentName, @PathParam("jobName") String jobName, @PathParam("jobVersion") int jobVersion, @PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);

        @GET("{nextUrl}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<BodyResponse<PageImpl1<JobVersionInner>>> listByJobNext(@PathParam(value = "nextUrl", encoded = true) String nextUrl, @HeaderParam("accept-language") String acceptLanguage);
    }

    /**
     * Gets all versions of a job.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param jobAgentName The name of the job agent.
     * @param jobName The name of the job to get.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the PagedList&lt;JobVersionInner&gt; object if successful.
     */
    public PagedList<JobVersionInner> listByJob(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String jobAgentName, @NonNull String jobName) {
        Page<JobVersionInner> response = listByJobSinglePageAsync(resourceGroupName, serverName, jobAgentName, jobName).blockingGet();
        return new PagedList<JobVersionInner>(response) {
            @Override
            public Page<JobVersionInner> nextPage(String nextPageLink) {
                return listByJobNextSinglePageAsync(nextPageLink).blockingGet();
            }
        };
    }

    /**
     * Gets all versions of a job.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param jobAgentName The name of the job agent.
     * @param jobName The name of the job to get.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the observable to the PagedList&lt;JobVersionInner&gt; object.
     */
    public Observable<Page<JobVersionInner>> listByJobAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String jobAgentName, @NonNull String jobName) {
        return listByJobSinglePageAsync(resourceGroupName, serverName, jobAgentName, jobName)
            .toObservable()
            .concatMap((Page<JobVersionInner> page) -> {
                String nextPageLink = page.nextPageLink();
                if (nextPageLink == null) {
                    return Observable.just(page);
                }
                return Observable.just(page).concatWith(listByJobNextAsync(nextPageLink));
            });
    }

    /**
     * Gets all versions of a job.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param jobAgentName The name of the job agent.
     * @param jobName The name of the job to get.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the Single&lt;Page&lt;JobVersionInner&gt;&gt; object if successful.
     */
    public Single<Page<JobVersionInner>> listByJobSinglePageAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String jobAgentName, @NonNull String jobName) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
        }
        if (jobAgentName == null) {
            throw new IllegalArgumentException("Parameter jobAgentName is required and cannot be null.");
        }
        if (jobName == null) {
            throw new IllegalArgumentException("Parameter jobName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        final String apiVersion = "2017-03-01-preview";
        return service.listByJob(resourceGroupName, serverName, jobAgentName, jobName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage())
            .map((BodyResponse<PageImpl1<JobVersionInner>> res) -> res.body());
    }

    /**
     * Gets a job version.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param jobAgentName The name of the job agent.
     * @param jobName The name of the job.
     * @param jobVersion The version of the job to get.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the JobVersionInner object if successful.
     */
    public JobVersionInner get(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String jobAgentName, @NonNull String jobName, @NonNull int jobVersion) {
        return getAsync(resourceGroupName, serverName, jobAgentName, jobName, jobVersion).blockingGet();
    }

    /**
     * Gets a job version.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param jobAgentName The name of the job agent.
     * @param jobName The name of the job.
     * @param jobVersion The version of the job to get.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    public ServiceFuture<JobVersionInner> getAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String jobAgentName, @NonNull String jobName, @NonNull int jobVersion, ServiceCallback<JobVersionInner> serviceCallback) {
        return ServiceFuture.fromBody(getAsync(resourceGroupName, serverName, jobAgentName, jobName, jobVersion), serviceCallback);
    }

    /**
     * Gets a job version.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param jobAgentName The name of the job agent.
     * @param jobName The name of the job.
     * @param jobVersion The version of the job to get.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Single<BodyResponse<JobVersionInner>> getWithRestResponseAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String jobAgentName, @NonNull String jobName, @NonNull int jobVersion) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
        }
        if (jobAgentName == null) {
            throw new IllegalArgumentException("Parameter jobAgentName is required and cannot be null.");
        }
        if (jobName == null) {
            throw new IllegalArgumentException("Parameter jobName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        final String apiVersion = "2017-03-01-preview";
        return service.get(resourceGroupName, serverName, jobAgentName, jobName, jobVersion, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage());
    }

    /**
     * Gets a job version.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param jobAgentName The name of the job agent.
     * @param jobName The name of the job.
     * @param jobVersion The version of the job to get.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Maybe<JobVersionInner> getAsync(@NonNull String resourceGroupName, @NonNull String serverName, @NonNull String jobAgentName, @NonNull String jobName, @NonNull int jobVersion) {
        return getWithRestResponseAsync(resourceGroupName, serverName, jobAgentName, jobName, jobVersion)
            .flatMapMaybe((BodyResponse<JobVersionInner> res) -> res.body() == null ? Maybe.empty() : Maybe.just(res.body()));
    }

    /**
     * Gets all versions of a job.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the PagedList&lt;JobVersionInner&gt; object if successful.
     */
    public PagedList<JobVersionInner> listByJobNext(@NonNull String nextPageLink) {
        Page<JobVersionInner> response = listByJobNextSinglePageAsync(nextPageLink).blockingGet();
        return new PagedList<JobVersionInner>(response) {
            @Override
            public Page<JobVersionInner> nextPage(String nextPageLink) {
                return listByJobNextSinglePageAsync(nextPageLink).blockingGet();
            }
        };
    }

    /**
     * Gets all versions of a job.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the observable to the PagedList&lt;JobVersionInner&gt; object.
     */
    public Observable<Page<JobVersionInner>> listByJobNextAsync(@NonNull String nextPageLink) {
        return listByJobNextSinglePageAsync(nextPageLink)
            .toObservable()
            .concatMap((Page<JobVersionInner> page) -> {
                String nextPageLink1 = page.nextPageLink();
                if (nextPageLink1 == null) {
                    return Observable.just(page);
                }
                return Observable.just(page).concatWith(listByJobNextAsync(nextPageLink1));
            });
    }

    /**
     * Gets all versions of a job.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the Single&lt;Page&lt;JobVersionInner&gt;&gt; object if successful.
     */
    public Single<Page<JobVersionInner>> listByJobNextSinglePageAsync(@NonNull String nextPageLink) {
        if (nextPageLink == null) {
            throw new IllegalArgumentException("Parameter nextPageLink is required and cannot be null.");
        }
        String nextUrl = String.format("%s", nextPageLink);
        return service.listByJobNext(nextUrl, this.client.acceptLanguage())
            .map((BodyResponse<PageImpl1<JobVersionInner>> res) -> res.body());
    }
}