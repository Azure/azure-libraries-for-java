/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.graphrbac.implementation;

import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedIterable;
import com.azure.management.graphrbac.RoleAssignment;
import com.azure.management.graphrbac.RoleAssignments;
import com.azure.management.graphrbac.models.RoleAssignmentInner;
import com.azure.management.graphrbac.models.RoleAssignmentsInner;
import com.azure.management.resources.fluentcore.arm.collection.implementation.CreatableResourcesImpl;
import com.azure.management.resources.fluentcore.model.HasInner;
import reactor.core.publisher.Mono;

/**
 * The implementation of RoleAssignments and its parent interfaces.
 */
class RoleAssignmentsImpl
        extends CreatableResourcesImpl<
                RoleAssignment,
                RoleAssignmentImpl,
                RoleAssignmentInner>
        implements
        RoleAssignments,
        HasInner<RoleAssignmentsInner> {
    private final GraphRbacManager manager;

    RoleAssignmentsImpl(
            final GraphRbacManager manager) {
        this.manager = manager;
    }

    @Override
    protected RoleAssignmentImpl wrapModel(RoleAssignmentInner roleAssignmentInner) {
        if (roleAssignmentInner == null) {
            return null;
        }
        return new RoleAssignmentImpl(roleAssignmentInner.getName(), roleAssignmentInner, getManager());
    }

    @Override
    public RoleAssignmentImpl getById(String objectId) {
        return (RoleAssignmentImpl) getByIdAsync(objectId).block();
    }

    @Override
    public Mono<RoleAssignment> getByIdAsync(String id) {
        return getInner().getByIdAsync(id).map(roleAssignmentInner -> {
            if (roleAssignmentInner == null) {
                return null;
            } else {
                return new RoleAssignmentImpl(roleAssignmentInner.getName(), roleAssignmentInner, getManager());
            }
        });
    }

    @Override
    public RoleAssignmentImpl getByScope(String scope,  String name) {
        return (RoleAssignmentImpl) getByScopeAsync(scope, name).block();
    }

    @Override
    public PagedFlux<RoleAssignment> listByScopeAsync(String scope) {
        return getInner().listForScopeAsync(scope, null).mapPage(roleAssignmentInner -> {
            if (roleAssignmentInner == null) {
                return null;
            }
            return new RoleAssignmentImpl(roleAssignmentInner.getName(), roleAssignmentInner, getManager());
        });
    }

    @Override
    public PagedIterable<RoleAssignment> listByScope(String scope) {
        return wrapList(getInner().listForScope(scope, null));
    }

    @Override
    public Mono<RoleAssignment> getByScopeAsync(String scope,  String name) {
        return getInner().getAsync(scope, name)
                .map(roleAssignmentInner -> {
                    if (roleAssignmentInner == null) {
                        return null;
                    }
                    return new RoleAssignmentImpl(roleAssignmentInner.getName(), roleAssignmentInner, getManager());
                });
    }

    @Override
    protected RoleAssignmentImpl wrapModel(String name) {
        return new RoleAssignmentImpl(name, new RoleAssignmentInner(), getManager());
    }

    @Override
    public Mono<RoleAssignment> deleteByIdAsync(String id) {
        return getInner().deleteByIdAsync(id).map(roleAssignmentInner -> {
            if (roleAssignmentInner == null) {
                return null;
            }
            return new RoleAssignmentImpl(roleAssignmentInner.getName(), roleAssignmentInner, getManager());
        });
    }

    @Override
    public RoleAssignmentImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    public GraphRbacManager getManager() {
        return this.manager;
    }

    @Override
    public RoleAssignmentsInner getInner() {
        return getManager().roleInner().roleAssignments();
    }
}
