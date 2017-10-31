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
 * Defines values for Kubernetes versions.
 */
@Fluent()
@Beta(Beta.SinceVersion.V1_4_0)
public final class KubernetesVersion extends ExpandableStringEnum<KubernetesVersion> {
    /** Static value Kubernetes version 1.7.7. */
    public static final KubernetesVersion KUBERNETES_1_7_7 = fromString("1.7.7");

    /** Static value Kubernetes version 1.7.7. */
    public static final KubernetesVersion KUBERNETES_1_8_1 = fromString("1.8.1");

    /**
     * Creates or finds a Kubernetes version from its string representation.
     * @param name a name to look for
     * @return the corresponding Kubernetes version
     */
    @JsonCreator
    public static KubernetesVersion fromString(String name) {
        return fromString(name, KubernetesVersion.class);
    }

    /**
     * @return known SkuName values
     */
    public static Collection<KubernetesVersion> values() {
        return values(KubernetesVersion.class);
    }
}
