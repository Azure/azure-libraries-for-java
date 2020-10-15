/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.fasterxml.jackson.core.JsonParseException;
import com.google.common.base.Joiner;
import com.google.common.io.ByteStreams;
import com.microsoft.azure.CloudException;
import com.microsoft.azure.management.appservice.DeployType;
import com.microsoft.azure.management.appservice.WebAppBase;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import rx.Completable;
import rx.Emitter;
import rx.Emitter.BackpressureMode;
import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

/**
 * A client which interacts with Kudu service.
 */
class KuduClient {
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
        service = webAppBase.manager().restClient().newBuilder()
                .withBaseUrl("https://" + host)
                .withConnectionTimeout(3, TimeUnit.MINUTES)
                .withReadTimeout(3, TimeUnit.MINUTES)
                .build()
                .retrofit().create(KuduService.class);
    }

    private interface KuduService {
        @Headers({ "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps streamApplicationLogs", "x-ms-body-logging: false" })
        @GET("api/logstream/application")
        @Streaming
        Observable<ResponseBody> streamApplicationLogs();

        @Headers({ "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps streamHttpLogs", "x-ms-body-logging: false" })
        @GET("api/logstream/http")
        @Streaming
        Observable<ResponseBody> streamHttpLogs();

        @Headers({ "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps streamTraceLogs", "x-ms-body-logging: false" })
        @GET("api/logstream/kudu/trace")
        @Streaming
        Observable<ResponseBody> streamTraceLogs();

        @Headers({ "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps streamDeploymentLogs", "x-ms-body-logging: false" })
        @GET("api/logstream/kudu/deployment")
        @Streaming
        Observable<ResponseBody> streamDeploymentLogs();

        @Headers({ "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps streamAllLogs", "x-ms-body-logging: false" })
        @GET("api/logstream")
        @Streaming
        Observable<ResponseBody> streamAllLogs();

        @Headers({ "Content-Type: application/octet-stream", "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps warDeploy", "x-ms-body-logging: false" })
        @POST("api/wardeploy")
        Observable<Void> warDeploy(@Body RequestBody warFile, @Query("name") String appName);

        @Headers({ "Content-Type: application/octet-stream", "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps zipDeploy", "x-ms-body-logging: false" })
        @POST("api/zipdeploy")
        Observable<Void> zipDeploy(@Body RequestBody zipFile);

        @Headers({ "Content-Type: application/octet-stream", "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps publish", "x-ms-body-logging: false" })
        @POST("api/publish")
        Observable<Void> deploy(@Body RequestBody file, @Query("type") DeployType type, @Query("path") String path, @Query("restart") Boolean restart, @Query("clean") Boolean clean);
    }

    Observable<String> streamApplicationLogsAsync() {
        return service.streamApplicationLogs()
                .flatMap(new Func1<ResponseBody, Observable<String>>() {
                    @Override
                    public Observable<String> call(ResponseBody responseBody) {
                        final BufferedSource source = responseBody.source();
                        return streamFromBufferedSource(source);
                    }
                });
    }

    Observable<String> streamHttpLogsAsync() {
        return service.streamHttpLogs()
                .flatMap(new Func1<ResponseBody, Observable<String>>() {
                    @Override
                    public Observable<String> call(ResponseBody responseBody) {
                        final BufferedSource source = responseBody.source();
                        return streamFromBufferedSource(source);
                    }
                });
    }

    Observable<String> streamTraceLogsAsync() {
        return service.streamTraceLogs()
                .flatMap(new Func1<ResponseBody, Observable<String>>() {
                    @Override
                    public Observable<String> call(ResponseBody responseBody) {
                        final BufferedSource source = responseBody.source();
                        return streamFromBufferedSource(source);
                    }
                });
    }

    Observable<String> streamDeploymentLogsAsync() {
        return service.streamDeploymentLogs()
                .flatMap(new Func1<ResponseBody, Observable<String>>() {
                    @Override
                    public Observable<String> call(ResponseBody responseBody) {
                        final BufferedSource source = responseBody.source();
                        return streamFromBufferedSource(source);
                    }
                });
    }

    Observable<String> streamAllLogsAsync() {
        return service.streamAllLogs()
                .flatMap(new Func1<ResponseBody, Observable<String>>() {
                    @Override
                    public Observable<String> call(ResponseBody responseBody) {
                        final BufferedSource source = responseBody.source();
                        return streamFromBufferedSource(source);
                    }
                });
    }

    private Observable<String> streamFromBufferedSource(final BufferedSource source) {
        return Observable.create(new Action1<Emitter<String>>() {
            @Override
            public void call(Emitter<String> stringEmitter) {
                try {
                    while (!source.exhausted()) {
                        stringEmitter.onNext(source.readUtf8Line());
                    }
                    stringEmitter.onCompleted();
                } catch (IOException e) {
                    stringEmitter.onError(e);
                }
            }
        }, BackpressureMode.BUFFER);
    }

    Completable warDeployAsync(InputStream warFile, String appName) {
        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), ByteStreams.toByteArray(warFile));
            return getCompletable(service.warDeploy(body, appName));
        } catch (IOException e) {
            return Completable.error(e);
        }
    }

    Completable zipDeployAsync(InputStream zipFile) {
        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), ByteStreams.toByteArray(zipFile));
            return getCompletable(service.zipDeploy(body));
        } catch (IOException e) {
            return Completable.error(e);
        }
    }

    Completable deployAsync(DeployType type, InputStream file, String path, Boolean restart, Boolean clean) {
        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), ByteStreams.toByteArray(file));
            return getCompletable(service.deploy(body, type, path, restart, clean));
        } catch (IOException e) {
            return Completable.error(e);
        }
    }

    private Completable getCompletable(Observable<Void> observable) {
        return observable
                .toCompletable()
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        return observable.zipWith(Observable.range(1, 6), new Func2<Throwable, Integer, Integer>() {
                            @Override
                            public Integer call(Throwable throwable, Integer integer) {
                                if (throwable instanceof CloudException
                                        && ((CloudException) throwable).response().code() == 502 || throwable instanceof JsonParseException || throwable instanceof SocketTimeoutException) {
                                    return integer;
                                } else {
                                    throw Exceptions.propagate(throwable);
                                }
                            }
                        }).flatMap(new Func1<Integer, Observable<?>>() {
                            @Override
                            public Observable<?> call(Integer i) {
                                return Observable.timer(i * 10, TimeUnit.SECONDS);
                            }
                        });
                    }
                });
    }
}
