/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.graphrbac.implementation;

import com.azure.management.graphrbac.ActiveDirectoryApplication;
import com.azure.management.graphrbac.BuiltInRole;
import com.azure.management.graphrbac.CertificateCredential;
import com.azure.management.graphrbac.KeyCredentialsUpdateParameters;
import com.azure.management.graphrbac.PasswordCredential;
import com.azure.management.graphrbac.PasswordCredentialsUpdateParameters;
import com.azure.management.graphrbac.RoleAssignment;
import com.azure.management.graphrbac.ServicePrincipal;
import com.azure.management.graphrbac.ServicePrincipalCreateParameters;
import com.azure.management.graphrbac.models.KeyCredentialInner;
import com.azure.management.graphrbac.models.PasswordCredentialInner;
import com.azure.management.graphrbac.models.ServicePrincipalInner;
import com.azure.management.resources.ResourceGroup;
import com.azure.management.resources.fluentcore.model.Creatable;
import com.azure.management.resources.fluentcore.model.Indexable;
import com.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Implementation for ServicePrincipal and its parent interfaces.
 */
class ServicePrincipalImpl
        extends CreatableUpdatableImpl<ServicePrincipal, ServicePrincipalInner, ServicePrincipalImpl>
        implements
            ServicePrincipal,
            ServicePrincipal.Definition,
            ServicePrincipal.Update,
            HasCredential<ServicePrincipalImpl> {
    private GraphRbacManager manager;

    private Map<String, PasswordCredential> cachedPasswordCredentials;
    private Map<String, CertificateCredential> cachedCertificateCredentials;
    private Map<String, RoleAssignment> cachedRoleAssignments;

    private ServicePrincipalCreateParameters createParameters;
    private Creatable<ActiveDirectoryApplication> applicationCreatable;
    private Map<String, BuiltInRole> rolesToCreate;
    private Set<String> rolesToDelete;

    String assignedSubscription;
    private List<CertificateCredentialImpl<?>> certificateCredentialsToCreate;
    private List<PasswordCredentialImpl<?>> passwordCredentialsToCreate;
    private Set<String> certificateCredentialsToDelete;
    private Set<String> passwordCredentialsToDelete;

    ServicePrincipalImpl(ServicePrincipalInner innerObject, GraphRbacManager manager) {
        super(innerObject.getDisplayName(), innerObject);
        this.manager = manager;
        this.createParameters = new ServicePrincipalCreateParameters();
        this.createParameters.setAccountEnabled(true);
        this.cachedRoleAssignments = new HashMap<>();
        this.rolesToCreate = new HashMap<>();
        this.rolesToDelete = new HashSet<>();
        this.cachedCertificateCredentials = new HashMap<>();
        this.certificateCredentialsToCreate = new ArrayList<>();
        this.certificateCredentialsToDelete = new HashSet<>();
        this.cachedPasswordCredentials = new HashMap<>();
        this.passwordCredentialsToCreate = new ArrayList<>();
        this.passwordCredentialsToDelete = new HashSet<>();
    }

    @Override
    public String applicationId() {
        return inner().getAppId();
    }

    @Override
    public List<String> servicePrincipalNames() {
        return inner().getServicePrincipalNames();
    }

    @Override
    public Map<String, PasswordCredential> passwordCredentials() {
        return Collections.unmodifiableMap(cachedPasswordCredentials);
    }

    @Override
    public Map<String, CertificateCredential> certificateCredentials() {
        return Collections.unmodifiableMap(cachedCertificateCredentials);
    }

    @Override
    public Set<RoleAssignment> roleAssignments() {
        return Collections.unmodifiableSet(new HashSet<>(cachedRoleAssignments.values()));
    }

    @Override
    protected Mono<ServicePrincipalInner> getInnerAsync() {
        return manager.inner().servicePrincipals().getAsync(id());
    }

    @Override
    public Mono<ServicePrincipal> createResourceAsync() {
        Mono<ServicePrincipal> sp = Mono.just(this);
        if (isInCreateMode()) {
            if (applicationCreatable != null) {
                ActiveDirectoryApplication application = this.taskResult(applicationCreatable.key());
                createParameters.setAppId(application.applicationId());
            }
            sp = manager.inner().servicePrincipals().createAsync(createParameters)
                    .map(innerToFluentMap(this));
        }
        return sp.flatMap((Function<ServicePrincipal, Mono<ServicePrincipal>>) servicePrincipal -> submitCredentialsAsync(servicePrincipal).mergeWith(submitRolesAsync(servicePrincipal)).last()).map(servicePrincipal -> {
            for (PasswordCredentialImpl<?> passwordCredential : passwordCredentialsToCreate) {
                passwordCredential.exportAuthFile((ServicePrincipalImpl) servicePrincipal);
            }
            for (CertificateCredentialImpl<?> certificateCredential : certificateCredentialsToCreate) {
                certificateCredential.exportAuthFile((ServicePrincipalImpl) servicePrincipal);
            }
            passwordCredentialsToCreate.clear();
            certificateCredentialsToCreate.clear();
            return servicePrincipal;
        });
    }

    private Mono<ServicePrincipal> submitCredentialsAsync(final ServicePrincipal sp) {
        Mono<ServicePrincipal> mono = Mono.empty();
        if (!certificateCredentialsToCreate.isEmpty() || !certificateCredentialsToDelete.isEmpty()) {
            Map<String, CertificateCredential> newCerts = new HashMap<>(cachedCertificateCredentials);
            for (String delete : certificateCredentialsToDelete) {
                newCerts.remove(delete);
            }
            for (CertificateCredential create : certificateCredentialsToCreate) {
                newCerts.put(create.name(), create);
            }
            List<KeyCredentialInner> updateKeyCredentials = new ArrayList<>();
            for (CertificateCredential certificateCredential: newCerts.values()) {
                updateKeyCredentials.add(certificateCredential.inner());
            }
            mono = mono.concatWith(manager().inner().servicePrincipals().updateKeyCredentialsAsync(
                    sp.id(),
                    new KeyCredentialsUpdateParameters().setValue(updateKeyCredentials)
            ).then(Mono.just(ServicePrincipalImpl.this))).last();
        }
        if (!passwordCredentialsToCreate.isEmpty() || !passwordCredentialsToDelete.isEmpty()) {
            Map<String, PasswordCredential> newPasses = new HashMap<>(cachedPasswordCredentials);
            for (String delete : passwordCredentialsToDelete) {
                newPasses.remove(delete);
            }
            for (PasswordCredential create : passwordCredentialsToCreate) {
                newPasses.put(create.name(), create);
            }
            List<PasswordCredentialInner> updatePasswordCredentials = new ArrayList<>();
            for (PasswordCredential passwordCredential: newPasses.values()) {
                updatePasswordCredentials.add(passwordCredential.inner());
            }
            mono = mono.concatWith(manager().inner().servicePrincipals().updatePasswordCredentialsAsync(
                    sp.id(),
                    new PasswordCredentialsUpdateParameters().setValue(updatePasswordCredentials)
            ).then(Mono.just(ServicePrincipalImpl.this))).last();
        }
        return mono.flatMap((Function<ServicePrincipal, Mono<ServicePrincipal>>) servicePrincipal -> {
            passwordCredentialsToDelete.clear();
            certificateCredentialsToDelete.clear();
            return refreshCredentialsAsync();
        });
    }

    private Mono<ServicePrincipal> submitRolesAsync(final ServicePrincipal servicePrincipal) {
        Mono<ServicePrincipal> create;
        if (rolesToCreate.isEmpty()) {
            create = Mono.just(servicePrincipal);
        } else {
            create = Mono.just(rolesToCreate.entrySet().iterator())
                    .flatMap((Function<Iterator<Map.Entry<String, BuiltInRole>>, Mono<Indexable>>) entryIterator -> {
                        if (entryIterator.hasNext()) {
                            Map.Entry<String, BuiltInRole> role = entryIterator.next();
                            return manager().roleAssignments().define(SdkContext.randomUuid())
                                    .forServicePrincipal(servicePrincipal)
                                    .withBuiltInRole(role.getValue())
                                    .withScope(role.getKey())
                                    .createAsync().last();
                        }
                        return null;
                    })
                    .doOnNext(indexable -> cachedRoleAssignments.put(((RoleAssignment)indexable).id(), (RoleAssignment)indexable))
                    .map(indexable -> {
                        rolesToCreate.clear();
                        return servicePrincipal;
                    });
        }
        Mono<ServicePrincipal> delete;
        if (rolesToDelete.isEmpty()) {
            delete =  Mono.just(servicePrincipal);
        } else {
            delete = Mono.just(rolesToDelete.iterator())
                    .flatMap((Function<Iterator<String>, Mono<RoleAssignment>>) stringIterator -> {
                        if (stringIterator.hasNext()) {
                            return (Mono<RoleAssignment>)manager().roleAssignments().deleteByIdAsync(cachedRoleAssignments.get(stringIterator.next()).id());
                        }
                        return null;
                    })
                    .doOnNext(roleAssignment -> cachedRoleAssignments.remove(roleAssignment.id()))
                    .map(roleAssignment -> {
                        rolesToDelete.clear();
                        return servicePrincipal;
                    });
        }
        return create.mergeWith(delete).last();
    }

    @Override
    public boolean isInCreateMode() {
        return id() == null;
    }

    Mono<ServicePrincipal> refreshCredentialsAsync() {
        return Mono.just(ServicePrincipalImpl.this).map((Function<ServicePrincipalImpl, ServicePrincipal>) servicePrincipal -> {
            servicePrincipal.cachedCertificateCredentials.clear();
            servicePrincipal.cachedPasswordCredentials.clear();
            return servicePrincipal;
        }).concatWith(manager().inner().servicePrincipals().listKeyCredentialsAsync(id()).map(keyCredentialInner -> {
            CertificateCredential credential = new CertificateCredentialImpl<>(keyCredentialInner);
            ServicePrincipalImpl.this.cachedCertificateCredentials.put(credential.name(), credential);
            return ServicePrincipalImpl.this;
        })).concatWith(manager().inner().servicePrincipals().listPasswordCredentialsAsync(id()).map(passwordCredentialInner -> {
            PasswordCredential credential = new PasswordCredentialImpl<>(passwordCredentialInner);
            ServicePrincipalImpl.this.cachedPasswordCredentials.put(credential.name(), credential);
            return ServicePrincipalImpl.this;
        })).last();
    }

    @Override
    public Mono<ServicePrincipal> refreshAsync() {
        return getInnerAsync()
                .map(innerToFluentMap(this))
                .flatMap((Function<ServicePrincipal, Mono<ServicePrincipal>>) application -> refreshCredentialsAsync());
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
    public ServicePrincipalImpl withoutCredential(String name) {
        if (cachedPasswordCredentials.containsKey(name)) {
            passwordCredentialsToDelete.add(name);
        } else if (cachedCertificateCredentials.containsKey(name)) {
            certificateCredentialsToDelete.add(name);
        }
        return this;
    }

    @Override
    public ServicePrincipalImpl withCertificateCredential(CertificateCredentialImpl<?> credential) {
        this.certificateCredentialsToCreate.add(credential);
        return this;
    }

    @Override
    public ServicePrincipalImpl withPasswordCredential(PasswordCredentialImpl<?> credential) {

        this.passwordCredentialsToCreate.add(credential);
        return this;
    }

    @Override
    public ServicePrincipalImpl withExistingApplication(String id) {
        createParameters.setAppId(id);
        return this;
    }

    @Override
    public ServicePrincipalImpl withExistingApplication(ActiveDirectoryApplication application) {
        createParameters.setAppId(application.applicationId());
        return this;
    }

    @Override
    public ServicePrincipalImpl withNewApplication(Creatable<ActiveDirectoryApplication> applicationCreatable) {
        this.addDependency(applicationCreatable);
        this.applicationCreatable = applicationCreatable;
        return this;
    }

    @Override
    public ServicePrincipalImpl withNewApplication(String signOnUrl) {
        return withNewApplication(manager.applications().define(name())
                .withSignOnUrl(signOnUrl)
                .withIdentifierUrl(signOnUrl));
    }

    @Override
    public ServicePrincipalImpl withNewRole(BuiltInRole role, String scope) {
        this.rolesToCreate.put(scope, role);
        return this;
    }

    @Override
    public ServicePrincipalImpl withNewRoleInSubscription(BuiltInRole role, String subscriptionId) {
        this.assignedSubscription = subscriptionId;
        return withNewRole(role, "subscriptions/" + subscriptionId);
    }

    @Override
    public ServicePrincipalImpl withNewRoleInResourceGroup(BuiltInRole role, ResourceGroup resourceGroup) {
        return withNewRole(role, resourceGroup.id());
    }

    @Override
    public Update withoutRole(RoleAssignment roleAssignment) {
        this.rolesToDelete.add(roleAssignment.id());
        return this;
    }

    @Override
    public String id() {
        return inner().getObjectId();
    }

    @Override
    public GraphRbacManager manager() {
        return this.manager;
    }
}
