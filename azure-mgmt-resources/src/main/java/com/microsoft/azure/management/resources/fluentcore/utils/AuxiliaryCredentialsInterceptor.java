/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.utils;

import com.microsoft.azure.credentials.AzureTokenCredentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * An interceptor for cross-tenant authorization in Azure.
 */
public final class AuxiliaryCredentialsInterceptor implements Interceptor {

    private static final String AUTHORIZATION_AUXILIARY_HEADER = "x-ms-authorization-auxiliary";
    private static final String LINKED_AUTHORIZATION_FAILED = "LinkedAuthorizationFailed";
    private static final String SCHEMA = "Bearer";

    private final AzureTokenCredentials[] tokenCredentials;

    /**
     * Initialize an auxiliary interceptor with the list of AzureTokenCredentials.
     *
     * @param credentials the AzureTokenCredentials list
     */
    public AuxiliaryCredentialsInterceptor(AzureTokenCredentials... credentials) {
        this.tokenCredentials = credentials;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (tokenCredentials != null && tokenCredentials.length > 0) {
            StringBuffer buff = new StringBuffer();
            for (int i = 0; i < tokenCredentials.length; i++) {
                buff.append(SCHEMA);
                buff.append(" ");
                buff.append(tokenCredentials[i].getToken(chain.request().url().scheme() + "://" + chain.request().url().host()));
                if (i < tokenCredentials.length - 1) {
                    buff.append(",");
                }
            }
            Request request = chain.request().newBuilder()
                    .header(AUTHORIZATION_AUXILIARY_HEADER, buff.toString())
                    .build();
            // Retry
            return chain.proceed(request);
        }
        return chain.proceed(chain.request());
    }
}
