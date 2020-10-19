/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.compute.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.compute.DataDisk;
import com.microsoft.azure.management.compute.HardwareProfile;
import com.microsoft.azure.management.compute.NetworkInterfaceReference;
import com.microsoft.azure.management.compute.NetworkProfile;
import com.microsoft.azure.management.compute.OSDisk;
import com.microsoft.azure.management.compute.OSProfile;
import com.microsoft.azure.management.compute.RunCommandInput;
import com.microsoft.azure.management.compute.RunCommandInputParameter;
import com.microsoft.azure.management.compute.RunCommandResult;
import com.microsoft.azure.management.compute.StorageProfile;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.compute.VirtualMachineCaptureParameters;
import com.microsoft.azure.management.compute.VirtualMachineSizes;
import com.microsoft.azure.management.compute.VirtualMachines;
import com.microsoft.azure.management.graphrbac.implementation.GraphRbacManager;
import com.microsoft.azure.management.network.implementation.NetworkManager;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;
import com.microsoft.azure.management.storage.implementation.StorageManager;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;
import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation for VirtualMachines.
 */
@LangDefinition
class VirtualMachinesImpl
        extends TopLevelModifiableResourcesImpl<
        VirtualMachine,
        VirtualMachineImpl,
        VirtualMachineInner,
        VirtualMachinesInner,
        ComputeManager>
        implements VirtualMachines {
    private final StorageManager storageManager;
    private final NetworkManager networkManager;
    private final GraphRbacManager rbacManager;
    private final VirtualMachineSizesImpl vmSizes;

    VirtualMachinesImpl(ComputeManager computeManager,
                        StorageManager storageManager,
                        NetworkManager networkManager,
                        GraphRbacManager rbacManager) {
        super(computeManager.inner().virtualMachines(), computeManager);
        this.storageManager = storageManager;
        this.networkManager = networkManager;
        this.rbacManager = rbacManager;
        this.vmSizes = new VirtualMachineSizesImpl(computeManager.inner().virtualMachineSizes());
    }

    // Actions

    @Override
    public VirtualMachine.DefinitionStages.Blank define(String name) {
        return wrapModel(name);
    }

    @Override
    public void deallocate(String groupName, String name) {
        this.inner().deallocate(groupName, name);
    }

    @Override
    public Completable deallocateAsync(String groupName, String name) {
        return this.inner().deallocateAsync(groupName, name).toCompletable();
    }

    @Override
    public ServiceFuture<Void> deallocateAsync(String groupName, String name, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(deallocateAsync(groupName, name), callback);
    }

    @Override
    public void generalize(String groupName, String name) {
        this.inner().generalize(groupName, name);
    }

    @Override
    public Completable generalizeAsync(String groupName, String name) {
        return this.inner().generalizeAsync(groupName, name).toCompletable();
    }

    @Override
    public ServiceFuture<Void> generalizeAsync(String groupName, String name, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(generalizeAsync(groupName, name), callback);
    }

    @Override
    public void powerOff(String groupName, String name) {
        this.inner().powerOff(groupName, name);
    }

    @Override
    public Completable powerOffAsync(String groupName, String name) {
        return this.inner().powerOffAsync(groupName, name).toCompletable();
    }

    @Override
    public ServiceFuture<Void> powerOffAsync(String groupName, String name, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(powerOffAsync(groupName, name), callback);
    }

    @Override
    public void restart(String groupName, String name) {
        this.inner().restart(groupName, name);
    }

    @Override
    public Completable restartAsync(String groupName, String name) {
        return this.inner().restartAsync(groupName, name).toCompletable();
    }

    @Override
    public ServiceFuture<Void> restartAsync(String groupName, String name, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(restartAsync(groupName, name), callback);
    }

    @Override
    public void start(String groupName, String name) {
        this.inner().start(groupName, name);
    }

    @Override
    public Completable startAsync(String groupName, String name) {
        return this.inner().startAsync(groupName, name).toCompletable();
    }

    @Override
    public ServiceFuture<Void> startAsync(String groupName, String name, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(startAsync(groupName, name), callback);
    }

    @Override
    public void redeploy(String groupName, String name) {
        this.inner().redeploy(groupName, name);
    }

    @Override
    public Completable redeployAsync(String groupName, String name) {
        return this.inner().redeployAsync(groupName, name).toCompletable();
    }

    @Override
    public ServiceFuture<Void> redeployAsync(String groupName, String name, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(redeployAsync(groupName, name), callback);
    }

    @Override
    public String capture(String groupName, String name,
                          String containerName,
                          String vhdPrefix,
                          boolean overwriteVhd) {
        return this.captureAsync(groupName, name, containerName, vhdPrefix, overwriteVhd).toBlocking().last();
    }

    @Override
    public Observable<String> captureAsync(String groupName, String name, String containerName, String vhdPrefix, boolean overwriteVhd) {
        VirtualMachineCaptureParameters parameters = new VirtualMachineCaptureParameters();
        parameters.withDestinationContainerName(containerName);
        parameters.withOverwriteVhds(overwriteVhd);
        parameters.withVhdPrefix(vhdPrefix);
        return this.inner().captureAsync(groupName, name, parameters)
                .map(new Func1<VirtualMachineCaptureResultInner, String>() {
                    @Override
                    public String call(VirtualMachineCaptureResultInner innerResult) {
                        if (innerResult == null) {
                            return null;
                        }
                        ObjectMapper mapper = new ObjectMapper();
                        //Object to JSON string
                        try {
                            return mapper.writeValueAsString(innerResult);
                        } catch (JsonProcessingException e) {
                            throw Exceptions.propagate(e);
                        }
                    }
                });
    }

    @Override
    public ServiceFuture<String> captureAsync(String groupName, String name, String containerName, String vhdPrefix, boolean overwriteVhd, ServiceCallback<String> callback) {
        return ServiceFuture.fromBody(captureAsync(groupName, name, containerName, vhdPrefix, overwriteVhd), callback);
    }

    @Override
    public void migrateToManaged(String groupName, String name) {
        this.inner().convertToManagedDisks(groupName, name);
    }

    @Override
    public Completable migrateToManagedAsync(String groupName, String name) {
       return this.inner().convertToManagedDisksAsync(groupName, name).toCompletable();
    }

    @Override
    public ServiceFuture<Void> migrateToManagedAsync(String groupName, String name, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(migrateToManagedAsync(groupName, name), callback);
    }

    @Override
    public RunCommandResult runPowerShellScript(String groupName, String name, List<String> scriptLines, List<RunCommandInputParameter> scriptParameters) {
        return this.runPowerShellScriptAsync(groupName, name, scriptLines, scriptParameters).toBlocking().last();
    }

    @Override
    public Observable<RunCommandResult> runPowerShellScriptAsync(String groupName, String name, List<String> scriptLines, List<RunCommandInputParameter> scriptParameters) {
        RunCommandInput inputCommand = new RunCommandInput();
        inputCommand.withCommandId("RunPowerShellScript");
        inputCommand.withScript(scriptLines);
        inputCommand.withParameters(scriptParameters);
        return this.runCommandAsync(groupName, name, inputCommand);
    }

    @Override
    public RunCommandResult runShellScript(String groupName, String name, List<String> scriptLines, List<RunCommandInputParameter> scriptParameters) {
        return this.runShellScriptAsync(groupName, name, scriptLines, scriptParameters).toBlocking().last();
    }

    @Override
    public Observable<RunCommandResult> runShellScriptAsync(String groupName, String name, List<String> scriptLines, List<RunCommandInputParameter> scriptParameters) {
        RunCommandInput inputCommand = new RunCommandInput();
        inputCommand.withCommandId("RunShellScript");
        inputCommand.withScript(scriptLines);
        inputCommand.withParameters(scriptParameters);
        return this.runCommandAsync(groupName, name, inputCommand);
    }

    @Override
    public RunCommandResult runCommand(String groupName, String name, RunCommandInput inputCommand) {
        return this.runCommandAsync(groupName, name, inputCommand).toBlocking().last();
    }

    @Override
    public Observable<RunCommandResult> runCommandAsync(String groupName, String name, RunCommandInput inputCommand) {
        return this.inner().runCommandAsync(groupName, name, inputCommand).map(
                new Func1<RunCommandResultInner, RunCommandResult>() {

                    @Override
                    public RunCommandResult call(RunCommandResultInner runCommandResultInner) {
                        return new RunCommandResultImpl(runCommandResultInner);
                    }
                }
        );
    }

    @Override
    public void forceDeleteById(String id) {
        forceDeleteByResourceGroup(ResourceUtils.groupFromResourceId(id), ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public ServiceFuture<Void> forceDeleteByIdAsync(String id, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(forceDeleteByIdAsync(id), callback);
    }

    @Override
    public Completable forceDeleteByIdAsync(String id) {
        return forceDeleteByResourceGroupAsync(
                ResourceUtils.groupFromResourceId(id), ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public void forceDeleteByResourceGroup(String resourceGroupName, String name) {
        this.inner().delete(resourceGroupName, name, true);
    }

    @Override
    public ServiceFuture<Void> forceDeleteByResourceGroupAsync(String resourceGroupName, String name, ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(deleteByResourceGroupAsync(resourceGroupName, name), callback);
    }

    @Override
    public Completable forceDeleteByResourceGroupAsync(String resourceGroupName, String name) {
        return this.inner().deleteAsync(resourceGroupName, name, true).toCompletable();
    }

    // Getters
    @Override
    public VirtualMachineSizes sizes() {
        return this.vmSizes;
    }


    // Helper methods

    @Override
    protected VirtualMachineImpl wrapModel(String name) {
        VirtualMachineInner inner = new VirtualMachineInner();
        inner.withStorageProfile(new StorageProfile()
                .withOsDisk(new OSDisk())
                .withDataDisks(new ArrayList<DataDisk>()));
        inner.withOsProfile(new OSProfile());
        inner.withHardwareProfile(new HardwareProfile());
        inner.withNetworkProfile(new NetworkProfile()
                .withNetworkInterfaces(new ArrayList<NetworkInterfaceReference>()));
        return new VirtualMachineImpl(name,
                inner,
                this.manager(),
                this.storageManager,
                this.networkManager,
                this.rbacManager);
    }

    @Override
    protected VirtualMachineImpl wrapModel(VirtualMachineInner virtualMachineInner) {
        if (virtualMachineInner == null) {
            return null;
        }
        return new VirtualMachineImpl(virtualMachineInner.name(),
                virtualMachineInner,
                this.manager(),
                this.storageManager,
                this.networkManager,
                this.rbacManager);
    }
}