/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.microsoft.azure.management.resources.fluentcore.arm.ExpandableStringEnum;

import java.util.Collection;

public final class BuildStatus extends ExpandableStringEnum<BuildStatus> {

    /** Enum value RuntimeSuccessful. */
    public static final BuildStatus RUNTIME_SUCCESSFUL = fromString("RuntimeSuccessful");

    /** Enum value RuntimeFailed. */
    public static final BuildStatus RUNTIME_FAILED = fromString("RuntimeFailed");

    /**
     * Creates or finds a BuildStatus from its string representation.
     * @param name a name to look for
     * @return the corresponding BuildStatus
     */
    @JsonCreator
    public static BuildStatus fromString(String name) {
        return fromString(name, BuildStatus.class);
    }

    /**
     * @return known SecurityRuleDirection values
     */
    public static Collection<BuildStatus> values() {
        return values(BuildStatus.class);
    }
}
