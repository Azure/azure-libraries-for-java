/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql;

import com.microsoft.rest.v2.annotations.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.v2.management.sql.implementation.SyncFullSchemaPropertiesInner;
import org.joda.time.DateTime;

import java.util.List;

/**
 * An immutable client-side representation of an Azure SQL Server Sync Group.
 */
@Fluent
@Beta(since = "V1_9_0")
public interface SqlSyncFullSchemaProperty
    extends HasInner<SyncFullSchemaPropertiesInner> {

    /**
     * @return the list of tables in the database full schema.
     */
    List<SyncFullSchemaTable> tables();

    /**
     * @return last update time of the database schema.
     */
    DateTime lastUpdateTime();
}
