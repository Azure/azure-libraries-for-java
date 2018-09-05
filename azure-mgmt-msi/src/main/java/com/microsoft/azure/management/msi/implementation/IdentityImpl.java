/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.msi.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.graphrbac.BuiltInRole;
import com.microsoft.azure.management.graphrbac.RoleAssignment;
import com.microsoft.azure.management.graphrbac.implementation.RoleAssignmentHelper;
import com.microsoft.azure.management.msi.Identity;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import rx.Observable;
import rx.functions.Func1;

import java.util.Objects;

/**
 * The implementation for Identity and its create and update interfaces.
 */
@LangDefinition
final class IdentityImpl
        extends GroupableResourceImpl<Identity, IdentityInner, IdentityImpl, MSIManager>
        implements Identity, Identity.Definition, Identity.Update {

    private RoleAssignmentHelper roleAssignmentHelper;

    protected IdentityImpl(String name, IdentityInner innerObject, MSIManager manager) {
        super(name, innerObject, manager);
        this.roleAssignmentHelper = new RoleAssignmentHelper(manager.graphRbacManager(), this.taskGroup(), this.idProvider());
    }

    @Override
    public String tenantId() {
        if (this.inner().tenantId() == null) {
            return null;
        } else {
            return this.inner().tenantId().toString();
        }
    }

    @Override
    public String principalId() {
        if (this.inner().principalId() == null) {
            return null;
        } else {
            return this.inner().principalId().toString();
        }
    }

    @Override
    public String clientId() {
        if (this.inner().clientId() == null) {
            return null;
        } else {
            return this.inner().clientId().toString();
        }
    }

    @Override
    public String clientSecretUrl() {
        return this.inner().clientSecretUrl();
    }

    @Override
    public IdentityImpl withAccessTo(Resource resource, BuiltInRole role) {
        this.roleAssignmentHelper.withAccessTo(resource.id(), role);
        return this;
    }

    @Override
    public IdentityImpl withAccessTo(String resourceId, BuiltInRole role) {
        this.roleAssignmentHelper.withAccessTo(resourceId, role);
        return this;
    }

    @Override
    public IdentityImpl withAccessToCurrentResourceGroup(BuiltInRole role) {
        this.roleAssignmentHelper.withAccessToCurrentResourceGroup(role);
        return this;
    }

    @Override
    public IdentityImpl withAccessTo(Resource resource, String roleDefinitionId) {
        this.roleAssignmentHelper.withAccessTo(resource.id(), roleDefinitionId);
        return this;
    }

    @Override
    public IdentityImpl withAccessTo(String resourceId, String roleDefinitionId) {
        this.roleAssignmentHelper.withAccessTo(resourceId, roleDefinitionId);
        return this;
    }

    @Override
    public IdentityImpl withAccessToCurrentResourceGroup(String roleDefinitionId) {
        this.roleAssignmentHelper.withAccessToCurrentResourceGroup(roleDefinitionId);
        return this;
    }

    @Override
    public IdentityImpl withoutAccess(RoleAssignment access) {
        this.roleAssignmentHelper.withoutAccessTo(access);
        return this;
    }

    @Override
    public IdentityImpl withoutAccessTo(String resourceId, BuiltInRole role) {
        this.roleAssignmentHelper.withoutAccessTo(resourceId, role);
        return this;
    }

    @Override
    public Observable<Identity> createResourceAsync() {
        return this.manager().inner().userAssignedIdentities()
                .createOrUpdateAsync(this.resourceGroupName(), this.name(), this.inner())
                .map(innerToFluentMap(this))
                .flatMap(new Func1<Identity, Observable<Identity>>() {
                    @Override
                    public Observable<Identity> call(Identity identity) {
                        // Often getting 'Principal xxx does not exist in the directory yyy'
                        // error when attempting to create role (access) assignments just
                        // after identity creation, so delaying here for some time before
                        // proceeding with next operation.
                        //
                        return SdkContext.delayedEmitAsync(identity, 30 * 1000);
                    }
                });
    }

    @Override
    protected Observable<IdentityInner> getInnerAsync() {
        return this.myManager
                .inner()
                .userAssignedIdentities()
                .getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    private RoleAssignmentHelper.IdProvider idProvider() {
        return new RoleAssignmentHelper.IdProvider() {
            @Override
            public String principalId() {
                Objects.requireNonNull(inner());
                Objects.requireNonNull(inner().principalId());
                return inner().principalId().toString();
            }
            @Override
            public String resourceId() {
                Objects.requireNonNull(inner());
                Objects.requireNonNull(inner().id());
                return inner().id();
            }
        };
    }
}
