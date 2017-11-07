/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerservice;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;

/**
 * The minimum valid number of master nodes.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_4_0)
public enum ContainerServiceMasterProfileCount {
    /** Enum value Min. */
    MIN(1),

    /** Enum value Mid. */
    MID(3),

    /** Enum value Max. */
    MAX(5);

    /** The count for a ContainerServiceMasterProfileCount instance. */
    private int count;

    ContainerServiceMasterProfileCount(int count) {
        this.count = count;
    }

    /**
     * @return the count.
     */
    public int count() {
        return this.count;
    }
}
