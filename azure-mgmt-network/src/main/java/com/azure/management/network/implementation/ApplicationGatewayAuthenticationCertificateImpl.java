/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.management.network.ApplicationGateway;
import com.azure.management.network.ApplicationGatewayAuthenticationCertificate;
import com.azure.management.network.models.ApplicationGatewayAuthenticationCertificateInner;
import com.azure.management.resources.fluentcore.arm.models.implementation.ChildResourceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

/**
 * Implementation for ApplicationGatewayAuthenticationCertificate.
 */
class ApplicationGatewayAuthenticationCertificateImpl
        extends ChildResourceImpl<
        ApplicationGatewayAuthenticationCertificateInner,
        ApplicationGatewayImpl,
        ApplicationGateway>
        implements
        ApplicationGatewayAuthenticationCertificate,
        ApplicationGatewayAuthenticationCertificate.Definition<ApplicationGateway.DefinitionStages.WithCreate>,
        ApplicationGatewayAuthenticationCertificate.UpdateDefinition<ApplicationGateway.Update>,
        ApplicationGatewayAuthenticationCertificate.Update {

    ApplicationGatewayAuthenticationCertificateImpl(ApplicationGatewayAuthenticationCertificateInner inner, ApplicationGatewayImpl parent) {
        super(inner, parent);
    }

    // Helpers

    // Getters

    @Override
    public String name() {
        return this.inner().getName();
    }

    @Override
    public String data() {
        return this.inner().getData();
    }

    // Verbs

    @Override
    public ApplicationGatewayImpl attach() {
        return this.parent().withAuthenticationCertificate(this);
    }

    // Withers

    @Override
    public ApplicationGatewayAuthenticationCertificateImpl fromBytes(byte[] data) {
        String encoded = new String(Base64.getEncoder().encode(data));
        return this.fromBase64(encoded);
    }

    @Override
    public ApplicationGatewayAuthenticationCertificateImpl fromFile(File certificateFile) throws IOException {
        if (certificateFile == null) {
            return null;
        }

        byte[] content = Files.readAllBytes(certificateFile.toPath());
        return (content != null) ? this.fromBytes(content) : null;
    }

    @Override
    public ApplicationGatewayAuthenticationCertificateImpl fromBase64(String base64data) {
        if (base64data == null) {
            return this;
        }

        this.inner().setData(base64data);
        return this;
    }
}
