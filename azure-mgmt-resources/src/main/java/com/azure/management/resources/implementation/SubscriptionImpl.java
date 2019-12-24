/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.implementation;

import com.azure.core.http.rest.PagedIterable;
import com.azure.management.resources.Location;
import com.azure.management.resources.Subscription;
import com.azure.management.resources.SubscriptionPolicies;
import com.azure.management.resources.SubscriptionState;
import com.azure.management.resources.fluentcore.arm.Region;
import com.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;
import com.azure.management.resources.models.SubscriptionInner;
import com.azure.management.resources.models.SubscriptionsInner;


/**
 * The implementation of {@link Subscription}.
 */
final class SubscriptionImpl extends
        IndexableWrapperImpl<SubscriptionInner>
        implements
        Subscription  {

    private final SubscriptionsInner client;

    SubscriptionImpl(SubscriptionInner innerModel, final SubscriptionsInner client) {
        super(innerModel);
        this.client = client;
    }

    @Override
    public String subscriptionId() {
        return this.getInner().getSubscriptionId();
    }

    @Override
    public String displayName() {
        return this.getInner().getDisplayName();
    }

    @Override
    public SubscriptionState state() {
        return this.getInner().getState();
    }

    @Override
    public SubscriptionPolicies subscriptionPolicies() {
        return this.getInner().getSubscriptionPolicies();
    }

    @Override
    public PagedIterable<Location> listLocations() {
        return client.listLocations(this.subscriptionId());
    }

    @Override
    public Location getLocationByRegion(Region region) {
        if (region != null) {
            PagedIterable<Location> locations = listLocations();
            // TODO: Fix region and location comparison.
            for (Location location : locations) {
                if (region.equals(location.getName())) {
                    return location;
                }
            }
        }
        return null;
    }
}
