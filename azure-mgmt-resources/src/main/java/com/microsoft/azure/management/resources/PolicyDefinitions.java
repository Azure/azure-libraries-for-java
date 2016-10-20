/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingByName;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeleting;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;

/**
 * Entry point to tenant management API.
 */
@Fluent
public interface PolicyDefinitions extends
        SupportsListing<PolicyDefinition>,
        SupportsGettingByName<PolicyDefinition>,
        SupportsCreating<PolicyDefinition.DefinitionStages.Blank>,
        SupportsDeleting {
}
