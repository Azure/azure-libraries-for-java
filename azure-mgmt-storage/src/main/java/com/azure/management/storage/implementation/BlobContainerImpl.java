/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.storage.implementation;

import com.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.azure.management.storage.BlobContainer;
import com.azure.management.storage.ImmutabilityPolicyProperties;
import com.azure.management.storage.LeaseDuration;
import com.azure.management.storage.LeaseState;
import com.azure.management.storage.LeaseStatus;
import com.azure.management.storage.LegalHoldProperties;
import com.azure.management.storage.PublicAccess;
import com.azure.management.storage.models.BlobContainerInner;
import com.azure.management.storage.models.BlobContainersInner;
import reactor.core.publisher.Mono;


import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

class BlobContainerImpl extends CreatableUpdatableImpl<BlobContainer, BlobContainerInner, BlobContainerImpl> implements BlobContainer, BlobContainer.Definition, BlobContainer.Update {
    private final StorageManager manager;
    private String resourceGroupName;
    private String accountName;
    private String containerName;
    private PublicAccess cpublicAccess;
    private Map<String, String> cmetadata;
    private PublicAccess upublicAccess;
    private Map<String, String> umetadata;

    BlobContainerImpl(String name, StorageManager manager) {
        super(name, new BlobContainerInner());
        this.manager = manager;
        // Set resource name
        this.containerName = name;
        //
    }

    BlobContainerImpl(BlobContainerInner inner, StorageManager manager) {
        super(inner.getName(), inner);
        this.manager = manager;
        // Set resource name
        this.containerName = inner.getName();
        // set resource ancestor and positional variables
        this.resourceGroupName = IdParsingUtils.getValueFromIdByName(inner.getId(), "resourceGroups");
        this.accountName = IdParsingUtils.getValueFromIdByName(inner.getId(), "storageAccounts");
        this.containerName = IdParsingUtils.getValueFromIdByName(inner.getId(), "containers");
        //
    }

    @Override
    public StorageManager getManager() {
        return this.manager;
    }

    @Override
    public Mono<BlobContainer> createResourceAsync() {
        BlobContainersInner client = this.getManager().getInner().blobContainers();
        BlobContainerInner inner = new BlobContainerInner();
        inner.setPublicAccess(this.cpublicAccess);
        inner.setMetadata(this.cmetadata);
        return client.createAsync(this.resourceGroupName, this.accountName, this.containerName, inner)
                .map(innerToFluentMap(this));
    }

    @Override
    public Mono<BlobContainer> updateResourceAsync() {
        BlobContainersInner client = this.getManager().getInner().blobContainers();
        BlobContainerInner inner = new BlobContainerInner();
        inner.setPublicAccess(this.cpublicAccess);
        inner.setMetadata(this.cmetadata);

        return client.updateAsync(this.resourceGroupName, this.accountName, this.containerName, inner)
                .map(innerToFluentMap(this));
    }

    @Override
    protected Mono<BlobContainerInner> getInnerAsync() {
        BlobContainersInner client = this.getManager().getInner().blobContainers();
        return null; // NOP getInnerAsync implementation as get is not supported
    }

    @Override
    public boolean isInCreateMode() {
        return this.getInner().getId() == null;
    }


    @Override
    public String etag() {
        return this.getInner().getEtag();
    }

    @Override
    public Boolean hasImmutabilityPolicy() {
        return this.getInner().isHasImmutabilityPolicy();
    }

    @Override
    public Boolean hasLegalHold() {
        return this.getInner().isHasLegalHold();
    }

    @Override
    public String id() {
        return this.getInner().getId();
    }

    @Override
    public ImmutabilityPolicyProperties immutabilityPolicy() {
        return this.getInner().getImmutabilityPolicy();
    }

    @Override
    public OffsetDateTime lastModifiedTime() {
        return this.getInner().getLastModifiedTime();
    }

    @Override
    public LeaseDuration leaseDuration() {
        return this.getInner().getLeaseDuration();
    }

    @Override
    public LeaseState leaseState() {
        return this.getInner().getLeaseState();
    }

    @Override
    public LeaseStatus leaseStatus() {
        return this.getInner().getLeaseStatus();
    }

    @Override
    public LegalHoldProperties legalHold() {
        return this.getInner().getLegalHold();
    }

    @Override
    public Map<String, String> metadata() {
        return this.getInner().getMetadata();
    }

    @Override
    public String name() {
        return this.getInner().getName();
    }

    @Override
    public PublicAccess publicAccess() {
        return this.getInner().getPublicAccess();
    }

    @Override
    public String type() {
        return this.getInner().getType();
    }

    @Override
    public BlobContainerImpl withExistingBlobService(String resourceGroupName, String accountName) {
        this.resourceGroupName = resourceGroupName;
        this.accountName = accountName;
        return this;
    }

    @Override
    public BlobContainerImpl withPublicAccess(PublicAccess publicAccess) {
        if (isInCreateMode()) {
            this.cpublicAccess = publicAccess;
        } else {
            this.upublicAccess = publicAccess;
        }
        return this;
    }

    @Override
    public BlobContainerImpl withMetadata(Map<String, String> metadata) {
        if (isInCreateMode()) {
            this.cmetadata = metadata;
        } else {
            this.umetadata = metadata;
        }
        return this;
    }

    @Override
    public BlobContainerImpl withMetadata(String name, String value) {
        if (isInCreateMode()) {
            if (this.cmetadata == null) {
                this.cmetadata = new HashMap<>();
            }
            this.cmetadata.put(name, value);
        } else {
            if (this.umetadata == null) {
                this.umetadata = new HashMap<>();
            }
            this.umetadata.put(name, value);
        }
        return this;
    }
}