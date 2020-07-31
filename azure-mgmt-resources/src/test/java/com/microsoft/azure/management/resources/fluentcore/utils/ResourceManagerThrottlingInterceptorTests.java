/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.utils;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.microsoft.rest.LogLevel;
import com.microsoft.rest.interceptors.LoggingInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class ResourceManagerThrottlingInterceptorTests {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8765);

    @Test
    public void testThrottlingOnHttpMethods() throws Exception {
        stubFor(put(urlMatching("/subscriptions/[0-9-]+/resourcegroups/[a-z0-9]+"))
                .willReturn(aResponse().withStatus(429).withHeader("Retry-After", "5")));

        stubFor(get(urlMatching("/subscriptions/[0-9-]+/resourcegroups/[a-z0-9]+"))
                .willReturn(ok()));

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ResourceManagerThrottlingInterceptor())
                .addInterceptor(new LoggingInterceptor(LogLevel.BODY_AND_HEADERS))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .baseUrl("http://localhost:8765/")
                .build();
        final ResourceGroupsService service = retrofit.create(ResourceGroupsService.class);

        final String ZERO_SUBSCRIPTION = "00000000-0000-0000-0000-000000000000";

        final long[] putCompleteTime = new long[1];
        final long[] getCompleteTime = new long[1];
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.submit(new Runnable() {
            @Override
            public void run() {
                ResponseBody response = service.createOrUpdate("rg", ZERO_SUBSCRIPTION).toBlocking().single().body();
                putCompleteTime[0] = System.nanoTime() / (long) 1E6;
            }
        });
        Thread.sleep(2000);
        pool.submit(new Runnable() {
            @Override
            public void run() {
                ResponseBody response = service.get("rg", ZERO_SUBSCRIPTION).toBlocking().single().body();
                getCompleteTime[0] = System.nanoTime() / (long) 1E6;
            }
        });
        pool.shutdown();
        pool.awaitTermination(30, TimeUnit.SECONDS);

        // PUT will retry due to 429, GET will success, hence GET would finish before PUT
        Assert.assertTrue(getCompleteTime[0] < putCompleteTime[0]);
    }

    interface ResourceGroupsService {
        @Headers({"Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.resources.ResourceGroups createOrUpdate"})
        @PUT("subscriptions/{subscriptionId}/resourcegroups/{resourceGroupName}")
        Observable<Response<ResponseBody>> createOrUpdate(@Path("resourceGroupName") String resourceGroupName, @Path("subscriptionId") String subscriptionId);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.resources.ResourceGroups get" })
        @GET("subscriptions/{subscriptionId}/resourcegroups/{resourceGroupName}")
        Observable<Response<ResponseBody>> get(@Path("resourceGroupName") String resourceGroupName, @Path("subscriptionId") String subscriptionId);
    }
}
