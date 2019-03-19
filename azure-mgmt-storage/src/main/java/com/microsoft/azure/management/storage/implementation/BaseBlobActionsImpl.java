/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.storage.implementation;

import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.storage.BaseBlobActions;
import com.microsoft.azure.management.storage.DateAfterModification;
import com.microsoft.azure.management.storage.ManagementPolicyBaseBlob;

class BaseBlobActionsImpl implements
        BaseBlobActions,
        BaseBlobActions.Definition,
        HasInner<ManagementPolicyBaseBlob> {

    private ManagementPolicyBaseBlob inner;
    private PolicyRuleImpl policyRuleImpl;

    BaseBlobActionsImpl(PolicyRuleImpl policyRuleImpl) {
        this.inner = new ManagementPolicyBaseBlob();
        this.policyRuleImpl = policyRuleImpl;
    }

    BaseBlobActionsImpl() {
        this.inner = new ManagementPolicyBaseBlob();
    }

    @Override
    public ManagementPolicyBaseBlob inner() {
        return this.inner;
    }

    @Override
    public BaseBlobActionsImpl withTierToCoolAction(int daysAfterModificationUntilCooling) {
        this.inner.withTierToCool(new DateAfterModification().withDaysAfterModificationGreaterThan(daysAfterModificationUntilCooling));
        return this;
    }

    @Override
    public BaseBlobActionsImpl withTierToArchiveAction(int daysAfterModificationUntilArchiving) {
        this.inner.withTierToArchive(new DateAfterModification().withDaysAfterModificationGreaterThan(daysAfterModificationUntilArchiving));
        return this;
    }

    @Override
    public BaseBlobActionsImpl withDeleteAction(int daysAfterModificationUntilDelete) {
        this.inner.withDelete(new DateAfterModification().withDaysAfterModificationGreaterThan(daysAfterModificationUntilDelete));
        return this;
    }

    @Override
    public PolicyRuleImpl attach() {
        this.policyRuleImpl.defineActionsOnBaseBlob(this);
        return this.policyRuleImpl;
    }
}
