/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources;


import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.implementation.TagsResourceInner;

import java.util.Map;

/**
 * An immutable client-side representation of an Azure resource with tags.
 */
public interface TagResource extends
        HasId, HasName, HasInner<TagsResourceInner> {

    /**
     * @return the type of the resource
     */
    String type();

    /**
     * @return the tags for the resource
     */
    Map<String, String> tags();
}
