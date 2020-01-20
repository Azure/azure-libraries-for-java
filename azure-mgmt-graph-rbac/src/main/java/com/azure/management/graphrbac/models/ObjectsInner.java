// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.graphrbac.models;

import com.azure.core.annotation.BodyParam;
import com.azure.core.annotation.ExpectedResponses;
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
import com.azure.core.http.rest.RestProxy;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.management.CloudException;
import com.azure.management.graphrbac.GetObjectsParameters;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * Objects.
 */
public final class ObjectsInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private ObjectsService service;

    /**
     * The service client containing this operation class.
     */
    private GraphRbacManagementClientImpl client;

    /**
     * Initializes an instance of ObjectsInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public ObjectsInner(GraphRbacManagementClientImpl client) {
        this.service = RestProxy.create(ObjectsService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * GraphRbacManagementClientObjects to be used by the proxy service to
     * perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "GraphRbacManagementClientObjects")
    private interface ObjectsService {
        @Post("/{tenantID}/getObjectsByObjectIds")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<DirectoryObjectListResultInner>> getObjectsByObjectIds(@HostParam("$host") String host, @PathParam("tenantID") String tenantID, @BodyParam("application/json") GetObjectsParameters parameters, @QueryParam("api-version") String apiVersion);

        @Post("/{tenantID}/{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<DirectoryObjectListResultInner>> getObjectsByObjectIdsNext(@HostParam("$host") String host, @PathParam(value = "nextLink", encoded = true) String nextLink, @PathParam("tenantID") String tenantID, @QueryParam("api-version") String apiVersion);
    }

    /**
     * Gets the directory objects specified in a list of object IDs. You can also specify which resource collections (users, groups, etc.) should be searched by specifying the optional types parameter.
     * 
     * @param parameters Request parameters for the GetObjectsByObjectIds API.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<DirectoryObjectInner>> getObjectsByObjectIdsSinglePageAsync(GetObjectsParameters parameters) {
        return service.getObjectsByObjectIds(this.client.getHost(), this.client.getTenantID(), parameters, this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getOdatanextLink(),
            null));
    }

    /**
     * Gets the directory objects specified in a list of object IDs. You can also specify which resource collections (users, groups, etc.) should be searched by specifying the optional types parameter.
     * 
     * @param parameters Request parameters for the GetObjectsByObjectIds API.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<DirectoryObjectInner> getObjectsByObjectIdsAsync(GetObjectsParameters parameters) {
        return new PagedFlux<>(
            () -> getObjectsByObjectIdsSinglePageAsync(parameters),
            nextLink -> getObjectsByObjectIdsNextSinglePageAsync(nextLink));
    }

    /**
     * Gets the directory objects specified in a list of object IDs. You can also specify which resource collections (users, groups, etc.) should be searched by specifying the optional types parameter.
     * 
     * @param parameters Request parameters for the GetObjectsByObjectIds API.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<DirectoryObjectInner> getObjectsByObjectIds(GetObjectsParameters parameters) {
        return new PagedIterable<>(getObjectsByObjectIdsAsync(parameters));
    }

    /**
     * Gets AD group membership for the specified AD object IDs.
     * 
     * @param nextLink MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<DirectoryObjectInner>> getObjectsByObjectIdsNextSinglePageAsync(String nextLink) {
        return service.getObjectsByObjectIdsNext(this.client.getHost(), nextLink, this.client.getTenantID(), this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getOdatanextLink(),
            null));
    }
}
