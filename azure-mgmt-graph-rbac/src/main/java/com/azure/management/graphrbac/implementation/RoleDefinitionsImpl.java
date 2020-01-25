/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.graphrbac.implementation;

import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedIterable;
import com.azure.management.graphrbac.RoleDefinition;
import com.azure.management.graphrbac.RoleDefinitions;
import com.azure.management.graphrbac.models.RoleDefinitionInner;
import com.azure.management.graphrbac.models.RoleDefinitionsInner;
import com.azure.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import com.azure.management.resources.fluentcore.model.HasInner;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * The implementation of RoleDefinitions and its parent interfaces.
 */
class RoleDefinitionsImpl
        extends ReadableWrappersImpl<
                RoleDefinition,
                RoleDefinitionImpl,
                RoleDefinitionInner>
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
        return (RoleDefinitionImpl) getByIdAsync(objectId).block();
    }

    @Override
    public Mono<RoleDefinition> getByIdAsync(String id) {
        return inner().getByIdAsync(id).map(roleDefinitionInner -> {
            if (roleDefinitionInner == null) {
                return null;
            } else {
                return new RoleDefinitionImpl(roleDefinitionInner, manager());
            }
        });
    }

    @Override
    public RoleDefinitionImpl getByScope(String scope,  String name) {
        return (RoleDefinitionImpl) getByScopeAsync(scope, name).block();
    }

    @Override
    public Mono<RoleDefinition> getByScopeAsync(String scope,  String name) {
        return inner().getAsync(scope, name)
                .map(roleDefinitionInner -> {
                    if (roleDefinitionInner == null) {
                        return null;
                    }
                    return new RoleDefinitionImpl(roleDefinitionInner, manager());
                });
    }

    @Override
    public RoleDefinitionImpl getByScopeAndRoleName(String scope,  String roleName) {
        return (RoleDefinitionImpl) getByScopeAndRoleNameAsync(scope, roleName).block();
    }

    @Override
    public PagedFlux<RoleDefinition> listByScopeAsync(String scope) {
        return inner().listAsync(scope, null).mapPage(roleDefinitionInner -> {
            if (roleDefinitionInner == null) {
                return null;
            }
            return new RoleDefinitionImpl(roleDefinitionInner, manager());
        });
    }

    @Override
    public PagedIterable<RoleDefinition> listByScope(String scope) {
        return wrapList(inner().list(scope, null));
    }

    @Override
    public Mono<RoleDefinition> getByScopeAndRoleNameAsync(String scope,  String roleName) {
        return inner().listAsync(scope, String.format("roleName eq '%s'", roleName))
                .map((Function<RoleDefinitionInner, RoleDefinition>) roleDefinitionInner -> {
                    if (roleDefinitionInner == null) {
                        return null;
                    }
                    return new RoleDefinitionImpl(roleDefinitionInner, manager());
                }).last();
    }

    @Override
    public GraphRbacManager manager() {
        return this.manager;
    }

    @Override
    public RoleDefinitionsInner inner() {
        return manager().roleInner().roleDefinitions();
    }
}
