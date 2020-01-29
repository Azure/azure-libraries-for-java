/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.management.network.NetworkUsage;
import com.azure.management.network.NetworkUsageUnit;
import com.azure.management.network.UsageName;
import com.azure.management.network.models.UsageInner;
import com.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.azure.management.resources.fluentcore.utils.Utils;

/**
 * The implementation of {@link NetworkUsage}.
 */
class NetworkUsageImpl extends WrapperImpl<UsageInner> implements NetworkUsage {
    NetworkUsageImpl(UsageInner innerObject) {
        super(innerObject);
    }

    @Override
    public NetworkUsageUnit unit() {
        return NetworkUsageUnit.fromString(inner().getUnit());
    }

    @Override
    public long currentValue() {
        return Utils.toPrimitiveLong(inner().getCurrentValue());
    }

    @Override
    public long limit() {
        return Utils.toPrimitiveLong(inner().getLimit());
    }

    @Override
    public UsageName name() {
        return inner().getName();
    }
}

