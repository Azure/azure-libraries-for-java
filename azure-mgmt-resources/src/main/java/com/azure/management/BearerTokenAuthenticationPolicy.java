// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.management;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.azure.core.http.HttpPipelineCallContext;
import com.azure.core.http.HttpPipelineNextPolicy;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.HttpResponse;
import com.azure.core.http.policy.HttpPipelinePolicy;
import com.azure.core.management.AzureEnvironment;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Rewrite the BearerTokenAuthenticationPolicy, it will use default scope when scopes parameter is empty.
 */
public class BearerTokenAuthenticationPolicy implements HttpPipelinePolicy {
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE_FORMAT = "Bearer %s";

    private final Map<String, AccessToken> tokenCache;
    private final TokenCredential credential;
    private final String[] scopes;
    private final AzureEnvironment environment;

    public BearerTokenAuthenticationPolicy(TokenCredential credential, String... scopes) {
        Objects.requireNonNull(credential);
        this.credential = credential;
        this.scopes = scopes;
        this.tokenCache = new HashMap<>();

        if (credential instanceof AzureTokenCredential) {
            this.environment = ((AzureTokenCredential) credential).getEnvironment();
        } else {
            this.environment = AzureEnvironment.AZURE;
        }
    }

    private String getDefaultScopeFromRequest(HttpRequest request) {
        String host = request.getUrl().getHost();
        String resource = this.environment.getManagementEndpoint();
        for (Map.Entry<String, String> endpoint : this.environment.endpoints().entrySet()) {
            if (host.contains(endpoint.getValue())) {
                if (endpoint.getKey().equals(AzureEnvironment.Endpoint.KEYVAULT.identifier())) {
                    resource = String.format("https://%s/", endpoint.getValue().replaceAll("^\\.*", ""));
                    break;
                } else if (endpoint.getKey().equals(AzureEnvironment.Endpoint.GRAPH.identifier())) {
                    resource = this.environment.getGraphEndpoint();
                    break;
                } else if (endpoint.getKey().equals(AzureEnvironment.Endpoint.LOG_ANALYTICS.identifier())) {
                    resource = this.environment.getLogAnalyticsEndpoint();
                    break;
                } else if (endpoint.getKey().equals(AzureEnvironment.Endpoint.APPLICATION_INSIGHTS.identifier())) {
                    resource = this.environment.getApplicationInsightsEndpoint();
                    break;
                } else if (endpoint.getKey().equals(AzureEnvironment.Endpoint.DATA_LAKE_STORE.identifier())
                        || endpoint.getKey().equals(AzureEnvironment.Endpoint.DATA_LAKE_ANALYTICS.identifier())) {
                    resource = this.environment.getDataLakeEndpointResourceId();
                    break;
                }
            }
        }
        return resource + "/.default";
    }

    @Override
    public Mono<HttpResponse> process(HttpPipelineCallContext context, HttpPipelineNextPolicy next) {
        if ("http".equals(context.getHttpRequest().getUrl().getProtocol().toLowerCase())) {
            return Mono.error(new RuntimeException("token credentials require a URL using the HTTPS protocol scheme"));
        }

        String[] scopes;
        if (this.scopes == null || this.scopes.length == 0) {
            scopes = new String[] {getDefaultScopeFromRequest(context.getHttpRequest())};
        } else {
            scopes = this.scopes;
        }
        assert scopes.length > 0;

        Mono<AccessToken> tokenResult;
        AccessToken token = tokenCache.get(scopes[0]);
        if (token == null || token.isExpired()) {
            tokenResult = this.credential.getToken(new TokenRequestContext().addScopes(scopes))
                            .doOnNext(accessToken -> this.tokenCache.put(scopes[0], accessToken));
        } else {
            tokenResult = Mono.just(token);
        }

        return tokenResult
                .flatMap(accessToken -> {
                    context.getHttpRequest().getHeaders().put(AUTHORIZATION_HEADER_KEY, String.format(AUTHORIZATION_HEADER_VALUE_FORMAT, accessToken.getToken()));
                    return next.process();
                });
    }
}
