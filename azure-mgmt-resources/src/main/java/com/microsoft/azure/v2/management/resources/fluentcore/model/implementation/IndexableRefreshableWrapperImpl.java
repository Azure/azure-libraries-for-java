/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.fluentcore.model.implementation;

import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Refreshable;
import io.reactivex.Maybe;
import io.reactivex.functions.Function;

/**
 * The implementation for {@link Indexable}, {@link Refreshable}, and {@link HasInner}.
 *
 * @param <FluentModelT> The fluent model type
 * @param <InnerModelT> Azure inner resource class type
 */
public abstract class IndexableRefreshableWrapperImpl<FluentModelT, InnerModelT>
    extends IndexableRefreshableImpl<FluentModelT>
    implements HasInner<InnerModelT> {

    private InnerModelT innerObject;
    protected IndexableRefreshableWrapperImpl(InnerModelT innerObject) {
        this.setInner(innerObject);
    }

    protected IndexableRefreshableWrapperImpl(String key, InnerModelT innerObject) {
        super(key);
        this.setInner(innerObject);
    }

    @Override
    public InnerModelT inner() {
        return this.innerObject;
    }

    /**
     * Set the wrapped inner model.
     * (For internal use only)
     *
     * @param inner the new inner model
     */
    public void setInner(InnerModelT inner) {
        this.innerObject = inner;
    }

    @Override
    public final FluentModelT refresh() {
        return refreshAsync().blockingGet();
    }

    @Override
    public Maybe<FluentModelT> refreshAsync() {
        final IndexableRefreshableWrapperImpl<FluentModelT, InnerModelT> self = this;
        return getInnerAsync().map(new Function<InnerModelT, FluentModelT>() {
            @Override
            public FluentModelT apply(InnerModelT innerModelT) {
                self.setInner(innerModelT);
                return (FluentModelT) self;
            }
        });
    }

    protected abstract Maybe<InnerModelT> getInnerAsync();
}
