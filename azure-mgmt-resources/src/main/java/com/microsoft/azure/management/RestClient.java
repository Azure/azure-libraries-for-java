package com.microsoft.azure.management;

import com.azure.core.http.HttpPipeline;
import com.azure.core.util.serializer.SerializerAdapter;

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
}
