/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources;

import com.microsoft.azure.arm.collection.SupportsListing;
import com.microsoft.azure.arm.resources.collection.SupportsGettingById;
import com.microsoft.azure.management.apigeneration.Fluent;

/**
 * Entry point to subscription management API.
 */
@Fluent
public interface Subscriptions extends
        SupportsListing<Subscription>,
        SupportsGettingById<Subscription> {
}
