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
 * Grouping of container registry build steps actions.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_14_0)
public interface BuildStepOperations {
    /**
     * Begins the definition of a new build step for the current build task.
     *
     * @param buildStepName the name of the new build step
     * @return the first stage of the new build step definition
     */
    BuildStep.BuildTaskBuildStepsDefinitionStages.Blank define(String buildStepName);

    /**
     * Gets the properties of the specified build step.
     *
     * @param buildStepName the name of the build step
     * @return the BuildTask object if successful
     */
    BuildStep get(String buildStepName);

    /**
     * Gets the properties of the specified build step.
     *
     * @param buildStepName the name of the build step
     * @return a representation of the future computation of this call, returning the build step object
     */
    Observable<BuildStep> getAsync(String buildStepName);

    /**
     * Deletes a build step from the build task of a container registry.
     *
     * @param buildStepName the name of the build step
     */
    void delete(String buildStepName);

    /**
     * Deletes a build step from the build task of a container registry asynchronously.
     *
     * @param buildStepName the name of the build step
     * @return a representation of the future computation of this call
     */
    Completable deleteAsync(String buildStepName);

    /**
     * Lists all the build steps for current build task of the container registry.
     *
     * @return the list of all the build steps for the current build task of the container registry
     */
    PagedList<BuildStep> list();

    /**
     * Lists all the build steps for the current build task of the container registry.
     *
     * @return a representation of the future computation of this call, returning the list of all the build steps
     *   for the current build task of the container registry
     */
    Observable<BuildStep> listAsync();
}
