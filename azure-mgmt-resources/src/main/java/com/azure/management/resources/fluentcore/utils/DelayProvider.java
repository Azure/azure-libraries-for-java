/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.fluentcore.utils;

import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.OffsetDateTime;

/**
 * A wrapper class for thread sleep.
 */
public class DelayProvider {
    /**
     * Puts current thread on sleep for passed milliseconds.
     *
     * @param milliseconds time to sleep for
     */
    public void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
        }
    }

    /**
     * @return the current time.
     */
    public OffsetDateTime now() {
        return OffsetDateTime.now();
    }

    /**
     * Creates an observable that emits the given item after the specified time in milliseconds.
     *
     * @param event        the event to emit
     * @param milliseconds the delay in milliseconds
     * @param <T>          the type of event
     * @return delayed observable
     */
    public <T> Mono<T> delayedEmitAsync(T event, int milliseconds) {
        return Mono.just(event).delayElement(Duration.ofMillis(milliseconds));
    }
}
