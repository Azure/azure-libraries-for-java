/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.implementation;

import com.microsoft.azure.management.resources.TagResource;

import java.util.Collections;
import java.util.Map;

final class TagResourceImpl implements TagResource {

    private final TagsResourceInner innerObject;

    TagResourceImpl(TagsResourceInner inner) {
        this.innerObject = inner;
    }

    @Override
    public TagsResourceInner inner() {
        return this.innerObject;
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public String type() {
        return this.inner().type();
    }

    @Override
    public Map<String, String> tags() {
        return this.inner().properties() == null
            ? Collections.<String, String>emptyMap()
            : Collections.unmodifiableMap(this.inner().properties().tags());
    }
}
