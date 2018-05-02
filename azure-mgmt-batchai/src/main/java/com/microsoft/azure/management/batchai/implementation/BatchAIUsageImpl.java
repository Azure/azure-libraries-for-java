/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIUsage;
import com.microsoft.azure.management.batchai.UsageName;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

/**
 * The implementation of BatchAIUsage.
 */
@LangDefinition
class BatchAIUsageImpl extends WrapperImpl<UsageInner> implements BatchAIUsage {
    BatchAIUsageImpl(UsageInner innerObject) {
        super(innerObject);
    }

    @Override
    public String unit() {
        return inner().unit();
    }

    @Override
    public int currentValue() {
        return inner().currentValue();
    }

    @Override
    public long limit() {
        return inner().limit();
    }

    @Override
    public UsageName name() {
        return inner().name();
    }
}

