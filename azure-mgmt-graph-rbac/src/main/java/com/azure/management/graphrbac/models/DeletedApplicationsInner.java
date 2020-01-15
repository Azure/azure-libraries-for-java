// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.graphrbac.models;

import com.azure.core.annotation.Delete;
import com.azure.core.annotation.ExpectedResponses;
import com.azure.core.annotation.Get;
import com.azure.core.annotation.Host;
import com.azure.core.annotation.HostParam;
import com.azure.core.annotation.PathParam;
import com.azure.core.annotation.Post;
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
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * DeletedApplications.
 */
public final class DeletedApplicationsInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private DeletedApplicationsService service;

    /**
     * The service client containing this operation class.
     */
    private GraphRbacManagementClientImpl client;

    /**
     * Initializes an instance of DeletedApplicationsInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public DeletedApplicationsInner(GraphRbacManagementClientImpl client) {
        this.service = RestProxy.create(DeletedApplicationsService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * GraphRbacManagementClientDeletedApplications to be used by the proxy
     * service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "GraphRbacManagementClientDeletedApplications")
    private interface DeletedApplicationsService {
        @Post("/{tenantID}/deletedApplications/{objectId}/restore")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(GraphErrorException.class)
        Mono<SimpleResponse<ApplicationInner>> restore(@HostParam("$host") String host, @PathParam("objectId") String objectId, @PathParam("tenantID") String tenantID, @QueryParam("api-version") String apiVersion);

        @Get("/{tenantID}/deletedApplications")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(GraphErrorException.class)
        Mono<SimpleResponse<ApplicationListResultInner>> list(@HostParam("$host") String host, @QueryParam("$filter") String filter, @PathParam("tenantID") String tenantID, @QueryParam("api-version") String apiVersion);

        @Delete("/{tenantID}/deletedApplications/{applicationObjectId}")
        @ExpectedResponses({204})
        @UnexpectedResponseExceptionType(GraphErrorException.class)
        Mono<Response<Void>> hardDelete(@HostParam("$host") String host, @PathParam("applicationObjectId") String applicationObjectId, @PathParam("tenantID") String tenantID, @QueryParam("api-version") String apiVersion);

        @Get("/{tenantID}/{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(GraphErrorException.class)
        Mono<SimpleResponse<ApplicationListResultInner>> listNext(@HostParam("$host") String host, @PathParam(value = "nextLink", encoded = true) String nextLink, @PathParam("tenantID") String tenantID, @QueryParam("api-version") String apiVersion);
    }

    /**
     * Restores the deleted application in the directory.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ApplicationInner>> restoreWithResponseAsync(String objectId) {
        return service.restore(this.client.getHost(), objectId, this.client.getTenantID(), this.client.getApiVersion());
    }

    /**
     * Restores the deleted application in the directory.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ApplicationInner> restoreAsync(String objectId) {
        return restoreWithResponseAsync(objectId)
            .flatMap((SimpleResponse<ApplicationInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Restores the deleted application in the directory.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ApplicationInner restore(String objectId) {
        return restoreAsync(objectId).block();
    }

    /**
     * Gets a list of deleted applications in the directory.
     * 
     * @param filter MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<ApplicationInner>> listSinglePageAsync(String filter) {
        return service.list(this.client.getHost(), filter, this.client.getTenantID(), this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getOdatanextLink(),
            null));
    }

    /**
     * Gets a list of deleted applications in the directory.
     * 
     * @param filter MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<ApplicationInner> listAsync(String filter) {
        return new PagedFlux<>(
            () -> listSinglePageAsync(filter),
            nextLink -> listNextSinglePageAsync(nextLink));
    }

    /**
     * Gets a list of deleted applications in the directory.
     * 
     * @param filter MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<ApplicationInner> list(String filter) {
        return new PagedIterable<>(listAsync(filter));
    }

    /**
     * Hard-delete an application.
     * 
     * @param applicationObjectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> hardDeleteWithResponseAsync(String applicationObjectId) {
        return service.hardDelete(this.client.getHost(), applicationObjectId, this.client.getTenantID(), this.client.getApiVersion());
    }

    /**
     * Hard-delete an application.
     * 
     * @param applicationObjectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> hardDeleteAsync(String applicationObjectId) {
        return hardDeleteWithResponseAsync(applicationObjectId)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Hard-delete an application.
     * 
     * @param applicationObjectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void hardDelete(String applicationObjectId) {
        hardDeleteAsync(applicationObjectId).block();
    }

    /**
     * Gets a list of deleted applications in the directory.
     * 
     * @param nextLink MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<ApplicationInner>> listNextSinglePageAsync(String nextLink) {
        return service.listNext(this.client.getHost(), nextLink, this.client.getTenantID(), this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getOdatanextLink(),
            null));
    }
}
