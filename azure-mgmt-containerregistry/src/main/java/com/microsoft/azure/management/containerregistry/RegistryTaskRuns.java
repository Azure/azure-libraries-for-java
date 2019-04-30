/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import rx.Completable;
import rx.Observable;

/**
 * An immutable client-side representation of collection of Azure registry task runs.
 */
@Fluent()
@Beta(Beta.SinceVersion.V1_17_0)
public interface RegistryTaskRuns {
    /**
     * The function that begins the steps to schedule a run.
     *
     * @return the next step in the execution of a run.
     */
    RegistryTaskRun.DefinitionStages.BlankFromRuns scheduleRun();

    /**
     * The function that lists the RegistryTaskRun instances in a registry asynchronously.
     *
     * @param rgName the resource group of the parent registry.
     * @param acrName the name of the parent registry.
     * @return the list of RegistryTaskRun instances.
     */
    Observable<RegistryTaskRun> listByRegistryAsync(String rgName, String acrName);

    /**
     * The function that lists the RegistryTaskRun instances in a registry asynch.
     *
     * @param rgName the resource group of the parent registry.
     * @param acrName the name of the parent registry.
     * @return the list of RegistryTaskRun instances.
     */
    PagedList<RegistryTaskRun> listByRegistry(String rgName, String acrName);

    /**
     * The function that returns the URI to the task run logs asynchronously.
     *
     * @param rgName the resource group of the parent registry.
     * @param acrName the name of the parent registry.
     * @param runId the id of the task run.
     * @return the URI to the task run logs.
     */
    Observable<String> getLogSasUrlAsync(String rgName, String acrName, String runId);

    /**
     * The function that returns the URI to the task run logs.
     *
     * @param rgName the resource group of the parent registry.
     * @param acrName the name of the parent registry.
     * @param runId the id of the task run.
     * @return the URI to the task run logs.
     */
    String getLogSasUrl(String rgName, String acrName, String runId);

    /**
     * The function that cancels a task run asynchronously.
     *
     * @param rgName the resource group of the parent registry.
     * @param acrName the name of the parent registry.
     * @param runId the id of the task run.
     * @return handle to the request.
     */
    Completable cancelAsync(String rgName, String acrName, String runId);

    /**
     * The function that cancels a task run.
     *
     * @param rgName the resource group of the parent registry.
     * @param acrName the name of the parent registry.
     * @param runId the id of the task run.
     */
    void cancel(String rgName, String acrName, String runId);
}
