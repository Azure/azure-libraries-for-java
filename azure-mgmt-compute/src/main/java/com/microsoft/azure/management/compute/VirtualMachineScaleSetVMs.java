/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.compute.implementation.VirtualMachineScaleSetVMsInner;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import rx.Completable;
import rx.Observable;

import java.util.Collection;

/**
 *  Entry point to virtual machine scale set instance management API.
 */
@Fluent
public interface VirtualMachineScaleSetVMs extends
        SupportsListing<VirtualMachineScaleSetVM>,
    HasInner<VirtualMachineScaleSetVMsInner> {
    /**
     * Deletes the specified virtual machine instances from the scale set.
     *
     * @param instanceIds instance IDs of the virtual machine scale set instances to be deleted
     * @return a representation of the deferred computation of this call.
     */
    Completable deleteInstancesAsync(Collection<String> instanceIds);

    /**
     * Deletes the specified virtual machine instances from the scale set.
     *
     * @param instanceIds instance IDs of the virtual machine scale set instances to be deleted
     * @return a representation of the deferred computation of this call.
     */
    Completable deleteInstancesAsync(String... instanceIds);

    /**
     * Deletes the specified virtual machine instances from the scale set.
     *
     * @param instanceIds instance IDs of the virtual machine scale set instances to be deleted
     */
    void deleteInstances(String... instanceIds);

    /**
     * Deletes the specified virtual machine instances from the scale set.
     *
     * @param instanceIds instance IDs of the virtual machine scale set instances to be deleted
     * @param forceDeletion force delete without graceful shutdown
     * @return a representation of the deferred computation of this call.
     */
    Completable deleteInstancesAsync(Collection<String> instanceIds, boolean forceDeletion);

    /**
     * Deletes the specified virtual machine instances from the scale set.
     *
     * @param instanceIds instance IDs of the virtual machine scale set instances to be deleted
     * @param forceDeletion force delete without graceful shutdown
     */
    void deleteInstances(Collection<String> instanceIds, boolean forceDeletion);

    /**
     * Get the specified virtual machine instance from the scale set.
     * @param instanceId instance ID of the virtual machine scale set instance to be fetched
     * @return the virtual machine scale set instance.
     */
    VirtualMachineScaleSetVM getInstance(String instanceId);

    /**
     * Get the specified virtual machine instance from the scale set.
     * @param instanceId instance ID of the virtual machine scale set instance to be fetched.
     * @return the virtual machine scale set instance.
     */
    Observable<VirtualMachineScaleSetVM> getInstanceAsync(String instanceId);

    /**
     * Updates the specified virtual machine instances from the scale set.
     *
     * @param instanceIds instance IDs of the virtual machine scale set instances to be updated
     * @return a representation of the deferred computation of this call.
     */
    Completable updateInstancesAsync(Collection<String> instanceIds);

    /**
     * Updates the specified virtual machine instances from the scale set.
     *
     * @param instanceIds instance IDs of the virtual machine scale set instances to be updated
     * @return a representation of the deferred computation of this call.
     */
    Completable updateInstancesAsync(String... instanceIds);

    /**
     * Updates the specified virtual machine instances from the scale set.
     *
     * @param instanceIds instance IDs of the virtual machine scale set instances to be updated
     */
    void updateInstances(String... instanceIds);

    /**
     * Simulates the eviction of the specified spot virtual machine in the scale set asynchronously.
     * The eviction will occur with 30 minutes after calling this API.
     *
     * @param instanceId The instance ID of the virtual machine.
     * @return a representation of the deferred computation of this call
     */
    Completable simulateEvictionAsync(String instanceId);

    /**
     * Simulates the eviction of the specified spot virtual machine in the scale set.
     * The eviction will occur with 30 minutes after calling this API.
     *
     * @param instanceId The instance ID of the virtual machine.
     */
    void simulateEviction(String instanceId);
}
