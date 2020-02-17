/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.appservice;

import com.microsoft.azure.PagedList;
import com.azure.management.apigeneration.Beta;
import com.azure.core.annotation.Fluent;
import com.azure.management.appservice.implementation.AppServiceManager;
import com.azure.management.appservice.implementation.WebAppsInner;
import com.azure.management.resources.fluentcore.arm.collection.SupportsDeletingByResourceGroup;
import com.azure.management.resources.fluentcore.arm.collection.SupportsGettingByResourceGroup;
import com.azure.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.azure.management.resources.fluentcore.arm.collection.SupportsListingByResourceGroup;
import com.azure.management.resources.fluentcore.arm.models.HasManager;
import com.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.azure.management.resources.fluentcore.collection.SupportsDeletingById;
import com.azure.management.resources.fluentcore.collection.SupportsListing;
import com.azure.management.resources.fluentcore.model.HasInner;

/**
 * Entry point for web app management API.
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.AppService.Fluent")
@Beta
public interface FunctionApps extends
        SupportsCreating<FunctionApp.DefinitionStages.Blank>,
        SupportsDeletingById,
        SupportsListing<FunctionApp>,
        SupportsListingByResourceGroup<FunctionApp>,
        SupportsGettingByResourceGroup<FunctionApp>,
        SupportsGettingById<FunctionApp>,
        SupportsDeletingByResourceGroup,
        HasManager<AppServiceManager>,
        HasInner<WebAppsInner> {

    /**
     * List function information elements.
     *
     * @param resourceGroupName resource group name
     * @param name              function app name
     * @return list of function information elements
     */
    PagedList<FunctionEnvelope> listFunctions(String resourceGroupName, String name);
}
