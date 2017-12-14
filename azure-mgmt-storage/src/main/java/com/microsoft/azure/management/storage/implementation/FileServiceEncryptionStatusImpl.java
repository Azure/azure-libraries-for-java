/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.storage.EncryptionService;
import com.microsoft.azure.management.storage.EncryptionServices;
import com.microsoft.azure.management.storage.StorageService;

/**
 * Implementation of StorageAccountEncryptionStatus for File service.
 */
@LangDefinition
class FileServiceEncryptionStatusImpl extends StorageAccountEncryptionStatusImpl {
    FileServiceEncryptionStatusImpl(EncryptionServices encryptionServices) {
        super(encryptionServices);
    }

    @Override
    public StorageService storageService() {
        return StorageService.FILE;
    }

    @Override
    protected EncryptionService encryptionService() {
        if (super.encryptionServices == null) {
            return null;
        } else {
            return  super.encryptionServices.file();
        }
    }
}