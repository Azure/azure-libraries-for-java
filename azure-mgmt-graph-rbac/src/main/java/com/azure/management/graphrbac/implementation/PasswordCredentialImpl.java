/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.graphrbac.implementation;

import com.azure.core.management.AzureEnvironment;
import com.azure.management.RestClient;
import com.azure.management.graphrbac.PasswordCredential;
import com.azure.management.graphrbac.models.PasswordCredentialInner;
import com.azure.management.resources.fluentcore.model.implementation.IndexableRefreshableWrapperImpl;
import com.azure.management.resources.fluentcore.utils.Utils;
import com.google.common.io.BaseEncoding;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Implementation for ServicePrincipal and its parent interfaces.
 */
class PasswordCredentialImpl<T>
        extends IndexableRefreshableWrapperImpl<PasswordCredential, PasswordCredentialInner>
        implements PasswordCredential,
            PasswordCredential.Definition<T>,
            PasswordCredential.UpdateDefinition<T> {

    private String name;
    private HasCredential<?> parent;
    OutputStream authFile;
    private String subscriptionId;

    PasswordCredentialImpl(PasswordCredentialInner passwordCredential) {
        super(passwordCredential);
        if (passwordCredential.getCustomKeyIdentifier() != null && passwordCredential.getCustomKeyIdentifier().length > 0) {
            this.name = new String(passwordCredential.getCustomKeyIdentifier());
        } else {
            this.name = passwordCredential.getKeyId();
        }
    }

    PasswordCredentialImpl(String name, HasCredential<?> parent) {
        super(new PasswordCredentialInner()
                .setCustomKeyIdentifier(BaseEncoding.base64().encode(name.getBytes()).getBytes())
                .setStartDate(DateTime.now())
                .setEndDate(DateTime.now().plusYears(1)));
        this.name = name;
        this.parent = parent;
    }

    @Override
    public Mono<PasswordCredential> refreshAsync() {
        throw new UnsupportedOperationException("Cannot refresh credentials.");
    }

    @Override
    protected Mono<PasswordCredentialInner> getInnerAsync() {
        throw new UnsupportedOperationException("Cannot refresh credentials.");
    }

    @Override
    public DateTime startDate() {
        return getInner().getStartDate();
    }

    @Override
    public DateTime endDate() {
        return getInner().getEndDate();
    }

    @Override
    public String value() {
        return getInner().getValue();
    }


    @Override
    @SuppressWarnings("unchecked")
    public T attach() {
        parent.withPasswordCredential(this);
        return (T) parent;
    }

    @Override
    public PasswordCredentialImpl<T> withPasswordValue(String password) {
        getInner().setValue(password);
        return this;
    }

    @Override
    public PasswordCredentialImpl<T> withStartDate(DateTime startDate) {
        DateTime original = startDate();
        getInner().setStartDate(startDate);
        // Adjust end time
        withDuration(Duration.millis(endDate().getMillis() - original.getMillis()));
        return this;
    }

    @Override
    public PasswordCredentialImpl<T> withDuration(Duration duration) {
        getInner().setEndDate(startDate().plus(duration.getMillis()));
        return this;
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
        AzureEnvironment environment = AzureEnvironment.AZURE;

        StringBuilder builder = new StringBuilder("{\n");
        builder.append("  ").append(String.format("\"clientId\": \"%s\",", servicePrincipal.applicationId())).append("\n");
        builder.append("  ").append(String.format("\"clientSecret\": \"%s\",", value())).append("\n");
        builder.append("  ").append(String.format("\"tenantId\": \"%s\",", servicePrincipal.getManager().tenantId())).append("\n");
        builder.append("  ").append(String.format("\"subscriptionId\": \"%s\",", servicePrincipal.assignedSubscription)).append("\n");
        builder.append("  ").append(String.format("\"activeDirectoryEndpointUrl\": \"%s\",", environment.getActiveDirectoryEndpoint())).append("\n");
        builder.append("  ").append(String.format("\"resourceManagerEndpointUrl\": \"%s\",", environment.getResourceManagerEndpoint())).append("\n");
        builder.append("  ").append(String.format("\"activeDirectoryGraphResourceId\": \"%s\",", environment.getGraphEndpoint())).append("\n");
        builder.append("  ").append(String.format("\"managementEndpointUrl\": \"%s\"", environment.getManagementEndpoint())).append("\n");
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

    @Override
    public String getId() {
        return getInner().getKeyId();
    }

    @Override
    public String getName() {
        return this.name;
    }
}
