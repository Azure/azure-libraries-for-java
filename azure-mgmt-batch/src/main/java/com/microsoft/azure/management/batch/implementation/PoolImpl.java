/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.batch.implementation;

import com.microsoft.azure.management.batch.BatchAccount;
import com.microsoft.azure.management.batch.MountConfiguration;
import com.microsoft.azure.management.batch.NetworkConfiguration;
import com.microsoft.azure.management.batch.Pool;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
import rx.Observable;
import rx.functions.Func1;

import java.util.List;

/**
 * Implementation for BatchAccount Pool and its parent interfaces.
 */
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
    public BatchAccountImpl attach() {
        return this.parent().withPool(this);
    }

}
