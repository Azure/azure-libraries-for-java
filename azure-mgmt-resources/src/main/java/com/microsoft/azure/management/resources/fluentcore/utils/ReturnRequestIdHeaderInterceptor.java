/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.utils;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * An interceptor for requesting server return client-request-id in response headers.
 * Optionally, fill-in the client-request-id, if server does not return it in response headers.
 */
public class ReturnRequestIdHeaderInterceptor implements Interceptor {

    private static final String NAME_RETURN_CLIENT_REQUEST_ID = "x-ms-return-client-request-id";
    private static final String NAME_CLIENT_REQUEST_ID = "x-ms-client-request-id";

    private final boolean copyClientRequestIdInResponse;

    /**
     * Creates a new instance of ReturnRequestIdHeaderInterceptor.
     * Sets "x-ms-return-client-request-id: true" in requests headers.
     */
    public ReturnRequestIdHeaderInterceptor() {
        this(false);
    }

    /**
     * Creates a new instance of ReturnRequestIdHeaderInterceptor.
     * Sets "x-ms-return-client-request-id: true" in requests headers.
     * Optionally fill-in the client-request-id if server does not return it in response headers.
     *
     * @param copyClientRequestIdInResponse whether to copy client-request-id in request headers to response headers, if server does not return client-request-id.
     */
    public ReturnRequestIdHeaderInterceptor(boolean copyClientRequestIdInResponse) {
        this.copyClientRequestIdInResponse = copyClientRequestIdInResponse;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String clientRequestId = request.header(NAME_CLIENT_REQUEST_ID);

        // add header if absent
        if (request.header(NAME_RETURN_CLIENT_REQUEST_ID) == null) {
            request = chain.request().newBuilder()
                    .header(NAME_RETURN_CLIENT_REQUEST_ID, "true")
                    .build();
        }

        // send request
        Response response = chain.proceed(request);

        // optionally, add header if absent
        if (copyClientRequestIdInResponse && clientRequestId != null && response.header(NAME_CLIENT_REQUEST_ID) == null) {
            response = response.newBuilder()
                    .addHeader(NAME_CLIENT_REQUEST_ID, clientRequestId)
                    .build();
        }

        return response;
    }
}
