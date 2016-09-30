/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.network.implementation;

import retrofit2.Retrofit;
import com.google.common.reflect.TypeToken;
import com.microsoft.azure.AzureServiceCall;
import com.microsoft.azure.AzureServiceResponseBuilder;
import com.microsoft.azure.CloudException;
import com.microsoft.azure.ListOperationCallback;
import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.rest.ServiceCall;
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
 * in ExpressRouteServiceProviders.
 */
public final class ExpressRouteServiceProvidersInner {
    /** The Retrofit service to perform REST calls. */
    private ExpressRouteServiceProvidersService service;
    /** The service client containing this operation class. */
    private NetworkManagementClientImpl client;

    /**
     * Initializes an instance of ExpressRouteServiceProvidersInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public ExpressRouteServiceProvidersInner(Retrofit retrofit, NetworkManagementClientImpl client) {
        this.service = retrofit.create(ExpressRouteServiceProvidersService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for ExpressRouteServiceProviders to be
     * used by Retrofit to perform actually REST calls.
     */
    interface ExpressRouteServiceProvidersService {
        @Headers("Content-Type: application/json; charset=utf-8")
        @GET("subscriptions/{subscriptionId}/providers/Microsoft.Network/expressRouteServiceProviders")
        Observable<Response<ResponseBody>> list(@Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers("Content-Type: application/json; charset=utf-8")
        @GET("{nextLink}")
        Observable<Response<ResponseBody>> listNext(@Path(value = "nextLink", encoded = true) String nextPageLink, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * The List ExpressRouteServiceProvider operation retrieves all the available ExpressRouteServiceProviders.
     *
     * @return the PagedList&lt;ExpressRouteServiceProviderInner&gt; object if successful.
     */
    public PagedList<ExpressRouteServiceProviderInner> list() {
        ServiceResponse<Page<ExpressRouteServiceProviderInner>> response = listSinglePageAsync().toBlocking().single();
        return new PagedList<ExpressRouteServiceProviderInner>(response.getBody()) {
            @Override
            public Page<ExpressRouteServiceProviderInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).toBlocking().single().getBody();
            }
        };
    }

    /**
     * The List ExpressRouteServiceProvider operation retrieves all the available ExpressRouteServiceProviders.
     *
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @return the {@link ServiceCall} object
     */
    public ServiceCall<List<ExpressRouteServiceProviderInner>> listAsync(final ListOperationCallback<ExpressRouteServiceProviderInner> serviceCallback) {
        return AzureServiceCall.create(
            listSinglePageAsync(),
            new Func1<String, Observable<ServiceResponse<Page<ExpressRouteServiceProviderInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ExpressRouteServiceProviderInner>>> call(String nextPageLink) {
                    return listNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * The List ExpressRouteServiceProvider operation retrieves all the available ExpressRouteServiceProviders.
     *
     * @return the observable to the PagedList&lt;ExpressRouteServiceProviderInner&gt; object
     */
    public Observable<Page<ExpressRouteServiceProviderInner>> listAsync() {
        return listWithServiceResponseAsync()
            .map(new Func1<ServiceResponse<Page<ExpressRouteServiceProviderInner>>, Page<ExpressRouteServiceProviderInner>>() {
                @Override
                public Page<ExpressRouteServiceProviderInner> call(ServiceResponse<Page<ExpressRouteServiceProviderInner>> response) {
                    return response.getBody();
                }
            });
    }

    /**
     * The List ExpressRouteServiceProvider operation retrieves all the available ExpressRouteServiceProviders.
     *
     * @return the observable to the PagedList&lt;ExpressRouteServiceProviderInner&gt; object
     */
    public Observable<ServiceResponse<Page<ExpressRouteServiceProviderInner>>> listWithServiceResponseAsync() {
        return listSinglePageAsync()
            .concatMap(new Func1<ServiceResponse<Page<ExpressRouteServiceProviderInner>>, Observable<ServiceResponse<Page<ExpressRouteServiceProviderInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ExpressRouteServiceProviderInner>>> call(ServiceResponse<Page<ExpressRouteServiceProviderInner>> page) {
                    String nextPageLink = page.getBody().getNextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * The List ExpressRouteServiceProvider operation retrieves all the available ExpressRouteServiceProviders.
     *
     * @return the PagedList&lt;ExpressRouteServiceProviderInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<ExpressRouteServiceProviderInner>>> listSinglePageAsync() {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        return service.list(this.client.subscriptionId(), this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<ExpressRouteServiceProviderInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ExpressRouteServiceProviderInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<ExpressRouteServiceProviderInner>> result = listDelegate(response);
                        return Observable.just(new ServiceResponse<Page<ExpressRouteServiceProviderInner>>(result.getBody(), result.getResponse()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<ExpressRouteServiceProviderInner>> listDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return new AzureServiceResponseBuilder<PageImpl<ExpressRouteServiceProviderInner>, CloudException>(this.client.mapperAdapter())
                .register(200, new TypeToken<PageImpl<ExpressRouteServiceProviderInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * The List ExpressRouteServiceProvider operation retrieves all the available ExpressRouteServiceProviders.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @return the PagedList&lt;ExpressRouteServiceProviderInner&gt; object if successful.
     */
    public PagedList<ExpressRouteServiceProviderInner> listNext(final String nextPageLink) {
        ServiceResponse<Page<ExpressRouteServiceProviderInner>> response = listNextSinglePageAsync(nextPageLink).toBlocking().single();
        return new PagedList<ExpressRouteServiceProviderInner>(response.getBody()) {
            @Override
            public Page<ExpressRouteServiceProviderInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).toBlocking().single().getBody();
            }
        };
    }

    /**
     * The List ExpressRouteServiceProvider operation retrieves all the available ExpressRouteServiceProviders.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @param serviceCall the ServiceCall object tracking the Retrofit calls
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @return the {@link ServiceCall} object
     */
    public ServiceCall<List<ExpressRouteServiceProviderInner>> listNextAsync(final String nextPageLink, final ServiceCall<List<ExpressRouteServiceProviderInner>> serviceCall, final ListOperationCallback<ExpressRouteServiceProviderInner> serviceCallback) {
        return AzureServiceCall.create(
            listNextSinglePageAsync(nextPageLink),
            new Func1<String, Observable<ServiceResponse<Page<ExpressRouteServiceProviderInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ExpressRouteServiceProviderInner>>> call(String nextPageLink) {
                    return listNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * The List ExpressRouteServiceProvider operation retrieves all the available ExpressRouteServiceProviders.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @return the observable to the PagedList&lt;ExpressRouteServiceProviderInner&gt; object
     */
    public Observable<Page<ExpressRouteServiceProviderInner>> listNextAsync(final String nextPageLink) {
        return listNextWithServiceResponseAsync(nextPageLink)
            .map(new Func1<ServiceResponse<Page<ExpressRouteServiceProviderInner>>, Page<ExpressRouteServiceProviderInner>>() {
                @Override
                public Page<ExpressRouteServiceProviderInner> call(ServiceResponse<Page<ExpressRouteServiceProviderInner>> response) {
                    return response.getBody();
                }
            });
    }

    /**
     * The List ExpressRouteServiceProvider operation retrieves all the available ExpressRouteServiceProviders.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @return the observable to the PagedList&lt;ExpressRouteServiceProviderInner&gt; object
     */
    public Observable<ServiceResponse<Page<ExpressRouteServiceProviderInner>>> listNextWithServiceResponseAsync(final String nextPageLink) {
        return listNextSinglePageAsync(nextPageLink)
            .concatMap(new Func1<ServiceResponse<Page<ExpressRouteServiceProviderInner>>, Observable<ServiceResponse<Page<ExpressRouteServiceProviderInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ExpressRouteServiceProviderInner>>> call(ServiceResponse<Page<ExpressRouteServiceProviderInner>> page) {
                    String nextPageLink = page.getBody().getNextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * The List ExpressRouteServiceProvider operation retrieves all the available ExpressRouteServiceProviders.
     *
    ServiceResponse<PageImpl<ExpressRouteServiceProviderInner>> * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @return the PagedList&lt;ExpressRouteServiceProviderInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<ExpressRouteServiceProviderInner>>> listNextSinglePageAsync(final String nextPageLink) {
        if (nextPageLink == null) {
            throw new IllegalArgumentException("Parameter nextPageLink is required and cannot be null.");
        }
        return service.listNext(nextPageLink, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<ExpressRouteServiceProviderInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ExpressRouteServiceProviderInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<ExpressRouteServiceProviderInner>> result = listNextDelegate(response);
                        return Observable.just(new ServiceResponse<Page<ExpressRouteServiceProviderInner>>(result.getBody(), result.getResponse()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<ExpressRouteServiceProviderInner>> listNextDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return new AzureServiceResponseBuilder<PageImpl<ExpressRouteServiceProviderInner>, CloudException>(this.client.mapperAdapter())
                .register(200, new TypeToken<PageImpl<ExpressRouteServiceProviderInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

}
