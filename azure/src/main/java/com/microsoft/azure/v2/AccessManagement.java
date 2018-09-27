/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2;

import com.microsoft.azure.v2.management.graphrbac.ActiveDirectoryApplications;
import com.microsoft.azure.v2.management.graphrbac.ActiveDirectoryGroups;
import com.microsoft.azure.v2.management.graphrbac.ActiveDirectoryUsers;
import com.microsoft.azure.v2.management.graphrbac.RoleAssignments;
import com.microsoft.azure.v2.management.graphrbac.RoleDefinitions;
import com.microsoft.azure.v2.management.graphrbac.ServicePrincipals;
import com.microsoft.rest.v2.annotations.Beta;

/**
 * Exposes methods related to managing access permissions in Azure.
 */
@Beta(since = "V1_2_0")
public interface AccessManagement {
    /**
     * Entry point to AD user management APIs.
     *
     * @return ActiveDirectoryUsers interface providing access to tenant management
     */
    ActiveDirectoryUsers activeDirectoryUsers();

    /**
     * Entry point to AD group management APIs.
     *
     * @return ActiveDirectoryGroups interface providing access to tenant management
     */
    ActiveDirectoryGroups activeDirectoryGroups();

    /**
     * Entry point to AD service principal management APIs.
     *
     * @return ServicePrincipals interface providing access to tenant management
     */
    ServicePrincipals servicePrincipals();

    /**
     * Entry point to AD application management APIs.
     *
     * @return Applications interface providing access to tenant management
     */
    ActiveDirectoryApplications activeDirectoryApplications();

    /**
     * Entry point to role definition management APIs.
     *
     * @return RoleDefinitions interface providing access to tenant management
     */
    RoleDefinitions roleDefinitions();

    /**
     * Entry point to role assignment management APIs.
     *
     * @return RoleAssignments interface providing access to tenant management
     */
    RoleAssignments roleAssignments();
}
