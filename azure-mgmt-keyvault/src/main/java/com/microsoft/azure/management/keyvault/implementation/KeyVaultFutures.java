/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault.implementation;

import com.microsoft.azure.ListOperationCallback;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Emitter;
import rx.Emitter.BackpressureMode;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

import java.util.List;

/**
 * The implementation of Vaults and its parent interfaces.
 */
@LangDefinition
class KeyVaultFutures {
    public static abstract class ServiceFutureConverter<TInner, T> {
        protected abstract ServiceFuture<TInner> callAsync();

        protected abstract T wrapModel(TInner inner);

        public ServiceFuture<T> toFuture(final ServiceCallback<T> callback) {
            final KeyVaultFuture<T> future = new KeyVaultFuture<>();
            Observable.from(callAsync())
                    .subscribe(new Action1<TInner>() {
                        @Override
                        public void call(TInner inner) {
                            T fluent = wrapModel(inner);
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

        public Observable<T> toObservable() {
            return Observable.defer(new Func0<Observable<T>>() {
                @Override
                public Observable<T> call() {
                    return Observable.from(toFuture(null));
                }
            });
        }
    }

    public static abstract class ListCallbackObserver<TInner, T> {
        protected abstract void list(ListOperationCallback<TInner> callback);

        protected abstract Observable<T> typeConvertAsync(TInner inner);

        public Observable<T> toObservable() {
            return Observable
                    .fromEmitter(new Action1<Emitter<List<TInner>>>() {
                        @Override
                        public void call(final Emitter<List<TInner>> emitter) {
                            list(new ListOperationCallback<TInner>() {
                                @Override
                                public PagingBehavior progress(List<TInner> partial) {
                                    emitter.onNext(partial);
                                    return PagingBehavior.CONTINUE;
                                }

                                @Override
                                public void success() {
                                    emitter.onCompleted();
                                }

                                @Override
                                public void failure(Throwable t) {
                                    emitter.onError(t);
                                }
                            });
                        }
                    }, BackpressureMode.BUFFER)
                    .flatMap(new Func1<List<TInner>, Observable<TInner>>() {
                        @Override
                        public Observable<TInner> call(List<TInner> secretItems) {
                            return Observable.from(secretItems);
                        }
                    }).flatMap(new Func1<TInner, Observable<T>>() {
                        @Override
                        public Observable<T> call(TInner tInner) {
                            return typeConvertAsync(tInner);
                        }
                    });
        }
    }

    static class KeyVaultFuture<T> extends ServiceFuture<T> {
        KeyVaultFuture() {
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
