/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.sql;

import com.microsoft.rest.v2.annotations.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.v2.management.sql.implementation.RestorePointInner;

import java.time.OffsetDateTime;


/**
 * An immutable client-side representation of an Azure SQL database's Restore Point.
 */
@Fluent
@Beta(since = "V1_7_0")
public interface RestorePoint extends
        HasInner<RestorePointInner>,
        HasResourceGroup,
        HasName,
        HasId {
    /**
     * @return name of the SQL Server to which this replication belongs
     */
    String sqlServerName();

    /**
     * @return name of the SQL Database to which this replication belongs
     */
    String databaseName();

    /**
     * @return the ID of the SQL Database to which this replication belongs
     */
    @Beta(since = "V1_7_0")
    String databaseId();

    /**
     * @return the restore point type of the Azure SQL Database restore point.
     */
    RestorePointTypes restorePointType();

    /**
     * @return restore point creation time (ISO8601 format). Populated when
     * restorePointType = CONTINUOUS. Null otherwise.
     */
    OffsetDateTime restorePointCreationDate();

    /**
     * @return earliest restore time (ISO8601 format). Populated when restorePointType
     * = DISCRETE. Null otherwise.
     */
    OffsetDateTime earliestRestoreDate();
}

