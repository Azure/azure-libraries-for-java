/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.storage.EncryptionService;
import com.microsoft.azure.management.storage.EncryptionServices;
import com.microsoft.azure.management.storage.StorageAccountEncryptionStatus;
import org.joda.time.DateTime;

/**
 * Shared implementation of StorageAccountEncryptionStatus.
 */
@LangDefinition
public abstract class StorageAccountEncryptionStatusImpl implements StorageAccountEncryptionStatus  {
    protected final EncryptionServices encryptionServices;

    protected StorageAccountEncryptionStatusImpl(EncryptionServices encryptionServices) {
        this.encryptionServices = encryptionServices;
    }

    @Override
    public boolean isEnabled() {
        EncryptionService encryptionService = this.encryptionService();
        if (encryptionService == null) {
            return false;
        } else if (encryptionService.enabled() != null) {
            return encryptionService.enabled();
        } else {
            return false;
        }
    }

    @Override
    public DateTime lastEnabledTime() {
        EncryptionService encryptionService = this.encryptionService();
        if (encryptionService == null) {
            return null;
        } else {
            return encryptionService.lastEnabledTime();
        }
    }

    protected abstract EncryptionService encryptionService();
}