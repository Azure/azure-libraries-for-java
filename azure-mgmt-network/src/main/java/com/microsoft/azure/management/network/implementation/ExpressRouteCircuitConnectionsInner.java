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
import com.microsoft.azure.CloudException;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import com.microsoft.rest.ServiceResponse;
import com.microsoft.rest.Validator;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.HTTP;
import retrofit2.http.Path;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.Response;
import rx.functions.Func1;
import rx.Observable;

/**
 * An instance of this class provides access to all the operations defined
 * in ExpressRouteCircuitConnections.
 */
public class ExpressRouteCircuitConnectionsInner {
    /** The Retrofit service to perform REST calls. */
    private ExpressRouteCircuitConnectionsService service;
    /** The service client containing this operation class. */
    private NetworkManagementClientImpl client;

    /**
     * Initializes an instance of ExpressRouteCircuitConnectionsInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public ExpressRouteCircuitConnectionsInner(Retrofit retrofit, NetworkManagementClientImpl client) {
        this.service = retrofit.create(ExpressRouteCircuitConnectionsService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for ExpressRouteCircuitConnections to be
     * used by Retrofit to perform actually REST calls.
     */
    interface ExpressRouteCircuitConnectionsService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.network.ExpressRouteCircuitConnections delete" })
        @HTTP(path = "subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/expressRouteCircuits/{circuitName}/peerings/{peeringName}/connections/{connectionName}", method = "DELETE", hasBody = true)
        Observable<Response<ResponseBody>> delete(@Path("resourceGroupName") String resourceGroupName, @Path("circuitName") String circuitName, @Path("peeringName") String peeringName, @Path("connectionName") String connectionName, @Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.network.ExpressRouteCircuitConnections beginDelete" })
        @HTTP(path = "subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/expressRouteCircuits/{circuitName}/peerings/{peeringName}/connections/{connectionName}", method = "DELETE", hasBody = true)
        Observable<Response<ResponseBody>> beginDelete(@Path("resourceGroupName") String resourceGroupName, @Path("circuitName") String circuitName, @Path("peeringName") String peeringName, @Path("connectionName") String connectionName, @Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.network.ExpressRouteCircuitConnections get" })
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/expressRouteCircuits/{circuitName}/peerings/{peeringName}/connections/{connectionName}")
        Observable<Response<ResponseBody>> get(@Path("resourceGroupName") String resourceGroupName, @Path("circuitName") String circuitName, @Path("peeringName") String peeringName, @Path("connectionName") String connectionName, @Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.network.ExpressRouteCircuitConnections createOrUpdate" })
        @PUT("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/expressRouteCircuits/{circuitName}/peerings/{peeringName}/connections/{connectionName}")
        Observable<Response<ResponseBody>> createOrUpdate(@Path("resourceGroupName") String resourceGroupName, @Path("circuitName") String circuitName, @Path("peeringName") String peeringName, @Path("connectionName") String connectionName, @Path("subscriptionId") String subscriptionId, @Body ExpressRouteCircuitConnectionInner expressRouteCircuitConnectionParameters, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.network.ExpressRouteCircuitConnections beginCreateOrUpdate" })
        @PUT("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/expressRouteCircuits/{circuitName}/peerings/{peeringName}/connections/{connectionName}")
        Observable<Response<ResponseBody>> beginCreateOrUpdate(@Path("resourceGroupName") String resourceGroupName, @Path("circuitName") String circuitName, @Path("peeringName") String peeringName, @Path("connectionName") String connectionName, @Path("subscriptionId") String subscriptionId, @Body ExpressRouteCircuitConnectionInner expressRouteCircuitConnectionParameters, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Deletes the specified Express Route Circuit Connection from the specified express route circuit.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     */
    public void delete(String resourceGroupName, String circuitName, String peeringName, String connectionName) {
        deleteWithServiceResponseAsync(resourceGroupName, circuitName, peeringName, connectionName).toBlocking().last().body();
    }

    /**
     * Deletes the specified Express Route Circuit Connection from the specified express route circuit.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<Void> deleteAsync(String resourceGroupName, String circuitName, String peeringName, String connectionName, final ServiceCallback<Void> serviceCallback) {
        return ServiceFuture.fromResponse(deleteWithServiceResponseAsync(resourceGroupName, circuitName, peeringName, connectionName), serviceCallback);
    }

    /**
     * Deletes the specified Express Route Circuit Connection from the specified express route circuit.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    public Observable<Void> deleteAsync(String resourceGroupName, String circuitName, String peeringName, String connectionName) {
        return deleteWithServiceResponseAsync(resourceGroupName, circuitName, peeringName, connectionName).map(new Func1<ServiceResponse<Void>, Void>() {
            @Override
            public Void call(ServiceResponse<Void> response) {
                return response.body();
            }
        });
    }

    /**
     * Deletes the specified Express Route Circuit Connection from the specified express route circuit.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    public Observable<ServiceResponse<Void>> deleteWithServiceResponseAsync(String resourceGroupName, String circuitName, String peeringName, String connectionName) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (circuitName == null) {
            throw new IllegalArgumentException("Parameter circuitName is required and cannot be null.");
        }
        if (peeringName == null) {
            throw new IllegalArgumentException("Parameter peeringName is required and cannot be null.");
        }
        if (connectionName == null) {
            throw new IllegalArgumentException("Parameter connectionName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        final String apiVersion = "2018-06-01";
        Observable<Response<ResponseBody>> observable = service.delete(resourceGroupName, circuitName, peeringName, connectionName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage(), this.client.userAgent());
        return client.getAzureClient().getPostOrDeleteResultAsync(observable, new TypeToken<Void>() { }.getType());
    }

    /**
     * Deletes the specified Express Route Circuit Connection from the specified express route circuit.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     */
    public void beginDelete(String resourceGroupName, String circuitName, String peeringName, String connectionName) {
        beginDeleteWithServiceResponseAsync(resourceGroupName, circuitName, peeringName, connectionName).toBlocking().single().body();
    }

    /**
     * Deletes the specified Express Route Circuit Connection from the specified express route circuit.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<Void> beginDeleteAsync(String resourceGroupName, String circuitName, String peeringName, String connectionName, final ServiceCallback<Void> serviceCallback) {
        return ServiceFuture.fromResponse(beginDeleteWithServiceResponseAsync(resourceGroupName, circuitName, peeringName, connectionName), serviceCallback);
    }

    /**
     * Deletes the specified Express Route Circuit Connection from the specified express route circuit.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceResponse} object if successful.
     */
    public Observable<Void> beginDeleteAsync(String resourceGroupName, String circuitName, String peeringName, String connectionName) {
        return beginDeleteWithServiceResponseAsync(resourceGroupName, circuitName, peeringName, connectionName).map(new Func1<ServiceResponse<Void>, Void>() {
            @Override
            public Void call(ServiceResponse<Void> response) {
                return response.body();
            }
        });
    }

    /**
     * Deletes the specified Express Route Circuit Connection from the specified express route circuit.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceResponse} object if successful.
     */
    public Observable<ServiceResponse<Void>> beginDeleteWithServiceResponseAsync(String resourceGroupName, String circuitName, String peeringName, String connectionName) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (circuitName == null) {
            throw new IllegalArgumentException("Parameter circuitName is required and cannot be null.");
        }
        if (peeringName == null) {
            throw new IllegalArgumentException("Parameter peeringName is required and cannot be null.");
        }
        if (connectionName == null) {
            throw new IllegalArgumentException("Parameter connectionName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        final String apiVersion = "2018-06-01";
        return service.beginDelete(resourceGroupName, circuitName, peeringName, connectionName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Void>>>() {
                @Override
                public Observable<ServiceResponse<Void>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<Void> clientResponse = beginDeleteDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<Void> beginDeleteDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<Void, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<Void>() { }.getType())
                .register(202, new TypeToken<Void>() { }.getType())
                .register(204, new TypeToken<Void>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Gets the specified Express Route Circuit Connection from the specified express route circuit.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the ExpressRouteCircuitConnectionInner object if successful.
     */
    public ExpressRouteCircuitConnectionInner get(String resourceGroupName, String circuitName, String peeringName, String connectionName) {
        return getWithServiceResponseAsync(resourceGroupName, circuitName, peeringName, connectionName).toBlocking().single().body();
    }

    /**
     * Gets the specified Express Route Circuit Connection from the specified express route circuit.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<ExpressRouteCircuitConnectionInner> getAsync(String resourceGroupName, String circuitName, String peeringName, String connectionName, final ServiceCallback<ExpressRouteCircuitConnectionInner> serviceCallback) {
        return ServiceFuture.fromResponse(getWithServiceResponseAsync(resourceGroupName, circuitName, peeringName, connectionName), serviceCallback);
    }

    /**
     * Gets the specified Express Route Circuit Connection from the specified express route circuit.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the ExpressRouteCircuitConnectionInner object
     */
    public Observable<ExpressRouteCircuitConnectionInner> getAsync(String resourceGroupName, String circuitName, String peeringName, String connectionName) {
        return getWithServiceResponseAsync(resourceGroupName, circuitName, peeringName, connectionName).map(new Func1<ServiceResponse<ExpressRouteCircuitConnectionInner>, ExpressRouteCircuitConnectionInner>() {
            @Override
            public ExpressRouteCircuitConnectionInner call(ServiceResponse<ExpressRouteCircuitConnectionInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Gets the specified Express Route Circuit Connection from the specified express route circuit.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the ExpressRouteCircuitConnectionInner object
     */
    public Observable<ServiceResponse<ExpressRouteCircuitConnectionInner>> getWithServiceResponseAsync(String resourceGroupName, String circuitName, String peeringName, String connectionName) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (circuitName == null) {
            throw new IllegalArgumentException("Parameter circuitName is required and cannot be null.");
        }
        if (peeringName == null) {
            throw new IllegalArgumentException("Parameter peeringName is required and cannot be null.");
        }
        if (connectionName == null) {
            throw new IllegalArgumentException("Parameter connectionName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        final String apiVersion = "2018-06-01";
        return service.get(resourceGroupName, circuitName, peeringName, connectionName, this.client.subscriptionId(), apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<ExpressRouteCircuitConnectionInner>>>() {
                @Override
                public Observable<ServiceResponse<ExpressRouteCircuitConnectionInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<ExpressRouteCircuitConnectionInner> clientResponse = getDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<ExpressRouteCircuitConnectionInner> getDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<ExpressRouteCircuitConnectionInner, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<ExpressRouteCircuitConnectionInner>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Creates or updates a Express Route Circuit Connection in the specified express route circuits.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @param expressRouteCircuitConnectionParameters Parameters supplied to the create or update express route circuit circuit connection operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the ExpressRouteCircuitConnectionInner object if successful.
     */
    public ExpressRouteCircuitConnectionInner createOrUpdate(String resourceGroupName, String circuitName, String peeringName, String connectionName, ExpressRouteCircuitConnectionInner expressRouteCircuitConnectionParameters) {
        return createOrUpdateWithServiceResponseAsync(resourceGroupName, circuitName, peeringName, connectionName, expressRouteCircuitConnectionParameters).toBlocking().last().body();
    }

    /**
     * Creates or updates a Express Route Circuit Connection in the specified express route circuits.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @param expressRouteCircuitConnectionParameters Parameters supplied to the create or update express route circuit circuit connection operation.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<ExpressRouteCircuitConnectionInner> createOrUpdateAsync(String resourceGroupName, String circuitName, String peeringName, String connectionName, ExpressRouteCircuitConnectionInner expressRouteCircuitConnectionParameters, final ServiceCallback<ExpressRouteCircuitConnectionInner> serviceCallback) {
        return ServiceFuture.fromResponse(createOrUpdateWithServiceResponseAsync(resourceGroupName, circuitName, peeringName, connectionName, expressRouteCircuitConnectionParameters), serviceCallback);
    }

    /**
     * Creates or updates a Express Route Circuit Connection in the specified express route circuits.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @param expressRouteCircuitConnectionParameters Parameters supplied to the create or update express route circuit circuit connection operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    public Observable<ExpressRouteCircuitConnectionInner> createOrUpdateAsync(String resourceGroupName, String circuitName, String peeringName, String connectionName, ExpressRouteCircuitConnectionInner expressRouteCircuitConnectionParameters) {
        return createOrUpdateWithServiceResponseAsync(resourceGroupName, circuitName, peeringName, connectionName, expressRouteCircuitConnectionParameters).map(new Func1<ServiceResponse<ExpressRouteCircuitConnectionInner>, ExpressRouteCircuitConnectionInner>() {
            @Override
            public ExpressRouteCircuitConnectionInner call(ServiceResponse<ExpressRouteCircuitConnectionInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Creates or updates a Express Route Circuit Connection in the specified express route circuits.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @param expressRouteCircuitConnectionParameters Parameters supplied to the create or update express route circuit circuit connection operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    public Observable<ServiceResponse<ExpressRouteCircuitConnectionInner>> createOrUpdateWithServiceResponseAsync(String resourceGroupName, String circuitName, String peeringName, String connectionName, ExpressRouteCircuitConnectionInner expressRouteCircuitConnectionParameters) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (circuitName == null) {
            throw new IllegalArgumentException("Parameter circuitName is required and cannot be null.");
        }
        if (peeringName == null) {
            throw new IllegalArgumentException("Parameter peeringName is required and cannot be null.");
        }
        if (connectionName == null) {
            throw new IllegalArgumentException("Parameter connectionName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (expressRouteCircuitConnectionParameters == null) {
            throw new IllegalArgumentException("Parameter expressRouteCircuitConnectionParameters is required and cannot be null.");
        }
        Validator.validate(expressRouteCircuitConnectionParameters);
        final String apiVersion = "2018-06-01";
        Observable<Response<ResponseBody>> observable = service.createOrUpdate(resourceGroupName, circuitName, peeringName, connectionName, this.client.subscriptionId(), expressRouteCircuitConnectionParameters, apiVersion, this.client.acceptLanguage(), this.client.userAgent());
        return client.getAzureClient().getPutOrPatchResultAsync(observable, new TypeToken<ExpressRouteCircuitConnectionInner>() { }.getType());
    }

    /**
     * Creates or updates a Express Route Circuit Connection in the specified express route circuits.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @param expressRouteCircuitConnectionParameters Parameters supplied to the create or update express route circuit circuit connection operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the ExpressRouteCircuitConnectionInner object if successful.
     */
    public ExpressRouteCircuitConnectionInner beginCreateOrUpdate(String resourceGroupName, String circuitName, String peeringName, String connectionName, ExpressRouteCircuitConnectionInner expressRouteCircuitConnectionParameters) {
        return beginCreateOrUpdateWithServiceResponseAsync(resourceGroupName, circuitName, peeringName, connectionName, expressRouteCircuitConnectionParameters).toBlocking().single().body();
    }

    /**
     * Creates or updates a Express Route Circuit Connection in the specified express route circuits.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @param expressRouteCircuitConnectionParameters Parameters supplied to the create or update express route circuit circuit connection operation.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<ExpressRouteCircuitConnectionInner> beginCreateOrUpdateAsync(String resourceGroupName, String circuitName, String peeringName, String connectionName, ExpressRouteCircuitConnectionInner expressRouteCircuitConnectionParameters, final ServiceCallback<ExpressRouteCircuitConnectionInner> serviceCallback) {
        return ServiceFuture.fromResponse(beginCreateOrUpdateWithServiceResponseAsync(resourceGroupName, circuitName, peeringName, connectionName, expressRouteCircuitConnectionParameters), serviceCallback);
    }

    /**
     * Creates or updates a Express Route Circuit Connection in the specified express route circuits.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @param expressRouteCircuitConnectionParameters Parameters supplied to the create or update express route circuit circuit connection operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the ExpressRouteCircuitConnectionInner object
     */
    public Observable<ExpressRouteCircuitConnectionInner> beginCreateOrUpdateAsync(String resourceGroupName, String circuitName, String peeringName, String connectionName, ExpressRouteCircuitConnectionInner expressRouteCircuitConnectionParameters) {
        return beginCreateOrUpdateWithServiceResponseAsync(resourceGroupName, circuitName, peeringName, connectionName, expressRouteCircuitConnectionParameters).map(new Func1<ServiceResponse<ExpressRouteCircuitConnectionInner>, ExpressRouteCircuitConnectionInner>() {
            @Override
            public ExpressRouteCircuitConnectionInner call(ServiceResponse<ExpressRouteCircuitConnectionInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Creates or updates a Express Route Circuit Connection in the specified express route circuits.
     *
     * @param resourceGroupName The name of the resource group.
     * @param circuitName The name of the express route circuit.
     * @param peeringName The name of the peering.
     * @param connectionName The name of the express route circuit connection.
     * @param expressRouteCircuitConnectionParameters Parameters supplied to the create or update express route circuit circuit connection operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the ExpressRouteCircuitConnectionInner object
     */
    public Observable<ServiceResponse<ExpressRouteCircuitConnectionInner>> beginCreateOrUpdateWithServiceResponseAsync(String resourceGroupName, String circuitName, String peeringName, String connectionName, ExpressRouteCircuitConnectionInner expressRouteCircuitConnectionParameters) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (circuitName == null) {
            throw new IllegalArgumentException("Parameter circuitName is required and cannot be null.");
        }
        if (peeringName == null) {
            throw new IllegalArgumentException("Parameter peeringName is required and cannot be null.");
        }
        if (connectionName == null) {
            throw new IllegalArgumentException("Parameter connectionName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (expressRouteCircuitConnectionParameters == null) {
            throw new IllegalArgumentException("Parameter expressRouteCircuitConnectionParameters is required and cannot be null.");
        }
        Validator.validate(expressRouteCircuitConnectionParameters);
        final String apiVersion = "2018-06-01";
        return service.beginCreateOrUpdate(resourceGroupName, circuitName, peeringName, connectionName, this.client.subscriptionId(), expressRouteCircuitConnectionParameters, apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<ExpressRouteCircuitConnectionInner>>>() {
                @Override
                public Observable<ServiceResponse<ExpressRouteCircuitConnectionInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<ExpressRouteCircuitConnectionInner> clientResponse = beginCreateOrUpdateDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<ExpressRouteCircuitConnectionInner> beginCreateOrUpdateDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<ExpressRouteCircuitConnectionInner, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<ExpressRouteCircuitConnectionInner>() { }.getType())
                .register(201, new TypeToken<ExpressRouteCircuitConnectionInner>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

}
