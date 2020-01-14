// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.management;

import com.azure.core.http.HttpPipeline;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.implementation.polling.PollerFactory;
import com.azure.core.management.implementation.polling.PollingState;
import com.azure.core.management.serializer.AzureJacksonAdapter;
import com.azure.core.polling.PollResult;
import com.azure.core.util.FluxUtil;
import com.azure.core.util.polling.PollerFlux;
import com.azure.core.util.polling.PollingContext;
import com.azure.core.util.serializer.SerializerAdapter;
import com.azure.core.util.serializer.SerializerEncoding;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.function.Function;

import static com.azure.core.util.FluxUtil.withContext;

/**
 * ServiceClient is the abstraction for accessing REST operations and their payload data types.
 */
public abstract class AzureServiceClient {
//    /**
//     * Initializes a new instance of the ServiceClient class.
//     *
//     * @param baseUrl     the service base uri
//     * @param credentials the credentials
//     */
//    protected AzureServiceClient(String baseUrl, TokenCredential credentials) {
//        this(new RestClientBuilder()
//                .withBaseUrl(baseUrl)
//                .withCredential(credentials)
//                .withSerializerAdapter(new AzureJacksonAdapter())
//                .buildClient());
//    }
//
//    /**
//     * Initializes a new instance of the ServiceClient class.
//     *
//     * @param restClient the REST client
//     */
//    protected AzureServiceClient(RestClient restClient) {
//        super(restClient);
//    }

    protected AzureServiceClient(HttpPipeline httpPipeline, AzureEnvironment environment) {
    }

    /**
     * The default User-Agent header. Override this method to override the user agent.
     *
     * @return the user agent string.
     */
    public String userAgent() {
        return String.format("Azure-SDK-For-Java/%s OS:%s MacAddressHash:%s Java:%s",
                getClass().getPackage().getImplementationVersion(),
                OS,
                MAC_ADDRESS_HASH,
                JAVA_VERSION);
    }

    private static final String MAC_ADDRESS_HASH;
    private static final String OS;
    private static final String JAVA_VERSION;

    static {
        OS = System.getProperty("os.name") + "/" + System.getProperty("os.version");
        String macAddress = "Unknown";
//        try {
//            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
//            while (networks.hasMoreElements()) {
//                NetworkInterface network = networks.nextElement();
//                byte[] mac = network.getHardwareAddress();
//
//                if (mac != null) {
//                    macAddress = Hashing.sha256().hashBytes(mac).toString();
//                    break;
//                }
//            }
//        } catch (Throwable t) {
//            // It's okay ignore mac address hash telemetry
//        }
        MAC_ADDRESS_HASH = macAddress;
        String version = System.getProperty("java.version");
        JAVA_VERSION = version != null ? version : "Unknown";
    }

    private SerializerAdapter serializerAdapter = new AzureJacksonAdapter();

    private int longRunningOperationRetryTimeout = -1;

    public SerializerAdapter getSerializerAdapter() {
        return this.serializerAdapter;
    }

    public <T, U> PollerFlux<PollResult<T>, U> getLroResultAsync(Mono<SimpleResponse<Flux<ByteBuffer>>> lroInit,
                                                                 HttpPipeline httpPipeline,
                                                                 Type pollResultType, Type finalResultType) {
        return PollerFactory.create(
                getSerializerAdapter(),
                httpPipeline,
                pollResultType,
                finalResultType,
                Duration.ofSeconds(this.longRunningOperationRetryTimeout),
                activationOperationRaw(lroInit, pollResultType));
    }

    private <T> Function<PollingContext<PollResult<T>>, Mono<PollResult<T>>> activationOperationRaw(Mono<SimpleResponse<Flux<ByteBuffer>>> lroInit, Type type) {
        return (pollingContext) -> withContext(context -> lroInit
                .flatMap(response -> {
                    Mono<String> bodyAsString = FluxUtil.collectBytesInByteBufferStream(response.getValue().map(ByteBuffer::duplicate)).map(bytes -> bytes == null ? null : new String(bytes, StandardCharsets.UTF_8));
                    return bodyAsString.map(body -> {
                        PollingState state = PollingState.create(getSerializerAdapter(), response.getRequest(), response.getStatusCode(), response.getHeaders(), body);
                        if (response.getStatusCode() == 200) {
                            // pollingContext.setData("INIT_STATUS", LongRunningOperationStatus.SUCCESSFULLY_COMPLETED.toString());
                        }
                        state.store(pollingContext);
                        try {
                            return getSerializerAdapter().deserialize(body, type, SerializerEncoding.JSON);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return (T) null;
                        }
                    }).map(PollResult::new);
                }));
        // TODO: onErrorMap(error -> Mono.just(new PollResult<T>((PollResult.Error) error)));
    }
}