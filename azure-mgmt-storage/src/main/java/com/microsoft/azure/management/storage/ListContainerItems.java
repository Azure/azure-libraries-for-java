/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.storage.implementation.ListContainerItemsInner;
import com.microsoft.azure.management.storage.implementation.StorageManager;

import java.util.List;

/**
 * Type representing ListContainerItems.
 */
@Fluent
@Beta
public interface ListContainerItems extends HasInner<ListContainerItemsInner>, HasManager<StorageManager> {
    /**
     * @return the value value.
     */
    List<ListContainerItem> value();

}