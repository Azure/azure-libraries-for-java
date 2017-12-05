/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.msi.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.graphrbac.BuiltInRole;
import com.microsoft.azure.management.graphrbac.RoleAssignment;
import com.microsoft.azure.management.msi.Identity;
import com.microsoft.azure.management.resources.Deployment;
import com.microsoft.azure.management.resources.DeploymentMode;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.io.IOException;
import java.io.InputStream;

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
        this.roleAssignmentHelper = new RoleAssignmentHelper(manager.graphRbacManager(), this);
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
    public IdentityImpl withRoleBasedAccessTo(Resource resource, BuiltInRole asRole) {
        this.roleAssignmentHelper.withRoleBasedAccessTo(resource.id(), asRole);
        return this;
    }

    @Override
    public IdentityImpl withRoleBasedAccessTo(String scope, BuiltInRole asRole) {
        this.roleAssignmentHelper.withRoleBasedAccessTo(scope, asRole);
        return this;
    }

    @Override
    public IdentityImpl withRoleBasedAccessToCurrentResourceGroup(BuiltInRole asRole) {
        this.roleAssignmentHelper.withRoleBasedAccessToCurrentResourceGroup(asRole);
        return this;
    }

    @Override
    public IdentityImpl withRoleDefinitionBasedAccessTo(Resource resource, String roleDefinitionId) {
        this.roleAssignmentHelper.withRoleDefinitionBasedAccessTo(resource.id(), roleDefinitionId);
        return this;
    }

    @Override
    public IdentityImpl withRoleDefinitionBasedAccessTo(String scope, String roleDefinitionId) {
        this.roleAssignmentHelper.withRoleDefinitionBasedAccessTo(scope, roleDefinitionId);
        return this;
    }

    @Override
    public IdentityImpl withRoleDefinitionBasedAccessToCurrentResourceGroup(String roleDefinitionId) {
        this.roleAssignmentHelper.withRoleDefinitionBasedAccessToCurrentResourceGroup(roleDefinitionId);
        return this;
    }

    @Override
    public IdentityImpl withoutRoleAssignment(RoleAssignment roleAssignment) {
        this.roleAssignmentHelper.withoutRoleAssignment(roleAssignment);
        return this;
    }

    @Override
    public IdentityImpl withoutRoleAssignment(String scope, BuiltInRole asRole) {
        this.roleAssignmentHelper.withoutRoleAssignment(scope, asRole);
        return this;
    }

    @Override
    public Completable afterPostRunAsync(boolean isFaulted) {
        this.roleAssignmentHelper.clear();
        return Completable.complete();
    }

    @Override
    public Observable<Identity> createResourceAsync() {
        final IdentityImpl self = this;
        return deployIdentityTemplateAsync()
                .flatMap(new Func1<Indexable, Observable<Identity>>() {
                    @Override
                    public Observable<Identity> call(Indexable indexable) {
                        return getInnerAsync()
                                .map(innerToFluentMap(self));
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

    /**
     * Prepare an ARM template for identity resource and deploy it.
     *
     * TODO: Replace template with imperative calls -
     * This is a temporary solution as the MSI resource provider has a bug that requires passing
     * empty properties section. This method will be replaced with imperative call once the service
     * fixes the issue.
     *
     * @return an ARM template deployment representing identity deployment.
     */
    private Observable<Indexable> deployIdentityTemplateAsync() {
        String templateStr;
        try {
            InputStream embeddedTemplate = IdentityImpl.class.getResourceAsStream("/identity.json");
            final ObjectMapper mapper = new ObjectMapper();
            final JsonNode template = mapper.readTree(embeddedTemplate);
            final ObjectNode locationParam = mapper.createObjectNode();
            locationParam.put("type", "string");
            locationParam.put("defaultValue", this.regionName());
            ObjectNode.class.cast(template.get("parameters")).replace("location", locationParam);

            final ObjectNode identityNameParam = mapper.createObjectNode();
            identityNameParam.put("type", "string");
            identityNameParam.put("defaultValue", this.name());
            ObjectNode.class.cast(template.get("parameters")).replace("identityName", identityNameParam);
            templateStr = template.toString();
        } catch (IOException ioException) {
            return Observable.error(new Throwable("client error: Failed to prepare identity template", ioException));
        }

        Creatable<Deployment> deploymentCreatable = null;
        try {
            deploymentCreatable = this.manager().resourceManager().deployments().define(this.name())
                    .withExistingResourceGroup(this.resourceGroupName())
                    .withTemplate(templateStr)
                    .withParameters("{}")
                    .withMode(DeploymentMode.INCREMENTAL);
        } catch (IOException e) {
            Observable.error(new Throwable("Client error: Failed to serialize identity template : " + templateStr, e));
        }
        return deploymentCreatable.createAsync().last();
    }
}
