// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.management;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenRequestContext;
import com.azure.core.management.AzureEnvironment;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;

public class ApplicationTokenCredential extends AzureTokenCredential {

    private String clientId;

    private String clientSecret;

    private byte[] clientCertificate;

    private String clientCertificatePassword;

    public ApplicationTokenCredential(String clientId, String domain, String secret, AzureEnvironment environment) {
        super(environment, domain);
        this.clientId = clientId;
        this.clientSecret = secret;
    }


    public ApplicationTokenCredential(String clientId, String domain, byte[] certificate, String password, AzureEnvironment environment) {
        super(environment, domain);
        this.clientId = clientId;
        this.clientCertificate = certificate;
        this.clientCertificatePassword = password;
    }

    public static ApplicationTokenCredential fromFile(File credentialFile) throws IOException {
        return AuthFile.parse(credentialFile).generateCredential();
    }

    public String getClientId() {
        return this.clientId;
    }

    String getClientSecret() {
        return this.clientSecret;
    }

    byte[] getClientCertificate() {
        return this.clientCertificate;
    }

    String getClientCertificatePassword() {
        return this.clientCertificatePassword;
    }


    @Override
    public synchronized Mono<AccessToken> getToken(TokenRequestContext request) {
        // TODO: Add client certificate token
        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(this.getClientId())
                .clientSecret(this.getClientSecret())
                .tenantId(getDomain())
                .build();
        return clientSecretCredential.getToken(request);
    }
}
