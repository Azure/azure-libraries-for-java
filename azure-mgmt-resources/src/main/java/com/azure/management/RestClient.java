package com.azure.management;

import com.azure.core.credential.TokenCredential;
import com.azure.core.http.HttpPipeline;
import com.azure.core.util.serializer.SerializerAdapter;

import java.net.URL;

public class RestClient {

    private final RestAsyncClient client;

    public RestClient(RestAsyncClient client) {
        this.client = client;
    }

    /**
     * @return the current serializer adapter.
     */
    public SerializerAdapter getSerializerAdapter() {
        return this.client.getSerializerAdapter();
    }

    public HttpPipeline getHttpPipeline() {
        return this.client.getHttpPipeline();
    }

    /**
     * @return the credentials attached to this REST client
     */
    public TokenCredential getCredential() {
        return this.client.getCredential();
    }

    public URL getBaseUrl() {
        return this.client.getBaseUrl();
    }
}
