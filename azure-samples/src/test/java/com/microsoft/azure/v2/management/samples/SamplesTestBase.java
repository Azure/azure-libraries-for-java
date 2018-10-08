/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.samples;


import com.microsoft.azure.v2.Azure;
import com.microsoft.azure.v2.AzureEnvironment;
import com.microsoft.azure.v2.management.resources.core.TestBase;
import com.microsoft.rest.v2.http.HttpPipeline;

public class SamplesTestBase extends TestBase {
    protected Azure azure;

    public SamplesTestBase() {
        super(RunCondition.BOTH);
    }

    public SamplesTestBase(RunCondition runCondition) {
        super(runCondition);
    }

    @Override
    protected void initializeClients(HttpPipeline restClient, String defaultSubscription, String domain, AzureEnvironment environment) {
        azure = Azure
                .authenticate(restClient, environment, defaultSubscription).withSubscription(defaultSubscription);
    }

    @Override
    protected void cleanUpResources() {
    }
}