/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.storage.implementation;


import com.azure.management.storage.BlobServiceProperties;
import com.azure.management.storage.BlobServices;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import rx.Observable;
import rx.functions.Func1;

@LangDefinition
class BlobServicesImpl extends WrapperImpl<BlobServicesInner> implements BlobServices {
    private final StorageManager manager;

    BlobServicesImpl(StorageManager manager) {
        super(manager.inner().blobServices());
        this.manager = manager;
    }

    public StorageManager manager() {
        return this.manager;
    }

    @Override
    public BlobServicePropertiesImpl define(String name) {
        return wrapModel(name);
    }

    private BlobServicePropertiesImpl wrapModel(BlobServicePropertiesInner inner) {
        return  new BlobServicePropertiesImpl(inner, manager());
    }

    private BlobServicePropertiesImpl wrapModel(String name) {
        return new BlobServicePropertiesImpl(name, this.manager());
    }

    @Override
    public Observable<BlobServiceProperties> getServicePropertiesAsync(String resourceGroupName, String accountName) {
        BlobServicesInner client = this.inner();
        return client.getServicePropertiesAsync(resourceGroupName, accountName)
                .map(new Func1<BlobServicePropertiesInner, BlobServiceProperties>() {
                    @Override
                    public BlobServiceProperties call(BlobServicePropertiesInner inner) {
                        return wrapModel(inner);
                    }
                });
    }

}