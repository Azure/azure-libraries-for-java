/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.mediaservices.implementation;

import com.microsoft.azure.management.mediaservices.AssetStorageEncryptionKey;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

class AssetStorageEncryptionKeyImpl extends WrapperImpl<AssetStorageEncryptionKeyInner> implements AssetStorageEncryptionKey {
    private final MediaManager manager;
    AssetStorageEncryptionKeyImpl(AssetStorageEncryptionKeyInner inner, MediaManager manager) {
        super(inner);
        this.manager = manager;
    }

    @Override
    public MediaManager manager() {
        return this.manager;
    }

    @Override
    public String storageEncryptionKey() {
        return this.inner().storageEncryptionKey();
    }

}
