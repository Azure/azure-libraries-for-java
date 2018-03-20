/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIFileServer;
import com.microsoft.azure.management.batchai.CachingType;
import com.microsoft.azure.management.batchai.DataDisks;
import com.microsoft.azure.management.batchai.FileServerProvisioningState;
import com.microsoft.azure.management.batchai.MountSettings;
import com.microsoft.azure.management.batchai.ResourceId;
import com.microsoft.azure.management.batchai.SshConfiguration;
import com.microsoft.azure.management.batchai.StorageAccountType;
import com.microsoft.azure.management.batchai.UserAccountSettings;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import org.joda.time.DateTime;
import rx.Observable;

/**
 * Implementation for BatchAIFileServer and its create and update interfaces.
 */
@LangDefinition
class BatchAIFileServerImpl extends GroupableResourceImpl<
        BatchAIFileServer,
        FileServerInner,
        BatchAIFileServerImpl,
        BatchAIManager>
        implements
        BatchAIFileServer,
        BatchAIFileServer.Definition {
    private FileServerCreateParametersInner createParameters = new FileServerCreateParametersInner();

    BatchAIFileServerImpl(String name, FileServerInner innerObject, BatchAIManager manager) {
        super(name, innerObject, manager);
    }

    @Override
    public Observable<BatchAIFileServer> createResourceAsync() {
        createParameters.withLocation(this.regionName());
        createParameters.withTags(this.inner().getTags());
        return this.manager().inner().fileServers().createAsync(resourceGroupName(), name(), createParameters)
                .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<FileServerInner> getInnerAsync() {
        return this.manager().inner().fileServers().getByResourceGroupAsync(this.resourceGroupName(), this.name());
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
}
