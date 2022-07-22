/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.compute.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.compute.VirtualMachineScaleSetVM;
import com.microsoft.azure.management.compute.VirtualMachineScaleSetVMs;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Implementation for {@link VirtualMachineScaleSetVMs}.
 */
@LangDefinition
class VirtualMachineScaleSetVMsImpl
        extends
        ReadableWrappersImpl<VirtualMachineScaleSetVM, VirtualMachineScaleSetVMImpl, VirtualMachineScaleSetVMInner>
        implements
        VirtualMachineScaleSetVMs {

    private final VirtualMachineScaleSetImpl scaleSet;
    private final VirtualMachineScaleSetVMsInner client;
    private final ComputeManager computeManager;

    VirtualMachineScaleSetVMsImpl(VirtualMachineScaleSetImpl scaleSet,
                                  VirtualMachineScaleSetVMsInner client,
                                  ComputeManager computeManager) {
        this.scaleSet = scaleSet;
        this.client = client;
        this.computeManager = computeManager;
    }

    @Override
    protected VirtualMachineScaleSetVMImpl wrapModel(VirtualMachineScaleSetVMInner inner) {
        if (inner == null) {
            return null;
        }
        return new VirtualMachineScaleSetVMImpl(inner, this.scaleSet, this.client, this.computeManager);
    }

    @Override
    public PagedList<VirtualMachineScaleSetVM> list() {
        return super.wrapList(this.client.list(this.scaleSet.resourceGroupName(), this.scaleSet.name()));
    }

    @Override
    public VirtualMachineScaleSetVMsInner inner() {
        return this.client;
    }

    @Override
    public Observable<VirtualMachineScaleSetVM> listAsync() {
        return super.wrapPageAsync(this.client.listAsync(this.scaleSet.resourceGroupName(), this.scaleSet.name()));
    }

    @Override
    public Completable deleteInstancesAsync(Collection<String> instanceIds) {
        return this.scaleSet.manager().virtualMachineScaleSets().deleteInstancesAsync(this.scaleSet.resourceGroupName(),
                this.scaleSet.name(), instanceIds, false);
    }

    @Override
    public Completable deleteInstancesAsync(String... instanceIds) {
        return this.deleteInstancesAsync(new ArrayList<>(Arrays.asList(instanceIds)));
    }

    @Override
    public void deleteInstances(String... instanceIds) {
        this.deleteInstancesAsync(instanceIds).await();
    }

    @Override
    public Completable deleteInstancesAsync(Collection<String> instanceIds, boolean forceDeletion) {
        return this.scaleSet.manager().virtualMachineScaleSets().deleteInstancesAsync(
                this.scaleSet.resourceGroupName(), this.scaleSet.name(), instanceIds, forceDeletion);
    }

    @Override
    public void deleteInstances(Collection<String> instanceIds, boolean forceDeletion) {
        this.deleteInstancesAsync(instanceIds, forceDeletion).await();
    }

    @Override
    public VirtualMachineScaleSetVM getInstance(String instanceId) {
        return this.wrapModel(client.get(this.scaleSet.resourceGroupName(), this.scaleSet.name(), instanceId));
    }

    @Override
    public Observable<VirtualMachineScaleSetVM> getInstanceAsync(String instanceId) {
        return this.client.getAsync(this.scaleSet.resourceGroupName(), this.scaleSet.name(), instanceId)
                .map(new Func1<VirtualMachineScaleSetVMInner, VirtualMachineScaleSetVM>() {
                    @Override
                    public VirtualMachineScaleSetVM call(VirtualMachineScaleSetVMInner inner) {
                        return wrapModel(inner);
                    }
                });
    }

    @Override
    public Completable updateInstancesAsync(Collection<String> instanceIds) {
        if (instanceIds == null || instanceIds.size() == 0) {
            return Completable.complete();
        }
        List<String> instanceIdList = new ArrayList<>();
        for (String instanceId : instanceIds) {
            instanceIdList.add(instanceId);
        }
        VirtualMachineScaleSetsInner scaleSetInnerManager = this.scaleSet.manager().virtualMachineScaleSets().inner();
        return scaleSetInnerManager.updateInstancesAsync(this.scaleSet.resourceGroupName(),
                this.scaleSet.name(), instanceIdList).toCompletable();
    }

    @Override
    public Completable updateInstancesAsync(String... instanceIds) {
        return this.updateInstancesAsync(new ArrayList<String>(Arrays.asList(instanceIds)));
    }

    @Override
    public void updateInstances(String... instanceIds) {
        this.updateInstancesAsync(instanceIds).await();
    }

    @Override
    public void simulateEviction(String instanceId) {
        this.simulateEvictionAsync(instanceId).await();
    }

    @Override
    public Completable simulateEvictionAsync(String instanceId) {
        return this.client.simulateEvictionAsync(this.scaleSet.resourceGroupName(), this.scaleSet.name(), instanceId).toCompletable();
    }
}
