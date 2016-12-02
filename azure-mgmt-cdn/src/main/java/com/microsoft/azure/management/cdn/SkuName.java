/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cdn;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for SkuName.
 */
public final class SkuName {
    /** Static value Standard_Verizon for SkuName. */
    public static final SkuName STANDARD_VERIZON = new SkuName("Standard_Verizon");

    /** Static value Premium_Verizon for SkuName. */
    public static final SkuName PREMIUM_VERIZON = new SkuName("Premium_Verizon");

    /** Static value Custom_Verizon for SkuName. */
    public static final SkuName CUSTOM_VERIZON = new SkuName("Custom_Verizon");

    /** Static value Standard_Akamai for SkuName. */
    public static final SkuName STANDARD_AKAMAI = new SkuName("Standard_Akamai");

    /** Static value Standard_ChinaCdn for SkuName. */
    public static final SkuName STANDARD_CHINA_CDN = new SkuName("Standard_ChinaCdn");

    private String value;

    /**
     * Creates a custom value for SkuName.
     * @param value the custom value
     */
    public SkuName(String value) {
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
        if (!(obj instanceof SkuName)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        SkuName rhs = (SkuName) obj;
        if (value == null) {
            return rhs.value == null;
        } else {
            return value.equals(rhs.value);
        }
    }
}
