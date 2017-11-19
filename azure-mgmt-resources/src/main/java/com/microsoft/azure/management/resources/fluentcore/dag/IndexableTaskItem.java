/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.dag;

import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Executable;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.Objects;

/**
 * An index-able TaskItem with a TaskGroup.
 */
public abstract class IndexableTaskItem
        implements Indexable, TaskItem, TaskGroup.HasTaskGroup {
    /**
     * The key that is unique to this TaskItem which is used to index this
     * TaskItem.
     */
    private final String key;
    /**
     * The TaskGroup with this TaskItem as root task.
     */
    private final TaskGroup taskGroup;
    /**
     * The result of computation performed by this TaskItem.
     */
    private Indexable taskResult;

    /**
     * Creates a TaskItem which is index-able using provided key.
     *
     * @param key the unique key to index this TaskItem
     */
    public IndexableTaskItem(String key) {
        this.key = key;
        this.taskGroup = new TaskGroup(this);
        this.taskResult = null;
    }

    /**
     * Creates a TaskItem which is index-able using a random UUID.
     */
    public IndexableTaskItem() {
        this(SdkContext.randomUuid());
    }

    /**
     * @return the TaskGroup this this TaskItem as root.
     */
    @Override
    public TaskGroup taskGroup() {
        return this.taskGroup;
    }

    /**
     * Clear the result produced by the task.
     */
    public void clear() {
        this.taskResult = voidIndexable();
    }

    @Override
    public String key() {
        return this.key;
    }

    /**
     * Add a dependency for this task item.
     *
     * @param dependency the dependency.
     * @return key of the dependency task group
     */
    protected String addDependency(TaskGroup.HasTaskGroup dependency) {
        Objects.requireNonNull(dependency);
        this.taskGroup.addDependencyTaskGroup(dependency.taskGroup());
        return dependency.taskGroup().key();
    }

    /**
     * Add a creatable dependency for this this task item.
     *
     * @param creatable the creatable dependency.
     * @return key of the creatable dependency
     */
    @SuppressWarnings("unchecked")
    protected String addDependency(Creatable<? extends Indexable> creatable) {
        TaskGroup.HasTaskGroup dependency = (TaskGroup.HasTaskGroup) creatable;
        return this.addDependency(dependency);
    }

    /**
     * Add an appliable dependency for this this task item.
     *
     * @param appliable the appliable dependency.
     * @return key of the appliable dependency
     */
    @SuppressWarnings("unchecked")
    protected String addeDependency(Appliable<? extends Indexable> appliable) {
        TaskGroup.HasTaskGroup dependency = (TaskGroup.HasTaskGroup) appliable;
        return this.addDependency(dependency);
    }

    /**
     * Add a {@link IndexableTaskItem} "post-run" dependency for this task item.
     *
     * @param dependent the "post-run" dependent
     */
    public void addPostRunDependent(TaskGroup.HasTaskGroup dependent) {
        Objects.requireNonNull(dependent);
        this.taskGroup().addPostRunDependentTaskGroup(dependent.taskGroup());
    }

    /**
     * Add a creatable "post-run" dependent for this task item.
     *
     * @param creatable the creatable "post-run" dependent.
     */
    @SuppressWarnings("unchecked")
    protected void addPostRunDependent(Creatable<? extends Indexable> creatable) {
        TaskGroup.HasTaskGroup dependency = (TaskGroup.HasTaskGroup) creatable;
        this.addPostRunDependent(dependency);
    }

    /**
     * Add an appliable "post-run" dependent for this task item.
     *
     * @param appliable the appliable "post-run" dependent.
     */
    @SuppressWarnings("unchecked")
    protected void addPostRunDependent(Appliable<? extends Indexable> appliable) {
        TaskGroup.HasTaskGroup dependency = (TaskGroup.HasTaskGroup) appliable;
        this.addPostRunDependent(dependency);
    }

    /**
     * Add an executable "post-run" dependent for this task item.
     *
     * @param executable the executable "post-run" dependent
     */
    @SuppressWarnings("unchecked")
    protected void addPostRunDependent(Executable<? extends Indexable> executable) {
        TaskGroup.HasTaskGroup dependency = (TaskGroup.HasTaskGroup) executable;
        this.addPostRunDependent(dependency);
    }

    /**
     * Get result of one of the task that belongs to this task's task group.
     *
     * @param key the task key
     * @param <T> the actual type of the task result
     * @return the task result, null will be returned if task has not produced a result yet
     */
    @SuppressWarnings("unchecked")
    protected <T extends Indexable> T taskResult(String key) {
        Indexable result = this.taskGroup.taskResult(key);
        if (result == null) {
            return null;
        } else {
            T castedResult = (T) result;
            return castedResult;
        }
    }

    @Override
    public Indexable result() {
        return this.taskResult;
    }

    @Override
    public void beforeGroupInvoke() {
        // NOP
    }

    @Override
    public boolean isHot() {
        return false;
    }

    @Override
    public Observable<Indexable> invokeAsync(TaskGroup.InvocationContext context) {
        return this.invokeTaskAsync(context)
                .subscribeOn(SdkContext.getRxScheduler())
                .map(new Func1<Indexable, Indexable>() {
                    @Override
                    public Indexable call(Indexable result) {
                        taskResult = result;
                        return result;
                    }
                });
    }

    @Override
    public Completable invokeAfterPostRunAsync(boolean isGroupFaulted) {
        return Completable.complete();
    }

    protected abstract Observable<Indexable> invokeTaskAsync(TaskGroup.InvocationContext context);

    /**
     * @return an instance of {@link VoidIndexable} with key same as the key of this TaskItem.
     */
    protected Indexable voidIndexable() {
        return new VoidIndexable(this.key);
    }

    /**
     * @return an Observable upon subscription emits {@link VoidIndexable} with key same as the key of
     * this TaskItem
     */
    protected Observable<Indexable> voidObservable() {
        return Observable.just(this.voidIndexable());
    }
}