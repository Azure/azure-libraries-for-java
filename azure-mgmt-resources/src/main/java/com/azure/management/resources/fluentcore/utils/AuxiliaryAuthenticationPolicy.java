/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.fluentcore.utils;

import com.azure.core.http.HttpPipelineCallContext;
import com.azure.core.http.HttpPipelineNextPolicy;
import com.azure.core.http.HttpResponse;
import com.azure.core.http.policy.HttpPipelinePolicy;
import com.azure.core.management.CloudError;
import com.azure.core.management.serializer.AzureJacksonAdapter;
import com.azure.core.util.serializer.SerializerEncoding;
import com.azure.management.AzureTokenCredential;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * A Http Pipeline Policy for cross-tenant authorization in Azure.
 */
public class AuxiliaryAuthenticationPolicy implements HttpPipelinePolicy {

    private static final String AUTHORIZATION_AUXILIARY_HEADER = "x-ms-authorization-auxiliary";
    private static final String LINKED_AUTHORIZATION_FAILED = "LinkedAuthorizationFailed";
    private static final String SCHEMA = "Bearer";

    private final AzureTokenCredential[] tokenCredentials;

    public AuxiliaryAuthenticationPolicy(AzureTokenCredential... credentials) {
        this.tokenCredentials = credentials;
    }

    private boolean responseSuccessful(HttpResponse response) {
        return response.getStatusCode() >= 200 && response.getStatusCode() < 300;
    }

    @Override
    public Mono<HttpResponse> process(HttpPipelineCallContext context, HttpPipelineNextPolicy next) {
        return next.process().flatMap(
            response -> {
                if (responseSuccessful(response) && this.tokenCredentials != null && this.tokenCredentials.length > 0) {
                    HttpResponse bufferedResponse = response.buffer();
                    return bufferedResponse.getBody().flatMap(
                        byteBuffer -> {
                            byte[] body = new byte[byteBuffer.remaining()];
                            byteBuffer.get(body);
                            String bodyStr = new String(body, StandardCharsets.UTF_8);

                            AzureJacksonAdapter jacksonAdapter = new AzureJacksonAdapter();
                            CloudError cloudError;
                            try {
                                cloudError = jacksonAdapter.deserialize(bodyStr, CloudError.class, SerializerEncoding.JSON);
                            } catch (IOException e) {
                                return Mono.just(bufferedResponse);
                            }

                            if (cloudError != null && LINKED_AUTHORIZATION_FAILED.equals(cloudError.getCode()) &&
                                context.getHttpRequest().getHeaders().getValue(AUTHORIZATION_AUXILIARY_HEADER) == null) {
                                StringBuffer authorization = new StringBuffer();
                                for (int i = 0; i < tokenCredentials.length; i++) {
                                    if (i > 0) {
                                        authorization.append(";");
                                    }
                                    authorization.append(SCHEMA);
                                    authorization.append(" ");
                                    authorization.append(tokenCredentials[i].getToken(context.getHttpRequest()));
                                }
                                context.getHttpRequest().setHeader(AUTHORIZATION_AUXILIARY_HEADER, authorization.toString());

                                // Retry
                                return next.process();
                            }

                            return Mono.just(bufferedResponse);
                        }
                    ).last();
                }
                return Mono.just(response);
            }
        );
    }
}
