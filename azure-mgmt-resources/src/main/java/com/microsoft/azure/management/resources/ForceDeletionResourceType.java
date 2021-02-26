/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.microsoft.rest.ExpandableStringEnum;

import java.util.Collection;

/**
 * Resource type for force deletion.
 */
public final class ForceDeletionResourceType extends ExpandableStringEnum<ForceDeletionResourceType> {

    /** Static value Microsoft.Compute/virtualMachines for ForceDeletionResourceType. */
    public static final ForceDeletionResourceType VIRTUAL_MACHINES = fromString("Microsoft.Compute/virtualMachines");

    /** Static value Microsoft.Compute/virtualMachineScaleSets for ForceDeletionResourceType. */
    public static final ForceDeletionResourceType VIRTUAL_MACHINE_SCALE_SETS = fromString("Microsoft.Compute/virtualMachineScaleSets");

    /**
     * Creates or finds a ForceDeletionResourceType from its string representation.
     * @param name a name to look for
     * @return the corresponding ForceDeletionResourceType
     */
    @JsonCreator
    public static ForceDeletionResourceType fromString(String name) {
        return fromString(name, ForceDeletionResourceType.class);
    }

    /**
     * @return known ForceDeletionResourceType values
     */
    public static Collection<ForceDeletionResourceType> values() {
        return values(ForceDeletionResourceType.class);
    }
}
