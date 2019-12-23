/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management;

import com.azure.core.management.serializer.AzureJacksonAdapter;
import com.azure.core.util.serializer.SerializerAdapter;

/**
 * ServiceClient is the abstraction for accessing REST operations and their payload data types.
 */
public abstract class ServiceClient {
//    /**
//     * The RestClient instance storing all information needed for making REST calls.
//     */
//    private RestClient restClient;


    /**
     * Initializes a new instance of the ServiceClient class.
     */
    protected ServiceClient(String baseUrl) {
//        this(new RestClient.Builder(clientBuilder, restBuilder)
//                .withBaseUrl(baseUrl)
//                .withResponseBuilderFactory(new ServiceResponseBuilder.Factory())
//                .withSerializerAdapter(new JacksonAdapter())
//                .build());
//        this(new RestClientBuilder()
//                .withBaseUrl(baseUrl)
//                .withSerializerAdapter(new AzureJacksonAdapter())
//                .buildClient());

    }

//    /**
//     * Initializes a new instance of the ServiceClient class.
//     *
//     * @param restClient the REST client
//     */
//    protected ServiceClient(RestClient restClient) {
//        this.restClient = restClient;
//    }
//
//    /**
//     * @return the {@link RestClient} instance.
//     */
//    public RestClient restClient() {
//        return restClient;
//    }
//
//
//    /**
//     * @return the adapter to a Jackson {@link com.fasterxml.jackson.databind.ObjectMapper}.
//     */
//    public SerializerAdapter getSerializerAdapter() {
//        return this.restClient.getSerializerAdapter();
//    }
}
