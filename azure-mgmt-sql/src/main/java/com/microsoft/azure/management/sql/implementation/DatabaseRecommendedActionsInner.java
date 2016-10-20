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
import com.microsoft.azure.AzureServiceResponseBuilder;
import com.microsoft.azure.management.sql.ArmErrorResponseMessageException;
import com.microsoft.rest.ServiceCall;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceResponse;
import com.microsoft.rest.Validator;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.Response;
import rx.functions.Func1;
import rx.Observable;

/**
 * An instance of this class provides access to all the operations defined
 * in DatabaseRecommendedActions.
 */
public final class DatabaseRecommendedActionsInner {
    /** The Retrofit service to perform REST calls. */
    private DatabaseRecommendedActionsService service;
    /** The service client containing this operation class. */
    private SqlManagementClientImpl client;

    /**
     * Initializes an instance of DatabaseRecommendedActionsInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public DatabaseRecommendedActionsInner(Retrofit retrofit, SqlManagementClientImpl client) {
        this.service = retrofit.create(DatabaseRecommendedActionsService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for DatabaseRecommendedActions to be
     * used by Retrofit to perform actually REST calls.
     */
    interface DatabaseRecommendedActionsService {
        @Headers("Content-Type: application/json; charset=utf-8")
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/databases/{databaseName}/advisors/{advisorName}/recommendedActions")
        Observable<Response<ResponseBody>> list(@Path("subscriptionId") String subscriptionId, @Path("resourceGroupName") String resourceGroupName, @Path("serverName") String serverName, @Path("databaseName") String databaseName, @Path("advisorName") String advisorName, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers("Content-Type: application/json; charset=utf-8")
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/databases/{databaseName}/advisors/{advisorName}/recommendedActions/{recommendedActionName}")
        Observable<Response<ResponseBody>> get(@Path("subscriptionId") String subscriptionId, @Path("resourceGroupName") String resourceGroupName, @Path("serverName") String serverName, @Path("databaseName") String databaseName, @Path("advisorName") String advisorName, @Path("recommendedActionName") String recommendedActionName, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers("Content-Type: application/json; charset=utf-8")
        @PATCH("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/databases/{databaseName}/advisors/{advisorName}/recommendedActions/{recommendedActionName}")
        Observable<Response<ResponseBody>> update(@Path("subscriptionId") String subscriptionId, @Path("resourceGroupName") String resourceGroupName, @Path("serverName") String serverName, @Path("databaseName") String databaseName, @Path("advisorName") String advisorName, @Path("recommendedActionName") String recommendedActionName, @Body RecommendedActionResourceInner recommendedAction, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Gets list of Azure SQL Database Recommended Actions.
     *
     * @param resourceGroupName The name of the Resource Group to which the resource belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database.
     * @param advisorName The name of the Azure SQL Database Advisor.
     * @return the RecommendedActionResourceListInner object if successful.
     */
    public RecommendedActionResourceListInner list(String resourceGroupName, String serverName, String databaseName, String advisorName) {
        return listWithServiceResponseAsync(resourceGroupName, serverName, databaseName, advisorName).toBlocking().single().getBody();
    }

    /**
     * Gets list of Azure SQL Database Recommended Actions.
     *
     * @param resourceGroupName The name of the Resource Group to which the resource belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database.
     * @param advisorName The name of the Azure SQL Database Advisor.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @return the {@link ServiceCall} object
     */
    public ServiceCall<RecommendedActionResourceListInner> listAsync(String resourceGroupName, String serverName, String databaseName, String advisorName, final ServiceCallback<RecommendedActionResourceListInner> serviceCallback) {
        return ServiceCall.create(listWithServiceResponseAsync(resourceGroupName, serverName, databaseName, advisorName), serviceCallback);
    }

    /**
     * Gets list of Azure SQL Database Recommended Actions.
     *
     * @param resourceGroupName The name of the Resource Group to which the resource belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database.
     * @param advisorName The name of the Azure SQL Database Advisor.
     * @return the observable to the RecommendedActionResourceListInner object
     */
    public Observable<RecommendedActionResourceListInner> listAsync(String resourceGroupName, String serverName, String databaseName, String advisorName) {
        return listWithServiceResponseAsync(resourceGroupName, serverName, databaseName, advisorName).map(new Func1<ServiceResponse<RecommendedActionResourceListInner>, RecommendedActionResourceListInner>() {
            @Override
            public RecommendedActionResourceListInner call(ServiceResponse<RecommendedActionResourceListInner> response) {
                return response.getBody();
            }
        });
    }

    /**
     * Gets list of Azure SQL Database Recommended Actions.
     *
     * @param resourceGroupName The name of the Resource Group to which the resource belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database.
     * @param advisorName The name of the Azure SQL Database Advisor.
     * @return the observable to the RecommendedActionResourceListInner object
     */
    public Observable<ServiceResponse<RecommendedActionResourceListInner>> listWithServiceResponseAsync(String resourceGroupName, String serverName, String databaseName, String advisorName) {
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
        if (advisorName == null) {
            throw new IllegalArgumentException("Parameter advisorName is required and cannot be null.");
        }
        final String apiVersion = "2015-05-01-preview";
        return service.list(this.client.subscriptionId(), resourceGroupName, serverName, databaseName, advisorName, apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<RecommendedActionResourceListInner>>>() {
                @Override
                public Observable<ServiceResponse<RecommendedActionResourceListInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<RecommendedActionResourceListInner> clientResponse = listDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<RecommendedActionResourceListInner> listDelegate(Response<ResponseBody> response) throws ArmErrorResponseMessageException, IOException, IllegalArgumentException {
        return new AzureServiceResponseBuilder<RecommendedActionResourceListInner, ArmErrorResponseMessageException>(this.client.mapperAdapter())
                .register(200, new TypeToken<RecommendedActionResourceListInner>() { }.getType())
                .registerError(ArmErrorResponseMessageException.class)
                .build(response);
    }

    /**
     * Returns details of an Azure SQL Database Recommended Action.
     *
     * @param resourceGroupName The name of the Resource Group to which the resource belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database.
     * @param advisorName The name of the Azure SQL Database Advisor.
     * @param recommendedActionName The name of the Azure SQL Database Recommended Action.
     * @return the RecommendedActionResourceInner object if successful.
     */
    public RecommendedActionResourceInner get(String resourceGroupName, String serverName, String databaseName, String advisorName, String recommendedActionName) {
        return getWithServiceResponseAsync(resourceGroupName, serverName, databaseName, advisorName, recommendedActionName).toBlocking().single().getBody();
    }

    /**
     * Returns details of an Azure SQL Database Recommended Action.
     *
     * @param resourceGroupName The name of the Resource Group to which the resource belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database.
     * @param advisorName The name of the Azure SQL Database Advisor.
     * @param recommendedActionName The name of the Azure SQL Database Recommended Action.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @return the {@link ServiceCall} object
     */
    public ServiceCall<RecommendedActionResourceInner> getAsync(String resourceGroupName, String serverName, String databaseName, String advisorName, String recommendedActionName, final ServiceCallback<RecommendedActionResourceInner> serviceCallback) {
        return ServiceCall.create(getWithServiceResponseAsync(resourceGroupName, serverName, databaseName, advisorName, recommendedActionName), serviceCallback);
    }

    /**
     * Returns details of an Azure SQL Database Recommended Action.
     *
     * @param resourceGroupName The name of the Resource Group to which the resource belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database.
     * @param advisorName The name of the Azure SQL Database Advisor.
     * @param recommendedActionName The name of the Azure SQL Database Recommended Action.
     * @return the observable to the RecommendedActionResourceInner object
     */
    public Observable<RecommendedActionResourceInner> getAsync(String resourceGroupName, String serverName, String databaseName, String advisorName, String recommendedActionName) {
        return getWithServiceResponseAsync(resourceGroupName, serverName, databaseName, advisorName, recommendedActionName).map(new Func1<ServiceResponse<RecommendedActionResourceInner>, RecommendedActionResourceInner>() {
            @Override
            public RecommendedActionResourceInner call(ServiceResponse<RecommendedActionResourceInner> response) {
                return response.getBody();
            }
        });
    }

    /**
     * Returns details of an Azure SQL Database Recommended Action.
     *
     * @param resourceGroupName The name of the Resource Group to which the resource belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database.
     * @param advisorName The name of the Azure SQL Database Advisor.
     * @param recommendedActionName The name of the Azure SQL Database Recommended Action.
     * @return the observable to the RecommendedActionResourceInner object
     */
    public Observable<ServiceResponse<RecommendedActionResourceInner>> getWithServiceResponseAsync(String resourceGroupName, String serverName, String databaseName, String advisorName, String recommendedActionName) {
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
        if (advisorName == null) {
            throw new IllegalArgumentException("Parameter advisorName is required and cannot be null.");
        }
        if (recommendedActionName == null) {
            throw new IllegalArgumentException("Parameter recommendedActionName is required and cannot be null.");
        }
        final String apiVersion = "2015-05-01-preview";
        return service.get(this.client.subscriptionId(), resourceGroupName, serverName, databaseName, advisorName, recommendedActionName, apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<RecommendedActionResourceInner>>>() {
                @Override
                public Observable<ServiceResponse<RecommendedActionResourceInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<RecommendedActionResourceInner> clientResponse = getDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<RecommendedActionResourceInner> getDelegate(Response<ResponseBody> response) throws ArmErrorResponseMessageException, IOException, IllegalArgumentException {
        return new AzureServiceResponseBuilder<RecommendedActionResourceInner, ArmErrorResponseMessageException>(this.client.mapperAdapter())
                .register(200, new TypeToken<RecommendedActionResourceInner>() { }.getType())
                .registerError(ArmErrorResponseMessageException.class)
                .build(response);
    }

    /**
     * Updates an Azure SQL Database Recommended Action.
     *
     * @param resourceGroupName The name of the Resource Group to which the resource belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database.
     * @param advisorName The name of the Azure SQL Database Advisor.
     * @param recommendedActionName The name of the Azure SQL Database Recommended Action.
     * @param recommendedAction The requested recommended action resource state.
     * @return the RecommendedActionResourceInner object if successful.
     */
    public RecommendedActionResourceInner update(String resourceGroupName, String serverName, String databaseName, String advisorName, String recommendedActionName, RecommendedActionResourceInner recommendedAction) {
        return updateWithServiceResponseAsync(resourceGroupName, serverName, databaseName, advisorName, recommendedActionName, recommendedAction).toBlocking().single().getBody();
    }

    /**
     * Updates an Azure SQL Database Recommended Action.
     *
     * @param resourceGroupName The name of the Resource Group to which the resource belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database.
     * @param advisorName The name of the Azure SQL Database Advisor.
     * @param recommendedActionName The name of the Azure SQL Database Recommended Action.
     * @param recommendedAction The requested recommended action resource state.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @return the {@link ServiceCall} object
     */
    public ServiceCall<RecommendedActionResourceInner> updateAsync(String resourceGroupName, String serverName, String databaseName, String advisorName, String recommendedActionName, RecommendedActionResourceInner recommendedAction, final ServiceCallback<RecommendedActionResourceInner> serviceCallback) {
        return ServiceCall.create(updateWithServiceResponseAsync(resourceGroupName, serverName, databaseName, advisorName, recommendedActionName, recommendedAction), serviceCallback);
    }

    /**
     * Updates an Azure SQL Database Recommended Action.
     *
     * @param resourceGroupName The name of the Resource Group to which the resource belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database.
     * @param advisorName The name of the Azure SQL Database Advisor.
     * @param recommendedActionName The name of the Azure SQL Database Recommended Action.
     * @param recommendedAction The requested recommended action resource state.
     * @return the observable to the RecommendedActionResourceInner object
     */
    public Observable<RecommendedActionResourceInner> updateAsync(String resourceGroupName, String serverName, String databaseName, String advisorName, String recommendedActionName, RecommendedActionResourceInner recommendedAction) {
        return updateWithServiceResponseAsync(resourceGroupName, serverName, databaseName, advisorName, recommendedActionName, recommendedAction).map(new Func1<ServiceResponse<RecommendedActionResourceInner>, RecommendedActionResourceInner>() {
            @Override
            public RecommendedActionResourceInner call(ServiceResponse<RecommendedActionResourceInner> response) {
                return response.getBody();
            }
        });
    }

    /**
     * Updates an Azure SQL Database Recommended Action.
     *
     * @param resourceGroupName The name of the Resource Group to which the resource belongs.
     * @param serverName The name of the Azure SQL Database Server on which the Azure SQL Database is hosted.
     * @param databaseName The name of the Azure SQL Database.
     * @param advisorName The name of the Azure SQL Database Advisor.
     * @param recommendedActionName The name of the Azure SQL Database Recommended Action.
     * @param recommendedAction The requested recommended action resource state.
     * @return the observable to the RecommendedActionResourceInner object
     */
    public Observable<ServiceResponse<RecommendedActionResourceInner>> updateWithServiceResponseAsync(String resourceGroupName, String serverName, String databaseName, String advisorName, String recommendedActionName, RecommendedActionResourceInner recommendedAction) {
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
        if (advisorName == null) {
            throw new IllegalArgumentException("Parameter advisorName is required and cannot be null.");
        }
        if (recommendedActionName == null) {
            throw new IllegalArgumentException("Parameter recommendedActionName is required and cannot be null.");
        }
        if (recommendedAction == null) {
            throw new IllegalArgumentException("Parameter recommendedAction is required and cannot be null.");
        }
        Validator.validate(recommendedAction);
        final String apiVersion = "2015-05-01-preview";
        return service.update(this.client.subscriptionId(), resourceGroupName, serverName, databaseName, advisorName, recommendedActionName, recommendedAction, apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<RecommendedActionResourceInner>>>() {
                @Override
                public Observable<ServiceResponse<RecommendedActionResourceInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<RecommendedActionResourceInner> clientResponse = updateDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<RecommendedActionResourceInner> updateDelegate(Response<ResponseBody> response) throws ArmErrorResponseMessageException, IOException, IllegalArgumentException {
        return new AzureServiceResponseBuilder<RecommendedActionResourceInner, ArmErrorResponseMessageException>(this.client.mapperAdapter())
                .register(200, new TypeToken<RecommendedActionResourceInner>() { }.getType())
                .registerError(ArmErrorResponseMessageException.class)
                .build(response);
    }

}
