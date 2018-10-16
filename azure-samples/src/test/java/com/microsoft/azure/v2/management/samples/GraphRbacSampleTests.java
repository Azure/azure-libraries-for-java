/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.samples;

import com.microsoft.azure.v2.AzureEnvironment;
import com.microsoft.azure.v2.management.Azure;
import com.microsoft.azure.v2.management.graphrbac.samples.ManageServicePrincipal;
import com.microsoft.azure.v2.management.graphrbac.samples.ManageServicePrincipalCredentials;
import com.microsoft.azure.v2.management.graphrbac.samples.ManageUsersGroupsAndRoles;
import com.microsoft.azure.v2.management.resources.core.TestBase;
import com.microsoft.azure.v2.management.resources.samples.ManageResourceGroup;
import com.microsoft.rest.v2.http.HttpPipeline;
import org.junit.Assert;
import org.junit.Test;

public class GraphRbacSampleTests extends SamplesTestBase {
    private Azure.Authenticated authenticated;
    private String defaultSubscription;

    public GraphRbacSampleTests() {
        super(TestBase.RunCondition.LIVE_ONLY);
    }

    @Test
    public void testManageUsersGroupsAndRoles() {
        Assert.assertTrue(ManageUsersGroupsAndRoles.runSample(authenticated, defaultSubscription));
    }

    @Test
    public void testManageServicePrincipal() {
        Assert.assertTrue(ManageServicePrincipal.runSample(authenticated, defaultSubscription));
    }

    @Test
    public void testManageServicePrincipalCredentials() {
        Assert.assertTrue(ManageServicePrincipalCredentials.runSample(authenticated, defaultSubscription, AzureEnvironment.AZURE));
    }

    @Override
    protected void initializeClients(HttpPipeline httpPipeline, String defaultSubscription, String domain, AzureEnvironment environment) {
        authenticated = Azure.authenticate(httpPipeline, environment, domain, defaultSubscription);
        this.defaultSubscription = defaultSubscription;
    }

    @Override
    protected void cleanUpResources() {
    }
}