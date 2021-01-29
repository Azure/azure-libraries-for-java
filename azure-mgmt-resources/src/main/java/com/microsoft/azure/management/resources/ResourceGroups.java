/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingByName;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsBatchCreation;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsBeginDeletingByName;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingByName;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListingByTag;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;

import java.util.Collection;

/**
 * Entry point to resource group management API.
 */
@Fluent
public interface ResourceGroups extends
        SupportsListing<ResourceGroup>,
        SupportsListingByTag<ResourceGroup>,
        SupportsGettingByName<ResourceGroup>,
        SupportsCreating<ResourceGroup.DefinitionStages.Blank>,
        SupportsDeletingByName,
        SupportsBeginDeletingByName,
        SupportsBatchCreation<ResourceGroup> {
    /**
     * Checks whether resource group exists.
     *
     * @param name The name of the resource group to check. The name is case insensitive
     * @return true if the resource group exists; false otherwise
     * @deprecated Use contain() instead.
     */
    @Deprecated
    boolean checkExistence(String name);

    /**
     * Checks whether resource group exists.
     *
     * @param name the name (case insensitive) of the resource group to check for
     * @return true of exists, otherwise false
     */
    @Beta(SinceVersion.V1_4_0)
    boolean contain(String name);

    /**
     * Deletes a resource from Azure, identifying it by its resource name.
     *
     * @param name the name of the resource to delete
     * @param forceDeletionResourceTypes resource types for force deletion
     */
    @Beta(SinceVersion.V1_38_0)
    void deleteByName(String name, Collection<ForceDeletionResourceType> forceDeletionResourceTypes);

    /**
     * Asynchronously delete a resource from Azure, identifying it by its resource name.
     *
     * @param name the name of the resource to delete
     * @param forceDeletionResourceTypes resource types for force deletion
     * @param callback the callback on success or failure
     * @return a handle to cancel the request
     */
    @Beta(SinceVersion.V1_38_0)
    ServiceFuture<Void> deleteByNameAsync(String name, Collection<ForceDeletionResourceType> forceDeletionResourceTypes, ServiceCallback<Void> callback);

    /**
     * Asynchronously delete a resource from Azure, identifying it by its resource name.
     *
     * @param name the name of the resource to delete
     * @param forceDeletionResourceTypes resource types for force deletion
     * @return a representation of the deferred computation of this call
     */
    @Beta(SinceVersion.V1_38_0)
    Completable deleteByNameAsync(String name, Collection<ForceDeletionResourceType> forceDeletionResourceTypes);
}
