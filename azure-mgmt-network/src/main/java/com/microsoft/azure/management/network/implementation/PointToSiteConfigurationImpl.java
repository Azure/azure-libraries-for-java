/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.network.AddressSpace;
import com.microsoft.azure.management.network.PointToSiteConfiguration;
import com.microsoft.azure.management.network.VirtualNetworkGateway;
import com.microsoft.azure.management.network.VpnClientConfiguration;
import com.microsoft.azure.management.network.VpnClientProtocol;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ChildResourceImpl;
import sun.security.provider.X509Factory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation for PointToSiteConfiguration and its create and update interfaces.
 */
@LangDefinition
class PointToSiteConfigurationImpl
        extends ChildResourceImpl<VpnClientConfiguration, VirtualNetworkGatewayImpl, VirtualNetworkGateway>
        implements
        PointToSiteConfiguration,
        PointToSiteConfiguration.Definition<VirtualNetworkGateway.Update>,
        PointToSiteConfiguration.Update {

    PointToSiteConfigurationImpl(VpnClientConfiguration inner, VirtualNetworkGatewayImpl parent) {
        super(inner, parent);
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public VirtualNetworkGatewayImpl attach() {
        this.parent().attachPointToSiteConfiguration(this);
        return parent();
    }

    @Override
    public PointToSiteConfigurationImpl withAddressPool(String addressPool) {
        List<String> addressPrefixes = new ArrayList<>();
        addressPrefixes.add(addressPool);
        inner().withVpnClientAddressPool(new AddressSpace().withAddressPrefixes(addressPrefixes));
        return this;
    }

    @Override
    public PointToSiteConfigurationImpl withRootCertificate(String name, String certificateData) {
        if (inner().vpnClientRootCertificates() == null) {
            inner().withVpnClientRootCertificates(new ArrayList<VpnClientRootCertificateInner>());
        }
        inner().vpnClientRootCertificates().add(new VpnClientRootCertificateInner().withName(name).withPublicCertData(certificateData));
        return this;
    }

    @Override
    public PointToSiteConfigurationImpl withRootCertificateFromFile(String name, File certificateFile) throws IOException {
        if (certificateFile == null) {
            return this;
        } else {
            byte[] content = Files.readAllBytes(certificateFile.toPath());
            String certificate = new String(content).replace(X509Factory.BEGIN_CERT, "").replace(X509Factory.END_CERT, "");
            return this.withRootCertificate(name, certificate);
        }
    }

    @Override
    public Update withoutRootCertificate(String name) {
        if (inner().vpnClientRootCertificates() != null) {
            for (VpnClientRootCertificateInner certificateInner : inner().vpnClientRootCertificates()) {
                if (name.equals(certificateInner.name())) {
                    inner().vpnClientRootCertificates().remove(certificateInner);
                    break;
                }
            }
        }
        return this;
    }

    @Override
    public PointToSiteConfigurationImpl withAzureCertificate() {
        return this;
    }

    @Override
    public PointToSiteConfigurationImpl withRadiusAuthentication(String serverIPAddress, String serverSecret) {
        inner().withRadiusServerAddress(serverIPAddress).withRadiusServerSecret(serverSecret);
        return this;
    }

    @Override
    public PointToSiteConfigurationImpl withRevokedCertificate(String name, String thumbprint) {
        if (inner().vpnClientRevokedCertificates() == null) {
            inner().withVpnClientRevokedCertificates(new ArrayList<VpnClientRevokedCertificateInner>());
        }
        inner().vpnClientRevokedCertificates().add(new VpnClientRevokedCertificateInner().withName(name).withThumbprint(thumbprint));
        return this;
    }

    @Override
    public PointToSiteConfigurationImpl withSstpOnly() {
        inner().withVpnClientProtocols(Collections.singletonList(VpnClientProtocol.SSTP));
        return this;
    }

    @Override
    public PointToSiteConfigurationImpl withIkeV2Only() {
        inner().withVpnClientProtocols(Collections.singletonList(VpnClientProtocol.IKE_V2));
        return this;
    }
}
