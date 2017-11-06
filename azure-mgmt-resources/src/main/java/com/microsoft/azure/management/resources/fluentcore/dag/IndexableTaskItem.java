/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.dag;

import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import rx.Completable;
import rx.Observable;

import java.util.UUID;

/**
 * Type representing a TaskItem which is indexable.
 */
public abstract class IndexableTaskItem implements Indexable, TaskItem {
    private final String key;
    private VoidIndexable voidIndexable;

    /**
     * Creates IndexableTaskItem.
     *
     * @param key the indexable key
     */
    public IndexableTaskItem(String key) {
        this.key = key;
    }

    /**
     * Creates IndexableTaskItem with key as random guid.
     */
    public IndexableTaskItem() {
        this(UUID.randomUUID().toString());
    }

    @Override
    public String key() {
        return this.key;
    }

    @Override
    public abstract Indexable result();

    @Override
    public void beforeGroupInvoke() {
        // NOP
    }

    @Override
    public boolean isHot() {
        return false;
    }

    @Override
    public abstract Observable<Indexable> invokeAsync(TaskGroup.InvocationContext context);

    @Override
    public Completable invokeAfterPostRunAsync(boolean isGroupFaulted) {
        return Completable.complete();
    }

    /**
     * A helper method that returns an instance of VoidIndexable with key same as
     * the key of this TaskItem.
     * <p>
     * Returning VoidIndexable from result() and invokeAsync() returned Observable
     * indicates that the task does not produce any value.
     *
     * @return a VoidIndexable.
     */
    protected VoidIndexable voidIndexable() {
        if (this.voidIndexable == null) {
            this.voidIndexable = new VoidIndexable(this.key);
        }
        return this.voidIndexable;
    }
}