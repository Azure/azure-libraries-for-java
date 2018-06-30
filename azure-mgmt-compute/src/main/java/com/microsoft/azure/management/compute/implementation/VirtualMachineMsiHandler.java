/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.compute.ResourceIdentityType;
import com.microsoft.azure.management.compute.VirtualMachineIdentity;
import com.microsoft.azure.management.graphrbac.implementation.GraphRbacManager;
import com.microsoft.azure.management.graphrbac.implementation.RoleAssignmentHelper;
import com.microsoft.azure.management.msi.Identity;
import com.microsoft.azure.management.resources.fluentcore.dag.TaskGroup;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Utility class to set Managed Service Identity (MSI) property on a virtual machine,
 * install or update MSI extension and create role assignments for the service principal
 * associated with the virtual machine.
 */
@LangDefinition
class VirtualMachineMsiHandler extends RoleAssignmentHelper {
    private final VirtualMachineImpl virtualMachine;

    private List<String> creatableIdentityKeys;

    /**
     * Creates VirtualMachineMsiHandler.
     *
     * @param rbacManager the graph rbac manager
     * @param virtualMachine the virtual machine to which MSI extension needs to be installed and
     *                       for which role assignments needs to be created
     */
    VirtualMachineMsiHandler(final GraphRbacManager rbacManager,
                             VirtualMachineImpl virtualMachine) {
        super(rbacManager, virtualMachine.taskGroup(), virtualMachine.idProvider());
        this.virtualMachine = virtualMachine;
        this.creatableIdentityKeys = new ArrayList<>();
    }

    /**
     * Specifies that Local Managed Service Identity needs to be enabled in the virtual machine.
     * If MSI extension is not already installed then it will be installed with access token
     * port as 50342.
     *
     * @return VirtualMachineMsiHandler
     */
    VirtualMachineMsiHandler withLocalManagedServiceIdentity() {
        return withLocalManagedServiceIdentity(null);
    }

    /**
     * Specifies that Local Managed Service Identity property needs to be enabled in the virtual machine.
     *
     * @param port the port in the virtual machine to get the access token from

     * @return VirtualMachineMsiHandler
     */
    VirtualMachineMsiHandler withLocalManagedServiceIdentity(Integer port) {
        this.initVMIdentity(ResourceIdentityType.SYSTEM_ASSIGNED);
        return this;
    }

    /**
     * Specifies that given identity should be set as one of the External Managed Service Identity
     * of the virtual machine.
     *
     * @param creatableIdentity yet-to-be-created identity to be associated with the virtual machine
     * @return VirtualMachineMsiHandler
     */
    VirtualMachineMsiHandler withNewExternalManagedServiceIdentity(Creatable<Identity> creatableIdentity) {
        this.initVMIdentity(ResourceIdentityType.USER_ASSIGNED);

        TaskGroup.HasTaskGroup dependency = (TaskGroup.HasTaskGroup) creatableIdentity;
        Objects.requireNonNull(dependency);

        this.virtualMachine.taskGroup().addDependency(dependency);
        this.creatableIdentityKeys.add(creatableIdentity.key());

        return this;
    }

    /**
     * Specifies that given identity should be set as one of the External Managed Service Identity
     * of the virtual machine.
     *
     * @param identity an identity to associate
     * @return VirtualMachineMsiHandler
     */
    VirtualMachineMsiHandler withExistingExternalManagedServiceIdentity(Identity identity) {
        this.initVMIdentity(ResourceIdentityType.USER_ASSIGNED);
        Utils.addToListIfNotExists(this.virtualMachine.inner().identity().identityIds(), identity.id());
        return this;
    }

    /**
     * Specifies that given identity should be removed from the list of External Managed Service Identity
     * associated with the virtual machine machine.
     *
     * @param identityId resource id of the identity
     * @return VirtualMachineMsiHandler
     */
    VirtualMachineMsiHandler withoutExternalManagedServiceIdentity(String identityId) {
        VirtualMachineInner virtualMachineInner = this.virtualMachine.inner();
        if (virtualMachineInner.identity() != null && virtualMachineInner.identity().identityIds() != null) {
            Utils.removeFromList(this.virtualMachine.inner().identity().identityIds(), identityId);
        }
        return this;
    }

    /**
     * Update the VM payload model using the created External Managed Service Identities.
     */
    void handleExternalIdentitySettings() {
        for (String key : this.creatableIdentityKeys) {
            Identity identity = (Identity) this.virtualMachine.taskGroup().taskResult(key);
            Objects.requireNonNull(identity);
            Utils.addToListIfNotExists(this.virtualMachine.inner().identity().identityIds(), identity.id());
        }
        this.creatableIdentityKeys.clear();
    }

    /**
     * Initialize VM's identity property.
     *
     * @param identityType the identity type to set
     */
    private void initVMIdentity(ResourceIdentityType identityType) {
        if (!identityType.equals(ResourceIdentityType.USER_ASSIGNED)
                && !identityType.equals(ResourceIdentityType.SYSTEM_ASSIGNED)) {
            throw new IllegalArgumentException("Invalid argument: " + identityType);
        }

        VirtualMachineInner virtualMachineInner = this.virtualMachine.inner();
        if (virtualMachineInner.identity() == null) {
            virtualMachineInner.withIdentity(new VirtualMachineIdentity());
        }
        if (virtualMachineInner.identity().type() == null
                || virtualMachineInner.identity().type().equals(ResourceIdentityType.NONE)
                || virtualMachineInner.identity().type().equals(identityType)) {
            virtualMachineInner.identity().withType(identityType);
        } else {
            virtualMachineInner.identity().withType(ResourceIdentityType.SYSTEM_ASSIGNED_USER_ASSIGNED);
        }
        if (virtualMachineInner.identity().identityIds() == null) {
            if (identityType.equals(ResourceIdentityType.USER_ASSIGNED)
                    || identityType.equals(ResourceIdentityType.SYSTEM_ASSIGNED_USER_ASSIGNED)) {
                virtualMachineInner.identity().withIdentityIds(new ArrayList<String>());
            }
        }
    }
}