/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerservice;

import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.rest.ExpandableStringEnum;


/**
 * Defines values for Kubernetes cluster access profile roles.
 */
@Fluent()
@Beta(Beta.SinceVersion.V1_6_0)
public final class KubernetesClusterAccessProfileRole extends ExpandableStringEnum<KubernetesClusterAccessProfileRole> {
    /** Static value Kubernetes access profile user role. */
    public static final KubernetesClusterAccessProfileRole USER = fromString("clusterUser");

    /** Static value Kubernetes access profile admin role. */
    public static final KubernetesClusterAccessProfileRole ADMIN = fromString("clusterAdmin");

    /**
     * Creates or finds a Kubernetes cluster access profile role from its string representation.
     * @param name a name to look for
     * @return the corresponding Kubernetes cluster access profile role
     */
    @JsonCreator
    public static KubernetesClusterAccessProfileRole fromString(String name) {
        return fromString(name, KubernetesClusterAccessProfileRole.class);
    }

    /**
     * @return known Kubernetes cluster access profile role values
     */
    public static Collection<KubernetesClusterAccessProfileRole> values() {
        return values(KubernetesClusterAccessProfileRole.class);
    }
}
