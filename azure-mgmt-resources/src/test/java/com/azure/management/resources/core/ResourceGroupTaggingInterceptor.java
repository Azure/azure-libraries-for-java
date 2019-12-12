/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.core;

import com.azure.management.resources.implementation.ResourceGroupInner;
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
    private static final String LOGGING_CONTEXT = "com.microsoft.azure.management.resources.ResourceGroups createOrUpdate";
    private AzureJacksonAdapter adapter = new AzureJacksonAdapter();

    @Override
    public Response intercept(Chain chain) throws IOException {
        if ("PUT".equals(chain.request().method()) && chain.request().url().uri().toString().contains("/resourcegroups/") &&
                LOGGING_CONTEXT.equals(chain.request().header("x-ms-logging-context"))) {
            String body = bodyToString(chain.request());
            ResourceGroupInner rg = adapter.deserialize(body, ResourceGroupInner.class);
            if (rg == null) {
                throw new RuntimeException("Failed to deserialize " + body);
            }
            Map<String, String> tags = rg.getTags();
            if (tags == null) {
                tags = new HashMap<>();
            }
            tags.put("product", "javasdk");
            tags.put("cause", "automation");
            tags.put("date", DateTime.now(DateTimeZone.UTC).toString());
            if (System.getenv("ENV_JOB_NAME") != null) {
                tags.put("job", System.getenv("ENV_JOB_NAME"));
            }
            rg.withTags(tags);

            String newBody = adapter.serialize(rg);
            Request newRequest = chain.request().newBuilder()
                    .put(RequestBody.create(chain.request().body().contentType(), newBody))
                    .header("Content-Length", String.valueOf(newBody.length())).build();
            return chain.proceed(newRequest);
        }
        return chain.proceed(chain.request());
    }

    private static String bodyToString(final Request request) throws IOException {
        final Request copy = request.newBuilder().build();
        final Buffer buffer = new Buffer();
        copy.body().writeTo(buffer);
        return buffer.readUtf8();
    }
}
