/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.graphrbac.implementation;

import retrofit2.Retrofit;
import com.google.common.reflect.TypeToken;
import com.microsoft.azure.management.graphrbac.GraphErrorException;
import com.microsoft.rest.ServiceCall;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceResponse;
import java.io.IOException;
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
 * in Objects.
 */
public final class ObjectsInner {
    /** The Retrofit service to perform REST calls. */
    private ObjectsService service;
    /** The service client containing this operation class. */
    private GraphRbacManagementClientImpl client;

    /**
     * Initializes an instance of ObjectsInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public ObjectsInner(Retrofit retrofit, GraphRbacManagementClientImpl client) {
        this.service = retrofit.create(ObjectsService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for Objects to be
     * used by Retrofit to perform actually REST calls.
     */
    interface ObjectsService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.graphrbac.Objects getCurrentUser" })
        @GET("{tenantID}/me")
        Observable<Response<ResponseBody>> getCurrentUser(@Path("tenantID") String tenantID, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Gets the details for the currently logged-in user.
     *
     * @return the AADObjectInner object if successful.
     */
    public AADObjectInner getCurrentUser() {
        return getCurrentUserWithServiceResponseAsync().toBlocking().single().body();
    }

    /**
     * Gets the details for the currently logged-in user.
     *
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @return the {@link ServiceCall} object
     */
    public ServiceCall<AADObjectInner> getCurrentUserAsync(final ServiceCallback<AADObjectInner> serviceCallback) {
        return ServiceCall.fromResponse(getCurrentUserWithServiceResponseAsync(), serviceCallback);
    }

    /**
     * Gets the details for the currently logged-in user.
     *
     * @return the observable to the AADObjectInner object
     */
    public Observable<AADObjectInner> getCurrentUserAsync() {
        return getCurrentUserWithServiceResponseAsync().map(new Func1<ServiceResponse<AADObjectInner>, AADObjectInner>() {
            @Override
            public AADObjectInner call(ServiceResponse<AADObjectInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Gets the details for the currently logged-in user.
     *
     * @return the observable to the AADObjectInner object
     */
    public Observable<ServiceResponse<AADObjectInner>> getCurrentUserWithServiceResponseAsync() {
        if (this.client.tenantID() == null) {
            throw new IllegalArgumentException("Parameter this.client.tenantID() is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        return service.getCurrentUser(this.client.tenantID(), this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<AADObjectInner>>>() {
                @Override
                public Observable<ServiceResponse<AADObjectInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<AADObjectInner> clientResponse = getCurrentUserDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<AADObjectInner> getCurrentUserDelegate(Response<ResponseBody> response) throws GraphErrorException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<AADObjectInner, GraphErrorException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<AADObjectInner>() { }.getType())
                .registerError(GraphErrorException.class)
                .build(response);
    }

}
