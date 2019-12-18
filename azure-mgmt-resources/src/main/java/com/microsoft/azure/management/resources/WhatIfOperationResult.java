/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.implementation.WhatIfOperationResultInner;

import java.util.List;

/**
 * An immutable client-side representation of an Azure deployment What-if operation result.
 */
@Fluent
public interface WhatIfOperationResult extends
        HasInner<WhatIfOperationResultInner> {

    /**
     * @return status of the What-If operation.
     */
    String status();

    /**
     * @return list of resource changes predicted by What-If operation.
     */
    List<WhatIfChange> changes();

    /**
     * @return error when What-If operation fails.
     */
    ErrorResponse error();
}
