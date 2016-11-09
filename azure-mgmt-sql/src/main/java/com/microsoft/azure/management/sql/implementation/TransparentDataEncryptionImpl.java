/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.microsoft.azure.management.sql.TransparentDataEncryption;
import com.microsoft.azure.management.sql.TransparentDataEncryptionStates;

/**
 * Implementation for TransparentDataEncryption.
 */
@LangDefinition
class TransparentDataEncryptionImpl
        extends WrapperImpl<TransparentDataEncryptionInner>
        implements TransparentDataEncryption {
    private final ResourceId resourceId;

    protected TransparentDataEncryptionImpl(TransparentDataEncryptionInner innerObject) {
        super(innerObject);
        this.resourceId = ResourceId.parseResourceId(this.inner().id());
    }

    @Override
    public String name() {
        return this.resourceId.name();
    }

    @Override
    public String id() {
        return this.resourceId.id();
    }

    @Override
    public String resourceGroupName() {
        return this.resourceId.resourceGroupName();
    }

    @Override
    public String sqlServerName() {
        return resourceId.parent().parent().name();
    }

    @Override
    public String databaseName() {
        return resourceId.parent().name();
    }

    @Override
    public TransparentDataEncryptionStates status() {
        return this.inner().status();
    }
}
