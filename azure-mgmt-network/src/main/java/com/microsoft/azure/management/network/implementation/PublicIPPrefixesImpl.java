/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.network.implementation;

import com.microsoft.azure.management.network.PublicIPPrefix;
import com.microsoft.azure.management.network.PublicIPPrefixes;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;

class PublicIPPrefixesImpl extends
        TopLevelModifiableResourcesImpl<
                PublicIPPrefix,
                PublicIPPrefixImpl,
                PublicIPPrefixInner,
                PublicIPPrefixesInner,
                NetworkManager> implements PublicIPPrefixes {

    PublicIPPrefixesImpl(final NetworkManager networkManager) {
        super(networkManager.inner().publicIPPrefixes(), networkManager);
    }

    @Override
    public PublicIPPrefixImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    protected PublicIPPrefixImpl wrapModel(PublicIPPrefixInner inner) {
        return new PublicIPPrefixImpl(inner.name(), inner, manager());
    }

    @Override
    protected PublicIPPrefixImpl wrapModel(String name) {
        return new PublicIPPrefixImpl(name, new PublicIPPrefixInner(), this.manager());
    }

}
