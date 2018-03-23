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
 * Defines values for IpsecEncryption.
 */
public final class IpsecEncryption {
    /** Static value None for IpsecEncryption. */
    public static final IpsecEncryption NONE = new IpsecEncryption("None");

    /** Static value DES for IpsecEncryption. */
    public static final IpsecEncryption DES = new IpsecEncryption("DES");

    /** Static value DES3 for IpsecEncryption. */
    public static final IpsecEncryption DES3 = new IpsecEncryption("DES3");

    /** Static value AES128 for IpsecEncryption. */
    public static final IpsecEncryption AES128 = new IpsecEncryption("AES128");

    /** Static value AES192 for IpsecEncryption. */
    public static final IpsecEncryption AES192 = new IpsecEncryption("AES192");

    /** Static value AES256 for IpsecEncryption. */
    public static final IpsecEncryption AES256 = new IpsecEncryption("AES256");

    /** Static value GCMAES128 for IpsecEncryption. */
    public static final IpsecEncryption GCMAES128 = new IpsecEncryption("GCMAES128");

    /** Static value GCMAES192 for IpsecEncryption. */
    public static final IpsecEncryption GCMAES192 = new IpsecEncryption("GCMAES192");

    /** Static value GCMAES256 for IpsecEncryption. */
    public static final IpsecEncryption GCMAES256 = new IpsecEncryption("GCMAES256");

    private String value;

    /**
     * Creates a custom value for IpsecEncryption.
     * @param value the custom value
     */
    public IpsecEncryption(String value) {
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
        if (!(obj instanceof IpsecEncryption)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        IpsecEncryption rhs = (IpsecEncryption) obj;
        if (value == null) {
            return rhs.value == null;
        } else {
            return value.equals(rhs.value);
        }
    }
}
