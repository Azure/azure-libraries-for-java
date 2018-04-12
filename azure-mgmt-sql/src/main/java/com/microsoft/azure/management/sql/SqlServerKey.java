/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import com.microsoft.azure.management.sql.implementation.ServerKeyInner;
import org.joda.time.DateTime;
import rx.Completable;

/**
 * An immutable client-side representation of an Azure SQL Server Key.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_8_0)
public interface SqlServerKey
    extends
        HasId,
        HasInner<ServerKeyInner>,
        HasName,
        HasResourceGroup,
        Indexable,
        Refreshable<SqlServerKey>,
        Updatable<SqlServerKey.Update> {
    /**
     * @return name of the SQL Server to which this DNS alias belongs
     */
    String sqlServerName();

    /**
     * @return the parent SQL server ID
     */
    String parentId();

    /**
     * @return  the kind of encryption protector; this is metadata used for the Azure Portal experience
     */
    String kind();

    /**
     * @return  the resource location
     */
    Region region();

    /**
     * @return the server key type
     */
    ServerKeyType serverKeyType();

    /**
     * @return the URI of the server key
     */
    String uri();

    /**
     * @return  the thumbprint of the server key
     */
    String thumbprint();

    /**
     * @return the server key creation date
     */
    DateTime creationDate();

    /**
     * Deletes the SQL Server Key.
     */
    @Method
    void delete();

    /**
     * Deletes the SQL Server Key asynchronously.
     *
     * @return a representation of the deferred computation of this call
     */
    @Method
    Completable deleteAsync();

    /**
     * The template for a SQL Server Key update operation, containing all the settings that can be modified.
     */
    interface Update extends
        SqlServerKey.UpdateStages.WithThumbprint,
        SqlServerKey.UpdateStages.WithCreationDate,
        Appliable<SqlServerKey> {
    }

    /**
     * Grouping of all the SQL Server Key update stages.
     */
    interface UpdateStages {
        /**
         * The SQL Server Key definition to set the thumbprint.
         */
        @Beta(Beta.SinceVersion.V1_8_0)
        interface WithThumbprint {
            /**
             * Sets the thumbprint of the server key.
             *
             * @param thumbprint the thumbprint of the server key
             * @return The next stage of the definition.
             */
            SqlServerKey.Update withThumbprint(String thumbprint);
        }

        /**
         * The SQL Server Key definition to set the server key creation date.
         */
        @Beta(Beta.SinceVersion.V1_8_0)
        interface WithCreationDate {
            /**
             * Sets the server key creation date.
             *
             * @param creationDate the server key creation date
             * @return The next stage of the definition.
             */
            SqlServerKey.Update withCreationDate(DateTime creationDate);
        }
    }
}
