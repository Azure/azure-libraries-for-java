/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.sql.implementation.ImportExportResponseInner;

/**
 * Response containing result of the Azure SQL Database import or export operation.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_7_0)
public interface SqlDatabaseImportExportResponse
    extends
        Indexable,
        HasInner<ImportExportResponseInner>,
        HasId,
        HasName {
    /**
     * @return the request type of the operation
     */
    String requestType();

    /**
     * @return the UUID of the operation
     */
    String requestId();

    /**
     * @return the name of the server
     */
    String serverName();

    /**
     * @return the name of the database
     */
    String databaseName();

    /**
     * @return the status message returned from the server
     */
    String status();

    /**
     * @return the operation status last modified time
     */
    String lastModifiedTime();

    /**
     * @return the operation queued time
     */
    String queuedTime();

    /**
     * @return the blob uri
     */
    String blobUri();

    /**
     * @return the error message returned from the server
     */
    String errorMessage();
}
