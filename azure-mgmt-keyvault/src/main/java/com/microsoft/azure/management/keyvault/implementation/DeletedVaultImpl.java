package com.microsoft.azure.management.keyvault.implementation;

import com.microsoft.azure.management.keyvault.DeletedVault;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

public class DeletedVaultImpl extends WrapperImpl<DeletedVaultInner> implements DeletedVault {

    DeletedVaultImpl(DeletedVaultInner inner) {
        super(inner);
    }

    @Override
    public String name() {
        return inner().name();
    }

    @Override
    public String id() {
        return inner().id();
    }

}
