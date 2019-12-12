/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.azure.core.management.AzureEnvironment;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.Proxy;
import java.util.Map;

/**
 * AzureTokenCredential represents a credentials object with access to Azure Resource management.
 */
public abstract class AzureTokenCredential implements TokenCredential {

    private final AzureEnvironment environment;

    private final String domain;

    private String defaultSubscription;

    private Proxy proxy;

    private SSLSocketFactory sslSocketFactory;

    /**
     * Initializes a new instance of the AzureTokenCredentials.
     *
     * @param environment the Azure environment to use
     * @param domain      the tenant or domain the credential is authorized to
     */
    public AzureTokenCredential(AzureEnvironment environment, String domain) {
        //  super("Bearer", null);
        this.environment = (environment == null) ? AzureEnvironment.AZURE : environment;
        this.domain = domain;
    }

    /**
     * Asynchronously get a token for a given resource/audience.
     *
     * @param request the details of the token request
     * @return a Publisher that emits a single access token
     */
    @Override
    public final Mono<AccessToken> getToken(TokenRequestContext request) {
        String host = request.toString().toLowerCase();
        String resource = environment().getManagementEndpoint();
        for (Map.Entry<String, String> endpoint : environment().endpoints().entrySet()) {
            if (host.contains(endpoint.getValue())) {
                if (endpoint.getKey().equals(AzureEnvironment.Endpoint.KEYVAULT.identifier())) {
                    resource = String.format("https://%s/", endpoint.getValue().replaceAll("^\\.*", ""));
                    break;
                } else if (endpoint.getKey().equals(AzureEnvironment.Endpoint.GRAPH.identifier())) {
                    resource = environment().getGraphEndpoint();
                    break;
                } else if (endpoint.getKey().equals(AzureEnvironment.Endpoint.LOG_ANALYTICS.identifier())) {
                    resource = environment().getLogAnalyticsEndpoint();
                    break;
                } else if (endpoint.getKey().equals(AzureEnvironment.Endpoint.APPLICATION_INSIGHTS.identifier())) {
                    resource = environment().getApplicationInsightsEndpoint();
                    break;
                } else if (endpoint.getKey().equals(AzureEnvironment.Endpoint.DATA_LAKE_STORE.identifier())
                        || endpoint.getKey().equals(AzureEnvironment.Endpoint.DATA_LAKE_ANALYTICS.identifier())) {
                    resource = environment().getDataLakeEndpointResourceId();
                    break;
                }
            }
        }
        return getToken(resource);
    }

    /**
     * Override this method to provide the mechanism to get a token.
     *
     * @param resource the resource the access token is for
     * @return the token to access the resource
     * @throws IOException exceptions from IO
     */
    public abstract Mono<AccessToken> getToken(String resource);

    /**
     * Override this method to provide the domain or tenant ID the token is valid in.
     *
     * @return the domain or tenant ID string
     */
    public String domain() {
        return domain;
    }

    /**
     * @return the environment details the credential has access to.
     */
    public AzureEnvironment environment() {
        return environment;
    }

    /**
     * @return The default subscription ID, if any
     */
    public String defaultSubscriptionId() {
        return defaultSubscription;
    }

    /**
     * Set default subscription ID.
     *
     * @param subscriptionId the default subscription ID.
     * @return the credentials object itself.
     */
    public AzureTokenCredential withDefaultSubscriptionId(String subscriptionId) {
        this.defaultSubscription = subscriptionId;
        return this;
    }

    /**
     * @return the proxy being used for accessing Active Directory.
     */
    public Proxy proxy() {
        return proxy;
    }

    /**
     * @return the ssl socket factory.
     */
    public SSLSocketFactory sslSocketFactory() {
        return sslSocketFactory;
    }

    /**
     * @param proxy the proxy being used for accessing Active Directory
     * @return the credential itself
     */
    public AzureTokenCredential withProxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    /**
     * @param sslSocketFactory the ssl socket factory
     * @return the credential itself
     */
    public AzureTokenCredential withSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
        return this;
    }
}
