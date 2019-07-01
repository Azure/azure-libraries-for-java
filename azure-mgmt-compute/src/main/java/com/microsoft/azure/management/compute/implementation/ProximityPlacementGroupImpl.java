/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute.implementation;

import com.microsoft.azure.SubResource;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.compute.AvailabilitySetSkuTypes;
import com.microsoft.azure.management.compute.ComputeResourceType;
import com.microsoft.azure.management.compute.ComputeSku;
import com.microsoft.azure.management.compute.ComputeSkuName;
import com.microsoft.azure.management.compute.ComputeSkuTier;
import com.microsoft.azure.management.compute.DiskSkuTypes;
import com.microsoft.azure.management.compute.DiskStorageAccountTypes;
import com.microsoft.azure.management.compute.ProximityPlacementGroup;
import com.microsoft.azure.management.compute.ProximityPlacementGroupType;
import com.microsoft.azure.management.compute.ResourceSkuCapabilities;
import com.microsoft.azure.management.compute.ResourceSkuCapacity;
import com.microsoft.azure.management.compute.ResourceSkuCosts;
import com.microsoft.azure.management.compute.ResourceSkuLocationInfo;
import com.microsoft.azure.management.compute.ResourceSkuRestrictions;
import com.microsoft.azure.management.compute.VirtualMachineSizeTypes;
import com.microsoft.azure.management.resources.fluentcore.arm.AvailabilityZoneId;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The implementation for {@link ComputeSku}.
 */
@LangDefinition
final class ProximityPlacementGroupImpl implements ProximityPlacementGroup {
    private final ProximityPlacementGroupInner inner;

    ProximityPlacementGroupImpl(ProximityPlacementGroupInner inner) {
        this.inner = inner;
    }

    @Override
    public ProximityPlacementGroupType proximityPlacementGroupType() {
        return this.inner().proximityPlacementGroupType();
    }

    @Override
    public List<String> virtualMachineIds() {
        return getStringListFromSubResourceList(this.inner().virtualMachines());
    }

    @Override
    public List<String> virtualMachineScaleSetIds() {
        return getStringListFromSubResourceList(this.inner().virtualMachineScaleSets());
    }

    @Override
    public List<String> availabilitySetIds() {
        return getStringListFromSubResourceList(this.inner().availabilitySets());
    }

    @Override
    public ProximityPlacementGroupInner inner() {
        return this.inner();
    }

    private List<String> getStringListFromSubResourceList(List<SubResource> subList) {
        List<String> stringList = null;

        if (subList != null && !subList.isEmpty()) {
            stringList = new ArrayList<>();
            Iterator<SubResource> iter = subList.iterator();
            while (iter.hasNext()) {
                stringList.add(iter.next().id());
            }
        }

        return stringList;
    }
}
