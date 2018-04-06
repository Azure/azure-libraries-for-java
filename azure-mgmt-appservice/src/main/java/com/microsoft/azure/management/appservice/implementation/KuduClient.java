/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.google.common.io.ByteStreams;
import com.microsoft.azure.CloudException;
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
import java.util.concurrent.TimeUnit;

/**
 * A client which interacts with Kudu service.
 */
class KuduClient {
    private KuduService service;

    KuduClient(WebAppBase webAppBase) {
        service = webAppBase.manager().restClient().newBuilder()
                .withBaseUrl("https://" + webAppBase.defaultHostName()
                        .replace("http://", "")
                        .replace(webAppBase.name(), webAppBase.name() + ".scm"))
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

        @Headers({ "Content-Type: application/octet-stream", "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps warDeploy", "x-ms-body-logging: false" })
        @POST("api/wardeploy")
        @Streaming
        Observable<Void> warDeploy(@Body RequestBody warFile, @Query("name") String appName);
    }

    Observable<String> streamApplicationLogsAsync() {
        return service.streamApplicationLogs()
                .flatMap(new Func1<ResponseBody, Observable<String>>() {
                    @Override
                    public Observable<String> call(ResponseBody responseBody) {
                        final BufferedSource source = responseBody.source();
                        return Observable.fromEmitter(new Action1<Emitter<String>>() {
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
                });
    }

    Completable warDeployAsync(InputStream warFile, String appName) {
        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), ByteStreams.toByteArray(warFile));
            return service.warDeploy(body, appName)
                    .toCompletable()
                    .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                        @Override
                        public Observable<?> call(Observable<? extends Throwable> observable) {
                            return observable.zipWith(Observable.range(1, 30), new Func2<Throwable, Integer, Integer>() {
                                @Override
                                public Integer call(Throwable throwable, Integer integer) {
                                    if (throwable instanceof CloudException
                                            && ((CloudException) throwable).response().code() == 502) {
                                        return integer;
                                    } else {
                                        throw Exceptions.propagate(throwable);
                                    }
                                }
                            }).flatMap(new Func1<Integer, Observable<?>>() {
                                @Override
                                public Observable<?> call(Integer i) {
                                    return Observable.timer(i, TimeUnit.SECONDS);
                                }
                            });
                        }
                    });
        } catch (IOException e) {
            return Completable.error(e);
        }
    }
}
