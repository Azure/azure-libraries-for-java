/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.ExternalChildResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasParent;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;

/**
 * Entry point for Batch AI Experiment management API in Azure.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_11_0)
public interface Experiment extends ExternalChildResource<Experiment, Workspace>,
        Refreshable<Experiment>,
        HasParent<Workspace> {
}
