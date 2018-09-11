/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.graphrbac.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.v2.management.graphrbac.RoleDefinition;
import com.microsoft.azure.v2.management.graphrbac.RoleDefinitions;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * The implementation of RoleDefinitions and its parent interfaces.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Graph.RBAC.Fluent")
class RoleDefinitionsImpl
        extends ReadableWrappersImpl<RoleDefinition, RoleDefinitionImpl, RoleDefinitionInner>
        implements
            RoleDefinitions,
        HasInner<RoleDefinitionsInner> {
    private final GraphRbacManager manager;

    RoleDefinitionsImpl(
            final GraphRbacManager manager) {
        this.manager = manager;
    }
    @Override
    protected RoleDefinitionImpl wrapModel(RoleDefinitionInner roleDefinitionInner) {
        if (roleDefinitionInner == null) {
            return null;
        }
        return new RoleDefinitionImpl(roleDefinitionInner, manager());
    }

    @Override
    public RoleDefinitionImpl getById(String objectId) {
        return (RoleDefinitionImpl) getByIdAsync(objectId).blockingGet();
    }

    @Override
    public Maybe<RoleDefinition> getByIdAsync(String id) {
        return manager().roleInner().roleDefinitions().getByIdAsync(id).map(roleDefinitionInner -> {
            if (roleDefinitionInner == null) {
                return null;
            } else {
                return new RoleDefinitionImpl(roleDefinitionInner, manager());
            }
        });
    }

    @Override
    public ServiceFuture<RoleDefinition> getByIdAsync(String id, ServiceCallback<RoleDefinition> callback) {
        return ServiceFuture.fromBody(getByIdAsync(id), callback);
    }

    @Override
    public RoleDefinitionImpl getByScope(String scope,  String name) {
        return (RoleDefinitionImpl) getByScopeAsync(scope, name).lastElement().blockingGet();
    }

    @Override
    public Observable<RoleDefinition> getByScopeAsync(String scope, String name) {
        return manager().roleInner().roleDefinitions().getAsync(scope, name)
                .map((io.reactivex.functions.Function<RoleDefinitionInner, RoleDefinition>)  roleDefinitionInner -> {
                        if (roleDefinitionInner == null) {
                            return null;
                        }
                        return new RoleDefinitionImpl(roleDefinitionInner, manager());
                    }).toObservable();
    }

    @Override
    public RoleDefinitionImpl getByScopeAndRoleName(String scope,  String roleName) {
        return (RoleDefinitionImpl) getByScopeAndRoleNameAsync(scope, roleName).lastElement().blockingGet();
    }

    @Override
    public Observable<RoleDefinition> listByScopeAsync(String scope) {
        return wrapPageAsync(manager().roleInner().roleDefinitions().listAsync(scope));
    }

    @Override
    public PagedList<RoleDefinition> listByScope(String scope) {
        return wrapList(manager().roleInner().roleDefinitions().list(scope));
    }

    @Override
    public Observable<RoleDefinition> getByScopeAndRoleNameAsync(String scope,  String roleName) {
        return
        manager().roleInner().roleDefinitions().listAsync(scope, String.format("roleName eq '%s'", roleName))
                .map(roleDefinitionInnerPage -> {
                    if (roleDefinitionInnerPage == null || roleDefinitionInnerPage.items() == null || roleDefinitionInnerPage.items().isEmpty()) {
                        return null;
                    }
                    return new RoleDefinitionImpl(roleDefinitionInnerPage.items().get(0), manager());
                });
    }

    @Override
    public RoleDefinitionsInner inner() {
        return this.manager().roleInner().roleDefinitions();
    }

    @Override
    public GraphRbacManager manager() {
        return manager;
    }
}
