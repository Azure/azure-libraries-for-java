// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.graphrbac.models;

import com.azure.core.annotation.BodyParam;
import com.azure.core.annotation.Delete;
import com.azure.core.annotation.ExpectedResponses;
import com.azure.core.annotation.Get;
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
import com.azure.core.http.rest.RestProxy;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.management.CloudException;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * RoleDefinitions.
 */
public final class RoleDefinitionsInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private RoleDefinitionsService service;

    /**
     * The service client containing this operation class.
     */
    private AuthorizationManagementClientImpl client;

    /**
     * Initializes an instance of RoleDefinitionsInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public RoleDefinitionsInner(AuthorizationManagementClientImpl client) {
        this.service = RestProxy.create(RoleDefinitionsService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * AuthorizationManagementClientRoleDefinitions to be used by the proxy
     * service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "AuthorizationManagementClientRoleDefinitions")
    private interface RoleDefinitionsService {
        @Delete("/{scope}/providers/Microsoft.Authorization/roleDefinitions/{roleDefinitionId}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<RoleDefinitionInner>> delete(@HostParam("$host") String host, @PathParam(value = "scope", encoded = true) String scope, @PathParam("roleDefinitionId") String roleDefinitionId, @QueryParam("api-version") String apiVersion);

        @Get("/{scope}/providers/Microsoft.Authorization/roleDefinitions/{roleDefinitionId}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<RoleDefinitionInner>> get(@HostParam("$host") String host, @PathParam(value = "scope", encoded = true) String scope, @PathParam("roleDefinitionId") String roleDefinitionId, @QueryParam("api-version") String apiVersion);

        @Put("/{scope}/providers/Microsoft.Authorization/roleDefinitions/{roleDefinitionId}")
        @ExpectedResponses({201})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<RoleDefinitionInner>> createOrUpdate(@HostParam("$host") String host, @PathParam(value = "scope", encoded = true) String scope, @PathParam("roleDefinitionId") String roleDefinitionId, @BodyParam("application/json") RoleDefinitionInner roleDefinition, @QueryParam("api-version") String apiVersion);

        @Get("/{scope}/providers/Microsoft.Authorization/roleDefinitions")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<RoleDefinitionListResultInner>> list(@HostParam("$host") String host, @PathParam(value = "scope", encoded = true) String scope, @QueryParam("%24filter") String filter, @QueryParam("api-version") String apiVersion);

        @Get("/{roleId}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<RoleDefinitionInner>> getById(@HostParam("$host") String host, @PathParam(value = "roleId", encoded = true) String roleId, @QueryParam("api-version") String apiVersion);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Mono<SimpleResponse<RoleDefinitionListResultInner>> listNext(@PathParam(value = "nextLink", encoded = true) String nextLink);
    }

    /**
     * Deletes a role definition.
     * 
     * @param scope MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param roleDefinitionId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<RoleDefinitionInner>> deleteWithResponseAsync(String scope, String roleDefinitionId) {
        return service.delete(this.client.getHost(), scope, roleDefinitionId, this.client.getApiVersion());
    }

    /**
     * Deletes a role definition.
     * 
     * @param scope MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param roleDefinitionId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<RoleDefinitionInner> deleteAsync(String scope, String roleDefinitionId) {
        return deleteWithResponseAsync(scope, roleDefinitionId)
            .flatMap((SimpleResponse<RoleDefinitionInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Deletes a role definition.
     * 
     * @param scope MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param roleDefinitionId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public RoleDefinitionInner delete(String scope, String roleDefinitionId) {
        return deleteAsync(scope, roleDefinitionId).block();
    }

    /**
     * Get role definition by name (GUID).
     * 
     * @param scope MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param roleDefinitionId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<RoleDefinitionInner>> getWithResponseAsync(String scope, String roleDefinitionId) {
        return service.get(this.client.getHost(), scope, roleDefinitionId, this.client.getApiVersion());
    }

    /**
     * Get role definition by name (GUID).
     * 
     * @param scope MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param roleDefinitionId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<RoleDefinitionInner> getAsync(String scope, String roleDefinitionId) {
        return getWithResponseAsync(scope, roleDefinitionId)
            .flatMap((SimpleResponse<RoleDefinitionInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Get role definition by name (GUID).
     * 
     * @param scope MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param roleDefinitionId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public RoleDefinitionInner get(String scope, String roleDefinitionId) {
        return getAsync(scope, roleDefinitionId).block();
    }

    /**
     * Creates or updates a role definition.
     * 
     * @param scope MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param roleDefinitionId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param roleDefinition Role definition.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<RoleDefinitionInner>> createOrUpdateWithResponseAsync(String scope, String roleDefinitionId, RoleDefinitionInner roleDefinition) {
        return service.createOrUpdate(this.client.getHost(), scope, roleDefinitionId, roleDefinition, this.client.getApiVersion());
    }

    /**
     * Creates or updates a role definition.
     * 
     * @param scope MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param roleDefinitionId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param roleDefinition Role definition.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<RoleDefinitionInner> createOrUpdateAsync(String scope, String roleDefinitionId, RoleDefinitionInner roleDefinition) {
        return createOrUpdateWithResponseAsync(scope, roleDefinitionId, roleDefinition)
            .flatMap((SimpleResponse<RoleDefinitionInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Creates or updates a role definition.
     * 
     * @param scope MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param roleDefinitionId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param roleDefinition Role definition.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public RoleDefinitionInner createOrUpdate(String scope, String roleDefinitionId, RoleDefinitionInner roleDefinition) {
        return createOrUpdateAsync(scope, roleDefinitionId, roleDefinition).block();
    }

    /**
     * Get all role definitions that are applicable at scope and above.
     * 
     * @param scope MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param filter MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<RoleDefinitionInner>> listSinglePageAsync(String scope, String filter) {
        return service.list(this.client.getHost(), scope, filter, this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }

    /**
     * Get all role definitions that are applicable at scope and above.
     * 
     * @param scope MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param filter MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<RoleDefinitionInner> listAsync(String scope, String filter) {
        return new PagedFlux<>(
            () -> listSinglePageAsync(scope, filter),
            nextLink -> listNextSinglePageAsync(nextLink));
    }

    /**
     * Get all role definitions that are applicable at scope and above.
     * 
     * @param scope MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param filter MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<RoleDefinitionInner> list(String scope, String filter) {
        return new PagedIterable<>(listAsync(scope, filter));
    }

    /**
     * Gets a role definition by ID.
     * 
     * @param roleId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<RoleDefinitionInner>> getByIdWithResponseAsync(String roleId) {
        return service.getById(this.client.getHost(), roleId, this.client.getApiVersion());
    }

    /**
     * Gets a role definition by ID.
     * 
     * @param roleId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<RoleDefinitionInner> getByIdAsync(String roleId) {
        return getByIdWithResponseAsync(roleId)
            .flatMap((SimpleResponse<RoleDefinitionInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Gets a role definition by ID.
     * 
     * @param roleId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public RoleDefinitionInner getById(String roleId) {
        return getByIdAsync(roleId).block();
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
    public Mono<PagedResponse<RoleDefinitionInner>> listNextSinglePageAsync(String nextLink) {
        return service.listNext(nextLink).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getNextLink(),
            null));
    }
}
