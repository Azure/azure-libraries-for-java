/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.ExpandableStringEnum;

import java.util.Collection;

/**
 * Storage resource types.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_5_0)
public class StorageResourceType extends ExpandableStringEnum<StorageResourceType> {
    /**
     * Static value storageAccounts for StorageResourceType.
     */
    public static final StorageResourceType STORAGE_ACCOUNTS = fromString("storageAccounts");

    /**
     * Finds or creates storage resource type based on the specified string.
     *
     * @param str the storage resource type in string format
     * @return an instance of StorageResourceType
     */
    public static StorageResourceType fromString(String str) {
        return fromString(str, StorageResourceType.class);
    }

    /**
     * @return known storage resource types
     */
    public static Collection<StorageResourceType> values() {
        return values(StorageResourceType.class);
    }
}