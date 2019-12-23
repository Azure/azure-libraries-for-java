/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management;

import com.azure.core.credential.TokenCredential;
import com.azure.core.http.HttpPipeline;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.serializer.AzureJacksonAdapter;
//import com.google.common.hash.Hashing;

import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * ServiceClient is the abstraction for accessing REST operations and their payload data types.
 */
public abstract class AzureServiceClient extends ServiceClient {
//    /**
//     * Initializes a new instance of the ServiceClient class.
//     *
//     * @param baseUrl     the service base uri
//     * @param credentials the credentials
//     */
//    protected AzureServiceClient(String baseUrl, TokenCredential credentials) {
//        this(new RestClientBuilder()
//                .withBaseUrl(baseUrl)
//                .withCredential(credentials)
//                .withSerializerAdapter(new AzureJacksonAdapter())
//                .buildClient());
//    }
//
//    /**
//     * Initializes a new instance of the ServiceClient class.
//     *
//     * @param restClient the REST client
//     */
//    protected AzureServiceClient(RestClient restClient) {
//        super(restClient);
//    }

    protected AzureServiceClient(HttpPipeline httpPipeline, AzureEnvironment environment) {
        super(null);
    }

    /**
     * The default User-Agent header. Override this method to override the user agent.
     *
     * @return the user agent string.
     */
    public String userAgent() {
        return String.format("Azure-SDK-For-Java/%s OS:%s MacAddressHash:%s Java:%s",
                getClass().getPackage().getImplementationVersion(),
                OS,
                MAC_ADDRESS_HASH,
                JAVA_VERSION);
    }

    private static final String MAC_ADDRESS_HASH;
    private static final String OS;
    private static final String JAVA_VERSION;

    static {
        OS = System.getProperty("os.name") + "/" + System.getProperty("os.version");
        String macAddress = "Unknown";
//        try {
//            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
//            while (networks.hasMoreElements()) {
//                NetworkInterface network = networks.nextElement();
//                byte[] mac = network.getHardwareAddress();
//
//                if (mac != null) {
//                    macAddress = Hashing.sha256().hashBytes(mac).toString();
//                    break;
//                }
//            }
//        } catch (Throwable t) {
//            // It's okay ignore mac address hash telemetry
//        }
        MAC_ADDRESS_HASH = macAddress;
        String version = System.getProperty("java.version");
        JAVA_VERSION = version != null ? version : "Unknown";
    }

    public void getPutOrPatchResultAsync() {

    }
}
