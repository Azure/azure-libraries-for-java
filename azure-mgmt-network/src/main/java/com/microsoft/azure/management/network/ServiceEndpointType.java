/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.network;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.microsoft.rest.ExpandableStringEnum;

import java.util.Collection;

/**
 * Defines values for ServiceEndpointType.
 */
public final class ServiceEndpointType extends ExpandableStringEnum<ServiceEndpointType> {
    /** Static value Microsoft.Storage for ServiceEndpointType. */
    public static final ServiceEndpointType MICROSOFT_STORAGE = fromString("Microsoft.Storage");

    /** Static value Microsoft.Sql for ServiceEndpointType. */
    public static final ServiceEndpointType MICROSOFT_SQL = fromString("Microsoft.Sql");

    /**
     * Creates or finds a ServiceEndpointType from its string representation.
     * @param name a name to look for
     * @return the corresponding ServiceEndpointType
     */
    @JsonCreator
    public static ServiceEndpointType fromString(String name) {
        return fromString(name, ServiceEndpointType.class);
    }

    /**
     * @return known ServiceEndpointType values
     */
    public static Collection<ServiceEndpointType> values() {
        return values(ServiceEndpointType.class);
    }
}
