/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.network.DdosProtectionPlan;
import com.microsoft.azure.v2.management.network.DdosProtectionPlans;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;

/**
 * Implementation for DdosProtectionPlans.
 */
@LangDefinition
class DdosProtectionPlansImpl
        extends TopLevelModifiableResourcesImpl<
                DdosProtectionPlan,
                DdosProtectionPlanImpl,
                DdosProtectionPlanInner,
                DdosProtectionPlansInner,
                NetworkManager>
        implements DdosProtectionPlans {

    DdosProtectionPlansImpl(final NetworkManager networkManager) {
        super(networkManager.inner().ddosProtectionPlans(), networkManager);
    }

    @Override
    public DdosProtectionPlanImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    protected DdosProtectionPlanImpl wrapModel(String name) {
        DdosProtectionPlanInner inner = new DdosProtectionPlanInner();
        return new DdosProtectionPlanImpl(name, inner, super.manager());
    }

    @Override
    protected DdosProtectionPlanImpl wrapModel(DdosProtectionPlanInner inner) {
        if (inner == null) {
            return null;
        }
        return new DdosProtectionPlanImpl(inner.name(), inner, this.manager());
    }
}

