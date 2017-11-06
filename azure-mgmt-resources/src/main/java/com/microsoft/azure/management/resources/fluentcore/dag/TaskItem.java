/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.dag;

import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import rx.Completable;
import rx.Observable;

/**
 * Type representing a unit of work, upon invocation produces result of {@link Indexable} type.
 * <p>
 * The {@link TaskGroup} holds a group of these TaskItems those depends on each other.
 */
public interface TaskItem {
    /**
     * @return the result of the task invocation
     */
    Indexable result();

    /**
     * The method that gets called before invoking all the tasks in the {@link TaskGroup}
     * this task belongs to.
     */
    void beforeGroupInvoke();

    /**
     * @return true if the observable returned by invokeAsync(cxt) is a hot observable,
     * false if its a cold observable.
     */
    boolean isHot();

    /**
     * The method that gets called to perform the unit of work asynchronously.
     *
     * @param context the context shared across the the all task items in the group
     *               this task item belongs to.
     * @return an observable upon subscription does the unit of work and produces
     * result of type {@link Indexable}
     */
    Observable<Indexable> invokeAsync(TaskGroup.InvocationContext context);

    /**
     * The method that gets called after invocation of "post run" task items depends on
     * this TaskItem.
     * <p>
     * This method will be invoked only if this TaskItem had "post run" dependents.
     *
     * @param isGroupFaulted true if one or more tasks in the group this TaskItem belongs
     *                       to are in faulted state.
     * @return a completable representing any asynchronous work initiated
     */
    Completable invokeAfterPostRunAsync(boolean isGroupFaulted);
}
