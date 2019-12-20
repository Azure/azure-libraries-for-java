//package com.microsoft.azure.management;
//
//import com.azure.core.http.HttpPipeline;
//import com.azure.core.http.rest.RestProxy;
//import com.azure.core.management.AzureEnvironment;
//import com.azure.core.management.annotations.AzureHost;
//import com.azure.core.management.serializer.AzureJacksonAdapter;
//import com.azure.core.util.serializer.SerializerAdapter;
//
//import java.lang.reflect.Proxy;
//
//public class AzureProxy extends RestProxy {
//
//    private AzureProxy(HttpPipeline httpPipeline, SwaggerInterfaceParser interfaceParser) {
//        super(httpPipeline, createDefaultSerializer(), interfaceParser);
//    }
//
//    /**
//     * Create a proxy implementation of the provided Swagger interface.
//     *
//     * @param swaggerInterface The Swagger interface to provide a proxy implementation for.
//     * @param azureEnvironment The azure environment that the proxy implementation will target.
//     * @param httpPipeline     The HTTP httpPipeline will be used to make REST calls. //* @param serializer The serializer
//     *                         that will be used to convert POJOs to and from request and response bodies.
//     * @param <A>              The type of the Swagger interface.
//     * @return A proxy implementation of the provided Swagger interface.
//     */
//    @SuppressWarnings("unchecked")
//    public static <A> A create(Class<A> swaggerInterface, AzureEnvironment azureEnvironment,
//                               HttpPipeline httpPipeline) {
//        String baseUrl = null;
//
//        if (azureEnvironment != null) {
//            final AzureHost azureHost = swaggerInterface.getAnnotation(AzureHost.class);
//            if (azureHost != null) {
//                baseUrl = azureEnvironment.url(azureHost.endpoint());
//            }
//        }
//
//        final SwaggerInterfaceParser interfaceParser =
//                new SwaggerInterfaceParser(swaggerInterface, createDefaultSerializer(), baseUrl);
//        final AzureProxy azureProxy = new AzureProxy(httpPipeline, interfaceParser);
//        return (A) Proxy.newProxyInstance(swaggerInterface.getClassLoader(), new Class<?>[]{swaggerInterface}, azureProxy);
//    }
//
//
//    /**
//     * Get the default serializer.
//     *
//     * @return the default serializer.
//     */
//    private static SerializerAdapter createDefaultSerializer() {
//        return AzureJacksonAdapter.createDefaultSerializerAdapter();
//    }
//
//}
//
//
