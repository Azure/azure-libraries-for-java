/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.fluentcore.model.implementation;

import com.microsoft.azure.v2.management.resources.fluentcore.model.Refreshable;
import io.reactivex.Maybe;
import io.reactivex.functions.Function;

/**
 * Base implementation for Wrapper interface.
 *
 * @param <InnerT> wrapped type
 * @param <Impl> impl type
 */
public abstract class RefreshableWrapperImpl<InnerT, Impl>
        extends WrapperImpl<InnerT>
        implements Refreshable<Impl> {

    protected RefreshableWrapperImpl(InnerT innerObject) {
        super(innerObject);
    }

    @Override
    public final Impl refresh() {
        return this.refreshAsync().blockingGet();
    }

    @Override
    public Maybe<Impl> refreshAsync() {
        final RefreshableWrapperImpl<InnerT, Impl> self = this;

        return this.getInnerAsync().map(new Function<InnerT, Impl>() {
            @Override
            public Impl apply(InnerT innerT) {
                self.setInner(innerT);
                return (Impl) self;
            }
        });
    }

    protected abstract Maybe<InnerT> getInnerAsync();
}
