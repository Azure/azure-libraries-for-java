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
import com.azure.core.annotation.Patch;
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
import com.azure.management.graphrbac.DirectoryObject;
import com.azure.management.graphrbac.KeyCredential;
import com.azure.management.graphrbac.KeyCredentialsUpdateParameters;
import com.azure.management.graphrbac.PasswordCredential;
import com.azure.management.graphrbac.PasswordCredentialsUpdateParameters;
import com.azure.management.graphrbac.ServicePrincipalCreateParameters;
import com.azure.management.graphrbac.ServicePrincipalUpdateParameters;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * ServicePrincipals.
 */
public final class ServicePrincipalsInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private ServicePrincipalsService service;

    /**
     * The service client containing this operation class.
     */
    private GraphRbacManagementClientImpl client;

    /**
     * Initializes an instance of ServicePrincipalsInner.
     * 
     * @param client the instance of the service client containing this operation class.
     */
    public ServicePrincipalsInner(GraphRbacManagementClientImpl client) {
        this.service = RestProxy.create(ServicePrincipalsService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * GraphRbacManagementClientServicePrincipals to be used by the proxy
     * service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "GraphRbacManagementClientServicePrincipals")
    private interface ServicePrincipalsService {
        @Post("/{tenantID}/servicePrincipals")
        @ExpectedResponses({201})
        @UnexpectedResponseExceptionType(GraphErrorException.class)
        Mono<SimpleResponse<ServicePrincipalInner>> create(@HostParam("$host") String host, @PathParam("tenantID") String tenantID, @BodyParam("application/json") ServicePrincipalCreateParameters parameters, @QueryParam("api-version") String apiVersion);

        @Get("/{tenantID}/servicePrincipals")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(GraphErrorException.class)
        Mono<SimpleResponse<ServicePrincipalListResultInner>> list(@HostParam("$host") String host, @QueryParam("$filter") String filter, @PathParam("tenantID") String tenantID, @QueryParam("api-version") String apiVersion);

        @Patch("/{tenantID}/servicePrincipals/{objectId}")
        @ExpectedResponses({204})
        @UnexpectedResponseExceptionType(GraphErrorException.class)
        Mono<Response<Void>> update(@HostParam("$host") String host, @PathParam("objectId") String objectId, @PathParam("tenantID") String tenantID, @BodyParam("application/json") ServicePrincipalUpdateParameters parameters, @QueryParam("api-version") String apiVersion);

        @Delete("/{tenantID}/servicePrincipals/{objectId}")
        @ExpectedResponses({204})
        @UnexpectedResponseExceptionType(GraphErrorException.class)
        Mono<Response<Void>> delete(@HostParam("$host") String host, @PathParam("objectId") String objectId, @PathParam("tenantID") String tenantID, @QueryParam("api-version") String apiVersion);

        @Get("/{tenantID}/servicePrincipals/{objectId}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(GraphErrorException.class)
        Mono<SimpleResponse<ServicePrincipalInner>> get(@HostParam("$host") String host, @PathParam("objectId") String objectId, @PathParam("tenantID") String tenantID, @QueryParam("api-version") String apiVersion);

        @Get("/{tenantID}/servicePrincipals/{objectId}/owners")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(GraphErrorException.class)
        Mono<SimpleResponse<DirectoryObjectListResultInner>> listOwners(@HostParam("$host") String host, @PathParam("objectId") String objectId, @PathParam("tenantID") String tenantID, @QueryParam("api-version") String apiVersion);

        @Get("/{tenantID}/servicePrincipals/{objectId}/keyCredentials")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(GraphErrorException.class)
        Mono<SimpleResponse<KeyCredentialListResultInner>> listKeyCredentials(@HostParam("$host") String host, @PathParam("objectId") String objectId, @PathParam("tenantID") String tenantID, @QueryParam("api-version") String apiVersion);

        @Patch("/{tenantID}/servicePrincipals/{objectId}/keyCredentials")
        @ExpectedResponses({204})
        @UnexpectedResponseExceptionType(GraphErrorException.class)
        Mono<Response<Void>> updateKeyCredentials(@HostParam("$host") String host, @PathParam("objectId") String objectId, @PathParam("tenantID") String tenantID, @BodyParam("application/json") KeyCredentialsUpdateParameters parameters, @QueryParam("api-version") String apiVersion);

        @Get("/{tenantID}/servicePrincipals/{objectId}/passwordCredentials")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(GraphErrorException.class)
        Mono<SimpleResponse<PasswordCredentialListResultInner>> listPasswordCredentials(@HostParam("$host") String host, @PathParam("objectId") String objectId, @PathParam("tenantID") String tenantID, @QueryParam("api-version") String apiVersion);

        @Patch("/{tenantID}/servicePrincipals/{objectId}/passwordCredentials")
        @ExpectedResponses({204})
        @UnexpectedResponseExceptionType(GraphErrorException.class)
        Mono<Response<Void>> updatePasswordCredentials(@HostParam("$host") String host, @PathParam("objectId") String objectId, @PathParam("tenantID") String tenantID, @BodyParam("application/json") PasswordCredentialsUpdateParameters parameters, @QueryParam("api-version") String apiVersion);

        @Get("/{tenantID}/{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(GraphErrorException.class)
        Mono<SimpleResponse<ServicePrincipalListResultInner>> listNext(@HostParam("$host") String host, @PathParam(value = "nextLink", encoded = true) String nextLink, @PathParam("tenantID") String tenantID, @QueryParam("api-version") String apiVersion);

        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(GraphErrorException.class)
        Mono<SimpleResponse<DirectoryObjectListResultInner>> listOwnersNext(@PathParam(value = "nextLink", encoded = true) String nextLink);
    }

    /**
     * Creates a service principal in the directory.
     * 
     * @param parameters Request parameters for creating a new service principal.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ServicePrincipalInner>> createWithResponseAsync(ServicePrincipalCreateParameters parameters) {
        return service.create(this.client.getHost(), this.client.getTenantID(), parameters, this.client.getApiVersion());
    }

    /**
     * Creates a service principal in the directory.
     * 
     * @param parameters Request parameters for creating a new service principal.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ServicePrincipalInner> createAsync(ServicePrincipalCreateParameters parameters) {
        return createWithResponseAsync(parameters)
            .flatMap((SimpleResponse<ServicePrincipalInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Creates a service principal in the directory.
     * 
     * @param parameters Request parameters for creating a new service principal.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ServicePrincipalInner create(ServicePrincipalCreateParameters parameters) {
        return createAsync(parameters).block();
    }

    /**
     * Gets a list of service principals from the current tenant.
     * 
     * @param filter MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<ServicePrincipalInner>> listSinglePageAsync(String filter) {
        return service.list(this.client.getHost(), filter, this.client.getTenantID(), this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getOdatanextLink(),
            null));
    }

    /**
     * Gets a list of service principals from the current tenant.
     * 
     * @param filter MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<ServicePrincipalInner> listAsync(String filter) {
        return new PagedFlux<>(
            () -> listSinglePageAsync(filter),
            nextLink -> listNextSinglePageAsync(nextLink));
    }

    /**
     * Gets a list of service principals from the current tenant.
     * 
     * @param filter MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<ServicePrincipalInner> list(String filter) {
        return new PagedIterable<>(listAsync(filter));
    }

    /**
     * Updates a service principal in the directory.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param parameters Request parameters for update an existing service principal.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> updateWithResponseAsync(String objectId, ServicePrincipalUpdateParameters parameters) {
        return service.update(this.client.getHost(), objectId, this.client.getTenantID(), parameters, this.client.getApiVersion());
    }

    /**
     * Updates a service principal in the directory.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param parameters Request parameters for update an existing service principal.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> updateAsync(String objectId, ServicePrincipalUpdateParameters parameters) {
        return updateWithResponseAsync(objectId, parameters)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Updates a service principal in the directory.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param parameters Request parameters for update an existing service principal.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void update(String objectId, ServicePrincipalUpdateParameters parameters) {
        updateAsync(objectId, parameters).block();
    }

    /**
     * Deletes a service principal from the directory.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> deleteWithResponseAsync(String objectId) {
        return service.delete(this.client.getHost(), objectId, this.client.getTenantID(), this.client.getApiVersion());
    }

    /**
     * Deletes a service principal from the directory.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteAsync(String objectId) {
        return deleteWithResponseAsync(objectId)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Deletes a service principal from the directory.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void delete(String objectId) {
        deleteAsync(objectId).block();
    }

    /**
     * Gets service principal information from the directory. Query by objectId or pass a filter to query by appId.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ServicePrincipalInner>> getWithResponseAsync(String objectId) {
        return service.get(this.client.getHost(), objectId, this.client.getTenantID(), this.client.getApiVersion());
    }

    /**
     * Gets service principal information from the directory. Query by objectId or pass a filter to query by appId.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ServicePrincipalInner> getAsync(String objectId) {
        return getWithResponseAsync(objectId)
            .flatMap((SimpleResponse<ServicePrincipalInner> res) -> {
                if (res.getValue() != null) {
                    return Mono.just(res.getValue());
                } else {
                    return Mono.empty();
                }
            });
    }

    /**
     * Gets service principal information from the directory. Query by objectId or pass a filter to query by appId.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ServicePrincipalInner get(String objectId) {
        return getAsync(objectId).block();
    }

    /**
     * The owners are a set of non-admin users who are allowed to modify this object.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<DirectoryObject>> listOwnersSinglePageAsync(String objectId) {
        return service.listOwners(this.client.getHost(), objectId, this.client.getTenantID(), this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getOdatanextLink(),
            null));
    }

    /**
     * The owners are a set of non-admin users who are allowed to modify this object.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<DirectoryObject> listOwnersAsync(String objectId) {
        return new PagedFlux<>(
            () -> listOwnersSinglePageAsync(objectId),
            nextLink -> listOwnersNextSinglePageAsync(nextLink));
    }

    /**
     * The owners are a set of non-admin users who are allowed to modify this object.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<DirectoryObject> listOwners(String objectId) {
        return new PagedIterable<>(listOwnersAsync(objectId));
    }

    /**
     * Get the keyCredentials associated with the specified service principal.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<KeyCredential>> listKeyCredentialsSinglePageAsync(String objectId) {
        return service.listKeyCredentials(this.client.getHost(), objectId, this.client.getTenantID(), this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            null,
            null));
    }

    /**
     * Get the keyCredentials associated with the specified service principal.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<KeyCredential> listKeyCredentialsAsync(String objectId) {
        return new PagedFlux<>(
            () -> listKeyCredentialsSinglePageAsync(objectId));
    }

    /**
     * Get the keyCredentials associated with the specified service principal.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<KeyCredential> listKeyCredentials(String objectId) {
        return new PagedIterable<>(listKeyCredentialsAsync(objectId));
    }

    /**
     * Update the keyCredentials associated with a service principal.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param parameters Request parameters for a KeyCredentials update operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> updateKeyCredentialsWithResponseAsync(String objectId, KeyCredentialsUpdateParameters parameters) {
        return service.updateKeyCredentials(this.client.getHost(), objectId, this.client.getTenantID(), parameters, this.client.getApiVersion());
    }

    /**
     * Update the keyCredentials associated with a service principal.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param parameters Request parameters for a KeyCredentials update operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> updateKeyCredentialsAsync(String objectId, KeyCredentialsUpdateParameters parameters) {
        return updateKeyCredentialsWithResponseAsync(objectId, parameters)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Update the keyCredentials associated with a service principal.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param parameters Request parameters for a KeyCredentials update operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void updateKeyCredentials(String objectId, KeyCredentialsUpdateParameters parameters) {
        updateKeyCredentialsAsync(objectId, parameters).block();
    }

    /**
     * Gets the passwordCredentials associated with a service principal.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<PasswordCredential>> listPasswordCredentialsSinglePageAsync(String objectId) {
        return service.listPasswordCredentials(this.client.getHost(), objectId, this.client.getTenantID(), this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            null,
            null));
    }

    /**
     * Gets the passwordCredentials associated with a service principal.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<PasswordCredential> listPasswordCredentialsAsync(String objectId) {
        return new PagedFlux<>(
            () -> listPasswordCredentialsSinglePageAsync(objectId));
    }

    /**
     * Gets the passwordCredentials associated with a service principal.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<PasswordCredential> listPasswordCredentials(String objectId) {
        return new PagedIterable<>(listPasswordCredentialsAsync(objectId));
    }

    /**
     * Updates the passwordCredentials associated with a service principal.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param parameters Request parameters for a PasswordCredentials update operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> updatePasswordCredentialsWithResponseAsync(String objectId, PasswordCredentialsUpdateParameters parameters) {
        return service.updatePasswordCredentials(this.client.getHost(), objectId, this.client.getTenantID(), parameters, this.client.getApiVersion());
    }

    /**
     * Updates the passwordCredentials associated with a service principal.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param parameters Request parameters for a PasswordCredentials update operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> updatePasswordCredentialsAsync(String objectId, PasswordCredentialsUpdateParameters parameters) {
        return updatePasswordCredentialsWithResponseAsync(objectId, parameters)
            .flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Updates the passwordCredentials associated with a service principal.
     * 
     * @param objectId MISSING·SCHEMA-DESCRIPTION-STRING.
     * @param parameters Request parameters for a PasswordCredentials update operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void updatePasswordCredentials(String objectId, PasswordCredentialsUpdateParameters parameters) {
        updatePasswordCredentialsAsync(objectId, parameters).block();
    }

    /**
     * Gets a list of service principals from the current tenant.
     * 
     * @param nextLink MISSING·SCHEMA-DESCRIPTION-STRING.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<ServicePrincipalInner>> listNextSinglePageAsync(String nextLink) {
        return service.listNext(this.client.getHost(), nextLink, this.client.getTenantID(), this.client.getApiVersion()).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getOdatanextLink(),
            null));
    }

    /**
     * Get the next page of items.
     * 
     * @param nextLink null
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws GraphErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<DirectoryObject>> listOwnersNextSinglePageAsync(String nextLink) {
        return service.listOwnersNext(nextLink).map(res -> new PagedResponseBase<>(
            res.getRequest(),
            res.getStatusCode(),
            res.getHeaders(),
            res.getValue().getValue(),
            res.getValue().getOdatanextLink(),
            null));
    }
}
