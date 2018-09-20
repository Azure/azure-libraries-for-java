/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.graphrbac.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.graphrbac.ActiveDirectoryUser;
import com.microsoft.azure.v2.management.graphrbac.PasswordProfile;
import com.microsoft.azure.v2.management.graphrbac.SignInName;
import com.microsoft.azure.v2.management.graphrbac.UserCreateParameters;
import com.microsoft.azure.v2.management.graphrbac.UserUpdateParameters;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.CountryIsoCode;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import io.reactivex.Maybe;
import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation for User and its parent interfaces.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Graph.RBAC.Fluent")
class ActiveDirectoryUserImpl
        extends CreatableUpdatableImpl<ActiveDirectoryUser, UserInner, ActiveDirectoryUserImpl>
        implements
        ActiveDirectoryUser,
        ActiveDirectoryUser.Definition,
        ActiveDirectoryUser.Update {

    private final GraphRbacManager manager;
    private UserCreateParameters createParameters;
    private UserUpdateParameters updateParameters;
    private String emailAlias;

    ActiveDirectoryUserImpl(UserInner innerObject, GraphRbacManager manager) {
        super(innerObject.displayName(), innerObject);
        this.manager = manager;
        this.createParameters = new UserCreateParameters().withDisplayName(name()).withAccountEnabled(true);
        this.updateParameters = new UserUpdateParameters().withDisplayName(name());
    }

    @Override
    public String id() {
        return inner().objectId();
    }

    @Override
    public String userPrincipalName() {
        return inner().userPrincipalName();
    }

    @Override
    public String name() {
        return inner().displayName();
    }

    @Override
    public List<String> signInNames() {
       List<SignInName> signInNames = inner().signInNames();
       List<String> names = new ArrayList<>();
       for (SignInName signInName : signInNames) {
            names.add(signInName.value());
       }
       return Collections.unmodifiableList(names);
    }

    @Override
    public String mail() {
        return inner().mail();
    }

    @Override
    public String mailNickname() {
        return inner().mailNickname();
    }

    @Override
    public CountryIsoCode usageLocation() {
        return CountryIsoCode.fromString(inner().usageLocation());
    }

    @Override
    public GraphRbacManager manager() {
        return manager;
    }

    @Override
    public ActiveDirectoryUserImpl withUserPrincipalName(String userPrincipalName) {
        createParameters.withUserPrincipalName(userPrincipalName);
        if (isInCreateMode() || updateParameters.mailNickname() != null) {
            withMailNickname(userPrincipalName.replaceAll("@.+$", ""));
        }
        return this;
    }

    @Override
    public ActiveDirectoryUserImpl withEmailAlias(String emailAlias) {
        this.emailAlias = emailAlias;
        return this;
    }

    @Override
    public ActiveDirectoryUserImpl withPassword(String password) {
        createParameters.withPasswordProfile(new PasswordProfile().withPassword(password));
        return this;
    }

    @Override
    protected Maybe<UserInner> getInnerAsync() {
        return manager.inner().users().getAsync(this.id());
    }

    @Override
    public boolean isInCreateMode() {
        return id() == null;
    }

    @Override
    public Observable<ActiveDirectoryUser> createResourceAsync() {
        Observable<ActiveDirectoryUserImpl> domain;
        if (emailAlias != null) {
            domain = manager().inner().domains().listAsync()
                .map(domainInners -> {
                    for (DomainInner inner : domainInners.items()) {
                        if (inner.isVerified() && inner.isDefault()) {
                            if (emailAlias != null) {
                                withUserPrincipalName(emailAlias + "@" + inner.name());
                                break;
                            }
                        }
                    }
                    return ActiveDirectoryUserImpl.this;
                });
        } else {
            domain = Observable.just(this);
        }
        //
        return domain.concatMap(adUser -> manager().inner().users().createAsync(createParameters).toObservable())
        .map(innerToFluentMap(this));
    }

    public Observable<ActiveDirectoryUser> updateResourceAsync() {
        return manager().inner().users().updateAsync(id(), updateParameters)
                .andThen(refreshAsync().toObservable());
    }

    private void withMailNickname(String mailNickname) {
        createParameters.withMailNickname(mailNickname);
        updateParameters.withMailNickname(mailNickname);
    }

    @Override
    public ActiveDirectoryUserImpl withPromptToChangePasswordOnLogin(boolean promptToChangePasswordOnLogin) {
        createParameters.passwordProfile().withForceChangePasswordNextLogin(promptToChangePasswordOnLogin);
        return this;
    }

    @Override
    public String toString() {
        return name() + " - " + userPrincipalName();
    }

    @Override
    public ActiveDirectoryUserImpl withAccountEnabled(boolean accountEnabled) {
        createParameters.withAccountEnabled(accountEnabled);
        updateParameters.withAccountEnabled(accountEnabled);
        return this;
    }

    @Override
    public ActiveDirectoryUserImpl withUsageLocation(CountryIsoCode usageLocation) {
        createParameters.withUsageLocation(usageLocation.toString());
        updateParameters.withUsageLocation(usageLocation.toString());
        return this;
    }
}
