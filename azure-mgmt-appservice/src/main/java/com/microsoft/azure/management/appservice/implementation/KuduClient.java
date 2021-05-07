/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;
import com.google.common.io.ByteStreams;
import com.google.common.reflect.TypeToken;
import com.microsoft.azure.AzureResponseBuilder;
import com.microsoft.azure.management.appservice.AsyncDeploymentResult;
import com.microsoft.azure.management.appservice.DeployType;
import com.microsoft.azure.management.appservice.WebAppBase;
import com.microsoft.rest.RestClient;
import com.microsoft.rest.RestException;
import com.microsoft.rest.ServiceResponse;
import com.microsoft.rest.ServiceResponseBuilder;
import com.microsoft.rest.protocol.ResponseBuilder;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
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
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * A client which interacts with Kudu service.
 */
class KuduClient {
    private final RestClient restClient;
    private final KuduService service;

    KuduClient(WebAppBase webAppBase) {
        if (webAppBase.defaultHostName() == null) {
            throw new UnsupportedOperationException("Cannot initialize kudu client before web app is created");
        }
        String host = webAppBase.defaultHostName().toLowerCase()
                .replace("http://", "")
                .replace("https://", "");
        String[] parts = host.split("\\.", 2);
        host = Joiner.on('.').join(parts[0], "scm", parts[1]);
        restClient = webAppBase.manager().restClient().newBuilder()
                .withBaseUrl("https://" + host)
                .withConnectionTimeout(3, TimeUnit.MINUTES)
                .withReadTimeout(3, TimeUnit.MINUTES)
                .build();
        service = restClient
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
        Observable<Response<ResponseBody>> warDeploy(@Body RequestBody warFile, @Query("name") String appName);

        @Headers({ "Content-Type: application/octet-stream", "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps zipDeploy", "x-ms-body-logging: false" })
        @POST("api/zipdeploy")
        Observable<Response<ResponseBody>> zipDeploy(@Body RequestBody zipFile, @Query("isAsync") Boolean isAsync, @Query("trackDeploymentProgress") Boolean trackDeploymentProgress);

        @Headers({ "Content-Type: application/octet-stream", "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps publish", "x-ms-body-logging: false" })
        @POST("api/publish")
        Observable<Response<ResponseBody>> deploy(@Body RequestBody file, @Query("type") DeployType type, @Query("path") String path, @Query("restart") Boolean restart, @Query("clean") Boolean clean, @Query("isAsync") Boolean isAsync, @Query("trackDeploymentProgress") Boolean trackDeploymentProgress);

        @Headers({ "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps settings" })
        @GET("api/settings")
        Observable<Response<ResponseBody>> settings();

        @Headers({ "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps getDeployment" })
        @GET
        Observable<Response<ResponseBody>> getDeployment(@Url String deploymentUrl);
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
            Observable<ServiceResponse<Void>> response =
                    retryOnError(handleResponse(service.warDeploy(body, appName)));
            return response.toCompletable();
        } catch (IOException e) {
            return Completable.error(e);
        }
    }

    Completable zipDeployAsync(InputStream zipFile) {
        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), ByteStreams.toByteArray(zipFile));
            Observable<ServiceResponse<Void>> response =
                    retryOnError(handleResponse(service.zipDeploy(body, false, false)));
            return response.toCompletable();
        } catch (IOException e) {
            return Completable.error(e);
        }
    }

    private static class DeploymentIntermediateResult {
        private String deploymentUrl;
        private String deploymentId;
    }

    private static class DeploymentResponse {
        @JsonProperty(value = "id")
        private String id;

        @JsonProperty(value = "is_temp")
        private Boolean isTemp;
    }

    Observable<AsyncDeploymentResult> pushZipDeployAsync(InputStream zipFile) {
//        final long pollIntervalInSeconds = 5;
//        final long pollCount = 3 * 60 / pollIntervalInSeconds;  // 3 minutes

        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), ByteStreams.toByteArray(zipFile));

            // service returns 404 on deploymentStatus, if deployment ID is get from SCM-DEPLOYMENT-ID
            Observable<AsyncDeploymentResult> result =
                    retryOnError(handleResponse(service.zipDeploy(body, true, true), new Func1<Response<ResponseBody>, AsyncDeploymentResult>() {
                        @Override
                        public AsyncDeploymentResult call(Response<ResponseBody> responseBodyResponse) {
                            String deploymentId = responseBodyResponse.headers().get("SCM-DEPLOYMENT-ID");
                            if (deploymentId == null || deploymentId.isEmpty()) {
                                // error if deployment ID not available
                                throw new RestException("Deployment ID not found in response", responseBodyResponse);
                            }
                            return new AsyncDeploymentResult(deploymentId);
                        }
                    })).map(new Func1<ServiceResponse<AsyncDeploymentResult>, AsyncDeploymentResult>() {
                        @Override
                        public AsyncDeploymentResult call(ServiceResponse<AsyncDeploymentResult> result) {
                            return result.body();
                        }
                    });

//            Observable<ServiceResponse<DeploymentIntermediateResult>> responseDeployment =
//                    retryOnError(handleResponse(service.zipDeploy(body, true), new Func1<Response<ResponseBody>, DeploymentIntermediateResult>() {
//                        @Override
//                        public DeploymentIntermediateResult call(Response<ResponseBody> responseBodyResponse) {
//                            DeploymentIntermediateResult result = new DeploymentIntermediateResult();
//                            result.deploymentUrl = responseBodyResponse.headers().get("Location");
//                            return result;
//                        }
//                    }));
//
//            Observable<AsyncDeploymentResult> result = responseDeployment.flatMap(new Func1<ServiceResponse<DeploymentIntermediateResult>, Observable<AsyncDeploymentResult>>() {
//                @Override
//                public Observable<AsyncDeploymentResult> call(ServiceResponse<DeploymentIntermediateResult> responseDeployment) {
//                    DeploymentIntermediateResult result = responseDeployment.body();
//                    if (result.deploymentUrl == null || result.deploymentUrl.isEmpty()) {
//                        // error if URL not available
//                        return Observable.error(new RestException("Deployment URL not found in response", responseDeployment.response()));
//                    } else {
//                        // delay for poll
//                        Observable<DeploymentIntermediateResult> delayedResult = Observable.just(result).delaySubscription(pollIntervalInSeconds, TimeUnit.SECONDS);
//                        return delayedResult.flatMap(new Func1<DeploymentIntermediateResult, Observable<ServiceResponse<DeploymentIntermediateResult>>>() {
//                            @Override
//                            public Observable<ServiceResponse<DeploymentIntermediateResult>> call(DeploymentIntermediateResult deployment) {
//                                return handleResponse(service.getDeployment(deployment.deploymentUrl), new Func1<Response<ResponseBody>, DeploymentIntermediateResult>() {
//                                    @Override
//                                    public DeploymentIntermediateResult call(Response<ResponseBody> response) {
//                                        DeploymentIntermediateResult result = new DeploymentIntermediateResult();
//                                        try {
//                                            DeploymentResponse deployment = restClient.serializerAdapter().deserialize(response.body().string(), DeploymentResponse.class);
//                                            if (deployment.id != null && !deployment.id.isEmpty() && deployment.isTemp != null && !deployment.isTemp) {
//                                                result.deploymentId = deployment.id;
//                                            }
//                                        } catch (IOException e) {
//                                            //
//                                        }
//                                        return result;
//                                    }
//                                });
//                            }
//                        }).repeat(pollCount).takeUntil(new Func1<ServiceResponse<DeploymentIntermediateResult>, Boolean>() {
//                            @Override
//                            public Boolean call(ServiceResponse<DeploymentIntermediateResult> response) {
//                                return response.body().deploymentId != null;
//                            }
//                        }).last().flatMap(new Func1<ServiceResponse<DeploymentIntermediateResult>, Observable<AsyncDeploymentResult>>() {
//                            @Override
//                            public Observable<AsyncDeploymentResult> call(ServiceResponse<DeploymentIntermediateResult> response) {
//                                DeploymentIntermediateResult result = response.body();
//                                if (result.deploymentId != null) {
//                                    return Observable.just(new AsyncDeploymentResult(result.deploymentId));
//                                } else {
//                                    // error if ID not available
//                                    return Observable.error(new RestException("Timeout while polling deployment ID", response.response()));
//                                }
//                            }
//                        });
//                    }
//                }
//            });

            return result;
        } catch (IOException e) {
            return Observable.error(e);
        }
    }

    Completable deployAsync(DeployType type, InputStream file, String path, Boolean restart, Boolean clean) {
        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), ByteStreams.toByteArray(file));
            Observable<ServiceResponse<Void>> response =
                    retryOnError(handleResponse(service.deploy(body, type, path, restart, clean, false, false)));
            return response.toCompletable();
        } catch (IOException e) {
            return Completable.error(e);
        }
    }

    Observable<AsyncDeploymentResult> pushDeployAsync(DeployType type, InputStream file, String path, Boolean restart, Boolean clean) {
        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), ByteStreams.toByteArray(file));

            // service returns 404 on deploymentStatus, if deployment ID is get from SCM-DEPLOYMENT-ID
            Observable<AsyncDeploymentResult> result =
                    retryOnError(handleResponse(service.deploy(body, type, path, restart, clean, true, true), new Func1<Response<ResponseBody>, AsyncDeploymentResult>() {
                        @Override
                        public AsyncDeploymentResult call(Response<ResponseBody> responseBodyResponse) {
                            String deploymentId = responseBodyResponse.headers().get("SCM-DEPLOYMENT-ID");
                            if (deploymentId == null || deploymentId.isEmpty()) {
                                // error if deployment ID not available
                                throw new RestException("Deployment ID not found in response", responseBodyResponse);
                            }
                            return new AsyncDeploymentResult(deploymentId);
                        }
                    })).map(new Func1<ServiceResponse<AsyncDeploymentResult>, AsyncDeploymentResult>() {
                        @Override
                        public AsyncDeploymentResult call(ServiceResponse<AsyncDeploymentResult> result) {
                            return result.body();
                        }
                    });
            return result;
        } catch (IOException e) {
            return Observable.error(e);
        }
    }

    Observable<Map<String, String>> settings() {
        return retryOnError(service.settings().flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Map<String, String>>>>() {
            @Override
            public Observable<ServiceResponse<Map<String, String>>> call(Response<ResponseBody> response) {
                try {
                    ResponseBuilder<Map<String, String>, RestException> responseBuilder = restClient
                            .responseBuilderFactory().newInstance(restClient.serializerAdapter());
                    // set throwOnGet404 to true
                    if (responseBuilder instanceof AzureResponseBuilder) {
                        ((AzureResponseBuilder<Map<String, String>, RestException>) responseBuilder).withThrowOnGet404(true);
                    } else if (responseBuilder instanceof ServiceResponseBuilder) {
                        ((ServiceResponseBuilder<Map<String, String>, RestException>) responseBuilder).withThrowOnGet404(true);
                    }

                    ServiceResponse<Map<String, String>> clientResponse = responseBuilder
                            .register(200, new TypeToken<Map<String, String>>() { }.getType())
                            .registerError(RestException.class)
                            .build(response);
                    return Observable.just(clientResponse);
                } catch (Throwable t) {
                    return Observable.error(t);
                }
            }
        }).map(new Func1<ServiceResponse<Map<String, String>>, Map<String, String>>() {
            @Override
            public Map<String, String> call(ServiceResponse<Map<String, String>> response) {
                return response.body();
            }
        }));
    }

    private Observable<ServiceResponse<Void>> handleResponse(Observable<Response<ResponseBody>> observable) {
        return this.handleResponse(observable, new Func1<Response<ResponseBody>, Void>() {
            @Override
            public Void call(Response<ResponseBody> responseBodyResponse) {
                return null;
            }
        });
    }

    private <T> Observable<ServiceResponse<T>> handleResponse(Observable<Response<ResponseBody>> observable, final Func1<Response<ResponseBody>, T> responseFunc) {
        return observable.flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<T>>>() {
            @Override
            public Observable<ServiceResponse<T>> call(Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        return Observable.just(new ServiceResponse<T>(responseFunc.call(response), response));
                    } else {
                        String errorMessage = "Status code " + response.code();
                        if (response.errorBody() != null
                                && response.errorBody().contentType() != null
                                && Objects.equals("text", response.errorBody().contentType().type())) {
                            String errorBody = response.errorBody().string();
                            if (!errorBody.isEmpty()) {
                                errorMessage = errorBody;
                            }
                        }
                        return Observable.error(new RestException(errorMessage, response));
                    }
                } catch (Throwable t) {
                    return Observable.error(t);
                }
            }
        });
    }

    private <T> Observable<T> retryOnError(Observable<T> observable) {
        final int retryCount = 5 + 1;   // retryCount is 5, last 1 is guard
        return observable.retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.zipWith(Observable.range(1, retryCount), new Func2<Throwable, Integer, Integer>() {
                    @Override
                    public Integer call(Throwable throwable, Integer integer) {
                        if (integer < retryCount
                                && (throwable instanceof SocketTimeoutException
                                || (throwable instanceof RestException
                                && ((RestException) throwable).response().code() == 502))) {
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
