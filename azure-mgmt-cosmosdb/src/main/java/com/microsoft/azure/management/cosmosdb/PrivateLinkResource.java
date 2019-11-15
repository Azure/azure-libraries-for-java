/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.cosmosdb;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.cosmosdb.implementation.PrivateLinkResourceInner;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

import java.util.List;

/**
 * A private link resource.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_28_0)
public interface PrivateLinkResource
        extends HasInner<PrivateLinkResourceInner> {

    /**
     * Get the id value.
     *
     * @return the id value
     */
    String id();

    /**
     * Get the name value.
     *
     * @return the name value
     */
    String name();

    /**
     * Get the type value.
     *
     * @return the type value
     */
    String type();

    /**
     * Get the private link resource group id.
     *
     * @return the groupId value
     */
    String groupId();

    /**
     * Get the private link resource required member names.
     *
     * @return the requiredMembers value
     */
    List<String> requiredMembers();
}
