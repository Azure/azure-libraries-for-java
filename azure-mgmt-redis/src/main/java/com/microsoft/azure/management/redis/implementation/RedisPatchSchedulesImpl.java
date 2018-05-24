/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.redis.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.redis.RedisCache;
import com.microsoft.azure.management.redis.RedisPatchSchedule;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ExternalChildResourcesCachedImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Redis patch schedule collection associated with a Redis cache instance.
 */
@LangDefinition
class RedisPatchSchedulesImpl extends
        ExternalChildResourcesCachedImpl<RedisPatchScheduleImpl,
                RedisPatchSchedule,
                RedisPatchScheduleInner,
                RedisCacheImpl,
                RedisCache> {

    RedisPatchSchedulesImpl(RedisCacheImpl parent) {
        super(parent, parent.taskGroup(), "PatchSchedule");
        if (parent.id() != null) {
            this.cacheCollection();
        }
    }

    Map<String, RedisPatchSchedule> patchSchedulesAsMap() {
        Map<String, RedisPatchSchedule> result = new HashMap<>();
        for (Map.Entry<String, RedisPatchScheduleImpl> entry : this.collection().entrySet()) {
            RedisPatchScheduleImpl patchSchedule = entry.getValue();
            result.put(entry.getKey(), patchSchedule);
        }
        return Collections.unmodifiableMap(result);
    }

    public void addPatchSchedule(RedisPatchScheduleImpl patchSchedule) {
        this.addChildResource(patchSchedule);
    }

    public RedisPatchScheduleImpl getPathSchedule() {
        return this.collection().get("default");
    }

    public void removePatchSchedule() {
        this.prepareInlineRemove("default");
    }

    public RedisPatchScheduleImpl defineInlinePatchSchedule() {
        return prepareInlineDefine("default");
    }

    public RedisPatchScheduleImpl updateInlinePatchSchedule() {
        return prepareInlineUpdate("default");
    }

    @Override
    protected List<RedisPatchScheduleImpl> listChildResources() {
        List<RedisPatchScheduleImpl> childResources = new ArrayList<>();
        for (RedisPatchScheduleInner patchSchedule : this.parent().manager().inner().patchSchedules().listByRedisResource(
                this.parent().resourceGroupName(),
                this.parent().name())) {
            childResources.add(new RedisPatchScheduleImpl(patchSchedule.name(), this.parent(), patchSchedule));
        }
        return Collections.unmodifiableList(childResources);
    }

    @Override
    protected RedisPatchScheduleImpl newChildResource(String name) {
        return new RedisPatchScheduleImpl(name, this.parent(), new RedisPatchScheduleInner());
    }
}
