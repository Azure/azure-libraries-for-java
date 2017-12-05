/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.dag;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of "Post Run" tasks.
 *
 * @param <T> the post run task item
 */
public class PostRunTaskCollection<T extends IndexableTaskItem> {
    private final List<T> collection;
    private final TaskGroup dependsOnTaskGroup;

    /**
     * Creates PostRunTaskCollection.
     *
     * @param dependsOnTaskGroup the task group in which "Post Run" tasks in the collection depends on
     */
    public PostRunTaskCollection(final TaskGroup dependsOnTaskGroup) {
        this.collection = new ArrayList<>();
        this.dependsOnTaskGroup = dependsOnTaskGroup;
    }

    /**
     * Adds a "Post Run" task to the collection.
     *
     * @param taskItem the "Post Run" task
     */
    public void add(final T taskItem) {
        this.dependsOnTaskGroup.addPostRunDependentTaskGroup(taskItem.taskGroup());
        this.collection.add(taskItem);
    }

    /**
     * Clears the result produced by all "Post Run" tasks in the collection.
     */
    public void clear() {
        for (IndexableTaskItem item : collection) {
            item.clear();
        }
    }
}