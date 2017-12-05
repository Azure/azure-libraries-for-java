/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.msi;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.graphrbac.BuiltInRole;
import com.microsoft.azure.management.graphrbac.RoleAssignment;
import com.microsoft.azure.management.msi.implementation.IdentityInner;
import com.microsoft.azure.management.msi.implementation.MSIManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;


/**
 * An immutable client-side representation of an Azure Managed Service Identity (MSI) Identity resource.
 */
@Fluent
@Beta // TODO Add since v1.5 param
public interface Identity
        extends GroupableResource<MSIManager, IdentityInner>,
        Refreshable<Identity>,
        Updatable<Identity.Update> {
    /**
     * @return id of the Azure Active Directory tenant to which the identity belongs to
     */
    String tenantId();

    /**
     * @return id of the Azure Active Directory service principal object associated with the identity
     */
    String principalId();

    /**
     * @return id of the Azure Active Directory application associated with the identity
     */
    String clientId();

    /**
     * @return the url that can be queried to obtain the identity credentials
     */
     String clientSecretUrl();

    /**
     * Container interface for all the definitions related to identity.
     */
    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.WithGroup,
            DefinitionStages.WithCreate {
    }

    /**
     * Grouping of identity definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of an identity definition.
         */
        interface Blank extends GroupableResource.DefinitionWithRegion<WithGroup> {
        }

        /**
         * The stage of the identity definition allowing to specify the resource group.
         */
        interface WithGroup extends GroupableResource.DefinitionStages.WithGroup<WithCreate> {
        }

        /**
         * The stage of the identity definition allowing to set role assignment for a scope.
         */
        @Beta // TODO Add since v1.5 param
        interface WithRoleAndScope {
            /**
             * Specifies that applications running on an Azure service with this identity requires
             * the given access role with scope of access limited to an ARM resource.
             *
             * @param resource scope of the access represented as ARM resource
             * @param asRole access role to assigned to the identity
             * @return the next stage of the definition
             */
            @Beta // TODO Add since v1.5 param
            WithCreate withRoleBasedAccessTo(Resource resource, BuiltInRole asRole);

            /**
             * Specifies that applications running on an Azure service with this identity requires
             * the given access role with scope of access limited to the ARM resource identified by
             * the resource ID specified in the scope parameter.
             *
             * @param scope scope of the access represented in ARM resource ID format
             * @param asRole access role to assigned to the identity
             * @return the next stage of the definition
             */
            @Beta // TODO Add since v1.5 param
            WithCreate withRoleBasedAccessTo(String scope, BuiltInRole asRole);

            /**
             * Specifies that applications running on an Azure service with this identity requires
             * the given access role with scope of access limited to the current resource group that
             * the identity resides.
             *
             * @param asRole access role to assigned to the identity
             * @return the next stage of the definition
             */
            @Beta // TODO Add since v1.5 param
            WithCreate withRoleBasedAccessToCurrentResourceGroup(BuiltInRole asRole);

            /**
             * Specifies that applications running on an Azure service with this identity requires
             * the access described in the given role definition with scope of access limited
             * to an ARM resource.
             *
             * @param resource scope of the access represented as ARM resource
             * @param roleDefinitionId access role definition to assigned to the identity
             * @return the next stage of the definition
             */
            @Beta // TODO Add since v1.5 param
            WithCreate withRoleDefinitionBasedAccessTo(Resource resource, String roleDefinitionId);

            /**
             * Specifies that applications running on an Azure service with this identity requires the
             * access described in the given role definition with scope of access limited to the ARM
             * resource identified by the resource ID specified in the scope parameter.
             *
             * @param scope scope of the access represented in ARM resource ID format
             * @param roleDefinitionId access role definition to assigned to the identity
             * @return the next stage of the definition
             */
            @Beta // TODO Add since v1.5 param
            WithCreate withRoleDefinitionBasedAccessTo(String scope, String roleDefinitionId);

            /**
             * Specifies that applications running on an Azure service requires the access described
             * in the given role definition with scope of access limited to the current resource group
             * that the identity resides.
             *
             * @param roleDefinitionId access role definition to assigned to the identity
             * @return the next stage of the definition
             */
            @Beta // TODO Add since v1.5 param
            WithCreate withRoleDefinitionBasedAccessToCurrentResourceGroup(String roleDefinitionId);
        }

        /**
         * The stage of the identity definition which contains all the minimum required inputs for
         * the resource to be created but also allows for any other optional settings to be specified.
         */
        interface WithCreate extends
                Resource.DefinitionWithTags<WithCreate>,
                Creatable<Identity>,
                DefinitionStages.WithRoleAndScope {
        }
    }

    /**
     * Grouping of identity update stages.
     */
    interface UpdateStages {
        /**
         * The stage of the identity update allowing to set role assignment for a scope.
         */
        @Beta // TODO Add since v1.5 param
        interface WithRoleAndScope {
            /**
             * Specifies that applications running on an Azure service with this identity requires
             * the given access role with scope of access limited to an ARM resource.
             *
             * @param resource scope of the access represented as ARM resource
             * @param asRole access role to assigned to the identity
             * @return the next stage of the update
             */
            @Beta // TODO Add since v1.5 param
            Update withRoleBasedAccessTo(Resource resource, BuiltInRole asRole);

            /**
             * Specifies that applications running on an Azure service with this identity requires
             * the given access role with scope of access limited to the ARM resource identified by
             * the resource ID specified in the scope parameter.
             *
             * @param scope scope of the access represented in ARM resource ID format
             * @param asRole access role to assigned to the identity
             * @return the next stage of the update
             */
            @Beta // TODO Add since v1.5 param
            Update withRoleBasedAccessTo(String scope, BuiltInRole asRole);

            /**
             * Specifies that applications running on an Azure service with this identity requires
             * the given access role with scope of access limited to the current resource group that
             * the identity resides.
             *
             * @param asRole access role to assigned to the identity
             * @return the next stage of the update
             */
            @Beta // TODO Add since v1.5 param
            Update withRoleBasedAccessToCurrentResourceGroup(BuiltInRole asRole);

            /**
             * Specifies that applications running on an Azure service with this identity requires
             * the access described in the given role definition with scope of access limited
             * to an ARM resource.
             *
             * @param resource scope of the access represented as ARM resource
             * @param roleDefinitionId access role definition to assigned to the identity
             * @return the next stage of the update
             */
            @Beta // TODO Add since v1.5 param
            Update withRoleDefinitionBasedAccessTo(Resource resource, String roleDefinitionId);

            /**
             * Specifies that applications running on an Azure service with this identity requires the
             * access described in the given role definition with scope of access limited to the ARM
             * resource identified by the resource ID specified in the scope parameter.
             *
             * @param scope scope of the access represented in ARM resource ID format
             * @param roleDefinitionId access role definition to assigned to the identity
             * @return the next stage of the update
             */
            @Beta // TODO Add since v1.5 param
            Update withRoleDefinitionBasedAccessTo(String scope, String roleDefinitionId);

            /**
             * Specifies that applications running on an Azure service requires the access described
             * in the given role definition with scope of access limited to the current resource group
             * that the identity resides.
             *
             * @param roleDefinitionId access role definition to assigned to the identity
             * @return the next stage of the update
             */
            @Beta // TODO Add since v1.5 param
            Update withRoleDefinitionBasedAccessToCurrentResourceGroup(String roleDefinitionId);

            /**
             * Specifies that a role assigned to the identity should be removed.
             *
             * @param roleAssignment a role assigned to the identity
             * @return the next stage of the update
             */
            @Beta // TODO Add since v1.5 param
            Update withoutRoleAssignment(RoleAssignment roleAssignment);

            /**
             * Specifies that a role assigned to the identity should be removed.
             *
             * @param scope the scope of the role assignment
             * @param asRole the role of the role assignment
             * @return the next stage of the update
             */
            @Beta // TODO Add since v1.5 param
            Update withoutRoleAssignment(String scope, BuiltInRole asRole);
        }
    }

    /**
     * The template for an identity update operation, containing all the settings that can be modified.
     */
    interface Update extends
            Appliable<Identity>,
            UpdateStages.WithRoleAndScope,
            Resource.UpdateWithTags<Update> {
    }
}
