/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.graphrbac;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.graphrbac.implementation.PermissionInner;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

import java.util.List;

/**
 * An immutable client-side representation of a permission.
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.Graph.RBAC.Fluent")
@Beta(SinceVersion.V1_8_0)
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
