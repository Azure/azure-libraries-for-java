/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.graphrbac.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.graphrbac.Permission;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

import java.util.List;

/**
 * Implementation for Permission and its parent interfaces.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Graph.RBAC.Fluent")
class PermissionImpl
        extends WrapperImpl<PermissionInner>
        implements Permission {
    protected PermissionImpl(PermissionInner innerObject) {
        super(innerObject);
    }

    @Override
    public List<String> actions() {
        return inner().actions();
    }

    @Override
    public List<String> notActions() {
        return inner().notActions();
    }

    @Override
    public List<String> dataActions() {
        return inner().dataActions();
    }

    @Override
    public List<String> notDataActions() {
        return inner().notDataActions();
    }
}
