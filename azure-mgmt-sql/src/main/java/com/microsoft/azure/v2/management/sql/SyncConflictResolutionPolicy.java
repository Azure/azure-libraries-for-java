/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.sql;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.microsoft.rest.v2.ExpandableStringEnum;
import java.util.Collection;

/**
 * Defines values for SyncConflictResolutionPolicy.
 */
public final class SyncConflictResolutionPolicy extends ExpandableStringEnum<SyncConflictResolutionPolicy> {
    /**
     * Static value HubWin for SyncConflictResolutionPolicy.
     */
    public static final SyncConflictResolutionPolicy HUB_WIN = fromString("HubWin");

    /**
     * Static value MemberWin for SyncConflictResolutionPolicy.
     */
    public static final SyncConflictResolutionPolicy MEMBER_WIN = fromString("MemberWin");

    /**
     * Creates or finds a SyncConflictResolutionPolicy from its string representation.
     *
     * @param name a name to look for.
     * @return the corresponding SyncConflictResolutionPolicy.
     */
    @JsonCreator
    public static SyncConflictResolutionPolicy fromString(String name) {
        return fromString(name, SyncConflictResolutionPolicy.class);
    }

    /**
     * @return known SyncConflictResolutionPolicy values.
     */
    public static Collection<SyncConflictResolutionPolicy> values() {
        return values(SyncConflictResolutionPolicy.class);
    }
}
