/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.resources.implementation;

import retrofit2.Retrofit;
import com.google.common.reflect.TypeToken;
import com.microsoft.azure.AzureServiceCall;
import com.microsoft.azure.CloudException;
import com.microsoft.azure.ListOperationCallback;
import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.rest.ServiceCall;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceResponse;
import java.io.IOException;
import java.util.List;
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
 * in Providers.
 */
public final class ProvidersInner {
    /** The Retrofit service to perform REST calls. */
    private ProvidersService service;
    /** The service client containing this operation class. */
    private ResourceManagementClientImpl client;

    /**
     * Initializes an instance of ProvidersInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public ProvidersInner(Retrofit retrofit, ResourceManagementClientImpl client) {
        this.service = retrofit.create(ProvidersService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for Providers to be
     * used by Retrofit to perform actually REST calls.
     */
    interface ProvidersService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.resources.Providers unregister" })
        @POST("subscriptions/{subscriptionId}/providers/{resourceProviderNamespace}/unregister")
        Observable<Response<ResponseBody>> unregister(@Path("resourceProviderNamespace") String resourceProviderNamespace, @Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.resources.Providers register" })
        @POST("subscriptions/{subscriptionId}/providers/{resourceProviderNamespace}/register")
        Observable<Response<ResponseBody>> register(@Path("resourceProviderNamespace") String resourceProviderNamespace, @Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.resources.Providers list" })
        @GET("subscriptions/{subscriptionId}/providers")
        Observable<Response<ResponseBody>> list(@Path("subscriptionId") String subscriptionId, @Query("$top") Integer top, @Query("$expand") String expand, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.resources.Providers get" })
        @GET("subscriptions/{subscriptionId}/providers/{resourceProviderNamespace}")
        Observable<Response<ResponseBody>> get(@Path("resourceProviderNamespace") String resourceProviderNamespace, @Path("subscriptionId") String subscriptionId, @Query("$expand") String expand, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.resources.Providers listNext" })
        @GET
        Observable<Response<ResponseBody>> listNext(@Url String nextUrl, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Unregisters a subscription from a resource provider.
     *
     * @param resourceProviderNamespace The namespace of the resource provider to unregister.
     * @return the ProviderInner object if successful.
     */
    public ProviderInner unregister(String resourceProviderNamespace) {
        return unregisterWithServiceResponseAsync(resourceProviderNamespace).toBlocking().single().body();
    }

    /**
     * Unregisters a subscription from a resource provider.
     *
     * @param resourceProviderNamespace The namespace of the resource provider to unregister.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @return the {@link ServiceCall} object
     */
    public ServiceCall<ProviderInner> unregisterAsync(String resourceProviderNamespace, final ServiceCallback<ProviderInner> serviceCallback) {
        return ServiceCall.fromResponse(unregisterWithServiceResponseAsync(resourceProviderNamespace), serviceCallback);
    }

    /**
     * Unregisters a subscription from a resource provider.
     *
     * @param resourceProviderNamespace The namespace of the resource provider to unregister.
     * @return the observable to the ProviderInner object
     */
    public Observable<ProviderInner> unregisterAsync(String resourceProviderNamespace) {
        return unregisterWithServiceResponseAsync(resourceProviderNamespace).map(new Func1<ServiceResponse<ProviderInner>, ProviderInner>() {
            @Override
            public ProviderInner call(ServiceResponse<ProviderInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Unregisters a subscription from a resource provider.
     *
     * @param resourceProviderNamespace The namespace of the resource provider to unregister.
     * @return the observable to the ProviderInner object
     */
    public Observable<ServiceResponse<ProviderInner>> unregisterWithServiceResponseAsync(String resourceProviderNamespace) {
        if (resourceProviderNamespace == null) {
            throw new IllegalArgumentException("Parameter resourceProviderNamespace is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        return service.unregister(resourceProviderNamespace, this.client.subscriptionId(), this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<ProviderInner>>>() {
                @Override
                public Observable<ServiceResponse<ProviderInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<ProviderInner> clientResponse = unregisterDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<ProviderInner> unregisterDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<ProviderInner, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<ProviderInner>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Registers a subscription with a resource provider.
     *
     * @param resourceProviderNamespace The namespace of the resource provider to register.
     * @return the ProviderInner object if successful.
     */
    public ProviderInner register(String resourceProviderNamespace) {
        return registerWithServiceResponseAsync(resourceProviderNamespace).toBlocking().single().body();
    }

    /**
     * Registers a subscription with a resource provider.
     *
     * @param resourceProviderNamespace The namespace of the resource provider to register.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @return the {@link ServiceCall} object
     */
    public ServiceCall<ProviderInner> registerAsync(String resourceProviderNamespace, final ServiceCallback<ProviderInner> serviceCallback) {
        return ServiceCall.fromResponse(registerWithServiceResponseAsync(resourceProviderNamespace), serviceCallback);
    }

    /**
     * Registers a subscription with a resource provider.
     *
     * @param resourceProviderNamespace The namespace of the resource provider to register.
     * @return the observable to the ProviderInner object
     */
    public Observable<ProviderInner> registerAsync(String resourceProviderNamespace) {
        return registerWithServiceResponseAsync(resourceProviderNamespace).map(new Func1<ServiceResponse<ProviderInner>, ProviderInner>() {
            @Override
            public ProviderInner call(ServiceResponse<ProviderInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Registers a subscription with a resource provider.
     *
     * @param resourceProviderNamespace The namespace of the resource provider to register.
     * @return the observable to the ProviderInner object
     */
    public Observable<ServiceResponse<ProviderInner>> registerWithServiceResponseAsync(String resourceProviderNamespace) {
        if (resourceProviderNamespace == null) {
            throw new IllegalArgumentException("Parameter resourceProviderNamespace is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        return service.register(resourceProviderNamespace, this.client.subscriptionId(), this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<ProviderInner>>>() {
                @Override
                public Observable<ServiceResponse<ProviderInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<ProviderInner> clientResponse = registerDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<ProviderInner> registerDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<ProviderInner, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<ProviderInner>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Gets all resource providers for a subscription.
     *
     * @return the PagedList&lt;ProviderInner&gt; object if successful.
     */
    public PagedList<ProviderInner> list() {
        ServiceResponse<Page<ProviderInner>> response = listSinglePageAsync().toBlocking().single();
        return new PagedList<ProviderInner>(response.body()) {
            @Override
            public Page<ProviderInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).toBlocking().single().body();
            }
        };
    }

    /**
     * Gets all resource providers for a subscription.
     *
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @return the {@link ServiceCall} object
     */
    public ServiceCall<List<ProviderInner>> listAsync(final ListOperationCallback<ProviderInner> serviceCallback) {
        return AzureServiceCall.fromPageResponse(
            listSinglePageAsync(),
            new Func1<String, Observable<ServiceResponse<Page<ProviderInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ProviderInner>>> call(String nextPageLink) {
                    return listNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * Gets all resource providers for a subscription.
     *
     * @return the observable to the PagedList&lt;ProviderInner&gt; object
     */
    public Observable<Page<ProviderInner>> listAsync() {
        return listWithServiceResponseAsync()
            .map(new Func1<ServiceResponse<Page<ProviderInner>>, Page<ProviderInner>>() {
                @Override
                public Page<ProviderInner> call(ServiceResponse<Page<ProviderInner>> response) {
                    return response.body();
                }
            });
    }

    /**
     * Gets all resource providers for a subscription.
     *
     * @return the observable to the PagedList&lt;ProviderInner&gt; object
     */
    public Observable<ServiceResponse<Page<ProviderInner>>> listWithServiceResponseAsync() {
        return listSinglePageAsync()
            .concatMap(new Func1<ServiceResponse<Page<ProviderInner>>, Observable<ServiceResponse<Page<ProviderInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ProviderInner>>> call(ServiceResponse<Page<ProviderInner>> page) {
                    String nextPageLink = page.body().nextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * Gets all resource providers for a subscription.
     *
     * @return the PagedList&lt;ProviderInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<ProviderInner>>> listSinglePageAsync() {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        final Integer top = null;
        final String expand = null;
        return service.list(this.client.subscriptionId(), top, expand, this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<ProviderInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ProviderInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<ProviderInner>> result = listDelegate(response);
                        return Observable.just(new ServiceResponse<Page<ProviderInner>>(result.body(), result.response()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    /**
     * Gets all resource providers for a subscription.
     *
     * @param top The number of results to return. If null is passed returns all deployments.
     * @param expand The properties to include in the results. For example, use &amp;$expand=metadata in the query string to retrieve resource provider metadata. To include property aliases in response, use $expand=resourceTypes/aliases.
     * @return the PagedList&lt;ProviderInner&gt; object if successful.
     */
    public PagedList<ProviderInner> list(final Integer top, final String expand) {
        ServiceResponse<Page<ProviderInner>> response = listSinglePageAsync(top, expand).toBlocking().single();
        return new PagedList<ProviderInner>(response.body()) {
            @Override
            public Page<ProviderInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).toBlocking().single().body();
            }
        };
    }

    /**
     * Gets all resource providers for a subscription.
     *
     * @param top The number of results to return. If null is passed returns all deployments.
     * @param expand The properties to include in the results. For example, use &amp;$expand=metadata in the query string to retrieve resource provider metadata. To include property aliases in response, use $expand=resourceTypes/aliases.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @return the {@link ServiceCall} object
     */
    public ServiceCall<List<ProviderInner>> listAsync(final Integer top, final String expand, final ListOperationCallback<ProviderInner> serviceCallback) {
        return AzureServiceCall.fromPageResponse(
            listSinglePageAsync(top, expand),
            new Func1<String, Observable<ServiceResponse<Page<ProviderInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ProviderInner>>> call(String nextPageLink) {
                    return listNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * Gets all resource providers for a subscription.
     *
     * @param top The number of results to return. If null is passed returns all deployments.
     * @param expand The properties to include in the results. For example, use &amp;$expand=metadata in the query string to retrieve resource provider metadata. To include property aliases in response, use $expand=resourceTypes/aliases.
     * @return the observable to the PagedList&lt;ProviderInner&gt; object
     */
    public Observable<Page<ProviderInner>> listAsync(final Integer top, final String expand) {
        return listWithServiceResponseAsync(top, expand)
            .map(new Func1<ServiceResponse<Page<ProviderInner>>, Page<ProviderInner>>() {
                @Override
                public Page<ProviderInner> call(ServiceResponse<Page<ProviderInner>> response) {
                    return response.body();
                }
            });
    }

    /**
     * Gets all resource providers for a subscription.
     *
     * @param top The number of results to return. If null is passed returns all deployments.
     * @param expand The properties to include in the results. For example, use &amp;$expand=metadata in the query string to retrieve resource provider metadata. To include property aliases in response, use $expand=resourceTypes/aliases.
     * @return the observable to the PagedList&lt;ProviderInner&gt; object
     */
    public Observable<ServiceResponse<Page<ProviderInner>>> listWithServiceResponseAsync(final Integer top, final String expand) {
        return listSinglePageAsync(top, expand)
            .concatMap(new Func1<ServiceResponse<Page<ProviderInner>>, Observable<ServiceResponse<Page<ProviderInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ProviderInner>>> call(ServiceResponse<Page<ProviderInner>> page) {
                    String nextPageLink = page.body().nextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * Gets all resource providers for a subscription.
     *
    ServiceResponse<PageImpl<ProviderInner>> * @param top The number of results to return. If null is passed returns all deployments.
    ServiceResponse<PageImpl<ProviderInner>> * @param expand The properties to include in the results. For example, use &amp;$expand=metadata in the query string to retrieve resource provider metadata. To include property aliases in response, use $expand=resourceTypes/aliases.
     * @return the PagedList&lt;ProviderInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<ProviderInner>>> listSinglePageAsync(final Integer top, final String expand) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        return service.list(this.client.subscriptionId(), top, expand, this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<ProviderInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ProviderInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<ProviderInner>> result = listDelegate(response);
                        return Observable.just(new ServiceResponse<Page<ProviderInner>>(result.body(), result.response()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<ProviderInner>> listDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PageImpl<ProviderInner>, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PageImpl<ProviderInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Gets the specified resource provider.
     *
     * @param resourceProviderNamespace The namespace of the resource provider.
     * @return the ProviderInner object if successful.
     */
    public ProviderInner get(String resourceProviderNamespace) {
        return getWithServiceResponseAsync(resourceProviderNamespace).toBlocking().single().body();
    }

    /**
     * Gets the specified resource provider.
     *
     * @param resourceProviderNamespace The namespace of the resource provider.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @return the {@link ServiceCall} object
     */
    public ServiceCall<ProviderInner> getAsync(String resourceProviderNamespace, final ServiceCallback<ProviderInner> serviceCallback) {
        return ServiceCall.fromResponse(getWithServiceResponseAsync(resourceProviderNamespace), serviceCallback);
    }

    /**
     * Gets the specified resource provider.
     *
     * @param resourceProviderNamespace The namespace of the resource provider.
     * @return the observable to the ProviderInner object
     */
    public Observable<ProviderInner> getAsync(String resourceProviderNamespace) {
        return getWithServiceResponseAsync(resourceProviderNamespace).map(new Func1<ServiceResponse<ProviderInner>, ProviderInner>() {
            @Override
            public ProviderInner call(ServiceResponse<ProviderInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Gets the specified resource provider.
     *
     * @param resourceProviderNamespace The namespace of the resource provider.
     * @return the observable to the ProviderInner object
     */
    public Observable<ServiceResponse<ProviderInner>> getWithServiceResponseAsync(String resourceProviderNamespace) {
        if (resourceProviderNamespace == null) {
            throw new IllegalArgumentException("Parameter resourceProviderNamespace is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        final String expand = null;
        return service.get(resourceProviderNamespace, this.client.subscriptionId(), expand, this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<ProviderInner>>>() {
                @Override
                public Observable<ServiceResponse<ProviderInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<ProviderInner> clientResponse = getDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    /**
     * Gets the specified resource provider.
     *
     * @param resourceProviderNamespace The namespace of the resource provider.
     * @param expand The $expand query parameter. For example, to include property aliases in response, use $expand=resourceTypes/aliases.
     * @return the ProviderInner object if successful.
     */
    public ProviderInner get(String resourceProviderNamespace, String expand) {
        return getWithServiceResponseAsync(resourceProviderNamespace, expand).toBlocking().single().body();
    }

    /**
     * Gets the specified resource provider.
     *
     * @param resourceProviderNamespace The namespace of the resource provider.
     * @param expand The $expand query parameter. For example, to include property aliases in response, use $expand=resourceTypes/aliases.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @return the {@link ServiceCall} object
     */
    public ServiceCall<ProviderInner> getAsync(String resourceProviderNamespace, String expand, final ServiceCallback<ProviderInner> serviceCallback) {
        return ServiceCall.fromResponse(getWithServiceResponseAsync(resourceProviderNamespace, expand), serviceCallback);
    }

    /**
     * Gets the specified resource provider.
     *
     * @param resourceProviderNamespace The namespace of the resource provider.
     * @param expand The $expand query parameter. For example, to include property aliases in response, use $expand=resourceTypes/aliases.
     * @return the observable to the ProviderInner object
     */
    public Observable<ProviderInner> getAsync(String resourceProviderNamespace, String expand) {
        return getWithServiceResponseAsync(resourceProviderNamespace, expand).map(new Func1<ServiceResponse<ProviderInner>, ProviderInner>() {
            @Override
            public ProviderInner call(ServiceResponse<ProviderInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Gets the specified resource provider.
     *
     * @param resourceProviderNamespace The namespace of the resource provider.
     * @param expand The $expand query parameter. For example, to include property aliases in response, use $expand=resourceTypes/aliases.
     * @return the observable to the ProviderInner object
     */
    public Observable<ServiceResponse<ProviderInner>> getWithServiceResponseAsync(String resourceProviderNamespace, String expand) {
        if (resourceProviderNamespace == null) {
            throw new IllegalArgumentException("Parameter resourceProviderNamespace is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        return service.get(resourceProviderNamespace, this.client.subscriptionId(), expand, this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<ProviderInner>>>() {
                @Override
                public Observable<ServiceResponse<ProviderInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<ProviderInner> clientResponse = getDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<ProviderInner> getDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<ProviderInner, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<ProviderInner>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Gets all resource providers for a subscription.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @return the PagedList&lt;ProviderInner&gt; object if successful.
     */
    public PagedList<ProviderInner> listNext(final String nextPageLink) {
        ServiceResponse<Page<ProviderInner>> response = listNextSinglePageAsync(nextPageLink).toBlocking().single();
        return new PagedList<ProviderInner>(response.body()) {
            @Override
            public Page<ProviderInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).toBlocking().single().body();
            }
        };
    }

    /**
     * Gets all resource providers for a subscription.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @param serviceCall the ServiceCall object tracking the Retrofit calls
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @return the {@link ServiceCall} object
     */
    public ServiceCall<List<ProviderInner>> listNextAsync(final String nextPageLink, final ServiceCall<List<ProviderInner>> serviceCall, final ListOperationCallback<ProviderInner> serviceCallback) {
        return AzureServiceCall.fromPageResponse(
            listNextSinglePageAsync(nextPageLink),
            new Func1<String, Observable<ServiceResponse<Page<ProviderInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ProviderInner>>> call(String nextPageLink) {
                    return listNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * Gets all resource providers for a subscription.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @return the observable to the PagedList&lt;ProviderInner&gt; object
     */
    public Observable<Page<ProviderInner>> listNextAsync(final String nextPageLink) {
        return listNextWithServiceResponseAsync(nextPageLink)
            .map(new Func1<ServiceResponse<Page<ProviderInner>>, Page<ProviderInner>>() {
                @Override
                public Page<ProviderInner> call(ServiceResponse<Page<ProviderInner>> response) {
                    return response.body();
                }
            });
    }

    /**
     * Gets all resource providers for a subscription.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @return the observable to the PagedList&lt;ProviderInner&gt; object
     */
    public Observable<ServiceResponse<Page<ProviderInner>>> listNextWithServiceResponseAsync(final String nextPageLink) {
        return listNextSinglePageAsync(nextPageLink)
            .concatMap(new Func1<ServiceResponse<Page<ProviderInner>>, Observable<ServiceResponse<Page<ProviderInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ProviderInner>>> call(ServiceResponse<Page<ProviderInner>> page) {
                    String nextPageLink = page.body().nextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * Gets all resource providers for a subscription.
     *
    ServiceResponse<PageImpl<ProviderInner>> * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @return the PagedList&lt;ProviderInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<ProviderInner>>> listNextSinglePageAsync(final String nextPageLink) {
        if (nextPageLink == null) {
            throw new IllegalArgumentException("Parameter nextPageLink is required and cannot be null.");
        }
        String nextUrl = String.format("%s", nextPageLink);
        return service.listNext(nextUrl, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<ProviderInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ProviderInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<ProviderInner>> result = listNextDelegate(response);
                        return Observable.just(new ServiceResponse<Page<ProviderInner>>(result.body(), result.response()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<ProviderInner>> listNextDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PageImpl<ProviderInner>, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PageImpl<ProviderInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

}
