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
import com.microsoft.azure.AzureServiceCall;
import com.microsoft.azure.AzureServiceResponseBuilder;
import com.microsoft.azure.ListOperationCallback;
import com.microsoft.azure.management.sql.ArmErrorResponseMessageException;
import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.rest.ServiceCall;
import com.microsoft.rest.ServiceResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
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
 * in EngineAuditRecords.
 */
public final class EngineAuditRecordsInner {
    /** The Retrofit service to perform REST calls. */
    private EngineAuditRecordsService service;
    /** The service client containing this operation class. */
    private SqlManagementClientImpl client;

    /**
     * Initializes an instance of EngineAuditRecordsInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public EngineAuditRecordsInner(Retrofit retrofit, SqlManagementClientImpl client) {
        this.service = retrofit.create(EngineAuditRecordsService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for EngineAuditRecords to be
     * used by Retrofit to perform actually REST calls.
     */
    interface EngineAuditRecordsService {
        @Headers("Content-Type: application/json; charset=utf-8")
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/databases/{databaseName}/auditRecords")
        Observable<Response<ResponseBody>> list(@Path("subscriptionId") UUID subscriptionId, @Path("resourceGroupName") String resourceGroupName, @Path("serverName") String serverName, @Path("databaseName") String databaseName, @Query("api-version") String apiVersion, @Query("$filter") String filter, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers("Content-Type: application/json; charset=utf-8")
        @GET("{nextLink}")
        Observable<Response<ResponseBody>> listNext(@Path(value = "nextLink", encoded = true) String nextPageLink, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Get a list of database engine audit records.
     *
     * @param resourceGroupName The name of the Resource Group to which the Azure SQL Database Server belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database for which database engine audit records are retrieved.
     * @return the PagedList&lt;EngineAuditRecordResourceInner&gt; object if successful.
     */
    public PagedList<EngineAuditRecordResourceInner> list(final String resourceGroupName, final String serverName, final String databaseName) {
        ServiceResponse<Page<EngineAuditRecordResourceInner>> response = listSinglePageAsync(resourceGroupName, serverName, databaseName).toBlocking().single();
        return new PagedList<EngineAuditRecordResourceInner>(response.getBody()) {
            @Override
            public Page<EngineAuditRecordResourceInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).toBlocking().single().getBody();
            }
        };
    }

    /**
     * Get a list of database engine audit records.
     *
     * @param resourceGroupName The name of the Resource Group to which the Azure SQL Database Server belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database for which database engine audit records are retrieved.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @return the {@link ServiceCall} object
     */
    public ServiceCall<List<EngineAuditRecordResourceInner>> listAsync(final String resourceGroupName, final String serverName, final String databaseName, final ListOperationCallback<EngineAuditRecordResourceInner> serviceCallback) {
        return AzureServiceCall.create(
            listSinglePageAsync(resourceGroupName, serverName, databaseName),
            new Func1<String, Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>> call(String nextPageLink) {
                    return listNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * Get a list of database engine audit records.
     *
     * @param resourceGroupName The name of the Resource Group to which the Azure SQL Database Server belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database for which database engine audit records are retrieved.
     * @return the observable to the PagedList&lt;EngineAuditRecordResourceInner&gt; object
     */
    public Observable<Page<EngineAuditRecordResourceInner>> listAsync(final String resourceGroupName, final String serverName, final String databaseName) {
        return listWithServiceResponseAsync(resourceGroupName, serverName, databaseName)
            .map(new Func1<ServiceResponse<Page<EngineAuditRecordResourceInner>>, Page<EngineAuditRecordResourceInner>>() {
                @Override
                public Page<EngineAuditRecordResourceInner> call(ServiceResponse<Page<EngineAuditRecordResourceInner>> response) {
                    return response.getBody();
                }
            });
    }

    /**
     * Get a list of database engine audit records.
     *
     * @param resourceGroupName The name of the Resource Group to which the Azure SQL Database Server belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database for which database engine audit records are retrieved.
     * @return the observable to the PagedList&lt;EngineAuditRecordResourceInner&gt; object
     */
    public Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>> listWithServiceResponseAsync(final String resourceGroupName, final String serverName, final String databaseName) {
        return listSinglePageAsync(resourceGroupName, serverName, databaseName)
            .concatMap(new Func1<ServiceResponse<Page<EngineAuditRecordResourceInner>>, Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>> call(ServiceResponse<Page<EngineAuditRecordResourceInner>> page) {
                    String nextPageLink = page.getBody().getNextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * Get a list of database engine audit records.
     *
     * @param resourceGroupName The name of the Resource Group to which the Azure SQL Database Server belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database for which database engine audit records are retrieved.
     * @return the PagedList&lt;EngineAuditRecordResourceInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>> listSinglePageAsync(final String resourceGroupName, final String serverName, final String databaseName) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
        }
        if (databaseName == null) {
            throw new IllegalArgumentException("Parameter databaseName is required and cannot be null.");
        }
        final String apiVersion = "2015-05-01-preview";
        final String filter = null;
        return service.list(this.client.subscriptionId(), resourceGroupName, serverName, databaseName, apiVersion, filter, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<EngineAuditRecordResourceInner>> result = listDelegate(response);
                        return Observable.just(new ServiceResponse<Page<EngineAuditRecordResourceInner>>(result.getBody(), result.getResponse()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    /**
     * Get a list of database engine audit records.
     *
     * @param resourceGroupName The name of the Resource Group to which the Azure SQL Database Server belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database for which database engine audit records are retrieved.
     * @param filter The filter to apply on the operation
     * @return the PagedList&lt;EngineAuditRecordResourceInner&gt; object if successful.
     */
    public PagedList<EngineAuditRecordResourceInner> list(final String resourceGroupName, final String serverName, final String databaseName, final String filter) {
        ServiceResponse<Page<EngineAuditRecordResourceInner>> response = listSinglePageAsync(resourceGroupName, serverName, databaseName, filter).toBlocking().single();
        return new PagedList<EngineAuditRecordResourceInner>(response.getBody()) {
            @Override
            public Page<EngineAuditRecordResourceInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).toBlocking().single().getBody();
            }
        };
    }

    /**
     * Get a list of database engine audit records.
     *
     * @param resourceGroupName The name of the Resource Group to which the Azure SQL Database Server belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database for which database engine audit records are retrieved.
     * @param filter The filter to apply on the operation
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @return the {@link ServiceCall} object
     */
    public ServiceCall<List<EngineAuditRecordResourceInner>> listAsync(final String resourceGroupName, final String serverName, final String databaseName, final String filter, final ListOperationCallback<EngineAuditRecordResourceInner> serviceCallback) {
        return AzureServiceCall.create(
            listSinglePageAsync(resourceGroupName, serverName, databaseName, filter),
            new Func1<String, Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>> call(String nextPageLink) {
                    return listNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * Get a list of database engine audit records.
     *
     * @param resourceGroupName The name of the Resource Group to which the Azure SQL Database Server belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database for which database engine audit records are retrieved.
     * @param filter The filter to apply on the operation
     * @return the observable to the PagedList&lt;EngineAuditRecordResourceInner&gt; object
     */
    public Observable<Page<EngineAuditRecordResourceInner>> listAsync(final String resourceGroupName, final String serverName, final String databaseName, final String filter) {
        return listWithServiceResponseAsync(resourceGroupName, serverName, databaseName, filter)
            .map(new Func1<ServiceResponse<Page<EngineAuditRecordResourceInner>>, Page<EngineAuditRecordResourceInner>>() {
                @Override
                public Page<EngineAuditRecordResourceInner> call(ServiceResponse<Page<EngineAuditRecordResourceInner>> response) {
                    return response.getBody();
                }
            });
    }

    /**
     * Get a list of database engine audit records.
     *
     * @param resourceGroupName The name of the Resource Group to which the Azure SQL Database Server belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database for which database engine audit records are retrieved.
     * @param filter The filter to apply on the operation
     * @return the observable to the PagedList&lt;EngineAuditRecordResourceInner&gt; object
     */
    public Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>> listWithServiceResponseAsync(final String resourceGroupName, final String serverName, final String databaseName, final String filter) {
        return listSinglePageAsync(resourceGroupName, serverName, databaseName, filter)
            .concatMap(new Func1<ServiceResponse<Page<EngineAuditRecordResourceInner>>, Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>> call(ServiceResponse<Page<EngineAuditRecordResourceInner>> page) {
                    String nextPageLink = page.getBody().getNextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * Get a list of database engine audit records.
     *
    ServiceResponse<PageImpl<EngineAuditRecordResourceInner>> * @param resourceGroupName The name of the Resource Group to which the Azure SQL Database Server belongs.
    ServiceResponse<PageImpl<EngineAuditRecordResourceInner>> * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
    ServiceResponse<PageImpl<EngineAuditRecordResourceInner>> * @param databaseName The name of the Azure SQL Database for which database engine audit records are retrieved.
    ServiceResponse<PageImpl<EngineAuditRecordResourceInner>> * @param filter The filter to apply on the operation
     * @return the PagedList&lt;EngineAuditRecordResourceInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>> listSinglePageAsync(final String resourceGroupName, final String serverName, final String databaseName, final String filter) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
        }
        if (databaseName == null) {
            throw new IllegalArgumentException("Parameter databaseName is required and cannot be null.");
        }
        final String apiVersion = "2015-05-01-preview";
        return service.list(this.client.subscriptionId(), resourceGroupName, serverName, databaseName, apiVersion, filter, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<EngineAuditRecordResourceInner>> result = listDelegate(response);
                        return Observable.just(new ServiceResponse<Page<EngineAuditRecordResourceInner>>(result.getBody(), result.getResponse()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<EngineAuditRecordResourceInner>> listDelegate(Response<ResponseBody> response) throws ArmErrorResponseMessageException, IOException, IllegalArgumentException {
        return new AzureServiceResponseBuilder<PageImpl<EngineAuditRecordResourceInner>, ArmErrorResponseMessageException>(this.client.mapperAdapter())
                .register(200, new TypeToken<PageImpl<EngineAuditRecordResourceInner>>() { }.getType())
                .registerError(ArmErrorResponseMessageException.class)
                .build(response);
    }

    /**
     * Get a list of database engine audit records.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @return the PagedList&lt;EngineAuditRecordResourceInner&gt; object if successful.
     */
    public PagedList<EngineAuditRecordResourceInner> listNext(final String nextPageLink) {
        ServiceResponse<Page<EngineAuditRecordResourceInner>> response = listNextSinglePageAsync(nextPageLink).toBlocking().single();
        return new PagedList<EngineAuditRecordResourceInner>(response.getBody()) {
            @Override
            public Page<EngineAuditRecordResourceInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).toBlocking().single().getBody();
            }
        };
    }

    /**
     * Get a list of database engine audit records.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @param serviceCall the ServiceCall object tracking the Retrofit calls
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @return the {@link ServiceCall} object
     */
    public ServiceCall<List<EngineAuditRecordResourceInner>> listNextAsync(final String nextPageLink, final ServiceCall<List<EngineAuditRecordResourceInner>> serviceCall, final ListOperationCallback<EngineAuditRecordResourceInner> serviceCallback) {
        return AzureServiceCall.create(
            listNextSinglePageAsync(nextPageLink),
            new Func1<String, Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>> call(String nextPageLink) {
                    return listNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * Get a list of database engine audit records.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @return the observable to the PagedList&lt;EngineAuditRecordResourceInner&gt; object
     */
    public Observable<Page<EngineAuditRecordResourceInner>> listNextAsync(final String nextPageLink) {
        return listNextWithServiceResponseAsync(nextPageLink)
            .map(new Func1<ServiceResponse<Page<EngineAuditRecordResourceInner>>, Page<EngineAuditRecordResourceInner>>() {
                @Override
                public Page<EngineAuditRecordResourceInner> call(ServiceResponse<Page<EngineAuditRecordResourceInner>> response) {
                    return response.getBody();
                }
            });
    }

    /**
     * Get a list of database engine audit records.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @return the observable to the PagedList&lt;EngineAuditRecordResourceInner&gt; object
     */
    public Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>> listNextWithServiceResponseAsync(final String nextPageLink) {
        return listNextSinglePageAsync(nextPageLink)
            .concatMap(new Func1<ServiceResponse<Page<EngineAuditRecordResourceInner>>, Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>> call(ServiceResponse<Page<EngineAuditRecordResourceInner>> page) {
                    String nextPageLink = page.getBody().getNextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * Get a list of database engine audit records.
     *
    ServiceResponse<PageImpl<EngineAuditRecordResourceInner>> * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @return the PagedList&lt;EngineAuditRecordResourceInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>> listNextSinglePageAsync(final String nextPageLink) {
        if (nextPageLink == null) {
            throw new IllegalArgumentException("Parameter nextPageLink is required and cannot be null.");
        }
        return service.listNext(nextPageLink, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<EngineAuditRecordResourceInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<EngineAuditRecordResourceInner>> result = listNextDelegate(response);
                        return Observable.just(new ServiceResponse<Page<EngineAuditRecordResourceInner>>(result.getBody(), result.getResponse()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<EngineAuditRecordResourceInner>> listNextDelegate(Response<ResponseBody> response) throws ArmErrorResponseMessageException, IOException, IllegalArgumentException {
        return new AzureServiceResponseBuilder<PageImpl<EngineAuditRecordResourceInner>, ArmErrorResponseMessageException>(this.client.mapperAdapter())
                .register(200, new TypeToken<PageImpl<EngineAuditRecordResourceInner>>() { }.getType())
                .registerError(ArmErrorResponseMessageException.class)
                .build(response);
    }

}
