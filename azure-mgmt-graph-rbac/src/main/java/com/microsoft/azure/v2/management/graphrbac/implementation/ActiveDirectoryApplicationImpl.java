/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.graphrbac.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.graphrbac.ActiveDirectoryApplication;
import com.microsoft.azure.v2.management.graphrbac.ApplicationCreateParameters;
import com.microsoft.azure.v2.management.graphrbac.ApplicationUpdateParameters;
import com.microsoft.azure.v2.management.graphrbac.CertificateCredential;
import com.microsoft.azure.v2.management.graphrbac.PasswordCredential;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation for ServicePrincipal and its parent interfaces.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Graph.RBAC.Fluent")
class ActiveDirectoryApplicationImpl
        extends CreatableUpdatableImpl<ActiveDirectoryApplication, ApplicationInner, ActiveDirectoryApplicationImpl>
        implements
            ActiveDirectoryApplication,
            ActiveDirectoryApplication.Definition,
            ActiveDirectoryApplication.Update,
            HasCredential<ActiveDirectoryApplicationImpl> {
    private GraphRbacManager manager;
    private ApplicationCreateParameters createParameters;
    private ApplicationUpdateParameters updateParameters;
    private Map<String, PasswordCredential> cachedPasswordCredentials;
    private Map<String, CertificateCredential> cachedCertificateCredentials;

    ActiveDirectoryApplicationImpl(ApplicationInner innerObject, GraphRbacManager manager) {
        super(innerObject.displayName(), innerObject);
        this.manager = manager;
        this.createParameters = new ApplicationCreateParameters().withDisplayName(innerObject.displayName());
        this.updateParameters = new ApplicationUpdateParameters().withDisplayName(innerObject.displayName());
    }

    @Override
    public boolean isInCreateMode() {
        return id() == null;
    }

    @Override
    public Observable<ActiveDirectoryApplication> createResourceAsync() {
        if (createParameters.identifierUris() == null) {
            createParameters.withIdentifierUris(new ArrayList<String>());
            createParameters.identifierUris().add(createParameters.homepage());
        }
        return manager.inner().applications().createAsync(createParameters)
                .map(innerToFluentMap(this))
                .concatMap(app -> refreshCredentialsAsync())
                .toObservable();
    }

    @Override
    public Observable<ActiveDirectoryApplication> updateResourceAsync() {
        return manager.inner().applications().patchAsync(id(), updateParameters)
                .andThen(refreshAsync()).toObservable();
    }

    Maybe<ActiveDirectoryApplication> refreshCredentialsAsync() {
        final Single<ActiveDirectoryApplication> keyCredentials = manager.inner().applications().listKeyCredentialsAsync(id())
                .flattenAsObservable(keyCredentialInners -> keyCredentialInners.items())
                .map(inner -> (CertificateCredential) new CertificateCredentialImpl<ActiveDirectoryApplication>(inner))
                .toMap(certificateCredential -> certificateCredential.name())
                .map(stringCertificateCredentialMap -> {
                    ActiveDirectoryApplicationImpl.this.cachedCertificateCredentials = stringCertificateCredentialMap;
                    return ActiveDirectoryApplicationImpl.this;
                });

        final Single<ActiveDirectoryApplication> passwordCredentials = manager.inner().applications().listPasswordCredentialsAsync(id())
                .flattenAsObservable(passwordCredentialInners -> passwordCredentialInners.items())
                .map((io.reactivex.functions.Function<PasswordCredentialInner, PasswordCredential>) passwordCredentialInner -> new PasswordCredentialImpl<ActiveDirectoryApplication>(passwordCredentialInner))
                .toMap(passwordCredential -> passwordCredential.name())
                .map(stringPasswordCredentialMap -> {
                    ActiveDirectoryApplicationImpl.this.cachedPasswordCredentials = stringPasswordCredentialMap;
                    return ActiveDirectoryApplicationImpl.this;
                });

        return keyCredentials.mergeWith(passwordCredentials).lastElement();
    }

    @Override
    public Maybe<ActiveDirectoryApplication> refreshAsync() {
        return getInnerAsync()
                .map(innerToFluentMap(this))
                .concatMap(app -> refreshCredentialsAsync());
    }

    @Override
    public String id() {
        return inner().objectId();
    }

    @Override
    public String applicationId() {
        return inner().appId();
    }

    @Override
    public List<String> applicationPermissions() {
        if (inner().appPermissions() == null) {
            return null;
        }
        return Collections.unmodifiableList(inner().appPermissions());
    }

    @Override
    public boolean availableToOtherTenants() {
        return inner().availableToOtherTenants();
    }

    @Override
    public Set<String> identifierUris() {
        if (inner().identifierUris() == null) {
            return null;
        }
        HashSet<String> identifierUrisSet = new HashSet<>(inner().identifierUris());
        return Collections.unmodifiableSet(identifierUrisSet);
    }

    @Override
    public Set<String> replyUrls() {
        if (inner().replyUrls() == null) {
            return null;
        }
        HashSet<String> replyUrlsSet = new HashSet<>(inner().replyUrls());
        return Collections.unmodifiableSet(replyUrlsSet);
    }

    @Override
    public URL signOnUrl() {
        try {
            return new URL(inner().homepage());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    @Override
    public Map<String, PasswordCredential> passwordCredentials() {
        if (cachedPasswordCredentials == null) {
            return null;
        }
        return Collections.unmodifiableMap(cachedPasswordCredentials);
    }

    @Override
    public Map<String, CertificateCredential> certificateCredentials() {
        if (cachedCertificateCredentials == null) {
            return null;
        }
        return Collections.unmodifiableMap(cachedCertificateCredentials);
    }

    @Override
    protected Maybe<ApplicationInner> getInnerAsync() {
        return manager.inner().applications().getAsync(id());
    }

    @Override
    public ActiveDirectoryApplicationImpl withSignOnUrl(String signOnUrl) {
        if (isInCreateMode()) {
            createParameters.withHomepage(signOnUrl);
        } else {
            updateParameters.withHomepage(signOnUrl);
        }
        return withReplyUrl(signOnUrl);
    }

    @Override
    public ActiveDirectoryApplicationImpl withReplyUrl(String replyUrl) {
        if (isInCreateMode()) {
            if (createParameters.replyUrls() == null) {
                createParameters.withReplyUrls(new ArrayList<String>());
            }
            createParameters.replyUrls().add(replyUrl);
        } else {
            if (updateParameters.replyUrls() == null) {
                updateParameters.withReplyUrls(new ArrayList<>(replyUrls()));
            }
            updateParameters.replyUrls().add(replyUrl);
        }
        return this;
    }

    @Override
    public ActiveDirectoryApplicationImpl withoutReplyUrl(String replyUrl) {
        if (updateParameters.replyUrls() != null) {
            updateParameters.replyUrls().remove(replyUrl);
        }
        return this;
    }

    @Override
    public ActiveDirectoryApplicationImpl withIdentifierUrl(String identifierUrl) {
        if (isInCreateMode()) {
            if (createParameters.identifierUris() == null) {
                createParameters.withIdentifierUris(new ArrayList<String>());
            }
            createParameters.identifierUris().add(identifierUrl);
        } else {
            if (updateParameters.identifierUris() == null) {
                updateParameters.withIdentifierUris(new ArrayList<>(identifierUris()));
            }
            updateParameters.identifierUris().add(identifierUrl);
        }
        return this;
    }

    @Override
    public Update withoutIdentifierUrl(String identifierUrl) {
        if (updateParameters.identifierUris() != null) {
            updateParameters.identifierUris().remove(identifierUrl);
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CertificateCredentialImpl defineCertificateCredential(String name) {
        return new CertificateCredentialImpl<>(name, this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PasswordCredentialImpl definePasswordCredential(String name) {
        return new PasswordCredentialImpl<>(name, this);
    }

    @Override
    public ActiveDirectoryApplicationImpl withoutCredential(final String name) {
        if (cachedPasswordCredentials.containsKey(name)) {
            cachedPasswordCredentials.remove(name);
            //
            List<PasswordCredentialInner> innerPasswords = new ArrayList<>();
            for (PasswordCredential pwd : cachedPasswordCredentials.values()) {
                innerPasswords.add(pwd.inner());
            }
            //
            updateParameters.withPasswordCredentials(innerPasswords);
        } else if (cachedCertificateCredentials.containsKey(name)) {
            cachedCertificateCredentials.remove(name);
            //
            //
            List<KeyCredentialInner> innerCerts = new ArrayList<>();
            for (CertificateCredential cert : cachedCertificateCredentials.values()) {
                innerCerts.add(cert.inner());
            }
            //
            //
            updateParameters.withKeyCredentials(innerCerts);
        }
        return this;
    }

    @Override
    public ActiveDirectoryApplicationImpl withCertificateCredential(CertificateCredentialImpl<?> credential) {
        if (isInCreateMode()) {
            if (createParameters.keyCredentials() == null) {
                createParameters.withKeyCredentials(new ArrayList<KeyCredentialInner>());
            }
            createParameters.keyCredentials().add(credential.inner());
        } else {
            if (updateParameters.keyCredentials() == null) {
                updateParameters.withKeyCredentials(new ArrayList<KeyCredentialInner>());
            }
            updateParameters.keyCredentials().add(credential.inner());
        }
        return this;
    }

    @Override
    public ActiveDirectoryApplicationImpl withPasswordCredential(PasswordCredentialImpl<?> credential) {
        if (isInCreateMode()) {
            if (createParameters.passwordCredentials() == null) {
                createParameters.withPasswordCredentials(new ArrayList<PasswordCredentialInner>());
            }
            createParameters.passwordCredentials().add(credential.inner());
        } else {
            if (updateParameters.passwordCredentials() == null) {
                updateParameters.withPasswordCredentials(cachedPasswordCredentials.values().stream().map(pc -> pc.inner()).collect(Collectors.toList()));
            }
            updateParameters.passwordCredentials().add(credential.inner());
        }
        return this;
    }

    @Override
    public ActiveDirectoryApplicationImpl withAvailableToOtherTenants(boolean availableToOtherTenants) {
        if (isInCreateMode()) {
            createParameters.withAvailableToOtherTenants(availableToOtherTenants);
        } else {
            updateParameters.withAvailableToOtherTenants(availableToOtherTenants);
        }
        return this;
    }

    @Override
    public GraphRbacManager manager() {
        return manager;
    }
}
