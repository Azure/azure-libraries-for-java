/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.network.ExpressRouteCrossConnection;
import com.microsoft.azure.management.network.ExpressRouteCrossConnections;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;

@LangDefinition
class ExpressRouteCrossConnectionsImpl extends TopLevelModifiableResourcesImpl<
        ExpressRouteCrossConnection,
        ExpressRouteCrossConnectionImpl,
        ExpressRouteCrossConnectionInner,
        ExpressRouteCrossConnectionsInner,
        NetworkManager>
        implements ExpressRouteCrossConnections {

    ExpressRouteCrossConnectionsImpl(NetworkManager manager) {
        super(manager.inner().expressRouteCrossConnections(), manager);
    }

    @Override
    protected ExpressRouteCrossConnectionImpl wrapModel(String name) {
        ExpressRouteCrossConnectionInner inner = new ExpressRouteCrossConnectionInner();

        return new ExpressRouteCrossConnectionImpl(name, inner, super.manager());
    }

    @Override
    protected ExpressRouteCrossConnectionImpl wrapModel(ExpressRouteCrossConnectionInner inner) {
        if (inner == null) {
            return null;
        }
        return new ExpressRouteCrossConnectionImpl(inner.name(), inner, this.manager());
    }

    @Override
    public ExpressRouteCrossConnectionImpl define(String name) {
        return wrapModel(name);
    }
}
