/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.core;

import com.microsoft.azure.v2.AzureEnvironment;
import com.microsoft.azure.v2.credentials.ApplicationTokenCredentials;

import java.io.IOException;
import java.util.HashMap;

public class AzureTestCredentials extends ApplicationTokenCredentials {
    boolean isPlaybackMode;

    public AzureTestCredentials(final String mockUrl, String mockTenant, boolean isPlaybackMode) {
        super("", mockTenant, "", new AzureEnvironment(new HashMap<String, String>() {{
            put("managementEndpointUrl", mockUrl);
            put("resourceManagerEndpointUrl", mockUrl);
            put("sqlManagementEndpointUrl", mockUrl);
            put("galleryEndpointUrl", mockUrl);
            put("activeDirectoryEndpointUrl", mockUrl);
            put("activeDirectoryResourceId", mockUrl);
            put("activeDirectoryGraphResourceId", mockUrl);

        }}));
        this.isPlaybackMode = isPlaybackMode;
    }

    @Override
    public String getToken(String resource) throws IOException {
        if (!isPlaybackMode) {
            super.getToken(resource);
        }
        return "https:/asdd.com";
    }
}
