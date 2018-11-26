/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.graphrbac;

import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.microsoft.rest.ExpandableStringEnum;

/**
 * Defines values for PrincipalType.
 */
public final class PrincipalType extends ExpandableStringEnum<PrincipalType> {
    /** Static value User for PrincipalType. */
    public static final PrincipalType USER = fromString("User");

    /** Static value Group for PrincipalType. */
    public static final PrincipalType GROUP = fromString("Group");

    /** Static value ServicePrincipal for PrincipalType. */
    public static final PrincipalType SERVICE_PRINCIPAL = fromString("ServicePrincipal");

    /** Static value Unknown for PrincipalType. */
    public static final PrincipalType UNKNOWN = fromString("Unknown");

    /** Static value DirectoryRoleTemplate for PrincipalType. */
    public static final PrincipalType DIRECTORY_ROLE_TEMPLATE = fromString("DirectoryRoleTemplate");

    /** Static value ForeignGroup for PrincipalType. */
    public static final PrincipalType FOREIGN_GROUP = fromString("ForeignGroup");

    /** Static value Application for PrincipalType. */
    public static final PrincipalType APPLICATION = fromString("Application");

    /** Static value MSI for PrincipalType. */
    public static final PrincipalType MSI = fromString("MSI");

    /** Static value DirectoryObjectOrGroup for PrincipalType. */
    public static final PrincipalType DIRECTORY_OBJECT_OR_GROUP = fromString("DirectoryObjectOrGroup");

    /** Static value Everyone for PrincipalType. */
    public static final PrincipalType EVERYONE = fromString("Everyone");

    /**
     * Creates or finds a PrincipalType from its string representation.
     * @param name a name to look for
     * @return the corresponding PrincipalType
     */
    @JsonCreator
    public static PrincipalType fromString(String name) {
        return fromString(name, PrincipalType.class);
    }

    /**
     * @return known PrincipalType values
     */
    public static Collection<PrincipalType> values() {
        return values(PrincipalType.class);
    }
}
