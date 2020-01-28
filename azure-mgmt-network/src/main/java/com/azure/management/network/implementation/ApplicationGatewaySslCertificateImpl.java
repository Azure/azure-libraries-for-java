/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.management.network.ApplicationGateway;
import com.azure.management.network.ApplicationGatewaySslCertificate;
import com.azure.management.network.models.ApplicationGatewaySslCertificateInner;
import com.azure.management.resources.fluentcore.arm.models.implementation.ChildResourceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

/**
 * Implementation for ApplicationGatewaySslCertificate.
 */
class ApplicationGatewaySslCertificateImpl
        extends ChildResourceImpl<ApplicationGatewaySslCertificateInner, ApplicationGatewayImpl, ApplicationGateway>
        implements
        ApplicationGatewaySslCertificate,
        ApplicationGatewaySslCertificate.Definition<ApplicationGateway.DefinitionStages.WithCreate>,
        ApplicationGatewaySslCertificate.UpdateDefinition<ApplicationGateway.Update>,
        ApplicationGatewaySslCertificate.Update {

    ApplicationGatewaySslCertificateImpl(ApplicationGatewaySslCertificateInner inner, ApplicationGatewayImpl parent) {
        super(inner, parent);
    }

    // Helpers

    // Getters

    @Override
    public String name() {
        return this.inner().getName();
    }

    @Override
    public String publicData() {
        return this.inner().getPublicCertData();
    }

    @Override
    public String keyVaultSecretId() {
        return this.inner().getKeyVaultSecretId();
    }

    // Verbs

    @Override
    public ApplicationGatewayImpl attach() {
        return this.parent().withSslCertificate(this);
    }


    // Withers

    @Override
    public ApplicationGatewaySslCertificateImpl withPfxFromBytes(byte[] pfxData) {
        String encoded = new String(Base64.getEncoder().encode(pfxData));
        this.inner().setData(encoded);
        return this;
    }

    @Override
    public ApplicationGatewaySslCertificateImpl withPfxFromFile(File pfxFile) throws IOException {
        if (pfxFile == null) {
            return null;
        }

        byte[] content = Files.readAllBytes(pfxFile.toPath());
        return (content != null) ? withPfxFromBytes(content) : null;
    }

    @Override
    public ApplicationGatewaySslCertificateImpl withPfxPassword(String password) {
        this.inner().setPassword(password);
        return this;
    }

    @Override
    public ApplicationGatewaySslCertificateImpl withKeyVaultSecretId(String keyVaultSecretId) {
        this.inner().setKeyVaultSecretId(keyVaultSecretId);
        return this;
    }

}
