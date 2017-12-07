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
import com.microsoft.azure.management.resources.fluentcore.dag.TaskGroup;

import java.util.Map;

/**
 * Utility class to set Managed Service Identity (MSI) and MSI related resources for a virtual machine scale set.
 */
@LangDefinition
class VirtualMachineScaleSetMsiHelper extends RoleAssignmentHelper {
    private static final int DEFAULT_TOKEN_PORT = 50342;
    private static final String MSI_EXTENSION_PUBLISHER_NAME = "Microsoft.ManagedIdentity";
    private static final String LINUX_MSI_EXTENSION = "ManagedIdentityExtensionForLinux";
    private static final String WINDOWS_MSI_EXTENSION = "ManagedIdentityExtensionForWindows";

    private Integer tokenPort;
    private boolean requireSetup;

    /**
     * Creates VirtualMachineScaleSetMsiHelper.
     *
     * @param rbacManager the graph rbac manager
     */
    VirtualMachineScaleSetMsiHelper(GraphRbacManager rbacManager, TaskGroup taskGroup, IdProvider idProvider) {
        super(rbacManager, taskGroup, idProvider);
        this.clear();
    }

    /**
     * Specifies that Managed Service Identity property needs to be set in the virtual machine scale set.
     *
     * If MSI extension is already installed then the access token will be available in the virtual machine
     * scale set instance at port specified in the extension public setting, otherwise the port for
     * new extension will be 50342.
     *
     * @param scaleSetInner the virtual machine scale set to set the identity
     * @return VirtualMachineScaleSetMsiHelper
     */
    VirtualMachineScaleSetMsiHelper withLocalManagedServiceIdentity(VirtualMachineScaleSetInner scaleSetInner) {
        return withLocalManagedServiceIdentity(null, scaleSetInner);
    }

    /**
     * Specifies that Managed Service Identity property needs to be set in the virtual machine scale set.
     *
     * The access token will be available in the virtual machine at given port.
     *
     * @param port the port in the virtual machine scale set instance to get the access token from
     * @param scaleSetInner the virtual machine scale set to set the identity
     * @return VirtualMachineScaleSetMsiHelper
     */
    VirtualMachineScaleSetMsiHelper withLocalManagedServiceIdentity(Integer port, VirtualMachineScaleSetInner scaleSetInner) {
        this.requireSetup = true;
        this.tokenPort = port;
        if (scaleSetInner.identity() == null) {
            scaleSetInner.withIdentity(new VirtualMachineScaleSetIdentity());
        }
        if (scaleSetInner.identity().type() == null) {
            scaleSetInner.identity().withType(ResourceIdentityType.SYSTEM_ASSIGNED);
        }
        return this;
    }

    /**
     * Add or update the Managed Service Identity extension for the given virtual machine scale set.
     *
     * @param scaleSetImpl the scale set
     */
    void addOrUpdateMSIExtension(VirtualMachineScaleSetImpl scaleSetImpl) {
        if (!requireSetup) {
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
        this.clear();
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
     * Clear internal properties.
     */
    private  void clear() {
        this.requireSetup = false;
        this.tokenPort = null;
    }
}
