/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.keyvault.implementation;

import com.microsoft.azure.management.keyvault.DeletedVault;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableRefreshableWrapperImpl;
import java.util.Arrays;
import java.util.Iterator;
import rx.Observable;
import com.microsoft.azure.management.keyvault.DeletedVaultProperties;

class DeletedVaultImpl extends IndexableRefreshableWrapperImpl<DeletedVault, DeletedVaultInner> implements DeletedVault {
    private final KeyVaultManager manager;
    private String vaultName;
    private String location;
    DeletedVaultImpl(DeletedVaultInner inner,  KeyVaultManager manager) {
        super(null, inner);
        this.manager = manager;
        this.vaultName = GetValueFromIdByName(inner.id(), "deletedVaults");
        this.location = GetValueFromIdByName(inner.id(), "locations");
    }

    @Override
    public KeyVaultManager manager() {
        return this.manager;
    }

    @Override
    protected Observable<DeletedVaultInner> getInnerAsync() {
        VaultsInner client = this.manager.inner().vaults();
        return client.getDeletedAsync(this.vaultName, this.location);
    }


    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public DeletedVaultProperties properties() {
        return this.inner().properties();
    }

    @Override
    public String type() {
        return this.inner().type();
    }


    private static String GetValueFromIdByName(String id, String name) {
        if (id == null) {
            return null;
        }
        Iterable<String> iterable = Arrays.asList(id.split("/"));
        Iterator <String> itr = iterable.iterator();
        while (itr.hasNext()) {
            String part = itr.next();
            if (part != null && part.trim() != "") {
                if (part.equalsIgnoreCase(name)) {
                    if (itr.hasNext()) {
                        return itr.next();
                    } else {
                        return null;
                    }
                }
            }
        }
        return null;
    }
    private static String GetValueFromIdByPosition(String id, int pos) {
        if (id == null) {
            return null;
        }
        Iterable<String> iterable = Arrays.asList(id.split("/"));
        Iterator <String> itr = iterable.iterator();
        int index = 0;
        while (itr.hasNext()) {
            String part = itr.next();
            if (part != null && part.trim() != "") {
                if (index == pos) {
                    if (itr.hasNext()) {
                        return itr.next();
                    } else {
                        return null;
                    }
                }
            }
            index++;
        }
        return null;
    }
}
