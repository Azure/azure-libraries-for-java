/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.msi.implementation;

import com.microsoft.azure.CloudException;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.graphrbac.BuiltInRole;
import com.microsoft.azure.management.graphrbac.RoleAssignment;
import com.microsoft.azure.management.graphrbac.RoleDefinition;
import com.microsoft.azure.management.graphrbac.implementation.GraphRbacManager;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.dag.IndexableTaskItem;
import com.microsoft.azure.management.resources.fluentcore.dag.TaskGroup;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A utility class to operate on identity role assignments.
 */
@LangDefinition
final class RoleAssignmentHelper {
    private static final String CURRENT_RESOURCE_GROUP_SCOPE = "CURRENT_RESOURCE_GROUP";

    private final GraphRbacManager rbacManager;
    private final IdentityImpl identity;

    private List<RoleAssignmentCreator> rolesAssignmentsToCreate;
    private List<RoleAssignmentCreator> roleDefinitionsAssignmentsToCreate;
    private List<RoleAssignmentRemover> rolesAssignmentsToRemove;

    /**
     * Creates RoleAssignmentHelper.
     *
     * @param rbacManager the graph rbac manager
     * @param identity the identity for which role assignments needs to be created or removed
     */
    RoleAssignmentHelper(final GraphRbacManager rbacManager, IdentityImpl identity) {
        this.rbacManager = rbacManager;
        this.identity = identity;

        this.rolesAssignmentsToCreate = new ArrayList<>();
        this.roleDefinitionsAssignmentsToCreate = new ArrayList<>();
        this.rolesAssignmentsToRemove = new ArrayList<>();
        clear();
    }

    /**
     * Specifies that applications running on an Azure service with this identity requires
     * the given access role with scope of access limited to the current resource group that
     * the identity resides.
     *
     * @param asRole access role to assigned to the identity
     * @return RoleAssignmentHelper
     */
    RoleAssignmentHelper withRoleBasedAccessToCurrentResourceGroup(BuiltInRole asRole) {
        return this.withRoleBasedAccessTo(CURRENT_RESOURCE_GROUP_SCOPE, asRole);
    }

    /**
     * Specifies that applications running on an Azure service with this identity requires
     * the given access role with scope of access limited to the ARM resource identified by
     * the resource ID specified in the scope parameter.
     *
     * @param scope scope of the access represented in ARM resource ID format
     * @param asRole access role to assigned to the identity
     * @return RoleAssignmentHelper
     */
    RoleAssignmentHelper withRoleBasedAccessTo(String scope, BuiltInRole asRole) {
        RoleAssignmentCreator creator = new RoleAssignmentCreator(rbacManager,
                asRole.toString(),
                scope,
                true,
                this.identity);
        this.identity.taskGroup().addPostRunDependentTaskGroup(creator.taskGroup());
        this.rolesAssignmentsToCreate.add(creator);
        return this;
    }

    /**
     * Specifies that applications running on an Azure service with this identity requires
     * the given access role with scope of access limited to the current resource group that
     * the identity resides.
     *
     * @param roleDefinitionId access role definition to assigned to the identity
     * @return RoleAssignmentHelper
     */
    RoleAssignmentHelper withRoleDefinitionBasedAccessToCurrentResourceGroup(String roleDefinitionId) {
        return this.withRoleDefinitionBasedAccessTo(CURRENT_RESOURCE_GROUP_SCOPE, roleDefinitionId);
    }

    /**
     * Specifies that applications running on an Azure service with this identity requires
     * the access described in the given role definition with scope of access limited
     * to an ARM resource.
     *
     * @param scope scope of the access represented in ARM resource ID format
     * @param roleDefinitionId access role definition to assigned to the identity
     * @return RoleAssignmentHelper
     */
    RoleAssignmentHelper withRoleDefinitionBasedAccessTo(String scope, String roleDefinitionId) {
        RoleAssignmentCreator creator = new RoleAssignmentCreator(rbacManager,
                roleDefinitionId,
                scope,
                false,
                this.identity);
        this.identity.taskGroup().addPostRunDependentTaskGroup(creator.taskGroup());
        this.roleDefinitionsAssignmentsToCreate.add(creator);
        return this;
    }

    /**
     * Specifies that a role assigned to the identity should be removed.
     *
     * @param roleAssignment a role assigned to the identity
     * @return RoleAssignmentHelper
     */
    RoleAssignmentHelper withoutRoleAssignment(RoleAssignment roleAssignment) {
        if (!roleAssignment.principalId().equalsIgnoreCase(this.identity.principalId())) {
            return this;
        } else {
            RoleAssignmentRemover remover = new RoleAssignmentRemover(rbacManager,
                    roleAssignment,
                    this.identity);
            this.identity.taskGroup().addPostRunDependentTaskGroup(remover.taskGroup());
            this.rolesAssignmentsToRemove.add(remover);
            return this;
        }
    }

    /**
     * Specifies that a role assigned to the identity should be removed.
     *
     * @param scope the scope of the role assignment
     * @param asRole the role of the role assignment
     * @return RoleAssignmentHelper
     */
    RoleAssignmentHelper withoutRoleAssignment(String scope, BuiltInRole asRole) {
        RoleAssignmentRemover remover = new RoleAssignmentRemover(rbacManager,
                asRole.toString(),
                scope,
                this.identity);
        this.identity.taskGroup().addPostRunDependentTaskGroup(remover.taskGroup());
        this.rolesAssignmentsToRemove.add(remover);
        return this;
    }

    /**
     * An instance of RoleAssignmentHelper will be composed by an instance of IdentityImpl.
     * This same composed instance will be used to perform msi related actions as part of
     * that Identity's create and update actions. Hence once one of these actions
     * (Identity create, Identity update) is done, this method must be invoked to clear
     * the msi related states specific to that action.
     */
    public  void clear() {
        for (RoleAssignmentCreator role : rolesAssignmentsToCreate) {
            role.clear();
        }
        this.rolesAssignmentsToCreate.clear();
        for (RoleAssignmentCreator role : roleDefinitionsAssignmentsToCreate) {
            role.clear();
        }
        this.roleDefinitionsAssignmentsToCreate.clear();
        for (RoleAssignmentRemover role : rolesAssignmentsToRemove) {
            role.clear();
        }
        this.rolesAssignmentsToRemove.clear();
    }

    /**
     * TaskItem in the graph that creates role assignment for the MSI service principal.
     */
    private static class RoleAssignmentCreator extends IndexableTaskItem {
        private final GraphRbacManager rbacManager;
        private final String roleOrRoleDefinition;
        private final String scope;
        private final boolean isRole;
        private final IdentityImpl identity;

        RoleAssignmentCreator(final GraphRbacManager rbacManager,
                              final String roleOrRoleDefinition,
                              final String scope,
                              final boolean isRole,
                              final IdentityImpl identity) {
            this.rbacManager = rbacManager;
            this.roleOrRoleDefinition = roleOrRoleDefinition;
            this.scope = scope;
            this.isRole = isRole;
            this.identity = identity;
        }

        @Override
        public Observable<Indexable> invokeTaskAsync(TaskGroup.InvocationContext context) {
            final String roleAssignmentName = SdkContext.randomUuid();
            final String managedServiceIdentityPrincipalId = identity.principalId().toString();
            final String resourceScope;
            if (scope == CURRENT_RESOURCE_GROUP_SCOPE) {
                resourceScope = resourceGroupId(identity.id());
            } else {
                resourceScope = scope;
            }

            if (isRole) {
                final BuiltInRole role = BuiltInRole.fromString(roleOrRoleDefinition);
                return this.rbacManager
                        .roleAssignments()
                        .define(roleAssignmentName)
                        .forObjectId(managedServiceIdentityPrincipalId)
                        .withBuiltInRole(role)
                        .withScope(resourceScope)
                        .createAsync()
                        .last()
                        .onErrorResumeNext(checkRoleAssignmentError());
            } else {
                final String roleDefinitionId = roleOrRoleDefinition;
                return this.rbacManager
                        .roleAssignments()
                        .define(roleAssignmentName)
                        .forObjectId(managedServiceIdentityPrincipalId)
                        .withRoleDefinition(roleDefinitionId)
                        .withScope(resourceScope)
                        .createAsync()
                        .last()
                        .onErrorResumeNext(checkRoleAssignmentError());
            }
        }

        /**
         * This method returns ARM id of the resource group from the given ARM id of a resource
         * in the resource group.
         *
         * @param id ARM id
         * @return the ARM id of resource group
         */
        private String resourceGroupId(String id) {
            final ResourceId resourceId = ResourceId.fromString(id);
            final StringBuilder builder = new StringBuilder();
            builder.append("/subscriptions/")
                    .append(resourceId.subscriptionId())
                    .append("/resourceGroups/")
                    .append(resourceId.resourceGroupName());
            return builder.toString();
        }

        /**
         * @return a void observable if given throwable indicates role assignment already exists,
         * error observable otherwise.
         */
        private Func1<Throwable, Observable<Indexable>> checkRoleAssignmentError() {
            return new Func1<Throwable, Observable<Indexable>>() {
                @Override
                public Observable<Indexable> call(Throwable throwable) {
                    if (throwable instanceof CloudException) {
                        CloudException exception = (CloudException) throwable;
                        if (exception.body() != null
                                && exception.body().code() != null
                                && exception.body().code().equalsIgnoreCase("RoleAssignmentExists")) {
                            return voidObservable();
                        }
                    }
                    return Observable.<Indexable>error(throwable);
                }
            };
        }
    }


    /**
     * TaskItem in the graph that removes role assignment for a MSI service principal.
     */
    private static class RoleAssignmentRemover extends IndexableTaskItem {
        private final GraphRbacManager rbacManager;
        private final String role;
        private final String scope;
        private final RoleAssignment roleAssignment;
        private final IdentityImpl identity;

        RoleAssignmentRemover(final GraphRbacManager rbacManager,
                              final String role,
                              final String scope,
                              final IdentityImpl identity) {
            this.rbacManager = rbacManager;
            this.role = role;
            this.scope = scope;
            this.identity = identity;
            this.roleAssignment = null;
        }

        RoleAssignmentRemover(final GraphRbacManager rbacManager,
                              final RoleAssignment roleAssignment,
                              final IdentityImpl identity) {
            this.rbacManager = rbacManager;
            this.role = null;
            this.scope = null;
            this.identity = identity;
            this.roleAssignment = roleAssignment;
        }

        @Override
        protected Observable<Indexable> invokeTaskAsync(TaskGroup.InvocationContext context) {
            if (this.roleAssignment != null) {
                return rbacManager
                        .roleAssignments()
                        .deleteByIdAsync(roleAssignment.id())
                        .<Indexable>toObservable();
            } else {
                Objects.requireNonNull(role);

                return this.rbacManager
                        .roleDefinitions()
                        .getByScopeAndRoleNameAsync(this.scope, role)
                        .flatMap(new Func1<RoleDefinition, Observable<RoleAssignment>>() {
                            @Override
                            public Observable<RoleAssignment> call(final RoleDefinition roleDefinition) {
                                return rbacManager
                                        .roleAssignments()
                                        .listByScopeAsync(scope)
                                        .filter(new Func1<RoleAssignment, Boolean>() {
                                            @Override
                                            public Boolean call(RoleAssignment roleAssignment) {
                                                if (roleDefinition != null && roleAssignment != null) {
                                                    return roleAssignment.roleDefinitionId().equalsIgnoreCase(roleDefinition.id())
                                                            && roleAssignment.principalId().equalsIgnoreCase(identity.principalId());
                                                } else {
                                                    return false;
                                                }
                                            }
                                        })
                                        .first();
                            }
                        })
                        .flatMap(new Func1<RoleAssignment, Observable<Indexable>>() {
                            @Override
                            public Observable<Indexable> call(final RoleAssignment roleAssignment) {
                                return rbacManager
                                        .roleAssignments()
                                        .deleteByIdAsync(roleAssignment.id())
                                        .<Indexable>toObservable();
                            }
                        });
            }
        }
    }
}
