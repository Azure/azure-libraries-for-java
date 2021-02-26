/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.resources.fluentcore.arm.AvailabilityZoneId;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ComputeSkuTetsts extends ComputeManagementTest {
    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        super.initializeClients(restClient, defaultSubscription, domain);
    }
//    @Test
//    public void Foo() {
//        HashSet<EncryptionStatus> s = new HashSet<>();
//        s.add(EncryptionStatus.NOT_ENCRYPTED);
//        s.add(EncryptionStatus.NOT_ENCRYPTED);
//
//
//        System.out.println(s.contains(EncryptionStatus.fromString("notEncrypted")));
//    }

    @Test
    public void canListSkus() throws Exception {
        PagedList<ComputeSku> skus = this.computeManager.computeSkus().list();

        boolean atleastOneVirtualMachineResourceSku = false;
        boolean atleastOneAvailabilitySetResourceSku = false;
        boolean atleastOneDiskResourceSku = false;
        boolean atleastOneSnapshotResourceSku = false;
        boolean atleastOneRegionWithZones = false;
        for (ComputeSku sku : skus) {
            Assert.assertNotNull(sku.resourceType());
            Assert.assertNotNull(sku.regions());
            if (sku.resourceType().equals(ComputeResourceType.VIRTUALMACHINES)) {
                Assert.assertNotNull(sku.virtualMachineSizeType());
                Assert.assertEquals(sku.virtualMachineSizeType().toString().toLowerCase(), sku.name().toString().toLowerCase());
                Assert.assertNull(sku.availabilitySetSkuType());
                Assert.assertNull(sku.diskSkuType());
                atleastOneVirtualMachineResourceSku = true;

                for (Map.Entry<Region, Set<AvailabilityZoneId>> zoneMapEntry: sku.zones().entrySet()) {
                     Region region = zoneMapEntry.getKey();
                     Assert.assertNotNull(region);
                     Set<AvailabilityZoneId> zones = zoneMapEntry.getValue();
                     if (zones.size() > 0) {
                         atleastOneRegionWithZones = true;
                     }
                }
            }
            if (sku.resourceType().equals(ComputeResourceType.AVAILABILITYSETS)) {
                Assert.assertNotNull(sku.availabilitySetSkuType());
                Assert.assertEquals(sku.availabilitySetSkuType().toString().toLowerCase(), sku.name().toString().toLowerCase());
                Assert.assertNull(sku.virtualMachineSizeType());
                Assert.assertNull(sku.diskSkuType());
                atleastOneAvailabilitySetResourceSku = true;
            }
            if (sku.resourceType().equals(ComputeResourceType.DISKS)) {
                Assert.assertNotNull(sku.diskSkuType().toString());
                Assert.assertEquals(sku.diskSkuType().toString().toLowerCase(), sku.name().toString().toLowerCase());
                Assert.assertNull(sku.virtualMachineSizeType());
                Assert.assertNull(sku.availabilitySetSkuType());
                atleastOneDiskResourceSku = true;
            }
            if (sku.resourceType().equals(ComputeResourceType.SNAPSHOTS)) {
                Assert.assertNotNull(sku.diskSkuType());
                Assert.assertEquals(sku.diskSkuType().toString().toLowerCase(), sku.name().toString().toLowerCase());
                Assert.assertNull(sku.virtualMachineSizeType());
                Assert.assertNull(sku.availabilitySetSkuType());
                atleastOneSnapshotResourceSku = true;
            }
        }
        Assert.assertTrue(atleastOneVirtualMachineResourceSku);
        Assert.assertTrue(atleastOneAvailabilitySetResourceSku);
        Assert.assertTrue(atleastOneDiskResourceSku);
        Assert.assertTrue(atleastOneSnapshotResourceSku);
        Assert.assertTrue(atleastOneRegionWithZones);
    }

    @Test
    public void canListSkusByRegion() throws Exception {
        PagedList<ComputeSku> skus = this.computeManager.computeSkus().listByRegion(Region.US_EAST2);
        for (ComputeSku sku : skus) {
            Assert.assertTrue(sku.regions().contains(Region.US_EAST2));
        }

        skus = this.computeManager.computeSkus().listByRegion(Region.fromName("Unknown"));
        Assert.assertNotNull(skus);
        Assert.assertEquals(0, skus.size());
    }

    @Test
    public void canListSkusByResourceType() throws Exception {
        PagedList<ComputeSku> skus = this.computeManager.computeSkus().listByResourceType(ComputeResourceType.VIRTUALMACHINES);
        for (ComputeSku sku : skus) {
            Assert.assertTrue(sku.resourceType().equals(ComputeResourceType.VIRTUALMACHINES));
        }

        skus = this.computeManager.computeSkus().listByResourceType(ComputeResourceType.fromString("Unknown"));
        Assert.assertNotNull(skus);
        Assert.assertEquals(0, skus.size());
    }

    @Test
    public void canListSkusByRegionAndResourceType() throws Exception {
        PagedList<ComputeSku> skus = this.computeManager.computeSkus().listbyRegionAndResourceType(Region.US_EAST2, ComputeResourceType.VIRTUALMACHINES);
        for (ComputeSku sku : skus) {
            Assert.assertTrue(sku.resourceType().equals(ComputeResourceType.VIRTUALMACHINES));
            Assert.assertTrue(sku.regions().contains(Region.US_EAST2));
        }

        skus = this.computeManager.computeSkus().listbyRegionAndResourceType(Region.US_EAST2, ComputeResourceType.fromString("Unknown"));
        Assert.assertNotNull(skus);
        Assert.assertEquals(0, skus.size());
    }
}
