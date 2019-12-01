//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.microsoft.azure.management;

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

    // private Map<String, AuthenticationResult> tokens;

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
        // this.tokens = new HashMap<>();
    }

    public static ApplicationTokenCredential fromFile(File credentialsFile) throws IOException {
        return AuthFile.parse(credentialsFile).generateCredentials();
    }

    public String clientId() {
        return this.clientId;
    }

    String clientSecret() {
        return this.clientSecret;
    }

    byte[] clientCertificate() {
        return this.clientCertificate;
    }

    String clientCertificatePassword() {
        return this.clientCertificatePassword;
    }

    @Override
    public synchronized Mono<AccessToken> getToken(String resource)  {
        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(this.clientId)
                .clientSecret(this.clientSecret)
                .tenantId(domain())
                .build();
        return clientSecretCredential.getToken(new TokenRequestContext().addScopes(this.environment().getResourceManagerEndpoint() + "/.default"));
    }
}
