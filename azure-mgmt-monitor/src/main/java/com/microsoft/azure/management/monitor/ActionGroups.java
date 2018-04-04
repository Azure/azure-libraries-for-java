/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.monitor.implementation.ActionGroupsInner;
import com.microsoft.azure.management.monitor.implementation.MonitorManager;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsBatchDeletion;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsDeletingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsListingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsBatchCreation;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingById;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;

/**
 * Entry point for Action Group management API.
 */
@Fluent
public interface ActionGroups extends
        SupportsCreating<ActionGroup.DefinitionStages.Blank>,
        SupportsListing<ActionGroup>,
        SupportsListingByResourceGroup<ActionGroup>,
        SupportsGettingById<ActionGroup>,
        SupportsDeletingById,
        SupportsDeletingByResourceGroup,
        SupportsBatchCreation<ActionGroup>,
        SupportsBatchDeletion,
        HasManager<MonitorManager>,
        HasInner<ActionGroupsInner> {

    void enableReceiver(String resourceGroupName, String actionGroupName, String receiverName);

    Completable enableReceiverAsync(String resourceGroupName, String actionGroupName, String receiverName);

    ServiceFuture<Void> enableReceiverAsync(String resourceGroupName, String actionGroupName, String receiverName, ServiceCallback<Void> callback);
}
