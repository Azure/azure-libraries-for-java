/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.graphrbac.implementation;

import com.azure.management.graphrbac.Permission;
import com.azure.management.graphrbac.RoleDefinition;
import com.azure.management.graphrbac.models.PermissionInner;
import com.azure.management.graphrbac.models.RoleDefinitionInner;
import com.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation for ServicePrincipal and its parent interfaces.
 */
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
    public String roleName() {
        return getInner().getRoleName();
    }

    @Override
    public String description() {
        return getInner().getDescription();
    }

    @Override
    public String type() {
        return getInner().getType();
    }

    @Override
    public Set<Permission> permissions() {
        HashSet<Permission> ret = new HashSet<>();
        for (PermissionInner inner : getInner().getPermissions()) {
            ret.add(new PermissionImpl(inner));
        }
        return ret;
    }

    @Override
    public Set<String> assignableScopes() {
        return Collections.unmodifiableSet(Sets.newHashSet(getInner().getAssignableScopes()));
    }

    @Override
    public String getId() {
        return getInner().getId();
    }

    @Override
    public GraphRbacManager getManager() {
        return this.manager;
    }

    @Override
    public String getName() {
        return getInner().getName();
    }
}
