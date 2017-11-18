/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.resources.fluentcore.arm.models.implementation;

import com.microsoft.azure.management.resources.fluentcore.arm.models.ExternalChildResource;
import com.microsoft.azure.management.resources.fluentcore.dag.IndexableTaskItem;
import com.microsoft.azure.management.resources.fluentcore.dag.TaskGroup;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

/**
 * Externalized child resource abstract implementation.
 *
 * Inorder to be eligible for an external child resource following criteria must be satisfied:
 * 1. It's is always associated with a parent resource and has no existence without parent
 *    i.e. if you delete parent then child resource will be deleted automatically.
 * 2. Parent may or may not contain collection of child resources (i.e. as inline collection property).
 * 3. It's has an ID and can be created, updated, fetched and deleted independent of the parent
 *    i.e. CRUD on child resource does not require CRUD on the parent
 * (Internal use only)
 *
 * @param <FluentModelT> the fluent model type of the child resource
 * @param <InnerModelT> Azure inner resource class type representing the child resource
 * @param <ParentImplT> the parent Azure resource impl class type that implements {@link ParentT}
 * @param <ParentT> parent interface
 */
public abstract class ExternalChildResourceImpl<FluentModelT extends Indexable,
        InnerModelT,
        ParentImplT extends ParentT,
        ParentT>
        extends
            ChildResourceImpl<InnerModelT, ParentImplT, ParentT>
        implements
            TaskGroup.HasTaskGroup,
            ExternalChildResource<FluentModelT, ParentT>,
            Refreshable<FluentModelT> {
    /**
     * State representing any pending action that needs to be performed on this child resource.
     */
    private PendingOperation pendingOperation = PendingOperation.None;
    /**
     * The child resource name.
     */
    private final String name;
    /**
     * TaskItem in the graph to perform action on this external child resource.
     */
    private final ExternalChildActionTaskItem childAction;

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param name the name of this external child resource
     * @param parent reference to the parent of this external child resource
     * @param innerObject reference to the inner object representing this external child resource
     */
    protected ExternalChildResourceImpl(String name,
                                        ParentImplT parent,
                                        InnerModelT innerObject) {
        super(innerObject, parent);
        this.childAction = new ExternalChildActionTaskItem(this);
        this.name = name;
    }

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param key the task group key for the task item that perform actions on this child
     * @param name the name of this external child resource
     * @param parent reference to the parent of this external child resource
     * @param innerObject reference to the inner object representing this external child resource
     */
    protected ExternalChildResourceImpl(String key,
                                        String name,
                                        ParentImplT parent,
                                        InnerModelT innerObject) {
        super(innerObject, parent);
        this.childAction = new ExternalChildActionTaskItem(key, this);
        this.name = name;
    }

    @Override
    public String name() {
        return this.name;
    }

    /**
     * @return the operation pending on this child resource.
     */
    public PendingOperation pendingOperation() {
        return this.pendingOperation;
    }

    /**
     * Update the operation state.
     *
     * @param pendingOperation the new state of this child resource
     */
    public void setPendingOperation(PendingOperation pendingOperation) {
        this.pendingOperation = pendingOperation;
    }

    /**
     * Mark that there is no action pending on this child resource and clear
     * any cached result, i.e. the output produced by the invocation of last
     * action.
     */
    public void clear() {
        this.setPendingOperation(PendingOperation.None);
        this.childAction.clear();
    }

    /**
     * @return the task group associated with this external child resource.
     */
    @Override
    public TaskGroup taskGroup() {
        return this.childAction.taskGroup();
    }

    /**
     * Creates this external child resource.
     *
     * @return the observable to track the create action
     */
    public abstract Observable<FluentModelT> createAsync();

    /**
     * Update this external child resource.
     *
     * @return the observable to track the update action
     */
    public abstract Observable<FluentModelT> updateAsync();

    /**
     * Delete this external child resource.
     *
     * @return the observable to track the delete action.
     */
    public abstract Observable<Void> deleteAsync();

    /**
     * @return the key of this child resource in the collection maintained by ExternalChildResourceCollectionImpl
     */
    public String childResourceKey() {
        return name();
    }

    @Override
    public final FluentModelT refresh() {
        return refreshAsync().toBlocking().last();
    }

    @Override
    public Observable<FluentModelT> refreshAsync() {
        final ExternalChildResourceImpl<FluentModelT, InnerModelT, ParentImplT, ParentT> self = this;
        return this.getInnerAsync().map(new Func1<InnerModelT, FluentModelT>() {
            @Override
            public FluentModelT call(InnerModelT innerModelT) {
                self.setInner(innerModelT);
                return (FluentModelT) self;
            }
        });
    }

    protected abstract Observable<InnerModelT> getInnerAsync();

    protected Completable afterPostRunAsync(boolean isGroupFaulted) {
        return Completable.complete();
    }

    /**
     * The possible operation pending on a child resource in-memory.
     */
    public enum PendingOperation {
        /**
         * No action needs to be taken on resource.
         */
        None,
        /**
         * Child resource required to be created.
         */
        ToBeCreated,
        /**
         * Child resource required to be updated.
         */
        ToBeUpdated,
        /**
         * Child resource required to be deleted.
         */
        ToBeRemoved
    }

    /**
     * A TaskItem in the graph, when invoked performs actions (create, update or delete) on an
     * external child resource it composes.
     */
    private class ExternalChildActionTaskItem extends IndexableTaskItem {
        /**
         * The composed external child resource.
         */
        private final ExternalChildResourceImpl<FluentModelT, InnerModelT, ParentImplT, ParentT> externalChild;

        /**
         * Creates ExternalChildActionTaskItem.
         *
         * @param externalChild an external child this TaskItem composes.
         */
        ExternalChildActionTaskItem(final ExternalChildResourceImpl<FluentModelT, InnerModelT, ParentImplT, ParentT> externalChild) {
            this.externalChild = externalChild;
        }

        /**
         * Creates ExternalChildActionTaskItem.
         *
         * @param key the task group key for this item
         * @param externalChild an external child this TaskItem composes.
         */
        ExternalChildActionTaskItem(final String key, final ExternalChildResourceImpl<FluentModelT, InnerModelT, ParentImplT, ParentT> externalChild) {
            super(key);
            this.externalChild = externalChild;
        }

        @Override
        public Observable<Indexable> invokeTaskAsync(TaskGroup.InvocationContext context) {
            switch (this.externalChild.pendingOperation()) {
                case ToBeCreated:
                    return this.externalChild.createAsync()
                            .map(new Func1<FluentModelT, Indexable>() {
                                @Override
                                public Indexable call(FluentModelT fluentModelT) {
                                    return fluentModelT;
                                }
                            });
                case ToBeUpdated:
                    return this.externalChild.updateAsync()
                            .map(new Func1<FluentModelT, Indexable>() {
                                @Override
                                public Indexable call(FluentModelT fluentModelT) {
                                    return fluentModelT;
                                }
                            });
                case ToBeRemoved:
                    return this.externalChild.deleteAsync()
                            .map(new Func1<Void, Indexable>() {
                                @Override
                                public Indexable call(Void aVoid) {
                                    return voidIndexable();
                                }
                            });
                default:
                    // PendingOperation.None
                    return Observable.error(new IllegalStateException("No action pending on child resource: " + externalChild.name + ", invokeAsync should not be called "));
            }
        }

        @Override
        public Completable invokeAfterPostRunAsync(boolean isGroupFaulted) {
            return this.externalChild.afterPostRunAsync(isGroupFaulted);
        }
    }
}