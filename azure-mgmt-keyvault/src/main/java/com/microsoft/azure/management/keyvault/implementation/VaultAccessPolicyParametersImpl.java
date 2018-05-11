/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 */

package com.microsoft.azure.management.keyvault.implementation;

import java.util.List;

import com.microsoft.azure.management.keyvault.AccessPolicyEntry;
import com.microsoft.azure.management.keyvault.VaultAccessPolicyParameters;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

/**
 * Deleted vault information with extended details.
 */
public class VaultAccessPolicyParametersImpl extends WrapperImpl<VaultAccessPolicyParametersInner> implements VaultAccessPolicyParameters {
    VaultAccessPolicyParametersImpl(VaultAccessPolicyParametersInner inner) {
        super(inner);
    }

    @Override
    public String id() {
        return inner().id();
    }

    @Override
    public String name() {
        return inner().name();
    }

    @Override
    public String type() {
        return inner().type();
    }

    @Override
    public String location() {
        return inner().location();
    }

    @Override
    public List<AccessPolicyEntry> accessPolicies() {
        return inner().properties().accessPolicies();
    }

}
