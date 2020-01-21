/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.keyvault.implementation;

import java.util.List;

/**
 * A collection of utilities for converting futures in Key Vault client to observables.
 */
final class KeyVaultFutures {
//    abstract static class ServiceFutureConverter<TInner, T> {
//        abstract ServiceFuture<TInner> callAsync();
//
//        abstract T wrapModel(TInner inner);
//
//        ServiceFuture<T> toFuture(final ServiceCallback<T> callback) {
//            final KeyVaultFuture<T> future = new KeyVaultFuture<>();
//            Observable.from(callAsync())
//                    .subscribe(new Action1<TInner>() {
//                        @Override
//                        public void call(TInner inner) {
//                            T fluent = wrapModel(inner);
//                            if (callback != null) {
//                                callback.success(fluent);
//                            }
//                            future.success(fluent);
//                        }
//                    }, new Action1<Throwable>() {
//                        @Override
//                        public void call(Throwable throwable) {
//                            if (callback != null) {
//                                callback.failure(throwable);
//                            }
//                            future.failure(throwable);
//                        }
//                    });
//            return future;
//        }
//
//        public Observable<T> toObservable() {
//            return Observable.defer(new Func0<Observable<T>>() {
//                @Override
//                public Observable<T> call() {
//                    return Observable.from(toFuture(null));
//                }
//            });
//        }
//    }
//
//    abstract static class ListCallbackObserver<TInner, T> {
//        abstract void list(ListOperationCallback<TInner> callback);
//
//        abstract Observable<T> typeConvertAsync(TInner inner);
//
//        Observable<T> toObservable() {
//            return Observable
//                    .create(new Action1<Emitter<List<TInner>>>() {
//                        @Override
//                        public void call(final Emitter<List<TInner>> emitter) {
//                            list(new ListOperationCallback<TInner>() {
//                                @Override
//                                public PagingBehavior progress(List<TInner> partial) {
//                                    emitter.onNext(partial);
//                                    return PagingBehavior.CONTINUE;
//                                }
//
//                                @Override
//                                public void success() {
//                                    emitter.onCompleted();
//                                }
//
//                                @Override
//                                public void failure(Throwable t) {
//                                    emitter.onError(t);
//                                }
//                            });
//                        }
//                    }, BackpressureMode.BUFFER)
//                    .flatMap(new Func1<List<TInner>, Observable<TInner>>() {
//                        @Override
//                        public Observable<TInner> call(List<TInner> secretItems) {
//                            return Observable.from(secretItems);
//                        }
//                    }).flatMap(new Func1<TInner, Observable<T>>() {
//                        @Override
//                        public Observable<T> call(TInner tInner) {
//                            return typeConvertAsync(tInner);
//                        }
//                    });
//        }
//    }
//
//    static class KeyVaultFuture<T> extends ServiceFuture<T> {
//        KeyVaultFuture() {
//            super();
//        }
//
//        @Override
//        protected void setSubscription(Subscription subscription) {
//            super.setSubscription(subscription);
//        }
//
//        boolean failure(Throwable t) {
//            return setException(t);
//        }
//    }
}
