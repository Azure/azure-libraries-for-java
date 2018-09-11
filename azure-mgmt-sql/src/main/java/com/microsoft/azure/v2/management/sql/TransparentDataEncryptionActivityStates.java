/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.sql;

import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ExpandableStringEnum;

/**
 * Defines values for TransparentDataEncryptionActivityStatus.
 */
public final class TransparentDataEncryptionActivityStates extends ExpandableStringEnum<TransparentDataEncryptionActivityStates> {
    /** Static value Encrypting for TransparentDataEncryptionActivityStatus. */
    public static final TransparentDataEncryptionActivityStates ENCRYPTING = fromString("Encrypting");

    /** Static value Decrypting for TransparentDataEncryptionActivityStatus. */
    public static final TransparentDataEncryptionActivityStates DECRYPTING = fromString("Decrypting");

    /**
     * Creates or finds a TransparentDataEncryptionActivityStatus from its string representation.
     * @param name a name to look for
     * @return the corresponding TransparentDataEncryptionActivityStatus
     */
    @JsonCreator
    public static TransparentDataEncryptionActivityStates fromString(String name) {
        return fromString(name, TransparentDataEncryptionActivityStates.class);
    }

    /**
     * @return known TransparentDataEncryptionActivityStatus values
     */
    public static Collection<TransparentDataEncryptionActivityStates> values() {
        return values(TransparentDataEncryptionActivityStates.class);
    }
}
