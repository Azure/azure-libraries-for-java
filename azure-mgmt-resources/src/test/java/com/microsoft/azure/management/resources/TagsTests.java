/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources;

import com.microsoft.azure.management.resources.implementation.TypeSerializationTests;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TagsTests extends ResourceManagerTestBase {

    @Test
    public void canUpdateTag() throws Exception {
        // assume there is a resource
        GenericResource resource = resourceClient.genericResources().list().iterator().next();

        Map<String, String> originalTags = resource.tags();
        if (originalTags == null) {
            originalTags = new HashMap<>();
        }

        TagResource updatedTags = resourceClient.tagOperations().updateTags(resource, new TypeSerializationTests.Map1<>("tag.1", "value.1"));
        Assert.assertEquals(1, updatedTags.tags().size());
        Assert.assertTrue(updatedTags.tags().containsKey("tag.1"));
        Assert.assertEquals("value.1", updatedTags.tags().get("tag.1"));

        updatedTags = resourceClient.tagOperations().updateTags(resource, new HashMap<String, String>());
        Assert.assertEquals(0, updatedTags.tags().size());

        updatedTags = resourceClient.tagOperations().updateTags(resource, originalTags);
        Assert.assertEquals(originalTags, updatedTags.tags());
    }
}
