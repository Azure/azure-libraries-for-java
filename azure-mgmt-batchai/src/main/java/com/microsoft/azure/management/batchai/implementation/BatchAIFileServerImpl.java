/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIFileServer;
import com.microsoft.azure.management.batchai.BatchAIWorkspace;
import com.microsoft.azure.management.batchai.CachingType;
import com.microsoft.azure.management.batchai.DataDisks;
import com.microsoft.azure.management.batchai.FileServerCreateParameters;
import com.microsoft.azure.management.batchai.FileServerProvisioningState;
import com.microsoft.azure.management.batchai.MountSettings;
import com.microsoft.azure.management.batchai.ResourceId;
import com.microsoft.azure.management.batchai.SshConfiguration;
import com.microsoft.azure.management.batchai.StorageAccountType;
import com.microsoft.azure.management.batchai.UserAccountSettings;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import org.joda.time.DateTime;
import rx.Observable;

/**
 * Implementation for BatchAIFileServer and its create and update interfaces.
 */
@LangDefinition
class BatchAIFileServerImpl extends CreatableUpdatableImpl<
        BatchAIFileServer,
        FileServerInner,
        BatchAIFileServerImpl>
        implements
        BatchAIFileServer,
        BatchAIFileServer.Definition {
    private final BatchAIWorkspaceImpl workspace;
    private FileServerCreateParameters createParameters = new FileServerCreateParameters();

    BatchAIFileServerImpl(String name, BatchAIWorkspaceImpl workspace, FileServerInner innerObject) {
        super(name, innerObject);
        this.workspace = workspace;
    }

    @Override
    public boolean isInCreateMode() {
        return true;
    }

    @Override
    public Observable<BatchAIFileServer> createResourceAsync() {
        return this.manager().inner().fileServers().createAsync(workspace.resourceGroupName(), workspace.name(), name(), createParameters)
                .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<FileServerInner> getInnerAsync() {
        return this.manager().inner().fileServers().getAsync(workspace.resourceGroupName(), workspace.name(), this.name());
    }


    @Override
    public BatchAIFileServerImpl withVMSize(String vmSize) {
        createParameters.withVmSize(vmSize);
        return this;
    }

    @Override
    public BatchAIFileServerImpl withUserName(String userName) {
        ensureUserAccountSettings().withAdminUserName(userName);
        return this;
    }

    @Override
    public BatchAIFileServer.DefinitionStages.WithCreate withSubnet(String subnetId) {
        createParameters.withSubnet(new ResourceId().withId(subnetId));
        return this;
    }

    @Override
    public BatchAIFileServer.DefinitionStages.WithCreate withSubnet(String networkId, String subnetName) {
        createParameters.withSubnet(new ResourceId().withId(networkId + "/subnets/" + subnetName));
        return this;
    }

    private UserAccountSettings ensureUserAccountSettings() {
        if (ensureSshConfiguration().userAccountSettings() == null) {
            createParameters.sshConfiguration().withUserAccountSettings(new UserAccountSettings());
        }
        return createParameters.sshConfiguration().userAccountSettings();
    }

    private SshConfiguration ensureSshConfiguration() {
        if (createParameters.sshConfiguration() == null) {
            createParameters.withSshConfiguration(new SshConfiguration());
        }
        return createParameters.sshConfiguration();
    }

    @Override
    public BatchAIFileServerImpl withPassword(String password) {
        ensureUserAccountSettings().withAdminUserPassword(password);
        return this;
    }

    @Override
    public BatchAIFileServerImpl withSshPublicKey(String sshPublicKey) {
        ensureUserAccountSettings().withAdminUserSshPublicKey(sshPublicKey);
        return this;
    }

    @Override
    public BatchAIFileServer.DefinitionStages.WithVMSize withDataDisks(int diskSizeInGB, int diskCount, StorageAccountType storageAccountType) {
        ensureDataDisks().withDiskSizeInGB(diskSizeInGB)
                .withDiskCount(diskCount)
                .withStorageAccountType(storageAccountType);
        return this;
    }

    @Override
    public BatchAIFileServer.DefinitionStages.WithVMSize withDataDisks(int diskSizeInGB, int diskCount, StorageAccountType storageAccountType, CachingType cachingType) {
        ensureDataDisks().withDiskSizeInGB(diskSizeInGB)
                .withDiskCount(diskCount)
                .withStorageAccountType(storageAccountType)
                .withCachingType(cachingType);
        return this;
    }

    private DataDisks ensureDataDisks() {
        if (createParameters.dataDisks() == null) {
            createParameters.withDataDisks(new DataDisks());
        }
        return createParameters.dataDisks();
    }

    @Override
    public String vmSize() {
        return inner().vmSize();
    }

    @Override
    public SshConfiguration sshConfiguration() {
        return inner().sshConfiguration();
    }

    @Override
    public DataDisks dataDisks() {
        return inner().dataDisks();
    }

    @Override
    public ResourceId subnet() {
        return inner().subnet();
    }

    @Override
    public MountSettings mountSettings() {
        return inner().mountSettings();
    }

    @Override
    public DateTime provisioningStateTransitionTime() {
        return inner().provisioningStateTransitionTime();
    }

    @Override
    public DateTime creationTime() {
        return inner().creationTime();
    }

    @Override
    public FileServerProvisioningState provisioningState() {
        return inner().provisioningState();
    }

    @Override
    public BatchAIWorkspace workspace() {
        return workspace;
    }

    @Override
    public BatchAIManager manager() {
        return workspace.manager();
    }

    @Override
    public String id() {
        return inner().id();
    }
}
