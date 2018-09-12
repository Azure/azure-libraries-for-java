/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.compute.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.compute.AccessLevel;
import com.microsoft.azure.v2.management.compute.Disk;
import com.microsoft.azure.v2.management.compute.Disks;
import com.microsoft.azure.v2.management.compute.GrantAccessData;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * The implementation for Disks.
 */
@LangDefinition
class DisksImpl
    extends TopLevelModifiableResourcesImpl<
        Disk,
        DiskImpl,
        DiskInner,
        DisksInner,
        ComputeManager>
    implements Disks {

    DisksImpl(ComputeManager computeManager) {
        super(computeManager.inner().disks(), computeManager);
    }

    @Override
    public String grantAccess(String resourceGroupName,
                              String diskName,
                              AccessLevel accessLevel,
                              int accessDuration) {
        return this.grantAccessAsync(resourceGroupName, diskName, accessLevel, accessDuration)
                .lastElement()
                .blockingGet(null);
    }

    @Override
    public Observable<String> grantAccessAsync(String resourceGroupName, String diskName, AccessLevel accessLevel, int accessDuration) {
        GrantAccessData grantAccessDataInner = new GrantAccessData();
        grantAccessDataInner.withAccess(accessLevel)
                .withDurationInSeconds(accessDuration);
        return this.inner().grantAccessAsync(resourceGroupName, diskName, grantAccessDataInner)
                .map(accessUriInner -> accessUriInner.accessSAS())
                .toObservable();
    }

    @Override
    public ServiceFuture<String> grantAccessAsync(String resourceGroupName, String diskName, AccessLevel accessLevel, int accessDuration, ServiceCallback<String> callback) {
        return ServiceFuture.fromBody(this.grantAccessAsync(resourceGroupName, diskName, accessLevel, accessDuration).lastElement(), callback);
    }

    @Override
    public void revokeAccess(String resourceGroupName, String diskName) {
        this.inner().revokeAccess(resourceGroupName, diskName);
    }

    @Override
    public Completable revokeAccessAsync(String resourceGroupName, String diskName) {
        return this.inner().revokeAccessAsync(resourceGroupName, diskName);
    }

    @Override
    public ServiceFuture<Void> revokeAccessAsync(String resourceGroupName, String diskName, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(this.revokeAccessAsync(resourceGroupName, diskName), callback);
    }

    @Override
    protected DiskImpl wrapModel(String name) {
        return new DiskImpl(name, new DiskInner(), this.manager());
    }

    @Override
    protected DiskImpl wrapModel(DiskInner inner) {
        if (inner == null) {
            return null;
        }
        return new DiskImpl(inner.name(), inner, this.manager());
    }

    @Override
    public Disk.DefinitionStages.Blank define(String name) {
        return this.wrapModel(name);
    }
}