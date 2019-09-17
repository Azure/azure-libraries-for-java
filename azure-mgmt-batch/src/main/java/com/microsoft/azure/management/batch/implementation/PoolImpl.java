/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.batch.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batch.*;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
import rx.Observable;
import rx.functions.Func1;

import java.util.List;

/**
 * Implementation for BatchAccount Pool and its parent interfaces.
 */
@LangDefinition
public class PoolImpl
        extends ExternalChildResourceImpl<Pool,
        PoolInner,
        BatchAccountImpl,
        BatchAccount>
        implements  Pool,
        Pool.Definition<BatchAccount.DefinitionStages.WithPool>,
        Pool.UpdateDefinition<BatchAccount.Update>,
        Pool.Update {

    protected PoolImpl(
            String name,
            BatchAccountImpl batchAccount,
            PoolInner inner){
        super(name, batchAccount, inner);
    }

    protected static PoolImpl newPool(
            String name,
            BatchAccountImpl parent) {
        PoolInner inner = new PoolInner();
        inner.withDisplayName(name);
        PoolImpl poolImpl = new PoolImpl(name, parent, inner);
        return poolImpl;
    }

    @Override
    public Observable<Pool> createResourceAsync() {
        final PoolImpl self = this;

        return this.parent().manager().inner().pools().createAsync(
                this.parent().resourceGroupName(),
                this.parent().name(),
                this.name(), this.inner()).
                map(new Func1<PoolInner, Pool>() {
                    @Override
                    public Pool call(PoolInner inner) {
                        self.setInner(inner);
                        return self;
                    }
                });
    }

    @Override
    public Observable<Pool> updateResourceAsync() {
        final PoolImpl self = this;

        return this.parent().manager().inner().pools().createAsync(
                this.parent().resourceGroupName(),
                this.parent().name(),
                this.name(), this.inner()).
                map(new Func1<PoolInner, Pool>() {
                    @Override
                    public Pool call(PoolInner inner) {
                        self.setInner(inner);
                        return self;
                    }
                });
    }

    @Override
    public Observable<Void> deleteResourceAsync() {
        return this.parent().manager().inner().pools().deleteAsync(
                this.parent().resourceGroupName(),
                this.parent().name(),
                this.name()
        );
    }

    @Override
    public Observable<Pool> refreshAsync() {
        return super.refreshAsync().map(new Func1<Pool, Pool>() {
            @Override
            public Pool call(Pool pool) {
                PoolImpl impl = (PoolImpl) pool;
                return impl;
            }
        });
    }

    @Override
    protected Observable<PoolInner> getInnerAsync() {
        return this.parent().manager().inner().pools().getAsync(
                this.parent().resourceGroupName(),
                this.parent().name(),
                this.inner().name()
        );
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public NetworkConfiguration networkConfiguration() {
        return this.inner().networkConfiguration();
    }

    @Override
    public List<MountConfiguration> mountConfiguration() {
        return this.inner().mountConfiguration();
    }

    @Override
    public ScaleSettings scaleSettings() {
        return this.inner().scaleSettings();
    }

    @Override
    public StartTask startTask() {
        return this.inner().startTask();
    }

    @Override
    public List<MetadataItem> metadata() {
        return this.inner().metadata();
    }

    @Override
    public List<ApplicationPackageReference> applicationPackages() {
        return this.inner().applicationPackages();
    }

    @Override
    public List<CertificateReference> certificates() {
        return this.inner().certificates();
    }

    @Override
    public String vmSize() {
        return this.inner().vmSize();
    }

    @Override
    public DeploymentConfiguration deploymentConfiguration() {
        return this.inner().deploymentConfiguration();
    }

    @Override
    public String displayName() {
        return this.inner().displayName();
    }

    @Override
    public InterNodeCommunicationState interNodeCommunication() {
        return this.inner().interNodeCommunication();
    }

    @Override
    public Integer maxTasksPerNode() {
        return this.inner().maxTasksPerNode();
    }

    @Override
    public TaskSchedulingPolicy taskSchedulingPolicy() {
        return this.inner().taskSchedulingPolicy();
    }

    @Override
    public List<UserAccount> userAccounts() {
        return this.inner().userAccounts();
    }

    @Override
    public List<String> applicationLicenses() {
        return this.inner().applicationLicenses();
    }

    @Override
    public PoolImpl withNetworkConfiguration(NetworkConfiguration networkConfiguration) {
        this.inner().withNetworkConfiguration(networkConfiguration);
        return this;
    }

    @Override
    public PoolImpl withMountConfiguration(List<MountConfiguration> mountConfigurations) {
        this.inner().withMountConfiguration(mountConfigurations);
        return this;
    }

    @Override
    public PoolImpl withScaleSettings(ScaleSettings scaleSettings) {
        this.inner().withScaleSettings(scaleSettings);
        return this;
    }

    @Override
    public PoolImpl withStartTask(StartTask startTask) {
        this.inner().withStartTask(startTask);
        return this;
    }

    @Override
    public PoolImpl withMetadata(List<MetadataItem> metadata) {
        this.inner().withMetadata(metadata);
        return this;
    }

    @Override
    public PoolImpl withApplicationPackages(List<ApplicationPackageReference> applicationPackages) {
        this.inner().withApplicationPackages(applicationPackages);
        return this;
    }

    @Override
    public PoolImpl withCertificates(List<CertificateReference> certificates) {
        this.inner().withCertificates(certificates);
        return this;
    }

    @Override
    public PoolImpl withVmSize(String vmSize) {
        this.inner().withVmSize(vmSize);
        return this;
    }

    @Override
    public PoolImpl withDeploymentConfiguration(DeploymentConfiguration deploymentConfiguration) {
        this.inner().withDeploymentConfiguration(deploymentConfiguration);
        return this;
    }

    @Override
    public PoolImpl withDisplayName(String displayName) {
        this.inner().withDisplayName(displayName);
        return this;
    }

    @Override
    public PoolImpl withInterNodeCommunication(InterNodeCommunicationState interNodeCommunication) {
        this.inner().withInterNodeCommunication(interNodeCommunication);
        return this;
    }

    @Override
    public PoolImpl withMaxTasksPerNode(Integer maxTasksPerNode) {
        this.inner().withMaxTasksPerNode(maxTasksPerNode);
        return this;
    }

    @Override
    public PoolImpl withTaskSchedulingPolicy(TaskSchedulingPolicy taskSchedulingPolicy) {
        this.inner().withTaskSchedulingPolicy(taskSchedulingPolicy);
        return this;
    }

    @Override
    public PoolImpl withUserAccounts(List<UserAccount> userAccounts) {
        this.inner().withUserAccounts(userAccounts);
        return this;
    }

    @Override
    public PoolImpl withApplicationLicenses(List<String> applicationLicenses) {
        this.inner().withApplicationLicenses(applicationLicenses);
        return this;
    }

    @Override
    public BatchAccountImpl attach() {
        return this.parent().withPool(this);
    }

}
