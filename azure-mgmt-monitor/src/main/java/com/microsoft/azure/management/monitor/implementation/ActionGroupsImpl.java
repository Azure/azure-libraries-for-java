/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.monitor.ActionGroup;
import com.microsoft.azure.management.monitor.ActionGroups;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;

/**
 * Implementation for {@link ActionGroups}.
 */
@LangDefinition
class ActionGroupsImpl
        extends TopLevelModifiableResourcesImpl<
                        ActionGroup,
                        ActionGroupImpl,
                        ActionGroupResourceInner,
                        ActionGroupsInner,
                        MonitorManager>
        implements ActionGroups {

    ActionGroupsImpl(final MonitorManager monitorManager) {
        super(monitorManager.inner().actionGroups(), monitorManager);
    }

    @Override
    protected ActionGroupImpl wrapModel(String name) {
        return new ActionGroupImpl(name, new ActionGroupResourceInner(), this.manager());
    }

    @Override
    protected ActionGroupImpl wrapModel(ActionGroupResourceInner inner) {
        if (inner ==  null) {
            return null;
        }
        return new ActionGroupImpl(inner.name(), inner, this.manager());
    }

    @Override
    public ActionGroupImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    public void enableReceiver(String resourceGroupName, String actionGroupName, String receiverName) {
        this.inner().enableReceiver(resourceGroupName, actionGroupName, receiverName);
    }

    @Override
    public Completable enableReceiverAsync(String resourceGroupName, String actionGroupName, String receiverName) {
        return this.inner().enableReceiverAsync(resourceGroupName, actionGroupName, receiverName).toCompletable();
    }

    @Override
    public ServiceFuture<Void> enableReceiverAsync(String resourceGroupName, String actionGroupName, String receiverName, ServiceCallback<Void> callback) {
        return this.inner().enableReceiverAsync(resourceGroupName, actionGroupName, receiverName, callback);
    }
}
