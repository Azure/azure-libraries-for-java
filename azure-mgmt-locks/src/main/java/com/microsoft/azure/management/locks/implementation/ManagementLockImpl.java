/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.locks.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.locks.LockLevel;
import com.microsoft.azure.management.locks.ManagementLock;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import rx.Observable;

/**
 *  Implementation for ManagementLock and its create and update interfaces.
 */
@LangDefinition
class ManagementLockImpl
    extends CreatableUpdatableImpl<ManagementLock, ManagementLockObjectInner, ManagementLockImpl>
    implements
        ManagementLock,
        ManagementLock.Definition,
        ManagementLock.Update {

    private final AuthorizationManager manager;
    private String lockedResourceId = null;

    ManagementLockImpl(
            String name,
            ManagementLockObjectInner innerModel,
            final AuthorizationManager lockManager) {
        super(name, innerModel);
        this.manager = lockManager;
    }

    // Verbs

    @Override
    protected Observable<ManagementLockObjectInner> getInnerAsync() {
        return this.manager().inner().managementLocks().getByScopeAsync(this.lockedResourceId, this.name());
    }

    // Setters (fluent)

    @Override
    public ManagementLockImpl withNotes(String notes) {
        this.inner().withNotes(notes);
        return this;
    }

    @Override
    public ManagementLockImpl withLevel(LockLevel level) {
        this.inner().withLevel(level);
        return this;
    }

    @Override
    public ManagementLockImpl withLockedResource(String resourceId) {
        this.lockedResourceId = resourceId;
        return this;
    }

    @Override
    public ManagementLockImpl withLockedResource(Resource resource) {
        if (resource != null) {
            this.lockedResourceId = resource.id();
        } else {
            throw new IllegalArgumentException("Missing resource ID.");
        }
        return this;
    }

    @Override
    public ManagementLockImpl withLockedResourceGroup(String resourceGroupName) {
        return withLockedResource(this.manager().subscriptionId() + "/resourceGroups/" + resourceGroupName);
    }

    @Override
    public ManagementLockImpl withLockedResourceGroup(ResourceGroup resourceGroup) {
        if (resourceGroup != null) {
            this.lockedResourceId = resourceGroup.id();
        } else {
            throw new IllegalArgumentException("Missing resource group ID.");
        }
        return this;
    }

    // Getters

    @Override
    public AuthorizationManager manager() {
        return this.manager;
    }

    // CreateUpdateTaskGroup.ResourceCreator implementation
    @Override
    public Observable<ManagementLock> createResourceAsync() {
        return this.manager().inner().managementLocks().createOrUpdateByScopeAsync(this.lockedResourceId, this.name(), this.inner())
                .map(innerToFluentMap(this));
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().id() == null;
    }

    @Override
    public LockLevel level() {
        return this.inner().level();
    }

    @Override
    public String lockedResourceId() {
        return ManagementLocksImpl.resourceIdFromLockId(this.inner().id());
    }

    @Override
    public String notes() {
        return this.inner().notes();
    }

    @Override
    public String id() {
        return this.inner().id();
    }
}
