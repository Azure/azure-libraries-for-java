/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network.implementation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.network.ApplicationGateway;
import com.microsoft.azure.v2.management.network.ApplicationGatewayAuthenticationCertificate;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.implementation.ChildResourceImpl;

/**
 *  Implementation for ApplicationGatewayAuthenticationCertificate.
 */
@LangDefinition
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
        return this.inner().name();
    }

    @Override
    public String data() {
        return this.inner().data();
    }

    // Verbs

    @Override
    public ApplicationGatewayImpl attach() {
        return this.parent().withAuthenticationCertificate(this);
    }

    // Withers

    @Override
    public ApplicationGatewayAuthenticationCertificateImpl fromBytes(byte[] data) {
        String encoded = Base64.getEncoder().encodeToString(data);
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

        this.inner().withData(base64data);
        return this;
    }
}
