/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.ExpandableStringEnum;

import java.util.Collection;

/**
 * Compute resource sku tier.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_5_0)
public class ComputeSkuTier extends ExpandableStringEnum<ComputeSkuTier> {
    /**
     * Static value Basic for ComputeSkuTier.
     */
    public static final ComputeSkuTier BASIC = fromString("Basic");
    /**
     * Static value Standard for ComputeSkuTier.
     */
    public static final ComputeSkuTier STANDARD = fromString("Standard");
    /**
     * Static value Premium for ComputeSkuTier.
     */
    public static final ComputeSkuTier PREMIUM = fromString("Premium");


    /**
     * Creates or finds a ComputeSkuTier from its string representation.
     *
     * @param name a name to look for
     * @return the corresponding ComputeSkuTier
     */
    public static ComputeSkuTier fromString(String name) {
        return fromString(name, ComputeSkuTier.class);
    }

    /**
     * @return known ComputeSkuTier values
     */
    public static Collection<ComputeSkuTier> values() {
        return values(ComputeSkuTier.class);
    }
}
