/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.dag;

import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import rx.Observable;
import rx.functions.Func1;

/**
 * Type representing an item in {@link PostRunTaskCollection}.
 */
public interface PostRunTaskItem
        extends Func1<PostRunTaskItem.Context, Observable<Indexable>> {
    /**
     * Type representing context of an {@link PostRunTaskItem} in {@link PostRunTaskCollection}.
     */
    final class Context {
        private final IndexableTaskItem taskItem;

        /**
         * Creates Context.
         *
         * @param taskItem the task item
         */
        Context(IndexableTaskItem taskItem) {
            this.taskItem = taskItem;
        }

        /**
         * Get result of one of the task that belongs to this task's task group.
         *
         * @param key the task key
         * @param <T> the actual type of the task result
         * @return the task result, null will be returned if task has not produced a result yet
         */
        @SuppressWarnings("unchecked")
        public <T extends Indexable> T taskResult(String key) {
            Indexable result = this.taskItem.taskGroup().taskResult(key);
            if (result == null) {
                return null;
            } else {
                T castedResult = (T) result;
                return castedResult;
            }
        }

        /**
         * @return an Observable upon subscription emits {@link VoidIndexable} with key same as the key of
         * this TaskItem
         */
        protected Observable<Indexable> voidObservable() {
            Indexable voidIndexable = new VoidIndexable(this.taskItem.key());
            return Observable.just(voidIndexable);
        }
    }
}
