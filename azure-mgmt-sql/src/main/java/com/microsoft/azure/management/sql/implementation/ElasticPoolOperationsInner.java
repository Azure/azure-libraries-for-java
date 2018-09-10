/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import retrofit2.Retrofit;
import com.google.common.reflect.TypeToken;
import com.microsoft.azure.AzureServiceFuture;
import com.microsoft.azure.CloudException;
import com.microsoft.azure.ListOperationCallback;
import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import com.microsoft.rest.ServiceResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import retrofit2.Response;
import rx.functions.Func1;
import rx.Observable;

/**
 * An instance of this class provides access to all the operations defined
 * in ElasticPoolOperations.
 */
public class ElasticPoolOperationsInner {
    /** The Retrofit service to perform REST calls. */
    private ElasticPoolOperationsService service;
    /** The service client containing this operation class. */
    private SqlManagementClientImpl client;

    /**
     * Initializes an instance of ElasticPoolOperationsInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public ElasticPoolOperationsInner(Retrofit retrofit, SqlManagementClientImpl client) {
        this.service = retrofit.create(ElasticPoolOperationsService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for ElasticPoolOperations to be
     * used by Retrofit to perform actually REST calls.
     */
    interface ElasticPoolOperationsService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.sql.ElasticPoolOperations cancel" })
        @POST("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/elasticPools/{elasticPoolName}/operations/{operationId}/cancel")
        Observable<Response<ResponseBody>> cancel(@Path("resourceGroupName") String resourceGroupName, @Path("serverName") String serverName, @Path("elasticPoolName") String elasticPoolName, @Path("operationId") UUID operationId, @Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.sql.ElasticPoolOperations listByElasticPool" })
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/elasticPools/{elasticPoolName}/operations")
        Observable<Response<ResponseBody>> listByElasticPool(@Path("resourceGroupName") String resourceGroupName, @Path("serverName") String serverName, @Path("elasticPoolName") String elasticPoolName, @Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.sql.ElasticPoolOperations listByElasticPoolNext" })
        @GET
        Observable<Response<ResponseBody>> listByElasticPoolNext(@Url String nextUrl, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Cancels the asynchronous operation on the elastic pool.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param elasticPoolName the String value
     * @param operationId The operation identifier.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     */
    public void cancel(String resourceGroupName, String serverName, String elasticPoolName, UUID operationId) {
        cancelWithServiceResponseAsync(resourceGroupName, serverName, elasticPoolName, operationId).toBlocking().single().body();
    }

    /**
     * Cancels the asynchronous operation on the elastic pool.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param elasticPoolName the String value
     * @param operationId The operation identifier.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<Void> cancelAsync(String resourceGroupName, String serverName, String elasticPoolName, UUID operationId, final ServiceCallback<Void> serviceCallback) {
        return ServiceFuture.fromResponse(cancelWithServiceResponseAsync(resourceGroupName, serverName, elasticPoolName, operationId), serviceCallback);
    }

    /**
     * Cancels the asynchronous operation on the elastic pool.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param elasticPoolName the String value
     * @param operationId The operation identifier.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceResponse} object if successful.
     */
    public Observable<Void> cancelAsync(String resourceGroupName, String serverName, String elasticPoolName, UUID operationId) {
        return cancelWithServiceResponseAsync(resourceGroupName, serverName, elasticPoolName, operationId).map(new Func1<ServiceResponse<Void>, Void>() {
            @Override
            public Void call(ServiceResponse<Void> response) {
                return response.body();
            }
        });
    }

    /**
     * Cancels the asynchronous operation on the elastic pool.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param elasticPoolName the String value
     * @param operationId The operation identifier.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceResponse} object if successful.
     */
    public Observable<ServiceResponse<Void>> cancelWithServiceResponseAsync(String resourceGroupName, String serverName, String elasticPoolName, UUID operationId) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
        }
        if (elasticPoolName == null) {
            throw new IllegalArgumentException("Parameter elasticPoolName is required and cannot be null.");
        }
        if (operationId == null) {
            throw new IllegalArgumentException("Parameter operationId is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        final String apiVersion = "2017-10-01-preview";
        return service.cancel(resourceGroupName, serverName, elasticPoolName, operationId, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Void>>>() {
                @Override
                public Observable<ServiceResponse<Void>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<Void> clientResponse = cancelDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<Void> cancelDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<Void, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<Void>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Gets a list of operations performed on the elastic pool.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param elasticPoolName the String value
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PagedList&lt;ElasticPoolOperationInner&gt; object if successful.
     */
    public PagedList<ElasticPoolOperationInner> listByElasticPool(final String resourceGroupName, final String serverName, final String elasticPoolName) {
        ServiceResponse<Page<ElasticPoolOperationInner>> response = listByElasticPoolSinglePageAsync(resourceGroupName, serverName, elasticPoolName).toBlocking().single();
        return new PagedList<ElasticPoolOperationInner>(response.body()) {
            @Override
            public Page<ElasticPoolOperationInner> nextPage(String nextPageLink) {
                return listByElasticPoolNextSinglePageAsync(nextPageLink).toBlocking().single().body();
            }
        };
    }

    /**
     * Gets a list of operations performed on the elastic pool.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param elasticPoolName the String value
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<ElasticPoolOperationInner>> listByElasticPoolAsync(final String resourceGroupName, final String serverName, final String elasticPoolName, final ListOperationCallback<ElasticPoolOperationInner> serviceCallback) {
        return AzureServiceFuture.fromPageResponse(
            listByElasticPoolSinglePageAsync(resourceGroupName, serverName, elasticPoolName),
            new Func1<String, Observable<ServiceResponse<Page<ElasticPoolOperationInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ElasticPoolOperationInner>>> call(String nextPageLink) {
                    return listByElasticPoolNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * Gets a list of operations performed on the elastic pool.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param elasticPoolName the String value
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;ElasticPoolOperationInner&gt; object
     */
    public Observable<Page<ElasticPoolOperationInner>> listByElasticPoolAsync(final String resourceGroupName, final String serverName, final String elasticPoolName) {
        return listByElasticPoolWithServiceResponseAsync(resourceGroupName, serverName, elasticPoolName)
            .map(new Func1<ServiceResponse<Page<ElasticPoolOperationInner>>, Page<ElasticPoolOperationInner>>() {
                @Override
                public Page<ElasticPoolOperationInner> call(ServiceResponse<Page<ElasticPoolOperationInner>> response) {
                    return response.body();
                }
            });
    }

    /**
     * Gets a list of operations performed on the elastic pool.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param elasticPoolName the String value
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;ElasticPoolOperationInner&gt; object
     */
    public Observable<ServiceResponse<Page<ElasticPoolOperationInner>>> listByElasticPoolWithServiceResponseAsync(final String resourceGroupName, final String serverName, final String elasticPoolName) {
        return listByElasticPoolSinglePageAsync(resourceGroupName, serverName, elasticPoolName)
            .concatMap(new Func1<ServiceResponse<Page<ElasticPoolOperationInner>>, Observable<ServiceResponse<Page<ElasticPoolOperationInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ElasticPoolOperationInner>>> call(ServiceResponse<Page<ElasticPoolOperationInner>> page) {
                    String nextPageLink = page.body().nextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listByElasticPoolNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * Gets a list of operations performed on the elastic pool.
     *
    ServiceResponse<PageImpl1<ElasticPoolOperationInner>> * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
    ServiceResponse<PageImpl1<ElasticPoolOperationInner>> * @param serverName The name of the server.
    ServiceResponse<PageImpl1<ElasticPoolOperationInner>> * @param elasticPoolName the String value
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;ElasticPoolOperationInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<ElasticPoolOperationInner>>> listByElasticPoolSinglePageAsync(final String resourceGroupName, final String serverName, final String elasticPoolName) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
        }
        if (elasticPoolName == null) {
            throw new IllegalArgumentException("Parameter elasticPoolName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        final String apiVersion = "2017-10-01-preview";
        return service.listByElasticPool(resourceGroupName, serverName, elasticPoolName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<ElasticPoolOperationInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ElasticPoolOperationInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl1<ElasticPoolOperationInner>> result = listByElasticPoolDelegate(response);
                        return Observable.just(new ServiceResponse<Page<ElasticPoolOperationInner>>(result.body(), result.response()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl1<ElasticPoolOperationInner>> listByElasticPoolDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PageImpl1<ElasticPoolOperationInner>, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PageImpl1<ElasticPoolOperationInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Gets a list of operations performed on the elastic pool.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PagedList&lt;ElasticPoolOperationInner&gt; object if successful.
     */
    public PagedList<ElasticPoolOperationInner> listByElasticPoolNext(final String nextPageLink) {
        ServiceResponse<Page<ElasticPoolOperationInner>> response = listByElasticPoolNextSinglePageAsync(nextPageLink).toBlocking().single();
        return new PagedList<ElasticPoolOperationInner>(response.body()) {
            @Override
            public Page<ElasticPoolOperationInner> nextPage(String nextPageLink) {
                return listByElasticPoolNextSinglePageAsync(nextPageLink).toBlocking().single().body();
            }
        };
    }

    /**
     * Gets a list of operations performed on the elastic pool.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @param serviceFuture the ServiceFuture object tracking the Retrofit calls
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<ElasticPoolOperationInner>> listByElasticPoolNextAsync(final String nextPageLink, final ServiceFuture<List<ElasticPoolOperationInner>> serviceFuture, final ListOperationCallback<ElasticPoolOperationInner> serviceCallback) {
        return AzureServiceFuture.fromPageResponse(
            listByElasticPoolNextSinglePageAsync(nextPageLink),
            new Func1<String, Observable<ServiceResponse<Page<ElasticPoolOperationInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ElasticPoolOperationInner>>> call(String nextPageLink) {
                    return listByElasticPoolNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * Gets a list of operations performed on the elastic pool.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;ElasticPoolOperationInner&gt; object
     */
    public Observable<Page<ElasticPoolOperationInner>> listByElasticPoolNextAsync(final String nextPageLink) {
        return listByElasticPoolNextWithServiceResponseAsync(nextPageLink)
            .map(new Func1<ServiceResponse<Page<ElasticPoolOperationInner>>, Page<ElasticPoolOperationInner>>() {
                @Override
                public Page<ElasticPoolOperationInner> call(ServiceResponse<Page<ElasticPoolOperationInner>> response) {
                    return response.body();
                }
            });
    }

    /**
     * Gets a list of operations performed on the elastic pool.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;ElasticPoolOperationInner&gt; object
     */
    public Observable<ServiceResponse<Page<ElasticPoolOperationInner>>> listByElasticPoolNextWithServiceResponseAsync(final String nextPageLink) {
        return listByElasticPoolNextSinglePageAsync(nextPageLink)
            .concatMap(new Func1<ServiceResponse<Page<ElasticPoolOperationInner>>, Observable<ServiceResponse<Page<ElasticPoolOperationInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ElasticPoolOperationInner>>> call(ServiceResponse<Page<ElasticPoolOperationInner>> page) {
                    String nextPageLink = page.body().nextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listByElasticPoolNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * Gets a list of operations performed on the elastic pool.
     *
    ServiceResponse<PageImpl1<ElasticPoolOperationInner>> * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;ElasticPoolOperationInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<ElasticPoolOperationInner>>> listByElasticPoolNextSinglePageAsync(final String nextPageLink) {
        if (nextPageLink == null) {
            throw new IllegalArgumentException("Parameter nextPageLink is required and cannot be null.");
        }
        String nextUrl = String.format("%s", nextPageLink);
        return service.listByElasticPoolNext(nextUrl, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<ElasticPoolOperationInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ElasticPoolOperationInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl1<ElasticPoolOperationInner>> result = listByElasticPoolNextDelegate(response);
                        return Observable.just(new ServiceResponse<Page<ElasticPoolOperationInner>>(result.body(), result.response()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl1<ElasticPoolOperationInner>> listByElasticPoolNextDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PageImpl1<ElasticPoolOperationInner>, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PageImpl1<ElasticPoolOperationInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

}
