/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.microsoft.azure.management.storage.BlobTypes;
import com.microsoft.azure.management.storage.ManagementPolicy;
import com.microsoft.azure.management.storage.ManagementPolicyBaseBlob;
import com.microsoft.azure.management.storage.ManagementPolicyRule;
import com.microsoft.azure.management.storage.ManagementPolicySchema;
import com.microsoft.azure.management.storage.ManagementPolicySnapShot;
import com.microsoft.azure.management.storage.PolicyRule;
import rx.Observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import rx.functions.Func1;

@LangDefinition
class ManagementPolicyImpl extends
        CreatableUpdatableImpl<ManagementPolicy,
                ManagementPolicyInner,
                ManagementPolicyImpl>
        implements
        ManagementPolicy,
        ManagementPolicy.Definition,
        ManagementPolicy.Update {
    private final StorageManager manager;
    private String resourceGroupName;
    private String accountName;
    private ManagementPolicySchema cpolicy;
    private ManagementPolicySchema upolicy;

    ManagementPolicyImpl(String name, StorageManager manager) {
        super(name, new ManagementPolicyInner());
        this.manager = manager;
        // Set resource name
        this.accountName = name;
        //
        this.cpolicy = new ManagementPolicySchema();
        this.upolicy = new ManagementPolicySchema();
    }

    ManagementPolicyImpl(ManagementPolicyInner inner, StorageManager manager) {
        super(inner.name(), inner);
        this.manager = manager;
        // Set resource name
        this.accountName = inner.name();
        // set resource ancestor and positional variables
        this.resourceGroupName = IdParsingUtils.getValueFromIdByName(inner.id(), "resourceGroups");
        this.accountName = IdParsingUtils.getValueFromIdByName(inner.id(), "storageAccounts");
        //
        this.cpolicy = new ManagementPolicySchema();
        this.upolicy = new ManagementPolicySchema();
    }

    @Override
    public StorageManager manager() {
        return this.manager;
    }

    @Override
    public Observable<ManagementPolicy> createResourceAsync() {
        ManagementPoliciesInner client = this.manager().inner().managementPolicies();
        return client.createOrUpdateAsync(this.resourceGroupName, this.accountName, this.cpolicy)
                .map(new Func1<ManagementPolicyInner, ManagementPolicyInner>() {
                    @Override
                    public ManagementPolicyInner call(ManagementPolicyInner resource) {
                        resetCreateUpdateParameters();
                        return resource;
                    }
                })
                .map(innerToFluentMap(this));
    }

    @Override
    public Observable<ManagementPolicy> updateResourceAsync() {
        ManagementPoliciesInner client = this.manager().inner().managementPolicies();
        return client.createOrUpdateAsync(this.resourceGroupName, this.accountName, this.upolicy)
                .map(new Func1<ManagementPolicyInner, ManagementPolicyInner>() {
                    @Override
                    public ManagementPolicyInner call(ManagementPolicyInner resource) {
                        resetCreateUpdateParameters();
                        return resource;
                    }
                })
                .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<ManagementPolicyInner> getInnerAsync() {
        ManagementPoliciesInner client = this.manager().inner().managementPolicies();
        return client.getAsync(this.resourceGroupName, this.accountName);
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().id() == null;
    }

    private void resetCreateUpdateParameters() {
        this.cpolicy = new ManagementPolicySchema();
        this.upolicy = new ManagementPolicySchema();
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public DateTime lastModifiedTime() {
        return this.inner().lastModifiedTime();
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public ManagementPolicySchema policy() {
        return this.inner().policy();
    }

    @Override
    public String type() {
        return this.inner().type();
    }

    @Override
    public List<PolicyRule> rules() {
        List<ManagementPolicyRule> originalRules = this.policy().rules();
        List<PolicyRule> returnRules = new ArrayList<>();
        for (ManagementPolicyRule originalRule : originalRules) {
            List<String> originalBlobTypes = originalRule.definition().filters().blobTypes();
            List<BlobTypes> returnBlobTypes = new ArrayList<>();
            for (String originalBlobType : originalBlobTypes) {
                returnBlobTypes.add(BlobTypes.fromString(originalBlobType));
            }
            PolicyRule returnRule = new PolicyRuleImpl(originalRule.name())
                    .withLifecycleRuleType()
                    .withBlobTypesToFilterFor(returnBlobTypes);

            // building up prefixes to filter on
            if (originalRule.definition().filters().prefixMatch() != null) {
                ((PolicyRuleImpl) returnRule).withPrefixesToFilterFor(originalRule.definition().filters().prefixMatch());
            }

            // building up actions on base blob
            ManagementPolicyBaseBlob originalBaseBlobActions = originalRule.definition().actions().baseBlob();
            if (originalBaseBlobActions != null) {
                if (originalBaseBlobActions.tierToCool() != null) {
                    ((PolicyRuleImpl) returnRule).withTierToCoolActionOnBaseBlob(originalBaseBlobActions.tierToCool().daysAfterModificationGreaterThan());
                }
                if (originalBaseBlobActions.tierToArchive() != null) {
                    ((PolicyRuleImpl) returnRule).withTierToArchiveActionOnBaseBlob(originalBaseBlobActions.tierToArchive().daysAfterModificationGreaterThan());
                }
                if (originalBaseBlobActions.delete() != null) {
                    ((PolicyRuleImpl) returnRule).withDeleteActionOnBaseBlob(originalBaseBlobActions.delete().daysAfterModificationGreaterThan());
                }
            }

            // build up actions on snapshot
            ManagementPolicySnapShot originalSnapshotActions = originalRule.definition().actions().snapshot();
            if (originalSnapshotActions != null) {
                if (originalSnapshotActions.delete() != null) {
                    ((PolicyRuleImpl) returnRule).withDeleteActionOnSnapShot(originalSnapshotActions.delete().daysAfterCreationGreaterThan());
                }
            }
            returnRules.add(returnRule);
        }
        return Collections.unmodifiableList(returnRules);
    }

    @Override
    public ManagementPolicyImpl withExistingStorageAccount(String resourceGroupName, String accountName) {
        this.resourceGroupName = resourceGroupName;
        this.accountName = accountName;
        return this;
    }

    @Override
    public ManagementPolicyImpl withPolicy(ManagementPolicySchema policy) {
        if (isInCreateMode()) {
            this.cpolicy = policy;
        } else {
            this.upolicy = policy;
        }
        return this;
    }

    @Override
    public PolicyRule.DefinitionStages.Blank defineRule(String name) {
        return new PolicyRuleImpl(this, name);
    }

    void defineRule(PolicyRuleImpl policyRuleImpl) {
        if (isInCreateMode()) {
            if (this.cpolicy.rules() == null) {
                this.cpolicy.withRules(new ArrayList<ManagementPolicyRule>());
            }
            List<ManagementPolicyRule> rules = this.cpolicy.rules();
            rules.add(policyRuleImpl.inner());
            this.cpolicy.withRules(rules);
        } else {
            if (this.upolicy.rules() == null) {
                this.upolicy.withRules(new ArrayList<ManagementPolicyRule>());
            }
            List<ManagementPolicyRule> rules = this.upolicy.rules();
            rules.add(policyRuleImpl.inner());
            this.upolicy.withRules(rules);
        }
    }

    @Override
    public PolicyRule.Update updateRule(String name) {
        ManagementPolicyRule ruleToUpdate = null;
        for (ManagementPolicyRule rule : this.policy().rules()) {
            if (rule.name().equals(name)) {
                ruleToUpdate = rule;
            }
        }
        if (ruleToUpdate == null) {
            throw new UnsupportedOperationException("There is no rule that exists with the name " + name + ". Please define a rule with this name before updating.");
        }
        return new PolicyRuleImpl(ruleToUpdate, this);
    }

    @Override
    public Update withoutRule(String name) {
        ManagementPolicyRule ruleToDelete = null;
        for (ManagementPolicyRule rule : this.policy().rules()) {
            if (rule.name().equals(name)) {
                ruleToDelete = rule;
            }
        }
        if (ruleToDelete == null) {
            throw new UnsupportedOperationException("There is no rule that exists with the name " + name + " so this rule can not be deleted.");
        }
        List<ManagementPolicyRule> currentRules = this.upolicy.rules();
        currentRules.remove(ruleToDelete);
        this.upolicy.withRules(currentRules);
        return this;
    }
}

