/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.appservice.implementation;

import com.azure.core.annotation.BodyParam;
import com.azure.core.annotation.Get;
import com.azure.core.annotation.Headers;
import com.azure.core.annotation.Host;
import com.azure.core.annotation.HostParam;
import com.azure.core.annotation.Post;
import com.azure.core.annotation.ServiceInterface;
import com.azure.core.http.HttpPipelineCallContext;
import com.azure.core.http.HttpPipelineNextPolicy;
import com.azure.core.http.HttpResponse;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.policy.HttpPipelinePolicy;
import com.azure.core.http.rest.RestProxy;
import com.azure.core.http.rest.StreamResponse;
import com.azure.core.management.CloudException;
import com.azure.management.RestClient;
import com.azure.management.RestClientBuilder;
import com.fasterxml.jackson.core.JsonParseException;
import com.google.common.base.Joiner;
import com.azure.management.appservice.WebAppBase;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;

/**
 * A client which interacts with Kudu service.
 */
class KuduClient {
    private String host;
    private KuduService service;

    KuduClient(WebAppBase webAppBase) {
        if (webAppBase.defaultHostName() == null) {
            throw new UnsupportedOperationException("Cannot initialize kudu client before web app is created");
        }
        String host = webAppBase.defaultHostName().toLowerCase()
                .replace("http://", "")
                .replace("https://", "");
        String[] parts = host.split("\\.", 2);
        host = Joiner.on('.').join(parts[0], "scm", parts[1]);
        this.host = "https://" + host;
        RestClient client = new RestClientBuilder() //webAppBase.manager().restClient().newBuilder()
                .withBaseUrl(this.host)
                .withHttpLogOptions(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS))
                // FIXME
                .withPolicy(new KuduAuthenticationPolicy(webAppBase))
//                .withConnectionTimeout(3, TimeUnit.MINUTES)
//                .withReadTimeout(3, TimeUnit.MINUTES)
                .buildClient();

        service = RestProxy.create(KuduService.class, client.getHttpPipeline());
    }

    @Host("{$host}")
    @ServiceInterface(name = "KuduService")
    private interface KuduService {
        @Headers({ "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps streamApplicationLogs", "x-ms-body-logging: false" })
        @Get("api/logstream/application")
        Mono<StreamResponse> streamApplicationLogs(@HostParam("$host") String host);

        @Headers({ "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps streamHttpLogs", "x-ms-body-logging: false" })
        @Get("api/logstream/http")
        Mono<StreamResponse> streamHttpLogs(@HostParam("$host") String host);

        @Headers({ "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps streamTraceLogs", "x-ms-body-logging: false" })
        @Get("api/logstream/kudu/trace")
        Mono<StreamResponse> streamTraceLogs(@HostParam("$host") String host);

        @Headers({ "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps streamDeploymentLogs", "x-ms-body-logging: false" })
        @Get("api/logstream/kudu/deployment")
        Mono<StreamResponse> streamDeploymentLogs(@HostParam("$host") String host);

        @Headers({ "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps streamAllLogs", "x-ms-body-logging: false" })
        @Get("api/logstream")
        Mono<StreamResponse> streamAllLogs(@HostParam("$host") String host);

        @Headers({ "Content-Type: application/octet-stream", "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps warDeploy", "x-ms-body-logging: false" })
        @Post("api/wardeploy")
        Mono<Void> warDeploy(@HostParam("$host") String host, @BodyParam("application/octet-stream") byte[] warFile, String appName);

        @Headers({ "Content-Type: application/octet-stream", "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps zipDeploy", "x-ms-body-logging: false" })
        @Post("api/zipdeploy")
        Mono<Void> zipDeploy(@HostParam("$host") String host, @BodyParam("application/octet-stream") byte[] zipFile);
    }

    Flux<String> streamApplicationLogsAsync() {
        return streamFromFluxBytes(service.streamApplicationLogs(host).flatMapMany(StreamResponse::getValue));
    }

    Flux<String> streamHttpLogsAsync() {
        return streamFromFluxBytes(service.streamHttpLogs(host).flatMapMany(StreamResponse::getValue));
    }

    Flux<String> streamTraceLogsAsync() {
        return streamFromFluxBytes(service.streamTraceLogs(host).flatMapMany(StreamResponse::getValue));
    }

    Flux<String> streamDeploymentLogsAsync() {
        return streamFromFluxBytes(service.streamDeploymentLogs(host).flatMapMany(StreamResponse::getValue));
    }

    Flux<String> streamAllLogsAsync() {
        return streamFromFluxBytes(service.streamAllLogs(host).flatMapMany(StreamResponse::getValue));
    }

    private Flux<String> streamFromFluxBytes(final Flux<ByteBuffer> source) {
        // FIXME
        return source.map(StandardCharsets.UTF_8::decode).map(CharBuffer::toString);

//        return Observable.create(new Action1<Emitter<String>>() {
//            @Override
//            public void call(Emitter<String> stringEmitter) {
//                try {
//                    while (!source.exhausted()) {
//                        stringEmitter.onNext(source.readUtf8Line());
//                    }
//                    stringEmitter.onCompleted();
//                } catch (IOException e) {
//                    stringEmitter.onError(e);
//                }
//            }
//        }, BackpressureMode.BUFFER);
    }

    Mono<Void> warDeployAsync(InputStream warFile, String appName) {
        return withRetry(service.warDeploy(host, byteArrayFromInputStream(warFile), appName));
    }

    Mono<Void> zipDeployAsync(InputStream zipFile) {
        return withRetry(service.zipDeploy(host, byteArrayFromInputStream(zipFile)));
    }

    private byte[] byteArrayFromInputStream(InputStream inputStream) {
        // FIXME core does not yet support InputStream as @BodyParam
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            return buffer.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Mono<Void> withRetry(Mono<Void> observable) {
        return observable
                .retryWhen(flux -> flux.zipWith(Flux.range(1, 30), (Throwable throwable, Integer integer) -> {
                    if (throwable instanceof CloudException
                            && ((CloudException) throwable).getResponse().getStatusCode() == 502 || throwable instanceof JsonParseException) {
                        return integer;
                    } else {
                        throw Exceptions.propagate(throwable);
                    }
                }).flatMap(i -> Mono.delay(Duration.ofSeconds(i))));
    }

    private static final class KuduAuthenticationPolicy implements HttpPipelinePolicy {
        private final WebAppBase webApp;
        private final static String HEADER_NAME = "Authorization";
        private String basicToken;

        private KuduAuthenticationPolicy(WebAppBase webApp) {
            this.webApp = webApp;
        }

        @Override
        public Mono<HttpResponse> process(HttpPipelineCallContext context, HttpPipelineNextPolicy next) {
            Mono<String> basicTokenMono = basicToken == null
                    ? webApp.getPublishingProfileAsync().map(profile -> {
                        basicToken = Base64.getEncoder().encodeToString((profile.gitUsername() + ":" + profile.gitPassword()).getBytes(StandardCharsets.UTF_8));
                        return basicToken;
                    })
                    : Mono.just(basicToken);
            return basicTokenMono.flatMap(key -> {
                context.getHttpRequest().setHeader(HEADER_NAME, "Basic " + basicToken);
                return next.process();
            });
        }
    }
}
