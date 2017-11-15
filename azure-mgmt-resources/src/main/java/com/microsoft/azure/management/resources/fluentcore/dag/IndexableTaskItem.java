/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.dag;

import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.UUID;

/**
 * An index-able TaskItem with a TaskGroup.
 */
public abstract class IndexableTaskItem implements Indexable, TaskItem, TaskGroup.HasTaskGroup {
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
        this(UUID.randomUUID().toString());
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
     * Add a {@link IndexableTaskItem} dependency for this task item.
     *
     * @param dependency the dependency
     */
    public void addDependency(IndexableTaskItem dependency) {
        this.taskGroup().addDependencyTaskGroup(dependency.taskGroup());
    }

    /**
     * Add a {@link IndexableTaskItem} post-run dependency for this task item.
     *
     * @param dependent the post-run dependent
     */
    public void addPostRunDependent(IndexableTaskItem dependent) {
        this.taskGroup().addPostRunDependentTaskGroup(dependent.taskGroup());
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