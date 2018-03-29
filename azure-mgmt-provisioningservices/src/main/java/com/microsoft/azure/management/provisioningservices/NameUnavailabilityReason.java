/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.provisioningservices;

import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.microsoft.rest.ExpandableStringEnum;

/**
 * Defines values for NameUnavailabilityReason.
 */
public final class NameUnavailabilityReason extends ExpandableStringEnum<NameUnavailabilityReason> {
    /** Static value Invalid for NameUnavailabilityReason. */
    public static final NameUnavailabilityReason INVALID = fromString("Invalid");

    /** Static value AlreadyExists for NameUnavailabilityReason. */
    public static final NameUnavailabilityReason ALREADY_EXISTS = fromString("AlreadyExists");

    /**
     * Creates or finds a NameUnavailabilityReason from its string representation.
     * @param name a name to look for
     * @return the corresponding NameUnavailabilityReason
     */
    @JsonCreator
    public static NameUnavailabilityReason fromString(String name) {
        return fromString(name, NameUnavailabilityReason.class);
    }

    /**
     * @return known NameUnavailabilityReason values
     */
    public static Collection<NameUnavailabilityReason> values() {
        return values(NameUnavailabilityReason.class);
    }
}
