/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage.implementation;


import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.microsoft.azure.management.storage.ListContainerItem;
import com.microsoft.azure.management.storage.ListContainerItems;

import java.util.List;

@LangDefinition
class ListContainerItemsImpl extends WrapperImpl<ListContainerItemsInner> implements ListContainerItems {
    private final StorageManager manager;
    ListContainerItemsImpl(ListContainerItemsInner inner, StorageManager manager) {
        super(inner);
        this.manager = manager;
    }

    @Override
    public StorageManager manager() {
        return this.manager;
    }

    @Override
    public List<ListContainerItem> value() {
        return this.inner().value();
    }

}
