/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.network;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for LoadDistribution.
 */
public final class LoadDistribution {
    /** Static value Default for LoadDistribution. */
    public static final LoadDistribution DEFAULT = new LoadDistribution("Default");

    /** Static value SourceIP for LoadDistribution. */
    public static final LoadDistribution SOURCE_IP = new LoadDistribution("SourceIP");

    /** Static value SourceIPProtocol for LoadDistribution. */
    public static final LoadDistribution SOURCE_IPPROTOCOL = new LoadDistribution("SourceIPProtocol");

    private String value;

    /**
     * Creates a custom value for LoadDistribution.
     * @param value the custom value
     */
    public LoadDistribution(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LoadDistribution)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        LoadDistribution rhs = (LoadDistribution) obj;
        if (value == null) {
            return rhs.value == null;
        } else {
            return value.equals(rhs.value);
        }
    }
}
