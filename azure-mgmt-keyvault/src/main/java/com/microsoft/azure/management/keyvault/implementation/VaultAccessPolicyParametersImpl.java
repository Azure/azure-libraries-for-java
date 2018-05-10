package com.microsoft.azure.management.keyvault.implementation;

import com.microsoft.azure.management.keyvault.VaultAccessPolicyParameters;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

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

}
