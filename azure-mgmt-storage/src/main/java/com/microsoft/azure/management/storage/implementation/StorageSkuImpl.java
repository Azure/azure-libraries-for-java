/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.storage.Kind;
import com.microsoft.azure.management.storage.Restriction;
import com.microsoft.azure.management.storage.SKUCapability;
import com.microsoft.azure.management.storage.SkuName;
import com.microsoft.azure.management.storage.SkuTier;
import com.microsoft.azure.management.storage.StorageAccountSkuType;
import com.microsoft.azure.management.storage.StorageResourceType;
import com.microsoft.azure.management.storage.StorageSku;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation for {@link StorageSku}.
 */
@LangDefinition
class StorageSkuImpl implements StorageSku {
    private final SkuInner inner;

    StorageSkuImpl(SkuInner skuInner) {
        this.inner = skuInner;
    }

    @Override
    public SkuName name() {
        return this.inner.name();
    }

    @Override
    public SkuTier tier() {
        return this.inner.tier();
    }

    @Override
    public StorageResourceType resourceType() {
        if (this.inner.resourceType() != null) {
            return StorageResourceType.fromString(this.inner.resourceType());
        } else {
            return null;
        }
    }

    @Override
    public List<Region> regions() {
        List<Region> regions = new ArrayList<>();
        if (this.inner.locations() != null) {
            for (String location : this.inner.locations()) {
                regions.add(Region.fromName(location));
            }
        }
        return regions;
    }

    @Override
    public List<SKUCapability> capabilities() {
        if (this.inner.capabilities() != null) {
            return this.inner.capabilities();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Restriction> restrictions() {
        if (this.inner.restrictions() != null) {
            return this.inner.restrictions();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Kind storageAccountKind() {
        return this.inner.kind();
    }

    @Override
    public StorageAccountSkuType storageAccountSku() {
        if (this.resourceType() != null && this.resourceType().equals(StorageResourceType.STORAGE_ACCOUNTS)) {
            return StorageAccountSkuType.fromSkuName(this.inner.name());
        }
        return null;
    }

    @Override
    public SkuInner inner() {
        return this.inner;
    }
}