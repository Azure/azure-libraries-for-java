/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.core;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;

import java.io.IOException;
import java.util.HashMap;

public class AzureTestCredentials extends ApplicationTokenCredentials {
    public AzureTestCredentials(final String mockUrl) {
        super("", MockIntegrationTestBase.MOCK_TENANT, "", new AzureEnvironment(new HashMap<String, String>() {{
            put("managementEndpointUrl", mockUrl);
            put("resourceManagerEndpointUrl", mockUrl);
            put("sqlManagementEndpointUrl", mockUrl);
            put("galleryEndpointUrl", mockUrl);
            put("activeDirectoryEndpointUrl", mockUrl);
            put("activeDirectoryResourceId", mockUrl);
            put("activeDirectoryGraphResourceId", mockUrl);
        }}));
    }

    @Override
    public String getToken(String resource) throws IOException {
        if (!MockIntegrationTestBase.IS_MOCKED) {
            super.getToken(resource);
        }
        return "https:/asdd.com";
    }
}
