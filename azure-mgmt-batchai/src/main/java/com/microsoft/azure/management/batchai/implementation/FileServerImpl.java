/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.DataDisks;
import com.microsoft.azure.management.batchai.FileServer;
import com.microsoft.azure.management.batchai.SshConfiguration;
import com.microsoft.azure.management.batchai.StorageAccountType;
import com.microsoft.azure.management.batchai.UserAccountSettings;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import rx.Observable;

/**
 * Implementation for FileServer and its create and update interfaces.
 */
@LangDefinition
class FileServerImpl extends GroupableResourceImpl<
        FileServer,
        FileServerInner,
        FileServerImpl,
        BatchAIManager>
        implements
        FileServer,
        FileServer.Definition,
        FileServer.Update {
    private FileServerCreateParametersInner createParameters = new FileServerCreateParametersInner();

    FileServerImpl(String name, FileServerInner innerObject, BatchAIManager manager) {
        super(name, innerObject, manager);
    }

    @Override
    public Observable<FileServer> createResourceAsync() {
        createParameters.withLocation(this.regionName());
        createParameters.withTags(this.inner().getTags());
        return this.manager().inner().fileServers().createAsync(resourceGroupName(), name(), createParameters)
                .map(innerToFluentMap(this));
    }

    @Override
    public Observable<FileServer> updateResourceAsync() {
//        updateParameters.withTags(this.inner().getTags());
//        return this.manager().inner().clusters().updateAsync(resourceGroupName(), name(), updateParameters)
//                .map(innerToFluentMap(this));
        return null;
    }

    @Override
    protected Observable<FileServerInner> getInnerAsync() {
        return this.manager().inner().fileServers().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }


    @Override
    public FileServerImpl withVMSize(String vmSize) {
        createParameters.withVmSize(vmSize);
        return this;
    }

    @Override
    public FileServerImpl withUserName(String userName) {
        ensureUserAccountSettings().withAdminUserName(userName);
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
    public FileServerImpl withPassword(String password) {
        ensureUserAccountSettings().withAdminUserPassword(password);
        return this;
    }

    @Override
    public FileServerImpl withSshPublicKey(String sshPublicKey) {
        ensureUserAccountSettings().withAdminUserSshPublicKey(sshPublicKey);
        return this;
    }

    @Override
    public FileServer.DefinitionStages.WithVMSize withDataDisks(int diskSizeInGB, int diskCount, StorageAccountType storageAccountType) {
        ensureDataDisks().withDiskSizeInGB(diskSizeInGB)
                .withDiskCount(diskCount)
                .withStorageAccountType(storageAccountType);
        return this;
    }

    private DataDisks ensureDataDisks() {
        if (createParameters.dataDisks() == null) {
            createParameters.withDataDisks(new DataDisks());
        }
        return createParameters.dataDisks();
    }
}
