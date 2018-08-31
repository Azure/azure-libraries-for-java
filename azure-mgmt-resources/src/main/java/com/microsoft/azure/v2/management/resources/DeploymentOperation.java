/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.v2.management.resources.implementation.DeploymentOperationInner;

import java.time.OffsetDateTime;

/**
 * An immutable client-side representation of a deployment operation.
 */
@Fluent
public interface DeploymentOperation extends
        Indexable,
        Refreshable<DeploymentOperation>,
        HasInner<DeploymentOperationInner> {

    /**
     * @return the deployment operation id
     */
    String operationId();

    /**
     *
     * @return the state of the provisioning resource being deployed
     */
    String provisioningState();

    /**
     * @return the date and time of the operation
     */
    OffsetDateTime timestamp();

    /**
     * @return the operation status code.=
     */
    String statusCode();

    /**
     * @return the operation status message
     */
    Object statusMessage();

    /**
     * @return the target resource
     */
    TargetResource targetResource();
}
