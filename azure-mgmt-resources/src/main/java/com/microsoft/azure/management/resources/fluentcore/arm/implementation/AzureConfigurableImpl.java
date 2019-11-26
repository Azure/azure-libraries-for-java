/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.arm.implementation;

import com.azure.core.credential.TokenCredential;
import com.azure.core.http.policy.HttpPipelinePolicy;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.annotations.AzureHost;
import com.azure.core.management.serializer.AzureJacksonAdapter;
import com.microsoft.azure.management.AzureTokenCredential;
import com.microsoft.azure.management.RestClient;
import com.microsoft.azure.management.RestClientBuilder;
import com.microsoft.azure.management.resources.fluentcore.arm.AzureConfigurable;
import com.microsoft.azure.management.resources.fluentcore.utils.ProviderRegistrationPolicy;
import com.microsoft.azure.management.resources.fluentcore.utils.ResourceManagerThrottlingPolicy;

import java.net.Proxy;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * The implementation for {@link AzureConfigurable<T>} and the base class for
 * configurable implementations.
 *
 * @param <T> the type of the configurable interface
 */
public class AzureConfigurableImpl<T extends AzureConfigurable<T>>
        implements AzureConfigurable<T> {
    protected RestClientBuilder restClientBuilder;

    protected AzureConfigurableImpl() {
        this.restClientBuilder = new RestClientBuilder()
            .withSerializerAdapter(new AzureJacksonAdapter());
            // .withResponseBuilderFactory(new AzureResponseBuilder.Factory());
    }

//    @SuppressWarnings("unchecked")
//    @Override
//    public T withLogLevel(LogLevel level) {
//        this.restClientBuilder = this.restClientBuilder.withLogLevel(level);
//        return (T) this;
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public T withInterceptor(Interceptor interceptor) {
//        this.restClientBuilder = this.restClientBuilder.withInterceptor(interceptor);
//        return (T) this;
//    }

    @SuppressWarnings("unchecked")
    @Override
    public T withUserAgent(String userAgent) {
        this.restClientBuilder = this.restClientBuilder.withUserAgent(userAgent);
        return (T) this;
    }

//    @SuppressWarnings("unchecked")
//    @Override
//    public T withReadTimeout(long timeout, TimeUnit unit) {
//        this.restClientBuilder = restClientBuilder.withReadTimeout(timeout, unit);
//        return (T) this;
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public T withConnectionTimeout(long timeout, TimeUnit unit) {
//        this.restClientBuilder = restClientBuilder.withConnectionTimeout(timeout, unit);
//        return (T) this;
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public T withMaxIdleConnections(int maxIdleConnections) {
//        this.restClientBuilder = restClientBuilder.withMaxIdleConnections(maxIdleConnections);
//        return (T) this;
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public T withConnectionPool(ConnectionPool connectionPool) {
//        this.restClientBuilder = restClientBuilder.withConnectionPool(connectionPool);
//        return (T) this;
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public T useHttpClientThreadPool(boolean useHttpClientThreadPool) {
//        this.restClientBuilder = restClientBuilder.useHttpClientThreadPool(useHttpClientThreadPool);
//        return (T) this;
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public T withDispatcher(Dispatcher dispatcher) {
//        this.restClientBuilder = restClientBuilder.withDispatcher(dispatcher);
//        return (T) this;
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public T withCallbackExecutor(Executor executor) {
//        this.restClientBuilder = restClientBuilder.withCallbackExecutor(executor);
//        return (T) this;
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public T withProxy(Proxy proxy) {
//        this.restClientBuilder = restClientBuilder.withProxy(proxy);
//        return (T) this;
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public T withProxyAuthenticator(Authenticator proxyAuthenticator) {
//        this.restClientBuilder = restClientBuilder.withProxyAuthenticator(proxyAuthenticator);
//        return (T) this;
//    }

    protected RestClient buildRestClient(AzureTokenCredential credentials, AzureEnvironment.Endpoint endpoint) {
        RestClient client =  restClientBuilder
                .withBaseUrl(credentials.environment(), endpoint)
                .withCredential(credentials)
                .addPolicy(new ProviderRegistrationPolicy(credentials))
                .addPolicy(new ResourceManagerThrottlingPolicy())
                .buildClient();
//        if (client.httpClient().proxy() != null) {
//            credentials.withProxy(client.httpClient().proxy());
//        }
        return client;
    }

    protected RestClient buildRestClient(AzureTokenCredential credentials) {
        return buildRestClient(credentials, AzureEnvironment.Endpoint.RESOURCE_MANAGER);
    }

    @Override
    public T withInterceptor(HttpPipelinePolicy interceptor) {
        return null;
    }

    @Override
    public T withReadTimeout(long timeout, TimeUnit unit) {
        return null;
    }

    @Override
    public T withConnectionTimeout(long timeout, TimeUnit unit) {
        return null;
    }

    @Override
    public T withMaxIdleConnections(int maxIdleConnections) {
        return null;
    }

    @Override
    public T useHttpClientThreadPool(boolean useHttpClientThreadPool) {
        return null;
    }

    @Override
    public T withCallbackExecutor(Executor executor) {
        return null;
    }

    @Override
    public T withProxy(Proxy proxy) {
        return null;
    }
}
