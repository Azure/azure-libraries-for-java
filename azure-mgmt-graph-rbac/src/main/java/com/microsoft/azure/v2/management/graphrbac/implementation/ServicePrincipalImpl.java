/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.graphrbac.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.CloudException;
import com.microsoft.azure.v2.Page;
import com.microsoft.azure.v2.management.graphrbac.ActiveDirectoryApplication;
import com.microsoft.azure.v2.management.graphrbac.BuiltInRole;
import com.microsoft.azure.v2.management.graphrbac.CertificateCredential;
import com.microsoft.azure.v2.management.graphrbac.PasswordCredential;
import com.microsoft.azure.v2.management.graphrbac.RoleAssignment;
import com.microsoft.azure.v2.management.graphrbac.ServicePrincipal;
import com.microsoft.azure.v2.management.graphrbac.ServicePrincipalCreateParameters;
import com.microsoft.azure.v2.management.resources.ResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.SdkContext;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import rx.exceptions.Exceptions;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Implementation for ServicePrincipal and its parent interfaces.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Graph.RBAC.Fluent")
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
        super(innerObject.displayName(), innerObject);
        this.manager = manager;
        this.createParameters = new ServicePrincipalCreateParameters().withAccountEnabled(true);
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
    public String id() {
        return inner().objectId();
    }

    @Override
    public String applicationId() {
        return inner().appId();
    }

    @Override
    public List<String> servicePrincipalNames() {
        return inner().servicePrincipalNames();
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
    protected Maybe<ServicePrincipalInner> getInnerAsync() {
        return manager.inner().servicePrincipals().getAsync(id());
    }

    @Override
    public Observable<ServicePrincipal> createResourceAsync() {
        Observable<ServicePrincipal> sp = Observable.just((ServicePrincipal) this);
        if (isInCreateMode()) {
            if (applicationCreatable != null) {
                ActiveDirectoryApplication application = this.<ActiveDirectoryApplication>taskResult(applicationCreatable.key());
                createParameters.withAppId(application.applicationId());
            }
            sp = manager.inner().servicePrincipals().createAsync(createParameters)
                    .map(innerToFluentMap(this)).toObservable();
        }
        //
        return sp.flatMap(servicePrincipal -> submitCredentialsAsync(servicePrincipal).mergeWith(submitRolesAsync(servicePrincipal)).toObservable())
            .map(servicePrincipal -> {
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

    private Single<ServicePrincipal> submitCredentialsAsync(final ServicePrincipal sp) {
        Completable completable = Completable.complete();
        //
        if (!certificateCredentialsToCreate.isEmpty() || !certificateCredentialsToDelete.isEmpty()) {
            Map<String, CertificateCredential> newCerts = new HashMap<>(cachedCertificateCredentials);
            for (String delete : certificateCredentialsToDelete) {
                newCerts.remove(delete);
            }
            for (CertificateCredential create : certificateCredentialsToCreate) {
                newCerts.put(create.name(), create);
            }
            //
            List<KeyCredentialInner> innerCerts = new ArrayList<>();
            for (CertificateCredential cert : newCerts.values()) {
                innerCerts.add(cert.inner());
            }
            //
            Completable updateKeyCredentialsCompletable = manager().inner().servicePrincipals().updateKeyCredentialsAsync(sp.id(),
                    innerCerts);
            completable = completable.mergeWith(updateKeyCredentialsCompletable);
        }
        //
        if (!passwordCredentialsToCreate.isEmpty() || !passwordCredentialsToDelete.isEmpty()) {
            Map<String, PasswordCredential> newPasses = new HashMap<>(cachedPasswordCredentials);
            for (String delete : passwordCredentialsToDelete) {
                newPasses.remove(delete);
            }
            for (PasswordCredential create : passwordCredentialsToCreate) {
                newPasses.put(create.name(), create);
            }
            List<PasswordCredentialInner> innerPasswords = new ArrayList<>();
            for (PasswordCredential pwd : newPasses.values()) {
                innerPasswords.add(pwd.inner());
            }
            Completable updatePasswordCredentialsCompletable = manager().inner().servicePrincipals().updatePasswordCredentialsAsync(sp.id(),
                    innerPasswords);
            completable = completable.mergeWith(updatePasswordCredentialsCompletable);
        }
        //
        return completable.doOnComplete(() -> {
            passwordCredentialsToDelete.clear();
            certificateCredentialsToDelete.clear();
        })
        .andThen(refreshCredentialsAsync());
    }

    private Single<ServicePrincipal> submitRolesAsync(final ServicePrincipal servicePrincipal) {
        Observable<ServicePrincipal> create;
        if (rolesToCreate.isEmpty()) {
            create = Observable.just(servicePrincipal);
        } else {
            create = Observable.fromIterable(rolesToCreate.entrySet())
                    .flatMap(role -> {
                        return manager().roleAssignments().define(SdkContext.randomUuid())
                                .forServicePrincipal(servicePrincipal)
                                .withBuiltInRole(role.getValue())
                                .withScope(role.getKey())
                                .createAsync()
                                .retryWhen(throwableObservable -> throwableObservable.zipWith(Observable.range(1, 30), (throwable, integer) -> {
                                    if (throwable instanceof CloudException
                                            && ((CloudException) throwable).body().code().equalsIgnoreCase("PrincipalNotFound")) {
                                        return integer;
                                    } else {
                                        throw Exceptions.propagate(throwable);
                                    }
                                }).flatMap(i -> Observable.timer(i, TimeUnit.SECONDS)))
                                .doOnNext(indexable -> cachedRoleAssignments.put(((RoleAssignment) indexable).id(), (RoleAssignment) indexable))
                                .lastElement()
                                .map(indexable -> {
                                    rolesToCreate.clear();
                                    return servicePrincipal;
                                }).toObservable();
                    });
        }

        Observable<ServicePrincipal> delete;
        if (rolesToDelete.isEmpty()) {
            delete =  Observable.just(servicePrincipal);
        } else {
            delete = Observable.fromIterable(rolesToDelete)
                    .flatMap(ra -> manager().roleAssignments().deleteByIdAsync(cachedRoleAssignments.get(ra).id()).andThen(Observable.just(ra)))
                    .doOnNext(ra -> cachedRoleAssignments.remove(ra))
                    .lastElement()
                    .map(s -> {
                        rolesToDelete.clear();
                        return servicePrincipal;
                    })
                    .toObservable();
        }
        return create.mergeWith(delete).last(servicePrincipal);
    }

    @Override
    public boolean isInCreateMode() {
        return id() == null;
    }

    Single<ServicePrincipal> refreshCredentialsAsync() {
        final Maybe<ServicePrincipal> keyCredentials = manager.inner().servicePrincipals().listKeyCredentialsAsync(id())
                .map((io.reactivex.functions.Function<Page<KeyCredentialInner>, Map<String, CertificateCredential>>) keyCredentialInners -> {
                    if (keyCredentialInners.items() == null || keyCredentialInners.items().isEmpty()) {
                        return Collections.emptyMap();
                    }
                    Map<String, CertificateCredential> certificateCredentialMap = new HashMap<String, CertificateCredential>();
                    for (KeyCredentialInner inner : keyCredentialInners.items()) {
                        CertificateCredential credential = new CertificateCredentialImpl<>(inner);
                        certificateCredentialMap.put(credential.name(), credential);
                    }
                    return certificateCredentialMap;
                }).map(stringCertificateCredentialMap -> {
                    ServicePrincipalImpl.this.cachedCertificateCredentials = stringCertificateCredentialMap;
                    return ServicePrincipalImpl.this;
                });
        //
        final Maybe<ServicePrincipal> passwordCredentials = manager.inner().servicePrincipals().listPasswordCredentialsAsync(id())
                .map((io.reactivex.functions.Function<Page<PasswordCredentialInner>, Map<String, PasswordCredential>>) passwordCredentialInners -> {
                    if (passwordCredentialInners.items() == null || passwordCredentialInners.items().isEmpty()) {
                        return Collections.emptyMap();
                    }
                    Map<String, PasswordCredential> passwordCredentialMap = new HashMap<String, PasswordCredential>();
                    for (PasswordCredentialInner inner : passwordCredentialInners.items()) {
                        PasswordCredential credential = new PasswordCredentialImpl<>(inner);
                        passwordCredentialMap.put(credential.name(), credential);
                    }
                    return passwordCredentialMap;
                }).map(stringPasswordCredentialMap -> {
                    ServicePrincipalImpl.this.cachedPasswordCredentials = stringPasswordCredentialMap;
                    return ServicePrincipalImpl.this;
                });
        //
        return keyCredentials.mergeWith(passwordCredentials).lastOrError();
    }

    @Override
    public Maybe<ServicePrincipal> refreshAsync() {
        return getInnerAsync()
                .map(innerToFluentMap(this))
                .flatMap(application -> refreshCredentialsAsync().toMaybe());

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
        createParameters.withAppId(id);
        return this;
    }

    @Override
    public ServicePrincipalImpl withExistingApplication(ActiveDirectoryApplication application) {
        createParameters.withAppId(application.applicationId());
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
    public GraphRbacManager manager() {
        return manager;
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
}
