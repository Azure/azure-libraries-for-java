/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.locks.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.locks.ManagementLock;
import com.microsoft.azure.management.locks.ManagementLocks;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.CreatableResourcesImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.RXMapper;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;

import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

/**
 *  Implementation for ManagementLocks.
 */
@LangDefinition
class ManagementLocksImpl
    extends CreatableResourcesImpl<ManagementLock, ManagementLockImpl, ManagementLockObjectInner>
    implements ManagementLocks {

    private final AuthorizationManager manager;

    ManagementLocksImpl(final AuthorizationManager manager) {
        this.manager = manager;
    }

    /**
     * Returns the part of the specified management lock resource ID representing the resource the lock is associated with.
     * @param lockId a lock resource ID
     * @return a resource ID
     */
    public static String resourceIdFromLockId(String lockId) {
        String[] lockIdParts = lockIdParts(lockId);
        if (lockIdParts == null) {
            return null;
        }

        StringBuilder resourceId = new StringBuilder();
        for (int i = 0; i < lockIdParts.length - 4; i++) {
            resourceId.append("/").append(lockIdParts[i]);
        }

        return resourceId.toString();
    }

    private static String[] lockIdParts(String lockId) {
        if (lockId == null) {
            return null;
        }

        // Format examples:
        // /subscriptions/0b1f6471-1bf0-4dda-aec3-cb9272f09590/resourceGroups/anu-abc/providers/Microsoft.Authorization/locks/lock-1
        // /subscriptions/0b1f6471-1bf0-4dda-aec3-cb9272f09590/resourceGroups/anu-abc/providers/Microsoft.Storage/storageAccounts/anustg234/providers/Microsoft.Authorization/locks/lock-2

        String[] parts = lockId.split("/");
        if (parts.length < 4) {
            // ID too short to be possibly a lock ID
            return null;
        }

        if (!parts[parts.length - 2].equalsIgnoreCase("locks")
                || !parts[parts.length - 3].equalsIgnoreCase("Microsoft.Authorization")
                || !parts[parts.length - 4].equalsIgnoreCase("providers")) {
            // Not a lock ID
            return null;
        }

        return parts;
    }

    @Override
    public ManagementLockImpl define(String name) {
        return wrapModel(name);
    }

    // Fluent model create helpers

    @Override
    protected ManagementLockImpl wrapModel(String name) {
        ManagementLockObjectInner inner = new ManagementLockObjectInner();

        return new ManagementLockImpl(name, inner, this.manager());
    }

    @Override
    protected ManagementLockImpl wrapModel(ManagementLockObjectInner inner) {
        if (inner == null) {
            return null;
        }
        return new ManagementLockImpl(inner.id(), inner, this.manager());
    }

    @Override
    public PagedList<ManagementLock> list() {
        return wrapList(this.inner().list());
    }

    @Override
    public Observable<ManagementLock> listAsync() {
        return wrapPageAsync(inner().listAsync());
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        String scope = resourceIdFromLockId(id);
        String lockName = ResourceUtils.nameFromResourceId(id);
        if (scope != null && lockName != null) {
            return inner().deleteByScopeAsync(scope, lockName).toCompletable();
        } else {
            return Observable.empty().toCompletable();
        }
    }

    @Override
    public PagedList<ManagementLock> listByResourceGroup(String resourceGroupName) {
        return wrapList(inner().listByResourceGroup(resourceGroupName));
    }

    @Override
    public Observable<ManagementLock> listByResourceGroupAsync(String resourceGroupName) {
        return wrapPageAsync(inner().listByResourceGroupAsync(resourceGroupName));
    }

    @Override
    public ManagementLock getByResourceGroup(String resourceGroupName, String name) {
        return this.getByResourceGroupAsync(resourceGroupName, name).toBlocking().last();
    }

    @Override
    public ServiceFuture<ManagementLock> getByResourceGroupAsync(String resourceGroupName, String name, ServiceCallback<ManagementLock> callback) {
        return ServiceFuture.fromBody(getByResourceGroupAsync(resourceGroupName, name), callback);
    }

    @Override
    public Observable<ManagementLock> getByResourceGroupAsync(String resourceGroupName, String name) {
        return this.inner().getByResourceGroupAsync(resourceGroupName, name).map(new Func1<ManagementLockObjectInner, ManagementLock>() {
            @Override
            public ManagementLock call(ManagementLockObjectInner innerT) {
                return wrapModel(innerT);
            }
        });
    }

    @Override
    public ManagementLock getById(String id) {
        return this.getByIdAsync(id).toBlocking().last();
    }

    @Override
    public Observable<ManagementLock> getByIdAsync(String id) {
        String resourceId = resourceIdFromLockId(id);
        String lockName = ResourceUtils.nameFromResourceId(id);
        if (resourceId != null || lockName != null) {
            return this.inner().getByScopeAsync(resourceId, lockName).map(new Func1<ManagementLockObjectInner, ManagementLock>() {

                @Override
                public ManagementLock call(ManagementLockObjectInner inner) {
                    return wrapModel(inner);
                }
            });
        } else {
            return null;
        }
    }

    @Override
    public ServiceFuture<ManagementLock> getByIdAsync(String id, ServiceCallback<ManagementLock> callback) {
        return ServiceFuture.fromBody(getByIdAsync(id), callback);
    }

    @Override
    public ServiceFuture<Void> deleteByResourceGroupAsync(String resourceGroupName, String name, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(deleteByResourceGroupAsync(resourceGroupName, name).andThen(Observable.<Void>just(null)), callback);
    }

    @Override
    public void deleteByResourceGroup(String resourceGroupName, String name) {
        this.deleteByResourceGroupAsync(resourceGroupName, name).await();
    }

    @Override
    public Completable deleteByResourceGroupAsync(String resourceGroupName, String name) {
        return this.inner().deleteAtResourceGroupLevelAsync(resourceGroupName, name).toCompletable();
    }

    @Override
    public Observable<String> deleteByIdsAsync(Collection<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Observable.empty();
        }

        Collection<Observable<String>> observables = new ArrayList<>();
        for (String id : ids) {
            String lockName = ResourceUtils.nameFromResourceId(id);
            String scopeName = ManagementLocksImpl.resourceIdFromLockId(id);
            if (lockName != null && scopeName != null) {
                Observable<String> o = RXMapper.map(this.inner().deleteByScopeAsync(scopeName, lockName), id);
                observables.add(o);
            }
        }

        return Observable.mergeDelayError(observables);
    }

    @Override
    public Observable<String> deleteByIdsAsync(String... ids) {
        return this.deleteByIdsAsync(new ArrayList<String>(Arrays.asList(ids)));
    }

    @Override
    public void deleteByIds(Collection<String> ids) {
        this.deleteByIdsAsync(ids).toCompletable().await();
    }

    @Override
    public void deleteByIds(String... ids) {
        this.deleteByIdsAsync(ids).toCompletable().await();
    }

    @Override
    public AuthorizationManager manager() {
        return this.manager;
    }

    @Override
    public ManagementLocksInner inner() {
        return this.manager().inner().managementLocks();
    }
}