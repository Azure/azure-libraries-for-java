// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.management;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenRequestContext;
import com.azure.core.http.HttpPipelineCallContext;
import com.azure.core.http.HttpPipelineNextPolicy;
import com.azure.core.http.HttpResponse;
import com.azure.core.http.policy.HttpPipelinePolicy;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Rewrite the BearerTokenAuthenticationPolicy, it will use default scope when scopes parameter is empty.
 */
public class BearerTokenAuthenticationPolicy implements HttpPipelinePolicy {
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE_FORMAT = "Bearer %s";

    private final AzureTokenCredential credential;
    private final String[] scopes;

    public BearerTokenAuthenticationPolicy(AzureTokenCredential credential, String... scopes) {
        Objects.requireNonNull(credential);
        this.credential = credential;
        this.scopes = scopes;
    }

    @Override
    public Mono<HttpResponse> process(HttpPipelineCallContext context, HttpPipelineNextPolicy next) {
        if ("http".equals(context.getHttpRequest().getUrl().getProtocol().toLowerCase())) {
            return Mono.error(new RuntimeException("token credentials require a URL using the HTTPS protocol scheme"));
        }

        Mono<AccessToken> tokenResult;
        if (this.scopes == null || this.scopes.length == 0) {
            tokenResult = this.credential.getToken(new TokenRequestContext().addScopes(scopes));
        } else {
            tokenResult = this.credential.getToken(context.getHttpRequest());
        }

        return tokenResult
                .flatMap(accessToken -> {
                    context.getHttpRequest().getHeaders().put(AUTHORIZATION_HEADER_KEY, String.format(AUTHORIZATION_HEADER_VALUE_FORMAT, accessToken.getToken()));
                    return next.process();
                });
    }
}
