/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.graphrbac.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.AzureEnvironment;
import com.microsoft.azure.v2.management.graphrbac.CertificateCredential;
import com.microsoft.azure.v2.management.graphrbac.CertificateType;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.IndexableRefreshableWrapperImpl;
import io.reactivex.Maybe;

import java.io.IOException;
import java.io.OutputStream;
import java.time.OffsetDateTime;
import java.util.Base64;

/**
 * Implementation for ServicePrincipal and its parent interfaces.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Graph.RBAC.Fluent")
class CertificateCredentialImpl<T>
        extends IndexableRefreshableWrapperImpl<CertificateCredential, KeyCredentialInner>
        implements
        CertificateCredential,
        CertificateCredential.Definition<T>,
        CertificateCredential.UpdateDefinition<T> {

    private String name;
    private HasCredential<?> parent;
    private OutputStream authFile;
    private String privateKeyPath;
    private String privateKeyPassword;

    CertificateCredentialImpl(KeyCredentialInner keyCredential) {
        super(keyCredential);
        if (keyCredential.customKeyIdentifier() != null && keyCredential.customKeyIdentifier().length > 0) {
            this.name = new String(keyCredential.customKeyIdentifier());
        } else {
            this.name = keyCredential.keyId();
        }
    }

    CertificateCredentialImpl(String name, HasCredential<?> parent) {
        super(new KeyCredentialInner()
                .withUsage("Verify")
                // TODO: service no longer takes string but byte[], check encoding is necessary
                .withCustomKeyIdentifier(Base64.getEncoder().encode(name.getBytes()))
                .withStartDate(OffsetDateTime.now())
                .withEndDate(OffsetDateTime.now().plusYears(1)));
        this.name = name;
        this.parent = parent;
    }

    @Override
    public Maybe<CertificateCredential> refreshAsync() {
        throw new UnsupportedOperationException("Cannot refresh credentials.");
    }

    @Override
    protected Maybe<KeyCredentialInner> getInnerAsync() {
        throw new UnsupportedOperationException("Cannot refresh credentials.");
    }

    @Override
    public String id() {
        return inner().keyId();
    }

    @Override
    public OffsetDateTime startDate() {
        return inner().startDate();
    }

    @Override
    public OffsetDateTime endDate() {
        return inner().endDate();
    }

    @Override
    public String value() {
        return inner().value();
    }


    @Override
    @SuppressWarnings("unchecked")
    public T attach() {
        parent.withCertificateCredential(this);
        return (T) parent;
    }

    @Override
    public CertificateCredentialImpl<T> withStartDate(OffsetDateTime startDate) {
        OffsetDateTime original = startDate();
        inner().withStartDate(startDate);
        // Adjust end time
        withDuration(java.time.Duration.ofSeconds(endDate().toEpochSecond() - original.toEpochSecond()));
        return this;
    }

    @Override
    public CertificateCredentialImpl<T> withDuration(java.time.Duration duration) {
        inner().withEndDate(startDate().plus(duration));
        return this;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public CertificateCredentialImpl<T> withAsymmetricX509Certificate() {
        inner().withType(CertificateType.ASYMMETRIC_X509_CERT.toString());
        return this;
    }

    @Override
    public CertificateCredentialImpl<T> withSymmetricEncryption() {
        inner().withType(CertificateType.SYMMETRIC.toString());
        return this;
    }

    @Override
    public CertificateCredentialImpl<T> withPublicKey(byte[] certificate) {
        inner().withValue(Base64.getEncoder().encodeToString(certificate));
        return this;
    }

    @Override
    public CertificateCredentialImpl<T> withSecretKey(byte[] secret) {
        inner().withValue(Base64.getEncoder().encodeToString(secret));
        return this;
    }

    void exportAuthFile(ServicePrincipalImpl servicePrincipal) {
        if (authFile == null) {
            return;
        }

        AzureEnvironment environment = servicePrincipal.manager().inner().azureEnvironment();

        StringBuilder builder = new StringBuilder("{\n");
        builder.append("  ").append(String.format("\"clientId\": \"%s\",", servicePrincipal.applicationId())).append("\n");
        builder.append("  ").append(String.format("\"clientCertificate\": \"%s\",", privateKeyPath.replace("\\", "\\\\"))).append("\n");
        builder.append("  ").append(String.format("\"clientCertificatePassword\": \"%s\",", privateKeyPassword)).append("\n");
        builder.append("  ").append(String.format("\"tenantId\": \"%s\",", servicePrincipal.manager().tenantId())).append("\n");
        builder.append("  ").append(String.format("\"subscriptionId\": \"%s\",", servicePrincipal.assignedSubscription)).append("\n");
        builder.append("  ").append(String.format("\"activeDirectoryEndpointUrl\": \"%s\",", environment.activeDirectoryEndpoint())).append("\n");
        builder.append("  ").append(String.format("\"resourceManagerEndpointUrl\": \"%s\",", environment.resourceManagerEndpoint())).append("\n");
        builder.append("  ").append(String.format("\"activeDirectoryGraphResourceId\": \"%s\",", environment.graphEndpoint())).append("\n");
        builder.append("  ").append(String.format("\"managementEndpointUrl\": \"%s\"", environment.managementEndpoint())).append("\n");
        builder.append("}");
        try {
            authFile.write(builder.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CertificateCredentialImpl<T> withAuthFileToExport(OutputStream outputStream) {
        this.authFile = outputStream;
        return this;
    }

    @Override
    public CertificateCredentialImpl<T> withPrivateKeyFile(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
        return this;
    }

    @Override
    public CertificateCredentialImpl<T> withPrivateKeyPassword(String privateKeyPassword) {
        this.privateKeyPassword = privateKeyPassword;
        return this;
    }
}
