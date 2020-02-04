/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 */

package com.azure.management.keyvault.implementation;

import java.time.OffsetDateTime;
import java.util.Map;

import com.azure.management.keyvault.models.DeletedVaultInner;
import com.azure.management.keyvault.DeletedVault;
import com.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

/**
 * Deleted vault information with extended details.
 */
public class DeletedVaultImpl extends WrapperImpl<DeletedVaultInner> implements DeletedVault {

    DeletedVaultImpl(DeletedVaultInner inner) {
        super(inner);
    }

    @Override
    public String getName() {
        return getInner().getName();
    }

    @Override
    public String getId() {
        return getInner().getId();
    }

    @Override
    public String location() {
        return getInner().getProperties().getLocation();
    }

    @Override
    public OffsetDateTime deletionDate() {
        return getInner().getProperties().getDeletionDate();
    }

    @Override
    public OffsetDateTime scheduledPurgeDate() {
        return getInner().getProperties().getScheduledPurgeDate();
    }

    @Override
    public Map<String, String> tags() {
        return getInner().getProperties().getTags();
    }

}
