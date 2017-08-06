/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.scheduler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for ServiceBusTransportType.
 */
public enum ServiceBusTransportType {
    /** Enum value NotSpecified. */
    NOT_SPECIFIED("NotSpecified"),

    /** Enum value NetMessaging. */
    NET_MESSAGING("NetMessaging"),

    /** Enum value AMQP. */
    AMQP("AMQP");

    /** The actual serialized value for a ServiceBusTransportType instance. */
    private String value;

    ServiceBusTransportType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a ServiceBusTransportType instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed ServiceBusTransportType object, or null if unable to parse.
     */
    @JsonCreator
    public static ServiceBusTransportType fromString(String value) {
        ServiceBusTransportType[] items = ServiceBusTransportType.values();
        for (ServiceBusTransportType item : items) {
            if (item.toString().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
