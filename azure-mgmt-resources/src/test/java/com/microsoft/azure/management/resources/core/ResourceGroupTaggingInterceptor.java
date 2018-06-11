/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.core;

import com.microsoft.azure.management.resources.implementation.ResourceGroupInner;
import com.microsoft.azure.serializer.AzureJacksonAdapter;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * An interceptor for tagging resource groups created in tests.
 */
public class ResourceGroupTaggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        if ("PUT".equals(chain.request().method()) && chain.request().url().uri().toString().contains("/resourcegroups/")) {
            String body = bodyToString(chain.request());
            AzureJacksonAdapter adapter = new AzureJacksonAdapter();
            ResourceGroupInner rg = adapter.deserialize(body, ResourceGroupInner.class);
            Map<String, String> tags = rg.tags();
            if (tags == null) {
                tags = new HashMap<>();
            }
            tags.put("product", "javasdk");
            tags.put("cause", "automation");
            tags.put("date", DateTime.now(DateTimeZone.UTC).toString());
            rg.withTags(tags);

            String newBody = adapter.serialize(rg);
            Request newRequest = chain.request().newBuilder()
                    .put(RequestBody.create(chain.request().body().contentType(), newBody))
                    .header("Content-Length", String.valueOf(newBody.length())).build();
            return chain.proceed(newRequest);
        }
        return chain.proceed(chain.request());
    }

    private static String bodyToString(final Request request){

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
