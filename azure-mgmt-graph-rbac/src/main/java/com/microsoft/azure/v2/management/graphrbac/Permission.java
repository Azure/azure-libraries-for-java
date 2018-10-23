/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.graphrbac;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.graphrbac.implementation.PermissionInner;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.rest.v2.annotations.Beta;

import java.util.List;

/**
 * An immutable client-side representation of a permission.
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.Graph.RBAC.Fluent")
@Beta(since = "1.8.0")
public interface Permission extends HasInner<PermissionInner> {
    /**
     * @return allowed actions
     */
    List<String> actions();

    /**
     * @return denied actions
     */
    List<String> notActions();

    /**
     * @return allowed Data actions
     */
    List<String> dataActions();

    /**
     * @return denied Data actions
     */
    List<String> notDataActions();
}
