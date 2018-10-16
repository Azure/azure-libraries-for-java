/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.graphrbac.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.graphrbac.Permission;
import com.microsoft.azure.v2.management.graphrbac.RoleDefinition;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.WrapperImpl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation for ServicePrincipal and its parent interfaces.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Graph.RBAC.Fluent")
class RoleDefinitionImpl
        extends WrapperImpl<RoleDefinitionInner>
        implements
        RoleDefinition {
    private GraphRbacManager manager;
    // Active Directory identify info
    private String objectId;
    private String userName;
    private String servicePrincipalName;
    // role info
    private String roleDefinitionId;
    private String roleName;

    RoleDefinitionImpl(RoleDefinitionInner innerObject, GraphRbacManager manager) {
        super(innerObject);
        this.manager = manager;
    }

    @Override
    public String id() {
        return inner().id();
    }

    @Override
    public GraphRbacManager manager() {
        return manager;
    }

    @Override
    public String name() {
        return inner().name();
    }

    @Override
    public String roleName() {
        return inner().roleName();
    }

    @Override
    public String description() {
        return inner().description();
    }

    @Override
    public String type() {
        return inner().type();
    }

    @Override
    public Set<Permission> permissions() {
        if (inner().permissionsProperty() == null) {
            return null;
        }
        HashSet<Permission> permissions = new HashSet<>(inner().permissionsProperty()
                .stream()
                .map(p -> new PermissionImpl(p))
                .collect(Collectors.toList()));
        return Collections.unmodifiableSet(permissions);
    }

    @Override
    public Set<String> assignableScopes() {
        if (inner().assignableScopes() == null) {
            return null;
        }
        HashSet<String> assignableScopes = new HashSet<>(inner().assignableScopes());
        return Collections.unmodifiableSet(assignableScopes);
    }
}
