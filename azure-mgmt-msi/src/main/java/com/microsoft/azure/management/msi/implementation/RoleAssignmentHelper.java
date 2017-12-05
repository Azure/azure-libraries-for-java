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
import com.microsoft.azure.management.resources.fluentcore.dag.FunctionalTaskItem;
import com.microsoft.azure.management.resources.fluentcore.dag.TaskGroup;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import rx.Observable;
import rx.functions.Func1;

/**
 * A utility class to operate on identity role assignments.
 */
@LangDefinition
final class RoleAssignmentHelper {
    /**
     * A type that provide the service principal id (object id) and ARM resource
     * id of the resource for which role assignments needs to be done.
     */
    interface IdProvider {
        /**
         * @return the service principal id (object id)
         */
        String principalId();
        /**
         * @return ARM resource id of the resource
         */
        String resourceId();
    }

    private static final String CURRENT_RESOURCE_GROUP_SCOPE = "CURRENT_RESOURCE_GROUP";

    private final GraphRbacManager rbacManager;
    private final IdProvider idProvider;
    private final TaskGroup preRunTaskGroup;

    /**
     * Creates RoleAssignmentHelper.
     *
     * @param rbacManager the graph rbac manager
     * @param taskGroup the pre-run task group after which role assignments create/remove tasks should run
     * @param idProvider the provider that provides service principal id and resource id
     */
    RoleAssignmentHelper(final GraphRbacManager rbacManager,
                         TaskGroup taskGroup,
                         IdProvider idProvider) {
        this.rbacManager = rbacManager;
        this.idProvider = idProvider;
        this.preRunTaskGroup = taskGroup;
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
    RoleAssignmentHelper withRoleBasedAccessTo(final String scope, final BuiltInRole asRole) {
        FunctionalTaskItem creator = new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> call(final Context cxt) {
                final String roleAssignmentName = SdkContext.randomUuid();
                final String managedServiceIdentityPrincipalId = idProvider.principalId();
                final String resourceScope;
                if (scope == CURRENT_RESOURCE_GROUP_SCOPE) {
                    resourceScope = resourceGroupId(idProvider.resourceId());
                } else {
                    resourceScope = scope;
                }
                return rbacManager
                        .roleAssignments()
                        .define(roleAssignmentName)
                        .forObjectId(managedServiceIdentityPrincipalId)
                        .withBuiltInRole(asRole)
                        .withScope(resourceScope)
                        .createAsync()
                        .last()
                        .onErrorResumeNext(new Func1<Throwable, Observable<Indexable>>() {
                            @Override
                            public Observable<Indexable> call(Throwable throwable) {
                                if (isRoleAssignmentExists(throwable)) {
                                    return cxt.voidObservable();
                                }
                                return Observable.<Indexable>error(throwable);
                            }
                        });
            }
        };
        this.preRunTaskGroup.addPostRunDependent(creator);
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
    RoleAssignmentHelper withRoleDefinitionBasedAccessTo(final String scope, final String roleDefinitionId) {
        FunctionalTaskItem creator = new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> call(final Context cxt) {
                final String roleAssignmentName = SdkContext.randomUuid();
                final String managedServiceIdentityPrincipalId = idProvider.principalId();
                final String resourceScope;
                if (scope == CURRENT_RESOURCE_GROUP_SCOPE) {
                    resourceScope = resourceGroupId(idProvider.resourceId());
                } else {
                    resourceScope = scope;
                }
                return rbacManager
                        .roleAssignments()
                        .define(roleAssignmentName)
                        .forObjectId(managedServiceIdentityPrincipalId)
                        .withRoleDefinition(roleDefinitionId)
                        .withScope(resourceScope)
                        .createAsync()
                        .last()
                        .onErrorResumeNext(new Func1<Throwable, Observable<Indexable>>() {
                            @Override
                            public Observable<Indexable> call(Throwable throwable) {
                                if (isRoleAssignmentExists(throwable)) {
                                    return cxt.voidObservable();
                                }
                                return Observable.<Indexable>error(throwable);
                            }
                        });
            }
        };
        this.preRunTaskGroup.addPostRunDependent(creator);
        return this;
    }

    /**
     * Specifies that a role assigned to the identity should be removed.
     *
     * @param roleAssignment a role assigned to the identity
     * @return RoleAssignmentHelper
     */
    RoleAssignmentHelper withoutRoleAssignment(final RoleAssignment roleAssignment) {
        if (!roleAssignment.principalId().equalsIgnoreCase(idProvider.principalId())) {
            return this;
        }
        FunctionalTaskItem remover = new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> call(final Context cxt) {
                return rbacManager
                        .roleAssignments()
                        .deleteByIdAsync(roleAssignment.id())
                        .<Indexable>toObservable()
                        .switchIfEmpty(cxt.voidObservable());
            }
        };
        this.preRunTaskGroup.addPostRunDependent(remover);
        return this;
    }

    /**
     * Specifies that a role assigned to the identity should be removed.
     *
     * @param scope the scope of the role assignment
     * @param asRole the role of the role assignment
     * @return RoleAssignmentHelper
     */
    RoleAssignmentHelper withoutRoleAssignment(final String scope, final BuiltInRole asRole) {
        FunctionalTaskItem remover = new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> call(final Context cxt) {
                return rbacManager
                        .roleDefinitions()
                        .getByScopeAndRoleNameAsync(scope, asRole.toString())
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
                                                            && roleAssignment.principalId().equalsIgnoreCase(idProvider.principalId());
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
                                        .<Indexable>toObservable()
                                        .switchIfEmpty(cxt.voidObservable());
                            }
                        });
            }
        };
        this.preRunTaskGroup.addPostRunDependent(remover);
        return this;
    }

    /**
     * This method returns ARM id of the resource group from the given ARM id of a resource
     * in the resource group.
     *
     * @param id ARM id
     * @return the ARM id of resource group
     */
    private static String resourceGroupId(String id) {
        final ResourceId resourceId = ResourceId.fromString(id);
        final StringBuilder builder = new StringBuilder();
        builder.append("/subscriptions/")
                .append(resourceId.subscriptionId())
                .append("/resourceGroups/")
                .append(resourceId.resourceGroupName());
        return builder.toString();
    }

    /**
     * Checks whether the given exception indicates role assignment already exists or not.
     *
     * @param throwable the exception to check
     * @return true if role assignment exists, false otherwise
     */
    private static boolean isRoleAssignmentExists(Throwable throwable) {
        if (throwable instanceof CloudException) {
            CloudException exception = (CloudException) throwable;
            if (exception.body() != null
                    && exception.body().code() != null
                    && exception.body().code().equalsIgnoreCase("RoleAssignmentExists")) {
                return true;
            }
        }
        return false;
    }
}
