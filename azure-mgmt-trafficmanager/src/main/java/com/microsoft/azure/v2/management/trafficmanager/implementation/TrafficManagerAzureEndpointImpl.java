/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.trafficmanager.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.v2.management.trafficmanager.TrafficManagerAzureEndpoint;
import com.microsoft.azure.v2.management.trafficmanager.TargetAzureResourceType;

/**
 * Implementation for {@link TrafficManagerAzureEndpoint}.
 */
@LangDefinition
class TrafficManagerAzureEndpointImpl extends TrafficManagerEndpointImpl implements TrafficManagerAzureEndpoint {
    TrafficManagerAzureEndpointImpl(String name,
                                    TrafficManagerProfileImpl parent,
                                    EndpointInner inner,
                                    EndpointsInner client) {
        super(name, parent, inner, client);
    }

    @Override
    public String targetAzureResourceId() {
        return inner().targetResourceId();
    }

    @Override
    public TargetAzureResourceType targetResourceType() {
        return new TargetAzureResourceType(ResourceUtils.resourceProviderFromResourceId(targetAzureResourceId()),
                ResourceUtils.resourceTypeFromResourceId(targetAzureResourceId()));
    }
}
