package com.microsoft.azure.management.storage.implementation;

import com.microsoft.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.microsoft.azure.management.storage.BlobContainer;
import com.microsoft.azure.management.storage.ImmutabilityPolicyProperties;
import com.microsoft.azure.management.storage.LeaseDuration;
import com.microsoft.azure.management.storage.LeaseState;
import com.microsoft.azure.management.storage.LeaseStatus;
import com.microsoft.azure.management.storage.LegalHoldProperties;
import com.microsoft.azure.management.storage.ListContainerItem;
import com.microsoft.azure.management.storage.PublicAccess;
import org.joda.time.DateTime;

import java.util.Map;

public class ListContainerItemImpl extends WrapperImpl<ListContainerItemInner> implements ListContainerItem {

    private final StorageManager manager;

    ListContainerItemImpl(ListContainerItemInner inner, StorageManager manager) {
        super(inner);
        this.manager = manager;
    }


    @Override
    public PublicAccess publicAccess() {
        return this.inner().publicAccess();
    }

    @Override
    public DateTime lastModifiedTime() {
        return this.inner().lastModifiedTime();
    }

    @Override
    public LeaseStatus leaseStatus() {
        return this.inner().leaseStatus();
    }

    @Override
    public LeaseState leaseState() {
        return this.inner().leaseState();
    }

    @Override
    public LeaseDuration leaseDuration() {
        return this.inner().leaseDuration();
    }

    @Override
    public Map<String, String> metadata() {
        return this.inner().metadata();
    }

    @Override
    public ImmutabilityPolicyProperties immutabilityPolicy() {
        return this.inner().immutabilityPolicy();
    }

    @Override
    public LegalHoldProperties legalHold() {
        return this.inner().legalHold();
    }

    @Override
    public Boolean hasLegalHold() {
        return this.inner().hasLegalHold();
    }

    @Override
    public Boolean hasImmutabilityPolicy() {
        return this.inner().hasImmutabilityPolicy();
    }
}
