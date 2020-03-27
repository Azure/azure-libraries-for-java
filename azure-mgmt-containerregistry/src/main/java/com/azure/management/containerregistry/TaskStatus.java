// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.containerregistry;

import com.azure.core.util.ExpandableStringEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Collection;

/**
 * Defines values for TaskStatus.
 */
public final class TaskStatus extends ExpandableStringEnum<TaskStatus> {
    /**
     * Static value Disabled for TaskStatus.
     */
    public static final TaskStatus DISABLED = fromString("Disabled");

    /**
     * Static value Enabled for TaskStatus.
     */
    public static final TaskStatus ENABLED = fromString("Enabled");

    /**
     * Creates or finds a TaskStatus from its string representation.
     * 
     * @param name a name to look for.
     * @return the corresponding TaskStatus.
     */
    @JsonCreator
    public static TaskStatus fromString(String name) {
        return fromString(name, TaskStatus.class);
    }

    /**
     * @return known TaskStatus values.
     */
    public static Collection<TaskStatus> values() {
        return values(TaskStatus.class);
    }
}
