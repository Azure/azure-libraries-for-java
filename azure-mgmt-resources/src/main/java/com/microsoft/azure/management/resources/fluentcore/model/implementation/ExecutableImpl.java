/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.model.implementation;

import com.microsoft.azure.management.resources.fluentcore.dag.TaskGroup;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Executable;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

/**
 * The base class for all executable model.
 *
 * @param <FluentModelT> the fluent model type
 */
public abstract class ExecutableImpl<FluentModelT extends Indexable>
        extends
        IndexableImpl
        implements
        TaskGroup.HasTaskGroup,
        Executable<FluentModelT>,
        ExecuteTask.Executor<FluentModelT> {
    /**
     * The group of tasks to the produces this result and it's dependencies results.
     */
    private final TaskGroup taskGroup;

    /**
     * Creates ExecutableImpl.
     */
    protected ExecutableImpl() {
        taskGroup = new TaskGroup(this.key(),
                new ExecuteTask(this));
    }

    @Override
    public TaskGroup taskGroup() {
        return this.taskGroup;
    }

    /**
     * Add a creatable dependency for the executable model.
     *
     * @param creatable the creatable dependency.
     */
    @SuppressWarnings("unchecked")
    protected void addCreatableDependency(Creatable<? extends Indexable> creatable) {
        TaskGroup.HasTaskGroup dependency = (TaskGroup.HasTaskGroup) creatable;
        assert dependency != null;
        this.taskGroup().addDependencyTaskGroup(dependency.taskGroup());
    }

    /**
     * Add an executable dependency for this executable model.
     *
     * @param executable the executable dependency
     */
    @SuppressWarnings("unchecked")
    protected void addExecutableDependency(Executable<? extends Indexable> executable) {
        TaskGroup.HasTaskGroup dependency = (TaskGroup.HasTaskGroup) executable;
        assert dependency != null;
        this.taskGroup().addDependencyTaskGroup(dependency.taskGroup());
    }

    @Override
    public void beforeGroupExecute() {
    }

    @Override
    public boolean isHot() {
        return false;
    }

    @Override
    public Observable<FluentModelT> executeAsync() {
        return taskGroup.invokeAsync(taskGroup.newInvocationContext())
                .last()
                .map(new Func1<Indexable, FluentModelT>() {
                    @Override
                    public FluentModelT call(Indexable indexable) {
                        return (FluentModelT) indexable;
                    }
                });
    }

    @Override
    public FluentModelT execute() {
        return executeAsync().toBlocking().last();
    }

    @Override
    public ServiceFuture<FluentModelT> executeAsync(ServiceCallback<FluentModelT> callback) {
        return ServiceFuture.fromBody(executeAsync(), callback);
    }

    @Override
    public Completable afterPostRunAsync(boolean isGroupFaulted) {
        return Completable.complete();
    }
}