/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.model.implementation;

import com.microsoft.azure.management.resources.fluentcore.dag.TaskGroup;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Executable;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.Objects;

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
        this(SdkContext.randomUuid());
    }

    /**
     * Creates ExecutableImpl.
     *
     * @param key the task group key for the executable
     */
    protected ExecutableImpl(String key) {
        super(key);
        taskGroup = new TaskGroup(this.key(),
                new ExecuteTask(this));
    }

    @Override
    public TaskGroup taskGroup() {
        return this.taskGroup;
    }

    /**
     * Add a dependency for this model.
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
     * Add a creatable dependency for this executable model.
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
     * Add an updatable dependency for this executable.
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
     * Add an executable dependency for this executable.
     *
     * @param executable the executable dependency
     * @return key of the executable dependency
     */
    @SuppressWarnings("unchecked")
    protected String addDependency(Executable<? extends Indexable> executable) {
        TaskGroup.HasTaskGroup dependency = (TaskGroup.HasTaskGroup) executable;
        return this.addDependency(dependency);
    }

    /**
     * Add a "post-run" dependent for this model.
     *
     * @param dependent the "post-run" dependent.
     */
    protected void addPostRunDependent(TaskGroup.HasTaskGroup dependent) {
        Objects.requireNonNull(dependent);
        this.taskGroup.addPostRunDependentTaskGroup(dependent.taskGroup());
    }

    /**
     * Add a creatable "post-run" dependent for this executable model.
     *
     * @param creatable the creatable "post-run" dependent.
     */
    @SuppressWarnings("unchecked")
    protected void addPostRunDependent(Creatable<? extends Indexable> creatable) {
        TaskGroup.HasTaskGroup dependency = (TaskGroup.HasTaskGroup) creatable;
        this.addPostRunDependent(dependency);
    }

    /**
     * Add an appliable "post-run" dependent for this executable.
     *
     * @param appliable the appliable "post-run" dependent.
     */
    @SuppressWarnings("unchecked")
    protected void addPostRunDependent(Appliable<? extends Indexable> appliable) {
        TaskGroup.HasTaskGroup dependency = (TaskGroup.HasTaskGroup) appliable;
        this.addPostRunDependent(dependency);
    }

    /**
     * Add an executable "post-run" dependent for this executable.
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