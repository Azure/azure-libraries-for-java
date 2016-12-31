/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.network.implementation;

import com.microsoft.azure.management.network.VirtualMachineScaleSetNetworkInterface;
import com.microsoft.azure.management.network.VirtualMachineScaleSetNicIpConfiguration;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import rx.Observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The implementation for {@link VirtualMachineScaleSetNetworkInterface}.
 */
class VirtualMachineScaleSetNetworkInterfaceImpl
        extends
        ResourceImpl<VirtualMachineScaleSetNetworkInterface,
                NetworkInterfaceInner,
                VirtualMachineScaleSetNetworkInterfaceImpl>
        implements
        VirtualMachineScaleSetNetworkInterface {
    private final NetworkInterfacesInner client;
    private final NetworkManager networkManager;
    private final String scaleSetName;
    private final String resourceGroupName;

    VirtualMachineScaleSetNetworkInterfaceImpl(String name,
                                                      String scaleSetName,
                                                      String resourceGroupName,
                                                      NetworkInterfaceInner innerObject,
                                                      NetworkInterfacesInner client,
                                                      NetworkManager networkManager) {
        super(name, innerObject);
        this.scaleSetName = scaleSetName;
        this.resourceGroupName = resourceGroupName;
        this.client = client;
        this.networkManager = networkManager;
    }

    @Override
    public boolean isIpForwardingEnabled() {
        return Utils.toPrimitiveBoolean(this.inner().enableIPForwarding());
    }

    @Override
    public String macAddress() {
        return this.inner().macAddress();
    }

    @Override
    public String internalDnsNameLabel() {
        if (this.inner().dnsSettings() == null) {
            return null;
        }
        return this.inner().dnsSettings().internalDnsNameLabel();
    }

    @Override
    public String internalFqdn() {
        if (this.inner().dnsSettings() == null) {
            return null;
        }
        return this.inner().dnsSettings().internalFqdn();
    }

    @Override
    public String internalDomainNameSuffix() {
        if (this.inner().dnsSettings() == null) {
            return null;
        }
        return this.inner().dnsSettings().internalDomainNameSuffix();
    }

    @Override
    public List<String> dnsServers() {
        if (this.inner().dnsSettings() == null || this.inner().dnsSettings().dnsServers() == null) {
            return Collections.unmodifiableList(new ArrayList<String>());
        }
        return Collections.unmodifiableList(this.inner().dnsSettings().dnsServers());
    }

    @Override
    public List<String> appliedDnsServers() {
        List<String> dnsServers = new ArrayList<>();
        if (this.inner().dnsSettings() == null || this.inner().dnsSettings().appliedDnsServers() == null) {
            return Collections.unmodifiableList(dnsServers);
        }
        return Collections.unmodifiableList(this.inner().dnsSettings().appliedDnsServers());
    }

    @Override
    public Map<String, VirtualMachineScaleSetNicIpConfiguration> ipConfigurations() {
        List<NetworkInterfaceIPConfigurationInner> inners = this.inner().ipConfigurations();
        if (inners == null || inners.size() == 0) {
            return Collections.unmodifiableMap(new TreeMap<String, VirtualMachineScaleSetNicIpConfiguration>());
        }
        Map<String, VirtualMachineScaleSetNicIpConfiguration> nicIpConfigurations = new TreeMap<>();
        for (NetworkInterfaceIPConfigurationInner inner : inners) {
            VirtualMachineScaleSetNicIpConfigurationImpl nicIpConfiguration = new VirtualMachineScaleSetNicIpConfigurationImpl(inner, this, this.networkManager);
            nicIpConfigurations.put(nicIpConfiguration.name(), nicIpConfiguration);
        }
        return Collections.unmodifiableMap(nicIpConfigurations);
    }

    @Override
    public String networkSecurityGroupId() {
        if (this.inner().networkSecurityGroup() == null) {
            return null;
        }
        return this.inner().networkSecurityGroup().id();
    }

    @Override
    public String virtualMachineId() {
        if (this.inner().virtualMachine() == null) {
            return null;
        }
        return this.inner().virtualMachine().id();
    }

    @Override
    public Observable<VirtualMachineScaleSetNetworkInterface> createResourceAsync() {
        // Future: We need to think of introducing ReadonlyResourceImpl
        throw new UnsupportedOperationException();
    }

    @Override
    public VirtualMachineScaleSetNetworkInterface refresh() {
        this.setInner(this.client.getVirtualMachineScaleSetNetworkInterface(this.resourceGroupName,
                this.scaleSetName,
                ResourceUtils.nameFromResourceId(this.virtualMachineId()),
                this.name()));
        return this;
    }
}