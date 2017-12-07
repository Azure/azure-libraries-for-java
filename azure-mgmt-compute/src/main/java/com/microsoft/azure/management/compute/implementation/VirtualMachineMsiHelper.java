/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.compute.OperatingSystemTypes;
import com.microsoft.azure.management.compute.ResourceIdentityType;
import com.microsoft.azure.management.compute.VirtualMachineExtension;
import com.microsoft.azure.management.compute.VirtualMachineIdentity;
import com.microsoft.azure.management.graphrbac.implementation.GraphRbacManager;
import com.microsoft.azure.management.graphrbac.implementation.RoleAssignmentHelper;
import com.microsoft.azure.management.resources.fluentcore.dag.IndexableTaskItem;
import com.microsoft.azure.management.resources.fluentcore.dag.TaskGroup;
import com.microsoft.azure.management.resources.fluentcore.dag.VoidIndexable;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to set Managed Service Identity (MSI) property on a virtual machine,
 * install or update MSI extension and create role assignments for the service principal
 * associated with the virtual machine.
 */
@LangDefinition
class VirtualMachineMsiHelper extends RoleAssignmentHelper {
    private final VirtualMachineImpl virtualMachine;

    private MSIExtensionInstaller msiExtensionInstaller;

    /**
     * Creates VirtualMachineMsiHelper.
     *
     * @param rbacManager the graph rbac manager
     * @param virtualMachine the virtual machine to which MSI extension needs to be installed and
     *                       for which role assignments needs to be created
     */
    VirtualMachineMsiHelper(final GraphRbacManager rbacManager,
                            VirtualMachineImpl virtualMachine) {
        super(rbacManager, virtualMachine.taskGroup(), virtualMachine.idProvider());
        this.virtualMachine = virtualMachine;
        this.msiExtensionInstaller = null;
        clear();
    }

    /**
     * Specifies that Managed Service Identity property needs to be set in the virtual machine.
     *
     * If MSI extension is already installed then the access token will be available in the virtual machine
     * at port specified in the extension public setting, otherwise the port for new extension will be 50342.
     *
     * @return VirtualMachineMsiHelper
     */
     VirtualMachineMsiHelper withLocalManagedServiceIdentity() {
        return withLocalManagedServiceIdentity(null);
    }

    /**
     * Specifies that Managed Service Identity property needs to be set in the virtual machine.
     *
     * The access token will be available in the virtual machine at given port.
     *
     * @param port the port in the virtual machine to get the access token from

     * @return VirtualMachineMsiHelper
     */
    VirtualMachineMsiHelper withLocalManagedServiceIdentity(Integer port) {
        if (this.msiExtensionInstaller != null) {
            return this;
        } else {
            VirtualMachineInner virtualMachineInner = this.virtualMachine.inner();
            if (virtualMachineInner.identity() == null) {
                virtualMachineInner.withIdentity(new VirtualMachineIdentity());
            }
            if (virtualMachineInner.identity().type() == null) {
                virtualMachineInner.identity().withType(ResourceIdentityType.SYSTEM_ASSIGNED);
            }
            this.msiExtensionInstaller = new MSIExtensionInstaller(this.virtualMachine);
            this.msiExtensionInstaller.withTokenPort(port);
            this.virtualMachine.taskGroup().addPostRunDependent(this.msiExtensionInstaller);
            return this;
        }
    }

    /**
     * Clear VirtualMachineMsiHelper internal state.
     */
    public  void clear() {
        if (this.msiExtensionInstaller != null) {
            this.msiExtensionInstaller.clear();
            this.msiExtensionInstaller = null;
        }
    }

    /**
     * The TaskItem in the graph that configure MSI extension in a virtual machine.
     */
    private static class MSIExtensionInstaller extends IndexableTaskItem {
        private static final int DEFAULT_TOKEN_PORT = 50342;
        private static final String MSI_EXTENSION_PUBLISHER_NAME = "Microsoft.ManagedIdentity";
        private static final String LINUX_MSI_EXTENSION = "ManagedIdentityExtensionForLinux";
        private static final String WINDOWS_MSI_EXTENSION = "ManagedIdentityExtensionForWindows";
        private final String msiExtensionTypeName;

        private Integer tokenPort;
        private final VirtualMachineImpl virtualMachine;

        /**
         * Creates MSIExtensionInstaller.
         *
         * @param virtualMachine the virtual machine for which MSI extension need to be configured
         */
        MSIExtensionInstaller(final VirtualMachineImpl virtualMachine) {
            this.tokenPort = null;
            this.virtualMachine = virtualMachine;
            OperatingSystemTypes osType = virtualMachine.osType();
            if (osType == null) {
                throw new IllegalStateException("MSIExtensionInstaller: Unable to resolve the operating system type");
            }
            this.msiExtensionTypeName = osType == OperatingSystemTypes.LINUX ? LINUX_MSI_EXTENSION : WINDOWS_MSI_EXTENSION;
        }

        MSIExtensionInstaller withTokenPort(Integer tokenPort) {
            this.tokenPort = tokenPort;
            return this;
        }

        @Override
        public Observable<Indexable> invokeTaskAsync(TaskGroup.InvocationContext context) {
            return Observable.defer(new Func0<Observable<Indexable>>() {
                @Override
                public Observable<Indexable> call() {
                    return getMSIExtensionAsync();
                }
            }).flatMap(new Func1<Indexable, Observable<Indexable>>() {
                @Override
                public Observable<Indexable> call(Indexable extension) {
                    return updateMSIExtensionAsync((VirtualMachineExtension) extension);
                }
            })
            .switchIfEmpty(Observable.defer(new Func0<Observable<Indexable>>() {
                @Override
                public Observable<Indexable> call() {
                    return installMSIExtensionAsync();
                }
            }));
        }

        private Observable<Indexable> getMSIExtensionAsync() {
            return virtualMachine.manager().inner().virtualMachineExtensions().getAsync(virtualMachine.resourceGroupName(),
                    virtualMachine.name(),
                    this.msiExtensionTypeName)
                    .map(new Func1<VirtualMachineExtensionInner, Indexable>() {
                        @Override
                        public Indexable call(VirtualMachineExtensionInner inner) {
                            if (inner == null) {
                                return voidIndexable();
                            } else {
                                return new VirtualMachineExtensionImpl(msiExtensionTypeName,
                                        virtualMachine,
                                        inner,
                                        virtualMachine.manager().inner().virtualMachineExtensions());
                            }
                        }
                    })
                    .filter(new Func1<Indexable, Boolean>() {
                        @Override
                        public Boolean call(Indexable e) {
                            if (e instanceof VoidIndexable) {
                                return false;
                            }
                            VirtualMachineExtension extension = (VirtualMachineExtension) e;
                            return extension.publisherName().equalsIgnoreCase(MSI_EXTENSION_PUBLISHER_NAME)
                                    && extension.typeName().equalsIgnoreCase(msiExtensionTypeName);
                        }
                    });
        }

        private Observable<Indexable> updateMSIExtensionAsync(VirtualMachineExtension extension) {
            Integer currentTokenPort = objectToInteger(extension.publicSettings().get("port"));
            Integer tokenPortToUse;
            if (tokenPort != null) {
                // User specified a port
                tokenPortToUse = tokenPort;
            } else if (currentTokenPort == null) {
                // User didn't specify a port and port is not already set
                tokenPortToUse = DEFAULT_TOKEN_PORT;
            } else {
                // User didn't specify a port and port is already set in the extension
                // No need to do a PUT on extension
                //
                return voidObservable();
            }
            Map<String, Object> settings = new HashMap<>();
            settings.put("port", tokenPortToUse);
            extension.inner().withSettings(settings);

            return virtualMachine.manager().inner().virtualMachineExtensions()
                    .createOrUpdateAsync(virtualMachine.resourceGroupName(),
                            virtualMachine.name(),
                            msiExtensionTypeName,
                            extension.inner())
                    .map(new Func1<VirtualMachineExtensionInner, Indexable>() {
                        @Override
                        public Indexable call(VirtualMachineExtensionInner inner) {
                            return new VirtualMachineExtensionImpl(msiExtensionTypeName,
                                    virtualMachine,
                                    inner,
                                    virtualMachine.manager().inner().virtualMachineExtensions());
                        }
                    });
        }

        private Observable<Indexable> installMSIExtensionAsync() {
            Integer tokenPortToUse = tokenPort != null ? tokenPort : DEFAULT_TOKEN_PORT;
            VirtualMachineExtensionInner extensionParameter = new VirtualMachineExtensionInner();
            extensionParameter
                    .withPublisher(MSI_EXTENSION_PUBLISHER_NAME)
                    .withVirtualMachineExtensionType(this.msiExtensionTypeName)
                    .withTypeHandlerVersion("1.0")
                    .withAutoUpgradeMinorVersion(true)
                    .withLocation(virtualMachine.regionName());
            Map<String, Object> settings = new HashMap<>();
            settings.put("port", tokenPortToUse);
            extensionParameter.withSettings(settings);
            extensionParameter.withProtectedSettings(null);

            return virtualMachine.manager().inner().virtualMachineExtensions()
                    .createOrUpdateAsync(virtualMachine.resourceGroupName(),
                            virtualMachine.name(),
                            this.msiExtensionTypeName,
                            extensionParameter)
                    .map(new Func1<VirtualMachineExtensionInner, Indexable>() {
                        @Override
                        public Indexable call(VirtualMachineExtensionInner inner) {
                            return new VirtualMachineExtensionImpl(msiExtensionTypeName,
                                    virtualMachine,
                                    inner,
                                    virtualMachine.manager().inner().virtualMachineExtensions());
                        }
                    });
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
    }
}