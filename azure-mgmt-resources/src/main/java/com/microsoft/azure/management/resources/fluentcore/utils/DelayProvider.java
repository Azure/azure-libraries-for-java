/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.utils;

import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * A wrapper class for thread sleep.
 */
public class DelayProvider {
    /**
     * Puts current thread on sleep for passed milliseconds.
     * @param milliseconds time to sleep for
     */
    public void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
        }
    }

    /**
     * Creates an observable that emits the given item after the specified time in milliseconds.
     *
     * @param event the event to emit
     * @param milliseconds the delay in milliseconds
     * @param <T> the type of event
     * @return delayed observable
     */
    public <T> Observable<T>  delayedEmitAsync(T event, int milliseconds) {
        return Observable.just(event).delay(milliseconds, TimeUnit.MILLISECONDS, Schedulers.immediate());
    }
}
