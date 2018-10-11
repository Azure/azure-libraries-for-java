/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.samples;

import com.microsoft.azure.v2.management.compute.samples.ConvertVirtualMachineToManagedDisks;
import com.microsoft.azure.v2.management.compute.samples.CreateVirtualMachineUsingCustomImageFromVHD;
import com.microsoft.azure.v2.management.compute.samples.CreateVirtualMachineUsingCustomImageFromVM;
import com.microsoft.azure.v2.management.compute.samples.CreateVirtualMachineUsingSpecializedDiskFromSnapshot;
import com.microsoft.azure.v2.management.compute.samples.CreateVirtualMachineUsingSpecializedDiskFromVhd;
import com.microsoft.azure.v2.management.compute.samples.CreateVirtualMachinesAsyncTrackingRelatedResources;
import com.microsoft.azure.v2.management.compute.samples.CreateVirtualMachinesInParallel;
import com.microsoft.azure.v2.management.compute.samples.CreateVirtualMachinesUsingCustomImageOrSpecializedVHD;
import com.microsoft.azure.v2.management.compute.samples.ListComputeSkus;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class ComputeSampleTests extends SamplesTestBase {

    @Test
    public void testConvertVirtualMachineToManagedDisks() {
        Assert.assertTrue(ConvertVirtualMachineToManagedDisks.runSample(azure));
    }

    @Test
    @Ignore("Sample leverages true parallelization, which cannot be recorded, until GenericResources support deleteByIds()")
    public void testCreateVirtualMachinesAsyncTrackingRelatedResources() {
        Assert.assertTrue(CreateVirtualMachinesAsyncTrackingRelatedResources.runSample(azure));
    }

    @Test
    @Ignore("Playback failure with unexpected GET request")
    public void testCreateVirtualMachinesInParallel() {
        Assert.assertTrue(CreateVirtualMachinesInParallel.runSample(azure));
    }

    @Test
    public void testCreateVirtualMachinesUsingCustomImageOrSpecializedVHD() {
        Assert.assertTrue(CreateVirtualMachinesUsingCustomImageOrSpecializedVHD.runSample(azure));
    }

    @Test
    public void testCreateVirtualMachineUsingCustomImageFromVHD() {
        Assert.assertTrue(CreateVirtualMachineUsingCustomImageFromVHD.runSample(azure));
    }

    @Test
    @Ignore("Need to investigate - 'Disks or snapshot cannot be resized down.'")
    public void testCreateVirtualMachineUsingCustomImageFromVM() {
        Assert.assertTrue(CreateVirtualMachineUsingCustomImageFromVM.runSample(azure));
    }

    @Test
    public void testCreateVirtualMachineUsingSpecializedDiskFromSnapshot() {
        Assert.assertTrue(CreateVirtualMachineUsingSpecializedDiskFromSnapshot.runSample(azure));
    }

    @Test
    public void testCreateVirtualMachineUsingSpecializedDiskFromVhd() {
        Assert.assertTrue(CreateVirtualMachineUsingSpecializedDiskFromVhd.runSample(azure));
    }

    @Test
    public void testListComputeSkus() {
        Assert.assertTrue(ListComputeSkus.runSample(azure));
    }

}