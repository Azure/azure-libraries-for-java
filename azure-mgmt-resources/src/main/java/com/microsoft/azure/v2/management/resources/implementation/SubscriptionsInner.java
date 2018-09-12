/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.resources.implementation;

import com.microsoft.azure.v2.AzureProxy;
import com.microsoft.azure.v2.CloudException;
import com.microsoft.azure.v2.Page;
import com.microsoft.azure.v2.PagedList;
import com.microsoft.rest.v2.BodyResponse;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import com.microsoft.rest.v2.annotations.ExpectedResponses;
import com.microsoft.rest.v2.annotations.GET;
import com.microsoft.rest.v2.annotations.HeaderParam;
import com.microsoft.rest.v2.annotations.Host;
import com.microsoft.rest.v2.annotations.PathParam;
import com.microsoft.rest.v2.annotations.QueryParam;
import com.microsoft.rest.v2.annotations.UnexpectedResponseExceptionType;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import java.util.List;

/**
 * An instance of this class provides access to all the operations defined in
 * Subscriptions.
 */
public final class SubscriptionsInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private SubscriptionsService service;

    /**
     * The service client containing this operation class.
     */
    private SubscriptionClientImpl client;

    /**
     * Initializes an instance of SubscriptionsInner.
     *
     * @param client the instance of the service client containing this operation class.
     */
    public SubscriptionsInner(SubscriptionClientImpl client) {
        this.service = AzureProxy.create(SubscriptionsService.class, client);
        this.client = client;
    }

    /**
     * The interface defining all the services for Subscriptions to be used by
     * the proxy service to perform REST calls.
     */
    @Host("https://management.azure.com")
    private interface SubscriptionsService {
        @GET("subscriptions/{subscriptionId}/locations")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<BodyResponse<List<LocationInner>>> listLocations(@PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);

        @GET("subscriptions/{subscriptionId}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<BodyResponse<SubscriptionInner>> get(@PathParam("subscriptionId") String subscriptionId, @QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);

        @GET("subscriptions")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<BodyResponse<PageImpl1<SubscriptionInner>>> list(@QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);

        @GET("{nextUrl}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudException.class)
        Single<BodyResponse<PageImpl1<SubscriptionInner>>> listNext(@PathParam(value = "nextUrl", encoded = true) String nextUrl, @HeaderParam("accept-language") String acceptLanguage);
    }

    /**
     * Gets all available geo-locations.
     * This operation provides all the locations that are available for resource providers; however, each resource provider may support a subset of this list.
     *
     * @param subscriptionId The ID of the target subscription.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the List&lt;LocationInner&gt; object if successful.
     */
    public List<LocationInner> listLocations(@NonNull String subscriptionId) {
        return listLocationsAsync(subscriptionId).blockingGet();
    }

    /**
     * Gets all available geo-locations.
     * This operation provides all the locations that are available for resource providers; however, each resource provider may support a subset of this list.
     *
     * @param subscriptionId The ID of the target subscription.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    public ServiceFuture<List<LocationInner>> listLocationsAsync(@NonNull String subscriptionId, ServiceCallback<List<LocationInner>> serviceCallback) {
        return ServiceFuture.fromBody(listLocationsAsync(subscriptionId), serviceCallback);
    }

    /**
     * Gets all available geo-locations.
     * This operation provides all the locations that are available for resource providers; however, each resource provider may support a subset of this list.
     *
     * @param subscriptionId The ID of the target subscription.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Single<BodyResponse<List<LocationInner>>> listLocationsWithRestResponseAsync(@NonNull String subscriptionId) {
        if (subscriptionId == null) {
            throw new IllegalArgumentException("Parameter subscriptionId is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        return service.listLocations(subscriptionId, this.client.apiVersion(), this.client.acceptLanguage());
    }

    /**
     * Gets all available geo-locations.
     * This operation provides all the locations that are available for resource providers; however, each resource provider may support a subset of this list.
     *
     * @param subscriptionId The ID of the target subscription.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Maybe<List<LocationInner>> listLocationsAsync(@NonNull String subscriptionId) {
        return listLocationsWithRestResponseAsync(subscriptionId)
            .flatMapMaybe((BodyResponse<List<LocationInner>> res) -> res.body() == null ? Maybe.empty() : Maybe.just(res.body()));
    }

    /**
     * Gets details about a specified subscription.
     *
     * @param subscriptionId The ID of the target subscription.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the SubscriptionInner object if successful.
     */
    public SubscriptionInner get(@NonNull String subscriptionId) {
        return getAsync(subscriptionId).blockingGet();
    }

    /**
     * Gets details about a specified subscription.
     *
     * @param subscriptionId The ID of the target subscription.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    public ServiceFuture<SubscriptionInner> getAsync(@NonNull String subscriptionId, ServiceCallback<SubscriptionInner> serviceCallback) {
        return ServiceFuture.fromBody(getAsync(subscriptionId), serviceCallback);
    }

    /**
     * Gets details about a specified subscription.
     *
     * @param subscriptionId The ID of the target subscription.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Single<BodyResponse<SubscriptionInner>> getWithRestResponseAsync(@NonNull String subscriptionId) {
        if (subscriptionId == null) {
            throw new IllegalArgumentException("Parameter subscriptionId is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        return service.get(subscriptionId, this.client.apiVersion(), this.client.acceptLanguage());
    }

    /**
     * Gets details about a specified subscription.
     *
     * @param subscriptionId The ID of the target subscription.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    public Maybe<SubscriptionInner> getAsync(@NonNull String subscriptionId) {
        return getWithRestResponseAsync(subscriptionId)
            .flatMapMaybe((BodyResponse<SubscriptionInner> res) -> res.body() == null ? Maybe.empty() : Maybe.just(res.body()));
    }

    /**
     * Gets all subscriptions for a tenant.
     *
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the PagedList&lt;SubscriptionInner&gt; object if successful.
     */
    public PagedList<SubscriptionInner> list() {
        Page<SubscriptionInner> response = listSinglePageAsync().blockingGet();
        return new PagedList<SubscriptionInner>(response) {
            @Override
            public Page<SubscriptionInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).blockingGet();
            }
        };
    }

    /**
     * Gets all subscriptions for a tenant.
     *
     * @return the observable to the PagedList&lt;SubscriptionInner&gt; object.
     */
    public Observable<Page<SubscriptionInner>> listAsync() {
        return listSinglePageAsync()
            .toObservable()
            .concatMap((Page<SubscriptionInner> page) -> {
                String nextPageLink = page.nextPageLink();
                if (nextPageLink == null) {
                    return Observable.just(page);
                }
                return Observable.just(page).concatWith(listNextAsync(nextPageLink));
            });
    }

    /**
     * Gets all subscriptions for a tenant.
     *
     * @return the Single&lt;Page&lt;SubscriptionInner&gt;&gt; object if successful.
     */
    public Single<Page<SubscriptionInner>> listSinglePageAsync() {
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        return service.list(this.client.apiVersion(), this.client.acceptLanguage())
            .map((BodyResponse<PageImpl1<SubscriptionInner>> res) -> res.body());
    }

    /**
     * Gets all subscriptions for a tenant.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the PagedList&lt;SubscriptionInner&gt; object if successful.
     */
    public PagedList<SubscriptionInner> listNext(@NonNull String nextPageLink) {
        Page<SubscriptionInner> response = listNextSinglePageAsync(nextPageLink).blockingGet();
        return new PagedList<SubscriptionInner>(response) {
            @Override
            public Page<SubscriptionInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).blockingGet();
            }
        };
    }

    /**
     * Gets all subscriptions for a tenant.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the observable to the PagedList&lt;SubscriptionInner&gt; object.
     */
    public Observable<Page<SubscriptionInner>> listNextAsync(@NonNull String nextPageLink) {
        return listNextSinglePageAsync(nextPageLink)
            .toObservable()
            .concatMap((Page<SubscriptionInner> page) -> {
                String nextPageLink1 = page.nextPageLink();
                if (nextPageLink1 == null) {
                    return Observable.just(page);
                }
                return Observable.just(page).concatWith(listNextAsync(nextPageLink1));
            });
    }

    /**
     * Gets all subscriptions for a tenant.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the Single&lt;Page&lt;SubscriptionInner&gt;&gt; object if successful.
     */
    public Single<Page<SubscriptionInner>> listNextSinglePageAsync(@NonNull String nextPageLink) {
        if (nextPageLink == null) {
            throw new IllegalArgumentException("Parameter nextPageLink is required and cannot be null.");
        }
        String nextUrl = String.format("%s", nextPageLink);
        return service.listNext(nextUrl, this.client.acceptLanguage())
            .map((BodyResponse<PageImpl1<SubscriptionInner>> res) -> res.body());
    }
}
