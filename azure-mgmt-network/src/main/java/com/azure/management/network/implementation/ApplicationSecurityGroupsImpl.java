/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.azure.management.network.ApplicationSecurityGroup;
import com.azure.management.network.ApplicationSecurityGroups;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;

/**
 * Implementation for ApplicationSecurityGroups.
 */
@LangDefinition
class ApplicationSecurityGroupsImpl
        extends TopLevelModifiableResourcesImpl<
        ApplicationSecurityGroup,
        ApplicationSecurityGroupImpl,
        ApplicationSecurityGroupInner,
        ApplicationSecurityGroupsInner,
        NetworkManager>
        implements ApplicationSecurityGroups {

    ApplicationSecurityGroupsImpl(final NetworkManager networkManager) {
        super(networkManager.inner().applicationSecurityGroups(), networkManager);
    }

    @Override
    public ApplicationSecurityGroupImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    protected ApplicationSecurityGroupImpl wrapModel(String name) {
        ApplicationSecurityGroupInner inner = new ApplicationSecurityGroupInner();
        return new ApplicationSecurityGroupImpl(name, inner, super.manager());
    }

    @Override
    protected ApplicationSecurityGroupImpl wrapModel(ApplicationSecurityGroupInner inner) {
        if (inner == null) {
            return null;
        }
        return new ApplicationSecurityGroupImpl(inner.name(), inner, this.manager());
    }
}

