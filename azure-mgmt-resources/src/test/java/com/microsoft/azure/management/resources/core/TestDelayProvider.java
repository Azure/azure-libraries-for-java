/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.core;

import com.microsoft.azure.management.resources.fluentcore.utils.DelayProvider;
import rx.Observable;

public class TestDelayProvider extends DelayProvider {
    private boolean isLiveMode;
    public TestDelayProvider(boolean isLiveMode) {
        this.isLiveMode = isLiveMode;
    }
    @Override
    public void sleep(int milliseconds) {
        if (isLiveMode) {
            super.sleep(milliseconds);
        }
    }

    @Override
    public <T> Observable<T> delayedEmitAsync(T event, int milliseconds) {
        if (isLiveMode) {
            return super.delayedEmitAsync(event, milliseconds);
        } else {
            return Observable.just(event);
        }
    }

}
