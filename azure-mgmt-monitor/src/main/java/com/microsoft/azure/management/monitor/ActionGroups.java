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

    /**
     * Enable a receiver in an action group. This changes the receiver's status from Disabled to Enabled. This operation is only supported for Email or SMS receivers.
     *
     * @param resourceGroupName The name of the resource group.
     * @param actionGroupName The name of the action group.
     * @param receiverName The name of the receiver to resubscribe.
     */
    void enableReceiver(String resourceGroupName, String actionGroupName, String receiverName);

    /**
     * Enable a receiver in an action group. This changes the receiver's status from Disabled to Enabled. This operation is only supported for Email or SMS receivers.
     *
     * @param resourceGroupName The name of the resource group.
     * @param actionGroupName The name of the action group.
     * @param receiverName The name of the receiver to resubscribe.
     * @return a representation of the deferred computation of this call.
     */
    Completable enableReceiverAsync(String resourceGroupName, String actionGroupName, String receiverName);

    /**
     * Enable a receiver in an action group. This changes the receiver's status from Disabled to Enabled. This operation is only supported for Email or SMS receivers.
     *
     * @param resourceGroupName The name of the resource group.
     * @param actionGroupName The name of the action group.
     * @param receiverName The name of the receiver to resubscribe.
     * @param callback the async ServiceCallback to handle successful and failed responses.
     * @return the {@link ServiceFuture} object
     */
    ServiceFuture<Void> enableReceiverAsync(String resourceGroupName, String actionGroupName, String receiverName, ServiceCallback<Void> callback);
}
