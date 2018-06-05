/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.RemoteLoginInformation;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

/**
 * Implementation for {@link RemoteLoginInformation}.
 */
@LangDefinition
final class RemoteLoginInformationImpl extends
        IndexableWrapperImpl<RemoteLoginInformationInner>
        implements
        RemoteLoginInformation {

    RemoteLoginInformationImpl(RemoteLoginInformationInner innerModel) {
        super(innerModel);
    }

    @Override
    public String nodeId() {
        return inner().nodeId();
    }

    @Override
    public String ipAddress() {
        return inner().ipAddress();
    }

    @Override
    public double port() {
        if (inner().port() == null) {
            return 0;
        }
        return inner().port();
    }
}
