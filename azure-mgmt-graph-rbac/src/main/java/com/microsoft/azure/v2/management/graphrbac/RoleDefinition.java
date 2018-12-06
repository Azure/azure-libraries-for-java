/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.graphrbac;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.graphrbac.implementation.GraphRbacManager;
import com.microsoft.azure.v2.management.graphrbac.implementation.RoleDefinitionInner;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.rest.v2.annotations.Beta;

import java.util.Set;

/**
 * An immutable client-side representation of an Azure AD role definition.
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.Graph.RBAC.Fluent")
@Beta(since = "1.1.0")
public interface RoleDefinition extends
        HasInner<RoleDefinitionInner>,
        HasId,
        HasName,
        HasManager<GraphRbacManager> {
    /**
     * @return the role name
     */
    String roleName();

    /**
     * @return the role definition description
     */
    String description();

    /**
     * @return the role type
     */
    String type();

    /**
     * @return role definition permissions
     */
    Set<Permission> permissions();

    /**
     * @return role definition assignable scopes
     */
    Set<String> assignableScopes();
}
