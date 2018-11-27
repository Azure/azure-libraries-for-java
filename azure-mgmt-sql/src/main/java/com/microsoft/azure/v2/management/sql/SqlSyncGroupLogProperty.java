/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.sql.implementation.SyncGroupLogPropertiesInner;
import org.joda.time.DateTime;

/**
 * An immutable client-side representation of an Azure SQL Server Sync Group.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_9_0)
public interface SqlSyncGroupLogProperty
    extends HasInner<SyncGroupLogPropertiesInner> {

    /**
     * @return timestamp of the sync group log
     */
    DateTime timestamp();

    /**
     * @return the type of the sync group log
     */
    SyncGroupLogType type();

    /**
     * @return the source of the sync group log.
     */
    String source();

    /**
     * @return the details of the sync group log.
     */
    String details();

    /**
     * @return the tracing ID of the sync group log.
     */
    String tracingId();

    /**
     * @return operation status of the sync group log.
     */
    String operationStatus();
}
