/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.storage.implementation;

import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.storage.BaseBlobActions;
import com.microsoft.azure.management.storage.DateAfterModification;
import com.microsoft.azure.management.storage.ManagementPolicyAction;
import com.microsoft.azure.management.storage.ManagementPolicyBaseBlob;
import com.microsoft.azure.management.storage.ManagementPolicyDefinition;
import com.microsoft.azure.management.storage.ManagementPolicyFilter;
import com.microsoft.azure.management.storage.ManagementPolicyRule;
import com.microsoft.azure.management.storage.ManagementPolicySnapShot;
import com.microsoft.azure.management.storage.PolicyRule;
import com.microsoft.azure.management.storage.SnapshotActions;

import java.util.ArrayList;
import java.util.List;

class PolicyRuleImpl implements
        PolicyRule,
        PolicyRule.Definition,
        PolicyRule.Update,
        HasInner<ManagementPolicyRule> {

    private ManagementPolicyRule inner;
    private ManagementPolicyImpl managementPolicyImpl;

    PolicyRuleImpl(ManagementPolicyImpl managementPolicyImpl) {
        this.inner = new ManagementPolicyRule();
        this.inner.withDefinition(new ManagementPolicyDefinition());
        this.inner.definition().withFilters(new ManagementPolicyFilter());
        this.inner.definition().withActions(new ManagementPolicyAction());
        this.managementPolicyImpl = managementPolicyImpl;
    }

    PolicyRuleImpl() {
        this.inner = new ManagementPolicyRule();
        this.inner.withDefinition(new ManagementPolicyDefinition());
        this.inner.definition().withFilters(new ManagementPolicyFilter());
        this.inner.definition().withActions(new ManagementPolicyAction());
    }

    @Override
    public String name() {
        return this.inner.name();
    }

    @Override
    public String type() {
        return this.inner.type();
    }

    @Override
    public List<String> blobTypesToFilterFor() {
        return this.inner.definition().filters().blobTypes();
    }

    @Override
    public List<String> prefixesToFilterFor() {
        return this.inner.definition().filters().prefixMatch();
    }

    @Override
    public BaseBlobActions actionsOnBaseBlob() {
        ManagementPolicyBaseBlob originalBaseBlobActions = this.inner.definition().actions().baseBlob();
        BaseBlobActions returnBaseBlobActions = new BaseBlobActionsImpl();
        if (originalBaseBlobActions.delete() != null) {
            ((BaseBlobActionsImpl) returnBaseBlobActions).withDeleteAction(originalBaseBlobActions.delete().daysAfterModificationGreaterThan());
        }
        if (originalBaseBlobActions.tierToArchive() != null) {
            ((BaseBlobActionsImpl) returnBaseBlobActions).withTierToArchiveAction(originalBaseBlobActions.tierToArchive().daysAfterModificationGreaterThan());
        }
        if (originalBaseBlobActions.tierToCool() != null) {
            ((BaseBlobActionsImpl) returnBaseBlobActions).withTierToCoolAction(originalBaseBlobActions.tierToCool().daysAfterModificationGreaterThan());
        }
        return returnBaseBlobActions;
    }

    @Override
    public SnapshotActions actionsOnSnapshot() {
        ManagementPolicySnapShot originalSnapshotActions = this.inner.definition().actions().snapshot();
        SnapshotActions returnSnapshotActions = new SnapshotActionsImpl();
        if (originalSnapshotActions.delete() != null) {
            ((SnapshotActionsImpl) returnSnapshotActions).withDeleteAction(originalSnapshotActions.delete().daysAfterCreationGreaterThan());
        }
        return returnSnapshotActions;
    }

    @Override
    public ManagementPolicyRule inner() {
        return this.inner;
    }

    @Override
    public PolicyRuleImpl withName(String ruleName) {
        this.inner.withName(ruleName);
        return this;
    }

    @Override
    public PolicyRuleImpl withType(String type) {
        this.inner.withType(type);
        return this;
    }

    @Override
    public PolicyRuleImpl withPrefixesToFilterFor(List<String> prefixes) {
        this.inner.definition().filters().withPrefixMatch(prefixes);
        return this;
    }

    @Override
    public PolicyRuleImpl withPrefixToFilterFor(String prefix) {
        List<String> prefixesToFilterFor = this.inner.definition().filters().prefixMatch();
        if (prefixesToFilterFor == null) {
            prefixesToFilterFor = new ArrayList<>();
        }
        prefixesToFilterFor.add(prefix);
        this.inner.definition().filters().withPrefixMatch(prefixesToFilterFor);
        return this;
    }

    @Override
    public Update withoutPrefixesToFilterFor() {
        this.inner.definition().filters().withPrefixMatch(null);
        return this;
    }

    @Override
    public ManagementPolicyImpl attach() {
        this.managementPolicyImpl.defineRule(this);
        return this.managementPolicyImpl;
    }



    @Override
    public PolicyRuleImpl withBlobTypesToFilterFor(List<String> blobTypes) {
        this.inner.definition().filters().withBlobTypes(blobTypes);
        isInCreateMode();
        return this;
    }

    @Override
    public PolicyRuleImpl withBlobTypeToFilterFor(String blobType) {
        List<String> blobTypesToFilterFor = this.inner.definition().filters().blobTypes();
        if (blobTypesToFilterFor == null) {
            blobTypesToFilterFor = new ArrayList<>();
        }
        blobTypesToFilterFor.add(blobType);
        this.inner.definition().filters().withBlobTypes(blobTypesToFilterFor);
        return this;
    }

    @Override
    public Update withoutBlobTypesToFilterFor() {
        this.inner.definition().filters().withBlobTypes(new ArrayList<String>());
        return this;
    }

    @Override
    public BaseBlobActions.DefinitionStages.Blank defineActionsOnBaseBlob() {
        return new BaseBlobActionsImpl(this);
    }

    @Override
    public SnapshotActions.DefinitionStages.Blank defineActionsOnSnapshot() {
        return new SnapshotActionsImpl(this);
    }

    public void defineActionsOnBaseBlob(BaseBlobActionsImpl baseBlobActionsImpl) {
        this.inner.definition().actions().withBaseBlob(baseBlobActionsImpl.inner());
    }

    public void defineActionsOnSnapshot(SnapshotActionsImpl snapshotActionsImpl) {
        this.inner.definition().actions().withSnapshot(snapshotActionsImpl.inner());
    }

    @Override
    public BaseBlobActions.Update updateActionsOnBaseBlob() {
        return null;
    }

    @Override
    public Update updateActionsOnBaseBlob(BaseBlobActions baseBlobActions) {
        ManagementPolicyBaseBlob updateBaseBlob = new ManagementPolicyBaseBlob();
        if (baseBlobActions.deleteActionEnabled()) {
            updateBaseBlob.withDelete(new DateAfterModification().withDaysAfterModificationGreaterThan(baseBlobActions.daysAfterModificationUntilDelete()));
        }
        if (baseBlobActions.tierToArchiveActionEnabled()) {
            updateBaseBlob.withTierToArchive(new DateAfterModification().withDaysAfterModificationGreaterThan(baseBlobActions.daysAfterModificationUntilArchiving()));
        }
        if (baseBlobActions.tierToCoolActionEnabled()) {
            updateBaseBlob.withTierToCool(new DateAfterModification().withDaysAfterModificationGreaterThan(baseBlobActions.daysAfterModificationUntilCooling()))
        }
        this.inner.definition().actions().withBaseBlob(updateBaseBlob);
        return this;
    }

    @Override
    public SnapshotActions.Update updateActionsOnSnapshot() {
        return null;
    }

    @Override
    public Update updateActionsOnSnapshot(SnapshotActions snapshotActions) {
        return null;
    }

    @Override
    public Update update() {
        return this;
    }

    private boolean isInCreateMode() {
        return this.inner().name() == "";
    }
}