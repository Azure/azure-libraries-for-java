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
import com.microsoft.azure.v2.management.compute.samples.ListVirtualMachineExtensionImages;
import com.microsoft.azure.v2.management.compute.samples.ListVirtualMachineImages;
import com.microsoft.azure.v2.management.compute.samples.ManageAvailabilitySet;
import com.microsoft.azure.v2.management.compute.samples.ManageManagedDisks;
import com.microsoft.azure.v2.management.compute.samples.ManageResourceFromMSIEnabledVirtualMachineBelongsToAADGroup;
import com.microsoft.azure.v2.management.compute.samples.ManageStorageFromMSIEnabledVirtualMachine;
import com.microsoft.azure.v2.management.compute.samples.ManageUserAssignedMSIEnabledVirtualMachine;
import com.microsoft.azure.v2.management.compute.samples.ManageVirtualMachine;
import com.microsoft.azure.v2.management.compute.samples.ManageVirtualMachineAsync;
import com.microsoft.azure.v2.management.compute.samples.ManageVirtualMachineExtension;
import com.microsoft.azure.v2.management.compute.samples.ManageVirtualMachineScaleSet;
import com.microsoft.azure.v2.management.compute.samples.ManageVirtualMachineScaleSetAsync;
import com.microsoft.azure.v2.management.compute.samples.ManageVirtualMachineScaleSetWithUnmanagedDisks;
import com.microsoft.azure.v2.management.compute.samples.ManageVirtualMachineWithDisk;
import com.microsoft.azure.v2.management.compute.samples.ManageVirtualMachineWithUnmanagedDisks;
import com.microsoft.azure.v2.management.compute.samples.ManageVirtualMachinesInParallel;
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

    @Test
    public void testListVirtualMachineExtensionImages() {
        Assert.assertTrue(ListVirtualMachineExtensionImages.runSample(azure));
    }

    @Test
    public void testListVirtualMachineImages() {
        Assert.assertTrue(ListVirtualMachineImages.runSample(azure));
    }

    @Test
    public void testManageAvailabilitySet() {
        Assert.assertTrue(ManageAvailabilitySet.runSample(azure));
    }

    @Test
    public void testManageManagedDisks() {
        Assert.assertTrue(ManageManagedDisks.runSample(azure));
    }

    @Test
    @Ignore("Skipping for now - looks like a service side issue")
    public void testManageResourceFromMSIEnabledVirtualMachineBelongsToAADGroup() {
        Assert.assertTrue(ManageResourceFromMSIEnabledVirtualMachineBelongsToAADGroup.runSample(azure));
    }

    @Test
    @Ignore("Skipping for now - looks like a service side issue")
    public void testManageStorageFromMSIEnabledVirtualMachine() {
        Assert.assertTrue(ManageStorageFromMSIEnabledVirtualMachine.runSample(azure));
    }

    @Test
    public void testManageUserAssignedMSIEnabledVirtualMachine() {
        Assert.assertTrue(ManageUserAssignedMSIEnabledVirtualMachine.runSample(azure));
    }

    @Test
    public void testManageVirtualMachine() {
        Assert.assertTrue(ManageVirtualMachine.runSample(azure));
    }

    @Test
    public void testManageVirtualMachineAsync() {
        Assert.assertTrue(ManageVirtualMachineAsync.runSample(azure));
    }

    @Test
    @Ignore("Error code: VMExtensionProvisioningError. VM has reported a failure when processing extension 'CustomScriptForLinux'")
    public void testManageVirtualMachineScaleSet() {
        Assert.assertTrue(ManageVirtualMachineScaleSet.runSample(azure));
    }

    @Test
    @Ignore("Netty force close error. Error: An existing connection was forcibly closed by the remote host")
    public void testManageVirtualMachineScaleSetAsync() {
        Assert.assertTrue(ManageVirtualMachineScaleSetAsync.runSample(azure));
    }

    @Test
    @Ignore("Netty force close error. Error: An existing connection was forcibly closed by the remote host")
    public void testManageVirtualMachineExtension() {
        Assert.assertTrue(ManageVirtualMachineExtension.runSample(azure));
    }

    @Test
    @Ignore("Netty force close error. Error: An existing connection was forcibly closed by the remote host")
    public void testManageVirtualMachineScaleSetWithUnmanagedDisks() {
        Assert.assertTrue(ManageVirtualMachineScaleSetWithUnmanagedDisks.runSample(azure));
    }

    @Test
    public void testManageVirtualMachineWithDisk() {
        Assert.assertTrue(ManageVirtualMachineWithDisk.runSample(azure));
    }

    @Test
    @Ignore("Netty force close error. Error: An existing connection was forcibly closed by the remote host")
    public void testManageVirtualMachineWithUnmanagedDisks() {
        Assert.assertTrue(ManageVirtualMachineWithUnmanagedDisks.runSample(azure));
    }

    @Test
    public void testManageVirtualMachinesInParallel() {
        Assert.assertTrue(ManageVirtualMachinesInParallel.runSample(azure));
    }


}