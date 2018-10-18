///**
// * Copyright (c) Microsoft Corporation. All rights reserved.
// * Licensed under the MIT License. See License.txt in the project root for
// * license information.
// */
//package com.microsoft.azure.management.containerregistry;
//
//import com.microsoft.azure.PagedList;
//import com.microsoft.azure.management.apigeneration.Beta;
//import com.microsoft.azure.management.apigeneration.Fluent;
//import rx.Completable;
//import rx.Observable;
//
///**
// * Grouping of container registry build task actions.
// */
//@Fluent
//@Beta(Beta.SinceVersion.V1_14_0)
//public interface BuildTaskOperations {
//
//    /**
//     * Begins the definition of a new build task for the current container registry.
//     *
//     * @param buildTaskName the name of the new build task
//     * @return the first stage of the new build task definition
//     */
//    BuildTask.DefinitionStages.Blank define(String buildTaskName);
//
//    /**
//     * Gets the properties of the specified build task.
//     *
//     * @param buildTaskName the name of the build task
//     * @return the BuildTask object if successful
//     */
//    BuildTask get(String buildTaskName);
//
//    /**
//     * Gets the properties of the specified build task.
//     *
//     * @param buildTaskName the name of the build task
//     * @return a representation of the future computation of this call, returning the build task object
//     */
//    Observable<BuildTask> getAsync(String buildTaskName);
//
//    /**
//     * Gets the source control properties for a build task.
//     *
//     * @param buildTaskName the name of the build task
//     * @return the source control properties object if successful
//     */
//    SourceRepositoryProperties getSourceRepositoryProperties(String buildTaskName);
//
//    /**
//     * Gets the source control properties for a build task.
//     *
//     * @param buildTaskName the name of the build task
//     * @return a representation of the future computation of this call, returning the source control properties object
//     */
//    Observable<SourceRepositoryProperties> getSourceRepositoryPropertiesAsync(String buildTaskName);
//
//    /**
//     * Deletes a build task from the container registry.
//     *
//     * @param buildTaskName the name of the build task
//     */
//    void delete(String buildTaskName);
//
//    /**
//     * Deletes a build task from the container registry.
//     *
//     * @param buildTaskName the name of the build task
//     * @return a representation of the future computation of this call
//     */
//    Completable deleteAsync(String buildTaskName);
//
//    /**
//     * Lists all the build tasks for the container registry.
//     *
//     * @return the list of all the build tasks for the specified container registry
//     */
//    PagedList<BuildTask> list();
//
//    /**
//     * Lists all the build tasks for the container registry.
//     *
//     * @return a representation of the future computation of this call, returning the list of all the build tasks
//     *   for the specified container registry
//     */
//    Observable<BuildTask> listAsync();
//
//    /**
//     * Lists all the build tasks for the container registry matching the specified filter.
//     *
//     * @param filter the specified filter
//     * @return the list of all the build tasks for the specified container registry
//     */
//    PagedList<BuildTask> listWithFilter(String filter);
//
//    /**
//     * Lists all the build tasks for the container registry matching the specified filter.
//     *
//     * @param filter the specified filter
//     * @return a representation of the future computation of this call, returning the list of all the build tasks
//     *   for the specified container registry
//     */
//    Observable<BuildTask> listWithFilterAsync(String filter);
//
//}
