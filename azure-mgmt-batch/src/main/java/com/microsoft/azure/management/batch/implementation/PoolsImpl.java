/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.batch.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.batch.BatchAccount;
import com.microsoft.azure.management.batch.Pool;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ExternalChildResourcesCachedImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a pool collection associated with a batch account.
 */
public class PoolsImpl extends
        ExternalChildResourcesCachedImpl<PoolImpl,
                Pool,
                PoolInner,
                BatchAccountImpl,
                BatchAccount> {

    PoolsImpl(BatchAccountImpl parent){
        super(parent, parent.taskGroup(), "Pool");
        this.cacheCollection();
    }

    public PoolImpl define(String name) {
        return this.prepareInlineDefine(name);
    }

    public PoolImpl update(String name) {
        return this.prepareInlineUpdate(name);
    }

    public void remove(String name) {
        this.prepareInlineRemove(name);
    }

    @Override
    protected List<PoolImpl> listChildResources() {
        List<PoolImpl> childResources = new ArrayList<>();
        if(this.parent().inner().id() == null || this.parent().autoStorage() == null){
            return childResources;
        }

        PagedList<PoolInner> poolList = this.parent().manager().inner().pools().listByBatchAccount(
                this.parent().resourceGroupName(),
                this.parent().name()
        );

        for(PoolInner pool: poolList){
            childResources.add(new PoolImpl(
                    pool.name(),
                    this.parent(),
                    pool
            ));
        }

        return childResources;
    }

    @Override
    protected PoolImpl newChildResource(String name) {
        PoolImpl pool = PoolImpl.newPool(name, this.parent());
        return pool;
    }

    public void addPool(PoolImpl pool) {
        this.addChildResource(pool);
    }
}
