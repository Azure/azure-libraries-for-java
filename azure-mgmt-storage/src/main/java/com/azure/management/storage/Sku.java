/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.storage;

/**
 * The SKU of the storage account.
 */
public class Sku {
    /**
     * the sku name.
     */
    private SkuName name;

    /**
     * the sku tier.
     */
    private SkuTier tier;

    /**
     * @return the name value
     */
    public SkuName name() {
        return this.name;
    }

    /**
     * Set the name value.
     *
     * @param name the name value to set
     * @return the Sku object.
     */
    public Sku withName(SkuName name) {
        this.name = name;
        return this;
    }

    /**
     * Get the tier value.
     *
     * @return the tier value
     */
    public SkuTier tier() {
        return this.tier;
    }
}