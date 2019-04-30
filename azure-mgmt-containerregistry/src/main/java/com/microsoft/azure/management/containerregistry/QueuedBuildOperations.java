/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry;
//
//import com.microsoft.azure.PagedList;
//import com.microsoft.azure.management.apigeneration.Beta;
//import com.microsoft.azure.management.apigeneration.Fluent;
//import rx.Completable;
//import rx.Observable;
//
/**
 * Grouping of container registry queued build actions.
 */
public interface QueuedBuildOperations {

}
//@Fluent
//@Beta(Beta.SinceVersion.V1_14_0)
//public interface QueuedBuildOperations {
//    /**
//     * Queues a build task to build for the current container registry.
//     *
//     * @param buildTaskName the name of build task to be queued
//     * @return returns the build object.
//     */
//    Build queueBuildTask(String buildTaskName);
//
//    /**
//     * Queues a build task to build for the current container registry.
//     *
//     * @param buildTaskName the name of build task to be queued
//     * @return returns the build object.
//     */
//    Observable<Build> queueBuildTaskAsync(String buildTaskName);
//
//    /**
//     * Creates a new quick build based on the request parameters and adds it to the build queue.
//     *
//     * @return returns the build object.
//     */
//    Build.QueuedQuickBuildDefinitionStages.Blank queueQuickBuild();
//
//    /**
//     * Gets the properties of the specified queued build.
//     *
//     * @param buildId the ID of the queued build
//     * @return the Build object if successful
//     */
//    Build get(String buildId);
//
//    /**
//     * Gets the properties of the specified queued build.
//     *
//     * @param buildId the ID of the queued build
//     * @return a representation of the future computation of this call, returning the build object
//     */
//    Observable<Build> getAsync(String buildId);
//
//    /**
//     * Cancels a queued build for the container registry.
//     *
//     * @param buildId the ID of the queued build
//     */
//    void cancel(String buildId);
//
//    /**
//     * Cancels a queued build for the container registry asynchronously.
//     *
//     * @param buildId the ID of the queued build
//     * @return a representation of the future computation of this call
//     */
//    Completable cancelAsync(String buildId);
//
//    /**
//     * Lists all the queued build for the container registry.
//     *
//     * @return the list of all the queued build for the specified container registry
//     */
//    PagedList<Build> list();
//
//    /**
//     * Lists all the build tasks for the container registry.
//     *
//     * @return a representation of the future computation of this call, returning the list of all the queued builds
//     *   for the specified container registry
//     */
//    Observable<Build> listAsync();
//
//    /**
//     * Lists all the queued builds for the container registry matching the specified filter.
//     *
//     * @param filter the specified filter
//     * @param top the maximum nuber of queued builds to return
//     * @return the list of all the queued builds for the specified container registry
//     */
//    PagedList<Build> listWithFilterAndTop(String filter, int top);
//
//    /**
//     * Lists all the queued builds for the container registry matching the specified filter.
//     *
//     * @param filter the specified filter
//     * @param top the maximum nuber of queued builds to return
//     * @return a representation of the future computation of this call, returning the list of all the queued builds
//     *   for the specified container registry
//     */
//    Observable<Build> listWithFilterAndTopAsync(String filter, int top);
//
//}
