/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources;

import com.microsoft.azure.arm.collection.SupportsCreating;
import com.microsoft.azure.arm.collection.SupportsDeletingById;
import com.microsoft.azure.arm.collection.SupportsListing;
import com.microsoft.azure.arm.resources.collection.SupportsGettingByName;
import com.microsoft.azure.management.apigeneration.Fluent;

/**
 * Entry point to tenant management API.
 */
@Fluent
public interface PolicyDefinitions extends
        SupportsListing<PolicyDefinition>,
        SupportsGettingByName<PolicyDefinition>,
        SupportsCreating<PolicyDefinition.DefinitionStages.Blank>,
        SupportsDeletingById {
}
