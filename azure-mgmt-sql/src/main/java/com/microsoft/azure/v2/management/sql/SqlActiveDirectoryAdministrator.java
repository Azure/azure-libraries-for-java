/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;

/**
 * Response containing the Azure SQL Active Directory administrator.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_7_0)
public interface SqlActiveDirectoryAdministrator {
    /**
     * @return the type of administrator.
     */
    String administratorType();

    /**
     * @return the server administrator login value.
     */
    String signInName();

    /**
     * @return the server administrator ID.
     */
    String id();

    /**
     * @return the server Active Directory Administrator tenant ID.
     */
    String tenantId();
}
