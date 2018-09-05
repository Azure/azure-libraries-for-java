/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.graphrbac.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.AzureEnvironment;
import com.microsoft.azure.v2.management.graphrbac.PasswordCredential;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.IndexableRefreshableWrapperImpl;
import io.reactivex.Maybe;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Base64;

/**
 * Implementation for ServicePrincipal and its parent interfaces.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Graph.RBAC.Fluent")
class PasswordCredentialImpl<T>
        extends IndexableRefreshableWrapperImpl<PasswordCredential, PasswordCredentialInner>
        implements
        PasswordCredential,
        PasswordCredential.Definition<T>,
        PasswordCredential.UpdateDefinition<T> {

    private String name;
    private HasCredential<?> parent;
    OutputStream authFile;
    private String subscriptionId;

    PasswordCredentialImpl(PasswordCredentialInner passwordCredential) {
        super(passwordCredential);
        if (passwordCredential.customKeyIdentifier() != null && !passwordCredential.customKeyIdentifier().isEmpty()) {
            this.name = new String(Base64.getDecoder().decode(passwordCredential.customKeyIdentifier()));
        } else {
            this.name = passwordCredential.keyId();
        }
    }

    PasswordCredentialImpl(String name, HasCredential<?> parent) {
        super(new PasswordCredentialInner()
                .withCustomKeyIdentifier(Base64.getEncoder().encodeToString(name.getBytes()))
                .withStartDate(OffsetDateTime.now())
                .withEndDate(OffsetDateTime.now().plusYears(1)));
        this.name = name;
        this.parent = parent;
    }

    @Override
    public Maybe<PasswordCredential> refreshAsync() {
        throw new UnsupportedOperationException("Cannot refresh credentials.");
    }

    @Override
    protected Maybe<PasswordCredentialInner> getInnerAsync() {
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
        parent.withPasswordCredential(this);
        return (T) parent;
    }

    @Override
    public PasswordCredentialImpl<T> withPasswordValue(String password) {
        inner().withValue(password);
        return this;
    }

    @Override
    public PasswordCredentialImpl<T> withStartDate(OffsetDateTime startDate) {
        OffsetDateTime original = startDate();
        inner().withStartDate(startDate);
        // Adjust end time
        withDuration(java.time.Duration.ofSeconds(endDate().toEpochSecond() - original.toEpochSecond()));
        return this;
    }

    @Override
    public PasswordCredentialImpl<T> withDuration(Duration duration) {
        inner().withEndDate(startDate().plus(duration));
        return this;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public PasswordCredentialImpl<T> withAuthFileToExport(OutputStream outputStream) {
        this.authFile = outputStream;
        return this;
    }

    void exportAuthFile(ServicePrincipalImpl servicePrincipal) {
        if (authFile == null) {
            return;
        }
        AzureEnvironment environment = servicePrincipal.manager().inner().azureEnvironment();

        StringBuilder builder = new StringBuilder("{\n");
        builder.append("  ").append(String.format("\"clientId\": \"%s\",", servicePrincipal.applicationId())).append("\n");
        builder.append("  ").append(String.format("\"clientSecret\": \"%s\",", value())).append("\n");
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
    public PasswordCredentialImpl<T> withSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }
}
