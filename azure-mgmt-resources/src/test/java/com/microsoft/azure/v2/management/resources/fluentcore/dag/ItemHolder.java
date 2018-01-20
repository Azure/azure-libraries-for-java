/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.fluentcore.dag;

import com.microsoft.azure.v2.management.resources.fluentcore.dag.DAGNode;

class ItemHolder extends DAGNode<String, ItemHolder> {
    ItemHolder(String taskId, String taskItem) {
        super(taskId,taskItem);
    }
}