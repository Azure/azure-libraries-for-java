/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.containerinstance.implementation;

import com.google.common.reflect.TypeToken;
import com.microsoft.azure.AzureClient;
import com.microsoft.azure.AzureServiceClient;
import com.microsoft.azure.CloudException;
import com.microsoft.rest.credentials.ServiceClientCredentials;
import com.microsoft.rest.RestClient;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import com.microsoft.rest.ServiceResponse;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.Response;
import rx.functions.Func1;
import rx.Observable;

/**
 * Initializes a new instance of the ContainerInstanceManagementClientImpl class.
 */
public class ContainerInstanceManagementClientImpl extends AzureServiceClient {
    /** The Retrofit service to perform REST calls. */
    private ContainerInstanceManagementClientService service;
    /** the {@link AzureClient} used for long running operations. */
    private AzureClient azureClient;

    /**
     * Gets the {@link AzureClient} used for long running operations.
     * @return the azure client;
     */
    public AzureClient getAzureClient() {
        return this.azureClient;
    }

    /** Subscription credentials which uniquely identify Microsoft Azure subscription. The subscription ID forms part of the URI for every service call. */
    private String subscriptionId;

    /**
     * Gets Subscription credentials which uniquely identify Microsoft Azure subscription. The subscription ID forms part of the URI for every service call.
     *
     * @return the subscriptionId value.
     */
    public String subscriptionId() {
        return this.subscriptionId;
    }

    /**
     * Sets Subscription credentials which uniquely identify Microsoft Azure subscription. The subscription ID forms part of the URI for every service call.
     *
     * @param subscriptionId the subscriptionId value.
     * @return the service client itself
     */
    public ContainerInstanceManagementClientImpl withSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    /** Client API version. */
    private String apiVersion;

    /**
     * Gets Client API version.
     *
     * @return the apiVersion value.
     */
    public String apiVersion() {
        return this.apiVersion;
    }

    /** The preferred language for the response. */
    private String acceptLanguage;

    /**
     * Gets The preferred language for the response.
     *
     * @return the acceptLanguage value.
     */
    public String acceptLanguage() {
        return this.acceptLanguage;
    }

    /**
     * Sets The preferred language for the response.
     *
     * @param acceptLanguage the acceptLanguage value.
     * @return the service client itself
     */
    public ContainerInstanceManagementClientImpl withAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
        return this;
    }

    /** The retry timeout in seconds for Long Running Operations. Default value is 30. */
    private int longRunningOperationRetryTimeout;

    /**
     * Gets The retry timeout in seconds for Long Running Operations. Default value is 30.
     *
     * @return the longRunningOperationRetryTimeout value.
     */
    public int longRunningOperationRetryTimeout() {
        return this.longRunningOperationRetryTimeout;
    }

    /**
     * Sets The retry timeout in seconds for Long Running Operations. Default value is 30.
     *
     * @param longRunningOperationRetryTimeout the longRunningOperationRetryTimeout value.
     * @return the service client itself
     */
    public ContainerInstanceManagementClientImpl withLongRunningOperationRetryTimeout(int longRunningOperationRetryTimeout) {
        this.longRunningOperationRetryTimeout = longRunningOperationRetryTimeout;
        return this;
    }

    /** Whether a unique x-ms-client-request-id should be generated. When set to true a unique x-ms-client-request-id value is generated and included in each request. Default is true. */
    private boolean generateClientRequestId;

    /**
     * Gets Whether a unique x-ms-client-request-id should be generated. When set to true a unique x-ms-client-request-id value is generated and included in each request. Default is true.
     *
     * @return the generateClientRequestId value.
     */
    public boolean generateClientRequestId() {
        return this.generateClientRequestId;
    }

    /**
     * Sets Whether a unique x-ms-client-request-id should be generated. When set to true a unique x-ms-client-request-id value is generated and included in each request. Default is true.
     *
     * @param generateClientRequestId the generateClientRequestId value.
     * @return the service client itself
     */
    public ContainerInstanceManagementClientImpl withGenerateClientRequestId(boolean generateClientRequestId) {
        this.generateClientRequestId = generateClientRequestId;
        return this;
    }

    /**
     * The ContainerGroupsInner object to access its operations.
     */
    private ContainerGroupsInner containerGroups;

    /**
     * Gets the ContainerGroupsInner object to access its operations.
     * @return the ContainerGroupsInner object.
     */
    public ContainerGroupsInner containerGroups() {
        return this.containerGroups;
    }

    /**
     * The OperationsInner object to access its operations.
     */
    private OperationsInner operations;

    /**
     * Gets the OperationsInner object to access its operations.
     * @return the OperationsInner object.
     */
    public OperationsInner operations() {
        return this.operations;
    }

    /**
     * The ContainerGroupUsagesInner object to access its operations.
     */
    private ContainerGroupUsagesInner containerGroupUsages;

    /**
     * Gets the ContainerGroupUsagesInner object to access its operations.
     * @return the ContainerGroupUsagesInner object.
     */
    public ContainerGroupUsagesInner containerGroupUsages() {
        return this.containerGroupUsages;
    }

    /**
     * The ContainersInner object to access its operations.
     */
    private ContainersInner containers;

    /**
     * Gets the ContainersInner object to access its operations.
     * @return the ContainersInner object.
     */
    public ContainersInner containers() {
        return this.containers;
    }

    /**
     * The ServiceAssociationLinksInner object to access its operations.
     */
    private ServiceAssociationLinksInner serviceAssociationLinks;

    /**
     * Gets the ServiceAssociationLinksInner object to access its operations.
     * @return the ServiceAssociationLinksInner object.
     */
    public ServiceAssociationLinksInner serviceAssociationLinks() {
        return this.serviceAssociationLinks;
    }

    /**
     * Initializes an instance of ContainerInstanceManagementClient client.
     *
     * @param credentials the management credentials for Azure
     */
    public ContainerInstanceManagementClientImpl(ServiceClientCredentials credentials) {
        this("https://management.azure.com", credentials);
    }

    /**
     * Initializes an instance of ContainerInstanceManagementClient client.
     *
     * @param baseUrl the base URL of the host
     * @param credentials the management credentials for Azure
     */
    public ContainerInstanceManagementClientImpl(String baseUrl, ServiceClientCredentials credentials) {
        super(baseUrl, credentials);
        initialize();
    }

    /**
     * Initializes an instance of ContainerInstanceManagementClient client.
     *
     * @param restClient the REST client to connect to Azure.
     */
    public ContainerInstanceManagementClientImpl(RestClient restClient) {
        super(restClient);
        initialize();
    }

    protected void initialize() {
        this.apiVersion = "2018-10-01";
        this.acceptLanguage = "en-US";
        this.longRunningOperationRetryTimeout = 30;
        this.generateClientRequestId = true;
        this.containerGroups = new ContainerGroupsInner(restClient().retrofit(), this);
        this.operations = new OperationsInner(restClient().retrofit(), this);
        this.containerGroupUsages = new ContainerGroupUsagesInner(restClient().retrofit(), this);
        this.containers = new ContainersInner(restClient().retrofit(), this);
        this.serviceAssociationLinks = new ServiceAssociationLinksInner(restClient().retrofit(), this);
        this.azureClient = new AzureClient(this);
        initializeService();
    }

    /**
     * Gets the User-Agent header for the client.
     *
     * @return the user agent string.
     */
    @Override
    public String userAgent() {
        return String.format("%s (%s, %s)", super.userAgent(), "ContainerInstanceManagementClient", "2018-10-01");
    }

    private void initializeService() {
        service = restClient().retrofit().create(ContainerInstanceManagementClientService.class);
    }

    /**
     * The interface defining all the services for ContainerInstanceManagementClient to be
     * used by Retrofit to perform actually REST calls.
     */
    interface ContainerInstanceManagementClientService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.containerinstance.ContainerInstanceManagementClient listCachedImages" })
        @GET("subscriptions/{subscriptionId}/providers/Microsoft.ContainerInstance/locations/{location}/cachedImages")
        Observable<Response<ResponseBody>> listCachedImages(@Path("subscriptionId") String subscriptionId, @Path("location") String location, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.containerinstance.ContainerInstanceManagementClient listCapabilities" })
        @GET("subscriptions/{subscriptionId}/providers/Microsoft.ContainerInstance/locations/{location}/capabilities")
        Observable<Response<ResponseBody>> listCapabilities(@Path("subscriptionId") String subscriptionId, @Path("location") String location, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Get the list of cached images.
     * Get the list of cached images on specific OS type for a subscription in a region.
     *
     * @param location The identifier for the physical azure location.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the CachedImagesListResultInner object if successful.
     */
    public CachedImagesListResultInner listCachedImages(String location) {
        return listCachedImagesWithServiceResponseAsync(location).toBlocking().single().body();
    }

    /**
     * Get the list of cached images.
     * Get the list of cached images on specific OS type for a subscription in a region.
     *
     * @param location The identifier for the physical azure location.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<CachedImagesListResultInner> listCachedImagesAsync(String location, final ServiceCallback<CachedImagesListResultInner> serviceCallback) {
        return ServiceFuture.fromResponse(listCachedImagesWithServiceResponseAsync(location), serviceCallback);
    }

    /**
     * Get the list of cached images.
     * Get the list of cached images on specific OS type for a subscription in a region.
     *
     * @param location The identifier for the physical azure location.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the CachedImagesListResultInner object
     */
    public Observable<CachedImagesListResultInner> listCachedImagesAsync(String location) {
        return listCachedImagesWithServiceResponseAsync(location).map(new Func1<ServiceResponse<CachedImagesListResultInner>, CachedImagesListResultInner>() {
            @Override
            public CachedImagesListResultInner call(ServiceResponse<CachedImagesListResultInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Get the list of cached images.
     * Get the list of cached images on specific OS type for a subscription in a region.
     *
     * @param location The identifier for the physical azure location.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the CachedImagesListResultInner object
     */
    public Observable<ServiceResponse<CachedImagesListResultInner>> listCachedImagesWithServiceResponseAsync(String location) {
        if (this.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.subscriptionId() is required and cannot be null.");
        }
        if (location == null) {
            throw new IllegalArgumentException("Parameter location is required and cannot be null.");
        }
        if (this.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.apiVersion() is required and cannot be null.");
        }
        return service.listCachedImages(this.subscriptionId(), location, this.apiVersion(), this.acceptLanguage(), this.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<CachedImagesListResultInner>>>() {
                @Override
                public Observable<ServiceResponse<CachedImagesListResultInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<CachedImagesListResultInner> clientResponse = listCachedImagesDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<CachedImagesListResultInner> listCachedImagesDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.restClient().responseBuilderFactory().<CachedImagesListResultInner, CloudException>newInstance(this.serializerAdapter())
                .register(200, new TypeToken<CachedImagesListResultInner>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Get the list of capabilities of the location.
     * Get the list of CPU/memory/GPU capabilities of a region.
     *
     * @param location The identifier for the physical azure location.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the CapabilitiesListResultInner object if successful.
     */
    public CapabilitiesListResultInner listCapabilities(String location) {
        return listCapabilitiesWithServiceResponseAsync(location).toBlocking().single().body();
    }

    /**
     * Get the list of capabilities of the location.
     * Get the list of CPU/memory/GPU capabilities of a region.
     *
     * @param location The identifier for the physical azure location.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<CapabilitiesListResultInner> listCapabilitiesAsync(String location, final ServiceCallback<CapabilitiesListResultInner> serviceCallback) {
        return ServiceFuture.fromResponse(listCapabilitiesWithServiceResponseAsync(location), serviceCallback);
    }

    /**
     * Get the list of capabilities of the location.
     * Get the list of CPU/memory/GPU capabilities of a region.
     *
     * @param location The identifier for the physical azure location.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the CapabilitiesListResultInner object
     */
    public Observable<CapabilitiesListResultInner> listCapabilitiesAsync(String location) {
        return listCapabilitiesWithServiceResponseAsync(location).map(new Func1<ServiceResponse<CapabilitiesListResultInner>, CapabilitiesListResultInner>() {
            @Override
            public CapabilitiesListResultInner call(ServiceResponse<CapabilitiesListResultInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Get the list of capabilities of the location.
     * Get the list of CPU/memory/GPU capabilities of a region.
     *
     * @param location The identifier for the physical azure location.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the CapabilitiesListResultInner object
     */
    public Observable<ServiceResponse<CapabilitiesListResultInner>> listCapabilitiesWithServiceResponseAsync(String location) {
        if (this.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.subscriptionId() is required and cannot be null.");
        }
        if (location == null) {
            throw new IllegalArgumentException("Parameter location is required and cannot be null.");
        }
        if (this.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.apiVersion() is required and cannot be null.");
        }
        return service.listCapabilities(this.subscriptionId(), location, this.apiVersion(), this.acceptLanguage(), this.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<CapabilitiesListResultInner>>>() {
                @Override
                public Observable<ServiceResponse<CapabilitiesListResultInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<CapabilitiesListResultInner> clientResponse = listCapabilitiesDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<CapabilitiesListResultInner> listCapabilitiesDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.restClient().responseBuilderFactory().<CapabilitiesListResultInner, CloudException>newInstance(this.serializerAdapter())
                .register(200, new TypeToken<CapabilitiesListResultInner>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

}
