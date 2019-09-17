/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.batch.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batch.BatchAccount;
import com.microsoft.azure.management.batch.Pool;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ExternalChildResourcesCachedImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Represents a pool collection associated with a batch account.
 */
@LangDefinition
public class PoolsImpl extends
        ExternalChildResourcesCachedImpl<PoolImpl,
                Pool,
                PoolInner,
                BatchAccountImpl,
                BatchAccount> {

    PoolsImpl(BatchAccountImpl parent) {
        super(parent, parent.taskGroup(), "Pool");
        this.cacheCollection();
    }

    /**
     * Define a new pool.
     *
     * @param name pool name value
     */
    public PoolImpl define(String name) {
        return this.prepareInlineDefine(name);
    }

    /**
     * Update the pool.
     *
     * @param name pool name value
     */
    public PoolImpl update(String name) {
        return this.prepareInlineUpdate(name);
    }

    /**
     * Remove the pool.
     *
     * @param name pool name value
     */
    public void remove(String name) {
        this.prepareInlineRemove(name);
    }

    @Override
    protected List<PoolImpl> listChildResources() {
        List<PoolImpl> childResources = new ArrayList<>();
        if (this.parent().inner().id() == null || this.parent().autoStorage() == null) {
            return childResources;
        }

        PagedList<PoolInner> poolList = this.parent().manager().inner().pools().listByBatchAccount(
                this.parent().resourceGroupName(),
                this.parent().name()
        );

        for (PoolInner pool: poolList) {
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

    /**
     * Add a new pool.
     *
     * @param pool the pool to add
     */
    public void addPool(PoolImpl pool) {
        this.addChildResource(pool);
    }

    /**
     * Return the map of pool.
     */
    public Map<String, Pool> asMap() {
        Map<String, Pool> result = new HashMap<>();
        for (Map.Entry<String, PoolImpl> entry: this.collection().entrySet()) {
            PoolImpl pool = entry.getValue();
            result.put(entry.getKey(), pool);
        }
        return  Collections.unmodifiableMap(result);
    }
}
