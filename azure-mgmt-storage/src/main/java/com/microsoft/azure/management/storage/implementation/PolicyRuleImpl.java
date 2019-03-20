/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.storage.implementation;

import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.storage.DateAfterCreation;
import com.microsoft.azure.management.storage.DateAfterModification;
import com.microsoft.azure.management.storage.ManagementPolicyAction;
import com.microsoft.azure.management.storage.ManagementPolicyBaseBlob;
import com.microsoft.azure.management.storage.ManagementPolicyDefinition;
import com.microsoft.azure.management.storage.ManagementPolicyFilter;
import com.microsoft.azure.management.storage.ManagementPolicyRule;
import com.microsoft.azure.management.storage.ManagementPolicySnapShot;
import com.microsoft.azure.management.storage.PolicyRule;

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
    public ManagementPolicyBaseBlob actionsOnBaseBlob() {
        return this.inner.definition().actions().baseBlob();
    }

    @Override
    public ManagementPolicySnapShot actionsOnSnapShot() {
        return this.inner.definition().actions().snapshot();
    }

    @Override
    public boolean tierToCoolActionOnBaseBlobEnabled() {
        if (this.inner.definition().actions().baseBlob() == null) {
            return false;
        }
        return this.inner.definition().actions().baseBlob().tierToCool() != null;
    }

    @Override
    public boolean tierToArchiveActionOnBaseBlobEnabled() {
        if (this.inner.definition().actions().baseBlob() == null) {
            return false;
        }
        return this.inner.definition().actions().baseBlob().tierToArchive() != null;
    }

    @Override
    public boolean deleteActionOnBaseBlobEnabled() {
        if (this.inner.definition().actions().baseBlob() == null) {
            return false;
        }
        return this.inner.definition().actions().baseBlob().delete() != null;
    }

    @Override
    public boolean deleteActionOnSnapShotEnabled() {
        if (this.inner.definition().actions().snapshot() == null) {
            return false;
        }
        return this.inner.definition().actions().snapshot().delete() != null;
    }

    @Override
    public Integer daysAfterBaseBlobModificationUntilCooling() {
        if (this.inner.definition().actions().baseBlob() == null || this.inner.definition().actions().baseBlob().tierToCool() == null) {
            return null;
        }
        return this.inner.definition().actions().baseBlob().tierToCool().daysAfterModificationGreaterThan();
    }

    @Override
    public Integer daysAfterBaseBlobModificationUntilArchiving() {
        if (this.inner.definition().actions().baseBlob() == null || this.inner.definition().actions().baseBlob().tierToArchive() == null) {
            return null;
        }
        return this.inner.definition().actions().baseBlob().tierToArchive().daysAfterModificationGreaterThan();
    }

    @Override
    public Integer daysAfterBaseBlobModificationUntilDeleting() {
        if (this.inner.definition().actions().baseBlob() == null || this.inner.definition().actions().baseBlob().delete() == null) {
            return null;
        }
        return this.inner.definition().actions().baseBlob().delete().daysAfterModificationGreaterThan();
    }

    @Override
    public Integer daysAfterSnapShotCreationUntilDeleting() {
        if (this.inner.definition().actions().snapshot() == null || this.inner.definition().actions().snapshot().delete() == null) {
            return null;
        }
        return this.inner.definition().actions().snapshot().delete().daysAfterCreationGreaterThan();
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
    public PolicyRuleImpl withBlobTypesToFilterFor(List<String> blobTypes) {
        this.inner.definition().filters().withBlobTypes(blobTypes);
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
    public PolicyRuleImpl withTierToCoolActionOnBaseBlob(int daysAfterBaseBlobModificationUntilCooling) {
        ManagementPolicyBaseBlob currentBaseBlob = this.inner.definition().actions().baseBlob();
        if (currentBaseBlob == null) {
            currentBaseBlob = new ManagementPolicyBaseBlob();
        }
        currentBaseBlob.withTierToCool(new DateAfterModification().withDaysAfterModificationGreaterThan(daysAfterBaseBlobModificationUntilCooling));
        this.inner.definition().actions().withBaseBlob(currentBaseBlob);
        return this;
    }

    @Override
    public PolicyRuleImpl withTierToArchiveActionOnBaseBlob(int daysAfterBaseBlobModificationUntilArchiving) {
        ManagementPolicyBaseBlob currentBaseBlob = this.inner.definition().actions().baseBlob();
        if (currentBaseBlob == null) {
            currentBaseBlob = new ManagementPolicyBaseBlob();
        }
        currentBaseBlob.withTierToArchive(new DateAfterModification().withDaysAfterModificationGreaterThan(daysAfterBaseBlobModificationUntilArchiving));
        this.inner.definition().actions().withBaseBlob(currentBaseBlob);
        return this;
    }

    @Override
    public PolicyRuleImpl withDeleteActionOnBaseBlob(int daysAfterBaseBlobModificationUntilDeleting) {
        ManagementPolicyBaseBlob currentBaseBlob = this.inner.definition().actions().baseBlob();
        if (currentBaseBlob == null) {
            currentBaseBlob = new ManagementPolicyBaseBlob();
        }
        currentBaseBlob.withDelete(new DateAfterModification().withDaysAfterModificationGreaterThan(daysAfterBaseBlobModificationUntilDeleting));
        this.inner.definition().actions().withBaseBlob(currentBaseBlob);
        return this;
    }

    @Override
    public Update updateActionsOnBaseBlob(ManagementPolicyBaseBlob baseBlobActions) {
        this.inner.definition().actions().withBaseBlob(baseBlobActions);
        return this;
    }

    @Override
    public PolicyRuleImpl withDeleteActionOnSnapShot(int daysAfterSnapShotCreationUntilDeleting) {
        ManagementPolicySnapShot currentSnapShot = new ManagementPolicySnapShot();
        if (currentSnapShot == null) {
            currentSnapShot = new ManagementPolicySnapShot();
        }
        currentSnapShot.withDelete(new DateAfterCreation().withDaysAfterCreationGreaterThan(daysAfterSnapShotCreationUntilDeleting));
        this.inner.definition().actions().withSnapshot(currentSnapShot);
        return this;
    }

    @Override
    public Update updateActionsOnSnapShot(ManagementPolicySnapShot snapShotActions) {
        this.inner.definition().actions().withSnapshot(snapShotActions);
        return this;
    }

    @Override
    public ManagementPolicyImpl attach() {
        this.managementPolicyImpl.defineRule(this);
        return this.managementPolicyImpl;
    }

    @Override
    public Update update() {
        return this;
    }

    private boolean isInCreateMode() {
        return this.inner().name() == "";
    }


}