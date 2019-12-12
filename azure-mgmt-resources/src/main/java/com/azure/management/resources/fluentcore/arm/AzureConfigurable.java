/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.fluentcore.arm;


import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpPipelinePolicy;
import com.azure.management.AzureTokenCredential;

import java.net.Proxy;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * The base interface for allowing configurations to be made on the HTTP client.
 *
 * @param <T> the actual type of the interface extending this interface
 */
public interface AzureConfigurable<T extends AzureConfigurable<T>> {

    /**
     * Set the logging level on the HTTP client.
     *
     * @param level the OkHttp logging level
     * @return the configurable object itself
     */
    T withLogLevel(HttpLogDetailLevel level);

    /**
     * Plug in an interceptor into the HTTP pipeline.
     *
     * @param policy the policy to plug in
     * @return the configurable object itself
     */
    T withPolicy(HttpPipelinePolicy policy);

    /**
     * Set the cross-tenant auxiliary credentials for Azure which can hold up to three.
     *
     * @param tokens the AzureTokenCredentials list
     * @return the configurable object itself
     */
    T withAuxiliaryCredentials(AzureTokenCredential... tokens);

    /**
     * Specify the user agent header.
     *
     * @param userAgent the user agent to use
     * @return the configurable object itself
     */
    T withUserAgent(String userAgent);

    /**
     * Set the read timeout on the HTTP client. Default is 10 seconds.
     *
     * @param timeout the timeout numeric value
     * @param unit    the time unit for the numeric value
     * @return the configurable object itself for chaining
     */
    T withReadTimeout(long timeout, TimeUnit unit);

    /**
     * Set the connection timeout on the HTTP client. Default is 10 seconds.
     *
     * @param timeout the timeout numeric value
     * @param unit    the time unit for the numeric value
     * @return the configurable object itself for chaining
     */
    T withConnectionTimeout(long timeout, TimeUnit unit);

    /**
     * Sets whether to use the thread pool in OkHttp/Netty client or RxJava schedulers.
     * If set to true, the thread pool in OkHttp client will be used. Default is false.
     *
     * @param useHttpClientThreadPool whether to use the thread pool in Okhttp client. Default is false.
     * @return the configurable object itself for chaining
     */
    T useHttpClientThreadPool(boolean useHttpClientThreadPool);

    /**
     * Sets the executor for async callbacks to run on.
     *
     * @param executor the executor to execute the callbacks.
     * @return the configurable object itself for chaining
     */
    T withCallbackExecutor(Executor executor);

    /**
     * Sets the proxy for the HTTP client.
     *
     * @param proxy the proxy to use
     * @return the configurable object itself for chaining
     */
    T withProxy(Proxy proxy);
}