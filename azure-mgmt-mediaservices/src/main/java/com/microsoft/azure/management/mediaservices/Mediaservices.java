/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.mediaservices;

import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsDeletingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsBatchDeletion;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsListingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.management.mediaservices.implementation.MediaservicesInner;
import rx.Completable;
import rx.Observable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * Type representing Mediaservices.
 */
public interface Mediaservices extends SupportsCreating<MediaService.DefinitionStages.Blank>, SupportsDeletingByResourceGroup, SupportsBatchDeletion, SupportsGettingByResourceGroup<MediaService>, SupportsListingByResourceGroup<MediaService>, SupportsListing<MediaService>, HasInner<MediaservicesInner> {

    /**
     * @return Entry point to manage Mediaservice Asset.
     */
    Assets assets();

    /**
     * @return Entry point to manage Mediaservice ContentKeyPolicy.
     */
    ContentKeyPolicies contentKeyPolicies();

    /**
     * @return Entry point to manage Mediaservice Transform.
     */
    Transforms transforms();

    /**
     * @return Entry point to manage Mediaservice StreamingPolicy.
     */
    StreamingPolicies streamingPolicies();

    /**
     * @return Entry point to manage Mediaservice StreamingLocator.
     */
    StreamingLocators streamingLocators();

    /**
     * @return Entry point to manage Mediaservice LiveEvent.
     */
    LiveEvents liveEvents();

    /**
     * @return Entry point to manage Mediaservice StreamingEndpoint.
     */
    StreamingEndpoints streamingEndpoints();
    /**
* Synchronizes Storage Account Keys.
    * Synchronizes storage account keys for a storage account associated with the Media Service account.
    *
    * @param resourceGroupName The name of the resource group within the Azure subscription.
    * @param accountName The Media Services account name.
    * @throws IllegalArgumentException thrown if parameters fail the validation
    * @return the observable for the request
    */
    Completable syncStorageKeysAsync(String resourceGroupName, String accountName);

    /**
* Get a Media Services account.
    * Get the details of a Media Services account.
    *
    * @param accountName The Media Services account name.
    * @throws IllegalArgumentException thrown if parameters fail the validation
    * @return the observable for the request
    */
    Observable<MediaService> getBySubscriptionAsync(String accountName);

}
