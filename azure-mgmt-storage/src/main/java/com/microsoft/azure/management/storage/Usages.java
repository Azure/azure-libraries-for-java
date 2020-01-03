/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.storage.implementation.StorageManager;
import com.microsoft.azure.management.storage.implementation.UsagesInner;

/**
 * Entry point for storage resource usage management API.
 */
@Fluent
public interface Usages extends SupportsListing<StorageUsage>,
        HasInner<UsagesInner>,
        HasManager<StorageManager> {
}
