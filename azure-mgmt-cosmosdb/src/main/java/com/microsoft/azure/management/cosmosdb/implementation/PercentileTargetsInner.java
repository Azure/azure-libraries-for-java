/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cosmosdb.implementation;

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
 * in PercentileTargets.
 */
public class PercentileTargetsInner {
    /** The Retrofit service to perform REST calls. */
    private PercentileTargetsService service;
    /** The service client containing this operation class. */
    private CosmosDBImpl client;

    /**
     * Initializes an instance of PercentileTargetsInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public PercentileTargetsInner(Retrofit retrofit, CosmosDBImpl client) {
        this.service = retrofit.create(PercentileTargetsService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for PercentileTargets to be
     * used by Retrofit to perform actually REST calls.
     */
    interface PercentileTargetsService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.cosmosdb.PercentileTargets listMetrics" })
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.DocumentDB/databaseAccounts/{accountName}/targetRegion/{targetRegion}/percentile/metrics")
        Observable<Response<ResponseBody>> listMetrics(@Path("subscriptionId") String subscriptionId, @Path("resourceGroupName") String resourceGroupName, @Path("accountName") String accountName, @Path("targetRegion") String targetRegion, @Query("api-version") String apiVersion, @Query("$filter") String filter, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Retrieves the metrics determined by the given filter for the given account target region. This url is only for PBS and Replication Latency data.
     *
     * @param resourceGroupName Name of an Azure resource group.
     * @param accountName Cosmos DB database account name.
     * @param targetRegion Target region to which data is written. Cosmos DB region, with spaces between words and each word capitalized.
     * @param filter An OData filter expression that describes a subset of metrics to return. The parameters that can be filtered are name.value (name of the metric, can have an or of multiple names), startTime, endTime, and timeGrain. The supported operator is eq.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the List&lt;PercentileMetricInner&gt; object if successful.
     */
    public List<PercentileMetricInner> listMetrics(String resourceGroupName, String accountName, String targetRegion, String filter) {
        return listMetricsWithServiceResponseAsync(resourceGroupName, accountName, targetRegion, filter).toBlocking().single().body();
    }

    /**
     * Retrieves the metrics determined by the given filter for the given account target region. This url is only for PBS and Replication Latency data.
     *
     * @param resourceGroupName Name of an Azure resource group.
     * @param accountName Cosmos DB database account name.
     * @param targetRegion Target region to which data is written. Cosmos DB region, with spaces between words and each word capitalized.
     * @param filter An OData filter expression that describes a subset of metrics to return. The parameters that can be filtered are name.value (name of the metric, can have an or of multiple names), startTime, endTime, and timeGrain. The supported operator is eq.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<PercentileMetricInner>> listMetricsAsync(String resourceGroupName, String accountName, String targetRegion, String filter, final ServiceCallback<List<PercentileMetricInner>> serviceCallback) {
        return ServiceFuture.fromResponse(listMetricsWithServiceResponseAsync(resourceGroupName, accountName, targetRegion, filter), serviceCallback);
    }

    /**
     * Retrieves the metrics determined by the given filter for the given account target region. This url is only for PBS and Replication Latency data.
     *
     * @param resourceGroupName Name of an Azure resource group.
     * @param accountName Cosmos DB database account name.
     * @param targetRegion Target region to which data is written. Cosmos DB region, with spaces between words and each word capitalized.
     * @param filter An OData filter expression that describes a subset of metrics to return. The parameters that can be filtered are name.value (name of the metric, can have an or of multiple names), startTime, endTime, and timeGrain. The supported operator is eq.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the List&lt;PercentileMetricInner&gt; object
     */
    public Observable<List<PercentileMetricInner>> listMetricsAsync(String resourceGroupName, String accountName, String targetRegion, String filter) {
        return listMetricsWithServiceResponseAsync(resourceGroupName, accountName, targetRegion, filter).map(new Func1<ServiceResponse<List<PercentileMetricInner>>, List<PercentileMetricInner>>() {
            @Override
            public List<PercentileMetricInner> call(ServiceResponse<List<PercentileMetricInner>> response) {
                return response.body();
            }
        });
    }

    /**
     * Retrieves the metrics determined by the given filter for the given account target region. This url is only for PBS and Replication Latency data.
     *
     * @param resourceGroupName Name of an Azure resource group.
     * @param accountName Cosmos DB database account name.
     * @param targetRegion Target region to which data is written. Cosmos DB region, with spaces between words and each word capitalized.
     * @param filter An OData filter expression that describes a subset of metrics to return. The parameters that can be filtered are name.value (name of the metric, can have an or of multiple names), startTime, endTime, and timeGrain. The supported operator is eq.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the List&lt;PercentileMetricInner&gt; object
     */
    public Observable<ServiceResponse<List<PercentileMetricInner>>> listMetricsWithServiceResponseAsync(String resourceGroupName, String accountName, String targetRegion, String filter) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (accountName == null) {
            throw new IllegalArgumentException("Parameter accountName is required and cannot be null.");
        }
        if (targetRegion == null) {
            throw new IllegalArgumentException("Parameter targetRegion is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        if (filter == null) {
            throw new IllegalArgumentException("Parameter filter is required and cannot be null.");
        }
        return service.listMetrics(this.client.subscriptionId(), resourceGroupName, accountName, targetRegion, this.client.apiVersion(), filter, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<List<PercentileMetricInner>>>>() {
                @Override
                public Observable<ServiceResponse<List<PercentileMetricInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<PercentileMetricInner>> result = listMetricsDelegate(response);
                        ServiceResponse<List<PercentileMetricInner>> clientResponse = new ServiceResponse<List<PercentileMetricInner>>(result.body().items(), result.response());
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<PercentileMetricInner>> listMetricsDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PageImpl<PercentileMetricInner>, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PageImpl<PercentileMetricInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

}
