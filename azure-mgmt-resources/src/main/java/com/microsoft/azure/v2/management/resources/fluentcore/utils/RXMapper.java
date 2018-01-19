/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.fluentcore.utils;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * An internal utility class representing an RX function returning the provided type instance
 * from a call with an arbitrary parameter.
 * @param <T> the type to emit as Observable
 */
public final class RXMapper<T> implements Function<Object, T> {
    private final T value;

    /**
     * Shortcut for mapping the output of an arbitrary observable to one returning an instance of a specific type, using the IO scheduler.
     * @param fromObservable the source observable
     * @param toValue the value to emit to the observer
     * @param <T> the type of the value to emit
     * @return an observable emitting the specified value
     */
    public static <T> Observable<T> map(Observable<?> fromObservable, @NonNull final T toValue) {
        if (fromObservable != null) {
            return fromObservable.subscribeOn(Schedulers.io())
                    .map(new RXMapper<>(toValue));
        } else {
            return Observable.empty();
        }
    }

    /**
     * Shortcut for mapping the output of an arbitrary Single to one returning an instance of a specific type, using the IO scheduler.
     * @param fromSingle the source observable
     * @param toValue the value to emit to the observer
     * @param <T> the type of the value to emit
     * @return an Single emitting the specified value
     */
    public static <T> Single<T> map(Single<?> fromSingle, @NonNull final T toValue) {
        if (fromSingle != null) {
            return fromSingle.subscribeOn(Schedulers.io())
                    .map(new RXMapper<>(toValue));
        } else {
            return Single.just(toValue);
        }
    }

    /**
     * Shortcut for mapping the output or completion of an arbitrary Maybe to one returning an instance of a specific type, using the IO scheduler.
     * @param fromMaybe the source observable
     * @param toValue the value to emit to the observer
     * @param <T> the type of the value to emit
     * @return an Single emitting the specified value
     */
    public static <T> Single<T> map(Maybe<?> fromMaybe, @NonNull final T toValue) {
        if (fromMaybe != null) {
            return fromMaybe.subscribeOn(Schedulers.io())
                    .ignoreElement()
                    .andThen(Single.just(toValue));
        } else {
            return Single.just(toValue);
        }
    }

    /**
     * @param value the value to emit
     */
    private RXMapper(T value) {
        this.value = value;
    }

    @Override
    public T apply(Object ignored) {
        return this.value;
    }
}
