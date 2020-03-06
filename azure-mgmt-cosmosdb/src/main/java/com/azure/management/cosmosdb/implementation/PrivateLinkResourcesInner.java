/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.azure.management.cosmosdb.implementation;

import retrofit2.Retrofit;
import com.google.common.reflect.TypeToken;
import com.microsoft.azure.CloudException;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import com.microsoft.rest.ServiceResponse;
import java.io.IOException;
import java.util.List;
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
 * An instance of this class provides access to all the operations defined
 * in PrivateLinkResources.
 */
public class PrivateLinkResourcesInner {
    /** The Retrofit service to perform REST calls. */
    private PrivateLinkResourcesService service;
    /** The service client containing this operation class. */
    private CosmosDBImpl client;

    /**
     * Initializes an instance of PrivateLinkResourcesInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public PrivateLinkResourcesInner(Retrofit retrofit, CosmosDBImpl client) {
        this.service = retrofit.create(PrivateLinkResourcesService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for PrivateLinkResources to be
     * used by Retrofit to perform actually REST calls.
     */
    interface PrivateLinkResourcesService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.cosmosdb.PrivateLinkResources listByDatabaseAccount" })
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.DocumentDB/databaseAccounts/{accountName}/privateLinkResources")
        Observable<Response<ResponseBody>> listByDatabaseAccount(@Path("subscriptionId") String subscriptionId, @Path("resourceGroupName") String resourceGroupName, @Path("accountName") String accountName, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.cosmosdb.PrivateLinkResources get" })
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.DocumentDB/databaseAccounts/{accountName}/privateLinkResources/{groupName}")
        Observable<Response<ResponseBody>> get(@Path("subscriptionId") String subscriptionId, @Path("resourceGroupName") String resourceGroupName, @Path("accountName") String accountName, @Path("groupName") String groupName, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Gets the private link resources that need to be created for a Cosmos DB account.
     *
     * @param resourceGroupName Name of an Azure resource group.
     * @param accountName Cosmos DB database account name.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the List&lt;PrivateLinkResourceInner&gt; object if successful.
     */
    public List<PrivateLinkResourceInner> listByDatabaseAccount(String resourceGroupName, String accountName) {
        return listByDatabaseAccountWithServiceResponseAsync(resourceGroupName, accountName).toBlocking().single().body();
    }

    /**
     * Gets the private link resources that need to be created for a Cosmos DB account.
     *
     * @param resourceGroupName Name of an Azure resource group.
     * @param accountName Cosmos DB database account name.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<PrivateLinkResourceInner>> listByDatabaseAccountAsync(String resourceGroupName, String accountName, final ServiceCallback<List<PrivateLinkResourceInner>> serviceCallback) {
        return ServiceFuture.fromResponse(listByDatabaseAccountWithServiceResponseAsync(resourceGroupName, accountName), serviceCallback);
    }

    /**
     * Gets the private link resources that need to be created for a Cosmos DB account.
     *
     * @param resourceGroupName Name of an Azure resource group.
     * @param accountName Cosmos DB database account name.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the List&lt;PrivateLinkResourceInner&gt; object
     */
    public Observable<List<PrivateLinkResourceInner>> listByDatabaseAccountAsync(String resourceGroupName, String accountName) {
        return listByDatabaseAccountWithServiceResponseAsync(resourceGroupName, accountName).map(new Func1<ServiceResponse<List<PrivateLinkResourceInner>>, List<PrivateLinkResourceInner>>() {
            @Override
            public List<PrivateLinkResourceInner> call(ServiceResponse<List<PrivateLinkResourceInner>> response) {
                return response.body();
            }
        });
    }

    /**
     * Gets the private link resources that need to be created for a Cosmos DB account.
     *
     * @param resourceGroupName Name of an Azure resource group.
     * @param accountName Cosmos DB database account name.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the List&lt;PrivateLinkResourceInner&gt; object
     */
    public Observable<ServiceResponse<List<PrivateLinkResourceInner>>> listByDatabaseAccountWithServiceResponseAsync(String resourceGroupName, String accountName) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (accountName == null) {
            throw new IllegalArgumentException("Parameter accountName is required and cannot be null.");
        }
        final String apiVersion = "2019-08-01-preview";
        return service.listByDatabaseAccount(this.client.subscriptionId(), resourceGroupName, accountName, apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<List<PrivateLinkResourceInner>>>>() {
                @Override
                public Observable<ServiceResponse<List<PrivateLinkResourceInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<PrivateLinkResourceInner>> result = listByDatabaseAccountDelegate(response);
                        List<PrivateLinkResourceInner> items = null;
                        if (result.body() != null) {
                            items = result.body().items();
                        }
                        ServiceResponse<List<PrivateLinkResourceInner>> clientResponse = new ServiceResponse<List<PrivateLinkResourceInner>>(items, result.response());
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<PrivateLinkResourceInner>> listByDatabaseAccountDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PageImpl<PrivateLinkResourceInner>, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PageImpl<PrivateLinkResourceInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Gets the private link resources that need to be created for a Cosmos DB account.
     *
     * @param resourceGroupName Name of an Azure resource group.
     * @param accountName Cosmos DB database account name.
     * @param groupName The name of the private link resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PrivateLinkResourceInner object if successful.
     */
    public PrivateLinkResourceInner get(String resourceGroupName, String accountName, String groupName) {
        return getWithServiceResponseAsync(resourceGroupName, accountName, groupName).toBlocking().single().body();
    }

    /**
     * Gets the private link resources that need to be created for a Cosmos DB account.
     *
     * @param resourceGroupName Name of an Azure resource group.
     * @param accountName Cosmos DB database account name.
     * @param groupName The name of the private link resource.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<PrivateLinkResourceInner> getAsync(String resourceGroupName, String accountName, String groupName, final ServiceCallback<PrivateLinkResourceInner> serviceCallback) {
        return ServiceFuture.fromResponse(getWithServiceResponseAsync(resourceGroupName, accountName, groupName), serviceCallback);
    }

    /**
     * Gets the private link resources that need to be created for a Cosmos DB account.
     *
     * @param resourceGroupName Name of an Azure resource group.
     * @param accountName Cosmos DB database account name.
     * @param groupName The name of the private link resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PrivateLinkResourceInner object
     */
    public Observable<PrivateLinkResourceInner> getAsync(String resourceGroupName, String accountName, String groupName) {
        return getWithServiceResponseAsync(resourceGroupName, accountName, groupName).map(new Func1<ServiceResponse<PrivateLinkResourceInner>, PrivateLinkResourceInner>() {
            @Override
            public PrivateLinkResourceInner call(ServiceResponse<PrivateLinkResourceInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Gets the private link resources that need to be created for a Cosmos DB account.
     *
     * @param resourceGroupName Name of an Azure resource group.
     * @param accountName Cosmos DB database account name.
     * @param groupName The name of the private link resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PrivateLinkResourceInner object
     */
    public Observable<ServiceResponse<PrivateLinkResourceInner>> getWithServiceResponseAsync(String resourceGroupName, String accountName, String groupName) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (accountName == null) {
            throw new IllegalArgumentException("Parameter accountName is required and cannot be null.");
        }
        if (groupName == null) {
            throw new IllegalArgumentException("Parameter groupName is required and cannot be null.");
        }
        final String apiVersion = "2019-08-01-preview";
        return service.get(this.client.subscriptionId(), resourceGroupName, accountName, groupName, apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<PrivateLinkResourceInner>>>() {
                @Override
                public Observable<ServiceResponse<PrivateLinkResourceInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PrivateLinkResourceInner> clientResponse = getDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PrivateLinkResourceInner> getDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PrivateLinkResourceInner, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PrivateLinkResourceInner>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

}
