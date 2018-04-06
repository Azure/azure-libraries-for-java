/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.mediaservices;

import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.mediaservices.implementation.LiveEventsInner;
import rx.Completable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * Type representing LiveEvents.
 */
public interface LiveEvents extends SupportsCreating<LiveEvent.DefinitionStages.Blank>, HasInner<LiveEventsInner> {

    /**
     * @return Entry point to manage LiveEvent LiveOutput.
     */
    LiveOutputs liveOutputs();

    /**
    * Lists the Live Events in the account.
    *
    * @param resourceGroupName The name of the resource group within the Azure subscription.
    * @param accountName The Media Services account name.
    * @throws IllegalArgumentException thrown if parameters fail the validation
    * @return the observable for the request
    */
    Observable<LiveEvent> listByMediaservicesAsync(final String resourceGroupName, final String accountName);

    /**
    * Gets a Live Event.
    *
    * @param resourceGroupName The name of the resource group within the Azure subscription.
    * @param accountName The Media Services account name.
    * @param liveEventName The name of the Live Event.
    * @throws IllegalArgumentException thrown if parameters fail the validation
    * @return the observable for the request
    */
    Observable<LiveEvent> getByMediaserviceAsync(String resourceGroupName, String accountName, String liveEventName);

    /**
    * Deletes a Live Event.
    *
    * @param resourceGroupName The name of the resource group within the Azure subscription.
    * @param accountName The Media Services account name.
    * @param liveEventName The name of the Live Event.
    * @throws IllegalArgumentException thrown if parameters fail the validation
    * @return the observable for the request
    */
    Completable deleteByMediaserviceAsync(String resourceGroupName, String accountName, String liveEventName);
    /**
    * Starts an existing Live Event.
    *
    * @param resourceGroupName The name of the resource group within the Azure subscription.
    * @param accountName The Media Services account name.
    * @param liveEventName The name of the Live Event.
    * @throws IllegalArgumentException thrown if parameters fail the validation
    * @return the observable for the request
    */
    Completable startAsync(String resourceGroupName, String accountName, String liveEventName);

    /**
    * Stops an existing Live Event.
    *
    * @param resourceGroupName The name of the resource group within the Azure subscription.
    * @param accountName The Media Services account name.
    * @param liveEventName The name of the Live Event.
    * @throws IllegalArgumentException thrown if parameters fail the validation
    * @return the observable for the request
    */
    Completable stopAsync(String resourceGroupName, String accountName, String liveEventName);

    /**
    * Resets an existing Live Event.
    *
    * @param resourceGroupName The name of the resource group within the Azure subscription.
    * @param accountName The Media Services account name.
    * @param liveEventName The name of the Live Event.
    * @throws IllegalArgumentException thrown if parameters fail the validation
    * @return the observable for the request
    */
    Completable resetAsync(String resourceGroupName, String accountName, String liveEventName);

}
