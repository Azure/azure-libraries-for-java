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
 * Defines values for ReadOnlyEndpointFailoverPolicy.
 */
public final class ReadOnlyEndpointFailoverPolicy extends ExpandableStringEnum<ReadOnlyEndpointFailoverPolicy> {
    /**
     * Static value Disabled for ReadOnlyEndpointFailoverPolicy.
     */
    public static final ReadOnlyEndpointFailoverPolicy DISABLED = fromString("Disabled");

    /**
     * Static value Enabled for ReadOnlyEndpointFailoverPolicy.
     */
    public static final ReadOnlyEndpointFailoverPolicy ENABLED = fromString("Enabled");

    /**
     * Creates or finds a ReadOnlyEndpointFailoverPolicy from its string representation.
     *
     * @param name a name to look for.
     * @return the corresponding ReadOnlyEndpointFailoverPolicy.
     */
    @JsonCreator
    public static ReadOnlyEndpointFailoverPolicy fromString(String name) {
        return fromString(name, ReadOnlyEndpointFailoverPolicy.class);
    }

    /**
     * @return known ReadOnlyEndpointFailoverPolicy values.
     */
    public static Collection<ReadOnlyEndpointFailoverPolicy> values() {
        return values(ReadOnlyEndpointFailoverPolicy.class);
    }
}
