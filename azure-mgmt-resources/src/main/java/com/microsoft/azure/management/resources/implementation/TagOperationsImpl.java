/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.implementation;

import com.microsoft.azure.management.resources.TagOperations;
import com.microsoft.azure.management.resources.TagResource;
import com.microsoft.azure.management.resources.Tags;
import com.microsoft.azure.management.resources.TagsPatchOperation;
import com.microsoft.azure.management.resources.TagsPatchResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import rx.Observable;
import rx.functions.Func1;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

class TagOperationsImpl implements TagOperations {

    private final ResourceManager myManager;

    TagOperationsImpl(ResourceManager resourceManager) {
        this.myManager = resourceManager;
    }

    @Override
    public TagResource updateTags(Resource resource, Map<String, String> tags) {
        return this.updateTagsAsync(resource, tags).toBlocking().last();
    }

    @Override
    public TagResource updateTags(String resourceId, Map<String, String> tags) {
        return this.updateTagsAsync(resourceId, tags).toBlocking().last();
    }

    @Override
    public Observable<TagResource> updateTagsAsync(Resource resource, Map<String, String> tags) {
        return this.updateTagsAsync(Objects.requireNonNull(resource).id(), tags);
    }

    @Override
    public Observable<TagResource> updateTagsAsync(String resourceId, Map<String, String> tags) {
        TagsPatchResource parameters = new TagsPatchResource()
            .withOperation(TagsPatchOperation.REPLACE)
            .withProperties(new Tags().withTags(new TreeMap<>(tags)));
        return this.manager().inner().tagOperations()
            .updateAtScopeAsync(resourceId, parameters)
            .map(new Func1<TagsResourceInner, TagResource>() {
                @Override
                public TagResource call(TagsResourceInner inner) {
                    return new TagResourceImpl(inner);
                }
            });
    }

    @Override
    public ResourceManager manager() {
        return this.myManager;
    }
}
