/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.storage.implementation;

import com.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.azure.management.storage.BlobContainer;
import com.azure.management.storage.BlobContainers;
import com.azure.management.storage.ImmutabilityPolicy;
import com.azure.management.storage.LegalHold;
import com.azure.management.storage.ListContainerItem;
import com.azure.management.storage.models.BlobContainerInner;
import com.azure.management.storage.models.BlobContainersInner;
import com.azure.management.storage.models.ImmutabilityPolicyInner;
import com.azure.management.storage.models.LegalHoldInner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

class BlobContainersImpl extends WrapperImpl<BlobContainersInner> implements BlobContainers {
    private final StorageManager manager;

    BlobContainersImpl(StorageManager manager) {
        super(manager.getInner().blobContainers());
        this.manager = manager;
    }

    public StorageManager manager() {
        return this.manager;
    }

    @Override
    public BlobContainerImpl defineContainer(String name) {
        return wrapContainerModel(name);
    }

    @Override
    public ImmutabilityPolicyImpl defineImmutabilityPolicy(String name) {
        return wrapImmutabilityPolicyModel(name);
    }

    private BlobContainerImpl wrapContainerModel(String name) {
        return new BlobContainerImpl(name, this.manager());
    }

    private ImmutabilityPolicyImpl wrapImmutabilityPolicyModel(String name) {
        return new ImmutabilityPolicyImpl(name, this.manager());
    }

    private BlobContainerImpl wrapBlobContainerModel(BlobContainerInner inner) {
        return new BlobContainerImpl(inner, manager());
    }

    private ImmutabilityPolicyImpl wrapImmutabilityPolicyModel(ImmutabilityPolicyInner inner) {
        return new ImmutabilityPolicyImpl(inner, manager());
    }

    private Mono<ImmutabilityPolicyInner> getImmutabilityPolicyInnerUsingBlobContainersInnerAsync(String id) {
        String resourceGroupName = IdParsingUtils.getValueFromIdByName(id, "resourceGroups");
        String accountName = IdParsingUtils.getValueFromIdByName(id, "storageAccounts");
        String containerName = IdParsingUtils.getValueFromIdByName(id, "containers");
        BlobContainersInner client = this.getInner();
        // FIXME: Last parameter
        return client.getImmutabilityPolicyAsync(resourceGroupName, accountName, containerName, null);
    }

    @Override
    public Flux<ListContainerItem> listAsync(String resourceGroupName, String accountName) {
        BlobContainersInner client = this.getInner();
        return client.listAsync(resourceGroupName, accountName)
                .flatMapMany(inners -> Flux.fromIterable(inners.getValue()));
    }

    @Override
    public Mono<BlobContainer> getAsync(String resourceGroupName, String accountName, String containerName) {
        BlobContainersInner client = this.getInner();
        return client.getAsync(resourceGroupName, accountName, containerName)
                .map(inner -> new BlobContainerImpl(inner, manager()));
    }

    @Override
    public Mono<Void> deleteAsync(String resourceGroupName, String accountName, String containerName) {
        BlobContainersInner client = this.getInner();
        return client.deleteAsync(resourceGroupName, accountName, containerName);
    }

    @Override
    public Mono<LegalHold> setLegalHoldAsync(String resourceGroupName, String accountName, String containerName, List<String> tags) {
        BlobContainersInner client = this.getInner();
        LegalHoldInner inner = new LegalHoldInner();
        inner.setTags(tags);
        return client.setLegalHoldAsync(resourceGroupName, accountName, containerName, inner)
                .map(legalHoldInner -> new LegalHoldImpl(legalHoldInner, manager()));
    }

    @Override
    public Mono<LegalHold> clearLegalHoldAsync(String resourceGroupName, String accountName, String containerName, List<String> tags) {
        BlobContainersInner client = this.getInner();
        LegalHoldInner inner = new LegalHoldInner();
        inner.setTags(tags);
        return client.clearLegalHoldAsync(resourceGroupName, accountName, containerName, inner)
                .map(legalHoldInner -> new LegalHoldImpl(legalHoldInner, manager()));
    }

    @Override
    public Mono<ImmutabilityPolicy> getImmutabilityPolicyAsync(String resourceGroupName, String accountName, String containerName) {
        BlobContainersInner client = this.getInner();
        // FIXME: If-match
        return client.getImmutabilityPolicyAsync(resourceGroupName, accountName, containerName, null)
                .map(inner -> wrapImmutabilityPolicyModel(inner));
    }

    @Override
    public Mono<ImmutabilityPolicyInner> deleteImmutabilityPolicyAsync(String resourceGroupName, String accountName, String containerName, String ifMatch) {
        return getInner().deleteImmutabilityPolicyAsync(resourceGroupName, accountName, containerName, ifMatch);
    }

    @Override
    public Mono<ImmutabilityPolicy> lockImmutabilityPolicyAsync(String resourceGroupName, String accountName, String containerName, String ifMatch) {
        BlobContainersInner client = this.getInner();
        return client.lockImmutabilityPolicyAsync(resourceGroupName, accountName, containerName, ifMatch)
                .map(inner -> new ImmutabilityPolicyImpl(inner, manager()));
    }

    @Override
    public Mono<ImmutabilityPolicy> extendImmutabilityPolicyAsync(String resourceGroupName, String accountName, String containerName, String ifMatch, int immutabilityPeriodSinceCreationInDays) {
        BlobContainersInner client = this.getInner();
        ImmutabilityPolicyInner inner = new ImmutabilityPolicyInner();
        inner.setImmutabilityPeriodSinceCreationInDays(immutabilityPeriodSinceCreationInDays);
        return client.extendImmutabilityPolicyAsync(resourceGroupName, accountName, containerName, ifMatch, inner)
                .map(policyInner -> new ImmutabilityPolicyImpl(policyInner, this.manager));
    }
}