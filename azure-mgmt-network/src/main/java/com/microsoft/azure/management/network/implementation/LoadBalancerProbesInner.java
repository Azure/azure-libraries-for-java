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
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import retrofit2.Response;
import rx.functions.Func1;
import rx.Observable;

/**
 * An instance of this class provides access to all the operations defined
 * in LoadBalancerProbes.
 */
public class LoadBalancerProbesInner {
    /** The Retrofit service to perform REST calls. */
    private LoadBalancerProbesService service;
    /** The service client containing this operation class. */
    private NetworkManagementClientImpl client;

    /**
     * Initializes an instance of LoadBalancerProbesInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public LoadBalancerProbesInner(Retrofit retrofit, NetworkManagementClientImpl client) {
        this.service = retrofit.create(LoadBalancerProbesService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for LoadBalancerProbes to be
     * used by Retrofit to perform actually REST calls.
     */
    interface LoadBalancerProbesService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.network.LoadBalancerProbes list" })
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/loadBalancers/{loadBalancerName}/probes")
        Observable<Response<ResponseBody>> list(@Path("resourceGroupName") String resourceGroupName, @Path("loadBalancerName") String loadBalancerName, @Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.network.LoadBalancerProbes get" })
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/loadBalancers/{loadBalancerName}/probes/{probeName}")
        Observable<Response<ResponseBody>> get(@Path("resourceGroupName") String resourceGroupName, @Path("loadBalancerName") String loadBalancerName, @Path("probeName") String probeName, @Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.network.LoadBalancerProbes listNext" })
        @GET
        Observable<Response<ResponseBody>> listNext(@Url String nextUrl, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Gets all the load balancer probes.
     *
     * @param resourceGroupName The name of the resource group.
     * @param loadBalancerName The name of the load balancer.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PagedList&lt;ProbeInner&gt; object if successful.
     */
    public PagedList<ProbeInner> list(final String resourceGroupName, final String loadBalancerName) {
        ServiceResponse<Page<ProbeInner>> response = listSinglePageAsync(resourceGroupName, loadBalancerName).toBlocking().single();
        return new PagedList<ProbeInner>(response.body()) {
            @Override
            public Page<ProbeInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).toBlocking().single().body();
            }
        };
    }

    /**
     * Gets all the load balancer probes.
     *
     * @param resourceGroupName The name of the resource group.
     * @param loadBalancerName The name of the load balancer.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<ProbeInner>> listAsync(final String resourceGroupName, final String loadBalancerName, final ListOperationCallback<ProbeInner> serviceCallback) {
        return AzureServiceFuture.fromPageResponse(
            listSinglePageAsync(resourceGroupName, loadBalancerName),
            new Func1<String, Observable<ServiceResponse<Page<ProbeInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ProbeInner>>> call(String nextPageLink) {
                    return listNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * Gets all the load balancer probes.
     *
     * @param resourceGroupName The name of the resource group.
     * @param loadBalancerName The name of the load balancer.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;ProbeInner&gt; object
     */
    public Observable<Page<ProbeInner>> listAsync(final String resourceGroupName, final String loadBalancerName) {
        return listWithServiceResponseAsync(resourceGroupName, loadBalancerName)
            .map(new Func1<ServiceResponse<Page<ProbeInner>>, Page<ProbeInner>>() {
                @Override
                public Page<ProbeInner> call(ServiceResponse<Page<ProbeInner>> response) {
                    return response.body();
                }
            });
    }

    /**
     * Gets all the load balancer probes.
     *
     * @param resourceGroupName The name of the resource group.
     * @param loadBalancerName The name of the load balancer.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;ProbeInner&gt; object
     */
    public Observable<ServiceResponse<Page<ProbeInner>>> listWithServiceResponseAsync(final String resourceGroupName, final String loadBalancerName) {
        return listSinglePageAsync(resourceGroupName, loadBalancerName)
            .concatMap(new Func1<ServiceResponse<Page<ProbeInner>>, Observable<ServiceResponse<Page<ProbeInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ProbeInner>>> call(ServiceResponse<Page<ProbeInner>> page) {
                    String nextPageLink = page.body().nextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * Gets all the load balancer probes.
     *
    ServiceResponse<PageImpl<ProbeInner>> * @param resourceGroupName The name of the resource group.
    ServiceResponse<PageImpl<ProbeInner>> * @param loadBalancerName The name of the load balancer.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;ProbeInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<ProbeInner>>> listSinglePageAsync(final String resourceGroupName, final String loadBalancerName) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (loadBalancerName == null) {
            throw new IllegalArgumentException("Parameter loadBalancerName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        final String apiVersion = "2018-04-01";
        return service.list(resourceGroupName, loadBalancerName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<ProbeInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ProbeInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<ProbeInner>> result = listDelegate(response);
                        return Observable.just(new ServiceResponse<Page<ProbeInner>>(result.body(), result.response()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<ProbeInner>> listDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PageImpl<ProbeInner>, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PageImpl<ProbeInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Gets load balancer probe.
     *
     * @param resourceGroupName The name of the resource group.
     * @param loadBalancerName The name of the load balancer.
     * @param probeName The name of the probe.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the ProbeInner object if successful.
     */
    public ProbeInner get(String resourceGroupName, String loadBalancerName, String probeName) {
        return getWithServiceResponseAsync(resourceGroupName, loadBalancerName, probeName).toBlocking().single().body();
    }

    /**
     * Gets load balancer probe.
     *
     * @param resourceGroupName The name of the resource group.
     * @param loadBalancerName The name of the load balancer.
     * @param probeName The name of the probe.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<ProbeInner> getAsync(String resourceGroupName, String loadBalancerName, String probeName, final ServiceCallback<ProbeInner> serviceCallback) {
        return ServiceFuture.fromResponse(getWithServiceResponseAsync(resourceGroupName, loadBalancerName, probeName), serviceCallback);
    }

    /**
     * Gets load balancer probe.
     *
     * @param resourceGroupName The name of the resource group.
     * @param loadBalancerName The name of the load balancer.
     * @param probeName The name of the probe.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the ProbeInner object
     */
    public Observable<ProbeInner> getAsync(String resourceGroupName, String loadBalancerName, String probeName) {
        return getWithServiceResponseAsync(resourceGroupName, loadBalancerName, probeName).map(new Func1<ServiceResponse<ProbeInner>, ProbeInner>() {
            @Override
            public ProbeInner call(ServiceResponse<ProbeInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Gets load balancer probe.
     *
     * @param resourceGroupName The name of the resource group.
     * @param loadBalancerName The name of the load balancer.
     * @param probeName The name of the probe.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the ProbeInner object
     */
    public Observable<ServiceResponse<ProbeInner>> getWithServiceResponseAsync(String resourceGroupName, String loadBalancerName, String probeName) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (loadBalancerName == null) {
            throw new IllegalArgumentException("Parameter loadBalancerName is required and cannot be null.");
        }
        if (probeName == null) {
            throw new IllegalArgumentException("Parameter probeName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        final String apiVersion = "2018-04-01";
        return service.get(resourceGroupName, loadBalancerName, probeName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<ProbeInner>>>() {
                @Override
                public Observable<ServiceResponse<ProbeInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<ProbeInner> clientResponse = getDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<ProbeInner> getDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<ProbeInner, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<ProbeInner>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Gets all the load balancer probes.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PagedList&lt;ProbeInner&gt; object if successful.
     */
    public PagedList<ProbeInner> listNext(final String nextPageLink) {
        ServiceResponse<Page<ProbeInner>> response = listNextSinglePageAsync(nextPageLink).toBlocking().single();
        return new PagedList<ProbeInner>(response.body()) {
            @Override
            public Page<ProbeInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).toBlocking().single().body();
            }
        };
    }

    /**
     * Gets all the load balancer probes.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @param serviceFuture the ServiceFuture object tracking the Retrofit calls
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<ProbeInner>> listNextAsync(final String nextPageLink, final ServiceFuture<List<ProbeInner>> serviceFuture, final ListOperationCallback<ProbeInner> serviceCallback) {
        return AzureServiceFuture.fromPageResponse(
            listNextSinglePageAsync(nextPageLink),
            new Func1<String, Observable<ServiceResponse<Page<ProbeInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ProbeInner>>> call(String nextPageLink) {
                    return listNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * Gets all the load balancer probes.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;ProbeInner&gt; object
     */
    public Observable<Page<ProbeInner>> listNextAsync(final String nextPageLink) {
        return listNextWithServiceResponseAsync(nextPageLink)
            .map(new Func1<ServiceResponse<Page<ProbeInner>>, Page<ProbeInner>>() {
                @Override
                public Page<ProbeInner> call(ServiceResponse<Page<ProbeInner>> response) {
                    return response.body();
                }
            });
    }

    /**
     * Gets all the load balancer probes.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;ProbeInner&gt; object
     */
    public Observable<ServiceResponse<Page<ProbeInner>>> listNextWithServiceResponseAsync(final String nextPageLink) {
        return listNextSinglePageAsync(nextPageLink)
            .concatMap(new Func1<ServiceResponse<Page<ProbeInner>>, Observable<ServiceResponse<Page<ProbeInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ProbeInner>>> call(ServiceResponse<Page<ProbeInner>> page) {
                    String nextPageLink = page.body().nextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * Gets all the load balancer probes.
     *
    ServiceResponse<PageImpl<ProbeInner>> * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;ProbeInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<ProbeInner>>> listNextSinglePageAsync(final String nextPageLink) {
        if (nextPageLink == null) {
            throw new IllegalArgumentException("Parameter nextPageLink is required and cannot be null.");
        }
        String nextUrl = String.format("%s", nextPageLink);
        return service.listNext(nextUrl, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<ProbeInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ProbeInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<ProbeInner>> result = listNextDelegate(response);
                        return Observable.just(new ServiceResponse<Page<ProbeInner>>(result.body(), result.response()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<ProbeInner>> listNextDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PageImpl<ProbeInner>, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PageImpl<ProbeInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

}
