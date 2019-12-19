/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.storage;

import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.microsoft.rest.ExpandableStringEnum;

/**
 * Defines values for LargeFileSharesState.
 */
public final class LargeFileSharesState extends ExpandableStringEnum<LargeFileSharesState> {
    /** Static value Disabled for LargeFileSharesState. */
    public static final LargeFileSharesState DISABLED = fromString("Disabled");

    /** Static value Enabled for LargeFileSharesState. */
    public static final LargeFileSharesState ENABLED = fromString("Enabled");

    /**
     * Creates or finds a LargeFileSharesState from its string representation.
     * @param name a name to look for
     * @return the corresponding LargeFileSharesState
     */
    @JsonCreator
    public static LargeFileSharesState fromString(String name) {
        return fromString(name, LargeFileSharesState.class);
    }

    /**
     * @return known LargeFileSharesState values
     */
    public static Collection<LargeFileSharesState> values() {
        return values(LargeFileSharesState.class);
    }
}
