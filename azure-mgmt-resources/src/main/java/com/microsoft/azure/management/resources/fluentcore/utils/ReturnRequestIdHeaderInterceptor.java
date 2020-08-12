/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.utils;

import com.microsoft.rest.RestClient;
import com.microsoft.rest.interceptors.RequestIdHeaderInterceptor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * An interceptor for requesting server return client-request-id in response headers.
 * Optionally, fill-in the client-request-id, if server does not return it in response headers.
 * <p>
 * ReturnRequestIdHeaderInterceptor should be added after {@link RequestIdHeaderInterceptor}.
 * By default, {@link RequestIdHeaderInterceptor} is added as first interceptor by {@link RestClient}.
 *
 * @see RequestIdHeaderInterceptor
 */
public class ReturnRequestIdHeaderInterceptor implements Interceptor {

    private static final String NAME_RETURN_CLIENT_REQUEST_ID = "x-ms-return-client-request-id";
    private static final String NAME_CLIENT_REQUEST_ID = "x-ms-client-request-id";

    private final Option option;

    /**
     * Additional client handling, if server does not return client-request-id in response headers.
     */
    public enum Option {
        /**
         * Default.
         */
        NONE,

        /**
         * Fill-in the client-request-id from request headers.
         */
        COPY_CLIENT_REQUEST_ID
    }

    /**
     * Creates a new instance of ReturnRequestIdHeaderInterceptor.
     * Sets "x-ms-return-client-request-id: true" in requests headers.
     */
    public ReturnRequestIdHeaderInterceptor() {
        this(Option.NONE);
    }

    /**
     * Creates a new instance of ReturnRequestIdHeaderInterceptor.
     * Sets "x-ms-return-client-request-id: true" in requests headers.
     * <p>
     * Optionally fill-in the client-request-id if server does not return it in response headers.
     *
     * @param option the option of additional client handling, if server does not return client-request-id in response headers.
     */
    public ReturnRequestIdHeaderInterceptor(Option option) {
        this.option = option;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        final String clientRequestId = request.header(NAME_CLIENT_REQUEST_ID);

        // add header if absent
        if (request.header(NAME_RETURN_CLIENT_REQUEST_ID) == null) {
            request = chain.request().newBuilder()
                    .header(NAME_RETURN_CLIENT_REQUEST_ID, "true")
                    .build();
        }

        // send request
        Response response = chain.proceed(request);

        // optionally, copy header from request, if absent in response
        if (option == Option.COPY_CLIENT_REQUEST_ID
                && clientRequestId != null && response.header(NAME_CLIENT_REQUEST_ID) == null) {
            response = response.newBuilder()
                    .addHeader(NAME_CLIENT_REQUEST_ID, clientRequestId)
                    .build();
        }

        return response;
    }
}
