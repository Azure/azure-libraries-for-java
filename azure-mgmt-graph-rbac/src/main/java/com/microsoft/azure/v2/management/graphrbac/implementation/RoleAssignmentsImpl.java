/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.graphrbac.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.v2.management.graphrbac.RoleAssignment;
import com.microsoft.azure.v2.management.graphrbac.RoleAssignments;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.CreatableResourcesImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * The implementation of RoleAssignments and its parent interfaces.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Graph.RBAC.Fluent")
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
        return new RoleAssignmentImpl(roleAssignmentInner, manager());
    }

    @Override
    public RoleAssignmentImpl getById(String objectId) {
        return (RoleAssignmentImpl) getByIdAsync(objectId).blockingGet();
    }

    @Override
    public Maybe<RoleAssignment> getByIdAsync(String id) {
        return manager().roleInner().roleAssignments().getByIdAsync(id).map(roleAssignmentInner -> {
            if (roleAssignmentInner == null) {
                return null;
            } else {
                return new RoleAssignmentImpl(roleAssignmentInner, manager());
            }
        });
    }

    @Override
    public ServiceFuture<RoleAssignment> getByIdAsync(String id, ServiceCallback<RoleAssignment> callback) {
        return null;
    }

    @Override
    public RoleAssignmentImpl getByScope(String scope,  String name) {
        return (RoleAssignmentImpl) getByScopeAsync(scope, name).blockingLast();
    }

    @Override
    public Observable<RoleAssignment> listByScopeAsync(String scope) {
        return wrapPageAsync(manager().roleInner().roleAssignments().listForScopeAsync(scope));
    }

    @Override
    public PagedList<RoleAssignment> listByScope(String scope) {
        return wrapList(manager().roleInner().roleAssignments().listForScope(scope));
    }

    @Override
    public Observable<RoleAssignment> getByScopeAsync(String scope,  String name) {
        return manager().roleInner().roleAssignments().getAsync(scope, name)
                .map((io.reactivex.functions.Function<RoleAssignmentInner, RoleAssignment>) roleAssignmentInner -> {
                    if (roleAssignmentInner == null) {
                        return null;
                    }
                    return new RoleAssignmentImpl(roleAssignmentInner, manager());
                }).toObservable();
    }

    @Override
    public RoleAssignmentsInner inner() {
        return this.manager().roleInner().roleAssignments();
    }

    @Override
    public GraphRbacManager manager() {
        return manager;
    }

    @Override
    protected RoleAssignmentImpl wrapModel(String name) {
        return new RoleAssignmentImpl(new RoleAssignmentInner().withName(name), manager());
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        return manager().roleInner().roleAssignments().deleteByIdAsync(id).flatMapCompletable(a -> Completable.complete());
    }

    @Override
    public RoleAssignmentImpl define(String name) {
        return wrapModel(name);
    }
}
