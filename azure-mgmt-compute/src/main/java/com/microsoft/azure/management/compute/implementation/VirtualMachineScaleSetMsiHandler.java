/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.compute.OperatingSystemTypes;
import com.microsoft.azure.management.compute.ResourceIdentityType;
import com.microsoft.azure.management.compute.VirtualMachineScaleSet;
import com.microsoft.azure.management.compute.VirtualMachineScaleSetExtension;
import com.microsoft.azure.management.compute.VirtualMachineScaleSetIdentity;
import com.microsoft.azure.management.graphrbac.implementation.GraphRbacManager;
import com.microsoft.azure.management.graphrbac.implementation.RoleAssignmentHelper;
import com.microsoft.azure.management.msi.Identity;
import com.microsoft.azure.management.resources.fluentcore.dag.TaskGroup;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class to set Managed Service Identity (MSI) property on a virtual machine scale set,
 * install or update MSI extension and create role assignments for the service principal (LMSI)
 * associated with the virtual machine scale set.
 */
@LangDefinition
class VirtualMachineScaleSetMsiHandler extends RoleAssignmentHelper {
    private static final int DEFAULT_TOKEN_PORT = 50342;
    private static final String MSI_EXTENSION_PUBLISHER_NAME = "Microsoft.ManagedIdentity";
    private static final String LINUX_MSI_EXTENSION = "ManagedIdentityExtensionForLinux";
    private static final String WINDOWS_MSI_EXTENSION = "ManagedIdentityExtensionForWindows";

    private final VirtualMachineScaleSetImpl scaleSet;

    private Integer tokenPort;
    private boolean scheduleMSIExtensionInstallation;
    private List<String> creatableIdentityKeys;

    /**
     * Creates VirtualMachineScaleSetMsiHandler.
     *
     * @param rbacManager the graph rbac manager
     */
    VirtualMachineScaleSetMsiHandler(GraphRbacManager rbacManager, VirtualMachineScaleSetImpl scaleSet) {
        super(rbacManager, scaleSet.taskGroup(), scaleSet.idProvider());
        this.scaleSet = scaleSet;
        this.creatableIdentityKeys = new ArrayList<>();
    }

    /**
     * Specifies that Local Managed Service Identity needs to be enabled in the virtual machine scale set.
     * If MSI extension is not already installed then it will be installed with access token port as 50342.
     *
     * @return VirtualMachineScaleSetMsiHandler
     */
    VirtualMachineScaleSetMsiHandler withLocalManagedServiceIdentity() {
        return withLocalManagedServiceIdentity(null);
    }

    /**
     * Specifies that Local Managed Service Identity property needs to be enabled in the virtual machine
     * scale set.
     *
     * @param port the port in the scale set VM instance to get the access token from
     * @return VirtualMachineScaleSetMsiHandler
     */
    VirtualMachineScaleSetMsiHandler withLocalManagedServiceIdentity(Integer port) {
        this.initVMSSIdentity(ResourceIdentityType.SYSTEM_ASSIGNED);
        this.tokenPort = port;
        this.scheduleMSIExtensionInstallation = true;
        return this;
    }

    /**
     * Specifies that given identity should be set as one of the External Managed Service Identity
     * of the virtual machine scale set.
     *
     * @param creatableIdentity yet-to-be-created identity to be associated with the virtual machine
     *                          scale set
     * @return VirtualMachineScaleSetMsiHandler
     */
    VirtualMachineScaleSetMsiHandler withNewExternalManagedServiceIdentity(Creatable<Identity> creatableIdentity) {
        this.initVMSSIdentity(ResourceIdentityType.USER_ASSIGNED);

        TaskGroup.HasTaskGroup dependency = (TaskGroup.HasTaskGroup) creatableIdentity;
        Objects.requireNonNull(dependency);

        this.scaleSet.taskGroup().addDependency(dependency);
        this.creatableIdentityKeys.add(creatableIdentity.key());

        this.scheduleMSIExtensionInstallation = true;
        return this;
    }

    /**
     * Specifies that given identity should be set as one of the External Managed Service Identity
     * of the virtual machine scale set.
     *
     * @param identity an identity to associate
     * @return VirtualMachineScaleSetMsiHandler
     */
    VirtualMachineScaleSetMsiHandler withExistingExternalManagedServiceIdentity(Identity identity) {
        this.initVMSSIdentity(ResourceIdentityType.USER_ASSIGNED);
        Utils.addToListIfNotExists(this.scaleSet.inner().identity().identityIds(), identity.id());
        this.scheduleMSIExtensionInstallation = true;
        return this;
    }

    /**
     * Specifies that given identity should be removed from the list of External Managed Service Identity
     * associated with the virtual machine machine scale set.
     *
     * @param identityId resource id of the identity
     * @return VirtualMachineScaleSetMsiHandler
     */
    VirtualMachineScaleSetMsiHandler withoutExternalManagedServiceIdentity(String identityId) {
        VirtualMachineScaleSetInner scaleSetInner = this.scaleSet.inner();
        if (scaleSetInner.identity() != null && scaleSetInner.identity().identityIds() != null) {
            Utils.removeFromList(scaleSetInner.identity().identityIds(), identityId);
        }
        return this;
    }

    /**
     * Update the VMSS payload model using the created External Managed Service Identities.
     */
    void handleExternalIdentitySettings() {
        for (String key : this.creatableIdentityKeys) {
            Identity identity = (Identity) this.scaleSet.taskGroup().taskResult(key);
            Objects.requireNonNull(identity);
            Utils.addToListIfNotExists(this.scaleSet.inner().identity().identityIds(), identity.id());
        }
        this.creatableIdentityKeys.clear();
    }

    /**
     * Add or update the Managed Service Identity extension for the given virtual machine scale set.
     *
     * @param scaleSetImpl the scale set
     */
    void addOrUpdateMSIExtension(VirtualMachineScaleSetImpl scaleSetImpl) {
        if (!scheduleMSIExtensionInstallation) {
            return;
        }
        // To add or update MSI extension, we relay on methods exposed from interfaces instead of from
        // impl so that any breaking change in the contract cause a compile time error here. So do not
        // change the below 'updateExtension' or 'defineNewExtension' to use impls.
        //
        String msiExtensionType = msiExtensionType(scaleSetImpl.osTypeIntern());
        VirtualMachineScaleSetExtension msiExtension = getMSIExtension(scaleSetImpl.extensions(), msiExtensionType);
        if (msiExtension != null) {
            Object currentTokenPortObj = msiExtension.publicSettings().get("port");
            Integer currentTokenPort = objectToInteger(currentTokenPortObj);
            Integer newPort;
            if (this.tokenPort != null) {
                // user specified a port
                newPort = this.tokenPort;
            } else if (currentTokenPort != null) {
                // user didn't specify a port and currently there is a port
                newPort = currentTokenPort;
            } else {
                // user didn't specify a port and currently there is no port
                newPort = DEFAULT_TOKEN_PORT;
            }
            VirtualMachineScaleSet.Update appliableVMSS = scaleSetImpl;
            appliableVMSS.updateExtension(msiExtension.name())
                    .withPublicSetting("port", newPort)
                    .parent();
        } else {
            Integer port;
            if (this.tokenPort != null) {
                port = this.tokenPort;
            } else {
                port = DEFAULT_TOKEN_PORT;
            }
            if (scaleSetImpl.isInCreateMode()) {
                VirtualMachineScaleSet.DefinitionStages.WithCreate creatableVMSS = scaleSetImpl;
                creatableVMSS.defineNewExtension(msiExtensionType)
                        .withPublisher(MSI_EXTENSION_PUBLISHER_NAME)
                        .withType(msiExtensionType)
                        .withVersion("1.0")
                        .withMinorVersionAutoUpgrade()
                        .withPublicSetting("port", port)
                        .attach();
            } else {
                VirtualMachineScaleSet.Update appliableVMSS = scaleSetImpl;
                appliableVMSS.defineNewExtension(msiExtensionType)
                        .withPublisher(MSI_EXTENSION_PUBLISHER_NAME)
                        .withType(msiExtensionType)
                        .withVersion("1.0")
                        .withMinorVersionAutoUpgrade()
                        .withPublicSetting("port", port)
                        .attach();
            }
        }
        this.tokenPort = null;
        this.scheduleMSIExtensionInstallation = false;
    }

    /**
     * Given the OS type, gets the Managed Service Identity extension type.
     *
     * @param osType the os type
     *
     * @return the extension type.
     */
    private String msiExtensionType(OperatingSystemTypes osType) {
        return osType == OperatingSystemTypes.LINUX ? LINUX_MSI_EXTENSION : WINDOWS_MSI_EXTENSION;
    }

    /**
     * Gets the Managed Service Identity extension from the given extensions.
     *
     * @param extensions the extensions
     * @param typeName the extension type
     * @return the MSI extension if exists, null otherwise
     */
    private VirtualMachineScaleSetExtension getMSIExtension(Map<String, VirtualMachineScaleSetExtension> extensions, String typeName) {
        for (VirtualMachineScaleSetExtension extension : extensions.values()) {
            if (extension.publisherName().equalsIgnoreCase(MSI_EXTENSION_PUBLISHER_NAME)) {
                if (extension.typeName().equalsIgnoreCase(typeName)) {
                    return extension;
                }
            }
        }
        return null;
    }

    /**
     * Given an object holding a numeric in Integer or String format, convert that to
     * Integer.
     *
     * @param obj the object
     * @return the integer value
     */
    private Integer objectToInteger(Object obj) {
        Integer result = null;
        if (obj != null) {
            if (obj instanceof Integer) {
                result = (Integer) obj;
            } else {
                result = Integer.valueOf((String) obj);
            }
        }
        return result;
    }

    /**
     * Initialize VMSS's identity property.
     *
     * @param identityType the identity type to set
     */
    private void initVMSSIdentity(ResourceIdentityType identityType) {
        if (!identityType.equals(ResourceIdentityType.USER_ASSIGNED)
                && !identityType.equals(ResourceIdentityType.SYSTEM_ASSIGNED)) {
            throw new IllegalArgumentException("Invalid argument: " + identityType);
        }

        final VirtualMachineScaleSetInner scaleSetInner = this.scaleSet.inner();
        if (scaleSetInner.identity() == null) {
            scaleSetInner.withIdentity(new VirtualMachineScaleSetIdentity());
        }
        if (scaleSetInner.identity().type() == null
                || scaleSetInner.identity().type().equals(ResourceIdentityType.NONE)
                || scaleSetInner.identity().type().equals(identityType)) {
            scaleSetInner.identity().withType(identityType);
        } else {
            scaleSetInner.identity().withType(ResourceIdentityType.SYSTEM_ASSIGNED_USER_ASSIGNED);
        }
        if (scaleSetInner.identity().identityIds() == null) {
            if (identityType.equals(ResourceIdentityType.USER_ASSIGNED)
                    || identityType.equals(ResourceIdentityType.SYSTEM_ASSIGNED_USER_ASSIGNED)) {
                scaleSetInner.identity().withIdentityIds(new ArrayList<String>());
            }
        }
    }
}