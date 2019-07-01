/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute;

import com.microsoft.azure.SubResource;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.compute.implementation.ProximityPlacementGroupInner;
import com.microsoft.azure.management.compute.implementation.ResourceSkuInner;
import com.microsoft.azure.management.resources.fluentcore.arm.AvailabilityZoneId;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Type representing Proximity Placement Group for an Azure compute resource.
 */
@Fluent
public interface ProximityPlacementGroup extends HasInner<ProximityPlacementGroupInner> {
    /**
     * Get specifies the type of the proximity placement group. &lt;br&gt;&lt;br&gt; Possible values are: &lt;br&gt;&lt;br&gt; **Standard** &lt;br&gt;&lt;br&gt; **Ultra**. Possible values include: 'Standard', 'Ultra'.
     *
     * @return the proximityPlacementGroupType value
     */
    ProximityPlacementGroupType proximityPlacementGroupType();

    /**
     * Get a list of references to all virtual machines in the proximity placement group.
     *
     * @return the virtualMachines value
     */
    List<String> virtualMachineIds();

    /**
     * Get a list of references to all virtual machine scale sets in the proximity placement group.
     *
     * @return the virtualMachineScaleSets value
     */
    List<String> virtualMachineScaleSetIds();

    /**
     * Get a list of references to all availability sets in the proximity placement group.
     *
     * @return the availabilitySets value
     */
    List<String> availabilitySetIds();
}
