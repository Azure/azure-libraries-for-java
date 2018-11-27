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
 * Defines values for JobStepActionType.
 */
public final class JobStepActionType extends ExpandableStringEnum<JobStepActionType> {
    /**
     * Static value TSql for JobStepActionType.
     */
    public static final JobStepActionType TSQL = fromString("TSql");

    /**
     * Creates or finds a JobStepActionType from its string representation.
     *
     * @param name a name to look for.
     * @return the corresponding JobStepActionType.
     */
    @JsonCreator
    public static JobStepActionType fromString(String name) {
        return fromString(name, JobStepActionType.class);
    }

    /**
     * @return known JobStepActionType values.
     */
    public static Collection<JobStepActionType> values() {
        return values(JobStepActionType.class);
    }
}
