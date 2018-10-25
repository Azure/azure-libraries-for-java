/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;

/**
 * An immutable client-side representation of collection of Azure registry task runs.
 */
@Fluent()
@Beta(Beta.SinceVersion.V1_1_0)
public interface RegistryTaskRuns {

    /**
     * The function that begins the steps to schedule a run.
     *
     * @return the next step in the execution of a run.
     */
    RegistryTaskRun.DefinitionStages.BlankFromRuns scheduleRun();
}
