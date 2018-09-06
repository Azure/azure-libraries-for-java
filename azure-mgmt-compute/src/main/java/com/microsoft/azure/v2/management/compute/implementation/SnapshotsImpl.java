/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.compute.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.compute.AccessLevel;
import com.microsoft.azure.v2.management.compute.GrantAccessData;
import com.microsoft.azure.v2.management.compute.Snapshot;
import com.microsoft.azure.v2.management.compute.Snapshots;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * The implementation for Snapshots.
 */
@LangDefinition
class SnapshotsImpl
    extends TopLevelModifiableResourcesImpl<
        Snapshot,
        SnapshotImpl,
        SnapshotInner,
        SnapshotsInner,
        ComputeManager>
    implements Snapshots {

    SnapshotsImpl(ComputeManager computeManager) {
        super(computeManager.inner().snapshots(), computeManager);
    }

    @Override
    public Observable<String> grantAccessAsync(String resourceGroupName, String snapshotName, AccessLevel accessLevel, int accessDuration) {
        GrantAccessData grantAccessDataInner = new GrantAccessData();
        grantAccessDataInner.withAccess(accessLevel)
                .withDurationInSeconds(accessDuration);
        return this.inner().grantAccessAsync(resourceGroupName, snapshotName, grantAccessDataInner)
                .map(accessUriInner -> accessUriInner.accessSAS())
                .toObservable();
    }

    @Override
    public ServiceFuture<String> grantAccessAsync(String resourceGroupName, String snapshotName, AccessLevel accessLevel, int accessDuration, ServiceCallback<String> callback) {
        return ServiceFuture.fromBody(this.grantAccessAsync(resourceGroupName, snapshotName, accessLevel, accessDuration).lastElement(), callback);
    }

    @Override
    public String grantAccess(String resourceGroupName,
                              String snapshotName,
                              AccessLevel accessLevel,
                              int accessDuration) {
        return this.grantAccessAsync(resourceGroupName, snapshotName, accessLevel, accessDuration)
                .blockingLast(null);
    }

    @Override
    public Completable revokeAccessAsync(String resourceGroupName, String snapName) {
        return this.inner().revokeAccessAsync(resourceGroupName, snapName);
    }

    @Override
    public ServiceFuture<Void> revokeAccessAsync(String resourceGroupName, String snapName, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(this.revokeAccessAsync(resourceGroupName, snapName), callback);
    }

    @Override
    public void revokeAccess(String resourceGroupName, String snapName) {
        this.revokeAccessAsync(resourceGroupName, snapName).blockingAwait();
    }

    @Override
    protected SnapshotImpl wrapModel(String name) {
        return new SnapshotImpl(name, new SnapshotInner(), this.manager());
    }

    @Override
    protected SnapshotImpl wrapModel(SnapshotInner inner) {
        if (inner == null) {
            return null;
        }
        return new SnapshotImpl(inner.name(), inner, this.manager());
    }

    @Override
    public Snapshot.DefinitionStages.Blank define(String name) {
        return this.wrapModel(name);
    }
}