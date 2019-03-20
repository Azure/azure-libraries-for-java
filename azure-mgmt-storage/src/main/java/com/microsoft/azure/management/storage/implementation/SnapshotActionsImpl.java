/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage.implementation;

import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.storage.DateAfterCreation;
import com.microsoft.azure.management.storage.ManagementPolicySnapShot;
import com.microsoft.azure.management.storage.SnapshotActions;

class SnapshotActionsImpl implements
        SnapshotActions,
        SnapshotActions.Definition,
        SnapshotActions.Update,
        HasInner<ManagementPolicySnapShot> {

    private ManagementPolicySnapShot inner;
    private PolicyRuleImpl policyRuleImpl;

    SnapshotActionsImpl(PolicyRuleImpl policyRuleImpl) {
        this.inner = new ManagementPolicySnapShot();
        this.policyRuleImpl = policyRuleImpl;
    }

    SnapshotActionsImpl() {
        this.inner = new ManagementPolicySnapShot();
    }

    @Override
    public boolean deleteActionEnabled() {
        return this.inner.delete() != null;
    }

    @Override
    public Integer daysAfterCreationUntilDelete() {
        if (this.inner.delete() == null) {
            return null;
        }
        return this.inner.delete().daysAfterCreationGreaterThan();
    }

    @Override
    public ManagementPolicySnapShot inner() {
        return this.inner;
    }

    @Override
    public SnapshotActionsImpl withDeleteAction(int daysAfterCreationUntilDelete) {
        this.inner.withDelete(new DateAfterCreation().withDaysAfterCreationGreaterThan(daysAfterCreationUntilDelete));
        return this;
    }

    @Override
    public PolicyRuleImpl attach() {
        this.policyRuleImpl.defineActionsOnSnapshot(this);
        return this.policyRuleImpl;
    }
}
