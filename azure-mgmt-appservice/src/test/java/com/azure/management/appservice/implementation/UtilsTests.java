/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.appservice.implementation;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTests {

    @Test
    public void testWebAppPrivateRegistryImage() throws Exception {
        // completion
        Assert.assertEquals("weidxuregistry.azurecr.io/az-func-java:v1", Utils.smartCompletionPrivateRegistryImage("weidxuregistry.azurecr.io/az-func-java:v1", "https://weidxuregistry.azurecr.io"));

        // completion
        Assert.assertEquals("weidxuregistry.azurecr.io/az-func-java:v1", Utils.smartCompletionPrivateRegistryImage("az-func-java:v1", "https://weidxuregistry.azurecr.io"));

        // completion
        Assert.assertEquals("weidxuregistry.azurecr.io/weidxu/az-func-java:v1", Utils.smartCompletionPrivateRegistryImage("weidxu/az-func-java:v1", "https://weidxuregistry.azurecr.io"));

        // completion
        Assert.assertEquals("weidxuregistry.azurecr.io:5000/weidxu/az-func-java:v1", Utils.smartCompletionPrivateRegistryImage("weidxu/az-func-java:v1", "https://weidxuregistry.azurecr.io:5000"));

        // completion
        Assert.assertEquals("weidxuregistry.azurecr.io/weidxu/az-func-java:v1", Utils.smartCompletionPrivateRegistryImage("az-func-java:v1", "https://weidxuregistry.azurecr.io/weidxu"));

        // completion not happen due to possible host
        Assert.assertEquals("host.name/az-func-java:v1", Utils.smartCompletionPrivateRegistryImage("host.name/az-func-java:v1", "https://weidxuregistry.azurecr.io"));

        // completion not happen due to possible port
        Assert.assertEquals("host:port/az-func-java:v1", Utils.smartCompletionPrivateRegistryImage("host:port/az-func-java:v1", "https://weidxuregistry.azurecr.io"));

        // completion not happen due to no idea what it is
        Assert.assertEquals("/az-func-java:v1", Utils.smartCompletionPrivateRegistryImage("/az-func-java:v1", "https://weidxuregistry.azurecr.io"));

        // completion not happen due to incorrect serviceUrl
        Assert.assertEquals("az-func-java:v1", Utils.smartCompletionPrivateRegistryImage("az-func-java:v1", "weidxuregistry.azurecr.io"));
    }
}
