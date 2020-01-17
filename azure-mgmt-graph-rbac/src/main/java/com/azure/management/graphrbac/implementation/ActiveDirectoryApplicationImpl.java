/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.graphrbac.implementation;

import com.azure.management.graphrbac.ActiveDirectoryApplication;
import com.azure.management.graphrbac.ApplicationCreateParameters;
import com.azure.management.graphrbac.ApplicationUpdateParameters;
import com.azure.management.graphrbac.CertificateCredential;
import com.azure.management.graphrbac.PasswordCredential;
import com.azure.management.graphrbac.models.ApplicationInner;
import com.azure.management.graphrbac.models.KeyCredentialInner;
import com.azure.management.graphrbac.models.PasswordCredentialInner;
import com.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Implementation for ServicePrincipal and its parent interfaces.
 */
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
        super(innerObject.getDisplayName(), innerObject);
        this.manager = manager;
        this.createParameters = new ApplicationCreateParameters().setDisplayName(innerObject.getDisplayName());
        this.updateParameters = new ApplicationUpdateParameters().setDisplayName(innerObject.getDisplayName());
    }

    @Override
    public boolean isInCreateMode() {
        return this.getId() == null;
    }

    @Override
    public Mono<ActiveDirectoryApplication> createResourceAsync() {
        if (createParameters.getIdentifierUris() == null) {
            createParameters.setIdentifierUris(new ArrayList<String>());
            createParameters.getIdentifierUris().add(createParameters.getHomepage());
        }
        return manager.inner().applications().createAsync(createParameters)
                .map(innerToFluentMap(this))
                .flatMap((Function<ActiveDirectoryApplication, Mono<ActiveDirectoryApplication>>) application -> refreshCredentialsAsync());
    }

    @Override
    public Mono<ActiveDirectoryApplication> updateResourceAsync() {
        return manager.inner().applications().patchAsync(getId(), updateParameters)
                .flatMap((Function<Void, Mono<ActiveDirectoryApplication>>) aVoid -> refreshAsync());
    }

    Mono<ActiveDirectoryApplication> refreshCredentialsAsync() {
        final Mono<ActiveDirectoryApplication> keyCredentials = manager.inner().applications().listKeyCredentialsAsync(getId())
                .map((Function<KeyCredentialInner, CertificateCredential>) keyCredentialInner -> new CertificateCredentialImpl<ActiveDirectoryApplication>(keyCredentialInner))
                .collectMap(certificateCredential -> certificateCredential.getName())
                .map(stringCertificateCredentialMap -> {
                    ActiveDirectoryApplicationImpl.this.cachedCertificateCredentials = stringCertificateCredentialMap;
                    return ActiveDirectoryApplicationImpl.this;
                });

        final Mono<ActiveDirectoryApplication> passwordCredentials = manager.inner().applications().listPasswordCredentialsAsync(getId())
                .map((Function<PasswordCredentialInner, PasswordCredential>) passwordCredentialInner -> new PasswordCredentialImpl<ActiveDirectoryApplication>(passwordCredentialInner))
                .collectMap(passwordCredential -> passwordCredential.getName())
                .map(stringPasswordCredentialMap -> {
                    ActiveDirectoryApplicationImpl.this.cachedPasswordCredentials = stringPasswordCredentialMap;
                    return ActiveDirectoryApplicationImpl.this;
                });

        return keyCredentials.mergeWith(passwordCredentials).last();
    }

    @Override
    public Mono<ActiveDirectoryApplication> refreshAsync() {
        return getInnerAsync()
                .map(innerToFluentMap(this))
                .flatMap(new Function<ActiveDirectoryApplication, Mono<ActiveDirectoryApplication>>() {
                    @Override
                    public Mono<ActiveDirectoryApplication> apply(ActiveDirectoryApplication application) {
                        return refreshCredentialsAsync();
                    }
                });
    }

    @Override
    public String applicationId() {
        return getInner().getAppId();
    }

    @Override
    public List<String> applicationPermissions() {
        if (getInner().getAppPermissions() == null) {
            return null;
        }
        return Collections.unmodifiableList(getInner().getAppPermissions());
    }

    @Override
    public boolean availableToOtherTenants() {
        return getInner().isAvailableToOtherTenants();
    }

    @Override
    public Set<String> identifierUris() {
        if (getInner().getIdentifierUris() == null) {
            return null;
        }
        return Collections.unmodifiableSet(Sets.newHashSet(getInner().getIdentifierUris()));
    }

    @Override
    public Set<String> replyUrls() {
        if (getInner().getReplyUrls() == null) {
            return null;
        }
        return Collections.unmodifiableSet(Sets.newHashSet(getInner().getReplyUrls()));
    }

    @Override
    public URL signOnUrl() {
        try {
            return new URL(getInner().getHomepage());
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
    protected Mono<ApplicationInner> getInnerAsync() {
        return manager.inner().applications().getAsync(getId());
    }

    @Override
    public ActiveDirectoryApplicationImpl withSignOnUrl(String signOnUrl) {
        if (isInCreateMode()) {
            createParameters.setHomepage(signOnUrl);
        } else {
            updateParameters.setHomepage(signOnUrl);
        }
        return withReplyUrl(signOnUrl);
    }

    @Override
    public ActiveDirectoryApplicationImpl withReplyUrl(String replyUrl) {
        if (isInCreateMode()) {
            if (createParameters.getReplyUrls() == null) {
                createParameters.setReplyUrls(new ArrayList<String>());
            }
            createParameters.getReplyUrls().add(replyUrl);
        } else {
            if (updateParameters.getReplyUrls() == null) {
                updateParameters.setReplyUrls(new ArrayList<>(replyUrls()));
            }
            updateParameters.getReplyUrls().add(replyUrl);
        }
        return this;
    }

    @Override
    public ActiveDirectoryApplicationImpl withoutReplyUrl(String replyUrl) {
        if (updateParameters.getReplyUrls() != null) {
            updateParameters.getReplyUrls().remove(replyUrl);
        }
        return this;
    }

    @Override
    public ActiveDirectoryApplicationImpl withIdentifierUrl(String identifierUrl) {
        if (isInCreateMode()) {
            if (createParameters.getIdentifierUris() == null) {
                createParameters.setIdentifierUris(new ArrayList<>());
            }
            createParameters.getIdentifierUris().add(identifierUrl);
        } else {
            if (updateParameters.getIdentifierUris() == null) {
                updateParameters.setIdentifierUris(new ArrayList<>(identifierUris()));
            }
            updateParameters.getIdentifierUris().add(identifierUrl);
        }
        return this;
    }

    @Override
    public Update withoutIdentifierUrl(String identifierUrl) {
        if (updateParameters.getIdentifierUris() != null) {
            updateParameters.getIdentifierUris().remove(identifierUrl);
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
            updateParameters.setPasswordCredentials(Lists.transform(
                    new ArrayList<>(cachedPasswordCredentials.values()),
                    passwordCredential -> passwordCredential.getInner()));
        } else if (cachedCertificateCredentials.containsKey(name)) {
            cachedCertificateCredentials.remove(name);
            updateParameters.setKeyCredentials(Lists.transform(
                    new ArrayList<>(cachedCertificateCredentials.values()),
                    certificateCredential -> certificateCredential.getInner()));
        }
        return this;
    }

    @Override
    public ActiveDirectoryApplicationImpl withCertificateCredential(CertificateCredentialImpl<?> credential) {
        if (isInCreateMode()) {
            if (createParameters.getKeyCredentials() == null) {
                createParameters.setKeyCredentials(new ArrayList<>());
            }
            createParameters.getKeyCredentials().add(credential.getInner());
        } else {
            if (updateParameters.getKeyCredentials() == null) {
                updateParameters.setKeyCredentials(new ArrayList<>());
            }
            updateParameters.getKeyCredentials().add(credential.getInner());
        }
        return this;
    }

    @Override
    public ActiveDirectoryApplicationImpl withPasswordCredential(PasswordCredentialImpl<?> credential) {
        if (isInCreateMode()) {
            if (createParameters.getPasswordCredentials() == null) {
                createParameters.setPasswordCredentials(new ArrayList<>());
            }
            createParameters.getPasswordCredentials().add(credential.getInner());
        } else {
            if (updateParameters.getPasswordCredentials() == null) {
                updateParameters.setPasswordCredentials(new ArrayList<>());
            }
            updateParameters.getPasswordCredentials().add(credential.getInner());
        }
        return this;
    }

    @Override
    public ActiveDirectoryApplicationImpl withAvailableToOtherTenants(boolean availableToOtherTenants) {
        if (isInCreateMode()) {
            createParameters.setAvailableToOtherTenants(availableToOtherTenants);
        } else {
            updateParameters.setAvailableToOtherTenants(availableToOtherTenants);
        }
        return this;
    }

    @Override
    public String getId() {
        return getInner().getObjectId();
    }

    @Override
    public GraphRbacManager getManager() {
        return this.manager;
    }
}
