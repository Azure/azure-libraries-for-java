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

    /** Enum value RuntimeFailed. */
    public static final BuildStatus RUNTIME_FAILED = fromString("RuntimeFailed");

    /** Enum value BuildAborted. */
    public static final BuildStatus BUILD_ABORTED = fromString("BuildAborted");

    /** Enum value BuildFailed. */
    public static final BuildStatus BUILD_FAILED = fromString("BuildFailed");

    /** Enum value BuildRequestReceived. */
    public static final BuildStatus BUILD_REQUEST_RECEIVED = fromString("BuildRequestReceived");

    /** Enum value BuildPending. */
    public static final BuildStatus BUILD_PENDING = fromString("BuildPending");

    /** Enum value BuildInProgress. */
    public static final BuildStatus BUILD_IN_PROGRESS = fromString("BuildInProgress");

    /** Enum value BuildSuccessful. */
    public static final BuildStatus BUILD_SUCCESSFUL = fromString("BuildSuccessful");

    /** Enum value PostBuildRestartRequired. */
    public static final BuildStatus POST_BUILD_RESTART_REQUIRED = fromString("PostBuildRestartRequired");

    /** Enum value RuntimeStarting. */
    public static final BuildStatus RUNTIME_STARTING = fromString("RuntimeStarting");

    /** Enum value RuntimeSuccessful. */
    public static final BuildStatus RUNTIME_SUCCESSFUL = fromString("RuntimeSuccessful");

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
