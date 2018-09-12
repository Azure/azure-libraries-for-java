/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.compute;

import com.microsoft.rest.v2.annotations.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.compute.implementation.GalleriesInner;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsBatchDeletion;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsDeletingByResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsGettingByResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsListingByResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;

/**
 * Entry point to galleries management API in Azure.
 */
@Fluent
@Beta(since = "V1_15_0")
public interface Galleries extends SupportsCreating<Gallery.DefinitionStages.Blank>,
        SupportsDeletingByResourceGroup,
        SupportsBatchDeletion,
        SupportsGettingByResourceGroup<Gallery>,
        SupportsListingByResourceGroup<Gallery>,
        SupportsListing<Gallery>,
        HasInner<GalleriesInner> {
}
