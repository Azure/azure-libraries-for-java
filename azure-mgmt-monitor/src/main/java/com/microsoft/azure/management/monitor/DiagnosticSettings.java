/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.monitor.implementation.DiagnosticSettingsInner;
import com.microsoft.azure.management.monitor.implementation.MonitorManager;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsBatchDeletion;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsBatchCreation;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingById;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;
import rx.Observable;

import java.util.List;

/**
 * Entry point for diagnostic settings management API.
 */
@Fluent
public interface DiagnosticSettings extends
        SupportsCreating<DiagnosticSetting.DefinitionStages.Blank>,
        SupportsBatchCreation<DiagnosticSetting>,
        SupportsGettingById<DiagnosticSetting>,
        SupportsDeletingById,
        SupportsBatchDeletion,
        HasManager<MonitorManager>,
        HasInner<DiagnosticSettingsInner> {

    List<DiagnosticSettingsCategory> listCategoriesByResource(String resourceId);

    DiagnosticSettingsCategory getCategory(String resourceId, String name);

    /**
     * Lists all the diagnostic settings in the currently selected subscription for a specific resource.
     *
     * @return list of resources
     */
    PagedList<DiagnosticSetting> listByResource(String resourceId);

    /**
     * Lists all the diagnostic settings in the currently selected subscription for a specific resource.
     *
     * @return list of resources
     */
    Observable<DiagnosticSetting> listByResourceAsync(String resourceId);

    /**
     * Deletes a resource from Azure, identifying it by its resource name.
     *
     * @param name the name of the resource to delete
     */
    void delete(String resourceId, String name);

    /**
     * Asynchronously delete a resource from Azure, identifying it by its resource name.
     *
     * @param name the name of the resource to delete
     * @param callback the callback on success or failure
     * @return a handle to cancel the request
     */
    ServiceFuture<Void> deleteAsync(String resourceId, String name, ServiceCallback<Void> callback);

    /**
     * Asynchronously delete a resource from Azure, identifying it by its resource name.
     *
     * @param name the name of the resource to delete
     * @return a representation of the deferred computation of this call
     */
    Completable deleteAsync(String resourceId, String name);

    /**
     * Gets the information about a resource from Azure based on the resource id.
     *
     * @param id the id of the resource.
     * @return an immutable representation of the resource
     */
    DiagnosticSetting get(String resourceId, String name);

    /**
     * Gets the information about a resource from Azure based on the resource id.
     *
     * @param id the id of the resource.
     * @return an immutable representation of the resource
     */
    Observable<DiagnosticSetting> getAsync(String resourceId, String name);
}
