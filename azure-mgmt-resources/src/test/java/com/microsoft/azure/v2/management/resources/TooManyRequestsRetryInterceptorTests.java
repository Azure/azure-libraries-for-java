/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.resources;

import com.microsoft.azure.v2.AzureEnvironment;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.rest.v2.http.HttpPipeline;
import org.junit.Test;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TooManyRequestsRetryInterceptorTests extends ResourceManagerTestBase {
    private static ResourceGroups resourceGroups;

    private String rgName;
    private String testId;
    private ResourceGroup rg;

    @Override
    protected void initializeClients(HttpPipeline pipeline, String defaultSubscription, String domain, AzureEnvironment environment) {
        testId = SdkContext.randomResourceName("", 9);
        rgName = "rg429" + testId;

        super.initializeClients(pipeline, defaultSubscription, domain, environment);
        resourceGroups = resourceClient.resourceGroups();

        rg = resourceGroups.define(rgName)
            .withRegion(Region.US_EAST)
            .create();
    }

    @Override
    protected void cleanUpResources() {
        resourceGroups.beginDeleteByName(rgName);
    }

    @Test
    public void canGenerate429() throws Exception {
        Observable.range(1, 1250).flatMap(new Function<Integer, Observable<ResourceGroup>>() {
            @Override
            public Observable<ResourceGroup> apply(final Integer iteration) {
                return rg.update().applyAsync();
            }
        }, 10)
            .subscribeOn(Schedulers.io()).blockingSubscribe();
    }
}
