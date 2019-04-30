/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.network;

import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.microsoft.rest.ExpandableStringEnum;

/**
 * Defines values for NextHopType.
 */
public final class NextHopType extends ExpandableStringEnum<NextHopType> {
    /** Static value Internet for NextHopType. */
    public static final NextHopType INTERNET = fromString("Internet");

    /** Static value VirtualAppliance for NextHopType. */
    public static final NextHopType VIRTUAL_APPLIANCE = fromString("VirtualAppliance");

    /** Static value VirtualNetworkGateway for NextHopType. */
    public static final NextHopType VIRTUAL_NETWORK_GATEWAY = fromString("VirtualNetworkGateway");

    /** Static value VnetLocal for NextHopType. */
    public static final NextHopType VNET_LOCAL = fromString("VnetLocal");

    /** Static value HyperNetGateway for NextHopType. */
    public static final NextHopType HYPER_NET_GATEWAY = fromString("HyperNetGateway");

    /** Static value None for NextHopType. */
    public static final NextHopType NONE = fromString("None");

    /**
     * Creates or finds a NextHopType from its string representation.
     * @param name a name to look for
     * @return the corresponding NextHopType
     */
    @JsonCreator
    public static NextHopType fromString(String name) {
        return fromString(name, NextHopType.class);
    }

    /**
     * @return known NextHopType values
     */
    public static Collection<NextHopType> values() {
        return values(NextHopType.class);
    }
}
