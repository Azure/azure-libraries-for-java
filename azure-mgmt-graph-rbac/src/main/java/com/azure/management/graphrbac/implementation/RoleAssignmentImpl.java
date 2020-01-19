/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.graphrbac.implementation;

import com.azure.core.management.CloudException;
import com.azure.management.graphrbac.ActiveDirectoryGroup;
import com.azure.management.graphrbac.ActiveDirectoryUser;
import com.azure.management.graphrbac.BuiltInRole;
import com.azure.management.graphrbac.RoleAssignment;
import com.azure.management.graphrbac.RoleAssignmentCreateParameters;
import com.azure.management.graphrbac.ServicePrincipal;
import com.azure.management.graphrbac.models.RoleAssignmentInner;
import com.azure.management.resources.ResourceGroup;
import com.azure.management.resources.fluentcore.arm.models.Resource;
import com.azure.management.resources.fluentcore.model.implementation.CreatableImpl;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import org.reactivestreams.Publisher;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Implementation for ServicePrincipal and its parent interfaces.
 */
class RoleAssignmentImpl
        extends CreatableImpl<RoleAssignment, RoleAssignmentInner, RoleAssignmentImpl>
        implements
            RoleAssignment,
            RoleAssignment.Definition {
    private GraphRbacManager manager;
    // Active Directory identify info
    private String objectId;
    private String userName;
    private String servicePrincipalName;
    // role info
    private String roleDefinitionId;
    private String roleName;

    RoleAssignmentImpl(String name, RoleAssignmentInner innerObject, GraphRbacManager manager) {
        super(name, innerObject);
        this.manager = manager;
    }

    @Override
    public boolean isInCreateMode() {
        return getInner().getId() == null;
    }

    @Override
    public Mono<RoleAssignment> createResourceAsync() {
        Mono<String> objectIdObservable;
        if (objectId != null) {
            objectIdObservable = Mono.just(objectId);
        } else if (userName != null) {
            objectIdObservable = manager.users().getByNameAsync(userName)
                    .map(user -> user.getId());
        } else if (servicePrincipalName != null) {
            objectIdObservable = manager.servicePrincipals().getByNameAsync(servicePrincipalName)
                    .map(sp -> sp.getId());
        } else {
            throw new IllegalArgumentException("Please pass a non-null value for either object Id, user, group, or service principal");
        }

        Mono<String> roleDefinitionIdObservable;
        if (roleDefinitionId != null) {
            roleDefinitionIdObservable = Mono.just(roleDefinitionId);
        } else if (roleName != null) {
            roleDefinitionIdObservable = getManager().roleDefinitions().getByScopeAndRoleNameAsync(scope(), roleName)
                    .map(roleDefinition -> roleDefinition.getId());
        } else {
            throw new IllegalArgumentException("Please pass a non-null value for either role name or role definition ID");
        }

        return Mono.zip(objectIdObservable, roleDefinitionIdObservable, new BiFunction<String, String, RoleAssignmentCreateParameters>() {
            @Override
            public RoleAssignmentCreateParameters apply(String objectId, String roleDefinitionId) {
                return new RoleAssignmentCreateParameters()
                        .setPrincipalId(objectId).setRoleDefinitionId(roleDefinitionId);
            }
        }).flatMap((Function<RoleAssignmentCreateParameters, Mono<RoleAssignmentInner>>) roleAssignmentPropertiesInner -> getManager().roleInner().roleAssignments()
                .createAsync(scope(), getName(), roleAssignmentPropertiesInner)
                .retryWhen(throwableFlux -> throwableFlux.zipWith(Flux.range(1, 30), (throwable, integer) -> {
                    if (throwable instanceof  CloudException) {
                        CloudException cloudException = (CloudException) throwable;
                        if ((cloudException.getValue().getCode() != null && cloudException.getValue().getCode().equalsIgnoreCase("PrincipalNotFound"))
                            || (cloudException.getValue()).getMessage() != null && cloudException.getValue().getMessage().toLowerCase().contains("does not exist in the directory")) {
                            // ref: https://github.com/Azure/azure-cli/blob/dev/src/command_modules/azure-cli-role/azure/cli/command_modules/role/custom.py#L1048-L1065
                            return integer;
                        } else {
                            throw Exceptions.propagate(throwable);
                        }
                    } else {
                        throw Exceptions.propagate(throwable);
                    }
                }).flatMap((Function<Integer, Publisher<?>>) i -> SdkContext.delayedEmitAsync(i, i * 1000)))).map(innerToFluentMap(this));
    }

    @Override
    protected Mono<RoleAssignmentInner> getInnerAsync() {
        return manager.roleInner().roleAssignments().getAsync(scope(), getName());
    }

    @Override
    public String scope() {
        return getInner().getScope();
    }

    @Override
    public String roleDefinitionId() {
        return getInner().getRoleDefinitionId();
    }

    @Override
    public String principalId() {
        return getInner().getPrincipalId();
    }

    @Override
    public RoleAssignmentImpl forObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    @Override
    public RoleAssignmentImpl forUser(ActiveDirectoryUser user) {
        this.objectId = user.getId();
        return this;
    }

    @Override
    public RoleAssignmentImpl forUser(String name) {
        this.userName = name;
        return this;
    }

    @Override
    public RoleAssignmentImpl forGroup(ActiveDirectoryGroup activeDirectoryGroup) {
        this.objectId = activeDirectoryGroup.getId();
        return this;
    }

    @Override
    public RoleAssignmentImpl forServicePrincipal(ServicePrincipal servicePrincipal) {
        this.objectId = servicePrincipal.getId();
        return this;
    }

    @Override
    public RoleAssignmentImpl forServicePrincipal(String servicePrincipalName) {
        this.servicePrincipalName = servicePrincipalName;
        return this;
    }

    @Override
    public RoleAssignmentImpl withBuiltInRole(BuiltInRole role) {
        this.roleName = role.toString();
        return this;
    }

    @Override
    public RoleAssignmentImpl withRoleDefinition(String roleDefinitionId) {
        this.roleDefinitionId = roleDefinitionId;
        return this;
    }

    @Override
    public RoleAssignmentImpl withScope(String scope) {
        this.getInner().setScope(scope);
        return this;
    }

    @Override
    public RoleAssignmentImpl withResourceGroupScope(ResourceGroup resourceGroup) {
        return withScope(resourceGroup.getId());
    }

    @Override
    public RoleAssignmentImpl withResourceScope(Resource resource) {
        return withScope(resource.getId());
    }

    @Override
    public RoleAssignmentImpl withSubscriptionScope(String subscriptionId) {
        return withScope("subscriptions/" + subscriptionId);
    }

    @Override
    public String getId() {
        return getInner().getId();
    }

    @Override
    public GraphRbacManager getManager() {
        return this.manager;
    }
}
