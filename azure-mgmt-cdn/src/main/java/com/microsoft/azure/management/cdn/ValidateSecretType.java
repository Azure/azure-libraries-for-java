/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cdn;

import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.microsoft.rest.ExpandableStringEnum;

/**
 * Defines values for ValidateSecretType.
 */
public final class ValidateSecretType extends ExpandableStringEnum<ValidateSecretType> {
    /** Static value UrlSigningKey for ValidateSecretType. */
    public static final ValidateSecretType URL_SIGNING_KEY = fromString("UrlSigningKey");

    /** Static value ManagedCertificate for ValidateSecretType. */
    public static final ValidateSecretType MANAGED_CERTIFICATE = fromString("ManagedCertificate");

    /** Static value CustomerCertificate for ValidateSecretType. */
    public static final ValidateSecretType CUSTOMER_CERTIFICATE = fromString("CustomerCertificate");

    /**
     * Creates or finds a ValidateSecretType from its string representation.
     * @param name a name to look for
     * @return the corresponding ValidateSecretType
     */
    @JsonCreator
    public static ValidateSecretType fromString(String name) {
        return fromString(name, ValidateSecretType.class);
    }

    /**
     * @return known ValidateSecretType values
     */
    public static Collection<ValidateSecretType> values() {
        return values(ValidateSecretType.class);
    }
}
