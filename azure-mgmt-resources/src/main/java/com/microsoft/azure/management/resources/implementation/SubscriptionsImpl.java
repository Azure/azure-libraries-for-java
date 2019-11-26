/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.implementation;

import com.azure.core.http.rest.PagedFlux;
import com.azure.core.management.PagedList;
import com.microsoft.azure.management.resources.Subscription;
import com.microsoft.azure.management.resources.Subscriptions;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.SupportsGettingByIdImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import reactor.core.publisher.Mono;

/**
 * The implementation of Subscriptions.
 */
final class SubscriptionsImpl
        extends SupportsGettingByIdImpl<Subscription>
        implements Subscriptions {
    private final SubscriptionsInner client;

    SubscriptionsImpl(final SubscriptionsInner client) {
        this.client = client;
    }

    @Override
    public PagedList<Subscription> list() {
        PagedListConverter<SubscriptionInner, Subscription> converter = new PagedListConverter<SubscriptionInner, Subscription>() {
            @Override
            public Mono<Subscription> typeConvertAsync(SubscriptionInner subscriptionInner) {
                return Mono.just((Subscription) wrapModel(subscriptionInner));
            }
        };
        return converter.convert(client.list());
    }


    @Override
    public Mono<Subscription> getByIdAsync(String id) {
        return client.getAsync(id).flatMap(inner -> Mono.just(wrapModel(inner)));
    }

    @Override
    public PagedFlux<Subscription> listAsync() {
        return client.listAsync().mapPage(inner -> wrapModel(inner));
    }

    private SubscriptionImpl wrapModel(SubscriptionInner subscriptionInner) {
        if (subscriptionInner == null) {
            return null;
        }
        return new SubscriptionImpl(subscriptionInner, client);
    }
}
