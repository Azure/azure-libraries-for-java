/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault.implementation;

import com.microsoft.azure.keyvault.models.SecretBundle;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.keyvault.Secret;
import com.microsoft.azure.management.keyvault.Vault;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * The implementation of Vaults and its parent interfaces.
 */
@LangDefinition
class KeyVaultFutures {
    public static ServiceFuture<Secret> fromInner(ServiceFuture<SecretBundle> inner, final ServiceCallback<Secret> callback, final Vault vault) {
        final SecretFuture future = new SecretFuture();
        Observable.from(inner)
                .subscribe(new Action1<SecretBundle>() {
                    @Override
                    public void call(SecretBundle secretBundle) {
                        Secret fluent = new SecretImpl(KeyVaultUtils.nameFromId(secretBundle.id()), secretBundle, vault);
                        if (callback != null) {
                            callback.success(fluent);
                        }
                        future.success(fluent);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (callback != null) {
                            callback.failure(throwable);
                        }
                        future.failure(throwable);
                    }
                });
        return future;
    }

    static class SecretFuture extends ServiceFuture<Secret> {
        SecretFuture() {
            super();
        }

        @Override
        protected void setSubscription(Subscription subscription) {
            super.setSubscription(subscription);
        }

        boolean failure(Throwable t) {
            return setException(t);
        }
    }
}
