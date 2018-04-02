/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.keyvault.implementation;

import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.microsoft.azure.management.keyvault.Operations;
import com.microsoft.azure.management.keyvault.Operation;
import rx.Observable;
import rx.functions.Func1;
import com.microsoft.azure.Page;

class OperationsImpl extends WrapperImpl<OperationsInner> implements Operations {
    private final KeyVaultManager manager;

    OperationsImpl(KeyVaultManager manager) {
        super(manager.inner().operations());
        this.manager = manager;
    }

    public KeyVaultManager manager() {
        return this.manager;
    }

    private Observable<Page<OperationInner>> listNextInnerPageAsync(String nextLink) {
        if (nextLink == null) {
            Observable.empty();
        }
        OperationsInner client = this.inner();
        return client.listNextAsync(nextLink)
        .flatMap(new Func1<Page<OperationInner>, Observable<Page<OperationInner>>>() {
            @Override
            public Observable<Page<OperationInner>> call(Page<OperationInner> page) {
                return Observable.just(page).concatWith(listNextInnerPageAsync(page.nextPageLink()));
            }
        });
    }
    @Override
    public Observable<Operation> listAsync() {
        OperationsInner client = this.inner();
        return client.listAsync()
        .flatMap(new Func1<Page<OperationInner>, Observable<Page<OperationInner>>>() {
            @Override
            public Observable<Page<OperationInner>> call(Page<OperationInner> page) {
                return listNextInnerPageAsync(page.nextPageLink());
            }
        })
        .flatMapIterable(new Func1<Page<OperationInner>, Iterable<OperationInner>>() {
            @Override
            public Iterable<OperationInner> call(Page<OperationInner> page) {
                return page.items();
            }
       })
        .map(new Func1<OperationInner, Operation>() {
            @Override
            public Operation call(OperationInner inner) {
                return new OperationImpl(inner);
            }
       });
    }

}
