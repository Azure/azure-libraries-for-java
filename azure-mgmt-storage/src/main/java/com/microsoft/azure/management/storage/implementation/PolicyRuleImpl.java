/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.storage.implementation;

import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.storage.BaseBlobActions;
import com.microsoft.azure.management.storage.ManagementPolicyAction;
import com.microsoft.azure.management.storage.ManagementPolicyDefinition;
import com.microsoft.azure.management.storage.ManagementPolicyFilter;
import com.microsoft.azure.management.storage.ManagementPolicyRule;
import com.microsoft.azure.management.storage.PolicyRule;
import com.microsoft.azure.management.storage.SnapshotActions;

import java.util.ArrayList;
import java.util.List;

class PolicyRuleImpl implements
        PolicyRule,
        PolicyRule.Definition,
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
        this.inner.definition().withFilters(new ManagementPolicyFilter().withPrefixMatch(prefixes));
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
    public ManagementPolicyImpl attach() {
        this.managementPolicyImpl.defineRule(this);
        return this.managementPolicyImpl;
    }



    @Override
    public PolicyRuleImpl withBlobTypesToFilterFor(List<String> blobTypes) {
        this.inner.definition().withFilters(new ManagementPolicyFilter().withBlobTypes(blobTypes));
        return this;
    }

    @Override
    public DefinitionStages.PolicyRuleAttachable withBlobTypeToFilterFor(String blobType) {
        List<String> blobTypesToFilterFor = this.inner.definition().filters().blobTypes();
        if (blobTypesToFilterFor == null) {
            blobTypesToFilterFor = new ArrayList<>();
        }
        blobTypesToFilterFor.add(blobType);
        this.inner.definition().filters().withBlobTypes(blobTypesToFilterFor);
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
}
