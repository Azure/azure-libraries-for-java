/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.network.ConnectionMonitorQueryResult;
import com.microsoft.azure.management.network.ConnectionMonitorSourceStatus;
import com.microsoft.azure.management.network.ConnectionStateSnapshot;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

import java.util.Collections;
import java.util.List;

/**
 * Implementation for {@link com.microsoft.azure.management.network.ConnectionMonitorQueryResult}.
 */
@LangDefinition
class ConnectionMonitorQueryResultImpl extends WrapperImpl<ConnectionMonitorQueryResultInner>
        implements ConnectionMonitorQueryResult {
    ConnectionMonitorQueryResultImpl(ConnectionMonitorQueryResultInner innerObject) {
        super(innerObject);
    }

    @Override
    public ConnectionMonitorSourceStatus sourceStatus() {
        return inner().sourceStatus();
    }

    @Override
    public List<ConnectionStateSnapshot> states() {
        return Collections.unmodifiableList(inner().states());
    }
}
