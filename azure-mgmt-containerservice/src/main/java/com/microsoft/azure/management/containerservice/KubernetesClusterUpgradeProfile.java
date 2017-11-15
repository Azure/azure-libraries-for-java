/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerservice;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.containerservice.implementation.ManagedClusterUpgradeProfileInner;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * The result of checking for the Kubernetes cluster's upgrade profile.
 */
@Fluent()
@Beta(Beta.SinceVersion.V1_4_0)
public interface KubernetesClusterUpgradeProfile extends HasInner<ManagedClusterUpgradeProfileInner> {
    /**
     * @return the ID of the Kubernetes cluster upgrade profile
     */
    String id();

    /**
     * @return the name of the Kubernetes cluster upgrade profile
     */
    String name();

    /**
     * @return the type of the Kubernetes cluster upgrade profile.
     */
    String type();
}
