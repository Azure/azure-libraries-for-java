/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.azure.management.containerregistry;

import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.microsoft.rest.ExpandableStringEnum;

/**
 * Defines values for Architecture.
 */
public final class Architecture extends ExpandableStringEnum<Architecture> {
    /** Static value amd64 for Architecture. */
    public static final Architecture AMD64 = fromString("amd64");

    /** Static value x86 for Architecture. */
    public static final Architecture X86 = fromString("x86");

    /** Static value arm for Architecture. */
    public static final Architecture ARM = fromString("arm");

    /**
     * Creates or finds a Architecture from its string representation.
     * @param name a name to look for
     * @return the corresponding Architecture
     */
    @JsonCreator
    public static Architecture fromString(String name) {
        return fromString(name, Architecture.class);
    }

    /**
     * @return known Architecture values
     */
    public static Collection<Architecture> values() {
        return values(Architecture.class);
    }
}
