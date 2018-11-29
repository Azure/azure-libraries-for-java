/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql;

import com.microsoft.rest.v2.annotations.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.v2.management.sql.implementation.RestorableDroppedDatabaseInner;
import java.time.OffsetDateTime;

/**
 * Response containing Azure SQL restorable dropped database.
 */
@Fluent
@Beta(since = "V1_7_0")
public interface SqlRestorableDroppedDatabase extends
        Refreshable<SqlRestorableDroppedDatabase>,
        HasInner<RestorableDroppedDatabaseInner>,
        HasResourceGroup,
        HasName,
        HasId {

    /**
     * @return the geo-location where the resource lives
     */
    Region region();

    /**
     * @return the name of the database
     */
    String databaseName();

    /**
     * @return the edition of the database
     */
    String edition();

    /**
     * @return the max size in bytes of the database
     */
    String maxSizeBytes();

    /**
     * @return the service level objective name of the database
     */
    String serviceLevelObjective();

    /**
     * @return the elastic pool name of the database
     */
    String elasticPoolName();

    /**
     * @return the creation date of the database (ISO8601 format)
     */
    OffsetDateTime creationDate();

    /**
     * @return the deletion date of the database (ISO8601 format)
     */
    OffsetDateTime deletionDate();

    /**
     * @return the earliest restore date of the database (ISO8601 format)
     */
    OffsetDateTime earliestRestoreDate();
}
