/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.microsoft.azure.SubResource;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.azure.management.network.DdosProtectionPlan;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import rx.Observable;

import java.util.Collections;
import java.util.List;

/**
 *  Implementation for DdosProtectionPlan and its create and update interfaces.
 */
@LangDefinition
class DdosProtectionPlanImpl
        extends GroupableResourceImpl<
        DdosProtectionPlan,
        DdosProtectionPlanInner,
        DdosProtectionPlanImpl,
        NetworkManager>
        implements
        DdosProtectionPlan,
        DdosProtectionPlan.Definition,
        DdosProtectionPlan.Update {

    DdosProtectionPlanImpl(
            final String name,
            final DdosProtectionPlanInner innerModel,
            final NetworkManager networkManager) {
        super(name, innerModel, networkManager);
    }

    @Override
    protected Observable<DdosProtectionPlanInner> getInnerAsync() {
        return this.manager().inner().ddosProtectionPlans().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public Observable<DdosProtectionPlan> createResourceAsync() {
        return this.manager().inner().ddosProtectionPlans().createOrUpdateAsync(resourceGroupName(), name(), inner())
                .map(innerToFluentMap(this));
    }

    @Override
    public String resourceGuid() {
        return inner().resourceGuid();
    }

    @Override
    public String provisioningState() {
        return inner().provisioningState();
    }

    @Override
    public List<SubResource> virtualNetworks() {
        return Collections.unmodifiableList(inner().virtualNetworks());
    }
}
