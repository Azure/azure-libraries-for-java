// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.management;

import com.azure.core.http.HttpPipelineCallContext;
import com.azure.core.http.HttpPipelineNextPolicy;
import com.azure.core.http.HttpResponse;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.policy.HttpPipelinePolicy;
import com.azure.core.util.Configuration;
import reactor.core.publisher.Mono;

public class UserAgentPolicy implements HttpPipelinePolicy {
    private static final String USER_AGENT_KEY = "User-Agent";
    private static final String SDK_NAME_KEY = "Sdk-Name";
    private static final String SDK_VERSION_KEY = "Sdk-Version";
    private static final String APPLICATION_ID_KEY = "Application-Id";

    private final String defaultSdkName = this.getClass().getPackage().getName();
    private final String defaultSdkVersion = this.getClass().getPackage().getSpecificationVersion();

    private final HttpLogOptions httpLogOptions;
    private final Configuration configuration;

    public UserAgentPolicy() {
        this(null, null);
    }

    public UserAgentPolicy(HttpLogOptions httpLogOptions, Configuration configuration) {
        if (httpLogOptions == null) {
            this.httpLogOptions = new HttpLogOptions();
        } else {
            this.httpLogOptions = httpLogOptions;
        }

        if (configuration == null) {
            this.configuration = Configuration.getGlobalConfiguration();
        } else {
            this.configuration = configuration;
        }
    }

    @Override
    public Mono<HttpResponse> process(HttpPipelineCallContext context, HttpPipelineNextPolicy next){
        String userAgent = context.getHttpRequest().getHeaders().getValue(USER_AGENT_KEY);
        if (!userAgent.isEmpty()) {
            return next.process();
        }

        userAgent = context.getData(USER_AGENT_KEY).orElse("").toString();
        if (!userAgent.isEmpty()) {
            context.getHttpRequest().setHeader(USER_AGENT_KEY, userAgent);
            return next.process();
        }

        String sdkName = context.getData(SDK_NAME_KEY).orElse("").toString();
        if (sdkName.isEmpty()) {
            sdkName = defaultSdkName;
        }

        String sdkVersion = context.getData(SDK_VERSION_KEY).orElse("").toString();
        if (sdkVersion.isEmpty()) {
            sdkVersion = defaultSdkVersion;
        }

        String applicationId = context.getData(APPLICATION_ID_KEY).orElse("").toString();
        if (applicationId.isEmpty()) {
            applicationId = httpLogOptions.getApplicationId();
        }

        return new com.azure.core.http.policy.UserAgentPolicy(applicationId, sdkName, sdkVersion, configuration).process(context, next);
    }
}
