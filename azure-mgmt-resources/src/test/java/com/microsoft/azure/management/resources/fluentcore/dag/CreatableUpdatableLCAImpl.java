/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.dag;

import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.CreateUpdateTask;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableRefreshableWrapperImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import com.microsoft.rest.ServiceFuture;
import com.microsoft.rest.ServiceCallback;
import rx.Completable;
import rx.Observable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

abstract class CreatableUpdatableLCAImpl<
        FluentModelT extends Indexable,
        InnerModelT>
        extends IndexableRefreshableWrapperImpl<FluentModelT, InnerModelT>
        implements
        Creatable<FluentModelT>,
        TaskGroup.HasTaskGroup,
        CreateUpdateTask.ResourceCreatorUpdater<FluentModelT> {
    private final String name;
    private final TaskGroup taskGroup;

    protected CreatableUpdatableLCAImpl(String name, InnerModelT innerObject) {
        super(innerObject);
        this.name = name;
        taskGroup = new TaskGroup(this.key(),
                new CreateUpdateTask<>(this),
                TaskGroupTerminateOnErrorStrategy.TERMINATE_ON_HITTING_LCA_TASK);
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public TaskGroup taskGroup() {
        return this.taskGroup;
    }

    @SuppressWarnings("unchecked")
    protected void addCreatableDependency(Creatable<? extends Indexable> creatable) {
        TaskGroup.HasTaskGroup dependency =
                (TaskGroup.HasTaskGroup) creatable;
        this.taskGroup().addDependencyTaskGroup(dependency.taskGroup());
    }

    @Override
    public void beforeGroupCreateOrUpdate() {
    }

    @Override
    public boolean isHot() {
        return false;
    }

    @Override
    public Observable<Indexable> createAsync() {
        return taskGroup.invokeAsync(taskGroup.newInvocationContext());
    }

    @Override
    public ServiceFuture<FluentModelT> createAsync(final ServiceCallback<FluentModelT> callback) {
        throw new NotImplementedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentModelT create() {
        return Utils.<FluentModelT>rootResource(createAsync()).toBlocking().single();
    }

    @Override
    public Observable<FluentModelT> updateResourceAsync() {
        return this.createResourceAsync();
    }

    @Override
    public abstract Observable<FluentModelT> createResourceAsync();

    @Override
    public Completable afterPostRunAsync(boolean isGroupFaulted) {
        return Completable.complete();
    }
}
