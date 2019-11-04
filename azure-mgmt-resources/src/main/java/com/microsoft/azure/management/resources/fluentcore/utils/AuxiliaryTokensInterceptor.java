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
public final class AuxiliaryTokensInterceptor implements Interceptor {

    private static final String AUTHORIZATION_AUXILIARY_HEADER = "x-ms-authorization-auxiliary";
    private static final String SCHEMA = "Bearer";

    private final AzureTokenCredentials[] tokens;

    /**
     * Initialize an auxiliary interceptor with the list of AzureTokenCredentials.
     *
     * @param tokens the AzureTokenCredentials list
     */
    public AuxiliaryTokensInterceptor(AzureTokenCredentials... tokens) {
        this.tokens = tokens;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (this.tokens == null || this.tokens.length == 0) {
            return chain.proceed(chain.request());
        }
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < tokens.length; i++) {
            buff.append(SCHEMA);
            buff.append(" ");
            buff.append(tokens[i].getToken(chain.request().url().scheme() + "://" + chain.request().url().host()));
            if (i < tokens.length - 1) {
                buff.append(";");
            }
        }
        Request request = chain.request().newBuilder()
                .header(AUTHORIZATION_AUXILIARY_HEADER, buff.toString())
                .build();
        return chain.proceed(request);
    }
}
