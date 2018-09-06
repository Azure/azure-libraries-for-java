/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.compute;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.compute.implementation.VirtualMachineScaleSetVMsInner;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.rest.v2.annotations.Beta;
import rx.Completable;

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
    @Beta(since="V1_4_0")
    Completable deleteInstancesAsync(Collection<String> instanceIds);

    /**
     * Deletes the specified virtual machine instances from the scale set.
     *
     * @param instanceIds instance IDs of the virtual machine scale set instances to be deleted
     * @return a representation of the deferred computation of this call.
     */
    @Beta(since="V1_4_0")
    Completable deleteInstancesAsync(String... instanceIds);

    /**
     * Deletes the specified virtual machine instances from the scale set.
     *
     * @param instanceIds instance IDs of the virtual machine scale set instances to be deleted
     */
    @Beta(since="V1_4_0")
    void deleteInstances(String... instanceIds);

    /**
     * Updates the specified virtual machine instances from the scale set.
     *
     * @param instanceIds instance IDs of the virtual machine scale set instances to be updated
     * @return a representation of the deferred computation of this call.
     */
    @Beta(since="V1_4_0")
    Completable updateInstancesAsync(Collection<String> instanceIds);

    /**
     * Updates the specified virtual machine instances from the scale set.
     *
     * @param instanceIds instance IDs of the virtual machine scale set instances to be updated
     * @return a representation of the deferred computation of this call.
     */
    @Beta(since="V1_4_0")
    Completable updateInstancesAsync(String... instanceIds);

    /**
     * Updates the specified virtual machine instances from the scale set.
     *
     * @param instanceIds instance IDs of the virtual machine scale set instances to be updated
     */
    @Beta(since="V1_4_0")
    void updateInstances(String... instanceIds);
}
