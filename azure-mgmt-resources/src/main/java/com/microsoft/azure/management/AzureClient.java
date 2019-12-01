/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management;

import com.azure.core.http.rest.Response;
import com.azure.core.management.implementation.polling.PollerFactory;
import com.azure.core.management.implementation.polling.PollingState;
import com.azure.core.polling.PollResult;
import com.azure.core.util.logging.ClientLogger;
import com.azure.core.util.polling.LongRunningOperationStatus;
import com.azure.core.util.polling.PollerFlux;
import com.azure.core.util.polling.PollingContext;
import com.azure.core.util.serializer.SerializerEncoding;
import com.google.gson.reflect.TypeToken;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.function.Function;

import static com.azure.core.util.FluxUtil.withContext;

/**
 * An instance of this class defines a ServiceClient that handles polling and
 * retrying for long running operations when accessing Azure resources.
 */
public class AzureClient extends AzureServiceClient {


    private final ClientLogger logger = new ClientLogger(AzureClient.class);

    /**
     * The interval time between two long running operation polls. Default is 30 seconds.
     */
    private int longRunningOperationRetryTimeout = -1;

    /**
     * The user agent from the service client that owns this Azure Client.
     */
    private final String serviceClientUserAgent;

    /**
     * Initializes an instance of this class with customized client metadata.
     *
     * @param serviceClient the caller client that initiates the asynchronous request.
     */
    public AzureClient(AzureServiceClient serviceClient) {
        super(serviceClient.restClient());
        this.serviceClientUserAgent = serviceClient.userAgent();
        this.longRunningOperationRetryTimeout = 30;
    }

    /**
     * Gets the interval time between two long running operation polls.
     *
     * @return the time in seconds.
     */
    public Integer longRunningOperationRetryTimeout() {
        return longRunningOperationRetryTimeout;
    }

    /**
     * Sets the interval time between two long running operation polls. Default is 30 seconds.
     * Set to any negative value to let AzureClient ignore this setting.
     *
     * @param longRunningOperationRetryTimeout the time in seconds. Set to any negative value to let AzureClient ignore this setting.
     */
    public void setLongRunningOperationRetryTimeout(int longRunningOperationRetryTimeout) {
        if (longRunningOperationRetryTimeout < 0) {
            throw new IllegalArgumentException("Invalid timeout for long running operations : " + longRunningOperationRetryTimeout);
        }
        this.longRunningOperationRetryTimeout = longRunningOperationRetryTimeout;
    }


    public <T, U> PollerFlux<PollResult<T>, U> getPutOrPatchResultAsync(Mono<Response<T>> lroInit, Type pollResultType, Type finalResultType) {
        return PollerFactory.create(this.restClient().getSerializerAdapter(),
                this.restClient().getHttpPipeline(),
                pollResultType,
                finalResultType,
                Duration.ofSeconds(this.longRunningOperationRetryTimeout),
                activationOperation(lroInit));

//        return new PollerFlux<T, U>(Duration.ofSeconds(this.longRunningOperationRetryTimeout),
//                activationOperation(observable),
//                createPollOperation(observable),
//                (pollerContext, firstResponse) -> Mono.empty(),
//                (pollingContext) -> Mono.empty());
    }


    public <T, U> PollerFlux<PollResult<T>, U> getPostOrDeleteResultAsync(Mono<Response<T>> lroInit) {
        return PollerFactory.create(this.restClient().getSerializerAdapter(),
                this.restClient().getHttpPipeline(),
                new TypeToken<T>() {
                }.getType(),
                new TypeToken<U>() {
                }.getType(),
                Duration.ofSeconds(this.longRunningOperationRetryTimeout),
                activationOperation(lroInit));
    }


    private <T> Function<PollingContext<PollResult<T>>, Mono<PollResult<T>>> activationOperation(Mono<Response<T>> lroInit) {
        return (pollingContext) -> withContext(context -> lroInit
                .flatMap(response -> {
                    PollingState state = null;
                    try {
                        state = PollingState.create(getSerializerAdapter(), response.getRequest(), response.getStatusCode(), response.getHeaders(),
                                this.getSerializerAdapter().serialize(response.getValue(), SerializerEncoding.JSON));
                        if (response.getStatusCode() == 200) {
                            // pollingContext.setData("INIT_STATUS", LongRunningOperationStatus.SUCCESSFULLY_COMPLETED.toString());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    state.store(pollingContext);
                    return Mono.just(new PollResult<T>(response.getValue()));
                }));
        // TODO: onErrorMap(error -> Mono.just(new PollResult<T>((PollResult.Error) error)));
    }
}