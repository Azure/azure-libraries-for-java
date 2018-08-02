/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources;

import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.Utils;
import com.microsoft.rest.v2.http.HttpClient;
import com.microsoft.rest.v2.util.FlowableUtil;
import io.reactivex.Flowable;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ResourceUtilsTests {
    @Test
    public void canExtractGroupFromId() throws Exception {
        Assert.assertEquals("foo", ResourceUtils.groupFromResourceId("subscriptions/123/resourceGroups/foo/providers/Microsoft.Bar/bars/bar1"));
        Assert.assertEquals("foo", ResourceUtils.groupFromResourceId("subscriptions/123/resourcegroups/foo/providers/Microsoft.Bar/bars/bar1"));
        Assert.assertNull(ResourceUtils.groupFromResourceId(null));
    }

    @Test
    public void canExtractResourceProviderFromResourceId() {
        Assert.assertEquals("Microsoft.Bar", ResourceUtils.resourceProviderFromResourceId("subscriptions/123/resourceGroups/foo/providers/Microsoft.Bar/bars/bar1"));
        Assert.assertNull(ResourceUtils.resourceProviderFromResourceId(null));
    }

    @Test
    public void canExtractParentPathFromId() throws Exception {
        Assert.assertEquals("/subscriptions/123/resourceGroups/foo/providers/Microsoft.Bar/bars/bar1", ResourceUtils.parentResourceIdFromResourceId("subscriptions/123/resourceGroups/foo/providers/Microsoft.Bar/bars/bar1/bazs/baz1"));
        Assert.assertNull(ResourceUtils.parentResourceIdFromResourceId("subscriptions/123/resourceGroups/foo/providers/Microsoft.Bar/bars/bar1"));
    }

    @Test
    public void canExtractRelativePathFromId() throws Exception {
        Assert.assertEquals("bars/bar1", ResourceUtils.relativePathFromResourceId("subscriptions/123/resourceGroups/foo/providers/Microsoft.Bar/bars/bar1"));
        Assert.assertEquals("", ResourceUtils.parentRelativePathFromResourceId("subscriptions/123/resourceGroups/foo/providers/Microsoft.Bar/bars/bar1"));
        Assert.assertEquals("bars/bar1/providers/provider1", ResourceUtils.relativePathFromResourceId("subscriptions/123/resourceGroups/foo/providers/Microsoft.Bar/bars/bar1/providers/provider1"));
        Assert.assertEquals("providers/provider1/bars/bar1", ResourceUtils.relativePathFromResourceId("subscriptions/123/resourceGroups/foo/providers/Microsoft.Bar/providers/provider1/bars/bar1"));
    }

    @Test
    public void canDownloadFile() throws Exception {
        Flowable<byte[]> contentStream = Utils.downloadFileAsync("http://google.com/humans.txt", HttpClient.createDefault());
        byte[] content = FlowableUtil.collectBytesInArray(contentStream.map(ByteBuffer::wrap)).blockingGet();
        String contentString = new String(content, StandardCharsets.UTF_8);
        Assert.assertNotNull(contentString);
    }
}
