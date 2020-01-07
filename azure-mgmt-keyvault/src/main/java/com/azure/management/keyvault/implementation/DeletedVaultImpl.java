/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 */

package com.azure.management.keyvault.implementation;

import java.util.Map;

import org.joda.time.DateTime;

import com.azure.management.keyvault.DeletedVault;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

/**
 * Deleted vault information with extended details.
 */
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

    @Override
    public String location() {
        return inner().properties().location();
    }

    @Override
    public DateTime deletionDate() {
        return inner().properties().deletionDate();
    }

    @Override
    public DateTime scheduledPurgeDate() {
        return inner().properties().scheduledPurgeDate();
    }

    @Override
    public Map<String, String> tags() {
        return inner().properties().tags();
    }

}
