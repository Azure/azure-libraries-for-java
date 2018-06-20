/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.batchai.implementation.BatchAIManager;
import com.microsoft.azure.management.batchai.implementation.ExperimentInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import org.joda.time.DateTime;

/**
 * Entry point for Batch AI Experiment management API in Azure.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_12_0)
public interface BatchAIExperiment extends
        HasInner<ExperimentInner>,
        Indexable,
        HasId,
        HasName,
        HasManager<BatchAIManager>,
        Refreshable<BatchAIExperiment> {

    /**
     * @return the entry point to Batch AI jobs management API for this experiment
     */
    BatchAIJobs jobs();

    /**
     * @return time when the Experiment was created
     */
    DateTime creationTime();

    /**
     * @return the provisioned state of the experiment
     */
    ProvisioningState provisioningState();

    /**
     * @return the time at which the experiment entered its current provisioning state
     */
    DateTime provisioningStateTransitionTime();

    /**
     * @return workspace this experiment belongs to
     */
    BatchAIWorkspace workspace();

    /**
     * The entirety of a Batch AI experiment definition.
     */
    interface Definition extends DefinitionStages.WithCreate {
    }

    /**
     * Grouping of Batch AI experiment definition stages.
     */
    interface DefinitionStages {

        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be created
         * but also allows for any other optional settings to be specified.
         */
        interface WithCreate extends
                Creatable<BatchAIExperiment> {
        }
    }
}
