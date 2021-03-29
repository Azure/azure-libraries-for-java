/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources;

import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.implementation.ResourceManager;
import rx.Observable;

import java.util.Map;

/**
 * Entry point to tag management API.
 */
public interface TagOperations extends HasManager<ResourceManager> {

    /**
     * Updates the tags of the Azure resource.
     *
     * @param resource the Azure resource to have its tags updated
     * @param tags the tags
     * @return the resource with updated tags
     */
    TagResource updateTags(Resource resource, Map<String, String> tags);

    /**
     * Updates the tags of the Azure resource.
     *
     * @param resourceId the ID of the Azure resource to have its tags updated
     * @param tags the tags
     * @return the resource with updated tags
     */
    TagResource updateTags(String resourceId, Map<String, String> tags);

    /**
     * Updates the tags of the Azure resource.
     *
     * @param resource the Azure resource to have its tags updated
     * @param tags the tags
     * @return the resource with updated tags
     */
    Observable<TagResource> updateTagsAsync(Resource resource, Map<String, String> tags);

    /**
     * Updates the tags of the Azure resource.
     *
     * @param resourceId the ID of the Azure resource to have its tags updated
     * @param tags the tags
     * @return the resource with updated tags
     */
    Observable<TagResource> updateTagsAsync(String resourceId, Map<String, String> tags);
}
